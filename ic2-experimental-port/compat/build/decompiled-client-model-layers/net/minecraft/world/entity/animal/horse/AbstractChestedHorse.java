/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal.horse;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public abstract class AbstractChestedHorse
extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> f_30482_ = SynchedEntityData.m_135353_(AbstractChestedHorse.class, EntityDataSerializers.f_135035_);
    public static final int f_149477_ = 15;

    protected AbstractChestedHorse(EntityType<? extends AbstractChestedHorse> p_30485_, Level p_30486_) {
        super((EntityType<? extends AbstractHorse>)p_30485_, p_30486_);
        this.f_30523_ = false;
    }

    @Override
    protected void m_214179_(RandomSource p_218803_) {
        this.m_21051_(Attributes.f_22276_).m_22100_(this.m_218805_(p_218803_));
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_30482_, false);
    }

    public static AttributeSupplier.Builder m_30501_() {
        return AbstractChestedHorse.m_30627_().m_22268_(Attributes.f_22279_, 0.175f).m_22268_(Attributes.f_22288_, 0.5);
    }

    public boolean m_30502_() {
        return this.f_19804_.m_135370_(f_30482_);
    }

    public void m_30504_(boolean p_30505_) {
        this.f_19804_.m_135381_(f_30482_, p_30505_);
    }

    @Override
    protected int m_7506_() {
        if (this.m_30502_()) {
            return 17;
        }
        return super.m_7506_();
    }

    @Override
    public double m_6048_() {
        return super.m_6048_() - 0.25;
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (this.m_30502_()) {
            if (!this.f_19853_.f_46443_) {
                this.m_19998_(Blocks.f_50087_);
            }
            this.m_30504_(false);
        }
    }

    @Override
    public void m_7380_(CompoundTag p_30496_) {
        super.m_7380_(p_30496_);
        p_30496_.m_128379_("ChestedHorse", this.m_30502_());
        if (this.m_30502_()) {
            ListTag $$1 = new ListTag();
            for (int $$2 = 2; $$2 < this.f_30520_.m_6643_(); ++$$2) {
                ItemStack $$3 = this.f_30520_.m_8020_($$2);
                if ($$3.m_41619_()) continue;
                CompoundTag $$4 = new CompoundTag();
                $$4.m_128344_("Slot", (byte)$$2);
                $$3.m_41739_($$4);
                $$1.add($$4);
            }
            p_30496_.m_128365_("Items", $$1);
        }
    }

    @Override
    public void m_7378_(CompoundTag p_30488_) {
        super.m_7378_(p_30488_);
        this.m_30504_(p_30488_.m_128471_("ChestedHorse"));
        this.m_30625_();
        if (this.m_30502_()) {
            ListTag $$1 = p_30488_.m_128437_("Items", 10);
            for (int $$2 = 0; $$2 < $$1.size(); ++$$2) {
                CompoundTag $$3 = $$1.m_128728_($$2);
                int $$4 = $$3.m_128445_("Slot") & 0xFF;
                if ($$4 < 2 || $$4 >= this.f_30520_.m_6643_()) continue;
                this.f_30520_.m_6836_($$4, ItemStack.m_41712_($$3));
            }
        }
        this.m_7493_();
    }

    @Override
    public SlotAccess m_141942_(int p_149479_) {
        if (p_149479_ == 499) {
            return new SlotAccess(){

                @Override
                public ItemStack m_142196_() {
                    return AbstractChestedHorse.this.m_30502_() ? new ItemStack(Items.f_42009_) : ItemStack.f_41583_;
                }

                @Override
                public boolean m_142104_(ItemStack p_149485_) {
                    if (p_149485_.m_41619_()) {
                        if (AbstractChestedHorse.this.m_30502_()) {
                            AbstractChestedHorse.this.m_30504_(false);
                            AbstractChestedHorse.this.m_30625_();
                        }
                        return true;
                    }
                    if (p_149485_.m_150930_(Items.f_42009_)) {
                        if (!AbstractChestedHorse.this.m_30502_()) {
                            AbstractChestedHorse.this.m_30504_(true);
                            AbstractChestedHorse.this.m_30625_();
                        }
                        return true;
                    }
                    return false;
                }
            };
        }
        return super.m_141942_(p_149479_);
    }

    @Override
    public InteractionResult m_6071_(Player p_30493_, InteractionHand p_30494_) {
        ItemStack $$2 = p_30493_.m_21120_(p_30494_);
        if (!this.m_6162_()) {
            if (this.m_30614_() && p_30493_.m_36341_()) {
                this.m_213583_(p_30493_);
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            if (this.m_20160_()) {
                return super.m_6071_(p_30493_, p_30494_);
            }
        }
        if (!$$2.m_41619_()) {
            if (this.m_6898_($$2)) {
                return this.m_30580_(p_30493_, $$2);
            }
            if (!this.m_30614_()) {
                this.m_7564_();
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            if (!this.m_30502_() && $$2.m_150930_(Blocks.f_50087_.m_5456_())) {
                this.m_30504_(true);
                this.m_7609_();
                if (!p_30493_.m_150110_().f_35937_) {
                    $$2.m_41774_(1);
                }
                this.m_30625_();
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
            if (!this.m_6162_() && !this.m_6254_() && $$2.m_150930_(Items.f_42450_)) {
                this.m_213583_(p_30493_);
                return InteractionResult.m_19078_(this.f_19853_.f_46443_);
            }
        }
        if (this.m_6162_()) {
            return super.m_6071_(p_30493_, p_30494_);
        }
        this.m_6835_(p_30493_);
        return InteractionResult.m_19078_(this.f_19853_.f_46443_);
    }

    protected void m_7609_() {
        this.m_5496_(SoundEvents.f_11811_, 1.0f, (this.f_19796_.m_188501_() - this.f_19796_.m_188501_()) * 0.2f + 1.0f);
    }

    public int m_7488_() {
        return 5;
    }
}

