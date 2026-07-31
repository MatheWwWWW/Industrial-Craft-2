/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFluidRegulator
extends ContainerElectricMachine<TileEntityFluidRegulator> {
    public ContainerFluidRegulator(int n, Inventory inventory, TileEntityFluidRegulator tileEntityFluidRegulator) {
        super(Ic2ScreenHandlers.FLUID_REGULATOR, n, inventory, tileEntityFluidRegulator, 184, 8, 57);
        this.m_38897_(new SlotInvSlot(tileEntityFluidRegulator.wasserinputSlot, 0, 58, 53));
        this.m_38897_(new SlotInvSlot(tileEntityFluidRegulator.wasseroutputSlot, 0, 58, 71));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        list.add("outputmb");
        list.add("mode");
        return list;
    }
}

