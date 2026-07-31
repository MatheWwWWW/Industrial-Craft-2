/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.api.tiles.readers.IProgressMachine;
import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;

public class ProgressComparator
extends BaseComparator {
    IProgressMachine machine;

    public ProgressComparator(String id, Component name, IProgressMachine machine) {
        super(id, name);
        this.machine = machine;
    }

    @Override
    protected int createValue() {
        return ProgressComparator.value(this.machine.getProgress(), this.machine.getMaxProgress(), 15);
    }
}

