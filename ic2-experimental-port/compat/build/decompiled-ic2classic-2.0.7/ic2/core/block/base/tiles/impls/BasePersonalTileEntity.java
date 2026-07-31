/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.common.util.LazyOptional
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkClientEventListener;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.features.personal.IPersonalWrenchable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.capabilities.CapabilityWrapperCache;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BasePersonalTileEntity
extends BaseTileEntity
implements IPersonalTile,
IPersonalWrenchable,
ITileGui,
INetworkClientEventListener {
    @NetworkInfo
    UUID owner;
    @NetworkInfo
    public int mode = 1;
    Map<Capability<?>, LazyOptional<?>> privateCapability = CollectionUtils.createMap();
    CapabilityWrapperCache publicCache = new CapabilityWrapperCache();

    public BasePersonalTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("owner", "mode");
    }

    public <T> void addPersonalCapability(Capability<T> cap, T value) {
        this.privateCapability.put(cap, LazyOptional.of(() -> value));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        this.writeItemData(compound);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.readItemData(compound, false);
    }

    public void readItemData(CompoundTag nbt, boolean place) {
        this.owner = nbt.m_128403_("owner") ? nbt.m_128342_("owner") : null;
        this.mode = nbt.m_128451_("mode");
        if (this.mode == 0) {
            this.publicCache.setOwner(new PublicWrapper());
        }
        if (place) {
            this.updateTileFields("owner", "mode");
        }
    }

    public CompoundTag writeItemData(CompoundTag nbt) {
        if (this.owner != null) {
            nbt.m_128362_("owner", this.owner);
        }
        NBTUtils.putByte(nbt, "mode", (int)((byte)this.mode), 0);
        return nbt;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 0) {
            if (entity.m_20148_().equals(this.owner)) {
                this.mode = (this.mode + 1) % 3;
                this.updateTileField("mode");
                if (this.mode == 0 && !this.publicCache.hasOwner()) {
                    this.publicCache.setOwner(new PublicWrapper());
                } else if (this.publicCache.hasOwner()) {
                    this.publicCache.clearOwner();
                }
                this.notifyChanges(true, DirectionList.ALL);
            } else {
                entity.m_5661_((Component)this.translate("gui.ic2.personal.perm"), false);
            }
        }
        if (key == 1 && this.canDoAction(entity.m_20148_(), Action.PERSONAL_BLOCKS_ADMIN, false)) {
            this.voidStorage();
        }
    }

    public void voidStorage() {
    }

    @Override
    public void onUnloaded(boolean chunk) {
        for (LazyOptional<?> entry : this.privateCapability.values()) {
            entry.invalidate();
        }
        this.publicCache.clearOwner();
        this.privateCapability.clear();
        super.onUnloaded(chunk);
    }

    @Override
    public <T> LazyOptional<T> getPersonalCapability(UUID requester, Capability<T> cap, Direction dir) {
        LazyOptional<?> cached;
        if (this.canDoAction(requester, Action.PERSONAL_BLOCKS_ACCESS, false) && (cached = this.privateCapability.get(cap)) != null) {
            return cached.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        LazyOptional<T> casted;
        if (this.isPublic() && (casted = this.publicCache.getCapability(cap, side)).isPresent()) {
            return casted.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null && owner != null) {
            this.owner = owner;
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
        if (this.owner.equals(user) || !ignorePublic && this.mode == 0 || IC2.PLATFORM.isOp(user)) {
            return true;
        }
        if (this.mode == 2) {
            return false;
        }
        Friend friend = FriendManager.getFriends().getFriend(this.owner, user);
        return friend != null && friend.canApplyAction(action);
    }

    public boolean isPublic() {
        return this.mode == 0;
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.canDoAction(player.m_20148_(), Action.PERSONAL_BLOCKS_ACCESS, false);
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.canDoAction(player.m_20148_(), Action.PERSONAL_BLOCKS_ACCESS, false);
    }

    @Override
    public boolean canSetFacing(Direction dir, Player player) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_() && this.canDoAction(player.m_20148_(), Action.PERSONAL_BLOCKS_EDIT, false);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return this.canDoAction(player.m_20148_(), Action.PERSONAL_BLOCKS_ADMIN, false);
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    private class PublicWrapper
    implements ICapabilityProvider {
        private PublicWrapper() {
        }

        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            LazyOptional<?> optional = BasePersonalTileEntity.this.privateCapability.get(cap);
            return optional == null ? LazyOptional.empty() : optional.cast();
        }
    }
}

