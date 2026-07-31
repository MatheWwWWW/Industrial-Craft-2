/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class EUComparator
extends BaseComparator {
    IEUStorage storage;

    public EUComparator(String id, Component name, IEUStorage storage) {
        super(id, name);
        this.storage = storage;
    }

    @Override
    protected int createValue() {
        return EUComparator.value(this.storage.getStoredEU(), this.storage.getMaxEU(), 15);
    }
}

