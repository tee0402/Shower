import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Game {
  static final int windowWidth = 1915;
  static final int windowHeight = 995;
  private boolean startSceneShowing = true;
  private boolean menuSceneShowing = false;
  private boolean gameSceneShowing = false;
  private boolean endSceneShowing = false;
  private boolean highScoreSceneShowing = false;
  private final HighScores highScores;
  private final Mute mute;
  private int score;
  private boolean scoreSaved;
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
  private EZRectangle timeRect;
  private EZImage wasd;

  Game() {
    EZ.initialize(windowWidth, windowHeight);
    highScores = new HighScores();
    mute = new Mute();

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

  private void startScene() {
    EZ.removeAllEZElements();

    EZ.addText("Impact", windowWidth / 2, windowHeight / 2 - 200, "Are you 18 or older?", Color.black, 50);
    EZRectangle yesRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2, 200, 100, Color.green, true);
    EZ.addText(windowWidth / 2, windowHeight / 2, "Yes", Color.white, 30);
    EZRectangle noRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2 + 150, 200, 100, Color.red, true);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 150, "No", Color.white, 30);
    mute.addCorrectMutedImage();

    while (startSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (yesRect.isPointInElement(mouseX, mouseY)) {
          startSceneShowing = false;
          menuSceneShowing = true;
          if (mute.isUnmuted()) {
            Beep3.play();
          }
        } else if (noRect.isPointInElement(mouseX, mouseY)) {
          startSceneShowing = false;
        } else {
          mute.checkIfMutedImageClicked(mouseX, mouseY, null);
        }
      }
    }
  }

  private void menuScene() {
    EZ.removeAllEZElements();
    if (mute.isUnmuted()) {
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
    mute.addCorrectMutedImage();

    while (menuSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (StartButton.isPointInElement(mouseX, mouseY)) {
          menuSceneShowing = false;
          gameSceneShowing = true;
          if (mute.isUnmuted()) {
            Beep1.play();
          }
          titleMusic.stop();
        } else if (ExitButton.isPointInElement(mouseX, mouseY)) {
          menuSceneShowing = false;
        } else {
          mute.checkIfMutedImageClicked(mouseX, mouseY, titleMusic);
        }
      }
    }
  }

  private void gameScene() {
    EZ.removeAllEZElements();
    if (mute.isUnmuted()) {
      battleMusic.play();
    }
    EZ.addImage("background.png", windowWidth / 2, windowHeight / 2);
    wasd = EZ.addImage("wasd.png", windowWidth / 2, 600);
    score = 0;
    EZText scoreText = EZ.addText(windowWidth / 2, 80, String.valueOf(score), Color.black, 70);
    EZText soapText = EZ.addText(windowWidth / 2, 200, "", Color.red, 120);
    EZ.addText(windowWidth / 2, 130, "High: " + highScores.getHighScore(), Color.black, 30);
    mute.addCorrectMutedImage();

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
        if (mute.isUnmuted() && timeLeft > 0) {
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
        if (mute.isUnmuted()) {
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
        if (mute.isUnmuted()) {
          Beep2.play();
        }
        break;
      } else if (EZInteraction.wasMouseLeftButtonPressed()) {
        mute.checkIfMutedImageClicked(mouseX, mouseY, battleMusic);
      }
      EZ.refreshScreen();
    }
  }

  private void endScene() {
    EZ.removeAllEZElements();

    if (!scoreSaved) {
      if (mute.isUnmuted()) {
        endSong.play();
      }
      highScores.checkHighScore(score);
      scoreSaved = true;
    }

    EZ.addImage("ayyyyy.png", windowWidth / 2, 200);
    EZRectangle highScoreRect = EZ.addRectangle(4 * windowWidth / 5, windowHeight / 2, 400, 100, Color.blue, true);
    EZ.addText(4 * windowWidth / 5, windowHeight / 2, "Top 10 High Scores", Color.white, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 100, "High Score: " + highScores.getHighScore() + " Dads", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2, "You got " + score + " Dads.", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 300, "Press space to retry", Color.black, 40);
    EZ.addText(windowWidth / 2, windowHeight / 2 + 400, "Press M to return to menu", Color.black, 40);
    mute.addCorrectMutedImage();

    while (endSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.isKeyDown(' ')) {
        endSceneShowing = false;
        gameSceneShowing = true;
        if (mute.isUnmuted()) {
          Beep1.play();
        }
        endSong.stop();
      } else if (EZInteraction.isKeyDown('m')){
        endSceneShowing = false;
        menuSceneShowing = true;
        if (mute.isUnmuted()) {
          Beep1.play();
        }
        endSong.stop();
      } else if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (highScoreRect.isPointInElement(mouseX, mouseY)) {
          endSceneShowing = false;
          highScoreSceneShowing = true;
          if (mute.isUnmuted()) {
            Beep1.play();
          }
        } else {
          mute.checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
    }
  }

  private void highScoreScene() {
    EZ.removeAllEZElements();
    EZ.addText(windowWidth / 2, windowHeight / 10, "Hall of Dads", Color.black, 90);
    EZRectangle backRect = EZ.addRectangle(windowWidth / 2, 9 * windowHeight / 10, 150, 70, Color.blue, true);
    EZ.addText(windowWidth / 2, 9 * windowHeight / 10, "Back", Color.white, 40);
    int newHighScoreIndex = highScores.getNewHighScoreIndex();
    for (int i = 0; i < 10; i++) {
      EZ.addText(windowWidth / 2 - 400, i * 60 + 210, i + 1 + ".", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    ArrayList<String> namesList = highScores.getNames();
    for (int i = 0; i < namesList.size(); i++) {
      EZ.addText(windowWidth / 2, i * 60 + 210, namesList.get(i), i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    ArrayList<Integer> highScoresList = highScores.getHighScores();
    for (int i = 0; i < highScoresList.size(); i++) {
      EZ.addText(windowWidth / 2 + 400, i * 60 + 210, highScoresList.get(i) + " Dads", i == newHighScoreIndex ? Color.red : Color.black, 40);
    }
    mute.addCorrectMutedImage();

    while (highScoreSceneShowing) {
      EZ.refreshScreen();
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (backRect.isPointInElement(mouseX, mouseY)) {
          highScoreSceneShowing = false;
          endSceneShowing = true;
          if (mute.isUnmuted()) {
            Beep1.play();
          }
        } else {
          mute.checkIfMutedImageClicked(mouseX, mouseY, endSong);
        }
      }
    }
  }
}
