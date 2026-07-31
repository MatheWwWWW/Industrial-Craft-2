/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.core.item.tool.ItemScanner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemScannerAdv
extends ItemScanner {
    public ItemScannerAdv(Item.Properties properties) {
        super(properties, 1000000.0, 512.0, 2);
    }

    @Override
    public int startLayerScan(ItemStack itemStack) {
        return ElectricItem.manager.use(itemStack, 250.0, null) ? this.getScanRange() / 2 : 0;
    }

    @Override
    public int getScanRange() {
        return 12;
    }
}

