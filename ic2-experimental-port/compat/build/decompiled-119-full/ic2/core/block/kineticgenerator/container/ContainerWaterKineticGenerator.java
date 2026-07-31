/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerWaterKineticGenerator
extends ContainerFullInv<TileEntityWaterKineticGenerator> {
    public ContainerWaterKineticGenerator(int n, Inventory inventory, TileEntityWaterKineticGenerator tileEntityWaterKineticGenerator) {
        super(Ic2ScreenHandlers.WATER_KINETIC_GENERATOR, n, inventory, tileEntityWaterKineticGenerator, 166);
        this.m_38897_(new SlotInvSlot(tileEntityWaterKineticGenerator.rotorSlot, 0, 80, 26));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("waterFlow");
        list.add("type");
        return list;
    }
}

