/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherPortalBlock
extends Block {
    public static final EnumProperty<Direction.Axis> f_54904_ = BlockStateProperties.f_61364_;
    protected static final int f_153985_ = 2;
    protected static final VoxelShape f_54905_ = Block.m_49796_(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape f_54906_ = Block.m_49796_(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public NetherPortalBlock(BlockBehaviour.Properties p_54909_) {
        super(p_54909_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_54904_, Direction.Axis.X));
    }

    @Override
    public VoxelShape m_5940_(BlockState p_54942_, BlockGetter p_54943_, BlockPos p_54944_, CollisionContext p_54945_) {
        switch (p_54942_.m_61143_(f_54904_)) {
            case Z: {
                return f_54906_;
            }
        }
        return f_54905_;
    }

    @Override
    public void m_213898_(BlockState p_221799_, ServerLevel p_221800_, BlockPos p_221801_, RandomSource p_221802_) {
        if (p_221800_.m_6042_().f_63858_() && p_221800_.m_46469_().m_46207_(GameRules.f_46134_) && p_221802_.m_188503_(2000) < p_221800_.m_46791_().m_19028_()) {
            ZombifiedPiglin $$4;
            while (p_221800_.m_8055_(p_221801_).m_60713_(this)) {
                p_221801_ = p_221801_.m_7495_();
            }
            if (p_221800_.m_8055_(p_221801_).m_60643_(p_221800_, p_221801_, EntityType.f_20531_) && ($$4 = EntityType.f_20531_.m_20600_(p_221800_, null, null, null, p_221801_.m_7494_(), MobSpawnType.STRUCTURE, false, false)) != null) {
                $$4.m_20091_();
            }
        }
    }

    @Override
    public BlockState m_7417_(BlockState p_54928_, Direction p_54929_, BlockState p_54930_, LevelAccessor p_54931_, BlockPos p_54932_, BlockPos p_54933_) {
        boolean $$8;
        Direction.Axis $$6 = p_54929_.m_122434_();
        Direction.Axis $$7 = p_54928_.m_61143_(f_54904_);
        boolean bl = $$8 = $$7 != $$6 && $$6.m_122479_();
        if ($$8 || p_54930_.m_60713_(this) || new PortalShape(p_54931_, p_54932_, $$7).m_77744_()) {
            return super.m_7417_(p_54928_, p_54929_, p_54930_, p_54931_, p_54932_, p_54933_);
        }
        return Blocks.f_50016_.m_49966_();
    }

    @Override
    public void m_7892_(BlockState p_54915_, Level p_54916_, BlockPos p_54917_, Entity p_54918_) {
        if (!p_54918_.m_20159_() && !p_54918_.m_20160_() && p_54918_.m_6072_()) {
            p_54918_.m_20221_(p_54917_);
        }
    }

    @Override
    public void m_214162_(BlockState p_221794_, Level p_221795_, BlockPos p_221796_, RandomSource p_221797_) {
        if (p_221797_.m_188503_(100) == 0) {
            p_221795_.m_7785_((double)p_221796_.m_123341_() + 0.5, (double)p_221796_.m_123342_() + 0.5, (double)p_221796_.m_123343_() + 0.5, SoundEvents.f_12286_, SoundSource.BLOCKS, 0.5f, p_221797_.m_188501_() * 0.4f + 0.8f, false);
        }
        for (int $$4 = 0; $$4 < 4; ++$$4) {
            double $$5 = (double)p_221796_.m_123341_() + p_221797_.m_188500_();
            double $$6 = (double)p_221796_.m_123342_() + p_221797_.m_188500_();
            double $$7 = (double)p_221796_.m_123343_() + p_221797_.m_188500_();
            double $$8 = ((double)p_221797_.m_188501_() - 0.5) * 0.5;
            double $$9 = ((double)p_221797_.m_188501_() - 0.5) * 0.5;
            double $$10 = ((double)p_221797_.m_188501_() - 0.5) * 0.5;
            int $$11 = p_221797_.m_188503_(2) * 2 - 1;
            if (p_221795_.m_8055_(p_221796_.m_122024_()).m_60713_(this) || p_221795_.m_8055_(p_221796_.m_122029_()).m_60713_(this)) {
                $$7 = (double)p_221796_.m_123343_() + 0.5 + 0.25 * (double)$$11;
                $$10 = p_221797_.m_188501_() * 2.0f * (float)$$11;
            } else {
                $$5 = (double)p_221796_.m_123341_() + 0.5 + 0.25 * (double)$$11;
                $$8 = p_221797_.m_188501_() * 2.0f * (float)$$11;
            }
            p_221795_.m_7106_(ParticleTypes.f_123760_, $$5, $$6, $$7, $$8, $$9, $$10);
        }
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_54911_, BlockPos p_54912_, BlockState p_54913_) {
        return ItemStack.f_41583_;
    }

    @Override
    public BlockState m_6843_(BlockState p_54925_, Rotation p_54926_) {
        switch (p_54926_) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                switch (p_54925_.m_61143_(f_54904_)) {
                    case X: {
                        return (BlockState)p_54925_.m_61124_(f_54904_, Direction.Axis.Z);
                    }
                    case Z: {
                        return (BlockState)p_54925_.m_61124_(f_54904_, Direction.Axis.X);
                    }
                }
                return p_54925_;
            }
        }
        return p_54925_;
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54935_) {
        p_54935_.m_61104_(f_54904_);
    }
}

