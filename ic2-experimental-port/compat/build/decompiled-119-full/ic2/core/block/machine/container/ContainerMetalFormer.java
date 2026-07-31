/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.container;

import ic2.core.block.machine.container.ContainerStandardMachine;
import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
import ic2.core.ref.Ic2ScreenHandlers;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerMetalFormer
extends ContainerStandardMachine<TileEntityMetalFormer> {
    public ContainerMetalFormer(int n, Inventory inventory, TileEntityMetalFormer tileEntityMetalFormer) {
        super(Ic2ScreenHandlers.METAL_FORMER, n, inventory, tileEntityMetalFormer, 166, 17, 53, 17, 17, 116, 35, 152, 8);
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("mode");
        return list;
    }
}

