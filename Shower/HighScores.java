import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

class HighScores {
  private record HighScore(String name, int score) {}
  private final List<HighScore> highScores = new ArrayList<>();
  private final File highScoresFile = new File("highscores.txt");
  private int newHighScoreIndex = -1;

  HighScores() {
    try {
      if (highScoresFile.exists() || highScoresFile.createNewFile()) {
        Scanner scanner = new Scanner(highScoresFile);
        while (scanner.hasNextInt()) {
          int score = scanner.nextInt();
          scanner.skip(" ");
          highScores.add(new HighScore(scanner.nextLine(), score));
        }
        scanner.close();
      }
    } catch (IOException e) {
      System.out.println("Error scanning high score files");
    }
  }

  int getHighScore() {
    return highScores.isEmpty() ? 0 : highScores.get(0).score();
  }

  void enterNameIfNewHighScore(int score) {
    newHighScoreIndex = -1;
    // Show enter name prompt if new high score
    int size = highScores.size();
    if (score > 0 && (size < 10 || score >= highScores.get(9).score())) {
      JFrame enterNameFrame = new JFrame("You got a new high score!");
      enterNameFrame.setLayout(new FlowLayout());
      enterNameFrame.add(new JLabel("Please enter your name:"));
      JTextField nameTextField = new JTextField(30);
      nameTextField.addActionListener(e -> {
        String name = nameTextField.getText().trim();
        if (name.length() > 0) {
          HighScore highScore = new HighScore(name, score);
          int insertionPoint = Collections.binarySearch(highScores, highScore, (x, y) -> y.score() - x.score());
          newHighScoreIndex = insertionPoint < 0 ? - (insertionPoint + 1) : insertionPoint;
          highScores.add(newHighScoreIndex, highScore);
          if (size == 10) {
            highScores.remove(10);
          }
          write();
          enterNameFrame.dispose();
        }
      });
      enterNameFrame.add(nameTextField);
      enterNameFrame.add(new JLabel("Press 'Enter' when you're done"));
      enterNameFrame.setBounds(Game.windowWidth / 2 - 250, Game.windowHeight / 2 - 50, 500, 100);
      enterNameFrame.setVisible(true);
    }
  }

  private void write() {
    try {
      FileWriter fileWriter = new FileWriter(highScoresFile);
      for (HighScore highScore : highScores) {
        fileWriter.write(highScore.score() + " " + highScore.name() + "\n");
      }
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("Error writing names and high scores");
    }
  }

  void createTable() {
    int size = highScores.size();
    int nameX = Game.windowWidth / 2;
    int numberX = nameX - 400;
    int highScoreX = nameX + 400;
    for (int i = 0; i < size; i++) {
      int y = i * 60 + 210;
      Color color = i == newHighScoreIndex ? Color.red : Color.black;
      EZ.addText(numberX, y, i + 1 + ".", color, 40);
      HighScore highScore = highScores.get(i);
      EZ.addText(nameX, y, highScore.name(), color, 40);
      EZ.addText(highScoreX, y, highScore.score() + " Dads", color, 40);
    }
    for (int i = size; i < 10; i++) {
      EZ.addText(numberX, i * 60 + 210, i + 1 + ".", Color.black, 40);
    }
  }
}