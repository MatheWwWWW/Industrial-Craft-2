/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.registries.IFluidFuelRegistry;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.misc.BucketFiller;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.LiquidFuelGenContainer;
import ic2.core.fluid.IC2Tank;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class LiquidFuelGenTileEntity
extends BaseGeneratorTileEntity
implements IClickable {
    @NetworkInfo
    public IC2Tank tank = new IC2Tank(16000, T -> IC2.RECIPES.get((boolean)this.isSimulating()).fluid_fuel.getFuel(T.getFluid()) != null);
    @NetworkInfo
    public int maxFuel = 0;
    public BucketFiller filler = new BucketFiller(this, (IFluidHandler)this.tank, 1, 2);
    public int cooldown = 0;
    public int outputEnergy = 32;

    public LiquidFuelGenTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
        this.tier = 2;
        this.maxStorage = 10000;
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addGuiFields("tank", "maxFuel");
        this.tank.addListener(T -> this.updateGuiField("tank"));
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, (IFluidTank)this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 1, 2);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerInputFilter(this::canInsertFuel, 1);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
        handler.registerNamedSlot(SlotType.FUEL, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    public boolean canInsertFuel(ItemStack stack) {
        return IC2.RECIPES.get().fluid_fuel.getFuel(FluidUtil.getFluidContained((ItemStack)stack).orElse(FluidStack.EMPTY).getFluid()) != null;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.readFromNBT(compound.m_128469_("tank"));
        this.maxFuel = compound.m_128451_("max_fuel");
        this.cooldown = compound.m_128451_("cooldown");
        this.production = compound.m_128451_("production");
        this.updateOutput();
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "tank", this.tank.writeToNBT(new CompoundTag()));
        NBTUtils.putInt(compound, "max_fuel", this.maxFuel, 0);
        NBTUtils.putByte(compound, "cooldown", this.cooldown, 0);
        NBTUtils.putInt(compound, "production", this.production, 0);
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.drainContainers(player.m_21120_(hand), player, (IFluidHandler)this.tank);
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.storage, this.outputEnergy);
    }

    protected void updateOutput() {
        int energyTier = EnergyNet.INSTANCE.getTierFromPower(this.production);
        if (EnergyNet.INSTANCE.getPowerFromTier(energyTier) < energyTier) {
            ++energyTier;
        }
        this.outputEnergy = EnergyNet.INSTANCE.getPowerFromTier(Math.max(1, energyTier));
    }

    @Override
    public int getMaxFuel() {
        return this.maxFuel;
    }

    @Override
    public int getMaxEnergyOutput() {
        return 128;
    }

    @Override
    public float getEUProduction() {
        return this.fuel > 0 ? (float)this.production : 0.0f;
    }

    @Override
    public boolean needsFuel() {
        return this.fuel <= 0 && this.tank.getFluidAmount() > 0;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.tank.getSpace() >= 1000) {
            this.filler.fillTank();
        }
    }

    @Override
    public boolean gainFuel() {
        if (this.cooldown > 0) {
            --this.cooldown;
        } else if (this.fuel <= 0 && this.tank.getFluidAmount() > 0) {
            FluidStack fluid = this.tank.drain(1000, IFluidHandler.FluidAction.SIMULATE);
            if (fluid.isEmpty()) {
                return false;
            }
            IFluidFuelRegistry.FuelEntry fuelEntry = IC2.RECIPES.get((boolean)this.isSimulating()).fluid_fuel.getFuel(fluid.getFluid());
            if (fuelEntry == null) {
                this.cooldown = 40;
                return false;
            }
            int fuelTicks = (int)((float)fuelEntry.getTicksPerBucket() * ((float)fluid.getAmount() / 1000.0f));
            if (fuelTicks <= 0) {
                this.cooldown = 40;
                return false;
            }
            this.fuel += fuelTicks;
            this.maxFuel = fuelTicks;
            this.production = Math.max(1, (int)((double)fuelEntry.getEuPerTick() * IC2.CONFIG.fluidGenMultiplier.get()));
            this.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
            this.updateGuiFields("fuel", "maxFuel", "production");
            this.updateOutput();
            return true;
        }
        return false;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new LiquidFuelGenContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_LIQUID_FUEL;
    }
}

