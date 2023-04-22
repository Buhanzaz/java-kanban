package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Manager {
    /*Попробовать реализацию через одну коллекцию
    Или сделать этот класс абстрактным и от него наследовать остальные для создания конструктора */
    ArrayList<Task> tasksList;
    ArrayList<Subtask> subtasksList;
    ArrayList<Epic> epicsList;
    HashMap<Epic, Subtask> epicAndSubtask;

    public Manager() {
        this.tasksList = new ArrayList<>();
        this.subtasksList = new ArrayList<>();
        this.epicsList = new ArrayList<>();
    }
}
