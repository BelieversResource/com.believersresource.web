package com.believersresource.web.downloads;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Categories;
import com.believersresource.data.Category;
import com.believersresource.data.Download;
import com.believersresource.web.AppUser;
import com.believersresource.web.controls.CommentsController;
import com.believersresource.web.controls.LinksController;
import com.believersresource.web.controls.RelatedImagesController;
import com.believersresource.web.downloads.controls.BreadCrumbController;
import com.believersresource.web.topics.controls.RelatedTopicsController;

 
@ManagedBean(name="downloads_downloadBean")
@RequestScoped
public class DownloadBean {


	Download download;
	CommentsController commentsController;
	LinksController linksController;
	RelatedImagesController relatedImagesController;
	RelatedTopicsController relatedTopicsController;
	BreadCrumbController breadCrumbController;
	
	public String getPageTitle() {return download.getTitle();}
	public Download getDownload() {return download;}
	public CommentsController getCommentsController() {return commentsController;}
	public LinksController getLinksController() {return linksController;}
	public RelatedImagesController getRelatedImagesController() {return relatedImagesController;}
	public RelatedTopicsController getRelatedTopicsController() {return relatedTopicsController;}
	public BreadCrumbController getBreadCrumbController() {return breadCrumbController;}
	
	public String getDescription() { return com.believersresource.data.bbCode.Utils.convertBBCodeToHtml(download.getDescription(), false); }
	
	public String getVoteHtml() { return "<table><tr><td><b>Votes:</b></td><td><div id=\"v_download_" + String.valueOf(download.getId()) + "\" class=\"voteHolder info\">" + String.valueOf(download.getVotes()) + "</div></tr></table>"; }
   
	public String getEdit() {
		if (AppUser.getCurrent().IsAdmin)
        {
            return "<br/><a href=\"editDownload.xhtml?id=" + String.valueOf(download.getId()) + "\" >[Edit]</a>"; 
        } else return "";
	}
	
	public DownloadBean() 
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getParameter("url");
		download = Download.load(url);
		commentsController = new CommentsController("download", download.getId());
		linksController = new LinksController("download", download.getId());
		relatedImagesController = new RelatedImagesController("download", download.getId(), "");
		relatedTopicsController = new RelatedTopicsController("download", download.getId(), 10);
		
		Categories categories = Categories.loadByType("download");
        Category category = categories.getById(download.getCategoryId());
        Categories breadCrumb = Categories.getBreadCrumb(categories, category);
        breadCrumbController = new BreadCrumbController(breadCrumb);	
        
	}
     
}