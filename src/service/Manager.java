package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

/*Привет вроде исправил все замечаия, а так же по совету моего наставника в классе Epic я поменял HashMap
 * где хранились ранее <subtaskId, Status> на ArrayList хронящий в себе subtaskId. Хотелось бы узнать прошлый вариант
 * был лучше или по факту разницы нет?*/
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
        int subtaskId;
        int epicId = subtask.getEpicId();

        if (checkSizeMapEpics()) {
            subtask.setId(id++);
            subtaskId = subtask.getId();
            repository.getSubtaskHashMap().put(subtaskId, subtask);
            repository.getEpicHashMap().get(epicId).addSubtasksId(subtaskId);
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
        ArrayList<Integer> subtasksId = repository.getEpicHashMap().get(epicId).getSubtasksId();
        Epic epic = repository.getEpicHashMap().get(epicId);

        for (Integer id : subtasksId) {
            subtask.put(id, repository.getSubtaskHashMap().get(id));
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
                epic.getSubtasksId().clear();
                epicUpdateStatus(epic.getId());
            }
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