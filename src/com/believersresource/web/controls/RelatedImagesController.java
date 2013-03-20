package com.believersresource.web.controls;

import com.believersresource.data.Image;
import com.believersresource.data.Images;

public class RelatedImagesController {

	Image image = null;
	String url = "";
	
	public boolean getRendered()
	{
		if (image==null) return false; else return true; 
	}
	
	public String getOutput()
	{
		String result = "";
		if (image != null) result = "<a href=\"/gallery/image.aspx?id=" + String.valueOf(image.getId()) + "\"><img src=\"http://www.believersresource.com/content/images/" + String.valueOf(image.getId()) + "." + image.getExtension() + "\" width=\"250\" /></a>";
		return result;
	}
	
	public String getSeeAll()
	{
		if (!url.equals("")) return "<a href=\"/gallery/" + url + "\" class=\"see-all\">SEE ALL</a>"; else return "";
	}
	
	public RelatedImagesController(String contentType, int contentId, String url)
	{
		this.url = url;
		if (contentType.equals("passage"))
		{
			image = Image.loadForPassage(contentId);
		} else {
			image = Image.loadRelated(contentType, contentId);
		}
	}
	
}
