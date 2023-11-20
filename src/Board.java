import java.util.ArrayList;
import java.util.List;

/** A class representing a square board.
 * Allows for the implementation of games that
 * incorporate selecting tiles.
 *
 * @author Angel Delgado
 * @version 1.0 10/6/2023
 * @since 10/6/2023
 *
 */

public class Board {

    /** Determines the size n of an n * n board */
    private final int size;

    /** Stores values representing tiles in board*/
    private int [][] board;

    /** Stores last move */
    private Tile lastMove;


    /**
     *
     */
    public Board(int size) {
        this.size = size;
        initBoard();
    }

    public Board() {
        this.size = 15;
        initBoard();
    }

    /**
     *
     */
    public void initBoard() {
        board = new int[size][size];
    }

    public Tile getLastMove() { return lastMove; }

    /**
     *
     */
    public int getSize() {
        return this.size;
    }

    public int[][] getArray() {
        return this.board;
    }

    public boolean isEmpty(int x, int y) {
        return board[y][x] == 0; // checking if there is no name in place
    }

    public void clear() {
        for (int i = 0; i < board.length; i += 1) {
            for (int j = 0; j < board[0].length; j += 1) {
                board[i][j] = 0; // clearing board by making spaces null
            }
        }
        lastMove = null;
    }
    /**
     *
     */
    public void printBoard() {  // printing of board
        System.out.print("   ");
        for (int i = 0; i < size; i += 1) {
            System.out.printf(" %-2d", i);
        }
        System.out.print("\n");
        for (int y = 0; y < size; y += 1) {
            System.out.printf(" %2d", y);
            for (int x = 0; x < size; x += 1) {
                if (board[x][y] == 1) {
                    System.out.print(" X "); // Place p1 tiles
                }
                else if (board[x][y] == 2) {
                    System.out.print(" O "); // Place p2 tiles
                }

                else {
                    if (y == 0 || y == size - 1) {
                        System.out.print(" - ");
                    }
                    else if (x == 0 || x == size - 1) {
                        System.out.print(" | ");
                    }
                    else {
                        System.out.print(" + ");
                    }
                }

            }
            System.out.print("\n");
        }
    }

    /**
     *
     */
    public void updateBoard(Tile tile, int id) {
        board[tile.y()][tile.x()] = id;
        lastMove = tile;
    }

    public Iterable<Tile> winningRow() {
        Iterable<Tile> winningRow;
        int height = board.length;
        int width = board[0].length;
        List<Tile> row = new ArrayList<>();
        int tile;
        int offset = 1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) { // revising each tile to see if there is a row starting with this tile
                tile = board[y][x]; // store the name of the current tile

                if (tile == 0) { continue; } // skip any empty spaces

                if (x <= width - 5) { // Checking horizontal rows from let to right only
                    while (board[y][x + offset] != 0 && board[y][x + offset] == tile) { // repeat only if it is consecutive
                        row.add(new Tile(x + offset, y));

                        if (row.size() >= 4) { // If there is 5 consecutive items return this row
                            row.add(0, new Tile(x, y)); // add the start of the row
                            winningRow = row;
                            return winningRow;
                        }

                        offset += 1; // Changing offset to check next element in the consecutive row

                    }
                    offset = 1;
                    row.clear();
                }

                if (y <= height - 5) { // Checking vertical rows from top to bottom only
                    while (board[y + offset][x] != 0 && board[y + offset][x] == tile) { // repeat only if it is consecutive
                        row.add(new Tile(x, y + offset));

                        if (row.size() >= 4) { // If there is 5 consecutive items return this row
                            row.add(0, new Tile(x, y)); // add the start of the row
                            winningRow = row;
                            return winningRow;
                        }

                        offset += 1; // Changing offset to check next element in the consecutive row

                    }
                    offset = 1;
                    row.clear();
                }

                if (x >= 4 && y <= height - 5) { // Checking diagonal rows from top right to bottom left
                    while (board[y + offset][x - offset] != 0 && board[y + offset][x - offset] == tile) { // repeat only if it is consecutive
                        row.add(new Tile(x - offset, y + offset));

                        if (row.size() >= 4) { // If there is 5 consecutive items return this row
                            row.add(0, new Tile(x, y)); // add the start of the row
                            winningRow = row;
                            return winningRow;
                        }

                        offset += 1; // Changing offset to check next element in the consecutive row

                    }
                    offset = 1;
                    row.clear();
                }

                if (x <= width - 5 && y <= height - 5) { // Checking diagonal rows from top left to bottom right
                    while (board[y + offset][x + offset] != 0 && board[y + offset][x + offset] == tile) { // repeat only if it is consecutive
                        row.add(new Tile(x + offset, y + offset));

                        if (row.size() >= 4) { // If there is 5 consecutive items return this row
                            row.add(0, new Tile(x, y)); // add the start of the row
                            winningRow = row;
                            return winningRow;
                        }

                        offset += 1; // Changing offset to check next element in the consecutive row

                    }
                    offset = 1;
                    row.clear();
                }

            }
        }

        return null;
    }

}
