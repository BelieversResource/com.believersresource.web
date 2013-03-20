package com.believersresource.web.controls;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.Topic;
import com.believersresource.data.Topics;

 
@ManagedBean(name="popularTopicsBean")
@RequestScoped
public class PopularTopicsBean {

   private String content = "";
   public String getContent()
   {
	   return content;
   }
   public void setContent(String content)
   {
	   this.content=content;
   }
   
   
   public PopularTopicsBean() 
   {
	   Topics topics = Topics.loadPopular(10);
	   StringBuilder sb=new StringBuilder();
	   for (Topic topic : topics)
	   {
		   sb.append("<li><a href=\"/topics/" + topic.getUrl() + "\">" + topic.getName() + "</a></li>");
	   }
	   content=sb.toString();
   }
   
}