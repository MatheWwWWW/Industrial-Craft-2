/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.animal.horse;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Markings;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;

public class Horse
extends AbstractHorse {
    private static final UUID f_30685_ = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final EntityDataAccessor<Integer> f_30686_ = SynchedEntityData.m_135353_(Horse.class, EntityDataSerializers.f_135028_);

    public Horse(EntityType<? extends Horse> p_30689_, Level p_30690_) {
        super((EntityType<? extends AbstractHorse>)p_30689_, p_30690_);
    }

    @Override
    protected void m_214179_(RandomSource p_218815_) {
        this.m_21051_(Attributes.f_22276_).m_22100_(this.m_218805_(p_218815_));
        this.m_21051_(Attributes.f_22279_).m_22100_(this.m_218811_(p_218815_));
        this.m_21051_(Attributes.f_22288_).m_22100_(this.m_218809_(p_218815_));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30686_, 0);
    }

    @Override
    public void m_7380_(CompoundTag p_30716_) {
        super.m_7380_(p_30716_);
        p_30716_.m_128405_("Variant", this.m_30725_());
        if (!this.f_30520_.m_8020_(1).m_41619_()) {
            p_30716_.m_128365_("ArmorItem", this.f_30520_.m_8020_(1).m_41739_(new CompoundTag()));
        }
    }

    public ItemStack m_30722_() {
        return this.m_6844_(EquipmentSlot.CHEST);
    }

    private void m_30732_(ItemStack p_30733_) {
        this.m_8061_(EquipmentSlot.CHEST, p_30733_);
        this.m_21409_(EquipmentSlot.CHEST, 0.0f);
    }

    @Override
    public void m_7378_(CompoundTag p_30711_) {
        ItemStack $$1;
        super.m_7378_(p_30711_);
        this.m_30736_(p_30711_.m_128451_("Variant"));
        if (p_30711_.m_128425_("ArmorItem", 10) && !($$1 = ItemStack.m_41712_(p_30711_.m_128469_("ArmorItem"))).m_41619_() && this.m_6010_($$1)) {
            this.f_30520_.m_6836_(1, $$1);
        }
        this.m_7493_();
    }

    private void m_30736_(int p_30737_) {
        this.f_19804_.m_135381_(f_30686_, p_30737_);
    }

    private int m_30725_() {
        return this.f_19804_.m_135370_(f_30686_);
    }

    private void m_30699_(Variant p_30700_, Markings p_30701_) {
        this.m_30736_(p_30700_.m_30985_() & 0xFF | p_30701_.m_30869_() << 8 & 0xFF00);
    }

    public Variant m_30723_() {
        return Variant.m_30986_(this.m_30725_() & 0xFF);
    }

    public Markings m_30724_() {
        return Markings.m_30870_((this.m_30725_() & 0xFF00) >> 8);
    }

    @Override
    protected void m_7493_() {
        if (this.f_19853_.f_46443_) {
            return;
        }
        super.m_7493_();
        this.m_30734_(this.f_30520_.m_8020_(1));
        this.m_21409_(EquipmentSlot.CHEST, 0.0f);
    }

    private void m_30734_(ItemStack p_30735_) {
        this.m_30732_(p_30735_);
        if (!this.f_19853_.f_46443_) {
            int $$1;
            this.m_21051_(Attributes.f_22284_).m_22120_(f_30685_);
            if (this.m_6010_(p_30735_) && ($$1 = ((HorseArmorItem)p_30735_.m_41720_()).m_41368_()) != 0) {
                this.m_21051_(Attributes.f_22284_).m_22118_(new AttributeModifier(f_30685_, "Horse armor bonus", (double)$$1, AttributeModifier.Operation.ADDITION));
            }
        }
    }

    @Override
    public void m_5757_(Container p_30696_) {
        ItemStack $$1 = this.m_30722_();
        super.m_5757_(p_30696_);
        ItemStack $$2 = this.m_30722_();
        if (this.f_19797_ > 20 && this.m_6010_($$2) && $$1 != $$2) {
            this.m_5496_(SoundEvents.f_11973_, 0.5f, 1.0f);
        }
    }

    @Override
    protected void m_5877_(SoundType p_30709_) {
        super.m_5877_(p_30709_);
        if (this.f_19796_.m_188503_(10) == 0) {
            this.m_5496_(SoundEvents.f_11974_, p_30709_.m_56773_() * 0.6f, p_30709_.m_56774_());
        }
    }

    @Override
    protected SoundEvent m_7515_() {
        super.m_7515_();
        return SoundEvents.f_11971_;
    }

    @Override
    protected SoundEvent m_5592_() {
        super.m_5592_();
        return SoundEvents.f_11975_;
    }

    @Override
    @Nullable
    protected SoundEvent m_7872_() {
        return SoundEvents.f_11976_;
    }

    @Override
    protected SoundEvent m_7975_(DamageSource p_30720_) {
        super.m_7975_(p_30720_);
        return SoundEvents.f_11978_;
    }

    @Override
    protected SoundEvent m_7871_() {
        super.m_7871_();
        return SoundEvents.f_11972_;
    }

    @Override
    public InteractionResult m_6071_(Player p_30713_, InteractionHand p_30714_) {
        ItemStack $$2 = p_30713_.m_21120_(p_30714_);
        if (!this.m_6162_()) {
            if (this.m_30614_() && p_30713_.m_36341_()) {
                this.m_213583_(p_30713_);
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            if (this.m_20160_()) {
                return super.m_6071_(p_30713_, p_30714_);
            }
        }
        if (!$$2.m_41619_()) {
            boolean $$4;
            if (this.m_6898_($$2)) {
                return this.m_30580_(p_30713_, $$2);
            }
            InteractionResult $$3 = $$2.m_41647_(p_30713_, this, p_30714_);
            if ($$3.m_19077_()) {
                return $$3;
            }
            if (!this.m_30614_()) {
                this.m_7564_();
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            boolean bl = $$4 = !this.m_6162_() && !this.m_6254_() && $$2.m_150930_(Items.f_42450_);
            if (this.m_6010_($$2) || $$4) {
                this.m_213583_(p_30713_);
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
        }
        if (this.m_6162_()) {
            return super.m_6071_(p_30713_, p_30714_);
        }
        this.m_6835_(p_30713_);
        return InteractionResult.m_19078_(this.f_19853_.f_46443_);
    }

    @Override
    public boolean m_7848_(Animal p_30698_) {
        if (p_30698_ == this) {
            return false;
        }
        if (p_30698_ instanceof Donkey || p_30698_ instanceof Horse) {
            return this.m_30628_() && ((AbstractHorse)p_30698_).m_30628_();
        }
        return false;
    }

    @Override
    public AgeableMob m_142606_(ServerLevel p_149533_, AgeableMob p_149534_) {
        AbstractHorse $$4;
        if (p_149534_ instanceof Donkey) {
            AbstractHorse $$2 = EntityType.f_20503_.m_20615_(p_149533_);
        } else {
            Markings $$12;
            Variant $$8;
            Horse $$3 = (Horse)p_149534_;
            $$4 = EntityType.f_20457_.m_20615_(p_149533_);
            int $$5 = this.f_19796_.m_188503_(9);
            if ($$5 < 4) {
                Variant $$6 = this.m_30723_();
            } else if ($$5 < 8) {
                Variant $$7 = $$3.m_30723_();
            } else {
                $$8 = Util.m_214670_(Variant.values(), this.f_19796_);
            }
            int $$9 = this.f_19796_.m_188503_(5);
            if ($$9 < 2) {
                Markings $$10 = this.m_30724_();
            } else if ($$9 < 4) {
                Markings $$11 = $$3.m_30724_();
            } else {
                $$12 = Util.m_214670_(Markings.values(), this.f_19796_);
            }
            ((Horse)$$4).m_30699_($$8, $$12);
        }
        this.m_149508_(p_149534_, $$4);
        return $$4;
    }

    @Override
    public boolean m_7482_() {
        return true;
    }

    @Override
    public boolean m_6010_(ItemStack p_30731_) {
        return p_30731_.m_41720_() instanceof HorseArmorItem;
    }

    @Override
    @Nullable
    public SpawnGroupData m_6518_(ServerLevelAccessor p_30703_, DifficultyInstance p_30704_, MobSpawnType p_30705_, @Nullable SpawnGroupData p_30706_, @Nullable CompoundTag p_30707_) {
        Variant $$7;
        RandomSource $$5 = p_30703_.m_213780_();
        if (p_30706_ instanceof HorseGroupData) {
            Variant $$6 = ((HorseGroupData)p_30706_).f_30738_;
        } else {
            $$7 = Util.m_214670_(Variant.values(), $$5);
            p_30706_ = new HorseGroupData($$7);
        }
        this.m_30699_($$7, Util.m_214670_(Markings.values(), $$5));
        return super.m_6518_(p_30703_, p_30704_, p_30705_, p_30706_, p_30707_);
    }

    public static class HorseGroupData
    extends AgeableMob.AgeableMobGroupData {
        public final Variant f_30738_;

        public HorseGroupData(Variant p_30740_) {
            super(true);
            this.f_30738_ = p_30740_;
        }
    }
}

