package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class Manager {
    /*Попробовать реализацию через одну коллекцию
    Или сделать этот класс абстрактным и от него наследовать остальные для создания конструктора */
    private int id = 0;

    HashMap<Integer, Task> taskHashMap;
    HashMap<Integer, HashMap<Epic, Subtask>> epicHashMap = new HashMap<>();

    public void makeTask(Task task){
        task.setId(id++);
        task.setStatus(Status.NEW);
        taskHashMap.put(task.getId(), task);
    }

    /*Вывод всех задач*/
    /*public void getAllObject(){
        if (tasksList.size() != 0) {
            for (Task task : tasksList) {
                System.out.println(task.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

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
