/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.phys;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BlockHitResult
extends HitResult {
    private final Direction f_82410_;
    private final BlockPos f_82411_;
    private final boolean f_82412_;
    private final boolean f_82413_;

    public static BlockHitResult m_82426_(Vec3 p_82427_, Direction p_82428_, BlockPos p_82429_) {
        return new BlockHitResult(true, p_82427_, p_82428_, p_82429_, false);
    }

    public BlockHitResult(Vec3 p_82415_, Direction p_82416_, BlockPos p_82417_, boolean p_82418_) {
        this(false, p_82415_, p_82416_, p_82417_, p_82418_);
    }

    private BlockHitResult(boolean p_82420_, Vec3 p_82421_, Direction p_82422_, BlockPos p_82423_, boolean p_82424_) {
        super(p_82421_);
        this.f_82412_ = p_82420_;
        this.f_82410_ = p_82422_;
        this.f_82411_ = p_82423_;
        this.f_82413_ = p_82424_;
    }

    public BlockHitResult m_82432_(Direction p_82433_) {
        return new BlockHitResult(this.f_82412_, this.f_82445_, p_82433_, this.f_82411_, this.f_82413_);
    }

    public BlockHitResult m_82430_(BlockPos p_82431_) {
        return new BlockHitResult(this.f_82412_, this.f_82445_, this.f_82410_, p_82431_, this.f_82413_);
    }

    public BlockPos m_82425_() {
        return this.f_82411_;
    }

    public Direction m_82434_() {
        return this.f_82410_;
    }

    @Override
    public HitResult.Type m_6662_() {
        return this.f_82412_ ? HitResult.Type.MISS : HitResult.Type.BLOCK;
    }

    public boolean m_82436_() {
        return this.f_82413_;
    }
}

