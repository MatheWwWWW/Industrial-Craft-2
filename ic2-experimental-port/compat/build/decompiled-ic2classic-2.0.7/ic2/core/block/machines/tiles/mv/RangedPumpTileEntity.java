/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BubbleColumnBlock
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IFluidScanner;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.mv.RangedPumpContainer;
import ic2.core.block.misc.MiningPipeBlock;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.fluid.ExtractionTank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class RangedPumpTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IProgressMachine,
ITileActivityProvider,
ISimpleFluidSource {
    ExtractionTank tank = new ExtractionTank(4000);
    public int progress = 0;
    PumpTarget target = null;
    int delay = 0;
    boolean isSearching = false;
    boolean searchDone = false;
    boolean addedToFluidNet = false;
    LinkedList<PumpTarget> targets = new LinkedList();
    ICache<IFluidHandler> fluids = new CapabilityCache<IFluidHandler>(this, DirectionList.DOWN.invert(), ForgeCapabilities.FLUID_HANDLER);
    ICache<IItemHandler> inventories = new CapabilityCache<IItemHandler>(this, DirectionList.HORIZONTAL, ForgeCapabilities.ITEM_HANDLER);

    public RangedPumpTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 128, 5000);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addCaches(this.fluids, this.inventories);
        this.setFuelSlot(0);
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, (IFluidTank)this.tank));
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.DOWN.invert());
        handler.registerBlockAccess(DirectionList.DOWN.invert(), AccessRule.BOTH);
        handler.registerBlockAccess(DirectionList.DOWN, AccessRule.DISABLED);
        handler.registerSlotAccess(AccessRule.BOTH, 0, 1, 2);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 1);
        handler.registerSlotsForSide(DirectionList.HORIZONTAL, 0, 2);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(SpecialFilters.BLOCK_FILTER, 1);
        handler.registerInputFilter(new ClassFilter(IFluidScanner.class), 2);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.MINING_PIPES, 1);
        handler.registerNamedSlot(SlotType.TOOLS, 2);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("tank", (Tag)this.tank.writeToNBT(new CompoundTag()));
        compound.m_128344_("progress", (byte)this.progress);
        if (this.target != null) {
            compound.m_128365_("target", (Tag)this.target.write());
        }
        ListTag list = new ListTag();
        for (PumpTarget target : this.targets) {
            list.add((Object)target.write());
        }
        compound.m_128365_("queue", (Tag)list);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.readFromNBT(compound.m_128469_("tank"));
        this.progress = compound.m_128451_("progress");
        if (compound.m_128441_("target")) {
            CompoundTag nbt = compound.m_128469_("target");
            this.target = nbt.m_128471_("dig") ? new DownTarget(nbt) : new PumpTarget(nbt);
        }
        this.targets.clear();
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("queue", 10), CompoundTag.class)) {
            this.targets.add(new PumpTarget(nbt));
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToFluidNet) {
            FluidNet.INSTANCE.addPipe(this);
            this.addedToFluidNet = true;
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToFluidNet) {
            FluidNet.INSTANCE.removePipe(this);
            this.addedToFluidNet = false;
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.RANGED_PUMP;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new RangedPumpContainer(this, player, windowID);
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return 5.0f;
    }

    @Override
    public void onTick() {
        int drained;
        ItemStack stack;
        this.handleChargeSlot(this.maxEnergy);
        if (this.hasEnergy(1) && !(stack = (ItemStack)this.inventory.get(2)).m_41619_()) {
            this.useEnergy(ElectricItem.MANAGER.charge(stack, this.energy, 2, true, false));
        }
        this.exportFluids();
        if (this.tank.getFluidAmount() > 0 && (drained = FluidNet.INSTANCE.sendFluid(this, null, this.tank.getFluid(), 100)) > 0) {
            this.tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);
        }
        if (this.delay > 0) {
            --this.delay;
            this.setActive(false);
            this.handleComparators();
            return;
        }
        if (this.isOperating()) {
            this.work();
            this.setActive(true);
            this.handleComparators();
            return;
        }
        if (((ItemStack)this.inventory.get(2)).m_41619_() && this.hasEnergy(2) && MiningPipeBlock.isMiningPipe(this.f_58857_.m_8055_(this.f_58858_.m_7495_()))) {
            this.useEnergy(2);
            ++this.progress;
            this.targets.clear();
            this.target = null;
            if (this.progress >= 5) {
                this.progress = 0;
                this.withdrawPipe();
            }
            this.setActive(true);
            this.handleComparators();
            return;
        }
        this.setActive(false);
        this.handleComparators();
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating() && slot == 2) {
            this.progress = 0;
            this.targets.clear();
            this.target = null;
        }
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return true;
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }

    public void exportFluids() {
        InventoryHandler handler = this.getInventoryHandler();
        for (Direction dir : this.fluids) {
            IFluidHandler entry;
            if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (entry = this.fluids.getHandler(dir)) == null) continue;
            this.tank.drain(entry.fill(this.tank.drain(1000, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            if (this.tank.getFluidAmount() > 0) continue;
            return;
        }
    }

    public void withdrawPipe() {
        BlockPos target = this.getPipeTip();
        if (!this.f_58857_.m_46859_(target)) {
            List list;
            if (this.m_58899_().m_123342_() - target.m_123342_() > 0 && !this.sendItemsAway(list = Block.m_49869_((BlockState)this.f_58857_.m_8055_(target), (ServerLevel)((ServerLevel)this.f_58857_), (BlockPos)target, null))) {
                for (int i = 0; i < list.size(); ++i) {
                    Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)((ItemStack)list.get(i)));
                }
            }
            this.f_58857_.m_7471_(target, false);
        }
        if (((ItemStack)this.inventory.get(1)).m_41720_() != IC2Blocks.MINING_PIPE_SHAFT.m_5456_() && Block.m_49814_((Item)((ItemStack)this.inventory.get(1)).m_41720_()) != Blocks.f_50016_) {
            this.f_58857_.m_46597_(target, Block.m_49814_((Item)((ItemStack)this.inventory.get(1)).m_41720_()).m_49966_());
            ((ItemStack)this.inventory.get(1)).m_41774_(1);
        }
        this.updateTip(target.m_123342_() + 1);
    }

    public boolean sendItemsAway(List<ItemStack> list) {
        InventoryHandler handler = this.getInventoryHandler();
        for (Direction dir : this.inventories) {
            IItemTransporter trans;
            if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (trans = TransporterManager.getTransporter(this.inventories.getHandler(dir))) == null) continue;
            for (int i = 0; i < list.size(); ++i) {
                ItemStack stack = list.get(i);
                if (stack.m_41619_()) {
                    list.remove(i--);
                    continue;
                }
                stack.m_41774_(trans.addItem(stack, dir.m_122424_(), false));
                if (stack.m_41613_() > 0) continue;
                list.remove(i--);
            }
        }
        return list.isEmpty();
    }

    public boolean isOperating() {
        return this.hasEnergy(100) && this.canOperate();
    }

    private boolean canOperate() {
        return (((ItemStack)this.inventory.get(1)).m_41720_() == IC2Blocks.MINING_PIPE_SHAFT.m_5456_() || this.targets.size() > 0) && ((ItemStack)this.inventory.get(2)).m_41720_() instanceof IFluidScanner;
    }

    public void work() {
        if (this.target == null) {
            this.findTarget();
            if (this.target == null) {
                this.delay = 50;
            }
            return;
        }
        if (this.progress < 5) {
            ++this.progress;
            this.useEnergy(10);
            if (!this.target.isDigTarget()) {
                BlockPos pos = this.target.getPos();
                ((ServerLevel)this.f_58857_).m_8767_((ParticleOptions)ParticleTypes.f_123795_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, 5, 0.2, 0.0, 0.2, 0.2);
            }
            return;
        }
        if (!this.target.isDigTarget() && this.tank.fillInternal(this.target.getFluids(), IFluidHandler.FluidAction.SIMULATE) < this.target.getFluids().getAmount()) {
            this.delay = 5;
            return;
        }
        this.tank.fillInternal(this.target.getFluids(), IFluidHandler.FluidAction.EXECUTE);
        this.useEnergy(10);
        this.progress = 0;
        if (this.target.getPos().m_123341_() == this.m_58899_().m_123341_() && this.target.getPos().m_123343_() == this.m_58899_().m_123343_()) {
            this.f_58857_.m_46597_(this.target.getPos(), IC2Blocks.MINING_PIPE_SHAFT.m_49966_());
            ((ItemStack)this.inventory.get(1)).m_41774_(1);
            this.updateTip(this.target.getPos().m_123342_());
            this.targets.clear();
            this.target = null;
            return;
        }
        BlockState state = this.f_58857_.m_8055_(this.target.getPos());
        if (state.m_60734_() instanceof BucketPickup) {
            if (this.target.isDigTarget()) {
                this.f_58857_.m_7731_(this.target.getPos(), Blocks.f_50016_.m_49966_(), 11);
            } else {
                ((BucketPickup)state.m_60734_()).m_142598_((LevelAccessor)this.f_58857_, this.target.getPos(), state);
            }
            Level level = this.f_58857_;
            if (level instanceof ServerLevel) {
                ServerLevel server = (ServerLevel)level;
                server.m_183324_().m_193234_(BoundingBox.m_162375_((Vec3i)this.target.getPos().m_7918_(-2, -2, -2), (Vec3i)this.target.getPos().m_7918_(2, 2, 2)));
            }
        }
        if (this.target.getPos().m_123342_() <= this.getPipeTip().m_123342_()) {
            this.updateTip(this.target.getPos().m_123342_());
        }
        this.target = null;
    }

    public void updateTip(int low) {
        if (low == this.m_58899_().m_123342_()) {
            return;
        }
        BlockPos.MutableBlockPos target = this.m_58899_().m_122032_().m_122173_(Direction.DOWN);
        while (target.m_123342_() > low) {
            boolean isTip;
            BlockState state = this.f_58857_.m_8055_((BlockPos)target);
            boolean miningPipe = MiningPipeBlock.isMiningPipe(state);
            boolean bl = isTip = state.m_60734_() == IC2Blocks.MINING_PIPE_BOTTOM;
            if (!miningPipe && ((ItemStack)this.inventory.get(1)).m_41613_() > 0 || isTip) {
                this.f_58857_.m_46597_((BlockPos)target, IC2Blocks.MINING_PIPE_SHAFT.m_49966_());
                if (!miningPipe) {
                    ((ItemStack)this.inventory.get(1)).m_41774_(1);
                }
            }
            target.m_142448_(target.m_123342_() - 1);
        }
        this.f_58857_.m_46597_((BlockPos)target, IC2Blocks.MINING_PIPE_BOTTOM.m_49966_());
    }

    public void setTarget(PumpTarget target) {
        if (target.isTargetValid(this.f_58857_)) {
            this.target = target;
            return;
        }
        this.target = null;
    }

    public BlockPos getPipeTip() {
        BlockPos.MutableBlockPos pipe = this.m_58899_().m_122032_().m_122173_(Direction.DOWN);
        while (pipe.m_123342_() >= this.f_58857_.m_141937_()) {
            BlockState state = this.f_58857_.m_8055_((BlockPos)pipe);
            if (MiningPipeBlock.isMiningPipe(state)) {
                pipe.m_122173_(Direction.DOWN);
                continue;
            }
            pipe.m_122173_(Direction.UP);
            break;
        }
        return pipe.m_7949_();
    }

    public void findTarget() {
        if (this.isSearching) {
            return;
        }
        BlockPos pos = this.getPipeTip();
        if (pos.m_123342_() >= this.m_58899_().m_123342_() || !(((ItemStack)this.inventory.get(2)).m_41720_() instanceof IFluidScanner)) {
            BlockState state = this.f_58857_.m_8055_(pos = pos.m_7495_());
            this.setTarget(this.hasFluids(state, pos) ? new PumpTarget(pos, state, new FluidStack(state.m_60819_().m_76152_(), 1000)) : new DownTarget(pos));
            return;
        }
        if (this.targets.size() > 0) {
            this.setTarget(this.targets.removeFirst());
            return;
        }
        if (!this.searchDone) {
            this.findTargets(pos, (ItemStack)this.inventory.get(2));
        }
        if (this.isSearching) {
            return;
        }
        this.searchDone = false;
        if (this.targets.size() > 0) {
            this.setTarget(this.targets.removeFirst());
            return;
        }
        BlockState state = this.f_58857_.m_8055_(pos = pos.m_7495_());
        this.setTarget(this.hasFluids(state, pos) ? new PumpTarget(pos, state, new FluidStack(state.m_60819_().m_76152_(), 1000)) : new DownTarget(pos));
    }

    public boolean hasFluids(BlockState state, BlockPos pos) {
        Block block = state.m_60734_();
        if (!(block instanceof LiquidBlock) && !(block instanceof BubbleColumnBlock)) {
            return false;
        }
        FluidState fluid = state.m_60819_();
        return fluid.m_76152_() != Fluids.f_76191_ && fluid.m_76170_();
    }

    public void findTargets(BlockPos start, ItemStack stack) {
        IFluidScanner scanner = (IFluidScanner)stack.m_41720_();
        int radius = scanner.getScanRadius(stack, true);
        if (radius <= 0) {
            return;
        }
        this.isSearching = true;
        AABBUtil.createOffthreadTask(this.f_58857_, start, Box.fromPos(start, this.m_58899_()).expand(DirectionList.HORIZONTAL, radius), (AABBUtil.IBlockFilter)new PumpSearch(scanner, stack), 32, DirectionList.DOWN.invert(), Integer.MAX_VALUE, T -> {
            for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(T.getMappedPositions())) {
                for (BlockPos pos : (List)entry.getValue()) {
                    BlockState state = this.f_58857_.m_8055_(pos);
                    if (this.hasFluids(state, pos)) {
                        this.targets.add(new PumpTarget(pos, state, new FluidStack(state.m_60819_().m_76152_(), 1000)));
                        continue;
                    }
                    this.targets.add(new DownTarget(pos));
                }
            }
            this.isSearching = false;
            this.searchDone = true;
        });
    }

    public static class PumpTarget {
        BlockPos pos;
        BlockState state;
        FluidStack fluid;

        PumpTarget() {
        }

        public PumpTarget(CompoundTag compound) {
            this.pos = BlockPos.m_122022_((long)compound.m_128454_("pos"));
            this.fluid = FluidStack.loadFluidStackFromNBT((CompoundTag)compound.m_128469_("fluid_data"));
            this.state = NbtUtils.m_129241_((CompoundTag)compound.m_128469_("block_data"));
        }

        public PumpTarget(BlockPos pos, BlockState state, FluidStack fluid) {
            this.pos = pos;
            this.state = state;
            this.fluid = fluid;
        }

        public CompoundTag write() {
            CompoundTag data = new CompoundTag();
            data.m_128356_("pos", this.pos.m_121878_());
            data.m_128365_("block_data", (Tag)NbtUtils.m_129202_((BlockState)this.state));
            data.m_128365_("fluid_data", (Tag)this.fluid.writeToNBT(new CompoundTag()));
            data.m_128379_("dig", false);
            return data;
        }

        public boolean isValid() {
            return !this.fluid.isEmpty() && this.state.m_60734_() != Blocks.f_50016_;
        }

        public boolean isTargetValid(Level world) {
            return world.m_8055_(this.pos) == this.state;
        }

        public boolean isDigTarget() {
            return false;
        }

        public FluidStack getFluids() {
            return this.fluid;
        }

        public BlockPos getPos() {
            return this.pos;
        }
    }

    public static class DownTarget
    extends PumpTarget {
        public DownTarget(CompoundTag nbt) {
            this.pos = BlockPos.m_122022_((long)nbt.m_128454_("pos"));
        }

        public DownTarget(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public boolean isDigTarget() {
            return true;
        }

        @Override
        public boolean isTargetValid(Level world) {
            if (world.m_46859_(this.pos)) {
                return true;
            }
            FluidState state = world.m_6425_(this.pos);
            return state.m_76152_() != Fluids.f_76191_ && !state.m_76170_();
        }

        @Override
        public FluidStack getFluids() {
            return FluidStack.EMPTY;
        }

        @Override
        public CompoundTag write() {
            CompoundTag data = new CompoundTag();
            data.m_128356_("pos", this.pos.m_121878_());
            data.m_128379_("dig", true);
            return data;
        }
    }

    public static class PumpSearch
    implements AABBUtil.IBlockMapper {
        IFluidScanner scanner;
        ItemStack stack;

        public PumpSearch(IFluidScanner scanner, ItemStack stack) {
            this.scanner = scanner;
            this.stack = stack;
        }

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60734_() == Blocks.f_50016_ || state.m_60734_() instanceof BucketPickup;
        }

        @Override
        public void mapBlocks(LevelReader reader, BlockPos pos, Map<Block, List<BlockPos>> mapper) {
            BlockState state = reader.m_8055_(pos);
            if (state.m_60734_() == Blocks.f_50016_) {
                return;
            }
            if (state.m_60734_() instanceof BucketPickup) {
                FluidState fluid = state.m_60819_();
                if (this.scanner.isValuableFluid(this.stack, fluid, reader, pos)) {
                    AABBUtil.mapResult(state.m_60734_(), pos, mapper);
                } else if (fluid.m_76152_() != Fluids.f_76191_ && !fluid.m_76170_()) {
                    AABBUtil.mapResult(state.m_60734_(), pos, mapper);
                }
            }
        }
    }
}

