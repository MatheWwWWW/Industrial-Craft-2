/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MinecartFurnace
extends AbstractMinecart {
    private static final EntityDataAccessor<Boolean> f_38547_ = SynchedEntityData.m_135353_(MinecartFurnace.class, EntityDataSerializers.f_135035_);
    private int f_38548_;
    public double f_38545_;
    public double f_38546_;
    private static final Ingredient f_38549_ = Ingredient.m_43929_(Items.f_42413_, Items.f_42414_);

    public MinecartFurnace(EntityType<? extends MinecartFurnace> p_38552_, Level p_38553_) {
        super(p_38552_, p_38553_);
    }

    public MinecartFurnace(Level p_38555_, double p_38556_, double p_38557_, double p_38558_) {
        super(EntityType.f_20472_, p_38555_, p_38556_, p_38557_, p_38558_);
    }

    @Override
    public AbstractMinecart.Type m_6064_() {
        return AbstractMinecart.Type.FURNACE;
    }

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(f_38547_, false);
    }

    @Override
    public void m_8119_() {
        super.m_8119_();
        if (!this.f_19853_.m_5776_()) {
            if (this.f_38548_ > 0) {
                --this.f_38548_;
            }
            if (this.f_38548_ <= 0) {
                this.f_38545_ = 0.0;
                this.f_38546_ = 0.0;
            }
            this.m_38576_(this.f_38548_ > 0);
        }
        if (this.m_38579_() && this.f_19796_.m_188503_(4) == 0) {
            this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20185_(), this.m_20186_() + 0.8, this.m_20189_(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected double m_7097_() {
        return (this.m_20069_() ? 3.0 : 4.0) / 20.0;
    }

    @Override
    protected Item m_213728_() {
        return Items.f_42520_;
    }

    @Override
    protected void m_6401_(BlockPos p_38569_, BlockState p_38570_) {
        double $$2 = 1.0E-4;
        double $$3 = 0.001;
        super.m_6401_(p_38569_, p_38570_);
        Vec3 $$4 = this.m_20184_();
        double $$5 = $$4.m_165925_();
        double $$6 = this.f_38545_ * this.f_38545_ + this.f_38546_ * this.f_38546_;
        if ($$6 > 1.0E-4 && $$5 > 0.001) {
            double $$7 = Math.sqrt($$5);
            double $$8 = Math.sqrt($$6);
            this.f_38545_ = $$4.f_82479_ / $$7 * $$8;
            this.f_38546_ = $$4.f_82481_ / $$7 * $$8;
        }
    }

    @Override
    protected void m_7114_() {
        double $$0 = this.f_38545_ * this.f_38545_ + this.f_38546_ * this.f_38546_;
        if ($$0 > 1.0E-7) {
            $$0 = Math.sqrt($$0);
            this.f_38545_ /= $$0;
            this.f_38546_ /= $$0;
            Vec3 $$1 = this.m_20184_().m_82542_(0.8, 0.0, 0.8).m_82520_(this.f_38545_, 0.0, this.f_38546_);
            if (this.m_20069_()) {
                $$1 = $$1.m_82490_(0.1);
            }
            this.m_20256_($$1);
        } else {
            this.m_20256_(this.m_20184_().m_82542_(0.98, 0.0, 0.98));
        }
        super.m_7114_();
    }

    @Override
    public InteractionResult m_6096_(Player p_38562_, InteractionHand p_38563_) {
        ItemStack $$2 = p_38562_.m_21120_(p_38563_);
        if (f_38549_.test($$2) && this.f_38548_ + 3600 <= 32000) {
            if (!p_38562_.m_150110_().f_35937_) {
                $$2.m_41774_(1);
            }
            this.f_38548_ += 3600;
        }
        if (this.f_38548_ > 0) {
            this.f_38545_ = this.m_20185_() - p_38562_.m_20185_();
            this.f_38546_ = this.m_20189_() - p_38562_.m_20189_();
        }
        return InteractionResult.m_19078_(this.f_19853_.f_46443_);
    }

    @Override
    protected void m_7380_(CompoundTag p_38567_) {
        super.m_7380_(p_38567_);
        p_38567_.m_128347_("PushX", this.f_38545_);
        p_38567_.m_128347_("PushZ", this.f_38546_);
        p_38567_.m_128376_("Fuel", (short)this.f_38548_);
    }

    @Override
    protected void m_7378_(CompoundTag p_38565_) {
        super.m_7378_(p_38565_);
        this.f_38545_ = p_38565_.m_128459_("PushX");
        this.f_38546_ = p_38565_.m_128459_("PushZ");
        this.f_38548_ = p_38565_.m_128448_("Fuel");
    }

    protected boolean m_38579_() {
        return this.f_19804_.m_135370_(f_38547_);
    }

    protected void m_38576_(boolean p_38577_) {
        this.f_19804_.m_135381_(f_38547_, p_38577_);
    }

    @Override
    public BlockState m_6390_() {
        return (BlockState)((BlockState)Blocks.f_50094_.m_49966_().m_61124_(FurnaceBlock.f_48683_, Direction.NORTH)).m_61124_(FurnaceBlock.f_48684_, this.m_38579_());
    }
}

