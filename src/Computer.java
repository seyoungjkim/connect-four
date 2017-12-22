import java.util.*;
import java.util.ArrayList;

public class Computer {
    private String turn;
    private GameBoard gb;
    
    private int bestOutcome;
    private GameBoard bestBoard;
    
    public static final String COMP_COLOR = "b";
    public static final String OPP_COLOR = "r";
    private static final int DEPTH = 3;
    private static final int BOARD_LENGTH = 7;
    private static final int BOARD_HEIGHT = 6;
    
    public Computer(GameBoard g) {
        this.gb = g;
        this.turn = gb.getTurn();
        bestOutcome = 0;
        bestBoard = g;
    }
       
    
    public List<Integer> findMoves(GameBoard b) {          
        List<Integer> moves = new ArrayList<Integer>();
        for (int i = 0; i < BOARD_LENGTH; i++) {            
            GameBoard tempBoard = new GameBoard(b.getBoard());
            if (tempBoard.addPiece(COMP_COLOR, i)) {
                moves.add(i);
            }
        }
        return moves;
    }
    
    public Map<Integer, GameBoard> findBoards(GameBoard b) {          
        List<Integer> moves = findMoves(b);
        Map<Integer, GameBoard> boards = new TreeMap<Integer, GameBoard>();
        for (int i = 0; i < moves.size(); i++) {            
            GameBoard tempBoard = new GameBoard(b.getBoard());
            tempBoard.addPiece(COMP_COLOR, i);
            boards.put(scoreOutcome(tempBoard), tempBoard);
        }
        return boards;
    }        
    
    public int chooseMove() {
        List<Integer> moves = findMoves(gb);
        Map<Integer, Integer> outcomes = new TreeMap<Integer, Integer>();
        for (Integer col: moves) {
            GameBoard tempGame = new GameBoard(gb.getBoard());
            tempGame.addPiece("r", col);
            outcomes.put(scoreOutcome(tempGame), col);
        }
        int maxOutcome = Collections.min(outcomes.keySet());
        return outcomes.get(maxOutcome);
    }
        
    
    public boolean makeMove() {
        return gb.addPiece(COMP_COLOR, chooseMove());
    }
    
    public int scoreOutcome(GameBoard board) {
        String winner = board.winner();
        if (winner.equals(COMP_COLOR)) {
            return 1;
        }
        else if (winner.equals(OPP_COLOR)) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
}