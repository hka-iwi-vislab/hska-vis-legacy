package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryManagerImpl implements CategoryManager {
    private final HttpDao httpDao = new HttpDao("http://reverse-proxy:5000/category");

    public List<Category> getCategories() {
        try {
            return httpDao.getList("/categories");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategory(int id) {
        try {
            return httpDao.get("/category" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategoryByName(String name) {
        List<Category> categories = getCategories();
        return categories.stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    }

    public void addCategory(String name) {
        try {
            httpDao.post("/category", new Gson().toJson(new AddCategory(name)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategory(Category cat) {
        try {
            httpDao.delete("/category/" + cat.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategoryById(int id) {
        try {
            httpDao.delete("/category/" + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public class AddCategory {
        private final String name;
    }
}
