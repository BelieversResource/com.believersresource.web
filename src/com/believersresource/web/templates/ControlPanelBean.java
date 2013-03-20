package com.believersresource.web.templates;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.User;
import com.believersresource.web.AppUser;

@ManagedBean(name="templates_controlPanelBean")
@RequestScoped
public class ControlPanelBean {

	
	public String getOutput() {return "";}
	
	public ControlPanelBean() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (!AppUser.getCurrent().IsAuthenticated)
        {
            String authCode = request.getParameter("authCode");
            if (authCode != null)
            {
                User user = User.loadByAuthCode(authCode);
                if (user != null)
                {
                    user.setAuthCodeIsNull(true);
                    user.save();
                    AppUser.login(user);
                }
                else
                {
                    try { response.sendRedirect("/"); } catch (Exception ex) {}
                }
            }
            else
            {
            	try { response.sendRedirect("/"); } catch (Exception ex) {}
            }
        }
		
	}
}
