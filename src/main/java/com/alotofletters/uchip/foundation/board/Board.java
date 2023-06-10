package com.alotofletters.uchip.foundation.board;

/**
 * Represents a board BoardObjects can interface with.
 *
 * @see Component
 * @param <D> Data bus type.
 * @param <A> Address space type.
 */
public abstract class Board<D extends Number, A extends Number> {

    protected D dataBus;
    protected A address;


}
