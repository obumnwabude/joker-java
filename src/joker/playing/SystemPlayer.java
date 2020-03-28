/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.playing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Random;
import joker.board.Board;
import joker.board.NoDemandedSuitException;
import joker.board.situe.Situe;
import joker.util.Hand;
import joker.util.JoCard;
import org.checkerframework.checker.optional.qual.MaybePresent;

/**
 * The {@code SystemPlayer} class defines a {@linkplain Plays player} 
 * of the <em>Joker</em> card game that is being controlled entirely by 
 * the System / Computer.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class SystemPlayer implements Plays {
    
    /**
     * The name of this {@code SystemPlayer} 
     */
    protected final String myName;
    
    /**
     * The hand of this {@code SystemPlayer}
     */
    protected final Hand myHand;
    
    /**
     * The {@linkplain PlayerLevel} of this {@code SystemPlayer}
     */
    protected final PlayerLevel myLevel;
    
    /**
     * The situe used across different methods and obtained from board at play
     */
    private Situe situe;
    
    /**
     * Used for implementing player logic
     * contains possible pick 2 pick 4 skip and j command cards contained in 
     * #myHand
     */
    private ArrayList<JoCard> firstSearch;
    
    /**
     * Used for implementing player logic
     * contains other normal cards after pick 2 pick 4 skip and j command have 
     * been removed into #firstSearch
     */
    private ArrayList<JoCard> secondSearch;
    
    /**
     * Used for implementing player logic
     * Contains cards required from a command
     */
    private ArrayList<JoCard> commandSearch;
    
    /**
     * Used for implementing player logic
     * Contains cards in myHand that match a suit and that don't contain 
     * adding or cooling cards
     */
    private ArrayList<JoCard> assistINTER1;
    
    /**
     * Used for implementing player logic
     * Contains cards in myHand that match a suit and that contains 
     * adding or cooling cards
     */
    private ArrayList<JoCard> assistINTER2;
    
    /**
     * Memorizes a suit to be demanded when requested
     */
    private int thoughtDemand = -1;
    
    /**
     * Used to signal that a demand suit has been placed
     */
    private boolean demanded = false;
    
    
    /**
     * Constructs a new {@code SystemPlayer}
     * @param name The name of this SystemPlayer
     * @param level The PlayerLevel of this SystemPlayer
     */
    public SystemPlayer (String name, PlayerLevel level) {
        myName = name;
        myHand = new Hand(name+"hand");
        myLevel = level;
    }

    @Override
    public String name() {
        return myName;
    }

    @Override
    public void play(Board board) {
        situe = board.getCurrentSitue();
        switch (situe.type()) {
            case COMMAND: 
                playCommand(board);
                break;
            case PICK: 
                draw(board.draw(this));
                break;
            case PICKADDCOOL: 
                playPickAddCool(board);
                break;
            case NORMAL:
                playNormal(board);
                break;
        }
    }

    @Override
    public Hand hand() {
        return myHand;
    }

    @Override
    public int whatCommand() {
        if (thoughtDemand != -1) {
            int ret = thoughtDemand;
            thoughtDemand = -1;
            return ret;
        } 
        if (myHand.size() == 1) {
            if (myHand.get(0).ofSuit(5)) return 1;
            if (myHand.get(0).ofSuit(6)) return 3;
            return myHand.get(0).suit;
        }
        int[] counted = counterMyHand();
        int ind;
        @MaybePresent OptionalInt opt = Arrays.stream(counted).distinct().max();
        if (opt.isPresent()) {
            Integer[] counted1 = new Integer[5];
            for (int j = 0; j < counted1.length; j++) {
                counted1[j] = counted[j];
            }
            ind = Arrays.asList(counted1).lastIndexOf(opt.getAsInt());
        } else ind = new Random(5).nextInt();
        return ind;
    }
    
    /**
     * Returns a histogram of the cards in my hand according to their suits
     * @return an array of suits
     */
        private int[]  counterMyHand() {
        int[] hist = new int[5];
        for (int i = 0; i < hist.length; i++) hist[i] = 0;
        for (JoCard jc : myHand) {
            if (jc.ofSuit(1)) hist[1]++;
            if (jc.ofSuit(2)) hist[2]++;
            if (jc.ofSuit(3)) hist[3]++;
            if (jc.ofSuit(4)) hist[4]++;
            // adds jocards to the 1st and second suits
            if (jc.ofSuit(5)) hist[1]++;
            if (jc.ofSuit(6)) hist[2]++;
        }
        return hist;
    }
    
    /**
     * play when board current situe is COMMAND
     * @param board the board to play on
     */
    private void playCommand(Board board) {
        int demand = obtainDemand(board);
        if (demand == 0) {
            playNormal(board);
            return;
        }
        fillCommandSearch(demand);
        if (!commandSearch.isEmpty()) {
            if (commandSearch.size() == 1) {
                if (commandSearch.get(0).ofRank(11)) {
                    if (myHand.size() > 2) {
                        draw(board.draw(this));
                        return;
                    } else { 
         board.played(this, myHand.pop(myHand.indexOf(commandSearch.get(0))));
         return;
                    }
                }
                if (board.addCool()) {
                    JoCard jc = commandSearch.get(0);
                    if (jc.ofRank(1) || jc.ofRank(7) || jc.ofRank(14)) {
                        if (myHand.size() > 2) {
                            draw(board.draw(this));
                            return;
                        } 
                    }
                }
            }
        board.played(this, myHand.pop(myHand.indexOf(commandSearch.get(0))));
        } else {
            draw(board.draw(this));
        }
    }
    
    
    /**
     * play when board current situe is PICKADDCOOL
     * @param board the board to play on
     */
    private void playPickAddCool(Board board) {
        fillFirstSearch();
        if (firstSearch.isEmpty()) {
            draw(board.draw(this));
            return;
        }
        if (firstSearch.size() == 1) {
            int i = myHand.indexOf(firstSearch.get(0));
            board.played(this, myHand.pop(i));
        } else {
            int i = myHand.indexOf(firstSearch.get(firstSearch.size() - 1));
            board.played(this, myHand.pop(i));
        }
    }
    
    /**
     * play when board current situe is NORMAL
     * @param board the board to play on
     */
    private void playNormal(Board board) {
        fillSecondSearch();
        if (!secondSearch.isEmpty()) {
            INTERMEDIATE: if (myLevel.level() == 2) {
                if (board.allowAlwaysJ()) {
                    fillFirstSearch();
                    if (myHand.hasCardRank(11) || demanded) {
                        if (board.addCool()) {
                        if (firstSearch.size() > 1 || myHand.size() <= 3) {
                        
                int[] suitsSecond = counterSecondSearch();
                int ind;
                @MaybePresent OptionalInt opt = Arrays.stream(suitsSecond).distinct().max();
                if (opt.isPresent()) {
                    Integer[] inter = new Integer[5];
                    for (int j = 0; j < inter.length; j++) {
                        inter[j] = suitsSecond[j];
                    }
                    ind = Arrays.asList(inter).lastIndexOf(opt.getAsInt());
                    if (board.prev().matchSuit(ind)) {
                        fillAssistInter(ind, 1);
                        if (!assistINTER1.isEmpty()) {
                            if (assistINTER1.size() == 1) {
    board.played(this, myHand.pop(myHand.indexOf(assistINTER1.get(0))));
    return;
                            }
                            assistINTER1.sort(JoCard.RANK_ORDER);
    board.played(this, myHand.pop(myHand.indexOf(assistINTER1.
            get(assistINTER1.size() - 1))));
    return;
                        } 
                    } else {
                         if (myHand.hasCardRank(11)) {
                             thoughtDemand = ind;
                             demanded = true;
                             for (JoCard jc: myHand) {
                                 if (jc.ofRank(11))
                        board.played(this, myHand.pop(myHand.indexOf(jc)));
                                 return;
                             }
                         }
                        break INTERMEDIATE;                        
                    }
                }  else break INTERMEDIATE;    
                    } 
                        }
                    }
                    
                } else {
                    break INTERMEDIATE;
                }
            }
            if (secondSearch.size() == 1) {
                int i = myHand.indexOf(secondSearch.get(0));
                board.played(this, myHand.pop(i));
                return;
            } else {
                for (int k = secondSearch.size() - 1; k >= 0; k--) {
                    if (secondSearch.get(k).match(board.prev())) {
                        int i = myHand.indexOf(secondSearch.get(k));
                        board.played(this, myHand.pop(i));
                        return;
                    }
                }
            }
        } else {
            fillFirstSearch();
            if (!firstSearch.isEmpty()) {
                if (firstSearch.size() == 1) {
                    int i = myHand.indexOf(firstSearch.get(0));
                    board.played(this, myHand.pop(i));
                    return;
                } else {
                    for (int k = firstSearch.size() - 1; k >= 0; k--) {
                        if (firstSearch.get(k).match(board.prev())) {
                            int i = myHand.indexOf(firstSearch.get(k));
                            board.played(this, myHand.pop(i));
                            return;
                        }
                    }
                }
            }
        }
        draw(board.draw(this));
    }
    
    /**
     * Returns a histogram of the cards in second search according to their suits
     * @return an array of suits
     */
        private int[]  counterSecondSearch() {
        fillSecondSearch();
        int[] hist = new int[5];
        for (int i = 0; i < hist.length; i++) hist[i] = 0;
        for (JoCard jc : secondSearch) {
            if (jc.ofSuit(1)) hist[1]++;
            if (jc.ofSuit(2)) hist[2]++;
            if (jc.ofSuit(3)) hist[3]++;
            if (jc.ofSuit(4)) hist[4]++;
        }
        return hist;
    }
    
    /**
     * searches my hand and checks for adding or cooling cards
     */
    private void fillFirstSearch() {
        firstSearch = new ArrayList<>();
        for (JoCard jc : myHand) {
            if (jc.ofRank(1) || jc.ofRank(11) || jc.ofRank(7) || jc.ofRank(11)) {
                firstSearch.add(jc);
            }
        }        
        firstSearch.sort(JoCard.COOL_ADD_ORDER);
    }
    
    /**
     * searches #myHand and adds non adding and non cooling cards to 
     * #secondSearch
     */
    private void fillSecondSearch() {
        secondSearch = new ArrayList<>();
        for (JoCard jc : myHand) {
            if (!(jc.ofRank(1) || jc.ofRank(11) || jc.ofRank(7) || 
                    jc.ofRank(11))) {
                secondSearch.add(jc);
            }
        }
        secondSearch.sort(JoCard.RANK_ORDER);
    }
    
    /**
     * searches #myHand and adds all jocards matching the provided command
     * argument to #commandSearch
     * @param suit the command argument
     */
    private void fillCommandSearch(int suit) {
        commandSearch = new ArrayList<>();
        for (JoCard jc: myHand) {
            if (jc.matchSuit(suit)) commandSearch.add(jc);
        }
        commandSearch.sort(JoCard.RANK_ORDER);
    }
    
    /**
     * Searches #myHand for cards matching a provided suit and fills 
     * #assistINTER1 with exceptions of adding and cooling cards or otherwise
     * if the choice argument is 0 or 1
     * @param suit the suit to check for matches in #myHand
     * @param choice 0 if to fill assistINTER with adding and cooling , 1 if
     * otherwise
     */
    private void fillAssistInter(int suit, int choice) {
        if (choice == 0) assistINTER2 = new ArrayList<>();
        if (choice == 1) assistINTER1 = new ArrayList<>();
        for (JoCard jc : myHand) {
            if (jc.matchSuit(suit)) {
                if (choice == 0) {
                    assistINTER2.add(jc);
                } else if (choice == 1) {
                    if (!(jc.ofRank(1) || jc.ofRank(11) || jc.ofRank(7)
                            || jc.ofRank(11))) {
                        assistINTER1.add(jc);
                    }
                }
            }
        }
    }
    
    /**
     * checks the board argument for the currently demanded suit (recursive 
     * over that) and returns that suit
     * @param board the board to obtain the demanded suit from
     * @return the demanded suit from the board
     */
    private int obtainDemand(Board board) {
        int ret;
        try {
            ret = board.demandedSuit();
        } catch (NoDemandedSuitException nds) {
            ret = 0;
        }
        return ret;
    }

}
