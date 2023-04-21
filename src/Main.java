import service.Manager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        int exit = 0;
        int in;//Переименовать
        int id;
        do {
            menu();
            in = scanner.nextInt();
            switch (in) {
                case 1:
                    System.out.println("Имя");
                    name = scanner.next();

                    System.out.println("Описание");
                    description = scanner.next();

                    manager.makeTask(name,description);
                    break;
                case 2:
                    System.out.println("Все задачи");
                    manager.getAllTask();
                    break;
                case 3:
                    System.out.println("Удалить все задачи");
                    manager.deleteAllTask();
                case 4:
                    System.out.println("Введите id");
                    id = scanner.nextInt();
                    manager.removeById(id);
            }
            System.out.println();
        } while (exit != in);
    }
    //Сменить тестовую реализацию !!!
    public static void menu() {
        System.out.println("1 - Создать задачу");
        System.out.println("2 - Вывести все задачи");
        System.out.println("3 - Удалить все задачи");
        System.out.println("4 - Удалить задачу по id");
    }
}
