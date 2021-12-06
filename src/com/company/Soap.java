package com.company;

import java.util.*;
public class Soap {
	private static EZImage soap;
	public static int x;
	public static int y;
	public static boolean soapMode = false;
	public static boolean soapAppeared = false;
	private static boolean soapXGenerated = false;
	private static final int limit = 100;
	private static final int threshold = 10;
	Random random = new Random();
	
	Soap() {
		
	}
	
	void soapOrNo() {
		int soapOrNo = random.nextInt(limit);
		if (soapOrNo <= threshold) {
			appear();
		}
	}
	
	void appear() {
		while (!soapXGenerated) {
			x = random.nextInt(EZ.getWindowWidth() - 100) + 50;
			if (x < EZ.getWindowWidth() / 2 - 200 || x > EZ.getWindowWidth() / 2 + 200) {
				soapXGenerated = true;
			}
		}
		y = random.nextInt(EZ.getWindowHeight() - 400) + 300;
		soap = EZ.addImage("soap.png", x, y);
		soapAppeared = true;
	}
	
	void remove() {
		EZ.removeEZElement(soap);
	}
	
	boolean isPointInSoap(Kid kid) {
		if (soap.isPointInElement(kid.getX() + 30, kid.getY() + 30) || soap.isPointInElement(kid.getX() - 30, kid.getY() + 30) || soap.isPointInElement(kid.getX() + 30, kid.getY() - 30) || soap.isPointInElement(kid.getX() - 30, kid.getY() - 30)) {
			return true;
		}
		else {
			return false;
		}
	}
}
