/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.inventory.slot;

import ic2.core.block.base.tiles.impls.BaseChargePadTileEntity;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.item.upgrades.PadUpgradeItem;

public class ChargePadSlot
extends FilterSlot {
    public ChargePadSlot(BaseChargePadTileEntity inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition, new ClassFilter(PadUpgradeItem.class));
    }

    @Override
    public void m_6654_() {
        super.m_6654_();
        ((BaseChargePadTileEntity)this.inventory).onPadUpgradeChanged();
    }
}

