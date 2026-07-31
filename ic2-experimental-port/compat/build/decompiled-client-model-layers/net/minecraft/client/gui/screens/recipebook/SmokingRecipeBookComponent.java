/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.recipebook;

import java.util.Set;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class SmokingRecipeBookComponent
extends AbstractFurnaceRecipeBookComponent {
    private static final Component f_100524_ = Component.m_237115_("gui.recipebook.toggleRecipes.smokable");

    @Override
    protected Component m_5815_() {
        return f_100524_;
    }

    @Override
    protected Set<Item> m_7690_() {
        return AbstractFurnaceBlockEntity.m_58423_().keySet();
    }
}

