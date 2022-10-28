class Kid {
	private final EZImage kidPicture;
	private int x, y;

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

	void control() {
    int step = 20;
    if (EZInteraction.isKeyDown('w')) {
      y -= step;
		}
    if (EZInteraction.isKeyDown('a')) {
      x -= step;
		}
    if (EZInteraction.isKeyDown('s')) {
      y += step;
		}
    if (EZInteraction.isKeyDown('d')) {
      x += step;
		}
    kidPicture.translateTo(x, y);
	}

	void remove() {
		EZ.removeEZElement(kidPicture);
	}
}