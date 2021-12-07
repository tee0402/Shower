package com.company;

public class Dad {
	EZImage dadImage;
	
	//Add dad image
	Dad (String filename, int x, int y) {
    dadImage = EZ.addImage(filename, x, y);
	}
	
	//Remove image from EZ
	void remove() {
		EZ.removeEZElement(dadImage);
	}
}