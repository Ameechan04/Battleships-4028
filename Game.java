package andrew.battleship_4028;
import java.util.Scanner;
import java.util.Random;
public class Game {

//void create_board(char b[10][12]);

/* 
 * A simple game of battleships
 * Author: Andrew Meeehan <andrew.meechan.2022@uni.strath.ac.uk>
 * Program is to create a 2D Array to represent a battleship board for two players
 * Each turn the player will choose a target to shoot at and will be told if it hits or not
 * Last updated: 07/10/23
 */
	int ship_count;
public Game(int choice) {
	Scanner scanner = new Scanner(System.in);
	for (int i = 0; i < 50; i++) {
        System.out.println();
    }
	System.out.print("enter how many ships you wish to have on the board: ");

		ship_count = get_ship_count();
		
		switch(choice) {
		case 1:
			Board board1 = new Board();
			Board board2 = new Board(); 
			Board target_board1 = new Board(); //this board is so the player can see where they've shot at!
		 	Board target_board2 = new Board(); //this board is so the player can see where they've shot at!
		 	System.out.println("Player 1's turn:");
		 	board1.print_board();
		 	human_create_board(ship_count, board1);
		 	System.out.println("Press enter to continue...");
		 	scanner.nextLine();
		 	for (int i = 0; i < 50; i++) {
	            System.out.println();
	        }
		 	  System.out.println("Player 2's turn:");
		 	 board2.print_board();
		 	human_create_board(ship_count, board2);
		 	
		 	System.out.println("Press enter to continue...");
		 	scanner.nextLine();
		 	for (int i = 0; i < 50; i++) {
	            System.out.println();
	        }
		 	 
		 	
		 	for (int i = 0; i < 50; i++) {
	            System.out.println();
	        }
		    main_game(ship_count, board1, board2, target_board1, target_board2);

			
		case 2:
			Board board = new Board();
			Board bot_board = new Board();
		 	Board target_board = new Board(); //this board is so the player can see where they've shot at!

		    human_create_board(ship_count, board);
		    ai_create_board(bot_board, ship_count);
		    human_bot_game(ship_count, board, bot_board, target_board);
		  
		case 3:
			 Board bot_board1 = new Board();
			 Board bot_board2 = new Board();
			 ai_create_board(bot_board1, ship_count);
			 ai_create_board(bot_board2, ship_count);
			 System.out.print("AI BOARD 1: \n");  
			 bot_board1.print_board();
			 System.out.print("AI BOARD 2: \n");
			 bot_board2.print_board();
			 bot_game(ship_count, bot_board1, bot_board2);
		}
	    
	    
	    




}

int get_ship_count() {
	Scanner userInput = new Scanner(System.in);
	boolean valid;
	do {
	    valid = false;
	    ship_count = userInput.nextInt();
	    userInput.nextLine(); //consume line
	   
	    if (ship_count < 1) {
	        System.out.print("The ship count is too low! Re-enter: ");
	    } else if (ship_count > 10) {
	        System.out.print("The ship count is too large! Re-enter: ");
	    } else {
	        valid = true;
	    }
	    
	} while (!valid);
	return ship_count;
}
 boolean check_empty_spot(char[][] board, int row, int column) {
    if (board[row][column] != 'X')   {
        //empty board position
        return true;   //returns true if empty
     } else {
         return false;
     }
    
}

 void human_bot_game(int ship_count, Board board, Board bot_board, Board target_board) {
	Scanner userInput = new Scanner(System.in);
	int target_row;
	int target_column;
	int number_of_hits_left_human = ship_count * 3;
	int number_of_hits_left_bot = ship_count * 3;
	char character = 0;
	boolean valid = false;
	boolean game_over = false;
	int player = 1;
	do {
	    if (player == 1) {
	        System.out.print("\nHUMAN TURN! \n");
	        target_board.print_board();
	        System.out.print("enter target row: ");
	        do {
	        	target_row = userInput.nextInt();
	        	userInput.nextLine();
	           
	            //convert row entered into usable format for the 2D array:
	            target_row = 9 - target_row;
	            if (target_row >= 0 && target_row <=8) {
	                valid = true;
	            } else {
	                System.out.print("invalid. Re-enter a correct row: ");
	            }
	        } while (!valid);
	        //input for C must be => 2 AND =< 11
	        valid = false; //reset valid to false
	        System.out.print("enter column: ");
	        do {
	        	String Scharacter = userInput.nextLine();
	        	character = Scharacter.charAt(0);
	            //Converted the input into array form
	            target_column = character - 63;  // 
	            if (target_column >= 2 && target_column < 11) {
	                valid = true;
	            } else {
	                System.out.print("invalid. Re-enter a correct column: ");
	            }
	        } while (!valid);
	        valid = false;
	        if (shoot(target_row, target_column, bot_board.get_array())) {
	        	//human shoots at bot board and hits
	        	number_of_hits_left_human--;
	        	target_board.set_value(target_row, target_column, 'H');
	        } else {
	        	//miss
	        	target_board.set_value(target_row, target_column, 'M');
	        }
	        player = 2;
	    } 
	    
	    if (player == 2) {
	        System.out.print("\nBOT TURN! \n");
	        target_row = (int)(Math.random() * 8 + 1);
	        //target_column = 10;
	        target_column = (int)(Math.random() * 9) + 2;
	        if (shoot(target_row, target_column, board.get_array())) {
	        	//bot shoots at human board and hits
	        	number_of_hits_left_bot--;
	        	
	        } else {
	        	board.set_value(target_row, target_column, 'M');
	        }
	        board.print_board();
	        
	        player = 1;
	    }
	    
	    
	    if (number_of_hits_left_bot == 0) {
	    	 game_over = true;
	 	    System.out.print("THE BOT IS VICTORIOUS!");
	 	
	    }
	    
	    if (number_of_hits_left_human == 0) {
	    	 game_over = true;
	 	    System.out.print("YOU ARE VICTORIOUS!");
	 	
	    }

	    
	    
	} while (!game_over);

}

 void main_game(int ship_count, Board board, Board bot_board, Board target_board1, Board target_board2) {
		Scanner userInput = new Scanner(System.in);
		int target_row;
		int target_column;
		int number_of_hits_left_human1 = ship_count * 3;
		int number_of_hits_left_human2 = ship_count * 3;
		char character = 0;
		boolean valid = false;
		boolean game_over = false;
		int player = 1;
		do {
		    if (player == 1) {
		        System.out.print("\nPLAYER 1 TURN! \n");
		        target_board1.print_board();
		        System.out.print("enter target row: ");
		        do {
		        	target_row = userInput.nextInt();
		        	userInput.nextLine();
		           
		            //convert row entered into usable format for the 2D array:
		            target_row = 9 - target_row;
		            if (target_row >= 0 && target_row <=8) {
		                valid = true;
		            } else {
		                System.out.print("invalid. Re-enter a correct row: ");
		            }
		        } while (!valid);
		        //input for C must be => 2 AND =< 11
		        valid = false; //reset valid to false
		        System.out.print("enter column: ");
		        do {
		        	String Scharacter = userInput.nextLine();
		        	character = Scharacter.charAt(0);
		            //Converted the input into array form
		            target_column = character - 63;  // 
		            if (target_column >= 2 && target_column < 11) {
		                valid = true;
		            } else {
		                System.out.print("invalid. Re-enter a correct column: ");
		            }
		        } while (!valid);
		        valid = false;
		        if (shoot(target_row, target_column, bot_board.get_array())) {
		        	//human shoots at bot board and hits
		        	number_of_hits_left_human1--;
		        	target_board1.set_value(target_row, target_column, 'H');
		        } else {
		        	//miss
		        	target_board1.set_value(target_row, target_column, 'M');
		        }
		        player = 2;
		    } 
		    System.out.println("Press enter to continue...");
		 	userInput.nextLine();
		 	for (int i = 0; i < 50; i++) {
	            System.out.println();
	        }
		 	
		    
		    if (player == 2) {
		        System.out.print("\nPLAYER 2 TURN! \n");
		        target_board2.print_board();
		        System.out.print("enter target row: ");
		        do {
		        	target_row = userInput.nextInt();
		        	userInput.nextLine();
		           
		            //convert row entered into usable format for the 2D array:
		            target_row = 9 - target_row;
		            if (target_row >= 0 && target_row <=8) {
		                valid = true;
		            } else {
		                System.out.print("invalid. Re-enter a correct row: ");
		            }
		        } while (!valid);
		        //input for C must be => 2 AND =< 11
		        valid = false; //reset valid to false
		        System.out.print("enter column: ");
		        do {
		        	String Scharacter = userInput.nextLine();
		        	character = Scharacter.charAt(0);
		            //Converted the input into array form
		            target_column = character - 63;  // 
		            if (target_column >= 2 && target_column < 11) {
		                valid = true;
		            } else {
		                System.out.print("invalid. Re-enter a correct column: ");
		            }
		        } while (!valid);
		        valid = false;
		        if (shoot(target_row, target_column, bot_board.get_array())) {
		        	//human shoots at bot board and hits
		        	number_of_hits_left_human2--;
		        	target_board2.set_value(target_row, target_column, 'H');
		        } else {
		        	//miss
		        	target_board2.set_value(target_row, target_column, 'M');
		        }
		        player = 1;
		    } 
		   
		    if (number_of_hits_left_human1 == 0) {
		    	 game_over = true;
		 	    System.out.print("PLAYER 1 IS VICTORIOUS!");
		 	
		    }
		    
		    if (number_of_hits_left_human2 == 0) {
		    	 game_over = true;
		 	    System.out.print("PLAYER 2 IS VICTORIOUS!");
		 	
		    }
		    System.out.println("Press enter to continue...");
		 	userInput.nextLine();
		 	for (int i = 0; i < 50; i++) {
	            System.out.println();
	        }
		 	 
		    
		    
		} while (!game_over);

	}

 
 void bot_game(int ship_count, Board board, Board bot_board) {
	 	Scanner next_turn = new Scanner(System.in);
		int target_row;
		int target_column;
		int number_of_hits_left_bot1 = ship_count * 3;
		int number_of_hits_left_bot2 = ship_count * 3;
		int round_counter = 1;
		boolean game_over = false;
		int player = 1;
		do {
			System.out.println("Round: " + round_counter);
			if (player == 1) {
	        System.out.print("\nBOT 1'S TURN! \n");
	        target_row = (int)(Math.random() * 8 + 1);
	        //target_column = 10;
	        target_column = (int)(Math.random() * 9) + 2;
	        if (shoot(target_row, target_column, bot_board.get_array())) {
	        	//bot1 shoots at bot2 board and hits
	        	number_of_hits_left_bot1--;
	        	
	        } else {
	        	bot_board.set_value(target_row, target_column, 'M');
	        }
	        bot_board.print_board();
	        
	        player = 2;
	    }
		    
		    if (player == 2) {
		        System.out.print("\nBOT 2'S TURN! \n");
		        target_row = (int)(Math.random() * 8 + 1);
		        //target_column = 10;
		        target_column = (int)(Math.random() * 9) + 2;
		        if (shoot(target_row, target_column, board.get_array())) {
		        	//bot2 shoots at bot1 board and hits
		        	number_of_hits_left_bot2--;
		        	
		        } else {
		        	board.set_value(target_row, target_column, 'M');
		        }
		        board.print_board();
		        
		        player = 1;
		    }
		    
		    
		    if (number_of_hits_left_bot1 == 0) {
		    	 game_over = true;
		 	    System.out.print("BOT 1 IS VICTORIOUS!");
		 	
		    }
		    
		    if (number_of_hits_left_bot2 == 0) {
		    	 game_over = true;
		 	    System.out.print("BOT 2 IS VICTORIOUS!");
		 	
		    }

		    System.out.println("Press enter to continue...");
		    next_turn.nextLine();
		   round_counter++;
		} while (!game_over);
		next_turn.close();
	}
 
 void human_create_board(int ship_count, Board board) {
	 boolean valid = false;
	    char character = 0;
	    int R, C;
		String Cs;
	Scanner userInput = new Scanner(System.in);
	for (int i = 0; i < ship_count; i++) {
		System.out.print("Enter a position to mark as your ship. \n");

		do {

		//input for R must be => 0 AND <= 8
		System.out.print("enter row: ");

		do {
			R = userInput.nextInt();
			userInput.nextLine(); //consume

		//convert row entered into usable format for the 2D array:
		R = 9 - R;
		if (R >= 0 && R <=8) {
		    valid = true;
		} else {
		    System.out.print("invalid. Re-enter a correct row: ");
		}
		} while (!valid);
		//input for C must be => 2 AND =< 11
		valid = false; //reset valid to false
		System.out.print("enter column: ");


		do {
			
			
			
			//the user input is a string, converted into a char, converted into an int
			Cs = userInput.nextLine();
			character = Cs.charAt(0);
			C = (int) (character - 63);  
		// System.out.println(C);
		if (C >= 2 && C < 11) {
		    valid = true;
		} else {
		    System.out.print("invalid. Re-enter a correct column: ");
		}
		} while (!valid);
		valid = false;
		if (!check_empty_spot(board.get_array(), R, C)) {
		    System.out.print("\n The selected spot is already taken. \n");
		    valid = false;
		} else {
		    valid = true;
		}
		} while (!valid);




		board.set_value(R, C, 'X');
		//board[R][C] = 'X';
		valid = false;
		int end_C = -1;
		int end_R = -1;
		String input;

		System.out.print("Enter the ships orientation. \n");
		System.out.print("Enter L, R, U or D: ");
		do {
			input = userInput.nextLine();
		   
		    switch (input) {
		    case "L":
		        end_C = C - 2;
		        end_R = R;
		        valid = true;
		        break;
		    case "R":
		        end_C = C + 2;
		        end_R = R;
		        valid = true;
		        break;
		    case "U":
		        end_R = R - 2;
		          end_C = C;
		        valid = true;
		        break;
		    case "D":
		       
		        end_R = R + 2;
		          end_C = C;
		        valid = true;
		        break;
		    default:
		        System.out.print("Invalid. Enter L, R, U or D: ");
		        valid = false;
		        break;
		}
		    if (!validate_orientation(board.get_array(), R, C, end_R, end_C, input)) {
		    System.out.println("The orientation " + input + " is invalid. Enter another: ");
		    valid = false;
		} else {
		    valid = true;
		}
		} while (!valid);

		board.print_board();
		}//FOR LOOP


}
 boolean validate_orientation(char[][] board, int R, int C, int end_R, int end_C, String orientation) {
    
	char[][] temp_arr = new char[10][11];
    	for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 11; c++) {
				temp_arr[r][c] = board[r][c];
			}
		}
		//input for R must be => 0 AND <= 8
		if ((end_C >= 2 && end_C < 11) && (end_R >= 0 && end_R <= 8)) {
		    
		    //VALID!
		    	
		    	if (board[end_R][end_C] == 'X') {
		    		return false;
		    	}
		    	board[end_R][end_C] = 'X';
		    	switch (orientation) {
		    	    case "L":   
		    	          
		    	          	if (board[R][C - 1] == 'X') {
		    	          		return false;
		                	}
		                	 board[R][C - 1] = 'X';
		                	break;
		    	    case "R":   
		    	           
		    	            if (board[R][C + 1] == 'X') {
		    	            	return false;
		                	}
		                	 board[R][C + 1] = 'X';
		    	           break;
		    	    case "U":
		    	            
		    	          
		    	            if (board[R - 1][C] == 'X') {
		    	              return false;
		                	}
		                	  board[R - 1][C] = 'X';
		    	           break;
		    	    case "D":  
		    	    
		    	            
		    	            	if (board[R + 1][C] == 'X') {
		    	               return false;
		                	}
		                	board[R + 1][C] = 'X';
		    	           break;
		    	    default:
		    	        System.out.print("something went wrong !");
		    	    
		    	}
		    
		    return true;
		}  else {
		   // System.out.print("This orientation makes the ship fall off the board.");
		    return false;
		    
		}
		

}

 void ai_create_board(Board bot_board, int ship_count) {
    int max_R = 8;
    int max_C = 11;
    boolean valid = false;
    Random rand = new Random();
    int R = 0, C = 0;

    for (int i = 0; i < ship_count; i++) {
        // Use different variables for random positions
        int randomR, randomC;

        do {
            do {
                randomR = rand.nextInt(max_R + 1);
                if (randomR >= 0 && randomR <= 8) {
                    valid = true;
                 }
            } while (!valid);

            valid = false;

            do {
                randomC = rand.nextInt(max_C - 1) + 2; // = rand.nextInt(max_C – 1) + 2;
                if (randomC >= 2 && randomC < 11) {
                    valid = true;
                }
            } while (!valid);

            valid = false;

            if (!check_empty_spot(bot_board.get_array(), randomR, randomC)) {
                valid = false;
            } else {
                valid = true;
            }
        } while (!valid);

        bot_board.set_value(randomR, randomC, 'X');
        valid = false;
        int end_C = -1;
        int end_R = -1;
        String input = null;
        int upper_bound = 4;
        int lower_bound = 1;
        do {
            int value = rand.nextInt(upper_bound - lower_bound + 1) + lower_bound;

            switch (value) {
                case 1:
                    input = "L";
                    break;
                case 2:
                    input = "R";
                    break;
                case 3:
                    input = "U";
                    break;
                case 4:
                    input = "D";
                    break;
            }

            switch (input) {
                case "L":
                    end_C = randomC - 2;
                    end_R = randomR;
                    valid = true;
                    break;
                case "R":
                    end_C = randomC + 2;
                    end_R = randomR;
                    valid = true;
                    break;
                case "U":
                    end_R = randomR - 2;
                    end_C = randomC;
                    valid = true;
                    break;
                case "D":
                    end_R = randomR + 2;
                    end_C = randomC;
                    valid = true;
                    break;
                default:
                    valid = false;
                    break;
            }

            if (!validate_orientation(bot_board.get_array(), randomR, randomC, end_R, end_C, input)) {
                valid = false;
            } else {
                valid = true;
            }
        } while (!valid);
    }
}
 boolean shoot(int row, int column, char[][] b) {
	
    if (b[row][column] == 'X') {
        //target hit then:
    	char col = (char) (column + 63);
        System.out.print("\nThe shot at " + (9 - row) + " " + col + " was a hit! \n");
        b[row][column] = 'H';
        return true;
    } else {
        System.out.print("\nMISS! \n");
        b[row][column] = 'M';
        return false;
    }
    
}

}
