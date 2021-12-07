package com.company;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
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
  private final EZSound BattleMusic = EZ.addSound("BattleMusic.wav");
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

  public Game() throws IOException, InterruptedException {
    EZ.initialize(1915, 995);

    newHighScoreIndex = -1;
    //creates a new scanner that reads the names
    namesFile = new File("names.txt");
    //noinspection ResultOfMethodCallIgnored
    namesFile.createNewFile();
    Scanner sc = new Scanner(namesFile);
    while (sc.hasNext()) {
      alNames.add(sc.nextLine());
    }
    sc.close();
    //creates a new scanner that scans highscores.txt
    highScoresFile = new File("highscores.txt");
    //noinspection ResultOfMethodCallIgnored
    highScoresFile.createNewFile();
    sc = new Scanner(highScoresFile);
    while (sc.hasNext()) {
      alHighScores.add(sc.nextInt());
    }
    sc.close();
    if (alHighScores.size() == 0) {
      alHighScores.add(0);
      highScore = 0;
    }
    else {
      highScore = alHighScores.get(0);
    }
    //creates a new scanner that reads mute.txt
    muteFile = new File("mute.txt");
    //noinspection ResultOfMethodCallIgnored
    muteFile.createNewFile();
    sc = new Scanner(muteFile);
    muted = sc.hasNext() && sc.nextInt() == 1;
    sc.close();

    while (true) {
      if (start) {
        startScene();
      }
      else if (menu) {
        menuScene();
      }
      else if (!end && !highScores) {
        gameScene();
      }
      else if (end) {
        endScene();
      }
      else {
        highScoreScene();
      }
    }
  }

  private void startScene() throws IOException {
    EZ.removeAllEZElements();

    EZ.addText("Impact", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 - 200, "Are you 18 or older?", Color.black, 50);
    EZRectangle yesRect = EZ.addRectangle(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, 200, 100, Color.green, true);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "Yes", Color.white, 30);
    EZRectangle noRect = EZ.addRectangle(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 150, 200, 100, Color.red, true);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 150, "No", Color.white, 30);
    mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    //code for showing and hiding the mute button
    while (start) {
      if (muted) {
        unmutedImage.hide();
        mutedImage.show();
      }
      else {
        mutedImage.hide();
        unmutedImage.show();
      }

      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (yesRect.isPointInElement(mouseX, mouseY)) {
          menu = true;								//set menu to true and start to false
          start = false;
          if (!muted) {								//if it is not muted, play beep3
            Beep3.play();
          }
        }
        else if (noRect.isPointInElement(mouseX, mouseY)) {
          System.exit(0);
        }
        else if (mutedImage.isPointInElement(mouseX, mouseY) && mutedImage.isShowing()) {//if mute is pressed, set muted to false ad write it to mute.txt
          muted = false;
          FileWriter fw = new FileWriter(muteFile);
          fw.close();
        }
        else if (unmutedImage.isPointInElement(mouseX, mouseY) && unmutedImage.isShowing()) {//if unmutedimage is pressed, unmute it and write it to mute.txt
          muted = true;
          FileWriter fw = new FileWriter(muteFile);
          fw.write("1");
          fw.close();
        }
      }
      EZ.refreshScreen();
    }
  }

  private void menuScene() throws IOException {
    EZ.removeAllEZElements();
    endSong.stop();
    if (!muted) {
      titleMusic.play();
    }
    EZ.setBackgroundColor(new Color(140,230,231));
    EZ.addImage("ShowerWithYourDadTitle.png", EZ.getWindowWidth() / 2, 200);
    EZ.addImage("Simulator.png", EZ.getWindowWidth() / 2, 300);
    EZ.addImage("2015.png", EZ.getWindowWidth() / 2, 400);
    EZ.addImage("survive.png", EZ.getWindowWidth() / 2, 500);
    EZ.addImage("kid1.png", 650, 400);
    EZ.addImage("dad1.png", 550, 375);
    EZ.addImage("kid3.png", 1240, 400);
    EZ.addImage("dad3.png", 1355, 375);
    EZImage StartButton = EZ.addImage("startImage.png", EZ.getWindowWidth() / 6, 800);
    EZImage ExitButton = EZ.addImage("Exit.png", 5 * EZ.getWindowWidth() / 6, 800);
    EZ.addImage("kid2.png", EZ.getWindowWidth() / 2, 650);
    EZ.addImage("matchMe.png", 820, 570);
    EZ.addImage("orElse.png", 1110, 570);
    mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    while (menu) {
      if (muted) {
        unmutedImage.hide();
        mutedImage.show();
      }
      else {
        mutedImage.hide();
        unmutedImage.show();
      }

      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      //if startbutton is pressed, set menu to false and if it is not muted, play beep1
      if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (StartButton.isPointInElement(mouseX, mouseY)) {
          menu = false;
          if (!muted) {
            Beep1.play();
          }
        }
        else if (ExitButton.isPointInElement(mouseX, mouseY)) {	//if exitbutton is pressed, exit
          System.exit(0);
        }
        else if (mutedImage.isPointInElement(mouseX, mouseY) && mutedImage.isShowing()) {
          muted = false;
          titleMusic.play();
          FileWriter fw = new FileWriter(muteFile);
          fw.close();
        }
        else if (unmutedImage.isPointInElement(mouseX, mouseY) && unmutedImage.isShowing()) {
          muted = true;
          titleMusic.pause();
          FileWriter fw = new FileWriter(muteFile);
          fw.write("1");
          fw.close();
        }
      }
      EZ.refreshScreen();
    }
  }

  private void gameScene() throws IOException {
    EZ.removeAllEZElements();
    //if start, end, menu, and highscore is false, remove all elements, stop endsong and titlelmusic then play battlemusic
    endSong.stop();
    titleMusic.stop();

    if (!muted) {
      BattleMusic.play();
    }
    score = 0;
    boolean wasdShowing = true;
    //set score to 0 and wasdshowing to true and reset the timer.
    timer.reset();
    //add the images for the main game
    EZ.addImage("background.png", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2);
    wasd = EZ.addImage("wasd.png", EZ.getWindowWidth() / 2, 600);
    EZText scoreText = EZ.addText(EZ.getWindowWidth() / 2, 80, String.valueOf(score), Color.black, 70);
    EZText soapText = EZ.addText(EZ.getWindowWidth() / 2, 200, "", Color.red, 120);
    EZ.addText(EZ.getWindowWidth() / 2, 130, "High: " + highScore, Color.black, 30);
    mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);

    while (!end) {
      boolean dadsXGenerated = false;
      boolean dadsYGenerated = false;
      int dad1x, dad2x, dad3x, dad1y, dad2y, dad3y;
      dad1x = dad2x = dad3x = dad1y = dad2y = dad3y = 0;
      Random random = new Random();
      //while dad x and y generated is false, generate the x and y integers for all 3 dads
      while (!dadsXGenerated) {
        dad1x = random.nextInt(EZ.getWindowWidth() - 100) + 50;
        dad2x = random.nextInt(EZ.getWindowWidth() - 100) + 50;
        dad3x = random.nextInt(EZ.getWindowWidth() - 100) + 50;
        if (dad1x != dad2x && dad2x != dad3x && dad1x != dad3x && (dad1x < EZ.getWindowWidth() / 2 - 200 || dad1x > EZ.getWindowWidth() / 2 + 200) && (dad2x < EZ.getWindowWidth() / 2 - 200 || dad2x > EZ.getWindowWidth() / 2 + 200) && (dad3x < EZ.getWindowWidth() / 2 - 200 || dad3x > EZ.getWindowWidth() / 2 + 200)) {
          dadsXGenerated = true;
        }
      }
      while (!dadsYGenerated) {
        dad1y = random.nextInt(EZ.getWindowHeight() - 400) + 300;
        dad2y = random.nextInt(EZ.getWindowHeight() - 400) + 300;
        dad3y = random.nextInt(EZ.getWindowHeight() - 400) + 300;
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
      }
      else {
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
      timeRect = EZ.addRectangle(EZ.getWindowWidth() / 2, 30, (int) (450 * timer.timeLeft()), 25, Color.black, true);
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

  private void kidGame(String kidImageFile, DadArrayList myDads, DadArrayList otherDads1, DadArrayList otherDads2) throws IOException {
    Kid kid = new Kid(kidImageFile, EZ.getWindowWidth() / 2, 800);

    while (true) {
      if (muted) {
        unmutedImage.hide();
        mutedImage.show();
      }
      else {
        mutedImage.hide();
        unmutedImage.show();
      }
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();

      timeRect.setWidth((int)(450 * timer.timeLeft()));

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
      }
      else if (!Soap.soapMode && (otherDads1.isPointInDads(kid) || otherDads2.isPointInDads(kid))) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        end = true;
        if (!muted) {
          Beep3.play();
        }
        break;
      }
      else if (!Soap.soapMode && timer.timeLeft() <= 0) {
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        end = true;
        break;
      }
      else if (!Soap.soapMode && Soap.soapAppeared && Soap.isPointInSoap(kid)) {
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
      }
      else if (Soap.soapMode && myDads.isPointInDads(kid)) {
        kid.remove();
        myDads.removeDads();
        score += 2;
        if (!muted) {
          Beep2.play();
        }
        timer.decrement();
        break;
      }
      else if (Soap.soapMode && (otherDads1.isPointInDads(kid) || otherDads2.isPointInDads(kid))) {
        Soap.soapMode = false;
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        if (!muted) {
          Beep3.play();
        }
        break;
      }
      else if (Soap.soapMode && timer.timeLeft() <= 0) {
        Soap.soapMode = false;
        kid.remove();
        myDads.removeDads();
        otherDads1.removeDads();
        otherDads2.removeDads();
        break;
      }
      else if (EZInteraction.wasMouseLeftButtonPressed()) {
        if (mutedImage.isPointInElement(mouseX, mouseY) && mutedImage.isShowing()) {
          muted = false;
          BattleMusic.play();
          FileWriter fw = new FileWriter(muteFile);
          fw.close();
        }
        else if (unmutedImage.isPointInElement(mouseX, mouseY) && unmutedImage.isShowing()) {
          muted = true;
          BattleMusic.pause();
          FileWriter fw = new FileWriter(muteFile);
          fw.write("1");
          fw.close();
        }
      }
      EZ.refreshScreen();
    }
  }

  private void endScene() throws IOException, InterruptedException {
    EZ.removeAllEZElements();

    if (!scoreSaved) {
      saveScore();
    }

    //end of game menu
    EZ.addImage("ayyyyy.png", EZ.getWindowWidth() / 2, 200);
    EZRectangle highScoreRect = EZ.addRectangle(4 * EZ.getWindowWidth() / 5, EZ.getWindowHeight() / 2, 400, 100, Color.blue, true);
    EZ.addText(4 * EZ.getWindowWidth() / 5, EZ.getWindowHeight() / 2, "Top 10 High Scores", Color.white, 40);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 100, "High Score: " + highScore + " Dads", Color.black, 40);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "You got " + score + " Dads.", Color.black, 40);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 300, "Press space to retry", Color.black, 40);
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 400, "Press M to return to menu", Color.black, 40);
    mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    EZ.refreshScreen();

    while (end) {	//while end is true
      if (muted) {
        unmutedImage.hide();
        mutedImage.show();
      }
      else {
        mutedImage.hide();
        unmutedImage.show();
      }
      //if space is pressed set end to false and if it is not muted play beep1
      int mouseX = EZInteraction.getXMouse();
      int mouseY = EZInteraction.getYMouse();
      if (EZInteraction.isKeyDown(' ')) {
        end = false;
        if (!muted) {
          Beep1.play();
        }
      }
      else if (EZInteraction.isKeyDown('m')){	//else if m is pressed, set end to false and menu to true
        end = false;
        menu = true;
        if (!muted) {
          Beep1.play();
        }
      }
      if (EZInteraction.wasMouseLeftButtonPressed()) {	//if highscore is pressed, set highscore to true and end to false
        if (highScoreRect.isPointInElement(mouseX, mouseY)) {
          highScores = true;
          end = false;
          if (!muted) {
            Beep1.play();
          }
        }	//if mute is pressed, set mute to false and play endsong and write it to mute.txt
        else if (mutedImage.isPointInElement(mouseX, mouseY) && mutedImage.isShowing()) {
          muted = false;
          endSong.play();
          FileWriter fw = new FileWriter(muteFile);
          fw.close();
        }	//if unmuted button is pressed set mute to true, pause endsong, and write to mute.txt
        else if (unmutedImage.isPointInElement(mouseX, mouseY) && unmutedImage.isShowing()) {
          muted = true;
          endSong.pause();
          FileWriter fw = new FileWriter(muteFile);
          fw.write("1");
          fw.close();
        }
      }
      EZ.refreshScreen();
    }
  }

  private void saveScore() throws IOException, InterruptedException {
    BattleMusic.stop();
    if (!muted) {
      endSong.play();
    }
    if (score > highScore) {
      highScore = score;
    }

    for (int i = 0; i < alHighScores.size(); i++) {
      // Find new highscore index and create a new textfield.
      if (score > alHighScores.get(i)) {
        new TextField();

        while (!TextField.done) {
          Thread.sleep(800);
        }

        newHighScoreIndex = i;
        if (alHighScores.size() < 10) {
          alHighScores.add(i, score);
          alNames.add(i, TextField.name);
        }
        else if (alNames.size() < 10) {
          alHighScores.add(i, score);
          alHighScores.remove(10);
          alNames.add(i, TextField.name);
        }
        else {
          alHighScores.add(i, score);
          alHighScores.remove(10);
          alNames.add(i, TextField.name);
          alNames.remove(10);
        }
        //writes scores to highscores.txt
        FileWriter fw = new FileWriter(highScoresFile);
        for (int j = 0; j < alHighScores.size() && j < 10; j++) {
          fw.write(alHighScores.get(j) + " ");
        }
        fw.close();
        //writes names to names.txt
        fw = new FileWriter(namesFile);
        for (int j = 0; j < alNames.size() && j < 10; j++) {
          fw.write(alNames.get(j) + "\n");
        }
        fw.close();
        break;
      }
    }
    scoreSaved = true;
  }

  private void highScoreScene() throws IOException {
    EZ.removeAllEZElements();
    EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 10, "Hall of Dads", Color.black, 90);
    EZRectangle backRect = EZ.addRectangle(EZ.getWindowWidth() / 2, 9 * EZ.getWindowHeight() / 10, 150, 70, Color.blue, true);
    EZ.addText(EZ.getWindowWidth() / 2, 9 * EZ.getWindowHeight() / 10, "Back", Color.white, 40);
    //if requirements are met, write text
    for (int i = 0; i < 10; i++) {
      if (i == newHighScoreIndex) {
        EZ.addText(EZ.getWindowWidth() / 2 - 400, i * 60 + 210, i + 1 + ".", Color.red, 40);
      }
      else {
        EZ.addText(EZ.getWindowWidth() / 2 - 400, i * 60 + 210, i + 1 + ".", Color.black, 40);
      }
    }
    //if requirements are met, write text
    for (int i = 0; i < alNames.size(); i++) {
      if (alHighScores.get(i) != 0) {
        if (i == newHighScoreIndex) {
          EZ.addText(EZ.getWindowWidth() / 2, i * 60 + 210, alNames.get(i), Color.red, 40);
        }
        else {
          EZ.addText(EZ.getWindowWidth() / 2, i * 60 + 210, alNames.get(i), Color.black, 40);
        }
      }
    }
    //if requirements are met
    for (int i = 0; i < alHighScores.size(); i++) {
      if (alHighScores.get(i) != 0) {
        if (i == newHighScoreIndex) {
          EZ.addText(EZ.getWindowWidth() / 2 + 400, i * 60 + 210, alHighScores.get(i) + " Dads", Color.red, 40);
        }
        else {
          EZ.addText(EZ.getWindowWidth() / 2 + 400, i * 60 + 210, alHighScores.get(i) + " Dads", Color.black, 40);
        }
      }
    }
    mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
    unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);

    while (highScores) {
      if (muted) {
        unmutedImage.hide();
        mutedImage.show();
      }
      else {
        mutedImage.hide();
        unmutedImage.show();
      }
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
        }
        else if (mutedImage.isPointInElement(mouseX, mouseY) && mutedImage.isShowing()) {
          muted = false;
          endSong.play();
          FileWriter fw = new FileWriter(muteFile);
          fw.close();
        }
        else if (unmutedImage.isPointInElement(mouseX, mouseY) && unmutedImage.isShowing()) {
          muted = true;
          endSong.pause();
          FileWriter fw = new FileWriter(muteFile);
          fw.write("1");
          fw.close();
        }
      }
      EZ.refreshScreen();
    }
  }
}
