/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board.situe;

/**
 * {@code SitueTypes} specify what kind of Situe a {@linkplain Situe} is.
 * Currently, it could either be normal, pick, skip or command
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public enum SitueType {
    
    /**
     * The {@code NORMAL SitueType} stands for {@linkplain Situe Situes} in 
     * which the next {@linkplain joker.util.JoCard JoCard} to be played should
     * match the previously played one according to {@linkplain 
     * joker.util.JoCard#match(joker.util.JoCard) JoCard.match(JoCard)} else
     * drawing should take place.
     */
    NORMAL(1, "Please play a card that match the suit or rank on the board."),
    
    /**
     * The {@code PICK SitueType} stands for {@linkplain Situe Situes} in which
     * the next turn will involve a pick.
     */
    PICK(2, "Please pick cards as required."),
    
    /**
     * The {@code SKIP SitueType} stands for {@linkplain Situe Situes} in which
     * the next turn will have the {@linkplain joker.playing.Plays player} to 
     * take it skipped.
     */
    SKIP(3),
    
    /**
     * The {@code COMMAND SitueType} stands for {@linkplain Situe Situes} in which
     * the next turn will have the {@linkplain joker.playing.Plays player} to 
     * take it play any {@linkplain joker.util.JoCard JoCard} that matches the 
     * demanded {@linkplain joker.util.JoCard#suit suit} according to 
     * {@linkplain joker.util.JoCard#matchSuit(joker.util.JoCard) 
     * JoCard.matchSuit(JoCard)}. Drawing takes place if 
     * such {@code JoCard} is not possessed and the next {@code JoCard} that must
     * be played must match the demanded {@code suit}.
     */
    COMMAND(4, "Please play a card of the demanded suit or go board."),
    
    /**
     * The {@code PICKADDCOOL SitueType} stands for {@linkplain Situe Situes} 
     * in which the next turn will involve a pick, adding or cooling.
     * @see #PICK PICK
     */
    PICKADDCOOL(5, "Please pick or play a adding or cooling card");
    
    // an integer representing the type of situe
    private final int ty;
    
    // a string warning a wrong play
    private final String warning;
    
    // assist warning string
    private final String assist  = "BOARD WARNING: ";
    
    SitueType (int t) {
       ty = t;
       warning = " ";
   }
    
    SitueType(int t, String wn) {
        ty = t;
        warning = assist + wn;
    }
    
   
    /**
     * Returns the type of a {@linkplain Situe} as an integer. 1 for NORMAL,
     * 2 for PICK, 3 for SKIP and 4 for COMMAND.
     * @return the type integer of this {@code SitueType}
     */
    public int type() {
        return ty;
    }
    
    /**
     * Returns the warning attached to a misdeed for this {@code SitueType}
     * @return the warning attached to this {@code SitueType}
     */
    public String warn() {
        return warning;
    }
}
