/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.platform.rendering.features.block;

import ic2.core.platform.rendering.features.block.IBlockModel;
import ic2.core.platform.rendering.features.block.ITileModel;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;

public interface IOptionalTileModel
extends ITileModel,
IBlockModel {
    public boolean shouldTileRender();

    @Override
    default public List<BlockState> getCustomStates() {
        return this.shouldTileRender() ? ITileModel.super.getCustomStates() : ObjectLists.emptyList();
    }

    @Override
    default public List<BlockState> getModelStates() {
        return this.shouldTileRender() ? ObjectLists.emptyList() : IBlockModel.super.getModelStates();
    }
}

