package com.believersresource.web.modal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Comment;

@ManagedBean(name="modal_originalPostBean")
@RequestScoped
public class OriginalPostBean {

	public String getOutput() { 
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		Comment c = Comment.load(id);
		return com.believersresource.data.bbCode.Utils.convertBBCodeToHtml(c.getOriginalBody(), false);
	}
}
