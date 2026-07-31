/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

import java.util.function.Predicate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ClipBlockStateContext {
    private final Vec3 f_151397_;
    private final Vec3 f_151398_;
    private final Predicate<BlockState> f_151399_;

    public ClipBlockStateContext(Vec3 p_151401_, Vec3 p_151402_, Predicate<BlockState> p_151403_) {
        this.f_151397_ = p_151401_;
        this.f_151398_ = p_151402_;
        this.f_151399_ = p_151403_;
    }

    public Vec3 m_151404_() {
        return this.f_151398_;
    }

    public Vec3 m_151405_() {
        return this.f_151397_;
    }

    public Predicate<BlockState> m_151406_() {
        return this.f_151399_;
    }
}

