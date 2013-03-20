package com.believersresource.web.downloads;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Categories;
import com.believersresource.data.Category;
import com.believersresource.web.controls.DownloadCategoriesController;
import com.believersresource.web.downloads.controls.BreadCrumbController;
import com.believersresource.web.downloads.controls.DownloadListController;

@ManagedBean(name="downloads_categoryBean")
@RequestScoped
public class CategoryBean {

	private Category category;
	private BreadCrumbController breadCrumbController;
	private DownloadCategoriesController categoriesController;
	private DownloadListController downloadListController;
	
	public String getPageTitle() {return category.getName() + " Downloads";}
	public Category getCategory() {return category;}
	public BreadCrumbController getBreadCrumbController() {return breadCrumbController;}
	public DownloadCategoriesController getCategoriesController() { return categoriesController; }
	public DownloadListController getDownloadListController() { return downloadListController; }
	
	
	public CategoryBean() 
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getParameter("url");
		category = Category.load(url);
		
		Categories categories = Categories.loadByType("download");
		
		if (category.getParentId()>0)
		{
			Category parent = categories.getById(category.getParentId());
        	Categories breadCrumb = Categories.getBreadCrumb(categories, parent);
        	breadCrumbController = new BreadCrumbController(breadCrumb);
		} else {
			breadCrumbController = new BreadCrumbController(null);
		}
        
        Categories childCategories = categories.buildTree(category.getId());
        categoriesController = new DownloadCategoriesController(childCategories);
        downloadListController = new DownloadListController(category.getId());
	}
}
