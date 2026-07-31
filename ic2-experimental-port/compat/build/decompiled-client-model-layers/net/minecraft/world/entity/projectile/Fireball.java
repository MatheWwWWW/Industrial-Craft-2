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
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class Fireball
extends AbstractHurtingProjectile
implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> f_36987_ = SynchedEntityData.m_135353_(Fireball.class, EntityDataSerializers.f_135033_);

    public Fireball(EntityType<? extends Fireball> p_37006_, Level p_37007_) {
        super((EntityType<? extends AbstractHurtingProjectile>)p_37006_, p_37007_);
    }

    public Fireball(EntityType<? extends Fireball> p_36990_, double p_36991_, double p_36992_, double p_36993_, double p_36994_, double p_36995_, double p_36996_, Level p_36997_) {
        super(p_36990_, p_36991_, p_36992_, p_36993_, p_36994_, p_36995_, p_36996_, p_36997_);
    }

    public Fireball(EntityType<? extends Fireball> p_36999_, LivingEntity p_37000_, double p_37001_, double p_37002_, double p_37003_, Level p_37004_) {
        super(p_36999_, p_37000_, p_37001_, p_37002_, p_37003_, p_37004_);
    }

    public void m_37010_(ItemStack p_37011_) {
        if (!p_37011_.m_150930_(Items.f_42613_) || p_37011_.m_41782_()) {
            this.m_20088_().m_135381_(f_36987_, Util.m_137469_(p_37011_.m_41777_(), p_37015_ -> p_37015_.m_41764_(1)));
        }
    }

    protected ItemStack m_37018_() {
        return this.m_20088_().m_135370_(f_36987_);
    }

    @Override
    public ItemStack m_7846_() {
        ItemStack $$0 = this.m_37018_();
        return $$0.m_41619_() ? new ItemStack(Items.f_42613_) : $$0;
    }

    @Override
    protected void m_8097_() {
        this.m_20088_().m_135372_(f_36987_, ItemStack.f_41583_);
    }

    @Override
    public void m_7380_(CompoundTag p_37013_) {
        super.m_7380_(p_37013_);
        ItemStack $$1 = this.m_37018_();
        if (!$$1.m_41619_()) {
            p_37013_.m_128365_("Item", $$1.m_41739_(new CompoundTag()));
        }
    }

    @Override
    public void m_7378_(CompoundTag p_37009_) {
        super.m_7378_(p_37009_);
        ItemStack $$1 = ItemStack.m_41712_(p_37009_.m_128469_("Item"));
        this.m_37010_($$1);
    }
}

