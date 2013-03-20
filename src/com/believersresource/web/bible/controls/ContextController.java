package com.believersresource.web.bible.controls;

import com.believersresource.data.BiblePassage;
import com.believersresource.web.AppUser;

public class ContextController {

	BiblePassage passage;
	
	public boolean getRendered() { return passage != null; }
	
	public String getOutput() { 
		if (passage!=null) return "<a href=\"/bible/" + passage.getUrl() + "\">" + passage.getDisplayName() + "</a> - " + passage.getBody(); else return ""; 
	}
	
	public String getListen() { 
		if (passage!=null) return "<span class=\"listen\" id=\"listen_" + String.valueOf(passage.getStartVerseId()) + "_" + String.valueOf(passage.getEndVerseId()) + "\"></span>"; else return ""; 
	}
	
	public String getChapterLink() {
		if (passage!=null)
		{
			String url = passage.getVerses().get(0).getUrl();
	        int idx = url.lastIndexOf("-");
	        url=url.substring(0,idx) + ".html";
	        return "<br/><i>View <a href=\"" + url + "\">" + passage.getVerses().get(0).getBookName() + " " + String.valueOf(passage.getVerses().get(0).getChapterNumber()) + "</a></i>";
		} else return "";
	}
	
	public ContextController(int verseId)
	{
		if (verseId>0)
		{
			passage = BiblePassage.loadContext(verseId);
			if (passage != null)
			{
				passage.populateVerses(AppUser.getCurrent().TranslationId);
			}
		}
	}
}
