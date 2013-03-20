package com.believersresource.web.ajax;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BibleVerses;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;

@ManagedBean(name="ajax_voteBean")
@RequestScoped
public class VoteBean {
	
	BibleVerses verses;
	
	int totalVotes = 0;
	public String getOutput() {
		return String.valueOf(totalVotes);
	}

	public VoteBean ()
	{
		HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String contentType = hsr.getParameter("contentType");
		int contentId = Integer.parseInt(hsr.getParameter("contentId"));
		boolean up = hsr.getParameter("up").equals("1");

		Vote vote = new Vote();
        vote.setContentType(contentType);
        vote.setContentId(contentId);
        vote.setIpAddress(AppUser.getCurrent().IpAddress);
        if (AppUser.getCurrent().IsAuthenticated) vote.setUserId(AppUser.getCurrent().UserData.getId()); else vote.setUserIdIsNull(true);
        
        vote.setVoteDate(new java.sql.Date(new java.util.Date().getTime()));
        if (up) vote.setPoints(1); else vote.setPoints(-1);
        totalVotes = vote.cast();
	}
}
