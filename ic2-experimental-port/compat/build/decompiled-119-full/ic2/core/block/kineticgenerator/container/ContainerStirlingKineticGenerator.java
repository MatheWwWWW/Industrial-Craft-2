/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerStirlingKineticGenerator
extends ContainerFullInv<TileEntityStirlingKineticGenerator> {
    public ContainerStirlingKineticGenerator(int n, Inventory inventory, TileEntityStirlingKineticGenerator tileEntityStirlingKineticGenerator) {
        super(Ic2ScreenHandlers.STIRLING_KINETIC_GENERATOR, n, inventory, tileEntityStirlingKineticGenerator, 204);
        this.m_38897_(new SlotInvSlot(tileEntityStirlingKineticGenerator.coolfluidinputSlot, 0, 8, 103));
        this.m_38897_(new SlotInvSlot(tileEntityStirlingKineticGenerator.cooloutputSlot, 0, 26, 103));
        this.m_38897_(new SlotInvSlot(tileEntityStirlingKineticGenerator.hotfluidinputSlot, 0, 134, 103));
        this.m_38897_(new SlotInvSlot(tileEntityStirlingKineticGenerator.hotoutputSlot, 0, 152, 103));
        for (int i = 0; i < 3; ++i) {
            this.m_38897_(new SlotInvSlot(tileEntityStirlingKineticGenerator.upgradeSlot, i, 62 + i * 18, 103));
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("inputTank");
        list.add("outputTank");
        return list;
    }
}

