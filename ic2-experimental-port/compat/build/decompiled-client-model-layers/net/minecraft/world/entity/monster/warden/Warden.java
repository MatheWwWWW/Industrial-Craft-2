/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.jetbrains.annotations.Contract
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.monster.warden;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.warden.AngerManagement;
import net.minecraft.world.entity.monster.warden.WardenAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

public class Warden
extends Monster
implements VibrationListener.VibrationListenerConfig {
    private static final Logger f_219315_ = LogUtils.getLogger();
    private static final int f_219320_ = 16;
    private static final int f_219321_ = 40;
    private static final int f_219322_ = 200;
    private static final int f_219323_ = 500;
    private static final float f_219324_ = 0.3f;
    private static final float f_219325_ = 1.0f;
    private static final float f_219326_ = 1.5f;
    private static final int f_219327_ = 30;
    private static final EntityDataAccessor<Integer> f_219328_ = SynchedEntityData.m_135353_(Warden.class, EntityDataSerializers.f_135028_);
    private static final int f_219329_ = 200;
    private static final int f_219330_ = 260;
    private static final int f_219331_ = 20;
    private static final int f_219332_ = 120;
    private static final int f_219333_ = 20;
    private static final int f_219334_ = 35;
    private static final int f_219335_ = 10;
    private static final int f_219336_ = 20;
    private static final int f_219337_ = 100;
    private static final int f_219338_ = 20;
    private static final int f_219339_ = 30;
    private static final float f_219340_ = 4.5f;
    private static final float f_219341_ = 0.7f;
    private static final int f_219342_ = 30;
    private int f_219343_;
    private int f_219344_;
    private int f_219345_;
    private int f_219317_;
    public AnimationState f_219312_ = new AnimationState();
    public AnimationState f_219316_ = new AnimationState();
    public AnimationState f_219346_ = new AnimationState();
    public AnimationState f_219347_ = new AnimationState();
    public AnimationState f_219313_ = new AnimationState();
    public AnimationState f_219314_ = new AnimationState();
    private final DynamicGameEventListener<VibrationListener> f_219318_;
    private AngerManagement f_219319_ = new AngerManagement(this::m_219385_, Collections.emptyList());

    public Warden(EntityType<? extends Monster> p_219350_, Level p_219351_) {
        super(p_219350_, p_219351_);
        this.f_219318_ = new DynamicGameEventListener<VibrationListener>(new VibrationListener(new EntityPositionSource(this, this.m_20192_()), 16, this, null, 0.0f, 0));
        this.f_21364_ = 5;
        this.m_21573_().m_7008_(true);
        this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_OTHER, 8.0f);
        this.m_21441_(BlockPathTypes.POWDER_SNOW, 8.0f);
        this.m_21441_(BlockPathTypes.LAVA, 8.0f);
        this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0f);
        this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0f);
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this, this.m_217003_(Pose.EMERGING) ? 1 : 0);
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_219420_) {
        super.m_141965_(p_219420_);
        if (p_219420_.m_131509_() == 1) {
            this.m_20124_(Pose.EMERGING);
        }
    }

    @Override
    public boolean m_6914_(LevelReader p_219398_) {
        return super.m_6914_(p_219398_) && p_219398_.m_45756_(this, this.m_6095_().m_20680_().m_20393_(this.m_20182_()));
    }

    @Override
    public float m_5610_(BlockPos p_219410_, LevelReader p_219411_) {
        return 0.0f;
    }

    @Override
    public boolean m_6673_(DamageSource p_219427_) {
        if (this.m_219450_() && !p_219427_.m_19378_()) {
            return true;
        }
        return super.m_6673_(p_219427_);
    }

    private boolean m_219450_() {
        return this.m_217003_(Pose.DIGGING) || this.m_217003_(Pose.EMERGING);
    }

    @Override
    protected boolean m_7341_(Entity p_219462_) {
        return false;
    }

    @Override
    public boolean m_213824_() {
        return true;
    }

    @Override
    protected float m_6059_() {
        return this.f_19788_ + 0.55f;
    }

    public static AttributeSupplier.Builder m_219463_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 500.0).m_22268_(Attributes.f_22279_, 0.3f).m_22268_(Attributes.f_22278_, 1.0).m_22268_(Attributes.f_22282_, 1.5).m_22268_(Attributes.f_22281_, 30.0);
    }

    @Override
    public boolean m_213854_() {
        return true;
    }

    @Override
    protected float m_6121_() {
        return 4.0f;
    }

    @Override
    @Nullable
    protected SoundEvent m_7515_() {
        if (this.m_217003_(Pose.ROARING) || this.m_219450_()) {
            return null;
        }
        return this.m_219446_().m_219234_();
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_219440_) {
        return SoundEvents.f_215763_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_215779_;
    }

    @Override
    protected void m_7355_(BlockPos p_219431_, BlockState p_219432_) {
        this.m_5496_(SoundEvents.f_215773_, 10.0f, 1.0f);
    }

    @Override
    public boolean m_7327_(Entity p_219472_) {
        this.f_19853_.m_7605_(this, (byte)4);
        this.m_5496_(SoundEvents.f_215778_, 10.0f, this.m_6100_());
        SonicBoom.m_217698_(this, 40);
        return super.m_7327_(p_219472_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_219328_, 0);
    }

    public int m_219464_() {
        return this.f_19804_.m_135370_(f_219328_);
    }

    private void m_219451_() {
        this.f_19804_.m_135381_(f_219328_, this.m_219454_());
    }

    @Override
    public void m_8119_() {
        Level level = this.f_19853_;
        if (level instanceof ServerLevel) {
            ServerLevel $$0 = (ServerLevel)level;
            this.f_219318_.m_223616_().m_157898_($$0);
            if (this.m_21532_() || this.m_8023_()) {
                WardenAi.m_219505_(this);
            }
        }
        super.m_8119_();
        if (this.f_19853_.m_5776_()) {
            if (this.f_19797_ % this.m_219452_() == 0) {
                this.f_219345_ = 10;
                if (!this.m_20067_()) {
                    this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_215762_, this.m_5720_(), 5.0f, this.m_6100_(), false);
                }
            }
            this.f_219344_ = this.f_219343_;
            if (this.f_219343_ > 0) {
                --this.f_219343_;
            }
            this.f_219317_ = this.f_219345_;
            if (this.f_219345_ > 0) {
                --this.f_219345_;
            }
            switch (this.m_20089_()) {
                case EMERGING: {
                    this.m_219383_(this.f_219346_);
                    break;
                }
                case DIGGING: {
                    this.m_219383_(this.f_219347_);
                }
            }
        }
    }

    @Override
    protected void m_8024_() {
        ServerLevel $$0 = (ServerLevel)this.f_19853_;
        $$0.m_46473_().m_6180_("wardenBrain");
        this.m_6274_().m_21865_($$0, this);
        this.f_19853_.m_46473_().m_7238_();
        super.m_8024_();
        if ((this.f_19797_ + this.m_19879_()) % 120 == 0) {
            Warden.m_219375_($$0, this.m_20182_(), this, 20);
        }
        if (this.f_19797_ % 20 == 0) {
            this.f_219319_.m_219263_($$0, this::m_219385_);
            this.m_219451_();
        }
        WardenAi.m_219512_(this);
    }

    @Override
    public void m_7822_(byte p_219360_) {
        if (p_219360_ == 4) {
            this.f_219312_.m_216973_();
            this.f_219313_.m_216977_(this.f_19797_);
        } else if (p_219360_ == 61) {
            this.f_219343_ = 10;
        } else if (p_219360_ == 62) {
            this.f_219314_.m_216977_(this.f_19797_);
        } else {
            super.m_7822_(p_219360_);
        }
    }

    private int m_219452_() {
        float $$0 = (float)this.m_219464_() / (float)AngerLevel.ANGRY.m_219226_();
        return 40 - Mth.m_14143_(Mth.m_14036_($$0, 0.0f, 1.0f) * 30.0f);
    }

    public float m_219467_(float p_219468_) {
        return Mth.m_14179_(p_219468_, this.f_219344_, this.f_219343_) / 10.0f;
    }

    public float m_219469_(float p_219470_) {
        return Mth.m_14179_(p_219470_, this.f_219317_, this.f_219345_) / 10.0f;
    }

    private void m_219383_(AnimationState p_219384_) {
        if ((float)p_219384_.m_216981_() < 4500.0f) {
            RandomSource $$1 = this.m_217043_();
            BlockState $$2 = this.m_20075_();
            if ($$2.m_60799_() != RenderShape.INVISIBLE) {
                for (int $$3 = 0; $$3 < 30; ++$$3) {
                    double $$4 = this.m_20185_() + (double)Mth.m_216283_($$1, -0.7f, 0.7f);
                    double $$5 = this.m_20186_();
                    double $$6 = this.m_20189_() + (double)Mth.m_216283_($$1, -0.7f, 0.7f);
                    this.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, $$2), $$4, $$5, $$6, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_219422_) {
        if (f_19806_.equals(p_219422_)) {
            switch (this.m_20089_()) {
                case ROARING: {
                    this.f_219312_.m_216977_(this.f_19797_);
                    break;
                }
                case SNIFFING: {
                    this.f_219316_.m_216977_(this.f_19797_);
                    break;
                }
                case EMERGING: {
                    this.f_219346_.m_216977_(this.f_19797_);
                    break;
                }
                case DIGGING: {
                    this.f_219347_.m_216977_(this.f_19797_);
                }
            }
        }
        super.m_7350_(p_219422_);
    }

    @Override
    public boolean m_6128_() {
        return this.m_219450_();
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_219406_) {
        return WardenAi.m_219520_(this, p_219406_);
    }

    public Brain<Warden> m_6274_() {
        return super.m_6274_();
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public void m_213651_(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_219413_) {
        Level level = this.f_19853_;
        if (level instanceof ServerLevel) {
            ServerLevel $$1 = (ServerLevel)level;
            p_219413_.accept(this.f_219318_, $$1);
        }
    }

    @Override
    public TagKey<GameEvent> m_213929_() {
        return GameEventTags.f_215853_;
    }

    @Override
    public boolean m_213734_() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Contract(value="null->false")
    public boolean m_219385_(@Nullable Entity p_219386_) {
        if (!(p_219386_ instanceof LivingEntity)) return false;
        LivingEntity $$1 = (LivingEntity)p_219386_;
        if (this.f_19853_ != p_219386_.f_19853_) return false;
        if (!EntitySelector.f_20406_.test(p_219386_)) return false;
        if (this.m_7307_(p_219386_)) return false;
        if ($$1.m_6095_() == EntityType.f_20529_) return false;
        if ($$1.m_6095_() == EntityType.f_217015_) return false;
        if ($$1.m_20147_()) return false;
        if ($$1.m_21224_()) return false;
        if (!this.f_19853_.m_6857_().m_61935_($$1.m_20191_())) return false;
        return true;
    }

    public static void m_219375_(ServerLevel p_219376_, Vec3 p_219377_, @Nullable Entity p_219378_, int p_219379_) {
        MobEffectInstance $$4 = new MobEffectInstance(MobEffects.f_216964_, 260, 0, false, false);
        MobEffectUtil.m_216946_(p_219376_, p_219378_, p_219377_, p_219379_, $$4, 200);
    }

    @Override
    public void m_7380_(CompoundTag p_219434_) {
        super.m_7380_(p_219434_);
        AngerManagement.m_219277_(this::m_219385_).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_219319_).resultOrPartial(arg_0 -> ((Logger)f_219315_).error(arg_0)).ifPresent(p_219437_ -> p_219434_.m_128365_("anger", (Tag)p_219437_));
        VibrationListener.m_223781_(this).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_219318_.m_223616_()).resultOrPartial(arg_0 -> ((Logger)f_219315_).error(arg_0)).ifPresent(p_219418_ -> p_219434_.m_128365_("listener", (Tag)p_219418_));
    }

    @Override
    public void m_7378_(CompoundTag p_219415_) {
        super.m_7378_(p_219415_);
        if (p_219415_.m_128441_("anger")) {
            AngerManagement.m_219277_(this::m_219385_).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_219415_.m_128423_("anger"))).resultOrPartial(arg_0 -> ((Logger)f_219315_).error(arg_0)).ifPresent(p_219394_ -> {
                this.f_219319_ = p_219394_;
            });
            this.m_219451_();
        }
        if (p_219415_.m_128425_("listener", 10)) {
            VibrationListener.m_223781_(this).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_219415_.m_128469_("listener"))).resultOrPartial(arg_0 -> ((Logger)f_219315_).error(arg_0)).ifPresent(p_219408_ -> this.f_219318_.m_223628_((VibrationListener)p_219408_, this.f_19853_));
        }
    }

    private void m_219453_() {
        if (!this.m_217003_(Pose.ROARING)) {
            this.m_5496_(this.m_219446_().m_219235_(), 10.0f, this.m_6100_());
        }
    }

    public AngerLevel m_219446_() {
        return AngerLevel.m_219227_(this.m_219454_());
    }

    private int m_219454_() {
        return this.f_219319_.m_219286_(this.m_5448_());
    }

    public void m_219428_(Entity p_219429_) {
        this.f_219319_.m_219266_(p_219429_);
    }

    public void m_219441_(@Nullable Entity p_219442_) {
        this.m_219387_(p_219442_, 35, true);
    }

    @VisibleForTesting
    public void m_219387_(@Nullable Entity p_219388_, int p_219389_, boolean p_219390_) {
        if (!this.m_21525_() && this.m_219385_(p_219388_)) {
            WardenAi.m_219505_(this);
            boolean $$3 = !(this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null) instanceof Player);
            int $$4 = this.f_219319_.m_219268_(p_219388_, p_219389_);
            if (p_219388_ instanceof Player && $$3 && AngerLevel.m_219227_($$4).m_219236_()) {
                this.m_6274_().m_21936_(MemoryModuleType.f_26372_);
            }
            if (p_219390_) {
                this.m_219453_();
            }
        }
    }

    public Optional<LivingEntity> m_219448_() {
        if (this.m_219446_().m_219236_()) {
            return this.f_219319_.m_219256_();
        }
        return Optional.empty();
    }

    @Override
    @Nullable
    public LivingEntity m_5448_() {
        return this.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null);
    }

    @Override
    public boolean m_6785_(double p_219457_) {
        return false;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_219400_, DifficultyInstance p_219401_, MobSpawnType p_219402_, @Nullable SpawnGroupData p_219403_, @Nullable CompoundTag p_219404_) {
        this.m_6274_().m_21882_(MemoryModuleType.f_217770_, Unit.INSTANCE, 1200L);
        if (p_219402_ == MobSpawnType.TRIGGERED) {
            this.m_20124_(Pose.EMERGING);
            this.m_6274_().m_21882_(MemoryModuleType.f_217786_, Unit.INSTANCE, WardenAi.f_219490_);
            this.m_5496_(SoundEvents.f_215775_, 5.0f, 1.0f);
        }
        return super.m_6518_(p_219400_, p_219401_, p_219402_, p_219403_, p_219404_);
    }

    @Override
    public boolean m_6469_(DamageSource p_219381_, float p_219382_) {
        boolean $$2 = super.m_6469_(p_219381_, p_219382_);
        if (!(this.f_19853_.f_46443_ || this.m_21525_() || this.m_219450_())) {
            Entity $$3 = p_219381_.m_7639_();
            this.m_219387_($$3, AngerLevel.ANGRY.m_219226_() + 20, false);
            if (this.f_20939_.m_21952_(MemoryModuleType.f_26372_).isEmpty() && $$3 instanceof LivingEntity) {
                LivingEntity $$4 = (LivingEntity)$$3;
                if (!(p_219381_ instanceof IndirectEntityDamageSource) || this.m_19950_($$4, 5.0)) {
                    this.m_219459_($$4);
                }
            }
        }
        return $$2;
    }

    public void m_219459_(LivingEntity p_219460_) {
        this.m_6274_().m_21936_(MemoryModuleType.f_217782_);
        StartAttacking.m_24213_(this, p_219460_);
        SonicBoom.m_217698_(this, 200);
    }

    @Override
    public EntityDimensions m_6972_(Pose p_219392_) {
        EntityDimensions $$1 = super.m_6972_(p_219392_);
        if (this.m_219450_()) {
            return EntityDimensions.m_20398_($$1.f_20377_, 1.0f);
        }
        return $$1;
    }

    @Override
    public boolean m_6094_() {
        return !this.m_219450_() && super.m_6094_();
    }

    @Override
    protected void m_7324_(Entity p_219353_) {
        if (!this.m_21525_() && !this.m_6274_().m_21874_(MemoryModuleType.f_217773_)) {
            this.m_6274_().m_21882_(MemoryModuleType.f_217773_, Unit.INSTANCE, 20L);
            this.m_219441_(p_219353_);
            WardenAi.m_219523_(this, p_219353_.m_20183_());
        }
        super.m_7324_(p_219353_);
    }

    @Override
    public boolean m_213641_(ServerLevel p_219370_, GameEventListener p_219371_, BlockPos p_219372_, GameEvent p_219373_, GameEvent.Context p_219374_) {
        LivingEntity $$5;
        if (this.m_21525_() || this.m_21224_() || this.m_6274_().m_21874_(MemoryModuleType.f_217774_) || this.m_219450_() || !p_219370_.m_6857_().m_61937_(p_219372_) || this.m_213877_() || this.f_19853_ != p_219370_) {
            return false;
        }
        Entity entity = p_219374_.f_223711_();
        return !(entity instanceof LivingEntity) || this.m_219385_($$5 = (LivingEntity)entity);
    }

    @Override
    public void m_213991_(ServerLevel p_219362_, GameEventListener p_219363_, BlockPos p_219364_, GameEvent p_219365_, @Nullable Entity p_219366_, @Nullable Entity p_219367_, float p_219368_) {
        if (this.m_21224_()) {
            return;
        }
        this.f_20939_.m_21882_(MemoryModuleType.f_217774_, Unit.INSTANCE, 40L);
        p_219362_.m_7605_(this, (byte)61);
        this.m_5496_(SoundEvents.f_215774_, 5.0f, this.m_6100_());
        BlockPos $$7 = p_219364_;
        if (p_219367_ != null) {
            if (this.m_19950_(p_219367_, 30.0)) {
                if (this.m_6274_().m_21874_(MemoryModuleType.f_217784_)) {
                    if (this.m_219385_(p_219367_)) {
                        $$7 = p_219367_.m_20183_();
                    }
                    this.m_219441_(p_219367_);
                } else {
                    this.m_219387_(p_219367_, 10, true);
                }
            }
            this.m_6274_().m_21882_(MemoryModuleType.f_217784_, Unit.INSTANCE, 100L);
        } else {
            this.m_219441_(p_219366_);
        }
        if (!this.m_219446_().m_219236_()) {
            Optional<LivingEntity> $$8 = this.f_219319_.m_219256_();
            if (p_219367_ != null || $$8.isEmpty() || $$8.get() == p_219366_) {
                WardenAi.m_219523_(this, $$7);
            }
        }
    }

    @VisibleForTesting
    public AngerManagement m_219449_() {
        return this.f_219319_;
    }

    @Override
    protected PathNavigation m_6037_(Level p_219396_) {
        return new GroundPathNavigation(this, p_219396_){

            @Override
            protected PathFinder m_5532_(int p_219479_) {
                this.f_26508_ = new WalkNodeEvaluator();
                this.f_26508_.m_77351_(true);
                return new PathFinder(this.f_26508_, p_219479_){

                    @Override
                    protected float m_214208_(Node p_219486_, Node p_219487_) {
                        return p_219486_.m_230613_(p_219487_);
                    }
                };
            }
        };
    }
}

