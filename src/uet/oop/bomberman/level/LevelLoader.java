package uet.oop.bomberman.level;

import uet.oop.bomberman.Map;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {
	protected int _width = 31, _height = 13; // default values just for testing
	protected int _level;
	protected Map _board;

	public LevelLoader(Map map, int level) throws LoadLevelException {
		_board = map;
		loadLevel(level);
	}

	public abstract void loadLevel(int level) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}
}
