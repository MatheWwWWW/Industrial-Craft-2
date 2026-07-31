/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractMinecartContainer
extends AbstractMinecart
implements ContainerEntity {
    private NonNullList<ItemStack> f_38202_ = NonNullList.m_122780_(36, ItemStack.f_41583_);
    @Nullable
    private ResourceLocation f_38204_;
    private long f_38205_;

    protected AbstractMinecartContainer(EntityType<?> p_38213_, Level p_38214_) {
        super(p_38213_, p_38214_);
    }

    protected AbstractMinecartContainer(EntityType<?> p_38207_, double p_38208_, double p_38209_, double p_38210_, Level p_38211_) {
        super(p_38207_, p_38211_, p_38208_, p_38209_, p_38210_);
    }

    @Override
    public void m_7617_(DamageSource p_38228_) {
        super.m_7617_(p_38228_);
        this.m_219927_(p_38228_, this.f_19853_, this);
    }

    @Override
    public ItemStack m_8020_(int p_38218_) {
        return this.m_219947_(p_38218_);
    }

    @Override
    public ItemStack m_7407_(int p_38220_, int p_38221_) {
        return this.m_219936_(p_38220_, p_38221_);
    }

    @Override
    public ItemStack m_8016_(int p_38244_) {
        return this.m_219945_(p_38244_);
    }

    @Override
    public void m_6836_(int p_38225_, ItemStack p_38226_) {
        this.m_219940_(p_38225_, p_38226_);
    }

    @Override
    public SlotAccess m_141942_(int p_150257_) {
        return this.m_219951_(p_150257_);
    }

    @Override
    public void m_6596_() {
    }

    @Override
    public boolean m_6542_(Player p_38230_) {
        return this.m_219954_(p_38230_);
    }

    @Override
    public void m_142687_(Entity.RemovalReason p_150255_) {
        if (!this.f_19853_.f_46443_ && p_150255_.m_146965_()) {
            Containers.m_18998_(this.f_19853_, this, this);
        }
        super.m_142687_(p_150255_);
    }

    @Override
    protected void m_7380_(CompoundTag p_38248_) {
        super.m_7380_(p_38248_);
        this.m_219943_(p_38248_);
    }

    @Override
    protected void m_7378_(CompoundTag p_38235_) {
        super.m_7378_(p_38235_);
        this.m_219934_(p_38235_);
    }

    @Override
    public InteractionResult m_6096_(Player p_38232_, InteractionHand p_38233_) {
        return this.m_219931_(this::m_146852_, p_38232_);
    }

    @Override
    protected void m_7114_() {
        float $$0 = 0.98f;
        if (this.f_38204_ == null) {
            int $$1 = 15 - AbstractContainerMenu.m_38938_(this);
            $$0 += (float)$$1 * 0.001f;
        }
        if (this.m_20069_()) {
            $$0 *= 0.95f;
        }
        this.m_20256_(this.m_20184_().m_82542_($$0, 0.0, $$0));
    }

    @Override
    public void m_6211_() {
        this.m_219953_();
    }

    public void m_38236_(ResourceLocation p_38237_, long p_38238_) {
        this.f_38204_ = p_38237_;
        this.f_38205_ = p_38238_;
    }

    @Override
    @Nullable
    public AbstractContainerMenu m_7208_(int p_38251_, Inventory p_38252_, Player p_38253_) {
        if (this.f_38204_ == null || !p_38253_.m_5833_()) {
            this.m_219949_(p_38252_.f_35978_);
            return this.m_7402_(p_38251_, p_38252_);
        }
        return null;
    }

    protected abstract AbstractContainerMenu m_7402_(int var1, Inventory var2);

    @Override
    @Nullable
    public ResourceLocation m_214142_() {
        return this.f_38204_;
    }

    @Override
    public void m_214199_(@Nullable ResourceLocation p_219859_) {
        this.f_38204_ = p_219859_;
    }

    @Override
    public long m_213803_() {
        return this.f_38205_;
    }

    @Override
    public void m_214065_(long p_219857_) {
        this.f_38205_ = p_219857_;
    }

    @Override
    public NonNullList<ItemStack> m_213659_() {
        return this.f_38202_;
    }

    @Override
    public void m_213775_() {
        this.f_38202_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
    }
}

