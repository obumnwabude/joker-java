/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board.situe;

import java.util.ArrayDeque;
import java.util.Iterator;
import joker.board.Turn;
import joker.util.JoCard;
import joker.playing.Plays;
import joker.board.Board;

class PickAddCoolSitue extends Situe {
    
    private boolean added = false;
    private boolean cooled = false;
    private int picksAdded = 0;
    private int initPick;
    ArrayDeque<JoCard> toPickCards = new ArrayDeque<>();
    
    
    PickAddCoolSitue(Board board) {
        super(board);
        situeType = SitueType.PICKADDCOOL;
        if (board.prev().ofRank(7)) toPick = 2;
        if (board.prev().ofRank(14)) toPick = 4;
        initPick = toPick;
        toPickCards.add(board.prev());
    }
    
    PickAddCoolSitue(Board board, ArrayDeque<JoCard> toPicks) {
        super(board);
        situeType = SitueType.PICKADDCOOL;
        toPickCards = toPicks;
        JoCard use;
        if (toPickCards.size() == 1) {
            use = toPickCards.peekLast();
            if (use != null) {
                if (use.ofRank(7)) toPick = 2;
                if (use.ofRank(14)) toPick = 4;
            }
        } else {
            Iterator<JoCard> it = toPickCards.descendingIterator();
            use = it.next();
            if (use.ofRank(7)) {
                toPick = 2;
                picksAdded = 2;
                added = true;
            }
            if (use.ofRank(14)) {
                toPick = 4;
                picksAdded = 4;
                added = true;
            }
            it.forEachRemaining((JoCard jc) -> {
                if (jc.ofRank(7)) toPick += 2;
                if (jc.ofRank(14)) toPick += 4;
            });
        }
        initPick = toPick;
    }

    @Override 
    public boolean demand (JoCard played) {
        return played.ofRank(7) || played.ofRank(14) || played.ofRank(11) || 
                played.ofRank(1);
    }
    
    @Override 
    public Situe handle (Plays player, JoCard played) {
        if (played.ofRank(7) || played.ofRank(14)) {
            added = true;
            if (played.rank == 7) {
                toPick += 2;
                picksAdded = 2;
            }
            if (played.rank == 14) {
                toPick += 4;
                picksAdded = 4;
            }
            initPick = toPick;
            toPickCards.add(played);
        } 
        if (played.ofRank(1) || played.ofRank(11)) {
            added = false;
            cooled = true;
            toPick = 0;
            handled = true;
            if (played.ofRank(11)) {
                fromPickAddCool = true;
            }
        }
        setTurn(player, played);
        if (handled) {
            Situe returnSitue;
            if (board.anyAfterJCool()) {
                returnSitue = new CommandSitue(board, 0);
            } else {
                returnSitue = new NormalSitue(board);
            }
            returnSitue.fromPickAddCool = fromPickAddCool;
            return returnSitue;
        }
        return this;
    }
    
    @Override 
    public Situe handle (Plays player) {
        if (toPick() == 0) handled = true;
        setTurn (player);
        return new NormalSitue(board);
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     * @param played the card played by the player argument
     */
    public void setTurn (Plays player, JoCard played) {
        turn = new Turn(player, played);
        if (added) {
            turn.toPick = true;
            turn.picks = initPick;
            turn.pickAdded = true;
            turn.picksAdded = picksAdded;
        } 
        if (cooled) {
            turn.cooled = true;
            turn.picks = initPick;
            cooled = false;
            initPick = 0;
            picksAdded = 0;
            if (fromPickAddCool && board.anyAfterJCool()) 
                if (!player.hand().empty()) 
                    turn.jfromAddCool = true;
        }
        turns.add(turn);
    }
    
    /**
     * Sets {@linkplain #turn turn} and adds it to {@linkplain #turns turns}
     * @param player the player who took turn
     */
    public void setTurn(Plays player) {
        turn = new Turn(player);
        turn.picked = true;
        turn.pickeds = initPick;
        turns.add(turn);
        initPick = 0;
        picksAdded = 0;
    }
}
