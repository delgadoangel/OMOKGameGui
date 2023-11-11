import java.util.HashSet;

public class CPU extends Player{

    public CPU(int id) {
        super(id, "White", true);
    }

    public Tile selectMove(HashSet<Tile> myTiles, HashSet<Tile> opponentTiles, int size) {
        return stratMove(myTiles, opponentTiles, size);
    }

}
