import java.util.Random;

public class Soap {
	private static EZImage soap;
  public static boolean soapMode = false;
	public static boolean soapAppeared = false;
	private static final int limit = 100;
	private static final int threshold = 10;
	
	public static void randomizedAppear() {
    Random random = new Random();
		if (random.nextInt(limit) <= threshold) {
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
      soap = EZ.addImage("soap.png", x, y);
      soapAppeared = true;
		}
	}
	
	public static void remove() {
		EZ.removeEZElement(soap);
    soapAppeared = false;
	}
	
	public static boolean isPointInSoap(Kid kid) {
    return soap.isPointInElement(kid.getX() + 30, kid.getY() + 30) || soap.isPointInElement(kid.getX() - 30, kid.getY() + 30) || soap.isPointInElement(kid.getX() + 30, kid.getY() - 30) || soap.isPointInElement(kid.getX() - 30, kid.getY() - 30);
	}
}
