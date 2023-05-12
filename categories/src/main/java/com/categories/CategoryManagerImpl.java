package com.categories;



import com.categories.database.Category;
import com.categories.database.CategoryDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryManagerImpl implements CategoryManager{
	private CategoryDAO helper;
	
	public CategoryManagerImpl() {
		helper = new CategoryDAO();
	}

	@GetMapping()
	public List<Category> getCategories() {
		return helper.getObjectList();
	}

	@GetMapping()
	public Category getCategory(@RequestParam("categoryId") int categoryId) {
		return helper.getObjectById(categoryId);
	}


	@GetMapping
	public Category getCategoryByName(@RequestParam("categoryName") String categoryName) {
		return helper.getObjectByName(categoryName);
	}

	@PostMapping()
	public void addCategory(@RequestBody String name) {
		Category cat = new Category(name);
		helper.saveObject(cat);
	}

	@DeleteMapping
	public void delCategory(@RequestBody Category cat) {
		//https://stackoverflow.com/questions/299628/is-an-entity-body-allowed-for-an-http-delete-request



// 		Products are also deleted because of relation in Category.java 
		helper.deleteById(cat.getId());
	}


	@DeleteMapping("/{categoryID}")
	public void delCategoryById(@PathVariable int categoryID) {
		
		helper.deleteById(categoryID);
	}
}
