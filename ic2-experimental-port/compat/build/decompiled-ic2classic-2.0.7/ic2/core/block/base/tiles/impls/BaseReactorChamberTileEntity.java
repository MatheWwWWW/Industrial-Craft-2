/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.reactor.IChamberReactor;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.features.redstone.IComparable;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.utils.helpers.capabilities.CapabilityWrapperCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BaseReactorChamberTileEntity
extends BaseTileEntity
implements IReactorChamber,
IWrenchRemovable {
    CapabilityWrapperCache wrapper = new CapabilityWrapperCache();

    public BaseReactorChamberTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.75;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return this.wrapper.getCapability(cap, side);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        IReactor reactor = this.getReactor();
        if (reactor instanceof IChamberReactor) {
            ((IChamberReactor)reactor).refreshChambers();
        }
        if (reactor instanceof ICapabilityProvider) {
            this.wrapper.setOwner((ICapabilityProvider)reactor);
            this.notifyChanges(false, DirectionList.ALL);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        IReactor reactor;
        if (this.isSimulating()) {
            this.wrapper.clearOwner();
        }
        if ((reactor = this.getReactor()) instanceof IChamberReactor) {
            ((IChamberReactor)reactor).refreshChambers();
        }
        super.onUnloaded(chunk);
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        IReactor reactor = this.getReactor();
        if (reactor == null) {
            return;
        }
        reactor.getWorldObj().m_46586_(reactor.getPosition(), this.m_58900_().m_60734_(), from);
    }

    @Override
    public ComparatorManager getManager() {
        IReactor reactor = this.getReactor();
        return reactor instanceof IComparable ? ((IComparable)((Object)reactor)).getManager() : null;
    }
}

