/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.audio;

import ic2.core.audio.AudioSource;
import ic2.core.audio.FutureSound;
import ic2.core.audio.PositionSpec;

public class AudioManager {
    public void initialize() {
    }

    public void playOnce(Object obj, String soundFile) {
    }

    public String playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {
        return null;
    }

    public void chainSource(String source, FutureSound onFinish) {
    }

    public void removeSource(String source) {
    }

    public void removeSources(Object obj) {
    }

    public AudioSource createSource(Object obj, String initialSoundFile) {
        return null;
    }

    public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
        return null;
    }

    public void onTick() {
    }

    public float getMasterVolume() {
        return 0.0f;
    }

    public float getDefaultVolume() {
        return 0.0f;
    }

    protected boolean valid() {
        return false;
    }
}

