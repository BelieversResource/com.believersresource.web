package com.believersresource.web.forum;

import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;
import com.believersresource.data.Comments;
import com.believersresource.data.ForumThread;
import com.believersresource.web.controls.CommentsController;
import com.believersresource.web.downloads.controls.BreadCrumbController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

 
@ManagedBean(name="forum_threadBean")
@RequestScoped
public class ThreadBean {
	
	ForumThread thread;
	Comment startComment;
	BreadCrumbController breadCrumbController;
	CommentsController commentsController;
	RelatedTopicsController relatedTopicsController;
	
	public String getPageTitle() { return thread.getTitle(); }
	public ForumThread getThread() { return thread; }
	public String getPostedBy() { return "Posted By: " + startComment.getUserName() + " on " + new SimpleDateFormat("M/d/yyyy").format(startComment.getDatePosted()) ; }
	public String getFirstPost() {return com.believersresource.data.bbCode.Utils.convertBBCodeToHtml(startComment.getBody(), false);}
	public String getVotes() {return "<table><tr><td><b>Votes:</b></td><td><div id=\"v_comment_" + String.valueOf(startComment.getId()) + "\" class=\"voteHolder info\">" + String.valueOf(startComment.getVotes()) + "</div></tr></table>"; }
	public BreadCrumbController getBreadCrumbController() {return breadCrumbController;}
	public CommentsController getCommentsController() {return commentsController;}
	public RelatedTopicsController getRelatedTopicsController() {return relatedTopicsController;}
	
	public ThreadBean() 
	{
		
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getParameter("url");
		thread = ForumThread.load(url);
		
		startComment=Comments.load("forumthreadstart", thread.getId()).get(0);
		commentsController = new CommentsController("forumthread", thread.getId());
		relatedTopicsController = new RelatedTopicsController("forumthread", thread.getId(), 10);
	}

	

	
     
}