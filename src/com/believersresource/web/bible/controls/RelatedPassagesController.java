package com.believersresource.web.bible.controls;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.BiblePassages;
import com.believersresource.data.RelatedPassages;
import com.believersresource.web.AppUser;

public class RelatedPassagesController {
	BiblePassages passages;
	String contentType="";
	int contentId=0;
	int limit = 0;
	
	public String getSeeAll()
	{
		if (passages.size() > limit && limit<20)
        {
            return "<a href=\"javascript:showAllRelatedPassages('" + contentType + "'," + String.valueOf(contentId) + ");\" class=\"see-all\">SEE ALL</a>";
        } else {
            return "<a href=\"javascript:submitRelatedPassage('" + contentType + "'," + String.valueOf(contentId) + ");\" class=\"submit\">SUBMIT</a>";
        }
	}
	
	public String getOutput()
	{
		StringBuilder sb = new StringBuilder();
		boolean alt = false;
		
		for (int i = 0; i < limit && i < passages.size(); i++)
        {
            BiblePassage passage = passages.get(i);
            if (alt) sb.append("<li class=\"grey\">"); else sb.append("<li>");

            sb.append("<div class=\"info\" style=\"width:85px;\">");
            if (passage.getRelatedTopicId() > 0)
            {
                sb.append("<div id=\"v_relatedtopic_" + String.valueOf(passage.getRelatedTopicId()) + "\" class=\"voteHolder\">" + String.valueOf(passage.getVotes()) + "</div>");
            }
            else
            {
                sb.append("<div id=\"v_relatedpassage_" + String.valueOf(passage.getRelatedPassageId()) + "\" class=\"voteHolder\">" + String.valueOf(passage.getVotes()) + "</div>");
            }
            sb.append("<span class=\"listen\" id=\"listen_" + String.valueOf(passage.getStartVerseId()) + "_" + String.valueOf(passage.getEndVerseId()) + "\"></span>");
            sb.append("</div>");
            sb.append("<div class=\"description\"><p>");
            sb.append("<a href=\"/bible/" + passage.getUrl() + "\">" + passage.getDisplayName() + "</a> - ");
            sb.append(passage.getBody());
            sb.append("</p></div>");
            sb.append("</li>");
            alt = !alt;
        }
        return sb.toString();
	}
	
	public RelatedPassagesController(String contentType, int contentId, int limit)
	{
		this.contentType=contentType;
		this.contentId=contentId;
		this.limit = limit;
		
		passages = BiblePassages.loadRelated(contentType, contentId, limit + 20);
		if (passages.size() == 0 && contentType.equals("passage"))
		{
			RelatedPassages.generateForNewPassage(contentId);
			passages = BiblePassages.loadRelated(contentType, contentId, limit + 20);
		}
		passages=passages.consolidateAndUpdate();
		passages.populateVerses(AppUser.getCurrent().TranslationId);
	}
}
