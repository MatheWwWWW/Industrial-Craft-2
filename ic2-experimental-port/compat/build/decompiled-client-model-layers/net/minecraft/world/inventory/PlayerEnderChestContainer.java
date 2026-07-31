/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;

public class PlayerEnderChestContainer
extends SimpleContainer {
    @Nullable
    private EnderChestBlockEntity f_40101_;

    public PlayerEnderChestContainer() {
        super(27);
    }

    public void m_40105_(EnderChestBlockEntity p_40106_) {
        this.f_40101_ = p_40106_;
    }

    public boolean m_150633_(EnderChestBlockEntity p_150634_) {
        return this.f_40101_ == p_150634_;
    }

    @Override
    public void m_7797_(ListTag p_40108_) {
        for (int $$1 = 0; $$1 < this.m_6643_(); ++$$1) {
            this.m_6836_($$1, ItemStack.f_41583_);
        }
        for (int $$2 = 0; $$2 < p_40108_.size(); ++$$2) {
            CompoundTag $$3 = p_40108_.m_128728_($$2);
            int $$4 = $$3.m_128445_("Slot") & 0xFF;
            if ($$4 < 0 || $$4 >= this.m_6643_()) continue;
            this.m_6836_($$4, ItemStack.m_41712_($$3));
        }
    }

    @Override
    public ListTag m_7927_() {
        ListTag $$0 = new ListTag();
        for (int $$1 = 0; $$1 < this.m_6643_(); ++$$1) {
            ItemStack $$2 = this.m_8020_($$1);
            if ($$2.m_41619_()) continue;
            CompoundTag $$3 = new CompoundTag();
            $$3.m_128344_("Slot", (byte)$$1);
            $$2.m_41739_($$3);
            $$0.add($$3);
        }
        return $$0;
    }

    @Override
    public boolean m_6542_(Player p_40104_) {
        if (this.f_40101_ != null && !this.f_40101_.m_59282_(p_40104_)) {
            return false;
        }
        return super.m_6542_(p_40104_);
    }

    @Override
    public void m_5856_(Player p_40112_) {
        if (this.f_40101_ != null) {
            this.f_40101_.m_155515_(p_40112_);
        }
        super.m_5856_(p_40112_);
    }

    @Override
    public void m_5785_(Player p_40110_) {
        if (this.f_40101_ != null) {
            this.f_40101_.m_155522_(p_40110_);
        }
        super.m_5785_(p_40110_);
        this.f_40101_ = null;
    }
}

