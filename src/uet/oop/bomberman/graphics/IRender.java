package uet.oop.bomberman.graphics;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface IRender {

    void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException;

    void render(Screen screen);
}
