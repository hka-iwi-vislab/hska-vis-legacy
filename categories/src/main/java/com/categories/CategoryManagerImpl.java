package com.categories;

import com.categories.database.Category;
import com.categories.database.CategoryDAO;
import com.categories.rest.RestHelper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO categoryDAO;
	private RestHelper restHelper;

	public CategoryManagerImpl() {
		categoryDAO = new CategoryDAO();
		restHelper = new RestHelper();
	}

	@GetMapping("")
	public List<Category> getCategories() {
		return categoryDAO.getObjectList();
	}

	@GetMapping("/id/{categoryID}")
	public Category getCategory(@PathVariable int categoryID) {
		return categoryDAO.getObjectById(categoryID);
	}

	@GetMapping("/exists/{categoryID}")
	public boolean categoryExists(@PathVariable int categoryID){
		Category category = categoryDAO.getObjectById(categoryID);
		if(category != null)
		{
			System.out.println(category.getId());
		}

		return category != null;
	}

	@GetMapping("/name/{categoryName}")
	public Category getCategoryByName(@PathVariable String categoryName) {
		return categoryDAO.getObjectByName(categoryName);
	}

	@PostMapping("")
	public void addCategory(@RequestBody String name) {
		Category cat = new Category(name);
		categoryDAO.saveObject(cat);
	}

	@DeleteMapping("")
	public void delCategory(@RequestBody Category cat) {
		//https://stackoverflow.com/questions/299628/is-an-entity-body-allowed-for-an-http-delete-request

		String url = RestHelper.productServiceUrl + "/by-category/" + cat.getId();
		try
		{
			restHelper.doDeleteRequest(url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


		categoryDAO.deleteById(cat.getId());
	}


	@DeleteMapping("/{categoryID}")
	public void delCategoryById(@PathVariable int categoryID) {

		String url = RestHelper.productServiceUrl + "/by-category/" + categoryID;
		try
		{
			restHelper.doDeleteRequest(url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		categoryDAO.deleteById(categoryID);
	}
}
