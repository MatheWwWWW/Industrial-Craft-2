/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import org.slf4j.Logger;

public abstract class LivingEntity
extends Entity {
    private static final Logger f_201943_ = LogUtils.getLogger();
    private static final UUID f_20929_ = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
    private static final UUID f_20959_ = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
    private static final UUID f_147184_ = UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce");
    private static final AttributeModifier f_20960_ = new AttributeModifier(f_20929_, "Sprinting speed boost", (double)0.3f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    public static final int f_147166_ = 2;
    public static final int f_147167_ = 4;
    public static final int f_147168_ = 98;
    public static final int f_147169_ = 100;
    public static final int f_147170_ = 6;
    public static final int f_147171_ = 100;
    private static final int f_147178_ = 40;
    public static final double f_147172_ = 0.003;
    public static final double f_147173_ = 0.08;
    public static final int f_147174_ = 20;
    private static final int f_147179_ = 7;
    private static final int f_147180_ = 10;
    private static final int f_147181_ = 2;
    public static final int f_147175_ = 4;
    private static final double f_147182_ = 128.0;
    protected static final int f_147176_ = 1;
    protected static final int f_147177_ = 2;
    protected static final int f_147163_ = 4;
    protected static final EntityDataAccessor<Byte> f_20909_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135027_);
    private static final EntityDataAccessor<Float> f_20961_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> f_20962_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Boolean> f_20963_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Integer> f_20940_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Integer> f_20941_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
    private static final EntityDataAccessor<Optional<BlockPos>> f_20942_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135039_);
    protected static final float f_147164_ = 1.74f;
    protected static final EntityDimensions f_20910_ = EntityDimensions.m_20398_(0.2f, 0.2f);
    public static final float f_147165_ = 0.5f;
    private final AttributeMap f_20943_;
    private final CombatTracker f_20944_ = new CombatTracker(this);
    private final Map<MobEffect, MobEffectInstance> f_20945_ = Maps.newHashMap();
    private final NonNullList<ItemStack> f_20946_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
    private final NonNullList<ItemStack> f_20947_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
    public boolean f_20911_;
    private boolean f_147183_ = false;
    public InteractionHand f_20912_;
    public int f_20913_;
    public int f_20914_;
    public int f_20915_;
    public int f_20916_;
    public int f_20917_;
    public float f_20918_;
    public int f_20919_;
    public float f_20920_;
    public float f_20921_;
    protected int f_20922_;
    public float f_20923_;
    public float f_20924_;
    public float f_20925_;
    public final int f_20926_ = 20;
    public final float f_20927_;
    public final float f_20928_;
    public float f_20883_;
    public float f_20884_;
    public float f_20885_;
    public float f_20886_;
    public float f_20887_ = 0.02f;
    @Nullable
    protected Player f_20888_;
    protected int f_20889_;
    protected boolean f_20890_;
    protected int f_20891_;
    protected float f_20892_;
    protected float f_20893_;
    protected float f_20894_;
    protected float f_20895_;
    protected float f_20896_;
    protected int f_20897_;
    protected float f_20898_;
    protected boolean f_20899_;
    public float f_20900_;
    public float f_20901_;
    public float f_20902_;
    protected int f_20903_;
    protected double f_20904_;
    protected double f_20905_;
    protected double f_20906_;
    protected double f_20907_;
    protected double f_20908_;
    protected double f_20933_;
    protected int f_20934_;
    private boolean f_20948_ = true;
    @Nullable
    private LivingEntity f_20949_;
    private int f_20950_;
    private LivingEntity f_20951_;
    private int f_20952_;
    private float f_20953_;
    private int f_20954_;
    private float f_20955_;
    protected ItemStack f_20935_ = ItemStack.f_41583_;
    protected int f_20936_;
    protected int f_20937_;
    private BlockPos f_20956_;
    private Optional<BlockPos> f_20957_ = Optional.empty();
    @Nullable
    private DamageSource f_20958_;
    private long f_20930_;
    protected int f_20938_;
    private float f_20931_;
    private float f_20932_;
    protected Brain<?> f_20939_;
    private boolean f_217034_;

    protected LivingEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
        this.f_20943_ = new AttributeMap(DefaultAttributes.m_22297_(p_20966_));
        this.m_21153_(this.m_21233_());
        this.f_19850_ = true;
        this.f_20928_ = (float)((Math.random() + 1.0) * (double)0.01f);
        this.m_20090_();
        this.f_20927_ = (float)Math.random() * 12398.0f;
        this.m_146922_((float)(Math.random() * 6.2831854820251465));
        this.f_20885_ = this.m_146908_();
        this.f_19793_ = 0.6f;
        NbtOps $$2 = NbtOps.f_128958_;
        this.f_20939_ = this.m_8075_(new Dynamic((DynamicOps)$$2, (Object)((Tag)$$2.createMap((Map)ImmutableMap.of((Object)$$2.createString("memories"), (Object)((Tag)$$2.emptyMap()))))));
    }

    public Brain<?> m_6274_() {
        return this.f_20939_;
    }

    protected Brain.Provider<?> m_5490_() {
        return Brain.m_21923_(ImmutableList.of(), ImmutableList.of());
    }

    protected Brain<?> m_8075_(Dynamic<?> p_21069_) {
        return this.m_5490_().m_22073_(p_21069_);
    }

    @Override
    public void m_6074_() {
        this.m_6469_(DamageSource.f_19317_, Float.MAX_VALUE);
    }

    public boolean m_6549_(EntityType<?> p_21032_) {
        return true;
    }

    @Override
    protected void m_8097_() {
        this.f_19804_.m_135372_(f_20909_, (byte)0);
        this.f_19804_.m_135372_(f_20962_, 0);
        this.f_19804_.m_135372_(f_20963_, false);
        this.f_19804_.m_135372_(f_20940_, 0);
        this.f_19804_.m_135372_(f_20941_, 0);
        this.f_19804_.m_135372_(f_20961_, Float.valueOf(1.0f));
        this.f_19804_.m_135372_(f_20942_, Optional.empty());
    }

    public static AttributeSupplier.Builder m_21183_() {
        return AttributeSupplier.m_22244_().m_22266_(Attributes.f_22276_).m_22266_(Attributes.f_22278_).m_22266_(Attributes.f_22279_).m_22266_(Attributes.f_22284_).m_22266_(Attributes.f_22285_);
    }

    @Override
    protected void m_7840_(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
        if (!this.m_20069_()) {
            this.m_20074_();
        }
        if (!this.f_19853_.f_46443_ && p_20991_ && this.f_19789_ > 0.0f) {
            this.m_21185_();
            this.m_21186_();
        }
        if (!this.f_19853_.f_46443_ && this.f_19789_ > 3.0f && p_20991_) {
            float $$4 = Mth.m_14167_(this.f_19789_ - 3.0f);
            if (!p_20992_.m_60795_()) {
                double $$5 = Math.min((double)(0.2f + $$4 / 15.0f), 2.5);
                int $$6 = (int)(150.0 * $$5);
                ((ServerLevel)this.f_19853_).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, p_20992_), this.m_20185_(), this.m_20186_(), this.m_20189_(), $$6, 0.0, 0.0, 0.0, 0.15f);
            }
        }
        super.m_7840_(p_20990_, p_20991_, p_20992_, p_20993_);
    }

    public boolean m_6040_() {
        return this.m_6336_() == MobType.f_21641_;
    }

    public float m_20998_(float p_20999_) {
        return Mth.m_14179_(p_20999_, this.f_20932_, this.f_20931_);
    }

    @Override
    public void m_6075_() {
        this.f_20920_ = this.f_20921_;
        if (this.f_19803_) {
            this.m_21257_().ifPresent(this::m_21080_);
        }
        if (this.m_6039_()) {
            this.m_21184_();
        }
        super.m_6075_();
        this.f_19853_.m_46473_().m_6180_("livingEntityBaseTick");
        if (this.m_5825_() || this.f_19853_.f_46443_) {
            this.m_20095_();
        }
        if (this.m_6084_()) {
            BlockPos $$9;
            double $$2;
            double $$1;
            boolean $$0 = this instanceof Player;
            if (this.m_5830_()) {
                this.m_6469_(DamageSource.f_19310_, 1.0f);
            } else if ($$0 && !this.f_19853_.m_6857_().m_61935_(this.m_20191_()) && ($$1 = this.f_19853_.m_6857_().m_61925_(this) + this.f_19853_.m_6857_().m_61964_()) < 0.0 && ($$2 = this.f_19853_.m_6857_().m_61965_()) > 0.0) {
                this.m_6469_(DamageSource.f_19310_, Math.max(1, Mth.m_14107_(-$$1 * $$2)));
            }
            if (this.m_204029_(FluidTags.f_13131_) && !this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20188_(), this.m_20189_())).m_60713_(Blocks.f_50628_)) {
                boolean $$3;
                boolean bl = $$3 = !this.m_6040_() && !MobEffectUtil.m_19588_(this) && (!$$0 || !((Player)this).m_150110_().f_35934_);
                if ($$3) {
                    this.m_20301_(this.m_7302_(this.m_20146_()));
                    if (this.m_20146_() == -20) {
                        this.m_20301_(0);
                        Vec3 $$4 = this.m_20184_();
                        for (int $$5 = 0; $$5 < 8; ++$$5) {
                            double $$6 = this.f_19796_.m_188500_() - this.f_19796_.m_188500_();
                            double $$7 = this.f_19796_.m_188500_() - this.f_19796_.m_188500_();
                            double $$8 = this.f_19796_.m_188500_() - this.f_19796_.m_188500_();
                            this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20185_() + $$6, this.m_20186_() + $$7, this.m_20189_() + $$8, $$4.f_82479_, $$4.f_82480_, $$4.f_82481_);
                        }
                        this.m_6469_(DamageSource.f_19312_, 2.0f);
                    }
                }
                if (!this.f_19853_.f_46443_ && this.m_20159_() && this.m_20202_() != null && !this.m_20202_().m_6146_()) {
                    this.m_8127_();
                }
            } else if (this.m_20146_() < this.m_6062_()) {
                this.m_20301_(this.m_7305_(this.m_20146_()));
            }
            if (!this.f_19853_.f_46443_ && !Objects.equal((Object)this.f_20956_, (Object)($$9 = this.m_20183_()))) {
                this.f_20956_ = $$9;
                this.m_5806_($$9);
            }
        }
        if (this.m_6084_() && (this.m_20071_() || this.f_146808_)) {
            if (!this.f_19853_.f_46443_ && this.f_146810_) {
                this.m_146873_();
            }
            this.m_20095_();
        }
        if (this.f_20916_ > 0) {
            --this.f_20916_;
        }
        if (this.f_19802_ > 0 && !(this instanceof ServerPlayer)) {
            --this.f_19802_;
        }
        if (this.m_21224_() && this.f_19853_.m_183599_(this)) {
            this.m_6153_();
        }
        if (this.f_20889_ > 0) {
            --this.f_20889_;
        } else {
            this.f_20888_ = null;
        }
        if (this.f_20951_ != null && !this.f_20951_.m_6084_()) {
            this.f_20951_ = null;
        }
        if (this.f_20949_ != null) {
            if (!this.f_20949_.m_6084_()) {
                this.m_6703_(null);
            } else if (this.f_19797_ - this.f_20950_ > 100) {
                this.m_6703_(null);
            }
        }
        this.m_21217_();
        this.f_20895_ = this.f_20894_;
        this.f_20884_ = this.f_20883_;
        this.f_20886_ = this.f_20885_;
        this.f_19859_ = this.m_146908_();
        this.f_19860_ = this.m_146909_();
        this.f_19853_.m_46473_().m_7238_();
    }

    public boolean m_6039_() {
        return this.f_19797_ % 5 == 0 && this.m_20184_().f_82479_ != 0.0 && this.m_20184_().f_82481_ != 0.0 && !this.m_5833_() && EnchantmentHelper.m_44942_(this) && this.m_6046_();
    }

    protected void m_21184_() {
        Vec3 $$0 = this.m_20184_();
        this.f_19853_.m_7106_(ParticleTypes.f_123746_, this.m_20185_() + (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_(), this.m_20186_() + 0.1, this.m_20189_() + (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_(), $$0.f_82479_ * -0.2, 0.1, $$0.f_82481_ * -0.2);
        float $$1 = this.f_19796_.m_188501_() * 0.4f + this.f_19796_.m_188501_() > 0.9f ? 0.6f : 0.0f;
        this.m_5496_(SoundEvents.f_12404_, $$1, 0.6f + this.f_19796_.m_188501_() * 0.4f);
    }

    protected boolean m_6046_() {
        return this.f_19853_.m_8055_(this.m_20099_()).m_204336_(BlockTags.f_13080_);
    }

    @Override
    protected float m_6041_() {
        if (this.m_6046_() && EnchantmentHelper.m_44836_(Enchantments.f_44976_, this) > 0) {
            return 1.0f;
        }
        return super.m_6041_();
    }

    protected boolean m_6757_(BlockState p_21140_) {
        return !p_21140_.m_60795_() || this.m_21255_();
    }

    protected void m_21185_() {
        AttributeInstance $$0 = this.m_21051_(Attributes.f_22279_);
        if ($$0 == null) {
            return;
        }
        if ($$0.m_22111_(f_20959_) != null) {
            $$0.m_22120_(f_20959_);
        }
    }

    protected void m_21186_() {
        int $$0;
        if (!this.m_217002_().m_60795_() && ($$0 = EnchantmentHelper.m_44836_(Enchantments.f_44976_, this)) > 0 && this.m_6046_()) {
            AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
            if ($$1 == null) {
                return;
            }
            $$1.m_22118_(new AttributeModifier(f_20959_, "Soul speed boost", (double)(0.03f * (1.0f + (float)$$0 * 0.35f)), AttributeModifier.Operation.ADDITION));
            if (this.m_217043_().m_188501_() < 0.04f) {
                ItemStack $$2 = this.m_6844_(EquipmentSlot.FEET);
                $$2.m_41622_(1, this, p_21301_ -> p_21301_.m_21166_(EquipmentSlot.FEET));
            }
        }
    }

    protected void m_147225_() {
        AttributeInstance $$0 = this.m_21051_(Attributes.f_22279_);
        if ($$0 == null) {
            return;
        }
        if ($$0.m_22111_(f_147184_) != null) {
            $$0.m_22120_(f_147184_);
        }
    }

    protected void m_147226_() {
        int $$0;
        if (!this.m_217002_().m_60795_() && ($$0 = this.m_146888_()) > 0) {
            AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
            if ($$1 == null) {
                return;
            }
            float $$2 = -0.05f * this.m_146889_();
            $$1.m_22118_(new AttributeModifier(f_147184_, "Powder snow slow", (double)$$2, AttributeModifier.Operation.ADDITION));
        }
    }

    protected void m_5806_(BlockPos p_21175_) {
        int $$1 = EnchantmentHelper.m_44836_(Enchantments.f_44974_, this);
        if ($$1 > 0) {
            FrostWalkerEnchantment.m_45018_(this, this.f_19853_, p_21175_, $$1);
        }
        if (this.m_6757_(this.m_217002_())) {
            this.m_21185_();
        }
        this.m_21186_();
    }

    public boolean m_6162_() {
        return false;
    }

    public float m_6134_() {
        return this.m_6162_() ? 0.5f : 1.0f;
    }

    protected boolean m_6129_() {
        return true;
    }

    @Override
    public boolean m_6146_() {
        return false;
    }

    protected void m_6153_() {
        ++this.f_20919_;
        if (this.f_20919_ == 20 && !this.f_19853_.m_5776_()) {
            this.f_19853_.m_7605_(this, (byte)60);
            this.m_142687_(Entity.RemovalReason.KILLED);
        }
    }

    public boolean m_6149_() {
        return !this.m_6162_();
    }

    protected boolean m_6125_() {
        return !this.m_6162_();
    }

    protected int m_7302_(int p_21303_) {
        int $$1 = EnchantmentHelper.m_44918_(this);
        if ($$1 > 0 && this.f_19796_.m_188503_($$1 + 1) > 0) {
            return p_21303_;
        }
        return p_21303_ - 1;
    }

    protected int m_7305_(int p_21307_) {
        return Math.min(p_21307_ + 4, this.m_6062_());
    }

    public int m_213860_() {
        return 0;
    }

    protected boolean m_6124_() {
        return false;
    }

    public RandomSource m_217043_() {
        return this.f_19796_;
    }

    @Nullable
    public LivingEntity m_21188_() {
        return this.f_20949_;
    }

    public int m_21213_() {
        return this.f_20950_;
    }

    public void m_6598_(@Nullable Player p_21248_) {
        this.f_20888_ = p_21248_;
        this.f_20889_ = this.f_19797_;
    }

    public void m_6703_(@Nullable LivingEntity p_21039_) {
        this.f_20949_ = p_21039_;
        this.f_20950_ = this.f_19797_;
    }

    @Nullable
    public LivingEntity m_21214_() {
        return this.f_20951_;
    }

    public int m_21215_() {
        return this.f_20952_;
    }

    public void m_21335_(Entity p_21336_) {
        this.f_20951_ = p_21336_ instanceof LivingEntity ? (LivingEntity)p_21336_ : null;
        this.f_20952_ = this.f_19797_;
    }

    public int m_21216_() {
        return this.f_20891_;
    }

    public void m_21310_(int p_21311_) {
        this.f_20891_ = p_21311_;
    }

    public boolean m_147223_() {
        return this.f_147183_;
    }

    public void m_147244_(boolean p_147245_) {
        this.f_147183_ = p_147245_;
    }

    protected boolean m_213772_(EquipmentSlot p_217035_) {
        return true;
    }

    public void m_238392_(EquipmentSlot p_238393_, ItemStack p_238394_, ItemStack p_238395_) {
        boolean $$3;
        boolean bl = $$3 = p_238395_.m_41619_() && p_238394_.m_41619_();
        if ($$3 || ItemStack.m_41758_(p_238394_, p_238395_) || this.f_19803_) {
            return;
        }
        if (p_238393_.m_20743_() == EquipmentSlot.Type.ARMOR) {
            this.m_217041_(p_238395_);
        }
        if (this.m_213772_(p_238393_)) {
            this.m_146850_(GameEvent.f_157811_);
        }
    }

    protected void m_217041_(ItemStack p_217042_) {
        if (p_217042_.m_41619_() || this.m_5833_()) {
            return;
        }
        SoundEvent $$1 = p_217042_.m_150920_();
        if ($$1 != null) {
            this.m_5496_($$1, 1.0f, 1.0f);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_21145_) {
        p_21145_.m_128350_("Health", this.m_21223_());
        p_21145_.m_128376_("HurtTime", (short)this.f_20916_);
        p_21145_.m_128405_("HurtByTimestamp", this.f_20950_);
        p_21145_.m_128376_("DeathTime", (short)this.f_20919_);
        p_21145_.m_128350_("AbsorptionAmount", this.m_6103_());
        p_21145_.m_128365_("Attributes", this.m_21204_().m_22180_());
        if (!this.f_20945_.isEmpty()) {
            ListTag $$1 = new ListTag();
            for (MobEffectInstance $$2 : this.f_20945_.values()) {
                $$1.add($$2.m_19555_(new CompoundTag()));
            }
            p_21145_.m_128365_("ActiveEffects", $$1);
        }
        p_21145_.m_128379_("FallFlying", this.m_21255_());
        this.m_21257_().ifPresent(p_21099_ -> {
            p_21145_.m_128405_("SleepingX", p_21099_.m_123341_());
            p_21145_.m_128405_("SleepingY", p_21099_.m_123342_());
            p_21145_.m_128405_("SleepingZ", p_21099_.m_123343_());
        });
        DataResult<Tag> $$3 = this.f_20939_.m_21914_(NbtOps.f_128958_);
        $$3.resultOrPartial(arg_0 -> ((Logger)f_201943_).error(arg_0)).ifPresent(p_21102_ -> p_21145_.m_128365_("Brain", (Tag)p_21102_));
    }

    @Override
    public void m_7378_(CompoundTag p_21096_) {
        this.m_7911_(p_21096_.m_128457_("AbsorptionAmount"));
        if (p_21096_.m_128425_("Attributes", 9) && this.f_19853_ != null && !this.f_19853_.f_46443_) {
            this.m_21204_().m_22168_(p_21096_.m_128437_("Attributes", 10));
        }
        if (p_21096_.m_128425_("ActiveEffects", 9)) {
            ListTag $$1 = p_21096_.m_128437_("ActiveEffects", 10);
            for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
                CompoundTag $$3 = $$1.m_128728_($$2);
                MobEffectInstance $$4 = MobEffectInstance.m_19560_($$3);
                if ($$4 == null) continue;
                this.f_20945_.put($$4.m_19544_(), $$4);
            }
        }
        if (p_21096_.m_128425_("Health", 99)) {
            this.m_21153_(p_21096_.m_128457_("Health"));
        }
        this.f_20916_ = p_21096_.m_128448_("HurtTime");
        this.f_20919_ = p_21096_.m_128448_("DeathTime");
        this.f_20950_ = p_21096_.m_128451_("HurtByTimestamp");
        if (p_21096_.m_128425_("Team", 8)) {
            boolean $$7;
            String $$5 = p_21096_.m_128461_("Team");
            PlayerTeam $$6 = this.f_19853_.m_6188_().m_83489_($$5);
            boolean bl = $$7 = $$6 != null && this.f_19853_.m_6188_().m_6546_(this.m_20149_(), $$6);
            if (!$$7) {
                f_201943_.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", (Object)$$5);
            }
        }
        if (p_21096_.m_128471_("FallFlying")) {
            this.m_20115_(7, true);
        }
        if (p_21096_.m_128425_("SleepingX", 99) && p_21096_.m_128425_("SleepingY", 99) && p_21096_.m_128425_("SleepingZ", 99)) {
            BlockPos $$8 = new BlockPos(p_21096_.m_128451_("SleepingX"), p_21096_.m_128451_("SleepingY"), p_21096_.m_128451_("SleepingZ"));
            this.m_21250_($$8);
            this.f_19804_.m_135381_(f_19806_, Pose.SLEEPING);
            if (!this.f_19803_) {
                this.m_21080_($$8);
            }
        }
        if (p_21096_.m_128425_("Brain", 10)) {
            this.f_20939_ = this.m_8075_(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_21096_.m_128423_("Brain")));
        }
    }

    protected void m_21217_() {
        Iterator<MobEffect> $$0 = this.f_20945_.keySet().iterator();
        try {
            while ($$0.hasNext()) {
                MobEffect $$1 = $$0.next();
                MobEffectInstance $$2 = this.f_20945_.get($$1);
                if (!$$2.m_19552_(this, () -> this.m_141973_($$2, true, null))) {
                    if (this.f_19853_.f_46443_) continue;
                    $$0.remove();
                    this.m_7285_($$2);
                    continue;
                }
                if ($$2.m_19557_() % 600 != 0) continue;
                this.m_141973_($$2, false, null);
            }
        }
        catch (ConcurrentModificationException $$1) {
            // empty catch block
        }
        if (this.f_20948_) {
            if (!this.f_19853_.f_46443_) {
                this.m_8034_();
                this.m_147239_();
            }
            this.f_20948_ = false;
        }
        int $$3 = this.f_19804_.m_135370_(f_20962_);
        boolean $$4 = this.f_19804_.m_135370_(f_20963_);
        if ($$3 > 0) {
            boolean $$6;
            if (this.m_20145_()) {
                boolean $$5 = this.f_19796_.m_188503_(15) == 0;
            } else {
                $$6 = this.f_19796_.m_188499_();
            }
            if ($$4) {
                $$6 &= this.f_19796_.m_188503_(5) == 0;
            }
            if ($$6 && $$3 > 0) {
                double $$7 = (double)($$3 >> 16 & 0xFF) / 255.0;
                double $$8 = (double)($$3 >> 8 & 0xFF) / 255.0;
                double $$9 = (double)($$3 >> 0 & 0xFF) / 255.0;
                this.f_19853_.m_7106_($$4 ? ParticleTypes.f_123770_ : ParticleTypes.f_123811_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), $$7, $$8, $$9);
            }
        }
    }

    protected void m_8034_() {
        if (this.f_20945_.isEmpty()) {
            this.m_21218_();
            this.m_6842_(false);
        } else {
            Collection<MobEffectInstance> $$0 = this.f_20945_.values();
            this.f_19804_.m_135381_(f_20963_, LivingEntity.m_21179_($$0));
            this.f_19804_.m_135381_(f_20962_, PotionUtils.m_43564_($$0));
            this.m_6842_(this.m_21023_(MobEffects.f_19609_));
        }
    }

    private void m_147239_() {
        boolean $$0 = this.m_142038_();
        if (this.m_20291_(6) != $$0) {
            this.m_20115_(6, $$0);
        }
    }

    public double m_20968_(@Nullable Entity p_20969_) {
        double $$1 = 1.0;
        if (this.m_20163_()) {
            $$1 *= 0.8;
        }
        if (this.m_20145_()) {
            float $$2 = this.m_21207_();
            if ($$2 < 0.1f) {
                $$2 = 0.1f;
            }
            $$1 *= 0.7 * (double)$$2;
        }
        if (p_20969_ != null) {
            ItemStack $$3 = this.m_6844_(EquipmentSlot.HEAD);
            EntityType<?> $$4 = p_20969_.m_6095_();
            if ($$4 == EntityType.f_20524_ && $$3.m_150930_(Items.f_42678_) || $$4 == EntityType.f_20501_ && $$3.m_150930_(Items.f_42681_) || $$4 == EntityType.f_20558_ && $$3.m_150930_(Items.f_42682_)) {
                $$1 *= 0.5;
            }
        }
        return $$1;
    }

    public boolean m_6779_(LivingEntity p_21171_) {
        if (p_21171_ instanceof Player && this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
            return false;
        }
        return p_21171_.m_142066_();
    }

    public boolean m_21040_(LivingEntity p_21041_, TargetingConditions p_21042_) {
        return p_21042_.m_26885_(this, p_21041_);
    }

    public boolean m_142066_() {
        return !this.m_20147_() && this.m_142065_();
    }

    public boolean m_142065_() {
        return !this.m_5833_() && this.m_6084_();
    }

    public static boolean m_21179_(Collection<MobEffectInstance> p_21180_) {
        for (MobEffectInstance $$1 : p_21180_) {
            if (!$$1.m_19572_() || $$1.m_19571_()) continue;
            return false;
        }
        return true;
    }

    protected void m_21218_() {
        this.f_19804_.m_135381_(f_20963_, false);
        this.f_19804_.m_135381_(f_20962_, 0);
    }

    public boolean m_21219_() {
        if (this.f_19853_.f_46443_) {
            return false;
        }
        Iterator<MobEffectInstance> $$0 = this.f_20945_.values().iterator();
        boolean $$1 = false;
        while ($$0.hasNext()) {
            this.m_7285_($$0.next());
            $$0.remove();
            $$1 = true;
        }
        return $$1;
    }

    public Collection<MobEffectInstance> m_21220_() {
        return this.f_20945_.values();
    }

    public Map<MobEffect, MobEffectInstance> m_21221_() {
        return this.f_20945_;
    }

    public boolean m_21023_(MobEffect p_21024_) {
        return this.f_20945_.containsKey(p_21024_);
    }

    @Nullable
    public MobEffectInstance m_21124_(MobEffect p_21125_) {
        return this.f_20945_.get(p_21125_);
    }

    public final boolean m_7292_(MobEffectInstance p_21165_) {
        return this.m_147207_(p_21165_, null);
    }

    public boolean m_147207_(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
        if (!this.m_7301_(p_147208_)) {
            return false;
        }
        MobEffectInstance $$2 = this.f_20945_.get(p_147208_.m_19544_());
        if ($$2 == null) {
            this.f_20945_.put(p_147208_.m_19544_(), p_147208_);
            this.m_142540_(p_147208_, p_147209_);
            return true;
        }
        if ($$2.m_19558_(p_147208_)) {
            this.m_141973_($$2, true, p_147209_);
            return true;
        }
        return false;
    }

    public boolean m_7301_(MobEffectInstance p_21197_) {
        MobEffect $$1;
        return this.m_6336_() != MobType.f_21641_ || ($$1 = p_21197_.m_19544_()) != MobEffects.f_19605_ && $$1 != MobEffects.f_19614_;
    }

    public void m_147215_(MobEffectInstance p_147216_, @Nullable Entity p_147217_) {
        if (!this.m_7301_(p_147216_)) {
            return;
        }
        MobEffectInstance $$2 = this.f_20945_.put(p_147216_.m_19544_(), p_147216_);
        if ($$2 == null) {
            this.m_142540_(p_147216_, p_147217_);
        } else {
            this.m_141973_(p_147216_, true, p_147217_);
        }
    }

    public boolean m_21222_() {
        return this.m_6336_() == MobType.f_21641_;
    }

    @Nullable
    public MobEffectInstance m_6234_(@Nullable MobEffect p_21164_) {
        return this.f_20945_.remove(p_21164_);
    }

    public boolean m_21195_(MobEffect p_21196_) {
        MobEffectInstance $$1 = this.m_6234_(p_21196_);
        if ($$1 != null) {
            this.m_7285_($$1);
            return true;
        }
        return false;
    }

    protected void m_142540_(MobEffectInstance p_147190_, @Nullable Entity p_147191_) {
        this.f_20948_ = true;
        if (!this.f_19853_.f_46443_) {
            p_147190_.m_19544_().m_6385_(this, this.m_21204_(), p_147190_.m_19564_());
        }
    }

    protected void m_141973_(MobEffectInstance p_147192_, boolean p_147193_, @Nullable Entity p_147194_) {
        this.f_20948_ = true;
        if (p_147193_ && !this.f_19853_.f_46443_) {
            MobEffect $$3 = p_147192_.m_19544_();
            $$3.m_6386_(this, this.m_21204_(), p_147192_.m_19564_());
            $$3.m_6385_(this, this.m_21204_(), p_147192_.m_19564_());
        }
    }

    protected void m_7285_(MobEffectInstance p_21126_) {
        this.f_20948_ = true;
        if (!this.f_19853_.f_46443_) {
            p_21126_.m_19544_().m_6386_(this, this.m_21204_(), p_21126_.m_19564_());
        }
    }

    public void m_5634_(float p_21116_) {
        float $$1 = this.m_21223_();
        if ($$1 > 0.0f) {
            this.m_21153_($$1 + p_21116_);
        }
    }

    public float m_21223_() {
        return this.f_19804_.m_135370_(f_20961_).floatValue();
    }

    public void m_21153_(float p_21154_) {
        this.f_19804_.m_135381_(f_20961_, Float.valueOf(Mth.m_14036_(p_21154_, 0.0f, this.m_21233_())));
    }

    public boolean m_21224_() {
        return this.m_21223_() <= 0.0f;
    }

    @Override
    public boolean m_6469_(DamageSource p_21016_, float p_21017_) {
        boolean $$18;
        if (this.m_6673_(p_21016_)) {
            return false;
        }
        if (this.f_19853_.f_46443_) {
            return false;
        }
        if (this.m_21224_()) {
            return false;
        }
        if (p_21016_.m_19384_() && this.m_21023_(MobEffects.f_19607_)) {
            return false;
        }
        if (this.m_5803_() && !this.f_19853_.f_46443_) {
            this.m_5796_();
        }
        this.f_20891_ = 0;
        float $$2 = p_21017_;
        boolean $$3 = false;
        float $$4 = 0.0f;
        if (p_21017_ > 0.0f && this.m_21275_(p_21016_)) {
            Entity $$5;
            this.m_7909_(p_21017_);
            $$4 = p_21017_;
            p_21017_ = 0.0f;
            if (!p_21016_.m_19360_() && ($$5 = p_21016_.m_7640_()) instanceof LivingEntity) {
                this.m_6728_((LivingEntity)$$5);
            }
            $$3 = true;
        }
        this.f_20924_ = 1.5f;
        boolean $$6 = true;
        if ((float)this.f_19802_ > 10.0f) {
            if (p_21017_ <= this.f_20898_) {
                return false;
            }
            this.m_6475_(p_21016_, p_21017_ - this.f_20898_);
            this.f_20898_ = p_21017_;
            $$6 = false;
        } else {
            this.f_20898_ = p_21017_;
            this.f_19802_ = 20;
            this.m_6475_(p_21016_, p_21017_);
            this.f_20916_ = this.f_20917_ = 10;
        }
        if (p_21016_.m_146705_() && !this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            this.m_142642_(p_21016_, p_21017_);
            p_21017_ *= 0.75f;
        }
        this.f_20918_ = 0.0f;
        Entity $$7 = p_21016_.m_7639_();
        if ($$7 != null) {
            Wolf $$8;
            if ($$7 instanceof LivingEntity && !p_21016_.m_181121_()) {
                this.m_6703_((LivingEntity)$$7);
            }
            if ($$7 instanceof Player) {
                this.f_20889_ = 100;
                this.f_20888_ = (Player)$$7;
            } else if ($$7 instanceof Wolf && ($$8 = (Wolf)$$7).m_21824_()) {
                this.f_20889_ = 100;
                LivingEntity $$9 = $$8.m_21826_();
                this.f_20888_ = $$9 != null && $$9.m_6095_() == EntityType.f_20532_ ? (Player)$$9 : null;
            }
        }
        if ($$6) {
            if ($$3) {
                this.f_19853_.m_7605_(this, (byte)29);
            } else if (p_21016_ instanceof EntityDamageSource && ((EntityDamageSource)p_21016_).m_19403_()) {
                this.f_19853_.m_7605_(this, (byte)33);
            } else {
                int $$14;
                if (p_21016_ == DamageSource.f_19312_) {
                    int $$10 = 36;
                } else if (p_21016_.m_19384_()) {
                    int $$11 = 37;
                } else if (p_21016_ == DamageSource.f_19325_) {
                    int $$12 = 44;
                } else if (p_21016_ == DamageSource.f_146701_) {
                    int $$13 = 57;
                } else {
                    $$14 = 2;
                }
                this.f_19853_.m_7605_(this, (byte)$$14);
            }
            if (p_21016_ != DamageSource.f_19312_ && (!$$3 || p_21017_ > 0.0f)) {
                this.m_5834_();
            }
            if ($$7 != null) {
                double $$15 = $$7.m_20185_() - this.m_20185_();
                double $$16 = $$7.m_20189_() - this.m_20189_();
                while ($$15 * $$15 + $$16 * $$16 < 1.0E-4) {
                    $$15 = (Math.random() - Math.random()) * 0.01;
                    $$16 = (Math.random() - Math.random()) * 0.01;
                }
                this.f_20918_ = (float)(Mth.m_14136_($$16, $$15) * 57.2957763671875 - (double)this.m_146908_());
                this.m_147240_(0.4f, $$15, $$16);
            } else {
                this.f_20918_ = (int)(Math.random() * 2.0) * 180;
            }
        }
        if (this.m_21224_()) {
            if (!this.m_21262_(p_21016_)) {
                SoundEvent $$17 = this.m_5592_();
                if ($$6 && $$17 != null) {
                    this.m_5496_($$17, this.m_6121_(), this.m_6100_());
                }
                this.m_6667_(p_21016_);
            }
        } else if ($$6) {
            this.m_6677_(p_21016_);
        }
        boolean bl = $$18 = !$$3 || p_21017_ > 0.0f;
        if ($$18) {
            this.f_20958_ = p_21016_;
            this.f_20930_ = this.f_19853_.m_46467_();
        }
        if (this instanceof ServerPlayer) {
            CriteriaTriggers.f_10574_.m_35174_((ServerPlayer)this, p_21016_, $$2, p_21017_, $$3);
            if ($$4 > 0.0f && $$4 < 3.4028235E37f) {
                ((ServerPlayer)this).m_36222_(Stats.f_12932_, Math.round($$4 * 10.0f));
            }
        }
        if ($$7 instanceof ServerPlayer) {
            CriteriaTriggers.f_10573_.m_60112_((ServerPlayer)$$7, this, p_21016_, $$2, p_21017_, $$3);
        }
        return $$18;
    }

    protected void m_6728_(LivingEntity p_21200_) {
        p_21200_.m_6731_(this);
    }

    protected void m_6731_(LivingEntity p_21246_) {
        p_21246_.m_147240_(0.5, p_21246_.m_20185_() - this.m_20185_(), p_21246_.m_20189_() - this.m_20189_());
    }

    private boolean m_21262_(DamageSource p_21263_) {
        if (p_21263_.m_19378_()) {
            return false;
        }
        ItemStack $$1 = null;
        for (InteractionHand $$2 : InteractionHand.values()) {
            ItemStack $$3 = this.m_21120_($$2);
            if (!$$3.m_150930_(Items.f_42747_)) continue;
            $$1 = $$3.m_41777_();
            $$3.m_41774_(1);
            break;
        }
        if ($$1 != null) {
            if (this instanceof ServerPlayer) {
                ServerPlayer $$4 = (ServerPlayer)this;
                $$4.m_36246_(Stats.f_12982_.m_12902_(Items.f_42747_));
                CriteriaTriggers.f_10551_.m_74431_($$4, $$1);
            }
            this.m_21153_(1.0f);
            this.m_21219_();
            this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 900, 1));
            this.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 100, 1));
            this.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 800, 0));
            this.f_19853_.m_7605_(this, (byte)35);
        }
        return $$1 != null;
    }

    @Nullable
    public DamageSource m_21225_() {
        if (this.f_19853_.m_46467_() - this.f_20930_ > 40L) {
            this.f_20958_ = null;
        }
        return this.f_20958_;
    }

    protected void m_6677_(DamageSource p_21160_) {
        SoundEvent $$1 = this.m_7975_(p_21160_);
        if ($$1 != null) {
            this.m_5496_($$1, this.m_6121_(), this.m_6100_());
        }
    }

    public boolean m_21275_(DamageSource p_21276_) {
        Vec3 $$4;
        AbstractArrow $$3;
        Entity $$1 = p_21276_.m_7640_();
        boolean $$2 = false;
        if ($$1 instanceof AbstractArrow && ($$3 = (AbstractArrow)$$1).m_36796_() > 0) {
            $$2 = true;
        }
        if (!p_21276_.m_19376_() && this.m_21254_() && !$$2 && ($$4 = p_21276_.m_7270_()) != null) {
            Vec3 $$5 = this.m_20252_(1.0f);
            Vec3 $$6 = $$4.m_82505_(this.m_20182_()).m_82541_();
            $$6 = new Vec3($$6.f_82479_, 0.0, $$6.f_82481_);
            if ($$6.m_82526_($$5) < 0.0) {
                return true;
            }
        }
        return false;
    }

    private void m_21278_(ItemStack p_21279_) {
        if (!p_21279_.m_41619_()) {
            if (!this.m_20067_()) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12018_, this.m_5720_(), 0.8f, 0.8f + this.f_19853_.f_46441_.m_188501_() * 0.4f, false);
            }
            this.m_21060_(p_21279_, 5);
        }
    }

    public void m_6667_(DamageSource p_21014_) {
        if (this.m_213877_() || this.f_20890_) {
            return;
        }
        Entity $$1 = p_21014_.m_7639_();
        LivingEntity $$2 = this.m_21232_();
        if (this.f_20897_ >= 0 && $$2 != null) {
            $$2.m_5993_(this, this.f_20897_, p_21014_);
        }
        if (this.m_5803_()) {
            this.m_5796_();
        }
        if (!this.f_19853_.f_46443_ && this.m_8077_()) {
            f_201943_.info("Named entity {} died: {}", (Object)this, (Object)this.m_21231_().m_19293_().getString());
        }
        this.f_20890_ = true;
        this.m_21231_().m_19296_();
        if (this.f_19853_ instanceof ServerLevel) {
            if ($$1 == null || $$1.m_214076_((ServerLevel)this.f_19853_, this)) {
                this.m_146850_(GameEvent.f_223707_);
                this.m_6668_(p_21014_);
                this.m_21268_($$2);
            }
            this.f_19853_.m_7605_(this, (byte)3);
        }
        this.m_20124_(Pose.DYING);
    }

    protected void m_21268_(@Nullable LivingEntity p_21269_) {
        if (this.f_19853_.f_46443_) {
            return;
        }
        boolean $$1 = false;
        if (p_21269_ instanceof WitherBoss) {
            if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
                BlockPos $$2 = this.m_20183_();
                BlockState $$3 = Blocks.f_50070_.m_49966_();
                if (this.f_19853_.m_8055_($$2).m_60795_() && $$3.m_60710_(this.f_19853_, $$2)) {
                    this.f_19853_.m_7731_($$2, $$3, 3);
                    $$1 = true;
                }
            }
            if (!$$1) {
                ItemEntity $$4 = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), new ItemStack(Items.f_41951_));
                this.f_19853_.m_7967_($$4);
            }
        }
    }

    protected void m_6668_(DamageSource p_21192_) {
        boolean $$4;
        int $$3;
        Entity $$1 = p_21192_.m_7639_();
        if ($$1 instanceof Player) {
            int $$2 = EnchantmentHelper.m_44930_((LivingEntity)$$1);
        } else {
            $$3 = 0;
        }
        boolean bl = $$4 = this.f_20889_ > 0;
        if (this.m_6125_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.m_7625_(p_21192_, $$4);
            this.m_7472_(p_21192_, $$3, $$4);
        }
        this.m_5907_();
        this.m_21226_();
    }

    protected void m_5907_() {
    }

    protected void m_21226_() {
        if (this.f_19853_ instanceof ServerLevel && !this.m_217046_() && (this.m_6124_() || this.f_20889_ > 0 && this.m_6149_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_))) {
            ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), this.m_213860_());
        }
    }

    protected void m_7472_(DamageSource p_21018_, int p_21019_, boolean p_21020_) {
    }

    public ResourceLocation m_5743_() {
        return this.m_6095_().m_20677_();
    }

    protected void m_7625_(DamageSource p_21021_, boolean p_21022_) {
        ResourceLocation $$2 = this.m_5743_();
        LootTable $$3 = this.f_19853_.m_7654_().m_129898_().m_79217_($$2);
        LootContext.Builder $$4 = this.m_7771_(p_21022_, p_21021_);
        $$3.m_79148_($$4.m_78975_(LootContextParamSets.f_81415_), this::m_19983_);
    }

    protected LootContext.Builder m_7771_(boolean p_21105_, DamageSource p_21106_) {
        LootContext.Builder $$2 = new LootContext.Builder((ServerLevel)this.f_19853_).m_230911_(this.f_19796_).m_78972_(LootContextParams.f_81455_, this).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78972_(LootContextParams.f_81457_, p_21106_).m_78984_(LootContextParams.f_81458_, p_21106_.m_7639_()).m_78984_(LootContextParams.f_81459_, p_21106_.m_7640_());
        if (p_21105_ && this.f_20888_ != null) {
            $$2 = $$2.m_78972_(LootContextParams.f_81456_, this.f_20888_).m_78963_(this.f_20888_.m_36336_());
        }
        return $$2;
    }

    public void m_147240_(double p_147241_, double p_147242_, double p_147243_) {
        if ((p_147241_ *= 1.0 - this.m_21133_(Attributes.f_22278_)) <= 0.0) {
            return;
        }
        this.f_19812_ = true;
        Vec3 $$3 = this.m_20184_();
        Vec3 $$4 = new Vec3(p_147242_, 0.0, p_147243_).m_82541_().m_82490_(p_147241_);
        this.m_20334_($$3.f_82479_ / 2.0 - $$4.f_82479_, this.f_19861_ ? Math.min(0.4, $$3.f_82480_ / 2.0 + p_147241_) : $$3.f_82480_, $$3.f_82481_ / 2.0 - $$4.f_82481_);
    }

    @Nullable
    protected SoundEvent m_7975_(DamageSource p_21239_) {
        return SoundEvents.f_11915_;
    }

    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11910_;
    }

    private SoundEvent m_5896_(int p_21313_) {
        return p_21313_ > 4 ? this.m_196493_().f_196627_() : this.m_196493_().f_196626_();
    }

    public void m_217045_() {
        this.f_217034_ = true;
    }

    public boolean m_217046_() {
        return this.f_217034_;
    }

    public Fallsounds m_196493_() {
        return new Fallsounds(SoundEvents.f_11916_, SoundEvents.f_11908_);
    }

    protected SoundEvent m_7838_(ItemStack p_21174_) {
        return p_21174_.m_41615_();
    }

    public SoundEvent m_7866_(ItemStack p_21202_) {
        return p_21202_.m_41616_();
    }

    @Override
    public void m_6853_(boolean p_21182_) {
        super.m_6853_(p_21182_);
        if (p_21182_) {
            this.f_20957_ = Optional.empty();
        }
    }

    public Optional<BlockPos> m_21227_() {
        return this.f_20957_;
    }

    public boolean m_6147_() {
        if (this.m_5833_()) {
            return false;
        }
        BlockPos $$0 = this.m_20183_();
        BlockState $$1 = this.m_146900_();
        if ($$1.m_204336_(BlockTags.f_13082_)) {
            this.f_20957_ = Optional.of($$0);
            return true;
        }
        if ($$1.m_60734_() instanceof TrapDoorBlock && this.m_21176_($$0, $$1)) {
            this.f_20957_ = Optional.of($$0);
            return true;
        }
        return false;
    }

    private boolean m_21176_(BlockPos p_21177_, BlockState p_21178_) {
        BlockState $$2;
        return p_21178_.m_61143_(TrapDoorBlock.f_57514_) != false && ($$2 = this.f_19853_.m_8055_(p_21177_.m_7495_())).m_60713_(Blocks.f_50155_) && $$2.m_61143_(LadderBlock.f_54337_) == p_21178_.m_61143_(TrapDoorBlock.f_54117_);
    }

    @Override
    public boolean m_6084_() {
        return !this.m_213877_() && this.m_21223_() > 0.0f;
    }

    @Override
    public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
        boolean $$3 = super.m_142535_(p_147187_, p_147188_, p_147189_);
        int $$4 = this.m_5639_(p_147187_, p_147188_);
        if ($$4 > 0) {
            this.m_5496_(this.m_5896_($$4), 1.0f, 1.0f);
            this.m_21229_();
            this.m_6469_(p_147189_, $$4);
            return true;
        }
        return $$3;
    }

    protected int m_5639_(float p_21237_, float p_21238_) {
        MobEffectInstance $$2 = this.m_21124_(MobEffects.f_19603_);
        float $$3 = $$2 == null ? 0.0f : (float)($$2.m_19564_() + 1);
        return Mth.m_14167_((p_21237_ - 3.0f - $$3) * p_21238_);
    }

    protected void m_21229_() {
        int $$2;
        int $$1;
        if (this.m_20067_()) {
            return;
        }
        int $$0 = Mth.m_14107_(this.m_20185_());
        BlockState $$3 = this.f_19853_.m_8055_(new BlockPos($$0, $$1 = Mth.m_14107_(this.m_20186_() - (double)0.2f), $$2 = Mth.m_14107_(this.m_20189_())));
        if (!$$3.m_60795_()) {
            SoundType $$4 = $$3.m_60827_();
            this.m_5496_($$4.m_56779_(), $$4.m_56773_() * 0.5f, $$4.m_56774_() * 0.75f);
        }
    }

    @Override
    public void m_6053_() {
        this.f_20916_ = this.f_20917_ = 10;
        this.f_20918_ = 0.0f;
    }

    public int m_21230_() {
        return Mth.m_14107_(this.m_21133_(Attributes.f_22284_));
    }

    protected void m_6472_(DamageSource p_21122_, float p_21123_) {
    }

    protected void m_142642_(DamageSource p_147213_, float p_147214_) {
    }

    protected void m_7909_(float p_21316_) {
    }

    protected float m_21161_(DamageSource p_21162_, float p_21163_) {
        if (!p_21162_.m_19376_()) {
            this.m_6472_(p_21162_, p_21163_);
            p_21163_ = CombatRules.m_19272_(p_21163_, this.m_21230_(), (float)this.m_21133_(Attributes.f_22285_));
        }
        return p_21163_;
    }

    protected float m_6515_(DamageSource p_21193_, float p_21194_) {
        int $$2;
        int $$3;
        float $$4;
        float $$5;
        float $$6;
        if (p_21193_.m_19379_()) {
            return p_21194_;
        }
        if (this.m_21023_(MobEffects.f_19606_) && p_21193_ != DamageSource.f_19317_ && ($$6 = ($$5 = p_21194_) - (p_21194_ = Math.max(($$4 = p_21194_ * (float)($$3 = 25 - ($$2 = (this.m_21124_(MobEffects.f_19606_).m_19564_() + 1) * 5))) / 25.0f, 0.0f))) > 0.0f && $$6 < 3.4028235E37f) {
            if (this instanceof ServerPlayer) {
                ((ServerPlayer)this).m_36222_(Stats.f_12934_, Math.round($$6 * 10.0f));
            } else if (p_21193_.m_7639_() instanceof ServerPlayer) {
                ((ServerPlayer)p_21193_.m_7639_()).m_36222_(Stats.f_12930_, Math.round($$6 * 10.0f));
            }
        }
        if (p_21194_ <= 0.0f) {
            return 0.0f;
        }
        if (p_21193_.m_238340_()) {
            return p_21194_;
        }
        int $$7 = EnchantmentHelper.m_44856_(this.m_6168_(), p_21193_);
        if ($$7 > 0) {
            p_21194_ = CombatRules.m_19269_(p_21194_, $$7);
        }
        return p_21194_;
    }

    protected void m_6475_(DamageSource p_21240_, float p_21241_) {
        if (this.m_6673_(p_21240_)) {
            return;
        }
        p_21241_ = this.m_21161_(p_21240_, p_21241_);
        float $$2 = p_21241_ = this.m_6515_(p_21240_, p_21241_);
        p_21241_ = Math.max(p_21241_ - this.m_6103_(), 0.0f);
        this.m_7911_(this.m_6103_() - ($$2 - p_21241_));
        float $$3 = $$2 - p_21241_;
        if ($$3 > 0.0f && $$3 < 3.4028235E37f && p_21240_.m_7639_() instanceof ServerPlayer) {
            ((ServerPlayer)p_21240_.m_7639_()).m_36222_(Stats.f_12929_, Math.round($$3 * 10.0f));
        }
        if (p_21241_ == 0.0f) {
            return;
        }
        float $$4 = this.m_21223_();
        this.m_21153_($$4 - p_21241_);
        this.m_21231_().m_19289_(p_21240_, $$4, p_21241_);
        this.m_7911_(this.m_6103_() - p_21241_);
        this.m_146850_(GameEvent.f_223706_);
    }

    public CombatTracker m_21231_() {
        return this.f_20944_;
    }

    @Nullable
    public LivingEntity m_21232_() {
        if (this.f_20944_.m_19294_() != null) {
            return this.f_20944_.m_19294_();
        }
        if (this.f_20888_ != null) {
            return this.f_20888_;
        }
        if (this.f_20949_ != null) {
            return this.f_20949_;
        }
        return null;
    }

    public final float m_21233_() {
        return (float)this.m_21133_(Attributes.f_22276_);
    }

    public final int m_21234_() {
        return this.f_19804_.m_135370_(f_20940_);
    }

    public final void m_21317_(int p_21318_) {
        this.f_19804_.m_135381_(f_20940_, p_21318_);
    }

    public final int m_21235_() {
        return this.f_19804_.m_135370_(f_20941_);
    }

    public final void m_21321_(int p_21322_) {
        this.f_19804_.m_135381_(f_20941_, p_21322_);
    }

    private int m_21304_() {
        if (MobEffectUtil.m_19584_(this)) {
            return 6 - (1 + MobEffectUtil.m_19586_(this));
        }
        if (this.m_21023_(MobEffects.f_19599_)) {
            return 6 + (1 + this.m_21124_(MobEffects.f_19599_).m_19564_()) * 2;
        }
        return 6;
    }

    public void m_6674_(InteractionHand p_21007_) {
        this.m_21011_(p_21007_, false);
    }

    public void m_21011_(InteractionHand p_21012_, boolean p_21013_) {
        if (!this.f_20911_ || this.f_20913_ >= this.m_21304_() / 2 || this.f_20913_ < 0) {
            this.f_20913_ = -1;
            this.f_20911_ = true;
            this.f_20912_ = p_21012_;
            if (this.f_19853_ instanceof ServerLevel) {
                ClientboundAnimatePacket $$2 = new ClientboundAnimatePacket(this, p_21012_ == InteractionHand.MAIN_HAND ? 0 : 3);
                ServerChunkCache $$3 = ((ServerLevel)this.f_19853_).m_7726_();
                if (p_21013_) {
                    $$3.m_8394_(this, $$2);
                } else {
                    $$3.m_8445_(this, $$2);
                }
            }
        }
    }

    @Override
    public void m_7822_(byte p_20975_) {
        switch (p_20975_) {
            case 2: 
            case 33: 
            case 36: 
            case 37: 
            case 44: 
            case 57: {
                DamageSource $$5;
                this.f_20924_ = 1.5f;
                this.f_19802_ = 20;
                this.f_20916_ = this.f_20917_ = 10;
                this.f_20918_ = 0.0f;
                if (p_20975_ == 33) {
                    this.m_5496_(SoundEvents.f_12511_, this.m_6121_(), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                }
                if (p_20975_ == 37) {
                    DamageSource $$1 = DamageSource.f_19307_;
                } else if (p_20975_ == 36) {
                    DamageSource $$2 = DamageSource.f_19312_;
                } else if (p_20975_ == 44) {
                    DamageSource $$3 = DamageSource.f_19325_;
                } else if (p_20975_ == 57) {
                    DamageSource $$4 = DamageSource.f_146701_;
                } else {
                    $$5 = DamageSource.f_19318_;
                }
                SoundEvent $$6 = this.m_7975_($$5);
                if ($$6 != null) {
                    this.m_5496_($$6, this.m_6121_(), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                }
                this.m_6469_(DamageSource.f_19318_, 0.0f);
                this.f_20958_ = $$5;
                this.f_20930_ = this.f_19853_.m_46467_();
                break;
            }
            case 3: {
                SoundEvent $$7 = this.m_5592_();
                if ($$7 != null) {
                    this.m_5496_($$7, this.m_6121_(), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
                }
                if (this instanceof Player) break;
                this.m_21153_(0.0f);
                this.m_6667_(DamageSource.f_19318_);
                break;
            }
            case 30: {
                this.m_5496_(SoundEvents.f_12347_, 0.8f, 0.8f + this.f_19853_.f_46441_.m_188501_() * 0.4f);
                break;
            }
            case 29: {
                this.m_5496_(SoundEvents.f_12346_, 1.0f, 0.8f + this.f_19853_.f_46441_.m_188501_() * 0.4f);
                break;
            }
            case 46: {
                int $$8 = 128;
                for (int $$9 = 0; $$9 < 128; ++$$9) {
                    double $$10 = (double)$$9 / 127.0;
                    float $$11 = (this.f_19796_.m_188501_() - 0.5f) * 0.2f;
                    float $$12 = (this.f_19796_.m_188501_() - 0.5f) * 0.2f;
                    float $$13 = (this.f_19796_.m_188501_() - 0.5f) * 0.2f;
                    double $$14 = Mth.m_14139_($$10, this.f_19854_, this.m_20185_()) + (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_() * 2.0;
                    double $$15 = Mth.m_14139_($$10, this.f_19855_, this.m_20186_()) + this.f_19796_.m_188500_() * (double)this.m_20206_();
                    double $$16 = Mth.m_14139_($$10, this.f_19856_, this.m_20189_()) + (this.f_19796_.m_188500_() - 0.5) * (double)this.m_20205_() * 2.0;
                    this.f_19853_.m_7106_(ParticleTypes.f_123760_, $$14, $$15, $$16, $$11, $$12, $$13);
                }
                break;
            }
            case 47: {
                this.m_21278_(this.m_6844_(EquipmentSlot.MAINHAND));
                break;
            }
            case 48: {
                this.m_21278_(this.m_6844_(EquipmentSlot.OFFHAND));
                break;
            }
            case 49: {
                this.m_21278_(this.m_6844_(EquipmentSlot.HEAD));
                break;
            }
            case 50: {
                this.m_21278_(this.m_6844_(EquipmentSlot.CHEST));
                break;
            }
            case 51: {
                this.m_21278_(this.m_6844_(EquipmentSlot.LEGS));
                break;
            }
            case 52: {
                this.m_21278_(this.m_6844_(EquipmentSlot.FEET));
                break;
            }
            case 54: {
                HoneyBlock.m_54010_(this);
                break;
            }
            case 55: {
                this.m_21312_();
                break;
            }
            case 60: {
                this.m_147246_();
                break;
            }
            default: {
                super.m_7822_(p_20975_);
            }
        }
    }

    private void m_147246_() {
        for (int $$0 = 0; $$0 < 20; ++$$0) {
            double $$1 = this.f_19796_.m_188583_() * 0.02;
            double $$2 = this.f_19796_.m_188583_() * 0.02;
            double $$3 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20208_(1.0), this.m_20187_(), this.m_20262_(1.0), $$1, $$2, $$3);
        }
    }

    private void m_21312_() {
        ItemStack $$0 = this.m_6844_(EquipmentSlot.OFFHAND);
        this.m_8061_(EquipmentSlot.OFFHAND, this.m_6844_(EquipmentSlot.MAINHAND));
        this.m_8061_(EquipmentSlot.MAINHAND, $$0);
    }

    @Override
    protected void m_6088_() {
        this.m_6469_(DamageSource.f_19317_, 4.0f);
    }

    protected void m_21203_() {
        int $$0 = this.m_21304_();
        if (this.f_20911_) {
            ++this.f_20913_;
            if (this.f_20913_ >= $$0) {
                this.f_20913_ = 0;
                this.f_20911_ = false;
            }
        } else {
            this.f_20913_ = 0;
        }
        this.f_20921_ = (float)this.f_20913_ / (float)$$0;
    }

    @Nullable
    public AttributeInstance m_21051_(Attribute p_21052_) {
        return this.m_21204_().m_22146_(p_21052_);
    }

    public double m_21133_(Attribute p_21134_) {
        return this.m_21204_().m_22181_(p_21134_);
    }

    public double m_21172_(Attribute p_21173_) {
        return this.m_21204_().m_22185_(p_21173_);
    }

    public AttributeMap m_21204_() {
        return this.f_20943_;
    }

    public MobType m_6336_() {
        return MobType.f_21640_;
    }

    public ItemStack m_21205_() {
        return this.m_6844_(EquipmentSlot.MAINHAND);
    }

    public ItemStack m_21206_() {
        return this.m_6844_(EquipmentSlot.OFFHAND);
    }

    public boolean m_21055_(Item p_21056_) {
        return this.m_21093_(p_147200_ -> p_147200_.m_150930_(p_21056_));
    }

    public boolean m_21093_(Predicate<ItemStack> p_21094_) {
        return p_21094_.test(this.m_21205_()) || p_21094_.test(this.m_21206_());
    }

    public ItemStack m_21120_(InteractionHand p_21121_) {
        if (p_21121_ == InteractionHand.MAIN_HAND) {
            return this.m_6844_(EquipmentSlot.MAINHAND);
        }
        if (p_21121_ == InteractionHand.OFF_HAND) {
            return this.m_6844_(EquipmentSlot.OFFHAND);
        }
        throw new IllegalArgumentException("Invalid hand " + p_21121_);
    }

    public void m_21008_(InteractionHand p_21009_, ItemStack p_21010_) {
        if (p_21009_ == InteractionHand.MAIN_HAND) {
            this.m_8061_(EquipmentSlot.MAINHAND, p_21010_);
        } else if (p_21009_ == InteractionHand.OFF_HAND) {
            this.m_8061_(EquipmentSlot.OFFHAND, p_21010_);
        } else {
            throw new IllegalArgumentException("Invalid hand " + p_21009_);
        }
    }

    public boolean m_21033_(EquipmentSlot p_21034_) {
        return !this.m_6844_(p_21034_).m_41619_();
    }

    @Override
    public abstract Iterable<ItemStack> m_6168_();

    public abstract ItemStack m_6844_(EquipmentSlot var1);

    @Override
    public abstract void m_8061_(EquipmentSlot var1, ItemStack var2);

    protected void m_181122_(ItemStack p_181123_) {
        CompoundTag $$1 = p_181123_.m_41783_();
        if ($$1 != null) {
            p_181123_.m_41720_().m_142312_($$1);
        }
    }

    public float m_21207_() {
        Iterable<ItemStack> $$0 = this.m_6168_();
        int $$1 = 0;
        int $$2 = 0;
        for (ItemStack $$3 : $$0) {
            if (!$$3.m_41619_()) {
                ++$$2;
            }
            ++$$1;
        }
        return $$1 > 0 ? (float)$$2 / (float)$$1 : 0.0f;
    }

    @Override
    public void m_6858_(boolean p_21284_) {
        super.m_6858_(p_21284_);
        AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
        if ($$1.m_22111_(f_20929_) != null) {
            $$1.m_22130_(f_20960_);
        }
        if (p_21284_) {
            $$1.m_22118_(f_20960_);
        }
    }

    protected float m_6121_() {
        return 1.0f;
    }

    public float m_6100_() {
        if (this.m_6162_()) {
            return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.5f;
        }
        return (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f;
    }

    protected boolean m_6107_() {
        return this.m_21224_();
    }

    @Override
    public void m_7334_(Entity p_21294_) {
        if (!this.m_5803_()) {
            super.m_7334_(p_21294_);
        }
    }

    private void m_21028_(Entity p_21029_) {
        Vec3 $$4;
        if (this.m_213877_()) {
            Vec3 $$1 = this.m_20182_();
        } else if (p_21029_.m_213877_() || this.f_19853_.m_8055_(p_21029_.m_20183_()).m_204336_(BlockTags.f_13075_)) {
            double $$2 = Math.max(this.m_20186_(), p_21029_.m_20186_());
            Vec3 $$3 = new Vec3(this.m_20185_(), $$2, this.m_20189_());
        } else {
            $$4 = p_21029_.m_7688_(this);
        }
        this.m_142098_($$4.f_82479_, $$4.f_82480_, $$4.f_82481_);
    }

    @Override
    public boolean m_6052_() {
        return this.m_20151_();
    }

    protected float m_6118_() {
        return 0.42f * this.m_20098_();
    }

    public double m_182332_() {
        return this.m_21023_(MobEffects.f_19603_) ? (double)(0.1f * (float)(this.m_21124_(MobEffects.f_19603_).m_19564_() + 1)) : 0.0;
    }

    protected void m_6135_() {
        double $$0 = (double)this.m_6118_() + this.m_182332_();
        Vec3 $$1 = this.m_20184_();
        this.m_20334_($$1.f_82479_, $$0, $$1.f_82481_);
        if (this.m_20142_()) {
            float $$2 = this.m_146908_() * ((float)Math.PI / 180);
            this.m_20256_(this.m_20184_().m_82520_(-Mth.m_14031_($$2) * 0.2f, 0.0, Mth.m_14089_($$2) * 0.2f));
        }
        this.f_19812_ = true;
    }

    protected void m_21208_() {
        this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04f, 0.0));
    }

    protected void m_203347_(TagKey<Fluid> p_204043_) {
        this.m_20256_(this.m_20184_().m_82520_(0.0, 0.04f, 0.0));
    }

    protected float m_6108_() {
        return 0.8f;
    }

    public boolean m_203441_(FluidState p_204042_) {
        return false;
    }

    public void m_7023_(Vec3 p_21280_) {
        if (this.m_6142_() || this.m_6109_()) {
            boolean $$2;
            double $$1 = 0.08;
            boolean bl = $$2 = this.m_20184_().f_82480_ <= 0.0;
            if ($$2 && this.m_21023_(MobEffects.f_19591_)) {
                $$1 = 0.01;
                this.m_183634_();
            }
            FluidState $$3 = this.f_19853_.m_6425_(this.m_20183_());
            if (this.m_20069_() && this.m_6129_() && !this.m_203441_($$3)) {
                double $$4 = this.m_20186_();
                float $$5 = this.m_20142_() ? 0.9f : this.m_6108_();
                float $$6 = 0.02f;
                float $$7 = EnchantmentHelper.m_44922_(this);
                if ($$7 > 3.0f) {
                    $$7 = 3.0f;
                }
                if (!this.f_19861_) {
                    $$7 *= 0.5f;
                }
                if ($$7 > 0.0f) {
                    $$5 += (0.54600006f - $$5) * $$7 / 3.0f;
                    $$6 += (this.m_6113_() - $$6) * $$7 / 3.0f;
                }
                if (this.m_21023_(MobEffects.f_19593_)) {
                    $$5 = 0.96f;
                }
                this.m_19920_($$6, p_21280_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                Vec3 $$8 = this.m_20184_();
                if (this.f_19862_ && this.m_6147_()) {
                    $$8 = new Vec3($$8.f_82479_, 0.2, $$8.f_82481_);
                }
                this.m_20256_($$8.m_82542_($$5, 0.8f, $$5));
                Vec3 $$9 = this.m_20994_($$1, $$2, this.m_20184_());
                this.m_20256_($$9);
                if (this.f_19862_ && this.m_20229_($$9.f_82479_, $$9.f_82480_ + (double)0.6f - this.m_20186_() + $$4, $$9.f_82481_)) {
                    this.m_20334_($$9.f_82479_, 0.3f, $$9.f_82481_);
                }
            } else if (this.m_20077_() && this.m_6129_() && !this.m_203441_($$3)) {
                double $$10 = this.m_20186_();
                this.m_19920_(0.02f, p_21280_);
                this.m_6478_(MoverType.SELF, this.m_20184_());
                if (this.m_204036_(FluidTags.f_13132_) <= this.m_20204_()) {
                    this.m_20256_(this.m_20184_().m_82542_(0.5, 0.8f, 0.5));
                    Vec3 $$11 = this.m_20994_($$1, $$2, this.m_20184_());
                    this.m_20256_($$11);
                } else {
                    this.m_20256_(this.m_20184_().m_82490_(0.5));
                }
                if (!this.m_20068_()) {
                    this.m_20256_(this.m_20184_().m_82520_(0.0, -$$1 / 4.0, 0.0));
                }
                Vec3 $$12 = this.m_20184_();
                if (this.f_19862_ && this.m_20229_($$12.f_82479_, $$12.f_82480_ + (double)0.6f - this.m_20186_() + $$10, $$12.f_82481_)) {
                    this.m_20334_($$12.f_82479_, 0.3f, $$12.f_82481_);
                }
            } else if (this.m_21255_()) {
                double $$22;
                double $$23;
                float $$24;
                Vec3 $$13 = this.m_20184_();
                if ($$13.f_82480_ > -0.5) {
                    this.f_19789_ = 1.0f;
                }
                Vec3 $$14 = this.m_20154_();
                float $$15 = this.m_146909_() * ((float)Math.PI / 180);
                double $$16 = Math.sqrt($$14.f_82479_ * $$14.f_82479_ + $$14.f_82481_ * $$14.f_82481_);
                double $$17 = $$13.m_165924_();
                double $$18 = $$14.m_82553_();
                double $$19 = Math.cos($$15);
                $$19 = $$19 * $$19 * Math.min(1.0, $$18 / 0.4);
                $$13 = this.m_20184_().m_82520_(0.0, $$1 * (-1.0 + $$19 * 0.75), 0.0);
                if ($$13.f_82480_ < 0.0 && $$16 > 0.0) {
                    double $$20 = $$13.f_82480_ * -0.1 * $$19;
                    $$13 = $$13.m_82520_($$14.f_82479_ * $$20 / $$16, $$20, $$14.f_82481_ * $$20 / $$16);
                }
                if ($$15 < 0.0f && $$16 > 0.0) {
                    double $$21 = $$17 * (double)(-Mth.m_14031_($$15)) * 0.04;
                    $$13 = $$13.m_82520_(-$$14.f_82479_ * $$21 / $$16, $$21 * 3.2, -$$14.f_82481_ * $$21 / $$16);
                }
                if ($$16 > 0.0) {
                    $$13 = $$13.m_82520_(($$14.f_82479_ / $$16 * $$17 - $$13.f_82479_) * 0.1, 0.0, ($$14.f_82481_ / $$16 * $$17 - $$13.f_82481_) * 0.1);
                }
                this.m_20256_($$13.m_82542_(0.99f, 0.98f, 0.99f));
                this.m_6478_(MoverType.SELF, this.m_20184_());
                if (this.f_19862_ && !this.f_19853_.f_46443_ && ($$24 = (float)(($$23 = $$17 - ($$22 = this.m_20184_().m_165924_())) * 10.0 - 3.0)) > 0.0f) {
                    this.m_5496_(this.m_5896_((int)$$24), 1.0f, 1.0f);
                    this.m_6469_(DamageSource.f_19316_, $$24);
                }
                if (this.f_19861_ && !this.f_19853_.f_46443_) {
                    this.m_20115_(7, false);
                }
            } else {
                BlockPos $$25 = this.m_20099_();
                float $$26 = this.f_19853_.m_8055_($$25).m_60734_().m_49958_();
                float $$27 = this.f_19861_ ? $$26 * 0.91f : 0.91f;
                Vec3 $$28 = this.m_21074_(p_21280_, $$26);
                double $$29 = $$28.f_82480_;
                if (this.m_21023_(MobEffects.f_19620_)) {
                    $$29 += (0.05 * (double)(this.m_21124_(MobEffects.f_19620_).m_19564_() + 1) - $$28.f_82480_) * 0.2;
                    this.m_183634_();
                } else if (!this.f_19853_.f_46443_ || this.f_19853_.m_46805_($$25)) {
                    if (!this.m_20068_()) {
                        $$29 -= $$1;
                    }
                } else {
                    $$29 = this.m_20186_() > (double)this.f_19853_.m_141937_() ? -0.1 : 0.0;
                }
                if (this.m_147223_()) {
                    this.m_20334_($$28.f_82479_, $$29, $$28.f_82481_);
                } else {
                    this.m_20334_($$28.f_82479_ * (double)$$27, $$29 * (double)0.98f, $$28.f_82481_ * (double)$$27);
                }
            }
        }
        this.m_21043_(this, this instanceof FlyingAnimal);
    }

    public void m_21043_(LivingEntity p_21044_, boolean p_21045_) {
        double $$4;
        double $$3;
        p_21044_.f_20923_ = p_21044_.f_20924_;
        double $$2 = p_21044_.m_20185_() - p_21044_.f_19854_;
        float $$5 = (float)Math.sqrt($$2 * $$2 + ($$3 = p_21045_ ? p_21044_.m_20186_() - p_21044_.f_19855_ : 0.0) * $$3 + ($$4 = p_21044_.m_20189_() - p_21044_.f_19856_) * $$4) * 4.0f;
        if ($$5 > 1.0f) {
            $$5 = 1.0f;
        }
        p_21044_.f_20924_ += ($$5 - p_21044_.f_20924_) * 0.4f;
        p_21044_.f_20925_ += p_21044_.f_20924_;
    }

    public Vec3 m_21074_(Vec3 p_21075_, float p_21076_) {
        this.m_19920_(this.m_21330_(p_21076_), p_21075_);
        this.m_20256_(this.m_21297_(this.m_20184_()));
        this.m_6478_(MoverType.SELF, this.m_20184_());
        Vec3 $$2 = this.m_20184_();
        if ((this.f_19862_ || this.f_20899_) && (this.m_6147_() || this.m_146900_().m_60713_(Blocks.f_152499_) && PowderSnowBlock.m_154255_(this))) {
            $$2 = new Vec3($$2.f_82479_, 0.2, $$2.f_82481_);
        }
        return $$2;
    }

    public Vec3 m_20994_(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
        if (!this.m_20068_() && !this.m_20142_()) {
            double $$4;
            if (p_20996_ && Math.abs(p_20997_.f_82480_ - 0.005) >= 0.003 && Math.abs(p_20997_.f_82480_ - p_20995_ / 16.0) < 0.003) {
                double $$3 = -0.003;
            } else {
                $$4 = p_20997_.f_82480_ - p_20995_ / 16.0;
            }
            return new Vec3(p_20997_.f_82479_, $$4, p_20997_.f_82481_);
        }
        return p_20997_;
    }

    private Vec3 m_21297_(Vec3 p_21298_) {
        if (this.m_6147_()) {
            this.m_183634_();
            float $$1 = 0.15f;
            double $$2 = Mth.m_14008_(p_21298_.f_82479_, -0.15f, 0.15f);
            double $$3 = Mth.m_14008_(p_21298_.f_82481_, -0.15f, 0.15f);
            double $$4 = Math.max(p_21298_.f_82480_, (double)-0.15f);
            if ($$4 < 0.0 && !this.m_146900_().m_60713_(Blocks.f_50616_) && this.m_5791_() && this instanceof Player) {
                $$4 = 0.0;
            }
            p_21298_ = new Vec3($$2, $$4, $$3);
        }
        return p_21298_;
    }

    private float m_21330_(float p_21331_) {
        if (this.f_19861_) {
            return this.m_6113_() * (0.21600002f / (p_21331_ * p_21331_ * p_21331_));
        }
        return this.f_20887_;
    }

    public float m_6113_() {
        return this.f_20953_;
    }

    public void m_7910_(float p_21320_) {
        this.f_20953_ = p_21320_;
    }

    public boolean m_7327_(Entity p_20970_) {
        this.m_21335_(p_20970_);
        return false;
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        this.m_21329_();
        this.m_21333_();
        if (!this.f_19853_.f_46443_) {
            int $$1;
            int $$0 = this.m_21234_();
            if ($$0 > 0) {
                if (this.f_20914_ <= 0) {
                    this.f_20914_ = 20 * (30 - $$0);
                }
                --this.f_20914_;
                if (this.f_20914_ <= 0) {
                    this.m_21317_($$0 - 1);
                }
            }
            if (($$1 = this.m_21235_()) > 0) {
                if (this.f_20915_ <= 0) {
                    this.f_20915_ = 20 * (30 - $$1);
                }
                --this.f_20915_;
                if (this.f_20915_ <= 0) {
                    this.m_21321_($$1 - 1);
                }
            }
            this.m_21315_();
            if (this.f_19797_ % 20 == 0) {
                this.m_21231_().m_19296_();
            }
            if (this.m_5803_() && !this.m_21334_()) {
                this.m_5796_();
            }
        }
        if (!this.m_213877_()) {
            this.m_8107_();
        }
        double $$2 = this.m_20185_() - this.f_19854_;
        double $$3 = this.m_20189_() - this.f_19856_;
        float $$4 = (float)($$2 * $$2 + $$3 * $$3);
        float $$5 = this.f_20883_;
        float $$6 = 0.0f;
        this.f_20892_ = this.f_20893_;
        float $$7 = 0.0f;
        if ($$4 > 0.0025000002f) {
            $$7 = 1.0f;
            $$6 = (float)Math.sqrt($$4) * 3.0f;
            float $$8 = (float)Mth.m_14136_($$3, $$2) * 57.295776f - 90.0f;
            float $$9 = Mth.m_14154_(Mth.m_14177_(this.m_146908_()) - $$8);
            $$5 = 95.0f < $$9 && $$9 < 265.0f ? $$8 - 180.0f : $$8;
        }
        if (this.f_20921_ > 0.0f) {
            $$5 = this.m_146908_();
        }
        if (!this.f_19861_) {
            $$7 = 0.0f;
        }
        this.f_20893_ += ($$7 - this.f_20893_) * 0.3f;
        this.f_19853_.m_46473_().m_6180_("headTurn");
        $$6 = this.m_5632_($$5, $$6);
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("rangeChecks");
        while (this.m_146908_() - this.f_19859_ < -180.0f) {
            this.f_19859_ -= 360.0f;
        }
        while (this.m_146908_() - this.f_19859_ >= 180.0f) {
            this.f_19859_ += 360.0f;
        }
        while (this.f_20883_ - this.f_20884_ < -180.0f) {
            this.f_20884_ -= 360.0f;
        }
        while (this.f_20883_ - this.f_20884_ >= 180.0f) {
            this.f_20884_ += 360.0f;
        }
        while (this.m_146909_() - this.f_19860_ < -180.0f) {
            this.f_19860_ -= 360.0f;
        }
        while (this.m_146909_() - this.f_19860_ >= 180.0f) {
            this.f_19860_ += 360.0f;
        }
        while (this.f_20885_ - this.f_20886_ < -180.0f) {
            this.f_20886_ -= 360.0f;
        }
        while (this.f_20885_ - this.f_20886_ >= 180.0f) {
            this.f_20886_ += 360.0f;
        }
        this.f_19853_.m_46473_().m_7238_();
        this.f_20894_ += $$6;
        this.f_20937_ = this.m_21255_() ? ++this.f_20937_ : 0;
        if (this.m_5803_()) {
            this.m_146926_(0.0f);
        }
    }

    private void m_21315_() {
        Map<EquipmentSlot, ItemStack> $$0 = this.m_21319_();
        if ($$0 != null) {
            this.m_21091_($$0);
            if (!$$0.isEmpty()) {
                this.m_21142_($$0);
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private Map<EquipmentSlot, ItemStack> m_21319_() {
        EnumMap $$0 = null;
        block4: for (EquipmentSlot $$1 : EquipmentSlot.values()) {
            void $$4;
            switch ($$1.m_20743_()) {
                case HAND: {
                    ItemStack $$2 = this.m_21244_($$1);
                    break;
                }
                case ARMOR: {
                    ItemStack $$3 = this.m_21198_($$1);
                    break;
                }
                default: {
                    continue block4;
                }
            }
            ItemStack $$5 = this.m_6844_($$1);
            if (ItemStack.m_41728_($$5, (ItemStack)$$4)) continue;
            if ($$0 == null) {
                $$0 = Maps.newEnumMap(EquipmentSlot.class);
            }
            $$0.put($$1, $$5);
            if (!$$4.m_41619_()) {
                this.m_21204_().m_22161_($$4.m_41638_($$1));
            }
            if ($$5.m_41619_()) continue;
            this.m_21204_().m_22178_($$5.m_41638_($$1));
        }
        return $$0;
    }

    private void m_21091_(Map<EquipmentSlot, ItemStack> p_21092_) {
        ItemStack $$1 = p_21092_.get((Object)EquipmentSlot.MAINHAND);
        ItemStack $$2 = p_21092_.get((Object)EquipmentSlot.OFFHAND);
        if ($$1 != null && $$2 != null && ItemStack.m_41728_($$1, this.m_21244_(EquipmentSlot.OFFHAND)) && ItemStack.m_41728_($$2, this.m_21244_(EquipmentSlot.MAINHAND))) {
            ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundEntityEventPacket(this, 55));
            p_21092_.remove((Object)EquipmentSlot.MAINHAND);
            p_21092_.remove((Object)EquipmentSlot.OFFHAND);
            this.m_21168_(EquipmentSlot.MAINHAND, $$1.m_41777_());
            this.m_21168_(EquipmentSlot.OFFHAND, $$2.m_41777_());
        }
    }

    private void m_21142_(Map<EquipmentSlot, ItemStack> p_21143_) {
        ArrayList $$1 = Lists.newArrayListWithCapacity((int)p_21143_.size());
        p_21143_.forEach((p_147204_, p_147205_) -> {
            ItemStack $$3 = p_147205_.m_41777_();
            $$1.add(Pair.of((Object)p_147204_, (Object)$$3));
            switch (p_147204_.m_20743_()) {
                case HAND: {
                    this.m_21168_((EquipmentSlot)((Object)p_147204_), $$3);
                    break;
                }
                case ARMOR: {
                    this.m_21128_((EquipmentSlot)((Object)p_147204_), $$3);
                }
            }
        });
        ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEquipmentPacket(this.m_19879_(), $$1));
    }

    private ItemStack m_21198_(EquipmentSlot p_21199_) {
        return this.f_20947_.get(p_21199_.m_20749_());
    }

    private void m_21128_(EquipmentSlot p_21129_, ItemStack p_21130_) {
        this.f_20947_.set(p_21129_.m_20749_(), p_21130_);
    }

    private ItemStack m_21244_(EquipmentSlot p_21245_) {
        return this.f_20946_.get(p_21245_.m_20749_());
    }

    private void m_21168_(EquipmentSlot p_21169_, ItemStack p_21170_) {
        this.f_20946_.set(p_21169_.m_20749_(), p_21170_);
    }

    protected float m_5632_(float p_21260_, float p_21261_) {
        boolean $$4;
        float $$2 = Mth.m_14177_(p_21260_ - this.f_20883_);
        this.f_20883_ += $$2 * 0.3f;
        float $$3 = Mth.m_14177_(this.m_146908_() - this.f_20883_);
        boolean bl = $$4 = $$3 < -90.0f || $$3 >= 90.0f;
        if ($$3 < -75.0f) {
            $$3 = -75.0f;
        }
        if ($$3 >= 75.0f) {
            $$3 = 75.0f;
        }
        this.f_20883_ = this.m_146908_() - $$3;
        if ($$3 * $$3 > 2500.0f) {
            this.f_20883_ += $$3 * 0.2f;
        }
        if ($$4) {
            p_21261_ *= -1.0f;
        }
        return p_21261_;
    }

    public void m_8107_() {
        if (this.f_20954_ > 0) {
            --this.f_20954_;
        }
        if (this.m_6109_()) {
            this.f_20903_ = 0;
            this.m_217006_(this.m_20185_(), this.m_20186_(), this.m_20189_());
        }
        if (this.f_20903_ > 0) {
            double $$0 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
            double $$1 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
            double $$2 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
            double $$3 = Mth.m_14175_(this.f_20907_ - (double)this.m_146908_());
            this.m_146922_(this.m_146908_() + (float)$$3 / (float)this.f_20903_);
            this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
            --this.f_20903_;
            this.m_6034_($$0, $$1, $$2);
            this.m_19915_(this.m_146908_(), this.m_146909_());
        } else if (!this.m_6142_()) {
            this.m_20256_(this.m_20184_().m_82490_(0.98));
        }
        if (this.f_20934_ > 0) {
            this.f_20885_ += (float)Mth.m_14175_(this.f_20933_ - (double)this.f_20885_) / (float)this.f_20934_;
            --this.f_20934_;
        }
        Vec3 $$4 = this.m_20184_();
        double $$5 = $$4.f_82479_;
        double $$6 = $$4.f_82480_;
        double $$7 = $$4.f_82481_;
        if (Math.abs($$4.f_82479_) < 0.003) {
            $$5 = 0.0;
        }
        if (Math.abs($$4.f_82480_) < 0.003) {
            $$6 = 0.0;
        }
        if (Math.abs($$4.f_82481_) < 0.003) {
            $$7 = 0.0;
        }
        this.m_20334_($$5, $$6, $$7);
        this.f_19853_.m_46473_().m_6180_("ai");
        if (this.m_6107_()) {
            this.f_20899_ = false;
            this.f_20900_ = 0.0f;
            this.f_20902_ = 0.0f;
        } else if (this.m_6142_()) {
            this.f_19853_.m_46473_().m_6180_("newAi");
            this.m_6140_();
            this.f_19853_.m_46473_().m_7238_();
        }
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("jump");
        if (this.f_20899_ && this.m_6129_()) {
            double $$9;
            if (this.m_20077_()) {
                double $$8 = this.m_204036_(FluidTags.f_13132_);
            } else {
                $$9 = this.m_204036_(FluidTags.f_13131_);
            }
            boolean $$10 = this.m_20069_() && $$9 > 0.0;
            double $$11 = this.m_20204_();
            if ($$10 && (!this.f_19861_ || $$9 > $$11)) {
                this.m_203347_(FluidTags.f_13131_);
            } else if (this.m_20077_() && (!this.f_19861_ || $$9 > $$11)) {
                this.m_203347_(FluidTags.f_13132_);
            } else if ((this.f_19861_ || $$10 && $$9 <= $$11) && this.f_20954_ == 0) {
                this.m_6135_();
                this.f_20954_ = 10;
            }
        } else {
            this.f_20954_ = 0;
        }
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("travel");
        this.f_20900_ *= 0.98f;
        this.f_20902_ *= 0.98f;
        this.m_21323_();
        AABB $$12 = this.m_20191_();
        this.m_7023_(new Vec3(this.f_20900_, this.f_20901_, this.f_20902_));
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("freezing");
        boolean $$13 = this.m_6095_().m_204039_(EntityTypeTags.f_144295_);
        if (!this.f_19853_.f_46443_ && !this.m_21224_()) {
            int $$14 = this.m_146888_();
            if (this.f_146808_ && this.m_142079_()) {
                this.m_146917_(Math.min(this.m_146891_(), $$14 + 1));
            } else {
                this.m_146917_(Math.max(0, $$14 - 2));
            }
        }
        this.m_147225_();
        this.m_147226_();
        if (!this.f_19853_.f_46443_ && this.f_19797_ % 40 == 0 && this.m_146890_() && this.m_142079_()) {
            int $$15 = $$13 ? 5 : 1;
            this.m_6469_(DamageSource.f_146701_, $$15);
        }
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("push");
        if (this.f_20938_ > 0) {
            --this.f_20938_;
            this.m_21071_($$12, this.m_20191_());
        }
        this.m_6138_();
        this.f_19853_.m_46473_().m_7238_();
        if (!this.f_19853_.f_46443_ && this.m_6126_() && this.m_20071_()) {
            this.m_6469_(DamageSource.f_19312_, 1.0f);
        }
    }

    public boolean m_6126_() {
        return false;
    }

    private void m_21323_() {
        boolean $$0 = this.m_20291_(7);
        if ($$0 && !this.f_19861_ && !this.m_20159_() && !this.m_21023_(MobEffects.f_19620_)) {
            ItemStack $$1 = this.m_6844_(EquipmentSlot.CHEST);
            if ($$1.m_150930_(Items.f_42741_) && ElytraItem.m_41140_($$1)) {
                $$0 = true;
                int $$2 = this.f_20937_ + 1;
                if (!this.f_19853_.f_46443_ && $$2 % 10 == 0) {
                    int $$3 = $$2 / 10;
                    if ($$3 % 2 == 0) {
                        $$1.m_41622_(1, this, p_147232_ -> p_147232_.m_21166_(EquipmentSlot.CHEST));
                    }
                    this.m_146850_(GameEvent.f_223705_);
                }
            } else {
                $$0 = false;
            }
        } else {
            $$0 = false;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_20115_(7, $$0);
        }
    }

    protected void m_6140_() {
    }

    protected void m_6138_() {
        List<Entity> $$0 = this.f_19853_.m_6249_(this, this.m_20191_(), EntitySelector.m_20421_(this));
        if (!$$0.isEmpty()) {
            int $$1 = this.f_19853_.m_46469_().m_46215_(GameRules.f_46149_);
            if ($$1 > 0 && $$0.size() > $$1 - 1 && this.f_19796_.m_188503_(4) == 0) {
                int $$2 = 0;
                for (int $$3 = 0; $$3 < $$0.size(); ++$$3) {
                    if ($$0.get($$3).m_20159_()) continue;
                    ++$$2;
                }
                if ($$2 > $$1 - 1) {
                    this.m_6469_(DamageSource.f_19311_, 6.0f);
                }
            }
            for (int $$4 = 0; $$4 < $$0.size(); ++$$4) {
                Entity $$5 = $$0.get($$4);
                this.m_7324_($$5);
            }
        }
    }

    protected void m_21071_(AABB p_21072_, AABB p_21073_) {
        AABB $$2 = p_21072_.m_82367_(p_21073_);
        List<Entity> $$3 = this.f_19853_.m_45933_(this, $$2);
        if (!$$3.isEmpty()) {
            for (int $$4 = 0; $$4 < $$3.size(); ++$$4) {
                Entity $$5 = $$3.get($$4);
                if (!($$5 instanceof LivingEntity)) continue;
                this.m_6727_((LivingEntity)$$5);
                this.f_20938_ = 0;
                this.m_20256_(this.m_20184_().m_82490_(-0.2));
                break;
            }
        } else if (this.f_19862_) {
            this.f_20938_ = 0;
        }
        if (!this.f_19853_.f_46443_ && this.f_20938_ <= 0) {
            this.m_21155_(4, false);
        }
    }

    protected void m_7324_(Entity p_20971_) {
        p_20971_.m_7334_(this);
    }

    protected void m_6727_(LivingEntity p_21277_) {
    }

    public boolean m_21209_() {
        return (this.f_19804_.m_135370_(f_20909_) & 4) != 0;
    }

    @Override
    public void m_8127_() {
        Entity $$0 = this.m_20202_();
        super.m_8127_();
        if ($$0 != null && $$0 != this.m_20202_() && !this.f_19853_.f_46443_) {
            this.m_21028_($$0);
        }
    }

    @Override
    public void m_6083_() {
        super.m_6083_();
        this.f_20892_ = this.f_20893_;
        this.f_20893_ = 0.0f;
        this.m_183634_();
    }

    @Override
    public void m_6453_(double p_20977_, double p_20978_, double p_20979_, float p_20980_, float p_20981_, int p_20982_, boolean p_20983_) {
        this.f_20904_ = p_20977_;
        this.f_20905_ = p_20978_;
        this.f_20906_ = p_20979_;
        this.f_20907_ = p_20980_;
        this.f_20908_ = p_20981_;
        this.f_20903_ = p_20982_;
    }

    @Override
    public void m_6541_(float p_21005_, int p_21006_) {
        this.f_20933_ = p_21005_;
        this.f_20934_ = p_21006_;
    }

    public void m_6862_(boolean p_21314_) {
        this.f_20899_ = p_21314_;
    }

    public void m_21053_(ItemEntity p_21054_) {
        Player $$1;
        Player player = $$1 = p_21054_.m_32057_() != null ? this.f_19853_.m_46003_(p_21054_.m_32057_()) : null;
        if ($$1 instanceof ServerPlayer) {
            CriteriaTriggers.f_215654_.m_221298_((ServerPlayer)$$1, p_21054_.m_32055_(), this);
        }
    }

    public void m_7938_(Entity p_21030_, int p_21031_) {
        if (!p_21030_.m_213877_() && !this.f_19853_.f_46443_ && (p_21030_ instanceof ItemEntity || p_21030_ instanceof AbstractArrow || p_21030_ instanceof ExperienceOrb)) {
            ((ServerLevel)this.f_19853_).m_7726_().m_8445_(p_21030_, new ClientboundTakeItemEntityPacket(p_21030_.m_19879_(), this.m_19879_(), p_21031_));
        }
    }

    public boolean m_142582_(Entity p_147185_) {
        if (p_147185_.f_19853_ != this.f_19853_) {
            return false;
        }
        Vec3 $$1 = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
        Vec3 $$2 = new Vec3(p_147185_.m_20185_(), p_147185_.m_20188_(), p_147185_.m_20189_());
        if ($$2.m_82554_($$1) > 128.0) {
            return false;
        }
        return this.f_19853_.m_45547_(new ClipContext($$1, $$2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).m_6662_() == HitResult.Type.MISS;
    }

    @Override
    public float m_5675_(float p_21286_) {
        if (p_21286_ == 1.0f) {
            return this.f_20885_;
        }
        return Mth.m_14179_(p_21286_, this.f_20886_, this.f_20885_);
    }

    public float m_21324_(float p_21325_) {
        float $$1 = this.f_20921_ - this.f_20920_;
        if ($$1 < 0.0f) {
            $$1 += 1.0f;
        }
        return this.f_20920_ + $$1 * p_21325_;
    }

    public boolean m_6142_() {
        return !this.f_19853_.f_46443_;
    }

    @Override
    public boolean m_6087_() {
        return !this.m_213877_();
    }

    @Override
    public boolean m_6094_() {
        return this.m_6084_() && !this.m_5833_() && !this.m_6147_();
    }

    @Override
    public float m_6080_() {
        return this.f_20885_;
    }

    @Override
    public void m_5616_(float p_21306_) {
        this.f_20885_ = p_21306_;
    }

    @Override
    public void m_5618_(float p_21309_) {
        this.f_20883_ = p_21309_;
    }

    @Override
    protected Vec3 m_7643_(Direction.Axis p_21085_, BlockUtil.FoundRectangle p_21086_) {
        return LivingEntity.m_21289_(super.m_7643_(p_21085_, p_21086_));
    }

    public static Vec3 m_21289_(Vec3 p_21290_) {
        return new Vec3(p_21290_.f_82479_, p_21290_.f_82480_, 0.0);
    }

    public float m_6103_() {
        return this.f_20955_;
    }

    public void m_7911_(float p_21328_) {
        if (p_21328_ < 0.0f) {
            p_21328_ = 0.0f;
        }
        this.f_20955_ = p_21328_;
    }

    public void m_8108_() {
    }

    public void m_8098_() {
    }

    protected void m_21210_() {
        this.f_20948_ = true;
    }

    public abstract HumanoidArm m_5737_();

    public boolean m_6117_() {
        return (this.f_19804_.m_135370_(f_20909_) & 1) > 0;
    }

    public InteractionHand m_7655_() {
        return (this.f_19804_.m_135370_(f_20909_) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    private void m_21329_() {
        if (this.m_6117_()) {
            if (ItemStack.m_41758_(this.m_21120_(this.m_7655_()), this.f_20935_)) {
                this.f_20935_ = this.m_21120_(this.m_7655_());
                this.m_142106_(this.f_20935_);
            } else {
                this.m_5810_();
            }
        }
    }

    protected void m_142106_(ItemStack p_147201_) {
        p_147201_.m_41731_(this.f_19853_, this, this.m_21212_());
        if (this.m_21332_()) {
            this.m_21137_(p_147201_, 5);
        }
        if (--this.f_20936_ == 0 && !this.f_19853_.f_46443_ && !p_147201_.m_41781_()) {
            this.m_8095_();
        }
    }

    private boolean m_21332_() {
        int $$0 = this.m_21212_();
        FoodProperties $$1 = this.f_20935_.m_41720_().m_41473_();
        boolean $$2 = $$1 != null && $$1.m_38748_();
        return ($$2 |= $$0 <= this.f_20935_.m_41779_() - 7) && $$0 % 4 == 0;
    }

    private void m_21333_() {
        this.f_20932_ = this.f_20931_;
        this.f_20931_ = this.m_6067_() ? Math.min(1.0f, this.f_20931_ + 0.09f) : Math.max(0.0f, this.f_20931_ - 0.09f);
    }

    protected void m_21155_(int p_21156_, boolean p_21157_) {
        int $$2 = this.f_19804_.m_135370_(f_20909_).byteValue();
        $$2 = p_21157_ ? ($$2 |= p_21156_) : ($$2 &= ~p_21156_);
        this.f_19804_.m_135381_(f_20909_, (byte)$$2);
    }

    public void m_6672_(InteractionHand p_21159_) {
        ItemStack $$1 = this.m_21120_(p_21159_);
        if ($$1.m_41619_() || this.m_6117_()) {
            return;
        }
        this.f_20935_ = $$1;
        this.f_20936_ = $$1.m_41779_();
        if (!this.f_19853_.f_46443_) {
            this.m_21155_(1, true);
            this.m_21155_(2, p_21159_ == InteractionHand.OFF_HAND);
            this.m_146850_(GameEvent.f_223698_);
        }
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_21104_) {
        super.m_7350_(p_21104_);
        if (f_20942_.equals(p_21104_)) {
            if (this.f_19853_.f_46443_) {
                this.m_21257_().ifPresent(this::m_21080_);
            }
        } else if (f_20909_.equals(p_21104_) && this.f_19853_.f_46443_) {
            if (this.m_6117_() && this.f_20935_.m_41619_()) {
                this.f_20935_ = this.m_21120_(this.m_7655_());
                if (!this.f_20935_.m_41619_()) {
                    this.f_20936_ = this.f_20935_.m_41779_();
                }
            } else if (!this.m_6117_() && !this.f_20935_.m_41619_()) {
                this.f_20935_ = ItemStack.f_41583_;
                this.f_20936_ = 0;
            }
        }
    }

    @Override
    public void m_7618_(EntityAnchorArgument.Anchor p_21078_, Vec3 p_21079_) {
        super.m_7618_(p_21078_, p_21079_);
        this.f_20886_ = this.f_20885_;
        this.f_20884_ = this.f_20883_ = this.f_20885_;
    }

    protected void m_21137_(ItemStack p_21138_, int p_21139_) {
        if (p_21138_.m_41619_() || !this.m_6117_()) {
            return;
        }
        if (p_21138_.m_41780_() == UseAnim.DRINK) {
            this.m_5496_(this.m_7838_(p_21138_), 0.5f, this.f_19853_.f_46441_.m_188501_() * 0.1f + 0.9f);
        }
        if (p_21138_.m_41780_() == UseAnim.EAT) {
            this.m_21060_(p_21138_, p_21139_);
            this.m_5496_(this.m_7866_(p_21138_), 0.5f + 0.5f * (float)this.f_19796_.m_188503_(2), (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
        }
    }

    private void m_21060_(ItemStack p_21061_, int p_21062_) {
        for (int $$2 = 0; $$2 < p_21062_; ++$$2) {
            Vec3 $$3 = new Vec3(((double)this.f_19796_.m_188501_() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
            $$3 = $$3.m_82496_(-this.m_146909_() * ((float)Math.PI / 180));
            $$3 = $$3.m_82524_(-this.m_146908_() * ((float)Math.PI / 180));
            double $$4 = (double)(-this.f_19796_.m_188501_()) * 0.6 - 0.3;
            Vec3 $$5 = new Vec3(((double)this.f_19796_.m_188501_() - 0.5) * 0.3, $$4, 0.6);
            $$5 = $$5.m_82496_(-this.m_146909_() * ((float)Math.PI / 180));
            $$5 = $$5.m_82524_(-this.m_146908_() * ((float)Math.PI / 180));
            $$5 = $$5.m_82520_(this.m_20185_(), this.m_20188_(), this.m_20189_());
            this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, p_21061_), $$5.f_82479_, $$5.f_82480_, $$5.f_82481_, $$3.f_82479_, $$3.f_82480_ + 0.05, $$3.f_82481_);
        }
    }

    protected void m_8095_() {
        if (this.f_19853_.f_46443_ && !this.m_6117_()) {
            return;
        }
        InteractionHand $$0 = this.m_7655_();
        if (!this.f_20935_.equals(this.m_21120_($$0))) {
            this.m_21253_();
            return;
        }
        if (!this.f_20935_.m_41619_() && this.m_6117_()) {
            this.m_21137_(this.f_20935_, 16);
            ItemStack $$1 = this.f_20935_.m_41671_(this.f_19853_, this);
            if ($$1 != this.f_20935_) {
                this.m_21008_($$0, $$1);
            }
            this.m_5810_();
        }
    }

    public ItemStack m_21211_() {
        return this.f_20935_;
    }

    public int m_21212_() {
        return this.f_20936_;
    }

    public int m_21252_() {
        if (this.m_6117_()) {
            return this.f_20935_.m_41779_() - this.m_21212_();
        }
        return 0;
    }

    public void m_21253_() {
        if (!this.f_20935_.m_41619_()) {
            this.f_20935_.m_41674_(this.f_19853_, this, this.m_21212_());
            if (this.f_20935_.m_41781_()) {
                this.m_21329_();
            }
        }
        this.m_5810_();
    }

    public void m_5810_() {
        if (!this.f_19853_.f_46443_) {
            boolean $$0 = this.m_6117_();
            this.m_21155_(1, false);
            if ($$0) {
                this.m_146850_(GameEvent.f_223697_);
            }
        }
        this.f_20935_ = ItemStack.f_41583_;
        this.f_20936_ = 0;
    }

    public boolean m_21254_() {
        if (!this.m_6117_() || this.f_20935_.m_41619_()) {
            return false;
        }
        Item $$0 = this.f_20935_.m_41720_();
        if ($$0.m_6164_(this.f_20935_) != UseAnim.BLOCK) {
            return false;
        }
        return $$0.m_8105_(this.f_20935_) - this.f_20936_ >= 5;
    }

    public boolean m_5791_() {
        return this.m_6144_();
    }

    public boolean m_21255_() {
        return this.m_20291_(7);
    }

    @Override
    public boolean m_6067_() {
        return super.m_6067_() || !this.m_21255_() && this.m_217003_(Pose.FALL_FLYING);
    }

    public int m_21256_() {
        return this.f_20937_;
    }

    public boolean m_20984_(double p_20985_, double p_20986_, double p_20987_, boolean p_20988_) {
        double $$4 = this.m_20185_();
        double $$5 = this.m_20186_();
        double $$6 = this.m_20189_();
        double $$7 = p_20986_;
        boolean $$8 = false;
        Level $$10 = this.f_19853_;
        BlockPos $$9 = new BlockPos(p_20985_, $$7, p_20987_);
        if ($$10.m_46805_($$9)) {
            boolean $$11 = false;
            while (!$$11 && $$9.m_123342_() > $$10.m_141937_()) {
                BlockPos $$12 = $$9.m_7495_();
                BlockState $$13 = $$10.m_8055_($$12);
                if ($$13.m_60767_().m_76334_()) {
                    $$11 = true;
                    continue;
                }
                $$7 -= 1.0;
                $$9 = $$12;
            }
            if ($$11) {
                this.m_6021_(p_20985_, $$7, p_20987_);
                if ($$10.m_45786_(this) && !$$10.m_46855_(this.m_20191_())) {
                    $$8 = true;
                }
            }
        }
        if (!$$8) {
            this.m_6021_($$4, $$5, $$6);
            return false;
        }
        if (p_20988_) {
            $$10.m_7605_(this, (byte)46);
        }
        if (this instanceof PathfinderMob) {
            ((PathfinderMob)this).m_21573_().m_26573_();
        }
        return true;
    }

    public boolean m_5801_() {
        return true;
    }

    public boolean m_5789_() {
        return true;
    }

    public void m_6818_(BlockPos p_21082_, boolean p_21083_) {
    }

    public boolean m_7066_(ItemStack p_21249_) {
        return false;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public EntityDimensions m_6972_(Pose p_21047_) {
        return p_21047_ == Pose.SLEEPING ? f_20910_ : super.m_6972_(p_21047_).m_20388_(this.m_6134_());
    }

    public ImmutableList<Pose> m_7431_() {
        return ImmutableList.of((Object)((Object)Pose.STANDING));
    }

    public AABB m_21270_(Pose p_21271_) {
        EntityDimensions $$1 = this.m_6972_(p_21271_);
        return new AABB(-$$1.f_20377_ / 2.0f, 0.0, -$$1.f_20377_ / 2.0f, $$1.f_20377_ / 2.0f, $$1.f_20378_, $$1.f_20377_ / 2.0f);
    }

    public Optional<BlockPos> m_21257_() {
        return this.f_19804_.m_135370_(f_20942_);
    }

    public void m_21250_(BlockPos p_21251_) {
        this.f_19804_.m_135381_(f_20942_, Optional.of(p_21251_));
    }

    public void m_21258_() {
        this.f_19804_.m_135381_(f_20942_, Optional.empty());
    }

    public boolean m_5803_() {
        return this.m_21257_().isPresent();
    }

    public void m_5802_(BlockPos p_21141_) {
        BlockState $$1;
        if (this.m_20159_()) {
            this.m_8127_();
        }
        if (($$1 = this.f_19853_.m_8055_(p_21141_)).m_60734_() instanceof BedBlock) {
            this.f_19853_.m_7731_(p_21141_, (BlockState)$$1.m_61124_(BedBlock.f_49441_, true), 3);
        }
        this.m_20124_(Pose.SLEEPING);
        this.m_21080_(p_21141_);
        this.m_21250_(p_21141_);
        this.m_20256_(Vec3.f_82478_);
        this.f_19812_ = true;
    }

    private void m_21080_(BlockPos p_21081_) {
        this.m_6034_((double)p_21081_.m_123341_() + 0.5, (double)p_21081_.m_123342_() + 0.6875, (double)p_21081_.m_123343_() + 0.5);
    }

    private boolean m_21334_() {
        return this.m_21257_().map(p_147236_ -> this.f_19853_.m_8055_((BlockPos)p_147236_).m_60734_() instanceof BedBlock).orElse(false);
    }

    public void m_5796_() {
        this.m_21257_().filter(this.f_19853_::m_46805_).ifPresent(p_147228_ -> {
            BlockState $$1 = this.f_19853_.m_8055_((BlockPos)p_147228_);
            if ($$1.m_60734_() instanceof BedBlock) {
                this.f_19853_.m_7731_((BlockPos)p_147228_, (BlockState)$$1.m_61124_(BedBlock.f_49441_, false), 3);
                Vec3 $$2 = BedBlock.m_49458_(this.m_6095_(), this.f_19853_, p_147228_, this.m_146908_()).orElseGet(() -> {
                    BlockPos $$1 = p_147228_.m_7494_();
                    return new Vec3((double)$$1.m_123341_() + 0.5, (double)$$1.m_123342_() + 0.1, (double)$$1.m_123343_() + 0.5);
                });
                Vec3 $$3 = Vec3.m_82539_(p_147228_).m_82546_($$2).m_82541_();
                float $$4 = (float)Mth.m_14175_(Mth.m_14136_($$3.f_82481_, $$3.f_82479_) * 57.2957763671875 - 90.0);
                this.m_6034_($$2.f_82479_, $$2.f_82480_, $$2.f_82481_);
                this.m_146922_($$4);
                this.m_146926_(0.0f);
            }
        });
        Vec3 $$0 = this.m_20182_();
        this.m_20124_(Pose.STANDING);
        this.m_6034_($$0.f_82479_, $$0.f_82480_, $$0.f_82481_);
        this.m_21258_();
    }

    @Nullable
    public Direction m_21259_() {
        BlockPos $$0 = this.m_21257_().orElse(null);
        return $$0 != null ? BedBlock.m_49485_(this.f_19853_, $$0) : null;
    }

    @Override
    public boolean m_5830_() {
        return !this.m_5803_() && super.m_5830_();
    }

    @Override
    protected final float m_6380_(Pose p_21049_, EntityDimensions p_21050_) {
        return p_21049_ == Pose.SLEEPING ? 0.2f : this.m_6431_(p_21049_, p_21050_);
    }

    protected float m_6431_(Pose p_21131_, EntityDimensions p_21132_) {
        return super.m_6380_(p_21131_, p_21132_);
    }

    public ItemStack m_6298_(ItemStack p_21272_) {
        return ItemStack.f_41583_;
    }

    public ItemStack m_5584_(Level p_21067_, ItemStack p_21068_) {
        if (p_21068_.m_41614_()) {
            p_21067_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7866_(p_21068_), SoundSource.NEUTRAL, 1.0f, 1.0f + (p_21067_.f_46441_.m_188501_() - p_21067_.f_46441_.m_188501_()) * 0.4f);
            this.m_21063_(p_21068_, p_21067_, this);
            if (!(this instanceof Player) || !((Player)this).m_150110_().f_35937_) {
                p_21068_.m_41774_(1);
            }
            this.m_146850_(GameEvent.f_157806_);
        }
        return p_21068_;
    }

    private void m_21063_(ItemStack p_21064_, Level p_21065_, LivingEntity p_21066_) {
        Item $$3 = p_21064_.m_41720_();
        if ($$3.m_41472_()) {
            List<Pair<MobEffectInstance, Float>> $$4 = $$3.m_41473_().m_38749_();
            for (Pair<MobEffectInstance, Float> $$5 : $$4) {
                if (p_21065_.f_46443_ || $$5.getFirst() == null || !(p_21065_.f_46441_.m_188501_() < ((Float)$$5.getSecond()).floatValue())) continue;
                p_21066_.m_7292_(new MobEffectInstance((MobEffectInstance)$$5.getFirst()));
            }
        }
    }

    private static byte m_21266_(EquipmentSlot p_21267_) {
        switch (p_21267_) {
            case MAINHAND: {
                return 47;
            }
            case OFFHAND: {
                return 48;
            }
            case HEAD: {
                return 49;
            }
            case CHEST: {
                return 50;
            }
            case FEET: {
                return 52;
            }
            case LEGS: {
                return 51;
            }
        }
        return 47;
    }

    public void m_21166_(EquipmentSlot p_21167_) {
        this.f_19853_.m_7605_(this, LivingEntity.m_21266_(p_21167_));
    }

    public void m_21190_(InteractionHand p_21191_) {
        this.m_21166_(p_21191_ == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    @Override
    public AABB m_6921_() {
        if (this.m_6844_(EquipmentSlot.HEAD).m_150930_(Items.f_42683_)) {
            float $$0 = 0.5f;
            return this.m_20191_().m_82377_(0.5, 0.5, 0.5);
        }
        return super.m_6921_();
    }

    public static EquipmentSlot m_147233_(ItemStack p_147234_) {
        Item $$1 = p_147234_.m_41720_();
        if (p_147234_.m_150930_(Items.f_42047_) || $$1 instanceof BlockItem && ((BlockItem)$$1).m_40614_() instanceof AbstractSkullBlock) {
            return EquipmentSlot.HEAD;
        }
        if ($$1 instanceof ArmorItem) {
            return ((ArmorItem)$$1).m_40402_();
        }
        if (p_147234_.m_150930_(Items.f_42741_)) {
            return EquipmentSlot.CHEST;
        }
        if (p_147234_.m_150930_(Items.f_42740_)) {
            return EquipmentSlot.OFFHAND;
        }
        return EquipmentSlot.MAINHAND;
    }

    private static SlotAccess m_147195_(LivingEntity p_147196_, EquipmentSlot p_147197_) {
        if (p_147197_ == EquipmentSlot.HEAD || p_147197_ == EquipmentSlot.MAINHAND || p_147197_ == EquipmentSlot.OFFHAND) {
            return SlotAccess.m_147299_(p_147196_, p_147197_);
        }
        return SlotAccess.m_147302_(p_147196_, p_147197_, p_147222_ -> p_147222_.m_41619_() || Mob.m_147233_(p_147222_) == p_147197_);
    }

    @Nullable
    private static EquipmentSlot m_147211_(int p_147212_) {
        if (p_147212_ == 100 + EquipmentSlot.HEAD.m_20749_()) {
            return EquipmentSlot.HEAD;
        }
        if (p_147212_ == 100 + EquipmentSlot.CHEST.m_20749_()) {
            return EquipmentSlot.CHEST;
        }
        if (p_147212_ == 100 + EquipmentSlot.LEGS.m_20749_()) {
            return EquipmentSlot.LEGS;
        }
        if (p_147212_ == 100 + EquipmentSlot.FEET.m_20749_()) {
            return EquipmentSlot.FEET;
        }
        if (p_147212_ == 98) {
            return EquipmentSlot.MAINHAND;
        }
        if (p_147212_ == 99) {
            return EquipmentSlot.OFFHAND;
        }
        return null;
    }

    @Override
    public SlotAccess m_141942_(int p_147238_) {
        EquipmentSlot $$1 = LivingEntity.m_147211_(p_147238_);
        if ($$1 != null) {
            return LivingEntity.m_147195_(this, $$1);
        }
        return super.m_141942_(p_147238_);
    }

    @Override
    public boolean m_142079_() {
        if (this.m_5833_()) {
            return false;
        }
        boolean $$0 = !this.m_6844_(EquipmentSlot.HEAD).m_204117_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.CHEST).m_204117_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.LEGS).m_204117_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.FEET).m_204117_(ItemTags.f_144320_);
        return $$0 && super.m_142079_();
    }

    @Override
    public boolean m_142038_() {
        return !this.f_19853_.m_5776_() && this.m_21023_(MobEffects.f_19619_) || super.m_142038_();
    }

    @Override
    public float m_213816_() {
        return this.f_20883_;
    }

    @Override
    public void m_141965_(ClientboundAddEntityPacket p_217037_) {
        double $$1 = p_217037_.m_131500_();
        double $$2 = p_217037_.m_131501_();
        double $$3 = p_217037_.m_131502_();
        float $$4 = p_217037_.m_237567_();
        float $$5 = p_217037_.m_237566_();
        this.m_217006_($$1, $$2, $$3);
        this.f_20883_ = p_217037_.m_237568_();
        this.f_20885_ = p_217037_.m_237568_();
        this.f_20884_ = this.f_20883_;
        this.f_20886_ = this.f_20885_;
        this.m_20234_(p_217037_.m_131496_());
        this.m_20084_(p_217037_.m_131499_());
        this.m_19890_($$1, $$2, $$3, $$4, $$5);
        this.m_20334_(p_217037_.m_131503_(), p_217037_.m_131504_(), p_217037_.m_131505_());
    }

    public boolean m_213824_() {
        return this.m_21205_().m_41720_() instanceof AxeItem;
    }

    public record Fallsounds(SoundEvent f_196626_, SoundEvent f_196627_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Fallsounds.class, "small;big", "f_196626_", "f_196627_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Fallsounds.class, "small;big", "f_196626_", "f_196627_"}, this);
        }

        @Override
        public final boolean equals(Object p_196634_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Fallsounds.class, "small;big", "f_196626_", "f_196627_"}, this, p_196634_);
        }
    }
}

