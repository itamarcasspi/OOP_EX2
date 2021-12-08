package api;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI()
    {
        JFrame frame = new JFrame();

        JButton start_program_button = new JButton("Click here to start program");

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(start_program_button);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ex2 Graph GUI");
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GUI();
    }
}
