package com.believersresource.web.gallery;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.Image;
import com.believersresource.web.AppUser;
import com.believersresource.web.gallery.controls.TopicListController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

@ManagedBean(name="gallery_imageBean")
@RequestScoped
public class ImageBean {

	private String title;
	private String source;
	private String output;
	
	private RelatedTopicsController relatedTopicsController;
	public String getOutput() { return output; }
	public String getSource() { return source; }
	public String getTitle() { return title; }
	public String getPageTitle() { return title; }
	public RelatedTopicsController getRelatedTopicsController() { return relatedTopicsController; }
	
	public ImageBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		
		Image image = Image.load(id);
		title = image.getTitle();
		if (!image.getSourceUrl().equals("")) source = image.getSourceDescription() + "<br/><a href=\"" + image.getSourceUrl() + "\" target=\"_blank\">Source</a><br/>";
		output = "<img src=\"http://www.believersresource.com/content/images/" + String.valueOf(image.getId()) + "." + image.getExtension() + "\" style=\"width:100%;max-width:" + String.valueOf(image.getWidth()) + "px;\" />";
		relatedTopicsController = new RelatedTopicsController("image", image.getId(), 10);
	}

	


	
}
