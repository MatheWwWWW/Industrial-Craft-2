/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 */
package ic2.core.block.base.misc.comparator;

import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class ComparatorListener {
    public static final int INVERT = 1;
    public static final int SIGN = 2;
    public static final int POST_INVERT = 4;
    BaseComparator source;
    int baseValue;
    int value;
    int flags;
    Direction dir;

    public ComparatorListener(BaseComparator source) {
        this.source = source;
    }

    public void addListener() {
        this.source.addListener(this);
        this.onComparatorChanged();
    }

    public void onRemoved() {
        this.source.removeListener(this);
    }

    public void onComparatorChanged() {
        this.baseValue = this.value = this.source.getValue(this.dir);
        if ((this.flags & 1) != 0) {
            this.value = 15 - this.value;
        }
        if ((this.flags & 2) != 0) {
            int n = this.value = this.value > 0 ? 15 : 0;
        }
        if ((this.flags & 4) != 0) {
            this.value = 15 - this.value;
        }
    }

    public Component getName() {
        return this.source.getName();
    }

    public int getFlags() {
        return this.flags;
    }

    public int getValue() {
        return this.value;
    }

    public int getBaseValue() {
        return this.baseValue;
    }
}

