/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.machines.logic.crafter;

import ic2.core.inventory.base.INBTSavable;
import net.minecraft.nbt.CompoundTag;

public class WrappedInteger
implements INBTSavable {
    int value;

    public WrappedInteger() {
    }

    public WrappedInteger(int value) {
        this.value = value;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.m_128405_("value", this.value);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.value = nbt.m_128451_("value");
    }

    public int getValue() {
        return this.value;
    }
}

