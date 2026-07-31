/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core.dispenser;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DefaultDispenseItemBehavior
implements DispenseItemBehavior {
    @Override
    public final ItemStack m_6115_(BlockSource p_123391_, ItemStack p_123392_) {
        ItemStack $$2 = this.m_7498_(p_123391_, p_123392_);
        this.m_6823_(p_123391_);
        this.m_123387_(p_123391_, p_123391_.m_6414_().m_61143_(DispenserBlock.f_52659_));
        return $$2;
    }

    protected ItemStack m_7498_(BlockSource p_123385_, ItemStack p_123386_) {
        Direction $$2 = p_123385_.m_6414_().m_61143_(DispenserBlock.f_52659_);
        Position $$3 = DispenserBlock.m_52720_(p_123385_);
        ItemStack $$4 = p_123386_.m_41620_(1);
        DefaultDispenseItemBehavior.m_123378_(p_123385_.m_7727_(), $$4, 6, $$2, $$3);
        return p_123386_;
    }

    public static void m_123378_(Level p_123379_, ItemStack p_123380_, int p_123381_, Direction p_123382_, Position p_123383_) {
        double $$5 = p_123383_.m_7096_();
        double $$6 = p_123383_.m_7098_();
        double $$7 = p_123383_.m_7094_();
        $$6 = p_123382_.m_122434_() == Direction.Axis.Y ? ($$6 -= 0.125) : ($$6 -= 0.15625);
        ItemEntity $$8 = new ItemEntity(p_123379_, $$5, $$6, $$7, p_123380_);
        double $$9 = p_123379_.f_46441_.m_188500_() * 0.1 + 0.2;
        $$8.m_20334_(p_123379_.f_46441_.m_216328_((double)p_123382_.m_122429_() * $$9, 0.0172275 * (double)p_123381_), p_123379_.f_46441_.m_216328_(0.2, 0.0172275 * (double)p_123381_), p_123379_.f_46441_.m_216328_((double)p_123382_.m_122431_() * $$9, 0.0172275 * (double)p_123381_));
        p_123379_.m_7967_($$8);
    }

    protected void m_6823_(BlockSource p_123384_) {
        p_123384_.m_7727_().m_46796_(1000, p_123384_.m_7961_(), 0);
    }

    protected void m_123387_(BlockSource p_123388_, Direction p_123389_) {
        p_123388_.m_7727_().m_46796_(2000, p_123388_.m_7961_(), p_123389_.m_122411_());
    }
}

