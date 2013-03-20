package com.believersresource.web.gallery.controls;

import com.believersresource.data.Images;

public class ImageListController {

	private Images images;

	public Images getImages() { return images; }
	
	public ImageListController (Images images)
	{
		this.images = images;
	}
	
}
