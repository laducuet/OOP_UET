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

public class BombItem extends Item {
    public BombItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e)
            throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // TODO: xử lý Bomber ăn Item
        if (e instanceof Bomber || e instanceof Bomber2) {
            if (this.isRemoved())
                return true; // không bị x2 Power Up
            remove();
            if (e instanceof Bomber)
                Game.addBombRate(1);
            else
                Game.addBombRate2(1);
            soundGame.playSound("eat_item.wav", playSoundCheck, 0);
        }
        return false;
    }
}
