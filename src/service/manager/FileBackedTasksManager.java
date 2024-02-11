package service.manager;

import model.TypeTasks;
import service.exception.ManagerSaveException;
import model.*;


import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static service.manager.CSVTaskFormatter.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;
    private static FileBackedTasksManager fileBacked;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        fileBacked = new FileBackedTasksManager(file);
        fileBacked.read();
        return fileBacked;
    }

    private void read() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (!Objects.equals(line = br.readLine(), "")) {
                AbstractTask task = fromString(line);
                int idTask = task.getId();
                if (task.getType() == TypeTasks.TASKS) {
                    repository.getTasksHashMap().put(task.getId(), (Task) task);
                } else if (task.getType() == TypeTasks.EPIC) {
                    repository.getEpicHashMap().put(task.getId(), (Epic) task);
                } else if (task.getType() == TypeTasks.SUBTASK) {
                    Subtask subtask = (Subtask) task;
                    repository.getSubtaskHashMap().put(subtask.getId(), subtask);
                    Epic epic = getEpicById(subtask.getEpicId());
                    epic.addSubtasksId(subtask.getId());
                    updateEpicTime(epic);
                }
                fileBacked.id = idTask;
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

    protected void save() {
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