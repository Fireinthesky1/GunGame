package main;

import java.util.Scanner;

import game_classes.Board;
import game_classes.Game;

public class CavesAndChickensMain {

	public static void main(String[] args) {
		boolean keep_playing = true;
		Scanner scan = new Scanner(System.in);
		Game myGame = new Game(scan);
		
		while (keep_playing) {
			myGame.play_game();
			System.out.println("Would you like to play again?");
			String response = scan.nextLine();
			if(response.equalsIgnoreCase("yes")) {
			}else {
				System.out.println("Thanks for playing!");
				keep_playing = false;
			}
		}
	}
}
