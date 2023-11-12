import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {
    private Board board;
    private Dimension dim;
    int squareWidth;
    int border;
    int boardWidth;
    int visualCueX;
    int visualCueY;

    public BoardPanel(Board board) {
        this.board = board;
        setSize(420, 420);
        this.dim = getSize();
        setPreferredSize(dim);

        squareWidth = dim.width/15;
        border = squareWidth*14 + squareWidth/2;
        boardWidth = squareWidth*14;


        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point pos = e.getPoint();

                int x = (pos.x/squareWidth  % 15) * squareWidth;
                int y = (pos.y/squareWidth  % 15) * squareWidth;

                if (pos.x > getSize().getWidth() || pos.y > getSize().getHeight())
                    return;

                if (!(visualCueX == x && visualCueY == y)) {
                    repaint();
                }

                visualCueX = x;
                visualCueY = y;
                Graphics g = getGraphics();
                g.setColor(Color.BLACK);
                g.drawOval(x, y, squareWidth, squareWidth);
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(189, 154,122));
        g.fillRect(squareWidth/2, squareWidth/2, boardWidth, boardWidth);
        g.drawRect(squareWidth/2, squareWidth/2, boardWidth, boardWidth);
        g.setColor(new Color(0, 0,0));

        for (int i = squareWidth/2; i <= border; i += squareWidth) {
            g.drawLine(squareWidth/2, i, border, i);
            g.drawLine(i, squareWidth/2, i, border);
        }

        for (int x = 0; x < board.getSize(); x += 1) {
            for (int y = 0; y < board.getSize(); y += 1) {
                if (board.getArray()[y][x] == 1) {
                    g.setColor(new Color(0, 0,0));
                    g.fillOval(x*squareWidth, y*squareWidth, squareWidth, squareWidth);
                } else if (board.getArray()[y][x] == 2) {
                    g.setColor(new Color(255, 255,255));
                    g.fillOval(x*squareWidth, y*squareWidth, squareWidth, squareWidth);
                }
            }
        }
    }

}
