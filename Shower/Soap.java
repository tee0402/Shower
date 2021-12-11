import java.util.Random;

class Soap {
	private EZImage soapImage;
  boolean soapMode = false;
	boolean soapAppeared = false;

  void randomizedAppear() {
    Random random = new Random();
    if (random.nextInt(10) == 0) {
      int x = 0;
      boolean soapXGenerated = false;
      int windowWidth = EZ.getWindowWidth();
      while (!soapXGenerated) {
        x = random.nextInt(windowWidth - 100) + 50;
        if (x < windowWidth / 2 - 200 || x > windowWidth / 2 + 200) {
          soapXGenerated = true;
        }
      }
      int y = random.nextInt(EZ.getWindowHeight() - 400) + 300;
      soapImage = EZ.addImage("soap.png", x, y);
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
