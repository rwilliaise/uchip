package com.alotofletters.uchip.content.board.memory.ram;

public enum RamType {
    RAM_32K(8, 15);

    public final int dataWidth;
    public final int addressWidth;

    RamType(int dataWidth, int addressWidth) {
        this.dataWidth = dataWidth;
        this.addressWidth = addressWidth;
    }
}
