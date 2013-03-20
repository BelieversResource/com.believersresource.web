package com.believersresource.web.forum.controls;

import com.believersresource.data.ForumThread;
import com.believersresource.data.ForumThreads;

public class ThreadListController {

	ForumThreads threads;
	
	public String getOutput()
	{
		boolean alt = false;
		StringBuilder sb=new StringBuilder();
		for (ForumThread thread : threads)
        {
            if (alt) sb.append("<tr class=\"grey post\">"); else sb.append("<tr class=\"post\">");
            sb.append("<td width=\"60\"><span  id=\"v_comment_" + String.valueOf(thread.getFirstCommentId()) + "\" class=\"voteHolder info\">" + String.valueOf(thread.getVotes()) + "</span></td>");
            sb.append("<td><a href=\"/forum/" + thread.getUrl() + "\">" + thread.getTitle() + "</a><br/><i>by " + thread.getFirstUserName() + "</i></td>");
            sb.append("<td>" + String.valueOf(thread.getPosts()) + " posts<br/></td>");
            sb.append("</tr>");
            alt = !alt;
        }
		return sb.toString();
	}
	
	public ThreadListController(int categoryId)
	{
		threads = ForumThreads.loadByExtendedByCategoryId(categoryId, -1);
		
	}
	
}
