import model.*;
import service.interfaces.TaskManager;
import service.manager.Manager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Manager.getDefault();

        int task1 = taskManager.create(new Task("Task - 1", "Test - 1"));
        int task2 = taskManager.create(new Task("Task - 2", "Test - 2"));
        int epic1 = taskManager.create(new Epic("Epic - 1", "Test - 1"));
        int epic2 = taskManager.create(new Epic("Epic - 2", "Test - 2"));
        int subtask1 = taskManager.create(new Subtask(epic1, "Subtask - 1", "Test - 1"));
        int subtask2 = taskManager.create(new Subtask(epic1, "Subtask - 2", "Test - 2"));
        int subtask3 = taskManager.create(new Subtask(epic1, "Subtask - 3", "Test - 3"));

        taskManager.getTaskById(task1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getTaskById(task1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask3);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getTaskById(task2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getTaskById(task1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask3);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getTaskById(task2);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getEpicById(epic1);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask3);
        System.out.println(taskManager.getHistory().toString());
        taskManager.getSubtaskById(subtask2);
        System.out.println(taskManager.getHistory().toString());

        taskManager.removeTaskById(task1);
        System.out.println(taskManager.getHistory().toString());

        taskManager.removeEpicById(epic1);
        taskManager.getEpicById(44);
        System.out.println(taskManager.getHistory().toString());
    }
}