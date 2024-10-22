import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Fun Times");

        GameModel game = new GameModel();
        frame.add(game);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.startGameThread();
    }
}