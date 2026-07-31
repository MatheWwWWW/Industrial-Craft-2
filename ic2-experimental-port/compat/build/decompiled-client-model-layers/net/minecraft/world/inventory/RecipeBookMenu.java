/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.Recipe;

public abstract class RecipeBookMenu<C extends Container>
extends AbstractContainerMenu {
    public RecipeBookMenu(MenuType<?> p_40115_, int p_40116_) {
        super(p_40115_, p_40116_);
    }

    public void m_6951_(boolean p_40119_, Recipe<?> p_40120_, ServerPlayer p_40121_) {
        new ServerPlaceRecipe(this).m_135434_(p_40121_, p_40120_, p_40119_);
    }

    public abstract void m_5816_(StackedContents var1);

    public abstract void m_6650_();

    public abstract boolean m_6032_(Recipe<? super C> var1);

    public abstract int m_6636_();

    public abstract int m_6635_();

    public abstract int m_6656_();

    public abstract int m_6653_();

    public abstract RecipeBookType m_5867_();

    public abstract boolean m_142157_(int var1);
}

