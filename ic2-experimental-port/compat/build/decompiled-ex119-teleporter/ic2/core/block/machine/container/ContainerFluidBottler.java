/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityFluidBottler;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFluidBottler
extends ContainerStandardMachine<TileEntityFluidBottler> {
    public ContainerFluidBottler(int n, Inventory inventory, TileEntityFluidBottler tileEntityFluidBottler) {
        super(Ic2ScreenHandlers.FLUID_BOTTLER, n, inventory, tileEntityFluidBottler, 184, 8, 53, 0, 0, 117, 53, 152, 26);
        this.m_38897_(new SlotInvSlot(tileEntityFluidBottler.drainInputSlot, 0, 44, 35));
        this.m_38897_(new SlotInvSlot(tileEntityFluidBottler.fillInputSlot, 0, 44, 72));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        return list;
    }
}

