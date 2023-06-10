package com.alotofletters.uchip.content.processor.emulator;

import com.alotofletters.uchip.foundation.board.Board;
import com.alotofletters.uchip.foundation.board.Component;

public class ProcessorEmulator<T extends Board<?, ?>> extends Component<T> {
    public ProcessorEmulator(T owner) {
        super(owner);
    }
}
