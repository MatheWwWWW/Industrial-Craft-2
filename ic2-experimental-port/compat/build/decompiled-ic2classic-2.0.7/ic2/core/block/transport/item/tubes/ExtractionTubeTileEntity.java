/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.tubes.ITube;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.misc.ITubeBlock;
import ic2.core.block.cables.AdvancedComparatorBlock;
import ic2.core.block.transport.item.TubeAction;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ExtractionTubeTileEntity
extends TubeTileEntity
implements IRedstoneListener {
    @NetworkInfo
    public DirectionList extraFacings = DirectionList.EMPTY;
    public int priorityDir = 0;
    public boolean specialConnect = false;
    public boolean pulse = false;
    public boolean comparator = false;
    public int pulseState = 0;

    public ExtractionTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.sensitive = false;
        this.addNetworkFields("extraFacings");
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.EXTRACTION_TUBE;
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
    public boolean isRotatable() {
        return true;
    }

    @Override
    public boolean canLoseUpdateTick() {
        return false;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return this.sensitive;
    }

    @Override
    public boolean canConnect(ITube other, Direction dir) {
        return super.canConnect(other, dir) && other.getTubeType() == ITube.TubeType.SIMPLE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "extra", this.extraFacings.getCode(), 0);
        NBTUtils.putBoolean(compound, "sensitive", this.sensitive, false);
        NBTUtils.putByte(compound, "prio", this.priorityDir, 0);
        NBTUtils.putBoolean(compound, "special_connected", this.specialConnect, false);
        NBTUtils.putBoolean(compound, "pulse", this.pulse, false);
        NBTUtils.putByte(compound, "pulse_state", this.pulseState, 0);
        NBTUtils.putBoolean(compound, "comparator", this.comparator, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        this.extraFacings = DirectionList.ofNumber(compound.m_128451_("extra"));
        this.sensitive = compound.m_128471_("sensitive");
        this.priorityDir = NBTUtils.getInt(compound, "prio", 0);
        this.specialConnect = compound.m_128471_("special_connected");
        this.pulse = compound.m_128471_("pulse");
        this.pulseState = compound.m_128451_("pulse_state");
        this.comparator = compound.m_128471_("comparator");
        super.m_142466_(compound);
    }

    @Override
    public void handleRedstone() {
        if (this.comparator) {
            if (this.needsRedstoneUpdate) {
                this.needsRedstoneUpdate = false;
                byte max = 0;
                for (Direction dir : DirectionList.ALL.remove(this.getFacing()).remove(this.extraFacings).remove(DirectionList.ofNumber(this.anchors))) {
                    byte strength = (byte)Mth.m_14045_((int)AdvancedComparatorBlock.getComparatorOutput(this.f_58857_, this.m_58899_(), dir, false, 0), (int)0, (int)15);
                    max = (byte)Math.max(max, strength);
                    this.sidedSignals[dir.m_122411_()] = strength;
                }
                this.signal = max;
                return;
            }
            return;
        }
        super.handleRedstone();
    }

    @Override
    public void onComparatorUpdate(BlockPos from) {
        if (this.comparator) {
            this.needsRedstoneUpdate = true;
        }
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41720_() == Items.f_42451_) {
            Direction dir = ITubeBlock.isClickingAt(hit.m_82450_().m_82546_(Vec3.m_82528_((Vec3i)hit.m_82425_())), side, 0.1875f);
            if (dir != null && dir != this.getFacing() && this.inventories.getHandler(dir) != null && !this.hasAnchor(dir)) {
                this.extraFacings = this.extraFacings.flip(dir);
                this.updateTileField("extraFacings");
                return true;
            }
        } else if (stack.m_41720_() == Items.f_41978_) {
            boolean bl = this.sensitive = !this.sensitive;
            if (this.isSimulating()) {
                player.m_5661_((Component)this.translate(this.sensitive ? "gui.ic2.tube.extraction.redstone.enable" : "gui.ic2.tube.extraction.redstone.disable"), false);
            }
            return true;
        }
        return super.onRightClick(player, hand, side, hit);
    }

    @Override
    public boolean doTubeAction(Direction side, Vec3 hit, Player player, TubeAction action) {
        if (action == TubeAction.EXTRA_EXTRACION_SIDE) {
            Direction dir = ITubeBlock.isClickingAt(hit, side, 0.1875f);
            if (!(dir == null || dir == this.getFacing() || this.inventories.getHandler(dir) == null || this.hasAnchor(dir) || this.priorityDir != 0 && Direction.m_122376_((int)(this.priorityDir - 1)) == dir)) {
                this.extraFacings = this.extraFacings.flip(dir);
                this.updateTileField("extraFacings");
                return true;
            }
        } else {
            if (action == TubeAction.REDSTONE_CONTROL) {
                boolean bl = this.sensitive = !this.sensitive;
                if (this.isSimulating()) {
                    player.m_5661_((Component)this.translate(this.sensitive ? "gui.ic2.tube.extraction.redstone.enable" : "gui.ic2.tube.extraction.redstone.disable"), false);
                }
                return true;
            }
            if (action == TubeAction.COMPARATOR) {
                boolean bl = this.comparator = !this.comparator;
                if (this.isSimulating()) {
                    player.m_5661_((Component)this.translate(this.comparator ? "gui.ic2.tube.extraction.comparator.enable" : "gui.ic2.tube.extraction.comparator.disable"), false);
                }
                return true;
            }
            if (action == TubeAction.OUTPUT_PRIORITY) {
                Direction clicked = ITubeBlock.isClickingAt(hit, side, 0.1875f);
                if (clicked != null && clicked != this.getFacing() && this.extraFacings.notContains(clicked)) {
                    this.priorityDir = clicked.ordinal() != this.priorityDir - 1 ? clicked.ordinal() + 1 : 0;
                    this.onStateChanged();
                    return true;
                }
            } else {
                if (action == TubeAction.SPECIAL_EXTRACTION_CONNECTIVITY) {
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
                if (action == TubeAction.PULSE) {
                    this.pulse = !this.pulse;
                    this.sensitive |= this.pulse;
                    if (this.isSimulating()) {
                        player.m_5661_((Component)this.translate(this.pulse ? "gui.ic2.tube.extraction.pulse.enable" : "gui.ic2.tube.extraction.pulse.disable"), false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DirectionList getExtraFacings() {
        return this.extraFacings;
    }

    @Override
    public void setFacing(Direction facing) {
        if (facing != this.getFacing()) {
            this.extraFacings = this.extraFacings.replace(facing, this.getFacing());
            this.updateTileField("extraFacings");
        }
        super.setFacing(facing);
    }

    @Override
    protected void onCachesUpdated() {
        super.onCachesUpdated();
        int code = this.extraFacings.getCode();
        this.extraFacings = this.extraFacings.keep(this.inventories.getPresentSides());
        if (code != this.extraFacings.getCode()) {
            this.updateTileField("extraFacings");
        }
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.isRendering() ? DirectionList.getNeighborCapability(this, dir, ForgeCapabilities.ITEM_HANDLER).isPresent() : this.inventories.getHandler(dir) != null);
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        DirectionList newList;
        DirectionList list = super.getValidDirections(item).remove(this.getFacing()).remove(this.extraFacings);
        if (this.priorityDir > 0 && (newList = list.keep(DirectionList.ofFacing(Direction.m_122376_((int)(this.priorityDir - 1))))).size() > 0) {
            return newList;
        }
        return list;
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        if (this.inventories.getHandler(this.getFacing()) == null && this.inventories.getPresentSides().size() > 0) {
            this.setFacing(this.inventories.getPresentSides().getDefaultFacing());
        }
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("extraFacings")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering()) {
            return;
        }
        this.handleRedstone();
        this.updatePulseState();
        if (this.clock(10) && (!this.sensitive || (this.pulse ? this.pulseState == 1 : this.isRedstonePowered()))) {
            if (this.pulse) {
                this.pulseState = 2;
            }
            this.handleExtraction(this.getFacing());
            if (this.extraFacings.size() > 0) {
                for (Direction dir : this.extraFacings) {
                    this.handleExtraction(dir);
                }
            }
        }
    }

    private void updatePulseState() {
        if (!this.pulse) {
            return;
        }
        if (this.pulseState == 0 && this.isRedstonePowered()) {
            this.pulseState = 1;
        } else if (this.pulseState == 2 && !this.isRedstonePowered()) {
            this.pulseState = 0;
        }
    }

    protected void handleExtraction(Direction dir) {
        IItemTransporter transporter = TransporterManager.getTransporter(this.inventories.getHandler(dir));
        if (transporter == null) {
            return;
        }
        ItemStack drained = transporter.removeItem(SpecialFilters.ANY_ITEM, dir.m_122424_(), 1, false);
        if (!drained.m_41619_()) {
            this.addItem(new TransportedItem(drained), dir);
        }
    }
}

