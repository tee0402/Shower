import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Game {
  private final int windowWidth = 1915;
  private final int windowHeight = 995;
  private boolean startSceneShowing = true;
  private boolean menuSceneShowing = false;
  private boolean gameSceneShowing = false;
  private boolean endSceneShowing = false;
  private boolean highScoreSceneShowing = false;
  private boolean muted;
  private int score;
  private int highScore;
  private boolean scoreSaved;
  private int newHighScoreIndex;
  private String newHighScoreName;
  private final DadArrayList dad1ArrayList = new DadArrayList();
  private final DadArrayList dad2ArrayList = new DadArrayList();
  private final DadArrayList dad3ArrayList = new DadArrayList();
  private final Timer timer = new Timer();
  private final Soap soap = new Soap();
  private int randKid;
  private final EZSound titleMusic = EZ.addSound("TitleMusic.wav");
  private final EZSound battleMusic = EZ.addSound("BattleMusic.wav");
  private final EZSound endSong = EZ.addSound("EndSong.wav");
  private final EZSound Beep1 = EZ.addSound("Beep1.wav");
  private final EZSound Beep2 = EZ.addSound("Beep2.wav");
  private final EZSound Beep3 = EZ.addSound("Beep3.wav");
  private final ArrayList<String> alNames = new ArrayList<>();
  private final ArrayList<Integer> alHighScores = new ArrayList<>();
  private final File namesFile;
  private final File highScoresFile;
  private final File muteFile;
  private EZRectangle timeRect;
  private EZImage wasd;
  private EZImage mutedImage;
  private EZImage unmutedImage;

  @SuppressWarnings("ResultOfMethodCallIgnored")
  Game() {
    EZ.initialize(windowWidth, windowHeight);

    newHighScoreIndex = -1;
    try {
      namesFile = new File("names.txt");
      namesFile.createNewFile();
      Scanner sc = new Scanner(namesFile);
      while (sc.hasNextLine()) {
        alNames.add(sc.nextLine());
      }
      sc.close();

      highScoresFile = new File("highscores.txt");
      highScoresFile.createNewFile();
      sc = new Scanner(highScoresFile);
      while (sc.hasNextInt()) {
        alHighScores.add(sc.nextInt());
      }
      sc.close();
      highScore = alHighScores.size() == 0 ? 0 : alHighScores.get(0);

      muteFile = new File("mute.txt");
      muteFile.createNewFile();
      sc = new Scanner(muteFile);
      muted = sc.hasNextInt() && sc.nextInt() == 1;
      sc.close();
    } catch (IOException e) {
      System.out.println("Error scanning save files");
    }

    while (true) {
      if (startSceneShowing) {
        startScene();
      } else if (menuSceneShowing) {
        menuScene();
      } else if (gameSceneShowing) {
        gameScene();
      } else if (endSceneShowing) {
        endScene();
      } else if (highScoreSceneShowing) {
        highScoreScene();
      } else {
        System.exit(0);
      }
    }
  }

  private void addCorrectMutedImage() {
    if (muted) {
      mutedImage = EZ.addImage("muted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
    } else {
      unmutedImage = EZ.addImage("unmuted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
    }
  }

  private void checkIfMutedImageClicked(int mouseX, int mouseY, EZSound sceneMusic) {
    if (mutedImage != null && mutedImage.isPointInElement(mouseX, mouseY)) {
      muted = false;
      EZ.removeEZElement(mutedImage);
      mutedImage = null;
      unmutedImage = EZ.addImage("unmuted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
      if (sceneMusic != null) {
        sceneMusic.play();
      }
      try {
        FileWriter fw = new FileWriter(muteFile);
        fw.close();
      } catch (IOException e) {
        System.out.println("Error writing mute file");
      }
    } else if (unmutedImage != null && unmutedImage.isPointInElement(mouseX, mouseY)) {
      muted = true;
      EZ.removeEZElement(unmutedImage);
      unmutedImage = null;
      mutedImage = EZ.addImage("muted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
      if (sceneMusic != null) {
        sceneMusic.pause();
      }
      try {
        FileWriter fw = new FileWriter(muteFile);
        fw.write("1");
        fw.close();
      } catch (IOException e) {
        System.out.println("Error writing mute file");
      }
    }
  }

  private void startScene() {
    EZ.removeAllEZElements();

    EZ.addText("Impact", windowWidth / 2, windowHeight / 2 - 200, "Are you 18 or older?", Color.black, 50);
    EZRectangle yesRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2, 200, 100, Color.green, true);
    EZ.addText(windowWidth / 2, windowHeight / 2, "Yes", Color.white, 30);
    EZRectangle noRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2 + 150, 200, 100, Color.red, true);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 150, "No", Color.white, 30);
    addCorrectMutedImage();

    while (startSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (yesRect.isPointInElement(mouseX, mouseY)) {
          startSceneShowing = false;
          menuSceneShowing = true;
          if (!muted) {
            Beep3.play();
          }
        } else if (noRect.isPointInElement(mouseX, mouseY)) {
          startSceneShowing = false;
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, null);
        }
      }
    }
  }

  private void menuScene() {
    EZ.removeAllEZElements();
    if (!muted) {
      titleMusic.play();
    }
    EZ.setBackgroundColor(new Color(140,230,231));
    EZ.addImage("ShowerWithYourDadTitle.png", windowWidth / 2, 200);
    EZ.addImage("Simulator.png", windowWidth / 2, 300);
    EZ.addImage("2015.png", windowWidth / 2, 400);
    EZ.addImage("survive.png", windowWidth / 2, 500);
    EZ.addImage("kid1.png", 650, 400);
    EZ.addImage("dad1.png", 550, 375);
    EZ.addImage("kid3.png", 1240, 400);
    EZ.addImage("dad3.png", 1355, 375);
    EZImage StartButton = EZ.addImage("startImage.png", windowWidth / 6, 800);
    EZImage ExitButton = EZ.addImage("Exit.png", 5 * windowWidth / 6, 800);
    EZ.addImage("kid2.png", windowWidth / 2, 650);
    EZ.addImage("matchMe.png", 820, 570);
    EZ.addImage("orElse.png", 1110, 570);
    addCorrectMutedImage();

    while (menuSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (StartButton.isPointInElement(mouseX, mouseY)) {
          menuSceneShowing = false;
          gameSceneShowing = true;
          if (!muted) {
            Beep1.play();
          }
          titleMusic.stop();
        } else if (ExitButton.isPointInElement(mouseX, mouseY)) {
          menuSceneShowing = false;
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, titleMusic);
        }
      }
    }
  }

  private void gameScene() {
    EZ.removeAllEZElements();
    if (!muted) {
      battleMusic.play();
    }
    EZ.addImage("background.png", windowWidth / 2, windowHeight / 2);
    wasd = EZ.addImage("wasd.png", windowWidth / 2, 600);
    score = 0;
    EZText scoreText = EZ.addText(windowWidth / 2, 80, String.valueOf(score), Color.black, 70);
    EZText soapText = EZ.addText(windowWidth / 2, 200, "", Color.red, 120);
    EZ.addText(windowWidth / 2, 130, "High: " + highScore, Color.black, 30);
    addCorrectMutedImage();

    timer.resetDecrement();
    while (gameSceneShowing) {
      EZ.refreshScreen();
      // Generate random coordinates and place the three dads there
      boolean dadsXGenerated = false;
      boolean dadsYGenerated = false;
      int dad1X, dad2X, dad3X, dad1Y, dad2Y, dad3Y;
      dad1X = dad2X = dad3X = dad1Y = dad2Y = dad3Y = 0;
      Random random = new Random();
      while (!dadsXGenerated) {
        dad1X = random.nextInt(windowWidth - 100) + 50;
        dad2X = random.nextInt(windowWidth - 100) + 50;
        dad3X = random.nextInt(windowWidth - 100) + 50;
        if (dad1X != dad2X && dad2X != dad3X && dad1X != dad3X && (dad1X < windowWidth / 2 - 200 || dad1X > windowWidth / 2 + 200) && (dad2X < windowWidth / 2 - 200 || dad2X > windowWidth / 2 + 200) && (dad3X < windowWidth / 2 - 200 || dad3X > windowWidth / 2 + 200)) {
          dadsXGenerated = true;
        }
      }
      while (!dadsYGenerated) {
        dad1Y = random.nextInt(windowHeight - 400) + 300;
        dad2Y = random.nextInt(windowHeight - 400) + 300;
        dad3Y = random.nextInt(windowHeight - 400) + 300;
        if (dad1Y != dad2Y && dad2Y != dad3Y && dad1Y != dad3Y) {
          dadsYGenerated = true;
        }
      }
      dad1ArrayList.add(new Dad("dad1.png", dad1X, dad1Y));
      dad2ArrayList.add(new Dad("dad2.png", dad2X, dad2Y));
      dad3ArrayList.add(new Dad("dad3.png", dad3X, dad3Y));

      if (soap.soapMode) {
        soapText.setMsg("SOAP FRENZY");
      } else {
        randKid = random.nextInt(3) + 1;
        soap.randomizedAppear();
        soapText.setMsg("");
      }

      timer.start();
      timeRect = EZ.addRectangle(windowWidth / 2, 30, (int) (450 * timer.timeLeft()), 25, Color.black, true);
      scoreText.setMsg(String.valueOf(score));

      switch (randKid) {
        case 1:
          kidGame("kid1.png", dad1ArrayList, dad2ArrayList, dad3ArrayList);
          break;
        case 2:
          kidGame("kid2.png", dad2ArrayList, dad1ArrayList, dad3ArrayList);
          break;
        case 3:
          kidGame("kid3.png", dad3ArrayList, dad1ArrayList, dad2ArrayList);
          break;
      }

      EZ.removeEZElement(timeRect);
      if (soap.soapAppeared) {
        soap.remove();
      }
    }
    scoreSaved = false;
  }

  private void kidGame(String kidImageFile, DadArrayList myDads, DadArrayList otherDads1, DadArrayList otherDads2) {
    Kid kid = new Kid(kidImageFile, windowWidth / 2, 800);

    while (true) {
      timeRect.setWidth((int) (450 * timer.timeLeft()));

      kid.controlIt();

      if (wasd != null && (EZInteraction.isKeyDown('w') || EZInteraction.isKeyDown('a') || EZInteraction.isKeyDown('s') || EZInteraction.isKeyDown('d'))) {
        EZ.removeEZElement(wasd);
        wasd = null;
      }

      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      int kidX = kid.getX();
      int kidY = kid.getY();
      double timeLeft = timer.timeLeft();
      if (timeLeft <= 0 || otherDads1.isPointInDads(kidX, kidY) || otherDads2.isPointInDads(kidX, kidY)) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        if (soap.soapMode) {
          soap.soapMode = false;
        } else {
          gameSceneShowing = false;
          endSceneShowing = true;
          battleMusic.stop();
        }
        if (!muted && timeLeft > 0) {
          Beep3.play();
        }
        break;
      } else if (myDads.isPointInDads(kidX, kidY)) {
        kid.remove();
        myDads.removeDads();
        if (soap.soapMode) {
          score += 2;
        } else {
          otherDads1.removeDads();
          otherDads2.removeDads();
          score++;
        }
        if (!muted) {
          Beep2.play();
        }
        timer.decrement();
        break;
      } else if (soap.soapAppeared && soap.isPointInSoap(kidX, kidY)) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        soap.soapMode = true;
        if (!muted) {
          Beep2.play();
        }
        break;
      } else if (EZInteraction.wasMouseLeftButtonPressed()) {
        checkIfMutedImageClicked(mouseX, mouseY, battleMusic);
      }
      EZ.refreshScreen();
    }
  }

  private void endScene() {
    EZ.removeAllEZElements();

    if (!scoreSaved) {
      saveScore();
    }

    EZ.addImage("ayyyyy.png", windowWidth / 2, 200);
    EZRectangle highScoreRect = EZ.addRectangle(4 * windowWidth / 5, windowHeight / 2, 400, 100, Color.blue, true);
    EZ.addText(4 * windowWidth / 5, windowHeight / 2, "Top 10 High Scores", Color.white, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 100, "High Score: " + highScore + " Dads", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2, "You got " + score + " Dads.", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 300, "Press space to retry", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 400, "Press M to return to menu", Color.black, 40);
    addCorrectMutedImage();

    while (endSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.isKeyDown(' ')) {
        endSceneShowing = false;
        gameSceneShowing = true;
        if (!muted) {
          Beep1.play();
        }
        endSong.stop();
      } else if (EZInteraction.isKeyDown('m')){
        endSceneShowing = false;
        menuSceneShowing = true;
        if (!muted) {
          Beep1.play();
        }
        endSong.stop();
      } else if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (highScoreRect.isPointInElement(mouseX, mouseY)) {
          endSceneShowing = false;
          highScoreSceneShowing = true;
          if (!muted) {
            Beep1.play();
          }
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
    }
  }

  private void saveScore() {
    if (!muted) {
      endSong.play();
    }
    if (score > highScore) {
      highScore = score;
    }
    // Remove previous high score highlight
    newHighScoreIndex = -1;

    int numHighScores = alHighScores.size();
    // Show enter name prompt if new high score
    if (score > 0 && (numHighScores < 10 || score > alHighScores.get(numHighScores - 1))) {
      JFrame enterNameFrame = new JFrame("You got a top 10 high score!");
      enterNameFrame.setLayout(new FlowLayout());
      enterNameFrame.add(new JLabel("Please enter your name:"));
      JTextField nameTextField = new JTextField(30);
      nameTextField.addActionListener(e -> {
        newHighScoreName = nameTextField.getText();
        // Find new high score index
        if (numHighScores == 0) {
          alNames.add(newHighScoreName);
          alHighScores.add(score);
          newHighScoreIndex = 0;
        } else if (numHighScores < 10 && score <= alHighScores.get(numHighScores - 1)) {
          alNames.add(newHighScoreName);
          alHighScores.add(score);
          newHighScoreIndex = alHighScores.size() - 1;
        } else {
          for (int i = 0; i < numHighScores; i++) {
            if (score > alHighScores.get(i)) {
              newHighScoreIndex = i;
              alNames.add(i, newHighScoreName);
              alHighScores.add(i, score);
              if (alHighScores.size() == 11) {
                alNames.remove(10);
                alHighScores.remove(10);
              }
              break;
            }
          }
        }
        // Write updated names and high scores to files
        try {
          FileWriter fw = new FileWriter(namesFile);
          for (String name : alNames) {
            fw.write(name + "\n");
          }
          fw.close();
          fw = new FileWriter(highScoresFile);
          for (int highScore : alHighScores) {
            fw.write(highScore + " ");
          }
          fw.close();
        } catch (IOException ex) {
          System.out.println("Error writing names and high scores");
        }
        enterNameFrame.dispose();
      });
      enterNameFrame.add(nameTextField);
      enterNameFrame.add(new JLabel("Press 'Enter' when you're done"));
      enterNameFrame.setBounds(windowWidth / 2 - 250, windowHeight / 2 - 50, 500, 100);
      enterNameFrame.setVisible(true);
    }

    scoreSaved = true;
  }

  private void highScoreScene() {
    EZ.removeAllEZElements();
    EZ.addText(windowWidth / 2, windowHeight / 10, "Hall of Dads", Color.black, 90);
    EZRectangle backRect = EZ.addRectangle(windowWidth / 2, 9 * windowHeight / 10, 150, 70, Color.blue, true);
    EZ.addText(windowWidth / 2, 9 * windowHeight / 10, "Back", Color.white, 40);
    for (int i = 0; i < 10; i++) {
      EZ.addText(windowWidth / 2 - 400, i * 60 + 210, i + 1 + ".", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    for (int i = 0; i < alNames.size(); i++) {
      EZ.addText(windowWidth / 2, i * 60 + 210, alNames.get(i), i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    for (int i = 0; i < alHighScores.size(); i++) {
      EZ.addText(windowWidth / 2 + 400, i * 60 + 210, alHighScores.get(i) + " Dads", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    addCorrectMutedImage();

    while (highScoreSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (backRect.isPointInElement(mouseX, mouseY)) {
          highScoreSceneShowing = false;
          endSceneShowing = true;
          if (!muted) {
            Beep1.play();
          }
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
    }
  }
}
