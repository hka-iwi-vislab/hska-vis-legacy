package com.hka.verteiltesysteme.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hka.verteiltesysteme.config.CategoryConfiguration;
import com.hka.verteiltesysteme.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryRepo {

    private final CategoryConfiguration categoryConfiguration;

    public CategoryDto getById(int id) throws IOException, URISyntaxException {
        String uri = "http://%s:8082/category/%d".formatted(categoryConfiguration.getDomain(),id);
        System.out.println(uri);
        HttpRequest request = HttpRequest.newBuilder(new URI(uri)).GET().build();

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
