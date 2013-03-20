package com.believersresource.web.modal;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.User;
import com.believersresource.data.Utils;
import com.believersresource.web.AppUser;

@ManagedBean(name="modal_registerBean")
@RequestScoped
public class RegisterBean {

	private String displayName;
	private String email;
	private String password;
	private String passwordVerify;
	private String errors;
	
	public String getErrors() { return errors; }
	public void setErrors(String errors) { this.errors = errors; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getPasswordVerify() { return passwordVerify; }
	public void setPasswordVerify(String passwordVerify) { this.passwordVerify = passwordVerify; }

	public boolean validate(User user, String verifyPassword)
	{
		
		ArrayList<String> errorList = new ArrayList<String>();
        
        if (user.getDisplayName().length() < 3) errorList.add("Display Name must be at least 3 characters");
        if (user.getEmail().length() < 3 || !user.getEmail().contains("@")) errorList.add("Invalid email address.");
        if (user.getPassword().length() < 6) errorList.add("Password must be at least 6 characters");
        if (!user.getPassword().equals(verifyPassword)) errorList.add("Passwords do not match");

        if (errorList.size() > 0)
        {
            errors = "<div class=\"error\">" + Utils.joinStrings("<br/>", errorList) + "</div>";
        } else errors = "";
        return errorList.size() == 0;
	}
	
	public void submit()
	{
		User user = new User();
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setPassword(password);
		if (validate(user, passwordVerify))
		{
			user.setRegisterDate(new java.sql.Date(new java.util.Date().getTime()));
			user.setPassword(com.believersresource.data.Utils.encrypt(password));
			user.save();
			AppUser.login(user);
			errors = "<script>window.parent.location.reload();</script>"; 
		}
	}
	
	

	public RegisterBean ()
	{
	}
	
}
