/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerCropHarvester
extends ContainerElectricMachine<TileEntityCropHarvester> {
    public ContainerCropHarvester(int n, Inventory inventory, TileEntityCropHarvester tileEntityCropHarvester) {
        super(Ic2ScreenHandlers.CROP_HARVESTER, n, inventory, tileEntityCropHarvester, 166, 16, 53);
        int n2;
        for (n2 = 0; n2 < tileEntityCropHarvester.contentSlot.size() / 5; ++n2) {
            for (int i = 0; i < 5; ++i) {
                this.m_38897_(new SlotInvSlot(tileEntityCropHarvester.contentSlot, i + n2 * 5, 48 + i * 18, 17 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 4; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityCropHarvester.upgradeSlot, n2, 152, 8 + n2 * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("energy");
        return list;
    }
}

