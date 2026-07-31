/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TurtleEggBlock
extends Block {
    public static final int f_154841_ = 2;
    public static final int f_154842_ = 1;
    public static final int f_154843_ = 4;
    private static final VoxelShape f_57755_ = Block.m_49796_(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
    private static final VoxelShape f_57756_ = Block.m_49796_(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
    public static final IntegerProperty f_57753_ = BlockStateProperties.f_61416_;
    public static final IntegerProperty f_57754_ = BlockStateProperties.f_61415_;

    public TurtleEggBlock(BlockBehaviour.Properties p_57759_) {
        super(p_57759_);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_57753_, 0)).m_61124_(f_57754_, 1));
    }

    @Override
    public void m_141947_(Level p_154857_, BlockPos p_154858_, BlockState p_154859_, Entity p_154860_) {
        if (!p_154860_.m_20161_()) {
            this.m_154850_(p_154857_, p_154859_, p_154858_, p_154860_, 100);
        }
        super.m_141947_(p_154857_, p_154858_, p_154859_, p_154860_);
    }

    @Override
    public void m_142072_(Level p_154845_, BlockState p_154846_, BlockPos p_154847_, Entity p_154848_, float p_154849_) {
        if (!(p_154848_ instanceof Zombie)) {
            this.m_154850_(p_154845_, p_154846_, p_154847_, p_154848_, 3);
        }
        super.m_142072_(p_154845_, p_154846_, p_154847_, p_154848_, p_154849_);
    }

    private void m_154850_(Level p_154851_, BlockState p_154852_, BlockPos p_154853_, Entity p_154854_, int p_154855_) {
        if (!this.m_57767_(p_154851_, p_154854_)) {
            return;
        }
        if (!p_154851_.f_46443_ && p_154851_.f_46441_.m_188503_(p_154855_) == 0 && p_154852_.m_60713_(Blocks.f_50578_)) {
            this.m_57791_(p_154851_, p_154853_, p_154852_);
        }
    }

    private void m_57791_(Level p_57792_, BlockPos p_57793_, BlockState p_57794_) {
        p_57792_.m_5594_(null, p_57793_, SoundEvents.f_12533_, SoundSource.BLOCKS, 0.7f, 0.9f + p_57792_.f_46441_.m_188501_() * 0.2f);
        int $$3 = p_57794_.m_61143_(f_57754_);
        if ($$3 <= 1) {
            p_57792_.m_46961_(p_57793_, false);
        } else {
            p_57792_.m_7731_(p_57793_, (BlockState)p_57794_.m_61124_(f_57754_, $$3 - 1), 2);
            p_57792_.m_220407_(GameEvent.f_157794_, p_57793_, GameEvent.Context.m_223722_(p_57794_));
            p_57792_.m_46796_(2001, p_57793_, Block.m_49956_(p_57794_));
        }
    }

    @Override
    public void m_213898_(BlockState p_222644_, ServerLevel p_222645_, BlockPos p_222646_, RandomSource p_222647_) {
        if (this.m_57765_(p_222645_) && TurtleEggBlock.m_57762_(p_222645_, p_222646_)) {
            int $$4 = p_222644_.m_61143_(f_57753_);
            if ($$4 < 2) {
                p_222645_.m_5594_(null, p_222646_, SoundEvents.f_12534_, SoundSource.BLOCKS, 0.7f, 0.9f + p_222647_.m_188501_() * 0.2f);
                p_222645_.m_7731_(p_222646_, (BlockState)p_222644_.m_61124_(f_57753_, $$4 + 1), 2);
            } else {
                p_222645_.m_5594_(null, p_222646_, SoundEvents.f_12535_, SoundSource.BLOCKS, 0.7f, 0.9f + p_222647_.m_188501_() * 0.2f);
                p_222645_.m_7471_(p_222646_, false);
                for (int $$5 = 0; $$5 < p_222644_.m_61143_(f_57754_); ++$$5) {
                    p_222645_.m_46796_(2001, p_222646_, Block.m_49956_(p_222644_));
                    Turtle $$6 = EntityType.f_20490_.m_20615_(p_222645_);
                    $$6.m_146762_(-24000);
                    $$6.m_30219_(p_222646_);
                    $$6.m_7678_((double)p_222646_.m_123341_() + 0.3 + (double)$$5 * 0.2, p_222646_.m_123342_(), (double)p_222646_.m_123343_() + 0.3, 0.0f, 0.0f);
                    p_222645_.m_7967_($$6);
                }
            }
        }
    }

    public static boolean m_57762_(BlockGetter p_57763_, BlockPos p_57764_) {
        return TurtleEggBlock.m_57800_(p_57763_, p_57764_.m_7495_());
    }

    public static boolean m_57800_(BlockGetter p_57801_, BlockPos p_57802_) {
        return p_57801_.m_8055_(p_57802_).m_204336_(BlockTags.f_13029_);
    }

    @Override
    public void m_6807_(BlockState p_57814_, Level p_57815_, BlockPos p_57816_, BlockState p_57817_, boolean p_57818_) {
        if (TurtleEggBlock.m_57762_(p_57815_, p_57816_) && !p_57815_.f_46443_) {
            p_57815_.m_46796_(2005, p_57816_, 0);
        }
    }

    private boolean m_57765_(Level p_57766_) {
        float $$1 = p_57766_.m_46942_(1.0f);
        if ((double)$$1 < 0.69 && (double)$$1 > 0.65) {
            return true;
        }
        return p_57766_.f_46441_.m_188503_(500) == 0;
    }

    @Override
    public void m_6240_(Level p_57771_, Player p_57772_, BlockPos p_57773_, BlockState p_57774_, @Nullable BlockEntity p_57775_, ItemStack p_57776_) {
        super.m_6240_(p_57771_, p_57772_, p_57773_, p_57774_, p_57775_, p_57776_);
        this.m_57791_(p_57771_, p_57773_, p_57774_);
    }

    @Override
    public boolean m_6864_(BlockState p_57796_, BlockPlaceContext p_57797_) {
        if (!p_57797_.m_7078_() && p_57797_.m_43722_().m_150930_(this.m_5456_()) && p_57796_.m_61143_(f_57754_) < 4) {
            return true;
        }
        return super.m_6864_(p_57796_, p_57797_);
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_57761_) {
        BlockState $$1 = p_57761_.m_43725_().m_8055_(p_57761_.m_8083_());
        if ($$1.m_60713_(this)) {
            return (BlockState)$$1.m_61124_(f_57754_, Math.min(4, $$1.m_61143_(f_57754_) + 1));
        }
        return super.m_5573_(p_57761_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57809_, BlockGetter p_57810_, BlockPos p_57811_, CollisionContext p_57812_) {
        if (p_57809_.m_61143_(f_57754_) > 1) {
            return f_57756_;
        }
        return f_57755_;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_57799_) {
        p_57799_.m_61104_(f_57753_, f_57754_);
    }

    private boolean m_57767_(Level p_57768_, Entity p_57769_) {
        if (p_57769_ instanceof Turtle || p_57769_ instanceof Bat) {
            return false;
        }
        if (p_57769_ instanceof LivingEntity) {
            return p_57769_ instanceof Player || p_57768_.m_46469_().m_46207_(GameRules.f_46132_);
        }
        return false;
    }
}

