import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class HighScores {
  private final List<String> names = new ArrayList<>();
  private final List<Integer> highScores = new ArrayList<>();
  private final File namesFile = new File("names.txt");
  private final File highScoresFile = new File("highscores.txt");
  private int newHighScoreIndex = -1;

  HighScores() {
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

  int getHighScore() {
    return highScores.size() == 0 ? 0 : highScores.get(0);
  }

  void enterNameIfNewHighScore(int score) {
    newHighScoreIndex = -1;

    int numHighScores = highScores.size();
    // Show enter name prompt if new high score
    if (score > 0 && (numHighScores < 10 || score > highScores.get(numHighScores - 1))) {
      JFrame enterNameFrame = new JFrame("You got a top 10 high score!");
      enterNameFrame.setLayout(new FlowLayout());
      enterNameFrame.add(new JLabel("Please enter your name:"));
      JTextField nameTextField = new JTextField(30);
      nameTextField.addActionListener(e -> {
        String name = nameTextField.getText();
        if (numHighScores == 0) {
          names.add(name);
          highScores.add(score);
          newHighScoreIndex = 0;
        } else if (numHighScores < 10 && score <= highScores.get(numHighScores - 1)) {
          names.add(name);
          highScores.add(score);
          newHighScoreIndex = highScores.size() - 1;
        } else {
          for (int i = 0; i < numHighScores; i++) {
            if (score > highScores.get(i)) {
              names.add(i, name);
              highScores.add(i, score);
              if (highScores.size() == 11) {
                names.remove(10);
                highScores.remove(10);
              }
              newHighScoreIndex = i;
              break;
            }
          }
        }
        writeToFiles();
        enterNameFrame.dispose();
      });
      enterNameFrame.add(nameTextField);
      enterNameFrame.add(new JLabel("Press 'Enter' when you're done"));
      enterNameFrame.setBounds(Game.windowWidth / 2 - 250, Game.windowHeight / 2 - 50, 500, 100);
      enterNameFrame.setVisible(true);
    }
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
    } catch (IOException e) {
      System.out.println("Error writing names and high scores");
    }
  }

  void createTable() {
    for (int i = 0; i < 10; i++) {
      EZ.addText(Game.windowWidth / 2 - 400, i * 60 + 210, i + 1 + ".", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    int numNames = names.size();
    for (int i = 0; i < numNames; i++) {
      EZ.addText(Game.windowWidth / 2, i * 60 + 210, names.get(i), i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    int numHighScores = highScores.size();
    for (int i = 0; i < numHighScores; i++) {
      EZ.addText(Game.windowWidth / 2 + 400, i * 60 + 210, highScores.get(i) + " Dads", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
  }
}