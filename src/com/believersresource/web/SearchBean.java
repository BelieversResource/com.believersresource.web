package com.believersresource.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.TextSearch;
import com.believersresource.data.Topic;


@ManagedBean(name="searchBean")
@RequestScoped
public class SearchBean {

	String result="";
	public String getOutput() { return result; }
	
	public SearchBean() {
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String term = hsr.getParameter("q").trim();

		
        if (term.length()<1) {
        	try{ response.sendRedirect("/"); } catch (Exception ex) {}
        }

        BiblePassage passage = BiblePassage.parse(term, CachedData.getBibleBooks(), true);
        if (passage != null)
        {
        	try{ response.sendRedirect("/bible/" + passage.getUrl()); } catch (Exception ex) {}
        }
        else
        {
        	
            if (!term.contains(" "))
            {
                String baseWord = Topic.getBaseWord(term);
                Topic topic = Topic.loadByBaseWord(baseWord);
                if (topic != null) {
                	try { response.sendRedirect("/topics/" + topic.getUrl()); } catch (Exception ex) {}
                }
            }
            else
            {
                int verseId = TextSearch.findMatch(term);
                passage = BiblePassage.load(verseId, verseId);
                passage.populateVerses(AppUser.getCurrent().TranslationId);
                try { response.sendRedirect("/bible/" + passage.getUrl()); } catch (Exception ex) {}
            }
            
        }
	}
}
