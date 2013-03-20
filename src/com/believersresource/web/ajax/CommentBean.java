package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;

@ManagedBean(name="ajax_commentBean")
@RequestScoped
public class CommentBean {
	
	boolean showPost = false;
	
	public boolean getShowPost() { return showPost; }
	public boolean getShowAuth() { return !AppUser.getCurrent().IsAuthenticated; }
	
	private String output = "";
	public String getOutput() { return output; }

	public CommentBean ()
	{
		if (AppUser.getCurrent().IsAuthenticated)
		{
			showPost = true;
			
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String contentType = request.getParameter("contentType");
			int contentId = Integer.parseInt(request.getParameter("contentId"));
			int parentId = 0;
			if (request.getParameter("parentId")!=null) parentId = Integer.parseInt(request.getParameter("parentId"));
			String body = request.getParameter("body");
			if (body!=null) postComment(contentType, contentId, parentId, body); else populate(contentType, contentId, parentId);
		}
	}
	
	private void populate(String contentType, int contentId, int parentId)
	{
		output = "<textarea id=\"commentText_" + contentType + "_" + String.valueOf(contentId) + "_" + String.valueOf(parentId) + "\" rows=\"3\" style=\"width:99%\" class=\"commentText\" ></textarea>";
	}
	
	private void postComment(String contentType, int contentId, int commentId, String body)
    {
        Comment comment = new Comment();
        comment.setActive(true);
        comment.setBody(com.believersresource.data.bbCode.Utils.convertHtmlToBBCode(body.trim()).trim());
        comment.setContentId(contentId);
        comment.setContentType(contentType);
        comment.setDatePosted(new java.sql.Date(new java.util.Date().getTime()));
        comment.setParentId(commentId);
        comment.setUserId(AppUser.getCurrent().UserData.getId());
        comment.setVotes(1);
        comment.save();
        
        Vote vote = new Vote();
        vote.setContentId(comment.getId());
        vote.setContentType("comment");
        vote.setIpAddress(AppUser.getCurrent().IpAddress);
        vote.setPoints(1);
        vote.setUserId(AppUser.getCurrent().UserData.getId());
        vote.setVoteDate(new java.sql.Date(new java.util.Date().getTime()));
        vote.save();

        showPost = false;
    }
	
}
