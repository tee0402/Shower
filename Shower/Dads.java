import java.util.ArrayList;
import java.util.List;

class Dads {
	private final List<Dad> dads = new ArrayList<>();
  private final String fileName;

  Dads(String fileName) {
    this.fileName = fileName;
  }
	
	void add(int x, int y) {
		dads.add(new Dad(x, y));
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

  private class Dad {
    private final EZImage dadImage;

    private Dad(int x, int y) {
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