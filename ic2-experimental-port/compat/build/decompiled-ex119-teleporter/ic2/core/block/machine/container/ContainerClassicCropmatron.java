/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityClassicCropmatron;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.world.entity.player.Inventory;

public class ContainerClassicCropmatron
extends ContainerFullInv<TileEntityClassicCropmatron> {
    public ContainerClassicCropmatron(int n, Inventory inventory, TileEntityClassicCropmatron tileEntityClassicCropmatron) {
        super(Ic2ScreenHandlers.CLASSIC_CROPMATRON, n, inventory, tileEntityClassicCropmatron, 166);
        int n2;
        for (n2 = 0; n2 < tileEntityClassicCropmatron.fertilizerSlot.size(); ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityClassicCropmatron.fertilizerSlot, n2, 62, 20 + n2 * 18));
        }
        for (n2 = 0; n2 < tileEntityClassicCropmatron.hydrationSlot.size(); ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityClassicCropmatron.hydrationSlot, n2, 98, 20 + n2 * 18));
        }
        for (n2 = 0; n2 < tileEntityClassicCropmatron.weedExSlot.size(); ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityClassicCropmatron.weedExSlot, n2, 134, 20 + n2 * 18));
        }
    }
}

