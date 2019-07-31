/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joker.board;

/**
 * {@code NoDemandedSuitException} occurs when there is an attempt to request
 * for the {@code suit} demanded by a {@linkplain joker.util.Plays player} or
 * the {@linkplain Board} and no {@code suit} was demanded.
 * @author OBUMUNEME NWABUDE
 * @since 1.0
 */
public class NoDemandedSuitException extends Exception {

    /**
     * Creates a new instance of <code>NoDemandedSuitException</code> without
     * detail message.
     */
    public NoDemandedSuitException() {
    }

    /**
     * Constructs an instance of <code>NoDemandedSuitException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoDemandedSuitException(String msg) {
        super(msg);
    }
}
