package com.company;

import java.lang.*;

public class Timer {
	private long start;
	private static double timeLimit = 4000;
	private static final double decrement = 200;
	private static final double lowerBound = 2000;
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public long elapsedTime() {
		long now = System.currentTimeMillis();
		return now - start;
	}
	
	public double timeLeft() {
		return (timeLimit - (double)elapsedTime()) / 1000;
	}
	
	public void decrement() {
		if (timeLimit > lowerBound) {
			timeLimit -= decrement;
		}
	}
	
	public void reset() {
		timeLimit = 4000;
	}
}
