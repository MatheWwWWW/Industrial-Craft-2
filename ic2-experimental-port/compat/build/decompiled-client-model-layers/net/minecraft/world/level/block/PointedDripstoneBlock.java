/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PointedDripstoneBlock
extends Block
implements Fallable,
SimpleWaterloggedBlock {
    public static final DirectionProperty f_154009_ = BlockStateProperties.f_155997_;
    public static final EnumProperty<DripstoneThickness> f_154010_ = BlockStateProperties.f_155998_;
    public static final BooleanProperty f_154011_ = BlockStateProperties.f_61362_;
    private static final int f_154012_ = 11;
    private static final int f_154014_ = 2;
    private static final float f_154015_ = 0.02f;
    private static final float f_154016_ = 0.12f;
    private static final int f_154017_ = 11;
    private static final float f_221844_ = 0.17578125f;
    private static final float f_221845_ = 0.05859375f;
    private static final double f_154020_ = 0.6;
    private static final float f_154021_ = 1.0f;
    private static final int f_154022_ = 40;
    private static final int f_153994_ = 6;
    private static final float f_153995_ = 2.0f;
    private static final int f_153996_ = 2;
    private static final float f_153997_ = 5.0f;
    private static final float f_153998_ = 0.011377778f;
    private static final int f_153999_ = 7;
    private static final int f_154000_ = 10;
    private static final float f_154001_ = 0.6875f;
    private static final VoxelShape f_154002_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape f_154003_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
    private static final VoxelShape f_154004_ = Block.m_49796_(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape f_154005_ = Block.m_49796_(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape f_154006_ = Block.m_49796_(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape f_154007_ = Block.m_49796_(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    private static final float f_154008_ = 0.125f;
    private static final VoxelShape f_202005_ = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    public PointedDripstoneBlock(BlockBehaviour.Properties p_154025_) {
        super(p_154025_);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_154009_, Direction.UP)).m_61124_(f_154010_, DripstoneThickness.TIP)).m_61124_(f_154011_, false));
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_154157_) {
        p_154157_.m_61104_(f_154009_, f_154010_, f_154011_);
    }

    @Override
    public boolean m_7898_(BlockState p_154137_, LevelReader p_154138_, BlockPos p_154139_) {
        return PointedDripstoneBlock.m_154221_(p_154138_, p_154139_, p_154137_.m_61143_(f_154009_));
    }

    @Override
    public BlockState m_7417_(BlockState p_154147_, Direction p_154148_, BlockState p_154149_, LevelAccessor p_154150_, BlockPos p_154151_, BlockPos p_154152_) {
        if (p_154147_.m_61143_(f_154011_).booleanValue()) {
            p_154150_.m_186469_(p_154151_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_154150_));
        }
        if (p_154148_ != Direction.UP && p_154148_ != Direction.DOWN) {
            return p_154147_;
        }
        Direction $$6 = p_154147_.m_61143_(f_154009_);
        if ($$6 == Direction.DOWN && p_154150_.m_183326_().m_183582_(p_154151_, this)) {
            return p_154147_;
        }
        if (p_154148_ == $$6.m_122424_() && !this.m_7898_(p_154147_, p_154150_, p_154151_)) {
            if ($$6 == Direction.DOWN) {
                p_154150_.m_186460_(p_154151_, this, 2);
            } else {
                p_154150_.m_186460_(p_154151_, this, 1);
            }
            return p_154147_;
        }
        boolean $$7 = p_154147_.m_61143_(f_154010_) == DripstoneThickness.TIP_MERGE;
        DripstoneThickness $$8 = PointedDripstoneBlock.m_154092_(p_154150_, p_154151_, $$6, $$7);
        return (BlockState)p_154147_.m_61124_(f_154010_, $$8);
    }

    @Override
    public void m_5581_(Level p_154042_, BlockState p_154043_, BlockHitResult p_154044_, Projectile p_154045_) {
        BlockPos $$4 = p_154044_.m_82425_();
        if (!p_154042_.f_46443_ && p_154045_.m_142265_(p_154042_, $$4) && p_154045_ instanceof ThrownTrident && p_154045_.m_20184_().m_82553_() > 0.6) {
            p_154042_.m_46961_($$4, true);
        }
    }

    @Override
    public void m_142072_(Level p_154047_, BlockState p_154048_, BlockPos p_154049_, Entity p_154050_, float p_154051_) {
        if (p_154048_.m_61143_(f_154009_) == Direction.UP && p_154048_.m_61143_(f_154010_) == DripstoneThickness.TIP) {
            p_154050_.m_142535_(p_154051_ + 2.0f, 2.0f, DamageSource.f_146703_);
        } else {
            super.m_142072_(p_154047_, p_154048_, p_154049_, p_154050_, p_154051_);
        }
    }

    @Override
    public void m_214162_(BlockState p_221870_, Level p_221871_, BlockPos p_221872_, RandomSource p_221873_) {
        if (!PointedDripstoneBlock.m_154238_(p_221870_)) {
            return;
        }
        float $$4 = p_221873_.m_188501_();
        if ($$4 > 0.12f) {
            return;
        }
        PointedDripstoneBlock.m_154181_(p_221871_, p_221872_, p_221870_).filter(p_221848_ -> $$4 < 0.02f || PointedDripstoneBlock.m_154158_(p_221848_.f_221893_)).ifPresent(p_221881_ -> PointedDripstoneBlock.m_154071_(p_221871_, p_221872_, p_221870_, p_221881_.f_221893_));
    }

    @Override
    public void m_213897_(BlockState p_221865_, ServerLevel p_221866_, BlockPos p_221867_, RandomSource p_221868_) {
        if (PointedDripstoneBlock.m_154242_(p_221865_) && !this.m_7898_(p_221865_, p_221866_, p_221867_)) {
            p_221866_.m_46961_(p_221867_, true);
        } else {
            PointedDripstoneBlock.m_154097_(p_221865_, p_221866_, p_221867_);
        }
    }

    @Override
    public void m_213898_(BlockState p_221883_, ServerLevel p_221884_, BlockPos p_221885_, RandomSource p_221886_) {
        PointedDripstoneBlock.m_221859_(p_221883_, p_221884_, p_221885_, p_221886_.m_188501_());
        if (p_221886_.m_188501_() < 0.011377778f && PointedDripstoneBlock.m_154203_(p_221883_, p_221884_, p_221885_)) {
            PointedDripstoneBlock.m_221887_(p_221883_, p_221884_, p_221885_, p_221886_);
        }
    }

    /*
     * WARNING - void declaration
     */
    @VisibleForTesting
    public static void m_221859_(BlockState p_221860_, ServerLevel p_221861_, BlockPos p_221862_, float p_221863_) {
        void $$8;
        if (p_221863_ > 0.17578125f && p_221863_ > 0.05859375f) {
            return;
        }
        if (!PointedDripstoneBlock.m_154203_(p_221860_, p_221861_, p_221862_)) {
            return;
        }
        Optional<FluidInfo> $$4 = PointedDripstoneBlock.m_154181_(p_221861_, p_221862_, p_221860_);
        if ($$4.isEmpty()) {
            return;
        }
        Fluid $$5 = $$4.get().f_221893_;
        if ($$5 == Fluids.f_76193_) {
            float $$6 = 0.17578125f;
        } else if ($$5 == Fluids.f_76195_) {
            float $$7 = 0.05859375f;
        } else {
            return;
        }
        if (p_221863_ >= $$8) {
            return;
        }
        BlockPos $$9 = PointedDripstoneBlock.m_154130_(p_221860_, p_221861_, p_221862_, 11, false);
        if ($$9 == null) {
            return;
        }
        if ($$4.get().f_221894_.m_60713_(Blocks.f_220864_) && $$5 == Fluids.f_76193_) {
            BlockState $$10 = Blocks.f_50129_.m_49966_();
            p_221861_.m_46597_($$4.get().f_221892_, $$10);
            Block.m_49897_($$4.get().f_221894_, $$10, p_221861_, $$4.get().f_221892_);
            p_221861_.m_220407_(GameEvent.f_157792_, $$4.get().f_221892_, GameEvent.Context.m_223722_($$10));
            p_221861_.m_46796_(1504, $$9, 0);
            return;
        }
        BlockPos $$11 = PointedDripstoneBlock.m_154076_(p_221861_, $$9, $$5);
        if ($$11 == null) {
            return;
        }
        p_221861_.m_46796_(1504, $$9, 0);
        int $$12 = $$9.m_123342_() - $$11.m_123342_();
        int $$13 = 50 + $$12;
        BlockState $$14 = p_221861_.m_8055_($$11);
        p_221861_.m_186460_($$11, $$14.m_60734_(), $$13);
    }

    @Override
    public PushReaction m_5537_(BlockState p_154237_) {
        return PushReaction.DESTROY;
    }

    @Override
    @Nullable
    public BlockState m_5573_(BlockPlaceContext p_154040_) {
        Direction $$3;
        BlockPos $$2;
        Level $$1 = p_154040_.m_43725_();
        Direction $$4 = PointedDripstoneBlock.m_154190_($$1, $$2 = p_154040_.m_8083_(), $$3 = p_154040_.m_151260_().m_122424_());
        if ($$4 == null) {
            return null;
        }
        boolean $$5 = !p_154040_.m_7078_();
        DripstoneThickness $$6 = PointedDripstoneBlock.m_154092_($$1, $$2, $$4, $$5);
        if ($$6 == null) {
            return null;
        }
        return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_(f_154009_, $$4)).m_61124_(f_154010_, $$6)).m_61124_(f_154011_, $$1.m_6425_($$2).m_76152_() == Fluids.f_76193_);
    }

    @Override
    public FluidState m_5888_(BlockState p_154235_) {
        if (p_154235_.m_61143_(f_154011_).booleanValue()) {
            return Fluids.f_76193_.m_76068_(false);
        }
        return super.m_5888_(p_154235_);
    }

    @Override
    public VoxelShape m_7952_(BlockState p_154170_, BlockGetter p_154171_, BlockPos p_154172_) {
        return Shapes.m_83040_();
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154117_, BlockGetter p_154118_, BlockPos p_154119_, CollisionContext p_154120_) {
        VoxelShape $$10;
        DripstoneThickness $$4 = p_154117_.m_61143_(f_154010_);
        if ($$4 == DripstoneThickness.TIP_MERGE) {
            VoxelShape $$5 = f_154002_;
        } else if ($$4 == DripstoneThickness.TIP) {
            if (p_154117_.m_61143_(f_154009_) == Direction.DOWN) {
                VoxelShape $$6 = f_154004_;
            } else {
                VoxelShape $$7 = f_154003_;
            }
        } else if ($$4 == DripstoneThickness.FRUSTUM) {
            VoxelShape $$8 = f_154005_;
        } else if ($$4 == DripstoneThickness.MIDDLE) {
            VoxelShape $$9 = f_154006_;
        } else {
            $$10 = f_154007_;
        }
        Vec3 $$11 = p_154117_.m_60824_(p_154118_, p_154119_);
        return $$10.m_83216_($$11.f_82479_, 0.0, $$11.f_82481_);
    }

    @Override
    public boolean m_180643_(BlockState p_181235_, BlockGetter p_181236_, BlockPos p_181237_) {
        return false;
    }

    @Override
    public float m_142740_() {
        return 0.125f;
    }

    @Override
    public void m_142525_(Level p_154059_, BlockPos p_154060_, FallingBlockEntity p_154061_) {
        if (!p_154061_.m_20067_()) {
            p_154059_.m_46796_(1045, p_154060_, 0);
        }
    }

    @Override
    public DamageSource m_142088_() {
        return DamageSource.f_146702_;
    }

    @Override
    public Predicate<Entity> m_142398_() {
        return EntitySelector.f_20406_.and(EntitySelector.f_20403_);
    }

    private static void m_154097_(BlockState p_154098_, ServerLevel p_154099_, BlockPos p_154100_) {
        BlockPos.MutableBlockPos $$3 = p_154100_.m_122032_();
        BlockState $$4 = p_154098_;
        while (PointedDripstoneBlock.m_154240_($$4)) {
            FallingBlockEntity $$5 = FallingBlockEntity.m_201971_(p_154099_, $$3, $$4);
            if (PointedDripstoneBlock.m_154153_($$4, true)) {
                int $$6 = Math.max(1 + p_154100_.m_123342_() - $$3.m_123342_(), 6);
                float $$7 = 1.0f * (float)$$6;
                $$5.m_149656_($$7, 40);
                break;
            }
            $$3.m_122173_(Direction.DOWN);
            $$4 = p_154099_.m_8055_($$3);
        }
    }

    @VisibleForTesting
    public static void m_221887_(BlockState p_221888_, ServerLevel p_221889_, BlockPos p_221890_, RandomSource p_221891_) {
        BlockState $$5;
        BlockState $$4 = p_221889_.m_8055_(p_221890_.m_6630_(1));
        if (!PointedDripstoneBlock.m_154140_($$4, $$5 = p_221889_.m_8055_(p_221890_.m_6630_(2)))) {
            return;
        }
        BlockPos $$6 = PointedDripstoneBlock.m_154130_(p_221888_, p_221889_, p_221890_, 7, false);
        if ($$6 == null) {
            return;
        }
        BlockState $$7 = p_221889_.m_8055_($$6);
        if (!PointedDripstoneBlock.m_154238_($$7) || !PointedDripstoneBlock.m_154194_($$7, p_221889_, $$6)) {
            return;
        }
        if (p_221891_.m_188499_()) {
            PointedDripstoneBlock.m_154035_(p_221889_, $$6, Direction.DOWN);
        } else {
            PointedDripstoneBlock.m_154032_(p_221889_, $$6);
        }
    }

    private static void m_154032_(ServerLevel p_154033_, BlockPos p_154034_) {
        BlockPos.MutableBlockPos $$2 = p_154034_.m_122032_();
        for (int $$3 = 0; $$3 < 10; ++$$3) {
            $$2.m_122173_(Direction.DOWN);
            BlockState $$4 = p_154033_.m_8055_($$2);
            if (!$$4.m_60819_().m_76178_()) {
                return;
            }
            if (PointedDripstoneBlock.m_154143_($$4, Direction.UP) && PointedDripstoneBlock.m_154194_($$4, p_154033_, $$2)) {
                PointedDripstoneBlock.m_154035_(p_154033_, $$2, Direction.UP);
                return;
            }
            if (PointedDripstoneBlock.m_154221_(p_154033_, $$2, Direction.UP) && !p_154033_.m_46801_((BlockPos)$$2.m_7495_())) {
                PointedDripstoneBlock.m_154035_(p_154033_, (BlockPos)$$2.m_7495_(), Direction.UP);
                return;
            }
            if (PointedDripstoneBlock.m_202017_(p_154033_, $$2, $$4)) continue;
            return;
        }
    }

    private static void m_154035_(ServerLevel p_154036_, BlockPos p_154037_, Direction p_154038_) {
        BlockPos $$3 = p_154037_.m_121945_(p_154038_);
        BlockState $$4 = p_154036_.m_8055_($$3);
        if (PointedDripstoneBlock.m_154143_($$4, p_154038_.m_122424_())) {
            PointedDripstoneBlock.m_154230_($$4, p_154036_, $$3);
        } else if ($$4.m_60795_() || $$4.m_60713_(Blocks.f_49990_)) {
            PointedDripstoneBlock.m_154087_(p_154036_, $$3, p_154038_, DripstoneThickness.TIP);
        }
    }

    private static void m_154087_(LevelAccessor p_154088_, BlockPos p_154089_, Direction p_154090_, DripstoneThickness p_154091_) {
        BlockState $$4 = (BlockState)((BlockState)((BlockState)Blocks.f_152588_.m_49966_().m_61124_(f_154009_, p_154090_)).m_61124_(f_154010_, p_154091_)).m_61124_(f_154011_, p_154088_.m_6425_(p_154089_).m_76152_() == Fluids.f_76193_);
        p_154088_.m_7731_(p_154089_, $$4, 3);
    }

    private static void m_154230_(BlockState p_154231_, LevelAccessor p_154232_, BlockPos p_154233_) {
        BlockPos $$6;
        BlockPos $$5;
        if (p_154231_.m_61143_(f_154009_) == Direction.UP) {
            BlockPos $$3 = p_154233_;
            BlockPos $$4 = p_154233_.m_7494_();
        } else {
            $$5 = p_154233_;
            $$6 = p_154233_.m_7495_();
        }
        PointedDripstoneBlock.m_154087_(p_154232_, $$5, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        PointedDripstoneBlock.m_154087_(p_154232_, $$6, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void m_154062_(Level p_154063_, BlockPos p_154064_, BlockState p_154065_) {
        PointedDripstoneBlock.m_154181_(p_154063_, p_154064_, p_154065_).ifPresent(p_221856_ -> PointedDripstoneBlock.m_154071_(p_154063_, p_154064_, p_154065_, p_221856_.f_221893_));
    }

    private static void m_154071_(Level p_154072_, BlockPos p_154073_, BlockState p_154074_, Fluid p_154075_) {
        Vec3 $$4 = p_154074_.m_60824_(p_154072_, p_154073_);
        double $$5 = 0.0625;
        double $$6 = (double)p_154073_.m_123341_() + 0.5 + $$4.f_82479_;
        double $$7 = (double)((float)(p_154073_.m_123342_() + 1) - 0.6875f) - 0.0625;
        double $$8 = (double)p_154073_.m_123343_() + 0.5 + $$4.f_82481_;
        Fluid $$9 = PointedDripstoneBlock.m_154052_(p_154072_, p_154075_);
        SimpleParticleType $$10 = $$9.m_205067_(FluidTags.f_13132_) ? ParticleTypes.f_175822_ : ParticleTypes.f_175824_;
        p_154072_.m_7106_($$10, $$6, $$7, $$8, 0.0, 0.0, 0.0);
    }

    @Nullable
    private static BlockPos m_154130_(BlockState p_154131_, LevelAccessor p_154132_, BlockPos p_154133_, int p_154134_, boolean p_154135_) {
        if (PointedDripstoneBlock.m_154153_(p_154131_, p_154135_)) {
            return p_154133_;
        }
        Direction $$5 = p_154131_.m_61143_(f_154009_);
        BiPredicate<BlockPos, BlockState> $$6 = (p_202023_, p_202024_) -> p_202024_.m_60713_(Blocks.f_152588_) && p_202024_.m_61143_(f_154009_) == $$5;
        return PointedDripstoneBlock.m_202006_(p_154132_, p_154133_, $$5.m_122421_(), $$6, p_154168_ -> PointedDripstoneBlock.m_154153_(p_154168_, p_154135_), p_154134_).orElse(null);
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private static Direction m_154190_(LevelReader p_154191_, BlockPos p_154192_, Direction p_154193_) {
        void $$5;
        if (PointedDripstoneBlock.m_154221_(p_154191_, p_154192_, p_154193_)) {
            Direction $$3 = p_154193_;
        } else if (PointedDripstoneBlock.m_154221_(p_154191_, p_154192_, p_154193_.m_122424_())) {
            Direction $$4 = p_154193_.m_122424_();
        } else {
            return null;
        }
        return $$5;
    }

    private static DripstoneThickness m_154092_(LevelReader p_154093_, BlockPos p_154094_, Direction p_154095_, boolean p_154096_) {
        Direction $$4 = p_154095_.m_122424_();
        BlockState $$5 = p_154093_.m_8055_(p_154094_.m_121945_(p_154095_));
        if (PointedDripstoneBlock.m_154207_($$5, $$4)) {
            if (p_154096_ || $$5.m_61143_(f_154010_) == DripstoneThickness.TIP_MERGE) {
                return DripstoneThickness.TIP_MERGE;
            }
            return DripstoneThickness.TIP;
        }
        if (!PointedDripstoneBlock.m_154207_($$5, p_154095_)) {
            return DripstoneThickness.TIP;
        }
        DripstoneThickness $$6 = $$5.m_61143_(f_154010_);
        if ($$6 == DripstoneThickness.TIP || $$6 == DripstoneThickness.TIP_MERGE) {
            return DripstoneThickness.FRUSTUM;
        }
        BlockState $$7 = p_154093_.m_8055_(p_154094_.m_121945_($$4));
        if (!PointedDripstoneBlock.m_154207_($$7, p_154095_)) {
            return DripstoneThickness.BASE;
        }
        return DripstoneThickness.MIDDLE;
    }

    public static boolean m_154238_(BlockState p_154239_) {
        return PointedDripstoneBlock.m_154240_(p_154239_) && p_154239_.m_61143_(f_154010_) == DripstoneThickness.TIP && p_154239_.m_61143_(f_154011_) == false;
    }

    private static boolean m_154194_(BlockState p_154195_, ServerLevel p_154196_, BlockPos p_154197_) {
        Direction $$3 = p_154195_.m_61143_(f_154009_);
        BlockPos $$4 = p_154197_.m_121945_($$3);
        BlockState $$5 = p_154196_.m_8055_($$4);
        if (!$$5.m_60819_().m_76178_()) {
            return false;
        }
        if ($$5.m_60795_()) {
            return true;
        }
        return PointedDripstoneBlock.m_154143_($$5, $$3.m_122424_());
    }

    private static Optional<BlockPos> m_154066_(Level p_154067_, BlockPos p_154068_, BlockState p_154069_, int p_154070_) {
        Direction $$4 = p_154069_.m_61143_(f_154009_);
        BiPredicate<BlockPos, BlockState> $$5 = (p_202015_, p_202016_) -> p_202016_.m_60713_(Blocks.f_152588_) && p_202016_.m_61143_(f_154009_) == $$4;
        return PointedDripstoneBlock.m_202006_(p_154067_, p_154068_, $$4.m_122424_().m_122421_(), $$5, p_154245_ -> !p_154245_.m_60713_(Blocks.f_152588_), p_154070_);
    }

    private static boolean m_154221_(LevelReader p_154222_, BlockPos p_154223_, Direction p_154224_) {
        BlockPos $$3 = p_154223_.m_121945_(p_154224_.m_122424_());
        BlockState $$4 = p_154222_.m_8055_($$3);
        return $$4.m_60783_(p_154222_, $$3, p_154224_) || PointedDripstoneBlock.m_154207_($$4, p_154224_);
    }

    private static boolean m_154153_(BlockState p_154154_, boolean p_154155_) {
        if (!p_154154_.m_60713_(Blocks.f_152588_)) {
            return false;
        }
        DripstoneThickness $$2 = p_154154_.m_61143_(f_154010_);
        return $$2 == DripstoneThickness.TIP || p_154155_ && $$2 == DripstoneThickness.TIP_MERGE;
    }

    private static boolean m_154143_(BlockState p_154144_, Direction p_154145_) {
        return PointedDripstoneBlock.m_154153_(p_154144_, false) && p_154144_.m_61143_(f_154009_) == p_154145_;
    }

    private static boolean m_154240_(BlockState p_154241_) {
        return PointedDripstoneBlock.m_154207_(p_154241_, Direction.DOWN);
    }

    private static boolean m_154242_(BlockState p_154243_) {
        return PointedDripstoneBlock.m_154207_(p_154243_, Direction.UP);
    }

    private static boolean m_154203_(BlockState p_154204_, LevelReader p_154205_, BlockPos p_154206_) {
        return PointedDripstoneBlock.m_154240_(p_154204_) && !p_154205_.m_8055_(p_154206_.m_7494_()).m_60713_(Blocks.f_152588_);
    }

    @Override
    public boolean m_7357_(BlockState p_154112_, BlockGetter p_154113_, BlockPos p_154114_, PathComputationType p_154115_) {
        return false;
    }

    private static boolean m_154207_(BlockState p_154208_, Direction p_154209_) {
        return p_154208_.m_60713_(Blocks.f_152588_) && p_154208_.m_61143_(f_154009_) == p_154209_;
    }

    @Nullable
    private static BlockPos m_154076_(Level p_154077_, BlockPos p_154078_, Fluid p_154079_) {
        Predicate<BlockState> $$3 = p_154162_ -> p_154162_.m_60734_() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)p_154162_.m_60734_()).m_142087_(p_154079_);
        BiPredicate<BlockPos, BlockState> $$4 = (p_202034_, p_202035_) -> PointedDripstoneBlock.m_202017_(p_154077_, p_202034_, p_202035_);
        return PointedDripstoneBlock.m_202006_(p_154077_, p_154078_, Direction.DOWN.m_122421_(), $$4, $$3, 11).orElse(null);
    }

    @Nullable
    public static BlockPos m_154055_(Level p_154056_, BlockPos p_154057_) {
        BiPredicate<BlockPos, BlockState> $$2 = (p_202030_, p_202031_) -> PointedDripstoneBlock.m_202017_(p_154056_, p_202030_, p_202031_);
        return PointedDripstoneBlock.m_202006_(p_154056_, p_154057_, Direction.UP.m_122421_(), $$2, PointedDripstoneBlock::m_154238_, 11).orElse(null);
    }

    public static Fluid m_221849_(ServerLevel p_221850_, BlockPos p_221851_) {
        return PointedDripstoneBlock.m_154181_(p_221850_, p_221851_, p_221850_.m_8055_(p_221851_)).map(p_221858_ -> p_221858_.f_221893_).filter(PointedDripstoneBlock::m_154158_).orElse(Fluids.f_76191_);
    }

    private static Optional<FluidInfo> m_154181_(Level p_154182_, BlockPos p_154183_, BlockState p_154184_) {
        if (!PointedDripstoneBlock.m_154240_(p_154184_)) {
            return Optional.empty();
        }
        return PointedDripstoneBlock.m_154066_(p_154182_, p_154183_, p_154184_, 11).map(p_221876_ -> {
            Fluid $$5;
            BlockPos $$2 = p_221876_.m_7494_();
            BlockState $$3 = p_154182_.m_8055_($$2);
            if ($$3.m_60713_(Blocks.f_220864_) && !p_154182_.m_6042_().f_63857_()) {
                FlowingFluid $$4 = Fluids.f_76193_;
            } else {
                $$5 = p_154182_.m_6425_($$2).m_76152_();
            }
            return new FluidInfo($$2, $$5, $$3);
        });
    }

    private static boolean m_154158_(Fluid p_154159_) {
        return p_154159_ == Fluids.f_76195_ || p_154159_ == Fluids.f_76193_;
    }

    private static boolean m_154140_(BlockState p_154141_, BlockState p_154142_) {
        return p_154141_.m_60713_(Blocks.f_152537_) && p_154142_.m_60713_(Blocks.f_49990_) && p_154142_.m_60819_().m_76170_();
    }

    private static Fluid m_154052_(Level p_154053_, Fluid p_154054_) {
        if (p_154054_.m_6212_(Fluids.f_76191_)) {
            return p_154053_.m_6042_().f_63857_() ? Fluids.f_76195_ : Fluids.f_76193_;
        }
        return p_154054_;
    }

    private static Optional<BlockPos> m_202006_(LevelAccessor p_202007_, BlockPos p_202008_, Direction.AxisDirection p_202009_, BiPredicate<BlockPos, BlockState> p_202010_, Predicate<BlockState> p_202011_, int p_202012_) {
        Direction $$6 = Direction.m_122390_(p_202009_, Direction.Axis.Y);
        BlockPos.MutableBlockPos $$7 = p_202008_.m_122032_();
        for (int $$8 = 1; $$8 < p_202012_; ++$$8) {
            $$7.m_122173_($$6);
            BlockState $$9 = p_202007_.m_8055_($$7);
            if (p_202011_.test($$9)) {
                return Optional.of($$7.m_7949_());
            }
            if (!p_202007_.m_151562_($$7.m_123342_()) && p_202010_.test($$7, $$9)) continue;
            return Optional.empty();
        }
        return Optional.empty();
    }

    private static boolean m_202017_(BlockGetter p_202018_, BlockPos p_202019_, BlockState p_202020_) {
        if (p_202020_.m_60795_()) {
            return true;
        }
        if (p_202020_.m_60804_(p_202018_, p_202019_)) {
            return false;
        }
        if (!p_202020_.m_60819_().m_76178_()) {
            return false;
        }
        VoxelShape $$3 = p_202020_.m_60812_(p_202018_, p_202019_);
        return !Shapes.m_83157_(f_202005_, $$3, BooleanOp.f_82689_);
    }

    record FluidInfo(BlockPos f_221892_, Fluid f_221893_, BlockState f_221894_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{FluidInfo.class, "pos;fluid;sourceState", "f_221892_", "f_221893_", "f_221894_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FluidInfo.class, "pos;fluid;sourceState", "f_221892_", "f_221893_", "f_221894_"}, this);
        }

        @Override
        public final boolean equals(Object p_221903_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FluidInfo.class, "pos;fluid;sourceState", "f_221892_", "f_221893_", "f_221894_"}, this, p_221903_);
        }
    }
}

