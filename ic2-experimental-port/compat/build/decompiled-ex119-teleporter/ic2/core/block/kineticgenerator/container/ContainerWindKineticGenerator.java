/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerWindKineticGenerator
extends ContainerFullInv<TileEntityWindKineticGenerator> {
    public ContainerWindKineticGenerator(int n, Inventory inventory, TileEntityWindKineticGenerator tileEntityWindKineticGenerator) {
        super(Ic2ScreenHandlers.WIND_KINETIC_GENERATOR, n, inventory, tileEntityWindKineticGenerator, 166);
        this.m_38897_(new SlotInvSlot(tileEntityWindKineticGenerator.rotorSlot, 0, 80, 26));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("windStrength");
        return list;
    }
}

