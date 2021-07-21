package minesweeper;

public class Cell {
	private boolean mine = false;
	private boolean revealed = false;
	private boolean flagged = false;
	private int col;
	private int row;
	private int mineNum;
	private Table table;
	
	public boolean getMine() {
		return mine;
	}
	
	public boolean getFlag() {
		return flagged;
	}
	
	public int getMineNum() {
		return mineNum;
	}
	
	public boolean getRevealed() {
		return revealed;
	}
	
	public void setMine(boolean b) {
		mine = b;
	}
	
	public void flag() {
		flagged = !flagged;
	}
	
	public Cell(int rows, int cols, Table t) {
		col = cols;
		row = rows;
		table = t;
	}
	
	public void map() {
		if(mine == true) {
			mineNum = -1;
			return;
		}
		
		int count = 0;
		
		for(int i = row - 1; i <= row + 1; i++) {
			for(int j = col - 1; j <= col + 1; j++) {
				if(table.getCell(i, j) != null) {
					if(table.getCell(i, j).getMine() == true) count++;
				}
			}
		}
		
		mineNum = count;
	}
	
	public boolean reveal() {
		if(revealed == true) return false;
		if(flagged == true) return false;
		
		revealed = true;
		
		if(mine == true) {
			return true;
		}
		
		if(mineNum == 0) {
			for(int i = row - 1; i <= row + 1; i++) {
				for(int j = col - 1; j <= col + 1; j++) {
					if(table.getCell(i, j) != null) {
						table.getCell(i, j).reveal();
					}
				}
			}
		}
		return false;
	}

}
