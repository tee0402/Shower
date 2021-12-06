package com.company;

public class Dad {
	EZImage dad;
	
	//Add dad image
	Dad (String filename, int x, int y) {
		dad = EZ.addImage(filename, x, y);
	}
	
	//Remove image from EZ
	void remove() {
		EZ.removeEZElement(dad);
	}
}