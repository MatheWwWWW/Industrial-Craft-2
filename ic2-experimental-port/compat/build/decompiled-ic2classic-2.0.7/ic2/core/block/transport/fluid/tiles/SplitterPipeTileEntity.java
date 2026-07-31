/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SplitterPipeTileEntity
extends PipeTileEntity
implements ITickListener,
IRedstoneListener,
ITileActivityProvider {
    boolean dirty = true;

    public SplitterPipeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.SPLITTER_PIPE;
    }

    @Override
    public boolean needsUpdateTick() {
        return this.isSimulating();
    }

    @Override
    public boolean canConnect(Direction dir, IFluidPipe pipe) {
        return this.cache.isAdded() && super.canConnect(dir, pipe);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public int getConnectivity() {
        return this.isActive() ? this.anchors : super.getConnectivity();
    }

    @Override
    public int getEmitters() {
        return this.isActive() ? 0 : super.getEmitters();
    }

    @Override
    public int getValidDirections() {
        return this.isActive() ? 0 : super.getValidDirections();
    }

    @Override
    public void onLoaded() {
        this.cache.setAdded(this.isActive());
        super.onLoaded();
        if (this.isActive()) {
            this.cache.setAdded(false);
        }
    }

    @Override
    public void onTick() {
        if (this.dirty) {
            this.dirty = false;
            if (this.m_58904_().m_46753_(this.m_58899_()) == this.cache.isAdded()) {
                if (this.cache.isAdded()) {
                    this.cache.remove();
                } else {
                    this.cache.add();
                }
            }
            this.setActive(!this.cache.isAdded());
        }
        super.onTick();
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        this.dirty = true;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }
}

