/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArrowItem
extends Item {
    public ArrowItem(Item.Properties p_40512_) {
        super(p_40512_);
    }

    public AbstractArrow m_6394_(Level p_40513_, ItemStack p_40514_, LivingEntity p_40515_) {
        Arrow $$3 = new Arrow(p_40513_, p_40515_);
        $$3.m_36878_(p_40514_);
        return $$3;
    }
}

