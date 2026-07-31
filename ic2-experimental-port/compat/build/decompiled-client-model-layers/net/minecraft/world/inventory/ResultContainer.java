/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class ResultContainer
implements Container,
RecipeHolder {
    private final NonNullList<ItemStack> f_40140_ = NonNullList.m_122780_(1, ItemStack.f_41583_);
    @Nullable
    private Recipe<?> f_40141_;

    @Override
    public int m_6643_() {
        return 1;
    }

    @Override
    public boolean m_7983_() {
        for (ItemStack $$0 : this.f_40140_) {
            if ($$0.m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack m_8020_(int p_40147_) {
        return this.f_40140_.get(0);
    }

    @Override
    public ItemStack m_7407_(int p_40149_, int p_40150_) {
        return ContainerHelper.m_18966_(this.f_40140_, 0);
    }

    @Override
    public ItemStack m_8016_(int p_40160_) {
        return ContainerHelper.m_18966_(this.f_40140_, 0);
    }

    @Override
    public void m_6836_(int p_40152_, ItemStack p_40153_) {
        this.f_40140_.set(0, p_40153_);
    }

    @Override
    public void m_6596_() {
    }

    @Override
    public boolean m_6542_(Player p_40155_) {
        return true;
    }

    @Override
    public void m_6211_() {
        this.f_40140_.clear();
    }

    @Override
    public void m_6029_(@Nullable Recipe<?> p_40157_) {
        this.f_40141_ = p_40157_;
    }

    @Override
    @Nullable
    public Recipe<?> m_7928_() {
        return this.f_40141_;
    }
}

