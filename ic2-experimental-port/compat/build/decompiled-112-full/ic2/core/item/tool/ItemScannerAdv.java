/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.item.tool;

import ic2.api.item.ElectricItem;
import ic2.core.item.tool.ItemScanner;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;

public class ItemScannerAdv
extends ItemScanner {
    public ItemScannerAdv() {
        super(ItemName.advanced_scanner, 1000000.0, 512.0, 2);
    }

    @Override
    public int startLayerScan(ItemStack stack) {
        return ElectricItem.manager.use(stack, 250.0, null) ? this.getScanRange() / 2 : 0;
    }

    @Override
    public int getScanRange() {
        return 12;
    }
}

