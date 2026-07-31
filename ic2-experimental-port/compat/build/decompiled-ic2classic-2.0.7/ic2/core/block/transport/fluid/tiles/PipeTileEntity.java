/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.api.blocks.PainterHelper;
import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ISpecialWrenchable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CableProperty;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.block.transport.fluid.FluidPipeContainer;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.block.transport.fluid.helper.FluidCache;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Properties;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PipeTileEntity
extends BaseTileEntity
implements IFluidPipe,
ITileGui,
IClickable,
ICamouflagable,
ISpecialWrenchable,
ITickListener,
ICopyableSettings {
    public ICache<IFluidHandler> fluidCache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, this::isValidPipe);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int connectivity = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int emitters = 0;
    @NetworkInfo
    public FluidCache<PipeTileEntity> cache = new FluidCache<PipeTileEntity>("cache", this, this.fluidCache, this::canConnect);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    protected int anchors = 0;
    @NetworkInfo
    public byte foamed = 0;
    @NetworkInfo
    public CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);

    public PipeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.fluidCache);
        this.addNetworkFields("connectivity", "emitters", "anchors", "foamed", "storage", "cache");
    }

    private boolean isValidPipe(BlockEntity tile) {
        IFluidPipe pipe;
        return !(tile instanceof IFluidPipe) || (pipe = (IFluidPipe)tile).isFluidAcceptor();
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PIPE;
    }

    protected boolean hasFluidOutputs() {
        return true;
    }

    protected boolean overrideBlockUpdate() {
        return false;
    }

    @Override
    public boolean needsUpdateTick() {
        return super.needsUpdateTick() && this.emitters > 0;
    }

    @Override
    public void onTick() {
        if (this.emitters <= 0) {
            if (!this.needsUpdateTick()) {
                this.removeFromTick();
            }
            return;
        }
        if (!this.cache.isAdded() || this.invClock(5)) {
            return;
        }
        for (Direction dir : DirectionList.ofNumber(this.emitters)) {
            int drained;
            IFluidHandler handler = this.fluidCache.getHandler(dir);
            if (handler == null || (drained = FluidNet.INSTANCE.sendFluid(this, dir, handler.drain(this.getTransferRate(), IFluidHandler.FluidAction.SIMULATE), this.getPressure())) <= 0) continue;
            handler.drain(drained, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public int getTransferRate() {
        return 100;
    }

    public int getPressure() {
        return 25;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128473_("facing");
        NBTUtils.putByte(compound, "connections", this.connectivity, 0);
        NBTUtils.putByte(compound, "emitters", this.emitters, 0);
        NBTUtils.putByte(compound, "anchors", this.anchors, 0);
        NBTUtils.putByte(compound, "foamed", (int)this.foamed, 0);
        NBTUtils.putIf(compound, "camouflage", this.storage.save(new CompoundTag()), this.foamed > 0);
        this.cache.save(compound, this.hasFluidOutputs());
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.connectivity = compound.m_128451_("connectivity");
        this.emitters = compound.m_128451_("emitters");
        this.anchors = compound.m_128451_("anchors");
        this.foamed = compound.m_128445_("foamed");
        this.storage.load(compound.m_128469_("camouflage"));
        this.cache.load(compound);
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        NBTUtils.putByte(compound, "emitters", this.emitters, 0);
        this.cache.save(compound, this.hasFluidOutputs());
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.emitters = compound.m_128451_("emitters");
        this.cache.load(compound);
        this.updateGuiField("emitters");
        this.cache.update();
        if (this.emitters > 0) {
            this.addToTick();
        }
    }

    @Override
    public AABB hasSpecialAction(Direction side, Vec3 hit, Player player) {
        float radius = this.getCableWidth() / 16.0f;
        Direction dir = ITubeBlock.isClickingAt(hit, side, radius);
        return dir == null || !this.hasAnchor(dir) ? null : ITubeBlock.getHitBox(hit, side, radius);
    }

    @Override
    public boolean doSpecialAction(Direction side, Vec3 hit, Player player) {
        Direction dir = ITubeBlock.isClickingAt(hit, side, this.getCableWidth() / 16.0f);
        if (dir != null && this.hasAnchor(dir)) {
            this.removeAnchor(dir);
            StackUtil.addOrPop(player, this.m_58899_(), new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT));
            return true;
        }
        return false;
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.foamed == 2;
    }

    @Override
    public boolean removeCamouflage() {
        return this.changeFoam((byte)0, false);
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.foamed == 2 && this.storage.canApply(dir, container)) {
            this.storage.setTexture(DirectionList.ofFacing(dir), container);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    public boolean setColor(Direction dir, DyeColor color) {
        if (this.foamed == 2 && this.isSimulating() && this.storage.canApply(dir == null ? DirectionList.ALL : DirectionList.ofFacing(dir), color)) {
            this.storage.setColor(dir == null ? DirectionList.ALL : DirectionList.ofFacings(dir), color);
            this.updateTileField("storage");
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        BlockState dustState;
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41619_()) {
            return false;
        }
        if (stack.m_204117_(ItemTags.f_13137_) && this.foamed == 1) {
            this.changeFoam((byte)2, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        if (stack.m_150930_(IC2Blocks.CFOAM_WET.m_5456_()) && this.foamed == 0) {
            this.changeFoam((byte)1, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        if (stack.m_150930_(IC2Blocks.MINING_PIPE_SHAFT.m_5456_())) {
            Direction click = ITubeBlock.isClickingAt(hit.m_82450_().m_82492_((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_()), side, this.getCableWidth() / 16.0f);
            if (click != null) {
                this.addAnchor(click);
                if (!player.m_7500_()) {
                    stack.m_41774_(1);
                }
                return true;
            }
        } else if (this.foamed == 1 && PainterHelper.CONCRETE_DUST.contains((dustState = Block.m_49814_((Item)stack.m_41720_()).m_49966_()).m_60734_())) {
            this.storage.setColor(DirectionList.ALL, PainterHelper.INSTANCE.getColor(dustState));
            this.changeFoam((byte)2, false);
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return !this.fluidCache.isEmpty() && this.hasFluidOutputs();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new FluidPipeContainer(this, player, windowID);
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return (this.getValidDirections() & 1 << dir.m_122411_()) != 0;
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return (this.getValidDirections() & 1 << dir.m_122411_()) != 0;
    }

    @Override
    public List<IFluidPipe.FluidOutput> getOutputs() {
        return this.cache.getOutputs();
    }

    @Override
    public Iterable<Direction> getEmitterSources() {
        return DirectionList.ofNumber(this.emitters);
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("connectivity") || fields.contains("emitters") || fields.contains("cache") || fields.contains("anchors") || fields.contains("foamed") || fields.contains("storage")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        FluidPipeContainer container;
        super.onClientDataReceived(entity, key, value);
        if (key >= 0 && key <= 3 && (container = (FluidPipeContainer)this.getContainer(entity, FluidPipeContainer.class)) != null) {
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
        if (this.getValue(BlockStateProperties.f_61362_, false).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)this.f_58857_));
        } else if (this.getValue(IC2Properties.LAVA_LOGGED, false).booleanValue()) {
            this.f_58857_.m_186469_(this.m_58899_(), (Fluid)Fluids.f_76195_, Fluids.f_76195_.m_6718_((LevelReader)this.f_58857_));
        }
        this.updateConnectivity();
    }

    protected void updateConnectivity() {
        int newValue = this.cache.updateConnectivity();
        if (newValue != this.connectivity) {
            this.connectivity = newValue;
            this.updateTileField("connectivity");
        }
    }

    public boolean canConnect(Direction dir, IFluidPipe pipe) {
        return !this.hasAnchor(dir) && !pipe.hasAnchor(dir.m_122424_());
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
        if (this.hasEmitter(side)) {
            this.emitters &= ~(1 << side.m_122411_());
            this.updateTileField("emitters");
            this.cache.update();
            return true;
        }
        return false;
    }

    @Override
    public boolean hasAnchor(Direction side) {
        return (this.anchors & 1 << side.m_122411_()) != 0;
    }

    @Override
    public boolean addAnchor(Direction side) {
        if (this.hasAnchor(side)) {
            return false;
        }
        this.anchors |= 1 << side.m_122411_();
        this.updateTileField("anchors");
        this.cache.update();
        return true;
    }

    @Override
    public boolean removeAnchor(Direction side) {
        if (this.hasAnchor(side)) {
            this.anchors &= ~(1 << side.m_122411_());
            this.updateTileField("anchors");
            this.cache.update();
            return true;
        }
        return false;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        this.cache.add();
        if (this.isSimulating()) {
            this.tickFoamed();
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.cache.remove();
        super.onUnloaded(chunk);
    }

    public int getConnectivity() {
        return this.connectivity | this.anchors | this.cache.getTanks();
    }

    public int getValidDirections() {
        return this.connectivity | this.cache.getTanks();
    }

    public int getEmitters() {
        return this.emitters;
    }

    public int getAnchors() {
        return this.anchors;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        if (this.foamed == 2) {
            return ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T)).build();
        }
        return ModelData.builder().with((ModelProperty)CableProperty.INSTANCE, (Object)(this.getValidDirections() << 6 | this.anchors)).with((ModelProperty)CableProperty.PIPE_EXTRACTION, (Object)this.getEmitters()).build();
    }

    public float getCableWidth() {
        BlockState state = this.m_58900_();
        Block block = state.m_60734_();
        if (block instanceof ITubeBlock) {
            ITubeBlock tube = (ITubeBlock)block;
            return tube.getRadius(state);
        }
        return -1.0f;
    }

    public boolean changeFoam(byte level, boolean duringLoad) {
        if (this.foamed == level) {
            return false;
        }
        if (this.isSimulating()) {
            this.foamed = level;
            if (this.foamed == 1) {
                this.storage.setColor(DirectionList.ALL, DyeColor.LIGHT_GRAY);
                if (!duringLoad) {
                    this.updateTileField("storage");
                }
                this.tickFoamed();
            }
            if (!duringLoad) {
                this.updateTileField("foamed");
                this.onStateChanged();
            }
        }
        return true;
    }

    private void tickFoamed() {
        if (this.foamed != 1) {
            return;
        }
        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, new ToIntFunction<Level>(){

            @Override
            public int applyAsInt(Level world) {
                if (PipeTileEntity.this.m_58901_() || PipeTileEntity.this.foamed != 1) {
                    return 0;
                }
                if (world.m_46472_().equals((Object)Level.f_46429_)) {
                    if (world.f_46441_.m_188503_(5) == 0) {
                        PipeTileEntity.this.changeFoam((byte)2, false);
                        return 0;
                    }
                    return 20;
                }
                if (world.m_46803_(PipeTileEntity.this.f_58858_) * 6 > world.f_46441_.m_188503_(1000)) {
                    PipeTileEntity.this.changeFoam((byte)2, false);
                    return 0;
                }
                return 500;
            }
        });
    }
}

