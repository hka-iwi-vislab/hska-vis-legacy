package services.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    
}
