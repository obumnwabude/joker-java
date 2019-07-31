/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.util;

/**
 * A {@code Deck} is a group of {@linkplain JoCard JoCards} that contains all
 * the possible {@code JoCards} once instantiated.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class Deck extends JoCardCollection {

    /**
     * Constructs a {@code Deck} with name as provided by the argument.
     * <p>This constructor creates a {@code Deck} of <em>Joker</em> card game:
     * it adds two {@code Joker JoCards}: {@code Red Joker} and {@code Black Joker}</p>
     * @param label The label of this {@code Deck}.
     */
    public Deck (String label) { 
        super (label);
        
        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                add(new JoCard(rank, suit));
            }
        }
        
        add(new JoCard(14, 5));
        add(new JoCard(14, 6));
    }
    
    /**
     * Constructs a {@code Deck} with name as provided by the argument. The 
     * second argument specifies that this {@code Deck} is a standard {@code Deck}
     * of 52 playing {@linkplain joker.game.Card cards}. In other words without
     * the {@code Joker JoCards}.
     * @param label the label of this {@code Deck}.
     * @param i an integer, it should be equal to 0; it specifies that this
     *      {@code Deck} will not contain {@code Joker JoCards}.
     * @see #Deck(java.lang.String) Deck(String)
     */
    public Deck (String label, int i) { 
        super (label);
        
        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                add(new JoCard(rank, suit));
            }
        }
    }

}
