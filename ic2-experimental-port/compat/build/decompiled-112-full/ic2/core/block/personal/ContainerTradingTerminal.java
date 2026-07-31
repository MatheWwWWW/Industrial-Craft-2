/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Slot
 */
package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.personal.TileEntityTradingTerminal;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerTradingTerminal
extends ContainerFullInv<TileEntityTradingTerminal> {
    public final Slot rangeSlot;

    public ContainerTradingTerminal(EntityPlayer player, TileEntityTradingTerminal base) {
        super(player, base, 176, 227);
        this.rangeSlot = this.func_75146_a(new SlotInvSlot(((TileEntityTradingTerminal)this.base).rangeUpgrade, 0, -100, -100));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> out = super.getNetworkedFields();
        out.add("range");
        return out;
    }
}

