class Dad {
	private final EZImage dadImage;
	
	//Add dad image
	Dad (String fileName, int x, int y) {
    dadImage = EZ.addImage(fileName, x, y);
	}

  boolean isPointInDad(int x, int y) {
    return dadImage.isPointInElement(x, y);
  }
	
	//Remove image from EZ
	void remove() {
		EZ.removeEZElement(dadImage);
	}
}