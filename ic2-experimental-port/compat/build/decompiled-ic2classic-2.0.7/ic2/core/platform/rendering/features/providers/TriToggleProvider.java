/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.providers;

import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TriToggleProvider
implements ITextureProvider {
    String mod;
    String path;

    public TriToggleProvider(String mod, String path) {
        this.mod = mod;
        this.path = path;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, Direction side) {
        return IC2Textures.getMappedEntriesBlock(this.mod, this.path).get(((Boolean)state.m_61143_((Property)IC2Properties.ACTIVE) != false ? "active_" : "inactive_") + (side == Direction.SOUTH ? "front" : (side == Direction.NORTH ? "back" : "side")));
    }
}

