/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.wiring;

import ic2.core.block.wiring.TileEntityChargepadBlock;
import ic2.core.profile.NotClassic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@NotClassic
public class TileEntityChargepadMFSU
extends TileEntityChargepadBlock {
    public TileEntityChargepadMFSU() {
        super(4, 2048, 40000000);
    }

    @Override
    protected void getItems(EntityPlayer player) {
        for (ItemStack current : player.field_71071_by.field_70460_b) {
            if (current == null) continue;
            this.chargeItem(current, 2048);
        }
        for (ItemStack current : player.field_71071_by.field_70462_a) {
            if (current == null) continue;
            this.chargeItem(current, 2048);
        }
    }
}

