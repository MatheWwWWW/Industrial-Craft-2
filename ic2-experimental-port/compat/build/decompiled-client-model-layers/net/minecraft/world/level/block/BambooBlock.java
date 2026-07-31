/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooBlock
extends Block
implements BonemealableBlock {
    protected static final float f_152092_ = 3.0f;
    protected static final float f_152093_ = 5.0f;
    protected static final float f_152094_ = 1.5f;
    protected static final VoxelShape f_48866_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    protected static final VoxelShape f_48867_ = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    protected static final VoxelShape f_48868_ = Block.m_49796_(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
    public static final IntegerProperty f_48869_ = BlockStateProperties.f_61405_;
    public static final EnumProperty<BambooLeaves> f_48870_ = BlockStateProperties.f_61400_;
    public static final IntegerProperty f_48871_ = BlockStateProperties.f_61387_;
    public static final int f_152095_ = 16;
    public static final int f_152096_ = 0;
    public static final int f_152097_ = 1;
    public static final int f_152098_ = 0;
    public static final int f_152099_ = 1;

    public BambooBlock(BlockBehaviour.Properties p_48874_) {
        super(p_48874_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_48869_, 0)).m_61124_(f_48870_, BambooLeaves.NONE)).m_61124_(f_48871_, 0));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_48928_) {
        p_48928_.m_61104_(f_48869_, f_48870_, f_48871_);
    }

    @Override
    public boolean m_7420_(BlockState p_48941_, BlockGetter p_48942_, BlockPos p_48943_) {
        return true;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_48945_, BlockGetter p_48946_, BlockPos p_48947_, CollisionContext p_48948_) {
        VoxelShape $$4 = p_48945_.m_61143_(f_48870_) == BambooLeaves.LARGE ? f_48867_ : f_48866_;
        Vec3 $$5 = p_48945_.m_60824_(p_48946_, p_48947_);
        return $$4.m_83216_($$5.f_82479_, $$5.f_82480_, $$5.f_82481_);
    }

    @Override
    public boolean m_7357_(BlockState p_48906_, BlockGetter p_48907_, BlockPos p_48908_, PathComputationType p_48909_) {
        return false;
    }

    @Override
    public VoxelShape m_5939_(BlockState p_48950_, BlockGetter p_48951_, BlockPos p_48952_, CollisionContext p_48953_) {
        Vec3 $$4 = p_48950_.m_60824_(p_48951_, p_48952_);
        return f_48868_.m_83216_($$4.f_82479_, $$4.f_82480_, $$4.f_82481_);
    }

    @Override
    public boolean m_180643_(BlockState p_181159_, BlockGetter p_181160_, BlockPos p_181161_) {
        return false;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_48881_) {
        FluidState $$1 = p_48881_.m_43725_().m_6425_(p_48881_.m_8083_());
        if (!$$1.m_76178_()) {
            return null;
        }
        BlockState $$2 = p_48881_.m_43725_().m_8055_(p_48881_.m_8083_().m_7495_());
        if ($$2.m_204336_(BlockTags.f_13065_)) {
            if ($$2.m_60713_(Blocks.f_50570_)) {
                return (BlockState)this.m_49966_().m_61124_(f_48869_, 0);
            }
            if ($$2.m_60713_(Blocks.f_50571_)) {
                int $$3 = $$2.m_61143_(f_48869_) > 0 ? 1 : 0;
                return (BlockState)this.m_49966_().m_61124_(f_48869_, $$3);
            }
            BlockState $$4 = p_48881_.m_43725_().m_8055_(p_48881_.m_8083_().m_7494_());
            if ($$4.m_60713_(Blocks.f_50571_)) {
                return (BlockState)this.m_49966_().m_61124_(f_48869_, $$4.m_61143_(f_48869_));
            }
            return Blocks.f_50570_.m_49966_();
        }
        return null;
    }

    @Override
    public void m_213897_(BlockState p_220727_, ServerLevel p_220728_, BlockPos p_220729_, RandomSource p_220730_) {
        if (!p_220727_.m_60710_(p_220728_, p_220729_)) {
            p_220728_.m_46961_(p_220729_, true);
        }
    }

    @Override
    public boolean m_6724_(BlockState p_48930_) {
        return p_48930_.m_61143_(f_48871_) == 0;
    }

    @Override
    public void m_213898_(BlockState p_220738_, ServerLevel p_220739_, BlockPos p_220740_, RandomSource p_220741_) {
        int $$4;
        if (p_220738_.m_61143_(f_48871_) != 0) {
            return;
        }
        if (p_220741_.m_188503_(3) == 0 && p_220739_.m_46859_(p_220740_.m_7494_()) && p_220739_.m_45524_(p_220740_.m_7494_(), 0) >= 9 && ($$4 = this.m_48932_(p_220739_, p_220740_) + 1) < 16) {
            this.m_220731_(p_220738_, p_220739_, p_220740_, p_220741_, $$4);
        }
    }

    @Override
    public boolean m_7898_(BlockState p_48917_, LevelReader p_48918_, BlockPos p_48919_) {
        return p_48918_.m_8055_(p_48919_.m_7495_()).m_204336_(BlockTags.f_13065_);
    }

    @Override
    public BlockState m_7417_(BlockState p_48921_, Direction p_48922_, BlockState p_48923_, LevelAccessor p_48924_, BlockPos p_48925_, BlockPos p_48926_) {
        if (!p_48921_.m_60710_(p_48924_, p_48925_)) {
            p_48924_.m_186460_(p_48925_, this, 1);
        }
        if (p_48922_ == Direction.UP && p_48923_.m_60713_(Blocks.f_50571_) && p_48923_.m_61143_(f_48869_) > p_48921_.m_61143_(f_48869_)) {
            p_48924_.m_7731_(p_48925_, (BlockState)p_48921_.m_61122_(f_48869_), 2);
        }
        return super.m_7417_(p_48921_, p_48922_, p_48923_, p_48924_, p_48925_, p_48926_);
    }

    @Override
    public boolean m_7370_(BlockGetter p_48886_, BlockPos p_48887_, BlockState p_48888_, boolean p_48889_) {
        int $$5;
        int $$4 = this.m_48882_(p_48886_, p_48887_);
        return $$4 + ($$5 = this.m_48932_(p_48886_, p_48887_)) + 1 < 16 && p_48886_.m_8055_(p_48887_.m_6630_($$4)).m_61143_(f_48871_) != 1;
    }

    @Override
    public boolean m_214167_(Level p_220722_, RandomSource p_220723_, BlockPos p_220724_, BlockState p_220725_) {
        return true;
    }

    @Override
    public void m_214148_(ServerLevel p_220717_, RandomSource p_220718_, BlockPos p_220719_, BlockState p_220720_) {
        int $$4 = this.m_48882_(p_220717_, p_220719_);
        int $$5 = this.m_48932_(p_220717_, p_220719_);
        int $$6 = $$4 + $$5 + 1;
        int $$7 = 1 + p_220718_.m_188503_(2);
        for (int $$8 = 0; $$8 < $$7; ++$$8) {
            BlockPos $$9 = p_220719_.m_6630_($$4);
            BlockState $$10 = p_220717_.m_8055_($$9);
            if ($$6 >= 16 || $$10.m_61143_(f_48871_) == 1 || !p_220717_.m_46859_($$9.m_7494_())) {
                return;
            }
            this.m_220731_($$10, p_220717_, $$9, p_220718_, $$6);
            ++$$4;
            ++$$6;
        }
    }

    @Override
    public float m_5880_(BlockState p_48901_, Player p_48902_, BlockGetter p_48903_, BlockPos p_48904_) {
        if (p_48902_.m_21205_().m_41720_() instanceof SwordItem) {
            return 1.0f;
        }
        return super.m_5880_(p_48901_, p_48902_, p_48903_, p_48904_);
    }

    protected void m_220731_(BlockState p_220732_, Level p_220733_, BlockPos p_220734_, RandomSource p_220735_, int p_220736_) {
        BlockState $$5 = p_220733_.m_8055_(p_220734_.m_7495_());
        BlockPos $$6 = p_220734_.m_6625_(2);
        BlockState $$7 = p_220733_.m_8055_($$6);
        BambooLeaves $$8 = BambooLeaves.NONE;
        if (p_220736_ >= 1) {
            if (!$$5.m_60713_(Blocks.f_50571_) || $$5.m_61143_(f_48870_) == BambooLeaves.NONE) {
                $$8 = BambooLeaves.SMALL;
            } else if ($$5.m_60713_(Blocks.f_50571_) && $$5.m_61143_(f_48870_) != BambooLeaves.NONE) {
                $$8 = BambooLeaves.LARGE;
                if ($$7.m_60713_(Blocks.f_50571_)) {
                    p_220733_.m_7731_(p_220734_.m_7495_(), (BlockState)$$5.m_61124_(f_48870_, BambooLeaves.SMALL), 3);
                    p_220733_.m_7731_($$6, (BlockState)$$7.m_61124_(f_48870_, BambooLeaves.NONE), 3);
                }
            }
        }
        int $$9 = p_220732_.m_61143_(f_48869_) == 1 || $$7.m_60713_(Blocks.f_50571_) ? 1 : 0;
        int $$10 = p_220736_ >= 11 && p_220735_.m_188501_() < 0.25f || p_220736_ == 15 ? 1 : 0;
        p_220733_.m_7731_(p_220734_.m_7494_(), (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_48869_, $$9)).m_61124_(f_48870_, $$8)).m_61124_(f_48871_, $$10), 3);
    }

    protected int m_48882_(BlockGetter p_48883_, BlockPos p_48884_) {
        int $$2;
        for ($$2 = 0; $$2 < 16 && p_48883_.m_8055_(p_48884_.m_6630_($$2 + 1)).m_60713_(Blocks.f_50571_); ++$$2) {
        }
        return $$2;
    }

    protected int m_48932_(BlockGetter p_48933_, BlockPos p_48934_) {
        int $$2;
        for ($$2 = 0; $$2 < 16 && p_48933_.m_8055_(p_48934_.m_6625_($$2 + 1)).m_60713_(Blocks.f_50571_); ++$$2) {
        }
        return $$2;
    }
}

