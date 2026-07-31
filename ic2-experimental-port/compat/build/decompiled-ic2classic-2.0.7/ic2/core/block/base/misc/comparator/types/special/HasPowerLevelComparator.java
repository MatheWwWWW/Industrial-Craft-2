/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.special;

import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class HasPowerLevelComparator
extends BaseComparator {
    IEUStorage storage;
    float level;

    public HasPowerLevelComparator(String id, Component name, IEUStorage storage, float level) {
        super(id, name);
        this.storage = storage;
        this.level = level;
    }

    @Override
    protected int createValue() {
        return (float)this.storage.getStoredEU() / (float)this.storage.getMaxEU() > this.level ? 15 : 0;
    }
}

