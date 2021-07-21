package minesweeper;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testScore {
	Score score;
	
	@Before
	public void init() {
		score = new Score("PeldaBela", 75, "medium");
	}
	
	
	@Test
	public void testWrite() {
		assertEquals("PeldaBela\t1:15", score.toString());
	}

}
