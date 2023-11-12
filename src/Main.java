import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

public class Main extends JFrame {

    Game game;
    public Main() {
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 520));
        setResizable(false);

        // Panel with Border Layout that holds all other layouts
        var mainPanel = new JPanel(new BorderLayout());

        // Panel that holds the board and game selection to play
        var gamePanel = new JPanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);


        // Label with current game status
        var status = new JLabel("Start Game");
        gamePanel.add(status);

        // Tool Bar that holds options to play
        var optionTool = new JToolBar();
        mainPanel.add(optionTool, BorderLayout.NORTH);

        // Play button for toolbar
        ImageIcon playIcon = createImageIcon("play.png");
        Image playImg = playIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        playIcon = new ImageIcon(playImg);
        JButton playButton = new JButton(playIcon);
//        playButton.addActionListener(e -> {
//            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to restart game? ");
//            if (confirm != 0) {
//                return;
//            }
//
//        });
        playButton.setToolTipText("Play a new game");
        playButton.setFocusPainted(false);
        optionTool.add(playButton);

        // Exit button for toolbar
        ImageIcon exitIcon = createImageIcon("exit.png");
        Image exitImg = exitIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        exitIcon = new ImageIcon(exitImg);
        JButton button = new JButton(exitIcon);
//        button.addActionListener(e -> );
        button.setToolTipText("Exit Omok");
        button.setFocusPainted(false);
        optionTool.add(button);


        game = new Game();
        game.setupHumanGame();
        if (game.gameEnd || game.getCheck() == null) {
            JOptionPane.showMessageDialog(this, "There is no Current Game", "JavaChat",
                    JOptionPane.PLAIN_MESSAGE);
        }


        // Making board panel and setting up its listeners
        var board = new BoardPanel(game.getBoard());
        gamePanel.add(board);
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

    /** Create an image icon from the given image file. */
    private ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource(filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        return null;
    }
    public static void main(String [] args) throws IOException {
        Main omok = new Main();
        omok.setVisible(true);

    }
}
