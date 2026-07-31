/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.mojang.authlib.GameProfile
 *  com.mojang.datafixers.util.Either
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ChatSender;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.slf4j.Logger;

public abstract class Player
extends LivingEntity {
    private static final Logger f_219722_ = LogUtils.getLogger();
    public static final int f_150082_ = 16;
    public static final int f_150083_ = 20;
    public static final int f_150084_ = 100;
    public static final int f_150085_ = 10;
    public static final int f_150086_ = 200;
    public static final float f_150087_ = 1.5f;
    public static final float f_150088_ = 0.6f;
    public static final float f_150089_ = 0.6f;
    public static final float f_150090_ = 1.62f;
    public static final EntityDimensions f_36088_ = EntityDimensions.m_20395_(0.6f, 1.8f);
    private static final Map<Pose, EntityDimensions> f_36074_ = ImmutableMap.builder().put((Object)Pose.STANDING, (Object)f_36088_).put((Object)Pose.SLEEPING, (Object)f_20910_).put((Object)Pose.FALL_FLYING, (Object)EntityDimensions.m_20395_(0.6f, 0.6f)).put((Object)Pose.SWIMMING, (Object)EntityDimensions.m_20395_(0.6f, 0.6f)).put((Object)Pose.SPIN_ATTACK, (Object)EntityDimensions.m_20395_(0.6f, 0.6f)).put((Object)Pose.CROUCHING, (Object)EntityDimensions.m_20395_(0.6f, 1.5f)).put((Object)Pose.DYING, (Object)EntityDimensions.m_20398_(0.2f, 0.2f)).build();
    private static final int f_150091_ = 25;
    private static final EntityDataAccessor<Float> f_36107_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135029_);
    private static final EntityDataAccessor<Integer> f_36108_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135028_);
    protected static final EntityDataAccessor<Byte> f_36089_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135027_);
    protected static final EntityDataAccessor<Byte> f_36090_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135027_);
    protected static final EntityDataAccessor<CompoundTag> f_36091_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135042_);
    protected static final EntityDataAccessor<CompoundTag> f_36092_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135042_);
    private long f_36109_;
    private final Inventory f_36093_ = new Inventory(this);
    protected PlayerEnderChestContainer f_36094_ = new PlayerEnderChestContainer();
    public final InventoryMenu f_36095_;
    public AbstractContainerMenu f_36096_;
    protected FoodData f_36097_ = new FoodData();
    protected WardenSpawnTracker f_219724_ = new WardenSpawnTracker(0, 0, 0);
    protected int f_36098_;
    public float f_36099_;
    public float f_36100_;
    public int f_36101_;
    public double f_36102_;
    public double f_36103_;
    public double f_36104_;
    public double f_36105_;
    public double f_36106_;
    public double f_36075_;
    private int f_36110_;
    protected boolean f_36076_;
    private final Abilities f_36077_ = new Abilities();
    public int f_36078_;
    public int f_36079_;
    public float f_36080_;
    protected int f_36081_;
    protected final float f_36082_ = 0.02f;
    private int f_36111_;
    private final GameProfile f_36084_;
    @Nullable
    private final ProfilePublicKey f_219725_;
    private boolean f_36085_;
    private ItemStack f_36086_ = ItemStack.f_41583_;
    private final ItemCooldowns f_36087_ = this.m_7478_();
    private Optional<GlobalPos> f_238176_ = Optional.empty();
    @Nullable
    public FishingHook f_36083_;

    public Player(Level p_219727_, BlockPos p_219728_, float p_219729_, GameProfile p_219730_, @Nullable ProfilePublicKey p_219731_) {
        super((EntityType<? extends LivingEntity>)EntityType.f_20532_, p_219727_);
        this.m_20084_(UUIDUtil.m_235875_(p_219730_));
        this.f_36084_ = p_219730_;
        this.f_219725_ = p_219731_;
        this.f_36095_ = new InventoryMenu(this.f_36093_, !p_219727_.f_46443_, this);
        this.f_36096_ = this.f_36095_;
        this.m_7678_((double)p_219728_.m_123341_() + 0.5, p_219728_.m_123342_() + 1, (double)p_219728_.m_123343_() + 0.5, p_219729_, 0.0f);
        this.f_20896_ = 180.0f;
    }

    public boolean m_36187_(Level p_36188_, BlockPos p_36189_, GameType p_36190_) {
        if (!p_36190_.m_46407_()) {
            return false;
        }
        if (p_36190_ == GameType.SPECTATOR) {
            return true;
        }
        if (this.m_36326_()) {
            return false;
        }
        ItemStack $$3 = this.m_21205_();
        return $$3.m_41619_() || !$$3.m_204128_(p_36188_.m_5962_().m_175515_(Registry.f_122901_), new BlockInWorld(p_36188_, p_36189_, false));
    }

    public static AttributeSupplier.Builder m_36340_() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 1.0).m_22268_(Attributes.f_22279_, 0.1f).m_22266_(Attributes.f_22283_).m_22266_(Attributes.f_22286_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_36107_, Float.valueOf(0.0f));
        this.f_19804_.m_135372_(f_36108_, 0);
        this.f_19804_.m_135372_(f_36089_, (byte)0);
        this.f_19804_.m_135372_(f_36090_, (byte)1);
        this.f_19804_.m_135372_(f_36091_, new CompoundTag());
        this.f_19804_.m_135372_(f_36092_, new CompoundTag());
    }

    @Override
    public void m_8119_() {
        this.f_19794_ = this.m_5833_();
        if (this.m_5833_()) {
            this.f_19861_ = false;
        }
        if (this.f_36101_ > 0) {
            --this.f_36101_;
        }
        if (this.m_5803_()) {
            ++this.f_36110_;
            if (this.f_36110_ > 100) {
                this.f_36110_ = 100;
            }
            if (!this.f_19853_.f_46443_ && this.f_19853_.m_46461_()) {
                this.m_6145_(false, true);
            }
        } else if (this.f_36110_ > 0) {
            ++this.f_36110_;
            if (this.f_36110_ >= 110) {
                this.f_36110_ = 0;
            }
        }
        this.m_7602_();
        super.m_8119_();
        if (!this.f_19853_.f_46443_ && this.f_36096_ != null && !this.f_36096_.m_6875_(this)) {
            this.m_6915_();
            this.f_36096_ = this.f_36095_;
        }
        this.m_36377_();
        if (!this.f_19853_.f_46443_) {
            this.f_36097_.m_38710_(this);
            this.f_219724_.m_219571_();
            this.m_36220_(Stats.f_144255_);
            this.m_36220_(Stats.f_144256_);
            if (this.m_6084_()) {
                this.m_36220_(Stats.f_12991_);
            }
            if (this.m_20163_()) {
                this.m_36220_(Stats.f_12993_);
            }
            if (!this.m_5803_()) {
                this.m_36220_(Stats.f_12992_);
            }
        }
        int $$0 = 29999999;
        double $$1 = Mth.m_14008_(this.m_20185_(), -2.9999999E7, 2.9999999E7);
        double $$2 = Mth.m_14008_(this.m_20189_(), -2.9999999E7, 2.9999999E7);
        if ($$1 != this.m_20185_() || $$2 != this.m_20189_()) {
            this.m_6034_($$1, this.m_20186_(), $$2);
        }
        ++this.f_20922_;
        ItemStack $$3 = this.m_21205_();
        if (!ItemStack.m_41728_(this.f_36086_, $$3)) {
            if (!ItemStack.m_41758_(this.f_36086_, $$3)) {
                this.m_36334_();
            }
            this.f_36086_ = $$3.m_41777_();
        }
        this.m_36372_();
        this.f_36087_.m_41518_();
        this.m_7594_();
    }

    public boolean m_36341_() {
        return this.m_6144_();
    }

    protected boolean m_36342_() {
        return this.m_6144_();
    }

    protected boolean m_36343_() {
        return this.m_6144_();
    }

    protected boolean m_7602_() {
        this.f_36076_ = this.m_204029_(FluidTags.f_13131_);
        return this.f_36076_;
    }

    private void m_36372_() {
        ItemStack $$0 = this.m_6844_(EquipmentSlot.HEAD);
        if ($$0.m_150930_(Items.f_42354_) && !this.m_204029_(FluidTags.f_13131_)) {
            this.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 200, 0, false, false, true));
        }
    }

    protected ItemCooldowns m_7478_() {
        return new ItemCooldowns();
    }

    private void m_36377_() {
        this.f_36102_ = this.f_36105_;
        this.f_36103_ = this.f_36106_;
        this.f_36104_ = this.f_36075_;
        double $$0 = this.m_20185_() - this.f_36105_;
        double $$1 = this.m_20186_() - this.f_36106_;
        double $$2 = this.m_20189_() - this.f_36075_;
        double $$3 = 10.0;
        if ($$0 > 10.0) {
            this.f_36102_ = this.f_36105_ = this.m_20185_();
        }
        if ($$2 > 10.0) {
            this.f_36104_ = this.f_36075_ = this.m_20189_();
        }
        if ($$1 > 10.0) {
            this.f_36103_ = this.f_36106_ = this.m_20186_();
        }
        if ($$0 < -10.0) {
            this.f_36102_ = this.f_36105_ = this.m_20185_();
        }
        if ($$2 < -10.0) {
            this.f_36104_ = this.f_36075_ = this.m_20189_();
        }
        if ($$1 < -10.0) {
            this.f_36103_ = this.f_36106_ = this.m_20186_();
        }
        this.f_36105_ += $$0 * 0.25;
        this.f_36075_ += $$2 * 0.25;
        this.f_36106_ += $$1 * 0.25;
    }

    protected void m_7594_() {
        Pose $$8;
        Pose $$5;
        if (!this.m_20175_(Pose.SWIMMING)) {
            return;
        }
        if (this.m_21255_()) {
            Pose $$0 = Pose.FALL_FLYING;
        } else if (this.m_5803_()) {
            Pose $$1 = Pose.SLEEPING;
        } else if (this.m_6069_()) {
            Pose $$2 = Pose.SWIMMING;
        } else if (this.m_21209_()) {
            Pose $$3 = Pose.SPIN_ATTACK;
        } else if (this.m_6144_() && !this.f_36077_.f_35935_) {
            Pose $$4 = Pose.CROUCHING;
        } else {
            $$5 = Pose.STANDING;
        }
        if (this.m_5833_() || this.m_20159_() || this.m_20175_($$5)) {
            void $$6 = $$5;
        } else if (this.m_20175_(Pose.CROUCHING)) {
            Pose $$7 = Pose.CROUCHING;
        } else {
            $$8 = Pose.SWIMMING;
        }
        this.m_20124_($$8);
    }

    @Override
    public int m_6078_() {
        return this.f_36077_.f_35934_ ? 1 : 80;
    }

    @Override
    protected SoundEvent m_5501_() {
        return SoundEvents.f_12279_;
    }

    @Override
    protected SoundEvent m_5509_() {
        return SoundEvents.f_12277_;
    }

    @Override
    protected SoundEvent m_5508_() {
        return SoundEvents.f_12278_;
    }

    @Override
    public int m_6045_() {
        return 10;
    }

    @Override
    public void m_5496_(SoundEvent p_36137_, float p_36138_, float p_36139_) {
        this.f_19853_.m_6263_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_36137_, this.m_5720_(), p_36138_, p_36139_);
    }

    public void m_6330_(SoundEvent p_36140_, SoundSource p_36141_, float p_36142_, float p_36143_) {
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.PLAYERS;
    }

    @Override
    protected int m_6101_() {
        return 20;
    }

    @Override
    public void m_7822_(byte p_36120_) {
        if (p_36120_ == 9) {
            this.m_8095_();
        } else if (p_36120_ == 23) {
            this.f_36085_ = false;
        } else if (p_36120_ == 22) {
            this.f_36085_ = true;
        } else if (p_36120_ == 43) {
            this.m_36208_(ParticleTypes.f_123796_);
        } else {
            super.m_7822_(p_36120_);
        }
    }

    private void m_36208_(ParticleOptions p_36209_) {
        for (int $$1 = 0; $$1 < 5; ++$$1) {
            double $$2 = this.f_19796_.m_188583_() * 0.02;
            double $$3 = this.f_19796_.m_188583_() * 0.02;
            double $$4 = this.f_19796_.m_188583_() * 0.02;
            this.f_19853_.m_7106_(p_36209_, this.m_20208_(1.0), this.m_20187_() + 1.0, this.m_20262_(1.0), $$2, $$3, $$4);
        }
    }

    protected void m_6915_() {
        this.f_36096_ = this.f_36095_;
    }

    @Override
    public void m_6083_() {
        if (!this.f_19853_.f_46443_ && this.m_36342_() && this.m_20159_()) {
            this.m_8127_();
            this.m_20260_(false);
            return;
        }
        double $$0 = this.m_20185_();
        double $$1 = this.m_20186_();
        double $$2 = this.m_20189_();
        super.m_6083_();
        this.f_36099_ = this.f_36100_;
        this.f_36100_ = 0.0f;
        this.m_36387_(this.m_20185_() - $$0, this.m_20186_() - $$1, this.m_20189_() - $$2);
    }

    @Override
    protected void m_6140_() {
        super.m_6140_();
        this.m_21203_();
        this.f_20885_ = this.m_146908_();
    }

    @Override
    public void m_8107_() {
        float $$1;
        if (this.f_36098_ > 0) {
            --this.f_36098_;
        }
        if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.f_19853_.m_46469_().m_46207_(GameRules.f_46139_)) {
            if (this.m_21223_() < this.m_21233_() && this.f_19797_ % 20 == 0) {
                this.m_5634_(1.0f);
            }
            if (this.f_36097_.m_38721_() && this.f_19797_ % 10 == 0) {
                this.f_36097_.m_38705_(this.f_36097_.m_38702_() + 1);
            }
        }
        this.f_36093_.m_36068_();
        this.f_36099_ = this.f_36100_;
        super.m_8107_();
        this.f_20887_ = 0.02f;
        if (this.m_20142_()) {
            this.f_20887_ += 0.006f;
        }
        this.m_7910_((float)this.m_21133_(Attributes.f_22279_));
        if (!this.f_19861_ || this.m_21224_() || this.m_6069_()) {
            float $$0 = 0.0f;
        } else {
            $$1 = Math.min(0.1f, (float)this.m_20184_().m_165924_());
        }
        this.f_36100_ += ($$1 - this.f_36100_) * 0.4f;
        if (this.m_21223_() > 0.0f && !this.m_5833_()) {
            AABB $$3;
            if (this.m_20159_() && !this.m_20202_().m_213877_()) {
                AABB $$2 = this.m_20191_().m_82367_(this.m_20202_().m_20191_()).m_82377_(1.0, 0.0, 1.0);
            } else {
                $$3 = this.m_20191_().m_82377_(1.0, 0.5, 1.0);
            }
            List<Entity> $$4 = this.f_19853_.m_45933_(this, $$3);
            ArrayList $$5 = Lists.newArrayList();
            for (int $$6 = 0; $$6 < $$4.size(); ++$$6) {
                Entity $$7 = $$4.get($$6);
                if ($$7.m_6095_() == EntityType.f_20570_) {
                    $$5.add($$7);
                    continue;
                }
                if ($$7.m_213877_()) continue;
                this.m_36277_($$7);
            }
            if (!$$5.isEmpty()) {
                this.m_36277_((Entity)Util.m_214621_($$5, this.f_19796_));
            }
        }
        this.m_36367_(this.m_36331_());
        this.m_36367_(this.m_36332_());
        if (!this.f_19853_.f_46443_ && (this.f_19789_ > 0.5f || this.m_20069_()) || this.f_36077_.f_35935_ || this.m_5803_() || this.f_146808_) {
            this.m_36328_();
        }
    }

    private void m_36367_(@Nullable CompoundTag p_36368_) {
        if (!(p_36368_ == null || p_36368_.m_128441_("Silent") && p_36368_.m_128471_("Silent") || this.f_19853_.f_46441_.m_188503_(200) != 0)) {
            String $$1 = p_36368_.m_128461_("id");
            EntityType.m_20632_($$1).filter(p_36280_ -> p_36280_ == EntityType.f_20508_).ifPresent(p_36255_ -> {
                if (!Parrot.m_29382_(this.f_19853_, this)) {
                    this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), Parrot.m_218238_(this.f_19853_, this.f_19853_.f_46441_), this.m_5720_(), 1.0f, Parrot.m_218236_(this.f_19853_.f_46441_));
                }
            });
        }
    }

    private void m_36277_(Entity p_36278_) {
        p_36278_.m_6123_(this);
    }

    public int m_36344_() {
        return this.f_19804_.m_135370_(f_36108_);
    }

    public void m_36397_(int p_36398_) {
        this.f_19804_.m_135381_(f_36108_, p_36398_);
    }

    public void m_36401_(int p_36402_) {
        int $$1 = this.m_36344_();
        this.f_19804_.m_135381_(f_36108_, $$1 + p_36402_);
    }

    public void m_204079_(int p_204080_) {
        this.f_20938_ = p_204080_;
        if (!this.f_19853_.f_46443_) {
            this.m_36328_();
            this.m_21155_(4, true);
        }
    }

    @Override
    public void m_6667_(DamageSource p_36152_) {
        super.m_6667_(p_36152_);
        this.m_20090_();
        if (!this.m_5833_()) {
            this.m_6668_(p_36152_);
        }
        if (p_36152_ != null) {
            this.m_20334_(-Mth.m_14089_((this.f_20918_ + this.m_146908_()) * ((float)Math.PI / 180)) * 0.1f, 0.1f, -Mth.m_14031_((this.f_20918_ + this.m_146908_()) * ((float)Math.PI / 180)) * 0.1f);
        } else {
            this.m_20334_(0.0, 0.1, 0.0);
        }
        this.m_36220_(Stats.f_12935_);
        this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12991_));
        this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12992_));
        this.m_20095_();
        this.m_146868_(false);
        this.m_219749_(Optional.of(GlobalPos.m_122643_(this.f_19853_.m_46472_(), this.m_20183_())));
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46133_)) {
            this.m_36345_();
            this.f_36093_.m_36071_();
        }
    }

    protected void m_36345_() {
        for (int $$0 = 0; $$0 < this.f_36093_.m_6643_(); ++$$0) {
            ItemStack $$1 = this.f_36093_.m_8020_($$0);
            if ($$1.m_41619_() || !EnchantmentHelper.m_44924_($$1)) continue;
            this.f_36093_.m_8016_($$0);
        }
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_36310_) {
        if (p_36310_ == DamageSource.f_19307_) {
            return SoundEvents.f_12273_;
        }
        if (p_36310_ == DamageSource.f_19312_) {
            return SoundEvents.f_12324_;
        }
        if (p_36310_ == DamageSource.f_19325_) {
            return SoundEvents.f_12274_;
        }
        if (p_36310_ == DamageSource.f_146701_) {
            return SoundEvents.f_144205_;
        }
        return SoundEvents.f_12323_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12322_;
    }

    @Nullable
    public ItemEntity m_36176_(ItemStack p_36177_, boolean p_36178_) {
        return this.m_7197_(p_36177_, false, p_36178_);
    }

    @Nullable
    public ItemEntity m_7197_(ItemStack p_36179_, boolean p_36180_, boolean p_36181_) {
        if (p_36179_.m_41619_()) {
            return null;
        }
        if (this.f_19853_.f_46443_) {
            this.m_6674_(InteractionHand.MAIN_HAND);
        }
        double $$3 = this.m_20188_() - (double)0.3f;
        ItemEntity $$4 = new ItemEntity(this.f_19853_, this.m_20185_(), $$3, this.m_20189_(), p_36179_);
        $$4.m_32010_(40);
        if (p_36181_) {
            $$4.m_32052_(this.m_20148_());
        }
        if (p_36180_) {
            float $$5 = this.f_19796_.m_188501_() * 0.5f;
            float $$6 = this.f_19796_.m_188501_() * ((float)Math.PI * 2);
            $$4.m_20334_(-Mth.m_14031_($$6) * $$5, 0.2f, Mth.m_14089_($$6) * $$5);
        } else {
            float $$7 = 0.3f;
            float $$8 = Mth.m_14031_(this.m_146909_() * ((float)Math.PI / 180));
            float $$9 = Mth.m_14089_(this.m_146909_() * ((float)Math.PI / 180));
            float $$10 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180));
            float $$11 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180));
            float $$12 = this.f_19796_.m_188501_() * ((float)Math.PI * 2);
            float $$13 = 0.02f * this.f_19796_.m_188501_();
            $$4.m_20334_((double)(-$$10 * $$9 * 0.3f) + Math.cos($$12) * (double)$$13, -$$8 * 0.3f + 0.1f + (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.1f, (double)($$11 * $$9 * 0.3f) + Math.sin($$12) * (double)$$13);
        }
        return $$4;
    }

    public float m_36281_(BlockState p_36282_) {
        float $$1 = this.f_36093_.m_36020_(p_36282_);
        if ($$1 > 1.0f) {
            int $$2 = EnchantmentHelper.m_44926_(this);
            ItemStack $$3 = this.m_21205_();
            if ($$2 > 0 && !$$3.m_41619_()) {
                $$1 += (float)($$2 * $$2 + 1);
            }
        }
        if (MobEffectUtil.m_19584_(this)) {
            $$1 *= 1.0f + (float)(MobEffectUtil.m_19586_(this) + 1) * 0.2f;
        }
        if (this.m_21023_(MobEffects.f_19599_)) {
            float $$7;
            switch (this.m_21124_(MobEffects.f_19599_).m_19564_()) {
                case 0: {
                    float $$4 = 0.3f;
                    break;
                }
                case 1: {
                    float $$5 = 0.09f;
                    break;
                }
                case 2: {
                    float $$6 = 0.0027f;
                    break;
                }
                default: {
                    $$7 = 8.1E-4f;
                }
            }
            $$1 *= $$7;
        }
        if (this.m_204029_(FluidTags.f_13131_) && !EnchantmentHelper.m_44934_(this)) {
            $$1 /= 5.0f;
        }
        if (!this.f_19861_) {
            $$1 /= 5.0f;
        }
        return $$1;
    }

    public boolean m_36298_(BlockState p_36299_) {
        return !p_36299_.m_60834_() || this.f_36093_.m_36056_().m_41735_(p_36299_);
    }

    @Override
    public void m_7378_(CompoundTag p_36215_) {
        super.m_7378_(p_36215_);
        this.m_20084_(UUIDUtil.m_235875_(this.f_36084_));
        ListTag $$1 = p_36215_.m_128437_("Inventory", 10);
        this.f_36093_.m_36035_($$1);
        this.f_36093_.f_35977_ = p_36215_.m_128451_("SelectedItemSlot");
        this.f_36110_ = p_36215_.m_128448_("SleepTimer");
        this.f_36080_ = p_36215_.m_128457_("XpP");
        this.f_36078_ = p_36215_.m_128451_("XpLevel");
        this.f_36079_ = p_36215_.m_128451_("XpTotal");
        this.f_36081_ = p_36215_.m_128451_("XpSeed");
        if (this.f_36081_ == 0) {
            this.f_36081_ = this.f_19796_.m_188502_();
        }
        this.m_36397_(p_36215_.m_128451_("Score"));
        this.f_36097_.m_38715_(p_36215_);
        if (p_36215_.m_128425_("warden_spawn_tracker", 10)) {
            WardenSpawnTracker.f_219557_.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)p_36215_.m_128423_("warden_spawn_tracker"))).resultOrPartial(arg_0 -> ((Logger)f_219722_).error(arg_0)).ifPresent(p_219743_ -> {
                this.f_219724_ = p_219743_;
            });
        }
        this.f_36077_.m_35950_(p_36215_);
        this.m_21051_(Attributes.f_22279_).m_22100_(this.f_36077_.m_35947_());
        if (p_36215_.m_128425_("EnderItems", 9)) {
            this.f_36094_.m_7797_(p_36215_.m_128437_("EnderItems", 10));
        }
        if (p_36215_.m_128425_("ShoulderEntityLeft", 10)) {
            this.m_36362_(p_36215_.m_128469_("ShoulderEntityLeft"));
        }
        if (p_36215_.m_128425_("ShoulderEntityRight", 10)) {
            this.m_36364_(p_36215_.m_128469_("ShoulderEntityRight"));
        }
        if (p_36215_.m_128425_("LastDeathLocation", 10)) {
            this.m_219749_(GlobalPos.f_122633_.parse((DynamicOps)NbtOps.f_128958_, (Object)p_36215_.m_128423_("LastDeathLocation")).resultOrPartial(arg_0 -> ((Logger)f_219722_).error(arg_0)));
        }
    }

    @Override
    public void m_7380_(CompoundTag p_36265_) {
        super.m_7380_(p_36265_);
        p_36265_.m_128405_("DataVersion", SharedConstants.m_183709_().getWorldVersion());
        p_36265_.m_128365_("Inventory", this.f_36093_.m_36026_(new ListTag()));
        p_36265_.m_128405_("SelectedItemSlot", this.f_36093_.f_35977_);
        p_36265_.m_128376_("SleepTimer", (short)this.f_36110_);
        p_36265_.m_128350_("XpP", this.f_36080_);
        p_36265_.m_128405_("XpLevel", this.f_36078_);
        p_36265_.m_128405_("XpTotal", this.f_36079_);
        p_36265_.m_128405_("XpSeed", this.f_36081_);
        p_36265_.m_128405_("Score", this.m_36344_());
        this.f_36097_.m_38719_(p_36265_);
        WardenSpawnTracker.f_219557_.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.f_219724_).resultOrPartial(arg_0 -> ((Logger)f_219722_).error(arg_0)).ifPresent(p_219756_ -> p_36265_.m_128365_("warden_spawn_tracker", (Tag)p_219756_));
        this.f_36077_.m_35945_(p_36265_);
        p_36265_.m_128365_("EnderItems", this.f_36094_.m_7927_());
        if (!this.m_36331_().m_128456_()) {
            p_36265_.m_128365_("ShoulderEntityLeft", this.m_36331_());
        }
        if (!this.m_36332_().m_128456_()) {
            p_36265_.m_128365_("ShoulderEntityRight", this.m_36332_());
        }
        this.m_219759_().flatMap(p_219745_ -> GlobalPos.f_122633_.encodeStart((DynamicOps)NbtOps.f_128958_, p_219745_).resultOrPartial(arg_0 -> ((Logger)f_219722_).error(arg_0))).ifPresent(p_219753_ -> p_36265_.m_128365_("LastDeathLocation", (Tag)p_219753_));
    }

    @Override
    public boolean m_6673_(DamageSource p_36249_) {
        if (super.m_6673_(p_36249_)) {
            return true;
        }
        if (p_36249_ == DamageSource.f_19312_) {
            return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46121_);
        }
        if (p_36249_.m_146707_()) {
            return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46122_);
        }
        if (p_36249_.m_19384_()) {
            return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46123_);
        }
        if (p_36249_ == DamageSource.f_146701_) {
            return !this.f_19853_.m_46469_().m_46207_(GameRules.f_151485_);
        }
        return false;
    }

    @Override
    public boolean m_6469_(DamageSource p_36154_, float p_36155_) {
        if (this.m_6673_(p_36154_)) {
            return false;
        }
        if (this.f_36077_.f_35934_ && !p_36154_.m_19378_()) {
            return false;
        }
        this.f_20891_ = 0;
        if (this.m_21224_()) {
            return false;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_36328_();
        }
        if (p_36154_.m_7986_()) {
            if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
                p_36155_ = 0.0f;
            }
            if (this.f_19853_.m_46791_() == Difficulty.EASY) {
                p_36155_ = Math.min(p_36155_ / 2.0f + 1.0f, p_36155_);
            }
            if (this.f_19853_.m_46791_() == Difficulty.HARD) {
                p_36155_ = p_36155_ * 3.0f / 2.0f;
            }
        }
        if (p_36155_ == 0.0f) {
            return false;
        }
        return super.m_6469_(p_36154_, p_36155_);
    }

    @Override
    protected void m_6728_(LivingEntity p_36295_) {
        super.m_6728_(p_36295_);
        if (p_36295_.m_213824_()) {
            this.m_36384_(true);
        }
    }

    @Override
    public boolean m_142066_() {
        return !this.m_150110_().f_35934_ && super.m_142066_();
    }

    public boolean m_7099_(Player p_36169_) {
        Team $$1 = this.m_5647_();
        Team $$2 = p_36169_.m_5647_();
        if ($$1 == null) {
            return true;
        }
        if (!$$1.m_83536_($$2)) {
            return true;
        }
        return $$1.m_6260_();
    }

    @Override
    protected void m_6472_(DamageSource p_36251_, float p_36252_) {
        this.f_36093_.m_150072_(p_36251_, p_36252_, Inventory.f_150068_);
    }

    @Override
    protected void m_142642_(DamageSource p_150103_, float p_150104_) {
        this.f_36093_.m_150072_(p_150103_, p_150104_, Inventory.f_150069_);
    }

    @Override
    protected void m_7909_(float p_36383_) {
        if (!this.f_20935_.m_150930_(Items.f_42740_)) {
            return;
        }
        if (!this.f_19853_.f_46443_) {
            this.m_36246_(Stats.f_12982_.m_12902_(this.f_20935_.m_41720_()));
        }
        if (p_36383_ >= 3.0f) {
            int $$1 = 1 + Mth.m_14143_(p_36383_);
            InteractionHand $$2 = this.m_7655_();
            this.f_20935_.m_41622_($$1, this, p_219739_ -> p_219739_.m_21190_($$2));
            if (this.f_20935_.m_41619_()) {
                if ($$2 == InteractionHand.MAIN_HAND) {
                    this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
                } else {
                    this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
                }
                this.f_20935_ = ItemStack.f_41583_;
                this.m_5496_(SoundEvents.f_12347_, 0.8f, 0.8f + this.f_19853_.f_46441_.m_188501_() * 0.4f);
            }
        }
    }

    @Override
    protected void m_6475_(DamageSource p_36312_, float p_36313_) {
        if (this.m_6673_(p_36312_)) {
            return;
        }
        p_36313_ = this.m_21161_(p_36312_, p_36313_);
        float $$2 = p_36313_ = this.m_6515_(p_36312_, p_36313_);
        p_36313_ = Math.max(p_36313_ - this.m_6103_(), 0.0f);
        this.m_7911_(this.m_6103_() - ($$2 - p_36313_));
        float $$3 = $$2 - p_36313_;
        if ($$3 > 0.0f && $$3 < 3.4028235E37f) {
            this.m_36222_(Stats.f_12933_, Math.round($$3 * 10.0f));
        }
        if (p_36313_ == 0.0f) {
            return;
        }
        this.m_36399_(p_36312_.m_19377_());
        float $$4 = this.m_21223_();
        this.m_21153_(this.m_21223_() - p_36313_);
        this.m_21231_().m_19289_(p_36312_, $$4, p_36313_);
        if (p_36313_ < 3.4028235E37f) {
            this.m_36222_(Stats.f_12931_, Math.round(p_36313_ * 10.0f));
        }
    }

    @Override
    protected boolean m_6046_() {
        return !this.f_36077_.f_35935_ && super.m_6046_();
    }

    public void m_7739_(SignBlockEntity p_36193_) {
    }

    public void m_7907_(BaseCommandBlock p_36182_) {
    }

    public void m_7698_(CommandBlockEntity p_36191_) {
    }

    public void m_5966_(StructureBlockEntity p_36194_) {
    }

    public void m_7569_(JigsawBlockEntity p_36192_) {
    }

    public void m_6658_(AbstractHorse p_36167_, Container p_36168_) {
    }

    public OptionalInt m_5893_(@Nullable MenuProvider p_36150_) {
        return OptionalInt.empty();
    }

    public void m_7662_(int p_36121_, MerchantOffers p_36122_, int p_36123_, int p_36124_, boolean p_36125_, boolean p_36126_) {
    }

    public void m_6986_(ItemStack p_36174_, InteractionHand p_36175_) {
    }

    public InteractionResult m_36157_(Entity p_36158_, InteractionHand p_36159_) {
        if (this.m_5833_()) {
            if (p_36158_ instanceof MenuProvider) {
                this.m_5893_((MenuProvider)((Object)p_36158_));
            }
            return InteractionResult.PASS;
        }
        ItemStack $$2 = this.m_21120_(p_36159_);
        ItemStack $$3 = $$2.m_41777_();
        InteractionResult $$4 = p_36158_.m_6096_(this, p_36159_);
        if ($$4.m_19077_()) {
            if (this.f_36077_.f_35937_ && $$2 == this.m_21120_(p_36159_) && $$2.m_41613_() < $$3.m_41613_()) {
                $$2.m_41764_($$3.m_41613_());
            }
            return $$4;
        }
        if (!$$2.m_41619_() && p_36158_ instanceof LivingEntity) {
            InteractionResult $$5;
            if (this.f_36077_.f_35937_) {
                $$2 = $$3;
            }
            if (($$5 = $$2.m_41647_(this, (LivingEntity)p_36158_, p_36159_)).m_19077_()) {
                if ($$2.m_41619_() && !this.f_36077_.f_35937_) {
                    this.m_21008_(p_36159_, ItemStack.f_41583_);
                }
                return $$5;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public double m_6049_() {
        return -0.35;
    }

    @Override
    public void m_6038_() {
        super.m_6038_();
        this.f_19851_ = 0;
    }

    @Override
    protected boolean m_6107_() {
        return super.m_6107_() || this.m_5803_();
    }

    @Override
    public boolean m_6129_() {
        return !this.f_36077_.f_35935_;
    }

    @Override
    protected Vec3 m_5763_(Vec3 p_36201_, MoverType p_36202_) {
        if (!this.f_36077_.f_35935_ && p_36201_.f_82480_ <= 0.0 && (p_36202_ == MoverType.SELF || p_36202_ == MoverType.PLAYER) && this.m_36343_() && this.m_36386_()) {
            double $$2 = p_36201_.f_82479_;
            double $$3 = p_36201_.f_82481_;
            double $$4 = 0.05;
            while ($$2 != 0.0 && this.f_19853_.m_45756_(this, this.m_20191_().m_82386_($$2, -this.f_19793_, 0.0))) {
                if ($$2 < 0.05 && $$2 >= -0.05) {
                    $$2 = 0.0;
                    continue;
                }
                if ($$2 > 0.0) {
                    $$2 -= 0.05;
                    continue;
                }
                $$2 += 0.05;
            }
            while ($$3 != 0.0 && this.f_19853_.m_45756_(this, this.m_20191_().m_82386_(0.0, -this.f_19793_, $$3))) {
                if ($$3 < 0.05 && $$3 >= -0.05) {
                    $$3 = 0.0;
                    continue;
                }
                if ($$3 > 0.0) {
                    $$3 -= 0.05;
                    continue;
                }
                $$3 += 0.05;
            }
            while ($$2 != 0.0 && $$3 != 0.0 && this.f_19853_.m_45756_(this, this.m_20191_().m_82386_($$2, -this.f_19793_, $$3))) {
                $$2 = $$2 < 0.05 && $$2 >= -0.05 ? 0.0 : ($$2 > 0.0 ? ($$2 -= 0.05) : ($$2 += 0.05));
                if ($$3 < 0.05 && $$3 >= -0.05) {
                    $$3 = 0.0;
                    continue;
                }
                if ($$3 > 0.0) {
                    $$3 -= 0.05;
                    continue;
                }
                $$3 += 0.05;
            }
            p_36201_ = new Vec3($$2, p_36201_.f_82480_, $$3);
        }
        return p_36201_;
    }

    private boolean m_36386_() {
        return this.f_19861_ || this.f_19789_ < this.f_19793_ && !this.f_19853_.m_45756_(this, this.m_20191_().m_82386_(0.0, this.f_19789_ - this.f_19793_, 0.0));
    }

    public void m_5706_(Entity p_36347_) {
        float $$3;
        if (!p_36347_.m_6097_()) {
            return;
        }
        if (p_36347_.m_7313_(this)) {
            return;
        }
        float $$1 = (float)this.m_21133_(Attributes.f_22281_);
        if (p_36347_ instanceof LivingEntity) {
            float $$2 = EnchantmentHelper.m_44833_(this.m_21205_(), ((LivingEntity)p_36347_).m_6336_());
        } else {
            $$3 = EnchantmentHelper.m_44833_(this.m_21205_(), MobType.f_21640_);
        }
        float $$4 = this.m_36403_(0.5f);
        $$3 *= $$4;
        this.m_36334_();
        if (($$1 *= 0.2f + $$4 * $$4 * 0.8f) > 0.0f || $$3 > 0.0f) {
            ItemStack $$11;
            boolean $$5 = $$4 > 0.9f;
            boolean $$6 = false;
            int $$7 = 0;
            $$7 += EnchantmentHelper.m_44894_(this);
            if (this.m_20142_() && $$5) {
                this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12314_, this.m_5720_(), 1.0f, 1.0f);
                ++$$7;
                $$6 = true;
            }
            boolean $$8 = $$5 && this.f_19789_ > 0.0f && !this.f_19861_ && !this.m_6147_() && !this.m_20069_() && !this.m_21023_(MobEffects.f_19610_) && !this.m_20159_() && p_36347_ instanceof LivingEntity;
            boolean bl = $$8 = $$8 && !this.m_20142_();
            if ($$8) {
                $$1 *= 1.5f;
            }
            $$1 += $$3;
            boolean $$9 = false;
            double $$10 = this.f_19787_ - this.f_19867_;
            if ($$5 && !$$8 && !$$6 && this.f_19861_ && $$10 < (double)this.m_6113_() && ($$11 = this.m_21120_(InteractionHand.MAIN_HAND)).m_41720_() instanceof SwordItem) {
                $$9 = true;
            }
            float $$12 = 0.0f;
            boolean $$13 = false;
            int $$14 = EnchantmentHelper.m_44914_(this);
            if (p_36347_ instanceof LivingEntity) {
                $$12 = ((LivingEntity)p_36347_).m_21223_();
                if ($$14 > 0 && !p_36347_.m_6060_()) {
                    $$13 = true;
                    p_36347_.m_20254_(1);
                }
            }
            Vec3 $$15 = p_36347_.m_20184_();
            boolean $$16 = p_36347_.m_6469_(DamageSource.m_19344_(this), $$1);
            if ($$16) {
                if ($$7 > 0) {
                    if (p_36347_ instanceof LivingEntity) {
                        ((LivingEntity)p_36347_).m_147240_((float)$$7 * 0.5f, Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)), -Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)));
                    } else {
                        p_36347_.m_5997_(-Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)) * (float)$$7 * 0.5f, 0.1, Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)) * (float)$$7 * 0.5f);
                    }
                    this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
                    this.m_6858_(false);
                }
                if ($$9) {
                    float $$17 = 1.0f + EnchantmentHelper.m_44821_(this) * $$1;
                    List<LivingEntity> $$18 = this.f_19853_.m_45976_(LivingEntity.class, p_36347_.m_20191_().m_82377_(1.0, 0.25, 1.0));
                    for (LivingEntity $$19 : $$18) {
                        if ($$19 == this || $$19 == p_36347_ || this.m_7307_($$19) || $$19 instanceof ArmorStand && ((ArmorStand)$$19).m_31677_() || !(this.m_20280_($$19) < 9.0)) continue;
                        $$19.m_147240_(0.4f, Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)), -Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)));
                        $$19.m_6469_(DamageSource.m_19344_(this), $$17);
                    }
                    this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12317_, this.m_5720_(), 1.0f, 1.0f);
                    this.m_36346_();
                }
                if (p_36347_ instanceof ServerPlayer && p_36347_.f_19864_) {
                    ((ServerPlayer)p_36347_).f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(p_36347_));
                    p_36347_.f_19864_ = false;
                    p_36347_.m_20256_($$15);
                }
                if ($$8) {
                    this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12313_, this.m_5720_(), 1.0f, 1.0f);
                    this.m_5704_(p_36347_);
                }
                if (!$$8 && !$$9) {
                    if ($$5) {
                        this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12316_, this.m_5720_(), 1.0f, 1.0f);
                    } else {
                        this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12318_, this.m_5720_(), 1.0f, 1.0f);
                    }
                }
                if ($$3 > 0.0f) {
                    this.m_5700_(p_36347_);
                }
                this.m_21335_(p_36347_);
                if (p_36347_ instanceof LivingEntity) {
                    EnchantmentHelper.m_44823_((LivingEntity)p_36347_, this);
                }
                EnchantmentHelper.m_44896_(this, p_36347_);
                ItemStack $$20 = this.m_21205_();
                Entity $$21 = p_36347_;
                if (p_36347_ instanceof EnderDragonPart) {
                    $$21 = ((EnderDragonPart)p_36347_).f_31010_;
                }
                if (!this.f_19853_.f_46443_ && !$$20.m_41619_() && $$21 instanceof LivingEntity) {
                    $$20.m_41640_((LivingEntity)$$21, this);
                    if ($$20.m_41619_()) {
                        this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
                    }
                }
                if (p_36347_ instanceof LivingEntity) {
                    float $$22 = $$12 - ((LivingEntity)p_36347_).m_21223_();
                    this.m_36222_(Stats.f_12928_, Math.round($$22 * 10.0f));
                    if ($$14 > 0) {
                        p_36347_.m_20254_($$14 * 4);
                    }
                    if (this.f_19853_ instanceof ServerLevel && $$22 > 2.0f) {
                        int $$23 = (int)((double)$$22 * 0.5);
                        ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123798_, p_36347_.m_20185_(), p_36347_.m_20227_(0.5), p_36347_.m_20189_(), $$23, 0.1, 0.0, 0.1, 0.2);
                    }
                }
                this.m_36399_(0.1f);
            } else {
                this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12315_, this.m_5720_(), 1.0f, 1.0f);
                if ($$13) {
                    p_36347_.m_20095_();
                }
            }
        }
    }

    @Override
    protected void m_6727_(LivingEntity p_36355_) {
        this.m_5706_(p_36355_);
    }

    public void m_36384_(boolean p_36385_) {
        float $$1 = 0.25f + (float)EnchantmentHelper.m_44926_(this) * 0.05f;
        if (p_36385_) {
            $$1 += 0.75f;
        }
        if (this.f_19796_.m_188501_() < $$1) {
            this.m_36335_().m_41524_(Items.f_42740_, 100);
            this.m_5810_();
            this.f_19853_.m_7605_(this, (byte)30);
        }
    }

    public void m_5704_(Entity p_36156_) {
    }

    public void m_5700_(Entity p_36253_) {
    }

    public void m_36346_() {
        double $$0 = -Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180));
        double $$1 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180));
        if (this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123766_, this.m_20185_() + $$0, this.m_20227_(0.5), this.m_20189_() + $$1, 0, $$0, 0.0, $$1, 0.0);
        }
    }

    public void m_7583_() {
    }

    @Override
    public void m_142687_(Entity.RemovalReason p_150097_) {
        super.m_142687_(p_150097_);
        this.f_36095_.m_6877_(this);
        if (this.f_36096_ != null) {
            this.f_36096_.m_6877_(this);
        }
    }

    public boolean m_7578_() {
        return false;
    }

    public GameProfile m_36316_() {
        return this.f_36084_;
    }

    @Nullable
    public ProfilePublicKey m_219760_() {
        return this.f_219725_;
    }

    public Inventory m_150109_() {
        return this.f_36093_;
    }

    public Abilities m_150110_() {
        return this.f_36077_;
    }

    public void m_141945_(ItemStack p_150098_, ItemStack p_150099_, ClickAction p_150100_) {
    }

    public boolean m_242612_() {
        return this.f_36096_ != this.f_36095_;
    }

    public Either<BedSleepingProblem, Unit> m_7720_(BlockPos p_36203_) {
        this.m_5802_(p_36203_);
        this.f_36110_ = 0;
        return Either.right((Object)((Object)Unit.INSTANCE));
    }

    public void m_6145_(boolean p_36226_, boolean p_36227_) {
        super.m_5796_();
        if (this.f_19853_ instanceof ServerLevel && p_36227_) {
            ((ServerLevel)this.f_19853_).m_8878_();
        }
        this.f_36110_ = p_36226_ ? 0 : 100;
    }

    @Override
    public void m_5796_() {
        this.m_6145_(true, true);
    }

    public static Optional<Vec3> m_36130_(ServerLevel p_36131_, BlockPos p_36132_, float p_36133_, boolean p_36134_, boolean p_36135_) {
        BlockState $$5 = p_36131_.m_8055_(p_36132_);
        Block $$6 = $$5.m_60734_();
        if ($$6 instanceof RespawnAnchorBlock && $$5.m_61143_(RespawnAnchorBlock.f_55833_) > 0 && RespawnAnchorBlock.m_55850_(p_36131_)) {
            Optional<Vec3> $$7 = RespawnAnchorBlock.m_55839_(EntityType.f_20532_, p_36131_, p_36132_);
            if (!p_36135_ && $$7.isPresent()) {
                p_36131_.m_7731_(p_36132_, (BlockState)$$5.m_61124_(RespawnAnchorBlock.f_55833_, $$5.m_61143_(RespawnAnchorBlock.f_55833_) - 1), 3);
            }
            return $$7;
        }
        if ($$6 instanceof BedBlock && BedBlock.m_49488_(p_36131_)) {
            return BedBlock.m_49458_(EntityType.f_20532_, p_36131_, p_36132_, p_36133_);
        }
        if (!p_36134_) {
            return Optional.empty();
        }
        boolean $$8 = $$6.m_5568_();
        boolean $$9 = p_36131_.m_8055_(p_36132_.m_7494_()).m_60734_().m_5568_();
        if ($$8 && $$9) {
            return Optional.of(new Vec3((double)p_36132_.m_123341_() + 0.5, (double)p_36132_.m_123342_() + 0.1, (double)p_36132_.m_123343_() + 0.5));
        }
        return Optional.empty();
    }

    public boolean m_36317_() {
        return this.m_5803_() && this.f_36110_ >= 100;
    }

    public int m_36318_() {
        return this.f_36110_;
    }

    public void m_5661_(Component p_36216_, boolean p_36217_) {
    }

    public void m_36220_(ResourceLocation p_36221_) {
        this.m_36246_(Stats.f_12988_.m_12902_(p_36221_));
    }

    public void m_36222_(ResourceLocation p_36223_, int p_36224_) {
        this.m_6278_(Stats.f_12988_.m_12902_(p_36223_), p_36224_);
    }

    public void m_36246_(Stat<?> p_36247_) {
        this.m_6278_(p_36247_, 1);
    }

    public void m_6278_(Stat<?> p_36145_, int p_36146_) {
    }

    public void m_7166_(Stat<?> p_36144_) {
    }

    public int m_7281_(Collection<Recipe<?>> p_36213_) {
        return 0;
    }

    public void m_7902_(ResourceLocation[] p_36228_) {
    }

    public int m_7279_(Collection<Recipe<?>> p_36263_) {
        return 0;
    }

    @Override
    public void m_6135_() {
        super.m_6135_();
        this.m_36220_(Stats.f_12926_);
        if (this.m_20142_()) {
            this.m_36399_(0.2f);
        } else {
            this.m_36399_(0.05f);
        }
    }

    @Override
    public void m_7023_(Vec3 p_36359_) {
        double $$1 = this.m_20185_();
        double $$2 = this.m_20186_();
        double $$3 = this.m_20189_();
        if (this.m_6069_() && !this.m_20159_()) {
            double $$5;
            double $$4 = this.m_20154_().f_82480_;
            double d = $$5 = $$4 < -0.2 ? 0.085 : 0.06;
            if ($$4 <= 0.0 || this.f_20899_ || !this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() + 1.0 - 0.1, this.m_20189_())).m_60819_().m_76178_()) {
                Vec3 $$6 = this.m_20184_();
                this.m_20256_($$6.m_82520_(0.0, ($$4 - $$6.f_82480_) * $$5, 0.0));
            }
        }
        if (this.f_36077_.f_35935_ && !this.m_20159_()) {
            double $$7 = this.m_20184_().f_82480_;
            float $$8 = this.f_20887_;
            this.f_20887_ = this.f_36077_.m_35942_() * (float)(this.m_20142_() ? 2 : 1);
            super.m_7023_(p_36359_);
            Vec3 $$9 = this.m_20184_();
            this.m_20334_($$9.f_82479_, $$7 * 0.6, $$9.f_82481_);
            this.f_20887_ = $$8;
            this.m_183634_();
            this.m_20115_(7, false);
        } else {
            super.m_7023_(p_36359_);
        }
        this.m_36378_(this.m_20185_() - $$1, this.m_20186_() - $$2, this.m_20189_() - $$3);
    }

    @Override
    public void m_5844_() {
        if (this.f_36077_.f_35935_) {
            this.m_20282_(false);
        } else {
            super.m_5844_();
        }
    }

    protected boolean m_36350_(BlockPos p_36351_) {
        return !this.f_19853_.m_8055_(p_36351_).m_60828_(this.f_19853_, p_36351_);
    }

    @Override
    public float m_6113_() {
        return (float)this.m_21133_(Attributes.f_22279_);
    }

    public void m_36378_(double p_36379_, double p_36380_, double p_36381_) {
        if (this.m_20159_()) {
            return;
        }
        if (this.m_6069_()) {
            int $$3 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0f);
            if ($$3 > 0) {
                this.m_36222_(Stats.f_12924_, $$3);
                this.m_36399_(0.01f * (float)$$3 * 0.01f);
            }
        } else if (this.m_204029_(FluidTags.f_13131_)) {
            int $$4 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0f);
            if ($$4 > 0) {
                this.m_36222_(Stats.f_13001_, $$4);
                this.m_36399_(0.01f * (float)$$4 * 0.01f);
            }
        } else if (this.m_20069_()) {
            int $$5 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0f);
            if ($$5 > 0) {
                this.m_36222_(Stats.f_12997_, $$5);
                this.m_36399_(0.01f * (float)$$5 * 0.01f);
            }
        } else if (this.m_6147_()) {
            if (p_36380_ > 0.0) {
                this.m_36222_(Stats.f_12999_, (int)Math.round(p_36380_ * 100.0));
            }
        } else if (this.f_19861_) {
            int $$6 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0f);
            if ($$6 > 0) {
                if (this.m_20142_()) {
                    this.m_36222_(Stats.f_12996_, $$6);
                    this.m_36399_(0.1f * (float)$$6 * 0.01f);
                } else if (this.m_6047_()) {
                    this.m_36222_(Stats.f_12995_, $$6);
                    this.m_36399_(0.0f * (float)$$6 * 0.01f);
                } else {
                    this.m_36222_(Stats.f_12994_, $$6);
                    this.m_36399_(0.0f * (float)$$6 * 0.01f);
                }
            }
        } else if (this.m_21255_()) {
            int $$7 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0f);
            this.m_36222_(Stats.f_12923_, $$7);
        } else {
            int $$8 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0f);
            if ($$8 > 25) {
                this.m_36222_(Stats.f_13000_, $$8);
            }
        }
    }

    private void m_36387_(double p_36388_, double p_36389_, double p_36390_) {
        int $$3;
        if (this.m_20159_() && ($$3 = Math.round((float)Math.sqrt(p_36388_ * p_36388_ + p_36389_ * p_36389_ + p_36390_ * p_36390_) * 100.0f)) > 0) {
            Entity $$4 = this.m_20202_();
            if ($$4 instanceof AbstractMinecart) {
                this.m_36222_(Stats.f_13002_, $$3);
            } else if ($$4 instanceof Boat) {
                this.m_36222_(Stats.f_13003_, $$3);
            } else if ($$4 instanceof Pig) {
                this.m_36222_(Stats.f_13004_, $$3);
            } else if ($$4 instanceof AbstractHorse) {
                this.m_36222_(Stats.f_13005_, $$3);
            } else if ($$4 instanceof Strider) {
                this.m_36222_(Stats.f_12925_, $$3);
            }
        }
    }

    @Override
    public boolean m_142535_(float p_150093_, float p_150094_, DamageSource p_150095_) {
        if (this.f_36077_.f_35936_) {
            return false;
        }
        if (p_150093_ >= 2.0f) {
            this.m_36222_(Stats.f_12998_, (int)Math.round((double)p_150093_ * 100.0));
        }
        return super.m_142535_(p_150093_, p_150094_, p_150095_);
    }

    @Override
    public ChatSender m_217000_() {
        return new ChatSender(this.m_36316_().getId(), this.m_219760_());
    }

    public boolean m_36319_() {
        ItemStack $$0;
        if (!this.f_19861_ && !this.m_21255_() && !this.m_20069_() && !this.m_21023_(MobEffects.f_19620_) && ($$0 = this.m_6844_(EquipmentSlot.CHEST)).m_150930_(Items.f_42741_) && ElytraItem.m_41140_($$0)) {
            this.m_36320_();
            return true;
        }
        return false;
    }

    public void m_36320_() {
        this.m_20115_(7, true);
    }

    public void m_36321_() {
        this.m_20115_(7, true);
        this.m_20115_(7, false);
    }

    @Override
    protected void m_5841_() {
        if (!this.m_5833_()) {
            super.m_5841_();
        }
    }

    @Override
    public LivingEntity.Fallsounds m_196493_() {
        return new LivingEntity.Fallsounds(SoundEvents.f_12276_, SoundEvents.f_12319_);
    }

    @Override
    public boolean m_214076_(ServerLevel p_219735_, LivingEntity p_219736_) {
        this.m_36246_(Stats.f_12986_.m_12902_(p_219736_.m_6095_()));
        return true;
    }

    @Override
    public void m_7601_(BlockState p_36196_, Vec3 p_36197_) {
        if (!this.f_36077_.f_35935_) {
            super.m_7601_(p_36196_, p_36197_);
        }
    }

    public void m_6756_(int p_36291_) {
        this.m_36401_(p_36291_);
        this.f_36080_ += (float)p_36291_ / (float)this.m_36323_();
        this.f_36079_ = Mth.m_14045_(this.f_36079_ + p_36291_, 0, Integer.MAX_VALUE);
        while (this.f_36080_ < 0.0f) {
            float $$1 = this.f_36080_ * (float)this.m_36323_();
            if (this.f_36078_ > 0) {
                this.m_6749_(-1);
                this.f_36080_ = 1.0f + $$1 / (float)this.m_36323_();
                continue;
            }
            this.m_6749_(-1);
            this.f_36080_ = 0.0f;
        }
        while (this.f_36080_ >= 1.0f) {
            this.f_36080_ = (this.f_36080_ - 1.0f) * (float)this.m_36323_();
            this.m_6749_(1);
            this.f_36080_ /= (float)this.m_36323_();
        }
    }

    public int m_36322_() {
        return this.f_36081_;
    }

    public void m_7408_(ItemStack p_36172_, int p_36173_) {
        this.f_36078_ -= p_36173_;
        if (this.f_36078_ < 0) {
            this.f_36078_ = 0;
            this.f_36080_ = 0.0f;
            this.f_36079_ = 0;
        }
        this.f_36081_ = this.f_19796_.m_188502_();
    }

    public void m_6749_(int p_36276_) {
        this.f_36078_ += p_36276_;
        if (this.f_36078_ < 0) {
            this.f_36078_ = 0;
            this.f_36080_ = 0.0f;
            this.f_36079_ = 0;
        }
        if (p_36276_ > 0 && this.f_36078_ % 5 == 0 && (float)this.f_36111_ < (float)this.f_19797_ - 100.0f) {
            float $$1 = this.f_36078_ > 30 ? 1.0f : (float)this.f_36078_ / 30.0f;
            this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12275_, this.m_5720_(), $$1 * 0.75f, 1.0f);
            this.f_36111_ = this.f_19797_;
        }
    }

    public int m_36323_() {
        if (this.f_36078_ >= 30) {
            return 112 + (this.f_36078_ - 30) * 9;
        }
        if (this.f_36078_ >= 15) {
            return 37 + (this.f_36078_ - 15) * 5;
        }
        return 7 + this.f_36078_ * 2;
    }

    public void m_36399_(float p_36400_) {
        if (this.f_36077_.f_35934_) {
            return;
        }
        if (!this.f_19853_.f_46443_) {
            this.f_36097_.m_38703_(p_36400_);
        }
    }

    public WardenSpawnTracker m_219758_() {
        return this.f_219724_;
    }

    public FoodData m_36324_() {
        return this.f_36097_;
    }

    public boolean m_36391_(boolean p_36392_) {
        return this.f_36077_.f_35934_ || p_36392_ || this.f_36097_.m_38721_();
    }

    public boolean m_36325_() {
        return this.m_21223_() > 0.0f && this.m_21223_() < this.m_21233_();
    }

    public boolean m_36326_() {
        return this.f_36077_.f_35938_;
    }

    public boolean m_36204_(BlockPos p_36205_, Direction p_36206_, ItemStack p_36207_) {
        if (this.f_36077_.f_35938_) {
            return true;
        }
        BlockPos $$3 = p_36205_.m_121945_(p_36206_.m_122424_());
        BlockInWorld $$4 = new BlockInWorld(this.f_19853_, $$3, false);
        return p_36207_.m_204121_(this.f_19853_.m_5962_().m_175515_(Registry.f_122901_), $$4);
    }

    @Override
    public int m_213860_() {
        if (this.f_19853_.m_46469_().m_46207_(GameRules.f_46133_) || this.m_5833_()) {
            return 0;
        }
        int $$0 = this.f_36078_ * 7;
        if ($$0 > 100) {
            return 100;
        }
        return $$0;
    }

    @Override
    protected boolean m_6124_() {
        return true;
    }

    @Override
    public boolean m_6052_() {
        return true;
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return !this.f_36077_.f_35935_ && (!this.f_19861_ || !this.m_20163_()) ? Entity.MovementEmission.ALL : Entity.MovementEmission.NONE;
    }

    public void m_6885_() {
    }

    @Override
    public Component m_7755_() {
        return Component.m_237113_(this.f_36084_.getName());
    }

    public PlayerEnderChestContainer m_36327_() {
        return this.f_36094_;
    }

    @Override
    public ItemStack m_6844_(EquipmentSlot p_36257_) {
        if (p_36257_ == EquipmentSlot.MAINHAND) {
            return this.f_36093_.m_36056_();
        }
        if (p_36257_ == EquipmentSlot.OFFHAND) {
            return this.f_36093_.f_35976_.get(0);
        }
        if (p_36257_.m_20743_() == EquipmentSlot.Type.ARMOR) {
            return this.f_36093_.f_35975_.get(p_36257_.m_20749_());
        }
        return ItemStack.f_41583_;
    }

    @Override
    protected boolean m_213772_(EquipmentSlot p_219741_) {
        return p_219741_.m_20743_() == EquipmentSlot.Type.ARMOR;
    }

    @Override
    public void m_8061_(EquipmentSlot p_36161_, ItemStack p_36162_) {
        this.m_181122_(p_36162_);
        if (p_36161_ == EquipmentSlot.MAINHAND) {
            this.m_238392_(p_36161_, this.f_36093_.f_35974_.set(this.f_36093_.f_35977_, p_36162_), p_36162_);
        } else if (p_36161_ == EquipmentSlot.OFFHAND) {
            this.m_238392_(p_36161_, this.f_36093_.f_35976_.set(0, p_36162_), p_36162_);
        } else if (p_36161_.m_20743_() == EquipmentSlot.Type.ARMOR) {
            this.m_238392_(p_36161_, this.f_36093_.f_35975_.set(p_36161_.m_20749_(), p_36162_), p_36162_);
        }
    }

    public boolean m_36356_(ItemStack p_36357_) {
        return this.f_36093_.m_36054_(p_36357_);
    }

    @Override
    public Iterable<ItemStack> m_6167_() {
        return Lists.newArrayList((Object[])new ItemStack[]{this.m_21205_(), this.m_21206_()});
    }

    @Override
    public Iterable<ItemStack> m_6168_() {
        return this.f_36093_.f_35975_;
    }

    public boolean m_36360_(CompoundTag p_36361_) {
        if (this.m_20159_() || !this.f_19861_ || this.m_20069_() || this.f_146808_) {
            return false;
        }
        if (this.m_36331_().m_128456_()) {
            this.m_36362_(p_36361_);
            this.f_36109_ = this.f_19853_.m_46467_();
            return true;
        }
        if (this.m_36332_().m_128456_()) {
            this.m_36364_(p_36361_);
            this.f_36109_ = this.f_19853_.m_46467_();
            return true;
        }
        return false;
    }

    protected void m_36328_() {
        if (this.f_36109_ + 20L < this.f_19853_.m_46467_()) {
            this.m_36370_(this.m_36331_());
            this.m_36362_(new CompoundTag());
            this.m_36370_(this.m_36332_());
            this.m_36364_(new CompoundTag());
        }
    }

    private void m_36370_(CompoundTag p_36371_) {
        if (!this.f_19853_.f_46443_ && !p_36371_.m_128456_()) {
            EntityType.m_20642_(p_36371_, this.f_19853_).ifPresent(p_219733_ -> {
                if (p_219733_ instanceof TamableAnimal) {
                    ((TamableAnimal)p_219733_).m_21816_(this.f_19820_);
                }
                p_219733_.m_6034_(this.m_20185_(), this.m_20186_() + (double)0.7f, this.m_20189_());
                ((ServerLevel)this.f_19853_).m_8847_((Entity)p_219733_);
            });
        }
    }

    @Override
    public abstract boolean m_5833_();

    @Override
    public boolean m_6069_() {
        return !this.f_36077_.f_35935_ && !this.m_5833_() && super.m_6069_();
    }

    public abstract boolean m_7500_();

    @Override
    public boolean m_6063_() {
        return !this.f_36077_.f_35935_;
    }

    public Scoreboard m_36329_() {
        return this.f_19853_.m_6188_();
    }

    @Override
    public Component m_5446_() {
        MutableComponent $$0 = PlayerTeam.m_83348_(this.m_5647_(), this.m_7755_());
        return this.m_36218_($$0);
    }

    private MutableComponent m_36218_(MutableComponent p_36219_) {
        String $$1 = this.m_36316_().getName();
        return p_36219_.m_130938_(p_219748_ -> p_219748_.m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + $$1 + " ")).m_131144_(this.m_20190_()).m_131138_($$1));
    }

    @Override
    public String m_6302_() {
        return this.m_36316_().getName();
    }

    @Override
    public float m_6431_(Pose p_36259_, EntityDimensions p_36260_) {
        switch (p_36259_) {
            case SWIMMING: 
            case FALL_FLYING: 
            case SPIN_ATTACK: {
                return 0.4f;
            }
            case CROUCHING: {
                return 1.27f;
            }
        }
        return 1.62f;
    }

    @Override
    public void m_7911_(float p_36396_) {
        if (p_36396_ < 0.0f) {
            p_36396_ = 0.0f;
        }
        this.m_20088_().m_135381_(f_36107_, Float.valueOf(p_36396_));
    }

    @Override
    public float m_6103_() {
        return this.m_20088_().m_135370_(f_36107_).floatValue();
    }

    public boolean m_36170_(PlayerModelPart p_36171_) {
        return (this.m_20088_().m_135370_(f_36089_) & p_36171_.m_36445_()) == p_36171_.m_36445_();
    }

    @Override
    public SlotAccess m_141942_(int p_150112_) {
        if (p_150112_ >= 0 && p_150112_ < this.f_36093_.f_35974_.size()) {
            return SlotAccess.m_147292_(this.f_36093_, p_150112_);
        }
        int $$1 = p_150112_ - 200;
        if ($$1 >= 0 && $$1 < this.f_36094_.m_6643_()) {
            return SlotAccess.m_147292_(this.f_36094_, $$1);
        }
        return super.m_141942_(p_150112_);
    }

    public boolean m_36330_() {
        return this.f_36085_;
    }

    public void m_36393_(boolean p_36394_) {
        this.f_36085_ = p_36394_;
    }

    @Override
    public void m_7311_(int p_36353_) {
        super.m_7311_(this.f_36077_.f_35934_ ? Math.min(p_36353_, 1) : p_36353_);
    }

    @Override
    public HumanoidArm m_5737_() {
        return this.f_19804_.m_135370_(f_36090_) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    public void m_36163_(HumanoidArm p_36164_) {
        this.f_19804_.m_135381_(f_36090_, (byte)(p_36164_ != HumanoidArm.LEFT ? 1 : 0));
    }

    public CompoundTag m_36331_() {
        return this.f_19804_.m_135370_(f_36091_);
    }

    protected void m_36362_(CompoundTag p_36363_) {
        this.f_19804_.m_135381_(f_36091_, p_36363_);
    }

    public CompoundTag m_36332_() {
        return this.f_19804_.m_135370_(f_36092_);
    }

    protected void m_36364_(CompoundTag p_36365_) {
        this.f_19804_.m_135381_(f_36092_, p_36365_);
    }

    public float m_36333_() {
        return (float)(1.0 / this.m_21133_(Attributes.f_22283_) * 20.0);
    }

    public float m_36403_(float p_36404_) {
        return Mth.m_14036_(((float)this.f_20922_ + p_36404_) / this.m_36333_(), 0.0f, 1.0f);
    }

    public void m_36334_() {
        this.f_20922_ = 0;
    }

    public ItemCooldowns m_36335_() {
        return this.f_36087_;
    }

    @Override
    protected float m_6041_() {
        return this.f_36077_.f_35935_ || this.m_21255_() ? 1.0f : super.m_6041_();
    }

    public float m_36336_() {
        return (float)this.m_21133_(Attributes.f_22286_);
    }

    public boolean m_36337_() {
        return this.f_36077_.f_35937_ && this.m_8088_() >= 2;
    }

    @Override
    public boolean m_7066_(ItemStack p_36315_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_36315_);
        return this.m_6844_($$1).m_41619_();
    }

    @Override
    public EntityDimensions m_6972_(Pose p_36166_) {
        return f_36074_.getOrDefault((Object)p_36166_, f_36088_);
    }

    @Override
    public ImmutableList<Pose> m_7431_() {
        return ImmutableList.of((Object)((Object)Pose.STANDING), (Object)((Object)Pose.CROUCHING), (Object)((Object)Pose.SWIMMING));
    }

    @Override
    public ItemStack m_6298_(ItemStack p_36349_) {
        if (!(p_36349_.m_41720_() instanceof ProjectileWeaponItem)) {
            return ItemStack.f_41583_;
        }
        Predicate<ItemStack> $$1 = ((ProjectileWeaponItem)p_36349_.m_41720_()).m_6442_();
        ItemStack $$2 = ProjectileWeaponItem.m_43010_(this, $$1);
        if (!$$2.m_41619_()) {
            return $$2;
        }
        $$1 = ((ProjectileWeaponItem)p_36349_.m_41720_()).m_6437_();
        for (int $$3 = 0; $$3 < this.f_36093_.m_6643_(); ++$$3) {
            ItemStack $$4 = this.f_36093_.m_8020_($$3);
            if (!$$1.test($$4)) continue;
            return $$4;
        }
        return this.f_36077_.f_35937_ ? new ItemStack(Items.f_42412_) : ItemStack.f_41583_;
    }

    @Override
    public ItemStack m_5584_(Level p_36185_, ItemStack p_36186_) {
        this.m_36324_().m_38712_(p_36186_.m_41720_(), p_36186_);
        this.m_36246_(Stats.f_12982_.m_12902_(p_36186_.m_41720_()));
        p_36185_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12321_, SoundSource.PLAYERS, 0.5f, p_36185_.f_46441_.m_188501_() * 0.1f + 0.9f);
        if (this instanceof ServerPlayer) {
            CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)this, p_36186_);
        }
        return super.m_5584_(p_36185_, p_36186_);
    }

    @Override
    protected boolean m_6757_(BlockState p_36262_) {
        return this.f_36077_.f_35935_ || super.m_6757_(p_36262_);
    }

    @Override
    public Vec3 m_7398_(float p_36374_) {
        double $$1 = 0.22 * (this.m_5737_() == HumanoidArm.RIGHT ? -1.0 : 1.0);
        float $$2 = Mth.m_14179_(p_36374_ * 0.5f, this.m_146909_(), this.f_19860_) * ((float)Math.PI / 180);
        float $$3 = Mth.m_14179_(p_36374_, this.f_20884_, this.f_20883_) * ((float)Math.PI / 180);
        if (this.m_21255_() || this.m_21209_()) {
            float $$11;
            Vec3 $$4 = this.m_20252_(p_36374_);
            Vec3 $$5 = this.m_20184_();
            double $$6 = $$5.m_165925_();
            double $$7 = $$4.m_165925_();
            if ($$6 > 0.0 && $$7 > 0.0) {
                double $$8 = ($$5.f_82479_ * $$4.f_82479_ + $$5.f_82481_ * $$4.f_82481_) / Math.sqrt($$6 * $$7);
                double $$9 = $$5.f_82479_ * $$4.f_82481_ - $$5.f_82481_ * $$4.f_82479_;
                float $$10 = (float)(Math.signum($$9) * Math.acos($$8));
            } else {
                $$11 = 0.0f;
            }
            return this.m_20318_(p_36374_).m_82549_(new Vec3($$1, -0.11, 0.85).m_82535_(-$$11).m_82496_(-$$2).m_82524_(-$$3));
        }
        if (this.m_6067_()) {
            return this.m_20318_(p_36374_).m_82549_(new Vec3($$1, 0.2, -0.15).m_82496_(-$$2).m_82524_(-$$3));
        }
        double $$12 = this.m_20191_().m_82376_() - 1.0;
        double $$13 = this.m_6047_() ? -0.2 : 0.07;
        return this.m_20318_(p_36374_).m_82549_(new Vec3($$1, $$12, $$13).m_82524_(-$$3));
    }

    @Override
    public boolean m_142389_() {
        return true;
    }

    public boolean m_150108_() {
        return this.m_6117_() && this.m_21211_().m_150930_(Items.f_151059_);
    }

    @Override
    public boolean m_142391_() {
        return false;
    }

    public Optional<GlobalPos> m_219759_() {
        return this.f_238176_;
    }

    public void m_219749_(Optional<GlobalPos> p_219750_) {
        this.f_238176_ = p_219750_;
    }

    public static final class BedSleepingProblem
    extends Enum<BedSleepingProblem> {
        public static final /* enum */ BedSleepingProblem NOT_POSSIBLE_HERE = new BedSleepingProblem();
        public static final /* enum */ BedSleepingProblem NOT_POSSIBLE_NOW = new BedSleepingProblem(Component.m_237115_("block.minecraft.bed.no_sleep"));
        public static final /* enum */ BedSleepingProblem TOO_FAR_AWAY = new BedSleepingProblem(Component.m_237115_("block.minecraft.bed.too_far_away"));
        public static final /* enum */ BedSleepingProblem OBSTRUCTED = new BedSleepingProblem(Component.m_237115_("block.minecraft.bed.obstructed"));
        public static final /* enum */ BedSleepingProblem OTHER_PROBLEM = new BedSleepingProblem();
        public static final /* enum */ BedSleepingProblem NOT_SAFE = new BedSleepingProblem(Component.m_237115_("block.minecraft.bed.not_safe"));
        @Nullable
        private final Component f_36413_;
        private static final /* synthetic */ BedSleepingProblem[] $VALUES;

        public static BedSleepingProblem[] values() {
            return (BedSleepingProblem[])$VALUES.clone();
        }

        public static BedSleepingProblem valueOf(String p_36425_) {
            return Enum.valueOf(BedSleepingProblem.class, p_36425_);
        }

        private BedSleepingProblem() {
            this.f_36413_ = null;
        }

        private BedSleepingProblem(Component p_36422_) {
            this.f_36413_ = p_36422_;
        }

        @Nullable
        public Component m_36423_() {
            return this.f_36413_;
        }

        private static /* synthetic */ BedSleepingProblem[] m_150113_() {
            return new BedSleepingProblem[]{NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OBSTRUCTED, OTHER_PROBLEM, NOT_SAFE};
        }

        static {
            $VALUES = BedSleepingProblem.m_150113_();
        }
    }
}

