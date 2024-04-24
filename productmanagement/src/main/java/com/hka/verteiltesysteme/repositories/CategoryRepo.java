package com.hka.verteiltesysteme.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hka.verteiltesysteme.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class CategoryRepo {
    public CategoryDto getById(int id) throws IOException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder(new URI("http://categorymanagement:8082/category/%d".formatted(id))).GET().build();

        CategoryDto categoryDto = null;

        try (HttpClient client = HttpClient.newHttpClient()){
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            categoryDto = new ObjectMapper().readValue(response.body(), CategoryDto.class);
            System.out.println(categoryDto.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return categoryDto;
    }

    public Optional<CategoryDto> findById(int id) throws IOException, URISyntaxException {
        return Optional.ofNullable(getById(id));
    }
}
