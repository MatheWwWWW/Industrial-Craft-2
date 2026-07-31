/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityClassicCanner;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerClassicCanner
extends ContainerElectricMachine<TileEntityClassicCanner> {
    public ContainerClassicCanner(int n, Inventory inventory, TileEntityClassicCanner tileEntityClassicCanner) {
        super(Ic2ScreenHandlers.CLASSIC_CANNER, n, inventory, tileEntityClassicCanner, 166, 30, 45);
        this.m_38897_(new SlotInvSlot(tileEntityClassicCanner.resInputSlot, 0, 69, 17));
        this.m_38897_(new SlotInvSlot(tileEntityClassicCanner.outputSlot, 0, 119, 35));
        this.m_38897_(new SlotInvSlot(tileEntityClassicCanner.inputSlot, 0, 69, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("progress");
        list.add("mode");
        return list;
    }
}

