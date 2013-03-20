package com.believersresource.web.cp;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.User;
import com.believersresource.data.Utils;
import com.believersresource.web.AppUser;


@ManagedBean(name="cp_defaultBean")
@RequestScoped
public class DefaultBean {

	private String displayName;
	private String emailAddress;
	private String password;
	private String verifyPassword;
	private boolean showTraditional;
	private String output;
	
	public boolean getShowTraditional() { return showTraditional; }
	
	public String getOutput() { return output; }
	public String getDisplayName() { return displayName; }
	public void setDisplayName(String displayName) { this.displayName = displayName; }
	public String getEmailAddress() { return emailAddress; }
	public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getVerifyPassword() { return verifyPassword; }
	public void setVerifyPassword(String verifyPassword) { this.verifyPassword = verifyPassword; }

	public DefaultBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (AppUser.getCurrent().UserData.getFacebookIdIsNull()) showTraditional=true;

		if (!FacesContext.getCurrentInstance().isPostback()) { 
			displayName = AppUser.getCurrent().UserData.getDisplayName();
			if (showTraditional) emailAddress = AppUser.getCurrent().UserData.getEmail();
			if (request.getParameter("saved")!=null) output = "<div class=\"success\">Changes saved.</div>";
		}
	}
	
	private boolean validate()
    {
        ArrayList<String> errors = new ArrayList<String>();

        if (displayName.length() < 3) errors.add("Display Name must be at least 3 characters");

        User user = User.loadByDisplayName(displayName);
        if (user != null && user.getId() != AppUser.getCurrent().UserData.getId()) errors.add("There is already a user with this name.");


        if (showTraditional)
        {
            if (emailAddress.length() < 3 || !emailAddress.contains("@")) errors.add("Invalid email address.");
            user = User.load(emailAddress);
            if (user != null && user.getId() != AppUser.getCurrent().UserData.getId()) errors.add("There is another a user with this email address.");

            if (password != "")
            {
                if (password.length() < 6) errors.add("Password must be at least 6 characters");
                if (password != verifyPassword) errors.add("Passwords do not match");
            }
        }

        if (errors.size() > 0) output = "<div class=\"error\">" + Utils.joinStrings("<br/>", errors) + "</div>";
        return errors.size() == 0;
    }
	
	
	public void submit()
	{
		//comment = Comment.load(commentId);
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		
		User user = AppUser.getCurrent().UserData;

        if (validate())
        {
            user.setDisplayName(displayName);
            if (showTraditional)
            {
                if (password != "") user.setPassword(password);
                user.setEmail(emailAddress);
            }
            user.save();
            try { response.sendRedirect("/cp/?saved=1"); } catch (Exception ex) {}
        }
		
	}
	
}
