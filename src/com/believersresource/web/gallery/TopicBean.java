package com.believersresource.web.gallery;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Images;
import com.believersresource.data.Topic;
import com.believersresource.data.Utils;
import com.believersresource.web.gallery.controls.ImageListController;

@ManagedBean(name="gallery_topicBean")
@RequestScoped
public class TopicBean {

	Topic topic;
	ImageListController imageListController;
	
	public String getPageTitle() { return "Illustrations for " + Utils.getTitleCase(topic.getName()); }
	public String getTopicName() { return Utils.getTitleCase(topic.getName()); }
	
	public ImageListController getImageListController() { return imageListController; }
	
	public TopicBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getParameter("url");
		topic = Topic.load(url);
		
		System.out.println(url);
		System.out.println(topic.getId());
		
		Images images = Images.loadRelated("topic", topic.getId(), 999);
		imageListController = new ImageListController(images);
		
	}
	
}
