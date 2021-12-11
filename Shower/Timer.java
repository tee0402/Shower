public class Timer {
	private long start;
	private static long timeLimit = 4000;
	private static final long decrement = 200;
	private static final long lowerBound = 2000;
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public long timeElapsed() {
		long now = System.currentTimeMillis();
		return now - start;
	}
	
	public double timeLeft() {
		return (double) (timeLimit - timeElapsed()) / 1000;
	}
	
	public void decrement() {
		if (timeLimit > lowerBound) {
			timeLimit -= decrement;
		}
	}
	
	public void resetDecrement() {
		timeLimit = 4000;
	}
}
