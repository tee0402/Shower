class Timer {
	private long start;
	private long timeLimit = 4000;

  void start() {
		start = System.currentTimeMillis();
	}
	
	double timeLeft() {
		return (double) (timeLimit - (System.currentTimeMillis() - start)) / 1000;
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