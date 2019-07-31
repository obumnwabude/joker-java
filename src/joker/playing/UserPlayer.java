/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.playing;

import joker.board.Board;
import joker.util.Hand;

/**
 * An adapter class for external user player
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class UserPlayer implements Plays {
    
    /**
     * The name of this {@code UserPlayer}
     */
    protected final String myName;
    
    /**
     * The hand of this {@code UserPlayer}
     */
    protected final Hand myHand;
    
    /**
     * Constructs a UserPlayer with the provided name argument
     * @param name the name of this userplayer
     */
    public UserPlayer(String name) {
        myName = name;
        myHand = new Hand("myHand");
    }
    
    @Override
    public String name() {
        return myName;
    }

    @Override
    public void play(Board board) {
        
    }

    @Override
    public Hand hand() {
        return myHand;
    }    
    
    @Override 
    public int whatCommand() {
        return 0;
    }
}
