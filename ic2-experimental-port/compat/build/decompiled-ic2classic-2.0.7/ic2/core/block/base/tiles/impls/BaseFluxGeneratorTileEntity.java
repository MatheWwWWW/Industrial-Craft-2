/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.LazyOptional
 *  net.minecraftforge.energy.IEnergyStorage
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.storage.container.FluxGeneratorContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.FeItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class BaseFluxGeneratorTileEntity
extends BaseElectricTileEntity
implements IEnergyStorage,
ITickListener,
ITileGui {
    int config;
    ICache<IEnergyStorage> cache = new CapabilityCache<IEnergyStorage>(this, DirectionList.ALL, ForgeCapabilities.ENERGY);
    LazyOptional<IEnergyStorage> listener = LazyOptional.of(() -> this);

    public BaseFluxGeneratorTileEntity(BlockPos pos, BlockState state, int size, int maxInput, int maxEnergy) {
        super(pos, state, size, maxInput, maxEnergy * IC2.CONFIG.fluxBalance.get());
        this.config = IC2.CONFIG.fluxBalance.get();
        this.addCaches(this.cache);
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotsForSide(DirectionList.UP, 0);
        handler.registerInputFilter(FeItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(FeItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FluxGeneratorContainer(this, player, windowID);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public int getRequestedEnergy() {
        return super.getRequestedEnergy() / this.config;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        return super.acceptEnergy(side, amount * this.config, voltage);
    }

    @Override
    public boolean isHarvestWrenchRequired(Player player) {
        return false;
    }

    @Override
    public int getMaxEU() {
        return super.getMaxEU() / this.config;
    }

    @Override
    public int getStoredEU() {
        return super.getStoredEU() / this.config;
    }

    @Override
    public void onTick() {
        if (this.hasEnergy(1)) {
            int needed;
            IEnergyStorage storage;
            int draining;
            int drained;
            IEnergyStorage cap;
            ItemStack toCharge = (ItemStack)this.inventory.get(0);
            if (!toCharge.m_41619_() && (cap = (IEnergyStorage)toCharge.getCapability(ForgeCapabilities.ENERGY).orElse(null)) != null && cap.canReceive() && (drained = cap.receiveEnergy(Math.min(draining = this.maxInput * this.config * 25, this.energy), true)) > 0) {
                cap.receiveEnergy(this.extractEnergy(draining, false), false);
            }
            if ((storage = this.cache.getHandler(this.getFacing())) != null && (needed = Math.min(storage.getMaxEnergyStored() - storage.getEnergyStored(), this.energy)) > 0) {
                this.useEnergy(storage.receiveEnergy(needed, false));
            }
        }
    }

    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    public int extractEnergy(int maxExtract, boolean simulate) {
        int toExtract = Math.min(this.energy, maxExtract);
        if (!simulate) {
            this.useEnergy(toExtract);
        }
        return toExtract;
    }

    public int getEnergyStored() {
        return this.energy;
    }

    public int getMaxEnergyStored() {
        return this.maxEnergy;
    }

    public boolean canExtract() {
        return true;
    }

    public boolean canReceive() {
        return false;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return side != this.getFacing();
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing();
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        if (this.listener != null) {
            this.listener.invalidate();
            this.listener = LazyOptional.of(() -> this);
            this.addToTick();
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.listener != null) {
            this.listener.invalidate();
            this.listener = null;
        }
        super.onUnloaded(chunk);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return (side == this.getFacing() || side == null) && this.listener != null ? this.listener.cast() : LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }
}

