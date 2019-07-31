/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board.situe;

import joker.board.Board;
import joker.board.Turn;
import joker.util.JoCard;
import joker.playing.Plays;

class CommandSitue extends Situe {
    
    private boolean setCommand = false;
    private boolean playedCommand = false;
    Situe returnSitue;
    
    CommandSitue(Board board, int suit) {
        super(board);
        situeType = SitueType.COMMAND;
        commandSuit = suit;
    }

    @Override
    public boolean demand(JoCard played) {
        if (commandSuit == 0) return true;
        return played.matchSuit(commandSuit) ? true : board.prev()
                .matchRank(played);
    }

    @Override
    public Situe handle(Plays player, JoCard played) { 
        if (played.ofRank(11)) {
            handled  = true;
            setCommand = true;
        } else {
            handled = true;
            playedCommand = true;
        }
        returnSitue = getSitue(board);
        setTurn(player, played);
        return returnSitue;
    }
    
    @Override 
    public Situe handle(Plays player) {
        setTurn(player);
        return this;
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     * @param played the card played by the player argument
     */ 
    protected void setTurn(Plays player, JoCard played) {
        turn = new Turn(player, played);
        turn.commanded = setCommand;
        if (returnSitue.type() == SitueType.COMMAND) {
            turn.commandedSuit = returnSitue.commandSuit;
        } else {
            turn.commandedSuit = commandSuit;
        }
        turn.playedCommand = playedCommand;
        turns.add(turn);
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     */
    protected void setTurn(Plays player) {
        turn = new Turn(player);
        turn.commandedSuit = commandSuit;
        turn.drawn = true;
        turn.inCommand = true;
        turns.add(turn);
    }
}