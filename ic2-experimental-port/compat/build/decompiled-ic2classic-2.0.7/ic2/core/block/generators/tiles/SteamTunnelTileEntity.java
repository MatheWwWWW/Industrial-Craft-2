/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.impl.LinkedSource;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.energy.tile.IMultiEnergyTile;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.blocks.ValveBlock;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.multiblock.IMultiBlockFluidExporter;
import ic2.core.block.base.misc.SubProduction;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.multi.TurbineMultiBlock;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.fluid.IC2Tank;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.StructureBuilder;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SteamTunnelTileEntity
extends BaseMultiBlockTileEntity
implements IMultiBlockFluidExporter,
IMultiEnergyTile,
IMultiEnergySource,
IFluidHandler,
IEUStorage,
IEUProducer,
ITileActivityProvider {
    IC2Tank steamTank = new IC2Tank(16000, T -> T.getFluid() == IC2Fluids.STEAM);
    IC2Tank waterTank = new IC2Tank(2000);
    boolean addedToEnet = false;
    SubProduction waterProduction = new SubProduction();
    int energy = 0;
    int delayActive = -20;
    LongList turbine_blades = new LongArrayList();
    int production;

    public SteamTunnelTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.production = IC2.CONFIG.steamTunnelOutput.get();
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.turbine_blades.size() > 0) {
            LongArrayList list = LongArrayList.wrap((long[])this.structure.structurePositions);
            byte[] result = new byte[this.turbine_blades.size()];
            int m = this.turbine_blades.size();
            for (int i = 0; i < m; ++i) {
                result[i] = (byte)(list.indexOf(this.turbine_blades.getLong(i)) - 128);
            }
            compound.m_128382_("blades", result);
        }
        NBTUtils.put(compound, "water_tank", this.waterTank.writeToNBT(new CompoundTag()));
        NBTUtils.put(compound, "steam_tank", this.steamTank.writeToNBT(new CompoundTag()));
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.put(compound, "production", this.waterProduction.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        byte[] offsets = compound.m_128463_("blades");
        int m = offsets.length;
        for (int i = 0; i < m; ++i) {
            this.turbine_blades.add(this.structure.structurePositions[offsets[i] + 128]);
        }
        this.waterTank.readFromNBT(compound.m_128469_("water_tank"));
        this.steamTank.readFromNBT(compound.m_128469_("steam_tank"));
        this.energy = compound.m_128451_("energy");
        this.waterProduction.load(compound.m_128469_("production"));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STEAM_TUNNEL;
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public boolean hasMultiplePackets() {
        return true;
    }

    @Override
    public int getPacketCount() {
        return 4;
    }

    @Override
    public int getSourceTier() {
        return 3;
    }

    @Override
    public int getMaxEnergyOutput() {
        return 512;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(512, this.energy);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy = Math.max(0, this.energy - consumed);
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return 10000;
    }

    @Override
    public int getTier() {
        return 3;
    }

    @Override
    public boolean needsUpdateTick() {
        return true;
    }

    @Override
    public float getEUProduction() {
        return this.steamTank.getFluidAmount() >= 1024 ? (float)(this.production * Math.min(4, this.steamTank.getFluidAmount() / 1024)) : 0.0f;
    }

    @Override
    public void onStructureTick() {
        if (this.energy + this.production <= this.getMaxEU() && this.steamTank.getFluidAmount() >= 1024) {
            int multiplier = Math.min(4, this.steamTank.getFluidAmount() / 1024);
            this.energy += this.production * multiplier;
            this.steamTank.drain(1024 * multiplier, IFluidHandler.FluidAction.EXECUTE);
            this.waterProduction.add(640 * multiplier);
            this.waterTank.fill(new FluidStack((Fluid)Fluids.f_76193_, this.waterProduction.consume(100, true)), IFluidHandler.FluidAction.EXECUTE);
            ++this.delayActive;
        } else {
            --this.delayActive;
        }
        if (this.clock(20)) {
            this.setActive(this.delayActive > -10);
            this.delayActive = 0;
        }
    }

    @Override
    public void onTick() {
        if (this.isRendering()) {
            this.spawnParticles();
            return;
        }
        super.onTick();
    }

    @OnlyIn(value=Dist.CLIENT)
    protected void spawnParticles() {
        if (this.isValid && this.isActive()) {
            BlockPos pos = this.m_58899_();
            Vec3i offset = this.getFacing().m_122436_();
            for (int i = 0; i < 5; ++i) {
                float xOffset = this.f_58857_.f_46441_.m_188501_() * 2.0f - 1.0f;
                float yOffset = this.f_58857_.f_46441_.m_188501_() * 2.0f - 1.0f;
                float zOffset = this.f_58857_.f_46441_.m_188501_() * 2.0f - 1.0f;
                Particle part = RenderUtils.addOptionalParticle((ParticleOptions)ParticleTypes.f_123762_, (float)pos.m_123341_() + 0.5f + (float)offset.m_123341_() * 0.6f + xOffset, (float)pos.m_123342_() + 0.5f + (float)offset.m_123342_() * 0.6f + yOffset, (float)pos.m_123343_() + 0.5f + (float)offset.m_123343_() * 0.6f + zOffset, (float)offset.m_123341_() * 0.35f, (float)offset.m_123342_() * 0.125f + 0.125f, (float)offset.m_123343_() * 0.35f);
                if (part == null) continue;
                part.m_107253_(0.75f, 0.75f, 0.75f);
            }
        }
    }

    @Override
    public void onStructureInvalidated(boolean wasValid, boolean removeMaster) {
        super.onStructureInvalidated(wasValid, removeMaster);
        if (this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        if (!this.turbine_blades.isEmpty()) {
            this.updateState(true);
            this.turbine_blades.clear();
        }
        if (wasValid && !removeMaster && !this.setActive(false)) {
            this.onStateChanged();
        }
    }

    @Override
    public void onStructureValidated(boolean revalidation) {
        super.onStructureValidated(revalidation);
        if (!this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        if (!revalidation) {
            this.onStateChanged();
            this.updateState(false);
        }
    }

    @Override
    public boolean setActive(boolean isActive) {
        boolean result = super.setActive(isActive);
        if (result && this.isSimulating()) {
            this.updateState(false);
        }
        return result;
    }

    private void updateState(boolean remove) {
        if (remove) {
            for (int i = 0; i < this.turbine_blades.size(); ++i) {
                BlockPos pos = BlockPos.m_122022_((long)this.turbine_blades.getLong(i));
                if (this.f_58857_.m_46859_(pos)) continue;
                this.f_58857_.m_46597_(pos, IC2Blocks.STEAM_TUNNEL_MULTIBLOCK.m_49966_());
            }
        } else {
            for (int i = 0; i < this.turbine_blades.size(); ++i) {
                this.f_58857_.m_46597_(BlockPos.m_122022_((long)this.turbine_blades.getLong(i)), TurbineMultiBlock.create(this.getFacing().m_122416_(), this.isActive()));
            }
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public List<IEnergyTile> getTiles() {
        ObjectList list = CollectionUtils.createList();
        list.add((LinkedSource)new LinkedSource(this, 0));
        int dirs = DirectionList.ofFacing(this.getFacing()).invert().getCode();
        for (BlockPos pos : this.boundingBox.getHollowIterator()) {
            if (pos == BlockPos.f_121853_ || this.children.contains(pos = pos.m_121955_((Vec3i)this.m_58899_()))) continue;
            list.add((LinkedSource)new LinkedSource(this.f_58857_, pos, dirs));
        }
        return list;
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        return newState.m_60713_(IC2Blocks.STEAM_TUNNEL_MULTIBLOCK) || !this.boundingBox.isEdge(actualPos) && newState.m_60734_() instanceof ValveBlock;
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        Box box = new Box().expandSide(builder.getFacing().m_122434_(), 1).expand(builder.getFacing(), 4);
        for (BlockPos pos : box) {
            if (builder.setOffset(pos).isDefaultPos()) continue;
            if (!box.isBorder(pos)) {
                if (builder.isAir()) continue;
                return false;
            }
            Block block = builder.getState().m_60734_();
            if (block == IC2Blocks.STEAM_TUNNEL_MULTIBLOCK || !box.isEdge(pos) && block instanceof ValveBlock) {
                if (!(block instanceof ValveBlock)) continue;
                this.children.add(builder.getTile(BaseLinkingTileEntity.class));
                continue;
            }
            return false;
        }
        builder.resetPosition();
        this.turbine_blades.add(builder.left().up().getCurrentPos());
        this.turbine_blades.add(builder.right().getCurrentPos());
        this.turbine_blades.add(builder.right().getCurrentPos());
        this.turbine_blades.add(builder.down().left().left().getCurrentPos());
        this.turbine_blades.add(builder.right().right().getCurrentPos());
        this.turbine_blades.add(builder.down().left().left().getCurrentPos());
        this.turbine_blades.add(builder.right().getCurrentPos());
        this.turbine_blades.add(builder.right().getCurrentPos());
        builder.resetPosition();
        return true;
    }

    @Override
    public void exportFluids(ICache<IFluidHandler> cache, IFluidPipe pump, Direction pumpSide) {
        if (!cache.isEmpty() && !this.waterTank.isEmpty()) {
            for (Direction dir : cache) {
                IFluidHandler handler = cache.getHandler(dir);
                if (handler == null || this.waterTank.drain(handler.fill(new FluidStack(this.waterTank.getFluid(), Math.min(1000, this.waterTank.getFluidAmount())), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE).getAmount() <= 0 || !this.waterTank.isEmpty()) continue;
                return;
            }
        }
        if (this.waterTank.isEmpty() || pump == null) {
            return;
        }
        this.waterTank.drain(FluidNet.INSTANCE.sendFluid(pump, pumpSide, new FluidStack(this.waterTank.getFluid(), Math.min(1000, this.waterTank.getFluidAmount())), 100), IFluidHandler.FluidAction.EXECUTE);
    }

    public int getTanks() {
        return 2;
    }

    public FluidStack getFluidInTank(int tank) {
        return (tank == 0 ? this.steamTank : this.waterTank).getFluid();
    }

    public int getTankCapacity(int tank) {
        return (tank == 0 ? this.steamTank : this.waterTank).getCapacity();
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return tank == 0 && this.steamTank.isFluidValid(stack);
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.steamTank.fill(FluidHelper.transform(resource, IC2Tags.STEAM, IC2Fluids.STEAM), action);
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.waterTank.drain(resource, action);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return this.waterTank.drain(maxDrain, action);
    }
}

