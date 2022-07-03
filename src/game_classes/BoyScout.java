package game_classes;
/*- Boy scout is a subclass of the sniper.
 * -Boy scout can move two spaces and go prone
 * -Boy scout shoots a BB gun and does 1d4 damage
 * -Boy scout has a lower base difficulty better at hitting target
 * -Boy scout has a range of the sniper
 * -Boy scout can choose to shoot twice in a round*/
public class BoyScout extends Sniper {
	//intance fields
	private int preparation_counter;
	
	
	
	//constructors
	public BoyScout(String name, int max_hp, int x_coord, int y_coord, int base_difficulty, int do_damage_max,
			int do_damage_min, int direction, int range) {
		super(name, max_hp, x_coord, y_coord, base_difficulty, do_damage_max, do_damage_min, direction, range);
		super.max_hp = 15;				//easier to kill
		super.current_hp = 15;
		super.base_difficulty = 6;		//better at hitting his target
		super.do_damage_max = 4;		//does bb gun damage
		super.do_damage_min = 1;		//bad min damage
		super.range = 9;				//higher range
	}

	
	
	public BoyScout(String name) {
		super(name);
		super.max_hp = 15;				//easier to kill
		super.current_hp = 15;
		super.do_damage_max = 4;		//does bb gun damage
		super.do_damage_min = 1;		//bad min damage
		super.range = 9;				//higher range
	}

	
	
	public BoyScout(Board board) {
		super(board);
		super.max_hp = 15;				//easier to kill
		super.current_hp = 15;
		super.do_damage_max = 4;		//does bb gun damage
		super.do_damage_min = 1;		//bad min damage
		super.range = 9;				//higher range
	}

	
	
	//methods
	public void Prepare() {
		if(preparation_counter == 0) {
			System.out.println(super.get_name() + " in true Boy Scout fashion prepares for the next turn!");
			super.do_damage_max += 5;
			super.current_hp = super.set_vars(0, super.max_hp, super.current_hp + 5);
			super.range += 5;
			preparation_counter++;
		}else {
			System.out.println(super.get_name() + " attempts to prepare but has already prepared.");
		}
	}
	
	
	
	//getters and setters
	public int get_preparation_counter() {
		return this.preparation_counter;
	}
}
