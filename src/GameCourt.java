/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.io.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another.
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
    private GameBoard gb;
    private ArrayList<Piece> pcs; 
    private Computer cp;
    
    private boolean playing = false; // whether the game is running 
    private boolean insUp = false; // whether instructions are up
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel instruct; // Displays instructions

    // Game constants
    public static final int COURT_WIDTH = 600;
    public static final int COURT_HEIGHT = 500;
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 500;
    public static final int PIECE_SIZE = 30;
    
    public static final Color BOARD_COLOR = Color.BLUE;

    public GameCourt(JLabel status, JLabel instruct) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    place(gb.getTurn(), 0);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    place(gb.getTurn(), 1);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    place(gb.getTurn(), 2);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    place(gb.getTurn(), 3);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_5) {
                    place(gb.getTurn(), 4);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_6) {
                    place(gb.getTurn(), 5);
                    compMove();
                } else if (e.getKeyCode() == KeyEvent.VK_7) {
                    place(gb.getTurn(), 6);
                    compMove();
                }
            }

        });
        
        this.status = status;
        this.instruct = instruct;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        gb = new GameBoard();
        pcs = new ArrayList<Piece>();
        cp = new Computer(gb);

        playing = true;
        status.setText("Running...");
        instruct.setText("");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        repaint();
    }   
    
    public void instructions() {
        if (!insUp) {
            //playing = false;
            insUp = true;
            String instructions = "<html><p> Hi! Welcome to Connect Four. </p><p></p>";
            instructions += "<p>You will start as the red player, playing against the computer, </p>";
            instructions += "<p>who has black pieces. The columns are numbered 1 to 7 from left </p>";
            instructions += "<p>to right. Press the appropriate key to place a piece in the </p>";
            instructions += "<p>column. If that column is full, you'll have to try again. </p><p></p>";
            instructions += "<p>The first player to get four pieces in a row, column, or diagonal </p>";
            instructions += "<p>wins!</p><p></p>";
            instructions += "<p>If you need to stop playing, you can choose to save the game. </p>";
            instructions += "<p>You'll be able to reload it later.</p><p></p>";
            instructions += "<p>Click 'Instructions' again to exit and start playing!</p></html>";
            instruct.setText(instructions);
        }
        else {
            instruct.setText("");
            insUp = false;
            playing = true;
            requestFocusInWindow();
            repaint();
        }
    }
    
    public void place(String c, int col) {
        if (playing) { 
            boolean added = gb.addPiece(c, col);
            if (added) {
                int x = gb.getLastCol();
                int y = gb.getLastRow();

                x = 20 + x * 80;
                y = 20 + y * 80;

                String player = "Red";
                Color color = Color.RED;
                if (c.equals("b")) {
                    color = Color.BLACK;
                    player = "Black";
                }
                Piece p = new Piece(x, y, BOARD_WIDTH, BOARD_HEIGHT, color);
                pcs.add(p);

                if (gb.checkFour()) {
                    playing = false;
                    status.setText(player + " wins!");
                }
                else if (gb.checkDraw()) {
                    playing = false;
                    status.setText("It's a draw.");
                }
                else {
                    if (c.equals("b")) {
                        color = Color.RED;
                        player = "Red";
                    }
                    else {
                        color = Color.BLACK;
                        player = "Black";
                    }            
                    status.setText("Running...");
                }
                                
                repaint();
            }
        }
    }
    
    public void compMove() {
        String c = Computer.COMP_COLOR;
        if (playing) { 
            boolean added = cp.makeMove();
            if (added) {
                int x = gb.getLastCol();
                int y = gb.getLastRow();

                x = 20 + x * 80;
                y = 20 + y * 80;

                String player = "Black";
                Color color = Color.BLACK;
                Piece p = new Piece(x, y, BOARD_WIDTH, BOARD_HEIGHT, color);
                pcs.add(p);

                if (gb.checkFour()) {
                    playing = false;
                    status.setText(player + " wins!");
                }
                else if (gb.checkDraw()) {
                    playing = false;
                    status.setText("It's a draw.");
                }
                else {
                    if (c.equals("b")) {
                        color = Color.RED;
                        player = "Red";
                    }
                    else {
                        color = Color.BLACK;
                        player = "Black";
                    }            
                    status.setText("Running...");
                }
                                
                repaint();
            }
        }
    }
    
    public void saveGame() throws IOException {
        gb.saveBoard("files/saved.txt");
        status.setText("Game successfully saved!");
        requestFocusInWindow();
        repaint();
    }
    
    public void loadGame() throws IOException {
        gb = new GameBoard("files/saved.txt");
        cp = new Computer(gb);
        String lastPlayer = "Red";
        if (gb.getTurn().equals("b")) {
            lastPlayer = "Black";
        }
        
        String[][] board = gb.getBoard();
        
        pcs = new ArrayList<Piece>();                
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                int y = i;
                int x = j;           

                x = 20 + x * 80;
                y = 20 + y * 80;

                if (!board[i][j].equals("x")) {
                    Color color = Color.RED;
                    if (board[i][j].equals("b")) {
                        color = Color.BLACK;
                    }
                    Piece p = new Piece(x, y, BOARD_WIDTH, BOARD_HEIGHT, color);
                    pcs.add(p);
                }
            }
        }       
        
        playing = true;
        status.setText("Game successfully loaded!");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        repaint();
        
    }
        
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);        
        g.setColor(BOARD_COLOR);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.WHITE);
        for (int i = 90; i < BOARD_WIDTH; i+=80) {
            g.drawLine(i, 0, i, BOARD_HEIGHT);
        }
        for (int i = 90; i < BOARD_HEIGHT; i+=80) {
            g.drawLine(0, i, BOARD_WIDTH, i);
        }
        for (Piece p : pcs) {
            p.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
   
}