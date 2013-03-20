package com.believersresource.web.bible;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.BibleVerse;
import com.believersresource.data.Topics;
import com.believersresource.web.AppUser;
import com.believersresource.web.bible.controls.ContextController;
import com.believersresource.web.bible.controls.RelatedPassagesController;
import com.believersresource.web.controls.CommentsController;
import com.believersresource.web.controls.RelatedImagesController;
import com.believersresource.web.forum.controls.RelatedDiscussionsController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

@ManagedBean(name="bible_passageBean")
@RequestScoped
public class PassageBean 
{
	BiblePassage passage;
	RelatedPassagesController relatedPassagesController;
	CommentsController commentsController;
	RelatedTopicsController relatedTopicsController;
	ContextController contextController;
	RelatedImagesController relatedImagesController;
	RelatedDiscussionsController relatedDiscussionsController;
	
	public String getPageTitle() { return "Resources for " + passage.getDisplayName(); }
	public RelatedDiscussionsController getRelatedDiscussionsController() { return relatedDiscussionsController; }
	public RelatedImagesController getRelatedImagesController() { return relatedImagesController; }
	public ContextController getContextController() { return contextController; }
	public RelatedPassagesController getRelatedPassagesController() { return relatedPassagesController; }
	public RelatedTopicsController getRelatedTopicsController() { return relatedTopicsController; }
	public CommentsController getCommentsController() {return commentsController;}
	public BiblePassage getPassage() { return passage; }
	public String getListen() { return "<span class=\"listen\" id=\"listen_" + String.valueOf(passage.getStartVerseId()) + "_" + String.valueOf(passage.getEndVerseId()) + "\"></span>"; }
	
	public PassageBean (){
		//String[] reference = parseUrl();
		int verseId=0;
	
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = request.getParameter("url");
		passage = BiblePassage.loadByUrl(url); 
		passage.populateVerses(AppUser.getCurrent().TranslationId);

		
		if (passage.getVerses().size()==1) verseId = passage.getVerses().get(0).getId();

		relatedPassagesController = new RelatedPassagesController("passage", passage.getId(), 10);
		commentsController = new CommentsController("passage", passage.getId());
		relatedTopicsController = new RelatedTopicsController("passage", passage.getId(), 5);
		contextController = new ContextController(verseId);
		relatedImagesController = new RelatedImagesController("passage", passage.getId(), passage.getUrl());
		relatedDiscussionsController = new RelatedDiscussionsController("passage", passage.getId(), 5);

		
	}
	
	
	
}
