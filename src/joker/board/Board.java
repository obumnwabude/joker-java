/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

import java.util.ArrayList;
import joker.util.JoCard;
import joker.board.situe.Situe;
import joker.playing.Plays;

/**
 * The {@code Board} interface defines how interaction with the Board used in 
 * <em>Joker</em> card game proceeds. It is unto the {@code Board} that 
 * {@link joker.util.JoCard JoCards} are played to or drawn from by 
 * {@linkplain joker.playing.Plays players} of the <em>Joker</em> card game.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public interface Board {
    
    /**
     * Deals {@link joker.util.JoCard JoCards} from this {@code Board}'s 
     * deck to the {@linkplain joker.playing.Plays players} specified by the
     * argument a handSize (provided by argument too) number of times.
     * @param players The players to deal JoCards unto
     * @param handSize The number of times to deal
     */
    public void deal (ArrayList<Plays> players, int handSize);
        
    /**
     * Returns the previous {@link joker.util.JoCard} that was played on this
     * {@code Board}.
     * @return the previous {@code JoCard} played on this {@code Board}.
     */
    public JoCard prev();
    
    /**
     * Provides a means by which a {@linkplain joker.playing.Plays player} carry
     * out a playing action on this {@code Board}. When a {@code player enters}
     * a {@code Board} it can then get a {@link joker.util.JoCard} 
     * {@linkplain #played played} unto this {@code Board} or {@linkplain 
     * #draw drawn} from this {@code Board}.
     * @param player the player that enters this Board
     * @return {@code true} if the {@linkplain joker.playing.Plays player} 
     * argument has successfully entered this {@code Board}, {@code false} if 
     * otherwise.
     */
    public boolean enter(Plays player);
    
    /**
     * Pops and returns (draws) a {@link joker.util.JoCard} from this
     * {@code Board}.
     * @param player The {@linkplain joker.playing.Plays player} that is drawing
     * from this {@code Board}.
     * @return the {@code JoCard} drawn from this {@code Board}.
     */
    public JoCard draw(Plays player);
    
    /**
     * Plays a {@link joker.util.JoCard} unto this {@code Board}.
     * @param player The {@linkplain joker.playing.Plays player} that is drawing
     * from this {@code Board}.
     * @param played the {@code JoCard} to played unto this {@code Board}.
     * @return {@code true} if the argument can be played and was successfully 
     * played, {@code false} if the argument was not played because it does not 
     * match the {@linkplain joker.board.situe.Situe#demand demands} of the 
     * current {@linkplain joker.board.situe.Situe}
     * @see #getCurrentSitue() getCurrentSitue()
     */
    public boolean played(Plays player, JoCard played);
    
    /**
     * Checks if undo is available on this {@code Board}
     * @return {@code true} if undo is available {@code false} if that is not
     * the case
     */
    public boolean canUndo();
    
    /**
     * Undoes the previous move. Note that {@linkplain #canUndo() canUndo()}
     * should be called first, if it returns {@code true} then this method
     * can be called. Otherwise, this method will do nothing.
     */
    public void undo();
    
    /**
     * Checks if redo is available on this {@code Board}
     * @return {@code true} if redo is available {@code false} if that is not
     * the case
     */
    public boolean canRedo();
    
    /**
     * Redoes the previously undone move. Note that {@linkplain #canRedo() 
     * canRedo()} should be called first, if it returns {@code true} then this
     * method can be called. Otherwise, this method will do nothing.
     */
    public void redo();
    
    /**
     * Returns the current {@linkplain joker.board.situe.Situe} on this 
     * {@code Board}.
     * @return The current {@code Situe} on this {@code Board}.
     */
    public Situe getCurrentSitue();
    
    /**
     * Returns the list of {@linkplain Turn turns} taken on this {@code Board}.
     * @return This {@code Board}'s TurnStack
     */
    public TurnStack turns();
    
    /**
     * Tells if Adding and cooling is allowed on this {@code Board}.
     * @return {@code true} if adding and cooling is allowed {@code false} if 
     * otherwise.
     * @see joker.situe.SitueType.PICKADDCOOL SitueType.PICKADDCOOL
     */
    public boolean addCool();
    
    /**
     * Returns the suit demanded by a {@linkplain joker.playing.Plays player} 
     * or by this {@code Board} when the {@linkplain joker.util.JoCard} that 
     * was played is J Command or has a rank of 11.
     * @return the suit as an integer value
     * @throws NoDemandedSuitException if the previously played {@code JoCard} is 
     * not J Command or does not have a rank of 11 or if the player has not yet
     * made the command of which {@code suit} should be played on this 
     * {@code Board}
     * @see joker.util.Card
     */
    public int demandedSuit() throws NoDemandedSuitException;
    
    /**
     * Tells if J command cards can be played at anytime on this {@code Board}
     * whether the J matches the colour on the {@code Board}
     * @return {@code true} if J command can always be played, {@code false} if 
     * otherwise
     */
    public boolean allowAlwaysJ();
    
    /**
     * Sets if J command cards can be played at anytime on this {@code Board}
     * whether the J matches the colour on the {@code Board}
     * @param value {@code true} if J command can always be played,
     * {@code false} if otherwise
     */
    public void setAllowAlwaysJ(boolean value);
    
    /**
     * Tells if any card can be played after J command was used for cooling 
     * pick cards 
     * @return {@code true} if any card can be played after J Command was used 
     * for cooling, {@code false} if otherwise.
     */
    public boolean anyAfterJCool();
    
    /**
     * Sets if any card can be played after J command was used for cooling 
     * pick cards 
     * @param value {@code true} if any card can be played after J Command 
     * was used for cooling, {@code false} if otherwise.
     */
    public void setAnyAfterJCool(boolean value);
    
    /**
     * Tells if any card can be played after J command was played by this 
     * {@code Board}
     * @return {@code true} if any card can be played after J command was 
     * played on this {@code Board}, {@code false} if otherwise
     */
    public boolean anyAfterBoardJ();
    
    /**
     * Sets if any card can be played after J command was played by this 
     * {@code Board}
     * @param value {@code true} if any card can be played after J command was 
     * played on this {@code Board}, {@code false} if otherwise
     */
    public void setAnyAfterBoardJ(boolean value);
    
    
}
