package service.manager;

import service.interfaces.HistoryManager;
import web.service.HttpTaskManager;

public class Manager {
    public static HttpTaskManager getDefault() {
        return HttpTaskManager.loadFromServer("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
