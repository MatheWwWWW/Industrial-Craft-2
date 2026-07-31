/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;

public class ContainerMagnetizer
extends ContainerElectricMachine<TileEntityMagnetizer> {
    public ContainerMagnetizer(int n, Inventory inventory, TileEntityMagnetizer tileEntityMagnetizer) {
        super(Ic2ScreenHandlers.MAGNETIZER, n, inventory, tileEntityMagnetizer, 166, 8, 44);
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityMagnetizer.upgradeSlot, i, 152, 8 + i * 18));
        }
        this.m_38897_(new SlotArmor(inventory, EquipmentSlot.FEET, 45, 26));
    }
}

