package com.believersresource.web.topics;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Topic;
import com.believersresource.data.Utils;
import com.believersresource.web.bible.controls.RelatedPassagesController;
import com.believersresource.web.controls.CommentsController;
import com.believersresource.web.controls.RelatedImagesController;
import com.believersresource.web.forum.controls.RelatedDiscussionsController;

@ManagedBean(name="topics_topicBean")
@RequestScoped
public class TopicBean {

	Topic topic;
	RelatedPassagesController relatedPassagesController;
	CommentsController commentsController;
	RelatedImagesController relatedImagesController;
	RelatedDiscussionsController relatedDiscussionsController;
	
	public String getPageTitle() { return "Biblical Resources about " + Utils.getTitleCase(topic.getName()); }
	public String getTopicName() { return Utils.getTitleCase(topic.getName()); }
	public Topic getTopic() { return topic; }
	public RelatedPassagesController getRelatedPassagesController() { return relatedPassagesController; }
	public CommentsController getCommentsController() {return commentsController;}
	public RelatedImagesController getRelatedImagesController() { return relatedImagesController; }
	public RelatedDiscussionsController getRelatedDiscussionsController() { return relatedDiscussionsController; }
	
	public TopicBean()
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getParameter("url");
		topic = Topic.load(url);

		relatedPassagesController = new RelatedPassagesController("topic", topic.getId(), 5);
		commentsController = new CommentsController("topic", topic.getId());
		relatedImagesController = new RelatedImagesController("topic", topic.getId(), topic.getUrl());
		relatedDiscussionsController = new RelatedDiscussionsController("topic", topic.getId(), 5);
	}
}
