/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AirItem
extends Item {
    private final Block f_40366_;

    public AirItem(Block p_40368_, Item.Properties p_40369_) {
        super(p_40369_);
        this.f_40366_ = p_40368_;
    }

    @Override
    public String m_5524_() {
        return this.f_40366_.m_7705_();
    }

    @Override
    public void m_7373_(ItemStack p_40372_, @Nullable Level p_40373_, List<Component> p_40374_, TooltipFlag p_40375_) {
        super.m_7373_(p_40372_, p_40373_, p_40374_, p_40375_);
        this.f_40366_.m_5871_(p_40372_, p_40373_, p_40374_, p_40375_);
    }
}

