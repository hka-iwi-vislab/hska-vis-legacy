package hska.iwi.eShopMaster.model.businessLogic.manager.impl;


import com.google.gson.Gson;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryManagerImpl implements CategoryManager {
//    private final HttpDao httpDao = new HttpDao("http://reverse-proxy:5000/category");
    private final HttpDao httpDao = new HttpDao("http://category.default.svc.cluster.local:8082");

    public List<Category> getCategories() {
        try {
            return httpDao.getList("/categories", Category.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategory(int id) {
        try {
            return httpDao.get("/category" + id, Category.class);
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
            httpDao.post("/category", new Gson().toJson(new AddCategory(name)), Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategory(Category cat) {
        try {
            httpDao.delete("/category/" + cat.getId(), Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delCategoryById(int id) {
        try {
            httpDao.delete("/category/" + id, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    public class AddCategory {
        private final String name;
    }
}
