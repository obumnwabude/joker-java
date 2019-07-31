/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

import java.util.ArrayDeque;

/**
 * The {@code CommandManager} holds the {@linkplain Command}s taken on the 
 * {@linkplain Board}.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class CommandManager {
    
    /**
     * A stack holding undone commands
     */
    private final ArrayDeque<Command> undos;
    
    /**
     * A stack holding redone commands
     */
    private final ArrayDeque<Command> redos;
    
    /**
     * Constructs a new {@code CommandManager}
     */
    public CommandManager() {
        undos = new ArrayDeque<>();
        redos = new ArrayDeque<>();
    }
    
    /**
     * Carries out the execution of a command
     * @param c the command to be executed
     */
    public void executeCommand(Command c) {
        c.execute();
        undos.addLast(c);
        redos.clear();
    }
    
    /**
     * Checks if undoing is possible
     * @return {@code true} if undo is available, {@code false} if otherwise.
     */
    public boolean isUndoAvailable() { 
        return !undos.isEmpty();
    }
    
    /**
     * Undoes the previous command
     */
    public void undo() {
        if (!undos.isEmpty()) {
            Command toUndo = undos.removeLast();
            redos.addLast(toUndo);
            toUndo.undo();
        } 
    }
    
    /**
     * Checks if redoing is possible
     * @return {@code true} if redo is available, {@code false} if otherwise.
     */
    public boolean isRedoAvailable() {
        return !redos.isEmpty();
    }
    
    /**
     * Re-does the previously undone move
     */
    public void redo() {
        if (!redos.isEmpty()) {
            Command toRedo = redos.removeLast();
            undos.addLast(toRedo);
            toRedo.execute();
        }
    }
}
