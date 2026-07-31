/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerCropmatron
extends ContainerElectricMachine<TileEntityCropmatron> {
    public ContainerCropmatron(int n, Inventory inventory, TileEntityCropmatron tileEntityCropmatron) {
        super(Ic2ScreenHandlers.CROPMATRON, n, inventory, tileEntityCropmatron, 192, 134, 80);
        int n2;
        for (n2 = 0; n2 < tileEntityCropmatron.fertilizerSlot.size(); ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityCropmatron.fertilizerSlot, n2, 8 + n2 * 18, 80));
        }
        this.m_38897_(new SlotInvSlot(tileEntityCropmatron.exInputSlot, 0, 49, 27));
        this.m_38897_(new SlotInvSlot(tileEntityCropmatron.exOutputSlot, 0, 67, 27));
        this.m_38897_(new SlotInvSlot(tileEntityCropmatron.wasserinputSlot, 0, 57, 56));
        this.m_38897_(new SlotInvSlot(tileEntityCropmatron.wasseroutputSlot, 0, 75, 56));
        for (n2 = 0; n2 < 4; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityCropmatron.upgradeSlot, n2, 152, 26 + n2 * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("waterTank");
        list.add("exTank");
        return list;
    }
}

