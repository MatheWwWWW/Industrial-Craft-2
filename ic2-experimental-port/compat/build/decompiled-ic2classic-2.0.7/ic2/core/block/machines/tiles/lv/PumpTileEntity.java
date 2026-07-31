/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidActionResult
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IPumpTile;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.PumpComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.lv.PumpContainer;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.fluid.ExtractionTank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class PumpTileEntity
extends BaseElectricTileEntity
implements IPumpTile,
ITickListener,
ITileGui,
ISimpleFluidSource,
ITileActivityProvider,
IClickable {
    @NetworkInfo
    public ExtractionTank tank = new ExtractionTank(4000);
    @NetworkInfo
    public int progress = 0;
    int delay = 0;
    ICache<IFluidHandler> fluid_cache = new CapabilityCache<IFluidHandler>(this, DirectionList.DOWN.invert(), ForgeCapabilities.FLUID_HANDLER);
    ICache<IItemHandler> inventory_cache = new CapabilityCache<IItemHandler>(this, DirectionList.DOWN.invert(), ForgeCapabilities.ITEM_HANDLER);
    IAudioSource source;
    boolean addedToFluidNet = false;

    public PumpTileEntity(BlockPos pos, BlockState state) {
        this(pos, state, 32, 200);
    }

    public PumpTileEntity(BlockPos pos, BlockState state, int maxInput, int maxEnergy) {
        super(pos, state, 3, maxInput, maxEnergy);
        this.setFuelSlot(0);
        this.addGuiFields("progress", "tank");
        this.tank.addListener(T -> this.updateGuiField("tank"));
        this.addCaches(this.fluid_cache, this.inventory_cache);
        this.addComparator(new PumpComparator("pump", ComparatorNames.PUMP, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.DOWN.invert());
        handler.registerBlockAccess(DirectionList.DOWN.invert(), AccessRule.BOTH);
        handler.registerBlockAccess(DirectionList.DOWN, AccessRule.DISABLED);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.HORIZONTAL, 0);
        handler.registerSlotsForSide(DirectionList.UP, 1);
        handler.registerSlotsForSide(DirectionList.HORIZONTAL, 2);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(FluidFilter.EMPTY_CONTAINER, 1);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.fillContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag()));
        compound.m_128376_("progress", (short)this.progress);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.readFromNBT(compound.m_128469_("tank"));
        this.progress = compound.m_128451_("progress");
    }

    @Override
    public void onTick() {
        int drained;
        this.handleChargeSlot((int)((float)this.maxEnergy * 0.9f));
        if (this.delay > 0) {
            --this.delay;
        }
        if (this.progress < this.getPumpMaxProgress() && this.hasEnergy(this.getPumpCost())) {
            ++this.progress;
            this.useEnergy(this.getPumpCost());
            this.updateGuiField("progress");
        }
        if (this.delay <= 0 && this.progress >= this.getPumpMaxProgress() && this.tank.getFluidAmount() <= 3000) {
            FluidStack stack = this.getFluidBlockBelow(false);
            if (!stack.isEmpty() && this.tank.fillInternal(stack, IFluidHandler.FluidAction.SIMULATE) == stack.getAmount()) {
                this.tank.fillInternal(stack, IFluidHandler.FluidAction.EXECUTE);
                this.getFluidBlockBelow(true);
                this.progress = 0;
                this.updateGuiField("progress");
            } else {
                this.delay = 20;
            }
        }
        if (this.clock(5)) {
            if (this.tank.getFluidAmount() > 0) {
                this.drainStorage();
            }
            if (!((ItemStack)this.inventory.get(2)).m_41619_()) {
                this.drainContainers();
            }
        }
        if (this.tank.getFluidAmount() > 0 && (drained = FluidNet.INSTANCE.sendFluid(this, null, this.tank.getFluid(), 100)) > 0) {
            this.tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);
        }
        if (this.isActive() == this.progress >= this.getPumpMaxProgress() && this.energy > 0) {
            this.setActive(!this.isActive());
        }
        this.handleComparators();
    }

    protected void drainContainers() {
        InventoryHandler handler = this.getInventoryHandler();
        for (Direction dir : this.inventory_cache) {
            int added;
            IItemTransporter transporter;
            if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (transporter = TransporterManager.getTransporter(this.inventory_cache.getHandler(dir))) == null || (added = transporter.addItem((ItemStack)this.inventory.get(2), dir.m_122424_(), false)) <= 0) continue;
            ((ItemStack)this.inventory.get(2)).m_41774_(added);
            if (!((ItemStack)this.inventory.get(2)).m_41619_()) continue;
            return;
        }
    }

    protected void drainStorage() {
        FluidActionResult result;
        if (!((ItemStack)this.inventory.get(1)).m_41619_() && (result = FluidUtil.tryFillContainer((ItemStack)((ItemStack)this.inventory.get(1)), (IFluidHandler)this.tank, (int)Integer.MAX_VALUE, null, (boolean)false)).isSuccess()) {
            result = FluidUtil.tryFillContainer((ItemStack)((ItemStack)this.inventory.get(1)), (IFluidHandler)this.tank, (int)Integer.MAX_VALUE, null, (boolean)true);
            if (!result.isSuccess()) {
                return;
            }
            this.setOrGrow(2, result.getResult(), true);
            ((ItemStack)this.inventory.get(1)).m_41774_(1);
            return;
        }
        InventoryHandler handler = this.getInventoryHandler();
        for (Direction dir : this.fluid_cache) {
            IFluidHandler entry;
            if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (entry = this.fluid_cache.getHandler(dir)) == null) continue;
            this.tank.drain(entry.fill(new FluidStack(this.tank.getFluid(), Math.min(this.tank.getFluidAmount(), 1000)), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            if (this.tank.getFluidAmount() > 0) continue;
            return;
        }
    }

    public InteractionResult pumpExternal(FluidStack fluid, boolean doPump) {
        if (fluid.isEmpty() || this.tank.fillInternal(fluid, IFluidHandler.FluidAction.SIMULATE) != fluid.getAmount()) {
            return InteractionResult.FAIL;
        }
        if (this.getPumpProgress() < this.getPumpMaxProgress()) {
            return InteractionResult.PASS;
        }
        if (doPump) {
            this.tank.fillInternal(fluid, IFluidHandler.FluidAction.EXECUTE);
            this.progress = 0;
            this.updateGuiField("progress");
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return true;
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToFluidNet) {
            FluidNet.INSTANCE.addPipe(this);
            this.addedToFluidNet = true;
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToFluidNet) {
            FluidNet.INSTANCE.removePipe(this);
            this.addedToFluidNet = false;
        }
        this.source = null;
        IC2.AUDIO.removeSource(this);
        super.onUnloaded(chunk);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            if (this.source == null || !this.source.isValid()) {
                this.source = IC2.AUDIO.createSource(this, IC2Sounds.PUMP_PROCESSING);
                if (this.source == null) {
                    return;
                }
            }
            this.source.playStop(this.isActive() && this.hasEnergy(1));
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PUMP;
    }

    @Override
    public int getPumpProgress() {
        return this.progress;
    }

    @Override
    public int getPumpMaxProgress() {
        return 120;
    }

    public int getPumpCost() {
        return 1;
    }

    public FluidStack getFluidBlockBelow(boolean remove) {
        Block block;
        BlockPos pos = this.m_58899_().m_7495_();
        BlockState state = this.f_58857_.m_8055_(pos);
        FluidState fluid = state.m_60819_();
        if (fluid.m_76170_() && (block = state.m_60734_()) instanceof BucketPickup) {
            BucketPickup bucket = (BucketPickup)block;
            if (remove) {
                bucket.m_142598_((LevelAccessor)this.m_58904_(), pos, state);
            }
            return new FluidStack(fluid.m_76152_(), 1000);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new PumpContainer(this, player, windowID);
    }
}

