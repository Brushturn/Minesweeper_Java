package minesweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class testCell {
	Cell cell;
	Table table;
	
	@Before
	public void init() {
		table = new Table(5, 5, 6);
		table.getCell(2, 1).setMine(true);
		table.getCell(1, 0).setMine(true);
		cell = new Cell(2, 0, table);
	}
	
	@Test
	public void testMine() {
		assertEquals(false, cell.getMine());
		cell.setMine(true);
		assertEquals(true, cell.getMine());
	}
	
	@Test
	public void testFlag() {
		assertEquals(false, cell.getFlag());
		cell.flag();
		assertEquals(true, cell.getFlag());
	}
	
	@Test
	public void testMap() {
		cell.map();
		assertEquals(2, cell.getMineNum());
		cell.setMine(true);
		cell.map();
		assertEquals(-1, cell.getMineNum());
	}

}
