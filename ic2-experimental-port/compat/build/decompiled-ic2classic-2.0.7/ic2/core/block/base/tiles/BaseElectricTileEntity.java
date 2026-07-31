/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles;

import ic2.api.core.IC2Classic;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.INotifiableMachine;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseElectricTileEntity
extends BaseInventoryTileEntity
implements IEnergySink,
IEUStorage,
IWrenchableTile {
    @NetworkInfo
    public int energy;
    @NetworkInfo
    public int maxEnergy;
    @NetworkInfo
    public int maxInput;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int tier;
    public int baseTier;
    int fuelSlot = -1;
    protected boolean redstoneIsFull = true;
    public boolean charge_slot = false;
    CapabilityCache<INotifiableMachine> listeners = new CapabilityCache<INotifiableMachine>(this, DirectionList.ALL, IC2Classic.NOTIFY_CAPABILITY);
    public boolean addedToEnet = false;

    public BaseElectricTileEntity(BlockPos pos, BlockState state, int size, int maxInput, int maxEnergy) {
        super(pos, state, size);
        this.maxInput = maxInput;
        this.maxEnergy = maxEnergy;
        this.baseTier = this.tier = EnergyNet.INSTANCE.getTierFromPower(maxInput);
        this.addGuiFields("energy", "maxEnergy", "tier", "maxInput");
        if (this.supportsNotify()) {
            this.addCaches(this.listeners);
        }
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
    }

    public abstract boolean supportsNotify();

    public void setFuelSlot(int fuelSlot) {
        this.fuelSlot = fuelSlot;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return true;
    }

    @Override
    public int getSinkTier() {
        return this.tier;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > this.maxEnergy || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
            this.handleNonTickComparators();
            if (this.needsUpdateTick() && this instanceof ITickListener && !this.isTicking()) {
                this.addToTick();
            }
        }
        return amount - added;
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    public void setMaxEnergy(int max) {
        this.maxEnergy = max;
        this.updateGuiField("maxEnergy");
    }

    public void setMaxInput(int inputEnergy) {
        this.maxInput = inputEnergy;
        this.tier = EnergyNet.INSTANCE.getTierFromPower(inputEnergy);
        this.updateGuiFields("tier", "maxInput");
    }

    public void setTier(int newTier) {
        this.tier = newTier;
        this.maxInput = EnergyNet.INSTANCE.getPowerFromTier(newTier);
        this.updateGuiFields("tier", "maxInput");
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    public int getMaxInput() {
        return this.maxInput;
    }

    public boolean hasEnergy(int amount) {
        return this.energy >= amount;
    }

    public int useEnergy(int used) {
        int removed = Math.min(used, this.energy);
        if (removed > 0) {
            this.energy -= removed;
            this.updateGuiField("energy");
        }
        return removed;
    }

    public int addEnergy(int amount) {
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return added;
    }

    public boolean handleChargeSlot(int needed) {
        if (this.fuelSlot != -1 && this.energy < needed && this.provideEnergy()) {
            this.updateGuiField("energy");
            return true;
        }
        return false;
    }

    public boolean provideEnergy() {
        if (!this.charge_slot) {
            return false;
        }
        int needed = this.maxEnergy - this.energy;
        if (needed <= 0) {
            return false;
        }
        ItemStack fuel = (ItemStack)this.inventory.get(this.fuelSlot);
        if (fuel.m_41619_()) {
            return false;
        }
        Item item = fuel.m_41720_();
        if (item == Items.f_42451_) {
            if (needed < (this.redstoneIsFull ? this.maxEnergy / 2 : Math.min(500, this.maxEnergy))) {
                return false;
            }
            this.addEnergy(this.redstoneIsFull ? this.maxEnergy : 500);
            fuel.m_41774_(1);
            this.charge_slot = !fuel.m_41619_();
            this.handleNonTickComparators();
            return true;
        }
        if (!this.redstoneIsFull && item == Items.f_42153_) {
            if (needed < Math.min(4500, this.maxEnergy)) {
                return false;
            }
            this.addEnergy(4500);
            fuel.m_41774_(1);
            this.charge_slot = !fuel.m_41619_();
            this.handleNonTickComparators();
            return true;
        }
        if (item == IC2Items.SU_BATTERY) {
            if (needed < Math.min(1000, this.maxEnergy)) {
                return false;
            }
            this.addEnergy(1000);
            fuel.m_41774_(1);
            this.charge_slot = !fuel.m_41619_();
            this.handleNonTickComparators();
            return true;
        }
        int transfer = ElectricItem.MANAGER.discharge(fuel, this.maxEnergy - this.energy, this.getTier(), false, true, false);
        if (transfer > 0) {
            this.addEnergy(transfer);
            return true;
        }
        this.charge_slot = false;
        return false;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot == this.fuelSlot) {
            this.charge_slot = !stack.m_41619_();
        }
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
        super.onUnloaded(chunk);
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.charge_slot = compound.m_128471_("charge_slot");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.putBoolean(compound, "charge_slot", this.charge_slot, false);
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85 - 0.05 * (double)this.baseTier;
    }

    public void notifyListeners() {
        for (Direction dir : this.listeners) {
            INotifiableMachine machine = (INotifiableMachine)this.listeners.getHandler(dir);
            if (machine == null) continue;
            machine.onNotify();
        }
    }
}

