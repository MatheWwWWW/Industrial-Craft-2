/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelData$Builder
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.block.transport.fluid.helper.FluidCache;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.FluidTubeContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidTubeTileEntity
extends TubeTileEntity
implements IFluidPipe,
ITileGui,
ICopyableSettings {
    public ICache<IFluidHandler> fluidCache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, this::isValidPipe);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int pipeConnections;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int emitters = 0;
    @NetworkInfo
    public FluidCache<FluidTubeTileEntity> cache = new FluidCache<FluidTubeTileEntity>("cache", this, this.fluidCache, this::canConnect);

    public FluidTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.fluidCache);
        this.addNetworkFields("pipeConnections", "cache", "emitters");
    }

    private boolean isValidPipe(BlockEntity tile) {
        IFluidPipe pipe;
        return !(tile instanceof IFluidPipe) || (pipe = (IFluidPipe)tile).isFluidAcceptor();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.FLUID_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "fluid_connectivity", this.pipeConnections, 0);
        NBTUtils.putByte(compound, "emitters", this.emitters, 0);
        this.cache.save(compound, true);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.pipeConnections = compound.m_128451_("fluid_connectivity");
        this.emitters = compound.m_128451_("emitters");
        this.cache.load(compound);
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        NBTUtils.putByte(compound, "emitters", this.emitters, 0);
        this.cache.save(compound, true);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.emitters = compound.m_128451_("emitters");
        this.cache.load(compound);
        this.updateGuiField("emitters");
        this.cache.update();
    }

    @Override
    public int getConnectivity() {
        return super.getConnectivity() | this.pipeConnections | this.cache.getTanks();
    }

    @Override
    public int getValidDirections() {
        return this.connectivity | this.pipeConnections | this.cache.getTanks();
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("cache") || fields.contains("emitters") || fields.contains("pipeConnections")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        ModelData.Builder builder = ModelData.builder().with((ModelProperty)CableProperty.INSTANCE, (Object)((this.pipeConnections | this.connectivity) << 6 | this.anchors)).with((ModelProperty)CableProperty.PIPE_EXTRACTION, (Object)this.emitters);
        if (this.foamed == 2) {
            builder.with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T));
        }
        return builder.build();
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return !this.fluidCache.isEmpty() && player.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && player.m_21120_(InteractionHand.OFF_HAND).m_41619_();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FluidTubeContainer(this, player, windowID);
    }

    @Override
    public void onLoaded() {
        this.cache.protect(() -> super.onLoaded());
        this.cache.add();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.cache.remove();
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return ((this.pipeConnections | this.cache.getTanks()) & 1 << dir.m_122411_()) != 0;
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return ((this.pipeConnections | this.cache.getTanks()) & 1 << dir.m_122411_()) != 0;
    }

    @Override
    public List<IFluidPipe.FluidOutput> getOutputs() {
        return this.cache.getOutputs();
    }

    @Override
    public Iterable<Direction> getEmitterSources() {
        return DirectionList.ofNumber(this.emitters);
    }

    public boolean canConnect(Direction dir, IFluidPipe pipe) {
        return !this.hasAnchor(dir) && !pipe.hasAnchor(dir.m_122424_());
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        FluidTubeContainer container;
        super.onClientDataReceived(entity, key, value);
        if (key >= 0 && key <= 3 && (container = (FluidTubeContainer)this.getContainer(entity, FluidTubeContainer.class)) != null) {
            switch (key) {
                case 0: {
                    container.data.setFacing(container, Mth.m_14045_((int)value, (int)0, (int)5));
                    return;
                }
                case 1: {
                    container.data.setPriority(value);
                    break;
                }
                case 2: {
                    container.data.setWhiteList(value == 1);
                    break;
                }
                case 3: {
                    Direction dir = Direction.m_122376_((int)container.data.getDir());
                    if (this.hasEmitter(dir)) {
                        this.removeEmitter(dir);
                        break;
                    }
                    this.addEmitter(dir);
                }
            }
        }
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        int newValue = this.cache.updateConnectivity();
        if (newValue != this.pipeConnections) {
            this.pipeConnections = newValue;
            this.updateTileField("pipeConnections");
        }
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering() || this.emitters <= 0) {
            return;
        }
        if (!this.cache.isAdded() || this.invClock(5)) {
            return;
        }
        for (Direction dir : DirectionList.ofNumber(this.emitters)) {
            int drained;
            IFluidHandler handler = this.fluidCache.getHandler(dir);
            if (handler == null || (drained = FluidNet.INSTANCE.sendFluid(this, dir, handler.drain(100, IFluidHandler.FluidAction.SIMULATE), 25)) <= 0) continue;
            handler.drain(drained, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public boolean hasEmitter(Direction side) {
        return (this.emitters & 1 << side.m_122411_()) != 0;
    }

    public boolean addEmitter(Direction side) {
        if (!this.hasEmitter(side)) {
            this.emitters |= 1 << side.m_122411_();
            this.updateTileField("emitters");
            this.cache.update();
            this.addToTick();
            return true;
        }
        return false;
    }

    public boolean removeEmitter(Direction side) {
        if (this.hasAnchor(side)) {
            this.anchors &= ~(1 << side.m_122411_());
            this.updateTileField("emitters");
            this.cache.update();
            return true;
        }
        return false;
    }
}

