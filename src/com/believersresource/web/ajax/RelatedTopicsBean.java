package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.topics.controls.RelatedTopicsController;

@ManagedBean(name="ajax_relatedTopicsBean")
@RequestScoped
public class RelatedTopicsBean {
	
	RelatedTopicsController relatedTopicsController;
	
	public RelatedTopicsController getRelatedTopicsController() { return relatedTopicsController; }

	public RelatedTopicsBean ()
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String contentType = hsr.getParameter("contentType");
		int contentId = Integer.parseInt(hsr.getParameter("contentId"));
		relatedTopicsController = new RelatedTopicsController(contentType, contentId, 999);
	}
}
