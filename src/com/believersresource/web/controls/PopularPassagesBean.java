package com.believersresource.web.controls;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.BiblePassages;


 
@ManagedBean(name="popularPassagesBean")
@RequestScoped
public class PopularPassagesBean {

   private String content = "";
   public String getContent()
   {
	   return content;
   }
   public void setContent(String content)
   {
	   this.content=content;
   }
   
   
   public PopularPassagesBean() 
   {
	   boolean alt = false;
	   BiblePassages passages = BiblePassages.loadPopular(15);
	   passages.populateVerses(1);
	   
	   //content = String.valueOf(passages.get(0).getVerses().get(0).getBody());
	   
	   StringBuilder sb=new StringBuilder();
	   for (BiblePassage passage : passages)
	   {
		   sb.append("<li");
		   if (alt) sb.append(" class=\"grey\"");
		   sb.append("><div class=\"description\"><p><a href=\"/bible/" + passage.getUrl() + "\">" + passage.getDisplayName() + "</a> - " + passage.getBody() + "</p></div></li>");
		   alt=!alt;
	   }
	   content=sb.toString();
	   
   }
   
}