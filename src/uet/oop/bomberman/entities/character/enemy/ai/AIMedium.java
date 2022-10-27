package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;

	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi

		int vertical = random.nextInt(3);

		if (vertical == 1) {
			if (calculateColDirection() != -1)
				return calculateColDirection();
			else
				return calculateRowDirection();

		} else if (vertical == 0) {
			if (calculateRowDirection() != -1)
				return calculateRowDirection();
			else
				return calculateColDirection();
		} else {
			return random.nextInt(4);
		}
	}

	protected int calculateColDirection() {
		if (_bomber.getXTile() < _e.getXTile())
			return 3;
		else if (_bomber.getXTile() > _e.getXTile())
			return 1;

		return -1;
	}

	protected int calculateRowDirection() {
		if (_bomber.getYTile() < _e.getYTile())
			return 0;
		else if (_bomber.getYTile() > _e.getYTile())
			return 2;
		return -1;
	}
}
