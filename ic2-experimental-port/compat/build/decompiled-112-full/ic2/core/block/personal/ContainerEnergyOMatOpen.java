/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IContainerListener
 */
package ic2.core.block.personal;

import ic2.core.ContainerFullInv;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;

public class ContainerEnergyOMatOpen
extends ContainerFullInv<TileEntityEnergyOMat> {
    private int lastTier = -1;

    public ContainerEnergyOMatOpen(EntityPlayer player, TileEntityEnergyOMat tileEntity1) {
        super(player, tileEntity1, 166);
        this.func_75146_a(new SlotInvSlot(tileEntity1.demandSlot, 0, 24, 17));
        this.func_75146_a(new SlotInvSlot(tileEntity1.upgradeSlot, 0, 24, 53));
        this.func_75146_a(new SlotInvSlot(tileEntity1.inputSlot, 0, 60, 17));
        this.func_75146_a(new SlotInvSlot(tileEntity1.chargeSlot, 0, 60, 53));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("paidFor");
        ret.add("euBuffer");
        ret.add("euOffer");
        return ret;
    }

    @Override
    public void func_75142_b() {
        super.func_75142_b();
        for (IContainerListener listener : this.field_75149_d) {
            if (((TileEntityEnergyOMat)this.base).chargeSlot.tier == this.lastTier) continue;
            listener.func_71112_a((Container)this, 0, ((TileEntityEnergyOMat)this.base).chargeSlot.tier);
        }
        this.lastTier = ((TileEntityEnergyOMat)this.base).chargeSlot.tier;
    }

    public void func_75137_b(int index, int value) {
        super.func_75137_b(index, value);
        switch (index) {
            case 0: {
                ((TileEntityEnergyOMat)this.base).chargeSlot.tier = value;
            }
        }
    }
}

