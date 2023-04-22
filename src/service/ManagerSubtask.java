package service;


import model.Subtask;

public class ManagerSubtask extends Manager implements ManagerInterface{
    public void makeObject(Subtask subtask){
        subtasksList.add(subtask);
    }

    /*Вывод всех задач*/
    @Override
    public void getAllObject(){
        if (subtasksList.size() != 0) {
            for (Subtask subtasks : subtasksList) {
                System.out.println(subtasks.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

    /*Получение по идентификатору.*/
    @Override
    public void getById(int id){
        Subtask subtasks = subtasksList.get(id);
        String string = subtasks.toString();
        System.out.println(string);
    }

    /*Удаление всех задач*/
    @Override
    public void deleteAll() {
        subtasksList.clear();
    }

    /*Удаление по идентификатору*/
    @Override
    public void removeById(int id) {
        subtasksList.remove(id);
    }

    /*Обновление задачи*/
    public void updateSubtask(Subtask subtask){
        for (Subtask sub : subtasksList) {
            if(subtask.equals(sub)) {
                //менять ли у обнавленной задачи id на id удаленной задачи???
                int id = sub.id;
                removeById(sub.id);
                subtasksList.add(id, subtask);
                subtask.id = id;
                break;
            }
        }
    }

    /*Идентификация id*/
    @Override
    public int identifier() {
        if (subtasksList.size() == 0) {
            return 0;
        }
        return subtasksList.size();
    }
}

