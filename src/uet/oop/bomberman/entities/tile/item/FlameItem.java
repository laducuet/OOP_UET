package uet.oop.bomberman.entities.tile.item;

import static uet.oop.bomberman.Game.soundGame;
import static uet.oop.bomberman.SoundGame.playSoundCheck;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Bomber2;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e)
			throws UnsupportedAudioFileException, LineUnavailableException, IOException {
		// TODO: xử lý Bomber ăn Item
		if (e instanceof Bomber || e instanceof Bomber2) {
			if (this.isRemoved())
				return true;
			remove();
			if (e instanceof Bomber)
				Game.addBombRadius(1);
			else
				Game.addBombRadius2(1);
			soundGame.playSound("eat_item.wav", playSoundCheck, 0);
		}
		return false;
	}
}
