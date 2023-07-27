package web.service;

import com.google.gson.*;
import model.*;
import service.manager.FileBackedTasksManager;
import web.adapter.time.TimeAdapter;
import web.client.KVTaskClient;
import web.exception.LoadException;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private static KVTaskClient client;
    private static final Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
            .create();
    private static final String[] KEYS = {"TASKS", "EPICS", "SUBTASKS", "HISTORY"};

    public HttpTaskManager(String URL) {
        super(new File("resources/data.csv"));
        client = new KVTaskClient(URL);
    }

    @Override
    protected void save() {
        client.saveToServer(KEYS[0], gson.toJson(getTasks()));
        client.saveToServer(KEYS[1], gson.toJson(getEpics()));
        client.saveToServer(KEYS[2], gson.toJson(getSubtasks()));
        client.saveToServer(KEYS[3], gson.toJson(getHistory()));
    }

    public static HttpTaskManager loadFromServer(String URL) {
        HttpTaskManager manager = new HttpTaskManager(URL);

        for (String key : KEYS) {
            String json = client.load(key);
            if (json == null || json.equals("")) {
                continue;
            }

            JsonElement element = JsonParser.parseString(json);
            if (!element.isJsonArray()) {
                throw new LoadException("Ошибка при загрузке данных из клиента");
            }

            JsonArray array = element.getAsJsonArray();
            if (array.isEmpty()) {
                continue;
            }

            for (JsonElement e : array) {
                if (!e.isJsonObject()) {
                    throw new LoadException("Ошибка при загрузке данных из клиента");
                }

                switch (key) {
                    case "TASKS":
                        Task task = gson.fromJson(e, Task.class);
                        manager.id = Math.max(task.getId(), manager.id);
                        manager.saveTask(task);
                        break;
                    case "EPICS":
                        Epic epic = gson.fromJson(e, Epic.class);
                        manager.id = Math.max(epic.getId(), manager.id);
                        manager.saveTask(epic);
                        break;
                    case "SUBTASKS":
                        Subtask subtask = gson.fromJson(e, Subtask.class);
                        manager.id = Math.max(subtask.getId(), manager.id);
                        manager.saveTask(subtask);
                        break;
                    case "HISTORY":
                        int id = e.getAsJsonObject().get("id").getAsInt();
                        AbstractTask t = manager.searchTaskById(id);
                        if (t != null) {
                            manager.historyManager.add(t);
                        }
                    default:
                        throw new LoadException("Ошибка, неправильный тип задач при загрузке");
                }
            }
        }
        return manager;
    }
}