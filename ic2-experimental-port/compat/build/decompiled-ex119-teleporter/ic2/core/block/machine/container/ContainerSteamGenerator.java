/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerBase;
import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.ref.Ic2ScreenHandlers;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerSteamGenerator
extends ContainerBase<TileEntitySteamGenerator> {
    public ContainerSteamGenerator(int n, Inventory inventory, TileEntitySteamGenerator tileEntitySteamGenerator) {
        super(Ic2ScreenHandlers.STEAM_GENERATOR, n, inventory, tileEntitySteamGenerator);
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("waterTank");
        list.add("heatInput");
        list.add("inputMB");
        list.add("outputMB");
        list.add("pressure");
        list.add("systemHeat");
        list.add("outputFluid");
        list.add("calcification");
        return list;
    }
}

