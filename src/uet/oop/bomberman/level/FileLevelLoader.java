package uet.oop.bomberman.level;

import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.StringTokenizer;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Bomber2;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Tree;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {
	public static boolean is_multi = false;

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	Random random = new Random();
	public static boolean level_load = false;

	public FileLevelLoader(Map map, int level) throws LoadLevelException {
		super(map, level);
	}

	@Override
	public void loadLevel(int level) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt

		try {
			URL absPath = FileLevelLoader.class.getResource("/levels/Level" + level + ".txt");
			URL absPath2 = FileLevelLoader.class.getResource("/levels2/Level" + level + ".txt");

			BufferedReader in;
			if (!is_multi) {
				in = new BufferedReader(new InputStreamReader(absPath.openStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(absPath2.openStream()));
			}

			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);

			_level = Integer.parseInt(tokens.nextToken());
			_height = Integer.parseInt(tokens.nextToken());
			_width = Integer.parseInt(tokens.nextToken());
			_map = new char[_height][_width];
			for (int i = 0; i < _height; i++) {
				String s = in.readLine();
				int j = 0;
				while (j < _width) {
					int index = s.charAt(j);
					_map[i][j] = (char) index;
					j++;
				}
			}
			in.close();
		} catch (IOException e) {
			throw new LoadLevelException("Error loading level " + level, e);
		}
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _map.addEntity() để thêm Entity vào game
		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				switch (_map[y][x]) {
					case '#':
						if (x == 0 || x == 30 || y == 0 || y == 12) {
							_board.addEntity(x + y * _width, new Wall(x, y, Sprite.wall));
							break;
						} else {
							_board.addEntity(x + y * _width, new Tree(x, y, Sprite.tree));
							break;
						}
					case 'p':
						int xBomber = x, yBomber = y;
						_board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber),
								Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(
								xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
						break;
					case 'q':
						int xBomber1 = x, yBomber1 = y;
						_board.addCharacter(new Bomber2(Coordinates.tileToPixel(xBomber1),
								Coordinates.tileToPixel(yBomber1) + Game.TILES_SIZE, _board));
						Screen.setOffset(0, 0);
						_board.addEntity(xBomber1 + yBomber1 * _width,
								new Grass(xBomber1, yBomber1, Sprite.grass));
						break;
					case '1':
						int xE = x, yE = y;
						_board.addCharacter(new Balloon(Coordinates.tileToPixel(xE),
								Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
						_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
						break;
					case '2':
						int xE2 = x, yE2 = y;
						_board.addCharacter(new Oneal(Coordinates.tileToPixel(xE2),
								Coordinates.tileToPixel(yE2) + Game.TILES_SIZE, _board));
						_board.addEntity(xE2 + yE2 * _width, new Grass(xE2, yE2, Sprite.grass));
						break;
					case '3':
						int xE3 = x, yE3 = y;
						_board.addCharacter(new Doll(Coordinates.tileToPixel(xE3),
								Coordinates.tileToPixel(yE3) + Game.TILES_SIZE, _board));
						_board.addEntity(xE3 + yE3 * _width, new Grass(xE3, yE3, Sprite.grass));
						break;
					case '4':
						int xE4 = x, yE4 = y;
						_board.addCharacter(new Kondoria(Coordinates.tileToPixel(xE4),
								Coordinates.tileToPixel(yE4) + Game.TILES_SIZE, _board));
						_board.addEntity(xE4 + yE4 * _width, new Grass(xE4, yE4, Sprite.grass));
						break;

					case '5':
						int xE5 = x, yE5 = y;
						_board.addCharacter(new Minvo(Coordinates.tileToPixel(xE5),
								Coordinates.tileToPixel(yE5) + Game.TILES_SIZE, _board));
						_board.addEntity(xE5 + yE5 * _width, new Grass(xE5, yE5, Sprite.grass));
						break;
					case '6':
						int xE6 = x, yE6 = y;
						_board.addCharacter(new Ovape(Coordinates.tileToPixel(xE6),
								Coordinates.tileToPixel(yE6) + Game.TILES_SIZE, _board));
						_board.addEntity(xE6 + yE6 * _width, new Grass(xE6, yE6, Sprite.grass));
						break;
					case '7':
						int xE7 = x, yE7 = y;
						_board.addCharacter(new Pass(Coordinates.tileToPixel(xE7),
								Coordinates.tileToPixel(yE7) + Game.TILES_SIZE, _board));
						_board.addEntity(xE7 + yE7 * _width, new Grass(xE7, yE7, Sprite.grass));
						break;

					case '*':
						int xB = x, yB = y;
						_board.addEntity(xB + yB * _width,
								new LayeredEntity(xB, yB, new Grass(xB, yB, Sprite.grass),
										new Brick(xB, yB, Sprite.brick)));
						break;
					case 'f':
						int xF = x, yF = y;
						_board.addEntity(xF + yF * _width,
								new LayeredEntity(xF, yF, new Grass(xF, yF, Sprite.grass),
										new FlameItem(xF, yF, Sprite.powerup_flames),
										new Brick(xF, yF, Sprite.brick)));
						break;
					case 'b':
						int xI = x, yI = y;
						_board.addEntity(xI + yI * _width,
								new LayeredEntity(xI, yI, new Grass(xI, yI, Sprite.grass),
										new BombItem(xI, yI, Sprite.powerup_bombs),
										new Brick(xI, yI, Sprite.brick)));
						break;
					case 's':
						int xS = x, yS = y;
						_board.addEntity(xS + yS * _width,
								new LayeredEntity(xS, yS, new Grass(xS, yS, Sprite.grass),
										new SpeedItem(xS, yS, Sprite.powerup_speed),
										new Brick(xS, yS, Sprite.brick)));
						break;
					case 'x':
						int xX = x, yX = y;
						_board.addEntity(xX + yX * _width,
								new LayeredEntity(xX, yX, new Grass(xX, yX, Sprite.grass),
										new Portal(xX, yX, Sprite.portal),
										new Brick(xX, yX, Sprite.brick)));
						break;
					default:
						int xA = x, yA = y;
						_board.addEntity(xA + yA * _width,
								new LayeredEntity(xA, yA, new Grass(xA, yA, Sprite.grass)));
						break;
				}
			}
		}
		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
	}
}
