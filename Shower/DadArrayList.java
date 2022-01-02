import java.util.ArrayList;

class DadArrayList {
	private final ArrayList<Dad> dads = new ArrayList<>();
	
	void add(Dad dad) {
		dads.add(dad);
	}

  boolean isPointInDads(int x, int y) {
    for (Dad dad : dads) {
      if (dad.isPointInDad(x, y)) {
        return true;
      }
    }
    return false;
  }
	
	void removeDads() {
		for (Dad dad : dads) {
			dad.remove();
		}
    dads.clear();
	}
}