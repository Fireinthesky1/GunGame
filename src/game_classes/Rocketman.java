package game_classes;
/*Variant of the shot gunner class that can launch rockets. The rockets are heat seeking and do 5D6 damage but can only be fired every four rounds. 
 *Additionally the rocket man can only move once every other turn.
 *The rocket man has as many hit points as the shot gunner
 *The rocket man has a reduced chance to hit but does more damage and doesn't need to be facing the opponent to hit.
 **/
public class Rocketman extends Shotgunner {
	//instance fields
	private int shot_counter;
	private int move_counter;
	
	
	
	//constructors
	public Rocketman(String name, int max_hp, int x_coord, int y_coord, int base_difficulty, int do_damage_max,
			int do_damage_min, int direction, int range) {
		super(name, max_hp, x_coord, y_coord, base_difficulty, do_damage_max, do_damage_min, direction, range);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
	}

	
	
	public Rocketman(String name) {	//human constructor
		super(name);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
	}

	
	
	public Rocketman(Board board) {	//computer constructor
		super(board);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
	}

	
	
	//methods
	
	
	
	//setters and getters
	public int get_move_counter() {
		return this.move_counter;
	}
	
	
	
	public int get_shot_counter() {
		return this.shot_counter;
	}
	
	
	
	public void increment_shot_counter() {
		this.shot_counter++;
	}
}
