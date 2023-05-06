import model.Epic;
import model.Subtask;
import model.Task;
import service.InMemoryTaskManager;
import model.Status;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        int testTask1 = manager.create(new Task("Задача - 1", "Тест задачи - 1"));
        int testTask2 = manager.create(new Task("Задача - 2", "Тест задачи - 2"));

        int testEpic1 = manager.create(new Epic("Эпическая задача - 1",
                "Тест эпической задачи - 1"));
        int testSubtask1 = manager.create(new Subtask(testEpic1, "Подзадача - 1",
                "Тест подзадачи - 1"));
        int testSubtask2 = manager.create(new Subtask(testEpic1, "Подзадача - 2",
                "Тест подзадачи - 2"));

        int testEpic2 = manager.create(new Epic("Эпическая задача - 2",
                "Тест эпической задачи - 2"));
        int testSubtask3 = manager.create(new Subtask(testEpic2, "Подзадача - 3",
                "Тест подзадачи - 3"));

        System.out.println(manager.getTasks().toString() + '\n');
        System.out.println(manager.getEpics().toString() + '\n');
        System.out.println(manager.getSubtasks().toString() + '\n');
// Статус Задачи не меняется???+
        manager.update(new Task("Задача - 1", "Тест задачи - 3", testTask1, Status.IN_PROGRESS));
        manager.update(new Task("Задача - 2", "Тест задачи - 2", testTask2, Status.DONE));

        System.out.println(manager.getTaskById(testTask1).toString() + '\n');
        System.out.println(manager.getTaskById(testTask2).toString() + '\n');

        manager.update(new Epic("Эпическая задача - Изменено",
                "Тест эпической задачи - Изменено", testEpic2));

        System.out.println(manager.getEpicById(testEpic2).toString() + '\n');

        manager.update(new Subtask(testEpic1, "Подзадача - 1",
                "Тест подзадачи - 1", testSubtask1, Status.DONE));
        manager.update(new Subtask(testEpic1, "Подзадача - 2",
                "Тест подзадачи - 2", testSubtask2, Status.IN_PROGRESS));

        System.out.println(manager.getSubtasksInEpic(testEpic1).toString() + '\n');

        manager.update(new Subtask(testEpic2, "Подзадача - 3",
                "Тест подзадачи - 3", testSubtask3, Status.DONE));

        System.out.println(manager.getSubtasksInEpic(testEpic2).toString() + '\n');

        manager.removeTaskById(testTask1);

        System.out.println(manager.getTasks().toString() + '\n');

        manager.removeEpicById(testEpic1);

        System.out.println(manager.getEpics().toString() + '\n');

    }
}