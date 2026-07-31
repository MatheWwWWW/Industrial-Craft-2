/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.block.generators.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.ISteamReactorChamber;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.impls.BaseNuclearReactorTileEntity;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.fluid.IC2Tank;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class SteamNuclearReactorTileEntity
extends BaseNuclearReactorTileEntity
implements IFluidHandler,
ISteamReactor,
ISimpleFluidSource {
    @NetworkInfo
    private IC2Tank water = new IC2Tank(2000);
    @NetworkInfo
    private IC2Tank steam = new IC2Tank(20000);
    boolean addedToFluidNet = false;
    long timer = 0L;

    public SteamNuclearReactorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("water", "steam");
        this.water.addListener(T -> this.updateGuiField("water"));
        this.steam.addListener(T -> this.updateGuiField("steam"));
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.addComparator(new TankComparator("steam_tank", ComparatorNames.STEAM_TANK, (IFluidTank)this.steam));
        this.addComparator(new TankComparator("water_tank", ComparatorNames.WATER_TANK, (IFluidTank)this.water));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "water", this.water.writeToNBT(new CompoundTag()));
        NBTUtils.put(compound, "steam", this.steam.writeToNBT(new CompoundTag()));
        compound.m_128356_("ticker", this.timer);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.water.readFromNBT(compound.m_128469_("water"));
        this.steam.readFromNBT(compound.m_128469_("steam"));
        this.timer = compound.m_128454_("ticker");
    }

    @Override
    public int getTickRate() {
        return 1;
    }

    @Override
    public boolean isDamageTick() {
        return this.timer++ % 20L == 0L;
    }

    @Override
    public boolean isValidChamber(BlockEntity tile) {
        return tile instanceof ISteamReactorChamber;
    }

    @Override
    protected boolean handleComparators(boolean ignoreTick) {
        return super.handleComparators(true);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STEAM_REACTOR;
    }

    public int getTanks() {
        return 2;
    }

    public FluidStack getFluidInTank(int tank) {
        return (tank == 0 ? this.water : this.steam).getFluid();
    }

    public int getTankCapacity(int tank) {
        return (tank == 0 ? this.water : this.steam).getCapacity();
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return tank == 0 && stack.getFluid() == Fluids.f_76193_;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return resource.getFluid() == Fluids.f_76193_ ? this.water.fill(resource, action) : 0;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.steam.drain(resource, action);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return this.steam.drain(maxDrain, action);
    }

    @Override
    public FluidTank getWaterTank() {
        return this.water;
    }

    @Override
    public FluidTank getSteamTank() {
        return this.steam;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.addedToFluidNet && !this.steam.isEmpty()) {
            this.steam.drain(FluidNet.INSTANCE.sendFluid(this, null, this.steam.drain(20000, IFluidHandler.FluidAction.SIMULATE), 100), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    public void onLoaded() {
        if (!this.addedToFluidNet && this.isSimulating()) {
            this.addedToFluidNet = true;
            FluidNet.INSTANCE.addPipe(this);
        }
        super.onLoaded();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToFluidNet && this.isSimulating()) {
            this.addedToFluidNet = false;
            FluidNet.INSTANCE.removePipe(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }

    @Override
    public boolean isFluidAcceptor() {
        return true;
    }
}

