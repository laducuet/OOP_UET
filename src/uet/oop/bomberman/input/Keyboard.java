package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener {
	private boolean[] keys = new boolean[120]; // 120 is enough to this game
	public boolean up, down, left, right, space;
	public boolean up1, down1, left1, right1, enter;

	public void update() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
	}

	public void update1() {
		up1 = keys[KeyEvent.VK_UP];
		down1 = keys[KeyEvent.VK_DOWN];
		left1 = keys[KeyEvent.VK_LEFT];
		right1 = keys[KeyEvent.VK_RIGHT];
		enter = keys[KeyEvent.VK_ENTER];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
