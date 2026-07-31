/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.MenuType
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class ContainerElectricMachine<T extends TileEntityElectricMachine>
extends ContainerFullInv<T> {
    public ContainerElectricMachine(MenuType<? extends ContainerElectricMachine<T>> menuType, int n, Inventory inventory, T t, int n2, int n3, int n4) {
        super(menuType, n, inventory, t, n2);
        this.m_38897_(new SlotInvSlot(((TileEntityElectricMachine)t).dischargeSlot, 0, n3, n4));
    }
}

