/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class BoatDispenseItemBehavior
extends DefaultDispenseItemBehavior {
    private final DefaultDispenseItemBehavior f_123368_ = new DefaultDispenseItemBehavior();
    private final Boat.Type f_123369_;
    private final boolean f_235889_;

    public BoatDispenseItemBehavior(Boat.Type p_123371_) {
        this(p_123371_, false);
    }

    public BoatDispenseItemBehavior(Boat.Type p_235891_, boolean p_235892_) {
        this.f_123369_ = p_235891_;
        this.f_235889_ = p_235892_;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public ItemStack m_7498_(BlockSource p_123375_, ItemStack p_123376_) {
        void $$10;
        Direction $$2 = p_123375_.m_6414_().m_61143_(DispenserBlock.f_52659_);
        ServerLevel $$3 = p_123375_.m_7727_();
        double $$4 = p_123375_.m_7096_() + (double)((float)$$2.m_122429_() * 1.125f);
        double $$5 = p_123375_.m_7098_() + (double)((float)$$2.m_122430_() * 1.125f);
        double $$6 = p_123375_.m_7094_() + (double)((float)$$2.m_122431_() * 1.125f);
        BlockPos $$7 = p_123375_.m_7961_().m_121945_($$2);
        if ($$3.m_6425_($$7).m_205070_(FluidTags.f_13131_)) {
            double $$8 = 1.0;
        } else if ($$3.m_8055_($$7).m_60795_() && $$3.m_6425_($$7.m_7495_()).m_205070_(FluidTags.f_13131_)) {
            double $$9 = 0.0;
        } else {
            return this.f_123368_.m_6115_(p_123375_, p_123376_);
        }
        Boat $$11 = this.f_235889_ ? new ChestBoat($$3, $$4, $$5 + $$10, $$6) : new Boat($$3, $$4, $$5 + $$10, $$6);
        $$11.m_38332_(this.f_123369_);
        $$11.m_146922_($$2.m_122435_());
        $$3.m_7967_($$11);
        p_123376_.m_41774_(1);
        return p_123376_;
    }

    @Override
    protected void m_6823_(BlockSource p_123373_) {
        p_123373_.m_7727_().m_46796_(1000, p_123373_.m_7961_(), 0);
    }
}

