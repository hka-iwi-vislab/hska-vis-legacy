package com.hka.verteiltesysteme.controller;

import com.hka.verteiltesysteme.dto.CategoryDto;
import com.hka.verteiltesysteme.dto.UpsertProductDto;
import com.hka.verteiltesysteme.models.Product;
import com.hka.verteiltesysteme.repositories.CategoryRepo;
import com.hka.verteiltesysteme.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @PostMapping("/product")
    public ResponseEntity<String> CreateProduct(@RequestBody UpsertProductDto createProductDto) {
        try {
            CategoryDto category = categoryRepo.getById(createProductDto.categoryId());
            var product = Product.builder().name(createProductDto.name())
                    .category(category.getId())
                    .price(createProductDto.price())
                    .details(createProductDto.description()).build();
            productRepo.save(product);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Product could not be created");
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> UpdateProduct(@PathVariable int id, @RequestBody UpsertProductDto createProductDto) {
        try {
            var product = productRepo.findById(id).orElse(null);
            if (product == null) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            product.setName(createProductDto.name());
            product.setPrice(createProductDto.price());
            product.setDetails(createProductDto.description());
            var category = categoryRepo.findById(createProductDto.categoryId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().body("Category not found");
            }
            product.setCategory(category.getId());
            productRepo.save(product);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Product could not be updated");
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable int id) {
        try {
            productRepo.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Category could not be deleted");
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> GetProduct(@PathVariable int id) {
        try {
            return ResponseEntity.ok(productRepo.findById(id).orElse(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> GetProducts() {
        try {
            return ResponseEntity.ok(productRepo.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
