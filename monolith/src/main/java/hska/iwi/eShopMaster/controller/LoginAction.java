package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginAction extends ActionSupport {

	Logger logger = LogManager.getLogger(LoginAction.class);

	//Logger logger = LoggerFactory.getLogger(LoginAction.class);

	//static Logger log = Logger.getLogger(Example.class.getName());

	/**
     *
     */
	private static final long serialVersionUID = -983183915002226000L;
	private String username = null;
	private String password = null;
	private String firstname;
	private String lastname;
	private String role;


	@Override
	public String execute() throws Exception {

		// Create a neat value object to hold the URL

		URL url = new URL("http://products-service:8081/products");

		// Open a connection(?) on the URL(??) and cast the response(???)
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// This line makes the request
		InputStream responseStream = connection.getInputStream();
		Scanner s = new Scanner(responseStream).useDelimiter("\\A");
		String apod = s.hasNext() ? s.next() : "";


		System.out.println("Fetched from products: " + apod);

		logger.error("Help, funktioniert das hier?");

		System.out.println("Help, funktioniert das hier?");

		// Return string:
		String result = "input";

		UserManager myCManager = new UserManagerImpl();

		// Get user from DB:
		User user = myCManager.getUserByUsername(getUsername());

		// Does user exist?
		if (user != null) {
			// Is the password correct?
			if (user.getPassword().equals(getPassword())) {
				// Get session to save user role and login:
				Map<String, Object> session = ActionContext.getContext().getSession();

				// Save user object in session:
				session.put("webshop_user", user);
				session.put("message", "");
				firstname= user.getFirstname();
				lastname = user.getLastname();
				role = user.getRole().getTyp();
				result = "success";
			} else {
				addActionError(getText("error.password.wrong"));
			}
		} else {
			addActionError(getText("error.username.wrong"));
		}

		return result;
	}

	@Override
	public void validate() {
		if (getUsername().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword().length() == 0) {
			addActionError(getText("error.password.required"));
		}
	}

	public String getUsername() {
		return (this.username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return (this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
