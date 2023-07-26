package web.client;

import web.exception.LoadException;
import web.exception.RegistrationAPIException;
import web.exception.SaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final HttpClient httpclient;
    private final String URL;
    private final String API_TOKEN;

    public KVTaskClient(String URL) {
        httpclient = HttpClient.newHttpClient();
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
            HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RegistrationAPIException("Ошибка при регистрации API_TOKEN");
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
            httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new SaveException("Ощибка при загрузке данных на Сервера");
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
            HttpResponse<String> response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new LoadException("Ощибка при загрузке данных из Сервера");
        }
    }
}
