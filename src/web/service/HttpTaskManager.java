package web.service;

import com.google.gson.*;
import model.*;
import service.manager.FileBackedTasksManager;
import web.adapterTime.LocalDateTimeAdapter;
import web.client.KVTaskClient;
import web.exception.ClientLoadException;
import web.server.KVServer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private static KVTaskClient client;
    private static final Gson gson = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    private static final String[] KEYS = {"TASKS", "EPICS", "SUBTASKS", "HISTORY"};

    public HttpTaskManager(String URL) {
        super(new File("resources/data.csv"));
        client = new KVTaskClient(URL);
    }

    @Override
    protected void save() {
        List<Task> taskList = getTasks();
        List<Subtask> subtaskList = getSubtasks();
        List<Epic> epicList = getEpics();
        List<AbstractTask> history = getHistory();

        String tasksJson = gson.toJson(taskList);
        String subtasksJson = gson.toJson(subtaskList);
        String epicsJson = gson.toJson(epicList);
        String historyJson = gson.toJson(history);

        List<String> toSave = List.of(tasksJson, subtasksJson, epicsJson, historyJson);
        for (int i = 0; i < KEYS.length; i++) {
            client.put(KEYS[i], toSave.get(i));
        }
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
                throw new ClientLoadException("Ошибка при загрузке данных из клиента");
            }

            JsonArray array = element.getAsJsonArray();
            if (array.isEmpty()) {
                continue;
            }

            for (JsonElement e : array) {
                if (!e.isJsonObject()) {
                    throw new ClientLoadException("Ошибка при загрузке данных из клиента");
                }

                switch (key) {
                    case "TASKS":
                        Task task = gson.fromJson(e, Task.class);
                        manager.id = Math.max(task.getId(), manager.id);
                        manager.addAnyTask(task);
                        break;
                    case "EPICS":
                        Epic epic = gson.fromJson(e, Epic.class);
                        manager.id = Math.max(epic.getId(), manager.id);
                        manager.addAnyTask(epic);
                        break;
                    case "SUBTASKS":
                        Subtask subtask = gson.fromJson(e, Subtask.class);
                        manager.id = Math.max(subtask.getId(), manager.id);
                        manager.addAnyTask(subtask);
                        break;
                    case "HISTORY":
                        int id = e.getAsJsonObject().get("id").getAsInt();
                        AbstractTask t = manager.findAnyTask(id);
                        if (t != null) {
                            manager.historyManager.add(t);
                        }
                }
            }
        }

        return manager;
    }
    public static void main(String[] args) {
        try {
            new KVServer().start();
            HttpTaskManager httpTaskManager = new HttpTaskManager("http://localhost:8078");
            String json = Arrays.toString(new String[]{"Task{name='Название задачи 1', description=' ', id=1, status='NEW', duration='30', startTime='-999999999-01-01T00:00', endTime='-999999999-01-01T00:30'}\n"});
            int i = httpTaskManager.create(new Task("Название задачи 1", " ", 30, LocalDateTime.of(2011,1,1,1,1,1,1)));
            httpTaskManager.save();
            HttpTaskManager.loadFromServer("http://localhost:8078");
            //kvTaskClient.put(String.valueOf(TypeTasks.TASKS), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
