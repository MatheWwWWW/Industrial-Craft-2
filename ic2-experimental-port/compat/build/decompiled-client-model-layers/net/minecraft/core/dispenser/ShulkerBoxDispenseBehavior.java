/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  org.slf4j.Logger
 */
package net.minecraft.core.dispenser;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.slf4j.Logger;

public class ShulkerBoxDispenseBehavior
extends OptionalDispenseItemBehavior {
    private static final Logger f_175749_ = LogUtils.getLogger();

    @Override
    protected ItemStack m_7498_(BlockSource p_123587_, ItemStack p_123588_) {
        this.m_123573_(false);
        Item $$2 = p_123588_.m_41720_();
        if ($$2 instanceof BlockItem) {
            Direction $$3 = p_123587_.m_6414_().m_61143_(DispenserBlock.f_52659_);
            BlockPos $$4 = p_123587_.m_7961_().m_121945_($$3);
            Direction $$5 = p_123587_.m_7727_().m_46859_($$4.m_7495_()) ? $$3 : Direction.UP;
            try {
                this.m_123573_(((BlockItem)$$2).m_40576_(new DirectionalPlaceContext((Level)p_123587_.m_7727_(), $$4, $$3, p_123588_, $$5)).m_19077_());
            }
            catch (Exception $$6) {
                f_175749_.error("Error trying to place shulker box at {}", (Object)$$4, (Object)$$6);
            }
        }
        return p_123588_;
    }
}

