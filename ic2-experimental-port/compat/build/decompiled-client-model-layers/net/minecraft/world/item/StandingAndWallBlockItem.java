/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class StandingAndWallBlockItem
extends BlockItem {
    protected final Block f_43246_;

    public StandingAndWallBlockItem(Block p_43248_, Block p_43249_, Item.Properties p_43250_) {
        super(p_43248_, p_43250_);
        this.f_43246_ = p_43249_;
    }

    @Override
    @Nullable
    protected BlockState m_5965_(BlockPlaceContext p_43255_) {
        BlockState $$1 = this.f_43246_.m_5573_(p_43255_);
        BlockState $$2 = null;
        Level $$3 = p_43255_.m_43725_();
        BlockPos $$4 = p_43255_.m_8083_();
        for (Direction $$5 : p_43255_.m_6232_()) {
            BlockState $$6;
            if ($$5 == Direction.UP) continue;
            BlockState blockState = $$6 = $$5 == Direction.DOWN ? this.m_40614_().m_5573_(p_43255_) : $$1;
            if ($$6 == null || !$$6.m_60710_($$3, $$4)) continue;
            $$2 = $$6;
            break;
        }
        return $$2 != null && $$3.m_45752_($$2, $$4, CollisionContext.m_82749_()) ? $$2 : null;
    }

    @Override
    public void m_6192_(Map<Block, Item> p_43252_, Item p_43253_) {
        super.m_6192_(p_43252_, p_43253_);
        p_43252_.put(this.f_43246_, p_43253_);
    }
}

