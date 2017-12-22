/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("CONNECT FOUR");
        frame.setLocation(0, 0);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
                        
        
        // Panel for instructions
        final JPanel instruct_panel = new JPanel();
        frame.add(instruct_panel, BorderLayout.EAST);
        final JLabel instruct = new JLabel("");
        instruct_panel.add(instruct);
               

        // Main playing area
        final GameCourt court = new GameCourt(status, instruct);
        frame.add(court, BorderLayout.CENTER);
               

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);
        
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    court.saveGame();
                }
                catch (IOException i) {
                    status.setText("Can't save.");
                }
            }
        });
        control_panel.add(save);
        
        final JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    court.loadGame();
                }
                catch (IOException i) {
                    status.setText("No game is saved.");
                }
            }
        });
        control_panel.add(load);
        
        final JButton ins = new JButton("Instructions");
        ins.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.instructions();
            }
        });
        control_panel.add(ins);
        


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
        
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}