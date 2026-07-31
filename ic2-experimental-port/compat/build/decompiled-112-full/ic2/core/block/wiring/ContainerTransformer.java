/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 */
package ic2.core.block.wiring;

import ic2.core.ContainerFullInv;
import ic2.core.block.wiring.TileEntityTransformer;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerTransformer
extends ContainerFullInv<TileEntityTransformer> {
    public ContainerTransformer(EntityPlayer player, TileEntityTransformer tileEntity1, int height) {
        super(player, tileEntity1, height);
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("configuredMode");
        ret.add("inputFlow");
        ret.add("outputFlow");
        return ret;
    }
}

