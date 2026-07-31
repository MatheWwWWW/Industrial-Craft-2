/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.multi.tiles;

import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IFuelBoiler;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.generators.tiles.FuelBoilerTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FuelBoilerLinkTileEntity
extends BaseLinkingTileEntity
implements ITickListener,
ILocation,
IFuelBoiler {
    public ICache<IFluidHandler> fluidCache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, T -> !(T instanceof FuelBoilerLinkTileEntity) && !(T instanceof FuelBoilerTileEntity));

    public FuelBoilerLinkTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.fluidCache);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FUEL_BOILER_STRUCTURE;
    }

    @Override
    public boolean needsUpdateTick() {
        return this.isSimulating() && !this.fluidCache.isEmpty();
    }

    @Override
    public void onTick() {
        BlockEntity master = this.getMaster();
        if (master instanceof FuelBoilerTileEntity) {
            ((FuelBoilerTileEntity)master).drainTanks(this.fluidCache);
        }
    }

    @Override
    protected void onCachesUpdated() {
        if (!this.fluidCache.isEmpty() && this.getMaster() != null) {
            if (this.getState() <= 3 && this.getState() >= 1) {
                this.addToTick();
            }
            return;
        }
        this.removeFromTick();
    }
}

