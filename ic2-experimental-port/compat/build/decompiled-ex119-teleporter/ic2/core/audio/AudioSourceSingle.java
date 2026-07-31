/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.audio;

import ic2.core.audio.AudioEngine;
import ic2.core.audio.AudioSourceClient;
import ic2.core.audio.PositionSpec;
import ic2.core.audio.SingleSound;

public class AudioSourceSingle
extends AudioSourceClient
implements SingleSound {
    private boolean run;
    private boolean cancelled;
    private Runnable onFinish;

    AudioSourceSingle(AudioEngine audioEngine, Object object, PositionSpec positionSpec, String string, boolean bl, boolean bl2, float f) {
        super(audioEngine, null, object, positionSpec, string, bl, bl2, f);
    }

    public boolean isPlaying() {
        if (!this.check()) {
            return false;
        }
        return this.soundSystem.isPlaying(this);
    }

    @Override
    public void remove() {
        this.cancelled = true;
        super.remove();
    }

    @Override
    public void onFinish(Runnable runnable) {
        if (this.run) {
            throw new IllegalStateException("Tried to chain onto a completed sound");
        }
        if (this.cancelled) {
            throw new IllegalStateException("Tried to chain onto a cancelled sound");
        }
        this.onFinish = this.onFinish == null ? runnable : () -> {
            this.onFinish.run();
            runnable.run();
        };
    }

    @Override
    public void cancel() {
        if (this.run) {
            throw new IllegalStateException("Tried to cancel completed sound");
        }
        this.cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    void onFinish() {
        if (this.run) {
            throw new IllegalStateException("Tried to run completed sound");
        }
        if (this.cancelled) {
            return;
        }
        this.remove();
        this.run = true;
        if (this.onFinish != null) {
            this.onFinish.run();
        }
    }

    @Override
    public boolean isComplete() {
        return this.run;
    }
}

