/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.platform.rendering.models.blocks;

import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ITileParticleTexture;
import ic2.core.platform.rendering.models.BaseModel;
import net.minecraft.world.level.block.state.BlockState;

public class TileBlockModel
extends BaseModel {
    BlockState state;

    public TileBlockModel(BlockState state) {
        this.state = state;
    }

    @Override
    public void init() {
        if (this.state.m_60734_() instanceof ITileParticleTexture) {
            this.setParticleTexture(((ITileParticleTexture)this.state.m_60734_()).getParticleTexture(this.state));
        } else if (this.state.m_60734_() instanceof IBlockModel) {
            this.setParticleTexture(((IBlockModel)this.state.m_60734_()).getSpriteForParticle(this.state));
        } else {
            this.setParticleTexture(null);
        }
    }

    @Override
    public boolean m_7521_() {
        return true;
    }

    @Override
    public boolean m_7547_() {
        return true;
    }
}

