package tests.managerTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import model.Epic;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.manager.Manager;
import web.adapter.time.TimeAdapter;
import web.server.HttpTaskServer;
import web.server.KVServer;
import web.service.HttpTaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HttpTaskManagerTest extends AbstractTaskManagerTest<HttpTaskManager> {
    final URI url = URI.create("http://localhost:8080/tasks/task/");
    private KVServer kvServer;
    private HttpTaskServer taskServer;
    private static HttpClient client;
    private static HttpResponse.BodyHandler<String> bodyHandler;
    private static Gson gson;
    private HttpResponse<String> response;

    @BeforeAll
    public static void beforeAll() {
        client = HttpClient.newHttpClient();
        bodyHandler = HttpResponse.BodyHandlers.ofString();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new TimeAdapter()).serializeNulls().create();
    }

    @BeforeEach
    public void beforeEach() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();

        manager = Manager.getDefault();
        task = new Task("Test Task", "Test Task", 30, LocalDateTime.of(2013, 12, 12, 12, 12, 12, 12));
        epic = new Epic("Test  Epic", "Test  Epic");
        taskId = manager.create(task);
        epicId = manager.create(epic);
        savedEpic = manager.getEpicById(epicId);
        subtask = new Subtask(epicId, "Test Subtask", "Test Subtask", 30, LocalDateTime.of(2023, 12, 13, 12, 12, 12, 12));
        subtaskId = manager.create(subtask);
        savedSubtask = manager.getSubtaskById(subtaskId);
        savedTask = manager.getTaskById(taskId);

        String json = gson.toJson(task);

        HttpRequest requestPost = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url)
                .build();
        client.send(requestPost, bodyHandler);

        HttpRequest requestGet = HttpRequest.newBuilder()
                .GET()
                .uri(url).
                version(HttpClient.Version.HTTP_1_1)
                .build();
        response = client.send(requestGet, bodyHandler);
    }

    @AfterEach
    public void afterEach() {
        kvServer.stop();
        taskServer.stop();
    }

    @Test
    public void shouldGetRequestWithRequestMethodGET() {
        assertTrue(JsonParser.parseString(response.body()).isJsonArray());

        final JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
        final Task jsonTask = gson.fromJson(array.get(0), Task.class);

        assertEquals(task, jsonTask);
    }

    @Test
    public void shouldGetRequestWithRequestMethodDELETE() throws IOException, InterruptedException {
        final HttpRequest requestGet = HttpRequest.newBuilder().GET().uri(url).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(requestGet, bodyHandler);

        assertTrue(JsonParser.parseString(response.body()).isJsonArray());

        final JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
        final Task jsonTask = gson.fromJson(array.get(0), Task.class);

        assertEquals(task, jsonTask);


        final HttpRequest requestDelete = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/tasks/task/?id=1"))
                .build();
        client.send(requestDelete, bodyHandler);

        HttpResponse<String> response2 = client.send(requestGet, bodyHandler);

        assertTrue(JsonParser.parseString(response2.body()).isJsonArray());

        final JsonArray emptyArray = JsonParser.parseString(response2.body()).getAsJsonArray();
        assertTrue(emptyArray.isEmpty());
    }
}
