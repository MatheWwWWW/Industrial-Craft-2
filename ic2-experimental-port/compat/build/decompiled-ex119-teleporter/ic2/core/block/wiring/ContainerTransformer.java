/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.wiring;

import ic2.core.ContainerFullInv;
import ic2.core.block.wiring.tileentity.TileEntityTransformer;
import ic2.core.ref.Ic2ScreenHandlers;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;

public class ContainerTransformer
extends ContainerFullInv<TileEntityTransformer> {
    public ContainerTransformer(int n, Inventory inventory, TileEntityTransformer tileEntityTransformer) {
        super(Ic2ScreenHandlers.TRANSFORMER, n, inventory, tileEntityTransformer, 219);
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("configuredMode");
        list.add("inputFlow");
        list.add("outputFlow");
        return list;
    }
}

