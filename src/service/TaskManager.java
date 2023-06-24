package service;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    /*Create*/
    int create(Task task) throws IOException;

    int create(Epic epic) throws IOException;

    int create(Subtask subtask) throws IOException;


    /*Update*/
    public void update(Task task);

    void update(Epic epic);

    void update(Subtask subtask);


    /*Show Tasks*/
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();


    /*Show by ID*/
    Task getTaskById(int taskId) throws IOException;

    Epic getEpicById(int epicId) throws IOException;

    Subtask getSubtaskById(int subtaskId) throws IOException;

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