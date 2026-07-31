/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.Util;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ContainerSortingMachine
extends ContainerElectricMachine<TileEntitySortingMachine> {
    public ContainerSortingMachine(int n, Inventory inventory, TileEntitySortingMachine tileEntitySortingMachine) {
        super(Ic2ScreenHandlers.SORTING_MACHINE, n, inventory, tileEntitySortingMachine, 243, 188, 219);
        int n2;
        for (n2 = 0; n2 < 3; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntitySortingMachine.upgradeSlot, n2, 188, 161 + n2 * 18));
        }
        for (n2 = 0; n2 < 11; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntitySortingMachine.buffer, n2, 8 + n2 * 18, 141));
        }
        for (n2 = 0; n2 < Util.ALL_DIRS.length; ++n2) {
            Direction direction = Util.ALL_DIRS[n2];
            ItemStack[] itemStackArray = tileEntitySortingMachine.getFilterSlots(direction);
            for (int i = 0; i < itemStackArray.length; ++i) {
                this.m_38897_(new SlotHologramSlot(itemStackArray, i, 80 + i * 18, 19 + n2 * 20, 64, null));
            }
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("defaultRoute");
        return list;
    }
}

