package com.alotofletters.uchip.content.board.memory;

public enum MemoryType {
    RAM_32K(true, 15),
    ROM_32K(false, 15),
    ;

    public final boolean writable;
    /** log2 of memory size */
    public final int addressWidth;

    MemoryType(boolean writable, int addressWidth) {
        this.writable = writable;
        this.addressWidth = addressWidth;
    }
}
