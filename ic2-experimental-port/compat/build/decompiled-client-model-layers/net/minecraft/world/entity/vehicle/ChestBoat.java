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
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class ChestBoat
extends Boat
implements HasCustomInventoryScreen,
ContainerEntity {
    private static final int f_219867_ = 27;
    private NonNullList<ItemStack> f_219864_ = NonNullList.m_122780_(27, ItemStack.f_41583_);
    @Nullable
    private ResourceLocation f_219865_;
    private long f_219866_;

    public ChestBoat(EntityType<? extends Boat> p_219869_, Level p_219870_) {
        super(p_219869_, p_219870_);
    }

    public ChestBoat(Level p_219872_, double p_219873_, double p_219874_, double p_219875_) {
        this((EntityType<? extends Boat>)EntityType.f_217016_, p_219872_);
        this.m_6034_(p_219873_, p_219874_, p_219875_);
        this.f_19854_ = p_219873_;
        this.f_19855_ = p_219874_;
        this.f_19856_ = p_219875_;
    }

    @Override
    protected float m_213802_() {
        return 0.15f;
    }

    @Override
    protected int m_213801_() {
        return 1;
    }

    @Override
    protected void m_7380_(CompoundTag p_219908_) {
        super.m_7380_(p_219908_);
        this.m_219943_(p_219908_);
    }

    @Override
    protected void m_7378_(CompoundTag p_219901_) {
        super.m_7378_(p_219901_);
        this.m_219934_(p_219901_);
    }

    @Override
    public void m_213560_(DamageSource p_219892_) {
        super.m_213560_(p_219892_);
        this.m_219927_(p_219892_, this.f_19853_, this);
    }

    @Override
    public void m_142687_(Entity.RemovalReason p_219894_) {
        if (!this.f_19853_.f_46443_ && p_219894_.m_146965_()) {
            Containers.m_18998_(this.f_19853_, this, this);
        }
        super.m_142687_(p_219894_);
    }

    @Override
    public InteractionResult m_6096_(Player p_219898_, InteractionHand p_219899_) {
        if (!this.m_7310_(p_219898_) || p_219898_.m_36341_()) {
            return this.m_219931_(this::m_146852_, p_219898_);
        }
        return super.m_6096_(p_219898_, p_219899_);
    }

    @Override
    public void m_213583_(Player p_219906_) {
        p_219906_.m_5893_(this);
        if (!p_219906_.f_19853_.f_46443_) {
            this.m_146852_(GameEvent.f_157803_, p_219906_);
            PiglinAi.m_34873_(p_219906_, true);
        }
    }

    @Override
    public Item m_38369_() {
        return switch (this.m_38387_()) {
            case Boat.Type.SPRUCE -> Items.f_220208_;
            case Boat.Type.BIRCH -> Items.f_220200_;
            case Boat.Type.JUNGLE -> Items.f_220201_;
            case Boat.Type.ACACIA -> Items.f_220202_;
            case Boat.Type.DARK_OAK -> Items.f_220203_;
            case Boat.Type.MANGROVE -> Items.f_220205_;
            default -> Items.f_220207_;
        };
    }

    @Override
    public void m_6211_() {
        this.m_219953_();
    }

    @Override
    public int m_6643_() {
        return 27;
    }

    @Override
    public ItemStack m_8020_(int p_219880_) {
        return this.m_219947_(p_219880_);
    }

    @Override
    public ItemStack m_7407_(int p_219882_, int p_219883_) {
        return this.m_219936_(p_219882_, p_219883_);
    }

    @Override
    public ItemStack m_8016_(int p_219904_) {
        return this.m_219945_(p_219904_);
    }

    @Override
    public void m_6836_(int p_219885_, ItemStack p_219886_) {
        this.m_219940_(p_219885_, p_219886_);
    }

    @Override
    public SlotAccess m_141942_(int p_219918_) {
        return this.m_219951_(p_219918_);
    }

    @Override
    public void m_6596_() {
    }

    @Override
    public boolean m_6542_(Player p_219896_) {
        return this.m_219954_(p_219896_);
    }

    @Override
    @Nullable
    public AbstractContainerMenu m_7208_(int p_219910_, Inventory p_219911_, Player p_219912_) {
        if (this.f_219865_ == null || !p_219912_.m_5833_()) {
            this.m_219913_(p_219911_.f_35978_);
            return ChestMenu.m_39237_(p_219910_, p_219911_, this);
        }
        return null;
    }

    public void m_219913_(@Nullable Player p_219914_) {
        this.m_219949_(p_219914_);
    }

    @Override
    @Nullable
    public ResourceLocation m_214142_() {
        return this.f_219865_;
    }

    @Override
    public void m_214199_(@Nullable ResourceLocation p_219890_) {
        this.f_219865_ = p_219890_;
    }

    @Override
    public long m_213803_() {
        return this.f_219866_;
    }

    @Override
    public void m_214065_(long p_219888_) {
        this.f_219866_ = p_219888_;
    }

    @Override
    public NonNullList<ItemStack> m_213659_() {
        return this.f_219864_;
    }

    @Override
    public void m_213775_() {
        this.f_219864_ = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
    }
}

