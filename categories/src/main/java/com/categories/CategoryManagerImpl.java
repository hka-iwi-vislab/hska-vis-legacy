package com.categories;



import com.categories.database.Category;
import com.categories.database.CategoryDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO categoryDAO;
	
	public CategoryManagerImpl() {
		categoryDAO = new CategoryDAO();
	}

	@GetMapping("")
	public List<Category> getCategories() {
		return categoryDAO.getObjectList();
	}

	@GetMapping("/id/{categoryID}")
	public Category getCategory(@PathVariable int categoryID) {
		return categoryDAO.getObjectById(categoryID);
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
		categoryDAO.deleteById(cat.getId());
	}


	@DeleteMapping("/{categoryID}")
	public void delCategoryById(@PathVariable int categoryID) {
		categoryDAO.deleteById(categoryID);
	}
}
