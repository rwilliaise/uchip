package com.alotofletters.uchip;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;

public class MicrochipLangPartials {

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static final String PROCESSOR_BOARD_EFFECT =
            addLang("item.uchip.processor.effect_board", "When in Board:");

    public static final String PROCESSOR_BOARD_DATA_WIDTH =
            addLang("item.uchip.processor.data_width", "Data Width: %s bits");

    public static final String PROCESSOR_BOARD_ADDRESS_WIDTH =
            addLang("item.uchip.processor.address_width", "Address Width: %s bits");

    // processor instruction sets/architectures
    public static final String PROCESSOR_ARCH_6502 =
            addArch("6502", "Arch: MOS 6502");

    // board tiers
    public static final String BOARD_8BIT =
            addBoard("8bit", "Tier I (8 bit)");

    private static String addLang(String key, String value) {
        REGISTRATE.addRawLang(key, value);
        return key;
    }

    private static String addArch(String id, String value) {
        String key = "arch.uchip." + id;
        addLang(key, value);
        return key;
    }

    private static String addBoard(String id, String value) {
        String key = "board.uchip." + id;
        addLang(key, value);
        return key;
    }

    private static String addDescLang(ItemEntry<?> entry, int i, String value) {
        String key = entry.get().getDescriptionId() + ".desc" + i;
        REGISTRATE.addRawLang(key, value);
        return key;
    }

    public static void register () { }
}
