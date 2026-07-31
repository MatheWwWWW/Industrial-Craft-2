/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.special;

import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.misc.tiles.BarrelTileEntity;
import net.minecraft.network.chat.Component;

public class BarrelComparator
extends BaseComparator {
    BarrelTileEntity tile;
    boolean stage;

    public BarrelComparator(String id, Component name, BarrelTileEntity tile, boolean stage) {
        super(id, name);
        this.tile = tile;
        this.stage = stage;
    }

    @Override
    protected int createValue() {
        return this.stage ? this.tile.getStage() : this.tile.getStageProgress();
    }
}

