/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerCanner
extends ContainerStandardMachine<TileEntityCanner> {
    public ContainerCanner(int n, Inventory inventory, TileEntityCanner tileEntityCanner) {
        super(Ic2ScreenHandlers.CANNER, n, inventory, tileEntityCanner, 184, 8, 80, 80, 44, 119, 17, 152, 26);
        this.m_38897_(new SlotInvSlot(tileEntityCanner.canInputSlot, 0, 41, 17));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("mode");
        list.add("inputTank");
        list.add("outputTank");
        return list;
    }
}

