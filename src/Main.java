import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Main extends JFrame {

    Game game;
    ImageIcon playIcon, exitIcon, winnerBlackIcon, winnerWhiteIcon;
    public Main() {
        // Setting up
        super("Omok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 540));
        setResizable(false);
        setupIcons("play.png", "exit.png", "black-winner.png", "white-winner.png");

        // ------- Game initialization
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
        var status = new JLabel("Black Turn");
        gamePanel.add(status);

        // Menu Bar that holds options to play
        var optionMenu = new JMenuBar();
        playingPanel.add(optionMenu, BorderLayout.NORTH);

        var menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_G);
        optionMenu.add(menu);

        // Tool Bar that holds options to play
        var optionTool = new JToolBar();
        playingPanel.add(optionTool, BorderLayout.SOUTH);

        // ------- Making board panel and setting up its listeners ------
        var board = new BoardPanel(game.getBoard());
        gamePanel.add(board);

        MouseAdapter mouseListener = new MouseAdapter() { // storing listener in a variable so it can later be removed
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
        board.addMouseListener(mouseListener);

        // -------- Option Actions -----------
        OmokAction playAction = new OmokAction("Play",
                playIcon,
                "Play a new game",
                KeyEvent.VK_P,
                KeyEvent.VK_P,
                () -> {
                    int restart = JOptionPane.showConfirmDialog(this, "Do you want to restart game? ", "Play", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, playIcon);
                    if (restart != 0)
                        return;
                    status.setText("Black Turn");
                    game.gameReset();
                    card.show(mainPanel, "setup");
                    board.addMouseListener(mouseListener);
                });

        OmokAction exitAction = new OmokAction("Exit",
                exitIcon,
                "Exit Omok",
                KeyEvent.VK_E,
                KeyEvent.VK_E,
                () -> {
                    int exit = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, exitIcon);
                    if (exit == 0)
                        System.exit(0);
                });

        // Play item for menu, setting Omok Action
        var playItem = new JMenuItem(playAction);
        menu.add(playItem);

        // Exit item for menu, setting Omok Action
        var exitItem = new JMenuItem(exitAction);
        menu.add(exitItem);

        // Play button for toolbar
        JButton playButton = new JButton(playAction);
        optionTool.add(playButton);

        // Exit button for toolbar
        JButton exitButton = new JButton(exitAction);
        optionTool.add(exitButton);

        // ------- Game Listeners ---------
        game.addTurnListener(turn -> {
            if (game.currentPlayer().getClass() == CPU.class)
                makeMove();

            status.setText(game.currentPlayer().getName() + " Turn");
            repaint();
        });

        game.addGameEndListener(gameEnd -> {
            if (gameEnd) {
                if (game.hasWonGame()) {
                    ImageIcon winnerIcon;
                    if (game.winnerName.equals("White"))
                        winnerIcon = winnerWhiteIcon;
                    else
                        winnerIcon = winnerBlackIcon;
                    JOptionPane.showMessageDialog(this, game.winnerName + " has Won!", "Omok", JOptionPane.INFORMATION_MESSAGE, winnerIcon);
                }
                else
                    JOptionPane.showMessageDialog(this, "Game tied\nNo Clear Winner", "Omok", JOptionPane.INFORMATION_MESSAGE);

                board.removeMouseListener(mouseListener);
                status.setText("Game has Ended");
            }
        });

        setContentPane(mainPanel);
        pack();
    }

    public void makeMove(int x, int y) {
        Tile move = new Tile(x, y);

        if (game.getCheck().isValidMove(move, game.getBoard()))
            game.playTurn(move, game.currentPlayer());
    }
    public void makeMove() {
        game.playTurn(null, game.currentPlayer());
    }

    public void setupIcons(String playFile, String exitFile, String winnerBlackFile, String winnerWhiteFile) {
        playIcon = createImageIcon(playFile);
        assert playIcon != null;
        Image playImg = playIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        playIcon = new ImageIcon(playImg);

        exitIcon = createImageIcon(exitFile);
        assert exitIcon != null;
        Image exitImg = exitIcon.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        exitIcon = new ImageIcon(exitImg);

        winnerBlackIcon = createImageIcon(winnerBlackFile);
        assert winnerBlackIcon != null;
        Image winnerBlackImg = winnerBlackIcon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        winnerBlackIcon = new ImageIcon(winnerBlackImg);

        winnerWhiteIcon = createImageIcon(winnerWhiteFile);
        assert winnerWhiteIcon != null;
        Image winnerWhiteImg = winnerWhiteIcon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way ;
        winnerWhiteIcon = new ImageIcon(winnerWhiteImg);
    }
    /** Create an image icon from the given image file. */
    private ImageIcon createImageIcon(String filename) {
        URL imageUrl = getClass().getResource(filename);
        if (imageUrl != null) {
            return new ImageIcon(imageUrl);
        }
        return null;
    }


    public static void main(String [] args) {
        Main omok = new Main();
        omok.setVisible(true);
    }
}

class OmokAction extends AbstractAction {
    private Runnable runnable;

    public OmokAction(String name, ImageIcon icon, String descr, int mnemonic, int accelerator,
                      Runnable runnable) {
        super(name, icon);
        this.runnable = runnable;
        putValue(SHORT_DESCRIPTION, descr);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(accelerator, InputEvent.ALT_DOWN_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        runnable.run();
    }
}
