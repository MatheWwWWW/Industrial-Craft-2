/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import ic2.core.IC2;
import ic2.core.item.ContainerHandHeldInventory;
import ic2.core.item.tool.HandHeldScanner;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.util.Tuple;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ContainerToolScanner
extends ContainerHandHeldInventory<HandHeldScanner> {
    public List<Tuple.T2<ItemStack, Integer>> scanResults;

    public ContainerToolScanner(int n, Inventory inventory, HandHeldScanner handHeldScanner) {
        super(Ic2ScreenHandlers.SCANNER, n, handHeldScanner);
        this.addPlayerInventorySlots(inventory, 231);
    }

    public void setResults(List<Tuple.T2<ItemStack, Integer>> list) {
        this.scanResults = list;
        IC2.network.get(true).sendContainerField(this, "scanResults");
    }
}

