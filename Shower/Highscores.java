import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

class Highscores {
  private final ArrayList<String> names;
  private final ArrayList<Integer> highScores;
  private final File namesFile;
  private final File highScoresFile;
  private int newHighScoreIndex = -1;

  Highscores() {
    names = new ArrayList<>();
    highScores = new ArrayList<>();
    namesFile = new File("names.txt");
    highScoresFile = new File("highscores.txt");
    try {
      if (namesFile.exists() || namesFile.createNewFile()) {
        Scanner scanner = new Scanner(namesFile);
        while (scanner.hasNextLine()) {
          names.add(scanner.nextLine());
        }
        scanner.close();
      }
      if (highScoresFile.exists() || highScoresFile.createNewFile()) {
        Scanner scanner = new Scanner(highScoresFile);
        while (scanner.hasNextInt()) {
          highScores.add(scanner.nextInt());
        }
        scanner.close();
      }
    } catch (IOException e) {
      System.out.println("Error scanning high score files");
    }
  }

  ArrayList<String> getNames() {
    return names;
  }

  ArrayList<Integer> getHighScores() {
    return highScores;
  }

  int getHighScore() {
    return highScores.size() == 0 ? 0 : highScores.get(0);
  }

  int getNewHighScoreIndex() {
    return newHighScoreIndex;
  }

  void checkHighScore(int score) {
    newHighScoreIndex = -1;

    int numHighScores = highScores.size();
    // Show enter name prompt if new high score
    if (score > 0 && (numHighScores < 10 || score > highScores.get(numHighScores - 1))) {
      JFrame enterNameFrame = new JFrame("You got a top 10 high score!");
      enterNameFrame.setLayout(new FlowLayout());
      enterNameFrame.add(new JLabel("Please enter your name:"));
      JTextField nameTextField = new JTextField(30);
      nameTextField.addActionListener(e -> {
        String newHighScoreName = nameTextField.getText();
        newHighScoreIndex = addHighScore(newHighScoreName, score);
        enterNameFrame.dispose();
      });
      enterNameFrame.add(nameTextField);
      enterNameFrame.add(new JLabel("Press 'Enter' when you're done"));
      enterNameFrame.setBounds(Game.windowWidth / 2 - 250, Game.windowHeight / 2 - 50, 500, 100);
      enterNameFrame.setVisible(true);
    }
  }

  // Adds a new high score and returns the index
  private int addHighScore(String name, int score) {
    int numHighScores = highScores.size();
    if (numHighScores == 0) {
      names.add(name);
      highScores.add(score);
      return 0;
    } else if (numHighScores < 10 && score <= highScores.get(numHighScores - 1)) {
      names.add(name);
      highScores.add(score);
      return highScores.size() - 1;
    } else {
      for (int i = 0; i < numHighScores; i++) {
        if (score > highScores.get(i)) {
          names.add(i, name);
          highScores.add(i, score);
          if (highScores.size() == 11) {
            names.remove(10);
            highScores.remove(10);
          }
          return i;
        }
      }
    }
    writeToFiles();
    return -1;
  }

  private void writeToFiles() {
    try {
      FileWriter fileWriter = new FileWriter(namesFile);
      for (String name : names) {
        fileWriter.write(name + "\n");
      }
      fileWriter.close();
      fileWriter = new FileWriter(highScoresFile);
      for (int highScore : highScores) {
        fileWriter.write(highScore + " ");
      }
      fileWriter.close();
    } catch (IOException ex) {
      System.out.println("Error writing names and high scores");
    }
  }
}
