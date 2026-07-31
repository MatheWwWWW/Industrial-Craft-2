/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFluidDistributor
extends ContainerFullInv<TileEntityFluidDistributor> {
    public ContainerFluidDistributor(int n, Inventory inventory, TileEntityFluidDistributor tileEntityFluidDistributor) {
        super(Ic2ScreenHandlers.FLUID_DISTRIBUTOR, n, inventory, tileEntityFluidDistributor, 184);
        this.m_38897_(new SlotInvSlot(tileEntityFluidDistributor.inputSlot, 0, 9, 54));
        this.m_38897_(new SlotInvSlot(tileEntityFluidDistributor.OutputSlot, 0, 9, 72));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        return list;
    }
}

