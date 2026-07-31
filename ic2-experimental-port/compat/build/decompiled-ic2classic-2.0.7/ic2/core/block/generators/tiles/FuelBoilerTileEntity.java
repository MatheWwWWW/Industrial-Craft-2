/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.items.IFuelableItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IFuelBoiler;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.features.multiblock.IMultiBlockClickable;
import ic2.core.block.base.misc.BucketFiller;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.FuelComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.misc.comparator.types.special.HeatComparator;
import ic2.core.block.base.misc.readers.IHeatProvider;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.generators.containers.FuelBoilerContainer;
import ic2.core.block.multi.tiles.FuelBoilerLinkTileEntity;
import ic2.core.fluid.ExtractionTank;
import ic2.core.fluid.IC2Tank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.StructureBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FuelBoilerTileEntity
extends BaseMultiBlockTileEntity
implements IMultiBlockClickable,
IFluidHandler,
ITileGui,
ILocation,
IFuelStorage,
IWrenchRemovable,
ITileActivityProvider,
IFuelBoiler,
IHeatProvider {
    @NetworkInfo
    public IC2Tank waterTank = new IC2Tank(16000, T -> T.getFluid() == Fluids.f_76193_);
    @NetworkInfo
    public ExtractionTank steamTank = new ExtractionTank(128000);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int fuel = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int maxFuel = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int heat = 0;
    public ICache<IFluidHandler> fluidCache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, T -> !(T instanceof FuelBoilerLinkTileEntity) && !(T instanceof FuelBoilerTileEntity));
    public BucketFiller waterFiller = new BucketFiller(this, (IFluidHandler)this.waterTank, 0, 1);
    SubProduction waterUsage = new SubProduction();

    public FuelBoilerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 6);
        this.addCaches(this.fluidCache);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.addGuiFields("waterTank", "steamTank", "fuel", "maxFuel", "heat");
        this.waterTank.addListener(T -> this.updateGuiField("waterTank"));
        this.steamTank.addListener(T -> this.updateGuiField("steamTank"));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addComparator(new TankComparator("water_tank", ComparatorNames.WATER_TANK, (IFluidTank)this.waterTank));
        this.addComparator(new TankComparator("steam_tank", ComparatorNames.STEAM_TANK, (IFluidTank)this.steamTank));
        this.addComparator(new FuelComparator("fuel", ComparatorNames.FUEL, this));
        this.addComparator(new HeatComparator("heat", ComparatorNames.HEAT, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerSlotAccess(AccessRule.IMPORT, 0, 2, 4, 5);
        handler.registerSlotAccess(AccessRule.EXPORT, 1, 3);
        handler.registerInputFilter(new FluidFilter(new Fluid[]{Fluids.f_76193_}), 0);
        handler.registerInputFilter(SpecialFilters.FUEL_CAN, 2, 4, 5);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 0, 2, 4, 5);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 1, 3);
        handler.registerNamedSlot(SlotType.FUEL, 2, 4, 5);
        handler.registerNamedSlot(SlotType.OUTPUT, 3);
        handler.registerNamedSlot(SlotType.EXTRA_INPUT, 0);
        handler.registerNamedSlot(SlotType.EXTRA_OUTPUT, 1);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_FUEL_BOILER;
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public int getMaxHeat() {
        return 24000;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.isValid;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FuelBoilerContainer(this, player, windowID);
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.isValid;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        if (FluidHelper.drainContainers(player.m_21120_(hand), player, (IFluidHandler)this.waterTank)) {
            return true;
        }
        return FluidHelper.fillContainers(player.m_21120_(hand), player, (IFluidHandler)this.steamTank);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "production", this.waterUsage.save(new CompoundTag()));
        NBTUtils.put(compound, "water", this.waterTank.writeToNBT(new CompoundTag()));
        NBTUtils.put(compound, "steam", this.steamTank.writeToNBT(new CompoundTag()));
        NBTUtils.putShort(compound, "fuel", this.fuel, 0);
        NBTUtils.putShort(compound, "max_fuel", this.maxFuel, 0);
        NBTUtils.putShort(compound, "heat", this.heat, 24000);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.waterUsage.load(compound.m_128469_("production"));
        this.waterTank.readFromNBT(compound.m_128469_("water"));
        this.steamTank.readFromNBT(compound.m_128469_("steam"));
        this.fuel = compound.m_128451_("fuel");
        this.maxFuel = compound.m_128451_("max_fuel");
        this.heat = NBTUtils.getInt(compound, "heat", 24000);
    }

    @Override
    public int getFuel() {
        return this.fuel;
    }

    @Override
    public int getMaxFuel() {
        return this.maxFuel;
    }

    @Override
    public void onStructureTick() {
        this.waterFiller.fillTank();
        if (this.fuel <= 0) {
            IFuelableItem fuel;
            ItemStack stack;
            Item item;
            if (!this.refuel((ItemStack)this.inventory.get(2)) && this.heat > 0) {
                --this.heat;
                this.updateGuiField("heat");
                this.setActive(false);
            }
            if ((item = (stack = (ItemStack)this.inventory.get(2)).m_41720_()) instanceof IFuelableItem && !(fuel = (IFuelableItem)item).hasFuel(stack)) {
                this.transferStack(2, 3);
            }
            this.transferStack(4, 5);
            this.transferStack(5, 2);
        } else {
            if (this.heat < 24000) {
                this.fuel -= Math.min(this.fuel, 30);
                ++this.heat;
                this.updateGuiField("heat");
            } else {
                this.fuel -= Math.min(8, this.fuel);
            }
            this.updateGuiField("fuel");
            this.setActive(true);
        }
        if (this.heat > 3000 && this.waterTank.getFluidAmount() >= 8 && this.steamTank.getSpace() >= 1280) {
            this.waterUsage.add((int)((double)this.heat / 3.75));
            int use = this.waterUsage.consume(1000, true);
            if (use > 0) {
                this.waterTank.drain(use, IFluidHandler.FluidAction.EXECUTE);
                this.steamTank.fillInternal(new FluidStack(IC2Fluids.STEAM, use * 160), IFluidHandler.FluidAction.EXECUTE);
            }
        }
        if (!this.fluidCache.isEmpty()) {
            this.drainTanks(this.fluidCache);
        }
    }

    public void drainTanks(ICache<IFluidHandler> cache) {
        if (this.steamTank.getFluidAmount() > 0 && !cache.isEmpty()) {
            for (Direction dir : cache) {
                IFluidHandler handler = cache.getHandler(dir);
                if (handler == null || this.steamTank.drain(handler.fill(new FluidStack(this.steamTank.getFluid(), Math.min(1000, this.steamTank.getFluidAmount())), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE).getAmount() <= 0 || !this.steamTank.isEmpty()) continue;
                return;
            }
        }
    }

    protected boolean refuel(ItemStack stack) {
        IFuelableItem item;
        Item item2 = stack.m_41720_();
        if (item2 instanceof IFuelableItem && (item = (IFuelableItem)item2).hasFuel(stack)) {
            int added = item.getFuel(stack, 10000, true);
            this.fuel += added;
            this.maxFuel = added;
            this.updateGuiFields("fuel", "maxFuel");
            return added > 0;
        }
        return false;
    }

    @Override
    protected void onInitialValidation() {
        List<BaseLinkingTileEntity> tiles = this.children.getTiles();
        int m = tiles.size();
        for (int i = 0; i < m; ++i) {
            tiles.get(i).setState(i + 1).setFacing(this.getFacing());
        }
        this.onStateChanged();
    }

    @Override
    public void onStructureInvalidated(boolean wasValid, boolean removeMaster) {
        super.onStructureInvalidated(wasValid, removeMaster);
        if (wasValid && !removeMaster) {
            if (!this.setActive(false)) {
                this.onStateChanged();
            }
            this.heat = 0;
        }
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        return newState.m_60734_() == IC2Blocks.FUEL_BOILER_MULTIBLOCK;
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        int i = 1;
        while (builder.cubicStructure(i, 2, 2, 2)) {
            if (!builder.isBlock(IC2Blocks.FUEL_BOILER_MULTIBLOCK)) {
                return false;
            }
            this.children.add(builder.getTile(FuelBoilerLinkTileEntity.class));
            ++i;
        }
        return true;
    }

    @Override
    public boolean setActive(boolean isActive) {
        boolean result = super.setActive(isActive);
        if (result && this.isSimulating()) {
            this.children.setActive(isActive);
        }
        return result;
    }

    public int getTanks() {
        return 2;
    }

    public FluidStack getFluidInTank(int tank) {
        return (tank == 0 ? this.waterTank : this.steamTank).getFluid();
    }

    public int getTankCapacity(int tank) {
        return (tank == 0 ? this.waterTank : this.steamTank).getCapacity();
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return tank == 0 && stack.getFluid() == Fluids.f_76193_;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.waterTank.fill(resource, action);
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.steamTank.drain(resource, action);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return this.steamTank.drain(maxDrain, action);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.75;
    }
}

