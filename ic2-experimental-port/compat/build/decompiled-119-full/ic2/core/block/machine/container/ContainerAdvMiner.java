/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerAdvMiner
extends ContainerElectricMachine<TileEntityAdvMiner> {
    public ContainerAdvMiner(int n, Inventory inventory, TileEntityAdvMiner tileEntityAdvMiner) {
        super(Ic2ScreenHandlers.ADVANCED_MINER, n, inventory, tileEntityAdvMiner, 203, 8, 80);
        int n2;
        this.m_38897_(new SlotInvSlot(tileEntityAdvMiner.scannerSlot, 0, 8, 26));
        for (n2 = 0; n2 < 4; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityAdvMiner.upgradeSlot, n2, 152, 26 + n2 * 18));
        }
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 5; ++i) {
                this.m_38897_(new SlotInvSlot(tileEntityAdvMiner.filterSlot, i + n2 * 5, 36 + i * 18, 44 + n2 * 18));
            }
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("mineTarget");
        list.add("blacklist");
        list.add("silkTouch");
        return list;
    }
}

