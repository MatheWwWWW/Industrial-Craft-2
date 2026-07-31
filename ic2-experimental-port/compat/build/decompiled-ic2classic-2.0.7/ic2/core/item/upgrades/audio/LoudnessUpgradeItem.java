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

public class LoudnessUpgradeItem
extends BaseUpgradeItem.SimpleUpgradeItem {
    public LoudnessUpgradeItem() {
        super("loudness");
    }

    @Override
    public float getSoundMultiplier(ItemStack stack, IMachine machine) {
        return 1.35f;
    }

    @Override
    public IUpgradeItem.UpgradeType getType(ItemStack stack) {
        return IUpgradeItem.UpgradeType.AUDIO_MOD;
    }
}

