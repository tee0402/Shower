import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Mute {
  private final File muteFile = new File("mute.txt");
  private boolean muted;
  private final MutedImage mutedImage = new MutedImage("resources/muted.png");
  private final MutedImage unmutedImage = new MutedImage("resources/unmuted.png");

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

  void addCorrectMutedImage() {
    if (muted) {
      mutedImage.add();
    } else {
      unmutedImage.add();
    }
  }

  void checkIfMutedImageClicked(int mouseX, int mouseY, EZSound sceneMusic) {
    if (mutedImage.isShowing() && mutedImage.isPointInImage(mouseX, mouseY)) {
      muted = false;
      mutedImage.remove();
      unmutedImage.add();
      if (sceneMusic != null) {
        sceneMusic.play();
      }
      writeToFile();
    } else if (unmutedImage.isShowing() && unmutedImage.isPointInImage(mouseX, mouseY)) {
      muted = true;
      unmutedImage.remove();
      mutedImage.add();
      if (sceneMusic != null) {
        sceneMusic.pause();
      }
      writeToFile();
    }
  }

  private void writeToFile() {
    try {
      FileWriter fileWriter = new FileWriter(muteFile);
      if (muted) {
        fileWriter.write("1");
      }
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("Error writing mute file");
    }
  }

  private static class MutedImage {
    private final String fileName;
    private EZImage image;

    private MutedImage(String fileName) {
      this.fileName = fileName;
    }

    private void add() {
      image = EZ.addImage(fileName, 29 * Game.windowWidth / 30, 14 * Game.windowHeight / 15);
    }

    private void remove() {
      EZ.removeEZElement(image);
      image = null;
    }

    private boolean isShowing() {
      return image != null;
    }

    private boolean isPointInImage(int x, int y) {
      return image.isPointInElement(x, y);
    }
  }
}