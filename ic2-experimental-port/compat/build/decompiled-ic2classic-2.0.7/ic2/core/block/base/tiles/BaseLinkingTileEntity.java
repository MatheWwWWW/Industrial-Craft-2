/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.redstone.IComparable;
import ic2.core.block.base.misc.comparator.ComparatorManager;
import ic2.core.block.base.tiles.BaseMultiBlockTileEntity;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.capabilities.CapabilityWrapperCache;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class BaseLinkingTileEntity
extends BaseTileEntity
implements ITileActivityProvider {
    @NetworkInfo
    int state;
    @NetworkInfo
    BlockPos linkedTile = BlockPos.f_121853_;
    BlockEntity reference;
    protected CapabilityWrapperCache wrapper = new CapabilityWrapperCache();

    public BaseLinkingTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("state", "linkedTile");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putShort(compound, "state", this.state, 0);
        NBTUtils.putLong(compound, "linked", this.linkedTile.m_121878_(), 0L);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.state = compound.m_128451_("state");
        this.linkedTile = BlockPos.m_122022_((long)compound.m_128454_("linked"));
    }

    @Override
    public void onNetworkFieldChanged(Set<String> fields, Player player) {
        super.onNetworkFieldChanged(fields, player);
        if (fields.contains("state") || fields.contains("linkedTile")) {
            this.requestModelDataUpdate();
            IC2.PLATFORM.markBlockForRenderUpdate(this.m_58899_());
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.LINKING_TILE;
    }

    public BlockEntity getMaster() {
        if (this.linkedTile.equals((Object)BlockPos.f_121853_)) {
            return null;
        }
        if (this.reference == null || this.reference.m_58901_()) {
            this.reference = this.f_58857_.m_7702_(this.linkedTile);
            this.wrapper.setOwner((ICapabilityProvider)this.reference);
        }
        return this.reference;
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating()) {
            this.wrapper.clearOwner();
        }
        super.onUnloaded(chunk);
    }

    public boolean clearMaster(BlockPos origin) {
        if (this.linkedTile != BlockPos.f_121853_ && this.linkedTile.equals((Object)origin)) {
            this.updateTileField("linkedTile");
            this.linkedTile = BlockPos.f_121853_;
            this.reference = null;
            this.wrapper.clearOwner();
            if (!this.m_58901_()) {
                this.setActive(false);
                this.setState(0);
            }
            this.notifyChanges(false, DirectionList.ALL);
            return true;
        }
        return false;
    }

    public void setMaster(BlockEntity tile) {
        if (tile == null) {
            return;
        }
        this.reference = tile;
        this.linkedTile = tile.m_58899_();
        this.wrapper.setOwner((ICapabilityProvider)tile);
        this.updateTileField("linkedTile");
        this.notifyChanges(false, DirectionList.ALL);
    }

    public BaseLinkingTileEntity setState(int state) {
        if (this.state != state) {
            this.state = state;
            this.updateTileField("state");
            this.onStateChanged();
        }
        return this;
    }

    public BaseLinkingTileEntity setState(int range, int state) {
        int value = state << 2 | range & 3;
        if (this.state != value) {
            this.state = value;
            this.updateTileField("state");
            this.onStateChanged();
        }
        return this;
    }

    public int getState() {
        return this.state;
    }

    protected boolean isTile(BlockEntity tile) {
        BlockEntity master = this.getMaster();
        if (tile == master || master == null) {
            return false;
        }
        if (tile instanceof BaseLinkingTileEntity && master == ((BaseLinkingTileEntity)tile).getMaster()) {
            return false;
        }
        return !((BaseMultiBlockTileEntity)master).getBox().intersectsWith(tile.m_58899_());
    }

    @Override
    public ComparatorManager getManager() {
        BlockEntity master = this.getMaster();
        return master instanceof IComparable ? ((IComparable)master).getManager() : null;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (this.reference == null && !this.linkedTile.equals((Object)BlockPos.f_121853_)) {
            this.getMaster();
        }
        return this.wrapper.getCapability(cap, side);
    }
}

