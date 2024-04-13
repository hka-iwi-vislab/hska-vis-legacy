package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.dto.CreateCategoryDto;
import com.hka.verteiltesysteme.models.Category;
import com.hka.verteiltesysteme.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepo categoryRepo;

    @PostMapping("/category")
    public ResponseEntity<String> CreateCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        try {
            var category = Category.builder().name(createCategoryDto.name()).build();
            categoryRepo.save(category);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body("Category could not be created");
        }
    }

    @DeleteMapping("/category")
    public ResponseEntity<String> DeleteCategory(int id) {
        try {
            categoryRepo.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Category> GetCategory(int id) {
        try {
            return ResponseEntity.ok(categoryRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/category/all")
    public ResponseEntity<Iterable<Category>> GetCategories() {
        try {
            return ResponseEntity.ok(categoryRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
