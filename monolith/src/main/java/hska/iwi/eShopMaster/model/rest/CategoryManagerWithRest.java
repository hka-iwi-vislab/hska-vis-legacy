package hska.iwi.eShopMaster.model.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryManagerWithRest implements CategoryManager {
    private RestHelper restHelper;
    private Gson gson;

    public CategoryManagerWithRest() {
        restHelper = new RestHelper();
        gson = new Gson();
    }

    //TODO Works!
    public List<Category> getCategories() {
        String url = RestHelper.categoryServiceUrl;

        try
        {
            String categoriesString = restHelper.doGetRequest(url);
            Type categoryListType = new TypeToken<ArrayList<Category>>(){}.getType();
            List<Category> list = gson.fromJson(categoriesString, categoryListType);

            return list;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    //TODO Works!
    public Category getCategory(int id) {

        String url = RestHelper.categoryServiceUrl + "/id/" + id ;
        try
        {
            String categoryString = restHelper.doGetRequest(url);
            Category category = gson.fromJson(categoryString, Category.class);
            return category;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    //TODO Never Used
    public Category getCategoryByName(String name) {
        String url = RestHelper.categoryServiceUrl + "/name/" + name;
        try
        {
            String categoryString = restHelper.doGetRequest(url);
            Category category = gson.fromJson(categoryString, Category.class);
            return category;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    //TODO Works
    public void addCategory(String name) {
        String jsonName = gson.toJson(name);

        try
        {
            restHelper.doPostRequest(RestHelper.categoryServiceUrl, jsonName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //TODO Never Used
    public void delCategory(Category cat) {
        String jsonCategory = gson.toJson(cat);

        try
        {
            restHelper.doDeleteRequest(RestHelper.categoryServiceUrl, jsonCategory);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //TODO Works
    public void delCategoryById(int id) {
        String url = RestHelper.categoryServiceUrl + "/" + id;

        try
        {
            restHelper.doDeleteRequest(url, "");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //TODO Works
    public boolean categoryExists(int categoryID) {
        String url = RestHelper.categoryServiceUrl + "/exists/" + categoryID;

        try
        {
            String categoryExistString = restHelper.doGetRequest(url);
            return gson.fromJson(categoryExistString, boolean.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }
}
