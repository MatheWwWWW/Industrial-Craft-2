/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerElectrolyzer
extends ContainerElectricMachine<TileEntityElectrolyzer> {
    public ContainerElectrolyzer(int n, Inventory inventory, TileEntityElectrolyzer tileEntityElectrolyzer) {
        super(Ic2ScreenHandlers.ELECTROLYZER, n, inventory, tileEntityElectrolyzer, 166, 8, 62);
        for (int i = 0; i < 4; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityElectrolyzer.upgradeSlot, i, 152, 8 + i * 18));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("progress");
        list.add("recipe");
        list.add("input");
        return list;
    }
}

