public class Kid {
	public EZImage kidPicture;
	private int x, y;
	
	//Create kid image by passing x and y
	public Kid(String filename, int posX, int posY) {
		kidPicture = EZ.addImage(filename, posX, posY);
		x = posX;
		y = posY;
	}

	//Return x position
	public int getX() {
		return x;
	}

	//Return y position
	public int getY() {
		return y;
	}

  //Translate image to x and y position
	private void setKidImagePosition(int posX, int posY) {
		kidPicture.translateTo(posX, posY);
	}

	//Move image left by step
	public void moveLeft(int step) {
		x -= step;
		setKidImagePosition(x, y);
	}

	//Move image right by step
	public void moveRight(int step) {
		x += step;
		setKidImagePosition(x, y);
	}

	//Move image up by step
	public void moveUp(int step) {
		y -= step;
		setKidImagePosition(x, y);
	}

	//Move image down by step
	public void moveDown(int step) {
		y += step;
		setKidImagePosition(x, y);
	}

	//Keyboard controls for moving the kid
	public void ControlIt() {
		if (EZInteraction.isKeyDown('w')) {
			moveUp(20);
		}
    if (EZInteraction.isKeyDown('a')) {
			moveLeft(20);
		}
    if (EZInteraction.isKeyDown('s')) {
			moveDown(20);
		}
    if (EZInteraction.isKeyDown('d')) {
			moveRight(20);
		}
	}
	
	//Remove image
	void remove() {
		EZ.removeEZElement(kidPicture);
	}
}
