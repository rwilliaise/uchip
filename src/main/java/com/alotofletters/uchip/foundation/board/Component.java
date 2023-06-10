package com.alotofletters.uchip.foundation.board;

/**
 * Represents an item that interfaces with buses on the board.
 *
 * @see Component
 * @param <T> Board type.
 */
public abstract class Component<T extends Board<?, ?>> {
    protected final T owner;

    public Component(T owner) {
        this.owner = owner;
    }

    public T getOwner() {
        return owner;
    }
}
