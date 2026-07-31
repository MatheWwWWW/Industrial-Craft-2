/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.gui.screen;

public class SmoothFloat {
    protected float value;
    protected float target;
    protected float agility;

    public SmoothFloat(float agility) {
        this.agility = agility;
    }

    public SmoothFloat(float value, float agility) {
        this.value = value;
        this.target = value;
        this.agility = agility;
    }

    public boolean isDone() {
        return Math.abs(this.target - this.value) <= 0.5f;
    }

    public void update(float delta) {
        this.value += (this.target - this.value) * this.agility * delta;
    }

    public void setTarget(float value) {
        this.target = value;
    }

    public void addTarget(float value) {
        this.target += value;
    }

    public void forceFinish() {
        this.value = this.target;
    }

    public float getValue() {
        return this.value;
    }

    public float getTarget() {
        return this.target;
    }
}

