package com.believersresource.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.data.Vote;

@ManagedBean(name="testBean")
@RequestScoped
public class TestBean {

	public String getOutput() {return "";}
	
	public TestBean()
	{
		//Vote.cast("relatedtopic", 1095860, AppUser.getCurrent().IpAddress, 0, true);
	}
	
}
