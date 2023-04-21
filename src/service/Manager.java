package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    static final String NEW_PROGRESS = "NEW";
    static final String IN_PROGRESS = "IN_PROGRESS";
    static final String DONE_PROGRESS = "DONE";
    private int countId = 0;//Сменить название

    /*Попробовать реализацию через одну коллекцию*/
    ArrayList<Task> taskList = new ArrayList<>();

    /*Создание объекта*/
    public void makeTask(String name, String description, int id){
        Task task = new Task(name, description, id, NEW_PROGRESS);
        taskList.add(task);
    }

    public void getAllTask(){
        for (Task task : taskList) {
            System.out.println(task.name);
            System.out.println(task.description);
            System.out.println(task.id);
            System.out.println(task.status);
        }
    }



    //Идентификатор задачи

}
