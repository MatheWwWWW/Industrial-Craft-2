/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerCondenser
extends ContainerElectricMachine<TileEntityCondenser> {
    public ContainerCondenser(int n, Inventory inventory, TileEntityCondenser tileEntityCondenser) {
        super(Ic2ScreenHandlers.CONDENSER, n, inventory, tileEntityCondenser, 184, 8, 44);
        this.m_38897_(new SlotInvSlot(tileEntityCondenser.waterInputSlot, 0, 26, 73));
        this.m_38897_(new SlotInvSlot(tileEntityCondenser.waterOutputSlot, 0, 134, 73));
        this.m_38897_(new SlotInvSlot(tileEntityCondenser.upgradeSlot, 0, 152, 73));
        for (int i = 0; i < 2; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityCondenser.ventSlots, i, 26 + i * 108, 26));
            this.m_38897_(new SlotInvSlot(tileEntityCondenser.ventSlots, i + 2, 26 + i * 108, 44));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("inputTank");
        list.add("outputTank");
        list.add("progress");
        return list;
    }
}

