/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class DropperBlock
extends DispenserBlock {
    private static final DispenseItemBehavior f_52939_ = new DefaultDispenseItemBehavior();

    public DropperBlock(BlockBehaviour.Properties p_52942_) {
        super(p_52942_);
    }

    @Override
    protected DispenseItemBehavior m_7216_(ItemStack p_52947_) {
        return f_52939_;
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153179_, BlockState p_153180_) {
        return new DropperBlockEntity(p_153179_, p_153180_);
    }

    @Override
    protected void m_5824_(ServerLevel p_52944_, BlockPos p_52945_) {
        ItemStack $$9;
        BlockSourceImpl $$2 = new BlockSourceImpl(p_52944_, p_52945_);
        DispenserBlockEntity $$3 = (DispenserBlockEntity)$$2.m_8118_();
        int $$4 = $$3.m_222761_(p_52944_.f_46441_);
        if ($$4 < 0) {
            p_52944_.m_46796_(1001, p_52945_, 0);
            return;
        }
        ItemStack $$5 = $$3.m_8020_($$4);
        if ($$5.m_41619_()) {
            return;
        }
        Direction $$6 = p_52944_.m_8055_(p_52945_).m_61143_(f_52659_);
        Container $$7 = HopperBlockEntity.m_59390_(p_52944_, p_52945_.m_121945_($$6));
        if ($$7 == null) {
            ItemStack $$8 = f_52939_.m_6115_($$2, $$5);
        } else {
            $$9 = HopperBlockEntity.m_59326_($$3, $$7, $$5.m_41777_().m_41620_(1), $$6.m_122424_());
            if ($$9.m_41619_()) {
                $$9 = $$5.m_41777_();
                $$9.m_41774_(1);
            } else {
                $$9 = $$5.m_41777_();
            }
        }
        $$3.m_6836_($$4, $$9);
    }
}

