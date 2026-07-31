/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerPatternStorage
extends ContainerFullInv<TileEntityPatternStorage> {
    public ContainerPatternStorage(int n, Inventory inventory, TileEntityPatternStorage tileEntityPatternStorage) {
        super(Ic2ScreenHandlers.PATTERN_STORAGE, n, inventory, tileEntityPatternStorage, 166);
        this.m_38897_(new SlotInvSlot(tileEntityPatternStorage.diskSlot, 0, 18, 20));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("index");
        list.add("maxIndex");
        list.add("pattern");
        list.add("patternUu");
        list.add("patternEu");
        return list;
    }
}

