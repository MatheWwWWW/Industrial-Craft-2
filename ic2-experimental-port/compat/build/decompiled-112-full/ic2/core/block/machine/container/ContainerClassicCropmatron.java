/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityClassicCropmatron;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerClassicCropmatron
extends ContainerFullInv<TileEntityClassicCropmatron> {
    public ContainerClassicCropmatron(EntityPlayer player, TileEntityClassicCropmatron base) {
        super(player, base, 166);
        int i;
        for (i = 0; i < base.fertilizerSlot.size(); ++i) {
            this.func_75146_a(new SlotInvSlot(base.fertilizerSlot, i, 62, 20 + i * 18));
        }
        for (i = 0; i < base.hydrationSlot.size(); ++i) {
            this.func_75146_a(new SlotInvSlot(base.hydrationSlot, i, 98, 20 + i * 18));
        }
        for (i = 0; i < base.weedExSlot.size(); ++i) {
            this.func_75146_a(new SlotInvSlot(base.weedExSlot, i, 134, 20 + i * 18));
        }
    }
}

