package service;

import model.*;

import java.util.HashMap;

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

    // Нужно сделать проверку на то есть ли эпик или нет
    public int createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();

        if (checkSizeMapEpics()) {
            subtask.setId(id++);
            repository.getSubtaskHashMap().put(subtask.getId(), subtask);
            repository.getEpicHashMap().get(epicId)
                    .setSubtasksIdAndStatus(subtask.getId(), subtask.getStatus());
            epicUpdateStatus(epicId);
        }
        return subtask.getId();
    }

    /*Update*/
    public void updateTask(Task task) {
        int taskId = task.getId();

        if (checkSizeMapTask()) {
            repository.getTasksHashMap().put(taskId, task);
        }
    }

    public void updateEpic(Epic epic) {
        int epicId = epic.getId();
        Epic epicInMap = repository.getEpicHashMap().get(epicId);

        if (checkSizeMapEpics()) {
            epicInMap.setName(epic.getName());
            epicInMap.setDescription(epic.getDescription());
        }
    }

    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();

        if (checkSizeMapSubtask()) {
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            repository.getEpicHashMap().get(epicId)
                    .setSubtasksIdAndStatus(subtaskId, subtask.getStatus());
            epicUpdateStatus(epicId);
        }
    }

    /*Epic status update*/
    private void epicUpdateStatus(int epicID) {
        int countDone = 0;
        int countInProgress = 0;
        Epic epic = repository.getEpicHashMap().get(epicID);
        HashMap<Integer, Status> subtasksIdAndStatus = epic.getSubtasksIdAndStatus();
        int sizeHashMap = subtasksIdAndStatus.size();

        if (sizeHashMap != 0) {
            for (Status status : subtasksIdAndStatus.values()) {
                if (status == Status.DONE) {
                    countDone++;
                    if (sizeHashMap == countDone) {
                        epic.setStatus(Status.DONE);
                        break;
                    }
                }
                if (status == Status.IN_PROGRESS) {
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
    public HashMap<Integer, Task> showTasks() {
        return repository.getTasksHashMap();
    }

    public HashMap<Integer, Epic> showEpics() {
        return repository.getEpicHashMap();
    }

    public HashMap<Integer, Subtask> showSubtasks() {
        return repository.getSubtaskHashMap();
    }

    /*Show by ID*/
    public Task showTaskById(int taskId) {
        return repository.getTasksHashMap().get(taskId);
    }

    public Epic showEpicById(int epicId) {
        return repository.getEpicHashMap().get(epicId);
    }

    public Subtask showSubtaskById(int subtaskId) {
        return repository.getSubtaskHashMap().get(subtaskId);
    }

    /*Show Tasks if Epic*/
    public HashMap<Epic, HashMap<Integer, Subtask>> showSubtaskInEpic(int epicId) {
        HashMap<Epic, HashMap<Integer, Subtask>> epicAndSubtask = new HashMap<>();
        HashMap<Integer, Subtask> subtask = new HashMap<>();
        HashMap<Integer, Status> subtasksIdAndStatus = repository.getEpicHashMap()
                .get(epicId).getSubtasksIdAndStatus();
        Epic epic = repository.getEpicHashMap().get(epicId);

        for (Integer subtaskId : subtasksIdAndStatus.keySet()) {
            subtask.put(subtaskId, repository.getSubtaskHashMap().get(subtaskId));
        }
        epicAndSubtask.put(epic, subtask);
        return epicAndSubtask;
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
        if (!checkSizeMapEpics()) {
            for (Epic epic : repository.getEpicHashMap().values()) {
                epic.getSubtasksIdAndStatus().clear();
                epicUpdateStatus(epic.getId());
            }
        }
    }

    /*Remove by ID*/
    public void removeTaskById(int taskId) {
        repository.getTasksHashMap().remove(taskId);
    }

    public void removeEpicById(int epicId) {
        HashMap<Integer, Status> subtasksIdAndStatus = repository.getEpicHashMap()
                .get(epicId).getSubtasksIdAndStatus();

        repository.getEpicHashMap().remove(epicId);
        for (Integer subtaskId : subtasksIdAndStatus.keySet()) {
            removeSubtaskById(subtaskId);
        }
    }

    public void removeSubtaskById(int subtaskId) {
        int epicId = repository.getSubtaskHashMap().get(subtaskId).getEpicId();
        Epic epic = repository.getEpicHashMap().get(epicId);

        repository.getSubtaskHashMap().remove(subtaskId);
        for (Integer epicIdInMap : repository.getEpicHashMap().keySet()) {
            if (epicId == epicIdInMap) {
                epic.getSubtasksIdAndStatus().remove(subtaskId);
                epicUpdateStatus(epicId);
            }
        }
    }

    /*Check size Map*/
    private boolean checkSizeMapTask() {
        return !repository.getTasksHashMap().isEmpty();
    }

    private boolean checkSizeMapEpics() {
        return !repository.getEpicHashMap().isEmpty();
    }

    private boolean checkSizeMapSubtask() {
        return !repository.getSubtaskHashMap().isEmpty();
    }
}