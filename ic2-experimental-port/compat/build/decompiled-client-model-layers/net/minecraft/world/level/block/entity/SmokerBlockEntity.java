/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SmokerBlockEntity
extends AbstractFurnaceBlockEntity {
    public SmokerBlockEntity(BlockPos p_155749_, BlockState p_155750_) {
        super(BlockEntityType.f_58906_, p_155749_, p_155750_, RecipeType.f_44110_);
    }

    @Override
    protected Component m_6820_() {
        return Component.m_237115_("container.smoker");
    }

    @Override
    protected int m_7743_(ItemStack p_59786_) {
        return super.m_7743_(p_59786_) / 2;
    }

    @Override
    protected AbstractContainerMenu m_6555_(int p_59783_, Inventory p_59784_) {
        return new SmokerMenu(p_59783_, p_59784_, this, this.f_58311_);
    }
}

