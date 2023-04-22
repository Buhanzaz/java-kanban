package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Manager {
    /*Попробовать реализацию через одну коллекцию
    Или сделать этот класс абстрактным и от него наследовать остальные для создания конструктора */
    ArrayList<Task> tasksList = new ArrayList<>();
    ArrayList<Subtask> subtasksList = new ArrayList<>();
    ArrayList<Epic> epicsList = new ArrayList<>();
    HashMap<Epic, Subtask> epicAndSubtask;



}
