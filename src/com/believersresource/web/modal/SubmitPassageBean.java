package com.believersresource.web.modal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.BiblePassage;
import com.believersresource.data.RelatedPassage;
import com.believersresource.data.RelatedTopic;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;
import com.believersresource.web.CachedData;

@ManagedBean(name="modal_submitPassageBean")
@RequestScoped
public class SubmitPassageBean {
	
	private String reference;
	private boolean showPassage;
	private String passageText;
	private String output;
	private int startVerseId;
	private int endVerseId;
	private String contentType;
	private int contentId;
	
	
	public String getContentType() { return contentType; }
	public void setContentType(String contentType) { this.contentType = contentType;   }
	
	public int getContentId() { return contentId; }
	public void setContentId(int contentId) { this.contentId = contentId;   }
	
	public String getReference() { return reference; }
	public void setReference(String reference) { this.reference = reference;   }
	public boolean getShowPassage() { return showPassage; }
	public String getPassageText() { return passageText;  }
	public void setPassageText(String passageText) { this.passageText = passageText; }
	public String getOutput() { return output; }
	public void setOutput(String output) { this.output = output; }

	public void submit()
	{
		lookup();

		BiblePassage passage = new BiblePassage();
        passage.setStartVerseId(startVerseId);
        passage.setEndVerseId(endVerseId);
        
        BiblePassage tempPassage = BiblePassage.load(passage.getStartVerseId(), passage.getEndVerseId());
        if (tempPassage != null)
        {
            passage = tempPassage;
        }
        else
        {
            passage.setVotes(0);
            passage.save();
        }

        if (contentType.equals("topic"))
        {
            RelatedTopic rt = RelatedTopic.load("passage", passage.getId(), contentId);
            if (rt == null)
            {
                rt = new RelatedTopic();
                rt.setContentType("passage");
                rt.setContentId(passage.getId());
                rt.setTopicId(contentId);
                rt.setVotes(0);
                rt.save();
            }
            int userId = 0;
            if (AppUser.getCurrent().IsAuthenticated) userId = AppUser.getCurrent().UserData.getId();
            Vote.cast("relatedtopic", rt.getId(), AppUser.getCurrent().IpAddress, userId, true);
        }
        else
        {
            RelatedPassage rp = RelatedPassage.load(contentType, contentId, passage.getId());
            if (rp == null)
            {
                rp = new RelatedPassage();
                rp.setContentType(contentType);
                rp.setContentId(contentId);
                rp.setPassageId(passage.getId());
                rp.setVotes(0);
                rp.save();
            }
            int userId = 0;
            if (AppUser.getCurrent().IsAuthenticated) userId = AppUser.getCurrent().UserData.getId();
            Vote.cast("relatedpassage", rp.getId(), AppUser.getCurrent().IpAddress, userId, true);
        }
        output = "<script>parent.closeModal();parent.showAllRelatedPassages('" + contentType + "'," + String.valueOf(contentId) + ");</script>";
	
	}
	
	public void lookup()
	{
		BiblePassage passage = BiblePassage.parse(reference, CachedData.getBibleBooks(), true);

        if (passage != null && passage.getVerses()!=null)
        {
            passageText = passage.getDisplayName() + " - " + passage.getBody();
            showPassage = true;
            startVerseId = passage.getStartVerseId();
            endVerseId = passage.getEndVerseId();
        }
        else
        {
            showPassage = false;
            output = "<div class=\"error\">Could not find a passage matching that reference. Please try again.</div>";
        }
	}
	
	public SubmitPassageBean()
	{
		if (FacesContext.getCurrentInstance().isPostback()) {
			showPassage=true;
		} else {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			contentType = request.getParameter("contenttype");
			contentId = Integer.parseInt(request.getParameter("contentId"));
		}
	}
}
