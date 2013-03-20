package com.believersresource.web.pages;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Page;

 
@ManagedBean(name="pages_pageBean")
@RequestScoped
public class PageBean {


	Page page;

	
	public String getPageTitle() { return page.getTitle(); }
	public String getHeader() { return page.getHeader(); }
	public String getBody() { return page.getBody(); }
	
	public PageBean() 
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getParameter("url");
		page = Page.load(url);
		
        
	}
     
}