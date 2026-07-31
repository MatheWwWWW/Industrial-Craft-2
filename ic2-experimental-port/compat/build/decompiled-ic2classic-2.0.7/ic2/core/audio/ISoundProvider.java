/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio;

import ic2.core.audio.IAudioPosition;
import net.minecraft.world.level.Level;

public interface ISoundProvider {
    public IAudioPosition getPosition();

    public boolean isValid(Level var1);
}

