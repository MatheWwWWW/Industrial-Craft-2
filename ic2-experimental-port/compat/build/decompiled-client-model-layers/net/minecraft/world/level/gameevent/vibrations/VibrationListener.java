/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.Nullable
 */
package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VibrationListener
implements GameEventListener {
    protected final PositionSource f_157887_;
    protected final int f_157888_;
    protected final VibrationListenerConfig f_157889_;
    @Nullable
    protected ReceivingEvent f_157890_;
    protected float f_157891_;
    protected int f_157892_;

    public static Codec<VibrationListener> m_223781_(VibrationListenerConfig p_223782_) {
        return RecordCodecBuilder.create(p_223785_ -> p_223785_.group((App)PositionSource.f_157868_.fieldOf("source").forGetter(p_223802_ -> p_223802_.f_157887_), (App)ExtraCodecs.f_144628_.fieldOf("range").forGetter(p_223800_ -> p_223800_.f_157888_), (App)ReceivingEvent.f_223803_.optionalFieldOf("event").forGetter(p_223798_ -> Optional.ofNullable(p_223798_.f_157890_)), (App)Codec.floatRange((float)0.0f, (float)Float.MAX_VALUE).fieldOf("event_distance").orElse((Object)Float.valueOf(0.0f)).forGetter(p_223796_ -> Float.valueOf(p_223796_.f_157891_)), (App)ExtraCodecs.f_144628_.fieldOf("event_delay").orElse((Object)0).forGetter(p_223794_ -> p_223794_.f_157892_)).apply((Applicative)p_223785_, (p_223788_, p_223789_, p_223790_, p_223791_, p_223792_) -> new VibrationListener((PositionSource)p_223788_, (int)p_223789_, p_223782_, p_223790_.orElse(null), p_223791_.floatValue(), (int)p_223792_)));
    }

    public VibrationListener(PositionSource p_223760_, int p_223761_, VibrationListenerConfig p_223762_, @Nullable ReceivingEvent p_223763_, float p_223764_, int p_223765_) {
        this.f_157887_ = p_223760_;
        this.f_157888_ = p_223761_;
        this.f_157889_ = p_223762_;
        this.f_157890_ = p_223763_;
        this.f_157891_ = p_223764_;
        this.f_157892_ = p_223765_;
    }

    public void m_157898_(Level p_157899_) {
        if (p_157899_ instanceof ServerLevel) {
            ServerLevel $$1 = (ServerLevel)p_157899_;
            if (this.f_157890_ != null) {
                --this.f_157892_;
                if (this.f_157892_ <= 0) {
                    this.f_157892_ = 0;
                    this.f_157889_.m_213991_($$1, this, new BlockPos(this.f_157890_.f_223806_), this.f_157890_.f_223804_, this.f_157890_.m_223830_($$1).orElse(null), this.f_157890_.m_223845_($$1).orElse(null), this.f_157891_);
                    this.f_157890_ = null;
                }
            }
        }
    }

    @Override
    public PositionSource m_142460_() {
        return this.f_157887_;
    }

    @Override
    public int m_142078_() {
        return this.f_157888_;
    }

    @Override
    public boolean m_214068_(ServerLevel p_223767_, GameEvent.Message p_223768_) {
        GameEvent.Context $$3;
        if (this.f_157890_ != null) {
            return false;
        }
        GameEvent $$2 = p_223768_.m_223740_();
        if (!this.f_157889_.m_223877_($$2, $$3 = p_223768_.m_223744_())) {
            return false;
        }
        Optional<Vec3> $$4 = this.f_157887_.m_142502_(p_223767_);
        if ($$4.isEmpty()) {
            return false;
        }
        Vec3 $$5 = p_223768_.m_223743_();
        Vec3 $$6 = $$4.get();
        if (!this.f_157889_.m_213641_(p_223767_, this, new BlockPos($$5), $$2, $$3)) {
            return false;
        }
        if (VibrationListener.m_223775_(p_223767_, $$5, $$6)) {
            return false;
        }
        this.m_223769_(p_223767_, $$2, $$3, $$5, $$6);
        return true;
    }

    private void m_223769_(ServerLevel p_223770_, GameEvent p_223771_, GameEvent.Context p_223772_, Vec3 p_223773_, Vec3 p_223774_) {
        this.f_157891_ = (float)p_223773_.m_82554_(p_223774_);
        this.f_157890_ = new ReceivingEvent(p_223771_, this.f_157891_, p_223773_, p_223772_.f_223711_());
        this.f_157892_ = Mth.m_14143_(this.f_157891_);
        p_223770_.m_8767_(new VibrationParticleOption(this.f_157887_, this.f_157892_), p_223773_.f_82479_, p_223773_.f_82480_, p_223773_.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
        this.f_157889_.m_214037_();
    }

    private static boolean m_223775_(Level p_223776_, Vec3 p_223777_, Vec3 p_223778_) {
        Vec3 $$3 = new Vec3((double)Mth.m_14107_(p_223777_.f_82479_) + 0.5, (double)Mth.m_14107_(p_223777_.f_82480_) + 0.5, (double)Mth.m_14107_(p_223777_.f_82481_) + 0.5);
        Vec3 $$4 = new Vec3((double)Mth.m_14107_(p_223778_.f_82479_) + 0.5, (double)Mth.m_14107_(p_223778_.f_82480_) + 0.5, (double)Mth.m_14107_(p_223778_.f_82481_) + 0.5);
        for (Direction $$5 : Direction.values()) {
            Vec3 $$6 = $$3.m_231075_($$5, 1.0E-5f);
            if (p_223776_.m_151353_(new ClipBlockStateContext($$6, $$4, p_223780_ -> p_223780_.m_204336_(BlockTags.f_144272_))).m_6662_() == HitResult.Type.BLOCK) continue;
            return false;
        }
        return true;
    }

    public static interface VibrationListenerConfig {
        default public TagKey<GameEvent> m_213929_() {
            return GameEventTags.f_144302_;
        }

        default public boolean m_213734_() {
            return false;
        }

        default public boolean m_223877_(GameEvent p_223878_, GameEvent.Context p_223879_) {
            if (!p_223878_.m_204528_(this.m_213929_())) {
                return false;
            }
            Entity $$2 = p_223879_.f_223711_();
            if ($$2 != null) {
                if ($$2.m_5833_()) {
                    return false;
                }
                if ($$2.m_20161_() && p_223878_.m_204528_(GameEventTags.f_144303_)) {
                    if (this.m_213734_() && $$2 instanceof ServerPlayer) {
                        ServerPlayer $$3 = (ServerPlayer)$$2;
                        CriteriaTriggers.f_215658_.m_222618_($$3);
                    }
                    return false;
                }
                if ($$2.m_213854_()) {
                    return false;
                }
            }
            if (p_223879_.f_223712_() != null) {
                return !p_223879_.f_223712_().m_204336_(BlockTags.f_215836_);
            }
            return true;
        }

        public boolean m_213641_(ServerLevel var1, GameEventListener var2, BlockPos var3, GameEvent var4, GameEvent.Context var5);

        public void m_213991_(ServerLevel var1, GameEventListener var2, BlockPos var3, GameEvent var4, @Nullable Entity var5, @Nullable Entity var6, float var7);

        default public void m_214037_() {
        }
    }

    public record ReceivingEvent(GameEvent f_223804_, float f_223805_, Vec3 f_223806_, @Nullable UUID f_223807_, @Nullable UUID f_223808_, @Nullable Entity f_223809_) {
        public static final Codec<ReceivingEvent> f_223803_ = RecordCodecBuilder.create(p_223835_ -> p_223835_.group((App)Registry.f_175412_.m_194605_().fieldOf("game_event").forGetter(ReceivingEvent::f_223804_), (App)Codec.floatRange((float)0.0f, (float)Float.MAX_VALUE).fieldOf("distance").forGetter(ReceivingEvent::f_223805_), (App)Vec3.f_231074_.fieldOf("pos").forGetter(ReceivingEvent::f_223806_), (App)ExtraCodecs.f_216157_.optionalFieldOf("source").forGetter(p_223850_ -> Optional.ofNullable(p_223850_.f_223807_())), (App)ExtraCodecs.f_216157_.optionalFieldOf("projectile_owner").forGetter(p_223843_ -> Optional.ofNullable(p_223843_.f_223808_()))).apply((Applicative)p_223835_, (p_223837_, p_223838_, p_223839_, p_223840_, p_223841_) -> new ReceivingEvent((GameEvent)p_223837_, p_223838_.floatValue(), (Vec3)p_223839_, p_223840_.orElse(null), p_223841_.orElse(null))));

        public ReceivingEvent(GameEvent p_223817_, float p_223818_, Vec3 p_223819_, @Nullable UUID p_223820_, @Nullable UUID p_223821_) {
            this(p_223817_, p_223818_, p_223819_, p_223820_, p_223821_, null);
        }

        public ReceivingEvent(GameEvent p_223812_, float p_223813_, Vec3 p_223814_, @Nullable Entity p_223815_) {
            this(p_223812_, p_223813_, p_223814_, p_223815_ == null ? null : p_223815_.m_20148_(), ReceivingEvent.m_223832_(p_223815_), p_223815_);
        }

        @Nullable
        private static UUID m_223832_(@Nullable Entity p_223833_) {
            Projectile $$1;
            if (p_223833_ instanceof Projectile && ($$1 = (Projectile)p_223833_).m_37282_() != null) {
                return $$1.m_37282_().m_20148_();
            }
            return null;
        }

        public Optional<Entity> m_223830_(ServerLevel p_223831_) {
            return Optional.ofNullable(this.f_223809_).or(() -> Optional.ofNullable(this.f_223807_).map(p_223831_::m_8791_));
        }

        public Optional<Entity> m_223845_(ServerLevel p_223846_) {
            return this.m_223830_(p_223846_).filter(p_223855_ -> p_223855_ instanceof Projectile).map(p_223848_ -> (Projectile)p_223848_).map(Projectile::m_37282_).or(() -> Optional.ofNullable(this.f_223808_).map(p_223846_::m_8791_));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{ReceivingEvent.class, "gameEvent;distance;pos;uuid;projectileOwnerUuid;entity", "f_223804_", "f_223805_", "f_223806_", "f_223807_", "f_223808_", "f_223809_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ReceivingEvent.class, "gameEvent;distance;pos;uuid;projectileOwnerUuid;entity", "f_223804_", "f_223805_", "f_223806_", "f_223807_", "f_223808_", "f_223809_"}, this);
        }

        @Override
        public final boolean equals(Object p_223861_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ReceivingEvent.class, "gameEvent;distance;pos;uuid;projectileOwnerUuid;entity", "f_223804_", "f_223805_", "f_223806_", "f_223807_", "f_223808_", "f_223809_"}, this, p_223861_);
        }
    }
}

