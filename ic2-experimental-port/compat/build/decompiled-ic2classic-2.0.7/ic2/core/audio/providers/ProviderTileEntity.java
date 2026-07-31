/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.audio.providers;

import ic2.core.audio.IAudioPosition;
import ic2.core.audio.ISoundProvider;
import ic2.core.audio.providers.SimplePosition;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ProviderTileEntity
implements ISoundProvider {
    BlockEntity tile;

    public ProviderTileEntity(BlockEntity tile) {
        this.tile = tile;
    }

    @Override
    public IAudioPosition getPosition() {
        return new SimplePosition(this.tile.m_58904_(), this.tile.m_58899_());
    }

    @Override
    public boolean isValid(Level world) {
        return !this.tile.m_58901_() && IAudioPosition.isInSameWorld(world, this.tile.m_58904_());
    }

    public int hashCode() {
        return this.tile.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ProviderTileEntity) {
            return ((ProviderTileEntity)obj).tile.equals(this.tile);
        }
        return false;
    }
}

