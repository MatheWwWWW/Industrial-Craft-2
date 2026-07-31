/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.MenuType
 */
package ic2.core;

import ic2.core.ContainerBase;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public abstract class ContainerFullInv<T extends Container>
extends ContainerBase<T> {
    public ContainerFullInv(MenuType<?> menuType, int n, Inventory inventory, T t, int n2) {
        super(menuType, n, inventory, t);
        this.addPlayerInventorySlots(inventory, n2);
    }

    public ContainerFullInv(MenuType<?> menuType, int n, Inventory inventory, T t, int n2, int n3) {
        super(menuType, n, inventory, t);
        this.addPlayerInventorySlots(inventory, n2, n3);
    }
}

