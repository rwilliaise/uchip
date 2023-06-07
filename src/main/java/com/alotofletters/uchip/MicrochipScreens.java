package com.alotofletters.uchip;

import com.alotofletters.uchip.content.machine.chip_builder.ChipBuilderMenu;
import com.alotofletters.uchip.content.machine.chip_builder.ChipBuilderScreen;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.MenuEntry;

public class MicrochipScreens {

    private static final Registrate REGISTRATE = Microchip.REGISTRATE.get();

    public static final MenuEntry<ChipBuilderMenu> CHIP_BUILDER = REGISTRATE.menu("chip_builder", ChipBuilderMenu::new, () -> ChipBuilderScreen::new)
            .register();

    public static void register() { }
}
