/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerReplicator
extends ContainerElectricMachine<TileEntityReplicator> {
    public ContainerReplicator(int n, Inventory inventory, TileEntityReplicator tileEntityReplicator) {
        super(Ic2ScreenHandlers.REPLICATOR, n, inventory, tileEntityReplicator, 184, 152, 83);
        this.m_38897_(new SlotInvSlot(tileEntityReplicator.outputSlot, 0, 90, 59));
        this.m_38897_(new SlotInvSlot(tileEntityReplicator.fluidSlot, 0, 8, 27));
        this.m_38897_(new SlotInvSlot(tileEntityReplicator.cellSlot, 0, 8, 72));
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityReplicator.upgradeSlot, i, 152, 8 + i * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("fluidTank");
        list.add("uuProcessed");
        list.add("pattern");
        list.add("mode");
        list.add("index");
        list.add("maxIndex");
        list.add("patternUu");
        list.add("patternEu");
        return list;
    }
}

