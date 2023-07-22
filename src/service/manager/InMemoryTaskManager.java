package service.manager;

import model.*;
import service.exception.TaskException;
import service.interfaces.HistoryManager;
import service.interfaces.TaskManager;
import service.storage.Repository;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected static int id = 1;
    protected final Repository repository = new Repository();
    protected final HistoryManager historyManager = Manager.getDefaultHistory();
    protected Set<AbstractTask> sortedTasks = new TreeSet<>((t1, t2) -> {
        if (t1.getStartTime() == null) {
            if (t2.getStartTime() == null) {
                return 0;
            }
            return 1;
        }
        return t1.getStartTime().compareTo(t2.getStartTime());
    });
    //protected TreeSet<AbstractTask> sortedTasks = new TreeSet<>(AbstractTask::compareTo);
    protected final TreeMap<LocalDateTime, AbstractTask> localDateTimeAbstractTaskTreeMap = new TreeMap<>();



    /*Create*/
    @Override
    public int create(Task task) {
        task.setId(id++);
        repository.getTasksHashMap().put(task.getId(), task);
        addPrioritizedTasks(task);
        return task.getId();
    }

    @Override
    public int create(Epic epic) {
        epic.setId(id++);
        repository.getEpicHashMap().put(epic.getId(), epic);
        addPrioritizedTasks(epic);
        return epic.getId();
    }

    @Override
    public int create(Subtask subtask) {
        int subtaskId;
        int epicId = subtask.getEpicId();
        Epic epic = repository.getEpicHashMap().get(epicId);

        if (epic != null) {
            subtask.setId(id++);
            subtaskId = subtask.getId();
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            epic.addSubtasksId(subtaskId);
            epicUpdateStatus(epicId);
            updateTimeEpic(subtask, epic);
            addPrioritizedTasks(subtask);
        }
        return subtask.getId();
    }

    private void updateTimeEpic(Subtask subtask, Epic epic) {
        if (epic.getStartTime() != null) {
            if (epic.getStartTime().isAfter(subtask.getStartTime())) {
                epic.setStartTime(subtask.getStartTime());
                epic.setDuration(epic.getDuration() + subtask.getDuration());
            } else {
                epic.setDuration(epic.getDuration() + subtask.getDuration());
            }
        } else {
            epic.setStartTime(subtask.getStartTime());
            epic.setDuration(subtask.getDuration());
        }
    }

    /*Update*/
    @Override
    public void update(Task task) {
        int taskId = task.getId();
        Task taskInMap = repository.getTasksHashMap().get(taskId);

        if (taskInMap != null) {
            repository.getTasksHashMap().put(taskId, task);
            addPrioritizedTasks(task);
        }
    }

    @Override
    public void update(Epic epic) {
        int epicId = epic.getId();
        Epic epicInMap = repository.getEpicHashMap().get(epicId);

        if (epicInMap != null) {
            epicInMap.setName(epic.getName());
            epicInMap.setDescription(epic.getDescription());
            addPrioritizedTasks(epic);
        }
    }

    @Override
    public void update(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();
        Epic epic = repository.getEpicHashMap().get(epicId);
        Subtask subtaskInMap = repository.getSubtaskHashMap().get(subtaskId);

        if (subtaskInMap != null) {
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            epicUpdateStatus(epicId);
            updateTimeEpic(subtask, epic);
            sortedTasks.remove(getSubtaskById(subtaskId));
            addPrioritizedTasks(subtask);
        }
    }

    /*Show Tasks*/
    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(repository.getTasksHashMap().values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(repository.getEpicHashMap().values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(repository.getSubtaskHashMap().values());
    }

    /*Show by ID*/
    @Override
    public Task getTaskById(int taskId) {
        Task task = repository.getTasksHashMap().get(taskId);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = repository.getEpicHashMap().get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = repository.getSubtaskHashMap().get(subtaskId);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    /*Delete all Task*/
    @Override
    public void deleteTasks() {
        for (Task value : repository.getTasksHashMap().values()) {
            historyManager.remove(value.getId());
        }
        repository.getTasksHashMap().clear();
    }

    @Override
    public void deleteEpics() {
        for (Epic value : repository.getEpicHashMap().values()) {
            historyManager.remove(value.getId());
        }
        repository.getEpicHashMap().clear();
        deleteSubtask();
    }

    @Override
    public void deleteSubtask() {
        for (Subtask value : repository.getSubtaskHashMap().values()) {
            historyManager.remove(value.getId());
        }
        repository.getSubtaskHashMap().clear();
        for (Epic epic : repository.getEpicHashMap().values()) {
            epic.getSubtasksId().clear();
            epicUpdateStatus(epic.getId());
        }
    }

    /*Remove by ID*/
    @Override
    public void removeTaskById(int taskId) {
        historyManager.remove(taskId);
        repository.getTasksHashMap().remove(taskId);
    }

    @Override
    public void removeEpicById(int epicId) {
        List<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();

        historyManager.remove(epicId);
        repository.getEpicHashMap().remove(epicId);
        for (Integer id : subtasksId) {
            removeSubtaskById(id);
        }
    }

    @Override
    public void removeSubtaskById(int subtaskId) {
        int epicId = repository.getSubtaskHashMap().get(subtaskId).getEpicId();
        Epic epic = repository.getEpicHashMap().get(epicId);

        historyManager.remove(subtaskId);
        repository.getSubtaskHashMap().remove(subtaskId);
        if (epic != null) {
            epic.removeSubtaskId(subtaskId);
        }
    }

    /*Show Tasks if Epic*/
    public ArrayList<Subtask> getSubtasksInEpic(int epicId) {
        ArrayList<Subtask> subtask = new ArrayList<>();
        List<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();

        for (Integer id : subtasksId) {
            subtask.add(repository.getSubtaskHashMap().get(id));
        }
        return subtask;
    }

    /*Epic status update*/
    private void epicUpdateStatus(int epicID) {
        int countDone = 0;
        int countInProgress = 0;
        Epic epic = repository.getEpicHashMap().get(epicID);
        List<Integer> subtasksId = epic.getSubtasksId();
        int sizeHashMap = subtasksId.size();

        if (!subtasksId.isEmpty()) {
            for (Integer id : subtasksId) {
                Status statusSubtask = repository.getSubtaskHashMap().get(id).getStatus();

                if (statusSubtask == Status.DONE) {
                    countDone++;
                    if (sizeHashMap == countDone) {
                        epic.setStatus(Status.DONE);
                        break;
                    }
                }
                if (statusSubtask == Status.IN_PROGRESS) {
                    countInProgress++;
                }
                if (countInProgress > 0 || countDone > 0) {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
        } else {
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public List<AbstractTask> getHistory() {
        return historyManager.getHistory();
    }

    public List<AbstractTask> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasks);
    }


    public void addPrioritizedTasks(AbstractTask task) {
        LocalDateTime startTimeNewTask = task.getStartTime();
        LocalDateTime endTimeNewTask = task.getEndTime();

        for (AbstractTask taskSort : sortedTasks) {
            LocalDateTime startTimeSortedTask = taskSort.getStartTime();
            LocalDateTime endTimeSortedTask = taskSort.getEndTime();

            if (startTimeSortedTask == null || startTimeNewTask == null || task.equals(taskSort)) {
                continue;
            }
            if (!endTimeNewTask.isAfter(startTimeSortedTask) || !endTimeSortedTask.isAfter(startTimeNewTask) ||
                    taskSort.getId() == task.getEpicId()) {
                continue;
            }
            throw new TaskException("Задача " + task.getId() + "пересекается с " + taskSort.getId());
        }
        sortedTasks.add(task);
    }
}
