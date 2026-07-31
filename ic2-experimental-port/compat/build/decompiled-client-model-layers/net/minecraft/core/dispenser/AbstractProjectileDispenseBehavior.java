/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public abstract class AbstractProjectileDispenseBehavior
extends DefaultDispenseItemBehavior {
    @Override
    public ItemStack m_7498_(BlockSource p_123366_, ItemStack p_123367_) {
        ServerLevel $$2 = p_123366_.m_7727_();
        Position $$3 = DispenserBlock.m_52720_(p_123366_);
        Direction $$4 = p_123366_.m_6414_().m_61143_(DispenserBlock.f_52659_);
        Projectile $$5 = this.m_6895_($$2, $$3, p_123367_);
        $$5.m_6686_($$4.m_122429_(), (float)$$4.m_122430_() + 0.1f, $$4.m_122431_(), this.m_7104_(), this.m_7101_());
        $$2.m_7967_($$5);
        p_123367_.m_41774_(1);
        return p_123367_;
    }

    @Override
    protected void m_6823_(BlockSource p_123364_) {
        p_123364_.m_7727_().m_46796_(1002, p_123364_.m_7961_(), 0);
    }

    protected abstract Projectile m_6895_(Level var1, Position var2, ItemStack var3);

    protected float m_7101_() {
        return 6.0f;
    }

    protected float m_7104_() {
        return 1.1f;
    }
}

