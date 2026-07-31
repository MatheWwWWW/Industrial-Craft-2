/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.items.ICoinItem;
import ic2.api.items.ItemRegistries;
import ic2.api.network.buffer.NetworkInfo;
import ic2.core.IC2;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.personal.IPersonalTile;
import ic2.core.block.base.features.personal.IPersonalWrenchable;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.personal.components.BaseTradeOMatComponent;
import ic2.core.block.personal.provider.ITradeProvider;
import ic2.core.block.personal.trade.Trade;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.inventory.transporter.transporters.special.DirectionalTransporter;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.NBTUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class BaseTradeOMatTileEntity<T extends Trade, V extends ITradeProvider>
extends BaseInventoryTileEntity
implements IPersonalTile,
IPersonalWrenchable,
ITileGui,
ITickListener {
    @NetworkInfo
    protected UUID owner;
    @NetworkInfo
    protected T trade;
    @NetworkInfo
    public int stock = 0;
    @NetworkInfo
    public int inputFilter = 4;
    @NetworkInfo
    public int outputFilter = 4;
    protected Map<UUID, V> playerTradeInfo = CollectionUtils.createLinkedMap();
    Set<UUID> watchingPlayers = CollectionUtils.createSet();

    public BaseTradeOMatTileEntity(BlockPos pos, BlockState state, int slotCount, T trade) {
        super(pos, state, slotCount);
        this.trade = trade;
        this.addNetworkFields("owner", "trade");
        this.addGuiFields("trade", "stock", "inputFilter", "outputFilter");
    }

    protected abstract boolean attemptTrade(V var1);

    protected abstract int recalculateStock();

    protected abstract IHasInventory getInput();

    protected abstract V createForPlayer(UUID var1);

    protected abstract V getSelf();

    public T getTrade() {
        return this.trade;
    }

    public V getOrCreate(Player player) {
        this.watchingPlayers.add(player.m_20148_());
        return (V)this.playerTradeInfo.computeIfAbsent(player.m_20148_(), this::createForPlayer);
    }

    @Override
    public void onGuiClosed(Player player) {
        this.watchingPlayers.remove(player.m_20148_());
    }

    public void onTraded() {
    }

    public void playTradeSound() {
        IC2.AUDIO.playSound(this, IC2Sounds.TRADED_SOUND);
    }

    protected boolean shouldRefresh() {
        if (((Trade)this.trade).perPlayer) {
            boolean result = false;
            for (UUID key : this.playerTradeInfo.keySet()) {
                result |= ((Trade)this.trade).shouldRefresh(this.f_58857_.m_46467_(), key);
            }
            return result;
        }
        return ((Trade)this.trade).shouldRefresh(this.f_58857_.m_46467_(), null);
    }

    @Override
    public void onTick() {
        if (this.clock(5) && this.shouldRefresh()) {
            this.stock = this.recalculateStock();
            this.updateGuiFields("stock", "trade");
        }
        if (this.clock(20)) {
            boolean didTrade = false;
            if (((Trade)this.trade).perPlayer && ((Trade)this.trade).canDoPerPlayer()) {
                Iterator<V> iter = this.playerTradeInfo.values().iterator();
                while (iter.hasNext()) {
                    ITradeProvider entry = (ITradeProvider)iter.next();
                    int i = 0;
                    while (this.attemptTrade(entry) && i++ < 256) {
                        didTrade = true;
                    }
                    if (i != 0 || this.watchingPlayers.contains(entry.getTradeOwner()) || !((Trade)this.trade).canRemove(entry.getTradeOwner())) continue;
                    iter.remove();
                }
            } else {
                int i = 0;
                while (this.attemptTrade(this.getSelf()) && i++ < 256) {
                    didTrade = true;
                }
            }
            if (didTrade) {
                this.playTradeSound();
            }
            if (didTrade || this.clock(400)) {
                this.stock = this.recalculateStock();
                this.updateGuiFields("stock", "trade");
            }
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void onGuiFieldChanged(Set<String> fields, Player player) {
        BaseTradeOMatComponent trade;
        ContainerComponent comp;
        super.onGuiFieldChanged(fields, player);
        if ((fields.contains("trade") || fields.contains("helper") && this.canDoAction(player.m_20148_(), Action.TRADE_SETTINGS, false)) && (comp = (ContainerComponent)this.getContainer(player, ContainerComponent.class)) != null && (trade = comp.getComponent(BaseTradeOMatComponent.class)) != null) {
            trade.onChanged();
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (this.canDoAction(entity.m_20148_(), Action.TRADE_SETTINGS, false)) {
            if (key == 0) {
                this.inputFilter ^= value;
                this.updateGuiField("inputFilter");
            } else if (key == 1) {
                this.outputFilter ^= value;
                this.updateGuiField("outputFilter");
            } else if (key == 2) {
                ((Trade)this.trade).updateMoneyTrade(Math.max(0, value));
                this.updateGuiField("trade");
            } else if (key == 3) {
                ((Trade)this.trade).tradeDelay = Math.max(0, value);
                this.updateGuiField("trade");
            } else if (key == 4) {
                ((Trade)this.trade).tradeCap = Math.max(0, value);
                this.updateGuiField("trade");
            } else if (key == 5) {
                ((Trade)this.trade).tradeReset = !((Trade)this.trade).tradeReset && ((Trade)this.trade).tradeCap > 0 && ((Trade)this.trade).tradeDelay > 0;
                this.updateGuiField("trade");
            } else if (key == 6) {
                if (IC2.PLATFORM.isOp(entity.m_20148_())) {
                    ((Trade)this.trade).infinite = !((Trade)this.trade).infinite;
                    this.updateGuiField("trade");
                } else {
                    entity.m_5661_((Component)this.translate("gui.ic2.trade.infinite_op"), false);
                }
            } else if (key == 8) {
                ((Trade)this.trade).perPlayer = !((Trade)this.trade).perPlayer && ((Trade)this.trade).canDoPerPlayer();
                ((Trade)this.trade).reset();
                this.updateTileField("trade");
                this.updateGuiField("trade");
            }
        }
        if (key == 7) {
            V provider = ((Trade)this.trade).perPlayer ? this.getOrCreate(entity) : this.getSelf();
            ((Trade)this.trade).findItems(new DirectionalTransporter(TransporterManager.getTransporter(entity), Direction.DOWN), TransporterManager.getTransporter(((Trade)this.trade).canDoPerPlayer() ? provider.getTradeInput() : this.getInput()), this.inputFilter);
        }
        super.onClientDataReceived(entity, key, value);
    }

    @Override
    public boolean canSetFacing(Direction dir, Player player) {
        return dir != this.getFacing() && dir.m_122434_().m_122479_() && this.canDoAction(player.m_20148_(), Action.TRADE_SETTINGS, false);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return this.canDoAction(player.m_20148_(), Action.TRADE_SETTINGS, false);
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }

    @Override
    public void setOwner(UUID owner) {
        if (this.owner == null && owner != null) {
            this.owner = owner;
            this.updateTileField("owner");
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
        return this.owner.equals(user) || IC2.PLATFORM.isOp(user);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.owner = compound.m_128403_("owner") ? compound.m_128342_("owner") : null;
        ((Trade)this.trade).load(compound.m_128469_("trade"));
        this.inputFilter = NBTUtils.getInt(compound, "input", 4);
        this.outputFilter = NBTUtils.getInt(compound, "output", 4);
        this.stock = compound.m_128451_("stock");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        if (this.owner != null) {
            compound.m_128362_("owner", this.owner);
        }
        NBTUtils.put(compound, "trade", ((Trade)this.trade).save(new CompoundTag()));
        NBTUtils.putInt(compound, "input", this.inputFilter, 4);
        NBTUtils.putInt(compound, "output", this.outputFilter, 4);
        NBTUtils.putInt(compound, "stock", this.stock, 0);
    }

    public static void mergeCoins(IHasInventory inv) {
        int moneyFound = 0;
        int m = inv.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!(stack.m_41720_() instanceof ICoinItem)) continue;
            moneyFound += ((ICoinItem)stack.m_41720_()).getMoneyValue(stack) * stack.m_41613_();
            inv.setStackInSlot(i, ItemStack.f_41583_);
        }
        NonNullList list = NonNullList.m_122779_();
        ItemRegistries.generateCoins(moneyFound, (NonNullList<ItemStack>)list, false);
        BaseTradeOMatTileEntity.insertItems(TransporterManager.getTransporter(inv), (NonNullList<ItemStack>)list, null, false);
    }

    public static boolean insertItems(IItemTransporter trans, NonNullList<ItemStack> list, Direction dir, boolean simulate) {
        int m = list.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)list.get(i);
            if (stack.m_41619_() || trans.addItem(stack, dir, simulate) >= stack.m_41613_()) continue;
            return false;
        }
        return true;
    }
}

