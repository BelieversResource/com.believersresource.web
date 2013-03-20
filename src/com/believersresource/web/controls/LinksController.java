package com.believersresource.web.controls;

import com.believersresource.data.Link;
import com.believersresource.data.Links;

public class LinksController {

	Links links;
	
	public String getOutput()
	{
		StringBuilder sb = new StringBuilder();
        for (Link link : links)
        {
            if (link.getLinkType().equals("download"))
            {
                sb.append("<li><a href=\"/tracklink.aspx?id=" + String.valueOf(link.getId()) + "\" >" + link.getName() + "</a></li>");
            }
            else
            {
                sb.append("<li><a href=\"" + link.getUrl() + "\" onclick=\"recordOutboundClick(this,'" + link.getLinkType() + "');\" target=\"_blank\" >" + link.getName() + "</a></li>");
            }
        }
        return sb.toString();
	}
	
	public LinksController(String contentType, int contentId)
	{
		links = Links.load(contentType, contentId);
	}
	
	
}
