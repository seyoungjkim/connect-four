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

public class GameBoardTest {
    public static GameBoard emptyBoard() {
        String[][] emptyBoard = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                emptyBoard[i][j] = "x";
            }
        }
        return new GameBoard(emptyBoard);
    }
    
    public static GameBoard singleBoard() {
        String[][] board = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = "x";
            }
        }
        board[5][3] = "r";
        return new GameBoard(board);
    }
    
    public static GameBoard columnBoard() {
        String[][] board = new String[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = "x";
            }
        }
        for (int i = 0; i < 6; i++) {
                board[i][3] = "r";
            }
        return new GameBoard(board);
    }
    
    @Test
    public void emptyBoardCreated() {
        GameBoard b = new GameBoard();
        assertEquals(b, emptyBoard());
    }
    
    @Test
    public void emptyBoardCreatedFromFile() throws IOException {
        GameBoard b = new GameBoard("files/emptyBoard.txt");       
        assertEquals(b, emptyBoard());
    }
    
    @Test
    public void singlePieceBoardCreatedFromFile() throws IOException { 
        GameBoard b = new GameBoard("files/singlePiece.txt");   
        assertEquals(b, singleBoard());
    }
    
    @Test
    public void columnBoardCreatedFromFile() throws IOException { 
        GameBoard b = new GameBoard("files/columnBoard.txt");   
        assertTrue(b.equals(columnBoard()));
    }
    
    @Test
    public void placePieceEmptyBoard() throws IOException {
        GameBoard b = new GameBoard();
        b.addPiece("r", 3);
        assertEquals(b, singleBoard());
    }
    
    @Test
    public void placePieceFullColumn() throws IOException {
        GameBoard b = new GameBoard("files/columnBoard.txt");   
        b.addPiece("b", 3);
        assertEquals(b, columnBoard());
    }
    
    @Test
    public void placePieceNonEmptyBoard() throws IOException {
        GameBoard c = new GameBoard("files/twoPieces.txt"); 
        GameBoard b = singleBoard();   
        b.addPiece("b", 3);
        assertEquals(b, c);
    }
    
    @Test
    public void checkFourColumn() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        assertTrue(b.checkFour());
    }
    
    @Test
    public void checkFourColumnPieceBetween() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        b.addPiece("b", 3);
        b.addPiece("r", 3);
        b.addPiece("r", 3);
        assertFalse(b.checkFour());
        
    }
    
    @Test
    public void checkFourRow() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 1);
        b.addPiece("r", 2);
        b.addPiece("r", 4);
        b.addPiece("r", 3);
        assertTrue(b.checkFour());
    }
    
    @Test
    public void checkFourRowPieceBetween() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 6);
        b.addPiece("r", 5);
        b.addPiece("r", 4);
        b.addPiece("b", 3);
        b.addPiece("r", 2);
        b.addPiece("r", 1);
        assertFalse(b.checkFour());
    }
    
    @Test
    public void checkFourLeftDiagonal() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 4);
        b.addPiece("b", 3);
        b.addPiece("r", 3);
        b.addPiece("b", 2);
        b.addPiece("b", 2);
        b.addPiece("r", 2);
        b.addPiece("b", 1);
        b.addPiece("b", 1);
        b.addPiece("b", 1);
        b.addPiece("r", 1);
        assertTrue(b.checkFour());
    }
    
    @Test
    public void checkFourRightDiagonal() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 1);
        b.addPiece("b", 2);
        b.addPiece("r", 2);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("r", 3);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("r", 4);
        assertTrue(b.checkFour());
    }
    
    @Test
    public void saveEmptyBoard() throws IOException {
        GameBoard b = new GameBoard(); 
        b.saveBoard("files/saved.txt");
        GameBoard c = new GameBoard("files/saved.txt");
        assertEquals(c, emptyBoard());
    }
    
    @Test
    public void saveColumnBoard() throws IOException {
        GameBoard b = columnBoard();
        b.saveBoard("files/saved.txt");
        GameBoard c = new GameBoard("files/saved.txt");
        assertEquals(c, columnBoard());
    }
    
    @Test
    public void saveDiagonalBoard() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 1);
        b.addPiece("b", 2);
        b.addPiece("r", 2);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("r", 3);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("r", 4);
        b.saveBoard("files/saved.txt");
        GameBoard c = new GameBoard("files/saved.txt");
        assertEquals(b, c);
    }
    
    @Test
    public void isDraw() throws IOException {
        GameBoard b = new GameBoard("files/draw.txt");
        assertTrue(b.checkDraw());
    }
    
    @Test
    public void isNotDraw() throws IOException {
        GameBoard b = new GameBoard(); 
        b.addPiece("r", 1);
        b.addPiece("b", 2);
        b.addPiece("r", 2);
        b.addPiece("b", 3);
        b.addPiece("b", 3);
        b.addPiece("r", 3);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("b", 4);
        b.addPiece("r", 4);
        assertFalse(b.checkDraw());
    }
    
    @Test
    public void redTurn() throws IOException {
        GameBoard b = new GameBoard("files/emptyBoard.txt"); 
        assertEquals(b.getTurn(), "r");
    }
    
    @Test
    public void blackTurn() throws IOException {
        GameBoard b = new GameBoard("files/singlePiece.txt"); 
        assertEquals(b.getTurn(), "b");
    }
}
