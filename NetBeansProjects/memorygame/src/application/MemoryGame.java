/*
 * MemoryGame.java
 * This application uses an ArrayContainer object to store string names of 
 * animal species that will be used as labels on buttons. The player will 
 * uncover the buttons to pair the buttons with the same species.
 */
package application;

import ContainerADT.ArrayContainer;
import exception.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemoryGame extends JFrame implements ActionListener {

    private JPanel panel = new JPanel();
    private TileButton btnA = null; //Two Button references to form a pair
    private TileButton btnB = null;
    //define some constants to use for configuring the game interface
    private static final int GAMEBOARD_WIDTH = 300;
    private static final int GAMEBOARD_HEIGHT = 250;
    private static final int ROWS_IN_GAME = 5;
    private static final int COLS_IN_GAME = 4;
    private final ArrayContainer<String> container
            = new ArrayContainer<String>(ROWS_IN_GAME * COLS_IN_GAME);
    private final String[] symbols = {
        "Fish", "Unicorn", "Squirrel", "Dog", "Elephant", "Chimp",
        "Gopher", "Rhino", "Duck", "Cougar"};

    public static void main(String[] args) throws EmptyContainerException{
        Frame f = new MemoryGame();
        f.setSize(GAMEBOARD_WIDTH, GAMEBOARD_HEIGHT);
        f.setVisible(true);
    }

    /**
     * Initialize the game board: size it, store the symbols, create the
     * buttons, give each button a symbol to display and place the buttons on
     * the panel.
     */
    public MemoryGame() throws EmptyContainerException {
        super("Memory Game");
        init();
    }

    /**
     * listen for clicks on the buttons.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // if clicked button was in hidden state do the following tests, 
        // otherwise ignore clicks on visible buttons
        if (!((TileButton) e.getSource()).isTileVisible()) { //was in hidden state
            if ((btnA != null) && (btnB != null)) {// both  buttons are now visible
                // if they are not a pair then 
                if (!(btnA.getLabelForVisibleState().equals(btnB.getLabelForVisibleState()))) {
                    btnA.hideLabel(); //hide the buttons
                    btnB.hideLabel();
                    btnA = null;
                    btnA = (TileButton) e.getSource(); //assign clicked button to btnA
                    btnA.showLabel();
                    btnB = null;
                } else { // they are a pair
                    btnA = (TileButton) e.getSource();
                    btnA.showLabel();
                    btnB = null;
                }
            } // end if part for test for btnA and btnB not null
            else {  // btnA or btnB (or both ) is/are null
                if (btnA == null) {  // we are getting first of a pair
                    btnA = (TileButton) e.getSource();
                    btnA.showLabel();
                } else {  // we are getting second of a pair
                    btnB = (TileButton) e.getSource();
                    btnB.showLabel();
                }
            } // end else part of test for btnA and btnB both not null
        }//end test for visible tileButton
    } // end actionPerformed()

    /**
     * initialize the memory game
     */
    private void init() throws EmptyContainerException {
        String output = "";
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        testMethods1();
        storeSymbols();
        add(panel);             // add the panel to the frame
        //create the buttons, give them a symbol and
        //add an actionlistener to each.
        try {
            for (int i = 0; i < ROWS_IN_GAME * COLS_IN_GAME; i++) {
                btnA = new TileButton((container.removeRandom()));  // create button
                panel.add(btnA, 0);                       // place on panel
                btnA.addActionListener(this);            // enable listener
            }
            btnA = null;
            btnB = null;
        } catch (EmptyContainerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Container is empty"
                    + output, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * store the Strings in the container. Store two copies of each symbol
     */
    private void storeSymbols() {
        for (int i = 0; i < symbols.length; i++) {
            container.add(symbols[i]);
            container.add(symbols[i]);
        }//end loop over symbol array
    }

    /**
     * This methods has no purpose in the memory game application. This method
     * tests size, toString, isEmpty, remove and removeRandom methods from the
     * ArrayContainer class
     */
    private void testMethods1() throws EmptyContainerException {
        String output = "";
        try {
            storeSymbols();         // store the symbols in the container 
            JOptionPane.showMessageDialog(null, "There are 10 animals in the game\n");
            JOptionPane.showMessageDialog(null, "Animals in the container \n"
                    + container.toString());

            while (!container.isEmpty()) {

                String element = container.removeRandom(); //remove a random element
                output += element + ", ";

                container.remove(element);
            }
            //remove the duplicate element
        } catch (EmptyContainerException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Container is empty"
                    + output, JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchElementException z) {
            JOptionPane.showMessageDialog(null, z.getMessage(), "Container is empty"
                    + output, JOptionPane.ERROR_MESSAGE);
        }
    }

} // end class MemoryGame
