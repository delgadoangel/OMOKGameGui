import java.util.HashSet;
import java.util.Scanner;
public class Human extends Player{

    public Human(int id, String name, boolean usingStrat) {
        super(id, name, usingStrat);
    }

    public Tile selectMove(HashSet<Tile> myTiles, HashSet<Tile> opponentTiles, int size) {
        Tile possMove = null;

        if (getUsingStrat()) {
            possMove = stratMove(myTiles, opponentTiles, size);
        }

        return possMove;
    }
}
