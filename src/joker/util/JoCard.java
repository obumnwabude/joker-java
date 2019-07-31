/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.util;

import java.util.Comparator;
/**
 * A {@code JoCard} is a type of {@linkplain Card Card} used in playing 
 * <em>Joker</em> card game.
 * <p>The {@code rank} of a {@code JoCard} is much the same as the {@linkplain
 * Card#rank rank} of a standard {@linkplain Card}. However {@code JoCard}s add
 * a special rank known as <b>Joker</b> to the {@code Black} and {@code Red}
 * {@code suits} only. {@code JoCard}s that have the  {@code Joker rank} have
 * special functionality as compared to other {@code JoCard}s. The 
 * {@code Joker rank} has an integer value of 14.</p>
 * <p>The {@code suit} of a {@code JoCard} is equally similar to the {@linkplain
 * Card#suit suit} of a standard {@linkplain Card}. However {@code JoCard}s have
 * two more suits: <b>Red</b> and <b>Black</b>; to the {@linkplain Card#suit suits},
 * available in a standard {@linkplain Card}. The {@code Black} suit has an 
 * integer value of 5 while the {@code Red} suit has an integer value of 6. 
 * These {@code suit}s have only one {@code rank} which is {@code Joker}.</p>
 * <p>The {@code Black suit} matches or serves as a generic {@code suit} to the
 * {@code Clubs} and {@code Spades suit} whereas the {@code Red suit} in turn 
 * equally match or is generic for the {@code Diamonds} and {@code Hearts suit}.</p>
 * @see Card
 * @see Card#rank rank
 * @see Card#suit suit
 * @see #match(joker.game.JoCard) match(JoCard) 
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class JoCard extends Card {
    
    /**
     * {@code RANKS} is an array of {@code Strings} that holds the names of the
     * {@linkplain #rank ranks} of {@code JoCard}s.
     */
   // public static final String[] RANKS = {null, "Ace", "2", "3", "4", "5", "6", 
   //     "7", "8", "9", "10", "Jack", "Queen", "King", "Joker"}; 
    public static final String[] RANKS = {null, "A","2", "3", "4", "5", "6", 
       "7", "8", "9", "10", "J", "Q", "K", "Joker"};
    
    /**
     * {@code SUITS} is an array of {@code Strings} that holds the names of the
     * {@linkplain #suit suits} to which {@code JoCard}s belong to.
     */
    //public static final String[] SUITS = {null, "Clubs", "Diamonds", 
    //    "Hearts", "Spades", "Black", "Red"};
    public static final String[] SUITS = {null, "Cassava", "Square", "Red",
        "Groundnuts", "Black", "Red"
    };
    
    /**
     * Constructs a <code>JoCard</code> object with {@linkplain #rank rank} and 
     * {@linkplain #suit suit} as provided. The provided arguments must be an 
     * integer: ranging from 1 to 14 for {@code rank}, and from 1 to 6 for 
     * {@code suit}.
     * @param rank The <code>rank</code> of this <code>Card</code>.
     * @param suit The <code>suit</code> of this <code>Card</code>.
     */
    public JoCard (int rank, int suit) {
        super(rank, suit);
    }
    
    /**
     * Constructs a <code>Card</code> object with the same {@linkplain #rank rank}
     * and {@linkplain #suit suit} as that of the argument.
     * @param that The Card to be used to construct this <code>Card</code>
     */
    public JoCard(JoCard that) {
        this(that.rank, that.suit);
    }
    
    /** 
     * Returns a String representation of this <code>JoCard</code>. The String 
     * returned specifies the {@linkplain #suit suit} of this <code>JoCard</code>
     * and the {@linkplain #rank rank} of this <code>JoCard</code> in its
     * <code>suit</code>.
     * @return A String representation of this {@linkplain JoCard JoCard}.
     */
    @Override
    public String toString() {
        if (this == null) return "";
        if (rank == 14) return SUITS[suit] + " " + RANKS[rank];
        return RANKS[rank] + " " + SUITS[suit];
    }
    
    /**
     * Tests if this {@code JoCard} matches the provided argument. 
     * The {@code JoCard}s match if either both JoCards are in the same suit or 
     * have the same rank. In addition, the {@code Black suit} matches 
     * the {@code Clubs} and {@code Spades suit}. The {@code Red suit} matches 
     * the {@code Diamonds} and the {@code Hearts suit}.
     * @param that The JoCard to test for match
     * @return {@code true} if this {@code JoCard} matches the provided argument,
     *       {@code false} if otherwise.
     */
    public boolean match (JoCard that) {
     /*   if (suit == that.suit) return true;
        if (suit == 5 || that.suit == 5) {
            if (that.suit == 1 || suit == 1) return true;
            if (that.suit == 4 || suit == 4) return true;
        }
        if (suit == 6 || that.suit == 6) {
            if (that.suit == 2 || suit == 2) return true;
            if (that.suit == 3 || suit == 3) return true;
        }
        if (this.rank == that.rank) return true;    */
        return JoCard.this.matchSuit(that) ? true : matchRank(that);
    }
    
    /**
     * Tests if the {@linkplain #suit suit} of this {@code JoCard} matches the 
     * {@code suit} of the provided argument. In addition to simple {@code suit}
     * matching, the {@code Black suit} matches the {@code Clubs} and
     * {@code Spades suit}. The {@code Red suit} matches the {@code Diamonds} 
     * and the {@code Hearts suit}.
     * @param that The JoCard to test for match
     * @return {@code true} if this {@code JoCard} matches the provided argument,
     *       {@code false} if otherwise.
     */
    public boolean matchSuit (JoCard that) {
        if (suit == that.suit) return true;
        if (suit == 5 || that.suit == 5) {
            if (that.suit == 1 || suit == 1) return true;
            if (that.suit == 4 || suit == 4) return true;
        }
        if (suit == 6 || that.suit == 6) {
            if (that.suit == 2 || suit == 2) return true;
            if (that.suit == 3 || suit == 3) return true;
        }
        return false;
    }
    
    /**
     * Tests if the {@linkplain #suit suit} of this {@code JoCard} matches the 
     * {@code suit} provided as argument. In addition to simple {@code suit}
     * matching, the {@code Black suit} matches the {@code Clubs} and
     * {@code Spades suit}. The {@code Red suit} matches the {@code Diamonds} 
     * and the {@code Hearts suit}.
     * @param suit The suit to test for match
     * @return {@code true} if the {@code suit} of this {@code JoCard} matches
     *      the provided argument, {@code false} if otherwise.
     */
    public boolean matchSuit(int suit) {
        if (this.suit == suit) return true;
        if (suit == 5) {
            if (this.suit == 1) return true;
            if (this.suit == 4) return true;
        } 
        if (suit == 6) {
            if (this.suit == 2) return true;
            if (this.suit == 3) return true;
        }
        if (this.suit == 5) {
            if (suit == 1) return true;
            if (suit == 4) return true;
        } 
        if (this.suit == 6) {
            if (suit == 2) return true;
            if (suit == 3) return true;
        }
        return false;
    }
    
    
    /**
     * Tests if the {@linkplain #rank rank} of this {@code JoCard} matches the 
     * {@code rank} of the provided argument. 
     * @param that The JoCard to test for match 
     * @return {@code true} if this {@code JoCard} matches the provided argument,
     *       {@code false} if otherwise.
     */
    public boolean matchRank (JoCard that) {
        return this.rank == that.rank;
    }
    
    /**
     * Return the points attached to this {@code JoCard}. 
     * <p>The points attached to a {@code JoCard} is the numerical value of its
     * {@linkplain #rank rank}. An exception to that is if the {@code rank} of 
     * a {@code JoCard} is {@code Ace, Queen} or {@code King} its points are 11.
     * If the {@code rank} is {@code Jack} its points are 20. If the {@code rank}
     * is {@code Joker}, its points are 40.
     * @return The points attached to this {@code JoCard}
     * @see JoCard
     */
    public int point () {
        if (this == null) return 0;
        if (rank == 14) return 40;
        if (rank == 11) return 20;
        if (rank == 1 || rank == 12 || rank == 13) return 11;
        return rank;
    }
    
    /**
     * Comparator to be used when there is need of comparing {@code JoCard}s 
     * in terms of {@code rank}s of cooling before adding in turns of picks; it
     * does not work on {@code JoCard}s not involved in the former mentioned.
     * @see java.util.Comparator
     */
    public static final Comparator<JoCard> COOL_ADD_ORDER = new CoolAddOrder();
    
    private static final class CoolAddOrder implements Comparator<JoCard> {
        
        @Override
        public int compare (JoCard a, JoCard b) {
            if (a == null || b == null) return 0;
            if (a.ofRank(11)) {
                if (b.rank == 11) return 0;
                if (b.rank == 1) return 1;
                if (b.rank == 7) return 1;
                if (b.rank == 14) return 1;
            }
            if (a.ofRank(1)) {
                if (b.rank == 11) return -1;
                if (b.rank == 1) return 0;
                if (b.rank == 7) return 1;
                if (b.rank == 14) return 1;
            }
            if (a.ofRank(7)) {
                if (b.rank == 11) return -1;
                if (b.rank == 1) return -1;
                if (b.rank == 7) return 0;
                if (b.rank == 14) return 1;
            }
            if (a.ofRank(14)) {
                if (b.rank == 11) return -1;
                if (b.rank == 1) return -1;
                if (b.rank == 7) return -1;
                if (b.rank == 14) return 0;
            }
            return 0;
        }
    }

}
