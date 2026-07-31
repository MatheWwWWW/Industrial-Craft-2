/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class Mob
extends LivingEntity {
    private static final EntityDataAccessor<Byte> f_21340_ = SynchedEntityData.m_135353_(Mob.class, EntityDataSerializers.f_135027_);
    private static final int f_147266_ = 1;
    private static final int f_147267_ = 2;
    private static final int f_147268_ = 4;
    protected static final int f_147265_ = 1;
    private static final Vec3i f_217048_ = new Vec3i(1, 0, 1);
    public static final float f_147269_ = 0.15f;
    public static final float f_147261_ = 0.55f;
    public static final float f_147262_ = 0.5f;
    public static final float f_147263_ = 0.25f;
    public static final String f_147264_ = "Leash";
    public static final float f_182333_ = 0.085f;
    public static final int f_217047_ = 2;
    public static final int f_186008_ = 2;
    public int f_21363_;
    protected int f_21364_;
    protected LookControl f_21365_;
    protected MoveControl f_21342_;
    protected JumpControl f_21343_;
    private final BodyRotationControl f_21361_;
    protected PathNavigation f_21344_;
    protected final GoalSelector f_21345_;
    protected final GoalSelector f_21346_;
    @Nullable
    private LivingEntity f_21362_;
    private final Sensing f_21349_;
    private final NonNullList<ItemStack> f_21350_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
    protected final float[] f_21347_ = new float[2];
    private final NonNullList<ItemStack> f_21351_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
    protected final float[] f_21348_ = new float[4];
    private boolean f_21352_;
    private boolean f_21353_;
    private final Map<BlockPathTypes, Float> f_21354_ = Maps.newEnumMap(BlockPathTypes.class);
    @Nullable
    private ResourceLocation f_21355_;
    private long f_21356_;
    @Nullable
    private Entity f_21357_;
    private int f_21358_;
    @Nullable
    private CompoundTag f_21359_;
    private BlockPos f_21360_ = BlockPos.f_121853_;
    private float f_21341_ = -1.0f;

    protected Mob(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super((EntityType<? extends LivingEntity>)p_21368_, p_21369_);
        this.f_21345_ = new GoalSelector(p_21369_.m_46658_());
        this.f_21346_ = new GoalSelector(p_21369_.m_46658_());
        this.f_21365_ = new LookControl(this);
        this.f_21342_ = new MoveControl(this);
        this.f_21343_ = new JumpControl(this);
        this.f_21361_ = this.m_7560_();
        this.f_21344_ = this.m_6037_(p_21369_);
        this.f_21349_ = new Sensing(this);
        Arrays.fill(this.f_21348_, 0.085f);
        Arrays.fill(this.f_21347_, 0.085f);
        if (p_21369_ != null && !p_21369_.f_46443_) {
            this.m_8099_();
        }
    }

    protected void m_8099_() {
    }

    public static AttributeSupplier.Builder m_21552_() {
        return LivingEntity.m_21183_().m_22268_(Attributes.f_22277_, 16.0).m_22266_(Attributes.f_22282_);
    }

    protected PathNavigation m_6037_(Level p_21480_) {
        return new GroundPathNavigation(this, p_21480_);
    }

    protected boolean m_8091_() {
        return false;
    }

    public float m_21439_(BlockPathTypes p_21440_) {
        Mob $$2;
        if (this.m_20202_() instanceof Mob && ((Mob)this.m_20202_()).m_8091_()) {
            Mob $$1 = (Mob)this.m_20202_();
        } else {
            $$2 = this;
        }
        Float $$3 = $$2.f_21354_.get((Object)p_21440_);
        return $$3 == null ? p_21440_.m_77124_() : $$3.floatValue();
    }

    public void m_21441_(BlockPathTypes p_21442_, float p_21443_) {
        this.f_21354_.put(p_21442_, Float.valueOf(p_21443_));
    }

    public boolean m_21481_(BlockPathTypes p_21482_) {
        return p_21482_ != BlockPathTypes.DANGER_FIRE && p_21482_ != BlockPathTypes.DANGER_CACTUS && p_21482_ != BlockPathTypes.DANGER_OTHER && p_21482_ != BlockPathTypes.WALKABLE_DOOR;
    }

    protected BodyRotationControl m_7560_() {
        return new BodyRotationControl(this);
    }

    public LookControl m_21563_() {
        return this.f_21365_;
    }

    public MoveControl m_21566_() {
        if (this.m_20159_() && this.m_20202_() instanceof Mob) {
            Mob $$0 = (Mob)this.m_20202_();
            return $$0.m_21566_();
        }
        return this.f_21342_;
    }

    public JumpControl m_21569_() {
        return this.f_21343_;
    }

    public PathNavigation m_21573_() {
        if (this.m_20159_() && this.m_20202_() instanceof Mob) {
            Mob $$0 = (Mob)this.m_20202_();
            return $$0.m_21573_();
        }
        return this.f_21344_;
    }

    public Sensing m_21574_() {
        return this.f_21349_;
    }

    @Nullable
    public LivingEntity m_5448_() {
        return this.f_21362_;
    }

    public void m_6710_(@Nullable LivingEntity p_21544_) {
        this.f_21362_ = p_21544_;
    }

    @Override
    public boolean m_6549_(EntityType<?> p_21399_) {
        return p_21399_ != EntityType.f_20453_;
    }

    public boolean m_5886_(ProjectileWeaponItem p_21430_) {
        return false;
    }

    public void m_8035_() {
        this.m_146850_(GameEvent.f_157806_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_21340_, (byte)0);
    }

    public int m_8100_() {
        return 80;
    }

    public void m_8032_() {
        SoundEvent $$0 = this.m_7515_();
        if ($$0 != null) {
            this.m_5496_($$0, this.m_6121_(), this.m_6100_());
        }
    }

    @Override
    public void m_6075_() {
        super.m_6075_();
        this.f_19853_.m_46473_().m_6180_("mobBaseTick");
        if (this.m_6084_() && this.f_19796_.m_188503_(1000) < this.f_21363_++) {
            this.m_21551_();
            this.m_8032_();
        }
        this.f_19853_.m_46473_().m_7238_();
    }

    @Override
    protected void m_6677_(DamageSource p_21493_) {
        this.m_21551_();
        super.m_6677_(p_21493_);
    }

    private void m_21551_() {
        this.f_21363_ = -this.m_8100_();
    }

    @Override
    public int m_213860_() {
        if (this.f_21364_ > 0) {
            int $$0 = this.f_21364_;
            for (int $$1 = 0; $$1 < this.f_21351_.size(); ++$$1) {
                if (this.f_21351_.get($$1).m_41619_() || !(this.f_21348_[$$1] <= 1.0f)) continue;
                $$0 += 1 + this.f_19796_.m_188503_(3);
            }
            for (int $$2 = 0; $$2 < this.f_21350_.size(); ++$$2) {
                if (this.f_21350_.get($$2).m_41619_() || !(this.f_21347_[$$2] <= 1.0f)) continue;
                $$0 += 1 + this.f_19796_.m_188503_(3);
            }
            return $$0;
        }
        return this.f_21364_;
    }

    public void m_21373_() {
        if (this.f_19853_.f_46443_) {
            for (int $$0 = 0; $$0 < 20; ++$$0) {
                double $$1 = this.f_19796_.m_188583_() * 0.02;
                double $$2 = this.f_19796_.m_188583_() * 0.02;
                double $$3 = this.f_19796_.m_188583_() * 0.02;
                double $$4 = 10.0;
                this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20165_(1.0) - $$1 * 10.0, this.m_20187_() - $$2 * 10.0, this.m_20262_(1.0) - $$3 * 10.0, $$1, $$2, $$3);
            }
        } else {
            this.f_19853_.m_7605_(this, (byte)20);
        }
    }

    @Override
    public void m_7822_(byte p_21375_) {
        if (p_21375_ == 20) {
            this.m_21373_();
        } else {
            super.m_7822_(p_21375_);
        }
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.f_19853_.f_46443_) {
            this.m_6119_();
            if (this.f_19797_ % 5 == 0) {
                this.m_8022_();
            }
        }
    }

    protected void m_8022_() {
        boolean $$0 = !(this.m_6688_() instanceof Mob);
        boolean $$1 = !(this.m_20202_() instanceof Boat);
        this.f_21345_.m_25360_(Goal.Flag.MOVE, $$0);
        this.f_21345_.m_25360_(Goal.Flag.JUMP, $$0 && $$1);
        this.f_21345_.m_25360_(Goal.Flag.LOOK, $$0);
    }

    @Override
    protected float m_5632_(float p_21538_, float p_21539_) {
        this.f_21361_.m_8121_();
        return p_21539_;
    }

    @Nullable
    protected SoundEvent m_7515_() {
        return null;
    }

    @Override
    public void m_7380_(CompoundTag p_21484_) {
        super.m_7380_(p_21484_);
        p_21484_.m_128379_("CanPickUpLoot", this.m_21531_());
        p_21484_.m_128379_("PersistenceRequired", this.f_21353_);
        ListTag $$1 = new ListTag();
        for (ItemStack itemStack : this.f_21351_) {
            CompoundTag compoundTag = new CompoundTag();
            if (!itemStack.m_41619_()) {
                itemStack.m_41739_(compoundTag);
            }
            $$1.add(compoundTag);
        }
        p_21484_.m_128365_("ArmorItems", $$1);
        ListTag $$4 = new ListTag();
        for (ItemStack itemStack : this.f_21350_) {
            CompoundTag $$6 = new CompoundTag();
            if (!itemStack.m_41619_()) {
                itemStack.m_41739_($$6);
            }
            $$4.add($$6);
        }
        p_21484_.m_128365_("HandItems", $$4);
        ListTag listTag = new ListTag();
        for (float $$8 : this.f_21348_) {
            listTag.add(FloatTag.m_128566_($$8));
        }
        p_21484_.m_128365_("ArmorDropChances", listTag);
        ListTag listTag2 = new ListTag();
        for (float $$10 : this.f_21347_) {
            listTag2.add(FloatTag.m_128566_($$10));
        }
        p_21484_.m_128365_("HandDropChances", listTag2);
        if (this.f_21357_ != null) {
            CompoundTag $$11 = new CompoundTag();
            if (this.f_21357_ instanceof LivingEntity) {
                UUID $$12 = this.f_21357_.m_20148_();
                $$11.m_128362_("UUID", $$12);
            } else if (this.f_21357_ instanceof HangingEntity) {
                BlockPos $$13 = ((HangingEntity)this.f_21357_).m_31748_();
                $$11.m_128405_("X", $$13.m_123341_());
                $$11.m_128405_("Y", $$13.m_123342_());
                $$11.m_128405_("Z", $$13.m_123343_());
            }
            p_21484_.m_128365_(f_147264_, $$11);
        } else if (this.f_21359_ != null) {
            p_21484_.m_128365_(f_147264_, this.f_21359_.m_6426_());
        }
        p_21484_.m_128379_("LeftHanded", this.m_21526_());
        if (this.f_21355_ != null) {
            p_21484_.m_128359_("DeathLootTable", this.f_21355_.toString());
            if (this.f_21356_ != 0L) {
                p_21484_.m_128356_("DeathLootTableSeed", this.f_21356_);
            }
        }
        if (this.m_21525_()) {
            p_21484_.m_128379_("NoAI", this.m_21525_());
        }
    }

    @Override
    public void m_7378_(CompoundTag p_21450_) {
        super.m_7378_(p_21450_);
        if (p_21450_.m_128425_("CanPickUpLoot", 1)) {
            this.m_21553_(p_21450_.m_128471_("CanPickUpLoot"));
        }
        this.f_21353_ = p_21450_.m_128471_("PersistenceRequired");
        if (p_21450_.m_128425_("ArmorItems", 9)) {
            ListTag $$1 = p_21450_.m_128437_("ArmorItems", 10);
            for (int $$2 = 0; $$2 < this.f_21351_.size(); ++$$2) {
                this.f_21351_.set($$2, ItemStack.m_41712_($$1.m_128728_($$2)));
            }
        }
        if (p_21450_.m_128425_("HandItems", 9)) {
            ListTag $$3 = p_21450_.m_128437_("HandItems", 10);
            for (int $$4 = 0; $$4 < this.f_21350_.size(); ++$$4) {
                this.f_21350_.set($$4, ItemStack.m_41712_($$3.m_128728_($$4)));
            }
        }
        if (p_21450_.m_128425_("ArmorDropChances", 9)) {
            ListTag $$5 = p_21450_.m_128437_("ArmorDropChances", 5);
            for (int $$6 = 0; $$6 < $$5.size(); ++$$6) {
                this.f_21348_[$$6] = $$5.m_128775_($$6);
            }
        }
        if (p_21450_.m_128425_("HandDropChances", 9)) {
            ListTag $$7 = p_21450_.m_128437_("HandDropChances", 5);
            for (int $$8 = 0; $$8 < $$7.size(); ++$$8) {
                this.f_21347_[$$8] = $$7.m_128775_($$8);
            }
        }
        if (p_21450_.m_128425_(f_147264_, 10)) {
            this.f_21359_ = p_21450_.m_128469_(f_147264_);
        }
        this.m_21559_(p_21450_.m_128471_("LeftHanded"));
        if (p_21450_.m_128425_("DeathLootTable", 8)) {
            this.f_21355_ = new ResourceLocation(p_21450_.m_128461_("DeathLootTable"));
            this.f_21356_ = p_21450_.m_128454_("DeathLootTableSeed");
        }
        this.m_21557_(p_21450_.m_128471_("NoAI"));
    }

    @Override
    protected void m_7625_(DamageSource p_21389_, boolean p_21390_) {
        super.m_7625_(p_21389_, p_21390_);
        this.f_21355_ = null;
    }

    @Override
    protected LootContext.Builder m_7771_(boolean p_21453_, DamageSource p_21454_) {
        return super.m_7771_(p_21453_, p_21454_).m_230908_(this.f_21356_, this.f_19796_);
    }

    @Override
    public final ResourceLocation m_5743_() {
        return this.f_21355_ == null ? this.m_7582_() : this.f_21355_;
    }

    protected ResourceLocation m_7582_() {
        return super.m_5743_();
    }

    public void m_21564_(float p_21565_) {
        this.f_20902_ = p_21565_;
    }

    public void m_21567_(float p_21568_) {
        this.f_20901_ = p_21568_;
    }

    public void m_21570_(float p_21571_) {
        this.f_20900_ = p_21571_;
    }

    @Override
    public void m_7910_(float p_21556_) {
        super.m_7910_(p_21556_);
        this.m_21564_(p_21556_);
    }

    @Override
    public void m_8107_() {
        super.m_8107_();
        this.f_19853_.m_46473_().m_6180_("looting");
        if (!this.f_19853_.f_46443_ && this.m_21531_() && this.m_6084_() && !this.f_20890_ && this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_)) {
            Vec3i $$0 = this.m_213552_();
            List<ItemEntity> $$1 = this.f_19853_.m_45976_(ItemEntity.class, this.m_20191_().m_82377_($$0.m_123341_(), $$0.m_123342_(), $$0.m_123343_()));
            for (ItemEntity $$2 : $$1) {
                if ($$2.m_213877_() || $$2.m_32055_().m_41619_() || $$2.m_32063_() || !this.m_7243_($$2.m_32055_())) continue;
                this.m_7581_($$2);
            }
        }
        this.f_19853_.m_46473_().m_7238_();
    }

    protected Vec3i m_213552_() {
        return f_217048_;
    }

    protected void m_7581_(ItemEntity p_21471_) {
        ItemStack $$1 = p_21471_.m_32055_();
        if (this.m_21540_($$1)) {
            this.m_21053_(p_21471_);
            this.m_7938_(p_21471_, $$1.m_41613_());
            p_21471_.m_146870_();
        }
    }

    public boolean m_21540_(ItemStack p_21541_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_21541_);
        ItemStack $$2 = this.m_6844_($$1);
        boolean $$3 = this.m_7808_(p_21541_, $$2);
        if ($$3 && this.m_7252_(p_21541_)) {
            double $$4 = this.m_21519_($$1);
            if (!$$2.m_41619_() && (double)Math.max(this.f_19796_.m_188501_() - 0.1f, 0.0f) < $$4) {
                this.m_19983_($$2);
            }
            this.m_21468_($$1, p_21541_);
            return true;
        }
        return false;
    }

    protected void m_21468_(EquipmentSlot p_21469_, ItemStack p_21470_) {
        this.m_8061_(p_21469_, p_21470_);
        this.m_21508_(p_21469_);
        this.f_21353_ = true;
    }

    public void m_21508_(EquipmentSlot p_21509_) {
        switch (p_21509_.m_20743_()) {
            case HAND: {
                this.f_21347_[p_21509_.m_20749_()] = 2.0f;
                break;
            }
            case ARMOR: {
                this.f_21348_[p_21509_.m_20749_()] = 2.0f;
            }
        }
    }

    protected boolean m_7808_(ItemStack p_21428_, ItemStack p_21429_) {
        if (p_21429_.m_41619_()) {
            return true;
        }
        if (p_21428_.m_41720_() instanceof SwordItem) {
            if (!(p_21429_.m_41720_() instanceof SwordItem)) {
                return true;
            }
            SwordItem $$2 = (SwordItem)p_21428_.m_41720_();
            SwordItem $$3 = (SwordItem)p_21429_.m_41720_();
            if ($$2.m_43299_() != $$3.m_43299_()) {
                return $$2.m_43299_() > $$3.m_43299_();
            }
            return this.m_21477_(p_21428_, p_21429_);
        }
        if (p_21428_.m_41720_() instanceof BowItem && p_21429_.m_41720_() instanceof BowItem) {
            return this.m_21477_(p_21428_, p_21429_);
        }
        if (p_21428_.m_41720_() instanceof CrossbowItem && p_21429_.m_41720_() instanceof CrossbowItem) {
            return this.m_21477_(p_21428_, p_21429_);
        }
        if (p_21428_.m_41720_() instanceof ArmorItem) {
            if (EnchantmentHelper.m_44920_(p_21429_)) {
                return false;
            }
            if (!(p_21429_.m_41720_() instanceof ArmorItem)) {
                return true;
            }
            ArmorItem $$4 = (ArmorItem)p_21428_.m_41720_();
            ArmorItem $$5 = (ArmorItem)p_21429_.m_41720_();
            if ($$4.m_40404_() != $$5.m_40404_()) {
                return $$4.m_40404_() > $$5.m_40404_();
            }
            if ($$4.m_40405_() != $$5.m_40405_()) {
                return $$4.m_40405_() > $$5.m_40405_();
            }
            return this.m_21477_(p_21428_, p_21429_);
        }
        if (p_21428_.m_41720_() instanceof DiggerItem) {
            if (p_21429_.m_41720_() instanceof BlockItem) {
                return true;
            }
            if (p_21429_.m_41720_() instanceof DiggerItem) {
                DiggerItem $$6 = (DiggerItem)p_21428_.m_41720_();
                DiggerItem $$7 = (DiggerItem)p_21429_.m_41720_();
                if ($$6.m_41008_() != $$7.m_41008_()) {
                    return $$6.m_41008_() > $$7.m_41008_();
                }
                return this.m_21477_(p_21428_, p_21429_);
            }
        }
        return false;
    }

    public boolean m_21477_(ItemStack p_21478_, ItemStack p_21479_) {
        if (p_21478_.m_41773_() < p_21479_.m_41773_() || p_21478_.m_41782_() && !p_21479_.m_41782_()) {
            return true;
        }
        if (p_21478_.m_41782_() && p_21479_.m_41782_()) {
            return p_21478_.m_41783_().m_128431_().stream().anyMatch(p_21513_ -> !p_21513_.equals("Damage")) && !p_21479_.m_41783_().m_128431_().stream().anyMatch(p_21503_ -> !p_21503_.equals("Damage"));
        }
        return false;
    }

    public boolean m_7252_(ItemStack p_21545_) {
        return true;
    }

    public boolean m_7243_(ItemStack p_21546_) {
        return this.m_7252_(p_21546_);
    }

    public boolean m_6785_(double p_21542_) {
        return true;
    }

    public boolean m_8023_() {
        return this.m_20159_();
    }

    protected boolean m_8028_() {
        return false;
    }

    @Override
    public void m_6043_() {
        if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
            this.m_146870_();
            return;
        }
        if (this.m_21532_() || this.m_8023_()) {
            this.f_20891_ = 0;
            return;
        }
        Player $$0 = this.f_19853_.m_45930_(this, -1.0);
        if ($$0 != null) {
            int $$2;
            int $$3;
            double $$1 = $$0.m_20280_(this);
            if ($$1 > (double)($$3 = ($$2 = this.m_6095_().m_20674_().m_21611_()) * $$2) && this.m_6785_($$1)) {
                this.m_146870_();
            }
            int $$4 = this.m_6095_().m_20674_().m_21612_();
            int $$5 = $$4 * $$4;
            if (this.f_20891_ > 600 && this.f_19796_.m_188503_(800) == 0 && $$1 > (double)$$5 && this.m_6785_($$1)) {
                this.m_146870_();
            } else if ($$1 < (double)$$5) {
                this.f_20891_ = 0;
            }
        }
    }

    @Override
    protected final void m_6140_() {
        ++this.f_20891_;
        this.f_19853_.m_46473_().m_6180_("sensing");
        this.f_21349_.m_26789_();
        this.f_19853_.m_46473_().m_7238_();
        int $$0 = this.f_19853_.m_7654_().m_129921_() + this.m_19879_();
        if ($$0 % 2 == 0 || this.f_19797_ <= 1) {
            this.f_19853_.m_46473_().m_6180_("targetSelector");
            this.f_21346_.m_25373_();
            this.f_19853_.m_46473_().m_7238_();
            this.f_19853_.m_46473_().m_6180_("goalSelector");
            this.f_21345_.m_25373_();
            this.f_19853_.m_46473_().m_7238_();
        } else {
            this.f_19853_.m_46473_().m_6180_("targetSelector");
            this.f_21346_.m_186081_(false);
            this.f_19853_.m_46473_().m_7238_();
            this.f_19853_.m_46473_().m_6180_("goalSelector");
            this.f_21345_.m_186081_(false);
            this.f_19853_.m_46473_().m_7238_();
        }
        this.f_19853_.m_46473_().m_6180_("navigation");
        this.f_21344_.m_7638_();
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("mob tick");
        this.m_8024_();
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_6180_("controls");
        this.f_19853_.m_46473_().m_6180_("move");
        this.f_21342_.m_8126_();
        this.f_19853_.m_46473_().m_6182_("look");
        this.f_21365_.m_8128_();
        this.f_19853_.m_46473_().m_6182_("jump");
        this.f_21343_.m_8124_();
        this.f_19853_.m_46473_().m_7238_();
        this.f_19853_.m_46473_().m_7238_();
        this.m_8025_();
    }

    protected void m_8025_() {
        DebugPackets.m_133699_(this.f_19853_, this, this.f_21345_);
    }

    protected void m_8024_() {
    }

    public int m_8132_() {
        return 40;
    }

    public int m_8085_() {
        return 75;
    }

    public int m_21529_() {
        return 10;
    }

    public void m_21391_(Entity p_21392_, float p_21393_, float p_21394_) {
        double $$7;
        double $$3 = p_21392_.m_20185_() - this.m_20185_();
        double $$4 = p_21392_.m_20189_() - this.m_20189_();
        if (p_21392_ instanceof LivingEntity) {
            LivingEntity $$5 = (LivingEntity)p_21392_;
            double $$6 = $$5.m_20188_() - this.m_20188_();
        } else {
            $$7 = (p_21392_.m_20191_().f_82289_ + p_21392_.m_20191_().f_82292_) / 2.0 - this.m_20188_();
        }
        double $$8 = Math.sqrt($$3 * $$3 + $$4 * $$4);
        float $$9 = (float)(Mth.m_14136_($$4, $$3) * 57.2957763671875) - 90.0f;
        float $$10 = (float)(-(Mth.m_14136_($$7, $$8) * 57.2957763671875));
        this.m_146926_(this.m_21376_(this.m_146909_(), $$10, p_21394_));
        this.m_146922_(this.m_21376_(this.m_146908_(), $$9, p_21393_));
    }

    private float m_21376_(float p_21377_, float p_21378_, float p_21379_) {
        float $$3 = Mth.m_14177_(p_21378_ - p_21377_);
        if ($$3 > p_21379_) {
            $$3 = p_21379_;
        }
        if ($$3 < -p_21379_) {
            $$3 = -p_21379_;
        }
        return p_21377_ + $$3;
    }

    public static boolean m_217057_(EntityType<? extends Mob> p_217058_, LevelAccessor p_217059_, MobSpawnType p_217060_, BlockPos p_217061_, RandomSource p_217062_) {
        BlockPos $$5 = p_217061_.m_7495_();
        return p_217060_ == MobSpawnType.SPAWNER || p_217059_.m_8055_($$5).m_60643_(p_217059_, $$5, p_217058_);
    }

    public boolean m_5545_(LevelAccessor p_21431_, MobSpawnType p_21432_) {
        return true;
    }

    public boolean m_6914_(LevelReader p_21433_) {
        return !p_21433_.m_46855_(this.m_20191_()) && p_21433_.m_45784_(this);
    }

    public int m_5792_() {
        return 4;
    }

    public boolean m_7296_(int p_21489_) {
        return false;
    }

    @Override
    public int m_6056_() {
        if (this.m_5448_() == null) {
            return 3;
        }
        int $$0 = (int)(this.m_21223_() - this.m_21233_() * 0.33f);
        if (($$0 -= (3 - this.f_19853_.m_46791_().m_19028_()) * 4) < 0) {
            $$0 = 0;
        }
        return $$0 + 3;
    }

    @Override
    public Iterable<ItemStack> m_6167_() {
        return this.f_21350_;
    }

    @Override
    public Iterable<ItemStack> m_6168_() {
        return this.f_21351_;
    }

    @Override
    public ItemStack m_6844_(EquipmentSlot p_21467_) {
        switch (p_21467_.m_20743_()) {
            case HAND: {
                return this.f_21350_.get(p_21467_.m_20749_());
            }
            case ARMOR: {
                return this.f_21351_.get(p_21467_.m_20749_());
            }
        }
        return ItemStack.f_41583_;
    }

    @Override
    public void m_8061_(EquipmentSlot p_21416_, ItemStack p_21417_) {
        this.m_181122_(p_21417_);
        switch (p_21416_.m_20743_()) {
            case HAND: {
                this.m_238392_(p_21416_, this.f_21350_.set(p_21416_.m_20749_(), p_21417_), p_21417_);
                break;
            }
            case ARMOR: {
                this.m_238392_(p_21416_, this.f_21351_.set(p_21416_.m_20749_(), p_21417_), p_21417_);
            }
        }
    }

    @Override
    protected void m_7472_(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
        super.m_7472_(p_21385_, p_21386_, p_21387_);
        for (EquipmentSlot $$3 : EquipmentSlot.values()) {
            boolean $$6;
            ItemStack $$4 = this.m_6844_($$3);
            float $$5 = this.m_21519_($$3);
            boolean bl = $$6 = $$5 > 1.0f;
            if ($$4.m_41619_() || EnchantmentHelper.m_44924_($$4) || !p_21387_ && !$$6 || !(Math.max(this.f_19796_.m_188501_() - (float)p_21386_ * 0.01f, 0.0f) < $$5)) continue;
            if (!$$6 && $$4.m_41763_()) {
                $$4.m_41721_($$4.m_41776_() - this.f_19796_.m_188503_(1 + this.f_19796_.m_188503_(Math.max($$4.m_41776_() - 3, 1))));
            }
            this.m_19983_($$4);
            this.m_8061_($$3, ItemStack.f_41583_);
        }
    }

    protected float m_21519_(EquipmentSlot p_21520_) {
        float $$3;
        switch (p_21520_.m_20743_()) {
            case HAND: {
                float $$1 = this.f_21347_[p_21520_.m_20749_()];
                break;
            }
            case ARMOR: {
                float $$2 = this.f_21348_[p_21520_.m_20749_()];
                break;
            }
            default: {
                $$3 = 0.0f;
            }
        }
        return $$3;
    }

    protected void m_213945_(RandomSource p_217055_, DifficultyInstance p_217056_) {
        if (p_217055_.m_188501_() < 0.15f * p_217056_.m_19057_()) {
            float $$3;
            int $$2 = p_217055_.m_188503_(2);
            float f = $$3 = this.f_19853_.m_46791_() == Difficulty.HARD ? 0.1f : 0.25f;
            if (p_217055_.m_188501_() < 0.095f) {
                ++$$2;
            }
            if (p_217055_.m_188501_() < 0.095f) {
                ++$$2;
            }
            if (p_217055_.m_188501_() < 0.095f) {
                ++$$2;
            }
            boolean $$4 = true;
            for (EquipmentSlot $$5 : EquipmentSlot.values()) {
                Item $$7;
                if ($$5.m_20743_() != EquipmentSlot.Type.ARMOR) continue;
                ItemStack $$6 = this.m_6844_($$5);
                if (!$$4 && p_217055_.m_188501_() < $$3) break;
                $$4 = false;
                if (!$$6.m_41619_() || ($$7 = Mob.m_21412_($$5, $$2)) == null) continue;
                this.m_8061_($$5, new ItemStack($$7));
            }
        }
    }

    @Nullable
    public static Item m_21412_(EquipmentSlot p_21413_, int p_21414_) {
        switch (p_21413_) {
            case HEAD: {
                if (p_21414_ == 0) {
                    return Items.f_42407_;
                }
                if (p_21414_ == 1) {
                    return Items.f_42476_;
                }
                if (p_21414_ == 2) {
                    return Items.f_42464_;
                }
                if (p_21414_ == 3) {
                    return Items.f_42468_;
                }
                if (p_21414_ == 4) {
                    return Items.f_42472_;
                }
            }
            case CHEST: {
                if (p_21414_ == 0) {
                    return Items.f_42408_;
                }
                if (p_21414_ == 1) {
                    return Items.f_42477_;
                }
                if (p_21414_ == 2) {
                    return Items.f_42465_;
                }
                if (p_21414_ == 3) {
                    return Items.f_42469_;
                }
                if (p_21414_ == 4) {
                    return Items.f_42473_;
                }
            }
            case LEGS: {
                if (p_21414_ == 0) {
                    return Items.f_42462_;
                }
                if (p_21414_ == 1) {
                    return Items.f_42478_;
                }
                if (p_21414_ == 2) {
                    return Items.f_42466_;
                }
                if (p_21414_ == 3) {
                    return Items.f_42470_;
                }
                if (p_21414_ == 4) {
                    return Items.f_42474_;
                }
            }
            case FEET: {
                if (p_21414_ == 0) {
                    return Items.f_42463_;
                }
                if (p_21414_ == 1) {
                    return Items.f_42479_;
                }
                if (p_21414_ == 2) {
                    return Items.f_42467_;
                }
                if (p_21414_ == 3) {
                    return Items.f_42471_;
                }
                if (p_21414_ != 4) break;
                return Items.f_42475_;
            }
        }
        return null;
    }

    protected void m_213946_(RandomSource p_217063_, DifficultyInstance p_217064_) {
        float $$2 = p_217064_.m_19057_();
        this.m_214095_(p_217063_, $$2);
        for (EquipmentSlot $$3 : EquipmentSlot.values()) {
            if ($$3.m_20743_() != EquipmentSlot.Type.ARMOR) continue;
            this.m_217051_(p_217063_, $$2, $$3);
        }
    }

    protected void m_214095_(RandomSource p_217049_, float p_217050_) {
        if (!this.m_21205_().m_41619_() && p_217049_.m_188501_() < 0.25f * p_217050_) {
            this.m_8061_(EquipmentSlot.MAINHAND, EnchantmentHelper.m_220292_(p_217049_, this.m_21205_(), (int)(5.0f + p_217050_ * (float)p_217049_.m_188503_(18)), false));
        }
    }

    protected void m_217051_(RandomSource p_217052_, float p_217053_, EquipmentSlot p_217054_) {
        ItemStack $$3 = this.m_6844_(p_217054_);
        if (!$$3.m_41619_() && p_217052_.m_188501_() < 0.5f * p_217053_) {
            this.m_8061_(p_217054_, EnchantmentHelper.m_220292_(p_217052_, $$3, (int)(5.0f + p_217053_ * (float)p_217052_.m_188503_(18)), false));
        }
    }

    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        RandomSource $$5 = p_21434_.m_213780_();
        this.m_21051_(Attributes.f_22277_).m_22125_(new AttributeModifier("Random spawn bonus", $$5.m_216328_(0.0, 0.11485000000000001), AttributeModifier.Operation.MULTIPLY_BASE));
        if ($$5.m_188501_() < 0.05f) {
            this.m_21559_(true);
        } else {
            this.m_21559_(false);
        }
        return p_21437_;
    }

    public void m_21530_() {
        this.f_21353_ = true;
    }

    public void m_21409_(EquipmentSlot p_21410_, float p_21411_) {
        switch (p_21410_.m_20743_()) {
            case HAND: {
                this.f_21347_[p_21410_.m_20749_()] = p_21411_;
                break;
            }
            case ARMOR: {
                this.f_21348_[p_21410_.m_20749_()] = p_21411_;
            }
        }
    }

    public boolean m_21531_() {
        return this.f_21352_;
    }

    public void m_21553_(boolean p_21554_) {
        this.f_21352_ = p_21554_;
    }

    @Override
    public boolean m_7066_(ItemStack p_21522_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_21522_);
        return this.m_6844_($$1).m_41619_() && this.m_21531_();
    }

    public boolean m_21532_() {
        return this.f_21353_;
    }

    @Override
    public final InteractionResult m_6096_(Player p_21420_, InteractionHand p_21421_) {
        if (!this.m_6084_()) {
            return InteractionResult.PASS;
        }
        if (this.m_21524_() == p_21420_) {
            this.m_21455_(true, !p_21420_.m_150110_().f_35937_);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        InteractionResult $$2 = this.m_21499_(p_21420_, p_21421_);
        if ($$2.m_19077_()) {
            return $$2;
        }
        $$2 = this.m_6071_(p_21420_, p_21421_);
        if ($$2.m_19077_()) {
            this.m_146850_(GameEvent.f_223708_);
            return $$2;
        }
        return super.m_6096_(p_21420_, p_21421_);
    }

    private InteractionResult m_21499_(Player p_21500_, InteractionHand p_21501_) {
        InteractionResult $$3;
        ItemStack $$2 = p_21500_.m_21120_(p_21501_);
        if ($$2.m_150930_(Items.f_42655_) && this.m_6573_(p_21500_)) {
            this.m_21463_(p_21500_, true);
            $$2.m_41774_(1);
            return InteractionResult.m_19078_(this.f_19853_.f_46443_);
        }
        if ($$2.m_150930_(Items.f_42656_) && ($$3 = $$2.m_41647_(p_21500_, this, p_21501_)).m_19077_()) {
            return $$3;
        }
        if ($$2.m_41720_() instanceof SpawnEggItem) {
            if (this.f_19853_ instanceof ServerLevel) {
                SpawnEggItem $$4 = (SpawnEggItem)$$2.m_41720_();
                Optional<Mob> $$5 = $$4.m_43215_(p_21500_, this, this.m_6095_(), (ServerLevel)this.f_19853_, this.m_20182_(), $$2);
                $$5.ifPresent(p_21476_ -> this.m_5502_(p_21500_, (Mob)p_21476_));
                return $$5.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    protected void m_5502_(Player p_21422_, Mob p_21423_) {
    }

    protected InteractionResult m_6071_(Player p_21472_, InteractionHand p_21473_) {
        return InteractionResult.PASS;
    }

    public boolean m_21533_() {
        return this.m_21444_(this.m_20183_());
    }

    public boolean m_21444_(BlockPos p_21445_) {
        if (this.f_21341_ == -1.0f) {
            return true;
        }
        return this.f_21360_.m_123331_(p_21445_) < (double)(this.f_21341_ * this.f_21341_);
    }

    public void m_21446_(BlockPos p_21447_, int p_21448_) {
        this.f_21360_ = p_21447_;
        this.f_21341_ = p_21448_;
    }

    public BlockPos m_21534_() {
        return this.f_21360_;
    }

    public float m_21535_() {
        return this.f_21341_;
    }

    public void m_147271_() {
        this.f_21341_ = -1.0f;
    }

    public boolean m_21536_() {
        return this.f_21341_ != -1.0f;
    }

    @Nullable
    public <T extends Mob> T m_21406_(EntityType<T> p_21407_, boolean p_21408_) {
        if (this.m_213877_()) {
            return null;
        }
        Mob $$2 = (Mob)p_21407_.m_20615_(this.f_19853_);
        $$2.m_20359_(this);
        $$2.m_6863_(this.m_6162_());
        $$2.m_21557_(this.m_21525_());
        if (this.m_8077_()) {
            $$2.m_6593_(this.m_7770_());
            $$2.m_20340_(this.m_20151_());
        }
        if (this.m_21532_()) {
            $$2.m_21530_();
        }
        $$2.m_20331_(this.m_20147_());
        if (p_21408_) {
            $$2.m_21553_(this.m_21531_());
            for (EquipmentSlot $$3 : EquipmentSlot.values()) {
                ItemStack $$4 = this.m_6844_($$3);
                if ($$4.m_41619_()) continue;
                $$2.m_8061_($$3, $$4.m_41777_());
                $$2.m_21409_($$3, this.m_21519_($$3));
                $$4.m_41764_(0);
            }
        }
        this.f_19853_.m_7967_($$2);
        if (this.m_20159_()) {
            Entity $$5 = this.m_20202_();
            this.m_8127_();
            $$2.m_7998_($$5, true);
        }
        this.m_146870_();
        return (T)$$2;
    }

    protected void m_6119_() {
        if (this.f_21359_ != null) {
            this.m_21528_();
        }
        if (this.f_21357_ == null) {
            return;
        }
        if (!this.m_6084_() || !this.f_21357_.m_6084_()) {
            this.m_21455_(true, true);
        }
    }

    public void m_21455_(boolean p_21456_, boolean p_21457_) {
        if (this.f_21357_ != null) {
            this.f_21357_ = null;
            this.f_21359_ = null;
            if (!this.f_19853_.f_46443_ && p_21457_) {
                this.m_19998_(Items.f_42655_);
            }
            if (!this.f_19853_.f_46443_ && p_21456_ && this.f_19853_ instanceof ServerLevel) {
                ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEntityLinkPacket(this, null));
            }
        }
    }

    public boolean m_6573_(Player p_21418_) {
        return !this.m_21523_() && !(this instanceof Enemy);
    }

    public boolean m_21523_() {
        return this.f_21357_ != null;
    }

    @Nullable
    public Entity m_21524_() {
        if (this.f_21357_ == null && this.f_21358_ != 0 && this.f_19853_.f_46443_) {
            this.f_21357_ = this.f_19853_.m_6815_(this.f_21358_);
        }
        return this.f_21357_;
    }

    public void m_21463_(Entity p_21464_, boolean p_21465_) {
        this.f_21357_ = p_21464_;
        this.f_21359_ = null;
        if (!this.f_19853_.f_46443_ && p_21465_ && this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEntityLinkPacket(this, this.f_21357_));
        }
        if (this.m_20159_()) {
            this.m_8127_();
        }
    }

    public void m_21506_(int p_21507_) {
        this.f_21358_ = p_21507_;
        this.m_21455_(false, false);
    }

    @Override
    public boolean m_7998_(Entity p_21396_, boolean p_21397_) {
        boolean $$2 = super.m_7998_(p_21396_, p_21397_);
        if ($$2 && this.m_21523_()) {
            this.m_21455_(true, true);
        }
        return $$2;
    }

    private void m_21528_() {
        if (this.f_21359_ != null && this.f_19853_ instanceof ServerLevel) {
            if (this.f_21359_.m_128403_("UUID")) {
                UUID $$0 = this.f_21359_.m_128342_("UUID");
                Entity $$1 = ((ServerLevel)this.f_19853_).m_8791_($$0);
                if ($$1 != null) {
                    this.m_21463_($$1, true);
                    return;
                }
            } else if (this.f_21359_.m_128425_("X", 99) && this.f_21359_.m_128425_("Y", 99) && this.f_21359_.m_128425_("Z", 99)) {
                BlockPos $$2 = NbtUtils.m_129239_(this.f_21359_);
                this.m_21463_(LeashFenceKnotEntity.m_31844_(this.f_19853_, $$2), true);
                return;
            }
            if (this.f_19797_ > 100) {
                this.m_19998_(Items.f_42655_);
                this.f_21359_ = null;
            }
        }
    }

    @Override
    public boolean m_6109_() {
        return this.m_217005_() && super.m_6109_();
    }

    @Override
    public boolean m_6142_() {
        return super.m_6142_() && !this.m_21525_();
    }

    public void m_21557_(boolean p_21558_) {
        byte $$1 = this.f_19804_.m_135370_(f_21340_);
        this.f_19804_.m_135381_(f_21340_, p_21558_ ? (byte)($$1 | 1) : (byte)($$1 & 0xFFFFFFFE));
    }

    public void m_21559_(boolean p_21560_) {
        byte $$1 = this.f_19804_.m_135370_(f_21340_);
        this.f_19804_.m_135381_(f_21340_, p_21560_ ? (byte)($$1 | 2) : (byte)($$1 & 0xFFFFFFFD));
    }

    public void m_21561_(boolean p_21562_) {
        byte $$1 = this.f_19804_.m_135370_(f_21340_);
        this.f_19804_.m_135381_(f_21340_, p_21562_ ? (byte)($$1 | 4) : (byte)($$1 & 0xFFFFFFFB));
    }

    public boolean m_21525_() {
        return (this.f_19804_.m_135370_(f_21340_) & 1) != 0;
    }

    public boolean m_21526_() {
        return (this.f_19804_.m_135370_(f_21340_) & 2) != 0;
    }

    public boolean m_5912_() {
        return (this.f_19804_.m_135370_(f_21340_) & 4) != 0;
    }

    public void m_6863_(boolean p_21451_) {
    }

    @Override
    public HumanoidArm m_5737_() {
        return this.m_21526_() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    public double m_142593_(LivingEntity p_147273_) {
        return this.m_20205_() * 2.0f * (this.m_20205_() * 2.0f) + p_147273_.m_20205_();
    }

    public boolean m_217066_(LivingEntity p_217067_) {
        double $$1 = this.m_20275_(p_217067_.m_20185_(), p_217067_.m_20186_(), p_217067_.m_20189_());
        return $$1 <= this.m_142593_(p_217067_);
    }

    @Override
    public boolean m_7327_(Entity p_21372_) {
        boolean $$4;
        int $$3;
        float $$1 = (float)this.m_21133_(Attributes.f_22281_);
        float $$2 = (float)this.m_21133_(Attributes.f_22282_);
        if (p_21372_ instanceof LivingEntity) {
            $$1 += EnchantmentHelper.m_44833_(this.m_21205_(), ((LivingEntity)p_21372_).m_6336_());
            $$2 += (float)EnchantmentHelper.m_44894_(this);
        }
        if (($$3 = EnchantmentHelper.m_44914_(this)) > 0) {
            p_21372_.m_20254_($$3 * 4);
        }
        if ($$4 = p_21372_.m_6469_(DamageSource.m_19370_(this), $$1)) {
            if ($$2 > 0.0f && p_21372_ instanceof LivingEntity) {
                ((LivingEntity)p_21372_).m_147240_($$2 * 0.5f, Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180)), -Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180)));
                this.m_20256_(this.m_20184_().m_82542_(0.6, 1.0, 0.6));
            }
            if (p_21372_ instanceof Player) {
                Player $$5 = (Player)p_21372_;
                this.m_21424_($$5, this.m_21205_(), $$5.m_6117_() ? $$5.m_21211_() : ItemStack.f_41583_);
            }
            this.m_19970_(this, p_21372_);
            this.m_21335_(p_21372_);
        }
        return $$4;
    }

    private void m_21424_(Player p_21425_, ItemStack p_21426_, ItemStack p_21427_) {
        if (!p_21426_.m_41619_() && !p_21427_.m_41619_() && p_21426_.m_41720_() instanceof AxeItem && p_21427_.m_150930_(Items.f_42740_)) {
            float $$3 = 0.25f + (float)EnchantmentHelper.m_44926_(this) * 0.05f;
            if (this.f_19796_.m_188501_() < $$3) {
                p_21425_.m_36335_().m_41524_(Items.f_42740_, 100);
                this.f_19853_.m_7605_(p_21425_, (byte)30);
            }
        }
    }

    protected boolean m_21527_() {
        if (this.f_19853_.m_46461_() && !this.f_19853_.f_46443_) {
            boolean $$2;
            float $$0 = this.m_213856_();
            BlockPos $$1 = new BlockPos(this.m_20185_(), this.m_20188_(), this.m_20189_());
            boolean bl = $$2 = this.m_20071_() || this.f_146808_ || this.f_146809_;
            if ($$0 > 0.5f && this.f_19796_.m_188501_() * 30.0f < ($$0 - 0.4f) * 2.0f && !$$2 && this.f_19853_.m_45527_($$1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void m_203347_(TagKey<Fluid> p_204045_) {
        if (this.m_21573_().m_26576_()) {
            super.m_203347_(p_204045_);
        } else {
            this.m_20256_(this.m_20184_().m_82520_(0.0, 0.3, 0.0));
        }
    }

    public void m_147272_() {
        this.f_21345_.m_148096_();
        this.m_6274_().m_147343_();
    }

    @Override
    protected void m_6089_() {
        super.m_6089_();
        this.m_21455_(true, false);
        this.m_20158_().forEach(p_181125_ -> p_181125_.m_41764_(0));
    }

    @Override
    @Nullable
    public ItemStack m_142340_() {
        SpawnEggItem $$0 = SpawnEggItem.m_43213_(this.m_6095_());
        if ($$0 == null) {
            return null;
        }
        return new ItemStack($$0);
    }

    public Iterable<BlockPos> m_238383_() {
        return ImmutableSet.of((Object)new BlockPos(this.m_20191_().f_82288_, (double)this.m_146904_(), this.m_20191_().f_82290_), (Object)new BlockPos(this.m_20191_().f_82288_, (double)this.m_146904_(), this.m_20191_().f_82293_), (Object)new BlockPos(this.m_20191_().f_82291_, (double)this.m_146904_(), this.m_20191_().f_82290_), (Object)new BlockPos(this.m_20191_().f_82291_, (double)this.m_146904_(), this.m_20191_().f_82293_));
    }
}

