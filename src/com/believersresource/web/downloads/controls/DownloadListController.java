package com.believersresource.web.downloads.controls;

import com.believersresource.data.Download;
import com.believersresource.data.Downloads;

public class DownloadListController {

	Downloads downloads;
	
	public boolean getRendered() { return downloads.size() > 0; }
	
	public String getOutput()
	{
		boolean alt=false;
		StringBuilder sb = new StringBuilder();
        
        for (Download download : downloads)
        {
            if (alt) sb.append("<li class=\"grey\">"); else sb.append("<li>");
            sb.append("<div id=\"v_download_" + String.valueOf(download.getId()) + "\" class=\"voteHolder info\">" + String.valueOf(download.getVotes()) + "</div>");

            sb.append("<div class=\"description\"><p><a href=\"/downloads/" + download.getUrl() + "\">" + download.getTitle() + "</a> - ");
            sb.append(download.getShortDescription());
            sb.append("</p></div>");
            sb.append("</li>");
            alt = !alt;
        }
        return sb.toString();
	}
	
	public DownloadListController(int categoryId)
	{
		this.downloads = com.believersresource.data.Downloads.loadByCategoryId(categoryId);
		this.downloads.sort("votes");
		this.downloads.reverse();
	}
}
