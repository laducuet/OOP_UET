package uet.oop.bomberman.gui;

import java.awt.*;
import javax.swing.*;
import uet.oop.bomberman.Game;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {
	private JLabel pointsLabel;
	private JLabel livesLabel;
	private JLabel emptyLabel_1;
	private JLabel emptyLabel_2;
	private JLabel emptyLabel_4;
	private JLabel levelsLabel;

	private ImageIcon left_bar =
			new ImageIcon((new ImageIcon("res/textures/left-bar.png"))
					.getImage()
					.getScaledInstance(300, 30, Image.SCALE_DEFAULT));
	private ImageIcon center_bar =
			new ImageIcon((new ImageIcon("res/textures/center-bar.png"))
					.getImage()
					.getScaledInstance(300, 30, Image.SCALE_DEFAULT));
	private ImageIcon right_bar =
			new ImageIcon((new ImageIcon("res/textures/right-bar.png"))
					.getImage()
					.getScaledInstance(300, 30, Image.SCALE_DEFAULT));

	public InfoPanel(Game game) {
		setLayout(new GridLayout(1, 3));

		Color color = new Color(99, 171, 63, 255);
		emptyLabel_1 = new JLabel();
		emptyLabel_2 = new JLabel();
		emptyLabel_4 = new JLabel();

		pointsLabel = new JLabel();
		pointsLabel.setIcon(left_bar);
		pointsLabel.setText("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);
		pointsLabel.setHorizontalTextPosition(JLabel.CENTER);

		levelsLabel = new JLabel();
		levelsLabel.setIcon(center_bar);
		levelsLabel.setText("Level: " + game.getBoard().getLevels());
		levelsLabel.setForeground(Color.white);
		levelsLabel.setHorizontalAlignment(JLabel.CENTER);
		levelsLabel.setHorizontalTextPosition(JLabel.CENTER);

		livesLabel = new JLabel();
		livesLabel.setIcon(right_bar);
		livesLabel.setText("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.white);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);
		livesLabel.setHorizontalTextPosition(JLabel.CENTER);

		add(pointsLabel);
		add(levelsLabel);
		add(livesLabel);

		setBackground(color);
		setPreferredSize(new Dimension(0, 40));
	}

	public void setLives(int t) {
		livesLabel.setText("Lives: " + t);
	}

	public void setLevels(int t) {
		levelsLabel.setText("Level: " + t);
	}
	public void setPoints(int t) {
		pointsLabel.setText("Points: " + t);
	}

	class FixedStateButtonModel extends DefaultButtonModel {
		FixedStateButtonModel() {}

		public boolean isPressed() {
			return false;
		}

		public boolean isRollover() {
			return false;
		}

		public void setRollover(boolean b) {}
	}
}
