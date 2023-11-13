import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

public class Main extends JFrame {

    Game game;
    ImageIcon playIcon, exitIcon;
    public Main() {
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 520));
        setResizable(false);
        setupIcons("play.png", "exit.png");

        game = new Game();
        // Using card layout to switch between panels
        var card = new CardLayout();
        var mainPanel = new JPanel(card);

        // -------------- Setup Panel for choosing game mode ------------
        var gameSetupPanel = new JPanel();
        gameSetupPanel.setLayout(new BorderLayout());
        mainPanel.add(gameSetupPanel, "setup");


        // Button UI
        var selectionPanel = new JPanel();

        selectionPanel.add(new JLabel("Select your opponent :"));

        // Setting up radio buttons
        var humanButton = new JRadioButton("Human");
        var compButton = new JRadioButton("Computer");
        var buttonGroup = new ButtonGroup();
        buttonGroup.add(humanButton);
        buttonGroup.add(compButton);

        selectionPanel.add(humanButton);
        selectionPanel.add(compButton);

        gameSetupPanel.add(selectionPanel, BorderLayout.NORTH);

        // Start button
        var startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentY(Component.TOP_ALIGNMENT);
        gameSetupPanel.add(startButton, BorderLayout.CENTER);

        // Selection
        var selectionText = new JLabel("");
        selectionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameSetupPanel.add(selectionText, BorderLayout.SOUTH);

        // Adding Action listener for game selection
        startButton.addActionListener(e -> {
            selectionText.setText("");
            if (buttonGroup.getSelection() == null) {
                selectionText.setText("Select a game mode first!");
                return;
            }
            if (humanButton.isSelected()) {
                game.setupHumanGame();
            }

            if (compButton.isSelected()) {
                game.setupComputerGame();
            }
            card.show(mainPanel, "play");
        });

        // ------------ Panel with Border Layout that holds all other panels -----------
        var playingPanel = new JPanel(new BorderLayout());
        mainPanel.add(playingPanel, "play");

        // Panel that holds the board and game selection to play
        var gamePanel = new JPanel();
        playingPanel.add(gamePanel, BorderLayout.CENTER);

        // Label with current game status
        var status = new JLabel("Start Game");
        gamePanel.add(status);

        // Tool Bar that holds options to play
        var optionTool = new JToolBar();
        playingPanel.add(optionTool, BorderLayout.NORTH);

        // Play button for toolbar
        JButton playButton = new JButton(playIcon);
        playButton.addActionListener(e -> {
            int restart = JOptionPane.showConfirmDialog(this, "Do you want to restart game? ", "Play", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, playIcon);
            if (restart != 0)
                return;
            game.restartBoard();
            card.show(mainPanel, "setup");
        });
        playButton.setToolTipText("Play a new game");
        playButton.setFocusPainted(false);
        optionTool.add(playButton);

        // Exit button for toolbar
        JButton button = new JButton(exitIcon);
        button.addActionListener(e -> {
            int exit = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, exitIcon);
            if (exit == 0)
                System.exit(0);
        });
        button.setToolTipText("Exit Omok");
        button.setFocusPainted(false);
        optionTool.add(button);

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

    public void setupIcons(String playFile, String exitFile) {
        playIcon = createImageIcon(playFile);
        assert playIcon != null;
        Image playImg = playIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        playIcon = new ImageIcon(playImg);

        exitIcon = createImageIcon(exitFile);
        assert exitIcon != null;
        Image exitImg = exitIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        exitIcon = new ImageIcon(exitImg);
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
