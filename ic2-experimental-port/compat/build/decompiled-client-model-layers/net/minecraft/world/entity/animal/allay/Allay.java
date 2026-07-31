/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.animal.allay;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.allay.AllayAi;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class Allay
extends PathfinderMob
implements InventoryCarrier {
    private static final Logger f_218307_ = LogUtils.getLogger();
    private static final int f_238548_ = 16;
    private static final Vec3i f_218299_ = new Vec3i(1, 1, 1);
    private static final int f_238768_ = 5;
    private static final float f_238696_ = 55.0f;
    private static final float f_238650_ = 15.0f;
    private static final float f_238172_ = 0.5f;
    private static final Ingredient f_238776_ = Ingredient.m_43929_(Items.f_151049_);
    private static final int f_238543_ = 6000;
    private static final int f_238742_ = 3;
    private static final EntityDataAccessor<Boolean> f_238627_ = SynchedEntityData.m_135353_(Allay.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_238802_ = SynchedEntityData.m_135353_(Allay.class, EntityDataSerializers.f_135035_);
    protected static final ImmutableList<SensorType<? extends Sensor<? super Allay>>> f_218297_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26814_, SensorType.f_26810_);
    protected static final ImmutableList<MemoryModuleType<?>> f_218301_ = ImmutableList.of(MemoryModuleType.f_26377_, MemoryModuleType.f_26371_, MemoryModuleType.f_148205_, MemoryModuleType.f_26370_, MemoryModuleType.f_26326_, MemoryModuleType.f_26381_, MemoryModuleType.f_26332_, MemoryModuleType.f_217778_, MemoryModuleType.f_217779_, MemoryModuleType.f_217780_, MemoryModuleType.f_217781_, MemoryModuleType.f_217768_, (Object[])new MemoryModuleType[0]);
    public static final ImmutableList<Float> f_218306_ = ImmutableList.of((Object)Float.valueOf(0.5625f), (Object)Float.valueOf(0.625f), (Object)Float.valueOf(0.75f), (Object)Float.valueOf(0.9375f), (Object)Float.valueOf(1.0f), (Object)Float.valueOf(1.0f), (Object)Float.valueOf(1.125f), (Object)Float.valueOf(1.25f), (Object)Float.valueOf(1.5f), (Object)Float.valueOf(1.875f), (Object)Float.valueOf(2.0f), (Object)Float.valueOf(2.25f), (Object[])new Float[]{Float.valueOf(2.5f), Float.valueOf(3.0f), Float.valueOf(3.75f), Float.valueOf(4.0f)});
    private final DynamicGameEventListener<VibrationListener> f_238685_;
    private final VibrationListener.VibrationListenerConfig f_238787_;
    private final DynamicGameEventListener<JukeboxListener> f_238563_;
    private final SimpleContainer f_218303_ = new SimpleContainer(1);
    @Nullable
    private BlockPos f_238682_;
    private long f_238791_;
    private float f_218304_;
    private float f_218305_;
    private float f_238687_;
    private float f_238541_;
    private float f_238552_;

    public Allay(EntityType<? extends Allay> p_218310_, Level p_218311_) {
        super((EntityType<? extends PathfinderMob>)p_218310_, p_218311_);
        this.f_21342_ = new FlyingMoveControl(this, 20, true);
        this.m_21553_(this.m_21531_());
        EntityPositionSource $$2 = new EntityPositionSource(this, this.m_20192_());
        this.f_238787_ = new AllayVibrationListenerConfig();
        this.f_238685_ = new DynamicGameEventListener<VibrationListener>(new VibrationListener($$2, 16, this.f_238787_, null, 0.0f, 0));
        this.f_238563_ = new DynamicGameEventListener<JukeboxListener>(new JukeboxListener($$2, GameEvent.f_238690_.m_157827_()));
    }

    protected Brain.Provider<Allay> m_5490_() {
        return Brain.m_21923_(f_218301_, f_218297_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_218344_) {
        return AllayAi.m_218419_(this.m_5490_().m_22073_(p_218344_));
    }

    public Brain<Allay> m_6274_() {
        return super.m_6274_();
    }

    public static AttributeSupplier.Builder m_218388_() {
        return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0).m_22268_(Attributes.f_22280_, 0.1f).m_22268_(Attributes.f_22279_, 0.1f).m_22268_(Attributes.f_22281_, 2.0).m_22268_(Attributes.f_22277_, 48.0);
    }

    @Override
    protected PathNavigation m_6037_(Level p_218342_) {
        FlyingPathNavigation $$1 = new FlyingPathNavigation(this, p_218342_);
        $$1.m_26440_(false);
        $$1.m_7008_(true);
        $$1.m_26443_(true);
        return $$1;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_238627_, false);
        this.f_19804_.m_135372_(f_238802_, true);
    }

    @Override
    public void m_7023_(Vec3 p_218382_) {
        if (this.m_6142_() || this.m_6109_()) {
            if (this.m_20069_()) {
                this.m_19920_(0.02f, p_218382_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_(0.8f));
            } else if (this.m_20077_()) {
                this.m_19920_(0.02f, p_218382_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_(0.5));
            } else {
                this.m_19920_(this.m_6113_(), p_218382_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                this.m_20256_(this.m_20184_().m_82490_(0.91f));
            }
        }
        this.m_21043_(this, false);
    }

    @Override
    protected float m_6431_(Pose p_218356_, EntityDimensions p_218357_) {
        return p_218357_.f_20378_ * 0.6f;
    }

    @Override
    public boolean m_142535_(float p_218321_, float p_218322_, DamageSource p_218323_) {
        return false;
    }

    @Override
    public boolean m_6469_(DamageSource p_218339_, float p_218340_) {
        Entity entity = p_218339_.m_7639_();
        if (entity instanceof Player) {
            Player $$2 = (Player)entity;
            Optional<UUID> $$3 = this.m_6274_().m_21952_(MemoryModuleType.f_217778_);
            if ($$3.isPresent() && $$2.m_20148_().equals($$3.get())) {
                return false;
            }
        }
        return super.m_6469_(p_218339_, p_218340_);
    }

    @Override
    protected void m_7355_(BlockPos p_218364_, BlockState p_218365_) {
    }

    @Override
    protected void m_7840_(double p_218316_, boolean p_218317_, BlockState p_218318_, BlockPos p_218319_) {
    }

    @Override
    protected SoundEvent m_7515_() {
        return this.m_21033_(EquipmentSlot.MAINHAND) ? SoundEvents.f_215670_ : SoundEvents.f_215671_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_218369_) {
        return SoundEvents.f_215675_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_215672_;
    }

    @Override
    protected float m_6121_() {
        return 0.4f;
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("allayBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("allayActivityUpdate");
        AllayAi.m_218421_(this);
        this.f_19853_.m_46473_().m_7238_();
        super.m_8024_();
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        if (!this.f_19853_.f_46443_ && this.m_6084_() && this.f_19797_ % 10 == 0) {
            this.m_5634_(1.0f);
        }
        if (this.m_239559_() && this.m_239812_() && this.f_19797_ % 20 == 0) {
            this.m_240177_(false);
            this.f_238682_ = null;
        }
        this.m_218375_();
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (this.f_19853_.f_46443_) {
            this.f_218305_ = this.f_218304_;
            this.f_218304_ = this.m_218389_() ? Mth.m_14036_(this.f_218304_ + 1.0f, 0.0f, 5.0f) : Mth.m_14036_(this.f_218304_ - 1.0f, 0.0f, 5.0f);
            if (this.m_239559_()) {
                this.f_238687_ += 1.0f;
                this.f_238552_ = this.f_238541_;
                this.f_238541_ = this.m_239302_() ? (this.f_238541_ += 1.0f) : (this.f_238541_ -= 1.0f);
                this.f_238541_ = Mth.m_14036_(this.f_238541_, 0.0f, 15.0f);
            } else {
                this.f_238687_ = 0.0f;
                this.f_238541_ = 0.0f;
                this.f_238552_ = 0.0f;
            }
        } else {
            this.f_238685_.m_223616_().m_157898_(this.f_19853_);
        }
    }

    @Override
    public boolean m_21531_() {
        return !this.m_218377_() && this.m_218389_();
    }

    public boolean m_218389_() {
        return !this.m_21120_(InteractionHand.MAIN_HAND).m_41619_();
    }

    @Override
    public boolean m_7066_(ItemStack p_218380_) {
        return false;
    }

    private boolean m_218377_() {
        return this.m_6274_().m_21876_(MemoryModuleType.f_217781_, MemoryStatus.VALUE_PRESENT);
    }

    @Override
    protected InteractionResult m_6071_(Player p_218361_, InteractionHand p_218362_) {
        ItemStack $$2 = p_218361_.m_21120_(p_218362_);
        ItemStack $$3 = this.m_21120_(InteractionHand.MAIN_HAND);
        if (this.m_239559_() && this.m_239735_($$2) && this.m_218324_()) {
            this.m_218376_();
            this.f_19853_.m_7605_(this, (byte)18);
            this.f_19853_.m_6269_(p_218361_, this, SoundEvents.f_144243_, SoundSource.NEUTRAL, 2.0f, 1.0f);
            this.m_239358_(p_218361_, $$2);
            return InteractionResult.SUCCESS;
        }
        if ($$3.m_41619_() && !$$2.m_41619_()) {
            ItemStack $$4 = $$2.m_41777_();
            $$4.m_41764_(1);
            this.m_21008_(InteractionHand.MAIN_HAND, $$4);
            this.m_239358_(p_218361_, $$2);
            this.f_19853_.m_6269_(p_218361_, this, SoundEvents.f_215676_, SoundSource.NEUTRAL, 2.0f, 1.0f);
            this.m_6274_().m_21879_(MemoryModuleType.f_217778_, p_218361_.m_20148_());
            return InteractionResult.SUCCESS;
        }
        if (!$$3.m_41619_() && p_218362_ == InteractionHand.MAIN_HAND && $$2.m_41619_()) {
            this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            this.f_19853_.m_6269_(p_218361_, this, SoundEvents.f_215677_, SoundSource.NEUTRAL, 2.0f, 1.0f);
            this.m_6674_(InteractionHand.MAIN_HAND);
            for (ItemStack $$5 : this.m_35311_().m_19195_()) {
                BehaviorUtils.m_22613_(this, $$5, this.m_20182_());
            }
            this.m_6274_().m_21936_(MemoryModuleType.f_217778_);
            p_218361_.m_36356_($$3);
            return InteractionResult.SUCCESS;
        }
        return super.m_6071_(p_218361_, p_218362_);
    }

    public void m_240101_(BlockPos p_240102_, boolean p_240103_) {
        if (p_240103_) {
            if (!this.m_239559_()) {
                this.f_238682_ = p_240102_;
                this.m_240177_(true);
            }
        } else if (p_240102_.equals(this.f_238682_) || this.f_238682_ == null) {
            this.f_238682_ = null;
            this.m_240177_(false);
        }
    }

    @Override
    public SimpleContainer m_35311_() {
        return this.f_218303_;
    }

    @Override
    protected Vec3i m_213552_() {
        return f_218299_;
    }

    @Override
    public boolean m_7243_(ItemStack p_218387_) {
        ItemStack $$1 = this.m_21120_(InteractionHand.MAIN_HAND);
        return !$$1.m_41619_() && $$1.m_41726_(p_218387_) && this.f_218303_.m_19183_(p_218387_) && this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_);
    }

    @Override
    protected void m_7581_(ItemEntity p_218359_) {
        InventoryCarrier.m_219611_(this, this, p_218359_);
    }

    @Override
    protected void m_8025_() {
        super.m_8025_();
        DebugPackets.m_133695_(this);
    }

    @Override
    public boolean m_142039_() {
        return !this.m_20096_();
    }

    @Override
    public void m_213651_(BiConsumer<DynamicGameEventListener<?>, ServerLevel> p_218348_) {
        Level level = this.f_19853_;
        if (level instanceof ServerLevel) {
            ServerLevel $$1 = (ServerLevel)level;
            p_218348_.accept(this.f_238685_, $$1);
            p_218348_.accept(this.f_238563_, $$1);
        }
    }

    public boolean m_239559_() {
        return this.f_19804_.m_135370_(f_238627_);
    }

    public void m_240177_(boolean p_240178_) {
        if (this.f_19853_.f_46443_) {
            return;
        }
        this.f_19804_.m_135381_(f_238627_, p_240178_);
    }

    private boolean m_239812_() {
        return this.f_238682_ == null || !this.f_238682_.m_203195_(this.m_20182_(), GameEvent.f_238690_.m_157827_()) || !this.f_19853_.m_8055_(this.f_238682_).m_60713_(Blocks.f_50131_);
    }

    public float m_218394_(float p_218395_) {
        return Mth.m_14179_(p_218395_, this.f_218305_, this.f_218304_) / 5.0f;
    }

    public boolean m_239302_() {
        float $$0 = this.f_238687_ % 55.0f;
        return $$0 < 15.0f;
    }

    public float m_240056_(float p_240057_) {
        return Mth.m_14179_(p_240057_, this.f_238552_, this.f_238541_) / 15.0f;
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        this.f_218303_.m_19195_().forEach(this::m_19983_);
        ItemStack $$0 = this.m_6844_(EquipmentSlot.MAINHAND);
        if (!$$0.m_41619_() && !EnchantmentHelper.m_44924_($$0)) {
            this.m_19983_($$0);
            this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
        }
    }

    @Override
    public boolean m_6785_(double p_218384_) {
        return false;
    }

    @Override
    public void m_7380_(CompoundTag p_218367_) {
        super.m_7380_(p_218367_);
        p_218367_.m_128365_("Inventory", this.f_218303_.m_7927_());
        VibrationListener.m_223781_(this.f_238787_).encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_238685_.m_223616_()).resultOrPartial(arg_0 -> ((Logger)f_218307_).error(arg_0)).ifPresent(p_218353_ -> p_218367_.m_128365_("listener", (Tag)p_218353_));
        p_218367_.m_128356_("DuplicationCooldown", this.f_238791_);
        p_218367_.m_128379_("CanDuplicate", this.m_218324_());
    }

    @Override
    public void m_7378_(CompoundTag p_218350_) {
        super.m_7378_(p_218350_);
        this.f_218303_.m_7797_(p_218350_.m_128437_("Inventory", 10));
        if (p_218350_.m_128425_("listener", 10)) {
            VibrationListener.m_223781_(this.f_238787_).parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_218350_.m_128469_("listener"))).resultOrPartial(arg_0 -> ((Logger)f_218307_).error(arg_0)).ifPresent(p_218346_ -> this.f_238685_.m_223628_((VibrationListener)p_218346_, this.f_19853_));
        }
        this.f_238791_ = p_218350_.m_128451_("DuplicationCooldown");
        this.f_19804_.m_135381_(f_238802_, p_218350_.m_128471_("CanDuplicate"));
    }

    @Override
    protected boolean m_213814_() {
        return false;
    }

    @Override
    public Iterable<BlockPos> m_238383_() {
        AABB $$0 = this.m_20191_();
        int $$1 = Mth.m_14107_($$0.f_82288_ - 0.5);
        int $$2 = Mth.m_14107_($$0.f_82291_ + 0.5);
        int $$3 = Mth.m_14107_($$0.f_82290_ - 0.5);
        int $$4 = Mth.m_14107_($$0.f_82293_ + 0.5);
        int $$5 = Mth.m_14107_($$0.f_82289_ - 0.5);
        int $$6 = Mth.m_14107_($$0.f_82292_ + 0.5);
        return BlockPos.m_121976_($$1, $$5, $$3, $$2, $$6, $$4);
    }

    private void m_218375_() {
        if (this.f_238791_ > 0L) {
            --this.f_238791_;
        }
        if (!this.f_19853_.m_5776_() && this.f_238791_ == 0L && !this.m_218324_()) {
            this.f_19804_.m_135381_(f_238802_, true);
        }
    }

    private boolean m_239735_(ItemStack p_239736_) {
        return f_238776_.test(p_239736_);
    }

    private void m_218376_() {
        Allay $$0 = EntityType.f_217014_.m_20615_(this.f_19853_);
        if ($$0 != null) {
            $$0.m_20219_(this.m_20182_());
            $$0.m_21530_();
            $$0.m_239811_();
            this.m_239811_();
            this.f_19853_.m_7967_($$0);
        }
    }

    private void m_239811_() {
        this.f_238791_ = 6000L;
        this.f_19804_.m_135381_(f_238802_, false);
    }

    private boolean m_218324_() {
        return this.f_19804_.m_135370_(f_238802_);
    }

    private void m_239358_(Player p_239359_, ItemStack p_239360_) {
        if (!p_239359_.m_150110_().f_35937_) {
            p_239360_.m_41774_(1);
        }
    }

    @Override
    public Vec3 m_7939_() {
        return new Vec3(0.0, (double)this.m_20192_() * 0.6, (double)this.m_20205_() * 0.1);
    }

    @Override
    public void m_7822_(byte p_239347_) {
        if (p_239347_ == 18) {
            for (int $$1 = 0; $$1 < 3; ++$$1) {
                this.m_240069_();
            }
        } else {
            super.m_7822_(p_239347_);
        }
    }

    private void m_240069_() {
        double $$0 = this.f_19796_.m_188583_() * 0.02;
        double $$1 = this.f_19796_.m_188583_() * 0.02;
        double $$2 = this.f_19796_.m_188583_() * 0.02;
        this.f_19853_.m_7106_(ParticleTypes.f_123750_, this.m_20208_(1.0), this.m_20187_() + 0.5, this.m_20262_(1.0), $$0, $$1, $$2);
    }

    class AllayVibrationListenerConfig
    implements VibrationListener.VibrationListenerConfig {
        AllayVibrationListenerConfig() {
        }

        @Override
        public boolean m_213641_(ServerLevel p_239787_, GameEventListener p_239788_, BlockPos p_239789_, GameEvent p_239790_, GameEvent.Context p_239791_) {
            if (Allay.this.m_9236_() != p_239787_ || Allay.this.m_213877_() || Allay.this.m_21525_()) {
                return false;
            }
            Optional<GlobalPos> $$5 = Allay.this.m_6274_().m_21952_(MemoryModuleType.f_217779_);
            if ($$5.isEmpty()) {
                return true;
            }
            GlobalPos $$6 = $$5.get();
            return $$6.m_122640_().equals(p_239787_.m_46472_()) && $$6.m_122646_().equals(p_239789_);
        }

        @Override
        public void m_213991_(ServerLevel p_239108_, GameEventListener p_239109_, BlockPos p_239110_, GameEvent p_239111_, @Nullable Entity p_239112_, @Nullable Entity p_239113_, float p_239114_) {
            if (p_239111_ == GameEvent.f_223699_) {
                AllayAi.m_218416_(Allay.this, new BlockPos(p_239110_));
            }
        }

        @Override
        public TagKey<GameEvent> m_213929_() {
            return GameEventTags.f_215855_;
        }
    }

    class JukeboxListener
    implements GameEventListener {
        private final PositionSource f_238537_;
        private final int f_238604_;

        public JukeboxListener(PositionSource p_239448_, int p_239449_) {
            this.f_238537_ = p_239448_;
            this.f_238604_ = p_239449_;
        }

        @Override
        public PositionSource m_142460_() {
            return this.f_238537_;
        }

        @Override
        public int m_142078_() {
            return this.f_238604_;
        }

        @Override
        public boolean m_214068_(ServerLevel p_240002_, GameEvent.Message p_240003_) {
            if (p_240003_.m_223740_() == GameEvent.f_238690_) {
                Allay.this.m_240101_(new BlockPos(p_240003_.m_223743_()), true);
                return true;
            }
            if (p_240003_.m_223740_() == GameEvent.f_238649_) {
                Allay.this.m_240101_(new BlockPos(p_240003_.m_223743_()), false);
                return true;
            }
            return false;
        }
    }
}

