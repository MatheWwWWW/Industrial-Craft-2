/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.ISpeedMachine;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class SpeedComparator
extends BaseComparator {
    ISpeedMachine machine;

    public SpeedComparator(String id, Component name, ISpeedMachine machine) {
        super(id, name);
        this.machine = machine;
    }

    @Override
    protected int createValue() {
        return SpeedComparator.value(this.machine.getSpeed(), this.machine.getMaxSpeed(), 15);
    }
}

