import java.util.ArrayList;

class Dads {
	private final ArrayList<Dad> dads = new ArrayList<>();
	
	void add(String fileName, int x, int y) {
		dads.add(new Dad(fileName, x, y));
	}

  boolean isPointInDads(int x, int y) {
    for (Dad dad : dads) {
      if (dad.isPointInDad(x, y)) {
        return true;
      }
    }
    return false;
  }
	
	void removeAll() {
		for (Dad dad : dads) {
			dad.remove();
		}
    dads.clear();
	}

  private static class Dad {
    private final EZImage dadImage;

    private Dad(String fileName, int x, int y) {
      dadImage = EZ.addImage(fileName, x, y);
    }

    private boolean isPointInDad(int x, int y) {
      return dadImage.isPointInElement(x, y);
    }

    private void remove() {
      EZ.removeEZElement(dadImage);
    }
  }
}