package minesweeper;

import java.util.Random;

public class Table {
	private int col;
	private int row;
	private int mineNum;
	private Cell[][] matrix;
	Random rand = new Random();
	
	public Table(int rows, int columns, int mines) {
		col = columns;
		row = rows;
		mineNum = mines;
		
		matrix = new Cell[row][col];
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				matrix[i][j] = new Cell(i, j, this);
			}
		}
	}
	
	public void fillTable(Cell starter) {
		for(int i = 0; i < mineNum; i++) {
			int r = rand.nextInt(row);
			int c = rand.nextInt(col);
			while(matrix[r][c] == starter || matrix[r][c].getMine() == true) {
				r = rand.nextInt(row);
				c = rand.nextInt(col);
			}
			matrix[r][c].setMine(true);
		}
		
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				matrix[i][j].map();
			}
		}

	}
	
	public Cell getCell(int r, int c) {
		if(r < 0 || r > row - 1) return null;	
		if(c < 0 || c > col - 1) return null;
		
		return matrix[r][c];
	}
	
	public void revealAll() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(matrix[i][j].getFlag()) {
					flag(i, j);
				}
				reveal(i, j);
			}
		}
	}
	
	public boolean reveal(int r, int c) {
		return matrix[r][c].reveal();
	}
	
	public void flag(int r, int c) {
		matrix[r][c].flag();
	}
	
	public void write() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				if(matrix[i][j].getFlag()) {
					System.out.print("f\t");
				} else {
					System.out.print(matrix[i][j].getMineNum() + "\t");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public boolean checkEnd() {
		for(int r = 0; r < row; ++r) {
			for(int c = 0; c < col; ++c) {
				if(!matrix[r][c].getMine()) {
					if(!matrix[r][c].getRevealed()) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
