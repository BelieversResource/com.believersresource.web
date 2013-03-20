package com.believersresource.web.modal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name="modal_frameBean")
@RequestScoped
public class FrameBean {

	public String getOutput() { 
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int height = Integer.parseInt(request.getParameter("height"));
		String url = request.getParameter("url");
		return "<iframe src=\"" + url + "\" width=\"100%\" height=\"" + String.valueOf(height) + "\" frameborder=\"0\"></iframe>";
	}

	public FrameBean ()
	{
		
	}
	
}
