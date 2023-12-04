package andrew.battleship_4028;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		System.out.println("SIMPLE BATTLESHIPS-4028");
		System.out.println("BY ANDREW MEECHAN");
		System.out.println("ALL RIGHTS RESERVED");
		System.out.println("COPYRIGHT 2023");
		System.out.println();
		boolean valid = false;
		try (Scanner input = new Scanner(System.in)) {
			int num = 0;
			do {
			System.out.println("Do you wish to play against another player, a bot or spectate?");
			System.out.println("Enter 1, 2 or 3");
			String choice = input.nextLine();
			switch(choice) {
			case "1":
				num = 1;
				valid = true;
				break;
			case "2":
				num = 2;
				valid = true;
				break;
			case "3":
				num = 3;
				valid = true;
				break;
			default:
				System.out.println("Invalid input!");
				valid = false;
			}
			} while (!valid);
				
			Game game = new Game(num);
		}
	}

}
