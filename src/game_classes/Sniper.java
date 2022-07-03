package game_classes;

import cus_util.Cus_Util;

public class Sniper extends Cave_Character {
	//instance fields
	private int prone_counter;
	
	
	
	//constructors
	public Sniper(String name, int max_hp, int x_coord, int y_coord, int base_difficulty, int do_damage_max,
			int do_damage_min, int direction, int range) {
		super(name, max_hp, x_coord, y_coord, base_difficulty, do_damage_max, do_damage_min, direction, range);
		super.max_hp = 10;				//easier to kill
		super.current_hp = 10;
		super.base_difficulty = 6;		//better at hitting his target
		super.do_damage_max = 12;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 9;				//has a higher range
	}

	
	
	public Sniper(String name) {
		super(name);
		super.max_hp = 10;				//easier to kill
		super.current_hp = 10;
		super.base_difficulty = 6;		//better at hitting his target
		super.do_damage_max = 12;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 9;				//higher range
	}

	
	
	public Sniper(Board board) {
		super(board);
		super.max_hp = 10;				//easier to kill
		super.current_hp = 10;
		super.base_difficulty = 6;		//better at hitting his target
		super.do_damage_max = 12;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 9;				//higher range
	}
	
	
	
	//methods
	public void go_prone() {
		super.base_difficulty = super.set_vars(1, super.base_difficulty, super.base_difficulty - 3);
		this.prone_counter++;
	}
	
	
	
	//getters and setters
	public int get_prone_counter() {
		return this.prone_counter;
	}
	
	
	
	public void reset_prone_counter() {
		this.prone_counter = 0;														//reset the prone counter
		super.base_difficulty = super.set_vars(3, 6, super.base_difficulty + 3);	//reset your base difficulty
	}
}
