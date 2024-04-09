package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.dto.CreateCategoryDto;
import com.hka.verteiltesysteme.models.Category;
import com.hka.verteiltesysteme.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepo categoryRepo;

    @PostMapping
    public ResponseEntity<String> CreateCategory(CreateCategoryDto createCategoryDto) {
        try {
            var category = Category.builder().name(createCategoryDto.name()).build();
            categoryRepo.save(category);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> DeleteCategory(int id) {
        try {
            categoryRepo.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping
    public ResponseEntity<Category> GetCategory(int id) {
        try {
            return ResponseEntity.ok(categoryRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Category>> GetCategories() {
        try {
            return ResponseEntity.ok(categoryRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
