package services.product.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import services.product.model.Category;
import services.product.model.CategoryRepository;
import services.product.model.Product;
import services.product.model.ProductRepository;
import services.product.model.ProductRequest;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "")
    public List<Product> getAllCategories() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {

        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with the same name already exists.");
        }

        Long categoryId = productRequest.getCategoryId();

        if (categoryId != null) {

            Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategoryId());

            if (categoryOptional.isPresent()) {

                Category category = categoryOptional.get();
                Product savedProduct = productRepository.save(new Product(productRequest.getName(),
                        productRequest.getDetails(), productRequest.getPrice(), category));

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(savedProduct.getId()).toUri();

                return ResponseEntity.created(location).body(savedProduct);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified category does not exist.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoryId was null.");
        }
    }

    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Product> findProductByName(@PathVariable String name) {
        Optional<Product> productOptional = productRepository.findByName(name);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok().body(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        if (id != null) {
            if (productRepository.findById(id).isPresent()) {
                productRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(String.format("Product with id %d was deleted.", id));
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        String.format("Product with id %d does not exist.", id));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "categoryId was null.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(value = "minPrice", required = false, defaultValue = "0") Double minPrice,
            @RequestParam(value = "maxPrice", required = false, defaultValue = "" + Double.MAX_VALUE) Double maxPrice,
            @RequestParam(value = "details", required = false, defaultValue = "") String details) {

        List<Product> products = productRepository.findByPriceBetweenAndDetailsContainingIgnoreCase(minPrice, maxPrice, details);
        return ResponseEntity.ok(products);
    }

}
