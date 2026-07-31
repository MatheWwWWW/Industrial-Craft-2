/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class RandomStroll
extends Behavior<PathfinderMob> {
    private static final int f_147849_ = 10;
    private static final int f_147850_ = 7;
    private final float f_23740_;
    protected final int f_23741_;
    protected final int f_23742_;
    private final boolean f_182340_;

    public RandomStroll(float p_23744_) {
        this(p_23744_, true);
    }

    public RandomStroll(float p_182347_, boolean p_182348_) {
        this(p_182347_, 10, 7, p_182348_);
    }

    public RandomStroll(float p_23746_, int p_23747_, int p_23748_) {
        this(p_23746_, p_23747_, p_23748_, true);
    }

    public RandomStroll(float p_182342_, int p_182343_, int p_182344_, boolean p_182345_) {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.f_26370_, (Object)((Object)MemoryStatus.VALUE_ABSENT)));
        this.f_23740_ = p_182342_;
        this.f_23741_ = p_182343_;
        this.f_23742_ = p_182344_;
        this.f_182340_ = p_182345_;
    }

    @Override
    protected boolean m_6114_(ServerLevel p_182353_, PathfinderMob p_182354_) {
        return this.f_182340_ || !p_182354_.m_20072_();
    }

    @Override
    protected void m_6735_(ServerLevel p_23754_, PathfinderMob p_23755_, long p_23756_) {
        Optional<Vec3> $$3 = Optional.ofNullable(this.m_142622_(p_23755_));
        p_23755_.m_6274_().m_21886_(MemoryModuleType.f_26370_, $$3.map(p_23758_ -> new WalkTarget((Vec3)p_23758_, this.f_23740_, 0)));
    }

    @Nullable
    protected Vec3 m_142622_(PathfinderMob p_147851_) {
        return LandRandomPos.m_148488_(p_147851_, this.f_23741_, this.f_23742_);
    }
}

