/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityChunkloader;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerChunkLoader
extends ContainerFullInv<TileEntityChunkloader> {
    public ContainerChunkLoader(int n, Inventory inventory, TileEntityChunkloader tileEntityChunkloader) {
        super(Ic2ScreenHandlers.CHUNK_LOADER, n, inventory, tileEntityChunkloader, 250);
        this.m_38897_(new SlotInvSlot(tileEntityChunkloader.dischargeSlot, 0, 8, 143));
        for (int i = 0; i < tileEntityChunkloader.upgradeSlot.size(); ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityChunkloader.upgradeSlot, i, 8, 44 + 18 * i));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("loadedChunks");
        return list;
    }
}

