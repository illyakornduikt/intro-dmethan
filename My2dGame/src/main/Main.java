package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        gamePanel.requestFocus();
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
