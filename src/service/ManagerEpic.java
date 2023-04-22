package service;

import model.Epic;

public class ManagerEpic extends Manager implements ManagerInterface{
    

    public void makeObject(Epic epic){
        epicsList.add(epic);
    }

    /*Вывод всех задач*/
    @Override
    public void getAllObject(){
        if (epicsList.size() != 0) {
            for (Epic epic : epicsList) {
                System.out.println(epic.toString());
            }
        } else {
            System.out.println("Нет задач");
        }
    }

    /*Получение по идентификатору.*/
    @Override
    public void getById(int id){
        Epic epic = epicsList.get(id);
        String string = epic.toString();
        System.out.println(string);
    }

    /*Удаление всех задач*/
    @Override
    public void deleteAll() {
        epicsList.clear();
    }

    /*Удаление по идентификатору*/
    @Override
    public void removeById(int id) {
        epicsList.remove(id);
    }

    /*Обновление задачи*/
    public void updateEpic(Epic epic){
        for (Epic epics : epicsList) {
            if(epic.equals(epics)) {
                //менять ли у обнавленной задачи id на id удаленной задачи???
                int id = epics.id;
                removeById(epics.id);
                epicsList.add(id, epic);
                epic.id = id;
                break;
            }
        }
    }

    /*Идентификация id*/
    @Override
    public int identifier() {
        if (epicsList.size() == 0) {
            return 0;
        }
        return epicsList.size();
    }
}
