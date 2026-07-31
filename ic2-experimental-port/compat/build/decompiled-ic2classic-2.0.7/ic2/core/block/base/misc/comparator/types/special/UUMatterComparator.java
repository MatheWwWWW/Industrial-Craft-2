/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.special;

import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.machines.tiles.nv.UUMatterExpansionTileEntity;
import net.minecraft.network.chat.Component;

public class UUMatterComparator
extends BaseComparator {
    UUMatterExpansionTileEntity tile;

    public UUMatterComparator(String id, Component name, UUMatterExpansionTileEntity tile) {
        super(id, name);
        this.tile = tile;
    }

    @Override
    protected int createValue() {
        return UUMatterComparator.value(this.tile.uuMatter, this.tile.maxUUMatter, 15);
    }
}

