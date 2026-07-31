/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 */
package ic2.core.item;

import ic2.core.init.Localization;
import ic2.core.profile.NotClassic;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import ic2.core.uu.UuIndex;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

@NotClassic
public class ItemCrystalMemory
extends Item {
    public static final String TOOLTIP_ITEM = "item.ic2.crystal_memory.tooltip.item";
    public static final String TOOLTIP_UU_MATTER = "item.ic2.crystal_memory.tooltip.uu_matter";
    public static final String TOOLTIP_ENERGY = "item.ic2.crystal_memory.tooltip.energy";
    public static final String TOOLTIP_EMPTY = "item.ic2.crystal_memory.tooltip.empty";

    public ItemCrystalMemory(Item.Properties properties) {
        super(properties);
    }

    public void m_7373_(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        ItemStack itemStack2 = this.readItemStack(itemStack);
        if (!StackUtil.isEmpty(itemStack2)) {
            list.add((Component)Component.m_237113_((String)(Localization.translate(TOOLTIP_ITEM) + " " + itemStack2.m_41786_())).m_130940_(ChatFormatting.GRAY));
            list.add((Component)Component.m_237113_((String)(Localization.translate(TOOLTIP_UU_MATTER) + " " + Util.toSiString(UuIndex.instance.getInBuckets(itemStack2), 4) + "B")).m_130940_(ChatFormatting.GRAY));
        } else {
            list.add((Component)Component.m_237115_((String)TOOLTIP_EMPTY).m_130940_(ChatFormatting.GRAY));
        }
    }

    public ItemStack readItemStack(ItemStack itemStack) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        CompoundTag compoundTag2 = compoundTag.m_128469_("Pattern");
        ItemStack itemStack2 = ItemStack.m_41712_((CompoundTag)compoundTag2);
        return itemStack2;
    }

    public void writecontentsTag(ItemStack itemStack, ItemStack itemStack2) {
        CompoundTag compoundTag = StackUtil.getOrCreateNbtData(itemStack);
        CompoundTag compoundTag2 = new CompoundTag();
        itemStack2.m_41739_(compoundTag2);
        compoundTag.m_128365_("Pattern", (Tag)compoundTag2);
    }
}

