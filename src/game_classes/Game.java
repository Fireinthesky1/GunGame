package game_classes;

import java.util.Arrays;

import java.util.Scanner;

import cus_util.Cus_Util;
//BUGS
/* -need to integrate subclass functionality into main game		   	complete
 * -make it so each subclass can do his unique move on his turn 	complete
 * -make it so you can only prone once							   	complete
 * -fix rocket launcher attack logic		                     	complete
 * -need to test boy scout class and rocket man class more         	complete
 * -health doesn't change with the corresponding class             	complete
 * -fix UI Readout                                                 	complete
 * -Need to implement subclass functionality to A.I.				complete
 * -A.I. stops taking turns if you end up south of it				complete
 * -Boy Scout is doing more damage than it should be able to		complete
 * -work on the prone logic											complete 
 * -reset the prone counter for computer when it moves				complete
 * -make board look nicer											complete
 * -moving off the board to the south and east causes a fatal error	complete
 * -Make the board change size when you play again.					complete
 * -fix the prompt for turn action									complete
 * -implement boy scout move logic for AI							complete*/
public class Game {
//instance fields-------------------------------------------------------------
	private Scanner scan = new Scanner(System.in);
	private boolean game_over = false;
	int turn;
	Cave_Character cc1, cc2;
	private Board board; //= new Board(Cus_Util.gen_rand_num(4, 15),Cus_Util.gen_rand_num(4, 15));
	String loadout;


	
	
//Constructor-------------------------------------------------------------
	public Game(Scanner scan) {
		display_info();

	}

	
	
	
//general methods-------------------------------------------------------------
	private void check_game_over() {
		if(!cc1.is_alive()) {
			System.out.println("Game over you lose!");
			game_over = true;
		}else if(!cc2.is_alive()) {
			System.out.println("The cyborg scum has been defeated! Game over!");
			game_over = true;
		}
	}
	
	
	
	private void move(Cave_Character cc) {
		//if boy scout
		if (cc == cc1 & cc instanceof BoyScout) {
			int num_spaces;
			System.out.println("Would you like to move 1 or 2 squares?");
			num_spaces = Cus_Util.get_user_input_int(scan, 1, 2);
			if(num_spaces == 2) {
				resolve_boyscout_move(cc);
				return;
			}
		}
		
		board.mark_board(cc.get_location()[0], cc.get_location()[1], ' ');
		cc.move(this.board);
		int ycoord = cc.get_location()[0];
		int xcoord = cc.get_location()[1];
		board.mark_board(ycoord, xcoord, cc.get_symbol());
		
		//if boy scout or sniper and prone then reset prone
		if((cc instanceof Sniper || cc instanceof BoyScout) && ((Sniper) cc).get_prone_counter() != 0) {	
			cc.base_difficulty = cc.set_vars(3, 6, cc1.base_difficulty + 3);	//reset your base difficulty
			((Sniper) cc).reset_prone_counter();								//reset the prone counter
		}
	}
	
	
	
	private void resolve_boyscout_move(Cave_Character cc) {
		board.mark_board(cc.get_location()[0], cc.get_location()[1], ' ');
		cc.move(this.board);
		cc.move(this.board);
		int ycoord = cc.get_location()[0];
		int xcoord = cc.get_location()[1];
		board.mark_board(ycoord, xcoord, cc.get_symbol());
	}
	
	
	
	private void human_change_direction() {	
		System.out.println("1: North\n2: East\n3: South\n4: West\n");
		int direction = Cus_Util.get_user_input_int(scan, 1, 4);
		cc1.change_direction(direction);
	}
	
	
	
	private void computer_change_direction(int direction) {
		cc2.change_direction(direction);
	}
	
	
	
	private void display_info() {	//display the rules of the game
		System.out.println("You wake up and find yourself in a disgusting cave. \nThe walls are damp, it smells of sulfur. As you come to your senses you see across the vast cavern there is"
				+ " a... a ... cyborg! \nHOLY SHIT IT'S GOT A GUN! Your head still pounding you look down to see a gun lying next to you... \nThe Game begins...");
	}
	
	
	
	public void play_game() {
		game_over = false;
		initialize_game();
		while(!game_over) {
			take_turns();
		}
	}
	
	
	
	private void initialize_game() {
		this.turn = Cus_Util.gen_rand_num(1, 2);
		this.board = new Board(Cus_Util.gen_rand_num(4, 15),Cus_Util.gen_rand_num(4, 15));
		board.initialize_board();
		initialize_characters();
	}
	
	
	
	private void take_turns() {
		if(turn % 2 == 0) {
			take_computer_turn();
			check_game_over();
		}else {
			take_human_turn();
			check_game_over();
		}
	}
	
	
	
	private void take_human_turn() {
		board.display_board();
		System.out.println();
		System.out.println(cc1.get_info());
		determine_human_move(scan);
		turn++;
	}
	
	
	
	private void take_computer_turn() {
		System.out.println(cc2.get_info());
		determine_computer_move();
		turn++;
	}
	
	
	
	private void determine_human_move(Scanner scan) {
		String response;
		switch(this.loadout) {
		case "pistol":
			System.out.println("What would you like to do: Move, Shoot, Turn, or wait?");
			break;                                                      
		case "shotgunner":                                              
			System.out.println("What would you like to do: Move, Shoot, Turn, shield or wait?");
			break;                                                                                                   
		case "rocketman":                                               
			System.out.println("What would you like to do: Move, Shoot, Turn, shield, or wait?");
			break;                                                      
		case "sniper":                                                 
			System.out.println("What would you like to do: Move, Shoot, Turn, prone or wait?");
			break;                                                      
		case "boyscout":                                                
			System.out.println("What would you like to do: Move, Shoot, Turn, prone, prepare, or wait?");
			break;
		default:
			System.out.println("Error reached in 'determine human move method'");
		}
		
		response = scan.nextLine();
		switch(response) {
		case "move":
			if(cc1 instanceof Rocketman && ((Rocketman) cc1).get_move_counter() % 2 != 0) {
				System.out.println("You are overencumbered and cannot run Rocketman!");
				break;
			}
			System.out.println(cc1.get_name() + " moves " + cc1.facing() + "...\n\n");
			move(cc1);
			break;
			
		case "shoot":
			System.out.println(cc1.get_name() + " shoots!\n\n");
			human_attack();
			break;
			
		case "turn":
			human_change_direction();
			System.out.println(cc1.get_name() + " turns toward the " + cc1.facing() +  "...\n\n");
			break;
			
		case "wait":
			System.out.println(cc1.get_name() + " waits...\n\n");
			break;
			
		case "shield":
			if(cc1 instanceof Shotgunner || cc1 instanceof Rocketman) {
				System.out.println(cc1.get_name() + " raises their shield...\n\n");
				((Shotgunner) cc1).shield();
			}else {
				System.out.println("You don't have a shield. Try again.");
				determine_human_move(this.scan);
			}
			break;
			
		case "prone":
			if((cc1 instanceof Sniper || cc1 instanceof BoyScout) && ((Sniper) cc1).get_prone_counter() == 0) {
				System.out.println(cc1.get_name() + " goes prone...\n\n");
				((Sniper) cc1).go_prone();
			}else if ((cc1 instanceof Sniper || cc1 instanceof BoyScout) && ((Sniper) cc1).get_prone_counter() != 0) {
				System.out.println("You are already prone! Try again!");
				determine_human_move(this.scan);
			}else {
				System.out.println("You aren't a Sniper or a BoyScout. Try again.");
				determine_human_move(this.scan);
			}
			break;
			
		case "prepare":
			if(cc1 instanceof BoyScout) {
				((BoyScout) cc1).Prepare();
			}else {
				System.out.println("You aren't a BoyScout. Try again.");
				determine_human_move(this.scan);
			}
			break;
			
		default:
			System.out.println("Invalid (use lower case)");
			determine_human_move(this.scan);
			break;
		}
	}
	
	
	
	private void determine_computer_move() {
		//are you a rocket man?
		if(cc2 instanceof Rocketman) {
			determine_computer_rocketman_move();
			return;
		}
		
		//are you a boy scout?
		if(cc2 instanceof BoyScout) {	//first thing a boy scout ai will do is prepare
			if(((BoyScout) cc2).get_preparation_counter() == 0) {
				((BoyScout) cc2).Prepare();
				return;
			}
		}
		
		//are you a shot gunner?
		if(cc2 instanceof Shotgunner) {
			if(cc2.get_current_hp() < 10) {
				((Shotgunner) cc2).shield();
				return;
			}
		}
		
		//are you a Sniper?
		if(cc2 instanceof Sniper) {	
			if(((Sniper) cc2).get_prone_counter() == 0) {
				resolve_computer_sniper_move();
				if(((Sniper) cc2).get_prone_counter() != 0) {
					return;
				}				
			}
		}
		
		//otherwise
		int[] human_location = cc1.get_location();
		int[] computer_location = cc2.get_location();
		int computer_direction = cc2.get_direction();
		int computer_range = cc2.get_range();
		
		//determine north south standing(get into range)-------------------------------------------
		if(human_location[0] < computer_location[0]-computer_range && computer_direction == 1) {	//if the enemy is north & i'm facing north move north
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[0] + 1 == computer_location[0]-computer_range) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[0] < computer_location[0]-computer_range && computer_direction != 1) {	//if enemy is north & i'm NOT facing north turn north
			computer_change_direction(1);//north
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}else if(human_location[0] > computer_location[0]+computer_range && computer_direction == 3) {	//if the enemy is south & i'm facing south move south
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[0] - 1 == computer_location[0]+computer_range) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[0] > computer_location[0]+computer_range && computer_direction != 1) {	//if the enemy is south & i'm NOT facing south turn south
			computer_change_direction(3);//south
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}
		
		//determine east west standing(get into range)-------------------------------------------
		else if(human_location[1] < computer_location[1]-computer_range && computer_direction == 4) {	//if the enemy is west & i'm facing west move west
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[1] + 1 == computer_location[1]-computer_range) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[1] < computer_location[1]-computer_range && computer_direction != 4) {	//if enemy is west & i'm NOT facing west turn west
			computer_change_direction(4);//west
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}else if(human_location[1] > computer_location[1]+computer_range && computer_direction == 2) {	//if the enemy is east & i'm facing east move east
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[1] - 1 == computer_location[1]+computer_range) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[1] > computer_location[1]+computer_range && computer_direction != 2) {	//if the enemy is east & i'm NOT facing east turn east
			computer_change_direction(2);//east
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}
		
		//if you are already in y coord firing position then fire-------------------------------------------
		else if (human_location[0] == computer_location[0] && //if same y and enemy is west by less than or equal to my range and I'm facing west then shoot
				(human_location[1] >= computer_location[1] - computer_range && 	
				human_location[1] < computer_location[1]) &&	
				computer_direction == 4) {	
			System.out.println(cc2.get_name() + " shoots!");
			computer_attack();
		}else if (human_location[0] == computer_location[0] && //if same y and enemy is west by less than or equal to my range and I'm NOT facing west then turn west
				(human_location[1] >= computer_location[1] - computer_range && 
				human_location[1] < computer_location[1]) &&
				computer_direction != 4) {	
			computer_change_direction(4);//west
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}else if (human_location[0] == computer_location[0] && //if same y and enemy is east by my range and I'm facing east then shoot
				(human_location[1] <= computer_location[1] + computer_range && 
				human_location[1] > computer_location[1]) &&
				computer_direction == 2) {	
			System.out.println(cc2.get_name() + " shoots!");
			computer_attack();
		}else if (human_location[0] == computer_location[0] && //if same y and enemy is east by two or one and I'm not facing east then turn east
				(human_location[1] <= computer_location[1] + computer_range && 
				human_location[1] > computer_location[1]) &&
				computer_direction != 4) {	
			computer_change_direction(2);//east
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}
		
		//otherwise get into x coord firing position-------------------------------------------
		else if(human_location[1] < computer_location[1] && computer_direction == 4) {	//if the enemy is west & i'm facing west move west	
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[1] + 1 == computer_location[1]) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[1] < computer_location[1] && computer_direction != 4) {	//if enemy is west & i'm NOT facing west turn west
			computer_change_direction(4);//west
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}else if(human_location[1] > computer_location[1] && computer_direction == 2) {	//if the enemy is east & i'm facing east move east
			System.out.println(cc2.get_name() + " moved " + cc2.facing() + "...");
			if(cc2 instanceof BoyScout) {
				if(human_location[1] - 1 == computer_location[1]) {
					move(cc2);
					return;
				}else {
					resolve_boyscout_move(cc2);
					return;
				}
			}
			move(cc2);
		}else if(human_location[1] > computer_location[1] && computer_direction != 2) {	//if the enemy is east & i'm NOT facing east turn east
			computer_change_direction(2);//east
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}
		
		//get into y coord firing facing and fire-------------------------------------------
		else if(human_location[0] < computer_location[0] && computer_direction == 1) {	//if the enemy is north & i'm facing north fire
			System.out.println(cc2.get_name() + " shoots!");
			computer_attack();
		}else if(human_location[0] < computer_location[0] && computer_direction != 1) {	//if enemy is north & i'm NOT facing north turn north
			computer_change_direction(1);//north
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}else if(human_location[0] > computer_location[0] && computer_direction == 3) {	//if the enemy is south & i'm facing south fire
			System.out.println(cc2.get_name() + " shoots!");
			computer_attack();
		}else if(human_location[0] > computer_location[0] && computer_direction != 3) {	//if the enemy is south & i'm NOT facing south turn south
			computer_change_direction(3);//south
			System.out.println(cc2.get_name() + " turns toward the " + cc2.facing() +  "...");
		}
	}
	
	
	
	private void resolve_computer_sniper_move() {	//if the conditions are right to go prone then go prone
		int[] human_location = cc1.get_location();
		int[] computer_location = cc2.get_location();
		int computer_direction = cc2.get_direction();
		int computer_range = cc2.get_range();
		int human_range = cc1.get_range();
		
		//If not already prone && if enemy is in range && you're out of their range ----> go prone

		if(human_location[0] >= computer_location[0]-computer_range && human_location[0] < computer_location[0] &&					//if the enemy is north and in range 
				computer_direction == 1 && human_location[1] == computer_location[1] && 											//if I'm facing north and we're are on the same x coordinate
				human_location[0] + human_range < computer_location[0]) {															//if I'm outside of human range
			((Sniper) cc2).go_prone();
			System.out.println(cc2.get_name() + " went prone.");
		}else if(human_location[0] <= computer_location[0]+computer_range && human_location[0] > computer_location[0] &&			//if the enemy is south and in range
				computer_direction == 3 && human_location[1] == computer_location[1] &&												//if I'm facing south and we're on the same x coordinate
				human_location[0] - human_range > computer_location[0]) {															//if I'm outside of human range
			((Sniper) cc2).go_prone();
			System.out.println(cc2.get_name() + " went prone.");
		}else if(human_location[1] >= computer_location[1]-computer_range && human_location[1] < computer_location[1] &&			//if the enemy is west and in range
				computer_direction == 4 && human_location[0] == computer_location[0] &&												//if i'm facing west and we're on the same y coordinate
				human_location[1] + human_range < computer_location[1]) {															//if i'm outside of human range
			((Sniper) cc2).go_prone();
			System.out.println(cc2.get_name() + " went prone.");
		}else if (human_location[1] <= computer_location[1] + computer_range  && human_location[1] > computer_location[1] && 		//in enemy is east and in range
				computer_direction == 2 && human_location[0] == computer_location[0] &&												//if i'm facing east and we're on the same y coordinate
				human_location[1] - human_range > computer_location[1]) {															//if i'm outside of human range
			((Sniper) cc2).go_prone();	
			System.out.println(cc2.get_name() + " went prone.");
		}

	}
	
	
	
	private void determine_computer_rocketman_move() {
		if(cc2.get_current_hp() < 10) {
			((Rocketman) cc2).shield();
		}else {
			rocketman_attack(cc2, cc1);
		}
	}
	
	
	
	private void initialize_characters() {
		initialize_human_character();
		initialize_computer_character();
	}
	
	
	
	private void initialize_human_character() {
		String name;
		System.out.println("Select your Weapon: pistol, shotgun, sniper, bb-gun, or rocket-launcher");
		this.loadout = scan.nextLine();
		switch(this.loadout) {
		case "pistol":
			this.loadout = "pistol";
			System.out.println("Enter your name");
			name = scan.nextLine();
			cc1 = new Cave_Character(name);
			break;
		case "shotgun":
			this.loadout = "shotgunner";
			System.out.println("Enter your name");
			name = scan.nextLine();
			cc1 = new Shotgunner(name);
			break;
		case "sniper":
			System.out.println("Enter your name");
			name = scan.nextLine();
			cc1 = new Sniper(name);
			break;
		case "bb-gun":
			this.loadout = "boyscout";
			System.out.println("Enter your name");
			name = scan.nextLine();
			cc1 = new BoyScout(name);
			break;
		case "rocket-launcher":
			this.loadout = "rocketman";
			System.out.println("Enter your name");
			name = scan.nextLine();
			cc1 = new Rocketman(name);
			break;
		default:
			System.out.println("Not a loadout. Try again. (use lower case)");
			initialize_human_character();
			break;
		}
		board.mark_board(0, 0, cc1.get_symbol());
	}
	
	
	
	private void initialize_computer_character() {
		int computer_loadout = Cus_Util.gen_rand_num(1, 5);	
		switch(computer_loadout) {
		case 1:
			cc2 = new Cave_Character(board);
			break;
		case 2:
			cc2 = new Shotgunner(board);
			break;
		case 3:
			cc2 = new Sniper(board);
			break;
		case 4:
			cc2 = new Rocketman(board);
			break;
		case 5:
			cc2 = new BoyScout(board);
			break;
		}
		board.mark_board(board.rows-1, board.columns-1, cc2.get_symbol());
	}
	
	
	
//attack methods-----------------------------------------
	private boolean hit_target(int[][] hit_locations, Cave_Character shooter, Cave_Character target) {
		int[] target_position = target.get_location();
		int range = shooter.get_range();
		
		for(int i = 0; i < range; i++) {
			int[] cell = {hit_locations[i][0], hit_locations[i][1]};	//go through every cell
			if(Arrays.equals(target_position, cell)) {	//if the target is in one of these cells return true
				return true;
			}
		}
		return false;	//if the target was in none of those cells return false
	}
	
	
	
	private void computer_attack() {
		int[][] hit_locations = cc2.fire(this.board, cc2.get_range());
		int damage = hit_locations[0][2];
		if(hit_target(hit_locations, cc2, cc1)) {
			cc1.hit(damage);
		}
	}
	
	
	
	private void human_attack() {
		//if you are a rocket man
		if(cc1 instanceof Rocketman) {
			rocketman_attack(cc1, cc2);
			return;
		}
		
		//if you aren't a rocket man
		int[][] hit_locations = cc1.fire(this.board, cc1.get_range());
		int damage = hit_locations[0][2];
		if(hit_target(hit_locations, cc1, cc2)) {
			cc2.hit(damage);
		}
	}
	
	
	
	private void rocketman_attack(Cave_Character shooter, Cave_Character target) {
		if(shooter instanceof Rocketman && ((Rocketman) shooter).get_shot_counter() % 4 == 0) {	//if you are a rocket man and you aren't reloading
			if(Cus_Util.gen_rand_num(1, 20) >= ((Rocketman) shooter).base_difficulty) {
				int damage = Cus_Util.gen_rand_num(5, 30);
				target.hit(damage);
				System.out.println(shooter.get_name() + " does " + damage + " damage!");
				((Rocketman) shooter).increment_shot_counter();
				return;
			}else {
				System.out.println(shooter.get_name() + "'s missile missed!");
				((Rocketman) shooter).increment_shot_counter();
				return;
			}
		}else if(shooter instanceof Rocketman && ((Rocketman) shooter).get_shot_counter() % 4 != 0) {	//if you are a rocket man and you are reloading
			System.out.println(shooter.get_name() + " is reloading!");
			((Rocketman) shooter).increment_shot_counter();
			return;
		}
	}
}
