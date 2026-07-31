/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EndPortalBlock
extends BaseEntityBlock {
    protected static final VoxelShape f_53014_ = Block.m_49796_(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

    protected EndPortalBlock(BlockBehaviour.Properties p_53017_) {
        super(p_53017_);
    }

    @Override
    public BlockEntity m_142194_(BlockPos p_153196_, BlockState p_153197_) {
        return new TheEndPortalBlockEntity(p_153196_, p_153197_);
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53038_, BlockGetter p_53039_, BlockPos p_53040_, CollisionContext p_53041_) {
        return f_53014_;
    }

    @Override
    public void m_7892_(BlockState p_53025_, Level p_53026_, BlockPos p_53027_, Entity p_53028_) {
        if (p_53026_ instanceof ServerLevel && !p_53028_.m_20159_() && !p_53028_.m_20160_() && p_53028_.m_6072_() && Shapes.m_83157_(Shapes.m_83064_(p_53028_.m_20191_().m_82386_(-p_53027_.m_123341_(), -p_53027_.m_123342_(), -p_53027_.m_123343_())), p_53025_.m_60808_(p_53026_, p_53027_), BooleanOp.f_82689_)) {
            ResourceKey<Level> $$4 = p_53026_.m_46472_() == Level.f_46430_ ? Level.f_46428_ : Level.f_46430_;
            ServerLevel $$5 = ((ServerLevel)p_53026_).m_7654_().m_129880_($$4);
            if ($$5 == null) {
                return;
            }
            p_53028_.m_5489_($$5);
        }
    }

    @Override
    public void m_214162_(BlockState p_221102_, Level p_221103_, BlockPos p_221104_, RandomSource p_221105_) {
        double $$4 = (double)p_221104_.m_123341_() + p_221105_.m_188500_();
        double $$5 = (double)p_221104_.m_123342_() + 0.8;
        double $$6 = (double)p_221104_.m_123343_() + p_221105_.m_188500_();
        p_221103_.m_7106_(ParticleTypes.f_123762_, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }

    @Override
    public ItemStack m_7397_(BlockGetter p_53021_, BlockPos p_53022_, BlockState p_53023_) {
        return ItemStack.f_41583_;
    }

    @Override
    public boolean m_5946_(BlockState p_53035_, Fluid p_53036_) {
        return false;
    }
}

