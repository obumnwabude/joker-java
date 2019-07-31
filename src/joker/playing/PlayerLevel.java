/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.playing;

/**
 * Describes the expertise of a player either as BEGINNER, INTERMEDIATE OR PRO
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public enum PlayerLevel {
    /**
     * Basic player level
     */
    BEGINNER(1), 
    
    /**
     * Genius player level
     */
    INTERMEDIATE(2),
    
    /**
     * Pro player level
     */
    PRO(3);
    
    /**
     * describes a player level of being either 0 , 1 or 2
     */
    private final int level;
    
    /**
     * Constructor for a player level type
     */
   PlayerLevel(int l) {
        level = l;
    }

    /**
     * Returns the level as an integer attached to this player level.
     * 1 is returned for the BEGINNER player level, 2 for INTERMEDIATE and 3 for PRO
     * @return the level described by the player level
     */
    public int level() {
        return level;
    }

}
