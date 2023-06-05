package com.alotofletters.uchip.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProcessorItem extends Item {
    public ProcessorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> tooltip, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, tooltip, p_41424_);

        String id = p_41421_.getDescriptionId();
        tooltip.add(CommonComponents.EMPTY);
        tooltip.add(Component.translatable(id + ".desc1").withStyle(ChatFormatting.GRAY));
        tooltip.add(CommonComponents.m_264333_().append(Component.translatable(id + ".desc2").withStyle(ChatFormatting.GOLD)));
    }
}
