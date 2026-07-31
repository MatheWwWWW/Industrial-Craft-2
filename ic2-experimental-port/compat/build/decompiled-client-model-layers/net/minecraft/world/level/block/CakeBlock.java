/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CakeBlock
extends Block {
    public static final int f_152742_ = 6;
    public static final IntegerProperty f_51180_ = BlockStateProperties.f_61412_;
    public static final int f_152743_ = CakeBlock.m_152746_(0);
    protected static final float f_152744_ = 1.0f;
    protected static final float f_152745_ = 2.0f;
    protected static final VoxelShape[] f_51181_ = new VoxelShape[]{Block.m_49796_(1.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(3.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(5.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(7.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(9.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(11.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.m_49796_(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)};

    protected CakeBlock(BlockBehaviour.Properties p_51184_) {
        super(p_51184_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_51180_, 0));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_51222_, BlockGetter p_51223_, BlockPos p_51224_, CollisionContext p_51225_) {
        return f_51181_[p_51222_.m_61143_(f_51180_)];
    }

    @Override
    public InteractionResult m_6227_(BlockState p_51202_, Level p_51203_, BlockPos p_51204_, Player p_51205_, InteractionHand p_51206_, BlockHitResult p_51207_) {
        Block $$8;
        ItemStack $$6 = p_51205_.m_21120_(p_51206_);
        Item $$7 = $$6.m_41720_();
        if ($$6.m_204117_(ItemTags.f_144319_) && p_51202_.m_61143_(f_51180_) == 0 && ($$8 = Block.m_49814_($$7)) instanceof CandleBlock) {
            if (!p_51205_.m_7500_()) {
                $$6.m_41774_(1);
            }
            p_51203_.m_5594_(null, p_51204_, SoundEvents.f_144090_, SoundSource.BLOCKS, 1.0f, 1.0f);
            p_51203_.m_46597_(p_51204_, CandleCakeBlock.m_152865_($$8));
            p_51203_.m_142346_(p_51205_, GameEvent.f_157792_, p_51204_);
            p_51205_.m_36246_(Stats.f_12982_.m_12902_($$7));
            return InteractionResult.SUCCESS;
        }
        if (p_51203_.f_46443_) {
            if (CakeBlock.m_51185_(p_51203_, p_51204_, p_51202_, p_51205_).m_19077_()) {
                return InteractionResult.SUCCESS;
            }
            if ($$6.m_41619_()) {
                return InteractionResult.CONSUME;
            }
        }
        return CakeBlock.m_51185_(p_51203_, p_51204_, p_51202_, p_51205_);
    }

    protected static InteractionResult m_51185_(LevelAccessor p_51186_, BlockPos p_51187_, BlockState p_51188_, Player p_51189_) {
        if (!p_51189_.m_36391_(false)) {
            return InteractionResult.PASS;
        }
        p_51189_.m_36220_(Stats.f_12942_);
        p_51189_.m_36324_().m_38707_(2, 0.1f);
        int $$4 = p_51188_.m_61143_(f_51180_);
        p_51186_.m_142346_(p_51189_, GameEvent.f_157806_, p_51187_);
        if ($$4 < 6) {
            p_51186_.m_7731_(p_51187_, (BlockState)p_51188_.m_61124_(f_51180_, $$4 + 1), 3);
        } else {
            p_51186_.m_7471_(p_51187_, false);
            p_51186_.m_142346_(p_51189_, GameEvent.f_157794_, p_51187_);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState m_7417_(BlockState p_51213_, Direction p_51214_, BlockState p_51215_, LevelAccessor p_51216_, BlockPos p_51217_, BlockPos p_51218_) {
        if (p_51214_ == Direction.DOWN && !p_51213_.m_60710_(p_51216_, p_51217_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_51213_, p_51214_, p_51215_, p_51216_, p_51217_, p_51218_);
    }

    @Override
    public boolean m_7898_(BlockState p_51209_, LevelReader p_51210_, BlockPos p_51211_) {
        return p_51210_.m_8055_(p_51211_.m_7495_()).m_60767_().m_76333_();
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51220_) {
        p_51220_.m_61104_(f_51180_);
    }

    @Override
    public int m_6782_(BlockState p_51198_, Level p_51199_, BlockPos p_51200_) {
        return CakeBlock.m_152746_(p_51198_.m_61143_(f_51180_));
    }

    public static int m_152746_(int p_152747_) {
        return (7 - p_152747_) * 2;
    }

    @Override
    public boolean m_7278_(BlockState p_51191_) {
        return true;
    }

    @Override
    public boolean m_7357_(BlockState p_51193_, BlockGetter p_51194_, BlockPos p_51195_, PathComputationType p_51196_) {
        return false;
    }
}

