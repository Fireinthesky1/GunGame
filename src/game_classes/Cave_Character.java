package game_classes;

import cus_util.Cus_Util;

public class Cave_Character implements IChicken {
//instance fields--------------------------------
	protected String name;
	protected char symbol;
	protected int max_hp;
	protected int current_hp;
	protected int x_coord;
	protected int y_coord;
	protected int base_difficulty;
	protected int do_damage_max, do_damage_min;
	protected int direction;
	protected int range;

	
	
//Constructors--------------------------------
	public Cave_Character(String name, int max_hp, int x_coord, int y_coord, int base_difficulty, int do_damage_max, int do_damage_min, int direction, int range) {
		this.name = name;
		this.symbol = name.charAt(0);	//your symbol is the first letter of your name
		this.max_hp = max_hp;			
		this.current_hp = max_hp;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.base_difficulty = base_difficulty;
		this.do_damage_max = do_damage_max;
		this.do_damage_min = do_damage_min;
		this.direction = direction;
		this.range = range;
	}

	
	
	public Cave_Character(String name) {	//human constructor
		this.name = name;
		this.symbol = name.charAt(0);	
		this.max_hp = 20;			
		this.current_hp = max_hp;
		this.x_coord = 0;	//top left
		this.y_coord = 0;	//top left
		this.base_difficulty = 10;
		this.do_damage_max = 6;
		this.do_damage_min = 1;
		this.direction = 3; //south
		this.range = 3;
	}
	
	
	
	public Cave_Character(Board board) {	//computer constructor
		this.name = "Giorgio Tsukalos";
		this.symbol = name.charAt(0);	//G
		this.max_hp = 20;			
		this.current_hp = max_hp;
		this.x_coord = board.columns-1;	//top left
		this.y_coord = board.rows-1;	//top left
		this.base_difficulty = 10;
		this.do_damage_max = 6;
		this.do_damage_min = 1;
		this.direction = 1; //north
		this.range = 3;
	}
	
	
	
//Methods--------------------------------
	public boolean successful_action(int difficulty) {
		int roll = Cus_Util.gen_rand_num(1,20);	//simulation of rolling a D20
		if(roll >= difficulty) {	//if your roll was equal to or higher than the difficulty 
			return true;
		}else {
			return false;
		}
	}
	
	
	
	@Override
	public void hit(int damage) {
		this.current_hp = set_vars(0, this.max_hp, this.current_hp - damage);
	}

	
	
	@Override
	public boolean is_alive() {
		return this.current_hp > 0;
	}

	
	
	
	@Override
	public int set_vars(int min, int max, int var) {
		if(var < min) {
			var = min;
		}else if(var > max) {
			var = max;
		}
		return var;
	}

	
	
//Location Methods--------------------------------
	@Override
	public void change_direction(int direction) {
		this.direction = this.set_vars(1, 4, direction);
	}
	
	
	
	@Override
	public void move(Board board) {
		int[] new_position = new_increment(board);
		this.y_coord = new_position[0];
		this.x_coord = new_position[1];
	}
	
	
	
	private int[] increment_east(Board board){
		int x = set_vars(0,board.columns-1,this.x_coord+1);//add to x to go east
		int[] new_increment = {this.y_coord,x};
		return new_increment;
	}



	private int[] increment_west(Board board){
		int x = set_vars(0,board.columns,this.x_coord-1);//subtract from x to go west.  
		int[] new_increment = {this.y_coord,x};
		return new_increment;
	}



	private int[] increment_north(Board board){
		int y = set_vars(0,board.rows,this.y_coord-1);//subtract from y to go north
		int[] new_increment = {y,this.x_coord};
		return new_increment;
	}



	private int[] increment_south(Board board){
		int y = set_vars(0,board.rows-1,this.y_coord+1);// add to y to go south
		int[] new_increment = {y,this.x_coord};
		return new_increment;
	}
	
	
	
	private int[] new_increment(Board board){
		int[] increment_array = new int[2]; 
		switch(this.direction){
		case 1:
			increment_array = this.increment_north(board);
			break;
		case 2:
			increment_array = this.increment_east(board);
			break;
		case 3:
			increment_array = this.increment_south(board);
			break;
		case 4:
			increment_array = this.increment_west(board);
			break;
		}
		return increment_array;
	}
	
	
	
	public String facing() {
		switch(this.direction) {
		case 1:
			return "North";
		case 2:
			return "East";
		case 3:
			return "South";
		case 4:
			return "west";
		default:
			return "Error in facing() argument";
		}
	}
	
	
	
//firing methods-----------------------------------
	@Override
	public int[][] fire(Board board, int range) {
		int damage = Cus_Util.gen_rand_num(do_damage_min, do_damage_max);
		int[][] fire_solution = new int[range][3];
		for (int i = 1; i <= range; i++) {
			fire_solution[i-1][0] = this.firing_increment(board, range)[(i*2)-2];	//the y coordinates of the cells you're firing at
			fire_solution[i-1][1] = this.firing_increment(board, range)[(i*2)-1];		//the x coordinates of the cells you're firing at
		}

		if(successful_action(this.base_difficulty)){	//if the action is successful
			for(int i = 0; i < range; i++) {
				fire_solution[i][2] = damage;
			}
			System.out.println(this.get_name() + " does " + damage + " damage!");
		}else{	//if the attack misses
			System.out.println(this.name + " misses wildly!");//you've missed
			for(int i = 0; i < range; i++) {
				fire_solution[i][2] = 0;
			}
		}
		return fire_solution;	//return the fire solution (ycoord,xcoord,damage)
	}
	
	
	
	private int[] firing_increment(Board board, int range){
		int[] increment_array = new int[range*2]; 
		switch(this.direction){
		case 1:
			increment_array = this.fire_increment_north(board, range);
			break;
		case 2:
			increment_array = this.fire_increment_east(board, range);
			break;
		case 3:
			increment_array = this.fire_increment_south(board, range);
			break;
		case 4:
			increment_array = this.fire_increment_west(board, range);
			break;
		}
		return increment_array;
	}
	
	
	
	private int[] fire_increment_east(Board board, int range){
		int y = this.y_coord;
		int[] new_increment = new int[range*2];
		
		for (int i = 1; i <= range; i++) {
			int x = set_vars(0,board.columns,this.x_coord+i);//add to x to go east
			new_increment[(i*2)-2] = y;
			new_increment[(i*2)-1] = x;
		}
		return new_increment;
	}



	private int[] fire_increment_west(Board board, int range){
		int y = this.y_coord;
		int[] new_increment = new int[range*2];
		for (int i = 1; i <= range; i++) {
			int x = set_vars(0,board.columns,this.x_coord-i);//subtract from x to go west.
			new_increment[(i*2)-2] = y;
			new_increment[(i*2)-1] = x;
		}
		return new_increment;
	}



	private int[] fire_increment_north(Board board, int range){
		int x = this.x_coord;
		int[] new_increment = new int[range*2];
		for (int i = 1; i <= range; i++) {
			int y = set_vars(0,board.rows,this.y_coord-i);//subtract from y to go north
			new_increment[(i*2)-2] = y;
			new_increment[(i*2)-1] = x;
		}
		return new_increment;
	}



	private int[] fire_increment_south(Board board, int range){
		int x = this.x_coord;
		int[] new_increment = new int[range*2];
		for (int i = 1; i <= range; i++) {
			int y = set_vars(0,board.rows,this.y_coord+i);//add to y to go south
			new_increment[(i*2)-2] = y;
			new_increment[(i*2)-1] = x;
		}
		return new_increment;
	}
	
	
	
//getters and setters-----------------------------------
		public int get_range() {
			return this.range;
		}
	
	
	
		public String get_name() {
			return name;
		}



		public char get_symbol(){
			return this.symbol;
		}

		

		public int get_direction() {
			return direction;
		}



		public int get_current_hp() {
			return this.current_hp;
		}

		
		
		@Override
		public int[] get_location() {
			int[] location = {this.y_coord,this.x_coord};
			return location;
		}


		
		public String get_info() {
			String read_out = this.name + "\n";
			read_out += "Facing " + facing() + "\n";
			read_out += "Has " + this.current_hp + " hp\n";
			return read_out;
		}
}
