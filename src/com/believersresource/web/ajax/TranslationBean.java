package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.web.AppUser;

@ManagedBean(name="ajax_translationBean")
@RequestScoped
public class TranslationBean {
	
	public String getOutput() {return "";}
	
	public TranslationBean()
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int id = Integer.parseInt(request.getParameter("id"));
		AppUser.getCurrent().TranslationId = id;
	}

}
