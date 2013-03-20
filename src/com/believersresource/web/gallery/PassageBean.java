package com.believersresource.web.gallery;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.web.AppUser;
import com.believersresource.web.gallery.controls.TopicListController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

@ManagedBean(name="gallery_passageBean")
@RequestScoped
public class PassageBean {

	BiblePassage passage;
	
	private String title;
	private RelatedTopicsController relatedTopicsController;
	private TopicListController topicListController;
	
	public String getPageTitle() { return "Illustrations for " + passage.getDisplayName(); }
	public TopicListController getTopicListController() { return topicListController; }
	public String getTitle() { return title; }
	public RelatedTopicsController getRelatedTopicsController() { return relatedTopicsController; }
	
	public PassageBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getParameter("url");
		passage = BiblePassage.loadByUrl(url);
		passage.populateVerses(AppUser.getCurrent().TranslationId);
		title = passage.getDisplayName();
		
		topicListController = new TopicListController("passage", passage.getId());
		relatedTopicsController = new RelatedTopicsController("passage", passage.getId(), 10);
		
	}

	


	
}
