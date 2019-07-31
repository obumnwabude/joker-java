/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;

/**
 * Makes a log of how {@linkplain Turn turns} have been taken on the 
 * {@linkplain Board} where <em>Joker</em> card game is played.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class LogMaker extends Observable implements Observer {
    
    /**
     * The {@linkplain TurnStack} from which logs are made
     */
    private final TurnStack turns;
    
    /**
     * The log itself
     */
    private final ArrayDeque<String> logs;
    
    /**
     * Constructs a new {@code LogMaker} from the provided argument
     * @param turns The {@code TurnStack} to make logs from
     */
    public LogMaker(TurnStack turns) {
        this.turns = turns;
        logs = new ArrayDeque<>();
        turns.addObserver(this);
        fillLogs();
    }
    
    /**
     * Returns an {@linkplain java.util.ArrayDeque} of Strings representing
     * the logs of the Turns played
     * @return The log
     */
    public ArrayDeque<String> log() {
        return logs;
    }
    
    /**
     * Returns the {@linkplain TurnStack} from which logs are made in this
     * {@code LogMaker}
     * @return The TurnStack
     */
    public TurnStack turns() {
        return turns;
    }

    @Override
    public void update(Observable o, Object args) {
        int change = (Integer) args;
        if (change == 2) { // a removal
            logs.removeLast();
            setChanged();
            notifyObservers(change);
        }
        if (change == 1) { // an addition
            logs.addLast(makeString(turns.element()));
            setChanged();
            notifyObservers(change);
        }
    }
    
    // fills log for the first time
    private void fillLogs() {
        for (Turn t : turns) {
            logs.addLast(makeString(t));
        }
    }
    
    /**
     * makes the String added to logs
     * @param moment The turn to be used to make the required String
     * @return The made string
     */
    private String makeString(Turn moment) {
        String str = "";
        str += moment.name;
        str += " ";
        if (moment.skipped) {
            str += "has been skipped.";
            return str;
        }
        if (moment.picked) {
            str += "picked ";
            str += moment.pickeds;
            str += ".";
            return str;
        }
        if (moment.played) {
            str += "played ";
            str += moment.playedJoCard;
            str += ".";
            if (moment.board != null || !moment.player.hand().empty()) {
                if (moment.toSkip) {
                    str += " Skip you.";
                }
                if (moment.pickAdded) {
                    str += " ";
                    str += moment.name;
                    str += " added pick ";
                    str += moment.picksAdded;
                    str += ".";
                }
                if (moment.toPick) {
                    str += " Pick ";
                    str += moment.picks;
                    str += ".";
                }
                if (moment.cooled) {
                    str += " ";
                    str += moment.name;
                    str += " cooled pick ";
                    str += moment.picks;
                    str += ".";
                    if (moment.jfromAddCool) {
                        str += " You can play any card.";
                    }
                }
                if (moment.commanded) {
                    if (moment.commandedSuit != 0) {
                        str += " ";
                        str += moment.name;
                        str += " commanded ";
                        String[] SUITS = {null, "Cassava", "Square", "Red",
                            "Groundnuts", "Black", "Red"};
                        str += SUITS[moment.commandedSuit];
                    } else {
                        str += " You can play any card.";
                    }
                }
                if (moment.playedCommand) {
                    if (!(moment.jfromAddCool || moment.commandedSuit == 0)) {
                        str += " ";
                        str += moment.name;
                        str += " played needed ";
                        String[] SUITS = {null, "Cassava", "Square", "Red",
                            "Groundnuts", "Black", "Red"};
                        str += SUITS[moment.commandedSuit];
                        str += ".";
                    }
                }
            }
            return str;
        }
        if (moment.drawn) {
            str += "go board.";
            if (moment.inCommand) {
                str += " Needed ";
                String[] SUITS = {"any card", "Cassava", "Square", "Red", 
                    "Groundnuts", "Black", "Red"};
                str += SUITS[moment.commandedSuit];
                str += ".";
            }
        }
        return str;
    }
}
