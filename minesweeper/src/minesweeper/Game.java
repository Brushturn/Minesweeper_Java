package minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Timer;

public class Game {
	//private static final long serialVersionUID = 1L;
	private Table table;
	private int cols;
	private int mines;
	private int rows;
	private String difficulty;
	private GUI frame;
	private String name;
	private boolean end = false;
	private Timer timer;
	private int time = 0;
	private boolean status = false;
	private Score result;
	private HashMap<String, Scoreboard> scoreboards = new HashMap<String, Scoreboard>(3);
	
	public static void main(String[] args) {
		Game uj = new Game();
	}
	
	public Game() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				++time; 
				//timerText.setText(Integer.toString(++time));
			}
		});
		
		frame = new GUI(this);
		frame.setVisible(true);
		
		Scoreboard easy = new Scoreboard("easyScoreboard.txt");
		Scoreboard medium = new Scoreboard("mediumScoreboard.txt");
		Scoreboard hard = new Scoreboard("hardScoreboard.txt");
		scoreboards.put("easy", easy);
		scoreboards.put("medium", medium);
		scoreboards.put("hard", hard);
	}
	
	public void startGame(String diff) {
		
		timer.start();
		
		difficulty = diff;
		
		if(diff.equals("easy")) {
			cols = 5;
			rows = 5;
			mines = 5;
		} else if(diff.equals("medium")) {
			cols = 10;
			rows = 10;
			mines = 20;
		} else if(diff.equals("hard")) {
			cols = 20;
			rows = 16;
			mines = 45;
		}
		
		table = new Table(rows, cols, mines);
		
	}
	
	private void endGame() {
		table.revealAll();
		end = true;
		timer.stop();
		result = new Score(name, time, difficulty);
	}
	
	private void winGame() {
		endGame();
		status = true;
		
		Scoreboard update = scoreboards.get(difficulty);
		update.add(result);
		scoreboards.put(difficulty, update);
	}
	
	private void loseGame() {
		endGame();
		status = false;
	}
	
	public int getRow() {
		return rows;
	}
	
	public int getCol() {
		return cols;
	}
	
	public void reveal(int r, int c) {
		boolean boom = table.getCell(r, c).reveal();
		if(boom == true) {
			loseGame();
			return;
		}
		if(table.checkEnd() == true) {
			winGame();
			return;
		}
	}
	
	public void flag(int r, int c) {
		table.flag(r, c);
	}
	
	public Table getTable() {
		return table;
	}

	public void setName(String n) {
		name = n;
	}

	public boolean getEnd() {
		return end;
	}
	
	public boolean gameWon() {
		if(end == true && status == true) return true;
		return false;
	}
	
	public String getTime() {
		return result.convertTime();
	}
	
	public Scoreboard getScoreboard(String diff) {
		return scoreboards.get(diff);
	}
}
