/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.AbstractMinecart
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.IEnergyStorage;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.misc.comparator.types.special.MinecartComparator;
import ic2.core.block.base.misc.readers.ILoader;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.storage.components.UnloaderComponent;
import ic2.core.block.storage.container.UnloaderContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class BaseElectricUnloaderTileEntity
extends BaseInventoryTileEntity
implements ITickListener,
ILoader,
ITileGui,
IRedstoneProvider,
IEUStorage,
IMultiEnergySource,
IWrenchableTile {
    IEnergyStorage lastMinecart = null;
    int lastTransfer = 0;
    @NetworkInfo
    public byte redstoneMode = 0;
    boolean lastRedstone = false;
    boolean addedToEnet = false;
    boolean chargeBattery = false;
    @NetworkInfo
    public int energy = 0;
    public final int maxEnergy;
    public final int maxOutput;
    public final int maxPackets;
    public final int tier;

    public BaseElectricUnloaderTileEntity(BlockPos pos, BlockState state, int maxEnergy, int maxOutput, int maxPackets) {
        super(pos, state, 1);
        this.maxEnergy = maxEnergy;
        this.maxOutput = maxOutput;
        this.maxPackets = maxPackets;
        this.tier = EnergyNet.INSTANCE.getTierFromPower(maxOutput);
        this.addGuiFields("redstoneMode", "energy");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
        this.addComparator(new MinecartComparator("minecarts", ComparatorNames.HAS_MINECART, this, MinecartComparator.InfoType.HAS_MINECART));
        this.addComparator(new MinecartComparator("has_transfer", ComparatorNames.HAS_TRANSFERRED, this, MinecartComparator.InfoType.HAS_TRANSFER));
        this.addComparator(new MinecartComparator("minecart_energy", ComparatorNames.MINECART_ENERGY, this, MinecartComparator.InfoType.MINECART_ENERGY));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 0);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.chargeBattery = compound.m_128471_("charge_slot");
        this.redstoneMode = compound.m_128445_("mode");
        this.lastRedstone = compound.m_128471_("state");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.putBoolean(compound, "charge_slot", this.chargeBattery, false);
        NBTUtils.putByte(compound, "mode", (int)this.redstoneMode, 0);
        NBTUtils.putBoolean(compound, "state", this.lastRedstone, false);
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getTier() {
        return this.tier;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public int getSourceTier() {
        return this.getTier();
    }

    @Override
    public int getMaxEnergyOutput() {
        return this.maxOutput;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.maxOutput, this.energy);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy -= consumed;
        this.updateGuiField("energy");
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public boolean hasMultiplePackets() {
        return this.maxPackets > 1;
    }

    @Override
    public int getPacketCount() {
        return this.maxPackets;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot == 0) {
            this.chargeBattery = !stack.m_41619_();
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
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new UnloaderContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        boolean nextRedstone;
        if (this.chargeBattery && this.energy > 0 && !((ItemStack)this.inventory.get(0)).m_41619_()) {
            int removed = ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(0), this.energy, this.getTier(), false, false);
            this.energy -= removed;
            if (removed > 0) {
                this.updateGuiField("energy");
            } else {
                this.chargeBattery = false;
            }
        }
        if (this.lastMinecart == null && this.invClock(20)) {
            return;
        }
        this.lastMinecart = null;
        this.lastTransfer = 0;
        IEnergyStorage storage = this.findMinecart();
        if (storage != null) {
            this.lastMinecart = storage;
            this.lastTransfer += this.lastMinecart.drawEnergy(this.addEnergy(Math.min(this.lastMinecart.getStoredEU(), this.maxOutput * 10)));
        }
        if ((nextRedstone = this.getRedstoneState()) != this.lastRedstone) {
            this.lastRedstone = nextRedstone;
            this.notifyChanges(true, DirectionList.ALL);
        }
        this.handleComparators();
    }

    public IEnergyStorage findMinecart() {
        List list = this.f_58857_.m_45976_(AbstractMinecart.class, new AABB(this.m_58899_().m_121945_(this.getFacing())));
        if (list.isEmpty()) {
            return null;
        }
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            Object e = list.get(i);
            if (!(e instanceof IEnergyStorage)) continue;
            IEnergyStorage storage = (IEnergyStorage)e;
            return storage;
        }
        return null;
    }

    protected int addEnergy(int toAdd) {
        int added = Math.min(this.maxEnergy - this.energy, toAdd);
        this.energy += added;
        return added;
    }

    public boolean getRedstoneState() {
        switch (this.redstoneMode) {
            case 0: {
                return false;
            }
            case 1: {
                return this.hasMinecart() && this.lastMinecart.getStoredEU() <= 0;
            }
            case 2: {
                return this.hasMinecart() && this.getTransferred() <= 0;
            }
            case 3: {
                return this.hasMinecart() && this.lastMinecart.getChargeLevel() >= 0.5;
            }
            case 4: {
                return this.hasMinecart() && this.lastMinecart.getChargeLevel() <= 0.5;
            }
            case 5: {
                return this.energy >= this.maxEnergy;
            }
        }
        return false;
    }

    @Override
    public boolean hasMinecart() {
        return this.lastMinecart != null;
    }

    @Override
    public int getTransferred() {
        return this.lastTransfer;
    }

    @Override
    public IEnergyStorage getMinecart() {
        return this.lastMinecart;
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return side == this.getFacing().m_122424_() && this.lastRedstone ? 15 : 0;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.redstoneMode = (byte)((this.redstoneMode + 1) % 6);
            boolean newRedstone = this.getRedstoneState();
            if (this.lastRedstone != newRedstone) {
                this.lastRedstone = newRedstone;
                this.notifyChanges(true, DirectionList.ALL);
            }
            this.updateGuiField("redstoneMode");
            entity.m_5661_(UnloaderComponent.NAMES[this.redstoneMode], false);
        }
    }
}

