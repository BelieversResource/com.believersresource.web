package com.believersresource.web.downloads;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.Categories;
import com.believersresource.data.Category;
import com.believersresource.data.Download;
import com.believersresource.data.Link;
import com.believersresource.data.Links;
import com.believersresource.data.Utils;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;

@ManagedBean(name="downloads_editDownloadBean")
@RequestScoped
public class EditDownloadBean {

	private String downloadTitle;
	private String shortDescription;
	private String description;
	private Download download;
	private ArrayList<SelectItem> categoryList;
	private int categoryId = 0;
	private Links links;
	private int downloadId = 0;
	
	

	private String linkType1;
	private String linkName1;
	private String linkUrl1;
	private String linkType2;
	private String linkName2;
	private String linkUrl2;
	private String linkType3;
	private String linkName3;
	private String linkUrl3;
	private String linkType4;
	private String linkName4;
	private String linkUrl4;
	private String linkType5;
	private String linkName5;
	private String linkUrl5;
	
	public String getLinkType1() { return linkType1; }
	public void setLinkType1(String linkType1) { this.linkType1 = linkType1; }
	public String getLinkName1() { return linkName1; }
	public void setLinkName1(String linkName1) { this.linkName1 = linkName1; }
	public String getLinkUrl1() { return linkUrl1; }
	public void setLinkUrl1(String linkUrl1) { this.linkUrl1 = linkUrl1; }
	public String getLinkType2() { return linkType2; }
	public void setLinkType2(String linkType2) { this.linkType2 = linkType2; }
	public String getLinkName2() { return linkName2; }
	public void setLinkName2(String linkName2) { this.linkName2 = linkName2; }
	public String getLinkUrl2() { return linkUrl2; }
	public void setLinkUrl2(String linkUrl2) { this.linkUrl2 = linkUrl2; }
	public String getLinkType3() { return linkType3; }
	public void setLinkType3(String linkType3) { this.linkType3 = linkType3; }
	public String getLinkName3() { return linkName3; }
	public void setLinkName3(String linkName3) { this.linkName3 = linkName3; }
	public String getLinkUrl3() { return linkUrl3; }
	public void setLinkUrl3(String linkUrl3) { this.linkUrl3 = linkUrl3; }
	public String getLinkType4() { return linkType4; }
	public void setLinkType4(String linkType4) { this.linkType4 = linkType4; }
	public String getLinkName4() { return linkName4; }
	public void setLinkName4(String linkName4) { this.linkName4 = linkName4; }
	public String getLinkUrl4() { return linkUrl4; }
	public void setLinkUrl4(String linkUrl4) { this.linkUrl4 = linkUrl4; }
	public String getLinkType5() { return linkType5; }
	public void setLinkType5(String linkType5) { this.linkType5 = linkType5; }
	public String getLinkName5() { return linkName5; }
	public void setLinkName5(String linkName5) { this.linkName5 = linkName5; }
	public String getLinkUrl5() { return linkUrl5; }
	public void setLinkUrl5(String linkUrl5) { this.linkUrl5 = linkUrl5; }
	
	public int getDownloadId() {return downloadId; }
	public void setDownloadId(int downloadId) { this.downloadId = downloadId; pageLoad();}
	public ArrayList<SelectItem> getCategoryList() { return categoryList; }
	public int getCategoryId() { return categoryId; }
	public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
	public String getDownloadTitle() { return downloadTitle; }
	public void setDownloadTitle(String downloadTitle) { this.downloadTitle = downloadTitle; }
	public String getShortDescription() { return shortDescription; }
	public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	
	private void pageLoad()
	{
		System.out.println("pageLoad");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		
		if (request.getParameter("id")!=null) downloadId = Integer.parseInt(request.getParameter("id"));
		
		System.out.println(downloadId);
		
		if (downloadId>0) 
		{
			download = Download.load(downloadId);
			categoryId = download.getCategoryId();
			checkAccess();
		} else {
			download = new Download();
			download.setCategoryId(1);
			download.setDescription("");
			download.setShortDescription("");
			if (AppUser.getCurrent().IsAuthenticated) download.setUserId(AppUser.getCurrent().UserData.getId());
		}
		links = Links.load("download", download.getId());
		if (!FacesContext.getCurrentInstance().isPostback()) populateFields();
	}
	
	public EditDownloadBean()
	{
		categoryList = new ArrayList<SelectItem>();
		Categories categories = Categories.loadByType("download").buildTree(0);
		populateCategories(categories,0);
		if (!FacesContext.getCurrentInstance().isPostback()) pageLoad();
	}
	
	private void populateFields()
	{
		downloadTitle = download.getTitle();
		description = download.getDescription();
		if (description.contains("<br>")) description = description.replaceAll("<br>", "\r\n");
		shortDescription = download.getShortDescription();
		populateLinks();
		
		//categoryList = new ArrayList<SelectItem>();
		//Categories categories = Categories.loadByType("download").buildTree(0);
		//populateCategories(categories,0);
		//SelectItem item;
	}
	
	private void populateCategories(Categories categories, int indent)
    {
        for (Category category : categories)
        {
            String name = "";
            if (indent > 0)
            {
                name += "└";
                if (indent > 1) {
                	for (int i=0;i<indent-1;i++)
                	{
                		name += "-";
                	}
                }
                name += " ";
            }
            name += category.getName();
            categoryList.add(new SelectItem(String.valueOf(category.getId()), name));
            if (category.getChildCategories() != null) populateCategories(category.getChildCategories(), indent + 1);
        }
    }
	
	
	private void checkAccess()
    {
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
        boolean canEdit = false;
        if (AppUser.getCurrent().IsAuthenticated)
        {
        	if (AppUser.getCurrent().IsAdmin|| AppUser.getCurrent().UserData.getId() == download.getUserId()) canEdit = true;
        }
        if (!canEdit) {
        	try { response.sendRedirect("/downloads/" + download.getUrl()); } catch (Exception ex) {}
        }
    }
	
	public void submit()
    {
		System.out.println("submit");
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        boolean isNew = download.getId() == 0;

        
        download.setCategoryId(this.categoryId);
        download.setDescription(com.believersresource.data.bbCode.Utils.convertHtmlToBBCode(description.trim().replace("\n","<br/>")).trim());
        download.setShortDescription(Utils.removeHtml(shortDescription,false).trim());
        download.setTitle(Utils.removeHtml(this.downloadTitle,false).trim());
        download.save();

        if (isNew)
        {
            download.setUrl(Utils.getUrlName(download.getTitle() + " " + String.valueOf(download.getId())) + ".html");
            download.setUserId(AppUser.getCurrent().UserData.getId());
            download.setVotes(1);
            download.save();
            Vote.cast("download", download.getId(), AppUser.getCurrent().IpAddress, AppUser.getCurrent().UserData.getId(), true);
        }

        saveLinks();
        //EditLinks1.Save(links, "download", download.Id);

        try { response.sendRedirect("/downloads/" + download.getUrl()); } catch (Exception ex) {}
    }
	
	private void saveLinks()
	{
		for (int i = 0; i < 5; i++)
        {
            Link link = new Link();
            if (links.size() > i) link = links.get(i);
            saveLink(link, i);
        }
	}
	
	private void saveLink(Link link, int idx)
	{
		
        String linkType = getLinkType(idx);
        String linkName = getLinkName(idx);
        String linkUrl = getLinkUrl(idx);
        
        link.setLinkType(linkType);
        link.setName(linkName);
        link.setUrl(linkUrl);
        link.setVotes(1);
        link.setContentId(download.getId());
        link.setContentType("download");

        if (link.getUrl() != "" && link.getName() != "")
        {
            link.save();
        }
        else
        {
            if (link.getId() > 0) Link.delete(link.getId());
        }
	}
	
	private void populateLinks()
	{
		for (int i = 0; i < links.size(); i++)
        {
            Link link = links.get(i);
            populateLink(i, link.getLinkType(), link.getName(), link.getUrl());
        }

        if (links.size() == 0)
        {
        	populateLink(0, "download", "Download", "");
        	populateLink(1, "source", "Source", "");
        }
	}
	
	private void populateLink(int idx, String linkType, String name, String url)
	{
		setLinkType(idx, linkType);
		setLinkName(idx, name);
		setLinkUrl(idx, url);
	}
	
	
	private String getLinkUrl(int idx)
    {
        String result="";
        switch (idx)
        {
            case 0: result = linkUrl1; break;
            case 1: result = linkUrl2; break;
            case 2: result = linkUrl3; break;
            case 3: result = linkUrl4; break;
            case 4: result = linkUrl5; break;
        }
        return result;
    }
	
	private String getLinkName(int idx)
    {
        String result="";
        switch (idx)
        {
            case 0: result = linkName1; break;
            case 1: result = linkName2; break;
            case 2: result = linkName3; break;
            case 3: result = linkName4; break;
            case 4: result = linkName5; break;
        }
        return result;
    }
	
	private String getLinkType(int idx)
    {
        String result="";
        switch (idx)
        {
            case 0: result = linkType1; break;
            case 1: result = linkType2; break;
            case 2: result = linkType3; break;
            case 3: result = linkType4; break;
            case 4: result = linkType5; break;
        }
        return result;
    }

	
	
	
	
	
	
	private String setLinkUrl(int idx, String value)
    {
        String result="";
        switch (idx)
        {
            case 0: linkUrl1 = value; break;
            case 1: linkUrl2 = value; break;
            case 2: linkUrl3 = value; break;
            case 3: linkUrl4 = value; break;
            case 4: linkUrl5 = value; break;
        }
        return result;
    }
	
	private String setLinkName(int idx, String value)
    {
        String result="";
        switch (idx)
        {
            case 0: linkName1 = value; break;
            case 1: linkName2 = value; break;
            case 2: linkName3 = value; break;
            case 3: linkName4 = value; break;
            case 4: linkName5 = value; break;
        }
        return result;
    }
	
	private String setLinkType(int idx, String value)
    {
        String result="";
        switch (idx)
        {
            case 0: linkType1 = value; break;
            case 1: linkType2 = value; break;
            case 2: linkType3 = value; break;
            case 3: linkType4 = value; break;
            case 4: linkType5 = value; break;
        }
        return result;
    }
	
}
