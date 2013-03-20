package com.believersresource.web;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.User;

@ManagedBean(name="loginBean")
@RequestScoped
public class LoginBean {

	public String getOutput() { return ""; }
	
	public LoginBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String action = request.getParameter("action");
		
		System.out.println(action);
		if (action.equals("fbAuth")) loginFacebook();

	}
	
	private void loginFacebook()
	{
		System.out.println("loginFacebook");
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String code = request.getParameter("code");
		
		System.out.println(code);
		
        String returnUrl = request.getParameter("returnUrl");

        System.out.println(returnUrl);
        
        String loginUrl=FacebookHelper.getLoginUrl(returnUrl);
        
        System.out.println(loginUrl);


        String accessToken = FacebookHelper.getAccessToken(code, loginUrl);
        User user = FacebookHelper.getUserData(accessToken);

        User existingUser = User.loadByFacebookId(user.getFacebookId());
        if (existingUser == null)
        {
        	user.setRegisterDate(new Date());
            loginUser(user, returnUrl);
        }
        else
        {
            loginUser(existingUser, returnUrl);
        }
        
	}
	
	private void loginUser(User user, String returnUrl)
    {
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        AppUser.login(user);
        if (returnUrl == null) returnUrl = "/";
        try { response.sendRedirect(returnUrl); } catch (Exception ex) {}
    }
	
	
}
