import model.*;
import service.interfaces.TaskManager;
import service.manager.Manager;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();

        int epicId = taskManager.create((Task) null);
        int subtaskId = taskManager.create(new Subtask(epicId, "Subtask - 2", "Subtask - 2", 30, LocalDateTime.now()));
        int subtaskId2 = taskManager.create(new Subtask(epicId, "Subtask - 3", "Subtask - 3", 30, LocalDateTime.now()));
        int a = taskManager.getEpicById(epicId).getDuration();
        System.out.println(taskManager.getTaskById(epicId));
    }
}