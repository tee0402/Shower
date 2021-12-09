import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
  private final int windowWidth = 1915;
  private final int windowHeight = 995;
  private boolean start = true;
  private boolean menu = false;
  private boolean end = false;
  private boolean highScores = false;
  private boolean muted;
  private int score;
  private int highScore;
  private boolean scoreSaved;
  private int newHighScoreIndex;
  private final DadArrayList dadAl1 = new DadArrayList();
  private final DadArrayList dadAl2 = new DadArrayList();
  private final DadArrayList dadAl3 = new DadArrayList();
  private final Timer timer = new Timer();
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
  public Game() {
    EZ.initialize(windowWidth, windowHeight);

    newHighScoreIndex = -1;
    try {
      //creates a new scanner that reads the names
      namesFile = new File("names.txt");
      namesFile.createNewFile();
      Scanner sc = new Scanner(namesFile);
      while (sc.hasNext()) {
        alNames.add(sc.nextLine());
      }
      sc.close();
      //creates a new scanner that scans highscores.txt
      highScoresFile = new File("highscores.txt");
      highScoresFile.createNewFile();
      sc = new Scanner(highScoresFile);
      while (sc.hasNext()) {
        alHighScores.add(sc.nextInt());
      }
      sc.close();
      if (alHighScores.size() == 0) {
        alHighScores.add(0);
        highScore = 0;
      } else {
        highScore = alHighScores.get(0);
      }
      //creates a new scanner that reads mute.txt
      muteFile = new File("mute.txt");
      muteFile.createNewFile();
      sc = new Scanner(muteFile);
      muted = sc.hasNext() && sc.nextInt() == 1;
      sc.close();
    } catch (IOException e) {
      System.out.println("Error scanning save files");
    }

    while (true) {
      if (start) {
        startScene();
      } else if (menu) {
        menuScene();
      } else if (!end && !highScores) {
        gameScene();
      } else if (end) {
        endScene();
      } else {
        highScoreScene();
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
    try {
      if (mutedImage != null && mutedImage.isPointInElement(mouseX, mouseY)) {
        muted = false;
        EZ.removeEZElement(mutedImage);
        mutedImage = null;
        unmutedImage = EZ.addImage("unmuted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
        if (sceneMusic != null) {
          sceneMusic.play();
        }
        FileWriter fw = new FileWriter(muteFile);
        fw.close();
      } else if (unmutedImage != null && unmutedImage.isPointInElement(mouseX, mouseY)) {
        muted = true;
        EZ.removeEZElement(unmutedImage);
        unmutedImage = null;
        mutedImage = EZ.addImage("muted.png", 29 * windowWidth / 30, 14 * windowHeight / 15);
        if (sceneMusic != null) {
          sceneMusic.pause();
        }
        FileWriter fw = new FileWriter(muteFile);
        fw.write("1");
        fw.close();
      }
    } catch (IOException e) {
      System.out.println("Error writing mute file");
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

    while (start) {
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (yesRect.isPointInElement(mouseX, mouseY)) {
          menu = true;								//set menu to true and start to false
          start = false;
          if (!muted) {								//if it is not muted, play beep3
            Beep3.play();
          }
        } else if (noRect.isPointInElement(mouseX, mouseY)) {
          System.exit(0);
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, null);
        }
      }
      EZ.refreshScreen();
    }
  }

  private void menuScene() {
    EZ.removeAllEZElements();
    endSong.stop();
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

    while (menu) {
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      //if startbutton is pressed, set menu to false and if it is not muted, play beep1
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (StartButton.isPointInElement(mouseX, mouseY)) {
          menu = false;
          if (!muted) {
            Beep1.play();
          }
        } else if (ExitButton.isPointInElement(mouseX, mouseY)) {	//if exitbutton is pressed, exit
          System.exit(0);
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, titleMusic);
        }
      }
      EZ.refreshScreen();
    }
  }

  private void gameScene() {
    EZ.removeAllEZElements();
    //if start, end, menu, and highscore is false, remove all elements, stop endsong and titlelmusic then play battlemusic
    endSong.stop();
    titleMusic.stop();
    if (!muted) {
      battleMusic.play();
    }
    score = 0;
    boolean wasdShowing = true;
    timer.reset();
    //add the images for the main game
    EZ.addImage("background.png", windowWidth / 2, windowHeight / 2);
    wasd = EZ.addImage("wasd.png", windowWidth / 2, 600);
    EZText scoreText = EZ.addText(windowWidth / 2, 80, String.valueOf(score), Color.black, 70);
    EZText soapText = EZ.addText(windowWidth / 2, 200, "", Color.red, 120);
    EZ.addText(windowWidth / 2, 130, "High: " + highScore, Color.black, 30);
    addCorrectMutedImage();

    while (!end) {
      boolean dadsXGenerated = false;
      boolean dadsYGenerated = false;
      int dad1x, dad2x, dad3x, dad1y, dad2y, dad3y;
      dad1x = dad2x = dad3x = dad1y = dad2y = dad3y = 0;
      Random random = new Random();
      //while dad x and y generated is false, generate the x and y integers for all 3 dads
      while (!dadsXGenerated) {
        dad1x = random.nextInt(windowWidth - 100) + 50;
        dad2x = random.nextInt(windowWidth - 100) + 50;
        dad3x = random.nextInt(windowWidth - 100) + 50;
        if (dad1x != dad2x && dad2x != dad3x && dad1x != dad3x && (dad1x < windowWidth / 2 - 200 || dad1x > windowWidth / 2 + 200) && (dad2x < windowWidth / 2 - 200 || dad2x > windowWidth / 2 + 200) && (dad3x < windowWidth / 2 - 200 || dad3x > windowWidth / 2 + 200)) {
          dadsXGenerated = true;
        }
      }
      while (!dadsYGenerated) {
        dad1y = random.nextInt(windowHeight - 400) + 300;
        dad2y = random.nextInt(windowHeight - 400) + 300;
        dad3y = random.nextInt(windowHeight - 400) + 300;
        if (dad1y != dad2y && dad2y != dad3y && dad1y != dad3y) {
          dadsYGenerated = true;
        }
      }

      //place the 3 dads at the randomly generated coordinates
      dadAl1.addDad(new Dad("dad1.png", dad1x, dad1y));
      dadAl2.addDad(new Dad("dad2.png", dad2x, dad2y));
      dadAl3.addDad(new Dad("dad3.png", dad3x, dad3y));

      if (Soap.soapMode) {
        soapText.setMsg("SOAP FRENZY");
      } else {
        randKid = random.nextInt(3) + 1;
        Soap.randomizedAppear();
        soapText.setMsg("");
      }

      if (wasdShowing) {
        wasd.pullToFront();
        wasdShowing = false;
      }
      //start the timer and add a timer bar
      timer.start();
      timeRect = EZ.addRectangle(windowWidth / 2, 30, (int) (450 * timer.timeLeft()), 25, Color.black, true);
      scoreText.setMsg(String.valueOf(score));

      switch (randKid) {
        //if case 1, creates a new kid1 and start the timer. then control the kid with wasd. if dad2 and dad3 is point in element, set end to true
        //if kid1 is point in soap, start soapmode
        //if kid1 is point in dad1, remove all dads and generate new ones and generate a new kid
        //if kid1 is point in dad1, decrement timer
        case 1:
          kidGame("kid1.png", dadAl1, dadAl2, dadAl3);
          break;
        case 2:
          kidGame("kid2.png", dadAl2, dadAl1, dadAl3);
          break;
        case 3:
          kidGame("kid3.png", dadAl3, dadAl1, dadAl2);
          break;
      }

      EZ.removeEZElement(timeRect);
      Soap.remove();
      EZ.refreshScreen();
    }
    scoreSaved = false;
  }

  private void kidGame(String kidImageFile, DadArrayList myDads, DadArrayList otherDads1, DadArrayList otherDads2) {
    Kid kid = new Kid(kidImageFile, windowWidth / 2, 800);

    while (true) {
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();

      timeRect.setWidth((int) (450 * timer.timeLeft()));

      kid.ControlIt();

      if ((EZInteraction.isKeyDown('w') || EZInteraction.isKeyDown('a') || EZInteraction.isKeyDown('s') || EZInteraction.isKeyDown('d')) && wasd.isShowing()) {
        EZ.removeEZElement(wasd);
      }

      if (!Soap.soapMode && myDads.isPointInDads(kid)) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        score++;
        if (!muted) {
          Beep2.play();
        }
        timer.decrement();
        break;
      } else if (!Soap.soapMode && (otherDads1.isPointInDads(kid) || otherDads2.isPointInDads(kid))) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        end = true;
        if (!muted) {
          Beep3.play();
        }
        break;
      } else if (!Soap.soapMode && timer.timeLeft() <= 0) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        end = true;
        break;
      } else if (!Soap.soapMode && Soap.soapAppeared && Soap.isPointInSoap(kid)) {
        Soap.soapMode = true;
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        if (!muted) {
          Beep2.play();
        }
        timer.decrement();
        break;
      } else if (Soap.soapMode && myDads.isPointInDads(kid)) {
        kid.remove();
        myDads.removeDads();
        score += 2;
        if (!muted) {
          Beep2.play();
        }
        timer.decrement();
        break;
      } else if (Soap.soapMode && (otherDads1.isPointInDads(kid) || otherDads2.isPointInDads(kid))) {
        Soap.soapMode = false;
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        if (!muted) {
          Beep3.play();
        }
        break;
      } else if (Soap.soapMode && timer.timeLeft() <= 0) {
        Soap.soapMode = false;
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
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

    //end of game menu
    EZ.addImage("ayyyyy.png", windowWidth / 2, 200);
    EZRectangle highScoreRect = EZ.addRectangle(4 * windowWidth / 5, windowHeight / 2, 400, 100, Color.blue, true);
    EZ.addText(4 * windowWidth / 5, windowHeight / 2, "Top 10 High Scores", Color.white, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 100, "High Score: " + highScore + " Dads", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2, "You got " + score + " Dads.", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 300, "Press space to retry", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 400, "Press M to return to menu", Color.black, 40);
    addCorrectMutedImage();

    while (end) {
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.isKeyDown(' ')) {
        end = false;
        if (!muted) {
          Beep1.play();
        }
      } else if (EZInteraction.isKeyDown('m')){
        end = false;
        menu = true;
        if (!muted) {
          Beep1.play();
        }
      }
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (highScoreRect.isPointInElement(mouseX, mouseY)) {
          highScores = true;
          end = false;
          if (!muted) {
            Beep1.play();
          }
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
      EZ.refreshScreen();
    }
  }

  private void saveScore() {
    battleMusic.stop();
    if (!muted) {
      endSong.play();
    }
    if (score > highScore) {
      highScore = score;
    }

    for (int i = 0; i < alHighScores.size(); i++) {
      // Find new highscore index and create a new textfield.
      if (score > alHighScores.get(i)) {
        new EnterTextField();

        try {
          while (!EnterTextField.done) {
            Thread.sleep(800);
          }
        } catch (InterruptedException e) {
          System.out.println("Error waiting for name input");
        }

        newHighScoreIndex = i;
        if (alHighScores.size() < 10) {
          alHighScores.add(i, score);
          alNames.add(i, EnterTextField.name);
        } else if (alNames.size() < 10) {
          alHighScores.add(i, score);
          alHighScores.remove(10);
          alNames.add(i, EnterTextField.name);
        } else {
          alHighScores.add(i, score);
          alHighScores.remove(10);
          alNames.add(i, EnterTextField.name);
          alNames.remove(10);
        }
        try {
          FileWriter fw = new FileWriter(namesFile);
          for (int j = 0; j < alNames.size() && j < 10; j++) {
            fw.write(alNames.get(j) + "\n");
          }
          fw.close();
          fw = new FileWriter(highScoresFile);
          for (int j = 0; j < alHighScores.size() && j < 10; j++) {
            fw.write(alHighScores.get(j) + " ");
          }
          fw.close();
        } catch (IOException e) {
          System.out.println("Error writing names and highscores");
        }
        break;
      }
    }
    scoreSaved = true;
  }

  private void highScoreScene() {
    EZ.removeAllEZElements();
    EZ.addText(windowWidth / 2, windowHeight / 10, "Hall of Dads", Color.black, 90);
    EZRectangle backRect = EZ.addRectangle(windowWidth / 2, 9 * windowHeight / 10, 150, 70, Color.blue, true);
    EZ.addText(windowWidth / 2, 9 * windowHeight / 10, "Back", Color.white, 40);
    //if requirements are met, write text
    for (int i = 0; i < 10; i++) {
      if (i == newHighScoreIndex) {
        EZ.addText(windowWidth / 2 - 400, i * 60 + 210, i + 1 + ".", Color.red, 40);
      } else {
        EZ.addText(windowWidth / 2 - 400, i * 60 + 210, i + 1 + ".", Color.black, 40);
      }
    }
    //if requirements are met, write text
    for (int i = 0; i < alNames.size(); i++) {
      if (alHighScores.get(i) != 0) {
        if (i == newHighScoreIndex) {
          EZ.addText(windowWidth / 2, i * 60 + 210, alNames.get(i), Color.red, 40);
        } else {
          EZ.addText(windowWidth / 2, i * 60 + 210, alNames.get(i), Color.black, 40);
        }
      }
    }
    //if requirements are met
    for (int i = 0; i < alHighScores.size(); i++) {
      if (alHighScores.get(i) != 0) {
        if (i == newHighScoreIndex) {
          EZ.addText(windowWidth / 2 + 400, i * 60 + 210, alHighScores.get(i) + " Dads", Color.red, 40);
        } else {
          EZ.addText(windowWidth / 2 + 400, i * 60 + 210, alHighScores.get(i) + " Dads", Color.black, 40);
        }
      }
    }
    addCorrectMutedImage();

    while (highScores) {
      //if backrect is pressed set highscores to false and end to true
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (backRect.isPointInElement(mouseX, mouseY)) {
          highScores = false;
          end = true;
          if (!muted) {
            Beep1.play();
          }
        } else {
          checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
      EZ.refreshScreen();
    }
  }
}
