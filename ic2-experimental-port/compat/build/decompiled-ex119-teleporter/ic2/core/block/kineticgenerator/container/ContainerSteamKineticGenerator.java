/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSteamKineticGenerator
extends ContainerFullInv<TileEntitySteamKineticGenerator> {
    public ContainerSteamKineticGenerator(int n, Inventory inventory, TileEntitySteamKineticGenerator tileEntitySteamKineticGenerator) {
        super(Ic2ScreenHandlers.STEAM_KINETIC_GENERATOR, n, inventory, tileEntitySteamKineticGenerator, 166);
        this.m_38897_(new SlotInvSlot(tileEntitySteamKineticGenerator.upgradeSlot, 0, 152, 26));
        this.m_38897_(new SlotInvSlot(tileEntitySteamKineticGenerator.turbineSlot, 0, 80, 26));
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("distilledWaterTank");
        list.add("kUoutput");
        list.add("ventingSteam");
        list.add("throttled");
        list.add("isTurbineFilledWithWater");
        return list;
    }
}

