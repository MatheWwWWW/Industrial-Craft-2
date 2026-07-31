/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;

public class Pillager
extends AbstractIllager
implements CrossbowAttackMob,
InventoryCarrier {
    private static final EntityDataAccessor<Boolean> f_33258_ = SynchedEntityData.m_135353_(Pillager.class, EntityDataSerializers.f_135035_);
    private static final int f_149740_ = 5;
    private static final int f_149738_ = 300;
    private static final float f_149739_ = 1.6f;
    private final SimpleContainer f_33259_ = new SimpleContainer(5);

    public Pillager(EntityType<? extends Pillager> p_33262_, Level p_33263_) {
        super((EntityType<? extends AbstractIllager>)p_33262_, p_33263_);
    }

    @Override
    protected void m_8099_() {
        super.m_8099_();
        this.f_21345_.m_25352_(0, new FloatGoal(this));
        this.f_21345_.m_25352_(2, new Raider.HoldGroundAttackGoal(this, this, 10.0f));
        this.f_21345_.m_25352_(3, new RangedCrossbowAttackGoal<Pillager>(this, 1.0, 8.0f));
        this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6));
        this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 15.0f, 1.0f));
        this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 15.0f));
        this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class).m_26044_(new Class[0]));
        this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<Player>((Mob)this, Player.class, true));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<AbstractVillager>((Mob)this, AbstractVillager.class, false));
        this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<IronGolem>((Mob)this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder m_33307_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.35f).m_22268_(Attributes.f_22276_, 24.0).m_22268_(Attributes.f_22281_, 5.0).m_22268_(Attributes.f_22277_, 32.0);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_33258_, false);
    }

    @Override
    public boolean m_5886_(ProjectileWeaponItem p_33280_) {
        return p_33280_ == Items.f_42717_;
    }

    public boolean m_33309_() {
        return this.f_19804_.m_135370_(f_33258_);
    }

    @Override
    public void m_6136_(boolean p_33302_) {
        this.f_19804_.m_135381_(f_33258_, p_33302_);
    }

    @Override
    public void m_5847_() {
        this.f_20891_ = 0;
    }

    @Override
    public void m_7380_(CompoundTag p_33300_) {
        super.m_7380_(p_33300_);
        ListTag $$1 = new ListTag();
        for (int $$2 = 0; $$2 < this.f_33259_.m_6643_(); ++$$2) {
            ItemStack $$3 = this.f_33259_.m_8020_($$2);
            if ($$3.m_41619_()) continue;
            $$1.add($$3.m_41739_(new CompoundTag()));
        }
        p_33300_.m_128365_("Inventory", $$1);
    }

    @Override
    public AbstractIllager.IllagerArmPose m_6768_() {
        if (this.m_33309_()) {
            return AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE;
        }
        if (this.m_21055_(Items.f_42717_)) {
            return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD;
        }
        if (this.m_5912_()) {
            return AbstractIllager.IllagerArmPose.ATTACKING;
        }
        return AbstractIllager.IllagerArmPose.NEUTRAL;
    }

    @Override
    public void m_7378_(CompoundTag p_33291_) {
        super.m_7378_(p_33291_);
        ListTag $$1 = p_33291_.m_128437_("Inventory", 10);
        for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
            ItemStack $$3 = ItemStack.m_41712_($$1.m_128728_($$2));
            if ($$3.m_41619_()) continue;
            this.f_33259_.m_19173_($$3);
        }
        this.m_21553_(true);
    }

    @Override
    public float m_5610_(BlockPos p_33288_, LevelReader p_33289_) {
        return 0.0f;
    }

    @Override
    public int m_5792_() {
        return 1;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_33282_, DifficultyInstance p_33283_, MobSpawnType p_33284_, @Nullable SpawnGroupData p_33285_, @Nullable CompoundTag p_33286_) {
        RandomSource $$5 = p_33282_.m_213780_();
        this.m_213945_($$5, p_33283_);
        this.m_213946_($$5, p_33283_);
        return super.m_6518_(p_33282_, p_33283_, p_33284_, p_33285_, p_33286_);
    }

    @Override
    protected void m_213945_(RandomSource p_219059_, DifficultyInstance p_219060_) {
        this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42717_));
    }

    @Override
    protected void m_214095_(RandomSource p_219056_, float p_219057_) {
        ItemStack $$2;
        super.m_214095_(p_219056_, p_219057_);
        if (p_219056_.m_188503_(300) == 0 && ($$2 = this.m_21205_()).m_150930_(Items.f_42717_)) {
            Map<Enchantment, Integer> $$3 = EnchantmentHelper.m_44831_($$2);
            $$3.putIfAbsent(Enchantments.f_44961_, 1);
            EnchantmentHelper.m_44865_($$3, $$2);
            this.m_8061_(EquipmentSlot.MAINHAND, $$2);
        }
    }

    @Override
    public boolean m_7307_(Entity p_33314_) {
        if (super.m_7307_(p_33314_)) {
            return true;
        }
        if (p_33314_ instanceof LivingEntity && ((LivingEntity)p_33314_).m_6336_() == MobType.f_21643_) {
            return this.m_5647_() == null && p_33314_.m_5647_() == null;
        }
        return false;
    }

    @Override
    protected SoundEvent m_7515_() {
        return SoundEvents.f_12307_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12309_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_33306_) {
        return SoundEvents.f_12310_;
    }

    @Override
    public void m_6504_(LivingEntity p_33272_, float p_33273_) {
        this.m_32336_(this, 1.6f);
    }

    @Override
    public void m_5811_(LivingEntity p_33275_, ItemStack p_33276_, Projectile p_33277_, float p_33278_) {
        this.m_32322_(this, p_33275_, p_33277_, p_33278_, 1.6f);
    }

    @Override
    public SimpleContainer m_35311_() {
        return this.f_33259_;
    }

    @Override
    protected void m_7581_(ItemEntity p_33296_) {
        ItemStack $$1 = p_33296_.m_32055_();
        if ($$1.m_41720_() instanceof BannerItem) {
            super.m_7581_(p_33296_);
        } else if (this.m_149744_($$1)) {
            this.m_21053_(p_33296_);
            ItemStack $$2 = this.f_33259_.m_19173_($$1);
            if ($$2.m_41619_()) {
                p_33296_.m_146870_();
            } else {
                $$1.m_41764_($$2.m_41613_());
            }
        }
    }

    private boolean m_149744_(ItemStack p_149745_) {
        return this.m_37886_() && p_149745_.m_150930_(Items.f_42660_);
    }

    @Override
    public SlotAccess m_141942_(int p_149743_) {
        int $$1 = p_149743_ - 300;
        if ($$1 >= 0 && $$1 < this.f_33259_.m_6643_()) {
            return SlotAccess.m_147292_(this.f_33259_, $$1);
        }
        return super.m_141942_(p_149743_);
    }

    @Override
    public void m_7895_(int p_33267_, boolean p_33268_) {
        boolean $$3;
        Raid $$2 = this.m_37885_();
        boolean bl = $$3 = this.f_19796_.m_188501_() <= $$2.m_37783_();
        if ($$3) {
            ItemStack $$4 = new ItemStack(Items.f_42717_);
            HashMap $$5 = Maps.newHashMap();
            if (p_33267_ > $$2.m_37724_(Difficulty.NORMAL)) {
                $$5.put(Enchantments.f_44960_, 2);
            } else if (p_33267_ > $$2.m_37724_(Difficulty.EASY)) {
                $$5.put(Enchantments.f_44960_, 1);
            }
            $$5.put(Enchantments.f_44959_, 1);
            EnchantmentHelper.m_44865_($$5, $$4);
            this.m_8061_(EquipmentSlot.MAINHAND, $$4);
        }
    }

    @Override
    public SoundEvent m_7930_() {
        return SoundEvents.f_12308_;
    }
}

