/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.util;

/**
 * A {@code Hand} is a group of {@linkplain joker.util.JoCard JoCards} that contains
 * {@code JoCards} that is used by a {@linkplain joker.playing.Plays player} 
 * while playing the <em>Joker</em> card game.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class Hand extends JoCardCollection {

    /**
     * Constructs a {@code Hand} with name as provided by the argument.
     * @param label The label of this {@code Hand}.
     */
    public Hand (String label) { 
        super(label);
    }
    
    /** 
     * Checks whether a {@linkplain joker.util.JoCard} argument with the 
     * provided {@code suit} can be found in this {@code Hand}.
     * @param cardSuit The {@code suit} to check for its presence in this 
     * {@code Hand}
     * @return {@code true} if there is at least one {@code JoCard} with the 
     * suit argument in this {@code Hand}, {@code false} if otherwise.
     */
    public boolean hasCardSuit (int cardSuit) {
        return cards.stream().anyMatch((jc) -> (jc.suit == cardSuit));
    }
    
    /** 
     * Checks whether a {@linkplain joker.util.JoCard} argument with the 
     * provided {@code rank} can be found in this {@code Hand}.
     * @param cardRank The {@code rank} to check for its presence in this 
     * {@code Hand}
     * @return {@code true} if there is at least one {@code JoCard} with the 
     * rank argument in this {@code Hand}, {@code false} if otherwise.
     */
    public boolean hasCardRank (int cardRank) {
        return cards.stream().anyMatch((jc) -> (jc.suit == cardRank));
    }
    

}
