/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Dynamic
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Piglin
extends AbstractPiglin
implements CrossbowAttackMob,
InventoryCarrier {
    private static final EntityDataAccessor<Boolean> f_34673_ = SynchedEntityData.m_135353_(Piglin.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_34674_ = SynchedEntityData.m_135353_(Piglin.class, EntityDataSerializers.f_135035_);
    private static final EntityDataAccessor<Boolean> f_34675_ = SynchedEntityData.m_135353_(Piglin.class, EntityDataSerializers.f_135035_);
    private static final UUID f_34676_ = UUID.fromString("766bfa64-11f3-11ea-8d71-362b9e155667");
    private static final AttributeModifier f_34677_ = new AttributeModifier(f_34676_, "Baby speed boost", (double)0.2f, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final int f_149918_ = 16;
    private static final float f_149919_ = 0.35f;
    private static final int f_149920_ = 5;
    private static final float f_149921_ = 1.6f;
    private static final float f_149922_ = 0.1f;
    private static final int f_149923_ = 3;
    private static final float f_149924_ = 0.2f;
    private static final float f_149925_ = 0.81f;
    private static final double f_149926_ = 0.5;
    private final SimpleContainer f_34678_ = new SimpleContainer(8);
    private boolean f_34679_;
    protected static final ImmutableList<SensorType<? extends Sensor<? super Piglin>>> f_34680_ = ImmutableList.of(SensorType.f_26811_, SensorType.f_26812_, SensorType.f_26810_, SensorType.f_26814_, SensorType.f_26819_);
    protected static final ImmutableList<MemoryModuleType<?>> f_34672_ = ImmutableList.of(MemoryModuleType.f_26371_, MemoryModuleType.f_26379_, MemoryModuleType.f_148204_, MemoryModuleType.f_148205_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_, MemoryModuleType.f_26347_, MemoryModuleType.f_26346_, MemoryModuleType.f_26332_, MemoryModuleType.f_26381_, MemoryModuleType.f_26382_, MemoryModuleType.f_26370_, (Object[])new MemoryModuleType[]{MemoryModuleType.f_26326_, MemoryModuleType.f_26372_, MemoryModuleType.f_26373_, MemoryModuleType.f_26374_, MemoryModuleType.f_26377_, MemoryModuleType.f_26334_, MemoryModuleType.f_26335_, MemoryModuleType.f_26383_, MemoryModuleType.f_26336_, MemoryModuleType.f_26337_, MemoryModuleType.f_26339_, MemoryModuleType.f_26338_, MemoryModuleType.f_26341_, MemoryModuleType.f_26342_, MemoryModuleType.f_26340_, MemoryModuleType.f_26344_, MemoryModuleType.f_26333_, MemoryModuleType.f_26351_, MemoryModuleType.f_26376_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_, MemoryModuleType.f_26343_, MemoryModuleType.f_26345_, MemoryModuleType.f_26354_, MemoryModuleType.f_26355_, MemoryModuleType.f_26356_});

    public Piglin(EntityType<? extends AbstractPiglin> p_34683_, Level p_34684_) {
        super(p_34683_, p_34684_);
        this.f_21364_ = 5;
    }

    @Override
    public void m_7380_(CompoundTag p_34751_) {
        super.m_7380_(p_34751_);
        if (this.m_6162_()) {
            p_34751_.m_128379_("IsBaby", true);
        }
        if (this.f_34679_) {
            p_34751_.m_128379_("CannotHunt", true);
        }
        p_34751_.m_128365_("Inventory", this.f_34678_.m_7927_());
    }

    @Override
    public void m_7378_(CompoundTag p_34725_) {
        super.m_7378_(p_34725_);
        this.m_6863_(p_34725_.m_128471_("IsBaby"));
        this.m_34791_(p_34725_.m_128471_("CannotHunt"));
        this.f_34678_.m_7797_(p_34725_.m_128437_("Inventory", 10));
    }

    @Override
    @VisibleForDebug
    public SimpleContainer m_35311_() {
        return this.f_34678_;
    }

    @Override
    protected void m_7472_(DamageSource p_34697_, int p_34698_, boolean p_34699_) {
        super.m_7472_(p_34697_, p_34698_, p_34699_);
        this.f_34678_.m_19195_().forEach(this::m_19983_);
    }

    protected ItemStack m_34778_(ItemStack p_34779_) {
        return this.f_34678_.m_19173_(p_34779_);
    }

    protected boolean m_34780_(ItemStack p_34781_) {
        return this.f_34678_.m_19183_(p_34781_);
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_34673_, false);
        this.f_19804_.m_135372_(f_34674_, false);
        this.f_19804_.m_135372_(f_34675_, false);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_34727_) {
        super.m_7350_(p_34727_);
        if (f_34673_.equals(p_34727_)) {
            this.m_6210_();
        }
    }

    public static AttributeSupplier.Builder m_34770_() {
        return Monster.m_33035_().m_22268_(Attributes.f_22276_, 16.0).m_22268_(Attributes.f_22279_, 0.35f).m_22268_(Attributes.f_22281_, 5.0);
    }

    public static boolean m_219197_(EntityType<Piglin> p_219198_, LevelAccessor p_219199_, MobSpawnType p_219200_, BlockPos p_219201_, RandomSource p_219202_) {
        return !p_219199_.m_8055_(p_219201_.m_7495_()).m_60713_(Blocks.f_50451_);
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_34717_, DifficultyInstance p_34718_, MobSpawnType p_34719_, @Nullable SpawnGroupData p_34720_, @Nullable CompoundTag p_34721_) {
        RandomSource $$5 = p_34717_.m_213780_();
        if (p_34719_ != MobSpawnType.STRUCTURE) {
            if ($$5.m_188501_() < 0.2f) {
                this.m_6863_(true);
            } else if (this.m_34667_()) {
                this.m_8061_(EquipmentSlot.MAINHAND, this.m_34772_());
            }
        }
        PiglinAi.m_219205_(this, p_34717_.m_213780_());
        this.m_213945_($$5, p_34718_);
        this.m_213946_($$5, p_34718_);
        return super.m_6518_(p_34717_, p_34718_, p_34719_, p_34720_, p_34721_);
    }

    @Override
    protected boolean m_8028_() {
        return false;
    }

    @Override
    public boolean m_6785_(double p_34775_) {
        return !this.m_21532_();
    }

    @Override
    protected void m_213945_(RandomSource p_219189_, DifficultyInstance p_219190_) {
        if (this.m_34667_()) {
            this.m_219191_(EquipmentSlot.HEAD, new ItemStack(Items.f_42476_), p_219189_);
            this.m_219191_(EquipmentSlot.CHEST, new ItemStack(Items.f_42477_), p_219189_);
            this.m_219191_(EquipmentSlot.LEGS, new ItemStack(Items.f_42478_), p_219189_);
            this.m_219191_(EquipmentSlot.FEET, new ItemStack(Items.f_42479_), p_219189_);
        }
    }

    private void m_219191_(EquipmentSlot p_219192_, ItemStack p_219193_, RandomSource p_219194_) {
        if (p_219194_.m_188501_() < 0.1f) {
            this.m_8061_(p_219192_, p_219193_);
        }
    }

    protected Brain.Provider<Piglin> m_5490_() {
        return Brain.m_21923_(f_34672_, f_34680_);
    }

    @Override
    protected Brain<?> m_8075_(Dynamic<?> p_34723_) {
        return PiglinAi.m_34840_(this, this.m_5490_().m_22073_(p_34723_));
    }

    public Brain<Piglin> m_6274_() {
        return super.m_6274_();
    }

    @Override
    public InteractionResult m_6071_(Player p_34745_, InteractionHand p_34746_) {
        InteractionResult $$2 = super.m_6071_(p_34745_, p_34746_);
        if ($$2.m_19077_()) {
            return $$2;
        }
        if (this.f_19853_.f_46443_) {
            boolean $$3 = PiglinAi.m_34909_(this, p_34745_.m_21120_(p_34746_)) && this.m_6389_() != PiglinArmPose.ADMIRING_ITEM;
            return $$3 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return PiglinAi.m_34846_(this, p_34745_, p_34746_);
    }

    @Override
    protected float m_6431_(Pose p_34740_, EntityDimensions p_34741_) {
        return this.m_6162_() ? 0.93f : 1.74f;
    }

    @Override
    public double m_6048_() {
        return (double)this.m_20206_() * 0.92;
    }

    @Override
    public void m_6863_(boolean p_34729_) {
        this.m_20088_().m_135381_(f_34673_, p_34729_);
        if (!this.f_19853_.f_46443_) {
            AttributeInstance $$1 = this.m_21051_(Attributes.f_22279_);
            $$1.m_22130_(f_34677_);
            if (p_34729_) {
                $$1.m_22118_(f_34677_);
            }
        }
    }

    @Override
    public boolean m_6162_() {
        return this.m_20088_().m_135370_(f_34673_);
    }

    private void m_34791_(boolean p_34792_) {
        this.f_34679_ = p_34792_;
    }

    @Override
    protected boolean m_7121_() {
        return !this.f_34679_;
    }

    @Override
    protected void m_8024_() {
        this.f_19853_.m_46473_().m_6180_("piglinBrain");
        this.m_6274_().m_21865_((ServerLevel)this.f_19853_, this);
        this.f_19853_.m_46473_().m_7238_();
        PiglinAi.m_34898_(this);
        super.m_8024_();
    }

    @Override
    public int m_213860_() {
        return this.f_21364_;
    }

    @Override
    protected void m_8063_(ServerLevel p_34756_) {
        PiglinAi.m_34927_(this);
        this.f_34678_.m_19195_().forEach(this::m_19983_);
        super.m_8063_(p_34756_);
    }

    private ItemStack m_34772_() {
        if ((double)this.f_19796_.m_188501_() < 0.5) {
            return new ItemStack(Items.f_42717_);
        }
        return new ItemStack(Items.f_42430_);
    }

    private boolean m_34773_() {
        return this.f_19804_.m_135370_(f_34674_);
    }

    @Override
    public void m_6136_(boolean p_34753_) {
        this.f_19804_.m_135381_(f_34674_, p_34753_);
    }

    @Override
    public void m_5847_() {
        this.f_20891_ = 0;
    }

    @Override
    public PiglinArmPose m_6389_() {
        if (this.m_34771_()) {
            return PiglinArmPose.DANCING;
        }
        if (PiglinAi.m_149965_(this.m_21206_())) {
            return PiglinArmPose.ADMIRING_ITEM;
        }
        if (this.m_5912_() && this.m_34668_()) {
            return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
        }
        if (this.m_34773_()) {
            return PiglinArmPose.CROSSBOW_CHARGE;
        }
        if (this.m_5912_() && this.m_21055_(Items.f_42717_)) {
            return PiglinArmPose.CROSSBOW_HOLD;
        }
        return PiglinArmPose.DEFAULT;
    }

    public boolean m_34771_() {
        return this.f_19804_.m_135370_(f_34675_);
    }

    public void m_34789_(boolean p_34790_) {
        this.f_19804_.m_135381_(f_34675_, p_34790_);
    }

    @Override
    public boolean m_6469_(DamageSource p_34694_, float p_34695_) {
        boolean $$2 = super.m_6469_(p_34694_, p_34695_);
        if (this.f_19853_.f_46443_) {
            return false;
        }
        if ($$2 && p_34694_.m_7639_() instanceof LivingEntity) {
            PiglinAi.m_34837_(this, (LivingEntity)p_34694_.m_7639_());
        }
        return $$2;
    }

    @Override
    public void m_6504_(LivingEntity p_34704_, float p_34705_) {
        this.m_32336_(this, 1.6f);
    }

    @Override
    public void m_5811_(LivingEntity p_34707_, ItemStack p_34708_, Projectile p_34709_, float p_34710_) {
        this.m_32322_(this, p_34707_, p_34709_, p_34710_, 1.6f);
    }

    @Override
    public boolean m_5886_(ProjectileWeaponItem p_34715_) {
        return p_34715_ == Items.f_42717_;
    }

    protected void m_34783_(ItemStack p_34784_) {
        this.m_21468_(EquipmentSlot.MAINHAND, p_34784_);
    }

    protected void m_34785_(ItemStack p_34786_) {
        if (p_34786_.m_150930_(PiglinAi.f_34794_)) {
            this.m_8061_(EquipmentSlot.OFFHAND, p_34786_);
            this.m_21508_(EquipmentSlot.OFFHAND);
        } else {
            this.m_21468_(EquipmentSlot.OFFHAND, p_34786_);
        }
    }

    @Override
    public boolean m_7243_(ItemStack p_34777_) {
        return this.f_19853_.m_46469_().m_46207_(GameRules.f_46132_) && this.m_21531_() && PiglinAi.m_34857_(this, p_34777_);
    }

    protected boolean m_34787_(ItemStack p_34788_) {
        EquipmentSlot $$1 = Mob.m_147233_(p_34788_);
        ItemStack $$2 = this.m_6844_($$1);
        return this.m_7808_(p_34788_, $$2);
    }

    @Override
    protected boolean m_7808_(ItemStack p_34712_, ItemStack p_34713_) {
        boolean $$3;
        if (EnchantmentHelper.m_44920_(p_34713_)) {
            return false;
        }
        boolean $$2 = PiglinAi.m_149965_(p_34712_) || p_34712_.m_150930_(Items.f_42717_);
        boolean bl = $$3 = PiglinAi.m_149965_(p_34713_) || p_34713_.m_150930_(Items.f_42717_);
        if ($$2 && !$$3) {
            return true;
        }
        if (!$$2 && $$3) {
            return false;
        }
        if (this.m_34667_() && !p_34712_.m_150930_(Items.f_42717_) && p_34713_.m_150930_(Items.f_42717_)) {
            return false;
        }
        return super.m_7808_(p_34712_, p_34713_);
    }

    @Override
    protected void m_7581_(ItemEntity p_34743_) {
        this.m_21053_(p_34743_);
        PiglinAi.m_34843_(this, p_34743_);
    }

    @Override
    public boolean m_7998_(Entity p_34701_, boolean p_34702_) {
        if (this.m_6162_() && p_34701_.m_6095_() == EntityType.f_20456_) {
            p_34701_ = this.m_34730_(p_34701_, 3);
        }
        return super.m_7998_(p_34701_, p_34702_);
    }

    private Entity m_34730_(Entity p_34731_, int p_34732_) {
        List<Entity> $$2 = p_34731_.m_20197_();
        if (p_34732_ == 1 || $$2.isEmpty()) {
            return p_34731_;
        }
        return this.m_34730_($$2.get(0), p_34732_ - 1);
    }

    @Override
    protected SoundEvent m_7515_() {
        if (this.f_19853_.f_46443_) {
            return null;
        }
        return PiglinAi.m_34947_(this).orElse(null);
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_34767_) {
        return SoundEvents.f_12244_;
    }

    @Override
    protected SoundEvent m_5592_() {
        return SoundEvents.f_12242_;
    }

    @Override
    protected void m_7355_(BlockPos p_34748_, BlockState p_34749_) {
        this.m_5496_(SoundEvents.f_12299_, 0.15f, 1.0f);
    }

    protected void m_219195_(SoundEvent p_219196_) {
        this.m_5496_(p_219196_, this.m_6121_(), this.m_6100_());
    }

    @Override
    protected void m_7580_() {
        this.m_219195_(SoundEvents.f_12300_);
    }
}

