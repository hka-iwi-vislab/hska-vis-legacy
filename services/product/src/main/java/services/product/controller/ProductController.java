package services.product.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import services.product.model.ProductRepository;
import services.product.model.ProductRequest;
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
    public ResponseEntity createProduct(@RequestBody ProductRequest productRequest) {

        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Long categoryId = productRequest.getCategoryId();

        if (categoryId != null) {

            Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategoryId());

            if (categoryOptional.isPresent()) {

                Category category = categoryOptional.get();
                Product savedProduct = productRepository.save(new Product(productRequest.getName(), productRequest.getDetails(), productRequest.getPrice(), category));

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedProduct.getId()).toUri();

                return ResponseEntity.created(location).body(savedProduct);
            } else {
                return ResponseEntity.badRequest().body("The specified category does not exist");
            }
        } else {
            return ResponseEntity.badRequest().body("categoryId was null");
        }
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (id != null) {
            if (productRepository.findById(id).isPresent()) {
                productRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(String.format("Product with id %d deleted successfully", id));
            } else {
                return ResponseEntity.badRequest().body(String.format("Product with id %d does not exist.", id));
            }
        } else {
            return ResponseEntity.badRequest().body("id was null");
        }
    }
}
