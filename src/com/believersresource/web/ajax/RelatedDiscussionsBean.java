package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.forum.controls.RelatedDiscussionsController;

@ManagedBean(name="ajax_relatedDiscussionsBean")
@RequestScoped
public class RelatedDiscussionsBean {
	
	RelatedDiscussionsController relatedDiscussionsController;
	
	public RelatedDiscussionsController getRelatedDiscussionsController() { return relatedDiscussionsController; }

	public RelatedDiscussionsBean ()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String contentType = request.getParameter("contentType");
		int contentId = Integer.parseInt(request.getParameter("contentId"));
		relatedDiscussionsController = new RelatedDiscussionsController(contentType, contentId, 999);
	}
}
