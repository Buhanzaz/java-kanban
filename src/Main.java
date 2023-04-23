import model.Epic;
import model.Subtask;
import model.Task;
import service.Manager;
import service.Status;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.makeTask(new Task("Задача - 1", "Тест - 1", manager.taskIdGenerator(), Status.NEW));
        manager.makeTask(new Task("Задача - 2", "Тест - 2", manager.taskIdGenerator(), Status.NEW));
        manager.updateTask(new Task("Задача - 3", "Тест - 3", 0 , Status.NEW));

        manager.makeEpic(new Epic("Епическая Задача - 1", "Епический тест - 1", manager.epicIdGenerator(), Status.NEW));
        manager.makeEpic(new Epic("Епическая Задача - 2", "Епический тест - 2", manager.epicIdGenerator(), Status.NEW));



        System.out.println();
        System.out.println();
        manager.makeSubtask(new Subtask(0, "Подзадача - 1", "Тест Подзадачи - 1", manager.subtaskIdGenerator(), Status.NEW));
        manager.makeSubtask(new Subtask(1, "Подзадача - 2", "Тест Подзадачи - 2", manager.subtaskIdGenerator(), Status.NEW));

        manager.getEpicAndSubtask();

    }
    //Сменить тестовую реализацию !!!

}
