package com.company;

import java.util.*;
import java.io.*;
import java.awt.*;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		EZ.initialize(1915, 995);
		//declares all the booleans and integers
		boolean start = true;
		boolean menu = false;
		boolean end = false;
		boolean highScores = false;
		boolean muted = false;
		
		int dads = 0;
		int highScore;
		int kid = 0;
		int newHigh = 100;
		
		boolean kidinplay1 = false;
		boolean kidinplay2 = false;
		boolean kidinplay3 = false;
		boolean highScoreSaved = true;
		//adds the sound and names them
		EZSound titleMusic = EZ.addSound("TitleMusic.wav");
		EZSound endSong = EZ.addSound("EndSong.wav");
		EZSound BattleMusic = EZ.addSound("BattleMusic.wav");
		EZSound Beep1 = EZ.addSound("Beep1.wav");
		EZSound Beep2 = EZ.addSound("Beep2.wav");
		EZSound Beep3 = EZ.addSound("Beep3.wav");
		EZSound Beep4 = EZ.addSound("Beep4.wav");
		//creates 3 new dad arraylists
		DadArrayList dadAl1 = new DadArrayList();
		DadArrayList dadAl2 = new DadArrayList();
		DadArrayList dadAl3 = new DadArrayList();
		
		Timer timer = new Timer();	//creates a new timer
		
		Soap soap = new Soap();		//creates a new soap
		//scans the next integer from save.txt. if no interger, set highscore to 0. then close scanner
		Scanner sc = new Scanner(new File("save.txt"));
		if (sc.hasNext()) {
			highScore = sc.nextInt();
		}
		else {
			highScore = 0;
		}
		sc.close();
		//creates 2 arraylists of alHighScores and alNames
		ArrayList<Integer> alHighScores = new ArrayList<Integer>();
		ArrayList<String> alNames = new ArrayList<String>();
		//creates a new scanner that scans highscores.txt
		Scanner sch = new Scanner(new File("highscores.txt"));
		while (sch.hasNext()) {
			alHighScores.add(new Integer(sch.nextInt()));
		}
		sch.close();
		if (alHighScores.size() == 0) {
			alHighScores.add(new Integer(0));
		}
		//creates a new scanner that reads the names
		Scanner scn = new Scanner(new File("names.txt"));
		while (scn.hasNext()) {
			alNames.add(new String(scn.nextLine()));
		}
		scn.close();
		//creates a new scanner that reads mute.txt
		Scanner scm = new Scanner(new File("mute.txt"));
		if (scm.hasNext() && scm.nextInt() == 1) {
			muted = true;
		}
		scm.close();
		
		while (true){
			//Start screen
			if (start) {
				EZ.removeAllEZElements();
				//if start is true, remove all elements and place a yes and no box
				EZImage mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZImage unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				
				EZText prompt = EZ.addText("Impact", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 - 200, "Are you 18 or older?", Color.black, 50);
				
				EZRectangle yesRect = EZ.addRectangle(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, 200, 100, Color.green, true);
				EZText yes = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "Yes", Color.white, 30);
				
				EZRectangle noRect = EZ.addRectangle(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 150, 200, 100, Color.red, true);
				EZText no = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 150, "No", Color.white, 30);
				//code for showing and hiding the mute button
				while (start)
				{
					if (muted) {
						mutedImage.show();
						unmutedImage.hide();
					}
					else {
						unmutedImage.show();
						mutedImage.hide();
					}
					
					int mousex = EZInteraction.getXMouse();
					int mousey = EZInteraction.getYMouse();
					if (EZInteraction.wasMouseLeftButtonPressed())
					{	
						if (yesRect.isPointInElement(mousex, mousey)) 	//if yes box is pressed
						{
							menu = true;								//set menu to true and start to false
							start = false;
							if (!muted) {								//if it is not muted, play beep4
								Beep4.play();
							}
						}
						else if (noRect.isPointInElement(mousex, mousey))//else if no box is pressed, exit
						{
							System.exit(0);
						}
						else if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {//if mute is pressed, set muted to false ad write it to mute.txt
							muted = false;
							FileWriter fw = new FileWriter("mute.txt");
							fw.close();
						}
						else if (unmutedImage.isPointInElement(mousex, 
mousey) && unmutedImage.isShowing()) {//if unmutedimage is pressed, unmute it and write it to mute.txt
							muted = true;
							FileWriter fw = new FileWriter("mute.txt");
							fw.write("1");
							fw.close();
						}
					}
					EZ.refreshScreen();
				}
			}
			
			//Menu
			else if (menu){ //if menu is true, remove all elements and add the menu
				EZ.removeAllEZElements();
				EZImage mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZImage unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
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
				while (menu){
					if (muted) {
						mutedImage.show();
						unmutedImage.hide();
					}
					else {
						unmutedImage.show();
						mutedImage.hide();
					}
					
					int mousex = EZInteraction.getXMouse();
					int mousey = EZInteraction.getYMouse();
					//if startbutton is pressed, set menu to false and if it is not muted, play beep2
					if (EZInteraction.wasMouseLeftButtonPressed()){
						if (StartButton.isPointInElement(mousex, mousey)){
							menu = false;
							if (!muted) {
								Beep2.play();
							}
						}
						else if (ExitButton.isPointInElement(mousex, mousey)){	//if exitbutton is pressed, exit
							System.exit(0);
						}
						else if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
							muted = false;
							titleMusic.play();
							FileWriter fw = new FileWriter("mute.txt");
							fw.close();
						}
						else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
							muted = true;
							titleMusic.pause();
							FileWriter fw = new FileWriter("mute.txt");
							fw.write("1");
							fw.close();
						}
					}
					EZ.refreshScreen();
				}
			}
			//Game
			else if (!start && !end && !menu && !highScores) {	
				EZ.removeAllEZElements();
				//if start, end, menu, and highscore is false, remove all elements, stop endsong and titlelmusic then play battlemusic
				endSong.stop();
				titleMusic.stop();
				
				if (!muted) {
					BattleMusic.play();
				}
				dads = 0;
				boolean wasdShowing = true;
				//set dads to 0 and wasdshowing to true and reset the timer.
				timer.reset();
				//add the images for the main game
				EZImage background = EZ.addImage("background.png", EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2);
				EZImage wasd = EZ.addImage("wasd.png", EZ.getWindowWidth() / 2, 600);
				EZImage mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZImage unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZText dadsText = EZ.addText(EZ.getWindowWidth() / 2, 80, String.valueOf(dads), Color.black, 70);
				EZText soapText = EZ.addText(EZ.getWindowWidth() / 2, 200, "", Color.red, 120);
				EZText highScoreText = EZ.addText(EZ.getWindowWidth() / 2, 130, "High: " + highScore, Color.black, 30);
				
				while (!end) {
					boolean dadsXGenerated = false;
					boolean dadsYGenerated = false;
					//while end is false, creates a random generator and set the ints to 0
					Random random = new Random();
					int dad1x = 0;
					int dad2x = 0;
					int dad3x = 0;
					int dad1y = 0;
					int dad2y = 0;
					int dad3y = 0;
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
					Dad dad1 = new Dad("dad1.png", dad1x, dad1y);
					Dad dad2 = new Dad("dad2.png", dad2x, dad2y);
					Dad dad3 = new Dad("dad3.png", dad3x, dad3y);
					dadAl1.addDad(dad1);
					dadAl2.addDad(dad2);
					dadAl3.addDad(dad3);
					//if it is not soapmode, write " "
					if (!Soap.soapMode) {
						kid = random.nextInt(3) + 1;
						soap.soapOrNo();
						soapText.setMsg("");
					}
					else { //else, write soap frenzy
						soapText.setMsg("SOAP FRENZY");
					}
					
					if (wasdShowing) { //if wasdshowing is true, pull wasd to front and set wasdshowing to false
						wasd.pullToFront();
						wasdShowing = false;
					}
					//start the timer and add a timer bar
					timer.start();
					
					EZRectangle timeRect = EZ.addRectangle(EZ.getWindowWidth() / 2, 30, (int)(450 * timer.timeLeft()), 25, Color.black, true); 

					dadsText.setMsg(String.valueOf(dads));
					//creates a switch statement
					switch (kid) {
					//if case 1, creates a new kid1 and start the timer. then control the kid with wasd. if dad2 and dad3 is point in element, set end to true
					//if kid1 is point in soap, start soapmode
					//if kid1 is point in dad1, remove all dads and generate new ones and generate a new kid
					//if kid1 is point in dad1, decrement timer
					case 1: 
						Kid kid1 = new Kid("kid1.png", EZ.getWindowWidth() / 2, 800);
						kidinplay1 = true;
						while (kidinplay1){
							if (muted) {
								mutedImage.show();
								unmutedImage.hide();
							}
							else {
								unmutedImage.show();
								mutedImage.hide();
							}
							int mousex = EZInteraction.getXMouse();
							int mousey = EZInteraction.getYMouse();
							
							timeRect.setWidth((int)(450 * timer.timeLeft()));
							
							kid1.ControlIt();
							
							if ((EZInteraction.isKeyDown('w') || EZInteraction.isKeyDown('a') || EZInteraction.isKeyDown('s') || EZInteraction.isKeyDown('d')) && wasd.isShowing()) {
								EZ.removeEZElement(wasd);
							}
							
							if (!Soap.soapMode && dadAl1.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								dads++;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (!Soap.soapMode && dadAl2.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (!Soap.soapMode && dadAl3.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}else if (!Soap.soapMode && timer.timeLeft() <= 0) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								end = true;
							}
							else if (dadAl1.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								kid1.remove();
								dads += 2;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (dadAl2.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (dadAl3.isPointInDads(kid1)) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (timer.timeLeft() <= 0) {
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								Soap.soapMode = false;
							}
							else if (Soap.soapAppeared && soap.isPointInSoap(kid1)) {
								Soap.soapMode = true;
								Soap.soapAppeared = false;
								kidinplay1 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid1.remove();
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (EZInteraction.wasMouseLeftButtonPressed()) {
								if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
									muted = false;
									BattleMusic.play();
									FileWriter fw = new FileWriter("mute.txt");
									fw.close();
								}
								else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
									muted = true;
									BattleMusic.pause();
									FileWriter fw = new FileWriter("mute.txt");
									fw.write("1");
									fw.close();
								}
							}
							EZ.refreshScreen();
						}
						break;
					case 2:
						Kid kid2 = new Kid("kid2.png", EZ.getWindowWidth() / 2, 800);
						kidinplay2 = true;
						
						while (kidinplay2){
							if (muted) {
								mutedImage.show();
								unmutedImage.hide();
							}
							else {
								unmutedImage.show();
								mutedImage.hide();
							}
							int mousex = EZInteraction.getXMouse();
							int mousey = EZInteraction.getYMouse();
							
							timeRect.setWidth((int)(450 * timer.timeLeft()));
							
							kid2.ControlIt();
							
							if ((EZInteraction.isKeyDown('w') || EZInteraction.isKeyDown('a') || EZInteraction.isKeyDown('s') || EZInteraction.isKeyDown('d')) && wasd.isShowing()) {
								EZ.removeEZElement(wasd);
							}
							
							if (!Soap.soapMode && dadAl2.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								dads++;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (!Soap.soapMode && dadAl1.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (!Soap.soapMode && dadAl3.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}else if (!Soap.soapMode && timer.timeLeft() <= 0) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								end = true;
							}
							else if (dadAl2.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl2.removeDads();
								kid2.remove();
								dads += 2;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (dadAl1.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (dadAl3.isPointInDads(kid2)) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (timer.timeLeft() <= 0) {
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								Soap.soapMode = false;
							}
							else if (Soap.soapAppeared && soap.isPointInSoap(kid2)) {
								Soap.soapMode = true;
								Soap.soapAppeared = false;
								kidinplay2 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid2.remove();
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (EZInteraction.wasMouseLeftButtonPressed()) {
								if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
									muted = false;
									BattleMusic.play();
									FileWriter fw = new FileWriter("mute.txt");
									fw.close();
								}
								else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
									muted = true;
									BattleMusic.pause();
									FileWriter fw = new FileWriter("mute.txt");
									fw.write("1");
									fw.close();
								}
							}
							EZ.refreshScreen();
						}
						break;
					case 3:
						Kid kid3 = new Kid("kid3.png", EZ.getWindowWidth() / 2, 800);
						kidinplay3 = true;
						while (kidinplay3){
							if (muted) {
								mutedImage.show();
								unmutedImage.hide();
							}
							else {
								unmutedImage.show();
								mutedImage.hide();
							}
							int mousex = EZInteraction.getXMouse();
							int mousey = EZInteraction.getYMouse();
							
							timeRect.setWidth((int)(450 * timer.timeLeft()));
							
							kid3.ControlIt();
							
							if ((EZInteraction.isKeyDown('w') || EZInteraction.isKeyDown('a') || EZInteraction.isKeyDown('s') || EZInteraction.isKeyDown('d')) && wasd.isShowing()) {
								EZ.removeEZElement(wasd);
							}
							
							if (!Soap.soapMode && dadAl3.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								dads++;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (!Soap.soapMode && dadAl1.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (!Soap.soapMode && dadAl2.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								end = true;
								if (!muted) {
									Beep4.play();
								}
							}else if (!Soap.soapMode && timer.timeLeft() <= 0) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								end = true;
							}
							else if (dadAl3.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl3.removeDads();
								kid3.remove();
								dads += 2;
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (dadAl1.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (dadAl2.isPointInDads(kid3)) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								Soap.soapMode = false;
								if (!muted) {
									Beep4.play();
								}
							}
							else if (timer.timeLeft() <= 0) {
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								Soap.soapMode = false;
							}
							else if (Soap.soapAppeared && soap.isPointInSoap(kid3)) {
								Soap.soapMode = true;
								Soap.soapAppeared = false;
								kidinplay3 = false;
								dadAl1.removeDads();
								dadAl2.removeDads();
								dadAl3.removeDads();
								kid3.remove();
								if (!muted) {
									Beep3.play();
								}
								timer.decrement();
							}
							else if (EZInteraction.wasMouseLeftButtonPressed()) {
								if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
									muted = false;
									BattleMusic.play();
									FileWriter fw = new FileWriter("mute.txt");
									fw.close();
								}
								else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
									muted = true;
									BattleMusic.pause();
									FileWriter fw = new FileWriter("mute.txt");
									fw.write("1");
									fw.close();
								}
							}
							EZ.refreshScreen();
						}
						break;
					}
				EZ.removeEZElement(timeRect);	//remove timeRect
				soap.remove();					//remove soap
				EZ.refreshScreen();
				}
				highScoreSaved = false;			//set nighscoresaved to false
				newHigh = 100;
			}
			//End screen
			else if (end) { //if end is true, remove all elements and stop music and play endsong
				EZ.removeAllEZElements();
				EZImage mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZImage unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				BattleMusic.stop();
				if (!muted && !highScoreSaved) {
					endSong.play();
				}
				//if dads is greater than highscore, set the value of dads to highscore and write the value of highscore
				if (dads > highScore) {
					FileWriter fw = new FileWriter("save.txt");
					highScore = dads;
					fw.write(String.valueOf(highScore));
					fw.close();
				}
				//if highscoresaved is false, 
				if (!highScoreSaved) {
					for (int i = 0; i < alHighScores.size(); i++) { //if all requirements are met, create a new textfield.
						if (dads > alHighScores.get(i)) {
							mutedImage.hide();
							unmutedImage.hide();
							
							TextField textField = new TextField();
							
							while (!TextField.done) {
								Thread.sleep(800);
							}
							//set the value of i to newHigh
							newHigh = i;
							//if alHichscore size is less than 10, all a new score and new name
							if (alHighScores.size() < 10) {
								alHighScores.add(i, new Integer(dads));
								alNames.add(i, new String(TextField.name));
							}
							else if (alNames.size() < 10 && alHighScores.size() >= 10) { //if size is less or equal or greater than 10 add new score and name
								alHighScores.add(i, new Integer(dads));
								alHighScores.remove(10);
								alNames.add(i, new String(TextField.name));
							}
							else {
								alHighScores.add(i, new Integer(dads));
								alHighScores.remove(10);
								alNames.add(i, new String(TextField.name));
								alNames.remove(10);
							}
							//writes scores to highscore.txt
							FileWriter fw = new FileWriter("highscores.txt");
							for (int j = 0; j < alHighScores.size() && j < 10; j++) {
								fw.write(String.valueOf(alHighScores.get(j)) + " ");
							}
							fw.close();
							//writes names to names.txt
							FileWriter fwn = new FileWriter("names.txt");
							for (int j = 0; j < alNames.size() && j < 10; j++) {
								fwn.write(alNames.get(j) + "\n");
							}
							fwn.close();
							break;
						}
					}
					highScoreSaved = true;
				}
				//end of game menu
				EZ.addImage("ayyyyy.png", EZ.getWindowWidth() / 2, 200);
				
				EZRectangle highScoreRect = EZ.addRectangle(4 * EZ.getWindowWidth() / 5, EZ.getWindowHeight() / 2, 400, 100, Color.blue, true);
				EZText highScoresText = EZ.addText(4 * EZ.getWindowWidth() / 5, EZ.getWindowHeight() / 2, "Top 10 High Scores", Color.white, 40);
				
				EZText highScoreText = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 100, "High Score: " + highScore + " Dads", Color.black, 40);

				EZText dadsText = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2, "You got " + dads + " Dads.", Color.black, 40);

				EZText retryText = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 300, "Press space to retry", Color.black, 40);

				EZText menuText = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 2 + 400, "Press M to return to menu", Color.black, 40);
				
				EZ.refreshScreen();

				while (end) {	//while end is true
					if (muted) {
						mutedImage.show();
						unmutedImage.hide();
					}
					else {
						unmutedImage.show();
						mutedImage.hide();
					}
					//if space is pressed set end to false and if it is not muted play beep2
					int mousex = EZInteraction.getXMouse();
					int mousey = EZInteraction.getYMouse();
					if (EZInteraction.isKeyDown(' ')) {
						end = false;
						if (!muted) {
							Beep2.play();
						}
					}
					else if (EZInteraction.isKeyDown('m')){	//else if m is pressed, set start and end to false and menu to true
						start = false;
						end = false;
						menu = true;
						if (!muted) {
							Beep2.play();
						}
					}
					if (EZInteraction.wasMouseLeftButtonPressed()) {	//if highscore is pressed, set highscore to true and end to false
						if (highScoreRect.isPointInElement(mousex, mousey)) {
							highScores = true;
							end = false;
							if (!muted) {
								Beep2.play();
							}
						}	//if mute is pressed, set mute to false and play endsong and write it to mute.txt
						else if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
							muted = false;
							endSong.play();
							FileWriter fw = new FileWriter("mute.txt");
							fw.close();
						}	//if unmuted button is pressed set mute to true, pause endsong, and write to mute.txt
						else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
							muted = true;
							endSong.pause();
							FileWriter fw = new FileWriter("mute.txt");
							fw.write("1");
							fw.close();
						}
					}
					EZ.refreshScreen();
				}
			}
			else if (highScores) {	//if highscores is true, remove all elements and place highscore page
				EZ.removeAllEZElements();
				EZImage mutedImage = EZ.addImage("muted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZImage unmutedImage = EZ.addImage("unmuted.png", 29 * EZ.getWindowWidth() / 30, 14 * EZ.getWindowHeight() / 15);
				EZText hallText = EZ.addText(EZ.getWindowWidth() / 2, EZ.getWindowHeight() / 10, "Hall of Dads", Color.black, 90);
				EZRectangle backRect = EZ.addRectangle(EZ.getWindowWidth() / 2, 9 * EZ.getWindowHeight() / 10, 150, 70, Color.blue, true);
				EZText backText = EZ.addText(EZ.getWindowWidth() / 2, 9 * EZ.getWindowHeight() / 10, "Back", Color.white, 40);
				//if requirements are met, write text
				for (int i = 1; i <= 10; i++) {
					if (newHigh != i - 1) {
						EZ.addText(EZ.getWindowWidth() / 2 - 400, i * 60 + 150, i + ".", Color.black, 40);
					}
					else {
						EZ.addText(EZ.getWindowWidth() / 2 - 400, i * 60 + 150, i + ".", Color.red, 40);
					}
				}
				//if requirements are met, write text
				for (int i = 1; i <= alNames.size(); i++) {
					if (alHighScores.get(i - 1) != 0) {
						if (newHigh != i - 1) {
							EZ.addText(EZ.getWindowWidth() / 2, i * 60 + 150, alNames.get(i - 1), Color.black, 40);
						}
						else {
							EZ.addText(EZ.getWindowWidth() / 2, i * 60 + 150, alNames.get(i - 1), Color.red, 40);
						}
					}
				}
				//if requirements are met
				for (int i = 1; i <= alHighScores.size(); i++) {
					if (alHighScores.get(i - 1) != 0) {
						if (newHigh != i - 1) {
							EZ.addText(EZ.getWindowWidth() / 2 + 400, i * 60 + 150, alHighScores.get(i - 1) + " Dads", Color.black, 40);
						}
						else {
							EZ.addText(EZ.getWindowWidth() / 2 + 400, i * 60 + 150, alHighScores.get(i - 1) + " Dads", Color.red, 40);
						}
					}
				}
				
				while (highScores) {
					if (muted) {
						mutedImage.show();
						unmutedImage.hide();
					}
					else {
						unmutedImage.show();
						mutedImage.hide();
					}
					//if backrect is pressed set highscores to false and end to true
					int mousex = EZInteraction.getXMouse();
					int mousey = EZInteraction.getYMouse();
					if (EZInteraction.wasMouseLeftButtonPressed()) {
						if (backRect.isPointInElement(mousex, mousey)) {
							highScores = false;
							end = true;
							if (!muted) {
								Beep2.play();
							}
						}
						else if (mutedImage.isPointInElement(mousex, mousey) && mutedImage.isShowing()) {
							muted = false;
							endSong.play();
							FileWriter fw = new FileWriter("mute.txt");
							fw.close();
						}
						else if (unmutedImage.isPointInElement(mousex, mousey) && unmutedImage.isShowing()) {
							muted = true;
							endSong.pause();
							FileWriter fw = new FileWriter("mute.txt");
							fw.write("1");
							fw.close();
						}
					}
					EZ.refreshScreen();
				}
			}
			EZ.refreshScreen();
		}
	}
}
