/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

public class SculkShriekerBlockEntity
extends BlockEntity
implements VibrationListener.VibrationListenerConfig {
    private static final Logger f_222822_ = LogUtils.getLogger();
    private static final int f_222823_ = 8;
    private static final int f_222824_ = 10;
    private static final int f_222825_ = 20;
    private static final int f_222826_ = 5;
    private static final int f_222827_ = 6;
    private static final int f_222828_ = 40;
    private static final Int2ObjectMap<SoundEvent> f_222829_ = (Int2ObjectMap)Util.m_137469_(new Int2ObjectOpenHashMap(), p_222866_ -> {
        p_222866_.put(1, (Object)SoundEvents.f_215766_);
        p_222866_.put(2, (Object)SoundEvents.f_215767_);
        p_222866_.put(3, (Object)SoundEvents.f_215768_);
        p_222866_.put(4, (Object)SoundEvents.f_215765_);
    });
    private static final int f_222830_ = 90;
    private int f_222831_;
    private VibrationListener f_222832_;

    public SculkShriekerBlockEntity(BlockPos p_222835_, BlockState p_222836_) {
        super(BlockEntityType.f_222759_, p_222835_, p_222836_);
        this.f_222832_ = new VibrationListener(new BlockPositionSource(this.f_58858_), 8, this, null, 0.0f, 0);
    }

    public VibrationListener m_222879_() {
        return this.f_222832_;
    }

    @Override
    public void m_142466_(CompoundTag p_222868_) {
        super.m_142466_(p_222868_);
        if (p_222868_.m_128425_("warning_level", 99)) {
            this.f_222831_ = p_222868_.m_128451_("warning_level");
        }
        if (p_222868_.m_128425_("listener", 10)) {
            VibrationListener.m_223781_(this).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_222868_.m_128469_("listener"))).resultOrPartial(arg_0 -> ((Logger)f_222822_).error(arg_0)).ifPresent(p_222864_ -> {
                this.f_222832_ = p_222864_;
            });
        }
    }

    @Override
    protected void m_183515_(CompoundTag p_222878_) {
        super.m_183515_(p_222878_);
        p_222878_.m_128405_("warning_level", this.f_222831_);
        VibrationListener.m_223781_(this).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_222832_).resultOrPartial(arg_0 -> ((Logger)f_222822_).error(arg_0)).ifPresent(p_222871_ -> p_222878_.m_128365_("listener", (Tag)p_222871_));
    }

    @Override
    public TagKey<GameEvent> m_213929_() {
        return GameEventTags.f_215854_;
    }

    @Override
    public boolean m_213641_(ServerLevel p_222856_, GameEventListener p_222857_, BlockPos p_222858_, GameEvent p_222859_, GameEvent.Context p_222860_) {
        return !this.m_58901_() && this.m_58900_().m_61143_(SculkShriekerBlock.f_222152_) == false && SculkShriekerBlockEntity.m_222861_(p_222860_.f_223711_()) != null;
    }

    @Nullable
    public static ServerPlayer m_222861_(@Nullable Entity p_222862_) {
        ItemEntity $$5;
        Projectile $$3;
        Entity entity;
        Entity entity2;
        if (p_222862_ instanceof ServerPlayer) {
            ServerPlayer $$1 = (ServerPlayer)p_222862_;
            return $$1;
        }
        if (p_222862_ != null && (entity2 = p_222862_.m_6688_()) instanceof ServerPlayer) {
            ServerPlayer $$2 = (ServerPlayer)entity2;
            return $$2;
        }
        if (p_222862_ instanceof Projectile && (entity = ($$3 = (Projectile)p_222862_).m_37282_()) instanceof ServerPlayer) {
            ServerPlayer $$4 = (ServerPlayer)entity;
            return $$4;
        }
        if (p_222862_ instanceof ItemEntity && (entity = ($$5 = (ItemEntity)p_222862_).m_238333_()) instanceof ServerPlayer) {
            ServerPlayer $$6 = (ServerPlayer)entity;
            return $$6;
        }
        return null;
    }

    @Override
    public void m_213991_(ServerLevel p_222848_, GameEventListener p_222849_, BlockPos p_222850_, GameEvent p_222851_, @Nullable Entity p_222852_, @Nullable Entity p_222853_, float p_222854_) {
        this.m_222841_(p_222848_, SculkShriekerBlockEntity.m_222861_(p_222853_ != null ? p_222853_ : p_222852_));
    }

    public void m_222841_(ServerLevel p_222842_, @Nullable ServerPlayer p_222843_) {
        if (p_222843_ == null) {
            return;
        }
        BlockState $$2 = this.m_58900_();
        if ($$2.m_61143_(SculkShriekerBlock.f_222152_).booleanValue()) {
            return;
        }
        this.f_222831_ = 0;
        if (this.m_222872_(p_222842_) && !this.m_222874_(p_222842_, p_222843_)) {
            return;
        }
        this.m_222844_(p_222842_, p_222843_);
    }

    private boolean m_222874_(ServerLevel p_222875_, ServerPlayer p_222876_) {
        OptionalInt $$2 = WardenSpawnTracker.m_219577_(p_222875_, this.m_58899_(), p_222876_);
        $$2.ifPresent(p_222838_ -> {
            this.f_222831_ = p_222838_;
        });
        return $$2.isPresent();
    }

    private void m_222844_(ServerLevel p_222845_, @Nullable Entity p_222846_) {
        BlockPos $$2 = this.m_58899_();
        BlockState $$3 = this.m_58900_();
        p_222845_.m_7731_($$2, (BlockState)$$3.m_61124_(SculkShriekerBlock.f_222152_, true), 2);
        p_222845_.m_186460_($$2, $$3.m_60734_(), 90);
        p_222845_.m_46796_(3007, $$2, 0);
        p_222845_.m_220407_(GameEvent.f_223701_, $$2, GameEvent.Context.m_223717_(p_222846_));
    }

    private boolean m_222872_(ServerLevel p_222873_) {
        return this.m_58900_().m_61143_(SculkShriekerBlock.f_222154_) != false && p_222873_.m_46791_() != Difficulty.PEACEFUL && p_222873_.m_46469_().m_46207_(GameRules.f_220347_);
    }

    public void m_222839_(ServerLevel p_222840_) {
        if (this.m_222872_(p_222840_) && this.f_222831_ > 0) {
            if (!this.m_222880_(p_222840_)) {
                this.m_222882_();
            }
            Warden.m_219375_(p_222840_, Vec3.m_82512_(this.m_58899_()), null, 40);
        }
    }

    private void m_222882_() {
        SoundEvent $$0 = (SoundEvent)f_222829_.get(this.f_222831_);
        if ($$0 != null) {
            BlockPos $$1 = this.m_58899_();
            int $$2 = $$1.m_123341_() + Mth.m_216287_(this.f_58857_.f_46441_, -10, 10);
            int $$3 = $$1.m_123342_() + Mth.m_216287_(this.f_58857_.f_46441_, -10, 10);
            int $$4 = $$1.m_123343_() + Mth.m_216287_(this.f_58857_.f_46441_, -10, 10);
            this.f_58857_.m_6263_(null, $$2, $$3, $$4, $$0, SoundSource.HOSTILE, 5.0f, 1.0f);
        }
    }

    private boolean m_222880_(ServerLevel p_222881_) {
        if (this.f_222831_ < 4) {
            return false;
        }
        return SpawnUtil.m_216403_(EntityType.f_217015_, MobSpawnType.TRIGGERED, p_222881_, this.m_58899_(), 20, 5, 6, SpawnUtil.Strategy.f_216413_).isPresent();
    }

    @Override
    public void m_214037_() {
        this.m_6596_();
    }
}

