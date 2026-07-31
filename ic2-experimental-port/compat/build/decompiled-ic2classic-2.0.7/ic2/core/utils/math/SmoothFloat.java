/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.math;

public class SmoothFloat {
    float value;
    float nextValue;

    public SmoothFloat() {
    }

    public SmoothFloat(float base) {
        this.set(base);
    }

    public void setNext(float value) {
        this.nextValue = value;
    }

    public void set(float value) {
        this.value = value;
        this.nextValue = value;
    }

    public void update(float changeRate) {
        if (this.value > this.nextValue) {
            this.value -= changeRate;
        } else if (this.value < this.nextValue) {
            this.value += changeRate;
        }
    }

    public float getValue() {
        return this.value;
    }
}

