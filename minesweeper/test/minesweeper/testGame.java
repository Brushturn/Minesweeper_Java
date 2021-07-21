package minesweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testGame {
	Game game;
	Table table;
	
	@Before
	public void init() {
		game = new Game();
		game.startGame("easy");
		table = game.getTable();
		table.fillTable(table.getCell(0, 0));
	}
	
	@Test
	public void testLose() {
		table.getCell(0, 0).setMine(true);
		game.reveal(0, 0);
		assertEquals(true, table.getCell(0, 0).getMine());
		
		assertEquals(true, game.getEnd());
		assertEquals(false, game.gameWon());
	}
	
	@Test
	public void testWin() {
		for(int i = 0; i < game.getRow(); i++) {
			for(int j = 0; j < game.getCol(); j++) {
				if(game.getTable().getCell(i, j).getMine() == false) {
					game.reveal(i, j);
				}
			}
		}
		assertEquals(true, game.getEnd());
		assertEquals(true, game.gameWon());
	}

}
