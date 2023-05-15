package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.model.rest.CategoryManagerWithRest;
import hska.iwi.eShopMaster.model.rest.ProductManagerWithRest;

public class AddProductAction extends ActionSupport {

	private static final long serialVersionUID = 39979991339088L;

	private String name = null;
	private String price = null;
	private int categoryId = 0;
	private String details = null;
	private List<Category> categories;

	public String execute() throws Exception {
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRole().getTyp().equals("admin"))) {

			//Schritt 1: CategoryManage gucken ob diese category existiert
			//Schritt 2: Product mit dieser CateoryID anlegen


			CategoryManagerWithRest categoryManagerWithRest = new CategoryManagerWithRest();

			if(categoryManagerWithRest.categoryExists(categoryId))
			{
				ProductManagerWithRest productManagerWithRest = new ProductManagerWithRest();
				int productId = productManagerWithRest.addProduct(name, Double.parseDouble(price), categoryId,
						details);

				//ProductManager productManager = new ProductManagerImpl();
				//int productId = productManager.addProduct(name, Double.parseDouble(price), categoryId,
				//		details);

				if (productId > 0) {
					result = "success";
				}
			}
		}

		return result;
	}

	@Override
	public void validate() {
		CategoryManagerWithRest categoryManagerWithRest = new CategoryManagerWithRest();
		this.setCategories(categoryManagerWithRest.getCategories());

		/*
		CategoryManager categoryManager = new CategoryManagerImpl();
		this.setCategories(categoryManager.getCategories());

		*/
		//TODO hier muss ein REST call an den Categories microservice rein!

		// Validate name:

		if (getName() == null || getName().length() == 0) {
			addActionError(getText("error.product.name.required"));
		}

		// Validate price:

		if (String.valueOf(getPrice()).length() > 0) {
			if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
					|| Double.parseDouble(getPrice()) < 0.0) {
				addActionError(getText("error.product.price.regex"));
			}
		} else {
			addActionError(getText("error.product.price.required"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
