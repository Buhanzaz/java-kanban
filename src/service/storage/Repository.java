package service.storage;

import model.*;
import java.util.HashMap;

public class Repository {
    private final HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();

    public HashMap<Integer, Task> getTasksHashMap() {
        return tasksHashMap;
    }

    public HashMap<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }

    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }
}