/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;

public class CraftingContainer
implements Container,
StackedContentsCompatible {
    private final NonNullList<ItemStack> f_39320_;
    private final int f_39321_;
    private final int f_39322_;
    private final AbstractContainerMenu f_39323_;

    public CraftingContainer(AbstractContainerMenu p_39325_, int p_39326_, int p_39327_) {
        this.f_39320_ = NonNullList.m_122780_(p_39326_ * p_39327_, ItemStack.f_41583_);
        this.f_39323_ = p_39325_;
        this.f_39321_ = p_39326_;
        this.f_39322_ = p_39327_;
    }

    @Override
    public int m_6643_() {
        return this.f_39320_.size();
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_39320_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack m_8020_(int p_39332_) {
        if (p_39332_ >= this.m_6643_()) {
            return ItemStack.f_41583_;
        }
        return this.f_39320_.get(p_39332_);
    }

    @Override
    public ItemStack m_8016_(int p_39344_) {
        return ContainerHelper.m_18966_(this.f_39320_, p_39344_);
    }

    @Override
    public ItemStack m_7407_(int p_39334_, int p_39335_) {
        ItemStack $$2 = ContainerHelper.m_18969_(this.f_39320_, p_39334_, p_39335_);
        if (!$$2.m_41619_()) {
            this.f_39323_.m_6199_(this);
        }
        return $$2;
    }

    @Override
    public void m_6836_(int p_39337_, ItemStack p_39338_) {
        this.f_39320_.set(p_39337_, p_39338_);
        this.f_39323_.m_6199_(this);
    }

    @Override
    public void m_6596_() {
    }

    @Override
    public boolean m_6542_(Player p_39340_) {
        return true;
    }

    @Override
    public void m_6211_() {
        this.f_39320_.clear();
    }

    public int m_39346_() {
        return this.f_39322_;
    }

    public int m_39347_() {
        return this.f_39321_;
    }

    @Override
    public void m_5809_(StackedContents p_39342_) {
        for (ItemStack $$1 : this.f_39320_) {
            p_39342_.m_36466_($$1);
        }
    }
}

