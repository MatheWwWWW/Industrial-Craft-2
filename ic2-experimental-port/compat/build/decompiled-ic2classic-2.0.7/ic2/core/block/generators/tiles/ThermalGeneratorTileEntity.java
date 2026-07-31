/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.Material
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IWorkProvider;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.block.generators.containers.ThermalGenContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.InvertedFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.corehacks.mixins.server.BiomeMixin;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ThermalGeneratorTileEntity
extends BaseGeneratorTileEntity
implements IFluidHandler,
IWorkProvider,
IClickable,
IAreaOfEffect {
    static final int MAX_FUEL = 30000000;
    static final int NEED_FUEL = 29000000;
    static final int FUEL_PER_BUCKET = 1500000;
    static final int FUEL_PER_MB = 1500;
    static final Set<Material> MATERIAL = new ObjectOpenHashSet((Object[])new Material[]{Material.f_76307_, Material.f_76305_, Material.f_76309_, Material.f_76278_, Material.f_76279_, Material.f_76276_, Material.f_76308_, Material.f_76280_, Material.f_76316_});
    static final AABBUtil.IBlockFilter FILTER = T -> {
        Block block = T.m_60734_();
        return block != Blocks.f_50752_ && (block == Blocks.f_50080_ || block == Blocks.f_50450_ || MATERIAL.contains(T.m_60767_()));
    };
    boolean thermalEnergy = false;
    @NetworkInfo
    public SubProduction subProduction = new SubProduction();
    boolean calculating = false;
    int visualId = -1;

    public ThermalGeneratorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.production = IC2.CONFIG.thermalGenerator.getProduction();
        this.maxStorage = 8000;
        this.tier = 2;
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0, 1, 2);
        handler.registerSlotsForSide(DirectionList.DOWN, 0);
        handler.registerSlotsForSide(DirectionList.ALL, 1, 2);
        handler.registerInputFilter(new FluidFilter(new Fluid[]{Fluids.f_76195_}), 1);
        handler.registerOutputFilter(SpecialFilters.ALWAYS_FALSE, 1);
        handler.registerOutputFilter(new InvertedFilter(new FluidFilter(new Fluid[]{Fluids.f_76195_})), 2);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 2);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0);
        handler.registerNamedSlot(SlotType.CHARGE, 0);
        handler.registerNamedSlot(SlotType.FUEL, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putBoolean(compound, "thermal", this.thermalEnergy, false);
        compound.m_128379_("migrated", true);
        NBTUtils.put(compound, "sub", this.subProduction.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.thermalEnergy = compound.m_128471_("thermal");
        this.subProduction.load(compound.m_128469_("sub"));
        if (!compound.m_128471_("migrated")) {
            this.fuel *= 1000;
        }
    }

    @Override
    public ResourceLocation getProcessingSound() {
        return IC2Sounds.GEOTHERMAL_OPERATING;
    }

    @Override
    protected boolean isAudioActive() {
        return super.isAudioActive() && !this.isThermalEnergy();
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        return FluidHelper.drainContainers(player.m_21120_(hand), player, this);
    }

    @Override
    public boolean hasAverageActivity() {
        return true;
    }

    @Override
    public int getDelay() {
        return 10;
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(this.f_58858_).m_82400_(7.0);
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2132855788;
    }

    @Override
    public void setVisualizationId(int id) {
        this.visualId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.visualId;
    }

    public boolean isThermalEnergy() {
        return this.thermalEnergy;
    }

    @Override
    public boolean isWorking() {
        return this.subProduction.getProduction() > 0;
    }

    public void setThermalEnergy(boolean value) {
        if (value != this.thermalEnergy) {
            this.thermalEnergy = value;
            this.onStateChanged();
        }
    }

    @Override
    public int getMaxFuel() {
        return 20000;
    }

    @Override
    public int getFuel() {
        return super.getFuel() / 1500;
    }

    @Override
    public boolean needsFuel() {
        return this.fuel <= 29000000;
    }

    @Override
    public float getEUProduction() {
        return this.fuel > 0 ? (float)(this.production * 3) : this.subProduction.getProduction(IC2.CONFIG.thermalGenerator.getPassiveProduction());
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ThermalGenContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_THERMAL;
    }

    @Override
    public boolean gainFuel() {
        if (30000000 - this.fuel >= 1500000) {
            return FluidHelper.drainContainers(this, 1, 2, this);
        }
        return false;
    }

    @Override
    public int getMaxEnergyOutput() {
        return this.production * 3;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.storage, this.production * 3);
    }

    @Override
    public boolean needsEnergy() {
        return this.fuel > 0 && this.storage + this.production * 3 <= this.maxStorage;
    }

    @Override
    protected void consumeFuel() {
    }

    @Override
    public boolean gainEnergy() {
        if (this.needsEnergy()) {
            int toConsume = Math.min(this.fuel, 4500);
            this.fuel -= toConsume;
            this.storage = Math.min(this.maxStorage, this.storage + this.production * Mth.m_14167_((float)((float)toConsume / 1500.0f)));
            this.consumeFuel();
            this.setThermalEnergy(false);
            return true;
        }
        if (this.clock(200) || this.subProduction.needsInit()) {
            this.updateProduction();
        }
        if (this.storage < this.maxStorage) {
            this.updateGuiField("subProduction");
            int heatRequired = IC2.CONFIG.thermalGenerator.getPassiveProduction();
            if (this.subProduction.update(heatRequired)) {
                this.storage += this.subProduction.consume(heatRequired, true);
                this.updateGuiField("storage");
            }
            this.setThermalEnergy(this.subProduction.getProduction() > 0);
            return true;
        }
        return false;
    }

    public void updateProduction() {
        AABBUtil.createOffthreadTask(this.m_58904_(), this.m_58899_(), 7, new Mapper(), 35, DirectionList.ALL, T -> {
            int heatPoints = 0;
            for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(T.getMappedPositions())) {
                Block block = (Block)entry.getKey();
                if (block == Blocks.f_50083_) {
                    heatPoints += ((List)entry.getValue()).size() * 3;
                    continue;
                }
                if (block == Blocks.f_49991_) {
                    heatPoints += ((List)entry.getValue()).size() * 10;
                    continue;
                }
                if (block == Blocks.f_50450_) {
                    heatPoints += ((List)entry.getValue()).size() * 7;
                    continue;
                }
                if (block == Blocks.f_50080_) {
                    heatPoints += ((List)entry.getValue()).size() * 5;
                    continue;
                }
                if (block == Blocks.f_50069_) {
                    heatPoints += ((List)entry.getValue()).size() * 2;
                    continue;
                }
                if (block == Blocks.f_49990_) {
                    heatPoints += ((List)entry.getValue()).size() * -3;
                    continue;
                }
                if (block == Blocks.f_50125_) {
                    heatPoints += ((List)entry.getValue()).size() * -5;
                    continue;
                }
                if (block != Blocks.f_50126_) continue;
                heatPoints += ((List)entry.getValue()).size() * -10;
            }
            this.subProduction.setProduction((int)Math.max(0.0f, (float)heatPoints * this.getBiomeBuff()));
            this.updateGuiField("subProduction");
        });
    }

    public float getBiomeBuff() {
        return Mth.m_14036_((float)((BiomeMixin)this.f_58857_.m_204166_(this.m_58899_()).m_203334_()).getTemp(this.m_58899_()), (float)0.5f, (float)1.5f);
    }

    public int getTanks() {
        return 1;
    }

    public FluidStack getFluidInTank(int tank) {
        return new FluidStack((Fluid)Fluids.f_76195_, this.fuel / 1500);
    }

    public int getTankCapacity(int tank) {
        return 20000;
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return stack.getFluid() == Fluids.f_76195_ || stack.getFluid() == IC2Fluids.BLAZING_LAVA;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.getFluid() == Fluids.f_76195_) {
            int mbFree = (30000000 - this.fuel) / 1500;
            if (mbFree <= 0) {
                return 0;
            }
            int consumed = Math.min(mbFree, resource.getAmount());
            if (action.execute() && consumed > 0) {
                this.fuel += consumed * 1500;
                this.updateGuiField("fuel");
            }
            return consumed;
        }
        if (resource.getFluid() == IC2Fluids.BLAZING_LAVA) {
            int mbFree = (30000000 - this.fuel) / 1500 / 5;
            if (mbFree <= 0) {
                return 0;
            }
            int consumed = Math.min(mbFree, resource.getAmount());
            if (action.execute() && consumed > 0) {
                this.fuel += consumed * 1500 * 5;
                this.updateGuiField("fuel");
            }
            return consumed;
        }
        return 0;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    static class Mapper
    implements AABBUtil.IBlockMapper {
        Mapper() {
        }

        @Override
        public boolean isValid(BlockState state) {
            return true;
        }

        @Override
        public void mapBlocks(LevelReader reader, BlockPos pos, Map<Block, List<BlockPos>> mapper) {
            BlockState state = reader.m_8055_(pos);
            Block block = state.m_60734_();
            Fluid fluid = state.m_60819_().m_76152_();
            Material mat = state.m_60767_();
            if (fluid == Fluids.f_76194_) {
                AABBUtil.mapResult(Blocks.f_50083_, pos, mapper);
            } else if (mat == Material.f_76307_ || mat == Material.f_76309_ || fluid == Fluids.f_76195_) {
                AABBUtil.mapResult(Blocks.f_49991_, pos, mapper);
            } else if (block == Blocks.f_50450_) {
                AABBUtil.mapResult(Blocks.f_50450_, pos, mapper);
            } else if (block == Blocks.f_50080_) {
                AABBUtil.mapResult(Blocks.f_50080_, pos, mapper);
            } else if (mat == Material.f_76278_ || mat == Material.f_76279_) {
                AABBUtil.mapResult(Blocks.f_50069_, pos, mapper);
            } else if (mat == Material.f_76305_ || fluid == Fluids.f_76193_ || fluid == Fluids.f_76192_) {
                AABBUtil.mapResult(Blocks.f_49990_, pos, mapper);
            } else if (mat == Material.f_76280_ || mat == Material.f_76308_) {
                AABBUtil.mapResult(Blocks.f_50125_, pos, mapper);
            } else if (mat == Material.f_76276_ || mat == Material.f_76316_) {
                AABBUtil.mapResult(Blocks.f_50126_, pos, mapper);
            }
        }
    }
}

