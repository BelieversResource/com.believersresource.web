package com.believersresource.web.downloads;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.Categories;
import com.believersresource.web.controls.DownloadCategoriesController;

 
@ManagedBean(name="downloads_defaultBean")
@RequestScoped
public class DefaultBean {
	
	private DownloadCategoriesController categoriesController;
	public DownloadCategoriesController getCategoriesController() { return categoriesController; }
	public void setCategoriesController(DownloadCategoriesController categoriesController) { this.categoriesController = categoriesController; }
   
   
   public DefaultBean() 
   {
	   Categories categories = Categories.loadByType("download");
	   categories = categories.buildTree(0);
	   categoriesController = new DownloadCategoriesController(categories);
   }
     
}