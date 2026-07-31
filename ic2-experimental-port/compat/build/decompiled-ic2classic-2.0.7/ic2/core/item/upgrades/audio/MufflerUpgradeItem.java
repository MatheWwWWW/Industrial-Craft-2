/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.upgrades.audio;

import ic2.api.items.IUpgradeItem;
import ic2.api.tiles.IMachine;
import ic2.core.item.upgrades.base.BaseUpgradeItem;
import net.minecraft.world.item.ItemStack;

public class MufflerUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public MufflerUpgradeItem() {
        super("muffler");
    }

    @Override
    public float getSoundMultiplier(ItemStack stack, IMachine machine) {
        return 0.8f;
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.AUDIO_MOD;
    }
}

