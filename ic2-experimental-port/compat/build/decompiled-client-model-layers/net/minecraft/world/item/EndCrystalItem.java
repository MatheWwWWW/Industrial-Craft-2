/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class EndCrystalItem
extends Item {
    public EndCrystalItem(Item.Properties p_41174_) {
        super(p_41174_);
    }

    @Override
    public InteractionResult m_6225_(UseOnContext p_41176_) {
        double $$7;
        double $$6;
        BlockPos $$2;
        Level $$1 = p_41176_.m_43725_();
        BlockState $$3 = $$1.m_8055_($$2 = p_41176_.m_8083_());
        if (!$$3.m_60713_(Blocks.f_50080_) && !$$3.m_60713_(Blocks.f_50752_)) {
            return InteractionResult.FAIL;
        }
        BlockPos $$4 = $$2.m_7494_();
        if (!$$1.m_46859_($$4)) {
            return InteractionResult.FAIL;
        }
        double $$5 = $$4.m_123341_();
        List<Entity> $$8 = $$1.m_45933_(null, new AABB($$5, $$6 = (double)$$4.m_123342_(), $$7 = (double)$$4.m_123343_(), $$5 + 1.0, $$6 + 2.0, $$7 + 1.0));
        if (!$$8.isEmpty()) {
            return InteractionResult.FAIL;
        }
        if ($$1 instanceof ServerLevel) {
            EndCrystal $$9 = new EndCrystal($$1, $$5 + 0.5, $$6, $$7 + 0.5);
            $$9.m_31056_(false);
            $$1.m_7967_($$9);
            $$1.m_142346_(p_41176_.m_43723_(), GameEvent.f_157810_, $$4);
            EndDragonFight $$10 = ((ServerLevel)$$1).m_8586_();
            if ($$10 != null) {
                $$10.m_64100_();
            }
        }
        p_41176_.m_43722_().m_41774_(1);
        return InteractionResult.m_19078_($$1.f_46443_);
    }

    @Override
    public boolean m_5812_(ItemStack p_41178_) {
        return true;
    }
}

