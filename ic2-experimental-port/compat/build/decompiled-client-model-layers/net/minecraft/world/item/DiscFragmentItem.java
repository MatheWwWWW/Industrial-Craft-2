/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class DiscFragmentItem
extends Item {
    public DiscFragmentItem(Item.Properties p_220029_) {
        super(p_220029_);
    }

    @Override
    public void m_7373_(ItemStack p_220031_, @Nullable Level p_220032_, List<Component> p_220033_, TooltipFlag p_220034_) {
        p_220033_.add(this.m_220035_().m_130940_(ChatFormatting.GRAY));
    }

    public MutableComponent m_220035_() {
        return Component.m_237115_(this.m_5524_() + ".desc");
    }
}

