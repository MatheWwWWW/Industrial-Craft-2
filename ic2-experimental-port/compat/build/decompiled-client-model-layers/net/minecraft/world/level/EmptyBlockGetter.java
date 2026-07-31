/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public final class EmptyBlockGetter
extends Enum<EmptyBlockGetter>
implements BlockGetter {
    public static final /* enum */ EmptyBlockGetter INSTANCE = new EmptyBlockGetter();
    private static final /* synthetic */ EmptyBlockGetter[] $VALUES;

    public static EmptyBlockGetter[] values() {
        return (EmptyBlockGetter[])$VALUES.clone();
    }

    public static EmptyBlockGetter valueOf(String p_45871_) {
        return Enum.valueOf(EmptyBlockGetter.class, p_45871_);
    }

    @Override
    @Nullable
    public BlockEntity m_7702_(BlockPos p_45867_) {
        return null;
    }

    @Override
    public BlockState m_8055_(BlockPos p_45869_) {
        return Blocks.f_50016_.m_49966_();
    }

    @Override
    public FluidState m_6425_(BlockPos p_45865_) {
        return Fluids.f_76191_.m_76145_();
    }

    @Override
    public int m_141937_() {
        return 0;
    }

    @Override
    public int m_141928_() {
        return 0;
    }

    private static /* synthetic */ EmptyBlockGetter[] m_151458_() {
        return new EmptyBlockGetter[]{INSTANCE};
    }

    static {
        $VALUES = EmptyBlockGetter.m_151458_();
    }
}

