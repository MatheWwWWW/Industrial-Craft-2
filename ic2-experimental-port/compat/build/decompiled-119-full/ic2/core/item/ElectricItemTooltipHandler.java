/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item;

import ic2.api.item.ElectricItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class ElectricItemTooltipHandler {
    public static void addTooltip(ItemStack itemStack, List<Component> list) {
        String string;
        if (itemStack != null && ElectricItem.manager.getMaxCharge(itemStack) > 0.0 && (string = ElectricItem.manager.getToolTip(itemStack)) != null && !string.trim().isEmpty()) {
            list.add((Component)Component.m_237113_((String)string));
            if (Screen.m_96638_()) {
                list.add((Component)Component.m_237110_((String)"ic2.item.tooltip.PowerTier", (Object[])new Object[]{ElectricItem.manager.getTier(itemStack)}).m_130940_(ChatFormatting.GRAY));
            }
        }
    }
}

