/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerElectricKineticGenerator
extends ContainerFullInv<TileEntityElectricKineticGenerator> {
    public ContainerElectricKineticGenerator(EntityPlayer player, TileEntityElectricKineticGenerator tileEntity1) {
        super(player, tileEntity1, 166);
        int i;
        for (i = 0; i < 5; ++i) {
            this.func_75146_a(new SlotInvSlot(tileEntity1.slotMotor, i, 44 + i * 18, 27));
        }
        for (i = 5; i < 10; ++i) {
            this.func_75146_a(new SlotInvSlot(tileEntity1.slotMotor, i, 44 + (i - 5) * 18, 45));
        }
        this.func_75146_a(new SlotInvSlot(tileEntity1.dischargeSlot, 0, 8, 62));
    }
}

