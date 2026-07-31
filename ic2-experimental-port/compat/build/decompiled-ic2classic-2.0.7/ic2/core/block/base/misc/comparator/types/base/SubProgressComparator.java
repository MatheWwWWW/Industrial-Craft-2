/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.ISubProgressMachine;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class SubProgressComparator
extends BaseComparator {
    ISubProgressMachine machine;

    public SubProgressComparator(String id, Component name, ISubProgressMachine machine) {
        super(id, name);
        this.machine = machine;
    }

    @Override
    protected int createValue() {
        return SubProgressComparator.value(this.machine.getSubProgress(), this.machine.getMaxSubProgress(), 15);
    }
}

