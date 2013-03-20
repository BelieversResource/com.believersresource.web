package com.believersresource.web.controls;

import java.text.SimpleDateFormat;

import com.believersresource.data.Comment;
import com.believersresource.data.Comments;
import com.believersresource.web.AppUser;



public class CommentsController {
	private com.believersresource.data.Comments comments;
	private String contentType = "";
	private int contentId = 0;
	
	public String getSectionName() {
		if (contentType.equals("forumthread")) return "Replies"; else return "Comments";
	}
	
	public String getOutputText() {
		StringBuilder sb = new StringBuilder();
		if (comments.size()>0)
		{
			getBranch(comments, sb, 0);
		} else {
			if (contentType == "forumthread")
            {
                sb.append("<li class=\"childComment\">Be the first to leave a reply</li>");
            }
            else
            {
                sb.append("<li class=\"childComment\">Be the first to leave a comment</li>");
            }
		}
		return sb.toString();
	}
	
	public String getSeeAllText() { return "<br/><div id=\"comment_" + contentType + "_" + String.valueOf(contentId) + "_0\"><a href=\"#\" onclick=\"return loadComment(this);\" class=\"submit\">SUBMIT</a></div>"; }
	
	public CommentsController(String mContentType, int mContentId)
	{
		this.contentType = mContentType;
		this.contentId = mContentId;
		comments = Comments.load(contentType, contentId);
		comments = comments.buildTree(0);
	}
	
	
	private void getBranch(Comments comments, StringBuilder sb, int indent)
    {
        int userId=-1;
        if (AppUser.getCurrent().IsAuthenticated) userId=AppUser.getCurrent().UserData.getId();

        for (Comment comment : comments)
        {
            if (indent > 1)
            {
                sb.append("<li class=\"childComment subChild\">\n");
            }
            else
            {
                sb.append("<li class=\"childComment\">\n");
            }
            if (comment.getVotes() >= AppUser.getCurrent().MinVotes)
            {
                sb.append("<div class=\"info-frame\">");
                sb.append("<div class=\"info-holder\">");
                sb.append("<a href=\"#\" class=\"collapse\" onclick=\"return toggleComment(this);\"></a> <b>" + comment.getUserName() + "</b><em class=\"date\"> - " + new SimpleDateFormat("M/d/yyyy").format(comment.getDatePosted()) + "</em>");
                if (!comment.getOriginalBodyIsNull()) sb.append("<sup><a href=\"javascript:showOriginalComment(" + String.valueOf(comment.getId()) + ");\">*</a></sup>");
                sb.append("</div>");
                sb.append("<div id=\"v_comment_" + String.valueOf(comment.getId()) + "\" class=\"voteHolder info\">" + String.valueOf(comment.getVotes()) + "</div>");
                sb.append("</div>\n");
                sb.append("<p>" + com.believersresource.data.bbCode.Utils.convertBBCodeToHtml(comment.getBody(), false));

                if (indent < 2) {
                    sb.append(" <span id=\"comment_" + comment.getContentType() + "_" + String.valueOf(comment.getContentId()) + "_" + String.valueOf(comment.getId()));
                    if (comment.getUserId() == userId) sb.append("_1");
                    sb.append("\" class=\"commentHolder\"></span>");
                }

                if (AppUser.getCurrent().IsAdmin) sb.append(" <a class=\"reply\" href=\"javascript:moderateComment(" + String.valueOf(comment.getId()) + ");\">[Moderate]</a><br/>");

                sb.append("</p>\n");
                if (comment.getChildComments() != null)
                {
                    sb.append("<ul>");
                    getBranch(comment.getChildComments(), sb, indent + 1);
                    sb.append("</ul>");
                }
            }
            else
            {
                sb.append("<div class=\"hiddenComment\">Buried Comment <a href=\"#\" onclick=\"return showComment(this," + String.valueOf(comment.getId()) + ");\">[Show]</a>"); 
            }

            sb.append("</li>\n");
         }

    }



	


}
