class Timer {
	private long start;
	private long timeLimit = 3000;

  long getTimeLimit() {
    return timeLimit;
  }

  void start() {
		start = System.currentTimeMillis();
	}
	
	double timeLeft() {
		return timeLimit - (System.currentTimeMillis() - start);
	}
	
	void decrement() {
    if (timeLimit > 1500) {
      timeLimit -= 100;
		}
	}
	
	void resetDecrement() {
		timeLimit = 3000;
	}
}