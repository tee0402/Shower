package com.company;

import java.util.*;

public class DadArrayList {
	private final ArrayList<Dad> dads = new ArrayList<>();
	
	void addDad(Dad dad) {
		dads.add(dad);
	}
	
	void removeDads() {
		for (Dad dad : dads) {
			dad.remove();
		}
    dads.clear();
	}
	
	boolean isPointInDads(Kid kid) {
    for (Dad dad : dads) {
      if (dad.dadImage.isPointInElement(kid.getX(), kid.getY())) {
        return true;
      }
    }
		return false;
	}
}
