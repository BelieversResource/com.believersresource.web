package com.believersresource.web.gallery.controls;

import java.util.ArrayList;

import com.believersresource.data.Images;
import com.believersresource.data.Topic;
import com.believersresource.data.Topics;

public class TopicListController {

	private ArrayList<TopicController> topicControllers;
	public ArrayList<TopicController> getTopicControllers() { return topicControllers; }
	
	public TopicListController(String contentType, int contentId)
	{
		Topics topics;
        if (contentType.equals("passage"))
        {
            topics = Topics.loadForPassage(contentId);
        } else {
            topics = Topics.loadRelated(contentType, contentId, 999);
        }
        
        topicControllers = new ArrayList<TopicController>();
        
        for (Topic topic : topics)
        {
        	Images images = Images.loadRelated("topic", topic.getId(), 4);
        	ImageListController imageListController = new ImageListController(images);
        	TopicController topicController = new TopicController(topic.getName(), topic.getUrl(), imageListController);
        	topicControllers.add(topicController);
        }
        
        
        
	}
	
}
