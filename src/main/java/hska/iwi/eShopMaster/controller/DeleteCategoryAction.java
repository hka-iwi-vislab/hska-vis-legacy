package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.CategoryManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.List;
import java.util.Map;

public class DeleteCategoryAction extends ActionSupport {
    private static final long serialVersionUID = 1254575994729199914L;

    private double catId;
    private List<Category> categories;

    public String execute() throws Exception {

        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getTyp().equals("admin"))) {

            CategoryManager categoryManager = new CategoryManagerImpl();

            categoryManager.delCategoryById((int) catId);

            categories = categoryManager.getCategories();

            res = "success";

        }

        return res;

    }

    public void setCatId(double catId) {
        this.catId = catId;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
