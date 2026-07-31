/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.audio;

import ic2.core.audio.IAudioPosition;
import net.minecraft.world.entity.player.Player;

public interface IAudioSource {
    public boolean isValid();

    public boolean isEnabled();

    public boolean isPlaying();

    public boolean isPaused();

    public boolean isPriority();

    public float getVolume();

    public float getRealVolume();

    public float getPitch();

    public void setVolume(float var1);

    public void setPitch(float var1);

    default public void playStop(boolean play) {
        if (play) {
            this.play();
        } else {
            this.stop();
        }
    }

    public void play();

    public void pause();

    public void stop();

    public void remove();

    public void enable();

    public void disable();

    public IAudioPosition getPosition();

    public void updatePosition();

    public void updateVolume(Player var1);
}

