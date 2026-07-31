/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMap$Entry
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.world.ForgeChunkManager
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.items.electric.IMiningDrill;
import ic2.api.items.electric.IScanner;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IWorkProvider;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.InventoryComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.machines.containers.hv.RocketMinerContainer;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.core.block.misc.MiningPipeBlock;
import ic2.core.entity.rockets.MiningRocketEntity;
import ic2.core.fluid.LayeredFluidTank;
import ic2.core.fluid.WrapperFluidHandler;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.ClassFilter;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.filter.SimpleFilter;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.item.tool.MinerRemoteItem;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Entities;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.objects.Object2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import java.util.List;
import java.util.OptionalLong;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class RocketMinerTileEntity
extends MinerTileEntity
implements IWorkProvider {
    public OptionalLong afterRecharge = OptionalLong.empty();
    public int finished = 0;
    public Direction nextDirection = Direction.SOUTH;
    public int maxJumps = 1;
    public int jumpProgress = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int storedMiningPipes = 0;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    public int storedCells = 0;
    public LayeredFluidTank tank = new LayeredFluidTank(128000);
    IHasInventory pipeInventory = new RangedInventory(this, 3, 4);
    IHasInventory cellInventory = new RangedInventory(this, 7, 8);
    IHasInventory dropsInventory = new RangedInventory(this, MathUtils.fromTo(11, 47));
    UUID currentChunkLoaderID = UUID.randomUUID();
    UUID nextChunkLoaderID = UUID.randomUUID();
    Object2LongMap<UUID> loadedChunks = new Object2LongLinkedOpenHashMap();
    boolean ignoreChunkRemoval = false;

    public RocketMinerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 47, 512, 10000);
        this.setFuelSlot(0);
        this.addGuiFields("storedMiningPipes", "storedCells");
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, new WrapperFluidHandler.DrainHandlerWrapper(this.tank));
        this.addComparator(FlagComparator.createWorker("refueled", ComparatorNames.REFUELED, this));
        this.addComparator(new InventoryComparator("cell_slots", ComparatorNames.CELL_FILLED_SLOTS, this.cellInventory, true));
        this.addComparator(new InventoryComparator("cell_items", ComparatorNames.CELL_FILLED_INVENTORY, this.cellInventory, false));
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        int[] logic = MathUtils.fromTo(0, 10);
        int[] slots = MathUtils.fromTo(11, 47);
        handler.registerSlotAccess(AccessRule.EXPORT, slots);
        handler.registerSlotAccess(AccessRule.BOTH, logic);
        handler.registerSlotsForSide(DirectionList.ALL, slots);
        handler.registerSlotsForSide(DirectionList.ALL, logic);
        handler.registerInputFilter(ElectricItemFilter.DISCHARGE_EXTERNAL_FILTER, 0);
        handler.registerInputFilter(new ClassFilter(IScanner.class), 1);
        handler.registerInputFilter(new ClassFilter(IMiningDrill.class), 2);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Blocks.MINING_PIPE_SHAFT), 3, 4);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Blocks.RECYCLER), 5);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.COBBLEGEN_UPGRADE), 6);
        handler.registerInputFilter(FluidFilter.EMPTY_CONTAINER, 7, 8);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Items.MINER_REMOTE), 9);
        handler.registerInputFilter(new SimpleFilter((ItemLike)IC2Blocks.CHUNKLOADER), 10);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.SCANNER, 1);
        handler.registerNamedSlot(SlotType.TOOLS, 2);
        handler.registerNamedSlot(SlotType.MINING_PIPES, 3, 4);
        handler.registerNamedSlot(SlotType.RECYCLER, 5);
        handler.registerNamedSlot(SlotType.FILLER, 6);
        handler.registerNamedSlot(SlotType.CONTAINER, 7, 8);
        handler.registerNamedSlot(SlotType.REFUEL_REMOTE, 9);
        handler.registerNamedSlot(SlotType.CHUNKLOADER, 10);
        handler.registerNamedSlot(SlotType.STORAGE, slots);
    }

    @Override
    public int getMaxStackSize(int slot) {
        if (slot == 0) {
            return 1;
        }
        return super.getMaxStackSize(slot);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ROCKET_MINER;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128344_("finished", (byte)this.finished);
        compound.m_128344_("next_dir", (byte)this.nextDirection.m_122416_());
        compound.m_128405_("jump", this.jumpProgress);
        compound.m_128405_("max_jumps", this.maxJumps);
        if (this.afterRecharge.isPresent()) {
            compound.m_128356_("after_recharge", this.afterRecharge.getAsLong());
        }
        compound.m_128362_("current_id", this.currentChunkLoaderID);
        compound.m_128362_("next_id", this.nextChunkLoaderID);
        compound.m_128405_("pipes", this.storedMiningPipes);
        compound.m_128405_("cells", this.storedCells);
        NBTUtils.put(compound, "tank", this.tank.write(new CompoundTag()));
        ListTag list = new ListTag();
        for (Object2LongMap.Entry entry : this.loadedChunks.object2LongEntrySet()) {
            CompoundTag data = new CompoundTag();
            data.m_128356_("pos", entry.getLongValue());
            data.m_128362_("id", (UUID)entry.getKey());
            list.add((Object)data);
        }
        compound.m_128365_("loaded_chunks", (Tag)list);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.finished = compound.m_128451_("finished");
        this.nextDirection = Direction.m_122407_((int)compound.m_128451_("next_dir"));
        this.jumpProgress = compound.m_128451_("jump");
        this.maxJumps = compound.m_128451_("max_jumps");
        this.storedCells = compound.m_128451_("cells");
        this.storedMiningPipes = compound.m_128451_("pipes");
        this.afterRecharge = compound.m_128441_("after_recharge") ? OptionalLong.of(compound.m_128454_("after_recharge")) : OptionalLong.empty();
        this.tank.read(compound.m_128469_("tank"));
        if (compound.m_128403_("current_id")) {
            this.currentChunkLoaderID = compound.m_128342_("current_id");
            this.nextChunkLoaderID = compound.m_128342_("next_id");
            for (CompoundTag data : NBTListWrapper.wrap(compound.m_128437_("loaded_chunks", 10), CompoundTag.class)) {
                this.loadedChunks.put((Object)data.m_128342_("id"), data.m_128454_("pos"));
            }
        }
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        StackUtil.expand(new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT), this.storedMiningPipes, drops);
        StackUtil.expand(new ItemStack((ItemLike)IC2Items.CELL_EMPTY), this.storedCells, drops);
    }

    @Override
    public int getDrillSlot() {
        return 2;
    }

    @Override
    protected int getMiningPipeSlot() {
        return 3;
    }

    @Override
    public boolean isWorking() {
        return this.isRefueling() && this.hasCells();
    }

    @Override
    protected void drainMiningPipe(int amount) {
        int last = this.storedMiningPipes;
        this.storedMiningPipes = Mth.m_14045_((int)(this.storedMiningPipes - amount), (int)0, (int)512);
        if (last == this.storedMiningPipes && amount < 0) {
            Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT));
        }
        this.updateGuiField("storedMiningPipes");
    }

    @Override
    protected boolean hasMiningPipe() {
        return this.storedMiningPipes > 0;
    }

    public boolean hasCells() {
        return this.storedCells < 1024;
    }

    public boolean isRefueled() {
        if (this.energy <= this.maxEnergy - 100) {
            return false;
        }
        if (((ItemStack)this.inventory.get(0)).m_41619_() || ElectricItem.MANAGER.getCharge((ItemStack)this.inventory.get(0)) < ElectricItem.MANAGER.getCapacity((ItemStack)this.inventory.get(0))) {
            return false;
        }
        int m = this.dropsInventory.getSlotCount();
        for (int i = 0; i < m; ++i) {
            if (this.dropsInventory.getStackInSlot(i).m_41619_()) continue;
            return false;
        }
        return true;
    }

    @Override
    public int getEnergyUsage() {
        return super.getEnergyUsage() + (this.isChunkLoading() ? 15 : 0);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 1) {
            if (this.storedMiningPipes > 0) {
                while (this.storedMiningPipes > 0) {
                    int left = Math.min(this.storedMiningPipes, 64);
                    this.storedMiningPipes -= left;
                    StackUtil.addOrDrop(entity, new ItemStack((ItemLike)IC2Blocks.MINING_PIPE_SHAFT, left));
                }
                this.storedMiningPipes = 0;
                this.updateGuiField("storedMiningPipes");
            }
        } else if (key == 2 && this.storedCells > 0) {
            while (this.storedCells > 0) {
                int left = Math.min(this.storedCells, 64);
                this.storedCells -= left;
                StackUtil.addOrDrop(entity, new ItemStack((ItemLike)IC2Items.CELL_EMPTY, left));
            }
            this.storedCells = 0;
            this.updateGuiField("storedCells");
        }
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new RocketMinerContainer(this, player, windowID);
    }

    public boolean isRefueling() {
        if (((ItemStack)this.inventory.get(9)).m_41619_() && this.afterRecharge.isPresent()) {
            return true;
        }
        BlockPos refuel = this.getRefuelPosition();
        return refuel != null && this.f_58858_.m_123341_() == refuel.m_123341_() && this.f_58858_.m_123343_() == refuel.m_123343_();
    }

    public boolean isChunkLoading() {
        return ((ItemStack)this.inventory.get(10)).m_41720_() == IC2Blocks.CHUNKLOADER.m_5456_();
    }

    public void onLanded(boolean triggerChunkload) {
        if (triggerChunkload && this.isChunkLoading()) {
            this.unloadChunks(this.loadedChunks.removeLong((Object)this.currentChunkLoaderID), this.currentChunkLoaderID);
            this.loadChunks(this.loadedChunks.getLong((Object)this.nextChunkLoaderID), this.currentChunkLoaderID);
            this.unloadChunks(this.loadedChunks.removeLong((Object)this.nextChunkLoaderID), this.nextChunkLoaderID);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onTick() {
        boolean operating;
        block27: {
            block26: {
                if (!this.isRefueling() && this.handleChargeSlot((int)((float)this.maxEnergy * 0.9f)) && this.energy >= 5000 && this.finished >= 2 && this.finished <= 3) {
                    this.finished = 0;
                }
                if (this.clock(10)) {
                    int drained = this.drainCount(this.cellInventory, 1024 - this.storedCells);
                    if (drained > 0) {
                        this.storedCells += drained;
                        this.updateGuiField("storedCells");
                    }
                    if ((drained = this.drainCount(this.pipeInventory, 512 - this.storedMiningPipes)) > 0) {
                        this.storedMiningPipes += drained;
                        this.updateGuiField("storedMiningPipes");
                    }
                }
                if (this.hasEnergy(100)) {
                    if (this.isRefueling()) {
                        this.energy -= ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(0), this.energy, Integer.MAX_VALUE, true, false);
                    }
                    if (((ItemStack)this.inventory.get(this.getScannerSlot())).m_41720_() instanceof IElectricItem) {
                        this.energy -= ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(this.getScannerSlot()), this.energy, Integer.MAX_VALUE, true, false);
                    }
                    if (((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_() instanceof IElectricItem) {
                        this.energy -= ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(this.getDrillSlot()), this.energy, Integer.MAX_VALUE, true, false);
                    }
                }
                if (!this.isChunkLoading()) break block26;
                if (this.useEnergy(15) == 0) {
                    this.clearLoadedChunks();
                    break block27;
                } else if (this.loadedChunks.isEmpty() && !this.isRefueling()) {
                    this.loadChunks(new ChunkPos(this.m_58899_()).m_45588_(), this.currentChunkLoaderID);
                }
                break block27;
            }
            this.clearLoadedChunks();
        }
        if (this.finished >= 3) {
            return;
        }
        if (this.isRefueling()) {
            this.handleRedstone();
            if (this.getRedstoneStrength() == 15) {
                if (this.isChunkLoading() && this.loadedChunks.isEmpty()) {
                    this.loadChunks(new ChunkPos(this.getPreviousTarget()).m_45588_(), this.currentChunkLoaderID);
                }
                MiningRocketEntity rocket = new MiningRocketEntity(IC2Entities.ROCKET_MINER, this.f_58857_, this.m_58899_(), false);
                rocket.setTarget(this.getPreviousTarget());
                this.afterRecharge = OptionalLong.empty();
                rocket.nbt = this.m_187482_();
                this.ignoreChunkRemoval = true;
                this.f_58857_.m_46597_(this.m_58899_(), Blocks.f_50016_.m_49966_());
                this.f_58857_.m_7967_((Entity)rocket);
            }
            this.handleComparators();
            return;
        }
        if (!this.handleChargeSlot((int)((float)this.maxEnergy * 0.9f)) && this.energy <= 4000 || this.isNearEmpty((ItemStack)this.inventory.get(0))) {
            this.finished = 2;
        }
        if (operating = this.isOperating()) {
            boolean wasNull = this.current == null;
            this.work();
            if (this.current == null && wasNull && !this.hasMiningPipe()) {
                this.finished = 1;
            }
        } else if (this.current == null && this.finished <= 0 && !this.hasMiningPipe()) {
            this.finished = 1;
        } else if ((this.finished >= 1 || this.getStackInSlot(this.getScannerSlot()).m_41619_()) && this.hasEnergy(1)) {
            if (!MiningPipeBlock.isMiningPipe(this.f_58857_.m_8055_(this.m_58899_().m_7495_()))) {
                this.launchRocket(this.getRefuelPosition());
                return;
            }
            operating = true;
            this.stuckOn = null;
            this.current = null;
            ++this.progress;
            this.useEnergy(1);
            if (this.progress >= 5) {
                this.progress = 0;
                this.withdrawPipe();
            }
            this.queue.clear();
        } else if (this.isStuck() && this.hasEnergy(2) && this.finished == 0) {
            this.progress = 0;
            this.finished = 1;
        }
        this.setActive(operating);
        this.handleComparators();
    }

    public boolean isNearEmpty(ItemStack stack) {
        return ElectricItem.MANAGER.getCharge(stack) < (int)Math.min(10000.0, (double)ElectricItem.MANAGER.getCapacity(stack) * 0.02);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.isSimulating()) {
            if (slot == 1) {
                this.finished = 0;
            } else if (slot == 0 && this.finished == 3) {
                this.finished = 0;
            }
        }
    }

    private int drainCount(IHasInventory inventory, int limit) {
        if (limit <= 0) {
            return 0;
        }
        int result = 0;
        int m = inventory.getSlotCount();
        for (int i = 0; i < m; ++i) {
            int left;
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.m_41619_() || (left = Math.min(limit - result, stack.m_41613_())) <= 0) continue;
            result += left;
            stack.m_41774_(left);
        }
        return result;
    }

    protected void launchRocket(BlockPos refuel) {
        if (this.finished == 2) {
            if (refuel == null) {
                this.finished = 3;
                return;
            }
            this.afterRecharge = OptionalLong.of(this.m_58899_().m_121878_());
            MiningRocketEntity rocket = new MiningRocketEntity(IC2Entities.ROCKET_MINER, this.f_58857_, this.m_58899_(), false);
            rocket.setTarget(refuel);
            rocket.nbt = this.m_187482_();
            this.ignoreChunkRemoval = true;
            this.f_58857_.m_46597_(this.m_58899_(), Blocks.f_50016_.m_49966_());
            this.f_58857_.m_7967_((Entity)rocket);
            return;
        }
        ItemStack stack = (ItemStack)this.inventory.get(this.getScannerSlot());
        Item item = stack.m_41720_();
        if (!(item instanceof IScanner)) {
            this.finished = 3;
            return;
        }
        IScanner scanner = (IScanner)item;
        int radius = scanner.getScanRadius(stack, false) * 2 + 1;
        Vec3i offset = this.nextDirection.m_122436_();
        BlockPos launchPos = this.m_58899_().m_7918_(offset.m_123341_() * radius, offset.m_123342_() * radius, offset.m_123343_() * radius);
        ++this.jumpProgress;
        if (this.jumpProgress == this.maxJumps) {
            this.jumpProgress = 0;
            this.nextDirection = this.nextDirection.m_122427_();
            this.maxJumps += this.nextDirection.m_122416_() % 2;
        }
        if (this.isChunkLoading()) {
            this.loadChunks(new ChunkPos(launchPos).m_45588_(), this.nextChunkLoaderID);
        }
        MiningRocketEntity rocket = new MiningRocketEntity(IC2Entities.ROCKET_MINER, this.f_58857_, this.m_58899_(), true);
        rocket.setTarget(launchPos);
        rocket.nbt = this.m_187482_();
        this.ignoreChunkRemoval = true;
        this.f_58857_.m_46597_(this.m_58899_(), Blocks.f_50016_.m_49966_());
        this.f_58857_.m_7967_((Entity)rocket);
    }

    @Override
    public void withdrawPipe() {
        BlockPos target = this.getPipeTip();
        if (!this.f_58857_.m_46859_(target)) {
            if (this.m_58899_().m_123342_() - target.m_123342_() > 0) {
                this.drainMiningPipe(-1);
            }
            this.f_58857_.m_7471_(target, false);
        }
        if (!((ItemStack)this.inventory.get(6)).m_41619_()) {
            this.f_58857_.m_46597_(target, Blocks.f_50652_.m_49966_());
        }
        this.updateTip(target.m_123342_() + 1);
    }

    @Override
    public void mineBlock(MinerTileEntity tile) {
        ((IMiningDrill)((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_()).onDrillUsed((ItemStack)this.inventory.get(this.getDrillSlot()));
        BlockPos miningPos = this.mining.getPos();
        BlockState state = this.f_58857_.m_8055_(miningPos);
        boolean valuable = ((IScanner)((ItemStack)this.inventory.get(this.getScannerSlot())).m_41720_()).isOreValuable((ItemStack)this.inventory.get(this.getScannerSlot()), state, (LevelReader)this.f_58857_, miningPos);
        List<ItemStack> list = this.mining.createDrops(this);
        if (valuable || ((ItemStack)this.inventory.get(5)).m_41619_()) {
            this.sendItemsAway(TransporterManager.getTransporter(this.dropsInventory), list, true);
        } else {
            this.recycle(StackUtil.countItems(list));
            list.clear();
        }
        if (miningPos.m_123341_() == this.m_58899_().m_123341_() && miningPos.m_123343_() == this.m_58899_().m_123343_()) {
            this.f_58857_.m_46597_(miningPos, IC2Blocks.MINING_PIPE_BOTTOM.m_49966_());
            this.drainMiningPipe(1);
            this.useEnergy(10);
        }
        this.updateTip(miningPos.m_123342_());
        if (miningPos.m_123342_() != this.getPipeTip().m_123342_()) {
            this.current = null;
            this.mining = null;
            this.queue.clear();
            return;
        }
        BlockPos targetPos = this.current.getPos();
        if (miningPos.equals((Object)targetPos) || !this.f_58857_.m_46749_(targetPos) || this.f_58857_.m_46859_(targetPos) || !this.isOreValuable(targetPos)) {
            this.current = null;
            this.mining = null;
        } else {
            this.mining = this.current;
        }
    }

    protected void recycle(int times) {
        this.useEnergy(times * 100);
        int count = 0;
        for (int i = 0; i < times; ++i) {
            if (this.f_58857_.f_46441_.m_188503_(4) != 0) continue;
            ++count;
        }
        IItemTransporter transporter = TransporterManager.getTransporter(this.dropsInventory);
        while (count > 0) {
            int removed = Math.min(64, count);
            count -= removed;
            transporter.addItem(new ItemStack((ItemLike)IC2Items.SCRAP, removed), null, false);
        }
        if (this.hasEnergy(100)) {
            ItemStack scrap;
            int scrapCount;
            while ((scrapCount = (scrap = transporter.removeItem(new SimpleFilter((ItemLike)IC2Items.SCRAP), null, 63, true)).m_41613_() / 9) > 0) {
                transporter.removeItem(new SimpleFilter((ItemLike)IC2Items.SCRAP), null, scrapCount * 9, false);
                transporter.addItem(new ItemStack((ItemLike)IC2Items.SCRAPBOX, scrapCount), null, false);
                this.useEnergy(100 * scrapCount);
            }
        }
    }

    protected void sendItemsAway(IItemTransporter transporter, List<ItemStack> items, boolean setStuck) {
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = items.get(i);
            if (stack.m_41619_()) continue;
            stack.m_41774_(transporter.addItem(stack, null, false));
            if (stack.m_41613_() <= 0) continue;
            Block.m_49840_((Level)this.m_58904_(), (BlockPos)this.m_58899_(), (ItemStack)stack);
            if (!setStuck) continue;
            this.finished = 2;
        }
        items.clear();
    }

    public BlockPos getRefuelPosition() {
        ItemStack stack = (ItemStack)this.inventory.get(9);
        if (stack.m_41720_() instanceof MinerRemoteItem) {
            BlockPos pos = MinerRemoteItem.getPos(stack);
            return pos.m_121878_() == BlockPos.f_121853_.m_121878_() ? null : pos;
        }
        return null;
    }

    public BlockPos getPreviousTarget() {
        return BlockPos.m_122022_((long)this.afterRecharge.orElse(0L));
    }

    @Override
    public boolean isOperating() {
        return this.finished == 0 && super.isOperating();
    }

    @Override
    public boolean canOperate() {
        return super.canOperate() && this.getStackInSlot(this.getScannerSlot()).m_41720_() instanceof IScanner;
    }

    @Override
    public boolean hasPumps() {
        return true;
    }

    @Override
    public InteractionResult pumpFluid(FluidStack fluidStack, boolean doPump) {
        if (fluidStack.isEmpty()) {
            return InteractionResult.FAIL;
        }
        Fluid fluid = fluidStack.getFluid();
        FluidTank tank = new FluidTank(1000);
        tank.setFluid(fluidStack);
        SimpleInventory inv = new SimpleInventory(1);
        if (this.storedCells > 0) {
            inv.setStackInSlot(0, new ItemStack((ItemLike)IC2Items.CELL_EMPTY));
        }
        if (FluidHelper.fillContainer(inv, 0, this.dropsInventory, (IFluidHandler)tank, !doPump)) {
            if (doPump) {
                --this.storedCells;
                this.updateGuiField("storedCells");
            }
            return InteractionResult.SUCCESS;
        }
        if (fluid != Fluids.f_76193_ && fluid != Fluids.f_76195_) {
            int amount = this.tank.fill(fluidStack, doPump ? IFluidHandler.FluidAction.EXECUTE : IFluidHandler.FluidAction.SIMULATE);
            return amount > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.isChunkLoading() && !chunk && !this.ignoreChunkRemoval) {
            this.clearLoadedChunks();
        }
        super.onUnloaded(chunk);
    }

    public void clearLoadedChunks() {
        if (this.loadedChunks.isEmpty()) {
            return;
        }
        for (Object2LongMap.Entry entry : this.loadedChunks.object2LongEntrySet()) {
            this.unloadChunks(entry.getLongValue(), (UUID)entry.getKey());
        }
        this.loadedChunks.clear();
    }

    public void loadChunks(long chunkPos, UUID id) {
        this.loadedChunks.put((Object)id, chunkPos);
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                ForgeChunkManager.forceChunk((ServerLevel)((ServerLevel)this.f_58857_), (String)"ic2", (UUID)id, (int)(x + ChunkPos.m_45592_((long)chunkPos)), (int)(z + ChunkPos.m_45602_((long)chunkPos)), (boolean)true, (boolean)false);
            }
        }
    }

    public void unloadChunks(long chunkPos, UUID id) {
        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                ForgeChunkManager.forceChunk((ServerLevel)((ServerLevel)this.f_58857_), (String)"ic2", (UUID)id, (int)(x + ChunkPos.m_45592_((long)chunkPos)), (int)(z + ChunkPos.m_45602_((long)chunkPos)), (boolean)false, (boolean)false);
            }
        }
    }
}

