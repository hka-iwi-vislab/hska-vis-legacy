package com.hka.verteiltesysteme.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hka.verteiltesysteme.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class CategoryRepo {
    public Category getById(int id) throws IOException {
        URL url = new URL("http://categorymanagement:8082/category/%d".formatted(id));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            Category category = objectMapper.readValue(result.toString(), Category.class);
            return category;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Category> findById(int id) throws IOException {
        return Optional.ofNullable(getById(id));
    }
}
