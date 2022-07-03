package game_classes;

public class Shotgunner extends Cave_Character {
	//instance fields
	
	
	
	//constructors
	public Shotgunner(String name, int max_hp, int x_coord, int y_coord, int base_difficulty, int do_damage_max,
			int do_damage_min, int direction, int range) {
		super(name, max_hp, x_coord, y_coord, base_difficulty, do_damage_max, do_damage_min, direction, range);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
		super.do_damage_max = 16;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 1;				//has range 1
	}

	public Shotgunner(String name) {
		super(name);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
		super.do_damage_max = 16;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 1;				//has range 1
	}

	public Shotgunner(Board board) {
		super(board);
		super.max_hp = 30;				//harder to kill
		super.current_hp = 30;
		super.do_damage_max = 16;		//does more damage
		super.do_damage_min = 2;		//better minimum damage
		super.range = 1;				//has range 1
	}
	
	
	
	//methods
	protected void shield() {
		super.current_hp = super.set_vars(0, super.max_hp, super.current_hp + 5);
	}
}
