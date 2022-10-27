package uet.oop.bomberman;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class SoundGame {
    private Clip clip = null;
    public static int baihat = 0;
    public static boolean playSoundCheck = true;
    public void playSound(String soundFile, boolean playSoundCheck, int Loop)
            throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (playSoundCheck) {
            File f = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.loop(Loop);
        }
    }
    public void stopSound() {
        if (!playSoundCheck) {
            this.clip.stop();
            this.clip.close();
        }
    }
}
