import model.Task;
import service.Manager;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();


        manager.makeTask(new Task("Задача - 1", "Тест - 1",
                manager.identifier(), manager.NEW_PROGRESS));
        manager.makeTask(new Task("Задача - 2", "Тест - 2",
                manager.identifier(), manager.NEW_PROGRESS));

        manager.getAllTask();


        manager.updateTask(new Task("Задача - 1", "Тест - 3",
                manager.identifier(), manager.NEW_PROGRESS));

        manager.getAllTask();
        manager.getById(1);
    }
    //Сменить тестовую реализацию !!!

}
