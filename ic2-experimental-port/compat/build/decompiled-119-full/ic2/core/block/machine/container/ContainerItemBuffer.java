/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityItemBuffer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.world.entity.player.Inventory;

public class ContainerItemBuffer
extends ContainerFullInv<TileEntityItemBuffer> {
    public ContainerItemBuffer(int n, Inventory inventory, TileEntityItemBuffer tileEntityItemBuffer) {
        super(Ic2ScreenHandlers.ITEM_BUFFER, n, inventory, tileEntityItemBuffer, 232);
        int n2;
        int n3;
        for (n3 = 0; n3 < tileEntityItemBuffer.leftcontentSlot.size() / 4; ++n3) {
            for (n2 = 0; n2 < 4; ++n2) {
                this.m_38897_(new SlotInvSlot(tileEntityItemBuffer.leftcontentSlot, n2 + n3 * 4, 8 + n2 * 18, 18 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < tileEntityItemBuffer.rightcontentSlot.size() / 4; ++n3) {
            for (n2 = 0; n2 < 4; ++n2) {
                this.m_38897_(new SlotInvSlot(tileEntityItemBuffer.rightcontentSlot, n2 + n3 * 4, 98 + n2 * 18, 18 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 2; ++n3) {
            this.m_38897_(new SlotInvSlot(tileEntityItemBuffer.upgradeSlot, n3, 35 + n3 * 90, 128));
        }
    }
}

