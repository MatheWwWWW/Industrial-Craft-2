/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerWeightedFluidDistributor
extends ContainerFullInv<TileEntityWeightedFluidDistributor> {
    public static final short HEIGHT = 211;

    public ContainerWeightedFluidDistributor(int n, Inventory inventory, TileEntityWeightedFluidDistributor tileEntityWeightedFluidDistributor) {
        super(Ic2ScreenHandlers.WEIGHTED_FLUID_DISTRIBUTOR, n, inventory, tileEntityWeightedFluidDistributor, 211);
        this.m_38897_(new SlotInvSlot(tileEntityWeightedFluidDistributor.inputSlot, 0, 8, 108));
        this.m_38897_(new SlotInvSlot(tileEntityWeightedFluidDistributor.OutputSlot, 0, 152, 108));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        return list;
    }
}

