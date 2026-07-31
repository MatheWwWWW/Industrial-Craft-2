/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.wiring;

import ic2.core.ContainerFullInv;
import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.ArmorSlot;
import ic2.core.slot.SlotArmor;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerElectricBlock
extends ContainerFullInv<TileEntityElectricBlock> {
    public ContainerElectricBlock(int n, Inventory inventory, TileEntityElectricBlock tileEntityElectricBlock) {
        super(Ic2ScreenHandlers.ENERGY_STORAGE, n, inventory, tileEntityElectricBlock, 196);
        for (int i = 0; i < ArmorSlot.getCount(); ++i) {
            this.m_38897_(new SlotArmor(inventory, ArmorSlot.get(i), 8 + i * 18, 84));
        }
        this.m_38897_(new SlotInvSlot(tileEntityElectricBlock.chargeSlot, 0, 56, 17));
        this.m_38897_(new SlotInvSlot(tileEntityElectricBlock.dischargeSlot, 0, 56, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("redstoneMode");
        return list;
    }
}

