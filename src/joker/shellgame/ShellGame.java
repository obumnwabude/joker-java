/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.shellgame;

import java.util.ArrayDeque;
import joker.board.*;
import joker.playing.*;
import java.util.ArrayList;
import joker.util.JoCard;
/**
 * Start the game.
 * @author OBUMUNEME NWABUDE
 */
public class ShellGame { 
    
    /**
     * The default handSize
     */
    public int handSize = 5;
    
    /**
     * The board on this game.
     */
    public Board board;
    
    /**
     * a system player used in playing the shell game
     */
    public SystemPlayer one;
    
    /** 
     * a system player used in playing the shell game
     */
    public SystemPlayer two;
    
    /**
     * a user player playing with the command line
     */
    public ShellUserPlayer user;
   
    /**
     * the list of players
     */
    public ArrayList<Plays> players;
    
    /**
     * Used in making the logs of the current state of the game
     */
    public LogMaker logMaker; 
    
    /**
     * produced by the {@link #logMaker logMaker}
     */
    ArrayDeque<String> logs;
    
    /**
     * displays the current state of the game
     */
    public void display() {
        logs = logMaker.log();
        for (String log: logs)  System.out.println(log);
        for (Plays pl: players) {
            if (pl.hand().empty()) {
                System.out.println(pl.name() + ": game");
                break;
            }
            if (pl.hand().size() == 1) {
                System.out.println(pl.name() + ": check");
            }
        }
    }
    
    /**
     * Constructor for the game
     */
    public ShellGame () {
        players = new ArrayList<>();
        one = new SystemPlayer("First", PlayerLevel.INTERMEDIATE);
        two = new SystemPlayer("Second", PlayerLevel.INTERMEDIATE);
        user = new ShellUserPlayer("Obum");
        players.add(user);
        players.add(one);
        players.add(two);
        board = new JoBoard(true, false, false);
        board.deal(players, handSize);
        logMaker = new LogMaker(board.turns());
    }

    
    /**
     * Checks if any of the {@linkplain joker.util.Hand hands} of the 
     * {@linkplain joker.playing.Plays players} of this {@code ShellGame} no longer
     * has {@linkplain JoCard JoCards} in them, returns {@code true} if that is
     * the case.
     * @return {@code true} if any player's hand is empty
     */
    public boolean empty() {
        for (Plays pl: players) if (pl.hand().empty()) {
            display();
            return true;
        }
        return false;
    }
    
    /**
     * Changes to the next player
     * @param current the current player
     * @return the next player
     */
    public Plays nextPlayer(Plays current) {
        int nextInd;
        int prevInd = players.indexOf(current);
        if (prevInd < players.size() - 1) nextInd = ++prevInd;
        else nextInd = 0;
        return players.get(nextInd);
    }

    /**
     * displays the {@link joker.util.JoCard JoCards} contained in the argument's
     * {@code hand}.
     * @param player the player who has the hand
     */
    public void showCards(Plays player) {
        if (player.hand().empty()) return;
        System.out.println("Cards of " + player.name() + " are: ");
        player.hand().sort();
        int no = 0;
        for (JoCard jc : player.hand()) {
            ++no;
            System.out.print("\t" + no + ". ");
            System.out.println(jc);
        }
    }
    
    /**
     * plays the game
     */
    public void playGame() {
        Plays player = players.get(0);
        while(!empty()) {
            if (player instanceof ShellUserPlayer) display();
            while(!board.enter(player)) {
                System.out.println(board.getCurrentSitue().type().warn());
            }
            player = nextPlayer(player);
        }
    }
    
    /**
     * Execution of this program begins here.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ShellGame sg = new ShellGame();
        sg.playGame();
    }

}
