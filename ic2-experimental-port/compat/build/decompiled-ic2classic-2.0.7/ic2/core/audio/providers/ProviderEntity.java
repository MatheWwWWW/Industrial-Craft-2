/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 */
package ic2.core.audio.providers;

import ic2.core.audio.AudioManager;
import ic2.core.audio.IAudioPosition;
import ic2.core.audio.ISoundProvider;
import ic2.core.audio.providers.SimplePosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ProviderEntity
implements ISoundProvider {
    Entity entity;
    AudioManager.SoundType type;

    public ProviderEntity(Entity entity, AudioManager.SoundType type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public IAudioPosition getPosition() {
        return SimplePosition.getFrom(this.entity, this.type);
    }

    @Override
    public boolean isValid(Level world) {
        return this.entity.m_6084_() && IAudioPosition.isInSameWorld(world, this.entity.m_20193_());
    }

    public int hashCode() {
        return this.entity.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ProviderEntity) {
            return ((ProviderEntity)obj).entity == this.entity;
        }
        return false;
    }
}

