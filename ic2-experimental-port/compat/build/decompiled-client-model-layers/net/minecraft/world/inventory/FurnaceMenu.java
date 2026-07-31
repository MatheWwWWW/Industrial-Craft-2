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

public class FurnaceMenu
extends AbstractFurnaceMenu {
    public FurnaceMenu(int p_39532_, Inventory p_39533_) {
        super(MenuType.f_39970_, RecipeType.f_44108_, RecipeBookType.FURNACE, p_39532_, p_39533_);
    }

    public FurnaceMenu(int p_39535_, Inventory p_39536_, Container p_39537_, ContainerData p_39538_) {
        super(MenuType.f_39970_, RecipeType.f_44108_, RecipeBookType.FURNACE, p_39535_, p_39536_, p_39537_, p_39538_);
    }
}

