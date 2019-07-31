/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joker.board;

import java.util.ArrayDeque;
import joker.board.situe.Situe;
import joker.board.situe.SitueType;
import joker.playing.Plays;
import joker.util.Deck;
import joker.util.JoCard;
import java.util.ArrayList;
import joker.board.situe.NoPickException;
import joker.util.Hand;
import joker.util.JoCardCollection;

/**
 * A {@code JoBoard} is a {@linkplain Board} used in playing the <em>Joker</em>
 * card game. 
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class JoBoard implements Board{
    
    /*
     * The board pile / discard pile
     */
    public final JoCardCollection discardPile;
    
    /*
     * The market pile / draw pile
     */
    public final JoCardCollection drawPile;
    
    /*
     * The deck of Cards to be used on this board.
     */
    private final Deck deck;
    
    /**
     * The Situe to be used for each turn taken
     */
    private Situe currentSitue;
    
    /**
     * The player that played last
     */
    private Plays currentPlayer = null;
    
    /**
     * This flag indicates if adding and cooling is permitted on this JoBoard
     */
    private final boolean addCool;
    
    /**
     * Flag for if j command can be played anytime on this board even if 
     * its colour does not match what's on the board
     */
    private boolean alwaysAllowJCommand = true;
    
    /**
     * Flag for if any card can be played on the board after j command was
     * used for cooling
     */
    private boolean canAnyAfterJCool = true;
    
    /**
     * Flag for if any card can be played on the board after j command was 
     * played by the board
     */
    private boolean canAnyAfterBoardJ = true;
    
    /**
     * holds the value of the demanded suit
     */
    private int demandSuit = -1;
    
    /**
     * This flag indicates that #played method ran successfully.
     */
    private boolean playedSuccessfully = true;
    
    /**
     * List of {@linkplain Turn turns} taken on this {@code JoBoard}.
     */
    private final TurnStack turns;
    
    /**
     * The command Manager used by this board
     */
    private final CommandManager cm;
    
    /**
     * used to multiply the decks and specify if joker is not to be used
     */
    private Deck decking(boolean addDeck, boolean noJoker) {
        if (noJoker) {
            Deck noJokerDeck = new Deck("deck3", 0);
            if (addDeck) {
                Deck noJokerDeck2 = new Deck("deck4", 0);
                noJokerDeck.addAll(noJokerDeck2);
                noJokerDeck.shuffle();
                return noJokerDeck;
            }
            noJokerDeck.shuffle();
            return noJokerDeck;
        }
        Deck deck1 = new Deck("Deck");
        if (addDeck) {
            Deck deck2 = new Deck("deck2");
            deck1.addAll(deck2);
            deck1.shuffle();
            return deck1;
        }
        deck1.shuffle();
        return deck1;
    }
    
    /**
     * Constructs a new <code>JoBoard</code>
     * @param addcool if adding and cooling is permitted on this {@code 
     * JoBoard}.
     * @param useTwoDecks if two decks should be used at the start of the 
     * <em>Joker</em> playing game.
     * @param noJoker if {@link joker.util.JoCard Joker cards} are not to
     * be used on this {@code Board}.
     */
    public JoBoard(boolean addcool, boolean useTwoDecks, boolean noJoker) {
        deck = decking(useTwoDecks, noJoker);
        discardPile = new JoCardCollection("Discard Pile");
        drawPile = new JoCardCollection("Draw Pile");
        this.addCool = addcool;
        turns = new TurnStack();    
        cm = new CommandManager();
    }


    /**
     * Returns a {@code String} representation of this {@code JoBoard}
     * and its values.
     * @return A {@code String} representation of this {@code JoBoard}.
     */
    @Override
    public String toString() {
        try {
            return "Board: " + prev();
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }
    
    @Override
    public void deal (ArrayList<Plays> players, int handSize) {
        cm.executeCommand(new BoardCommand(this, players, handSize));
    }
    
    @Override
    public JoCard prev() {
        return discardPile.last();
    }
    
    // shows the current player used by shell game
    public Plays currentPlayer() {
        return currentPlayer;
    }
    
    @Override 
    public boolean enter(Plays player) {
        if (checkSkipSitue(player)) {
            return true;
        }
        if (!addCool && currentSitue.type() == SitueType.PICK) { 
            checkPickSitue(player);
            return true;
        }
        player.play(this);
        return playedSuccessfully;
    }
    
    /**
     * Checks if the {@linkplain #currentSitue currentSitue} is of
     * type {@linkplain joker.board.situe.SitueType.SKIP} and acts 
     * if that is the case
     * @param player The player to be skipped
     * @return {@code true} if this player has been skipped, {@code 
     * false} if the {@code currentSitue} is not SKIP type.
     */
    private boolean checkSkipSitue(Plays player) {
        if (currentSitue.type() == SitueType.SKIP) {
            cm.executeCommand(new CheckSkipCommand(player));
            return true;
        }
        return false;
    }
    
    /**
     * if the draw pile has been emptied, save the last card
     * from the discard pile and transfer the discard pile to the 
     * draw pile and shuffle.
     */
    private void reshuffle() {
        JoCard last = discardPile.last();
        discardPile.dealAll(drawPile);
        discardPile.add(last);
        drawPile.shuffle();
    }
    
    /**
     * Carries out picking on the provided player
     * @param player the player that picks cards
     */
    private void checkPickSitue(Plays player) {
        cm.executeCommand(new CheckPickCommand(player));
    }
    
    
    @Override
    public JoCard draw(Plays player) {
        if (drawPile.empty()) reshuffle();
        if (currentSitue.type() == SitueType.PICK || 
                currentSitue.type() == SitueType.PICKADDCOOL) {
            checkPickSitue(player);
            playedSuccessfully = true;
            return null;
        }
        DrawCommand dc = new DrawCommand(player);
        cm.executeCommand(dc);
        playedSuccessfully = true;
        return dc.drawn();
    }
    
    @Override
    public boolean played (Plays player, JoCard played) {
        if (played != null) {
            if (currentSitue.demand(played)) {
                cm.executeCommand(new PlayedCommand(player, played));
                playedSuccessfully = true;
                return true;
            } else {
                playedSuccessfully = false;
                return false;
            }
        }
        playedSuccessfully = false;
        return false;
    }
    
    @Override
    public Situe getCurrentSitue() {
        return currentSitue;
    } 
    
    @Override 
    public TurnStack turns() {
        return turns;
    }
    
    
    @Override 
    public boolean addCool() {
        return addCool;
    }
    
    @Override 
    public int demandedSuit() throws NoDemandedSuitException {
        if (demandSuit == -1) throw new NoDemandedSuitException
                ("No suit is being demanded");
        return demandSuit;
    }

    @Override
    public boolean canUndo() {
        return cm.isUndoAvailable();
    }

    @Override
    public void undo() {
        cm.undo();
    }
    
    @Override 
    public boolean canRedo() {
        return cm.isRedoAvailable();
    }
    
    @Override 
    public void redo() {
        cm.redo();
    }

    @Override
    public boolean allowAlwaysJ() {
        return alwaysAllowJCommand;
    }

    @Override
    public void setAllowAlwaysJ(boolean value) {
        alwaysAllowJCommand = value;
    }

    @Override
    public boolean anyAfterJCool() {
        return canAnyAfterJCool;
    }

    @Override
    public void setAnyAfterJCool(boolean value) {
        canAnyAfterJCool = value;
    }

    @Override
    public boolean anyAfterBoardJ() {
        return canAnyAfterBoardJ;
    }

    @Override
    public void setAnyAfterBoardJ(boolean value) {
        canAnyAfterBoardJ = value;
    }
    
    /**
     * The {@linkplain Command} of the first turn by this {@code Board}
     */
    private class BoardCommand implements Command {
        
        private final ArrayList<Plays> players;
        private final int handSize;
        private final Board board;
        
        private BoardCommand(Board board, ArrayList<Plays> players,
                int handsize) {
            this.board = board;
            this.players = players;
            handSize = handsize;
        }
        
        @Override 
        public void execute() {
            deck.deal(players, handSize);
            deck.deal(discardPile, 1);
            deck.dealAll(drawPile);
            if (discardPile.last().ofRank(11)) {
                if (canAnyAfterBoardJ) demandSuit = 0;
                else demandSuit = discardPile.last().suit;
            }
            currentSitue = Situe.getSitue(board);
            currentSitue.handle(board);
        }
        
        @Override 
        public void undo() {
            drawPile.dealAll(deck);
            discardPile.dealAll(deck);
            players.forEach(pl -> pl.hand().dealAll(deck));
            deck.shuffle();
            turns.remove();
            cm.redo();
        }
    }
    
    /**
     * The {@linkplain Command} of {@linkplain 
     * #checkPickSitue(joker.playing.Plays) checkSkipSitue}
     */
    private class CheckSkipCommand implements Command {
        
        private final Situe previousSitue;
        private Situe nextSitue;
        private final Plays previousPlayer;
        private final Plays player;
        
        private CheckSkipCommand(Plays player) {
            previousSitue = Situe.getSitue(currentSitue);
            previousPlayer = currentPlayer;
            this.player = player;
        }
        
        @Override 
        public void execute() {
            nextSitue = currentSitue.handle(player);
            currentPlayer = player;
            currentSitue = nextSitue;
        }
        
        @Override 
        public void undo() {
            currentSitue = previousSitue;
            currentPlayer = previousPlayer;
            turns.remove();
        }
    }
    
    /**
     * The {@linkplain Command} of {@linkplain 
     * #checkPickSitue(joker.playing.Plays) checkPickSitue}.
     */ 
    private class CheckPickCommand implements Command {
        
        private final Situe previousSitue;
        private Situe nextSitue;
        private final Plays previousPlayer;
        private final Plays player;
        private ArrayDeque<JoCard> pickedCards;
        
        private CheckPickCommand(Plays player) {
            previousSitue = Situe.getSitue(currentSitue);
            previousPlayer = currentPlayer;
            this.player = player;
            pickedCards = new ArrayDeque<>();
        }
        
        @Override 
        public void execute() {
            while (currentSitue.toPick() > 0) {
                if (drawPile.empty()) reshuffle();
                drawPile.shuffle();
                try {
                    currentSitue.pick();
                    JoCard pick = drawPile.pop();
                    player.picked(pick);
                    pickedCards.push(pick);
                } catch (NoPickException noPickExc) { }
            } 
            nextSitue = currentSitue.handle(player);
            currentPlayer = player;
            currentSitue = nextSitue;
        }
        
        @Override 
        public void undo() {
            Hand ph = player.hand();
            pickedCards.stream()
                    .filter((picked) -> (ph.has(picked)))
                    .map(ph::indexOf)
                    .map(ph::get)
                    .forEachOrdered(drawPile::add);
            /* legacy approach
            for (JoCard picked: pickedCards) {
                if (ph.has(picked)) {
                    int use = ph.indexOf(picked);
                    JoCard unpick = ph.get(use);
                    dap.add(unpick);
                }
            }
            */
            pickedCards = new ArrayDeque<>();
            currentSitue = previousSitue;
            currentPlayer = previousPlayer;
            turns.remove();
        }
    }
    
    /**
     * The {@linkplain Command} of {@linkplain
     * #draw(joker.playing.Plays) draw}
     */
    private class DrawCommand implements Command {
        
        private final Situe previousSitue;
        private Situe nextSitue;
        private final Plays previousPlayer;
        private final Plays player;
        private JoCard popped;
        
        private DrawCommand(Plays player) {
            previousSitue = Situe.getSitue(currentSitue);
            previousPlayer = currentPlayer;
            this.player = player;
        }
        
        @Override 
        public void execute() {
            if (drawPile.empty()) reshuffle();
            drawPile.shuffle();
            popped = drawPile.pop();
            nextSitue = currentSitue.handle(player);
            currentPlayer = player;
            currentSitue = nextSitue;
        }
        
        @Override 
        public void undo() {
            Hand ph = player.hand();
            if (ph.has(popped)) {
                int use = ph.indexOf(popped);
                drawPile.add(ph.pop(use));
            }
            currentSitue = previousSitue;
            currentPlayer = previousPlayer;
            turns.remove();
        }
        
        // returns the drawn card
        private JoCard drawn() {
            return popped;
        }
    }
    
    /**
     * The {@linkplain Command} of {@linkplain 
     * played(joker.playing.Plays, joker.util.JoCard) played}
     */
    private class PlayedCommand implements Command {
        
        private final Situe previousSitue;
        private Situe nextSitue;
        private final Plays previousPlayer;
        private final Plays player;
        private final JoCard played;
        private int hasBeenUndone;
        
        private PlayedCommand(Plays player, JoCard played) {
            previousSitue = Situe.getSitue(currentSitue);
            previousPlayer = currentPlayer;
            this.player = player;
            this.played = played;
            hasBeenUndone = 0;
        }
        
        @Override 
        public void execute() {
            if (hasBeenUndone > 0) {
                Hand ph = player.hand();
                if (ph.has(played)) {
                    int use = ph.indexOf(played);
                    ph.pop(use);
                }
            }
            discardPile.add(played);
            if (!player.hand().empty()) {
                if (discardPile.last().ofRank(11)) {
                    if (!(currentSitue.type() == SitueType.PICK ||
                            currentSitue.type() == SitueType.PICKADDCOOL)) {
                        synchronized (this) {
                            demandSuit = player.whatCommand();
                        }
                    }
                }
            }
            nextSitue = currentSitue.handle(player, prev());
            if (previousSitue.type() == SitueType.COMMAND && nextSitue.type() 
                    != SitueType.COMMAND) {
                demandSuit = -1;
            }
            currentPlayer = player;
            currentSitue = nextSitue;
            hasBeenUndone++;
        }
        
        @Override 
        public void undo() {
            player.draw(discardPile.pop());
            currentSitue = previousSitue;
            currentPlayer = previousPlayer;
            turns.remove();
        }
    }
    
}
