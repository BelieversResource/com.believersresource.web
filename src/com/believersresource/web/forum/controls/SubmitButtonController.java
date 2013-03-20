package com.believersresource.web.forum.controls;

import com.believersresource.data.ForumThread;
import com.believersresource.data.ForumThreads;

public class SubmitButtonController {

	int categoryId;
	
	public String getOutput()
	{
		return "<a class=\"submitDownload\" href=\"/forum/newThread.xhtml?categoryId=" + String.valueOf(categoryId) + "\" onclick=\"return verifyLoggedIn();\">Submit a New Thread</a>";
	}
	
	public SubmitButtonController(int categoryId)
	{
		this.categoryId = categoryId;
	}
	
}
