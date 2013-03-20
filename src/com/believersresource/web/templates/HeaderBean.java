package com.believersresource.web.templates;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.AppUser;
import com.believersresource.web.FacebookHelper;

@ManagedBean(name="templates_headerBean")
@RequestScoped
public class HeaderBean {

	public boolean getShowLogin() { return !AppUser.getCurrent().IsAuthenticated; }
	public String getWelcome() { if (AppUser.getCurrent().IsAuthenticated) return "Welcome " + AppUser.getCurrent().UserData.getDisplayName() + " <a href=\"javascript:logout();\">Log out</a> | <a href=\"/cp/\">Control Panel</a>"; else return ""; }
	public String getFacebook() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String fbUrl = FacebookHelper.getFacebookLoginUrl(request.getRequestURL().toString().replace("http://localhost:8080/com.believersresource.web", "http://www.believersresource.com") );
		return " <a href=\"" + fbUrl.replace("&", "&amp;") + "\"><img src=\"/images/facebook.gif\" width=\"169\" height=\"21\" alt=\"facebook\" /></a>";
	}
	
	public HeaderBean() {
	}
}
