/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

/**
 * An action carried out during play when a {@linkplain Turn} is taken 
 * on the {@linkplain Board} where the <em>Joker</em> card game is played
 * is a {@code Command} and as such the action can be undone or redone
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public interface Command {
    
    /**
     * Carries out this {@code Command}
     */
    public void execute();
    
    /**
     * Undoes this {@code Command}
     */
    public void undo();
    
}
