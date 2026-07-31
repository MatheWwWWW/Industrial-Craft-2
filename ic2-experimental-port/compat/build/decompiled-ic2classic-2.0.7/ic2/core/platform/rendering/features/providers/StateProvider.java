/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.platform.rendering.features.providers;

import ic2.core.platform.rendering.features.ITextureProvider;
import java.util.function.IntSupplier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StateProvider
implements ITextureProvider {
    ITextureProvider[] providers;
    IntSupplier decider;

    public StateProvider(IntSupplier decider, ITextureProvider ... providers) {
        this.providers = providers;
        this.decider = decider;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public TextureAtlasSprite getTexture(BlockState state, Direction dir) {
        return this.providers[this.decider.getAsInt()].getTexture(state, dir);
    }
}

