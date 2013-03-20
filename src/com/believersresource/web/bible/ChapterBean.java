package com.believersresource.web.bible;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.BibleVerse;
import com.believersresource.data.BibleVerses;
import com.believersresource.data.Topics;
import com.believersresource.web.AppUser;
import com.believersresource.web.CachedData;
import com.believersresource.web.controls.CommentsController;
import com.believersresource.web.controls.RelatedImagesController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

@ManagedBean(name="bible_chapterBean")
@RequestScoped
public class ChapterBean 
{
	BiblePassage passage;
	CommentsController commentsController;
	RelatedTopicsController relatedTopicsController;
	RelatedImagesController relatedImagesController;
	
	public RelatedImagesController getRelatedImagesController() { return relatedImagesController; }
	public RelatedTopicsController getRelatedTopicsController() { return relatedTopicsController; }
	public CommentsController getCommentsController() {return commentsController;}
	public BiblePassage getPassage() { return passage; }
	
	public String getName() { return passage.getDisplayName().split(":")[0]; }
	public String getPageTitle() { return "Resources for " + passage.getDisplayName().split(":")[0]; }
	public String getListen() { return "<span class=\"listen\" id=\"listen_" + String.valueOf(passage.getStartVerseId()) + "_" + String.valueOf(passage.getEndVerseId()) + "\"></span>"; }
	
	public String getOutput() {
		boolean alt = false;
		StringBuilder sb=new StringBuilder();
        for (BibleVerse verse : passage.getVerses())
        {
            if (alt) sb.append("<li class=\"grey\">"); else sb.append("<li>");
            sb.append("<sup>[<a href=\"" + verse.getUrl() + "\">" + String.valueOf(verse.getVerseNumber()) + "</a>]" + verse.getBody() + "</li>");
            alt = !alt;
        }
        return sb.toString();
	}
	
	public ChapterBean (){
		
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getParameter("url").replace(".html", "");
		int idx = url.lastIndexOf("-");
		int chapter = Integer.parseInt(url.substring(idx+1, url.length()));
		String book = url.substring(0, idx).replace("-", " ");

		int bookId = CachedData.getBibleBooks().getByName(book).getId();
		BibleVerses verses = BibleVerses.loadByBookIdChapterNumber(bookId, chapter);
		passage = BiblePassage.load(verses.get(0).getId(), verses.get(verses.size()-1).getId());
		if (passage==null)
		{
			passage = new BiblePassage();
			passage.setStartVerseId(verses.get(0).getId());
			passage.setEndVerseId(verses.get(verses.size()-1).getId());
			passage.save();
			Topics.generateForPassage(passage.getId(), passage.getStartVerseId(), passage.getEndVerseId());
		}
		passage.populateVerses(AppUser.getCurrent().TranslationId);
		
		commentsController = new CommentsController("passage", passage.getId());
		relatedTopicsController = new RelatedTopicsController("passage", passage.getId(), 5);
		relatedImagesController = new RelatedImagesController("passage", passage.getId(), passage.getUrl());
	}
	
	
}
