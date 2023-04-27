package services.product.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CategorieRepositoryTest {

    @Autowired 
    private CategoryRepository categoryRepositroy;
    
    @Test
    public void saveOne_GetOne(){
        
        categoryRepositroy.save(CategorieFixtures.Technik);
        List<Category> categories = categoryRepositroy.findAll();
        assertThat(categories).isNotEmpty();
        assertEquals(1, categories.size());

    }
}
