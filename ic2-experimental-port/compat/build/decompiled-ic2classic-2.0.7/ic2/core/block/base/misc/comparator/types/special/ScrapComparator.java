/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.special;

import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.machines.tiles.hv.MassFabricatorTileEntity;
import net.minecraft.network.chat.Component;

public class ScrapComparator
extends BaseComparator {
    MassFabricatorTileEntity tile;

    public ScrapComparator(String id, Component name, MassFabricatorTileEntity tile) {
        super(id, name);
        this.tile = tile;
    }

    @Override
    protected int createValue() {
        return ScrapComparator.value(this.tile.getScrap(), this.tile.getScrap(), 15);
    }
}

