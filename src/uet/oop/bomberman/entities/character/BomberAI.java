package uet.oop.bomberman.entities.character;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;

public class BomberAI {
    public static int calculateDirection(List<Bomb> bombs, Bomber bomber)
            throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Random random = new Random();
        // TODO: cài đặt thuật toán tìm đường đi
        if (!Game.getBoard().detectNoEnemies()) {
            for (int i = 1; i < 1 + Game.getBombRadius(); i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(
                        bomber.getXTile() + i, bomber.getYTile(), bomber);
                if (character != null) {
                    int a = random.nextInt(4);
                    if (a == 0)
                        return 0;
                    else if (a == 1)
                        return 4;
                    else if (a == 2)
                        return 2;
                    else if (a == 3)
                        return 3;
                }
            }
            for (int i = 1; i < 1 + Game.getBombRadius(); i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(
                        bomber.getXTile() - i, bomber.getYTile(), bomber);
                if (character != null) {
                    int a = random.nextInt(4);
                    if (a == 0)
                        return 0;
                    else if (a == 1)
                        return 1;
                    else if (a == 2)
                        return 2;
                    else if (a == 3)
                        return 4;
                }
            }
            for (int i = 1; i < 1 + Game.getBombRadius(); i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(
                        bomber.getXTile(), bomber.getYTile() + i, bomber);
                if (character != null) {
                    int a = random.nextInt(4);
                    if (a == 0)
                        return 0;
                    else if (a == 1)
                        return 1;
                    else if (a == 2)
                        return 4;
                    else if (a == 3)
                        return 3;
                }
            }
            for (int i = 1; i < 1 + Game.getBombRadius(); i++) {
                Character character = Game.getBoard().getCharacterAtExcluding(
                        bomber.getXTile(), bomber.getYTile() - i, bomber);
                if (character != null) {
                    int a = random.nextInt(4);
                    if (a == 0)
                        return 4;
                    else if (a == 1)
                        return 1;
                    else if (a == 2)
                        return 2;
                    else if (a == 3)
                        return 3;
                }
            }
        }
        if (Game.getBombRate() > 0 && Game.getBombRate2() > 0) {
            return random.nextInt(4);
        }

        else {
            for (int i = 0; i < bombs.size(); i++) {
                int dX = Math.abs(bomber.getXTile() - (int) bombs.get(i).getX());
                int dY = Math.abs(bomber.getYTile() - (int) bombs.get(i).getY());
                if (dX <= Game.getBombRadius() && dY <= Game.getBombRadius()) {
                    if (bomber.canMove(0, -5))
                        return 0;
                    else if (bomber.canMove(5, 0))
                        return 1;
                    else if (bomber.canMove(0, 5))
                        return 2;
                    else if (bomber.canMove(-5, 0))
                        return 3;
                } else {
                    if (bomber.getXTile() > (int) bombs.get(i).getX()) {
                        return 1;
                    }
                    if (bomber.getXTile() < (int) bombs.get(i).getX()) {
                        return 3;
                    }
                    if (bomber.getYTile() > (int) bombs.get(i).getY()) {
                        return 2;
                    }
                    if (bomber.getXTile() < (int) bombs.get(i).getX()) {
                        return 0;
                    }
                }
            }
        }
        return 4;
    }
}
