/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.misc.comparator.types.base.FuelComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseGeneratorTileEntity
extends BaseInventoryTileEntity
implements IEnergySource,
IEUStorage,
ITickListener,
IFuelStorage,
ITileGui,
IWrenchableTile,
IEUProducer,
ITileActivityProvider {
    @NetworkInfo
    public int fuel = 0;
    @NetworkInfo
    public int storage = 0;
    public int maxStorage;
    @NetworkInfo
    public int production;
    public int tier = 1;
    int delayedActive = 0;
    boolean addedToEnet = false;
    boolean chargeSlot = false;
    IAudioSource source = null;

    public BaseGeneratorTileEntity(BlockPos pos, BlockState state, int size) {
        super(pos, state, size);
        this.addGuiFields("fuel", "storage");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
        this.addComparator(new FuelComparator("fuel", ComparatorNames.FUEL, this));
    }

    public ResourceLocation getProcessingSound() {
        return null;
    }

    @Override
    public int getStoredEU() {
        return this.storage;
    }

    @Override
    public int getMaxEU() {
        return this.maxStorage;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public int getFuel() {
        return this.fuel;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.storage = compound.m_128451_("storage");
        this.fuel = compound.m_128451_("fuel");
        this.chargeSlot = compound.m_128471_("charge_slot");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "fuel", this.fuel, 0);
        NBTUtils.putInt(compound, "storage", this.storage, 0);
        NBTUtils.putBoolean(compound, "charge_slot", this.chargeSlot, false);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        this.source = null;
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            ResourceLocation id = this.getProcessingSound();
            if (id == null) {
                return;
            }
            if (this.source == null || !this.source.isValid()) {
                this.source = this.getSounds().createSource(this, id);
            }
            this.playStop(this.source, this.isAudioActive());
        }
    }

    protected boolean isAudioActive() {
        return this.isActive();
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction dir) {
        return true;
    }

    @Override
    public int getSourceTier() {
        return this.tier;
    }

    @Override
    public int getMaxEnergyOutput() {
        return this.production;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.storage, this.production);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.storage -= consumed;
        if (consumed > 0) {
            this.updateGuiField("storage");
        }
    }

    public boolean needsFuel() {
        return this.fuel <= 0 && this.storage + this.production <= this.maxStorage;
    }

    public boolean needsEnergy() {
        return this.fuel > 0 && this.storage + this.production <= this.maxStorage;
    }

    public abstract boolean gainFuel();

    public boolean gainEnergy() {
        if (this.needsEnergy()) {
            this.storage = Math.min(this.maxStorage, this.storage + this.production);
            this.consumeFuel();
            return true;
        }
        return false;
    }

    protected void consumeFuel() {
        --this.fuel;
    }

    @Override
    public void onTick() {
        boolean active;
        if (this.needsFuel() && this.gainFuel()) {
            this.updateGuiField("fuel");
        }
        if (active = this.gainEnergy()) {
            this.updateGuiField("fuel");
            this.updateGuiField("storage");
        }
        if (this.hasDelayedActivity()) {
            this.delayedActive += active ? 1 : -1;
            if (this.clock(this.getDelay())) {
                this.setActive(this.delayedActive > 0);
                this.delayedActive = 0;
            }
        } else if (this.hasAverageActivity()) {
            this.delayedActive += active ? 1 : 0;
            if (this.clock(this.getDelay())) {
                this.setActive(this.delayedActive > 0);
                this.delayedActive = 0;
            }
        } else {
            this.setActive(active);
        }
        if (this.storage > 0 && this.chargeSlot && !((ItemStack)this.inventory.get(0)).m_41619_()) {
            int removed = ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(0), this.storage, this.tier, false, false);
            this.storage -= removed;
            if (removed > 0) {
                this.updateGuiField("storage");
            } else {
                this.chargeSlot = false;
            }
        }
        this.handleComparators();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot == 0) {
            this.chargeSlot = !stack.m_41619_();
        }
    }

    public boolean hasAverageActivity() {
        return false;
    }

    public boolean hasDelayedActivity() {
        return false;
    }

    public int getDelay() {
        return 120;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85 - 0.05 * (double)this.tier;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir.m_122434_().m_122479_() && dir != this.getFacing();
    }
}

