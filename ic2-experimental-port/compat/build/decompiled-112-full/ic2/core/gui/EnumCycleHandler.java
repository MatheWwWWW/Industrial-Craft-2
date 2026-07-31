/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.gui.CycleHandler;
import ic2.core.gui.INumericValueHandler;
import java.util.Arrays;
import java.util.Collections;

public class EnumCycleHandler<E extends Enum<E>>
extends CycleHandler {
    protected final E[] set;

    public EnumCycleHandler(E[] set) {
        this((Enum[])set, (Enum)set[0]);
    }

    public EnumCycleHandler(E[] set, E start) {
        this(0, 0, 0, 0, 0, false, (Enum[])set, (Enum)start);
    }

    public EnumCycleHandler(int uS, int vS, int uE, int vE, int overlayStep, boolean vertical, E[] set, E start) {
        super(uS, vS, uE, vE, overlayStep, vertical, set.length, new INumericValueHandler((Enum[])set, (Enum)start){
            private E currentValue;
            private final int[] index;
            final /* synthetic */ Enum[] val$set;
            final /* synthetic */ Enum val$start;
            {
                this.val$set = enumArray;
                this.val$start = enum_;
                this.currentValue = this.val$start;
                this.index = this.makeIndexMap();
            }

            private int[] makeIndexMap() {
                int[] ret = new int[Collections.max(Arrays.asList(this.val$set)).ordinal() + 1];
                for (int index = 0; index < this.val$set.length; ++index) {
                    ret[this.val$set[index].ordinal()] = index;
                }
                return ret;
            }

            @Override
            public void onChange(int value) {
                assert (value >= 0 && value < this.val$set.length);
                this.currentValue = this.val$set[value];
            }

            @Override
            public int getValue() {
                return this.index[((Enum)this.currentValue).ordinal()];
            }
        });
        this.set = set;
    }

    public E getCurrentValue() {
        return this.set[this.getValue()];
    }
}

