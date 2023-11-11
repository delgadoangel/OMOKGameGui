import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Main extends JFrame {

    Game game;
    public Main() {
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(420, 450));

        // Panel with Border Layout that holds all other layouts
        var mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel that holds the board and game selection to play
        var gamePanel = new JPanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // Tool Bar that holds options to play
        var optionBar = new JToolBar();
        mainPanel.add(optionBar, BorderLayout.NORTH);

        optionBar.add(new JButton("Play"));


        game = new Game();
        game.setupHumanGame();
        if (game.gameEnd || game.getCheck() == null) {
            JOptionPane.showMessageDialog(this, "There is no Current Game", "JavaChat",
                    JOptionPane.PLAIN_MESSAGE);
        }


        // Making board panel and setting up its listeners
        var board = new BoardPanel(game.getBoard());
        mainPanel.add(board);
        var boardClick = new MouseAdapter() { // storing listener in a variable so it can later be removed
            @Override
            public void mouseClicked(MouseEvent e) { // listener for when mouse clicked
                Point pos = e.getPoint();

                if (pos.x > board.getSize().getWidth() || pos.y > board.getSize().getHeight())
                    return;

                int x = (pos.x/board.squareWidth  % 15);
                int y = (pos.y/board.squareWidth  % 15);

                makeMove(x, y);
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        };
        board.addMouseListener(boardClick); // adding listeners to the board

        setContentPane(mainPanel);
        pack();
    }

    public void makeMove(int x, int y) {
        game.playTurn(new Tile(x, y), game.currentPlayer());
    }
    public static void main(String [] args) throws IOException {
        Main omok = new Main();
        omok.setVisible(true);

    }
}
