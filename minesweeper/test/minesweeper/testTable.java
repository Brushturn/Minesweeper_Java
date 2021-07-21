package minesweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testTable {
	Table table;
	
	@Before
	public void init() {
		table = new Table(5, 5, 6);
	}
	
	@Test
	public void testFillTable() {
		table.fillTable(table.getCell(0, 0));
		assertEquals(false, table.getCell(0, 0).getMine());
		int n = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(table.getCell(i, j).getMine()) n++;
			}
		}
		assertEquals(6, n);
	}
	
	@Test
	public void testInit() {
		int n = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(table.getCell(i, j).getMine()) n++;
			}
		}
		assertEquals(0, n);
	}
	
	@Test
	public void testReveal() {
		table.reveal(0, 0);
		assertEquals(true, table.getCell(0, 0).getRevealed());
	}
	
	@Test
	public void testRevealAll() {
		table.revealAll();
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				assertEquals(true, table.getCell(i, j).getRevealed());
			}
		}
	}

}
