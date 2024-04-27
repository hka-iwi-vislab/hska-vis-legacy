package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.config.Config;
import com.hka.verteiltesysteme.dto.UpsertCategoryDto;
import com.hka.verteiltesysteme.models.Category;
import com.hka.verteiltesysteme.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepo categoryRepo;
    private final Config config;
    @PostMapping("/category")
    public ResponseEntity<String> CreateCategory(@RequestBody UpsertCategoryDto createCategoryDto) {
        try {
            var category = Category.builder().name(createCategoryDto.name()).build();
            categoryRepo.save(category);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<String> UpdateCategory(@PathVariable int id, @RequestBody UpsertCategoryDto createCategoryDto) {
        try {
            var category = categoryRepo.findById(id).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body("Category not found");
            }
            category.setName(createCategoryDto.name());
            categoryRepo.save(category);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> DeleteCategory(@PathVariable int id) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            categoryRepo.deleteById(id);

            String uri = config.products + "/category/" + id;
            System.out.println(uri);

            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> GetCategory(@PathVariable int id) {
        try {
            return ResponseEntity.ok(categoryRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> GetCategories() {
        try {
            return ResponseEntity.ok(categoryRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
