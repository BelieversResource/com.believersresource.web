package com.believersresource.web.ajax;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BibleVerses;

@ManagedBean(name="ajax_passageAudioBean")
@RequestScoped
public class PassageAudioBean {
	
	BibleVerses verses;
	
	public String getOutput() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < verses.size(); i++)
        {
            if (i > 0) sb.append(",");
            sb.append("{ \"mp3\": \"http://downloads.believersresource.com/content/audiobibles/web/" + String.format("%02d", verses.get(i).getBookId()) + "/" + String.format("%03d", verses.get(i).getChapterNumber()) + "/" + String.format("%03d", verses.get(i).getVerseNumber()) + ".mp3\" }");
        }
		return sb.toString();
	}

	public PassageAudioBean ()
	{
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//		response.setContentType("application/json");
		response.setContentType("text/js");
		
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		int startVerseId = Integer.parseInt(hsr.getParameter("startVerseId"));
		int endVerseId = Integer.parseInt(hsr.getParameter("endVerseId"));
		verses = BibleVerses.loadRange(startVerseId, endVerseId);
		
	}
}
