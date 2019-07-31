/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board.situe;

/**
 * {@code NoPickException} occurs when there is an attempt to pick a 
 * Card but there is no picking available
 * @author OBUMUNEME NWABUDE
 */
public class NoPickException extends Exception {

    /**
     * Creates a new instance of <code>NoPickException</code> without detail
     * message.
     */
    public NoPickException() {
    }

    /**
     * Constructs an instance of <code>NoPickException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoPickException(String msg) {
        super(msg);
    }
}
