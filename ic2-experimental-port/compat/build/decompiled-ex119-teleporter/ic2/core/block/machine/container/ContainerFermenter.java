/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityFermenter;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFermenter
extends ContainerFullInv<TileEntityFermenter> {
    public ContainerFermenter(int n, Inventory inventory, TileEntityFermenter tileEntityFermenter) {
        super(Ic2ScreenHandlers.FERMENTER, n, inventory, tileEntityFermenter, 184);
        this.m_38897_(new SlotInvSlot(tileEntityFermenter.fluidInputCellInSlot, 0, 14, 46));
        this.m_38897_(new SlotInvSlot(tileEntityFermenter.fluidInputCellOutSlot, 0, 14, 64));
        this.m_38897_(new SlotInvSlot(tileEntityFermenter.fluidOutputCellInSlot, 0, 148, 43));
        this.m_38897_(new SlotInvSlot(tileEntityFermenter.fluidOutputCellOutSlot, 0, 148, 61));
        this.m_38897_(new SlotInvSlot(tileEntityFermenter.fertiliserSlot, 0, 86, 83));
        for (int i = 0; i < 2; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityFermenter.upgradeSlot, i, 125 + i * 18, 83));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("inputTank");
        list.add("outputTank");
        list.add("progress");
        list.add("heatBuffer");
        return list;
    }
}

