package com.believersresource.web.modal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;
import com.believersresource.web.AppUser;

@ManagedBean(name="modal_moderateCommentBean")
@RequestScoped
public class ModerateCommentBean {
	
	Comment comment;
	private String body;
	private String output;
	
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public String getOutput() { return output; }
	public void setOutput(String output) { this.output = output; }


	public ModerateCommentBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		int id = Integer.parseInt(request.getParameter("commentId"));
		comment = Comment.load(id);
		body=comment.getBody();
	}
	
	public void submit()
	{
		if (AppUser.getCurrent().IsAdmin)
		{
			comment.setBody(body);
			comment.save();
			output = "<script>window.parent.location.reload();</script>";
		}
	}
	
}
