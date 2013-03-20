package com.believersresource.web.topics.controls;

import com.believersresource.data.Topic;
import com.believersresource.data.Topics;

public class RelatedTopicsController 
{

	Topics topics;
	String contentType="";
	int contentId=0;
	int limit = 0;
	
	public String getOutput()
	{
		if (topics.size() > 0)
		{
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < limit && i < topics.size(); i++)
            {
                Topic topic = topics.get(i);
                if (contentType=="image") {
                	sb.append("<li><div id=\"v_relatedimage_" + String.valueOf(topic.getRelatedImageId()) + "\" class=\"voteHolder info\">" + String.valueOf(topic.getVotes()) + "</div>");
                } else {
                	sb.append("<li><div id=\"v_relatedtopic_" + String.valueOf(topic.getRelatedTopicId()) + "\" class=\"voteHolder info\">" + String.valueOf(topic.getVotes()) + "</div>");
                }
                sb.append("<a href=\"/topics/" + topic.getUrl() + "\">" + topic.getName() + "</a></li>");
            }
            return sb.toString();
		} else {
			return  "<li>There are not currently any related topics for this " + contentType.replace("forumthread","forum thread") + ".  Why not submit one?</li>";
		}
	}
	
	public String getSeeAll()
	{
		if (topics.size() > limit)
        {
            return "<br/><a href=\"javascript:showAllRelatedTopics('" + contentType + "'," + String.valueOf(contentId) + ");\" class=\"see-all\">SEE ALL</a>";
        }
        else
        {
            return "<br/><a href=\"javascript:submitRelatedTopic('" + contentType + "'," + String.valueOf(contentId) + ");\" class=\"submit\">SUBMIT</a>";
        }
	}
	
	public RelatedTopicsController(String contentType, int contentId, int limit)
	{
		this.contentType=contentType;
		this.contentId=contentId;
		this.limit = limit;
		
		if (contentType.equals("image"))
		{
			topics = Topics.loadForImage(contentId, limit+1);
		} else {
			topics = Topics.loadRelated(contentType, contentId, limit+1);
		}
	}
	
}
