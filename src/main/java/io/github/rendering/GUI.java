package io.github.rendering;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI extends JFrame {
    public static void StartGUI() {
        Frame f= new Frame();
        f.setTitle("ASFM TEST GUI");
        f.setBounds(100, 100, 300, 300);
        JButton button = new JButton("TEST");
        f.add(button);
        f.setVisible(true);

    }
}
