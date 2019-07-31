/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board.situe;

import joker.board.Turn;
import joker.playing.Plays;
import joker.board.Board;

class PickSitue extends Situe {
    
    private int initPick;
    
    PickSitue(Board board) {
        super(board);
        situeType = SitueType.PICK;
        if (board.prev().ofRank(7)) toPick = 2;
        if (board.prev().ofRank(14)) toPick = 4;
        initPick = toPick;
    }  

    @Override
    public Situe handle(Plays player) {
        if (toPick() == 0)  handled = true;
        setTurn(player);
        return new NormalSitue(board);
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     */ 
    protected void setTurn(Plays player) {
        turn = new Turn(player);
        turn.picked = true;
        turn.pickeds = initPick;
        initPick = 0;
        turns.add(turn);
    }
    
}