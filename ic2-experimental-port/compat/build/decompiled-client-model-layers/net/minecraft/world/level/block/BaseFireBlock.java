/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseFireBlock
extends Block {
    private static final int f_152137_ = 8;
    private final float f_49238_;
    protected static final float f_152136_ = 1.0f;
    protected static final VoxelShape f_49237_ = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

    public BaseFireBlock(BlockBehaviour.Properties p_49241_, float p_49242_) {
        super(p_49241_);
        this.f_49238_ = p_49242_;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_49244_) {
        return BaseFireBlock.m_49245_(p_49244_.m_43725_(), p_49244_.m_8083_());
    }

    public static BlockState m_49245_(BlockGetter p_49246_, BlockPos p_49247_) {
        BlockPos $$2 = p_49247_.m_7495_();
        BlockState $$3 = p_49246_.m_8055_($$2);
        if (SoulFireBlock.m_154650_($$3)) {
            return Blocks.f_50084_.m_49966_();
        }
        return ((FireBlock)Blocks.f_50083_).m_53470_(p_49246_, p_49247_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_49274_, BlockGetter p_49275_, BlockPos p_49276_, CollisionContext p_49277_) {
        return f_49237_;
    }

    @Override
    public void m_214162_(BlockState p_220763_, Level p_220764_, BlockPos p_220765_, RandomSource p_220766_) {
        block12: {
            block11: {
                BlockPos $$4;
                BlockState $$5;
                if (p_220766_.m_188503_(24) == 0) {
                    p_220764_.m_7785_((double)p_220765_.m_123341_() + 0.5, (double)p_220765_.m_123342_() + 0.5, (double)p_220765_.m_123343_() + 0.5, SoundEvents.f_11936_, SoundSource.BLOCKS, 1.0f + p_220766_.m_188501_(), p_220766_.m_188501_() * 0.7f + 0.3f, false);
                }
                if (!this.m_7599_($$5 = p_220764_.m_8055_($$4 = p_220765_.m_7495_())) && !$$5.m_60783_(p_220764_, $$4, Direction.UP)) break block11;
                for (int $$6 = 0; $$6 < 3; ++$$6) {
                    double $$7 = (double)p_220765_.m_123341_() + p_220766_.m_188500_();
                    double $$8 = (double)p_220765_.m_123342_() + p_220766_.m_188500_() * 0.5 + 0.5;
                    double $$9 = (double)p_220765_.m_123343_() + p_220766_.m_188500_();
                    p_220764_.m_7106_(ParticleTypes.f_123755_, $$7, $$8, $$9, 0.0, 0.0, 0.0);
                }
                break block12;
            }
            if (this.m_7599_(p_220764_.m_8055_(p_220765_.m_122024_()))) {
                for (int $$10 = 0; $$10 < 2; ++$$10) {
                    double $$11 = (double)p_220765_.m_123341_() + p_220766_.m_188500_() * (double)0.1f;
                    double $$12 = (double)p_220765_.m_123342_() + p_220766_.m_188500_();
                    double $$13 = (double)p_220765_.m_123343_() + p_220766_.m_188500_();
                    p_220764_.m_7106_(ParticleTypes.f_123755_, $$11, $$12, $$13, 0.0, 0.0, 0.0);
                }
            }
            if (this.m_7599_(p_220764_.m_8055_(p_220765_.m_122029_()))) {
                for (int $$14 = 0; $$14 < 2; ++$$14) {
                    double $$15 = (double)(p_220765_.m_123341_() + 1) - p_220766_.m_188500_() * (double)0.1f;
                    double $$16 = (double)p_220765_.m_123342_() + p_220766_.m_188500_();
                    double $$17 = (double)p_220765_.m_123343_() + p_220766_.m_188500_();
                    p_220764_.m_7106_(ParticleTypes.f_123755_, $$15, $$16, $$17, 0.0, 0.0, 0.0);
                }
            }
            if (this.m_7599_(p_220764_.m_8055_(p_220765_.m_122012_()))) {
                for (int $$18 = 0; $$18 < 2; ++$$18) {
                    double $$19 = (double)p_220765_.m_123341_() + p_220766_.m_188500_();
                    double $$20 = (double)p_220765_.m_123342_() + p_220766_.m_188500_();
                    double $$21 = (double)p_220765_.m_123343_() + p_220766_.m_188500_() * (double)0.1f;
                    p_220764_.m_7106_(ParticleTypes.f_123755_, $$19, $$20, $$21, 0.0, 0.0, 0.0);
                }
            }
            if (this.m_7599_(p_220764_.m_8055_(p_220765_.m_122019_()))) {
                for (int $$22 = 0; $$22 < 2; ++$$22) {
                    double $$23 = (double)p_220765_.m_123341_() + p_220766_.m_188500_();
                    double $$24 = (double)p_220765_.m_123342_() + p_220766_.m_188500_();
                    double $$25 = (double)(p_220765_.m_123343_() + 1) - p_220766_.m_188500_() * (double)0.1f;
                    p_220764_.m_7106_(ParticleTypes.f_123755_, $$23, $$24, $$25, 0.0, 0.0, 0.0);
                }
            }
            if (!this.m_7599_(p_220764_.m_8055_(p_220765_.m_7494_()))) break block12;
            for (int $$26 = 0; $$26 < 2; ++$$26) {
                double $$27 = (double)p_220765_.m_123341_() + p_220766_.m_188500_();
                double $$28 = (double)(p_220765_.m_123342_() + 1) - p_220766_.m_188500_() * (double)0.1f;
                double $$29 = (double)p_220765_.m_123343_() + p_220766_.m_188500_();
                p_220764_.m_7106_(ParticleTypes.f_123755_, $$27, $$28, $$29, 0.0, 0.0, 0.0);
            }
        }
    }

    protected abstract boolean m_7599_(BlockState var1);

    @Override
    public void m_7892_(BlockState p_49260_, Level p_49261_, BlockPos p_49262_, Entity p_49263_) {
        if (!p_49263_.m_5825_()) {
            p_49263_.m_7311_(p_49263_.m_20094_() + 1);
            if (p_49263_.m_20094_() == 0) {
                p_49263_.m_20254_(8);
            }
        }
        p_49263_.m_6469_(DamageSource.f_19305_, this.f_49238_);
        super.m_7892_(p_49260_, p_49261_, p_49262_, p_49263_);
    }

    @Override
    public void m_6807_(BlockState p_49279_, Level p_49280_, BlockPos p_49281_, BlockState p_49282_, boolean p_49283_) {
        Optional<PortalShape> $$5;
        if (p_49282_.m_60713_(p_49279_.m_60734_())) {
            return;
        }
        if (BaseFireBlock.m_49248_(p_49280_) && ($$5 = PortalShape.m_77708_(p_49280_, p_49281_, Direction.Axis.X)).isPresent()) {
            $$5.get().m_77743_();
            return;
        }
        if (!p_49279_.m_60710_(p_49280_, p_49281_)) {
            p_49280_.m_7471_(p_49281_, false);
        }
    }

    private static boolean m_49248_(Level p_49249_) {
        return p_49249_.m_46472_() == Level.f_46428_ || p_49249_.m_46472_() == Level.f_46429_;
    }

    @Override
    protected void m_142387_(Level p_152139_, Player p_152140_, BlockPos p_152141_, BlockState p_152142_) {
    }

    @Override
    public void m_5707_(Level p_49251_, BlockPos p_49252_, BlockState p_49253_, Player p_49254_) {
        if (!p_49251_.m_5776_()) {
            p_49251_.m_5898_(null, 1009, p_49252_, 0);
        }
        super.m_5707_(p_49251_, p_49252_, p_49253_, p_49254_);
    }

    public static boolean m_49255_(Level p_49256_, BlockPos p_49257_, Direction p_49258_) {
        BlockState $$3 = p_49256_.m_8055_(p_49257_);
        if (!$$3.m_60795_()) {
            return false;
        }
        return BaseFireBlock.m_49245_(p_49256_, p_49257_).m_60710_(p_49256_, p_49257_) || BaseFireBlock.m_49269_(p_49256_, p_49257_, p_49258_);
    }

    private static boolean m_49269_(Level p_49270_, BlockPos p_49271_, Direction p_49272_) {
        if (!BaseFireBlock.m_49248_(p_49270_)) {
            return false;
        }
        BlockPos.MutableBlockPos $$3 = p_49271_.m_122032_();
        boolean $$4 = false;
        for (Direction $$5 : Direction.values()) {
            if (!p_49270_.m_8055_($$3.m_122190_(p_49271_).m_122173_($$5)).m_60713_(Blocks.f_50080_)) continue;
            $$4 = true;
            break;
        }
        if (!$$4) {
            return false;
        }
        Direction.Axis $$6 = p_49272_.m_122434_().m_122479_() ? p_49272_.m_122428_().m_122434_() : Direction.Plane.HORIZONTAL.m_235692_(p_49270_.f_46441_);
        return PortalShape.m_77708_(p_49270_, p_49271_, $$6).isPresent();
    }
}

