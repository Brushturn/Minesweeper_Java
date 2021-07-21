package minesweeper;

import java.io.Serializable;

public class Score implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int time;
	private String difficulty;
	
	public Score(String n, int t, String diff) {
		name = n;
		time = t;
		difficulty = diff;
	}
	
	public int getTime() {
		return time;
	}
	
	public String convertTime() {
		return Integer.toString(time/60) + ":" + Integer.toString(time%60);
	}
	
	public String toString() {
		return name + "\t" + convertTime();
	}

}
