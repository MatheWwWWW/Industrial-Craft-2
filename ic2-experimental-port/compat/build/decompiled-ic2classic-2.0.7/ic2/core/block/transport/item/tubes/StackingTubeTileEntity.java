/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.transport.item.tubes;

import ic2.api.core.IC2Classic;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkClientEventListener;
import ic2.api.network.tile.PacketRange;
import ic2.api.tiles.tubes.TransportedItem;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.transport.item.TubeTileEntity;
import ic2.core.block.transport.item.container.StackingTubeContainer;
import ic2.core.inventory.base.ICamouflageGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.collection.SynchronizedList;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StackingTubeTileEntity
extends TubeTileEntity
implements IRedstoneListener,
ICamouflageGui,
INetworkClientEventListener {
    @NetworkInfo
    public SynchronizedList<StackingStack> cached = new SynchronizedList<StackingStack>(StackingStack::write, StackingStack::new);
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int stacking = 64;
    @NetworkInfo
    public boolean ignoreColors = false;
    boolean extracted = false;

    public StackingTubeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addNetworkFields("facing");
        this.addGuiFields("stacking", "cached", "ignoreColors");
    }

    @Override
    public boolean isFoamed() {
        return this.foamed > 0;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new StackingTubeContainer(this, player, windowID);
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        for (int i = 0; i < this.cached.size(); ++i) {
            ((StackingStack)this.cached.get(i)).addToDrops(drops);
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 1) {
            this.stacking = Mth.m_14045_((int)(this.stacking + value), (int)1, (int)64);
            this.updateGuiField("stacking");
        } else if (key == 2) {
            if (value < 0 || value >= this.cached.size()) {
                return;
            }
            IItemTransporter transporter = TransporterManager.getTransporter(entity);
            StackingStack entry = (StackingStack)this.cached.get(value);
            while (entry.getAmount() > 0) {
                ItemStack toAdd = entry.consume(64);
                int added = transporter.addItem(toAdd, Direction.DOWN, false);
                toAdd.m_41774_(added);
                if (toAdd.m_41613_() <= 0) continue;
                entry.add(toAdd.m_41613_());
                break;
            }
            if (entry.getAmount() <= 0) {
                this.cached.remove(value);
            }
            this.updateGuiField("cached");
        } else if (key == 3) {
            this.ignoreColors = !this.ignoreColors;
            this.updateGuiField("ignoreColors");
        }
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        ListTag list = new ListTag();
        int m = this.cached.size();
        for (int i = 0; i < m; ++i) {
            list.add((Object)((StackingStack)this.cached.get(i)).write());
        }
        NBTUtils.put(compound, "queue", list);
        NBTUtils.putByte(compound, "max", this.stacking, 64);
        NBTUtils.putBoolean(compound, "fast", this.extracted, false);
        NBTUtils.putBoolean(compound, "ignoreColors", this.ignoreColors, false);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        for (CompoundTag entry : NBTListWrapper.wrap(compound.m_128437_("queue", 10), CompoundTag.class)) {
            StackingStack stack = StackingStack.read(entry);
            if (stack == null) continue;
            this.cached.add(stack);
        }
        this.stacking = NBTUtils.getInt(compound, "max", 64);
        this.extracted = compound.m_128471_("fast");
        this.ignoreColors = compound.m_128471_("ignoreColors");
    }

    @Override
    protected boolean needsInitialRedstoneCheck() {
        return true;
    }

    @Override
    public boolean canLoseUpdateTick() {
        return false;
    }

    @Override
    public void onTubeUpdate() {
        if (this.isRendering() || this.invClock(this.extracted ? 1 : 40)) {
            return;
        }
        this.handleRedstone();
        boolean powered = this.isRedstonePowered();
        int max = powered ? -1 : this.stacking;
        int m = this.cached.size();
        for (int i = 0; i < m; ++i) {
            StackingStack entry = (StackingStack)this.cached.get(i);
            if (!entry.canExtract(max)) continue;
            TransportedItem item = new TransportedItem(entry.consume(powered ? 64 : this.stacking));
            this.addItem(item, null);
            item.setExportDirection(this.getFacing());
            if (entry.getAmount() <= 0) {
                this.cached.remove(i);
            }
            this.updateGuiField("cached");
            this.extracted = true;
            return;
        }
        this.extracted = false;
    }

    @Override
    public boolean onCenterReached(TransportedItem item) {
        if (this.isSimulating() && item.getRequestId() == null && (this.ignoreColors || item.getColor() == null) && item.getTransferDirection() != this.getFacing() && item.getStack().m_41741_() >= this.stacking) {
            this.updateGuiField("cached");
            ItemStack stack = item.getStack();
            int m = this.cached.size();
            for (int i = 0; i < m; ++i) {
                StackingStack entry = (StackingStack)this.cached.get(i);
                if (!entry.matches(stack)) continue;
                entry.add(stack.m_41613_());
                item.invalidate();
                this.sendToClient(1, item.getId(), PacketRange.CHUNK_TRACKED);
                return true;
            }
            this.cached.add(new StackingStack(stack));
            item.invalidate();
            this.sendToClient(1, item.getId(), PacketRange.CHUNK_TRACKED);
            return true;
        }
        return false;
    }

    @Override
    public boolean isRotatable() {
        return true;
    }

    @Override
    public boolean canSetFacingInternal(Direction dir) {
        return dir != this.getFacing() && (this.isRendering() ? DirectionList.getNeighborCapability(this, dir, IC2Classic.TUBE_CAPABILITY).isPresent() : this.tubes.getHandler(dir) != null);
    }

    @Override
    public DirectionList getValidDirections(TransportedItem item) {
        return DirectionList.ofFacing(this.getFacing()).remove(DirectionList.ofNumber(this.connectivity).invert());
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        DirectionList list;
        super.onBlockUpdate(block, from);
        if (this.inventories.getHandler(this.getFacing()) == null && this.tubes.getHandler(this.getFacing()) == null && (list = this.inventories.getPresentSides().add(this.tubes.getPresentSides())).size() > 0) {
            this.setFacing(list.getDefaultFacing());
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STACKING_TUBE;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }

    @Override
    public boolean allowWeakSignal(Direction dir) {
        return true;
    }

    public static class StackingStack {
        ItemStack stack;
        int amount;

        public StackingStack(ItemStack stack) {
            this.stack = StackUtil.copyWithSize(stack, 1);
            this.amount = stack.m_41613_();
        }

        public StackingStack(ItemStack stack, int amount) {
            this.stack = StackUtil.copyWithSize(stack, 1);
            this.amount = amount;
        }

        public StackingStack(IInputBuffer buffer) {
            this.stack = buffer.readItemStack();
            this.amount = buffer.readVarInt();
        }

        public static StackingStack read(CompoundTag nbt) {
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            return stack.m_41619_() ? null : new StackingStack(stack, nbt.m_128451_("items"));
        }

        public CompoundTag write() {
            CompoundTag data = this.stack.m_41739_(new CompoundTag());
            data.m_128405_("items", this.amount);
            return data;
        }

        public void add(int amount) {
            this.amount += amount;
        }

        public void addToDrops(List<ItemStack> list) {
            int oldAmount = this.amount;
            while (this.amount > 0) {
                ItemStack stack = this.consume(64);
                if (stack.m_41619_()) continue;
                list.add(stack);
            }
            this.amount = oldAmount;
        }

        public ItemStack consume(int amount) {
            if (this.getAmount() <= 0 || amount <= 0) {
                return ItemStack.f_41583_;
            }
            ItemStack result = StackUtil.copyWithSize(this.stack, Math.min(this.stack.m_41741_(), Math.min(this.getAmount(), amount)));
            this.amount -= result.m_41613_();
            return result;
        }

        @OnlyIn(value=Dist.CLIENT)
        public ItemStack getClientStack() {
            return StackUtil.copyWithSize(this.stack, Math.min(999, this.amount));
        }

        public void write(IOutputBuffer buffer) {
            buffer.writeItemStack(this.stack);
            buffer.writeVarInt(this.amount);
        }

        public boolean canExtract(int value) {
            return value < 0 || this.amount >= value;
        }

        public int getAmount() {
            return this.amount;
        }

        public ItemStack getStack() {
            return this.stack;
        }

        public boolean matches(ItemStack item) {
            return StackUtil.isStackEqual(this.stack, item);
        }
    }
}

