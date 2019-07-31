/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.playing;

import joker.board.Board;
import joker.util.Hand;
import joker.util.JoCard;

/**
 * The {@code Plays} interface defines how players of the <em>Joker</em> card
 * game are interacted with.
 * <p>Players in this context refers to the System / Computer
 * when playing the game or external users. In either ways every player must 
 * implement the {@code Plays} interface in order to participate in the
 * <em>Joker</em> card game.</p>
 * <p>The {@code Plays} interface is at the top hierarchy of the 
 * {@code joker.playing} package.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public interface Plays {
    
    /**
     * Returns the name of this player 
     * @return the name of this player
     */
    public String name() ;
    
    /**
     * Called when this {@code Player} takes turn while playing the
     * <em>Joker</em> card game.
     * @param board The {@code Board} on which this {@code Player} plays
     * the <em>Joker</em> card game. 
     */
    public void play(Board board);
    
    /**
     * Returns the hand of this player
     * @return the hand of this player
     */
    public Hand hand();
    
    /**
     * returns the some commanded {@code suit}
     * @return a commanded suit as integer
     */
    public int whatCommand();
    
    /**
     * Adds the provided {@link joker.util.JoCard} argument to this 
     * {@code Plays}'s {@code Hand}.
     * @param card The {@code JoCard} to add to this {@code Plays}'s {@code Hand}
     */
    default public void draw (JoCard card) {
        if (card != null) hand().add(card);
    }
    

    /**
     * Displays the player's name and score.
     */
    default public void displayPoints() {
        System.out.println(name() + " has " + points() + " points"); 
    }
    
    /**
     * Adds the provided {@link joker.util.JoCard} argument to this 
     * {@code Plays}' {@code Hand} when this {@code Plays} is to pick {@code
     * JoCard}s from the {@linkplain joker.board.Board}
     * @param card The {@code JoCard} picked by this {@code Plays}
     */
    default public void picked (JoCard card) {
        draw(card);
    }

    /**
     * Calculates and returns the player's score (penalty points). This points
     * are the sum of the points attached to the {@linkplain JoCard JoCards}
     * contained in the {@linkplain Hand hand} of this {@code Plays}.
     * @return The score of this {@code Plays}.
     * @see JoCard#point
     */
    default public int points() {
        int sum = 0;
        for (JoCard jc : hand()) {
            sum += jc.point();
        }
        return sum;
    }
}
