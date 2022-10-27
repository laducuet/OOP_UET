package uet.oop.bomberman;

import static uet.oop.bomberman.ProgressBar.isDoned;

import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import uet.oop.bomberman.gui.Frame;
public class BombermanGame {
    public static void main(String[] args) throws UnsupportedAudioFileException,
            LineUnavailableException, IOException,
            FontFormatException {
        ProgressBar intro = new ProgressBar();
        intro.setVisible(true);
        intro.setLocationRelativeTo(null);
        intro.iterate();

        if (isDoned) {
            intro.setVisible(false);
            new Frame();
        } else {
            System.out.println("Please wait a moment");
        }
    }
}
