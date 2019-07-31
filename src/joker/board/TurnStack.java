/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Observable;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
/**
 * TurnStack represents a group of {@linkplain Turn turns} taken on
 * the {@linkplain Board} on which the <em>Joker</em> card game is played.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class TurnStack extends Observable implements Iterable<Turn>, 
        Serializable {
    
    /**
     * Tells whether the change was a remove or add
     */
    private int type;
           
    /**
     * Encapsulated in this class
     */
    private final ArrayDeque<Turn> turns;
    
    /**
     * Constructs a new TurnList
     */
    public TurnStack() {
        turns = new ArrayDeque<>();
        type = 0;
    }
    
    /**
     * adds the provided turn to the end of this {@code TurnStack}
     * @param turn The {@code Turn} to be added
     */
    public void add (Turn turn) {
        turns.addLast(turn);    
        type = 1;
        setChanged();
        notifyObservers(type);
        type = 0;
    }
    
    /**
     * removes and returns the last turn from this {@code TurnStack}
     * @return The popped turn
     */
    public Turn remove() {
       Turn hold = turns.removeLast();
       type = 2;
       setChanged();
       notifyObservers(type);
       type = 0;
       return hold;
    }
    
    /**
     * Retrieves but does not remove the last {@linkplain Turn} of this 
     * {@code TurnStack}
     * @return The unremoved last Turn
     */
    public Turn element() {
        return turns.peekLast();
    }
    
    /**
     * Checks and returns true if this TurnStack is empty
     * @return {@code true} if this {@code TurnStack} is empty
     */
    public boolean empty() {
        return turns.isEmpty();
    }
    
    /**
     * returns the size of this {@code TurnStack}
     * @return the size of this {@code TurnStack}
     */
    public int size() {
        return turns.size();
    }
    
    /**
     * Removes all the Turns in this TurnStack
     */
    public void clear() {
        turns.clear();
    }
    
    @Override
    public Iterator<Turn> iterator() {
        return turns.iterator();
    }
    
    @Override 
    public void forEach(Consumer action) {
        turns.forEach(action);
    }
    
    @Override 
    public Spliterator<Turn> spliterator() { 
       return turns.spliterator();
    }
    
    /**
     * Returns a {@linkplain java.util.stream.Stream} over this TurnStack
     * @return the required Stream
     */
    public Stream<Turn> stream() {
        return turns.stream();
    }
    
    /**
     * Returns a parallel or concurrent {@linkplain java.util.stream.Stream} 
     * over this TurnStack
     * @return the required parallel stream
     */
    public Stream<Turn> parallelStream() {
        return turns.parallelStream();
    }
    
    
}
