package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import lombok.RequiredArgsConstructor;

import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
public class CategoryManagerImpl implements CategoryManager {
    private final HttpDao httpDao = new HttpDao("http://category:8082");

    public List<Category> getCategories() {
        try {
            return httpDao.getList("/categories");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategory(int id) {
        try {
            return httpDao.get("/category" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategoryByName(String name) {
        var categories = getCategories();
        return categories.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

    public void addCategory(String name) {
        try {
            httpDao.post("/category", new ObjectMapper().writeValueAsString(new AddCategory(name)));
        } catch (URISyntaxException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategory(Category cat) {
        try {
            httpDao.delete("/category/" + cat.getId());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategoryById(int id) {
        try {
            httpDao.delete("/category/" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public record AddCategory(String name) {
    }
}
