/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongPriorityQueue
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.crops.ICrop;
import ic2.api.crops.ICropTile;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.IAreaOfEffect;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.mv.CropHarvesterContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.InventoryWrapper;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongPriorityQueue;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CropHarvesterTileEntity
extends BaseElectricTileEntity
implements ITickListener,
ITileGui,
IDropProvider,
IAreaOfEffect {
    public static final AABBUtil.IBlockFilter FILTER = new AABBUtil.IBlockFilter(){

        @Override
        public boolean isValid(LevelReader world, BlockPos pos) {
            return world.m_7702_(pos) instanceof ICropTile;
        }

        @Override
        public boolean isValid(BlockState state) {
            return state.m_60734_() == IC2Blocks.CROP_STICK;
        }
    };
    LongSet knownPositions = new LongOpenHashSet();
    LongPriorityQueue queue = new LongArrayFIFOQueue();
    LinkedList<ItemStack> items = new LinkedList();
    IItemTransporter transporter = null;
    @NetworkInfo
    public int radius = 1;
    int delay = 20;
    int networkId = -1;
    public SimpleInventory upgrades = new SimpleInventory(5).setMaxStackSize(1);

    public CropHarvesterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 9, 128, 5800);
        this.addNetworkFields("radius");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] array = MathUtils.fromTo(0, 9);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.EXPORT);
        handler.registerSlotAccess(AccessRule.EXPORT, array);
        handler.registerSlotsForSide(DirectionList.ALL, array);
        handler.registerNamedSlot(SlotType.OUTPUT, array);
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CROP_HARVESTER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new CropHarvesterContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("upgrades", (Tag)this.upgrades.save(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.upgrades.load(compound.m_128469_("upgrades"));
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            this.updateUpgrades();
        }
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        this.upgrades.addToDrops(drops);
    }

    public void updateUpgrades() {
        int speed = 0;
        int rangeUpgrade = 1;
        int m = this.upgrades.getSlotCount();
        for (int i = 0; i < m; ++i) {
            Item item = this.upgrades.getStackInSlot(i).m_41720_();
            if (item == IC2Items.OVERCLOCKER_UPGRADE) {
                ++speed;
                continue;
            }
            if (item == IC2Items.QUANTUM_OVERCLOCKER_UPGRADE) {
                speed += 2;
                continue;
            }
            if (item == IC2Items.BASIC_FIELD_PAD_UPGRADE) {
                ++rangeUpgrade;
                continue;
            }
            if (item == IC2Items.FIELD_PAD_UPGRADE) {
                rangeUpgrade += 2;
                continue;
            }
            if (item != IC2Items.ADVANCED_FIELD_PAD_UPGRADE) continue;
            rangeUpgrade += 3;
        }
        if (rangeUpgrade > 5) {
            rangeUpgrade = 5;
        }
        if (this.radius > rangeUpgrade) {
            this.queue.clear();
            this.knownPositions.clear();
        }
        this.radius = rangeUpgrade;
        this.updateTileField("radius");
        this.delay = speed < 1 ? 20 : (speed < 2 ? 10 : (speed < 3 ? 5 : (speed < 4 ? 2 : 1)));
        this.updateQueue();
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.handleComparators();
        this.addItemsToInventory();
        if (!this.hasEnergy(25) || this.invClock(this.delay) || this.isRedstonePowered()) {
            return;
        }
        if (this.clock(1024)) {
            this.updateQueue();
        }
        if (this.queue.isEmpty() || this.items.size() > 0) {
            return;
        }
        this.useEnergy(5);
        long position = this.queue.dequeueLong();
        BlockPos pos = BlockPos.m_122022_((long)position);
        BlockEntity tile = this.f_58857_.m_7702_(pos);
        if (!(tile instanceof ICropTile)) {
            this.knownPositions.remove(position);
            return;
        }
        this.queue.enqueue(position);
        ICropTile cropTile = (ICropTile)tile;
        ICrop crop = cropTile.getCrop();
        if (crop == null || !crop.canBeHarvested(cropTile) || cropTile.getGrowthStage() < crop.getOptimalHarvestStep(cropTile)) {
            return;
        }
        this.useEnergy(20);
        this.items.addAll(cropTile.performHarvest(false));
        this.addItemsToInventory();
    }

    public void addItemsToInventory() {
        if (this.items.isEmpty()) {
            return;
        }
        if (this.transporter == null) {
            this.transporter = TransporterManager.getTransporter(new InventoryWrapper(this));
        }
        Iterator iter = this.items.iterator();
        while (iter.hasNext()) {
            ItemStack stack = (ItemStack)iter.next();
            if (stack.m_41619_()) {
                iter.remove();
                continue;
            }
            int added = this.transporter.addItem(stack, null, false);
            if (added <= 0) continue;
            stack.m_41774_(added);
            if (!stack.m_41619_()) continue;
            iter.remove();
        }
    }

    public void updateQueue() {
        for (BlockPos pos : AABBUtil.getValidBlocks(this.m_58904_(), this.m_58899_(), this.radius, FILTER, 2, DirectionList.ALL).getPositions()) {
            long position = pos.m_121878_();
            if (!this.knownPositions.add(position)) continue;
            this.queue.enqueue(position);
        }
    }

    @Override
    public AABB getAreaOfEffect() {
        return new AABB(this.m_58899_().m_7918_(-this.radius, -this.radius, -this.radius), this.m_58899_().m_7918_(this.radius + 1, this.radius + 1, this.radius + 1));
    }

    @Override
    public int getAreaOfEffectColor() {
        return -2147050257;
    }

    @Override
    public void setVisualizationId(int id) {
        this.networkId = id;
    }

    @Override
    public int getVisualizationId() {
        return this.networkId;
    }
}

