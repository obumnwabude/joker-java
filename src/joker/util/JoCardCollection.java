/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.util;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.Random;
import java.util.Iterator;
import java.util.Observable;
import java.util.Spliterator;
import joker.playing.Plays;

/**
 * Defines a group of {@linkplain joker.game.JoCard JoCards}.
 * <p>A {@code JoCardCollection} is the super class of all group of {@code JoCard}s
 * used in playing <em>Joker</em> card game.
 * @author OBUMUNEME NWABUDE 
 * @since 1.0
 */
public class JoCardCollection extends Observable implements Iterable<JoCard> {
    
    protected final String label;
   
    protected final ArrayList<JoCard> cards;
    
    /**
     * Constructs a new {@code JoCardCollection} with a label as provided in
     * the argument.
     * <p>Note that the label of this {@code JoCardCollection} cannot be changed
     * once created by this constructor.</p>
     * @param label The name of this {@code JoCardCollection}.
     */
    public JoCardCollection (String label) { 
        this.label = label;
        this.cards = new ArrayList<>();
    }
    
     /**
     * Returns a String representation of this {@code JoCardCollection}.
     * @return a String representation of this {@code JoCardCollection}.
     */
    @Override
    public String toString() {
        return label + ": " + cards.toString();
    }
    
    /**
     * Returns the size of this {@code JoCardCollection}.
     * @return The size of this {@code JoCardCollection}.
     */
    public int size() { 
        return cards.size();
    }
    
    /**
     * Checks whether this {@code JoCardCollection} is empty and returns
     * <code>true</code> if that is the case.
     * @return <code>true</code> if this {@code JoCardCollection} has no 
     * {@linkplain joker.game.JoCard} in it. <code>false</code> if otherwise. 
     */
    public boolean empty() {
        return cards.isEmpty();
    }
    
     /** 
     * Returns the label of this {@code JoCardCollection}. 
     * @return the label of this {@code JoCardCollection}.
     */
    public String label() {
        return label;
    }
    
    /** 
     * Returns the {@linkplain joker.game.JoCard} from this {@code JoCardCollection} 
     * found at the position provided by the argument.
     * @param i The position of the {@code JoCard} to be gotten from this 
     * {@code JoCardCollection}. 
     * @return The {@code JoCard} found at the provided position of this 
     * {@code JoCardCollection}.
     */
    public JoCard get(int i) { 
        return cards.get(i);
    }
    
    /**
     * Returns but does not remove the last {@linkplain joker.game.JoCard} from 
     * this {@code JoCardCollection}.
     * @return The last {@code JoCard} from this {@code JoCardCollection}.
     */
    public JoCard last() {
        return size() == 0 ? get(0) : get(size() - 1);
    }

    /**
     * Returns but does not remove the first {@linkplain joker.game.JoCard} from 
     * this {@code JoCardCollection}.
     * @return The first {@code JoCard} from this {@code JoCardCollection}.
     * {@code null} is returned if this {@code JoCardCollection} is empty.
     */
    public JoCard first() {
        return empty() ? null : get(0);
    }
    
    /** 
     * Checks whether the {@linkplain joker.game.JoCard} argument is contained
     * in this {@code JoCardCollection}.
     * @param that The {@code JoCard} to check for its presence in this 
     * {@code JoCardCollection}
     * @return {@code true} if the argument is found in this {@code JoCardCollection},
     *  {@code false} if otherwise.
     */
    public boolean has (JoCard that) {
        return cards.contains(that);
    }
    
     /**
     * Adds the provided {@linkplain joker.game.JoCard} argument to this
     * {@code JoCardCollection}.
     * @param card The {@code JoCard} to add to this {@code JoCardCollection}.
     */
    public void add(JoCard card) {
        cards.add(card);
        setChanged();
        notifyObservers();
    }
    
    /**
     * Adds all the {@linkplain joker.game.JoCard JoCards} found in the 
     * {@code JoCardCollection} argument to this {@code JoCardCollection}.
     * @param toAdd A {@code JoCardCollection} whose {@code JoCard}s are to be
     * added to this {@code JoCardCollection}.
     */
    public void addAll(JoCardCollection toAdd) {
        try {
            cards.addAll(toAdd.cards);
        } catch (Exception e) {
            for (JoCard c: cards) toAdd.cards.add(c);
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * Removes and returns the {@linkplain joker.game.JoCard} found at the 
     * position provided as argument from this {@code JoCardCollection}, 
     * then shifts all the other {@code JoCard} members left to fill the gap 
     * created. 
     * @param i The position at this {@code JoCardCollection} from 
     * which the {@code JoCard}, there present should be removed.
     * @return The {@code JoCard} that has been removed. {@code null} is returned 
     * if the {@code JoCard} provided parameter is negative or is greater than 
     * the size of this {@code JoCardCollection}.
     */
    public JoCard pop (int i) { 
        if (i < 0 || i >= cards.size()) return null;
        setChanged();
        notifyObservers();
        return cards.remove(i);
    }
    
     /**
     * Removes and returns the last {@linkplain joker.game.JoCard} 
     * of this {@code JoCardCollection}.
     * @return The {@code JoCard} that has been removed.
     */
    public JoCard pop () { 
        int i = size() - 1;
        setChanged();
        notifyObservers();
        return pop(i);
    }
    
    /**
     * Returns the index of the given argument 
     * @param that The {@code JoCard} to check for its presence in this 
     *      {@code JoCardCollection}
     * @return the index of the argument in this {@code JoCardCollection}, 
     *      -1 is the argument is not found.
     * @see java.util.ArrayList#indexOf
     */
    public int indexOf (JoCard that) {
        return cards.indexOf(that);
    }
    /**
     * Removes or deals out the last {@linkplain joker.game.JoCard} from this 
     * {@code JoCardCollection} and adds it to the provided {@code JoCardCollection} 
     * argument. The second argument specifies how many times 
     * dealing takes place. 
     * @param that The {@code JoCardCollection} to deal {@code JoCard}s unto.
     * @param n The number of times to deal.
     */
    public void deal(JoCardCollection that, int n) {
        for (int i = 0; i < n; i++) { 
            JoCard card = pop();
            that.add(card);
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * Deals one {@code JoCard} after the other to each {@linkplain 
     * joker.playing.Plays player} contained in the provided argument for 
     * the number of times specified by the second argument.
     * @param players The {@code players} to deal {@code JoCards} to. 
     * @param n the number of times to deal to each player
     */
    public void deal(ArrayList<Plays> players, int n) {
        while (n > 0) {
            for (Plays pl : players) deal(pl.hand(), 1);
            n--;
        }
        setChanged();
        notifyObservers();
    }
    
    /**
     * Deals all {@linkplain JoCard} in this {@link JoCardCollection} 
     * unto the provided argument.
     * @param that The {@link JoCardCollection} to deal all cards to.
     */
    public void dealAll(JoCardCollection that) { 
        int i = size();
        deal(that, i);
        setChanged();
        notifyObservers();
    }
    
    /*
    swaps two JoCards their positions are provided by the argument
    */
    private void swap (int i, int j) { 
        JoCard temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp); 
    }
    
    /**
     * Shuffles the {@linkplain joker.game.JoCard JoCards} in this 
     * {@code JoCardCollection}.
     * @see java.util.Collections#shuffle Collections#shuffle
     */
    public void shuffle() {
   /*     try {
            Collections.shuffle(cards);
        } catch (UnsupportedOperationException exc) {*/
            Random random = new Random();
            for (int i = size() - 1; i > 0; i--) {
                int j = random.nextInt(i);
                swap(i, j);
            }
        //}
    }
    
    /**
     * Sorts the {@linkplain joker.game.JoCard JoCards} in this 
     * {@code JoCardCollection} such that they will be in ascending order 
     * according to the {@linkplain joker.game.JoCard#compareTo JoCard.compareTo}.
     */
    public void sort() {
        try {
            cards.sort(null);
        } catch (Exception e) { }
    }

    @Override
    public Iterator iterator() {
        return cards.iterator();
    }

    @Override
    public void forEach(Consumer action) {
        cards.forEach(action);     
    }

    @Override
    public Spliterator spliterator() {
        return cards.spliterator(); 
    }
    
}
