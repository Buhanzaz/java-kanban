package web.client;

import model.TypeTasks;
import web.exception.ClientLoadException;
import web.exception.ClientRegistrationException;
import web.exception.ClientSaveException;
import web.server.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class KVTaskClient {

    private final HttpClient client;
    private final String URL;
    private final String API_TOKEN;

    public KVTaskClient(String URL) {
        client = HttpClient.newHttpClient();
        this.URL = URL;
        API_TOKEN = registerApiToken(URL);
    }

    private String registerApiToken(String URL) {
        try {
            URI url = URI.create(String.format("%s/register", URL));
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ClientRegistrationException("Произошла ошибка при регистрации клиента", e);
        }
    }

    public void put(String key, String json) {
        try {
            URI url = URI.create(String.format("%s/save/%s/?API_TOKEN=%s", URL, key, API_TOKEN));
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(url)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new ClientSaveException("Произошла ошибка при загрузке данных на сервер", e);
        }
    }

    public String load(String key) {
        try {
            URI url = URI.create(String.format("%s/load/%s/?API_TOKEN=%s", URL, key, API_TOKEN));
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(url)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ClientLoadException("Произошла ошибка при получении данных с сервера", e);
        }
    }

    public static void main(String[] args) {
        try {
            new KVServer().start();
            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8078");
            String json = Arrays.toString(new String[]{"Task{name='Название задачи 1', description=' ', id=1, status='NEW', duration='30', startTime='-999999999-01-01T00:00', endTime='-999999999-01-01T00:30'}\n"});
            kvTaskClient.put(String.valueOf(TypeTasks.TASKS), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
