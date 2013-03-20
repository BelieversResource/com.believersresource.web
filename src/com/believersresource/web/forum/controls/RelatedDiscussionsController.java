package com.believersresource.web.forum.controls;

import com.believersresource.data.ForumThread;
import com.believersresource.data.ForumThreads;

public class RelatedDiscussionsController {

	private ForumThreads threads;
	private String contentType = "";
	private int contentId = 0;
	private int limit = 0;
	
	public boolean getRendered() { return threads.size() > 0;}
	
	public String getOutput()
	{
		int idx = 0;
		boolean alt = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < limit && i < threads.size(); i++)
        {
            ForumThread thread = threads.get(i);
            sb.append("<li><a href=\"/forum/" + thread.getUrl() + "\">" + thread.getTitle() + "</a></li>");
            alt = !alt;
        }
        return sb.toString();
	}
	
	public String getSeeAll()
	{
		if (threads.size() > limit) return "<br/><a href=\"javascript:showAllRelatedDiscussions('" + contentType + "'," + String.valueOf(contentId) + ");\" class=\"see-all\">SEE ALL</a>";
		return "";
	}
	
	public RelatedDiscussionsController(String contentType, int contentId, int limit)
	{
		this.contentType = contentType;
		this.contentId = contentId;
		this.limit = limit;
		
		if (contentType=="passage")
		{
			threads = ForumThreads.loadForPassage(contentId);
		} else 
		{
			threads = ForumThreads.loadForTopic(contentId);
		}
		
		
	}
}
