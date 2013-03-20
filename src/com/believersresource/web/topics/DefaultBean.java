package com.believersresource.web.topics;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.believersresource.data.Topics;


@ManagedBean(name="topics_defaultBean")
@RequestScoped
public class DefaultBean {

	Topics topics;
	String searchText;
	
	public String getSearchText() { return searchText; }
	public void setSearchText(String searchText) { this.searchText = searchText; }
	public Topics getTopics() { return topics; }
	
	public String search()
	{
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String url = "/search.xhtml?q=" + java.net.URLEncoder.encode(searchText);
		try { response.sendRedirect(url); } catch (Exception ex) {}
		return "";
	}
	
	public DefaultBean()
	{
		topics = Topics.loadPopular(100);
		topics.sort("name");
	}
}
