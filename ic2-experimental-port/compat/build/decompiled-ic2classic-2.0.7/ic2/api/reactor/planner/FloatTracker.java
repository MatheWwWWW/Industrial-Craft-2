/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.reactor.planner;

public class FloatTracker {
    private float total = 0.0f;
    private int count = 0;
    private float change = 0.0f;

    public void addChange(float value) {
        this.change += value;
    }

    public void commit() {
        this.total += this.change;
        this.change = 0.0f;
        ++this.count;
    }

    public void reset() {
        this.total = 0.0f;
        this.count = 0;
        this.change = 0.0f;
    }

    public float getAverage() {
        return this.count == 0 ? 0.0f : this.total / (float)this.count;
    }
}

