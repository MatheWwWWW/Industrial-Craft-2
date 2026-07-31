/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerMatter
extends ContainerFullInv<TileEntityMatter> {
    public ContainerMatter(int n, Inventory inventory, TileEntityMatter tileEntityMatter) {
        super(Ic2ScreenHandlers.MATTER_GENERATOR, n, inventory, tileEntityMatter, 166);
        this.m_38897_(new SlotInvSlot(tileEntityMatter.amplifierSlot, 0, 72, 40));
        this.m_38897_(new SlotInvSlot(tileEntityMatter.outputSlot, 0, 125, 59));
        this.m_38897_(new SlotInvSlot(tileEntityMatter.containerslot, 0, 125, 23));
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityMatter.upgradeSlot, i, 152, 8 + i * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("energy");
        list.add("scrap");
        list.add("fluidTank");
        return list;
    }
}

