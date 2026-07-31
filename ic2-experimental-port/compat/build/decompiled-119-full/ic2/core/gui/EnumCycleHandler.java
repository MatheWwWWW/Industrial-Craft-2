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

    public EnumCycleHandler(E[] EArray) {
        this((Enum[])EArray, (Enum)EArray[0]);
    }

    public EnumCycleHandler(E[] EArray, E e) {
        this(0, 0, 0, 0, 0, false, (Enum[])EArray, (Enum)e);
    }

    public EnumCycleHandler(int n, int n2, int n3, int n4, int n5, boolean bl, E[] EArray, E e) {
        super(n, n2, n3, n4, n5, bl, EArray.length, new INumericValueHandler(){
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
                int[] nArray = new int[Collections.max(Arrays.asList(this.val$set)).ordinal() + 1];
                for (int i = 0; i < this.val$set.length; ++i) {
                    nArray[this.val$set[i].ordinal()] = i;
                }
                return nArray;
            }

            @Override
            public void onChange(int n) {
                assert (n >= 0 && n < this.val$set.length);
                this.currentValue = this.val$set[n];
            }

            @Override
            public int getValue() {
                return this.index[((Enum)this.currentValue).ordinal()];
            }
        });
        this.set = EArray;
    }

    public E getCurrentValue() {
        return this.set[this.getValue()];
    }
}

