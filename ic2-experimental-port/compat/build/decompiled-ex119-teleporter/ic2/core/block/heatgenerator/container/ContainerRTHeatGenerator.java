/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.heatgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerRTHeatGenerator
extends ContainerFullInv<TileEntityRTHeatGenerator> {
    public ContainerRTHeatGenerator(int n, Inventory inventory, TileEntityRTHeatGenerator tileEntityRTHeatGenerator) {
        super(Ic2ScreenHandlers.RT_HEAT_GENERATOR, n, inventory, tileEntityRTHeatGenerator, 166);
        int n2;
        for (n2 = 0; n2 < 3; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityRTHeatGenerator.fuelSlot, n2, 62 + n2 * 18, 27));
        }
        for (n2 = 3; n2 < 6; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityRTHeatGenerator.fuelSlot, n2, 62 + (n2 - 3) * 18, 45));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("transmitHeat");
        list.add("maxHeatEmitpeerTick");
        return list;
    }
}

