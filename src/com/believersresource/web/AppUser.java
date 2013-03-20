package com.believersresource.web;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.believersresource.data.User;

public class AppUser {
	public com.believersresource.data.User UserData;
    public boolean IsAuthenticated = false;
    public int IpAddress = 0;
    public int TranslationId = 1;
    public int MinVotes = 0;
    public boolean IsAdmin = false;
    
    public static AppUser getCurrent()
    {
    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	HttpSession session = request.getSession();
    	
    	AppUser appUser = (AppUser) session.getAttribute("AppUser");
    	if (appUser==null)
    	{
    		appUser = new AppUser();
            appUser.IpAddress = com.believersresource.data.Utils.getIntForIP(request.getRemoteAddr());
            setCurrent(appUser);
    	}
    	return appUser;
    }
    
    public static void setCurrent(AppUser appUser)
    {
    	HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
    	session.setAttribute("AppUser", appUser);
	}
    
    public static void login(User user)
    {
        user.setLastLogin(new java.sql.Date(new java.util.Date().getTime()));
        user.save();
        AppUser.getCurrent().UserData = user;
        AppUser.getCurrent().IsAuthenticated = true;
        if (user.getId() == 1 || user.getId() == 189) AppUser.getCurrent().IsAdmin = true;
    }

    public static void logout()
    {
        AppUser.getCurrent().IsAuthenticated = false;
        AppUser.getCurrent().UserData = null;
    }

}
