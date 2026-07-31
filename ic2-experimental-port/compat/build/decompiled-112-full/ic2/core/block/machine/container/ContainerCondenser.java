/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCondenser
extends ContainerElectricMachine<TileEntityCondenser> {
    public ContainerCondenser(EntityPlayer player, TileEntityCondenser te) {
        super(player, te, 184, 8, 44);
        this.func_75146_a(new SlotInvSlot(te.waterInputSlot, 0, 26, 73));
        this.func_75146_a(new SlotInvSlot(te.waterOutputSlot, 0, 134, 73));
        this.func_75146_a(new SlotInvSlot(te.upgradeSlot, 0, 152, 73));
        for (int i = 0; i < 2; ++i) {
            this.func_75146_a(new SlotInvSlot(te.ventSlots, i, 26 + i * 108, 26));
            this.func_75146_a(new SlotInvSlot(te.ventSlots, i + 2, 26 + i * 108, 44));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("inputTank");
        ret.add("outputTank");
        ret.add("progress");
        return ret;
    }
}

