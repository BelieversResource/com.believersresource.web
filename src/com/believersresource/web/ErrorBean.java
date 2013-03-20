package com.believersresource.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;

import com.believersresource.data.ErrorLog;

@ManagedBean(name="errorBean")
@RequestScoped
public class ErrorBean {

	private String output="";
	public String getOutput() { return output; }
	
	public ErrorBean()
	{
		FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        Throwable ex = (Throwable) requestMap.get("javax.servlet.error.exception");

        String url = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        
        String additionalInfo = writer.toString();
        
        Map<String, String> headers = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
        
        
        ErrorLog log = new ErrorLog();
        log.setErrorMessage(ex.getMessage());
        log.setAdditionalInfo(additionalInfo);
        //log.setUrl( request.getRequestURL().toString());
        log.setUrl( url  );
        log.setErrorDate(new Date());
        log.save();
        
        
        
        //LOG.fatal(writer.toString());

        //return writer.toString();
		
	}
	
}
