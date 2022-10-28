import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Mute {
  private final File muteFile = new File("mute.txt");
  private boolean muted;
  private final MuteImage muteImage = new MuteImage();

  Mute() {
    try {
      if (muteFile.exists() || muteFile.createNewFile()) {
        Scanner scanner = new Scanner(muteFile);
        muted = scanner.hasNextInt() && scanner.nextInt() == 1;
        scanner.close();
      }
    } catch (IOException e) {
      System.out.println("Error scanning mute file");
    }
  }

  void playIfUnmuted(EZSound sound) {
    if (!muted) {
      sound.play();
    }
  }

  void addImage() {
    muteImage.add();
  }

  void checkIfMuteImageClicked(int mouseX, int mouseY, EZSound sceneMusic) {
    if (muteImage.isPointInImage(mouseX, mouseY)) {
      muted = !muted;
      if (sceneMusic != null) {
        if (muted) {
          sceneMusic.pause();
        } else {
          sceneMusic.play();
        }
      }
      muteImage.toggle();
      write();
    }
  }

  private void write() {
    try {
      FileWriter fileWriter = new FileWriter(muteFile);
      fileWriter.write(muted ? "1" : "");
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("Error writing mute file");
    }
  }

  private class MuteImage {
    private EZImage image;

    private void add() {
      image = EZ.addImage(muted ? "resources/muted.png" : "resources/unmuted.png", 29 * Game.windowWidth / 30, 14 * Game.windowHeight / 15);
    }

    private void toggle() {
      EZ.removeEZElement(image);
      add();
    }

    private boolean isPointInImage(int x, int y) {
      return image.isPointInElement(x, y);
    }
  }
}