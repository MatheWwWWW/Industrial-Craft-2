/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.heatgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerFluidHeatGenerator
extends ContainerFullInv<TileEntityFluidHeatGenerator> {
    public ContainerFluidHeatGenerator(EntityPlayer player, TileEntityFluidHeatGenerator tileEntity1) {
        super(player, tileEntity1, 166);
        this.func_75146_a(new SlotInvSlot(tileEntity1.fluidSlot, 0, 27, 21));
        this.func_75146_a(new SlotInvSlot(tileEntity1.outputSlot, 0, 27, 54));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("fluidTank");
        ret.add("transmitHeat");
        ret.add("maxHeatEmitpeerTick");
        return ret;
    }
}

