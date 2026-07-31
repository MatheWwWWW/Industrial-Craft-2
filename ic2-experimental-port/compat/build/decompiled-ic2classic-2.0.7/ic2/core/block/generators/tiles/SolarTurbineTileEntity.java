/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IPumpTile;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.special.HeatComparator;
import ic2.core.block.base.misc.readers.IHeatProvider;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.SolarTurbineContainer;
import ic2.core.block.generators.tiles.SolarPanelTileEntity;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SolarTurbineTileEntity
extends BaseGeneratorTileEntity
implements IFluidHandler,
IPumpTile,
IHeatProvider,
IClickable {
    public static final int MAX_WATER = 40000;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int heat = 0;
    float consumed = 0.0f;
    float subStorage = 0.0f;

    public SolarTurbineTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
        this.maxStorage = 100;
        this.production = (int)(32.0 * IC2.CONFIG.solarTurbine.get());
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.addGuiFields("heat");
        this.addComparator(new HeatComparator("heat", ComparatorNames.HEAT, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.UP.invert());
        handler.registerBlockAccess(DirectionList.UP.invert(), AccessRule.BOTH);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2);
        handler.registerSlotsForSide(DirectionList.HORIZONTAL, 1);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.setSlotAccess(0, Direction.UP, AccessRule.DISABLED);
        handler.setSlotAccess(1, Direction.UP, AccessRule.DISABLED);
        handler.setSlotAccess(2, Direction.UP, AccessRule.DISABLED);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerInputFilter(new FluidFilter(new Fluid[]{Fluids.f_76193_}), 1);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
        handler.registerNamedSlot(SlotType.FUEL, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "heat", this.heat, 24000);
        NBTUtils.putFloat(compound, "consumed", this.consumed, 0.0f);
        NBTUtils.putFloat(compound, "sub_storage", this.subStorage, 0.0f);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.heat = compound.m_128451_("heat");
        this.subStorage = compound.m_128457_("sub_storage");
        this.consumed = compound.m_128457_("consumed");
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.drainContainers(player.m_21120_(hand), player, this);
    }

    @Override
    public int getPumpProgress() {
        return this.fuel;
    }

    @Override
    public int getPumpMaxProgress() {
        return 40000;
    }

    @Override
    public int getMaxFuel() {
        return 40000;
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
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new SolarTurbineContainer(this, player, windowID);
    }

    @Override
    public boolean needsFuel() {
        return this.fuel < 38000;
    }

    @Override
    public boolean gainFuel() {
        return FluidHelper.drainContainers(this, 1, 2, this);
    }

    @Override
    public boolean gainEnergy() {
        if (this.clock(128)) {
            this.setActive(SolarPanelTileEntity.isSunVisible(this.m_58904_(), this.m_58899_().m_7494_()));
        }
        if (this.isActive()) {
            if (this.heat < 24000) {
                this.heat += 2;
                if (this.heat > 24000) {
                    this.heat = 24000;
                }
                this.updateGuiField("heat");
            }
        } else if (this.heat > 0) {
            --this.heat;
            this.updateGuiField("heat");
        }
        if (this.fuel > 0 && this.heat > 0) {
            float per = (float)this.heat / 24000.0f;
            this.consumed += 4.0f * per;
            int drain = (int)this.consumed;
            this.consumed -= (float)drain;
            if (this.fuel < 0) {
                this.fuel = 0;
            }
            this.updateGuiField("fuel");
            if (this.storage >= this.maxStorage) {
                this.fuel -= drain;
                return this.isActive();
            }
            float gen = (float)this.fuel * per / 1250.0f * (float)IC2.CONFIG.solarTurbine.get();
            this.subStorage += gen;
            int adding = (int)this.subStorage;
            if (adding > 0) {
                this.storage += adding;
                this.subStorage -= (float)adding;
            }
            if ((int)gen != this.production) {
                this.production = (int)gen;
                this.updateGuiField("production");
            }
            this.fuel -= drain;
        }
        return this.isActive();
    }

    @Override
    public float getEUProduction() {
        return this.fuel > 0 && this.heat > 0 ? (float)this.heat / 24000.0f * (float)this.fuel / 1250.0f : 0.0f;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.setActive(SolarPanelTileEntity.isSunVisible(this.m_58904_(), this.m_58899_().m_7494_()));
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_SOLAR_TURBINE;
    }

    public int getTanks() {
        return 1;
    }

    public FluidStack getFluidInTank(int tank) {
        return new FluidStack((Fluid)Fluids.f_76193_, this.fuel);
    }

    public int getTankCapacity(int tank) {
        return 40000;
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.getFluid() == Fluids.f_76193_;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.getFluid() != Fluids.f_76193_) {
            return 0;
        }
        int toAdd = Math.min(resource.getAmount(), (40000 - this.fuel) / 2);
        if (action.execute()) {
            this.fuel += toAdd * 2;
            this.updateGuiField("fuel");
        }
        return toAdd;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Override
    public int getMaxEnergyOutput() {
        return (int)(32.0 * IC2.CONFIG.solarTurbine.get());
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.storage, this.getMaxEnergyOutput());
    }
}

