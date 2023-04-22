package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    public final String NEW_PROGRESS = "NEW";
    static final String IN_PROGRESS = "IN_PROGRESS";
    static final String DONE_PROGRESS = "DONE";



    /*Попробовать реализацию через одну коллекцию
    Или сделать этот класс абстрактным и от него наследовать остальные для создания конструктора */
    ArrayList<Task> tasksList;
    ArrayList<Subtask> subtasksList;
    ArrayList<Epic> epicsList;
    HashMap<Epic, Subtask> epicAndSubtask;

    public Manager() {
        this.tasksList = new ArrayList<>();
    }

    /*Создание объекта*/
    public void makeTask(Task task){
        tasksList.add(task);
    }

    /*Вывод всех задач*/
//    А правильно ли я вывожу? нужно вывести список !!! или выводить его массивом
    public void getAllTask(){
        if (tasksList.size() != 0) {
            for (Task task : tasksList) {
                System.out.println(task.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }
    /*Получение по идентификатору.*/
//    Сделать реализацию через toString
    public void getById(int id){
        Task task = tasksList.get(id);
        String string = task.toString();
        System.out.println(string);
    }

    /*Удаление всех задач*/
    public void deleteAllTask() {
        tasksList.clear();
    }

    /*Удаление по идентификатору*/
    public void removeById(int value) {
        tasksList.remove(value);
    }

    public void updateTask(Task task){
        for (Task tasks : tasksList) {
            if(task.equals(tasks)) {
                //менять ли у обнавленной задачи id на id удаленной задачи???
                int id = tasks.id;
                removeById(tasks.id);
                tasksList.add(id, task);
                task.id = id;
                break;
            } else {
                continue;
            }
        }
    }
    //Идентификатор задачи
    public int identifier() {
        if (tasksList.size() == 0) {
            return 0;
        }
        return tasksList.size();
    }
}
