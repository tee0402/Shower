import java.awt.*;
import java.util.Random;

class Game {
  static final int windowWidth = 1915;
  static final int windowHeight = 995;
  private boolean startSceneShowing = true;
  private boolean menuSceneShowing = false;
  private boolean gameSceneShowing = false;
  private boolean endSceneShowing = false;
  private boolean highScoreSceneShowing = false;
  private final EndScene endScene = new EndScene();
  private final Mute mute = new Mute();
  private final EZSound endSong = EZ.addSound("resources/EndSong.wav");
  private final EZSound beep1 = EZ.addSound("resources/Beep1.wav");
  private final EZSound beep3 = EZ.addSound("resources/Beep3.wav");
  private final HighScores highScores = new HighScores();

  Game() {
    EZ.initialize(windowWidth, windowHeight);
    MenuScene menuScene = new MenuScene();
    GameScene gameScene = new GameScene();
    HighScoreScene highScoreScene = new HighScoreScene();
    while (true) {
      EZ.removeAllEZElements();
      mute.addCorrectMutedImage();
      if (startSceneShowing) {
        new StartScene().show();
      } else if (menuSceneShowing) {
        menuScene.show();
      } else if (gameSceneShowing) {
        gameScene.show();
      } else if (endSceneShowing) {
        endScene.show();
      } else if (highScoreSceneShowing) {
        highScoreScene.show();
      } else {
        System.exit(0);
      }
    }
  }

  private class StartScene {
    private void show() {
      EZ.addText("Impact", windowWidth / 2, windowHeight / 2 - 200, "Are you 18 or older?", Color.black, 50);
      EZRectangle yesRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2, 200, 100, Color.green, true);
      EZ.addText(windowWidth / 2, windowHeight / 2, "Yes", Color.white, 30);
      EZRectangle noRect = EZ.addRectangle(windowWidth / 2, windowHeight / 2 + 150, 200, 100, Color.red, true);
      EZ.addText(windowWidth / 2, windowHeight / 2 + 150, "No", Color.white, 30);

      while (startSceneShowing) {
        EZ.refreshScreen();
        int mouseX = EZInteraction.getXMouse();
        int mouseY = EZInteraction.getYMouse();
        if (EZInteraction.wasMouseLeftButtonPressed()) {
          if (yesRect.isPointInElement(mouseX, mouseY)) {
            mute.playIfUnmuted(beep3);
            startSceneShowing = false;
            menuSceneShowing = true;
          } else if (noRect.isPointInElement(mouseX, mouseY)) {
            startSceneShowing = false;
          } else {
            mute.checkIfMutedImageClicked(mouseX, mouseY, null);
          }
        }
      }
    }
  }

  private class MenuScene {
    private final EZSound titleMusic = EZ.addSound("resources/TitleMusic.wav");

    private void show() {
      mute.playIfUnmuted(titleMusic);
      EZ.setBackgroundColor(new Color(140,230,231));
      EZ.addImage("resources/ShowerWithYourDadTitle.png", windowWidth / 2, 200);
      EZ.addImage("resources/Simulator.png", windowWidth / 2, 300);
      EZ.addImage("resources/2015.png", windowWidth / 2, 400);
      EZ.addImage("resources/survive.png", windowWidth / 2, 500);
      EZ.addImage("resources/kid1.png", 650, 400);
      EZ.addImage("resources/dad1.png", 550, 375);
      EZ.addImage("resources/kid3.png", 1240, 400);
      EZ.addImage("resources/dad3.png", 1355, 375);
      EZImage StartButton = EZ.addImage("resources/startImage.png", windowWidth / 6, 800);
      EZImage ExitButton = EZ.addImage("resources/Exit.png", 5 * windowWidth / 6, 800);
      EZ.addImage("resources/kid2.png", windowWidth / 2, 650);
      EZ.addImage("resources/matchMe.png", 820, 570);
      EZ.addImage("resources/orElse.png", 1110, 570);

      while (menuSceneShowing) {
        EZ.refreshScreen();
        int mouseX = EZInteraction.getXMouse();
        int mouseY = EZInteraction.getYMouse();
        if (EZInteraction.wasMouseLeftButtonPressed()) {
          if (StartButton.isPointInElement(mouseX, mouseY)) {
            mute.playIfUnmuted(beep1);
            titleMusic.stop();
            menuSceneShowing = false;
            gameSceneShowing = true;
          } else if (ExitButton.isPointInElement(mouseX, mouseY)) {
            menuSceneShowing = false;
          } else {
            mute.checkIfMutedImageClicked(mouseX, mouseY, titleMusic);
          }
        }
      }
    }
  }

  private class GameScene {
    private int score;
    private EZImage wasd;
    private EZRectangle timeRect;
    private final Timer timer = new Timer();
    private final Soap soap = new Soap();
    private int randKid;
    private final Dads dads1 = new Dads();
    private final Dads dads2 = new Dads();
    private final Dads dads3 = new Dads();
    private final EZSound battleMusic = EZ.addSound("resources/BattleMusic.wav");
    private final EZSound beep2 = EZ.addSound("resources/Beep2.wav");

    private void show() {
      mute.playIfUnmuted(battleMusic);
      EZImage background = EZ.addImage("resources/background.png", windowWidth / 2, windowHeight / 2);
      background.pushToBack();
      wasd = EZ.addImage("resources/wasd.png", windowWidth / 2, 600);
      score = 0;
      EZText scoreText = EZ.addText(windowWidth / 2, 80, String.valueOf(score), Color.black, 70);
      EZText soapText = EZ.addText(windowWidth / 2, 200, "", Color.red, 120);
      EZ.addText(windowWidth / 2, 130, "High: " + highScores.getHighScore(), Color.black, 30);

      timer.resetDecrement();
      while (gameSceneShowing) {
        EZ.refreshScreen();

        Random random = new Random();
        if (soap.isSoapMode()) {
          soapText.setMsg("SOAP FRENZY");
        } else {
          randKid = random.nextInt(3) + 1;
          soap.randomizedAppear();
          soapText.setMsg("");
        }
        while (true) {
          int dad1X = (random.nextBoolean() ? 50 : windowWidth / 2 + 200) + random.nextInt(windowWidth / 2 - 250);
          int dad2X = (random.nextBoolean() ? 50 : windowWidth / 2 + 200) + random.nextInt(windowWidth / 2 - 250);
          int dad3X = (random.nextBoolean() ? 50 : windowWidth / 2 + 200) + random.nextInt(windowWidth / 2 - 250);
          int dad1Y = random.nextInt(windowHeight - 400) + 300;
          int dad2Y = random.nextInt(windowHeight - 400) + 300;
          int dad3Y = random.nextInt(windowHeight - 400) + 300;
          if (dad1X != dad2X && dad2X != dad3X && dad1X != dad3X && dad1Y != dad2Y && dad2Y != dad3Y && dad1Y != dad3Y) {
            dads1.add("resources/dad1.png", dad1X, dad1Y);
            dads2.add("resources/dad2.png", dad2X, dad2Y);
            dads3.add("resources/dad3.png", dad3X, dad3Y);
            break;
          }
        }

        timer.start();
        timeRect = EZ.addRectangle(windowWidth / 2, 30, (int) (450 * timer.timeLeft()), 25, Color.black, true);
        scoreText.setMsg(String.valueOf(score));

        switch (randKid) {
          case 1:
            gameSceneShowing = kidGame("resources/kid1.png", dads1, dads2, dads3);
            break;
          case 2:
            gameSceneShowing = kidGame("resources/kid2.png", dads2, dads1, dads3);
            break;
          case 3:
            gameSceneShowing = kidGame("resources/kid3.png", dads3, dads1, dads2);
            break;
        }

        EZ.removeEZElement(timeRect);
      }
      battleMusic.stop();
      endSceneShowing = true;
      endScene.save(score);
    }

    private boolean kidGame(String kidImageFile, Dads myDads, Dads otherDads1, Dads otherDads2) {
      Kid kid = new Kid(kidImageFile, windowWidth / 2, 800);
      while (true) {
        timeRect.setWidth((int) (450 * timer.timeLeft()));

        kid.control();

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
          myDads.removeAll();
          otherDads1.removeAll();
          otherDads2.removeAll();
          if (timeLeft > 0) {
            mute.playIfUnmuted(beep3);
          }
          if (soap.isSoapMode()) {
            soap.setSoapMode(false);
            return true;
          } else {
            return false;
          }
        } else if (myDads.isPointInDads(kidX, kidY)) {
          kid.remove();
          myDads.removeAll();
          if (soap.isSoapMode()) {
            score += 2;
          } else {
            otherDads1.removeAll();
            otherDads2.removeAll();
            if (soap.isSoapAppeared()) {
              soap.remove();
            }
            score++;
          }
          mute.playIfUnmuted(beep2);
          timer.decrement();
          return true;
        } else if (soap.isSoapAppeared() && soap.isPointInSoap(kidX, kidY)) {
          kid.remove();
          myDads.removeAll();
          otherDads1.removeAll();
          otherDads2.removeAll();
          soap.remove();
          soap.setSoapMode(true);
          mute.playIfUnmuted(beep2);
          return true;
        } else if (EZInteraction.wasMouseLeftButtonPressed()) {
          mute.checkIfMutedImageClicked(mouseX, mouseY, battleMusic);
        }
        EZ.refreshScreen();
      }
    }
  }

  private class EndScene {
    private int score;

    private void save(int score) {
      this.score = score;
      mute.playIfUnmuted(endSong);
      highScores.enterNameIfNewHighScore(score);
    }

    private void show() {
      EZ.addImage("resources/ayyyyy.png", windowWidth / 2, 200);
      EZRectangle highScoreRect = EZ.addRectangle(4 * windowWidth / 5, windowHeight / 2, 400, 100, Color.blue, true);
      EZ.addText(4 * windowWidth / 5, windowHeight / 2, "Top 10 High Scores", Color.white, 40);
      EZ.addText(windowWidth / 2, windowHeight / 2 + 100, "High Score: " + highScores.getHighScore() + " Dads", Color.black, 40);
      EZ.addText(windowWidth / 2, windowHeight / 2, "You got " + score + " Dads.", Color.black, 40);
      EZ.addText(windowWidth / 2, windowHeight / 2 + 300, "Press space to retry", Color.black, 40);
      EZ.addText(windowWidth / 2, windowHeight / 2 + 400, "Press M to return to menu", Color.black, 40);

      while (endSceneShowing) {
        EZ.refreshScreen();
        int mouseX = EZInteraction.getXMouse();
        int mouseY = EZInteraction.getYMouse();
        if (EZInteraction.isKeyDown(' ')) {
          mute.playIfUnmuted(beep1);
          endSong.stop();
          endSceneShowing = false;
          gameSceneShowing = true;
        } else if (EZInteraction.isKeyDown('m')){
          mute.playIfUnmuted(beep1);
          endSong.stop();
          endSceneShowing = false;
          menuSceneShowing = true;
        } else if (EZInteraction.wasMouseLeftButtonPressed()) {
          if (highScoreRect.isPointInElement(mouseX, mouseY)) {
            mute.playIfUnmuted(beep1);
            endSceneShowing = false;
            highScoreSceneShowing = true;
          } else {
            mute.checkIfMutedImageClicked(mouseX, mouseY, endSong);
          }
        }
      }
    }
  }

  private class HighScoreScene {
    private void show() {
      EZ.addText(windowWidth / 2, windowHeight / 10, "Hall of Dads", Color.black, 90);
      EZRectangle backRect = EZ.addRectangle(windowWidth / 2, 9 * windowHeight / 10, 150, 70, Color.blue, true);
      EZ.addText(windowWidth / 2, 9 * windowHeight / 10, "Back", Color.white, 40);
      highScores.createTable();

      while (highScoreSceneShowing) {
        EZ.refreshScreen();
        int mouseX = EZInteraction.getXMouse();
        int mouseY = EZInteraction.getYMouse();
        if (EZInteraction.wasMouseLeftButtonPressed()) {
          if (backRect.isPointInElement(mouseX, mouseY)) {
            mute.playIfUnmuted(beep1);
            highScoreSceneShowing = false;
            endSceneShowing = true;
          } else {
            mute.checkIfMutedImageClicked(mouseX, mouseY, endSong);
          }
        }
      }
    }
  }
}