package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int taskId = 0;
    private int epicId = 0;
    private int subtaskId = 0;

    HashMap<Integer, Task> tasksHashMap;
    HashMap<Integer, HashMap<Epic, ArrayList<Subtask>>> epicAndSub;

    public Manager() {
        this.tasksHashMap = new HashMap<>();
        this.epicAndSub = new HashMap<>();
    }

    public int taskIdGenerator() {
        return taskId++;
    }

    public int epicIdGenerator() {
        return epicId++;
    }

    public int subtaskIdGenerator() {
        return subtaskId++;
    }

    /*Ввод задач*/
    public void makeTask(Task task){
        tasksHashMap.put(task.getId(), task);
    }

    public void makeEpic(Epic epic){
        ArrayList<Subtask> sub = new ArrayList<>();
        HashMap<Epic, ArrayList<Subtask>> epicSubtask = new HashMap<>();
        epicSubtask.put(epic, sub);
        epicAndSub.put(epic.getId(), epicSubtask);
    }

    // Попробовать убрать проверку id
    public void makeSubtask(Subtask subtask) {
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (Epic epic : value.keySet()) {
                if (epic.getId() == subtask.getEpicId()) {
                    for (ArrayList<Subtask> subtasks : value.values()) {
                        subtasks.add(subtask);
                    }
                }
            }
        }
    }



    /*Вывод всех задач*/
    public void getAllTask(){
        if (tasksHashMap.size() != 0) {
            for (Task task : tasksHashMap.values()) {
                System.out.println(task.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

    public void getAllEpic() {
        for (HashMap<Epic, ArrayList<Subtask>> epicMap : epicAndSub.values()) {
            for (Epic epic : epicMap.keySet()) {
                System.out.println(epic.toString());
            }
        }
    }

    public void getAllSubtask() {
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (ArrayList<Subtask> subtasks : value.values()) {
                if (subtasks.size() != 0) {
                    System.out.println(subtasks.toString());
                }
            }
        }
    }

    public void getEpicAndSubtask() {
        for (Integer integer : epicAndSub.keySet()) {
            System.out.println(integer);
            System.out.println(epicAndSub.get(integer));
        }
    }

    /*Получение по идентификатору.*/
    public void getTaskById(int id){
        System.out.println(tasksHashMap.get(id).toString());
    }

    public void getEpicById(int id){
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (Epic epic : value.keySet()) {
                if (epic.getId() == id) {
                    System.out.println(epic.toString());
                }
            }
        }
    }

    /*Удаление всех задач*/
    public void deleteAllTask() {
        tasksHashMap.clear();
    }

    public void deleteAllEpic() {
        epicAndSub.clear();
    }

    public void deleteAllSubtask() {
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (ArrayList<Subtask> subtasks : value.values()) {
                subtasks.clear();
            }
        }
    }

    /*Удаление по идентификатору*/
    public void removeTaskById(int id) {
        tasksHashMap.remove(id);
    }

    public void removeEpicById(int id) {
        epicAndSub.remove(id);
    }

    public void removeSubtaskById(int idEpic, int id) {
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (Epic epic : value.keySet()) {
                if (epic.getId() == idEpic) {
                    for (ArrayList<Subtask> subtasks : value.values()) {
                        subtasks.remove(id);
                    }
                }
            }
        }
    }

    /*Обновление задачи*/
    public void updateTask(Task task){
        for (Task tasks : tasksHashMap.values()) {
            if(task.equals(tasks)) {
                int id = tasks.getId();
                task.setId(id);
                tasksHashMap.put(id, task);
                break;
            }
        }
    }

    public void updateEpic(Epic epic){
        ArrayList<Subtask> sub = new ArrayList<>();
        HashMap<Epic, ArrayList<Subtask>> epics = new HashMap<>();
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (Epic epicKey : value.keySet()) {
                if (epic.equals(epicKey)) {
                    for (ArrayList<Subtask> subtasks : value.values()) {
                        sub.addAll(subtasks);
                    }
                    break;
                }
            }
        }
        epics.put(epic, sub);
        epicAndSub.put(epic.getId(), epics);
    }

    public void updateSubtask(Subtask subtask) {
        for (HashMap<Epic, ArrayList<Subtask>> value : epicAndSub.values()) {
            for (ArrayList<Subtask> subtasks : value.values()) {
                for (Subtask subtask1 : subtasks) {
                    if (subtask1.equals(subtask)) {
                        subtasks.remove(subtask1);
                        subtasks.add(subtask.getId(), subtask);
                    }
                }
            }
        }
    }
}




