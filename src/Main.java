import model.Epic;
import model.Subtask;
import model.Task;
import service.HistoryManager;
import service.InMemoryTaskManager;
import model.Status;
import service.Manager;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        TaskManager taskManager = manager.getDefault();
        HistoryManager historyManager = Manager.getDefaultHistory();

        int task1 = taskManager.create(new Task("Task - 1", "Test - 1"));
        int task2 = taskManager.create(new Task("Task - 2", "Test - 2"));
        int task3 = taskManager.create(new Task("Task - 3", "Test - 3"));
        int task4 = taskManager.create(new Task("Task - 4", "Test - 4"));
        int task5 = taskManager.create(new Task("Task - 5", "Test - 5"));

        for (int i = task1; i <= task5; i++) {
            taskManager.getTaskById(i);
        }
        System.out.println(historyManager.getHistory().toString());

        int epic1 = taskManager.create(new Epic("Epic - 1", "Test - 1"));
        int epic2 = taskManager.create(new Epic("Epic - 2", "Test - 2"));
        int epic3 = taskManager.create(new Epic("Epic - 3", "Test - 3"));
        int epic4 = taskManager.create(new Epic("Epic - 4", "Test - 4"));
        int epic5 = taskManager.create(new Epic("Epic - 5", "Test - 5"));

        for (int i = epic1; i <= epic5; i++) {
            taskManager.getEpicById(i);
        }
        System.out.println(historyManager.getHistory().toString());

        int subtask1 = taskManager.create(new Subtask(epic1, "Subtask - 1", "Test - 1"));
        int subtask2 = taskManager.create(new Subtask(epic2, "Subtask - 2", "Test - 1"));
        int subtask3 = taskManager.create(new Subtask(epic3, "Subtask - 3", "Test - 1"));
        int subtask4 = taskManager.create(new Subtask(epic4, "Subtask - 4", "Test - 1"));
        int subtask5 = taskManager.create(new Subtask(epic5, "Subtask - 5", "Test - 1"));

        for (int i = subtask1; i <= subtask5; i++) {
            taskManager.getSubtaskById(i);
        }
        System.out.println(historyManager.getHistory().toString());

        taskManager.getSubtaskById(11);
        System.out.println(historyManager.getHistory().toString());
    }


}