package web.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Subtask;
import model.Task;
import service.interfaces.TaskManager;
import service.manager.Manager;
import web.adapterTime.TimeAdapter;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson;

    public HttpTaskServer() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", this::handleTasks);
        taskManager = Manager.getDefault();
        gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new TimeAdapter())
                .create();
    }

    private void handleTasks(HttpExchange exchange) {
        final String pathTasks = "/tasks/";
        final String pathTask = pathTasks + "task/";
        final String pathEpic = pathTasks + "epic/";
        final String pathSubtask = pathTask + "subtask/";
        final String pathHistory = pathTasks + "history";
        try {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            int id;
            String json = null;
            switch (method) {
                case "GET":
                    switch (path) {
                        case pathTasks:
                            json = gson.toJson(taskManager.getPrioritizedTasks());

                            sendText(exchange, json, 200);
                            break;
                        case pathTask:
                            if (query != null) {
                                id = getIdFromQuery(query);
                                Task task = taskManager.getTaskById(id);
                                json = gson.toJson(task);
                            } else {
                                List<Task> tasks = taskManager.getTasks();
                                json = gson.toJson(tasks);
                            }

                            sendText(exchange, json, 200);
                            break;
                        case pathEpic:
                            if (query != null) {
                                id = getIdFromQuery(query);
                                Task task = taskManager.getTaskById(id);
                                json = gson.toJson(task);
                            } else {
                                List<Epic> epics = taskManager.getEpics();
                                json = gson.toJson(epics);
                            }

                            sendText(exchange, json, 200);
                            break;
                        case pathSubtask:
                            if (query != null) {
                                id = getIdFromQuery(query);
                                Subtask task = taskManager.getSubtaskById(id);
                                json = gson.toJson(task);
                            } else if (Pattern.matches("^/tasks/subtask/epic/?\\d+$", path)) {
                                if (getIdFromQuery(query) != -1) {
                                    List<Subtask> subtasks = taskManager.getSubtasksInEpic(getIdFromQuery(query));
                                    json = gson.toJson(subtasks);
                                }
                            } else {
                                List<Subtask> subtasks = taskManager.getSubtasks();
                                json = gson.toJson(subtasks);
                            }

                            sendText(exchange, json, 200);
                            break;
                        case pathHistory:
                            json = gson.toJson(taskManager.getHistory());

                            sendText(exchange, json, 200);
                            break;
                        default:
                            sendText(exchange, "Некорректный запрос", 405);
                            break;
                    }
                    break;
                case "POST":
                    switch (path) {
                        case pathTask:
                            json = readText(exchange);
                            Task task = gson.fromJson(json, Task.class);

                            if (task != null) {
                                if (taskManager.getTasks().stream().map(Task::getId).anyMatch(tId -> tId == task.getId())) {
                                    taskManager.update(task);
                                } else {
                                    taskManager.create(task);
                                }
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        case pathEpic:
                            json = readText(exchange);
                            Epic epic = gson.fromJson(json, Epic.class);

                            if (epic != null) {
                                if (taskManager.getEpics().stream().map(Epic::getId).anyMatch(eId -> eId == epic.getId())) {
                                    taskManager.update(epic);
                                } else {
                                    taskManager.create(epic);
                                }
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        case pathSubtask:
                            json = readText(exchange);
                            Subtask subtask = gson.fromJson(json, Subtask.class);

                            if (subtask != null) {
                                if (taskManager.getSubtasks().stream().map(Subtask::getId)
                                        .anyMatch(stId -> stId == subtask.getId())
                                ) {
                                    taskManager.update(subtask);
                                } else {
                                    taskManager.create(subtask);
                                }
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        default:
                            sendText(exchange, "Некорректный запрос", 405);
                            break;
                    }
                    break;
                case "DELETE":
                    switch (path) {
                        case pathTask:
                            if (query == null) {
                                taskManager.deleteTasks();
                            } else {
                                id = getIdFromQuery(query);
                                taskManager.removeTaskById(id);
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        case pathEpic:
                            if (query == null) {
                                taskManager.deleteTasks();
                            } else {
                                id = getIdFromQuery(query);
                                taskManager.removeEpicById(id);
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        case pathSubtask:
                            if (query == null) {
                                taskManager.deleteSubtask();
                            } else {
                                id = getIdFromQuery(query);
                                taskManager.removeSubtaskById(id);
                            }

                            exchange.sendResponseHeaders(200, 0);
                            break;
                        default:
                            sendText(exchange, "Некорректный запрос", 405);
                            break;
                    }
                    break;
                default:
                    System.out.printf("Получен неизвестный метод запроса: %s\n", method);
                    exchange.sendResponseHeaders(405, 0);
            }
        } catch (IOException e) {
            System.out.printf("Произошла ошибка на сервере. Порт: %d\n", PORT);
        } finally {
            exchange.close();
        }
    }

    private int getIdFromQuery(String query) {
        try {
            return Integer.parseInt(query.split("=")[1]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }

    public void stop() {
        System.out.println("Остановлен сервер на порту " + PORT);
        httpServer.stop(0);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text, int rCode) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(rCode, resp.length);
        h.getResponseBody().write(resp);
    }
}
