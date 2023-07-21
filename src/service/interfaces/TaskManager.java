package service.interfaces;

import model.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    /*Create*/
    int create(Task task);

    int create(Epic epic);

    int create(Subtask subtask);


    /*Update*/
    void update(Task task);

    void update(Epic epic);

    void update(Subtask subtask);


    /*Show Tasks*/
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();


    /*Show by ID*/
    Task getTaskById(int taskId);

    Epic getEpicById(int epicId);

    Subtask getSubtaskById(int subtaskId);

    ArrayList<Subtask> getSubtasksInEpic(int epicId);


    /*Delete all Task*/
    void deleteTasks();

    void deleteEpics();

    void deleteSubtask();

    /*Remove by ID*/
    void removeTaskById(int taskId);

    void removeEpicById(int epicId);

    void removeSubtaskById(int subtaskId);

    List<AbstractTask> getHistory();

}