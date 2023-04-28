package service;

import model.*;

import java.util.ArrayList;

public class Manager {
    private final Repository repository = new Repository();
    private int id = 1;

    /*Create*/
    public int createTask(Task task) {
        task.setId(id++);
        repository.getTasksHashMap().put(task.getId(), task);
        return task.getId();
    }

    public int createEpic(Epic epic) {
        epic.setId(id++);
        repository.getEpicHashMap().put(epic.getId(), epic);
        return epic.getId();
    }

    public int createSubtask(Subtask subtask) {
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
    public void updateTask(Task task) {
        int taskId = task.getId();
        Task taskInMap = repository.getTasksHashMap().get(taskId);

        if (taskInMap != null) {
            repository.getTasksHashMap().put(taskId, task);
        }
    }

    public void updateEpic(Epic epic) {
        int epicId = epic.getId();
        Epic epicInMap = repository.getEpicHashMap().get(epicId);

        if (epicInMap != null) {
            epicInMap.setName(epic.getName());
            epicInMap.setDescription(epic.getDescription());
        }
    }

    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();
        Subtask subtaskInMap = repository.getSubtaskHashMap().get(subtaskId);

        if (subtaskInMap != null) {
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            epicUpdateStatus(epicId);
        }
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

    /*Show Tasks*/
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(repository.getTasksHashMap().values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(repository.getEpicHashMap().values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(repository.getSubtaskHashMap().values());
    }

    /*Show by ID*/
    public Task getTaskById(int taskId) {
        return repository.getTasksHashMap().get(taskId);
    }

    public Epic getEpicById(int epicId) {
        return repository.getEpicHashMap().get(epicId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return repository.getSubtaskHashMap().get(subtaskId);
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

    /*Delete all Task*/
    public void deleteTasks() {
        repository.getTasksHashMap().clear();
    }

    public void deleteEpics() {
        repository.getEpicHashMap().clear();
        deleteSubtask();
    }

    public void deleteSubtask() {
        repository.getSubtaskHashMap().clear();
        for (Epic epic : repository.getEpicHashMap().values()) {
            epic.getSubtasksId().clear();
            epicUpdateStatus(epic.getId());
        }
    }

    /*Remove by ID*/
    public void removeTaskById(int taskId) {
        repository.getTasksHashMap().remove(taskId);
    }

    public void removeEpicById(int epicId) {
        ArrayList<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();

        repository.getEpicHashMap().remove(epicId);
        for (Integer id : subtasksId) {
            removeSubtaskById(id);
        }
    }

    public void removeSubtaskById(int subtaskId) {
        int epicId = repository.getSubtaskHashMap().get(subtaskId).getEpicId();
        Epic epic = repository.getEpicHashMap().get(epicId);

        repository.getSubtaskHashMap().remove(subtaskId);
        if (epic != null) {
            epic.removeSubtaskId(subtaskId);
        }
    }
}