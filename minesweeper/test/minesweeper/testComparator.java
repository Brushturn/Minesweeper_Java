package minesweeper;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class testComparator {
	ScoreComparator sc;
	Score s1, s2;
	
	@Before
	public void init() {
		s1 = new Score("A", 15, "easy");
		s2 = new Score("B", 20, "easy");
		sc = new ScoreComparator();
	}
	
	@Test
	public void test() {
		assertEquals(true, sc.compare(s1, s2) < 0);
		assertEquals(true, sc.compare(s2, s1) > 0);
		
		Score s3 = new Score("C", 15, "easy");
		assertEquals(true, sc.compare(s1, s3) == 0);
	}

}
