/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.world.entity.player.Inventory;

public class ContainerElectricKineticGenerator
extends ContainerFullInv<TileEntityElectricKineticGenerator> {
    public ContainerElectricKineticGenerator(int n, Inventory inventory, TileEntityElectricKineticGenerator tileEntityElectricKineticGenerator) {
        super(Ic2ScreenHandlers.ELECTRIC_KINETIC_GENERATOR, n, inventory, tileEntityElectricKineticGenerator, 166);
        int n2;
        for (n2 = 0; n2 < 5; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityElectricKineticGenerator.slotMotor, n2, 44 + n2 * 18, 27));
        }
        for (n2 = 5; n2 < 10; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityElectricKineticGenerator.slotMotor, n2, 44 + (n2 - 5) * 18, 45));
        }
        this.m_38897_(new SlotInvSlot(tileEntityElectricKineticGenerator.dischargeSlot, 0, 8, 62));
    }
}

