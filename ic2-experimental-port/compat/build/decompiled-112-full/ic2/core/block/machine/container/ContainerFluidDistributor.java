/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerFluidDistributor
extends ContainerFullInv<TileEntityFluidDistributor> {
    public ContainerFluidDistributor(EntityPlayer player, TileEntityFluidDistributor tileEntite) {
        super(player, tileEntite, 184);
        this.func_75146_a(new SlotInvSlot(tileEntite.inputSlot, 0, 9, 54));
        this.func_75146_a(new SlotInvSlot(tileEntite.OutputSlot, 0, 9, 72));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("fluidTank");
        return ret;
    }
}

