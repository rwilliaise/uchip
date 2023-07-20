package com.alotofletters.uchip.content.board.memory;

/**
 * Different variants of memory.
 */
public enum MemoryType {
    RAM_32K(true, 15),
    ROM_32K(false, 15),
    ;

    public final boolean writable;
    /** log2 of memory size, i.e. amount of pins for the address */
    public final int addressWidth;

    MemoryType(boolean writable, int addressWidth) {
        this.writable = writable;
        this.addressWidth = addressWidth;
    }
}
