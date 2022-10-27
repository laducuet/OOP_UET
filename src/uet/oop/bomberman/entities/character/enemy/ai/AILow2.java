package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AILow2 extends AI {
    Bomber _bomber;
    Enemy _e;

    public AILow2(Bomber bomber, Enemy e) {
        _bomber = bomber;
        _e = e;
    }

    @Override
    public int calculateDirection() {
        int v = random.nextInt(3);
        if (v == 0)
            return calculateLeftDirection();
        else if (v == 1)
            return calculateRightDirection();
        else
            return random.nextInt(4);
    }

    protected int calculateRightDirection() {
        return 1;
    }

    protected int calculateLeftDirection() {
        return 3;
    }
}
