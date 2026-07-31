/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CommonLevelAccessor;
import net.minecraft.world.level.LevelTimeAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.LevelTickAccess;
import net.minecraft.world.ticks.ScheduledTick;
import net.minecraft.world.ticks.TickPriority;

public interface LevelAccessor
extends CommonLevelAccessor,
LevelTimeAccess {
    @Override
    default public long m_8044_() {
        return this.m_6106_().m_6792_();
    }

    public long m_183596_();

    public LevelTickAccess<Block> m_183326_();

    private <T> ScheduledTick<T> m_186482_(BlockPos p_186483_, T p_186484_, int p_186485_, TickPriority p_186486_) {
        return new ScheduledTick<T>(p_186484_, p_186483_, this.m_6106_().m_6793_() + (long)p_186485_, p_186486_, this.m_183596_());
    }

    private <T> ScheduledTick<T> m_186478_(BlockPos p_186479_, T p_186480_, int p_186481_) {
        return new ScheduledTick<T>(p_186480_, p_186479_, this.m_6106_().m_6793_() + (long)p_186481_, this.m_183596_());
    }

    default public void m_186464_(BlockPos p_186465_, Block p_186466_, int p_186467_, TickPriority p_186468_) {
        this.m_183326_().m_183393_(this.m_186482_(p_186465_, p_186466_, p_186467_, p_186468_));
    }

    default public void m_186460_(BlockPos p_186461_, Block p_186462_, int p_186463_) {
        this.m_183326_().m_183393_(this.m_186478_(p_186461_, p_186462_, p_186463_));
    }

    public LevelTickAccess<Fluid> m_183324_();

    default public void m_186473_(BlockPos p_186474_, Fluid p_186475_, int p_186476_, TickPriority p_186477_) {
        this.m_183324_().m_183393_(this.m_186482_(p_186474_, p_186475_, p_186476_, p_186477_));
    }

    default public void m_186469_(BlockPos p_186470_, Fluid p_186471_, int p_186472_) {
        this.m_183324_().m_183393_(this.m_186478_(p_186470_, p_186471_, p_186472_));
    }

    public LevelData m_6106_();

    public DifficultyInstance m_6436_(BlockPos var1);

    @Nullable
    public MinecraftServer m_7654_();

    default public Difficulty m_46791_() {
        return this.m_6106_().m_5472_();
    }

    public ChunkSource m_7726_();

    @Override
    default public boolean m_7232_(int p_46794_, int p_46795_) {
        return this.m_7726_().m_5563_(p_46794_, p_46795_);
    }

    public RandomSource m_213780_();

    default public void m_6289_(BlockPos p_46781_, Block p_46782_) {
    }

    default public void m_213683_(Direction p_220411_, BlockState p_220412_, BlockPos p_220413_, BlockPos p_220414_, int p_220415_, int p_220416_) {
        NeighborUpdater.m_230770_(this, p_220411_, p_220412_, p_220413_, p_220414_, p_220415_, p_220416_ - 1);
    }

    public void m_5594_(@Nullable Player var1, BlockPos var2, SoundEvent var3, SoundSource var4, float var5, float var6);

    public void m_7106_(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12);

    public void m_5898_(@Nullable Player var1, int var2, BlockPos var3, int var4);

    default public void m_46796_(int p_46797_, BlockPos p_46798_, int p_46799_) {
        this.m_5898_(null, p_46797_, p_46798_, p_46799_);
    }

    public void m_214171_(GameEvent var1, Vec3 var2, GameEvent.Context var3);

    default public void m_220400_(@Nullable Entity p_220401_, GameEvent p_220402_, Vec3 p_220403_) {
        this.m_214171_(p_220402_, p_220403_, new GameEvent.Context(p_220401_, null));
    }

    default public void m_142346_(@Nullable Entity p_151549_, GameEvent p_151550_, BlockPos p_151551_) {
        this.m_220407_(p_151550_, p_151551_, new GameEvent.Context(p_151549_, null));
    }

    default public void m_220407_(GameEvent p_220408_, BlockPos p_220409_, GameEvent.Context p_220410_) {
        this.m_214171_(p_220408_, Vec3.m_82512_(p_220409_), p_220410_);
    }
}

