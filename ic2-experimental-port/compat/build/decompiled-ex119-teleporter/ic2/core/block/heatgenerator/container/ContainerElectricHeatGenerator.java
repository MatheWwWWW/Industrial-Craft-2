/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.heatgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerElectricHeatGenerator
extends ContainerFullInv<TileEntityElectricHeatGenerator> {
    public ContainerElectricHeatGenerator(int n, Inventory inventory, TileEntityElectricHeatGenerator tileEntityElectricHeatGenerator) {
        super(Ic2ScreenHandlers.ELECTRIC_HEAT_GENERATOR, n, inventory, tileEntityElectricHeatGenerator, 166);
        int n2;
        for (n2 = 0; n2 < 5; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityElectricHeatGenerator.coilSlot, n2, 44 + n2 * 18, 27));
        }
        for (n2 = 5; n2 < 10; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityElectricHeatGenerator.coilSlot, n2, 44 + (n2 - 5) * 18, 45));
        }
        this.m_38897_(new SlotInvSlot(tileEntityElectricHeatGenerator.dischargeSlot, 0, 8, 62));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("transmitHeat");
        list.add("maxHeatEmitpeerTick");
        return list;
    }
}

