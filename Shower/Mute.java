import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Mute {
  private final File muteFile = new File("mute.txt");
  private boolean muted;
  private EZImage mutedImage;
  private EZImage unmutedImage;

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

  boolean isUnmuted() {
    return !muted;
  }

  private void addMutedImage() {
    mutedImage = EZ.addImage("muted.png", 29 * Game.windowWidth / 30, 14 * Game.windowHeight / 15);
  }

  private void removeMutedImage() {
    EZ.removeEZElement(mutedImage);
    mutedImage = null;
  }

  private void addUnmutedImage() {
    unmutedImage = EZ.addImage("unmuted.png", 29 * Game.windowWidth / 30, 14 * Game.windowHeight / 15);
  }

  private void removeUnmutedImage() {
    EZ.removeEZElement(unmutedImage);
    unmutedImage = null;
  }

  void addCorrectMutedImage() {
    if (muted) {
      addMutedImage();
    } else {
      addUnmutedImage();
    }
  }

  void checkIfMutedImageClicked(int mouseX, int mouseY, EZSound sceneMusic) {
    if (mutedImage != null && mutedImage.isPointInElement(mouseX, mouseY)) {
      muted = false;
      removeMutedImage();
      addUnmutedImage();
      if (sceneMusic != null) {
        sceneMusic.play();
      }
      writeToFile();
    } else if (unmutedImage != null && unmutedImage.isPointInElement(mouseX, mouseY)) {
      muted = true;
      removeUnmutedImage();
      addMutedImage();
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
}