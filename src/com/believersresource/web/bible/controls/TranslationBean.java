package com.believersresource.web.bible.controls;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.web.AppUser;

@ManagedBean(name="bible_controls_translationBean")
@RequestScoped
public class TranslationBean {

	String[] translations = new String[]{"WEB - World English Bible", "KJV - King James Version", "ASV - American Standard Version", "DAR - Darby", "YLT - Youngs Literal Translation", "BBE - Bible in Basic English"};
	
	public String getOutput()
	{
		StringBuilder sb=new StringBuilder();
		int translationId = AppUser.getCurrent().TranslationId;
		for (int i=0;i<translations.length;i++)
		{
			if (i == translationId-1)
			{
				sb.append("<option value=\"" + String.valueOf(i+1) + "\" selected=\"true\">" + translations[i] + "</option>");
			} else 
			{
				sb.append("<option value=\"" + String.valueOf(i+1) + "\">" + translations[i] + "</option>");
			}
		}
		return sb.toString();
	}
	
	public TranslationBean()
	{
		
	}
	
}
