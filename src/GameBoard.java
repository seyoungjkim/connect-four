import java.util.Arrays;
import java.io.*;
import java.io.FileNotFoundException;

public class GameBoard {
    private String[][] board;
    private String turn;
    private int lastCol;
    private int lastRow;
    private static final int LENGTH = 7;
    private static final int HEIGHT = 6;
    private boolean gameOver;
    
    public GameBoard() {
        turn = "r";
        board = new String[HEIGHT][LENGTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                board[i][j] = "x";
            }
        }
        lastCol = 0;
        lastRow = 0;
        gameOver = false;
    }
    
    public GameBoard(String[][] arr) {       
        board = arr;
        lastCol = 0;
        lastRow = 0;
        turn = whoseTurn();
    }
    
    public GameBoard(String filename) throws IOException {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        try {            
            FileReader fr = new FileReader(filename);
            board = new String[HEIGHT][LENGTH];
            BufferedReader br = new BufferedReader(fr);
            int i = 0;
            String line = br.readLine();
            while (line != null && i < HEIGHT) {            
                String[] arr = line.split("");
                for (int j = 0; j < Math.min(LENGTH, arr.length); j++) {
                    board[i][j] = arr[j];
                }
                line = br.readLine();
                i++;
            }           
            br.close();
            turn = whoseTurn();
        }
         catch (IOException e) {
            throw new FileNotFoundException("no such file");
        }
        lastCol = 0;
        lastRow = 0;
        
    }
    
    private String whoseTurn() {
        int red = 0;
        int black = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                String c = board[i][j];
                    if (c != null) {
                    if (c.equals("r")) {
                        red++;
                    }
                    else if (c.equals("b")) {
                        black++;
                    }
                }
            }
        }
        if (red == black) {
            return "r";
        }
        else {
            return "b";
        }
    }
    
    public String[][] getBoard() { 
        String[][] arr = new String[HEIGHT][LENGTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                arr[i][j] = board[i][j];
            }
        }        
        return arr;
    }
    
    public String getTurn() {
        return turn;
    }
    
    public int getLastRow() {
        return lastRow;
    }
    
    public int getLastCol() {
        return lastCol;
    }
    
    public boolean gameOver() {
        return gameOver;
    }
    
    public boolean equals(Object o) {
        GameBoard gb = (GameBoard) o;
        String b[][] = gb.getBoard();
        boolean same = true;
        for (int i = 0; i < HEIGHT; i++) {           
            for (int j = 0; j < LENGTH; j++) {
                if (!board[i][j].equals(b[i][j])) {
                    same = false;
                }
            }
        }
        return same;
        
    }
    
    public boolean addPiece(String c, int col) {
        if (col >= 0 && col < LENGTH) {
        	if (board[0][col].equals("x")) {
	            int j = 0;
	            while (j < HEIGHT && board[j][col].equals("x")) {                
	                j++;
	            }
	            board[j - 1][col] = c;
	            if (turn.equals("r")) {
	                turn = "b";             
	            }
	            else {
	                turn = "r";
	            } 
                lastCol = col;
                lastRow = j - 1;
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    public boolean checkFour() {
        String c = board[lastRow][lastCol];
        boolean row = false;
        boolean diag = false;
        boolean col = false;
        
        // checks if there are 4 in a column
        int colCount = 0;
        int i = lastRow;
        while (i < Math.min(HEIGHT - 1, lastRow + 3) && colCount <= 3) {
            if (c.equals(board[i][lastCol]) && 
                c.equals(board[i + 1][lastCol])) {
                colCount++;
            }
            i++;
        }
        if (colCount >= 3) {
            col = true;
        }
        
        // checks if there are 4 in a row
        int rowCount = 0;
        int j = Math.max(lastCol - 3, 0);
        while (j < Math.min(LENGTH - 1, lastCol + 3) && rowCount <= 3) {
            if (c.equals(board[lastRow][j]) && 
                c.equals(board[lastRow][j + 1])) {
                rowCount++;
            }
            j++;
        }
        if (rowCount >= 3) {
            row = true;
        }
        
        // checks if there are 4 in a diagonal
        int left = 0;
        int x = lastCol;
        int y = lastRow;
        int diff = 3;
        while (x > 0 && y > 0 && diff > 0) {
            x--;
            y--; 
            diff--;
        }
        while (x < Math.min(LENGTH - 1, lastCol + 3) && y < Math.min(HEIGHT - 1, lastRow + 3)) {
            if (c.equals(board[y][x]) &&
                c.equals(board[y + 1][x + 1])) {
                left++;
            }
            x++;
            y++;
        }
               
        int right = 0;
        x = lastCol;
        y = lastRow;
        diff = 3;
        while (x < LENGTH - 1 && y > 0 && diff > 0) {
            x++;
            y--;
            diff--;
        }
        while (x > Math.max(lastCol - 3, 0) && y < Math.min(HEIGHT - 1, lastRow + 3)) {
            if (c.equals(board[y][x]) &&
                c.equals(board[y + 1][x - 1])) {
                right++;
            }
            x--;
            y++;
        }
        if (left >= 3 || right >= 3) {
            diag = true;
        }
        
        boolean four = (row || col || diag);
        if (four) {
            gameOver = true;
        }
        return four;
    }
    
    public boolean checkDraw() {
        if (!gameOver) {
            int empty = 0;
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < LENGTH; j++) {
                    if (board[i][j].equals("x")) {
                        empty++;
                    }
                }
            }
            if (empty == 0) {
                gameOver = true;
            }
            
        }
        return gameOver;
    }
    
    public String winner() {
        if (checkFour()) {
            return board[lastRow][lastCol];
        }
        else {
            return "x";
        }
    }
    
    public void saveBoard(String filename) throws IOException {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < HEIGHT; i++) {
                String line = "";
                    for (int j = 0; j < LENGTH; j++) {
                        line = line + board[i][j];
                    }            
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new IOException();
        }
    }
    
    public void print() {
    	for (int i = 0; i < HEIGHT; i++) {   	
            for (int j = 0; j < LENGTH; j++) {
            	System.out.print(board[i][j]);
            }
            System.out.println("");
    	}
    }
}