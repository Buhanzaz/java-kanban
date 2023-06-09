package service.manager;

import model.*;
import service.interfaces.HistoryManager;
import service.storage.Repository;
import service.interfaces.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected static int id = 1;
    protected final Repository repository = new Repository();
    protected final HistoryManager historyManager = Manager.getDefaultHistory();


    /*Create*/
    @Override
    public int create(Task task) {
        task.setId(id++);
        repository.getTasksHashMap().put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int create(Epic epic) {
        epic.setId(id++);
        repository.getEpicHashMap().put(epic.getId(), epic);
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
        }
        return subtask.getId();
    }

    /*Update*/
    @Override
    public void update(Task task) {
        int taskId = task.getId();
        Task taskInMap = repository.getTasksHashMap().get(taskId);

        if (taskInMap != null) {
            repository.getTasksHashMap().put(taskId, task);
        }
    }

    @Override
    public void update(Epic epic) {
        int epicId = epic.getId();
        Epic epicInMap = repository.getEpicHashMap().get(epicId);

        if (epicInMap != null) {
            epicInMap.setName(epic.getName());
            epicInMap.setDescription(epic.getDescription());
        }
    }

    @Override
    public void update(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask subtaskInMap = repository.getSubtaskHashMap().get(subtaskId);

        if (subtaskInMap != null) {
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            epicUpdateStatus(epicId);
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
        ArrayList<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();

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
        ArrayList<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();

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
        ArrayList<Integer> subtasksId = epic.getSubtasksId();
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

}
