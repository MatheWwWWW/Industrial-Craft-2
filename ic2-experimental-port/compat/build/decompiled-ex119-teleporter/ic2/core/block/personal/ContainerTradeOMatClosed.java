/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import ic2.core.slot.SlotInvSlotReadOnly;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerTradeOMatClosed
extends ContainerFullInv<TileEntityTradeOMat> {
    public ContainerTradeOMatClosed(int n, Inventory inventory, TileEntityTradeOMat tileEntityTradeOMat) {
        super(Ic2ScreenHandlers.TRADE_O_MAT_CLOSED, n, inventory, tileEntityTradeOMat, 166);
        this.m_38897_(new SlotInvSlotReadOnly(tileEntityTradeOMat.demandSlot, 0, 50, 19));
        this.m_38897_(new SlotInvSlotReadOnly(tileEntityTradeOMat.offerSlot, 0, 50, 38));
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.inputSlot, 0, 143, 17));
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.outputSlot, 0, 143, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("stock");
        return list;
    }
}

