import org.junit.*;
import static org.junit.Assert.*;
 
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.io.*;

public class ComputerTest {

	@Test
    public void scoreNoWinner() throws IOException {
        GameBoard b = new GameBoard(); 
        Computer c = new Computer(b);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        assertEquals(c.scoreOutcome(b), 0);
    }
	
	@Test
    public void scoreRedWinner() throws IOException {
        GameBoard b = new GameBoard(); 
        Computer c = new Computer(b);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        assertEquals(c.scoreOutcome(b), -1);
    }
	
	@Test
    public void scoreBlackWinner() throws IOException {
        GameBoard b = new GameBoard(); 
        Computer c = new Computer(b);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        assertEquals(c.scoreOutcome(b), 1);
    }
	
	@Test
	public void findMovesAllPossible() {
		GameBoard b = new GameBoard(); 
        Computer c = new Computer(b);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        List<Integer> m = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
        	m.add(i);
        }
        assertEquals(c.findMoves(), m);		
	}
	
	@Test
	public void findMovesFilledColumn() throws IOException {
		GameBoard b = new GameBoard("files/columnBoard.txt"); 
        Computer c = new Computer(b);
        List<Integer> m = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
        	m.add(i);
        }
        m.remove(3);
        assertEquals(c.findMoves(), m);		
	}
	
	@Test
	public void chooseMove() throws IOException {
		GameBoard b = new GameBoard(); 
        Computer c = new Computer(b);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        assertEquals(c.chooseMove(), 3);		
	}
}
