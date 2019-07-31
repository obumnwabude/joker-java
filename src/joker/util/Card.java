/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.util;

import java.util.Comparator;

/**
 * A card is used in playing the <em>joker</em> card game
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class Card implements Comparable<Card> {
    
    /**
     * Holds the value of the rank of the card. Runs from 1 to 13
     */
    public final int rank;
    
    /**
     * Holds the value of the suit of the card. Runs from 1 to 4
     */
    public final int suit;
    
    /**
     * Holds the real names of the ranks, useful for #toString
     */
    protected final String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "Jack", "Queen", "King"};
    
    /**
     * Holds the real names of the suits, useful for #toString
     */
    protected final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
    
    /**
     * Constructs a new Card with rank and suit as provided
     * @param rank the rank of the card
     * @param suit the suit of the card
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    /**
     * Constructs a new Card from the provided card argument 
     * @param that the card to be constructed as is 
     */
    public Card(Card that) {
        this.rank = that.rank;
        this.suit = that.suit;
    }
    
    /**
     * Returns a string representation of this card
     * @return A string representation of this card
     */
    @Override
    public String toString() {
        return this == null ? "" : RANKS[rank] + " " + SUITS[suit];
    }
    
    /**
     * Checks if this card equals the provided Card argument
     * @param that the card to test for equality with 
     * @return true if this card equals the provided card argument, false if 
     * otherwise
     */
    public boolean equals(Card that) {
        return this == null ? false : that == null ? false : rank == that.rank ?
                suit == that.suit : false;
    }
    
    /**
     * <p>Compares this Card to the provided Card argument.</p>
     * <p>The basis of comparison is such that the suit is first compared before 
     * the rank. In suit's comparison: Clubs is the least suit, Diamonds is 
     * greater than Clubs, Hearts is greater than Diamonds and Spades is the 
     * greatest suit.</p>
     * <p> In rank's comparison: Ace is the smallest card and the cards continue 
     * to be greater than each other in ascending numerical order. The Jack is
     * greater than 10. Queen is greater than Jack and King is the greatest.</p>
     * @param that the card to be compared with
     * @return 1 if this card is greater than the provided argument, 0 if it is 
     * equal, -1 if this card is less than the provided argument.
     */
    @Override
    public int compareTo(Card that) {
        return this == null ? -1 : suit > that.suit ? 1 : suit < that.suit ? -1 
                : rank > that.rank ? 1 : rank < that.rank ? -1 : 0;
    }
    
    /**
     * Checks if the provided rank argument is the rank of this card
     * @param rank the rank to be tested for equality with
     * @return true if the rank argument is the rank of this Card, false if 
     * otherwise
     */
    public boolean ofRank(int rank) {
        return this == null ? false : this.rank == rank;
    }
    
    /**
     * Checks if the provided suit argument is the suit of this card
     * @param suit the suit to be tested for equality with
     * @return true if the suit argument is the rank of this Card, false if 
     * otherwise
     */
    public boolean ofSuit(int suit) {
        return this == null ? false : this.suit == suit;
    }
    
    /**
     * This comparator gives preference to ranks rather than suits of cards when 
     * comparing a card with another.
     */
    public static final Comparator<Card> RANK_ORDER = new RankOrder();
    
    private static final class RankOrder implements Comparator<Card> {
        
        @Override 
        public int compare(Card a, Card b) {
            return a == null ? -1 : b == null ? 1 : a.rank > b.rank ? 1 : 
                    a.rank < b.rank ? -1 : a.suit > b.suit ? 1 : a.suit < b.suit 
                    ? -1 : 0;
        }
    }
}
