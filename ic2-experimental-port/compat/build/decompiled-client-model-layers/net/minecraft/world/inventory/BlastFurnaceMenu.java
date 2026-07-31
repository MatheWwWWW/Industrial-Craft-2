/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.RecipeType;

public class BlastFurnaceMenu
extends AbstractFurnaceMenu {
    public BlastFurnaceMenu(int p_39079_, Inventory p_39080_) {
        super(MenuType.f_39966_, RecipeType.f_44109_, RecipeBookType.BLAST_FURNACE, p_39079_, p_39080_);
    }

    public BlastFurnaceMenu(int p_39082_, Inventory p_39083_, Container p_39084_, ContainerData p_39085_) {
        super(MenuType.f_39966_, RecipeType.f_44109_, RecipeBookType.BLAST_FURNACE, p_39082_, p_39083_, p_39084_, p_39085_);
    }
}

