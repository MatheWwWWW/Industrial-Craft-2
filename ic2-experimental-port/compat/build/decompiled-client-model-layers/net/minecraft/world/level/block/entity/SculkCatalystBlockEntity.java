/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package net.minecraft.world.level.block.entity;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;

public class SculkCatalystBlockEntity
extends BlockEntity
implements GameEventListener {
    private final BlockPositionSource f_222771_;
    private final SculkSpreader f_222772_;

    public SculkCatalystBlockEntity(BlockPos p_222774_, BlockState p_222775_) {
        super(BlockEntityType.f_222758_, p_222774_, p_222775_);
        this.f_222771_ = new BlockPositionSource(this.f_58858_);
        this.f_222772_ = SculkSpreader.m_222254_();
    }

    @Override
    public boolean m_214054_() {
        return true;
    }

    @Override
    public PositionSource m_142460_() {
        return this.f_222771_;
    }

    @Override
    public int m_142078_() {
        return 8;
    }

    @Override
    public boolean m_214068_(ServerLevel p_222777_, GameEvent.Message p_222778_) {
        Entity entity;
        if (this.m_58901_()) {
            return false;
        }
        GameEvent.Context $$2 = p_222778_.m_223744_();
        if (p_222778_.m_223740_() == GameEvent.f_223707_ && (entity = $$2.f_223711_()) instanceof LivingEntity) {
            LivingEntity $$3 = (LivingEntity)entity;
            if (!$$3.m_217046_()) {
                int $$4 = $$3.m_213860_();
                if ($$3.m_6149_() && $$4 > 0) {
                    this.f_222772_.m_222266_(new BlockPos(p_222778_.m_223743_().m_231075_(Direction.UP, 0.5)), $$4);
                    LivingEntity $$5 = $$3.m_21188_();
                    if ($$5 instanceof ServerPlayer) {
                        ServerPlayer $$6 = (ServerPlayer)$$5;
                        DamageSource $$7 = $$3.m_21225_() == null ? DamageSource.m_19344_($$6) : $$3.m_21225_();
                        CriteriaTriggers.f_215656_.m_48104_($$6, $$2.f_223711_(), $$7);
                    }
                }
                $$3.m_217045_();
                SculkCatalystBlock.m_222094_(p_222777_, this.f_58858_, this.m_58900_(), p_222777_.m_213780_());
            }
            return true;
        }
        return false;
    }

    public static void m_222779_(Level p_222780_, BlockPos p_222781_, BlockState p_222782_, SculkCatalystBlockEntity p_222783_) {
        p_222783_.f_222772_.m_222255_(p_222780_, p_222781_, p_222780_.m_213780_(), true);
    }

    @Override
    public void m_142466_(CompoundTag p_222787_) {
        super.m_142466_(p_222787_);
        this.f_222772_.m_222269_(p_222787_);
    }

    @Override
    protected void m_183515_(CompoundTag p_222789_) {
        this.f_222772_.m_222275_(p_222789_);
        super.m_183515_(p_222789_);
    }

    @VisibleForTesting
    public SculkSpreader m_222793_() {
        return this.f_222772_;
    }

    private static /* synthetic */ Integer m_222784_(SculkSpreader.ChargeCursor p_222785_) {
        return 1;
    }
}

