package service;

import model.Task;

import java.util.ArrayList;

public class ManagerTask extends Manager implements ManagerInterface{
    super(tasksList);

    public void makeObject(Task task){
        tasksList.add(task);
    }

    /*Вывод всех задач*/
    @Override
    public void getAllObject(){
        if (tasksList.size() != 0) {
            for (Task task : tasksList) {
                System.out.println(task.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

    /*Получение по идентификатору.*/
    @Override
    public void getById(int id){
        Task task = tasksList.get(id);
        String string = task.toString();
        System.out.println(string);
    }

    /*Удаление всех задач*/
    @Override
    public void deleteAll() {
        tasksList.clear();
    }


    /*Удаление по идентификатору*/
    @Override
    public void removeById(int id) {
        tasksList.remove(id);
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
            }
        }
    }
    @Override
    public int identifier() {
        if (tasksList.size() == 0) {
            return 0;
        }
        return tasksList.size();
    }
}
