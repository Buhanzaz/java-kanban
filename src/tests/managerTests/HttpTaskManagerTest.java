package tests.managerTests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.interfaces.TaskManager;
import service.manager.Manager;
import tests.managerTests.AbstractTaskManagerTest;
import web.adapterTime.LocalDateTimeAdapter;
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

public class HttpTaskManagerTest {

    private KVServer kvServer;
    private HttpTaskServer taskServer;
    private static HttpClient client;
    private static HttpResponse.BodyHandler<String> bodyHandler;
    private static Gson gson;
    TaskManager manager;

    @BeforeAll
    public static void beforeAll() {
        client = HttpClient.newHttpClient();
        bodyHandler = HttpResponse.BodyHandlers.ofString();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).serializeNulls().create();
    }

    @BeforeEach
    public void beforeEach() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();

        manager = Manager.getDefault();
    }

    @AfterEach
    public void afterEach() {
        kvServer.stop();
        taskServer.stop();
    }

    @Test
    public void shouldGetRequestWithRequestMethodGET() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Task task1 = new Task("Название задачи 1", " ", 30, LocalDateTime.of(2000,1,1,1,1,1,1));
        Task task2 = new Task("Название задачи 2", " ", 30, LocalDateTime.of(2001,1,1,1,1,1,1));

        manager.create(task1);
        manager.create(task2);

        final URI url = URI.create("http://localhost:8080/tasks/task/");

        final String json1 = gson.toJson(task1);
        final String json2 = gson.toJson(task2);

        final HttpRequest request1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json1))
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url).build();
        client.send(request1, bodyHandler);

        final HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json2)).version(HttpClient.Version.HTTP_1_1).uri(url).build();
        client.send(request2, bodyHandler);

        final HttpRequest request3 = HttpRequest.newBuilder().GET().uri(url).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response = client.send(request3, bodyHandler);

        assertTrue(JsonParser.parseString(response.body()).isJsonArray());

        final JsonArray array = JsonParser.parseString(response.body()).getAsJsonArray();
        final Task jsonTask1 = gson.fromJson(array.get(0), Task.class);
        final Task jsonTask2 = gson.fromJson(array.get(1), Task.class);

        assertEquals(task1, jsonTask1);
        assertEquals(task2, jsonTask2);
    }

    @Test
    public void shouldGetRequestWithRequestMethodDELETE() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        Task task1 = new Task("Название задачи 1", " ", 30, LocalDateTime.MIN);
        Task task2 = new Task("Название задачи 2", " ", 30, LocalDateTime.MIN.plusMinutes(10));

        manager.create(task1);
        manager.create(task2);

        final URI url = URI.create("http://localhost:8080/tasks/task/");

        final String json1 = gson.toJson(task1);
        final String json2 = gson.toJson(task2);

        final HttpRequest request1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json1))
                .version(HttpClient.Version.HTTP_1_1).uri(url).build();
        client.send(request1, bodyHandler);

        final HttpRequest request2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json2)).uri(url).build();
        client.send(request2, bodyHandler);

        final HttpRequest request3 = HttpRequest.newBuilder().GET().uri(url).version(HttpClient.Version.HTTP_1_1).build();
        HttpResponse<String> response1 = client.send(request3, bodyHandler);

        assertTrue(JsonParser.parseString(response1.body()).isJsonArray());

        final JsonArray array = JsonParser.parseString(response1.body()).getAsJsonArray();
        final Task jsonTask1 = gson.fromJson(array.get(0), Task.class);
        final Task jsonTask2 = gson.fromJson(array.get(1), Task.class);

        assertEquals(task1, jsonTask1);
        assertEquals(task2, jsonTask2);

        final HttpRequest request4 = HttpRequest.newBuilder()
                .DELETE().uri(URI.create("http://localhost:8080/tasks/task/?id=1")).build();
        client.send(request4, bodyHandler);

        HttpResponse<String> response2 = client.send(request3, bodyHandler);

        assertTrue(JsonParser.parseString(response2.body()).isJsonArray());

        final JsonArray array2 = JsonParser.parseString(response2.body()).getAsJsonArray();

        final Task jsonTask = gson.fromJson(array2.get(0), Task.class);

        assertEquals(task2, jsonTask);
    }
}
