/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.projectile;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class ThrowableItemProjectile
extends ThrowableProjectile
implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> f_37429_ = SynchedEntityData.m_135353_(ThrowableItemProjectile.class, EntityDataSerializers.f_135033_);

    public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> p_37442_, Level p_37443_) {
        super((EntityType<? extends ThrowableProjectile>)p_37442_, p_37443_);
    }

    public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> p_37432_, double p_37433_, double p_37434_, double p_37435_, Level p_37436_) {
        super(p_37432_, p_37433_, p_37434_, p_37435_, p_37436_);
    }

    public ThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> p_37438_, LivingEntity p_37439_, Level p_37440_) {
        super(p_37438_, p_37439_, p_37440_);
    }

    public void m_37446_(ItemStack p_37447_) {
        if (!p_37447_.m_150930_(this.m_7881_()) || p_37447_.m_41782_()) {
            this.m_20088_().m_135381_(f_37429_, Util.m_137469_(p_37447_.m_41777_(), p_37451_ -> p_37451_.m_41764_(1)));
        }
    }

    protected abstract Item m_7881_();

    protected ItemStack m_37454_() {
        return this.m_20088_().m_135370_(f_37429_);
    }

    @Override
    public ItemStack m_7846_() {
        ItemStack $$0 = this.m_37454_();
        return $$0.m_41619_() ? new ItemStack(this.m_7881_()) : $$0;
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_37429_, ItemStack.f_41583_);
    }

    @Override
    public void m_7380_(CompoundTag p_37449_) {
        super.m_7380_(p_37449_);
        ItemStack $$1 = this.m_37454_();
        if (!$$1.m_41619_()) {
            p_37449_.m_128365_("Item", $$1.m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_37445_) {
        super.m_7378_(p_37445_);
        ItemStack $$1 = ItemStack.m_41712_(p_37445_.m_128469_("Item"));
        this.m_37446_($$1);
    }
}

