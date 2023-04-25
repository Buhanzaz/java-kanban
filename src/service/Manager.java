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
            for (Integer epicIdInMap : repository.getEpicHashMap().keySet()) {
                if (epicId == epicIdInMap) {
                    subtask.setId(id++);
                    repository.getSubtaskHashMap().put(subtask.getId(), subtask);
                    repository.getEpicHashMap().get(epicId)
                            .setSubtasksIdAndStatus(subtask.getId(), subtask.getStatus());
                    epicUpdateStatus(epicId);
                    return subtask.getId();
                }
            }
            System.out.println("Ошибка! Нет Эпика с данным id!\n");
        } else {
            System.out.println("Ошибка! Нет сложных задач\n");
        }
        return subtask.getId();
    }

    /*Update*/
    public void updateTask(Task task) {
        int taskId = task.getId();
        if (checkSizeMapTask()) {
            for (Integer taskIdInMap : repository.getTasksHashMap().keySet()) {
                if (taskId == taskIdInMap) {
                    repository.getTasksHashMap().put(taskId, task);
                    return;
                }
            }
            System.out.println("Ошибка обновления задачи! Непрвильный id!\n");
        } else {
            System.out.println("Невозможно обнавить задачу, список задач пуст!\n");
        }
    }

    public void updateEpic(Epic epic) {
        if (checkSizeMapEpics()) {
            for (Epic epicInMap : repository.getEpicHashMap().values()) {
                if (epic.equals(epicInMap)) {
                    epicInMap.setName(epic.getName());
                    epic.setDescription(epic.getDescription());
                    return;
                }
            }
            System.out.println("Ошибка обновления Эпика!\n");
        } else {
            System.out.println("Невозможно обнавить Эпическую задачу, список Эпических задач пуст!\n");
        }
    }

    public void updateSubtask(Subtask subtask) {
        int subtaskId = subtask.getId();
        int epicId = subtask.getEpicId();

        if (checkSizeMapEpics()) {
            if (checkSizeMapSubtask()) {
                for (Subtask subtaskInMap : repository.getSubtaskHashMap().values()) {
                    if (subtask.equals(subtaskInMap)) {
                        repository.getSubtaskHashMap().put(subtaskId, subtask);
                        repository.getEpicHashMap().get(epicId)
                                .setSubtasksIdAndStatus(subtaskId, subtask.getStatus());
                        epicUpdateStatus(epicId);
                        return;
                    }
                }
                System.out.println("Ошибка обновления подзадачи!\n");
            } else {
                System.out.println("Невозможно обнавить подзадачу, список подзадач пуст!\n");
            }
        } else {
            System.out.println("Ошибка! Нет сложных задач\n");
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
    public String showTasks() {
        return repository.getTasksHashMap().toString();
    }

    public String showEpics() {
        return repository.getEpicHashMap().toString();
    }

    public String showSubtasks() {
        return repository.getSubtaskHashMap().toString();
    }

    /*Show by ID*/
    public String showTaskById(int taskId) {
        for (Integer taskIdInMap : repository.getTasksHashMap().keySet()) {
            if (taskIdInMap == taskId) {
                return repository.getTasksHashMap().get(taskId).toString();
            }
        }
        return "Ошибка! Задачи с такой id не существует\n";
    }

    public String showEpicById(int epicId) {
        for (Integer epicIdInMap : repository.getEpicHashMap().keySet()) {
            if (epicIdInMap == epicId) {
                return repository.getEpicHashMap().get(epicId).toString();
            }
        }
        return "Ошибка! Эпической задачи с такой id не существует\n";
    }

    public String showSubtaskById(int subtaskId) {
        for (Integer subtaskIdInMap : repository.getSubtaskHashMap().keySet()) {
            if (subtaskIdInMap == subtaskId) {
                return repository.getSubtaskHashMap().get(subtaskId).toString();
            }
        }
        return "Ошибка! Подзадачи с такой id не существует\n";
    }

    /*Show Tasks if Epic*/
    public String showSubtaskInEpic(int epicId) {
        HashMap<Epic, HashMap<Integer, Subtask>> epicAndSubtask = new HashMap<>();
        HashMap<Integer, Subtask> subtask = new HashMap<>();
        HashMap<Integer, Status> subtasksIdAndStatus = repository.getEpicHashMap()
                .get(epicId).getSubtasksIdAndStatus();
        Epic epic = repository.getEpicHashMap().get(epicId);

        for (Integer subtaskId : subtasksIdAndStatus.keySet()) {
            subtask.put(subtaskId, repository.getSubtaskHashMap().get(subtaskId));
        }
        epicAndSubtask.put(epic, subtask);
        return epicAndSubtask.toString();
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
        return repository.getTasksHashMap().size() != 0;
    }

    private boolean checkSizeMapEpics() {
        return repository.getEpicHashMap().size() != 0;
    }

    private boolean checkSizeMapSubtask() {
        return repository.getSubtaskHashMap().size() != 0;
    }
}