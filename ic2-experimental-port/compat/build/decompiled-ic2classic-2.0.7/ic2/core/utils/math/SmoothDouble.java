/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.math;

public class SmoothDouble {
    double value;
    double nextValue;

    public SmoothDouble() {
    }

    public SmoothDouble(double base) {
        this.set(base);
    }

    public void setNext(double value) {
        this.nextValue = value;
    }

    public void set(double value) {
        this.value = value;
        this.nextValue = value;
    }

    public void update(double changeRate) {
        if (this.value > this.nextValue) {
            this.value -= changeRate;
        } else if (this.value < this.nextValue) {
            this.value += changeRate;
        }
    }

    public double getValue() {
        return this.value;
    }
}

