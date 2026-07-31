/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.wiring;

import ic2.core.ContainerFullInv;
import ic2.core.block.wiring.tileentity.TileEntityChargepadBlock;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerChargepadBlock
extends ContainerFullInv<TileEntityChargepadBlock> {
    public ContainerChargepadBlock(int n, Inventory inventory, TileEntityChargepadBlock tileEntityChargepadBlock) {
        super(Ic2ScreenHandlers.CHARGEPAD, n, inventory, tileEntityChargepadBlock, 161);
        this.m_38897_(new SlotInvSlot(tileEntityChargepadBlock.chargeSlot, 0, 56, 17));
        this.m_38897_(new SlotInvSlot(tileEntityChargepadBlock.dischargeSlot, 0, 56, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("redstoneMode");
        return list;
    }
}

