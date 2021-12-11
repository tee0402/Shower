class Timer {
	private long start;
	private long timeLimit = 4000;

  void start() {
		start = System.currentTimeMillis();
	}
	
	long timeElapsed() {
		long now = System.currentTimeMillis();
		return now - start;
	}
	
	double timeLeft() {
		return (double) (timeLimit - timeElapsed()) / 1000;
	}
	
	void decrement() {
    if (timeLimit > 2000) {
      timeLimit -= 200;
		}
	}
	
	void resetDecrement() {
		timeLimit = 4000;
	}
}
