package uet.oop.bomberman;

import java.awt.*;
import javax.swing.*;

public class ProgressBar extends JFrame {
    public static boolean isDoned = false;
    JLabel imagelabel = new JLabel();
    JProgressBar jb;
    int i = 0, num = 0;
    private ImageIcon ii = new ImageIcon((new ImageIcon("res/textures/ScreenLoading.png"))
            .getImage()
            .getScaledInstance(1488, 624, Image.SCALE_DEFAULT));
    private ImageIcon iii = new ImageIcon((new ImageIcon("res/textures/loading.gif"))
            .getImage()
            .getScaledInstance(1488, 624, Image.SCALE_DEFAULT));

    ProgressBar() {
        jb = new JProgressBar(0, 2000);
        jb.setBounds(0, 562, 1488, 15);
        jb.setValue(0);
        jb.setStringPainted(true);
        jb.setForeground(Color.yellow);
        imagelabel.setIcon(iii);
        add(jb);
        add(imagelabel);
        this.setTitle("Bomberman by OOP_N11");
        this.setSize(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

        // this.setLayout(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void iterate() {
        while (i <= 2000) {
            jb.setValue(i);
            if (i <= 1600) {
                i += 50;
            } else {
                i += 12;
            }
            try {
                Thread.sleep(150);
            } catch (Exception e) {
            }
        }
        isDoned = true;
    }
}