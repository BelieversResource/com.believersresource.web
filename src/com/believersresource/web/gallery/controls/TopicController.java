package com.believersresource.web.gallery.controls;

public class TopicController {
	private String topicName;
	private String topicUrl;
	private ImageListController imageListController;
	
	public String getTopicName() { System.out.println("made it"); return topicName;  }
	public ImageListController getImageListController() { return imageListController; }
	public String getSeeAll() { if (imageListController.getImages().size() == 0) return ""; else return "<a href=\"/gallery/" + topicUrl + "\" class=\"see-all\">SEE ALL</a>"; }

	
	public TopicController(String topicName, String topicUrl, ImageListController imageListController)
	{
		this.topicUrl = topicUrl;
		this.topicName = topicName;
		this.imageListController = imageListController;
	}
	
}
