import java.util.HashMap;
import java.util.HashSet;

public class Intersection {
    private Player p1, p2;
    private HashMap<Player, HashSet<Tile>> playingTiles = new HashMap<>();

    public Intersection(String p1name, boolean p1Hints, String p2name, boolean p2Hints) {
        this.p1 = new Human(1, p1name, p1Hints);
        this.p2 = new Human(2, p2name, p2Hints);
        playingTiles.put(p1, new HashSet<>());
        playingTiles.put(p2, new HashSet<>());
    }
    public Intersection(String p1name, boolean p1Hints) {
        this.p1 = new Human(1, p1name, p1Hints);
        this.p2 = new CPU(2);
        playingTiles.put(p1, new HashSet<>());
        playingTiles.put(p2, new HashSet<>());
    }

    public Player p1() {
        return p1;
    }

    public Player p2() {
        return p2;
    }

    public Player currentPlayer(boolean p1turn) {
        if (p1turn) {
            return p1;
        }
        return p2;
    }

    public Player opponentPlayer(Player current) {
        if (current == p1) {
            return p2;
        }
        return p1;
    }
    public Tile getMove(Player currP, Board board) {
        return currP.selectMove(playingTiles.get(currP), playingTiles.get(opponentPlayer(currP)), board.getSize());
    }

    public boolean isValidMove(Tile move, Board board) { // searching for a valid move and updating the board class
         return !intersects(move) && move.x() < board.getSize() && move.y() < board.getSize() && move.x() >= 0 && move.y() >= 0;
    }

    public void updateBoard(Player current, Tile move, Board board) {
        playingTiles.get(current).add(move);
        board.updateBoard(move, current.getId());
    }

    public boolean intersects (Tile temp) {
        return playingTiles.get(p1).contains(temp) || playingTiles.get(p2).contains(temp);
    }

    public boolean isWinningSequence(Tile newTile, boolean p1turn) {
        Player p = currentPlayer(p1turn);
        return verticalSequence(newTile, p) || horizontalSequence(newTile, p) || diagonalSequence(newTile, p);
    }

    public boolean isUnplayable(int size) {
        return playingTiles.get(p1).size() + playingTiles.get(p2).size() >= size * size;
    }

    public boolean verticalSequence(Tile newTile, Player p) { // checks a vertical sequence
        int offset = 1;
        int sequence = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x(), newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x(), newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }

    public boolean horizontalSequence(Tile newTile, Player p) { /// checks for a horizontal sequence
        int offset = 1;
        int sequence = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() + offset, newTile.y()))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() - offset, newTile.y()))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }
    public boolean diagonalSequence(Tile newTile, Player p) { // checks for a diagonal sequence
        int offset = 1;
        int sequence = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() + offset, newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() - offset, newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        if (sequence >= 5) {
            return true;
        }

        offset = 1;
        sequence = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() + offset, newTile.y() - offset))) {
            sequence += 1;
            offset += 1;
        }
        offset = 1;
        while (playingTiles.get(p).contains(new Tile(newTile.x() - offset, newTile.y() + offset))) {
            sequence += 1;
            offset += 1;
        }
        return sequence >= 5;
    }

//    public static void main(String [] args) {
//        Intersection inter = new Intersection();
//        inter.playingTiles.get(inter.p1).add(new Tile(0, 1));
//        inter.playingTiles.get(inter.p1).add(new Tile(0, 2));
//        inter.playingTiles.get(inter.p1).add(new Tile(0, 3));
//
//        inter.playingTiles.get(inter.p1).add(new Tile(0, 7));
//
//        System.out.print(inter.playingTiles.get(inter.p1).contains(new Tile(1, 1)));
//        System.out.print(inter.isWinningSequence(new Tile(0, 5), true));
//    }
}


