/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.readers.IPumpTile;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.lv.ElectricWoodGassifierContainer;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.fluid.ExtractionTank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.TagFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class WoodGassifierTileEntity
extends BaseElectricTileEntity
implements ITickListener,
IProgressMachine,
ITileGui,
IPumpTile,
ISimpleFluidSource,
ITileActivityProvider,
IClickable {
    @NetworkInfo
    protected ExtractionTank tank = new ExtractionTank(18000);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    int progress = 0;
    boolean addedToFluidNet = false;

    public WoodGassifierTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5, 32, 500);
        this.setFuelSlot(0);
        this.addGuiFields("tank", "progress");
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, (IFluidTank)this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1, 3);
        handler.registerSlotAccess(AccessRule.EXPORT, 2, 4);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1, 3);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2, 4);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new TagFilter((TagKey<Item>)ItemTags.f_13182_), 1);
        handler.registerInputFilter(FluidFilter.createFillFilter(IC2Fluids.WOOD_GAS), 3);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
        handler.registerNamedSlot(SlotType.CONTAINER, 3);
        handler.registerNamedSlot(SlotType.EXTRA_OUTPUT, 4);
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.WOOD_GASSIFIER_ELECTRIC;
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
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.fillContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ElectricWoodGassifierContainer(this, player, windowID);
    }

    @Override
    public int getPumpProgress() {
        return this.tank.getFluidAmount();
    }

    @Override
    public int getPumpMaxProgress() {
        return this.tank.getCapacity();
    }

    @Override
    public float getProgress() {
        return this.progress == 0 ? 0.0f : this.getMaxProgress() - (float)this.progress;
    }

    @Override
    public float getMaxProgress() {
        return 80.0f;
    }

    @Override
    public void onTick() {
        this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        if (this.hasEnergy(1) && (this.progress > 0 || this.canRun())) {
            if (this.progress == 0 && this.canRun()) {
                this.progress = (int)this.getMaxProgress();
                ((ItemStack)this.inventory.get(1)).m_41774_(1);
                this.updateGuiField("progress");
            }
            if (this.progress > 0) {
                --this.progress;
                this.tank.fillInternal(new FluidStack(IC2Fluids.WOOD_GAS, 10), IFluidHandler.FluidAction.EXECUTE);
                if (this.progress <= 0) {
                    this.progress = 0;
                    this.tank.fillInternal(new FluidStack(IC2Fluids.WOOD_GAS, 10), IFluidHandler.FluidAction.EXECUTE);
                    this.setOrGrow(2, new ItemStack((ItemLike)Items.f_42414_), false);
                }
                this.supportsNotify();
                this.updateGuiField("tank");
                this.updateGuiField("progress");
            }
            this.useEnergy(1);
            this.setActive(true);
        } else {
            this.setActive(false);
        }
        if (this.clock(10) && this.canFill()) {
            FluidHelper.fillContainers(this, 3, 4, (IFluidHandler)this.tank);
        }
        if (this.clock(10) && this.tank.getFluidAmount() > 0) {
            this.tank.drain(FluidNet.INSTANCE.sendFluid(this, null, this.tank.getFluid(), 25), IFluidHandler.FluidAction.EXECUTE);
        }
        this.handleComparators();
    }

    public boolean canRun() {
        if (this.tank.getFluidAmount() > 17000) {
            return false;
        }
        ItemStack input = (ItemStack)this.inventory.get(1);
        return !input.m_41619_() && input.m_204117_(ItemTags.f_13182_) && this.hasOutputRoom();
    }

    public boolean hasOutputRoom() {
        return ((ItemStack)this.inventory.get(2)).m_41619_() || ((ItemStack)this.inventory.get(2)).m_41720_() == Items.f_42414_ && ((ItemStack)this.inventory.get(2)).m_41613_() < ((ItemStack)this.inventory.get(2)).m_41741_();
    }

    public boolean canFill() {
        return this.tank.getFluidAmount() >= 1000 && !((ItemStack)this.inventory.get(3)).m_41619_();
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToFluidNet) {
            this.addedToFluidNet = true;
            FluidNet.INSTANCE.addPipe(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToFluidNet) {
            this.addedToFluidNet = false;
            FluidNet.INSTANCE.removePipe(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }
}

