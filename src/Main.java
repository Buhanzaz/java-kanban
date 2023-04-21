import service.Manager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        int id;
        int exit = 0;
        int in;
        do {
            menu();
            in = scanner.nextInt();
            switch (in) {
                case 1:
                    System.out.println("Имя");
                    name = scanner.next();
                    System.out.println("описание");
                    description = scanner.next();
                    System.out.println("id");
                    id = scanner.nextInt();

                    manager.makeTask(name,description,id);
                    break;
                case 2:
                    System.out.println("Все задачи");
                    manager.getAllTask();
            }
            System.out.println();
        } while (exit != in);



    }
    public static void menu() {
        System.out.println("1 - создать задачу");
        System.out.println("2 - Вывести все задачи");
    }

}
