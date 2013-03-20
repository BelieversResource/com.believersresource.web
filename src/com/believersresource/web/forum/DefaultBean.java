package com.believersresource.web.forum;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.believersresource.web.forum.controls.SubmitButtonController;
import com.believersresource.web.forum.controls.ThreadListController;

 
@ManagedBean(name="forum_defaultBean")
@RequestScoped
public class DefaultBean {
	
	private ThreadListController threadListController;
	private SubmitButtonController submitButtonController;
	
	public ThreadListController getThreadListController() { return threadListController; }
	public SubmitButtonController getSubmitButtonController() { return submitButtonController; }
   
   public DefaultBean() 
   {
	   threadListController = new ThreadListController(12);
	   submitButtonController = new SubmitButtonController(12);
   }
     
}