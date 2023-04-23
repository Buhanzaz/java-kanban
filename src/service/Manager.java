package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class Manager {
    private int id = 1;



    HashMap<Integer, Task> tasksHashMap;
    HashMap<Epic, Subtask> epicHashMap;

    public Manager() {
        this.tasksHashMap = new HashMap<>();
        this.epicHashMap = new HashMap<>();
    }

    /*Ввод задач*/
    public void makeTask(Task task){
        task.setId(id++);
        task.setStatus(Status.NEW);
        tasksHashMap.put(task.getId(), task);
    }

    public void makeEpic(Epic epic){
        epic.setId(id++);
        epic.setStatus(Status.NEW);
        epicHashMap.put(epic, null);
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

    public void getAllEpic(){
        if (epicHashMap.size() != 0) {
            for (Epic epic : epicHashMap.keySet()) {
                System.out.println(epic.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

    /*Получение по идентификатору.*/
    public void getTaskById(int id){
        System.out.println(tasksHashMap.get(id).toString());
    }

    /*Удаление всех задач*/
    public void deleteAllTask() {
        tasksHashMap.clear();
    }


    /*Удаление по идентификатору*/
    public void removeTaskById(int id) {
        tasksHashMap.remove(id);
    }

    /*Обновление задачи*/
    public void updateTask(Task task){
        for (Task tasks : tasksHashMap.values()) {
            if(task.equals(tasks)) {
                //менять ли у обнавленной задачи id на id удаленной задачи???
                int id = tasks.getId();
                removeTaskById(tasks.getId());
                tasksHashMap.put(id, task);
                task.setId(id);
                break;
            }
        }
    }

}
