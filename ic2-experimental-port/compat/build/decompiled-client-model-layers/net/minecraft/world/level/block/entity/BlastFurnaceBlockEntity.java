/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlastFurnaceBlockEntity
extends AbstractFurnaceBlockEntity {
    public BlastFurnaceBlockEntity(BlockPos p_155225_, BlockState p_155226_) {
        super(BlockEntityType.f_58907_, p_155225_, p_155226_, RecipeType.f_44109_);
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.blast_furnace");
    }

    @Override
    protected int m_7743_(ItemStack p_58852_) {
        return super.m_7743_(p_58852_) / 2;
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_58849_, Inventory p_58850_) {
        return new BlastFurnaceMenu(p_58849_, p_58850_, this, this.f_58311_);
    }
}

