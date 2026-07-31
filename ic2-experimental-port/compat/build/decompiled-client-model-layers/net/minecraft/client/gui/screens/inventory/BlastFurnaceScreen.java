/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens.inventory;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.BlastingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BlastFurnaceMenu;

public class BlastFurnaceScreen
extends AbstractFurnaceScreen<BlastFurnaceMenu> {
    private static final ResourceLocation f_98042_ = new ResourceLocation("textures/gui/container/blast_furnace.png");

    public BlastFurnaceScreen(BlastFurnaceMenu p_98045_, Inventory p_98046_, Component p_98047_) {
        super(p_98045_, new BlastingRecipeBookComponent(), p_98046_, p_98047_, f_98042_);
    }
}

