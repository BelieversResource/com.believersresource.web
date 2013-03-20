package com.believersresource.web.downloads.controls;

import com.believersresource.data.Categories;

public class BreadCrumbController {

	Categories categories;
	
	public String getOutput()
	{
		StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"/downloads/\">Downloads</a>");
        if (categories!=null)
        {
	        for (int i = categories.size() -1 ; i >= 0 ; i--)
	        {
	            sb.append(" &gt; ");
	            sb.append("<a href=\"/downloads/categories/" + categories.get(i).getUrl() + "\">" + categories.get(i).getName() + "</a>");
	        }
        }
        return sb.toString();
	}
	
	public BreadCrumbController(Categories categories)
	{
		this.categories = categories;
	}
}
