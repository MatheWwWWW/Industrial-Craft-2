/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class ResultSlot
extends Slot {
    private final CraftingContainer f_40162_;
    private final Player f_40163_;
    private int f_40164_;

    public ResultSlot(Player p_40166_, CraftingContainer p_40167_, Container p_40168_, int p_40169_, int p_40170_, int p_40171_) {
        super(p_40168_, p_40169_, p_40170_, p_40171_);
        this.f_40163_ = p_40166_;
        this.f_40162_ = p_40167_;
    }

    @Override
    public boolean m_5857_(ItemStack p_40178_) {
        return false;
    }

    @Override
    public ItemStack m_6201_(int p_40173_) {
        if (this.m_6657_()) {
            this.f_40164_ += Math.min(p_40173_, this.m_7993_().m_41613_());
        }
        return super.m_6201_(p_40173_);
    }

    @Override
    protected void m_7169_(ItemStack p_40180_, int p_40181_) {
        this.f_40164_ += p_40181_;
        this.m_5845_(p_40180_);
    }

    @Override
    protected void m_6405_(int p_40183_) {
        this.f_40164_ += p_40183_;
    }

    @Override
    protected void m_5845_(ItemStack p_40185_) {
        if (this.f_40164_ > 0) {
            p_40185_.m_41678_(this.f_40163_.f_19853_, this.f_40163_, this.f_40164_);
        }
        if (this.f_40218_ instanceof RecipeHolder) {
            ((RecipeHolder)((Object)this.f_40218_)).m_8015_(this.f_40163_);
        }
        this.f_40164_ = 0;
    }

    @Override
    public void m_142406_(Player p_150638_, ItemStack p_150639_) {
        this.m_5845_(p_150639_);
        NonNullList<ItemStack> $$2 = p_150638_.f_19853_.m_7465_().m_44069_(RecipeType.f_44107_, this.f_40162_, p_150638_.f_19853_);
        for (int $$3 = 0; $$3 < $$2.size(); ++$$3) {
            ItemStack $$4 = this.f_40162_.m_8020_($$3);
            ItemStack $$5 = $$2.get($$3);
            if (!$$4.m_41619_()) {
                this.f_40162_.m_7407_($$3, 1);
                $$4 = this.f_40162_.m_8020_($$3);
            }
            if ($$5.m_41619_()) continue;
            if ($$4.m_41619_()) {
                this.f_40162_.m_6836_($$3, $$5);
                continue;
            }
            if (ItemStack.m_41746_($$4, $$5) && ItemStack.m_41658_($$4, $$5)) {
                $$5.m_41769_($$4.m_41613_());
                this.f_40162_.m_6836_($$3, $$5);
                continue;
            }
            if (this.f_40163_.m_150109_().m_36054_($$5)) continue;
            this.f_40163_.m_36176_($$5, false);
        }
    }
}

