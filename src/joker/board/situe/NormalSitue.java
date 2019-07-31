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

class NormalSitue extends Situe {
    
    // used by the handle method
    // used to add pick and skip options to turns
    private Situe returnSitue;
    
    NormalSitue(Board board) {
        super(board);
        situeType = SitueType.NORMAL;
    }
    
    @Override
    public boolean demand(JoCard played) {
        if (fromPickAddCool) {
            if (board.anyAfterJCool()) return true;
        }
        return board.prev().match(played) ? true : 
                (board.allowAlwaysJ() ? played.ofRank(11) : false);
    }

    @Override
    public Situe handle(Plays player, JoCard played) { 
        switch (played.rank) {
            case 7: 
                if (board.addCool())
                    returnSitue = new PickAddCoolSitue(board); 
                else 
                    returnSitue = new PickSitue(board);
                break;
            case 14: 
                if (board.addCool())
                    returnSitue = new PickAddCoolSitue(board); 
                else 
                    returnSitue = new PickSitue(board);
                break;
            case 1:
                returnSitue = new SkipSitue(board); 
                break;
            case 11: 
                int demandSuit;
                try {
                    demandSuit = board.demandedSuit();
                } catch (NoDemandedSuitException exc) {
                    if (player.hand().empty()) {
                        returnSitue = this;
                        break;
                    }
                    demandSuit = 0;
                }
                returnSitue = new CommandSitue(board, demandSuit);
                break;
            default: returnSitue = this; 
        }
        setTurn(player, played);
        return returnSitue;
    }
    
    @Override
    public Situe handle (Plays player) {
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
        if (returnSitue.type() == SitueType.SKIP) turn.toSkip = true;
        if (returnSitue.type() == SitueType.PICK || returnSitue.type() 
                == SitueType.PICKADDCOOL) {
            turn.toPick = true;
            turn.picks = returnSitue.toPick();
        }
        if (returnSitue.type() == SitueType.COMMAND) {
            turn.commanded = true;
            turn.commandedSuit = returnSitue.commandSuit;
        }
        turns.add(turn);
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     */ 
    protected void setTurn(Plays player) {
        turn = new Turn(player);
        turn.drawn = true;
        turns.add(turn);
    }
}
