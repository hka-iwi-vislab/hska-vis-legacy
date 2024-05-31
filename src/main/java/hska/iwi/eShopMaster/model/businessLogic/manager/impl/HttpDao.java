package hska.iwi.eShopMaster.model.businessLogic.manager.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
public class HttpDao {
    private final String url;

    public <T> T post(String path, String requestBody) throws URISyntaxException {
        String uri = url + path;
        HttpRequest request = HttpRequest.newBuilder(new URI(uri)).POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        return send(request);
    }

    public <T> T get(String path) throws URISyntaxException {
        String uri = url + path;
        HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
        return send(request);
    }

    public <T> List<T> getList(String path) throws URISyntaxException {
        String uri = url + path;
        HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();
        return sendList(request);
    }

    private <T> T send(HttpRequest request) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            T responseDto = new ObjectMapper().readValue(response.body(), new TypeReference<>() {
            });
            System.out.println(responseDto.toString());
            return responseDto;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> List<T> sendList(HttpRequest request) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            List<T> responseDto = new ObjectMapper().readValue(response.body(), new TypeReference<>() {
            });
            System.out.println(responseDto.toString());
            return responseDto;
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
