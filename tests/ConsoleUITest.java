import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleUITest {

    InputStream in;
    PrintStream out;
    ByteArrayOutputStream result;
    ConsoleUI ui;

    @BeforeEach
    void setUp(){
        in = new ByteArrayInputStream(new byte[0]); // Needed just for console initialization
        result = new ByteArrayOutputStream(); // Output stream to which output will print
        out = new PrintStream(result, true);

        ui = new ConsoleUI(in, out);


    }

    @Test
    void ConsoleUi() {
        assertNotNull(ui);
        assertSame(in, ui.getIn());
        assertSame(out, ui.getOut());
    }

    @Test
    void setBoardSize() {
        String input = "15\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));

        ui.setBoardSize();

        assertEquals(15, ui.getOmok().getBoard().getSize()); // testing if input correlates to size

        input = "20\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));

        ui.setBoardSize();

        assertEquals(20, ui.getOmok().getBoard().getSize()); // testing if input correlates to size
    }

    @Test
    void setGameMode() {
        String input = "2 Angel no\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));

        ui.setGameMode();

        assertNotNull(ui.getOmok().getCheck().p1()); // checking players initialized
        assertNotNull(ui.getOmok().getCheck().p2());
    }

    @Test
    void promptMove() throws IOException {
        String input = "15\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.setBoardSize();

        input = "2 Angel no\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.setGameMode();

        input = "10 10\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.promptMove();

        assertEquals(1, ui.getOmok().getBoard().getArray()[10][10]); // Testing if tile was placed correctly according to input

        input = "11 11\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes()));
        ui.promptMove();

        assertEquals(2, ui.getOmok().getBoard().getArray()[11][11]); // Testing if tile was placed according to input for player 2
    }

    @Test
    void end() {
        ui.getOmok().gameEnd = false;
        assertFalse(ui.end()); // if gameEnd is false then end() should also return false

        String input = "15\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes())); // initializing board
        ui.setBoardSize();

        input = "2 Angel no\n";
        ui.setIn(new ByteArrayInputStream(input.getBytes())); // initializing intersection
        ui.setGameMode();

        ui.getOmok().gameEnd = true;
        assertTrue(ui.end());
    }

    @Test
    void welcomeMessage() {
        ui.welcomeMessage();

        assertEquals("Welcome to the OMOK App!\n", result.toString());
    }

    @Test
    void winnerMessage() {
        ui.winnerMessage("Angel");

        assertEquals("- Angel has won OMOK!\n", result.toString());
        result.reset();

        ui.winnerMessage("Computer");

        assertEquals("- Computer has won OMOK!\n", result.toString());
    }

    @Test
    void tieMessage() {
        ui.tieMessage();

        assertEquals("There is no winner game tied.", result.toString());
    }
}