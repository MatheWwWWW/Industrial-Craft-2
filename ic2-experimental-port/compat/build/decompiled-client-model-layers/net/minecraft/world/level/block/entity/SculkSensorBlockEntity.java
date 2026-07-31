/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import org.slf4j.Logger;

public class SculkSensorBlockEntity
extends BlockEntity
implements VibrationListener.VibrationListenerConfig {
    private static final Logger f_222794_ = LogUtils.getLogger();
    private VibrationListener f_155632_;
    private int f_155633_;

    public SculkSensorBlockEntity(BlockPos p_155635_, BlockState p_155636_) {
        super(BlockEntityType.f_155257_, p_155635_, p_155636_);
        this.f_155632_ = new VibrationListener(new BlockPositionSource(this.f_58858_), ((SculkSensorBlock)p_155636_.m_60734_()).m_154482_(), this, null, 0.0f, 0);
    }

    @Override
    public void m_142466_(CompoundTag p_155649_) {
        super.m_142466_(p_155649_);
        this.f_155633_ = p_155649_.m_128451_("last_vibration_frequency");
        if (p_155649_.m_128425_("listener", 10)) {
            VibrationListener.m_223781_(this).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_155649_.m_128469_("listener"))).resultOrPartial(arg_0 -> ((Logger)f_222794_).error(arg_0)).ifPresent(p_222817_ -> {
                this.f_155632_ = p_222817_;
            });
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_187511_) {
        super.m_183515_(p_187511_);
        p_187511_.m_128405_("last_vibration_frequency", this.f_155633_);
        VibrationListener.m_223781_(this).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_155632_).resultOrPartial(arg_0 -> ((Logger)f_222794_).error(arg_0)).ifPresent(p_222820_ -> p_187511_.m_128365_("listener", (Tag)p_222820_));
    }

    public VibrationListener m_155655_() {
        return this.f_155632_;
    }

    public int m_155656_() {
        return this.f_155633_;
    }

    @Override
    public boolean m_213734_() {
        return true;
    }

    @Override
    public boolean m_213641_(ServerLevel p_222811_, GameEventListener p_222812_, BlockPos p_222813_, GameEvent p_222814_, @Nullable GameEvent.Context p_222815_) {
        if (this.m_58901_() || p_222813_.equals(this.m_58899_()) && (p_222814_ == GameEvent.f_157794_ || p_222814_ == GameEvent.f_157797_)) {
            return false;
        }
        return SculkSensorBlock.m_154489_(this.m_58900_());
    }

    @Override
    public void m_213991_(ServerLevel p_222803_, GameEventListener p_222804_, BlockPos p_222805_, GameEvent p_222806_, @Nullable Entity p_222807_, @Nullable Entity p_222808_, float p_222809_) {
        BlockState $$7 = this.m_58900_();
        if (SculkSensorBlock.m_154489_($$7)) {
            this.f_155633_ = SculkSensorBlock.f_222121_.getInt((Object)p_222806_);
            SculkSensorBlock.m_222125_(p_222807_, p_222803_, this.f_58858_, $$7, SculkSensorBlockEntity.m_222797_(p_222809_, p_222804_.m_142078_()));
        }
    }

    @Override
    public void m_214037_() {
        this.m_6596_();
    }

    public static int m_222797_(float p_222798_, int p_222799_) {
        double $$2 = (double)p_222798_ / (double)p_222799_;
        return Math.max(1, 15 - Mth.m_14107_($$2 * 15.0));
    }

    public void m_222800_(int p_222801_) {
        this.f_155633_ = p_222801_;
    }
}

