package com.believersresource.web.controls;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.Download;
import com.believersresource.data.Downloads;

 
@ManagedBean(name="popularDownloadsBean")
@RequestScoped
public class PopularDownloadsBean {

   private String content = "";
   public String getContent()
   {
	   return content;
   }
   public void setContent(String content)
   {
	   this.content=content;
   }
   
   
   
   public PopularDownloadsBean() 
   {
	   
	   Downloads downloads = Downloads.loadPopular(10);
	   //Downloads downloads = Downloads.loadByCategoryId(6); //Downloads.loadPopular(10);
	   StringBuilder sb=new StringBuilder();
	   for (Download download : downloads)
	   {
		   sb.append("<li><a href=\"/downloads/" + download.getUrl() + "\">" + download.getTitle() + "</a></li>");
	   }
	   
	   //int a=1;
	   //Object o=(Object)a;
	   //content = o.getClass().getName();
	   
	   content=sb.toString();
   }
   
}