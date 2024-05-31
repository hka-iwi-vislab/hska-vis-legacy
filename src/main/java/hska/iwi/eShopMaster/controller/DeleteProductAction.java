package hska.iwi.eShopMaster.controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

public class DeleteProductAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3666796923937616729L;

    private double id;

    public String execute() throws Exception {

        String res = "input";

        Map<String, Object> session = ActionContext.getContext().getSession();
        User user = (User) session.get("webshop_user");

        if (user != null && (user.getRole().getTyp().equals("admin"))) {

            new ProductManagerImpl().deleteProductById((int) id);
            {
                res = "success";
            }
        }

        return res;

    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

}
