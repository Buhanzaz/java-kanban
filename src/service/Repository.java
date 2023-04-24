package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.HashMap;

public class Repository {
    protected HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    protected HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    protected HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();
}
