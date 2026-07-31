/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.EmptyHandler
 */
package ic2.core.block.personal.tile;

import ic2.api.items.ItemRegistries;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.cache.PersonalCapabilityCache;
import ic2.core.block.base.tiles.impls.BaseTradeOMatTileEntity;
import ic2.core.block.personal.container.TradeOMatContainer;
import ic2.core.block.personal.provider.IItemTradeProvider;
import ic2.core.block.personal.provider.ItemTradeProvider;
import ic2.core.block.personal.trade.ItemTrade;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Tiles;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

public class TradeOMatTileEntity
extends BaseTradeOMatTileEntity<ItemTrade, IItemTradeProvider>
implements IItemTradeProvider {
    protected ICache<IItemHandler> tradeSources = new PersonalCapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    protected IHasInventory input = new RangedInventory(this, 0, 1, 2, 3);
    protected IHasInventory output = new RangedInventory(this, 4, 5, 6, 7, 8, 9, 10, 11);

    public TradeOMatTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 12, new ItemTrade());
        this.addCaches(this.tradeSources);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new TradeOMatContainer(this, player, windowID, !IC2.KEYBOARD.isSideInventoryKeyDown(player) && this.canDoAction(player.m_20148_(), Action.TRADE_SETTINGS, false));
    }

    @Override
    protected IItemTradeProvider createForPlayer(UUID player) {
        return new ItemTradeProvider(player);
    }

    @Override
    protected IHasInventory getInput() {
        return this.input;
    }

    @Override
    public IHasInventory getTradeInput() {
        return this.input;
    }

    @Override
    public IHasInventory getTradeOutput() {
        return this.output;
    }

    @Override
    protected IItemTradeProvider getSelf() {
        return this;
    }

    @Override
    public UUID getTradeOwner() {
        return null;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TRADE_O_MAT;
    }

    @Override
    protected boolean attemptTrade(IItemTradeProvider provider) {
        if ((!this.tradeSources.isEmpty() || ((ItemTrade)this.trade).infinite) && ((ItemTrade)this.trade).canTrade(this.f_58857_.m_46467_(), provider.getTradeOwner())) {
            if (((ItemTrade)this.trade).infinite) {
                if (this.trade(EmptyHandler.INSTANCE, provider)) {
                    ((ItemTrade)this.trade).onTrade(this.f_58857_.m_46467_(), provider.getTradeOwner());
                    this.onTraded();
                    return true;
                }
            } else {
                for (Direction dir : this.tradeSources) {
                    if (!this.trade(this.tradeSources.getHandler(dir), provider)) continue;
                    ((ItemTrade)this.trade).onTrade(this.f_58857_.m_46467_(), provider.getTradeOwner());
                    this.onTraded();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean trade(IItemHandler handler, IItemTradeProvider provider) {
        if (handler != null) {
            SimpleInventory inputCopy = SimpleInventory.createCopy(provider.getTradeInput());
            NonNullList stockResult = NonNullList.m_122779_();
            NonNullList result = NonNullList.m_122779_();
            if (((ItemTrade)this.trade).moneyNeeded > 0) {
                int moneyFound = ((ItemTrade)this.trade).getFoundMoney(inputCopy);
                if (((ItemTrade)this.trade).moneyNeeded > moneyFound) {
                    return false;
                }
                int coinsLeft = ((ItemTrade)this.trade).consumeCoins(inputCopy, ((ItemTrade)this.trade).moneyNeeded);
                ItemRegistries.generateCoins(((ItemTrade)this.trade).moneyNeeded, (NonNullList<ItemStack>)stockResult, false);
                if (coinsLeft < 0) {
                    ItemRegistries.generateCoins(-coinsLeft, (NonNullList<ItemStack>)result, false);
                }
            } else if (!((ItemTrade)this.trade).hasItems(TransporterManager.getTransporter(inputCopy), this.inputFilter, (NonNullList<ItemStack>)stockResult)) {
                return false;
            }
            SimpleInventory stockCopy = SimpleInventory.createCopy(handler);
            if (!((ItemTrade)this.trade).hasStock(TransporterManager.getTransporter(stockCopy), this.outputFilter, (NonNullList<ItemStack>)result)) {
                return false;
            }
            if (!((ItemTrade)this.trade).infinite && !TradeOMatTileEntity.insertItems(TransporterManager.getTransporter(stockCopy), (NonNullList<ItemStack>)stockResult, null, false)) {
                return false;
            }
            SimpleInventory outputCopy = SimpleInventory.createCopy(provider.getTradeOutput());
            if (!TradeOMatTileEntity.insertItems(TransporterManager.getTransporter(outputCopy), (NonNullList<ItemStack>)result, null, false)) {
                return false;
            }
            TradeOMatTileEntity.mergeCoins(stockCopy);
            TradeOMatTileEntity.mergeCoins(outputCopy);
            outputCopy.overrideTo(provider.getTradeOutput());
            inputCopy.overrideTo(provider.getTradeInput());
            stockCopy.applyDifferences(handler);
            return true;
        }
        return false;
    }

    @Override
    protected int recalculateStock() {
        if (((ItemTrade)this.trade).infinite) {
            return Short.MAX_VALUE;
        }
        int total = 0;
        for (Direction dir : this.tradeSources) {
            total += this.countStock(this.tradeSources.getHandler(dir));
        }
        return total;
    }

    public int countStock(IItemHandler handler) {
        if (handler != null) {
            IItemTransporter stockCopy = TransporterManager.getTransporter(SimpleInventory.createCopy(handler));
            int amount = 0;
            while (((ItemTrade)this.trade).hasStock(stockCopy, this.outputFilter, (NonNullList<ItemStack>)NonNullList.m_122779_())) {
                ++amount;
            }
            return amount;
        }
        return 0;
    }
}

