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
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerTradeOMatOpen
extends ContainerFullInv<TileEntityTradeOMat> {
    final boolean canToggleInfinite;

    public ContainerTradeOMatOpen(int n, Inventory inventory, TileEntityTradeOMat tileEntityTradeOMat, boolean bl) {
        super(Ic2ScreenHandlers.TRADE_O_MAT_OPEN, n, inventory, tileEntityTradeOMat, 166);
        this.canToggleInfinite = bl;
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.demandSlot, 0, 50, 19));
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.offerSlot, 0, 50, 53));
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.inputSlot, 0, 80, 19));
        this.m_38897_(new SlotInvSlot(tileEntityTradeOMat.outputSlot, 0, 80, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("stock");
        list.add("totalTradeCount");
        return list;
    }
}

