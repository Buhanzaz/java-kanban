import model.*;
import service.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Manager.getDefault();
        FileBackedTasksManager fileBackedTasksManager = Manager.getDefaultFileBacked();

        /*fileBackedTasksManager.create(new Task("Task - 1", "Test Task - 1"));
        fileBackedTasksManager.create(new Task("Task - 2", "Test Task - 2"));
        fileBackedTasksManager.create(new Task("Task - 3", "Test Task - 3"));
        fileBackedTasksManager.create(new Epic("Epic - 4", "Test Epic - 4"));
        fileBackedTasksManager.create(new Epic("Epic - 5", "Test Epic - 6"));
        fileBackedTasksManager.create(new Subtask(4,"Subtask -7", "Subtask -7"));*/
        fileBackedTasksManager.read();

        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println(fileBackedTasksManager.getSubtasks());

    }
}