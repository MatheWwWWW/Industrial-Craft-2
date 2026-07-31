/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package ic2.core.item.tool;

import ic2.api.item.IBoxable;
import ic2.api.item.IItemHudInfo;
import ic2.core.init.Localization;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ItemToolCrafting
extends Item
implements IBoxable,
IItemHudInfo {
    public static final String TOOLTIP_USES_LEFT = "ic2.tooltip.tool.uses_left";

    public ItemToolCrafting(Item.Properties properties) {
        super(properties);
    }

    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add((Component)Component.m_237110_((String)TOOLTIP_USES_LEFT, (Object[])new Object[]{ItemToolCrafting.getRemainingUses(itemStack)}));
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemStack) {
        return true;
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack, boolean bl) {
        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add(Localization.translate(TOOLTIP_USES_LEFT, ItemToolCrafting.getRemainingUses(itemStack)).formatted(ChatFormatting.GRAY));
        return linkedList;
    }

    protected static int getRemainingUses(ItemStack itemStack) {
        return itemStack.m_41776_() - itemStack.m_41773_();
    }
}

