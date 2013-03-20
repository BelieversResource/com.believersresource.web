package com.believersresource.web.bible;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="bible_defaultBean")
@RequestScoped
public class DefaultBean {

	String searchText;
	
	public String getSearchText() { return searchText; }
	public void setSearchText(String searchText) { this.searchText = searchText; }
	
	public String search()
	{
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String url = "/search.xhtml?q=" + java.net.URLEncoder.encode(searchText);
		try { response.sendRedirect(url); } catch (Exception ex) {}
		return "";
	}
	
	public DefaultBean (){
		
	}
}
