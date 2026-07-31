/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IAirSpeed;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class WindComparator
extends BaseComparator {
    IAirSpeed speed;

    public WindComparator(String id, Component name, IAirSpeed speed) {
        super(id, name);
        this.speed = speed;
    }

    @Override
    protected int createValue() {
        return WindComparator.value(this.speed.getCurrentSpeed(), this.speed.getMaxSpeed(), 15);
    }
}

