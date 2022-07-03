package game_classes;

public interface IChicken {
	public void move(Board board);
	public int[][] fire(Board board, int range);
	public void change_direction(int direction);
	public int[] get_location();
	public void hit(int damage);
	public boolean is_alive();
	public int set_vars(int min, int max,int var);
}
