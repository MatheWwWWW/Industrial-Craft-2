/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.DataSlot
 */
package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import ic2.core.slot.SlotInvSlotReadOnly;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;

public class ContainerEnergyOMatClosed
extends ContainerFullInv<TileEntityEnergyOMat> {
    public ContainerEnergyOMatClosed(int n, Inventory inventory, TileEntityEnergyOMat tileEntityEnergyOMat) {
        super(Ic2ScreenHandlers.ENERGY_O_MAT_CLOSED, n, inventory, tileEntityEnergyOMat, 166);
        this.m_38897_(new SlotInvSlotReadOnly(tileEntityEnergyOMat.demandSlot, 0, 50, 17));
        this.m_38897_(new SlotInvSlot(tileEntityEnergyOMat.inputSlot, 0, 143, 17));
        this.m_38897_(new SlotInvSlot(tileEntityEnergyOMat.chargeSlot, 0, 143, 53));
        this.m_38895_(new DataSlot(){

            public int m_6501_() {
                return ((TileEntityEnergyOMat)ContainerEnergyOMatClosed.this.base).chargeSlot.tier;
            }

            public void m_6422_(int n) {
                ((TileEntityEnergyOMat)ContainerEnergyOMatClosed.this.base).chargeSlot.tier = n;
            }
        });
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("paidFor");
        list.add("euOffer");
        return list;
    }
}

