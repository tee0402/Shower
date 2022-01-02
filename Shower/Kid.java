class Kid {
	private final EZImage kidPicture;
	private int x, y;
  private final int step = 20;
	
	//Create kid image by passing x and y
	Kid(String fileName, int x, int y) {
		kidPicture = EZ.addImage(fileName, x, y);
		this.x = x;
		this.y = y;
	}

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

	//Move image left by step
	private void moveLeft() {
		x -= step;
    kidPicture.translateTo(x, y);
	}

	//Move image right by step
	private void moveRight() {
		x += step;
    kidPicture.translateTo(x, y);
	}

	//Move image up by step
	private void moveUp() {
		y -= step;
    kidPicture.translateTo(x, y);
	}

	//Move image down by step
	private void moveDown() {
		y += step;
    kidPicture.translateTo(x, y);
	}

	//Keyboard controls for moving the kid
	void controlIt() {
		if (EZInteraction.isKeyDown('w')) {
			moveUp();
		}
    if (EZInteraction.isKeyDown('a')) {
			moveLeft();
		}
    if (EZInteraction.isKeyDown('s')) {
			moveDown();
		}
    if (EZInteraction.isKeyDown('d')) {
			moveRight();
		}
	}
	
	//Remove image
	void remove() {
		EZ.removeEZElement(kidPicture);
	}
}