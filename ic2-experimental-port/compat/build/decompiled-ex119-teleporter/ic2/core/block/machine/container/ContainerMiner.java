/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityMiner;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerMiner
extends ContainerElectricMachine<TileEntityMiner> {
    public ContainerMiner(int n, Inventory inventory, TileEntityMiner tileEntityMiner) {
        super(Ic2ScreenHandlers.MINER, n, inventory, tileEntityMiner, 166, 152, 58);
        this.m_38897_(new SlotInvSlot(tileEntityMiner.scannerSlot, 0, 8, 58));
        this.m_38897_(new SlotInvSlot(tileEntityMiner.pipeSlot, 0, 8, 40));
        this.m_38897_(new SlotInvSlot(tileEntityMiner.drillSlot, 0, 8, 22));
        this.m_38897_(new SlotInvSlot(tileEntityMiner.upgradeSlot, 0, 152, 22));
        for (int i = 0; i < tileEntityMiner.buffer.size() / 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.m_38897_(new SlotInvSlot(tileEntityMiner.buffer, j + i * 5, 44 + j * 18, 22 + i * 18));
            }
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("progress");
        return list;
    }
}

