/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.misc.TreeTapAndBucketBlock;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;

public class StickyTubeTileEntity
extends TubeTileEntity {
    public int priorityDir = 0;
    public boolean specialConnect = false;
    boolean changed = false;
    int nextTime = RandomSource.m_216327_().m_188503_(1024);

    public StickyTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STICKY_TUBE;
    }

    @Override
    public ITube.TubeType getTubeType() {
        return this.specialConnect ? ITube.TubeType.SIMPLE : ITube.TubeType.EXTRACTION;
    }

    @Override
    public int getPrioritySide() {
        return this.priorityDir;
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return super.canConnect(other, dir) && other.getTubeType() == ITube.TubeType.SIMPLE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "prio", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "special_connected", this.specialConnect, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        this.priorityDir = NBTUtils.getInt(compound, "prio", 0);
        this.specialConnect = compound.m_128471_("special_connected");
        super.m_142466_(compound);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.OUTPUT_PRIORITY) {
            Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
            if (clicked != null && clicked != this.getFacing()) {
                this.priorityDir = clicked.ordinal() != this.priorityDir - 1 ? clicked.ordinal() + 1 : 0;
                this.onStateChanged();
                return true;
            }
        } else if (action == TubeAction.SPECIAL_EXTRACTION_CONNECTIVITY) {
            this.specialConnect = !this.specialConnect;
            this.tubes.markDirty();
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.specialConnect ? "gui.ic2.tube.extraction.special.enable" : "gui.ic2.tube.extraction.special.disable"), false);
            }
            this.self.setActiveDirections(DirectionList.EMPTY);
            this.self.setActiveDirections(DirectionList.ALL.remove(DirectionList.ofNumber(this.anchors)));
            this.tubes.invalidateCache();
            this.onStateChanged();
            this.notifyChanges(true, DirectionList.ALL);
            return true;
        }
        return false;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        this.changed = true;
    }

    @Override
    protected void onCachesUpdated() {
        DirectionList list = DirectionList.EMPTY.add(this.inventories.getPresentSides()).add(this.tubes.getPresentSides());
        if (list.notContains(Direction.UP) && this.f_58857_.m_8055_(this.m_58899_().m_7494_()).m_60713_(IC2Blocks.TREETAP_AND_BUCKET)) {
            list = list.add(Direction.UP);
        }
        this.connectivity = list.remove(DirectionList.ofNumber(this.anchors)).getCode();
        this.updateTileField("connectivity");
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering()) {
            return;
        }
        this.tickLog();
        this.onExtraction();
    }

    protected void tickLog() {
        if (this.nextTime > 0) {
            --this.nextTime;
            return;
        }
        this.nextTime = this.f_58857_.f_46441_.m_188503_(1024);
        Level level = this.f_58857_;
        if (!(level instanceof ServerLevel)) {
            return;
        }
        ServerLevel server = (ServerLevel)level;
        BlockPos position = this.m_58899_().m_121945_(Direction.UP);
        BlockState state = this.f_58857_.m_8055_(position);
        if (!state.m_60713_(IC2Blocks.TREETAP_AND_BUCKET)) {
            return;
        }
        if (!(state = this.f_58857_.m_8055_(position = position.m_121945_((Direction)state.m_61143_((Property)TreeTapAndBucketBlock.FACING)))).m_60713_(IC2Blocks.RUBBERWOOD_LOG)) {
            return;
        }
        state.m_222972_(server, position, server.f_46441_);
    }

    protected void onExtraction() {
        if (this.invClock(256) && !this.changed) {
            return;
        }
        this.changed = false;
        BlockState state = this.m_58904_().m_8055_(this.m_58899_().m_121945_(Direction.UP));
        if (!state.m_60713_(IC2Blocks.TREETAP_AND_BUCKET)) {
            return;
        }
        int resin = (Integer)state.m_61143_((Property)TreeTapAndBucketBlock.FILL_STAGE);
        if (resin <= 0) {
            return;
        }
        this.addItem(new ItemStack((ItemLike)IC2Items.STICKY_RESIN, resin), Direction.UP);
        this.f_58857_.m_46597_(this.m_58899_().m_121945_(Direction.UP), (BlockState)state.m_61124_((Property)TreeTapAndBucketBlock.FILL_STAGE, (Comparable)Integer.valueOf(0)));
        this.changed = false;
    }
}

