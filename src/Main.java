import model.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();

        byte id[] = new byte[5];
        for (int i = 1; i <= id.length ; i++) {
            id[i - 1] = (byte) taskManager.create(new Task("Task - " + i, "Test - " + i));
        }

        for (int i = 0; i <= id.length; i++) {
            taskManager.getTaskById(i + 1);
        }
        System.out.println(taskManager.getHistory().toString());

        int epic1 = taskManager.create(new Epic("Epic - 1", "Test - 1"));
        int epic2 = taskManager.create(new Epic("Epic - 2", "Test - 2"));
        int epic3 = taskManager.create(new Epic("Epic - 3", "Test - 3"));
        int epic4 = taskManager.create(new Epic("Epic - 4", "Test - 4"));
        int epic5 = taskManager.create(new Epic("Epic - 5", "Test - 5"));

        for (int i = epic1; i <= epic5; i++) {
            taskManager.getEpicById(i);
        }
        System.out.println(taskManager.getHistory().toString());

        int subtask1 = taskManager.create(new Subtask(epic1, "Subtask - 1", "Test - 1"));
        int subtask2 = taskManager.create(new Subtask(epic2, "Subtask - 2", "Test - 1"));
        int subtask3 = taskManager.create(new Subtask(epic3, "Subtask - 3", "Test - 1"));
        int subtask4 = taskManager.create(new Subtask(epic4, "Subtask - 4", "Test - 1"));
        int subtask5 = taskManager.create(new Subtask(epic5, "Subtask - 5", "Test - 1"));

        for (int i = subtask1; i <= subtask5; i++) {
            taskManager.getSubtaskById(i);
        }
        System.out.println(taskManager.getHistory().toString());

        taskManager.getSubtaskById(11);
        System.out.println(taskManager.getHistory().toString());

        taskManager.removeSubtaskById(subtask1);
        System.out.println(taskManager.getSubtaskById(subtask1));
        System.out.println(taskManager.getSubtasksInEpic(epic1));
    }
}