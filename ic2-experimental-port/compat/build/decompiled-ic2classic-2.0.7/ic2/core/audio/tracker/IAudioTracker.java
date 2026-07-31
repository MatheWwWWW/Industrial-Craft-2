/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.tracker;

import net.minecraft.world.level.Level;

public interface IAudioTracker {
    public void onTick();

    public boolean isValid(Level var1);
}

