/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.items.IFoamOverrider;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.core.IC2;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.TeleportTubeContainer;
import ic2.core.block.transport.item.logic.TeleportManager;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.networking.buffers.data.NBTBuffer;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;

public class TeleportTubeTileEntity
extends TubeTileEntity
implements IPersonalTile,
ITileGui {
    public static final int FLAG_SEND = 1;
    public static final int FLAG_RECEIVE = 2;
    public static final int FLAG_PUBLIC = 4;
    public static final int FLAG_DEFAULT = 7;
    @NetworkInfo
    public int state = 7;
    @NetworkInfo
    public String frequency = "default";
    @NetworkInfo
    public UUID owner;
    boolean addedToNetwork = false;

    public TeleportTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("owner", "state", "frequency");
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return this.canDoAction(player.m_20148_(), Action.TELEPORT_SETTINGS, false) && (this.foamed <= 0 || !(player.m_21120_(hand).m_41720_() instanceof IFoamOverrider));
    }

    @Override
    public boolean canInteractWith(Player player) {
        return !this.m_58901_() && this.canDoAction(player.m_20148_(), Action.TELEPORT_SETTINGS, false);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TeleportTubeContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TELEPORT_TUBE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "state", this.state, 7);
        NBTUtils.putString(compound, "frequency", this.frequency, "default");
        if (this.owner != null) {
            compound.m_128362_("owner", this.owner);
        }
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.state = NBTUtils.getInt(compound, "state", 7);
        this.frequency = NBTUtils.getString(compound, "frequency", "default");
        this.owner = compound.m_128403_("owner") ? compound.m_128342_("owner") : null;
    }

    public void receiveItem(TransportedItem item) {
        if (this.items.add(item)) {
            item.clearDirections();
            item.setInsertionDirection(null);
            item.setExportDirection(this.getValidDirections(item).getRandomFacing());
            if (this.isSimulating()) {
                if (this.items.size() < 200) {
                    this.sync.markDirty(item);
                }
                if (!this.isTicking() || this.isRemoving()) {
                    if (!this.isAdding()) {
                        IC2.TICK_HANDLER.addWorldCallback(this.f_58857_, x$0 -> this.initTick((Level)x$0));
                    }
                    this.addToTick();
                }
            }
            this.applySpeed(item);
            this.onItemEntered(item, null);
        }
    }

    @Override
    public boolean onCenterReached(TransportedItem item) {
        this.itemsToDelete.add((Object)item);
        if (this.isRendering()) {
            return true;
        }
        TeleportTubeTileEntity tile = TeleportManager.INSTANCE.getRandomTarget(this);
        if (tile == null) {
            Block.m_49840_((Level)this.f_58857_, (BlockPos)this.m_58899_(), (ItemStack)item.getStack());
        } else {
            tile.receiveItem(item);
        }
        return true;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0 && this.canDoAction(entity.m_20148_(), Action.TELEPORT_SETTINGS, key == 2)) {
            this.state = value;
            this.updateTileField("state");
        }
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        NBTBuffer buffer;
        String newFreq;
        super.onDataBufferReceived(player, id, data, target);
        if (target.isDedicatedServer() && this.canDoAction(player.m_20148_(), Action.TELEPORT_SETTINGS, false) && id.equalsIgnoreCase("frequency") && data instanceof NBTBuffer && !this.frequency.equals(newFreq = (buffer = (NBTBuffer)data).getNBT().m_128461_("frequency"))) {
            this.frequency = newFreq;
            this.updateTileField("frequency");
            if (this.addedToNetwork) {
                TeleportManager.INSTANCE.update(this);
            }
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToNetwork) {
            this.addedToNetwork = true;
            TeleportManager.INSTANCE.add(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToNetwork) {
            this.addedToNetwork = false;
            TeleportManager.INSTANCE.remove(this);
        }
        super.onUnloaded(chunk);
    }

    public boolean canReceive() {
        return (this.state & 2) != 0;
    }

    public boolean canSend() {
        return (this.state & 1) != 0;
    }

    public boolean isPublic() {
        return (this.state & 4) != 0;
    }

    public String getFrequency() {
        return this.frequency;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null && owner != null) {
            this.owner = owner;
            this.updateTileField("owner");
        }
    }

    @Override
    public boolean canDoAction(UUID user, Action action, boolean ignorePublic) {
        if (this.owner == null) {
            this.setOwner(user);
            return true;
        }
        if (this.owner.equals(user) || !ignorePublic && this.isPublic() || IC2.PLATFORM.isOp(user)) {
            return true;
        }
        Friend friend = FriendManager.getFriends().getFriend(this.owner, user);
        return friend != null && friend.canApplyAction(action);
    }
}

