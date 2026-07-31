/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BucketPickup
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.util.FakePlayerFactory
 *  net.minecraftforge.event.level.BlockEvent$BreakEvent
 *  net.minecraftforge.eventbus.api.Event
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.core.IC2Classic;
import ic2.api.items.electric.ElectricItem;
import ic2.api.items.electric.IElectricItem;
import ic2.api.items.electric.IMiningDrill;
import ic2.api.items.electric.IScanner;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.audio.IAudioSource;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.cache.TileCache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.lv.MinerContainer;
import ic2.core.block.machines.logic.miner.BlockMiningTarget;
import ic2.core.block.machines.logic.miner.FluidMiningTarget;
import ic2.core.block.machines.logic.miner.IMiningTarget;
import ic2.core.block.machines.logic.miner.MiningQueue;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.block.machines.tiles.lv.PumpTileEntity;
import ic2.core.block.misc.MiningPipeBlock;
import ic2.core.block.rendering.world.impl.BlockHighlighter;
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
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

public class MinerTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IProgressMachine,
ITileActivityProvider {
    TileCache<PumpTileEntity> pumps = new TileCache<PumpTileEntity>(this, DirectionList.HORIZONTAL, PumpTileEntity.class);
    ICache<IItemHandler> inventoryCache = new CapabilityCache<IItemHandler>(this, DirectionList.HORIZONTAL, ForgeCapabilities.ITEM_HANDLER);
    ICache<ITube> tubeCache = new CapabilityCache<ITube>(this, DirectionList.DOWN.invert(), IC2Classic.TUBE_CAPABILITY);
    protected LinkedList<IMiningTarget> queue = new LinkedList();
    protected MiningQueue masterWorkingQueue;
    protected BlockPos masterPos;
    protected boolean validatedMining = false;
    protected int progress = 0;
    @NetworkInfo
    protected BlockPos stuckOn = null;
    protected IMiningTarget current = null;
    protected IMiningTarget mining = null;
    IAudioSource source;

    public MinerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4, 128, 10000);
        this.setFuelSlot(0);
        this.addGuiFields("stuckOn");
        this.addCaches(this.pumps, this.inventoryCache, this.tubeCache);
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    protected MinerTileEntity(BlockPos pos, BlockState state, int size, int maxInput, int maxEnergy) {
        super(pos, state, size, maxInput, maxEnergy);
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.DOWN.invert());
        handler.registerBlockAccess(DirectionList.DOWN.invert(), AccessRule.BOTH);
        handler.registerBlockAccess(DirectionList.DOWN, AccessRule.DISABLED);
        handler.registerSlotAccess(AccessRule.BOTH, 0, this.getScannerSlot(), this.getMiningPipeSlot(), this.getDrillSlot());
        handler.registerSlotsForSide(DirectionList.UP.invert(), this.getMiningPipeSlot());
        handler.registerSlotsForSide(DirectionList.HORIZONTAL, 0, this.getScannerSlot(), this.getDrillSlot());
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new ClassFilter(IScanner.class), this.getScannerSlot());
        handler.registerInputFilter(SpecialFilters.BLOCK_FILTER, this.getMiningPipeSlot());
        handler.registerInputFilter(new ClassFilter(IMiningDrill.class), this.getDrillSlot());
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.TOOLS, this.getScannerSlot(), this.getDrillSlot());
        handler.registerNamedSlot(SlotType.MINING_PIPES, this.getMiningPipeSlot());
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MINER;
    }

    public int getDrillSlot() {
        return 3;
    }

    protected int getMiningPipeSlot() {
        return 2;
    }

    protected int getScannerSlot() {
        return 1;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return ((ItemStack)this.inventory.get(this.getDrillSlot())).m_41619_() ? 5.0f : 200.0f;
    }

    protected void drainMiningPipe(int amount) {
        ((ItemStack)this.inventory.get(this.getMiningPipeSlot())).m_41774_(1);
    }

    protected boolean hasMiningPipe() {
        ItemStack stack = (ItemStack)this.inventory.get(this.getMiningPipeSlot());
        return stack.m_41720_() == IC2Blocks.MINING_PIPE_SHAFT.m_5456_() && stack.m_41613_() > 0;
    }

    public boolean setMaster(BlockPos masterPos, MiningQueue queue) {
        if (this.masterPos != null) {
            return false;
        }
        this.masterPos = masterPos;
        this.masterWorkingQueue = queue;
        return true;
    }

    public void setQueue(MiningQueue queue) {
        if (queue.getMasterPos().equals((Object)this.masterPos)) {
            this.masterWorkingQueue = queue;
        }
    }

    public void clear(BlockPos master) {
        if (master.equals((Object)this.masterPos)) {
            this.masterWorkingQueue = null;
            this.masterPos = null;
        }
    }

    public MiningQueue getMasterQueue() {
        return this.masterWorkingQueue;
    }

    public void assistMiner(MinerTileEntity source, int progress) {
        this.progress += progress;
        if (progress >= 200) {
            this.validatedMining = false;
            progress = 0;
            this.f_58857_.m_6801_(this.m_58899_().hashCode(), this.mining.getPos(), -1);
            this.mineBlock(source);
        } else if (this.mining != null) {
            this.f_58857_.m_6801_(this.m_58899_().hashCode(), this.mining.getPos(), Mth.m_14045_((int)(progress / 20), (int)0, (int)10));
        }
    }

    public void assistMiner(MinerTileEntity source, ItemStack stack, IMiningDrill drill) {
        this.assistMiner(source, Math.max(1, 1 + drill.getMiningBoost(stack, this.mining.getState())));
    }

    public boolean canAssist(MinerTileEntity other) {
        return this.mining != null && this.mining.canMine(other) && this.mining.canContinue(other);
    }

    public int getEnergyUsage() {
        ItemStack stack = (ItemStack)this.inventory.get(this.getDrillSlot());
        if (stack.m_41720_() instanceof IMiningDrill) {
            return Math.max(1, 1 + ((IMiningDrill)stack.m_41720_()).getExtraEnergyCost(stack));
        }
        return 1;
    }

    @Override
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        super.onGuiFieldChanged(fields, player);
        if (fields.contains("stuckOn") && this.stuckOn != null) {
            BlockHighlighter.INSTANCE.addHighlight(this.stuckOn, 600, ColorUtils.rgb(255, 0, 0, 128));
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("isActive")) {
            if (this.source == null || !this.source.isValid()) {
                this.source = IC2.AUDIO.createSource(this, IC2Sounds.MINER_PROCESSING);
                if (this.source == null) {
                    return;
                }
            }
            this.source.playStop(this.isActive() && this.isOperating());
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0 && this.masterPos == null) {
            if (this.masterWorkingQueue == null) {
                this.masterWorkingQueue = new MiningQueue(this.m_58899_());
                this.findMinersInRange(this.masterWorkingQueue::addWorker);
                entity.m_213846_((Component)Component.m_237113_((String)("Setup Workgroup with [" + this.masterWorkingQueue.size() + "] helpers")));
            } else if (this.masterWorkingQueue != null) {
                this.masterWorkingQueue.remove(this.f_58857_);
                this.masterWorkingQueue = null;
                entity.m_213846_((Component)Component.m_237113_((String)"Cleared Workgroup"));
            }
        }
    }

    private void findMinersInRange(Consumer<MinerTileEntity> tiles) {
        for (BlockPos pos : Box.withRange(this.m_58899_(), 5)) {
            BlockEntity entity = this.f_58857_.m_7702_(pos);
            if (!(entity instanceof MinerTileEntity)) continue;
            MinerTileEntity miner = (MinerTileEntity)entity;
            if (entity instanceof RocketMinerTileEntity || miner.getMasterQueue() != null) continue;
            tiles.accept(miner);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        this.source = null;
        IC2.AUDIO.removeSource(this);
        if (!chunk) {
            if (this.masterPos == null && this.masterWorkingQueue != null) {
                this.masterWorkingQueue.remove(this.f_58857_);
                this.masterWorkingQueue = null;
            } else if (this.masterPos != null && this.masterWorkingQueue != null) {
                this.masterWorkingQueue.removeWorker(this);
                this.masterWorkingQueue = null;
            }
        }
        super.onUnloaded(chunk);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MinerContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        ListTag list = new ListTag();
        for (IMiningTarget target : this.queue) {
            CompoundTag nbt = target.save();
            nbt.m_128344_("type", target.getID());
            list.add((Object)nbt);
        }
        if (!list.isEmpty()) {
            compound.m_128365_("queue", (Tag)list);
        }
        compound.m_128405_("progress", this.progress);
        if (this.masterPos != null) {
            compound.m_128356_("master_pos", this.masterPos.m_121878_());
        } else if (this.masterWorkingQueue != null) {
            compound.m_128365_("master_queue", (Tag)this.masterWorkingQueue.save());
        }
        if (this.current != null) {
            CompoundTag nbt = this.current.save();
            nbt.m_128344_("type", this.current.getID());
            compound.m_128365_("current", (Tag)nbt);
            nbt = this.mining.save();
            nbt.m_128344_("type", this.mining.getID());
            compound.m_128365_("mining", (Tag)nbt);
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        for (CompoundTag nbt : NBTListWrapper.wrap(compound.m_128437_("queue", 10), CompoundTag.class)) {
            this.queue.add(nbt.m_128445_("type") == 1 ? new FluidMiningTarget(nbt) : new BlockMiningTarget(nbt));
        }
        this.progress = compound.m_128451_("progress");
        this.masterPos = compound.m_128441_("master_pos") ? BlockPos.m_122022_((long)compound.m_128454_("master_pos")) : null;
        MiningQueue miningQueue = this.masterWorkingQueue = compound.m_128441_("master_queue") ? new MiningQueue(this.m_58899_(), compound.m_128469_("master_queue")) : null;
        if (compound.m_128441_("current")) {
            CompoundTag nbt = compound.m_128469_("current");
            this.current = nbt.m_128445_("type") == 1 ? new FluidMiningTarget(nbt) : new BlockMiningTarget(nbt);
            nbt = compound.m_128469_("mining");
            this.mining = nbt.m_128445_("type") == 1 ? new FluidMiningTarget(nbt) : new BlockMiningTarget(nbt);
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.masterPos != null && this.f_58857_.m_46749_(this.masterPos)) {
            BlockEntity master = this.f_58857_.m_7702_(this.masterPos);
            if (master instanceof MinerTileEntity) {
                MinerTileEntity miner = (MinerTileEntity)master;
                if (miner.getMasterQueue() == null) {
                    this.masterPos = null;
                    return;
                }
                this.masterWorkingQueue = miner.getMasterQueue();
            }
        } else if (this.masterPos == null && this.masterWorkingQueue != null) {
            this.masterWorkingQueue.loadIfPossible(this.m_58904_());
        }
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.handleChargeSlot((int)((float)this.maxEnergy * 0.9f));
        if (this.hasEnergy(100)) {
            if (((ItemStack)this.inventory.get(this.getScannerSlot())).m_41720_() instanceof IElectricItem) {
                this.energy -= ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(this.getScannerSlot()), this.energy, Integer.MAX_VALUE, true, false);
            }
            if (((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_() instanceof IElectricItem) {
                this.energy -= ElectricItem.MANAGER.charge((ItemStack)this.inventory.get(this.getDrillSlot()), this.energy, Integer.MAX_VALUE, true, false);
            }
        }
        this.handleChargeSlot((int)((float)this.maxEnergy * 0.9f));
        boolean operating = this.isOperating();
        if (operating) {
            this.work();
        } else if (this.hasEnergy(1) && ((ItemStack)this.inventory.get(this.getDrillSlot())).m_41619_()) {
            if (MiningPipeBlock.isMiningPipe(this.f_58857_.m_8055_(this.m_58899_().m_7495_()))) {
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
            } else if (this.isStuck()) {
                this.progress = 0;
            }
        } else if (this.isStuck() && this.clock(120)) {
            this.progress = 0;
        }
        this.setActive(operating);
        this.handleComparators();
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
        if (((ItemStack)this.inventory.get(this.getMiningPipeSlot())).m_41720_() != IC2Blocks.MINING_PIPE_SHAFT.m_5456_() && Block.m_49814_((Item)((ItemStack)this.inventory.get(this.getMiningPipeSlot())).m_41720_()) != Blocks.f_50016_) {
            this.f_58857_.m_46597_(target, Block.m_49814_((Item)((ItemStack)this.inventory.get(this.getMiningPipeSlot())).m_41720_()).m_49966_());
            ((ItemStack)this.inventory.get(this.getMiningPipeSlot())).m_41774_(1);
        }
        this.updateTip(target.m_123342_() + 1);
    }

    public boolean isStuck() {
        return this.progress < 0;
    }

    public boolean isOperating() {
        return this.hasEnergy(100) && this.canOperate() && !this.isRedstonePowered();
    }

    public boolean canOperate() {
        return !((ItemStack)this.inventory.get(this.getDrillSlot())).m_41619_() && (this.hasMiningPipe() || this.queue.size() > 0 || this.masterWorkingQueue != null && this.masterWorkingQueue.getTaskQueue().size() > 0) && this.canMiningDrillBeUsed() && !this.isStuck();
    }

    public boolean canMiningDrillBeUsed() {
        if (!(((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_() instanceof IMiningDrill)) {
            return false;
        }
        return ((IMiningDrill)((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_()).canDrillBeUsed((ItemStack)this.inventory.get(this.getDrillSlot()));
    }

    protected void work() {
        if (this.current == null) {
            this.findTask();
            if (this.current == null) {
                return;
            }
        }
        if (this.mining == null) {
            this.mining = this.current;
        }
        if (!this.validatedMining) {
            int checksLeft = 20;
            BlockPos.MutableBlockPos miningTarget = this.mining.getPos().m_122032_();
            BlockPos target = this.mining.getPos();
            while (!this.canReachTarget(miningTarget, true)) {
                int x_diff = target.m_123341_() - this.m_58899_().m_123341_();
                int z_diff = target.m_123343_() - this.m_58899_().m_123343_();
                target = Math.abs(x_diff) > Math.abs(z_diff) ? target.m_121945_(x_diff > 0 ? Direction.WEST : Direction.EAST) : target.m_121945_(z_diff > 0 ? Direction.NORTH : Direction.SOUTH);
                miningTarget.m_122190_((Vec3i)target);
                if (checksLeft-- > 0) continue;
                break;
            }
            if (!this.mining.getPos().equals((Object)target)) {
                if (this.masterWorkingQueue != null) {
                    this.masterWorkingQueue.releaseTarget(this.mining.getPos(), this);
                    this.mining = this.createTarget(target);
                    this.masterWorkingQueue.claimTarget(target, this);
                    this.validatedMining = false;
                } else {
                    this.mining = this.createTarget(target);
                    this.validatedMining = false;
                }
            } else {
                this.validatedMining = true;
                return;
            }
        }
        if (this.masterWorkingQueue != null && this.masterWorkingQueue.isAssisting(this.mining.getPos(), this)) {
            if (this.mining.canMine(this) && this.mining.canContinue(this)) {
                IMiningDrill drill = (IMiningDrill)((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_();
                this.masterWorkingQueue.assist(this, this.mining.getPos(), Math.max(1, 1 + drill.getMiningBoost((ItemStack)this.inventory.get(this.getDrillSlot()), this.mining.getState())));
                this.useEnergy(Math.max(1, 1 + drill.getExtraEnergyCost((ItemStack)this.inventory.get(this.getDrillSlot()))));
            }
            if (this.f_58857_.m_46859_(this.mining.getPos())) {
                this.clearMiningPos(this.mining.getPos());
            }
            return;
        }
        if (!this.mining.canMine(this)) {
            this.validatedMining = false;
            this.progress = -1;
            this.stuckOn = this.mining.getPos();
            this.updateGuiField("stuckOn");
        } else if (this.mining.canContinue(this)) {
            this.stuckOn = null;
            this.updateGuiField("stuckOn");
            IMiningDrill drill = (IMiningDrill)((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_();
            this.progress += Math.max(1, 1 + drill.getMiningBoost((ItemStack)this.inventory.get(this.getDrillSlot()), this.mining.getState()));
            this.useEnergy(Math.max(1, 1 + drill.getExtraEnergyCost((ItemStack)this.inventory.get(this.getDrillSlot()))));
            if (this.progress >= 200) {
                this.validatedMining = false;
                this.progress = 0;
                this.f_58857_.m_6801_(this.m_58899_().hashCode(), this.mining.getPos(), -1);
                this.mineBlock(this);
            }
            if (this.mining != null) {
                this.f_58857_.m_6801_(this.m_58899_().hashCode(), this.mining.getPos(), Mth.m_14045_((int)(this.progress / 20), (int)0, (int)10));
            }
        }
    }

    public void mineBlock(MinerTileEntity entity) {
        this.pumps.validateExisting();
        ((IMiningDrill)((ItemStack)entity.inventory.get(this.getDrillSlot())).m_41720_()).onDrillUsed((ItemStack)entity.inventory.get(this.getDrillSlot()));
        List<ItemStack> list = this.mining.createDrops(entity);
        BlockPos miningPos = this.mining.getPos();
        if (!this.sendItemsAway(list)) {
            for (int i = 0; i < list.size(); ++i) {
                Block.m_49840_((Level)this.f_58857_, (BlockPos)entity.m_58899_(), (ItemStack)list.get(i));
            }
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
            if (this.masterWorkingQueue != null) {
                this.masterWorkingQueue.releaseTarget(miningPos, this);
            }
            return;
        }
        this.clearMiningPos(miningPos);
    }

    protected void clearMiningPos(BlockPos miningPos) {
        BlockPos targetPos = this.current.getPos();
        if (miningPos.equals((Object)targetPos) || !this.f_58857_.m_46749_(targetPos) || this.f_58857_.m_46859_(targetPos) || !this.isOreValuable(targetPos)) {
            this.current = null;
            this.mining = null;
            if (this.masterWorkingQueue != null) {
                this.masterWorkingQueue.releaseTarget(miningPos, this);
            }
        } else if (this.masterWorkingQueue != null) {
            this.masterWorkingQueue.releaseTarget(miningPos, this);
            this.mining = this.current;
            this.masterWorkingQueue.claimTarget(targetPos, this);
        } else {
            this.mining = this.current;
        }
    }

    public boolean sendItemsAway(List<ItemStack> list) {
        IItemTransporter trans;
        if (list.isEmpty()) {
            return true;
        }
        InventoryHandler handler = this.getInventoryHandler();
        if (handler.areAllSidesBlocked()) {
            return false;
        }
        for (Direction dir : this.tubeCache) {
            if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (trans = TransporterManager.getTransporter(this.tubeCache.getHandler(dir))) == null) continue;
            this.sendItems(trans, dir.m_122424_(), list);
        }
        if (!list.isEmpty()) {
            for (Direction dir : this.inventoryCache) {
                if (!handler.isBlockSideUnlocked(dir) || !handler.getBlockAccess(dir).canExport() || (trans = TransporterManager.getTransporter(this.inventoryCache.getHandler(dir))) == null) continue;
                this.sendItems(trans, dir.m_122424_(), list);
            }
        }
        return list.isEmpty();
    }

    private void sendItems(IItemTransporter transporter, Direction dir, List<ItemStack> list) {
        for (int i = 0; i < list.size(); ++i) {
            ItemStack stack = list.get(i);
            if (stack.m_41619_()) {
                list.remove(i--);
                continue;
            }
            stack.m_41774_(transporter.addItem(stack, dir, false));
            if (stack.m_41613_() > 0) continue;
            list.remove(i--);
        }
    }

    public boolean isOreValuable(BlockPos pos) {
        ItemStack stack = (ItemStack)this.inventory.get(this.getScannerSlot());
        if (!(stack.m_41720_() instanceof IScanner)) {
            return false;
        }
        return ((IScanner)stack.m_41720_()).isOreValuable(stack, this.f_58857_.m_8055_(pos), (LevelReader)this.f_58857_, pos);
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
            if (!miningPipe && this.hasMiningPipe() || isTip) {
                this.f_58857_.m_46597_((BlockPos)target, IC2Blocks.MINING_PIPE_SHAFT.m_49966_());
                if (!miningPipe) {
                    this.drainMiningPipe(1);
                }
            }
            target.m_142448_(target.m_123342_() - 1);
        }
        this.f_58857_.m_46597_((BlockPos)target, IC2Blocks.MINING_PIPE_BOTTOM.m_49966_());
    }

    public boolean canReachTarget(BlockPos.MutableBlockPos targetPos, boolean ignore) {
        BlockState state;
        int x_diff = targetPos.m_123341_() - this.m_58899_().m_123341_();
        int z_diff = targetPos.m_123343_() - this.m_58899_().m_123343_();
        if (x_diff == 0 && z_diff == 0) {
            return true;
        }
        if (!(ignore || (state = this.f_58857_.m_8055_((BlockPos)targetPos)).m_60795_() || this.canPass(state, (LevelReader)this.f_58857_, (BlockPos)targetPos))) {
            return false;
        }
        if (Math.abs(x_diff) > Math.abs(z_diff)) {
            targetPos.m_122173_(x_diff > 0 ? Direction.WEST : Direction.EAST);
        } else {
            targetPos.m_122173_(z_diff > 0 ? Direction.NORTH : Direction.SOUTH);
        }
        return this.canReachTarget(targetPos, false);
    }

    public boolean canPass(BlockState state, LevelReader world, BlockPos pos) {
        FluidState fluid = state.m_60819_();
        if (!fluid.m_76178_()) {
            return !fluid.m_76170_();
        }
        return MiningPipeBlock.isMiningPipe(state) || state.m_60734_() == IC2Blocks.MINER;
    }

    protected void findTask() {
        if (this.masterPos != null && this.masterWorkingQueue == null) {
            return;
        }
        if (this.masterWorkingQueue != null) {
            BlockPos pos = this.getPipeTip();
            if (this.masterWorkingQueue.getYLevel() < pos.m_123342_()) {
                this.setTarget(this.createTarget(pos.m_7495_()));
                return;
            }
            LinkedList<IMiningTarget> queue = this.masterWorkingQueue.getTaskQueue();
            if (queue.size() > 0 && queue.peekFirst().getPos().m_123342_() == pos.m_123342_()) {
                this.setTarget(queue.removeFirst());
                return;
            }
            if (this.masterPos == null) {
                if (((ItemStack)this.inventory.get(this.getScannerSlot())).m_41720_() instanceof IScanner) {
                    this.findTargets(pos, (ItemStack)this.inventory.get(this.getScannerSlot()), queue, this.masterWorkingQueue.hasPumps());
                    if (queue.size() > 0) {
                        this.masterWorkingQueue.setYLevel(pos.m_123342_());
                        this.setTarget(queue.removeFirst());
                        return;
                    }
                    this.setTarget(this.createTarget(pos.m_7495_()));
                    this.masterWorkingQueue.setYLevel(pos.m_123342_() - 1);
                }
                return;
            }
            Item item = ((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_();
            if (item instanceof IMiningDrill) {
                IMiningDrill drill = (IMiningDrill)item;
                if (this.masterWorkingQueue.assistRandomly(this, (ItemStack)this.inventory.get(this.getDrillSlot()), drill)) {
                    this.useEnergy(Math.max(1, 1 + drill.getExtraEnergyCost((ItemStack)this.inventory.get(this.getDrillSlot()))));
                }
            }
            return;
        }
        BlockPos pos = this.getPipeTip();
        if (pos.m_123342_() >= this.m_58899_().m_123342_()) {
            this.setTarget(this.createTarget(this.m_58899_().m_7495_()));
            return;
        }
        if (!(((ItemStack)this.inventory.get(this.getScannerSlot())).m_41720_() instanceof IScanner)) {
            this.setTarget(this.createTarget(pos.m_7495_()));
            return;
        }
        if (this.queue.size() > 0) {
            this.setTarget(this.queue.removeFirst());
            return;
        }
        this.findTargets(pos, (ItemStack)this.inventory.get(this.getScannerSlot()), this.queue, this.hasPumps());
        if (this.queue.size() > 0) {
            this.setTarget(this.queue.removeFirst());
            return;
        }
        this.setTarget(this.createTarget(pos.m_7495_()));
    }

    public void setTarget(IMiningTarget target) {
        if (target.isValid(this)) {
            this.current = target;
            this.mining = target;
            if (this.masterWorkingQueue != null) {
                this.masterWorkingQueue.claimTarget(target.getPos(), this);
            }
        } else {
            this.current = null;
            this.mining = null;
        }
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

    protected void findTargets(BlockPos pos, ItemStack stack, LinkedList<IMiningTarget> queue, boolean canPump) {
        IScanner item = (IScanner)stack.m_41720_();
        int radius = item.getScanRadius(stack, true);
        if (radius < 0) {
            return;
        }
        this.pumps.validateExisting();
        Box box = Box.withRange(pos, radius);
        ObjectArrayFIFOQueue toCheck = new ObjectArrayFIFOQueue();
        ObjectSet alreadyDone = CollectionUtils.createSet();
        this.addToQueue(pos, (PriorityQueue<BlockPos>)toCheck, (Set<BlockPos>)alreadyDone);
        while (!toCheck.isEmpty()) {
            BlockState state;
            BlockPos task = (BlockPos)toCheck.dequeue();
            if (!box.intersectsWith(task) || !alreadyDone.add((BlockPos)task) || !this.f_58857_.m_46749_(task) || !this.canMine(task, state = this.f_58857_.m_8055_(task), canPump)) continue;
            FluidState fluid = state.m_60819_();
            if (!fluid.m_76178_() && !fluid.m_76170_()) {
                queue.add(this.createTarget(task));
            }
            if (item.isOreValuable(stack, state, (LevelReader)this.f_58857_, task)) {
                queue.add(this.createTarget(task));
            }
            this.addToQueue(task, (PriorityQueue<BlockPos>)toCheck, (Set<BlockPos>)alreadyDone);
        }
    }

    public boolean canMine(BlockPos pos, BlockState state, boolean canPump) {
        if (state.m_60795_()) {
            return true;
        }
        if (MiningPipeBlock.isMiningPipe(state)) {
            return false;
        }
        FluidState fluid = state.m_60819_();
        if (!fluid.m_76178_()) {
            if (fluid.m_76170_()) {
                if (state.m_60734_() instanceof BucketPickup && canPump) {
                    return this.canMineEvent(pos, state);
                }
                return false;
            }
            return true;
        }
        if (state.m_60800_((BlockGetter)this.f_58857_, pos) < 0.0f) {
            return false;
        }
        if (state.m_60819_().m_76178_() || !state.m_60834_()) {
            return this.canMineEvent(pos, state);
        }
        Item item = ((ItemStack)this.inventory.get(this.getDrillSlot())).m_41720_();
        if (item instanceof IMiningDrill) {
            IMiningDrill drill = (IMiningDrill)item;
            if (!drill.canMineBlock((ItemStack)this.inventory.get(this.getDrillSlot()), state, (LevelReader)this.f_58857_, pos) && !((ItemStack)this.inventory.get(this.getDrillSlot())).m_41735_(state)) {
                return false;
            }
            return this.canMineEvent(pos, state);
        }
        return false;
    }

    public boolean canMineEvent(BlockPos target, BlockState state) {
        BlockEvent.BreakEvent evt = new BlockEvent.BreakEvent(this.f_58857_, target, state, (Player)FakePlayerFactory.getMinecraft((ServerLevel)((ServerLevel)this.f_58857_)));
        evt.setExpToDrop(0);
        MinecraftForge.EVENT_BUS.post((Event)evt);
        return !evt.isCanceled();
    }

    protected void addToQueue(BlockPos pos, PriorityQueue<BlockPos> queue, Set<BlockPos> alreadyDone) {
        for (Direction dir : DirectionList.HORIZONTAL) {
            BlockPos thePos = pos.m_121945_(dir);
            if (alreadyDone.contains(thePos)) continue;
            queue.enqueue((Object)thePos);
        }
    }

    public IMiningTarget createTarget(BlockPos pos) {
        return this.createTarget(pos, false);
    }

    public IMiningTarget createTarget(BlockPos pos, boolean force) {
        BlockState state = this.f_58857_.m_8055_(pos);
        FluidState fluid = state.m_60819_();
        if (!fluid.m_76178_() && fluid.m_76170_() && !force) {
            return new FluidMiningTarget(pos, state);
        }
        return new BlockMiningTarget(pos, state);
    }

    public boolean hasPumps() {
        return !this.pumps.isEmpty();
    }

    public InteractionResult pumpFluid(FluidStack fluidStack, boolean doPump) {
        if (fluidStack.isEmpty()) {
            return InteractionResult.FAIL;
        }
        InteractionResult result = InteractionResult.FAIL;
        for (Direction dir : this.pumps) {
            PumpTileEntity pump = this.pumps.getHandler(dir);
            InteractionResult type = pump.pumpExternal(fluidStack, doPump);
            if (type == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }
            if (type != InteractionResult.PASS) continue;
            result = InteractionResult.PASS;
        }
        return result;
    }
}

