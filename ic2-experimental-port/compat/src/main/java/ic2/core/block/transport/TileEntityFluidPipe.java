package ic2.core.block.transport;

import ic2.api.transport.IFluidPipe;
import ic2.api.transport.IPipe;
import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.block.transport.cover.ICoverItem;
import ic2.core.block.transport.items.PipeSize;
import ic2.core.block.transport.items.PipeType;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.item.block.ItemFluidPipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/**
 * IC2 2.8.222 fluid-pipe implementation on Forge's 1.19.2 fluid capability.
 * The equalisation and endpoint split below intentionally follows the old
 * server-tick bytecode, including its integer remainder distribution.
 */
public final class TileEntityFluidPipe extends BlockEntity
        implements IFluidPipe, ICoverHolder, ICapabilityProvider {
    private PipeType type;
    private PipeSize size;
    private byte connectivity;
    private byte coverMask;
    private final ItemStack[] covers = new ItemStack[Direction.values().length];
    private final PipeTank tank;
    private final PipeFluidHandler[] sideHandlers = new PipeFluidHandler[Direction.values().length];
    private final LazyOptional<IFluidHandler>[] fluidCapabilities;
    private boolean contentsDropped;

    @SuppressWarnings("unchecked")
    public TileEntityFluidPipe(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.FLUID_PIPE_BLOCK_ENTITY.get(), pos, state);
        this.type = state.m_61143_(BlockFluidPipe.TYPE);
        this.size = state.m_61143_(BlockFluidPipe.SIZE);
        for (int i = 0; i < covers.length; i++) {
            covers[i] = ItemStack.f_41583_;
            sideHandlers[i] = new PipeFluidHandler(Direction.values()[i]);
        }
        this.tank = new PipeTank(capacity(type, size));
        this.fluidCapabilities = (LazyOptional<IFluidHandler>[]) new LazyOptional<?>[covers.length];
        for (Direction side : Direction.values()) {
            int index = side.ordinal();
            fluidCapabilities[index] = LazyOptional.of(() -> sideHandlers[index]);
        }
    }

    public void configure(PipeType newType, PipeSize newSize) {
        type = newType == null ? PipeType.bronze : newType;
        size = newSize == null ? PipeSize.small : newSize;
        tank.setCapacity(capacity(type, size));
        if (tank.getFluidAmount() > tank.getCapacity()) {
            tank.getFluid().setAmount(tank.getCapacity());
        }
        updateConnectivity();
        m_6596_();
    }

    public PipeType getPipeType() {
        return type;
    }

    public PipeSize getPipeSize() {
        return size;
    }

    @Override
    public BlockEntity getTile() {
        return this;
    }

    @Override
    public boolean isConnected(Direction side) {
        return side != null && (connectivity & (1 << side.ordinal())) != 0;
    }

    @Override
    public void flipConnection(Direction side) {
        if (side == null) {
            return;
        }
        connectivity ^= (byte) (1 << side.ordinal());
        m_6596_();
        updateConnectivity();
    }

    public void setConnection(Direction side, boolean connected) {
        if (isConnected(side) != connected) {
            flipConnection(side);
        }
    }

    /** Connects only through the face behind the placement face, as 2.8 did. */
    public void onPlaced(Direction placementFace) {
        Level level = m_58904_();
        if (level == null || placementFace == null || level.f_46443_) {
            return;
        }
        Direction back = placementFace.m_122424_();
        BlockEntity neighbor = level.m_7702_(m_58899_().m_121945_(back));
        if (neighbor instanceof IPipe pipe) {
            setConnection(back, true);
            if (!pipe.isConnected(back.m_122424_())) {
                pipe.flipConnection(back.m_122424_());
            }
        } else if (getFluidHandler(neighbor, back.m_122424_()) != null) {
            setConnection(back, true);
        }
        updateConnectivity();
    }

    @Override
    public int getTransferRate() {
        return type.transferRate;
    }

    @Override
    public FluidTank getTank() {
        return tank;
    }

    @Override
    public int getCurrentInnerCapacity() {
        return tank.getFluidAmount();
    }

    @Override
    public int getMaxInnerCapacity() {
        return tank.getCapacity();
    }

    public void tickPipe() {
        Level level = m_58904_();
        if (level == null || level.f_46443_) {
            return;
        }
        tickCovers();
        distributeFluid(level);
    }

    private void distributeFluid(Level level) {
        if (tank.isEmpty() || tank.getFluidAmount() <= 0) {
            return;
        }

        int total = tank.getFluidAmount();
        int pipeCount = 1;
        List<AdjacentHandler> valid = new ArrayList<>();

        for (Direction side : Direction.values()) {
            IFluidHandler source = getExposedHandler(side);
            if (source == null || source.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE).isEmpty()) {
                continue;
            }
            BlockEntity neighbor = level.m_7702_(m_58899_().m_121945_(side));
            IFluidHandler target = getFluidHandler(neighbor, side.m_122424_());
            if (target == null) {
                continue;
            }

            if (neighbor instanceof IFluidPipe pipe) {
                int neighborAmount = pipe.getTank().getFluidAmount();
                if (tank.getFluidAmount() >= neighborAmount) {
                    total += neighborAmount;
                    pipeCount++;
                    valid.add(new AdjacentHandler(side, neighbor, target));
                }
            } else if (target.fill(tank.getFluid().copy(), IFluidHandler.FluidAction.SIMULATE) > 0) {
                valid.add(new AdjacentHandler(side, neighbor, target));
            }
        }

        int remainder = total % pipeCount;
        int average = total / pipeCount;
        List<AdjacentHandler> pipes = new ArrayList<>();

        for (var iterator = valid.iterator(); iterator.hasNext();) {
            AdjacentHandler adjacent = iterator.next();
            if (adjacent.tile instanceof IFluidPipe pipe) {
                transfer(getExposedHandler(adjacent.side), adjacent.handler,
                        average - pipe.getTank().getFluidAmount());
                pipes.add(adjacent);
                iterator.remove();
            }
        }

        if (valid.isEmpty()) {
            while (remainder > 0 && !pipes.isEmpty()) {
                int index = level.f_46441_.m_188503_(pipes.size());
                AdjacentHandler adjacent = pipes.remove(index);
                transfer(getExposedHandler(adjacent.side), adjacent.handler, 1);
                remainder--;
            }
        } else {
            average += remainder;
        }

        if (valid.isEmpty()) {
            return;
        }

        int transferBudget = Math.min(average, capacity(type, size) / 20);
        int perTarget = (int) Math.floor((float) transferBudget / (float) valid.size());
        if (perTarget <= 0) {
            while (transferBudget > 0 && !valid.isEmpty()) {
                int index = level.f_46441_.m_188503_(valid.size());
                AdjacentHandler adjacent = valid.remove(index);
                transfer(getExposedHandler(adjacent.side), adjacent.handler, 1);
                transferBudget--;
            }
        } else {
            for (AdjacentHandler adjacent : valid) {
                transfer(getExposedHandler(adjacent.side), adjacent.handler, perTarget);
            }
        }
    }

    public void updateConnectivity() {
        Level level = m_58904_();
        if (level == null) {
            return;
        }
        BlockState state = level.m_8055_(m_58899_());
        if (!(state.m_60734_() instanceof BlockFluidPipe)) {
            return;
        }

        BlockState updated = state
                .m_61124_(BlockFluidPipe.TYPE, type)
                .m_61124_(BlockFluidPipe.SIZE, size);
        for (Direction side : Direction.values()) {
            boolean visible = false;
            if (isConnected(side)) {
                BlockEntity neighbor = level.m_7702_(m_58899_().m_121945_(side));
                if (neighbor instanceof IPipe pipe) {
                    visible = pipe.isConnected(side.m_122424_());
                } else {
                    visible = getFluidHandler(neighbor, side.m_122424_()) != null;
                }
            }
            updated = updated.m_61124_(BlockFluidPipe.property(side), visible);
        }
        if (updated != state) {
            level.m_7731_(m_58899_(), updated, 2);
        }
    }

    @Override
    public Set<CoverProperty> getCoverProperties() {
        return Set.of(CoverProperty.FluidConsuming);
    }

    @Override
    public boolean canPlaceCover(Level level, BlockPos pos, Direction side, ItemStack stack) {
        return side != null
                && !hasCover(side)
                && stack != null
                && !stack.m_41619_()
                && stack.m_41720_() instanceof ICoverItem cover
                && cover.isSuitableFor(stack, getCoverProperties());
    }

    @Override
    public void placeCover(Level level, BlockPos pos, Direction side, ItemStack stack) {
        if (!canPlaceCover(level, pos, side, stack)) {
            return;
        }
        ItemStack copy = stack.m_41777_();
        copy.m_41764_(1);
        copy.m_41784_().m_128344_("side", (byte) side.ordinal());
        covers[side.ordinal()] = copy;
        coverMask |= (byte) (1 << side.ordinal());
        m_6596_();
        updateConnectivity();
    }

    @Override
    public boolean canRemoveCover(Level level, BlockPos pos, Direction side) {
        return side != null && hasCover(side);
    }

    @Override
    public void removeCover(Level level, BlockPos pos, Direction side) {
        if (!canRemoveCover(level, pos, side)) {
            return;
        }
        ItemStack removed = covers[side.ordinal()];
        covers[side.ordinal()] = ItemStack.f_41583_;
        coverMask &= (byte) ~(1 << side.ordinal());
        stripPlacementSide(removed);
        Block.m_49840_(level, pos, removed);
        m_6596_();
        updateConnectivity();
    }

    public boolean hasCover(Direction side) {
        return side != null && !covers[side.ordinal()].m_41619_();
    }

    public ItemStack getCover(Direction side) {
        return side == null ? ItemStack.f_41583_ : covers[side.ordinal()];
    }

    private void tickCovers() {
        for (Direction side : Direction.values()) {
            ItemStack stack = covers[side.ordinal()];
            if (!stack.m_41619_() && stack.m_41720_() instanceof ICoverItem cover) {
                cover.onTick(stack, this);
            }
        }
    }

    private boolean coverAllowsInput(Direction side, FluidStack fluid) {
        ItemStack stack = covers[side.ordinal()];
        if (stack.m_41619_()) {
            return true;
        }
        if (!(stack.m_41720_() instanceof ICoverItem cover)) {
            return false;
        }
        return cover.allowsInput(Ic2FluidStack.create(fluid.getFluid(), fluid.getAmount()));
    }

    private boolean coverAllowsOutput(Direction side, FluidStack fluid) {
        ItemStack stack = covers[side.ordinal()];
        if (stack.m_41619_()) {
            return true;
        }
        if (!(stack.m_41720_() instanceof ICoverItem cover)) {
            return false;
        }
        return cover.allowsOutput(Ic2FluidStack.create(fluid.getFluid(), fluid.getAmount()));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        if (capability == ForgeCapabilities.FLUID_HANDLER && side != null && isConnected(side)) {
            return fluidCapabilities[side.ordinal()].cast();
        }
        return LazyOptional.empty();
    }

    private IFluidHandler getExposedHandler(Direction side) {
        return isConnected(side) ? sideHandlers[side.ordinal()] : null;
    }

    private static IFluidHandler getFluidHandler(BlockEntity tile, Direction side) {
        if (!(tile instanceof ICapabilityProvider provider)) {
            return null;
        }
        return provider.getCapability(ForgeCapabilities.FLUID_HANDLER, side).orElse(null);
    }

    public static int transfer(IFluidHandler source, IFluidHandler target, int amount) {
        if (source == null || target == null || amount <= 0) {
            return 0;
        }
        FluidStack simulatedDrain = source.drain(amount, IFluidHandler.FluidAction.SIMULATE);
        if (simulatedDrain.isEmpty()) {
            return 0;
        }
        int accepted = target.fill(simulatedDrain, IFluidHandler.FluidAction.SIMULATE);
        if (accepted <= 0) {
            return 0;
        }
        FluidStack drained = source.drain(Math.min(amount, accepted), IFluidHandler.FluidAction.EXECUTE);
        if (drained.isEmpty()) {
            return 0;
        }
        int filled = target.fill(drained, IFluidHandler.FluidAction.EXECUTE);
        if (filled < drained.getAmount()) {
            FluidStack remainder = drained.copy();
            remainder.setAmount(drained.getAmount() - filled);
            source.fill(remainder, IFluidHandler.FluidAction.EXECUTE);
        }
        return filled;
    }

    public void dropContents(boolean placeFluid) {
        if (contentsDropped) {
            return;
        }
        contentsDropped = true;
        Level level = m_58904_();
        if (level == null) {
            return;
        }
        Block.m_49840_(level, m_58899_(), ItemFluidPipe.getPipe(type, size));
        for (Direction side : Direction.values()) {
            if (hasCover(side)) {
                ItemStack cover = covers[side.ordinal()];
                stripPlacementSide(cover);
                Block.m_49840_(level, m_58899_(), cover);
                covers[side.ordinal()] = ItemStack.f_41583_;
            }
        }
        if (placeFluid && tank.getFluidAmount() >= 1000) {
            BlockState fluidBlock = tank.getFluid().getFluid().m_76145_().m_76188_();
            if (!fluidBlock.m_60795_()) {
                level.m_7731_(m_58899_(), fluidBlock, 3);
            }
        }
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        type = PipeType.byId(tag.m_128451_("type"));
        size = PipeSize.byId(tag.m_128451_("size"));
        connectivity = tag.m_128445_("connectivity");
        tank.setCapacity(capacity(type, size));
        if (tag.m_128425_("tank", 10)) {
            tank.readFromNBT(tag.m_128469_("tank"));
        }
        for (int i = 0; i < covers.length; i++) {
            covers[i] = ItemStack.f_41583_;
        }
        coverMask = 0;
        ListTag list = tag.m_128437_("coverItems", 10);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag entry = list.m_128728_(i);
            int facing = entry.m_128445_("facing") & 255;
            if (facing >= covers.length) {
                continue;
            }
            ItemStack stack = ItemStack.m_41712_(entry);
            if (!stack.m_41619_() && stack.m_41720_() instanceof ICoverItem) {
                covers[facing] = stack;
                coverMask |= (byte) (1 << facing);
            }
        }
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128405_("type", type.ordinal());
        tag.m_128405_("size", size.ordinal());
        tag.m_128344_("connectivity", connectivity);
        tag.m_128344_("covers", coverMask);
        tag.m_128365_("tank", tank.writeToNBT(new CompoundTag()));
        ListTag list = new ListTag();
        for (Direction side : Direction.values()) {
            ItemStack stack = covers[side.ordinal()];
            if (stack.m_41619_()) {
                continue;
            }
            CompoundTag entry = new CompoundTag();
            entry.m_128344_("facing", (byte) side.ordinal());
            stack.m_41739_(entry);
            list.add(entry);
        }
        tag.m_128365_("coverItems", list);
    }

    private static int capacity(PipeType type, PipeSize size) {
        return (int) (type.transferRate * size.multiplier);
    }

    private static void stripPlacementSide(ItemStack stack) {
        CompoundTag tag = stack.m_41783_();
        if (tag == null) {
            return;
        }
        tag.m_128473_("side");
        if (tag.m_128456_()) {
            stack.m_41751_(null);
        }
    }

    private record AdjacentHandler(Direction side, BlockEntity tile, IFluidHandler handler) {
    }

    private final class PipeTank extends FluidTank {
        private PipeTank(int capacity) {
            super(capacity);
        }

        @Override
        protected void onContentsChanged() {
            TileEntityFluidPipe.this.m_6596_();
        }
    }

    private final class PipeFluidHandler implements IFluidHandler {
        private final Direction side;

        private PipeFluidHandler(Direction side) {
            this.side = side;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tankIndex) {
            return tankIndex == 0 ? tank.getFluid() : FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int tankIndex) {
            return tankIndex == 0 ? tank.getCapacity() : 0;
        }

        @Override
        public boolean isFluidValid(int tankIndex, FluidStack stack) {
            return tankIndex == 0 && tank.isFluidValid(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource == null || resource.isEmpty() || !coverAllowsInput(side, resource)) {
                return 0;
            }
            return tank.fill(resource, action);
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource == null || resource.isEmpty() || !coverAllowsOutput(side, resource)) {
                return FluidStack.EMPTY;
            }
            return tank.drain(resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (maxDrain <= 0 || tank.isEmpty() || !coverAllowsOutput(side, tank.getFluid())) {
                return FluidStack.EMPTY;
            }
            return tank.drain(maxDrain, action);
        }
    }
}
