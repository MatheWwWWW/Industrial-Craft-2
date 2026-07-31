/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnaceResultSlot
extends Slot {
    private final Player f_39539_;
    private int f_39540_;

    public FurnaceResultSlot(Player p_39542_, Container p_39543_, int p_39544_, int p_39545_, int p_39546_) {
        super(p_39543_, p_39544_, p_39545_, p_39546_);
        this.f_39539_ = p_39542_;
    }

    @Override
    public boolean m_5857_(ItemStack p_39553_) {
        return false;
    }

    @Override
    public ItemStack m_6201_(int p_39548_) {
        if (this.m_6657_()) {
            this.f_39540_ += Math.min(p_39548_, this.m_7993_().m_41613_());
        }
        return super.m_6201_(p_39548_);
    }

    @Override
    public void m_142406_(Player p_150563_, ItemStack p_150564_) {
        this.m_5845_(p_150564_);
        super.m_142406_(p_150563_, p_150564_);
    }

    @Override
    protected void m_7169_(ItemStack p_39555_, int p_39556_) {
        this.f_39540_ += p_39556_;
        this.m_5845_(p_39555_);
    }

    @Override
    protected void m_5845_(ItemStack p_39558_) {
        p_39558_.m_41678_(this.f_39539_.f_19853_, this.f_39539_, this.f_39540_);
        if (this.f_39539_ instanceof ServerPlayer && this.f_40218_ instanceof AbstractFurnaceBlockEntity) {
            ((AbstractFurnaceBlockEntity)this.f_40218_).m_155003_((ServerPlayer)this.f_39539_);
        }
        this.f_39540_ = 0;
    }
}

