import java.util.Random;

class Soap {
	private EZImage soapImage;
  private boolean soapAppeared = false;
  private boolean soapMode = false;

  boolean isSoapAppeared() {
    return soapAppeared;
  }

  boolean isSoapMode() {
    return soapMode;
  }
  void setSoapMode(boolean soapMode) {
    this.soapMode = soapMode;
  }

  void randomizedAppear() {
    Random random = new Random();
    if (random.nextInt(10) == 0) {
      int x = (random.nextBoolean() ? 50 : Game.windowWidth / 2 + 200) + random.nextInt(Game.windowWidth / 2 - 250);
      int y = random.nextInt(Game.windowHeight - 400) + 300;
      soapImage = EZ.addImage("resources/soap.png", x, y);
      soapAppeared = true;
		}
	}
	
	void remove() {
		EZ.removeEZElement(soapImage);
    soapAppeared = false;
	}
	
	boolean isPointInSoap(int x, int y) {
    return soapImage.isPointInElement(x + 30, y + 30) || soapImage.isPointInElement(x - 30, y + 30) || soapImage.isPointInElement(x + 30, y - 30) || soapImage.isPointInElement(x - 30, y - 30);
	}
}