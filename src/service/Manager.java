package service;

import model.*;

import java.util.ArrayList;

public class Manager {
    Repository repository = new Repository();
    private int id = 1;

    /*Create*/
    public int createTask(Task task) {
        task.setId(id++);
        repository.tasksHashMap.put(task.getId(), task);
        return task.getId();
    }

    public int createEpic(Epic epic) {
        epic.setId(id++);
        repository.epicHashMap.put(epic.getId(), epic);
        return epic.getId();
    }

    public int createSubtask(Subtask subtask) {
        subtask.setId(id++);
        replaceSubtaskAndReturnEpic(subtask).setSubtaskId(subtask.getId());
        return subtask.getId();
    }

    /*Update*/
    public void updateTask(Task task) {
        repository.tasksHashMap.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        ArrayList<Integer> subtaskId = repository.epicHashMap.get(epic.getId()).getSubtaskId();
        repository.epicHashMap.put(epic.getId(), epic);
        for (Integer id : subtaskId) {
            repository.epicHashMap.get(epic.getId()).setSubtaskId(id);
        }
    }

    public void updateSubtask(Subtask subtask) {
        updateStatus(replaceSubtaskAndReturnEpic(subtask));
    }

    /*Epic status update*/
    private void updateStatus(Epic epic) {
        int countDone = 0;
        int countInProgress = 0;

        for (Integer subtaskId : epic.getSubtaskId()) {
            Subtask subItem = repository.subtaskHashMap.get(subtaskId);
            if (subItem.getStatus() == Status.DONE) {
                countDone++;
                if (countDone == epic.getSubtaskId().size()) {
                    epic.setStatus(Status.DONE);
                    break;
                }
            }
            if (subItem.getStatus() == Status.IN_PROGRESS) {
                countInProgress++;
            }
            if (countInProgress > 0 || countDone > 0) {
                epic.setStatus(Status.IN_PROGRESS);
            }

        }
    }

    private Epic replaceSubtaskAndReturnEpic(Subtask subtask) {
        repository.subtaskHashMap.put(subtask.getId(), subtask);
        return repository.epicHashMap.get(subtask.getEpicId());
    }

    /*Show Tasks*/
    public String showTasks() {
        return repository.tasksHashMap.toString();
    }

    public String showEpics() {
        return repository.epicHashMap.toString();
    }

    public String showSubtasks() {
        return repository.subtaskHashMap.toString();
    }

    /*Show by ID*/
    public String showTaskById(int id) {
        return repository.tasksHashMap.get(id).toString();
    }

    public String showEpicById(int id) {
        return repository.epicHashMap.get(id).toString();
    }

    public String showSubtaskById(int id) {
        return repository.subtaskHashMap.get(id).toString();
    }

    /*Show Tasks if Epic*/
    public String showSubtaskInEpic(int epicId) {
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Integer subtaskId : repository.epicHashMap.get(epicId).getSubtaskId()) {
            subtasks.add(repository.subtaskHashMap.get(subtaskId));
        }
        return subtasks.toString();
    }

    /*Delete all Task*/
    public void deleteTasks() {
        repository.tasksHashMap.clear();
    }

    public void deleteEpics() {
        repository.epicHashMap.clear();
    }

    public void deleteSubtask() {
        repository.subtaskHashMap.clear();
    }

    /*Remove by ID*/
    public void removeTaskById(int id) {
        repository.tasksHashMap.remove(id);
    }

    public void removeEpicById(int id) {
        repository.epicHashMap.remove(id);
    }

    public void removeSubtaskById(int id) {
        repository.subtaskHashMap.remove(id);
    }
}