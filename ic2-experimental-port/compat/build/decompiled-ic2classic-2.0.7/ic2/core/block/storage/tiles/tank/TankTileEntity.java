/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.model.data.ModelData
 *  net.minecraftforge.client.model.data.ModelProperty
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.storage.tiles.tank;

import ic2.api.events.RetextureEvent;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.blocks.ValveBlock;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ICamouflagable;
import ic2.core.block.base.features.multiblock.IMultiBlockClickable;
import ic2.core.block.base.features.multiblock.IMultiBlockFluidExporter;
import ic2.core.block.base.misc.BucketFiller;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.rendering.camouflage.CamouflageStorage;
import ic2.core.block.rendering.camouflage.shape.CamouflageShape;
import ic2.core.block.rendering.props.CamouflageProperty;
import ic2.core.block.storage.container.TankContainer;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.fluid.IC2Tank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.StructureBuilder;
import ic2.core.utils.math.geometry.Box;
import ic2.core.utils.math.geometry.Vec2i;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TankTileEntity
extends BaseMultiBlockTileEntity
implements ITileGui,
IMultiBlockClickable,
IMultiBlockFluidExporter,
ICamouflagable {
    static final Vec2i MAX_BOUNDS = new Vec2i(50, 200);
    public static final Predicate<BlockState> VALID_BLOCKS = T -> T.m_204336_(IC2Tags.TANK_STRUCTURE) || T.m_60734_() == IC2Blocks.TANK || T.m_60734_() instanceof ValveBlock;
    public static final Predicate<BlockState> STRUCTURE_BLOCKS = T -> T.m_204336_(IC2Tags.TANK_STRUCTURE) && !T.m_204336_(Tags.Blocks.GLASS);
    @NetworkInfo
    public IC2Tank tank;
    @NetworkInfo
    public CamouflageStorage storage = new CamouflageStorage(CamouflageShape.FULL_CUBE);
    @NetworkInfo
    public boolean textureCopied = false;
    protected BucketFiller filler = new BucketFiller(this, (IFluidHandler)this.tank, 0, 1);
    protected BucketFiller drainer = new BucketFiller(this, (IFluidHandler)this.tank, 2, 3);

    public TankTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.addNetworkFields("tank", "children", "storage", "textureCopied");
        this.tank.addListener(T -> {
            this.updateTileField("tank");
            this.updateGuiField("tank");
        });
        this.tank.addListener(T -> this.handleNonTickComparators());
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, (IFluidTank)this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        this.tank = new IC2Tank(0);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerInputFilter(new FluidFilter.FluidTankFillFilter((IFluidHandler)this.tank), 2);
        handler.registerInputFilter(new FluidFilter.FluidTankFilter((IFluidHandler)this.tank), 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 0, 2);
        handler.registerSlotAccess(AccessRule.EXPORT, 1, 3);
        handler.registerSlotsForSide(DirectionList.ALL, 0, 1, 2, 3);
        handler.registerNamedSlot(SlotType.INPUT, 0, 2);
        handler.registerNamedSlot(SlotType.OUTPUT, 1, 3);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TANK;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.put(compound, "tank", this.tank.writeToNBT(new CompoundTag()));
        NBTUtils.putBoolean(compound, "tex", this.textureCopied, false);
        if (this.textureCopied) {
            NBTUtils.put(compound, "storage", this.storage.save(new CompoundTag()));
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.readFromNBT(compound.m_128469_("tank"));
        this.textureCopied = compound.m_128471_("tex");
        if (this.textureCopied) {
            this.storage.load(compound.m_128469_("storage"));
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("storage")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public CamouflageStorage getStorage() {
        return this.storage;
    }

    @Override
    public boolean applyTexture(Direction dir, RetextureEvent.TextureContainer container) {
        if (this.storage.canApply(DirectionList.ALL, container)) {
            this.storage.setTexture(DirectionList.ALL, container);
            this.textureCopied = true;
            this.updateTileFields("storage", "textureCopied");
            return true;
        }
        return false;
    }

    @Override
    public boolean isSideEnabled(Direction dir) {
        return this.textureCopied;
    }

    @Override
    public boolean removeCamouflage() {
        return false;
    }

    @OnlyIn(value=Dist.CLIENT)
    public ModelData getModelData() {
        return this.textureCopied ? ModelData.builder().with((ModelProperty)CamouflageProperty.INSTANCE, T -> this.storage.getQuads(this.m_58900_(), this.m_58904_(), this.m_58899_(), (RenderType)T)).build() : ModelData.EMPTY;
    }

    public AABB getBox(float progress) {
        AABB box = this.boundingBox.toInclusiveAxis().m_82406_(1.0);
        return box.m_82310_(0.0, Math.max((double)0.001f, (box.f_82292_ - box.f_82289_) * (double)(1.0f - progress)), 0.0).m_82386_((double)(-this.f_58858_.m_123341_()), (double)(-this.f_58858_.m_123342_()), (double)(-this.f_58858_.m_123343_()));
    }

    public BlockPos getCenter() {
        return new BlockPos(this.boundingBox.getMinX() + this.boundingBox.getWidth() / 2, this.boundingBox.getMinY() + 1, this.boundingBox.getMinZ() + this.boundingBox.getDepth() / 2);
    }

    @Override
    public boolean doesNeedTickAfterValidation() {
        return !((ItemStack)this.inventory.get(0)).m_41619_() || !((ItemStack)this.inventory.get(2)).m_41619_();
    }

    @Override
    public void onStructureValidated(boolean revalidation) {
        super.onStructureValidated(revalidation);
        this.tank.setCapacity(this.boundingBox.copy().expand(DirectionList.ALL, -1).size() * 16000);
        this.updateTileField("tank");
    }

    @Override
    protected void onInitialValidation() {
        this.updateTileField("children");
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TankContainer(this, player, windowID);
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.isValid;
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.isValid;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && (slot == 0 || slot == 2) && !stack.m_41619_()) {
            this.addToTick();
        }
    }

    @Override
    public void onStructureTick() {
        this.filler.fillTank();
        this.drainer.fillItem();
        if (!this.doesNeedTickAfterValidation()) {
            this.removeFromTick();
        }
        this.handleComparators();
    }

    @Override
    public void exportFluids(ICache<IFluidHandler> cache, IFluidPipe pump, Direction pumpSide) {
        if (!this.tank.isEmpty() && !cache.isEmpty()) {
            for (Direction dir : cache) {
                IFluidHandler handler = cache.getHandler(dir);
                if (handler == null || this.tank.drain(handler.fill(new FluidStack(this.tank.getFluid(), Math.min(2000, this.tank.getFluidAmount())), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE).getAmount() <= 0 || !this.tank.isEmpty()) continue;
                return;
            }
        }
        if (this.tank.isEmpty() || pump == null) {
            return;
        }
        this.tank.drain(FluidNet.INSTANCE.sendFluid(pump, pumpSide, new FluidStack(this.tank.getFluid(), Math.min(2000, this.tank.getFluidAmount())), 100), IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        return this.boundingBox.isEdge(structurePos) ? STRUCTURE_BLOCKS.test(newState) : VALID_BLOCKS.test(newState);
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        if (!builder.forward().isAir()) {
            return false;
        }
        Box box = new Box(0, 0, 0, 0, 0, 0).offset(this.getFacing().m_122424_());
        if (this.rayTest(builder.getFacing(), builder.resetPosition(), box) || this.rayTest(builder.getFacing().m_122427_(), builder.resetPosition(), box) || this.rayTest(builder.getFacing().m_122428_(), builder.resetPosition(), box) || this.rayTest(Direction.DOWN, builder.resetPosition(), box) || this.rayTest(Direction.UP, builder.resetPosition(), box)) {
            return false;
        }
        for (BlockPos pos : box) {
            if (builder.setOffset(pos).isAir()) continue;
            return false;
        }
        box.expand(DirectionList.ALL, 1);
        for (BlockPos pos : box.getHollowIterator()) {
            BlockState state = builder.setOffset(pos).getState();
            if (!(box.isEdge(pos) ? STRUCTURE_BLOCKS : VALID_BLOCKS).test(state) || !Block.m_49916_((VoxelShape)state.m_60808_((BlockGetter)this.f_58857_, pos.m_121955_((Vec3i)this.m_58899_())))) {
                return false;
            }
            if (!(state.m_60734_() instanceof ValveBlock)) continue;
            this.children.add(builder.getTile(BaseLinkingTileEntity.class));
        }
        builder.resetPosition().removeMark();
        return true;
    }

    protected boolean rayTest(Direction rotation, StructureBuilder builder, Box box) {
        int steps;
        builder.forward();
        int max = rotation.m_122434_().m_122479_() ? MAX_BOUNDS.getX() : MAX_BOUNDS.getY();
        for (steps = 0; steps <= max && builder.offset(rotation).isAir(); ++steps) {
        }
        if (steps >= max || !builder.isState(VALID_BLOCKS)) {
            return true;
        }
        box.expand(rotation, steps);
        return false;
    }
}

