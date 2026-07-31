/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FlowerBlock
extends BushBlock {
    protected static final float f_153265_ = 3.0f;
    protected static final VoxelShape f_53507_ = Block.m_49796_(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
    private final MobEffect f_53508_;
    private final int f_53509_;

    public FlowerBlock(MobEffect p_53512_, int p_53513_, BlockBehaviour.Properties p_53514_) {
        super(p_53514_);
        this.f_53508_ = p_53512_;
        this.f_53509_ = p_53512_.m_8093_() ? p_53513_ : p_53513_ * 20;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_53517_, BlockGetter p_53518_, BlockPos p_53519_, CollisionContext p_53520_) {
        Vec3 $$4 = p_53517_.m_60824_(p_53518_, p_53519_);
        return f_53507_.m_83216_($$4.f_82479_, $$4.f_82480_, $$4.f_82481_);
    }

    public MobEffect m_53521_() {
        return this.f_53508_;
    }

    public int m_53522_() {
        return this.f_53509_;
    }
}

