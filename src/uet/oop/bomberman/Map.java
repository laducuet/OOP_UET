package uet.oop.bomberman;

import static uet.oop.bomberman.level.FileLevelLoader.is_multi;
import static uet.oop.bomberman.level.FileLevelLoader.level_load;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Bomb2;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Bomber2;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.IRender;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.level.LevelLoader;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Map implements IRender {
	protected LevelLoader levelLoader;
	public static int levels = 1;
	protected Game _game;
	protected Keyboard _input;
	protected Keyboard _input1;
	protected Screen _screen;
	Random random = new Random();

	public Entity[] _entities;
	public List<Character> _characters = new ArrayList<>();
	protected List<Bomb> _bombs = new ArrayList<>();
	protected List<Bomb2> _bombs2 = new ArrayList<>();

	public static int _screenToShow = -1; // 1:endgame, 2:changelevel, 3:paused

	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	private int _lives = Game.LIVES;

	public Map(Game game, Keyboard input, Keyboard input1, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		_input1 = input1;

		_screenToShow = 4;
	}

	@Override
	public void update()
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (_game.isPaused())
			return;

		updateEntities();
		updateCharacters();
		updateBombs();
		updateBombs2();
		detectEndGame();

		for (int i = 0; i < _characters.size(); i++) {
			Character a = _characters.get(i);
			if (a.isRemoved())
				_characters.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if (_game.isPaused())
			return;

		int x0 = 0, x1 = 31, y0 = 0, y1 = 13;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				_entities[x + y * levelLoader.getWidth()].render(screen);
			}
		}

		renderBombs(screen);
		renderBombs2(screen);
		renderCharacter(screen);
	}

	public void restartLevel() {
		_game.bomberSpeed = 1.0;
		_game.bombRadius = 1;
		_game.bombRate = 1;
		_game.bomberSpeed2 = 1.0;
		_game.bombRadius2 = 1;
		_game.bombRate2 = 1;
		loadLevel(levelLoader.getLevel());
	}

	public void nextLevel() {
		loadLevel(levelLoader.getLevel() + 1);
		levels += 1;
		Map.setLevels(levels);

		System.out.println(levelLoader.getLevel());
		System.out.println(levels);
	}

	public void loadLevel(int level) {
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_characters.clear();
		_bombs.clear();
		_bombs2.clear();
		try {
			levelLoader = new FileLevelLoader(this, level);

			_entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];

			levelLoader.createEntities();
		} catch (LoadLevelException e) {
			endGame();
		}
	}

	protected void detectEndGame() {
		if (_time <= 0)
			endGame();
	}

	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
		_game.isEndgame = true;
		if (getPoints() >= _game.get_highscore()) {
			_game.set_highscore(getPoints());
			_game.saveHighScore();
		}
	}

	public boolean detectNoEnemies() {
		int total = 0;
		for (int i = 0; i < _characters.size(); i++) {
			if (_characters.get(i) instanceof Bomber == false)
				++total;
		}

		return total == 0;
	}

	public void newGame() {
		resetProperties();
		if (level_load) {
			loadLevel(random.nextInt(5) + 1);
		} else {
			levels = 1;
			loadLevel(1);
		}
	}

	@SuppressWarnings("static-access")
	private void resetProperties() {
		_points = Game.POINTS;
		_lives = Game.LIVES;

		_game.bomberSpeed = 1.0;
		_game.bombRadius = 1;
		_game.bombRate = 1;
		_game.bomberSpeed2 = 1.0;
		_game.bombRadius2 = 1;
		_game.bombRate2 = 1;
	}
	public void drawScreen(Graphics g) throws IOException, FontFormatException {
		_screen.intializeFont();
		switch (_screenToShow) {
			case 1:
				if (!is_multi) {
					_screen.drawEndGame(g, _points);
					break;
				} else {
					if (!Bomber2.checkPlayer2) {
						_screen.drawPlayer1Win(g);
					} else {
						_screen.drawPlayer2Win(g);
					}
					break;
				}
			case 2:
				_screen.drawChangeLevel(g, levelLoader.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
			case 4:
				_screen.drawMenu(g);
				break;
			case 5:
				_screen.drawHelp(g);
				break;
			case 6:
				_screen.drawSoundOn(g);
				break;
			case 7:
				_screen.drawSoundOff(g);
				break;
		}
	}

	public Entity getEntity(double x, double y, Character m) {
		Entity res = null;

		res = getFlameSegmentAt((int) x, (int) y);
		if (res != null)
			return res;

		res = getFlameSegmentAt2((int) x, (int) y);
		if (res != null)
			return res;

		res = getBombAt(x, y);
		if (res != null)
			return res;

		res = getBombAt2(x, y);
		if (res != null)
			return res;

		res = getCharacterAtExcluding((int) x, (int) y, m);
		if (res != null)
			return res;

		res = getEntityAt((int) x, (int) y);

		return res;
	}

	public List<Bomb> getBombs() {
		return _bombs;
	}

	public List<Bomb2> getBombs2() {
		return _bombs2;
	}

	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while (bs.hasNext()) {
			b = bs.next();
			if (b.getX() == (int) x && b.getY() == (int) y)
				return b;
		}

		return null;
	}

	public Bomb2 getBombAt2(double x, double y) {
		Iterator<Bomb2> bs = _bombs2.iterator();
		Bomb2 b;
		while (bs.hasNext()) {
			b = bs.next();
			if (b.getX() == (int) x && b.getY() == (int) y)
				return b;
		}

		return null;
	}

	public Bomber getBomber() {
		Iterator<Character> itr = _characters.iterator();

		Character cur;
		while (itr.hasNext()) {
			cur = itr.next();

			if (cur instanceof Bomber)
				return (Bomber) cur;
		}

		return null;
	}

	public Character getCharacterAtExcluding(int x, int y, Character a) {
		Iterator<Character> itr = _characters.iterator();

		Character cur;
		while (itr.hasNext()) {
			cur = itr.next();
			if (cur == a) {
				continue;
			}

			if (cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
		}

		return null;
	}

	public FlameSegment getFlameSegmentAt(int x, int y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while (bs.hasNext()) {
			b = bs.next();

			FlameSegment e = b.flameAt(x, y);
			if (e != null) {
				return e;
			}
		}

		return null;
	}

	public FlameSegment getFlameSegmentAt2(int x, int y) {
		Iterator<Bomb2> bs = _bombs2.iterator();
		Bomb2 b;
		while (bs.hasNext()) {
			b = bs.next();

			FlameSegment e = b.flameAt(x, y);
			if (e != null) {
				return e;
			}
		}

		return null;
	}

	public Entity getEntityAt(double x, double y) {
		return _entities[(int) x + (int) y * levelLoader.getWidth()];
	}

	public void addEntity(int pos, Entity e) {
		_entities[pos] = e;
	}

	public void addCharacter(Character e) {
		_characters.add(e);
	}

	public void addBomb(Bomb e) {
		_bombs.add(e);
	}

	public void addBomb2(Bomb2 e) {
		_bombs2.add(e);
	}

	protected void renderCharacter(Screen screen) {
		Iterator<Character> itr = _characters.iterator();

		while (itr.hasNext()) itr.next().render(screen);
	}

	protected void renderBombs(Screen screen) {
		Iterator<Bomb> itr = _bombs.iterator();

		while (itr.hasNext()) itr.next().render(screen);
	}

	protected void renderBombs2(Screen screen) {
		Iterator<Bomb2> itr = _bombs2.iterator();

		while (itr.hasNext()) itr.next().render(screen);
	}

	protected void updateEntities()
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (_game.isPaused())
			return;
		for (int i = 0; i < _entities.length; i++) {
			_entities[i].update();
		}
	}

	protected void updateCharacters()
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (_game.isPaused())
			return;
		Iterator<Character> itr = _characters.iterator();

		while (itr.hasNext() && !_game.isPaused()) itr.next().update();
	}

	protected void updateBombs()
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (_game.isPaused())
			return;
		Iterator<Bomb> itr = _bombs.iterator();

		while (itr.hasNext()) itr.next().update();
	}

	protected void updateBombs2()
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		if (_game.isPaused())
			return;
		Iterator<Bomb2> itr = _bombs2.iterator();

		while (itr.hasNext()) itr.next().update();
	}

	public Keyboard getInput() {
		return _input;
	}

	public Keyboard get_input1() {
		return _input1;
	}

	public LevelLoader getLevel() {
		return levelLoader;
	}

	public Game getGame() {
		return _game;
	}

	public int getShow() {
		return _screenToShow;
	}

	public void setShow(int i) {
		_screenToShow = i;
	}

	public int getPoints() {
		return _points;
	}

	public int getLives() {
		return _lives;
	}

	public int getLevels() {
		return levels;
	}

	public static void setLevels(int levels) {
		Map.levels = levels;
	}

	public void addPoints(int points) {
		this._points += points;
	}

	public void addLives(int lives) {
		this._lives += lives;
	}

	public int getWidth() {
		return levelLoader.getWidth();
	}

	public int getHeight() {
		return levelLoader.getHeight();
	}
}
