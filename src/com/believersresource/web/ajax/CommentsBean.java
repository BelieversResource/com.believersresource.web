package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.controls.CommentsController;

@ManagedBean(name="ajax_commentsBean")
@RequestScoped
public class CommentsBean {

	private CommentsController commentsController;
	public CommentsController getCommentsController() { return commentsController; }

	public CommentsBean ()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String contentType = request.getParameter("contentType");
		int contentId = Integer.parseInt(request.getParameter("contentId"));
		commentsController = new CommentsController(contentType, contentId);
	}
	
}
