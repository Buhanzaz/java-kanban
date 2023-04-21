package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static final String NEW_PROGRESS = "NEW";
    static final String IN_PROGRESS = "IN_PROGRESS";
    static final String DONE_PROGRESS = "DONE";



    /*Попробовать реализацию через одну коллекцию
    Или сделать этот класс абстрактным и от него наследовать остальные для создания конструктора */
    ArrayList<Task> taskList;
    ArrayList<Subtask> subtasksList;
    ArrayList<Epic> epicsList;
    HashMap<Epic, Subtask> epicAndSubtask;


    /*Создание объекта*/
    public void makeTask(String name, String description){
        Task task = new Task(name, description, identifier(), NEW_PROGRESS);
        taskList.add(task);
    }

    /*Вывод всех задач*/
//    А правильно ли я вывожу? нужно вывести список !!! или выводить его массивом
    public void getAllTask(){
        for (Task task : taskList) {
            System.out.println(task.name);
            System.out.println(task.description);
            System.out.println(task.id);
            System.out.println(task.status);
        }
    }

    /*Удаление всех задач*/
    public void deleteAllTask() {
        taskList.clear();
    }

    /*Удаление по идентификатору*/
    public void removeById(int value) {
            taskList.remove(value);
    }




    //Идентификатор задачи
    private int identifier() {
        if (taskList.size() == 0) {
            return 0;
        }
        return taskList.size();
    }
}
