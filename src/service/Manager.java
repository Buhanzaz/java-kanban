package service;

import java.io.File;

public class Manager {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBacked() {
        return new FileBackedTasksManager(new File("FileBacked.csv"));
    }
}
