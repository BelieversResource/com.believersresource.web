package com.believersresource.web.controls;

import com.believersresource.data.Categories;
import com.believersresource.data.Category;

public class DownloadCategoriesController {
	public com.believersresource.data.Categories categories;
	
	private String outputText;
	
	public boolean getRendered()
	{
		return this.categories.size()>0;
	}

	public String getOutputText() {
		return outputText;
	}


	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}
	
	public DownloadCategoriesController(com.believersresource.data.Categories mCategories)
	{
		
		this.categories=mCategories;
		if (categories.size()==0) {
		   //this.visible=false;
		} else {
		   StringBuilder sb=new StringBuilder();
		   appendCategories(sb, categories);
		   outputText = sb.toString();
		}
	}


	

   private void appendCategories(StringBuilder sb, Categories categories)
   {
	   boolean alt=false;
	   for (Category category : categories)
	   {
		   if (alt) sb.append("<li class=\"grey\">"); else sb.append("<li>");
           sb.append("<a href=\"/downloads/categories/" + category.getUrl() + "\">" + category.getName() + "</a>");
           sb.append(" - " + category.getDescription());
           if (category.getChildCategories() != null) appendChildCategories(sb, category.getChildCategories());
           sb.append("</li>");
           alt = !alt;
	   }
   }
	   
   private void appendChildCategories(StringBuilder sb, Categories categories)
   {
       sb.append("<ul class=\"horizontal\">");
       for (Category category : categories)
       {
           sb.append("<li><a href=\"/downloads/categories/" + category.getUrl() + "\">" + category.getName() + "</a></li>");
       }
       sb.append("</ul>");
   }

}
