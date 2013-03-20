package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.bible.controls.RelatedPassagesController;

@ManagedBean(name="ajax_relatedPassagesBean")
@RequestScoped
public class RelatedPassagesBean {
	
	RelatedPassagesController relatedPassagesController;
	
	public RelatedPassagesController getRelatedPassagesController() { return relatedPassagesController; }

	public RelatedPassagesBean ()
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String contentType = hsr.getParameter("contentType");
		int contentId = Integer.parseInt(hsr.getParameter("contentId"));
		relatedPassagesController = new RelatedPassagesController(contentType, contentId, 999);
	}
}
