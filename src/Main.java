import model.Epic;
import model.Subtask;
import model.Task;
import service.Manager;
import service.Status;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        int testTask1 = manager.createTask(new Task("Задача - 1", "Тест задачи - 1"));
        int testTask2 = manager.createTask(new Task("Задача - 2", "Тест задачи - 2"));

        int testEpic1 = manager.createEpic(new Epic("Эпическая задача - 1",
                "Тест эпической задачи - 1"));
        int testSubtask1 = manager.createSubtask(new Subtask(testEpic1, "Подзадача - 1",
                "Тест подзадачи - 1"));
        int testSubtask2 = manager.createSubtask(new Subtask(testEpic1, "Подзадача - 2",
                "Тест подзадачи - 2"));

        int testEpic2 = manager.createEpic(new Epic("Эпическая задача - 2",
                "Тест эпической задачи - 2"));
        int testSubtask3 = manager.createSubtask(new Subtask(testEpic2, "Подзадача - 3",
                "Тест подзадачи - 3"));

        System.out.println(manager.showTasks() + '\n');
        System.out.println(manager.showEpics() + '\n');
        System.out.println(manager.showSubtasks() + '\n');

        manager.updateTask(new Task("Задача - 1", "Тест задачи - 1", testEpic1, Status.IN_PROGRESS));
        manager.updateTask(new Task("Задача - 2", "Тест задачи - 2", testTask2, Status.DONE));

        System.out.println(manager.showTaskById(testTask1) + '\n');
        System.out.println(manager.showTaskById(testTask2) + '\n');

        manager.updateEpic(new Epic("Эпическая задача - Изменено",
                "Тест эпической задачи - Изменено", testEpic2));

        System.out.println(manager.showEpicById(testEpic2) + '\n');

        manager.updateSubtask(new Subtask(testEpic1, "Подзадача - 1",
                "Тест подзадачи - 1", testSubtask1, Status.DONE));
        manager.updateSubtask(new Subtask(testEpic1, "Подзадача - 2",
                "Тест подзадачи - 2", testSubtask2, Status.IN_PROGRESS));

        System.out.println(manager.showSubtaskInEpic(testEpic1) + '\n');

        manager.updateSubtask(new Subtask(testEpic2, "Подзадача - 3",
                "Тест подзадачи - 3", testSubtask3, Status.DONE));

        System.out.println(manager.showSubtaskInEpic(testEpic2) + '\n');

        manager.removeTaskById(testTask1);

        System.out.println(manager.showTasks() + '\n');

        manager.removeEpicById(testEpic1);

        System.out.println(manager.showEpics() + '\n');

    }
}