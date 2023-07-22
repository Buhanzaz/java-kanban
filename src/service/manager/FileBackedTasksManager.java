package service.manager;

import model.TypeTasks;
import service.exception.ManagerSaveException;
import model.*;


import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static service.manager.CSVTaskFormatter.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBacked = new FileBackedTasksManager(file);
        fileBacked.read();
        return fileBacked;
    }

    private void read() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (!Objects.equals(line = br.readLine(), "")) {
                var task = fromString(line);
                int idTask = task.getId();
                if (task.getType() == TypeTasks.TASKS) {
                    repository.getTasksHashMap().put(task.getId(), (Task) task);
                } else if (task.getType() == TypeTasks.EPIC) {
                    repository.getEpicHashMap().put(task.getId(), (Epic) task);
                } else if (task.getType() == TypeTasks.SUBTASK) {
                    repository.getSubtaskHashMap().put(task.getId(), (Subtask) task);
                }
                InMemoryTaskManager.id = idTask;
            }
            while (br.ready()) {
                List<Integer> id = historyFromString(br.readLine());
                for (Integer integer : id) {
                    getTaskById(integer);
                    getSubtaskById(integer);
                    getEpicById(integer);
                }
            }
        } catch (Exception e) {
            throw new ManagerSaveException("Error in read method");
        }
    }

    private void save() {
        try (Writer fileWriter = new FileWriter(file)) {
            String firstLineCSV = "id,type,name,status,description,epicId,duration,startTime\n";
            fileWriter.write(firstLineCSV);
            for (Map.Entry<Integer, Task> taskEntry : repository.getTasksHashMap().entrySet()) {
                fileWriter.write(taskToString(taskEntry.getValue()));
            }
            for (Map.Entry<Integer, Epic> epicEntry : repository.getEpicHashMap().entrySet()) {
                fileWriter.write(taskToString(epicEntry.getValue()));
            }
            for (Map.Entry<Integer, Subtask> subtaskEntry : repository.getSubtaskHashMap().entrySet()) {
                fileWriter.write(taskToString(subtaskEntry.getValue()));
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString(historyManager));
        } catch (Exception e) {
            throw new ManagerSaveException("Error in save method");
        }
    }

    @Override
    public int create(Task task) {
        int id = super.create(task);
        save();
        return id;
    }

    @Override
    public int create(Epic epic) {
        int id = super.create(epic);
        save();
        return id;
    }

    @Override
    public int create(Subtask subtask) {
        int id = super.create(subtask);
        save();
        return id;
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = super.getSubtaskById(subtaskId);
        save();
        return subtask;
    }

    @Override
    public void update(Task task) {
        super.update(task);
        save();
    }

    @Override
    public void update(Epic epic) {
        super.update(epic);
        save();
    }

    @Override
    public void update(Subtask subtask) {
        super.update(subtask);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtask() {
        super.deleteSubtask();
        save();
    }

    @Override
    public void removeTaskById(int taskId) {
        super.removeTaskById(taskId);
        save();
    }

    @Override
    public void removeEpicById(int epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubtaskById(int subtaskId) {
        super.removeSubtaskById(subtaskId);
        save();
    }
}

class Main {
    public static void main(String[] args) {
        File file = new File("FileBacked.csv");
        FileBackedTasksManager fileWright = new FileBackedTasksManager(file);

        int id1Task = fileWright.create(new Task("Task Test 1", "Test 1", 30, LocalDateTime.of(2000, 1, 1, 0, 0, 0, 0)));
        int id2Epic = fileWright.create(new Epic("Epic Test 2", "Test 2"));
        int id3Subtask = fileWright.create(new Subtask(id2Epic, "Subtask Test 3", "Test 3", 30, LocalDateTime.of(2001, 1, 1, 0, 0, 0, 0)));
        int id4Subtask = fileWright.create(new Subtask(id2Epic, "Subtask Test 4", "Test 4", 30, LocalDateTime.of(2002, 1, 1, 0, 0, 0, 0)));
        int id5Task = fileWright.create(new Task("Task Test 5", "Test 5", 30, LocalDateTime.of(2003, 1, 1, 0, 0, 0, 0)));
        int id6Epic = fileWright.create(new Epic("Epic Test 6", "Test 6"));

        fileWright.getPrioritizedTasks().forEach(System.out::println);
        System.out.println();
    }
}