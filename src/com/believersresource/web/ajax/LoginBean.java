package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.User;
import com.believersresource.web.AppUser;

@ManagedBean(name="ajax_loginBean")
@RequestScoped
public class LoginBean {
	
	User user;
	public String getOutput() { if (user==null) return "false"; else return "true"; }

	public LoginBean ()
	{
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean logout = request.getParameter("logout") != null;
		
		if (logout)
		{
			AppUser.logout();
		} else {
			user = User.load(email, com.believersresource.data.Utils.encrypt(password));
			System.out.println(user);
			if (user!=null) AppUser.login(user);
		}
		
		//verses = BibleVerses.loadRange(startVerseId, endVerseId);
		
	}
}
