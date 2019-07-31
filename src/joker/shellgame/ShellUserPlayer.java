/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.shellgame;

import joker.board.Board;
import joker.util.JoCard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import joker.playing.UserPlayer;
/**
 * This class interacts directly with an external user who plays the 
 * <em>Joker</em> card game.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class ShellUserPlayer extends UserPlayer {
    
    /*
    Input stream used by this player
    */
    private final BufferedReader br;
    
    /**
     * Constructs a new <code>UserPlayer</code>
     * @param name The name of this {@code UserPlayer}
     */
    public ShellUserPlayer(String name) {
        super(name);
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    @Override
    public void play(Board board) {
        System.out.println();
        System.out.println("Board: " + board.prev());
        showCards();
        int use = inputHandler(1);
        if (use == 0) {
            draw(board.draw(this));
            return;
        }
        JoCard toPlay = myHand.pop(use - 1);
        if(!board.played(this, toPlay)) myHand.add(toPlay);
    }

    /**
     * displays the {@link joker.util.JoCard JoCards} contained in this
     * {@code ShellUserPlayer}'s {@code hand}.
     */
    public void showCards() {
        if (myHand.empty()) return;
        System.out.println("Cards of " + myName + " are: ");
        myHand.sort();
        int no = 0;
        System.out.println("\t" + no + ". Go Board");
        for (JoCard jc : myHand) {
            ++no;
            System.out.print("\t" + no + ". ");
            System.out.println(jc);
        }
    }
    
    /**
     * displays play undo and redo options its context is out of board entry
     * @param board The board to which the chosen options can be acted on
     */
    public void showOptions(Board board) {
        int no = 0;
        System.out.println("Select an option");
        System.out.println("\t" + no + ". Play");
        no++;
        if (board.canUndo()) System.out.println("\t" + no + ". Undo");
        no++;
        if (board.canRedo()) System.out.println("\t" + no + ". Redo");
    }
    
    
    /**
     * handles external user input from console
     * @param given
     * @return 
     */
    public int inputHandler(int given) {
        String collected;
        int use;
        switch (given) {
            case 0:
                try {
                    collected = br.readLine().trim();
                    use = Integer.parseInt(collected);
                    if (use > 2 || use < 0) {
                        System.out.println("Enter a digit corresponding to an"
                                + "option");
                        use = inputHandler(given);
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Please enter a number matching an option");
                    use = inputHandler(given);
                }   break;
            case 1:
                try {
                    collected = br.readLine().trim();
                    use = Integer.parseInt(collected);
                    if (use > myHand.size() || use < 0) {
                        System.out.println("Enter a digit corresponding to a card "
                                + "number");
                        use = inputHandler(given);
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Please enter a number matching a card you "
                            + "want to play or enter 0 to go board.");
                    use = inputHandler(given);
                }   break;
            default:
                try {
                    collected = br.readLine().trim();
                    use = Integer.parseInt(collected);
                    if (use > 4 || use < 1) {
                        System.out.println("Enter a digit corresponding to a "
                                + "numbered colour");
                        use = inputHandler(given);
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Please enter a number matching a colour "
                            + "you want to command.");
                    use = inputHandler(given);
                }   break;
        }
        return use;
    }
    
    /**
     * handles the chosen options
     * @param board the board on which chosen options will be applied
     * @return {@code true} if the player's option is to play
     */
    private boolean handleOptions(Board board) {
        int use = inputHandler(0);
        if (board.canUndo()) {
            if (use == 1) {
                board.undo();
                return false;
            } 
        } else {
            if (use == 1) {
                System.out.println("Select a valid option");
                handleOptions(board);
            }
        }
        if (board.canRedo()) {
            if (use == 2) {
                board.redo();
                return false;
            }
        } else {
            if (use == 2) {
                System.out.println("Select a valid option");
                handleOptions(board);
            }
        }
        return true;
    }
    
    /**
     * gives this {@code ShellUserPlayer} power to choose options
     * @param board The board in which chosen option can be acted on
     * @param callUndo if there should be an undo prompt or not
     * @return {@code true} if the player's choice is to play
     */
    public boolean context(Board board, boolean callUndo) {
        if (callUndo) {
            showOptions(board);
            return handleOptions(board);
        }
        return true;
    }

    @Override
    public int whatCommand() {
        int use; 
        showColours();
        use = inputHandler(2);
        return use;
    }
    
    /**
     * displays the 4 possible colours of a card
     */
    public void showColours() {
        System.out.println("Enter a number matching a colour you want to "
                + "command");
        System.out.println("\t1. Cassava");
        System.out.println("\t2. Square");
        System.out.println("\t3. Red");
        System.out.println("\t4. Groundnuts");
    }
    
}
