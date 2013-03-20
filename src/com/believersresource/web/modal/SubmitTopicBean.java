package com.believersresource.web.modal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.believersresource.data.RelatedTopic;
import com.believersresource.data.Topic;
import com.believersresource.data.Vote;
import com.believersresource.web.AppUser;

@ManagedBean(name="modal_submitTopicBean")
@RequestScoped
public class SubmitTopicBean {
	
	private boolean showConfirm;
	private String topicText;
	private String error;
	private String contentType;
	private int contentId;
	
	
	public String getContentType() { return contentType; }
	public void setContentType(String contentType) { this.contentType = contentType;   }
	public int getContentId() { return contentId; }
	public void setContentId(int contentId) { this.contentId = contentId;   }
	public boolean getShowConfirm() { return showConfirm; }
	public String getError() { return error; }
	public String getTopicText() { return topicText;  }
	public void setTopicText(String topicText) { this.topicText = topicText; }
	


	public void submit()
	{
		addTopic(false);
	}
	
	public void confirm()
	{
		addTopic(true);
	}
	
	public SubmitTopicBean()
	{
		if (FacesContext.getCurrentInstance().isPostback()) {
			showConfirm=true;
		} else {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			contentType = request.getParameter("contenttype");
			contentId = Integer.parseInt(request.getParameter("contentId"));
		}
	}
	
	
	
	private void addTopic(boolean confirmed)
    {
		error = "";
        if (!topicText.matches("[A-Za-z]{3,99}"))
        {
            error = "<div class=\"error\">Topics must be a single word that is at least 3 characters long.</div>";
            showConfirm=false;
        }
        else
        {
            String baseWord = Topic.getBaseWord(topicText);
            Topic t = Topic.loadByBaseWord(baseWord);
            if (t == null)
            {
                if (confirmed)
                {
                    t = new Topic();
                    t.setBaseWord(baseWord);
                    t.setName(topicText);
                    t.setUrl(t.getName().toLowerCase() + ".html");
                    t.save();
                }
                else
                {
                    showConfirm = true;
                    error = "";
                    return;
                }
            }


            RelatedTopic rt = RelatedTopic.load(contentType, contentId, t.getId());
            if (rt == null)
            {
                rt = new RelatedTopic();
                rt.setContentId(contentId);
                rt.setContentType(contentType);
                rt.setTopicId(t.getId());
                rt.setVotes(0);
                rt.save();
            }
            int userId = 0;
            if (AppUser.getCurrent().IsAuthenticated) userId = AppUser.getCurrent().UserData.getId();
            Vote.cast("relatedtopic", rt.getId(), AppUser.getCurrent().IpAddress, userId, true);

            error = "<script>parent.closeModal();parent.showAllRelatedTopics('" + contentType + "'," + String.valueOf(contentId) + ");</script>";

        }
    }
	
}
