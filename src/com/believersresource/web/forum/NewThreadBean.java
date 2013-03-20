package com.believersresource.web.forum;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;
import com.believersresource.data.ForumThread;
import com.believersresource.data.Utils;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;

@ManagedBean(name="forum_newThreadBean")
@RequestScoped
public class NewThreadBean {

	private String threadTitle;
	private String commentText;
	
	public String getThreadTitle() { return threadTitle; }
	public void setThreadTitle(String threadTitle) { this.threadTitle = threadTitle; }
	public String getCommentText() { return commentText; }
	public void setCommentText(String commentText) { this.commentText = commentText; }

	public NewThreadBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (!AppUser.getCurrent().IsAuthenticated) {
			try{ response.sendRedirect("/forum/"); } catch (Exception ex) {}
		}
		
	}
	
	public void submit()
	{
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		ForumThread thread = new ForumThread();
        thread.setCategoryId(12);
        thread.setTitle(Utils.getTitleCase(threadTitle.trim()));
        thread.save();

        thread.setUrl(Utils.getUrlName(thread.getTitle() + " " + String.valueOf(thread.getId())) + ".html");
        thread.save();

        Comment comment = new Comment();
        comment.setActive(true);
        comment.setBody(com.believersresource.data.bbCode.Utils.convertHtmlToBBCode(commentText.trim()).trim());
        comment.setContentId(thread.getId());
        comment.setContentType("forumthreadstart");
        comment.setDatePosted(new Date());
        comment.setParentId(0);
        comment.setUserId(AppUser.getCurrent().UserData.getId());
        comment.setVotes(1);
        comment.save();

        Vote.cast("comment", comment.getId(), AppUser.getCurrent().IpAddress, AppUser.getCurrent().IpAddress, true);
        try{ response.sendRedirect("/forum/" + thread.getUrl()); } catch (Exception ex) {}
	}
	
}
