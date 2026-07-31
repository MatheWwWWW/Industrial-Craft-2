/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.machines.tiles.nv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IFuelStorage;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.readers.IPumpTile;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.IParticleSpawner;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.FuelComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.machines.containers.nv.WoodGassifierContainer;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.fluid.ExtractionTank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.TagFilter;
import ic2.core.inventory.filter.special.FuelFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class StoneWoodGassifierTileEntity
extends BaseInventoryTileEntity
implements ITickListener,
IProgressMachine,
IFuelStorage,
ITileGui,
IPumpTile,
IParticleSpawner,
ISimpleFluidSource,
ITileActivityProvider,
IClickable {
    @NetworkInfo
    protected ExtractionTank tank = new ExtractionTank(9000);
    @NetworkInfo
    int fuel = 0;
    @NetworkInfo
    int maxFuel = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    int progress = 0;
    boolean addedToFluidNet = false;

    public StoneWoodGassifierTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addGuiFields("tank", "fuel", "maxFuel", "progress");
        this.tank.addListener(T -> this.updateGuiField("tank"));
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addComparator(new FuelComparator("fuel", ComparatorNames.FUEL, this));
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, (IFluidTank)this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1, 3);
        handler.registerSlotAccess(AccessRule.EXPORT, 2, 4);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2, 4);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1, 3);
        handler.registerInputFilter(FuelFilter.WITH_LAVA, 0);
        handler.registerOutputFilter(FuelFilter.NOT_WITH_LAVA, 0);
        handler.registerInputFilter(new TagFilter((TagKey<Item>)ItemTags.f_13182_), 1);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.CELL_EMPTY), 3);
        handler.registerNamedSlot(SlotType.FUEL, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
        handler.registerNamedSlot(SlotType.CONTAINER, 3);
        handler.registerNamedSlot(SlotType.EXTRA_OUTPUT, 4);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.readFromNBT(compound.m_128469_("tank"));
        this.fuel = compound.m_128451_("fuel");
        this.maxFuel = compound.m_128451_("max_fuel");
        this.progress = compound.m_128451_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag()));
        compound.m_128405_("fuel", this.fuel);
        compound.m_128405_("max_fuel", this.maxFuel);
        compound.m_128376_("progress", (short)this.progress);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.fillContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank);
    }

    @Override
    public void onTick() {
        int newFuel;
        if (this.fuel <= 0 && (!((ItemStack)this.inventory.get(0)).m_41619_() && this.canRun() || this.progress > 0) && (newFuel = IC2.RECIPES.get(this.isSimulating()).getFuel((ItemStack)this.inventory.get(0), false)) > 0) {
            this.fuel = newFuel;
            this.maxFuel = newFuel;
            this.updateGuiField("maxFuel");
            this.updateGuiField("fuel");
            ItemStack fuel = (ItemStack)this.inventory.get(0);
            if (fuel.hasCraftingRemainingItem()) {
                this.inventory.set(0, (Object)fuel.getCraftingRemainingItem());
            } else {
                fuel.m_41774_(1);
            }
        }
        if (this.fuel > 0) {
            if (this.progress == 0 && this.canRun()) {
                this.progress = (int)this.getMaxProgress();
                ((ItemStack)this.inventory.get(1)).m_41774_(1);
                this.updateGuiField("progress");
            }
            if (this.progress > 0) {
                --this.progress;
                this.tank.fillInternal(new FluidStack(IC2Fluids.WOOD_GAS, 6), IFluidHandler.FluidAction.EXECUTE);
                if (this.progress <= 0) {
                    this.progress = 0;
                    this.tank.fillInternal(new FluidStack(IC2Fluids.WOOD_GAS, 40), IFluidHandler.FluidAction.EXECUTE);
                    if (((ItemStack)this.inventory.get(2)).m_41619_()) {
                        this.inventory.set(2, (Object)new ItemStack((ItemLike)Items.f_42414_));
                    } else {
                        ((ItemStack)this.inventory.get(2)).m_41769_(1);
                    }
                }
                this.updateGuiField("progress");
            }
            --this.fuel;
            this.updateGuiField("fuel");
        }
        if (this.clock(10) && this.canFill()) {
            FluidHelper.fillContainers(this, 3, 4, (IFluidHandler)this.tank);
        }
        if (this.clock(10) && this.tank.getFluidAmount() > 0) {
            this.tank.drain(FluidNet.INSTANCE.sendFluid(this, null, this.tank.getFluid(), 25), IFluidHandler.FluidAction.EXECUTE);
        }
        this.setActive(this.fuel > 0);
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
    public float getProgress() {
        return this.progress == 0 ? 0.0f : this.getMaxProgress() - (float)this.progress;
    }

    @Override
    public float getMaxProgress() {
        return 160.0f;
    }

    @Override
    public int getPumpMaxProgress() {
        return this.tank.getCapacity();
    }

    @Override
    public int getPumpProgress() {
        return this.tank.getFluidAmount();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.WOOD_GASSIFIER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new WoodGassifierContainer(this, player, windowID);
    }

    public boolean canRun() {
        if (this.tank.getFluidAmount() > 8000) {
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
    @OnlyIn(value=Dist.CLIENT)
    public void animationTick(RandomSource rand) {
        if (this.isActive()) {
            Direction direction = this.getFacing();
            Direction.Axis axis = direction.m_122434_();
            double d0 = (double)this.f_58858_.m_123341_() + 0.5 + (axis == Direction.Axis.X ? (double)direction.m_122429_() * 0.52 : rand.m_188500_() * 0.6 - 0.3);
            double d1 = (double)this.f_58858_.m_123342_() + rand.m_188500_() * 6.0 / 16.0;
            double d2 = (double)this.f_58858_.m_123343_() + 0.5 + (axis == Direction.Axis.Z ? (double)direction.m_122431_() * 0.52 : rand.m_188500_() * 0.6 - 0.3);
            if (rand.m_188500_() < 0.1) {
                this.f_58857_.m_7785_(d0, d1, d2, SoundEvents.f_11907_, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
            this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123762_, d0, d1, d2, 0.0, 0.0, 0.0);
            this.f_58857_.m_7106_((ParticleOptions)ParticleTypes.f_123744_, d0, d1, d2, 0.0, 0.0, 0.0);
        }
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

