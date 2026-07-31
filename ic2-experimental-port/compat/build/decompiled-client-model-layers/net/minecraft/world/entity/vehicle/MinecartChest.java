/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.vehicle;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MinecartChest
extends AbstractMinecartContainer {
    public MinecartChest(EntityType<? extends MinecartChest> p_38487_, Level p_38488_) {
        super(p_38487_, p_38488_);
    }

    public MinecartChest(Level p_38490_, double p_38491_, double p_38492_, double p_38493_) {
        super(EntityType.f_20470_, p_38491_, p_38492_, p_38493_, p_38490_);
    }

    @Override
    protected Item m_213728_() {
        return Items.f_42519_;
    }

    @Override
    public int m_6643_() {
        return 27;
    }

    @Override
    public AbstractMinecart.Type m_6064_() {
        return AbstractMinecart.Type.CHEST;
    }

    @Override
    public BlockState m_6390_() {
        return (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, Direction.NORTH);
    }

    @Override
    public int m_7144_() {
        return 8;
    }

    @Override
    public AbstractContainerMenu m_7402_(int p_38496_, Inventory p_38497_) {
        return ChestMenu.m_39237_(p_38496_, p_38497_, this);
    }
}

