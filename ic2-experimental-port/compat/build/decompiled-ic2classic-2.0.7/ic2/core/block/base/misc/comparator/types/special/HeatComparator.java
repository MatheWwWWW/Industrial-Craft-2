/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator.types.special;

import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.block.base.misc.readers.IHeatProvider;
import net.minecraft.network.chat.Component;

public class HeatComparator
extends BaseComparator {
    IHeatProvider heat;

    public HeatComparator(String id, Component name, IHeatProvider heat) {
        super(id, name);
        this.heat = heat;
    }

    @Override
    protected int createValue() {
        return HeatComparator.value(this.heat.getHeat(), this.heat.getMaxHeat(), 15);
    }
}

