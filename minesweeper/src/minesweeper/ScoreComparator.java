package minesweeper;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
	public int compare(Score s1, Score s2) {
		return s1.getTime() - s2.getTime();
	}
}
