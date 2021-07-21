package minesweeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Score> list = new ArrayList<Score>(10);
	private String file;
	
	public Scoreboard(String filename) {
		file = filename;
		try {
			addData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add(Score newScore) {
		if(list.size() == 0) {
			list.add(newScore);
		} else {
			if(list.size() == 10) {
				Score max = Collections.max(list, new ScoreComparator());
				if(new ScoreComparator().compare(newScore, max) < 0) {
					list.remove(max);		
				} else {
					return;
				}
			}
			list.add(newScore);
			Collections.sort(list, new ScoreComparator());
		}
		
		try {
			saveData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addData() throws FileNotFoundException, IOException, ClassNotFoundException {
		File scoreboardFile = new File(file);
		if(scoreboardFile.exists() == false) return;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		list = (ArrayList<Score>)in.readObject();
		in.close();
	}
	
	private void saveData() throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(list);
		out.close();
	}

	public String toString() {
		String results = "";
		int place = 1;
		for(Score i: list) {
			results += place + ".\t"+ i.toString() + "\n";
			place++;
		}
		return results;
	}
	
	public String getPlace(int place) {
		return list.get(place - 1).toString();
	}
}
