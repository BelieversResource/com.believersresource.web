package com.believersresource.web.cp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;
import com.believersresource.data.Comments;
import com.believersresource.web.AppUser;

@ManagedBean(name="cp_editPostBean")
@RequestScoped
public class EditPostBean {

	private int commentId;
	private String body;
	private Comment comment;
	
	public int getCommentId() { return commentId; }
	public void setCommentId(int commentId) { this.commentId = commentId; }
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	
	public EditPostBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (!FacesContext.getCurrentInstance().isPostback()) { 
			commentId = Integer.parseInt(request.getParameter("id"));
			comment = Comment.load(commentId);
			if (!AppUser.getCurrent().IsAuthenticated || comment.getUserId() != AppUser.getCurrent().UserData.getId()) {
				try { response.sendRedirect("/"); } catch (Exception ex) {}
			}
			body = com.believersresource.data.bbCode.Utils.convertBBCodeToHtml(comment.getBody(), true);
		}
	}
	
	public void submit()
	{
		comment = Comment.load(commentId);
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (comment.getContentType().equals("forumthreadstart"))
        {
            Comments comments = Comments.load("forumthread", comment.getContentId());
            if (comments.size() > 0 && comment.getOriginalBodyIsNull()) comment.setOriginalBody(comment.getBody());
        } else {
            Comments comments = Comments.loadByParentId(comment.getId());
            if (comments.size() > 0 && comment.getOriginalBodyIsNull()) comment.setOriginalBody(comment.getBody());
        }

        comment.setBody(com.believersresource.data.bbCode.Utils.convertHtmlToBBCode(body));
        comment.save();

        String redirectUrl = Comment.determineContentUrl(comment.getContentType(), comment.getContentId());
        try { response.sendRedirect(redirectUrl); } catch (Exception ex) {}
        
	}
	
}
