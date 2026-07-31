/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.cache.InterfaceCache;
import ic2.core.block.base.features.IFuelBoiler;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.fluid.InsertionTank;
import ic2.core.fluid.TransformHandler;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SteamTurbineTileEntity
extends BaseTileEntity
implements IEnergySource,
ITickListener,
IEUStorage,
IWrenchableTile,
IEUProducer,
ITileActivityProvider {
    InsertionTank tank = new InsertionTank(1000);
    int energy = 0;
    float speed = 0.0f;
    boolean addedToEnergyNet = false;
    int lastProduction;
    int baseProduction;
    ICache<IFuelBoiler> boilers = new InterfaceCache<IFuelBoiler>(this, DirectionList.ALL, IFuelBoiler.class);

    public SteamTurbineTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, new TransformHandler((IFluidHandler)this.tank, IC2Tags.STEAM, IC2Fluids.STEAM));
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addCaches(this.boilers);
        this.baseProduction = IC2.CONFIG.steamTurbineOutput.get();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.GENERATOR_STEAM_TURBINE;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return this.getFacing() != dir && dir.m_122434_().m_122479_();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.75;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnergyNet) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "energy", this.energy, 0);
        NBTUtils.putFloat(compound, "speed", this.speed, 1.0f);
        NBTUtils.put(compound, "tank", this.tank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.speed = NBTUtils.getFloat(compound, "speed", 1.0f);
        this.tank.readFromNBT(compound.m_128469_("tank"));
    }

    @Override
    public float getEUProduction() {
        return this.lastProduction;
    }

    protected boolean isProducingExtra() {
        return this.boilers.getPresentSides().size() > 0;
    }

    @Override
    public void onTick() {
        this.setActive(this.speed > 0.0f);
        if (this.energy >= 4000 || this.tank.isEmpty() && this.speed <= 0.0f) {
            if (this.speed > 0.0f) {
                this.speed = Mth.m_14036_((float)(this.speed - 0.005f), (float)0.0f, (float)1.0f);
                this.lastProduction = (int)(this.speed * (float)this.baseProduction);
            }
        } else {
            int drained = this.tank.drainInternally(128, IFluidHandler.FluidAction.EXECUTE).getAmount();
            this.speed = Mth.m_14036_((float)(this.speed + Mth.m_14036_((float)((float)drained - Math.min(128.0f, 128.0f * this.speed)), (float)-0.005f, (float)0.005f)), (float)0.0f, (float)1.0f);
            this.lastProduction = (int)(this.speed * (float)this.baseProduction + (this.isProducingExtra() ? (float)drained / 8.0f : 0.0f));
        }
        if (this.lastProduction > 0) {
            this.energy = Math.min(4000, this.energy + this.lastProduction);
        }
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return 4000;
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public int getSourceTier() {
        return 2;
    }

    @Override
    public int getMaxEnergyOutput() {
        return 128;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(128, this.energy);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy -= consumed;
    }
}

