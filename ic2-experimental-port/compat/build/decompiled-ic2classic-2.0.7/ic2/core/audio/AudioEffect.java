/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.audio;

public class AudioEffect {
    final float radius;
    final float effect;

    public AudioEffect(float radius, float effect) {
        this.radius = radius;
        this.effect = effect;
    }

    public float getEffect() {
        return this.effect;
    }

    public float getRadius() {
        return this.radius;
    }
}

