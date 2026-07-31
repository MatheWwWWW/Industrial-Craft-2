/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.item;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class ItemEntity
extends Entity {
    private static final EntityDataAccessor<ItemStack> f_31984_ = SynchedEntityData.m_135353_(ItemEntity.class, EntityDataSerializers.f_135033_);
    private static final int f_149659_ = 6000;
    private static final int f_149660_ = Short.MAX_VALUE;
    private static final int f_149661_ = Short.MIN_VALUE;
    private int f_31985_;
    private int f_31986_;
    private int f_31987_ = 5;
    @Nullable
    private UUID f_31988_;
    @Nullable
    private UUID f_31982_;
    public final float f_31983_;

    public ItemEntity(EntityType<? extends ItemEntity> p_31991_, Level p_31992_) {
        super(p_31991_, p_31992_);
        this.f_31983_ = this.f_19796_.m_188501_() * (float)Math.PI * 2.0f;
        this.m_146922_(this.f_19796_.m_188501_() * 360.0f);
    }

    public ItemEntity(Level p_32001_, double p_32002_, double p_32003_, double p_32004_, ItemStack p_32005_) {
        this(p_32001_, p_32002_, p_32003_, p_32004_, p_32005_, p_32001_.f_46441_.m_188500_() * 0.2 - 0.1, 0.2, p_32001_.f_46441_.m_188500_() * 0.2 - 0.1);
    }

    public ItemEntity(Level p_149663_, double p_149664_, double p_149665_, double p_149666_, ItemStack p_149667_, double p_149668_, double p_149669_, double p_149670_) {
        this((EntityType<? extends ItemEntity>)EntityType.f_20461_, p_149663_);
        this.m_6034_(p_149664_, p_149665_, p_149666_);
        this.m_20334_(p_149668_, p_149669_, p_149670_);
        this.m_32045_(p_149667_);
    }

    private ItemEntity(ItemEntity p_31994_) {
        super(p_31994_.m_6095_(), p_31994_.f_19853_);
        this.m_32045_(p_31994_.m_32055_().m_41777_());
        this.m_20359_(p_31994_);
        this.f_31985_ = p_31994_.f_31985_;
        this.f_31983_ = p_31994_.f_31983_;
    }

    @Override
    public boolean m_213854_() {
        return this.m_32055_().m_204117_(ItemTags.f_215865_);
    }

    public Entity m_238333_() {
        return Util.m_214614_(this.m_32057_(), this.f_19853_::m_46003_);
    }

    @Override
    protected Entity.MovementEmission m_142319_() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_31984_, ItemStack.f_41583_);
    }

    @Override
    public void m_8119_() {
        double $$6;
        int $$5;
        if (this.m_32055_().m_41619_()) {
            this.m_146870_();
            return;
        }
        super.m_8119_();
        if (this.f_31986_ > 0 && this.f_31986_ != Short.MAX_VALUE) {
            --this.f_31986_;
        }
        this.f_19854_ = this.m_20185_();
        this.f_19855_ = this.m_20186_();
        this.f_19856_ = this.m_20189_();
        Vec3 $$0 = this.m_20184_();
        float $$1 = this.m_20192_() - 0.11111111f;
        if (this.m_20069_() && this.m_204036_(FluidTags.f_13131_) > (double)$$1) {
            this.m_32067_();
        } else if (this.m_20077_() && this.m_204036_(FluidTags.f_13132_) > (double)$$1) {
            this.m_32068_();
        } else if (!this.m_20068_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0, -0.04, 0.0));
        }
        if (this.f_19853_.f_46443_) {
            this.f_19794_ = false;
        } else {
            boolean bl = this.f_19794_ = !this.f_19853_.m_45756_(this, this.m_20191_().m_82406_(1.0E-7));
            if (this.f_19794_) {
                this.m_20314_(this.m_20185_(), (this.m_20191_().f_82289_ + this.m_20191_().f_82292_) / 2.0, this.m_20189_());
            }
        }
        if (!this.f_19861_ || this.m_20184_().m_165925_() > (double)1.0E-5f || (this.f_19797_ + this.m_19879_()) % 4 == 0) {
            this.m_6478_(MoverType.SELF, this.m_20184_());
            float $$2 = 0.98f;
            if (this.f_19861_) {
                $$2 = this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_())).m_60734_().m_49958_() * 0.98f;
            }
            this.m_20256_(this.m_20184_().m_82542_($$2, 0.98, $$2));
            if (this.f_19861_) {
                Vec3 $$3 = this.m_20184_();
                if ($$3.f_82480_ < 0.0) {
                    this.m_20256_($$3.m_82542_(1.0, -0.5, 1.0));
                }
            }
        }
        boolean $$4 = Mth.m_14107_(this.f_19854_) != Mth.m_14107_(this.m_20185_()) || Mth.m_14107_(this.f_19855_) != Mth.m_14107_(this.m_20186_()) || Mth.m_14107_(this.f_19856_) != Mth.m_14107_(this.m_20189_());
        int n = $$5 = $$4 ? 2 : 40;
        if (this.f_19797_ % $$5 == 0 && !this.f_19853_.f_46443_ && this.m_32070_()) {
            this.m_32069_();
        }
        if (this.f_31985_ != Short.MIN_VALUE) {
            ++this.f_31985_;
        }
        this.f_19812_ |= this.m_20073_();
        if (!this.f_19853_.f_46443_ && ($$6 = this.m_20184_().m_82546_($$0).m_82556_()) > 0.01) {
            this.f_19812_ = true;
        }
        if (!this.f_19853_.f_46443_ && this.f_31985_ >= 6000) {
            this.m_146870_();
        }
    }

    private void m_32067_() {
        Vec3 $$0 = this.m_20184_();
        this.m_20334_($$0.f_82479_ * (double)0.99f, $$0.f_82480_ + (double)($$0.f_82480_ < (double)0.06f ? 5.0E-4f : 0.0f), $$0.f_82481_ * (double)0.99f);
    }

    private void m_32068_() {
        Vec3 $$0 = this.m_20184_();
        this.m_20334_($$0.f_82479_ * (double)0.95f, $$0.f_82480_ + (double)($$0.f_82480_ < (double)0.06f ? 5.0E-4f : 0.0f), $$0.f_82481_ * (double)0.95f);
    }

    private void m_32069_() {
        if (!this.m_32070_()) {
            return;
        }
        List<ItemEntity> $$0 = this.f_19853_.m_6443_(ItemEntity.class, this.m_20191_().m_82377_(0.5, 0.0, 0.5), p_186268_ -> p_186268_ != this && p_186268_.m_32070_());
        for (ItemEntity $$1 : $$0) {
            if (!$$1.m_32070_()) continue;
            this.m_32015_($$1);
            if (!this.m_213877_()) continue;
            break;
        }
    }

    private boolean m_32070_() {
        ItemStack $$0 = this.m_32055_();
        return this.m_6084_() && this.f_31986_ != Short.MAX_VALUE && this.f_31985_ != Short.MIN_VALUE && this.f_31985_ < 6000 && $$0.m_41613_() < $$0.m_41741_();
    }

    private void m_32015_(ItemEntity p_32016_) {
        ItemStack $$1 = this.m_32055_();
        ItemStack $$2 = p_32016_.m_32055_();
        if (!Objects.equals(this.m_32056_(), p_32016_.m_32056_()) || !ItemEntity.m_32026_($$1, $$2)) {
            return;
        }
        if ($$2.m_41613_() < $$1.m_41613_()) {
            ItemEntity.m_32017_(this, $$1, p_32016_, $$2);
        } else {
            ItemEntity.m_32017_(p_32016_, $$2, this, $$1);
        }
    }

    public static boolean m_32026_(ItemStack p_32027_, ItemStack p_32028_) {
        if (!p_32028_.m_150930_(p_32027_.m_41720_())) {
            return false;
        }
        if (p_32028_.m_41613_() + p_32027_.m_41613_() > p_32028_.m_41741_()) {
            return false;
        }
        if (p_32028_.m_41782_() ^ p_32027_.m_41782_()) {
            return false;
        }
        return !p_32028_.m_41782_() || p_32028_.m_41783_().equals(p_32027_.m_41783_());
    }

    public static ItemStack m_32029_(ItemStack p_32030_, ItemStack p_32031_, int p_32032_) {
        int $$3 = Math.min(Math.min(p_32030_.m_41741_(), p_32032_) - p_32030_.m_41613_(), p_32031_.m_41613_());
        ItemStack $$4 = p_32030_.m_41777_();
        $$4.m_41769_($$3);
        p_32031_.m_41774_($$3);
        return $$4;
    }

    private static void m_32022_(ItemEntity p_32023_, ItemStack p_32024_, ItemStack p_32025_) {
        ItemStack $$3 = ItemEntity.m_32029_(p_32024_, p_32025_, 64);
        p_32023_.m_32045_($$3);
    }

    private static void m_32017_(ItemEntity p_32018_, ItemStack p_32019_, ItemEntity p_32020_, ItemStack p_32021_) {
        ItemEntity.m_32022_(p_32018_, p_32019_, p_32021_);
        p_32018_.f_31986_ = Math.max(p_32018_.f_31986_, p_32020_.f_31986_);
        p_32018_.f_31985_ = Math.min(p_32018_.f_31985_, p_32020_.f_31985_);
        if (p_32021_.m_41619_()) {
            p_32020_.m_146870_();
        }
    }

    @Override
    public boolean m_5825_() {
        return this.m_32055_().m_41720_().m_41475_() || super.m_5825_();
    }

    @Override
    public boolean m_6469_(DamageSource p_32013_, float p_32014_) {
        if (this.m_6673_(p_32013_)) {
            return false;
        }
        if (!this.m_32055_().m_41619_() && this.m_32055_().m_150930_(Items.f_42686_) && p_32013_.m_19372_()) {
            return false;
        }
        if (!this.m_32055_().m_41720_().m_41386_(p_32013_)) {
            return false;
        }
        if (this.f_19853_.f_46443_) {
            return true;
        }
        this.m_5834_();
        this.f_31987_ = (int)((float)this.f_31987_ - p_32014_);
        this.m_146852_(GameEvent.f_223706_, p_32013_.m_7639_());
        if (this.f_31987_ <= 0) {
            this.m_32055_().m_150924_(this);
            this.m_146870_();
        }
        return true;
    }

    @Override
    public void m_7380_(CompoundTag p_32050_) {
        p_32050_.m_128376_("Health", (short)this.f_31987_);
        p_32050_.m_128376_("Age", (short)this.f_31985_);
        p_32050_.m_128376_("PickupDelay", (short)this.f_31986_);
        if (this.m_32057_() != null) {
            p_32050_.m_128362_("Thrower", this.m_32057_());
        }
        if (this.m_32056_() != null) {
            p_32050_.m_128362_("Owner", this.m_32056_());
        }
        if (!this.m_32055_().m_41619_()) {
            p_32050_.m_128365_("Item", this.m_32055_().m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_32034_) {
        this.f_31987_ = p_32034_.m_128448_("Health");
        this.f_31985_ = p_32034_.m_128448_("Age");
        if (p_32034_.m_128441_("PickupDelay")) {
            this.f_31986_ = p_32034_.m_128448_("PickupDelay");
        }
        if (p_32034_.m_128403_("Owner")) {
            this.f_31982_ = p_32034_.m_128342_("Owner");
        }
        if (p_32034_.m_128403_("Thrower")) {
            this.f_31988_ = p_32034_.m_128342_("Thrower");
        }
        CompoundTag $$1 = p_32034_.m_128469_("Item");
        this.m_32045_(ItemStack.m_41712_($$1));
        if (this.m_32055_().m_41619_()) {
            this.m_146870_();
        }
    }

    @Override
    public void m_6123_(Player p_32040_) {
        if (this.f_19853_.f_46443_) {
            return;
        }
        ItemStack $$1 = this.m_32055_();
        Item $$2 = $$1.m_41720_();
        int $$3 = $$1.m_41613_();
        if (this.f_31986_ == 0 && (this.f_31982_ == null || this.f_31982_.equals(p_32040_.m_20148_())) && p_32040_.m_150109_().m_36054_($$1)) {
            p_32040_.m_7938_(this, $$3);
            if ($$1.m_41619_()) {
                this.m_146870_();
                $$1.m_41764_($$3);
            }
            p_32040_.m_6278_(Stats.f_12984_.m_12902_($$2), $$3);
            p_32040_.m_21053_(this);
        }
    }

    @Override
    public Component m_7755_() {
        Component $$0 = this.m_7770_();
        if ($$0 != null) {
            return $$0;
        }
        return Component.m_237115_(this.m_32055_().m_41778_());
    }

    @Override
    public boolean m_6097_() {
        return false;
    }

    @Override
    @Nullable
    public Entity m_5489_(ServerLevel p_32042_) {
        Entity $$1 = super.m_5489_(p_32042_);
        if (!this.f_19853_.f_46443_ && $$1 instanceof ItemEntity) {
            ((ItemEntity)$$1).m_32069_();
        }
        return $$1;
    }

    public ItemStack m_32055_() {
        return this.m_20088_().m_135370_(f_31984_);
    }

    public void m_32045_(ItemStack p_32046_) {
        this.m_20088_().m_135381_(f_31984_, p_32046_);
    }

    @Override
    public void m_7350_(EntityDataAccessor<?> p_32036_) {
        super.m_7350_(p_32036_);
        if (f_31984_.equals(p_32036_)) {
            this.m_32055_().m_41636_(this);
        }
    }

    @Nullable
    public UUID m_32056_() {
        return this.f_31982_;
    }

    public void m_32047_(@Nullable UUID p_32048_) {
        this.f_31982_ = p_32048_;
    }

    @Nullable
    public UUID m_32057_() {
        return this.f_31988_;
    }

    public void m_32052_(@Nullable UUID p_32053_) {
        this.f_31988_ = p_32053_;
    }

    public int m_32059_() {
        return this.f_31985_;
    }

    public void m_32060_() {
        this.f_31986_ = 10;
    }

    public void m_32061_() {
        this.f_31986_ = 0;
    }

    public void m_32062_() {
        this.f_31986_ = Short.MAX_VALUE;
    }

    public void m_32010_(int p_32011_) {
        this.f_31986_ = p_32011_;
    }

    public boolean m_32063_() {
        return this.f_31986_ > 0;
    }

    public void m_149678_() {
        this.f_31985_ = Short.MIN_VALUE;
    }

    public void m_32064_() {
        this.f_31985_ = -6000;
    }

    public void m_32065_() {
        this.m_32062_();
        this.f_31985_ = 5999;
    }

    public float m_32008_(float p_32009_) {
        return ((float)this.m_32059_() + p_32009_) / 20.0f + this.f_31983_;
    }

    @Override
    public Packet<?> m_5654_() {
        return new ClientboundAddEntityPacket(this);
    }

    public ItemEntity m_32066_() {
        return new ItemEntity(this);
    }

    @Override
    public SoundSource m_5720_() {
        return SoundSource.AMBIENT;
    }

    @Override
    public float m_213816_() {
        return 180.0f - this.m_32008_(0.5f) / ((float)Math.PI * 2) * 360.0f;
    }
}

