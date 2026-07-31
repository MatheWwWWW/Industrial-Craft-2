/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.providers;

import ic2.core.audio.IAudioPosition;
import ic2.core.audio.ISoundProvider;
import net.minecraft.world.level.Level;

public class SimpleAudioProvider
implements ISoundProvider {
    IAudioPosition position;

    public SimpleAudioProvider(IAudioPosition position) {
        this.position = position;
    }

    @Override
    public IAudioPosition getPosition() {
        return this.position;
    }

    @Override
    public boolean isValid(Level world) {
        return this.position.isInSameWorld(world);
    }

    public int hashCode() {
        return this.position.getPosition().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof SimpleAudioProvider) {
            return ((SimpleAudioProvider)obj).position.equals(this.position);
        }
        return false;
    }
}

