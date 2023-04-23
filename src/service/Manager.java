package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class Manager {
    private int id = 1;

    public Manager() {
        this.tasksHashMap = new HashMap<>();
    }

    HashMap<Integer, Task> tasksHashMap;
    HashMap<Integer, HashMap<Epic, Subtask>> epicHashMap = new HashMap<>();

    public void makeTask(Task task){
        task.setId(id++);
        task.setStatus(Status.NEW);
        tasksHashMap.put(task.getId(), task);
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
/*
    *//*Получение по идентификатору.*//*
    public void getById(int id){
        Task task = tasksList.get(id);
        String string = task.toString();
        System.out.println(string);
    }

    *//*Удаление всех задач*//*
    public void deleteAll() {
        tasksList.clear();
    }


    *//*Удаление по идентификатору*//*
    public void removeById(int id) {
        tasksList.remove(id);
    }

    *//*Обновление задачи*//*
    public void updateTask(Task task){
        for (Task tasks : tasksList) {
            if(task.equals(tasks)) {
                //менять ли у обнавленной задачи id на id удаленной задачи???
                int id = tasks.id;
                removeById(tasks.id);
                tasksList.add(id, task);
                task.id = id;
                break;
            }
        }
    }*/

}
