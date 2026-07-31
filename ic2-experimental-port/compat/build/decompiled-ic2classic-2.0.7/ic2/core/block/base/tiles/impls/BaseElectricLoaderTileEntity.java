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
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.IEnergyStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.MinecartComparator;
import ic2.core.block.base.misc.readers.ILoader;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.storage.components.LoaderComponent;
import ic2.core.block.storage.container.LoaderContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ArrayOrFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class BaseElectricLoaderTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ILoader,
ITileGui,
IRedstoneProvider {
    IEnergyStorage lastMinecart = null;
    int lastTransfer = 0;
    @NetworkInfo
    public byte redstoneMode = 0;
    boolean lastRedstone = false;

    public BaseElectricLoaderTileEntity(BlockPos pos, BlockState state, int maxInput, int maxEnergy) {
        super(pos, state, 1, maxInput, maxEnergy);
        this.setFuelSlot(0);
        this.addGuiFields("redstoneMode");
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
        handler.registerInputFilter(new ArrayOrFilter(ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER, new SimpleFilter((ItemLike)Items.f_42451_), new SimpleFilter((ItemLike)Items.f_42153_), new SimpleFilter((ItemLike)IC2Items.SU_BATTERY)), 1);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.redstoneMode = compound.m_128445_("mode");
        this.lastRedstone = compound.m_128471_("state");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "mode", (int)this.redstoneMode, 0);
        NBTUtils.putBoolean(compound, "state", this.lastRedstone, false);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new LoaderContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        boolean nextRedstone;
        this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        if (this.lastMinecart == null && this.invClock(20)) {
            return;
        }
        this.lastMinecart = null;
        this.lastTransfer = 0;
        IEnergyStorage storage = this.findMinecart();
        if (storage != null) {
            this.lastMinecart = storage;
            this.lastTransfer += this.useEnergy(this.lastMinecart.addEnergy(Math.min(this.energy, this.getMaxInput() * 10)));
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

    public boolean getRedstoneState() {
        switch (this.redstoneMode) {
            case 0: {
                return false;
            }
            case 1: {
                return this.hasMinecart() && this.lastMinecart.getStoredEU() >= this.lastMinecart.getMaxEU();
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
                return this.energy <= 0;
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
            entity.m_5661_(LoaderComponent.NAMES[this.redstoneMode], false);
        }
    }
}

