/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.decoration;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStand
extends LivingEntity {
    public static final int f_149592_ = 5;
    private static final boolean f_149595_ = true;
    private static final Rotations f_31529_ = new Rotations(0.0f, 0.0f, 0.0f);
    private static final Rotations f_31530_ = new Rotations(0.0f, 0.0f, 0.0f);
    private static final Rotations f_31531_ = new Rotations(-10.0f, 0.0f, -10.0f);
    private static final Rotations f_31532_ = new Rotations(-15.0f, 0.0f, 10.0f);
    private static final Rotations f_31533_ = new Rotations(-1.0f, 0.0f, -1.0f);
    private static final Rotations f_31534_ = new Rotations(1.0f, 0.0f, 1.0f);
    private static final EntityDimensions f_31535_ = new EntityDimensions(0.0f, 0.0f, true);
    private static final EntityDimensions f_31536_ = EntityType.f_20529_.m_20680_().m_20388_(0.5f);
    private static final double f_149596_ = 0.1;
    private static final double f_149597_ = 0.9;
    private static final double f_149598_ = 0.4;
    private static final double f_149600_ = 1.6;
    public static final int f_149599_ = 8;
    public static final int f_149601_ = 16;
    public static final int f_149602_ = 1;
    public static final int f_149603_ = 4;
    public static final int f_149593_ = 8;
    public static final int f_149594_ = 16;
    public static final EntityDataAccessor<Byte> f_31524_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135027_);
    public static final EntityDataAccessor<Rotations> f_31546_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    public static final EntityDataAccessor<Rotations> f_31547_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    public static final EntityDataAccessor<Rotations> f_31548_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    public static final EntityDataAccessor<Rotations> f_31549_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    public static final EntityDataAccessor<Rotations> f_31550_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    public static final EntityDataAccessor<Rotations> f_31527_ = SynchedEntityData.m_135353_(ArmorStand.class, EntityDataSerializers.f_135037_);
    private static final Predicate<Entity> f_31537_ = p_31582_ -> p_31582_ instanceof AbstractMinecart && ((AbstractMinecart)p_31582_).m_6064_() == AbstractMinecart.Type.RIDEABLE;
    private final NonNullList<ItemStack> f_31538_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
    private final NonNullList<ItemStack> f_31539_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
    private boolean f_31540_;
    public long f_31528_;
    private int f_31541_;
    private Rotations f_31542_ = f_31529_;
    private Rotations f_31543_ = f_31530_;
    private Rotations f_31544_ = f_31531_;
    private Rotations f_31545_ = f_31532_;
    private Rotations f_31525_ = f_31533_;
    private Rotations f_31526_ = f_31534_;

    public ArmorStand(EntityType<? extends ArmorStand> p_31553_, Level p_31554_) {
        super((EntityType<? extends LivingEntity>)p_31553_, p_31554_);
        this.f_19793_ = 0.0f;
    }

    public ArmorStand(Level p_31556_, double p_31557_, double p_31558_, double p_31559_) {
        this((EntityType<? extends ArmorStand>)EntityType.f_20529_, p_31556_);
        this.m_6034_(p_31557_, p_31558_, p_31559_);
    }

    @Override
    public void m_6210_() {
        double $$0 = this.m_20185_();
        double $$1 = this.m_20186_();
        double $$2 = this.m_20189_();
        super.m_6210_();
        this.m_6034_($$0, $$1, $$2);
    }

    private boolean m_31560_() {
        return !this.m_31677_() && !this.m_20068_();
    }

    @Override
    public boolean m_6142_() {
        return super.m_6142_() && this.m_31560_();
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_31524_, (byte)0);
        this.f_19804_.m_135372_(f_31546_, f_31529_);
        this.f_19804_.m_135372_(f_31547_, f_31530_);
        this.f_19804_.m_135372_(f_31548_, f_31531_);
        this.f_19804_.m_135372_(f_31549_, f_31532_);
        this.f_19804_.m_135372_(f_31550_, f_31533_);
        this.f_19804_.m_135372_(f_31527_, f_31534_);
    }

    @Override
    public Iterable<ItemStack> m_6167_() {
        return this.f_31538_;
    }

    @Override
    public Iterable<ItemStack> m_6168_() {
        return this.f_31539_;
    }

    @Override
    public ItemStack m_6844_(EquipmentSlot p_31612_) {
        switch (p_31612_.m_20743_()) {
            case HAND: {
                return this.f_31538_.get(p_31612_.m_20749_());
            }
            case ARMOR: {
                return this.f_31539_.get(p_31612_.m_20749_());
            }
        }
        return ItemStack.f_41583_;
    }

    @Override
    public void m_8061_(EquipmentSlot p_31584_, ItemStack p_31585_) {
        this.m_181122_(p_31585_);
        switch (p_31584_.m_20743_()) {
            case HAND: {
                this.m_238392_(p_31584_, this.f_31538_.set(p_31584_.m_20749_(), p_31585_), p_31585_);
                break;
            }
            case ARMOR: {
                this.m_238392_(p_31584_, this.f_31539_.set(p_31584_.m_20749_(), p_31585_), p_31585_);
            }
        }
    }

    @Override
    public boolean m_7066_(ItemStack p_31638_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_31638_);
        return this.m_6844_($$1).m_41619_() && !this.m_31626_($$1);
    }

    @Override
    public void m_7380_(CompoundTag p_31619_) {
        super.m_7380_(p_31619_);
        ListTag $$1 = new ListTag();
        for (ItemStack $$2 : this.f_31539_) {
            CompoundTag $$3 = new CompoundTag();
            if (!$$2.m_41619_()) {
                $$2.m_41739_($$3);
            }
            $$1.add($$3);
        }
        p_31619_.m_128365_("ArmorItems", $$1);
        ListTag $$4 = new ListTag();
        for (ItemStack $$5 : this.f_31538_) {
            CompoundTag $$6 = new CompoundTag();
            if (!$$5.m_41619_()) {
                $$5.m_41739_($$6);
            }
            $$4.add($$6);
        }
        p_31619_.m_128365_("HandItems", $$4);
        p_31619_.m_128379_("Invisible", this.m_20145_());
        p_31619_.m_128379_("Small", this.m_31666_());
        p_31619_.m_128379_("ShowArms", this.m_31671_());
        p_31619_.m_128405_("DisabledSlots", this.f_31541_);
        p_31619_.m_128379_("NoBasePlate", this.m_31674_());
        if (this.m_31677_()) {
            p_31619_.m_128379_("Marker", this.m_31677_());
        }
        p_31619_.m_128365_("Pose", this.m_31561_());
    }

    @Override
    public void m_7378_(CompoundTag p_31600_) {
        super.m_7378_(p_31600_);
        if (p_31600_.m_128425_("ArmorItems", 9)) {
            ListTag $$1 = p_31600_.m_128437_("ArmorItems", 10);
            for (int $$2 = 0; $$2 < this.f_31539_.size(); ++$$2) {
                this.f_31539_.set($$2, ItemStack.m_41712_($$1.m_128728_($$2)));
            }
        }
        if (p_31600_.m_128425_("HandItems", 9)) {
            ListTag $$3 = p_31600_.m_128437_("HandItems", 10);
            for (int $$4 = 0; $$4 < this.f_31538_.size(); ++$$4) {
                this.f_31538_.set($$4, ItemStack.m_41712_($$3.m_128728_($$4)));
            }
        }
        this.m_6842_(p_31600_.m_128471_("Invisible"));
        this.m_31603_(p_31600_.m_128471_("Small"));
        this.m_31675_(p_31600_.m_128471_("ShowArms"));
        this.f_31541_ = p_31600_.m_128451_("DisabledSlots");
        this.m_31678_(p_31600_.m_128471_("NoBasePlate"));
        this.m_31681_(p_31600_.m_128471_("Marker"));
        this.f_19794_ = !this.m_31560_();
        CompoundTag $$5 = p_31600_.m_128469_("Pose");
        this.m_31657_($$5);
    }

    private void m_31657_(CompoundTag p_31658_) {
        ListTag $$1 = p_31658_.m_128437_("Head", 5);
        this.m_31597_($$1.isEmpty() ? f_31529_ : new Rotations($$1));
        ListTag $$2 = p_31658_.m_128437_("Body", 5);
        this.m_31616_($$2.isEmpty() ? f_31530_ : new Rotations($$2));
        ListTag $$3 = p_31658_.m_128437_("LeftArm", 5);
        this.m_31623_($$3.isEmpty() ? f_31531_ : new Rotations($$3));
        ListTag $$4 = p_31658_.m_128437_("RightArm", 5);
        this.m_31628_($$4.isEmpty() ? f_31532_ : new Rotations($$4));
        ListTag $$5 = p_31658_.m_128437_("LeftLeg", 5);
        this.m_31639_($$5.isEmpty() ? f_31533_ : new Rotations($$5));
        ListTag $$6 = p_31658_.m_128437_("RightLeg", 5);
        this.m_31651_($$6.isEmpty() ? f_31534_ : new Rotations($$6));
    }

    private CompoundTag m_31561_() {
        CompoundTag $$0 = new CompoundTag();
        if (!f_31529_.equals(this.f_31542_)) {
            $$0.m_128365_("Head", this.f_31542_.m_123155_());
        }
        if (!f_31530_.equals(this.f_31543_)) {
            $$0.m_128365_("Body", this.f_31543_.m_123155_());
        }
        if (!f_31531_.equals(this.f_31544_)) {
            $$0.m_128365_("LeftArm", this.f_31544_.m_123155_());
        }
        if (!f_31532_.equals(this.f_31545_)) {
            $$0.m_128365_("RightArm", this.f_31545_.m_123155_());
        }
        if (!f_31533_.equals(this.f_31525_)) {
            $$0.m_128365_("LeftLeg", this.f_31525_.m_123155_());
        }
        if (!f_31534_.equals(this.f_31526_)) {
            $$0.m_128365_("RightLeg", this.f_31526_.m_123155_());
        }
        return $$0;
    }

    @Override
    public boolean m_6094_() {
        return false;
    }

    @Override
    protected void m_7324_(Entity p_31564_) {
    }

    @Override
    protected void m_6138_() {
        List<Entity> $$0 = this.f_19853_.m_6249_(this, this.m_20191_(), f_31537_);
        for (int $$1 = 0; $$1 < $$0.size(); ++$$1) {
            Entity $$2 = $$0.get($$1);
            if (!(this.m_20280_($$2) <= 0.2)) continue;
            $$2.m_7334_(this);
        }
    }

    @Override
    public InteractionResult m_7111_(Player p_31594_, Vec3 p_31595_, InteractionHand p_31596_) {
        ItemStack $$3 = p_31594_.m_21120_(p_31596_);
        if (this.m_31677_() || $$3.m_150930_(Items.f_42656_)) {
            return InteractionResult.PASS;
        }
        if (p_31594_.m_5833_()) {
            return InteractionResult.SUCCESS;
        }
        if (p_31594_.f_19853_.f_46443_) {
            return InteractionResult.CONSUME;
        }
        EquipmentSlot $$4 = Mob.m_147233_($$3);
        if ($$3.m_41619_()) {
            EquipmentSlot $$6;
            EquipmentSlot $$5 = this.m_31659_(p_31595_);
            EquipmentSlot equipmentSlot = $$6 = this.m_31626_($$5) ? $$4 : $$5;
            if (this.m_21033_($$6) && this.m_31588_(p_31594_, $$6, $$3, p_31596_)) {
                return InteractionResult.SUCCESS;
            }
        } else {
            if (this.m_31626_($$4)) {
                return InteractionResult.FAIL;
            }
            if ($$4.m_20743_() == EquipmentSlot.Type.HAND && !this.m_31671_()) {
                return InteractionResult.FAIL;
            }
            if (this.m_31588_(p_31594_, $$4, $$3, p_31596_)) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private EquipmentSlot m_31659_(Vec3 p_31660_) {
        EquipmentSlot $$1 = EquipmentSlot.MAINHAND;
        boolean $$2 = this.m_31666_();
        double $$3 = $$2 ? p_31660_.f_82480_ * 2.0 : p_31660_.f_82480_;
        EquipmentSlot $$4 = EquipmentSlot.FEET;
        if ($$3 >= 0.1) {
            double d = $$2 ? 0.8 : 0.45;
            if ($$3 < 0.1 + d && this.m_21033_($$4)) {
                return EquipmentSlot.FEET;
            }
        }
        double d = $$2 ? 0.3 : 0.0;
        if ($$3 >= 0.9 + d) {
            double d2 = $$2 ? 1.0 : 0.7;
            if ($$3 < 0.9 + d2 && this.m_21033_(EquipmentSlot.CHEST)) {
                return EquipmentSlot.CHEST;
            }
        }
        if ($$3 >= 0.4) {
            double d3 = $$2 ? 1.0 : 0.8;
            if ($$3 < 0.4 + d3 && this.m_21033_(EquipmentSlot.LEGS)) {
                return EquipmentSlot.LEGS;
            }
        }
        if ($$3 >= 1.6 && this.m_21033_(EquipmentSlot.HEAD)) {
            return EquipmentSlot.HEAD;
        }
        if (this.m_21033_(EquipmentSlot.MAINHAND)) return $$1;
        if (!this.m_21033_(EquipmentSlot.OFFHAND)) return $$1;
        return EquipmentSlot.OFFHAND;
    }

    private boolean m_31626_(EquipmentSlot p_31627_) {
        return (this.f_31541_ & 1 << p_31627_.m_20750_()) != 0 || p_31627_.m_20743_() == EquipmentSlot.Type.HAND && !this.m_31671_();
    }

    private boolean m_31588_(Player p_31589_, EquipmentSlot p_31590_, ItemStack p_31591_, InteractionHand p_31592_) {
        ItemStack $$4 = this.m_6844_(p_31590_);
        if (!$$4.m_41619_() && (this.f_31541_ & 1 << p_31590_.m_20750_() + 8) != 0) {
            return false;
        }
        if ($$4.m_41619_() && (this.f_31541_ & 1 << p_31590_.m_20750_() + 16) != 0) {
            return false;
        }
        if (p_31589_.m_150110_().f_35937_ && $$4.m_41619_() && !p_31591_.m_41619_()) {
            ItemStack $$5 = p_31591_.m_41777_();
            $$5.m_41764_(1);
            this.m_8061_(p_31590_, $$5);
            return true;
        }
        if (!p_31591_.m_41619_() && p_31591_.m_41613_() > 1) {
            if (!$$4.m_41619_()) {
                return false;
            }
            ItemStack $$6 = p_31591_.m_41777_();
            $$6.m_41764_(1);
            this.m_8061_(p_31590_, $$6);
            p_31591_.m_41774_(1);
            return true;
        }
        this.m_8061_(p_31590_, p_31591_);
        p_31589_.m_21008_(p_31592_, $$4);
        return true;
    }

    @Override
    public boolean m_6469_(DamageSource p_31579_, float p_31580_) {
        if (this.f_19853_.f_46443_ || this.m_213877_()) {
            return false;
        }
        if (DamageSource.f_19317_.equals(p_31579_)) {
            this.m_6074_();
            return false;
        }
        if (this.m_6673_(p_31579_) || this.f_31540_ || this.m_31677_()) {
            return false;
        }
        if (p_31579_.m_19372_()) {
            this.m_31653_(p_31579_);
            this.m_6074_();
            return false;
        }
        if (DamageSource.f_19305_.equals(p_31579_)) {
            if (this.m_6060_()) {
                this.m_31648_(p_31579_, 0.15f);
            } else {
                this.m_20254_(5);
            }
            return false;
        }
        if (DamageSource.f_19307_.equals(p_31579_) && this.m_21223_() > 0.5f) {
            this.m_31648_(p_31579_, 4.0f);
            return false;
        }
        boolean $$2 = p_31579_.m_7640_() instanceof AbstractArrow;
        boolean $$3 = $$2 && ((AbstractArrow)p_31579_.m_7640_()).m_36796_() > 0;
        boolean $$4 = "player".equals(p_31579_.m_19385_());
        if (!$$4 && !$$2) {
            return false;
        }
        if (p_31579_.m_7639_() instanceof Player && !((Player)p_31579_.m_7639_()).m_150110_().f_35938_) {
            return false;
        }
        if (p_31579_.m_19390_()) {
            this.m_31566_();
            this.m_31565_();
            this.m_6074_();
            return $$3;
        }
        long $$5 = this.f_19853_.m_46467_();
        if ($$5 - this.f_31528_ <= 5L || $$2) {
            this.m_31646_(p_31579_);
            this.m_31565_();
            this.m_6074_();
        } else {
            this.f_19853_.m_7605_(this, (byte)32);
            this.m_146852_(GameEvent.f_223706_, p_31579_.m_7639_());
            this.f_31528_ = $$5;
        }
        return true;
    }

    @Override
    public void m_7822_(byte p_31568_) {
        if (p_31568_ == 32) {
            if (this.f_19853_.f_46443_) {
                this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11683_, this.m_5720_(), 0.3f, 1.0f, false);
                this.f_31528_ = this.f_19853_.m_46467_();
            }
        } else {
            super.m_7822_(p_31568_);
        }
    }

    @Override
    public boolean m_6783_(double p_31574_) {
        double $$1 = this.m_20191_().m_82309_() * 4.0;
        if (Double.isNaN($$1) || $$1 == 0.0) {
            $$1 = 4.0;
        }
        return p_31574_ < ($$1 *= 64.0) * $$1;
    }

    private void m_31565_() {
        if (this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_50705_.m_49966_()), this.m_20185_(), this.m_20227_(0.6666666666666666), this.m_20189_(), 10, this.m_20205_() / 4.0f, this.m_20206_() / 4.0f, this.m_20205_() / 4.0f, 0.05);
        }
    }

    private void m_31648_(DamageSource p_31649_, float p_31650_) {
        float $$2 = this.m_21223_();
        if (($$2 -= p_31650_) <= 0.5f) {
            this.m_31653_(p_31649_);
            this.m_6074_();
        } else {
            this.m_21153_($$2);
            this.m_146852_(GameEvent.f_223706_, p_31649_.m_7639_());
        }
    }

    private void m_31646_(DamageSource p_31647_) {
        Block.m_49840_(this.f_19853_, this.m_20183_(), new ItemStack(Items.f_42650_));
        this.m_31653_(p_31647_);
    }

    private void m_31653_(DamageSource p_31654_) {
        this.m_31566_();
        this.m_6668_(p_31654_);
        for (int $$1 = 0; $$1 < this.f_31538_.size(); ++$$1) {
            ItemStack $$2 = this.f_31538_.get($$1);
            if ($$2.m_41619_()) continue;
            Block.m_49840_(this.f_19853_, this.m_20183_().m_7494_(), $$2);
            this.f_31538_.set($$1, ItemStack.f_41583_);
        }
        for (int $$3 = 0; $$3 < this.f_31539_.size(); ++$$3) {
            ItemStack $$4 = this.f_31539_.get($$3);
            if ($$4.m_41619_()) continue;
            Block.m_49840_(this.f_19853_, this.m_20183_().m_7494_(), $$4);
            this.f_31539_.set($$3, ItemStack.f_41583_);
        }
    }

    private void m_31566_() {
        this.f_19853_.m_6263_(null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11681_, this.m_5720_(), 1.0f, 1.0f);
    }

    @Override
    protected float m_5632_(float p_31644_, float p_31645_) {
        this.f_20884_ = this.f_19859_;
        this.f_20883_ = this.m_146908_();
        return 0.0f;
    }

    @Override
    protected float m_6431_(Pose p_31614_, EntityDimensions p_31615_) {
        return p_31615_.f_20378_ * (this.m_6162_() ? 0.5f : 0.9f);
    }

    @Override
    public double m_6049_() {
        return this.m_31677_() ? 0.0 : (double)0.1f;
    }

    @Override
    public void m_7023_(Vec3 p_31656_) {
        if (!this.m_31560_()) {
            return;
        }
        super.m_7023_(p_31656_);
    }

    @Override
    public void m_5618_(float p_31670_) {
        this.f_20884_ = this.f_19859_ = p_31670_;
        this.f_20886_ = this.f_20885_ = p_31670_;
    }

    @Override
    public void m_5616_(float p_31668_) {
        this.f_20884_ = this.f_19859_ = p_31668_;
        this.f_20886_ = this.f_20885_ = p_31668_;
    }

    @Override
    public void m_8119_() {
        Rotations $$5;
        Rotations $$4;
        Rotations $$3;
        Rotations $$2;
        Rotations $$1;
        super.m_8119_();
        Rotations $$0 = this.f_19804_.m_135370_(f_31546_);
        if (!this.f_31542_.equals($$0)) {
            this.m_31597_($$0);
        }
        if (!this.f_31543_.equals($$1 = this.f_19804_.m_135370_(f_31547_))) {
            this.m_31616_($$1);
        }
        if (!this.f_31544_.equals($$2 = this.f_19804_.m_135370_(f_31548_))) {
            this.m_31623_($$2);
        }
        if (!this.f_31545_.equals($$3 = this.f_19804_.m_135370_(f_31549_))) {
            this.m_31628_($$3);
        }
        if (!this.f_31525_.equals($$4 = this.f_19804_.m_135370_(f_31550_))) {
            this.m_31639_($$4);
        }
        if (!this.f_31526_.equals($$5 = this.f_19804_.m_135370_(f_31527_))) {
            this.m_31651_($$5);
        }
    }

    @Override
    protected void m_8034_() {
        this.m_6842_(this.f_31540_);
    }

    @Override
    public void m_6842_(boolean p_31663_) {
        this.f_31540_ = p_31663_;
        super.m_6842_(p_31663_);
    }

    @Override
    public boolean m_6162_() {
        return this.m_31666_();
    }

    @Override
    public void m_6074_() {
        this.m_142687_(Entity.RemovalReason.KILLED);
        this.m_146850_(GameEvent.f_223707_);
    }

    @Override
    public boolean m_6128_() {
        return this.m_20145_();
    }

    @Override
    public PushReaction m_7752_() {
        if (this.m_31677_()) {
            return PushReaction.IGNORE;
        }
        return super.m_7752_();
    }

    private void m_31603_(boolean p_31604_) {
        this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 1, p_31604_));
    }

    public boolean m_31666_() {
        return (this.f_19804_.m_135370_(f_31524_) & 1) != 0;
    }

    private void m_31675_(boolean p_31676_) {
        this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 4, p_31676_));
    }

    public boolean m_31671_() {
        return (this.f_19804_.m_135370_(f_31524_) & 4) != 0;
    }

    private void m_31678_(boolean p_31679_) {
        this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 8, p_31679_));
    }

    public boolean m_31674_() {
        return (this.f_19804_.m_135370_(f_31524_) & 8) != 0;
    }

    private void m_31681_(boolean p_31682_) {
        this.f_19804_.m_135381_(f_31524_, this.m_31569_(this.f_19804_.m_135370_(f_31524_), 16, p_31682_));
    }

    public boolean m_31677_() {
        return (this.f_19804_.m_135370_(f_31524_) & 0x10) != 0;
    }

    private byte m_31569_(byte p_31570_, int p_31571_, boolean p_31572_) {
        p_31570_ = p_31572_ ? (byte)(p_31570_ | p_31571_) : (byte)(p_31570_ & ~p_31571_);
        return p_31570_;
    }

    public void m_31597_(Rotations p_31598_) {
        this.f_31542_ = p_31598_;
        this.f_19804_.m_135381_(f_31546_, p_31598_);
    }

    public void m_31616_(Rotations p_31617_) {
        this.f_31543_ = p_31617_;
        this.f_19804_.m_135381_(f_31547_, p_31617_);
    }

    public void m_31623_(Rotations p_31624_) {
        this.f_31544_ = p_31624_;
        this.f_19804_.m_135381_(f_31548_, p_31624_);
    }

    public void m_31628_(Rotations p_31629_) {
        this.f_31545_ = p_31629_;
        this.f_19804_.m_135381_(f_31549_, p_31629_);
    }

    public void m_31639_(Rotations p_31640_) {
        this.f_31525_ = p_31640_;
        this.f_19804_.m_135381_(f_31550_, p_31640_);
    }

    public void m_31651_(Rotations p_31652_) {
        this.f_31526_ = p_31652_;
        this.f_19804_.m_135381_(f_31527_, p_31652_);
    }

    public Rotations m_31680_() {
        return this.f_31542_;
    }

    public Rotations m_31685_() {
        return this.f_31543_;
    }

    public Rotations m_31688_() {
        return this.f_31544_;
    }

    public Rotations m_31689_() {
        return this.f_31545_;
    }

    public Rotations m_31691_() {
        return this.f_31525_;
    }

    public Rotations m_31694_() {
        return this.f_31526_;
    }

    @Override
    public boolean m_6087_() {
        return super.m_6087_() && !this.m_31677_();
    }

    @Override
    public boolean m_7313_(Entity p_31687_) {
        return p_31687_ instanceof Player && !this.f_19853_.m_7966_((Player)p_31687_, this.m_20183_());
    }

    @Override
    public HumanoidArm m_5737_() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public LivingEntity.Fallsounds m_196493_() {
        return new LivingEntity.Fallsounds(SoundEvents.f_11682_, SoundEvents.f_11682_);
    }

    @Override
    @Nullable
    protected SoundEvent m_7975_(DamageSource p_31636_) {
        return SoundEvents.f_11683_;
    }

    @Override
    @Nullable
    protected SoundEvent m_5592_() {
        return SoundEvents.f_11681_;
    }

    @Override
    public void m_8038_(ServerLevel p_31576_, LightningBolt p_31577_) {
    }

    @Override
    public boolean m_5801_() {
        return false;
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_31602_) {
        if (f_31524_.equals(p_31602_)) {
            this.m_6210_();
            this.f_19850_ = !this.m_31677_();
        }
        super.m_7350_(p_31602_);
    }

    @Override
    public boolean m_5789_() {
        return false;
    }

    @Override
    public EntityDimensions m_6972_(Pose p_31587_) {
        return this.m_31683_(this.m_31677_());
    }

    private EntityDimensions m_31683_(boolean p_31684_) {
        if (p_31684_) {
            return f_31535_;
        }
        return this.m_6162_() ? f_31536_ : this.m_6095_().m_20680_();
    }

    @Override
    public Vec3 m_7371_(float p_31665_) {
        if (this.m_31677_()) {
            AABB $$1 = this.m_31683_(false).m_20393_(this.m_20182_());
            BlockPos $$2 = this.m_20183_();
            int $$3 = Integer.MIN_VALUE;
            for (BlockPos $$4 : BlockPos.m_121940_(new BlockPos($$1.f_82288_, $$1.f_82289_, $$1.f_82290_), new BlockPos($$1.f_82291_, $$1.f_82292_, $$1.f_82293_))) {
                int $$5 = Math.max(this.f_19853_.m_45517_(LightLayer.BLOCK, $$4), this.f_19853_.m_45517_(LightLayer.SKY, $$4));
                if ($$5 == 15) {
                    return Vec3.m_82512_($$4);
                }
                if ($$5 <= $$3) continue;
                $$3 = $$5;
                $$2 = $$4.m_7949_();
            }
            return Vec3.m_82512_($$2);
        }
        return super.m_7371_(p_31665_);
    }

    @Override
    public ItemStack m_142340_() {
        return new ItemStack(Items.f_42650_);
    }

    @Override
    public boolean m_142065_() {
        return !this.m_20145_() && !this.m_31677_();
    }
}

