package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
	private JTextField nameField;
	private JButton startButton;
	private JComboBox difficultyBox;
	private GridLayout layout;
	private String difficulty;
	private JCheckBox edition;
	private JDialog result;
	private JPanel init;
	private JPanel mineField;
	private JPanel endPanel = null;
	private JMenuBar menuBar;
	private JDialog menuDialog;
	private JButton close;

	private Game game;
	private boolean firstClick = true;
	private boolean covidEdition = false;
	private final int buttonSize = 55;

	public final static String BOMB = new String(Character.toChars(128163));
	public final static String FLAG = new String(Character.toChars(9873));
	public final Icon bombCovid = new ImageIcon(new ImageIcon("mine_covid.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	public final Icon flagCovid = new ImageIcon(new ImageIcon("mask_covid.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
	public final Icon empty = new ImageIcon();
	private final Color[] buttonColors = {
	        Color.BLUE,
	        Color.CYAN.darker(),
	        Color.GREEN.darker(),
	        Color.YELLOW.darker(),
	        Color.ORANGE.darker(),
	        Color.PINK.darker(),
	        Color.MAGENTA,
	        Color.RED
		};
	
	public GUI(Game g) {
		game = g;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Minesweeper");
		this.setLocationRelativeTo(null); 
		this.setResizable(false);
		
		this.setSize(400, 150);
		
		nameField = new JTextField("Name", 20);
		nameField.setEditable(true);
			
		String setDiff[] = {"easy", "medium", "hard"};
		difficultyBox = new JComboBox(setDiff);
		
		startButton = new JButton("Start!");
		startButton.setActionCommand("start");
		startButton.addActionListener(new ButtonActionListener());
		
		edition = new JCheckBox("Covid edition");
		
		
		layout = new GridLayout(1, 1);
		this.setLayout(layout);
		
		init = new JPanel();
		init.add(difficultyBox);
		init.add(nameField);
		init.add(startButton);
		init.add(edition);
		
		setMenu();
		
		this.add(init);
	}
	
	private void setMenu() {
		
		MenuActionListener mal = new MenuActionListener();
		
		JMenu help = new JMenu("Help");
		JMenu scores = new JMenu("Scoreboard");
		
		JMenuItem rules = new JMenuItem("Rules");
		rules.setActionCommand("rules");
		rules.addActionListener(mal);
		help.add(rules);
		
		JMenuItem scoresE = new JMenuItem("Easy");
		scoresE.setActionCommand("easy");
		scoresE.addActionListener(mal);
		JMenuItem scoresM = new JMenuItem("Medium");
		scoresM.setActionCommand("medium");
		scoresM.addActionListener(mal);
		JMenuItem scoresH = new JMenuItem("Hard");
		scoresH.setActionCommand("hard");
		scoresH.addActionListener(mal);
		scores.add(scoresE);
		scores.add(scoresM);
		scores.add(scoresH);
		
		menuBar = new JMenuBar();
		menuBar.add(help);
		menuBar.add(scores);
		this.setJMenuBar(menuBar);
	}
	
	private void setMineField() {
		mineField = new JPanel(new GridLayout(game.getRow(), game.getCol()));
		
		CellClickMouseListener ml = new CellClickMouseListener();
		
		for (int i = 0; i < game.getRow(); i++) {
            for (int j = 0; j < game.getCol(); j++) {
                JButton b = new JButton();
                String idx = i + "-" + j;
                b.setName(idx);
                b.addMouseListener(ml);

                Cell active = game.getTable().getCell(i, j);
                
                if (active.getRevealed()) {
                	
                	b.setBackground(Color.getHSBColor(219, 0, 78.f/100.f));
                	
                	if (active.getMine()) {
                		if(covidEdition) {
                			b.setIcon(bombCovid); 
                		} else {
                			b.setText(BOMB);             			
                		}
                    } else if (active.getMineNum() > 0) {
                    	int count = active.getMineNum();
                        b.setForeground(buttonColors[count - 1]);
                        b.setText("" + count);
                    } else {
                        b.setText("");
                        b.setIcon(empty);
                    }
                } else {
                	if(active.getFlag()) {
                		if(covidEdition) {
                			b.setIcon(flagCovid);
                		} else {
                			b.setText(FLAG);
                		}
                	}
                }
                mineField.add(b);
            }
        }
	}
	
	private void setEndPanel() {
		endPanel = new JPanel();
    	
    	JLabel gameResult = new JLabel();
    	if(game.gameWon()) {
    		gameResult.setText("You win!!!");
    	} else {
    		gameResult.setText("You lose!!!");
    	}
    	
    	gameResult.setAlignmentX(CENTER_ALIGNMENT);
    	JLabel gameTime = new JLabel();
    	
    	gameTime.setText("Your time: " + game.getTime());
    	gameTime.setAlignmentX(CENTER_ALIGNMENT);

    	JButton restart = new JButton("Restart!");
    	restart.setActionCommand("restart");
    	restart.addActionListener(new ButtonActionListener());
    	restart.setAlignmentX(JPanel.CENTER_ALIGNMENT);
    	
    	JPanel scores = new JPanel();
		String text = game.getScoreboard(difficulty).toString();
		String formatted = text.replace("\n", "<br>");
		JLabel l = new JLabel("<html>"+formatted+"</html>");
		l.setHorizontalTextPosition(JLabel.CENTER);
		scores.add(l);

    	endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
    	endPanel.add(gameResult);
    	endPanel.add(gameTime);
    	endPanel.add(scores);
    	endPanel.add(restart);

    	
    	result = new JDialog(this);
    	if(game.gameWon()) {
    		result.setTitle("You win");
    	} else {
    		result.setTitle("You lose");
    	}
    	result.setLocationRelativeTo(null);
    	result.setSize(300, 300);
 	
    	result.add(endPanel);
   	
    	this.add(mineField);
    	result.setVisible(true);
	}
	
	private void drawGrid() {
		if(mineField != null) {
			this.remove(mineField);
		}
		
		setMenu();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Minesweeper");
		this.setResizable(true);
		
		this.setSize(game.getCol() * buttonSize, game.getRow() * buttonSize);

		setMineField();
        
        if(game.getEnd()) {
        	setEndPanel();
        	return;
        }
        this.remove(init);
        this.add(mineField);
        
	}
	
	private void openRules() {
		menuDialog = new JDialog(this);
		menuDialog.setTitle("Rules");
		menuDialog.setLocationRelativeTo(null);
		menuDialog.setSize(600, 300);
		
		String text = "A játékmezõt egy kétdimenziós téglalap alakú négyzetrács alkotja. Minden négyzetrács (továbbiakban:\r\n" + 
				"mezõ) vagy aknát rejt, vagy pedig semmit. Azon mezõk, amelyek nem tartalmaznak aknát, azt jelzik,\r\n" + 
				"hogy közvetlen szomszédságukban (fent, lent, jobbra, balra és átlósan) összesen hány akna (0-8 db)\r\n" + 
				"található. Az, hogy egy adott mezõ mit rejt, természetesen a játék kezdetekor nem látszik.\r\n" + 
				"A játékos a játék kezdetén választhat, hogy mekkora legyen a játékmezõ (kicsi, közepes, nagy), és ezzel\r\n" + 
				"eldönti, hogy milyen nehéz legyen a játék.\r\n" + 
				"A játékos egérrel kattinthat az egyes mezõkre. Aknára való kattintás a játék elvesztését eredményezi.\r\n" + 
				"Ha a játékos egy olyan mezõre kattint, ami nem akna, akkor a mezõn megjelenik, hogy összesen hány\r\n" + 
				"aknával szomszédos. A legelsõ kattintott mezõ biztosan nem rejt aknát.\r\n" + 
				"A játékos a szerinte aknát tartalmazó mezõket zászlóval láthatja el, ekkor a mezõ kattintásra nem fog\r\n" + 
				"reagálni.\r\n" + 
				"A játék akkor végzõdik gyõzelemmel, ha a játékos megtalálta az összes nem-akna mezõt. Amennyiben\r\n" + 
				"a játékos gyorsan tisztította le a pályát, akkor felkerül a toplistára.";
		
		JTextArea ta = new JTextArea(text);
		
		close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(new ButtonActionListener());
		
		JPanel p = new JPanel();
		p.add(ta);
		p.add(close);
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		menuDialog.add(p);
		menuDialog.setVisible(true);
		
	}
	
	private void openScoreboard(String diff) {
		JPanel scores = new JPanel();
		String text = game.getScoreboard(diff).toString();
		String formatted = text.replace("\n", "<br>");
		JLabel l = new JLabel("<html>"+formatted+"</html>");
		l.setHorizontalTextPosition(JLabel.CENTER);
		
		close = new JButton("Close");
		close.setActionCommand("close");
		close.addActionListener(new ButtonActionListener());
		close.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));
		
		scores.add(l);
		scores.add(close);
		
		menuDialog = new JDialog(this);
		menuDialog.setSize(300, 300);
		menuDialog.setTitle(diff + "scoreboard");
		menuDialog.add(scores);
		menuDialog.setVisible(true);
	}
	
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(ae.getActionCommand().equals("start")) {

				difficulty = (String)difficultyBox.getSelectedItem();
				covidEdition = edition.isSelected();
				
				game.startGame(difficulty);
				drawGrid();
				
				game.setName(nameField.getText());
				
			} else if(ae.getActionCommand().equals("restart")) {
				
				dispose();
				new Game();
				
			} else if(ae.getActionCommand().equals("close")) {
				menuDialog.dispose();
			}
		}
	}
	
	class MenuActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if(ae.getActionCommand().equals("rules")) {
				openRules();
			} else if(ae.getActionCommand().equals("easy")) {
				openScoreboard("easy");
			} else if(ae.getActionCommand().equals("medium")) {
				openScoreboard("medium");
			} else if(ae.getActionCommand().equals("hard")) {
				openScoreboard("hard");
			}
		}
	}
	
	class CellClickMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			if(game.getEnd()) return;
			
			JButton b = (JButton)e.getComponent();
			String[] idx = b.getName().split("-");
			int r = Integer.parseInt(idx[0]);
			int c = Integer.parseInt(idx[1]);
			
			Cell active = game.getTable().getCell(r, c);

			if(active.getRevealed()) return;

			if(SwingUtilities.isLeftMouseButton(e) == true) {
				if(firstClick) {
					game.getTable().fillTable(active);
					firstClick = false;
				}
				
				if(active.getFlag()) return;

				game.reveal(r, c);
				
				if(active.getMine() == false) {
					b.setText(Integer.toString(active.getMineNum()));
					b.setForeground(Color.gray);
				} else if(active.getMine() == true) {
					b.setIcon(new ImageIcon("mine_covid.png"));
				}
				
				
			} else if(SwingUtilities.isRightMouseButton(e) == true) {
				game.flag(r, c);
				if(active.getFlag() == true) {
					if(covidEdition) {
						b.setIcon(flagCovid);
					} else {
						b.setText(FLAG);
					}
				} else {
					if(covidEdition) {
						b.setIcon(empty);
					} else {
						b.setText("");
					}
					
				}
			}
			
			drawGrid();
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
}
