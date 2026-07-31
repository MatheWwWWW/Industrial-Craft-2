/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.reactor.tileentity;

import ic2.core.block.reactor.tileentity.TileEntityAbstractRCI;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@NotClassic
public class TileEntityRCI_RSH
extends TileEntityAbstractRCI {
    public TileEntityRCI_RSH() {
        super(ItemName.rsh_condensator.getItemStack(), new ItemStack(Blocks.field_150451_bX));
    }
}

