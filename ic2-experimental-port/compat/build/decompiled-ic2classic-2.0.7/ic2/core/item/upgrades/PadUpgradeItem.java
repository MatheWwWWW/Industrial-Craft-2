/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.item.upgrades;

import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.item.base.IC2SimpleItem;

public class PadUpgradeItem
extends IC2SimpleItem {
    BaseChargePadTileEntity.PadUpgrade upgrade;

    public PadUpgradeItem(String itemName, BaseChargePadTileEntity.PadUpgrade upgrade, String textureName) {
        super(itemName, "upgrades/chargepads", textureName);
        this.upgrade = upgrade;
    }

    public BaseChargePadTileEntity.PadUpgrade getUpgrade() {
        return this.upgrade;
    }
}

