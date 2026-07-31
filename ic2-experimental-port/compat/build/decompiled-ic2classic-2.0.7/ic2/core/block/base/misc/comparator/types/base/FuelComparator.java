/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IFuelStorage;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class FuelComparator
extends BaseComparator {
    IFuelStorage storage;

    public FuelComparator(String id, Component name, IFuelStorage storage) {
        super(id, name);
        this.storage = storage;
    }

    @Override
    protected int createValue() {
        return FuelComparator.value(this.storage.getFuel(), this.storage.getMaxFuel(), 15);
    }
}

