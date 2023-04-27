package services.product.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByCategory(Category category);
    Optional<Product> findByDetails(String details);
    Optional<Product> findByPrice(double price);
}
