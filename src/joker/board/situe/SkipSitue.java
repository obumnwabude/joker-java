/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board.situe;

import joker.board.Turn;
import joker.playing.Plays;
import joker.board.Board;

class SkipSitue extends Situe {
    
    SkipSitue(Board board) {
        super(board);
        situeType = SitueType.SKIP;
    }
    
     @Override
    public Situe handle(Plays player) {
        handled = true;
        setTurn(player);
        Situe returnSitue = new NormalSitue(board);
        return returnSitue;
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     */ 
    protected void setTurn(Plays player) {
        turn = new Turn(player);
        turn.skipped = true;
        turns.add(turn);
    }
    
}
