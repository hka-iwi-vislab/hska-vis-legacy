package services.product.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import services.product.model.ProductRepository;
import services.product.model.CategoryRepository;
import services.product.model.Product;
import services.product.model.Category;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "")
    public List<Product> getAllCategories(){
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @RequestParam Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

            return ResponseEntity.created(location).body(savedProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
