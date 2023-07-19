package service.manager;

import model.*;
import service.interfaces.HistoryManager;
import model.TypeTasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormatter {
    private CSVTaskFormatter() {
    }

    static String taskToString(AbstractTask task) {
        String taskToString = null;

        if (TypeTasks.TASKS == task.getType()) {
            taskToString = String.format("%d,%s,%s,%s,%s,%d,%s\n", task.getId(), task.getType(), task.getName(), task.getStatus(), task.getDescription(), task.getDuration(), task.getStartTime());
        } else if (TypeTasks.EPIC == task.getType()) {
            taskToString = String.format("%d,%s,%s,%s,%s,%d,%s\n", task.getId(), task.getType(), task.getName(), task.getStatus(), task.getDescription(), task.getDuration(), task.getStartTime());
        } else if (TypeTasks.SUBTASK == task.getType()) {
            taskToString = String.format("%d,%s,%s,%s,%s,%d\n", task.getId(), task.getType(), task.getName(), task.getStatus(), task.getDescription(), task.getEpicId());
        }
        return taskToString;
    }

    static AbstractTask fromString(String value) {
        AbstractTask abstractTask = null;
        String[] data = value.split(",");
        TypeTasks name = TypeTasks.valueOf(data[1]);

        if (TypeTasks.TASKS == name) {
            abstractTask = new Task(data[2], data[4], Integer.parseInt(data[5]), LocalDateTime.parse(data[6]));
        } else if (TypeTasks.EPIC.equals(name)) {
            abstractTask = new Epic(data[2], data[4]);
        } else if (TypeTasks.SUBTASK.equals(name)) {
            abstractTask = new Subtask(Integer.parseInt(data[5]), data[4], data[2]);
        }

        abstractTask.setId(Integer.parseInt(data[0]));
        abstractTask.setStatus(Status.valueOf(data[3]));
        return abstractTask;
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
}
