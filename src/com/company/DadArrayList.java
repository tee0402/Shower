package com.company;

import java.util.*;

public class DadArrayList {
	private ArrayList<Dad> al = new ArrayList<Dad>();
	
	void addDad(Dad dad) {
		al.add(dad);
	}
	
	void removeDads() {
		for (int i = al.size(); i > 0; i--) {
			al.get(i-1).remove();
			al.remove(i-1);
		}
	}
	
	boolean isPointInDads(Kid kid) {
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).dad.isPointInElement(kid.getX(), kid.getY())) {
				return true;
			}
		}
		return false;
	}
}
