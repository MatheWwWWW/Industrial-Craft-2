/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.Container
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.block.personal;

import com.mojang.authlib.GameProfile;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLinked;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.personal.ContainerTradeOMatClosed;
import ic2.core.block.personal.ContainerTradeOMatOpen;
import ic2.core.block.personal.IPersonalBlock;
import ic2.core.block.personal.TileEntityPersonalChest;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.event.WorldData;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2Items;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import java.io.IOException;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileEntityTradeOMat
extends TileEntityInventory
implements IPersonalBlock,
IHasGui,
INetworkTileEntityEventListener,
INetworkClientTileEntityEventListener {
    private int ticker = IC2.random.m_188503_(64);
    private GameProfile owner = null;
    public int totalTradeCount = 0;
    public int stock = 0;
    public boolean infinite = false;
    private static final int stockUpdateRate = 64;
    private static final int EventTrade = 0;
    public final InvSlot demandSlot = new InvSlot(this, "demand", InvSlot.Access.NONE, 1);
    public final InvSlot offerSlot = new InvSlot(this, "offer", InvSlot.Access.NONE, 1);
    public final InvSlotConsumableLinked inputSlot = new InvSlotConsumableLinked(this, "input", 1, this.demandSlot);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);

    public TileEntityTradeOMat(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.TRADE_O_MAT, blockPos, blockState);
    }

    @Override
    public void m_142466_(CompoundTag compoundTag) {
        super.m_142466_(compoundTag);
        if (compoundTag.m_128441_("ownerGameProfile")) {
            this.owner = NbtUtils.m_129228_((CompoundTag)compoundTag.m_128469_("ownerGameProfile"));
        }
        this.totalTradeCount = compoundTag.m_128451_("totalTradeCount");
        if (compoundTag.m_128441_("infinite")) {
            this.infinite = compoundTag.m_128471_("infinite");
        }
    }

    @Override
    public void m_183515_(CompoundTag compoundTag) {
        super.m_183515_(compoundTag);
        if (this.owner != null) {
            CompoundTag compoundTag2 = new CompoundTag();
            NbtUtils.m_129230_((CompoundTag)compoundTag2, (GameProfile)this.owner);
            compoundTag.m_128365_("ownerGameProfile", (Tag)compoundTag2);
        }
        compoundTag.m_128405_("totalTradeCount", this.totalTradeCount);
        if (this.infinite) {
            compoundTag.m_128379_("infinite", this.infinite);
        }
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("owner");
        return list;
    }

    public final boolean isWireless() {
        return this.getActive();
    }

    public final boolean setWireless(boolean bl) {
        if (this.isWireless() == bl) {
            return false;
        }
        if (bl) {
            this.setActive(true);
            WorldData.get((Level)this.f_58857_).tradeMarket.registerTradeOMat(this);
        } else {
            this.setActive(false);
            WorldData.get((Level)this.f_58857_).tradeMarket.unregisterTradeOMat(this);
        }
        return true;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        this.trade();
        if (this.infinite) {
            this.stock = -1;
        } else if (++this.ticker % 64 == 0) {
            this.updateStock();
        }
    }

    private void trade() {
        ItemStack itemStack = this.inputSlot.consumeLinked(true);
        if (StackUtil.isEmpty(itemStack)) {
            return;
        }
        ItemStack itemStack2 = this.offerSlot.get();
        if (StackUtil.isEmpty(itemStack2)) {
            return;
        }
        if (!this.outputSlot.canAdd(itemStack2)) {
            return;
        }
        if (this.infinite) {
            this.inputSlot.consumeLinked(false);
            this.outputSlot.add(itemStack2);
        } else {
            int n = StackUtil.fetch(this, itemStack2, true);
            if (n != StackUtil.getSize(itemStack2)) {
                return;
            }
            int n2 = StackUtil.distribute(this, itemStack, true);
            if (n2 != StackUtil.getSize(itemStack)) {
                return;
            }
            n = StackUtil.fetch(this, itemStack2, false);
            if (n == 0) {
                return;
            }
            if (n != StackUtil.getSize(itemStack2)) {
                IC2.log.warn(LogCategory.Block, "The Trade-O-Mat at %s received an inconsistent result from an adjacent trade supply inventory, the %s items will be lost.", Util.formatPosition(this), n);
                return;
            }
            StackUtil.distribute(this, this.inputSlot.consumeLinked(false), false);
            this.outputSlot.add(itemStack2);
            --this.stock;
        }
        ++this.totalTradeCount;
        IC2.network.get(true).initiateTileEntityEvent(this, 0, true);
        this.m_6596_();
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!this.f_58857_.f_46443_) {
            this.updateStock();
            if (this.isWireless()) {
                WorldData.get((Level)this.f_58857_).tradeMarket.registerTradeOMat(this);
            }
        }
    }

    @Override
    protected InteractionResult onActivated(Player player, InteractionHand interactionHand, Direction direction, Vec3 vec3) {
        if (!this.isWireless() && StackUtil.consume(player, interactionHand, StackUtil.sameItem(Ic2Items.REMOTE_INTERFACE_UPGRADE), 1)) {
            if (!this.m_58904_().f_46443_) {
                this.setWireless(true);
            }
            return InteractionResult.CONSUME;
        }
        return super.onActivated(player, interactionHand, direction, vec3);
    }

    public void updateStock() {
        ItemStack itemStack = this.offerSlot.get();
        this.stock = StackUtil.isEmpty(itemStack) ? 0 : StackUtil.fetch(this, StackUtil.copyWithSize(itemStack, Integer.MAX_VALUE), true) / StackUtil.getSize(itemStack);
    }

    @Override
    protected void onUnloaded() {
        super.onUnloaded();
        if (!this.m_58904_().f_46443_ && this.isWireless()) {
            WorldData.get((Level)this.f_58857_).tradeMarket.unregisterTradeOMat(this);
        }
    }

    @Override
    public boolean wrenchCanRemove(Player player) {
        return this.permitsAccess(player.m_36316_());
    }

    @Override
    protected List<ItemStack> getAuxDrops(int n) {
        List<ItemStack> list = super.getAuxDrops(n);
        if (this.isWireless()) {
            list.add(new ItemStack((ItemLike)Ic2Items.REMOTE_INTERFACE_UPGRADE));
        }
        return list;
    }

    @Override
    public boolean permitsAccess(GameProfile gameProfile) {
        return TileEntityPersonalChest.checkAccess(this, gameProfile);
    }

    @Override
    public Container getPrivilegedInventory(GameProfile gameProfile) {
        return this;
    }

    @Override
    public GameProfile getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(GameProfile gameProfile) {
        this.owner = gameProfile;
    }

    @Override
    protected boolean canEntityDestroy(Entity entity) {
        return false;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        if (this.permitsAccess(player.m_36316_())) {
            return new ContainerTradeOMatOpen(n, player.m_150109_(), this, this.canToggleInfinite(player));
        }
        return new ContainerTradeOMatClosed(n, player.m_150109_(), this);
    }

    @Override
    public void writeScreenOpenData(Player player, InteractionHand interactionHand, GrowingBuffer growingBuffer) throws IOException {
        boolean bl = this.permitsAccess(player.m_36316_());
        growingBuffer.writeBoolean(bl);
        if (bl) {
            growingBuffer.writeBoolean(this.canToggleInfinite(player));
        }
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        if (growingBuffer.readBoolean()) {
            return new ContainerTradeOMatOpen(n, inventory, this, growingBuffer.readBoolean());
        }
        return new ContainerTradeOMatClosed(n, inventory, this);
    }

    @Override
    public void onScreenClosed(Player player) {
    }

    @Override
    public void onNetworkEvent(int n) {
        switch (n) {
            case 0: {
                IC2.audioManager.playOnce(this, "Machines/o-mat.ogg");
                break;
            }
            default: {
                IC2.sideProxy.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + n + ", tile entity below)\nT: " + this + " (" + this.f_58858_ + ")", new Object[0]);
            }
        }
    }

    @Override
    public void onNetworkEvent(Player player, int n) {
        if (n == 0 && this.canToggleInfinite(player)) {
            boolean bl = this.infinite = !this.infinite;
            if (!this.infinite) {
                this.updateStock();
            }
        }
    }

    private boolean canToggleInfinite(Player player) {
        MinecraftServer minecraftServer = player.m_20194_();
        return minecraftServer != null && minecraftServer.m_6846_().m_11303_(player.m_36316_());
    }
}

