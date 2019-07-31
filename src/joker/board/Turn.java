/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board;

import joker.playing.Plays;
import joker.util.JoCard;

/**
 * Logical construct representing a turn taken on the {@linkplain Board}
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class Turn {
        
    // name of the player taking turn
        public String name;
        
        // the player that played 
        public Plays player = null;
        
        // the board that played
        public Board board = null;

       // flag for played Card
        public boolean played = false;

       // flag for drawn Card
        public boolean drawn = false;

       // flag for picked Cards
        public boolean picked = false;

       // flag for added Cards
        public boolean added = false;

        // flag for cooled Cards
        public boolean cooled = false;

        // flag for skipped 
        public boolean skipped = false;

        // flag for having commanding a suit
        public boolean commanded = false;
        
        // the suit as int for the commanded suit
        public int commandedSuit = 0;
        
        // flag to indicate that a particular suit is still in need
        public boolean inCommand = false;
        
        // flag to indicate an expected pick
        public boolean toPick = false;
        
        // flag to indicate an expected skip
        public boolean toSkip = false;
        
        // flag to indicate a command has been played
        public boolean playedCommand = false;
        
        // flag to indicate picks have been added
        public boolean pickAdded = false;
        
        
        // flag to indicate that any card can be played in the next round
        // commandedsuit when equal zero also indicate same when the 
        // value of commanded is true
        public boolean jfromAddCool = false;
        
        // number of cards to pick
        public int picks = 0;
        
        // number of picked cards
        public int pickeds = 0;
        
        // when picks are added the number of added picks
        public int picksAdded = 0;
        
        // the played jocard 
        public JoCard playedJoCard;
        
        public Turn(Plays player, JoCard card) {
            this.player = player;
            name = player.name();
            played = true;
            playedJoCard = card;
        }

        public Turn(Plays player) {
            this.player = player;
           name = player.name();
           playedJoCard = null;
        }
        
        public Turn (Board board) {
            this.board = board;
            name = "Board";
            played = true;
            playedJoCard = board.prev();
        }

}
