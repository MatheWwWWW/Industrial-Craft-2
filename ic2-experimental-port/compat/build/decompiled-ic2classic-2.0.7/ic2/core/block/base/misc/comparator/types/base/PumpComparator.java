/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IPumpTile;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class PumpComparator
extends BaseComparator {
    IPumpTile pump;

    public PumpComparator(String id, Component name, IPumpTile pump) {
        super(id, name);
        this.pump = pump;
    }

    @Override
    protected int createValue() {
        return PumpComparator.value(this.pump.getPumpProgress(), this.pump.getPumpMaxProgress(), 15);
    }
}

