/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 */
package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowerPotBlock
extends Block {
    private static final Map<Block, Block> f_53524_ = Maps.newHashMap();
    public static final float f_153266_ = 3.0f;
    protected static final VoxelShape f_53523_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final Block f_53525_;

    public FlowerPotBlock(Block p_53528_, BlockBehaviour.Properties p_53529_) {
        super(p_53529_);
        this.f_53525_ = p_53528_;
        f_53524_.put(p_53528_, this);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53556_, BlockGetter p_53557_, BlockPos p_53558_, CollisionContext p_53559_) {
        return f_53523_;
    }

    @Override
    public RenderShape m_7514_(BlockState p_53554_) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult m_6227_(BlockState p_53540_, Level p_53541_, BlockPos p_53542_, Player p_53543_, InteractionHand p_53544_, BlockHitResult p_53545_) {
        boolean $$10;
        ItemStack $$6 = p_53543_.m_21120_(p_53544_);
        Item $$7 = $$6.m_41720_();
        BlockState $$8 = ($$7 instanceof BlockItem ? f_53524_.getOrDefault(((BlockItem)$$7).m_40614_(), Blocks.f_50016_) : Blocks.f_50016_).m_49966_();
        boolean $$9 = $$8.m_60713_(Blocks.f_50016_);
        if ($$9 != ($$10 = this.m_153267_())) {
            if ($$10) {
                p_53541_.m_7731_(p_53542_, $$8, 3);
                p_53543_.m_36220_(Stats.f_12961_);
                if (!p_53543_.m_150110_().f_35937_) {
                    $$6.m_41774_(1);
                }
            } else {
                ItemStack $$11 = new ItemStack(this.f_53525_);
                if ($$6.m_41619_()) {
                    p_53543_.m_21008_(p_53544_, $$11);
                } else if (!p_53543_.m_36356_($$11)) {
                    p_53543_.m_36176_($$11, false);
                }
                p_53541_.m_7731_(p_53542_, Blocks.f_50276_.m_49966_(), 3);
            }
            p_53541_.m_142346_(p_53543_, GameEvent.f_157792_, p_53542_);
            return InteractionResult.m_19078_(p_53541_.f_46443_);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_53531_, BlockPos p_53532_, BlockState p_53533_) {
        if (this.m_153267_()) {
            return super.m_7397_(p_53531_, p_53532_, p_53533_);
        }
        return new ItemStack(this.f_53525_);
    }

    private boolean m_153267_() {
        return this.f_53525_ == Blocks.f_50016_;
    }

    @Override
    public BlockState m_7417_(BlockState p_53547_, Direction p_53548_, BlockState p_53549_, LevelAccessor p_53550_, BlockPos p_53551_, BlockPos p_53552_) {
        if (p_53548_ == Direction.DOWN && !p_53547_.m_60710_(p_53550_, p_53551_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_53547_, p_53548_, p_53549_, p_53550_, p_53551_, p_53552_);
    }

    public Block m_53560_() {
        return this.f_53525_;
    }

    @Override
    public boolean m_7357_(BlockState p_53535_, BlockGetter p_53536_, BlockPos p_53537_, PathComputationType p_53538_) {
        return false;
    }
}

