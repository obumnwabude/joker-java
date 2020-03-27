/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board.situe;

import joker.board.Board;
import joker.board.NoDemandedSuitException;
import joker.board.Turn;
import joker.util.JoCard;
import joker.playing.Plays;
import joker.board.TurnStack;

/**
 * The {@code abstract Situe} class represents a situation of play on the 
 * {@linkplain joker.board.Board Board} used in playing the <em>Joker</em>
 * card game. 
 * <p>The {@code abstract Situe} class consists of a continuous flow of 
 * {@linkplain joker.board.Turn}s taken on the {@linkplain joker.board.Board} 
 * where the <em>Joker</em> card game is played. As such, it offers control
 * over the playing procedure and makes sure that all specified rules of the
 * <em>Joker</em> card game are strictly observed. 
 * <p>{@code Situe}s can't be instantiated but an instance of a {@code Situe}
 * having a particular {@linkplain SitueType} can be obtained through the 
 * {@linkplain #getSitue getSitue} factory method.</p>
 * <p>{@code Situe}s have two special methods that make sure 
 * {@linkplain joker.board.Turn}s taken on the {@code Board} observe game rules:<br>
 * A {@linkplain #demand(joker.util.JoCard) demand(JoCard)} method 
 * that tells if its argument can be played on the {@code Board}. Note that if 
 * this method returns {@code false} for a {@code JoCard} and the card is still
 * played, the card will be rejected by the {@code Board}.
 * A {@code handle()} method, that should be called at the end of each 
 * {@linkplain joker.board.Turn} on the {@code Board}. It has two forms: <br>
 * {@linkplain #handle(joker.playing.Plays, joker.util.JoCard) handle(player,
 * played)} that takes the player and the card played by that player as 
 * arguments.<br>
 * {@linkplain #handle(joker.playing.Plays) handle(player)} that takes the 
 * player that played as argument. This method should be called when play by 
 * the player argument was not a card-playing move such as been skipped or
 * picking cards or going to the board.<br>
 * {@linkplain #handle(joker.board.Board) handle(board)} 
 * that rather takes a {@code Board} argument. it should be the first
 * type of handle() method called since the {@code Board} first takes turn 
 * in the <em>Joker</em> card game. Hence, its called only once.</p>
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public abstract class Situe {
    
    /**
     * The number of {@linkplain joker.util.JoCard}s to pick if the 
     * {@linkplain #type type()} of this {@code Situe} is PICK or 
     * PICKADDCOOL, however its value is zero if this {@code Situe} has any 
     * other different type.
     */
    protected int toPick;
    
    /**
     * The {@code suit} demanded when the {@linkplain #type type()} of this 
     * {@code Situe} is COMMAND.
     * @see joker.util.Card
     * @see joker.util.JoCard JoCard
     */
    protected int commandSuit;
    
    /**
     * Signals that a J command was used to cool picks and any card can then be
     * played
     */
    protected boolean fromPickAddCool = false;
    
    /**
     * Signals if handling this {@code Situe} terminated the {@code Situe},
     * a value of {@code true} or if this {@code Situe} has not yet been 
     * handled or it has been handled but not yet terminated, in this case
     * it has a value of {@code false}.
     */
    protected boolean handled = false;
    
    /**
     * The {@linkplain joker.board.situe.SitueType type} of this {@code Situe}.
     */
    protected SitueType situeType;
        
    /**
     * The {@linkplain joker.board.Board} on which {@code Situe} occurs.
     */
    protected final Board board;
    
    /**
     * The {@linkplain joker.board.Turn} taken on the {@code joker.board.Board}
     * when this {@code Situe} is handled.
     */
    protected Turn turn;
    
    /**
     * The {@linkplain joker.board.TurnStack} to which {@linkplain #turn turn} 
     * is added.
     */
    protected final TurnStack turns;
    
    /**
     * Used by the subclasses of this {@code Situe} class.
     * @param board The {@code Board} where this {@code Situe} occurs
     */
    protected Situe (Board board) {
        this.board = board;
        turns = board.turns();
    }
     
      /**
      * Checks if the {@link joker.util.JoCard} argument matches the next
      * possible move specified by this {@code Situe} and returns {@code true}
      * if that is the case.
      * @param played The {@code JoCard} to check if it matches the demands of 
      * this {@code Situe}.
      * @return {@code true} if the provided {@code JoCard} argument matches 
      * the next possible move, {@code false} if otherwise or if no 
      * {@code JoCard} is needed.
      */
    public boolean demand(JoCard played) {
        return false;
    } 
    
    /**
     * <i>handling</i> refers to procedures that take place after a turn is 
     * taken on the {@linkplain joker.board.Board} on which this {@code Situe}
     * is happening. This method is expected to be called before the next turn
     * is taken to ensure that played card is according to rules of the <em>Joker
     * card game</em>.
     * @param player The {@linkplain joker.playing.Plays player} that took
     * the last turn before handling should take place.
     * @param played The {@link joker.util.JoCard} played by the {@code 
     * Player} argument
     * @return an handled {@code Situe}
     */
    public Situe handle (Plays player, JoCard played) {
        return this;
    }
    
    /**
     * <i>handling</i> refers to procedures that take place after a turn is 
     * taken on the {@linkplain joker.board.Board} on which this {@code Situe}
     * is happening. This method should be called when play by the provided 
     * player argument was not a card-playing move such as been skipped, 
     * picking cards or going to the {@code Board}.
     * @param player The {@linkplain joker.playing.Plays player} that took
     * the last turn before handling should take place.
     * @return an handled {@code Situe}
     */
   public Situe handle(Plays player) {
       return this;
   }

    /**
     * Returns a {@code String} representation of this {@code Situe}.
     * <p>The returned String comprises the type of the {@code Situe} and 
     * the first {@link joker.util.JoCard} that was played when it started.
     * @return A {@code String} representation of this {@code Situe}.
     */
    @Override
    public String toString() {
        return "A " + situeType + " type of Situe.";
    }   
     
     /**
      * Returns the number of {@link joker.util.JoCard JoCards} to be picked by 
      * the {@linkplain joker.playing.Plays player} who takes the next turn
      * if the {@linkplain #type type} of this {@code Situe} is {@code PICK} or
      * {@code PICKADDCOOL}.
      * @return The number of {@code JoCard}s to pick.
      */
     public int toPick() {
         return toPick;
     }
     
     /**
      * Called when picking takes place 
      * @throws NoPickException if there is no pick possible
      */
    public void pick() throws NoPickException {    
        if (toPick > 0) toPick--;
        else throw new NoPickException("Attempt to pick when there is no pick "
                + "available.");
    }
    
    /**
     * Returns the {@linkplain joker.board.situe.SitueType} of this 
     * {@code Situe}.
     * @return The {@code SitueType} of this {@code Situe}.
     */
    public SitueType type() {
        return situeType;
    }
   
    /**
     * Handles the first {@linkplain joker.board.Turn} which is always taken 
     * by the {@linkplain joker.board.Board}.
     * @param board The {@linkplain joker.board.Board board} that began the game
     * @return an handled {@code Situe}.
     * @see #handle(Plays, JoCard)
     */
    public Situe handle (Board board) {
        setBoardTurn(board);
        return this;
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param board the board that took the first turn
     */
    protected void setBoardTurn(Board board) {
        turn = new Turn(board);
        if (type() == SitueType.SKIP) {
            turn.toSkip = true;
        }
        if (type() == SitueType.PICK || type() == SitueType.PICKADDCOOL) {
            turn.toPick = true;
            turn.picks = toPick();
        }
        if (type() == SitueType.COMMAND) {
            turn.commanded = true;
            turn.commandedSuit = commandSuit;
        }
        turns.add(turn);
    }
        
    /**
     * Returns an instance of a {@code Situe}.
     * The returned {@code Situe} happens on the provided 
     * {@linkplain joker.board.Board} argument where the game started 
     * with the provided {@link joker.util.JoCard JoCard} argument.
     * @param board The {@code Board} on which this {@code Situe} occurs
     * @return a {@code Situe}
     */
    public static Situe getSitue(Board board) {
        Situe returnSitue;
        JoCard played = board.prev();
        if (played.ofRank(7) || played.ofRank(14)) {
            if (board.addCool()) {
                returnSitue = new PickAddCoolSitue(board);
                return returnSitue;
            } else {
                returnSitue = new PickSitue(board);
                return returnSitue;
            }
        }
        if (played.ofRank(1)) {
            returnSitue = new SkipSitue(board);
            return returnSitue;
        }
        if (played.ofRank(11)) {
            int demandSuit;
            try {
                demandSuit = board.demandedSuit();
            } catch (NoDemandedSuitException exc) {
                demandSuit = 0;
            }
            returnSitue = new CommandSitue(board, demandSuit);
            return returnSitue;
        } else {
            returnSitue = new NormalSitue(board);
        }
        return returnSitue;
    }
    
    
    /**
     * Duplicates and returns a copy of the given {@code Situe} argument
     * @param situe The Situe to be duplicated
     * @return an exact copy of the given Situe
     */
    public static Situe getSitue(Situe situe) {
        Situe copySitue = situe;
        Board board = situe.board;
        switch (situe.type()) {
            case SKIP: 
                copySitue = new SkipSitue(board);
                break;
            case COMMAND:
                copySitue = new CommandSitue(board, situe.commandSuit);
                break;
            case PICK: 
                copySitue = new PickSitue(board);
                break; 
            case PICKADDCOOL: 
                copySitue = new PickAddCoolSitue(board, 
                    ((PickAddCoolSitue) situe).toPickCards);
                break;
            case NORMAL: 
                copySitue = new NormalSitue(board);
        }
        return copySitue;
    }
}
