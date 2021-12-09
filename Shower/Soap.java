import java.util.*;

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
      while (!soapXGenerated) {
        x = random.nextInt(EZ.getWindowWidth() - 100) + 50;
        if (x < EZ.getWindowWidth() / 2 - 200 || x > EZ.getWindowWidth() / 2 + 200) {
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
