/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public abstract class ContainerElectricMachine<T extends TileEntityElectricMachine>
extends ContainerFullInv<T> {
    public ContainerElectricMachine(EntityPlayer player, T base1, int height, int dischargeX, int dischargeY) {
        super(player, base1, height);
        this.func_75146_a(new SlotInvSlot(((TileEntityElectricMachine)base1).dischargeSlot, 0, dischargeX, dischargeY));
    }
}

