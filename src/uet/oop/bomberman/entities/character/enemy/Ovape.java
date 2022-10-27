package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.Map;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium2;
import uet.oop.bomberman.graphics.Sprite;

public class Ovape extends Enemy {
    public Ovape(int x, int y, Map board) {
        super(x, y, board, Sprite.ovape_dead, Game.getBomberSpeed() / 2, 2000);

        _sprite = Sprite.ovape_right1;

        _ai = new AIMedium2(board.getBomber(), this, board); // TODO: implement AIHigh
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.ovape_right1, Sprite.ovape_right2,
                            Sprite.ovape_right3, _animate, 60);
                else
                    _sprite = Sprite.ovape_left1;
                break;
            case 2:
            case 3:
                if (_moving)
                    _sprite = Sprite.movingSprite(
                            Sprite.ovape_left1, Sprite.ovape_left2, Sprite.ovape_left3, _animate, 60);
                else
                    _sprite = Sprite.ovape_left1;
                break;
        }
    }
}
