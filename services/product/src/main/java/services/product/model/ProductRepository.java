package services.product.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByDetails(String details);
    Optional<Product> findByPrice(double price);

    Optional<List<Product>> findAllByCategory(Category category);
    Optional<List<Product>> findAllByName(String name);

    Long deleteByCategory(Category category);

    List<Product> findByPriceBetweenAndDetailsContainingIgnoreCase(
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("details") String details
    );
}
