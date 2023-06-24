package service;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String nameFile;
    private final String firstLineCSV = "id,type,name,status,description,epic\n";

    public FileBackedTasksManager(String nameFile) {
        this.nameFile = nameFile;
    }

    static List<Integer> historyFromString(String value) {
        String[] data = value.split(",");
        List<Integer> id = new ArrayList<>();

        for (String line : data) {
            id.add(Integer.parseInt(line));
        }
        return id;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder s = new StringBuilder();
        for (AbstractTask abstractTask : manager.getHistory()) {
            s.append(abstractTask.getId()).append(",");
        }
        return s.toString();
    }

    @Override
    public int create(Task task) throws IOException {
        int id = super.create(task);
        save();
        return id;
    }

    @Override
    public int create(Epic epic) throws IOException {
        int id = super.create(epic);
        save();
        return id;
    }

    @Override
    public int create(Subtask subtask) throws IOException {
        int id = super.create(subtask);
        save();
        return id;
    }

    @Override
    public Task getTaskById(int taskId) throws IOException {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) throws IOException {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) throws IOException {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();
        return subtask;
    }

    private String toString(AbstractTask task) {
        String taskToString;
        if (task instanceof Task) {
            taskToString = String.format("%d,%s,%s,%s,%s\n", task.getId(), NameTasks.TASKS,
                    task.getName(), task.getStatus(), task.getDescription() + ',');
        } else if (task instanceof Epic) {
            taskToString = String.format("%d,%s,%s,%s,%s\n", task.getId(), NameTasks.EPIC,
                    task.getName(), task.getStatus(), task.getDescription());
        } else {
            taskToString = String.format("%d,%s,%s,%s,%s,%d\n", task.getId(), NameTasks.SUBTASK,
                    task.getName(), task.getStatus(), task.getDescription(), task.getEpicId());
        }
        return taskToString;
    }

    private AbstractTask fromString(String value) throws IOException {
        String[] data = value.split(",");
        NameTasks name = NameTasks.valueOf(data[1]);

        if (NameTasks.TASKS.equals(name)) {
            AbstractTask task = new Task(data[2], data[4]);
            task.setStatus(Status.valueOf(data[3]));
            return task;
        } else if (NameTasks.EPIC.equals(name)) {
            AbstractTask epic = new Epic(data[2], data[4]);
            epic.setStatus(Status.valueOf(data[3]));
            return epic;
        }
        AbstractTask subtask = new Subtask(Integer.parseInt(data[5]), data[4], data[2]);
        subtask.setStatus(Status.valueOf(data[3]));
        return subtask;
    }

    public void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nameFile));
        while (br.ready()) {
            AbstractTask task = fromString(br.readLine());
            if (task instanceof Task) {
                create((Task) task);
            } else if (task instanceof Epic) {
                create((Epic) task);
            } else if (task instanceof Subtask) {
                create((Subtask) task);
            }
        }
    }


    //Попробовать реализовать через одну мапу)
    public void save() throws IOException {
        try (Writer fileWriter = new FileWriter(nameFile)) {
            //fileWriter.write(firstLineCSV);
            for (Map.Entry<Integer, Task> taskEntry : repository.getTasksHashMap().entrySet()) {
                fileWriter.write(toString(taskEntry.getValue()));
            }
            for (Map.Entry<Integer, Epic> epicEntry : repository.getEpicHashMap().entrySet()) {
                fileWriter.write(toString(epicEntry.getValue()));
            }
            for (Map.Entry<Integer, Subtask> subtaskEntry : repository.getSubtaskHashMap().entrySet()) {
                fileWriter.write(toString(subtaskEntry.getValue()));
            }
//            fileWriter.write("\n");
//            fileWriter.write(historyToString(historyManager));
        }
    }
}
