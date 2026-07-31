/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.misc.tiles;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkFieldProvider;
import ic2.core.IC2;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.platform.registries.IC2Tiles;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class IridiumStoneTileEntity
extends BlockEntity
implements IPersonalTile,
INetworkFieldProvider {
    @NetworkInfo
    UUID owner = null;

    public IridiumStoneTileEntity(BlockPos pos, BlockState state) {
        super(IC2Tiles.IRIDIUM_STONE, pos, state);
    }

    @Override
    public List<String> getNetworkFields() {
        return ObjectArrayList.wrap((Object[])new String[]{"owner"});
    }

    @Override
    public List<String> getGuiFields() {
        return Collections.emptyList();
    }

    public CompoundTag m_5995_() {
        CompoundTag nbt = super.m_5995_();
        IC2.NETWORKING.get(IC2.PLATFORM.isSimulating()).sendInitialData(this, nbt);
        return nbt;
    }

    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        IC2.NETWORKING.get(IC2.PLATFORM.isSimulating()).handleInitialChange(this, tag);
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null) {
            this.owner = owner;
            this.m_6596_();
            IC2.NETWORKING.get(IC2.PLATFORM.isSimulating()).updateTileField(this, "owner");
        }
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public boolean canDoAction(UUID user, Action action, boolean ignorePublic) {
        if (this.owner == null) {
            this.setOwner(user);
            return true;
        }
        if (this.owner.equals(user) || IC2.PLATFORM.isOp(user)) {
            return true;
        }
        Friend friend = FriendManager.getFriends().getFriend(this.owner, user);
        return friend != null && friend.canApplyAction(action);
    }

    public void m_142466_(CompoundTag nbt) {
        super.m_142466_(nbt);
        this.owner = nbt.m_128441_("owner") ? nbt.m_128342_("owner") : null;
    }

    public void m_183515_(CompoundTag nbt) {
        super.m_183515_(nbt);
        if (this.owner != null) {
            nbt.m_128362_("owner", this.owner);
        }
    }
}

