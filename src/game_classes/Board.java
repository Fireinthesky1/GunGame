package game_classes;

import cus_util.Cus_Util;

public class Board {
//instance fields
	private char[][] board;
	int rows, columns;
	
	
	
//constructor
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[rows][columns];
		this.initialize_board();
	}
	
	
	
//methods
	public void initialize_board() {
		int rand_num = 0;
		char cell = ' ';
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = cell;
			}
		}
	}
	
	
	
	public void display_board() {	//Usable for any size board except 1X1
		String horizontal_bar = "||";
		String top_line_begin = "||===";
		String top_line_middle = "||===";
		String top_line_end = "||===||";
		String middle_line = "||===";
		String middle_line_end = "||===||";
		String bottom_line_begin = "||===";
		String bottom_line_middle = "||===";
		String bottom_line_end = "||===||";
		
//top line-------------------------------------------------------------
		System.out.print(top_line_begin);
		for (int i = 0; i < columns-2; i++) {
			System.out.print(top_line_middle);
		}
		System.out.println(top_line_end);
	
//horizontal bars-------------------------------------------------------------		
		for(int i = 0;i<rows;i++){
			for(int j = 0;j< columns;j++){
				System.out.print(horizontal_bar +" "+ board[i][j]+ " ");
			}
			System.out.print(horizontal_bar);
			System.out.println();
			
//bottom line-------------------------------------------------------------
			if(i == rows-1){
				System.out.print(bottom_line_begin);
				for (int b = 0; b < columns-2; b++) {
					System.out.print(bottom_line_middle);
				}
				System.out.print(bottom_line_end);
				
//middle lines-------------------------------------------------------------
			}else{
				System.out.print(middle_line);
				for (int c = 0; c < columns-2; c++) {
					System.out.print(middle_line);
				}
				System.out.println(middle_line_end);
			}
		}
	}
	
	
	
	
	public void mark_board(int y, int x, char symbol) {
		this.board[y][x] = symbol;
	}
	
	
	
//setters and getters-------------------------------------------------------------
	public int get_rows() {
		return this.rows;
	}
	
	
	
	public int get_columns() {
		return this.columns;
	}
}
