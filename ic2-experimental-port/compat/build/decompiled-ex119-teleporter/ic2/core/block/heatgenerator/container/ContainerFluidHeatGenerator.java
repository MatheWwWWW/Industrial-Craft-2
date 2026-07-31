/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.heatgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerFluidHeatGenerator
extends ContainerFullInv<TileEntityFluidHeatGenerator> {
    public ContainerFluidHeatGenerator(int n, Inventory inventory, TileEntityFluidHeatGenerator tileEntityFluidHeatGenerator) {
        super(Ic2ScreenHandlers.FLUID_HEAT_GENERATOR, n, inventory, tileEntityFluidHeatGenerator, 166);
        this.m_38897_(new SlotInvSlot(tileEntityFluidHeatGenerator.fluidSlot, 0, 27, 21));
        this.m_38897_(new SlotInvSlot(tileEntityFluidHeatGenerator.outputSlot, 0, 27, 54));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        list.add("transmitHeat");
        list.add("maxHeatEmitpeerTick");
        return list;
    }
}

