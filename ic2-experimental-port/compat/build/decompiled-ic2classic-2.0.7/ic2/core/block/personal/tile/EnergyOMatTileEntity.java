/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.personal.tile;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.items.IUpgradeItem;
import ic2.api.items.ItemRegistries;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.tiles.IMachine;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.cache.PersonalCapabilityCache;
import ic2.core.block.base.features.IDropProvider;
import ic2.core.block.base.tiles.impls.BaseTradeOMatTileEntity;
import ic2.core.block.personal.container.EnergyOMatContainer;
import ic2.core.block.personal.provider.ITradeProvider;
import ic2.core.block.personal.trade.EnergyTrade;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.inventory.transporter.TransporterManager;
import ic2.core.platform.player.friends.Action;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class EnergyOMatTileEntity
extends BaseTradeOMatTileEntity<EnergyTrade, ITradeProvider>
implements IEnergySource,
IEnergySink,
IMachine,
IDropProvider {
    static final EnumSet<IUpgradeItem.UpgradeType> TYPES = EnumSet.of(IUpgradeItem.UpgradeType.MACHINE_MOD);
    protected ICache<IItemHandler> items = new PersonalCapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    public SimpleInventory upgrades = new SimpleInventory(2);
    @NetworkInfo
    public int payedEnergy = 0;
    @NetworkInfo
    public int lastPayed = 0;
    @NetworkInfo
    public int maxOutput = 32;
    @NetworkInfo
    public int storedEU = 0;
    @NetworkInfo
    public int maxBuffer = 10000;
    protected IHasInventory input = new RangedInventory(this, 0, 1, 2, 3);
    protected IHasInventory output = new RangedInventory(this, 4, 5, 6, 7);
    protected boolean addedToEnet;

    public EnergyOMatTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 8, new EnergyTrade());
        this.addCaches(this.items);
        this.addGuiFields("payedEnergy", "maxOutput", "maxBuffer", "lastPayed");
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new EnergyOMatContainer(this, player, windowID, !IC2.KEYBOARD.isSideInventoryKeyDown(player) && this.canDoAction(player.m_20148_(), Action.TRADE_SETTINGS, false));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ENERGY_O_MAT;
    }

    @Override
    protected ITradeProvider createForPlayer(UUID player) {
        return null;
    }

    @Override
    protected ITradeProvider getSelf() {
        return null;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.payedEnergy = compound.m_128451_("payed");
        this.lastPayed = compound.m_128451_("lastPayed");
        this.storedEU = compound.m_128451_("storedEU");
        this.upgrades.load(compound.m_128469_("upgrades"));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "payed", this.payedEnergy, 0);
        NBTUtils.putInt(compound, "lastPayed", this.lastPayed, 0);
        NBTUtils.putInt(compound, "storedEU", this.storedEU, 0);
        NBTUtils.put(compound, "upgrades", this.upgrades.save(new CompoundTag()));
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        super.addDrops(drops);
        this.upgrades.addToDrops(drops);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (this.canDoAction(entity.m_20148_(), Action.TRADE_SETTINGS, false) && key == 8) {
            ((EnergyTrade)this.getTrade()).offeredEnergy = Mth.m_14045_((int)value, (int)0, (int)1000000);
            this.updateGuiField("trade");
        }
    }

    @Override
    protected boolean attemptTrade(ITradeProvider provider) {
        if (((EnergyTrade)this.trade).canTrade(this.f_58857_.m_46467_(), null) && this.trade()) {
            ((EnergyTrade)this.trade).onTrade(this.f_58857_.m_46467_(), null);
            this.onTraded();
            return true;
        }
        return false;
    }

    protected boolean trade() {
        if (((EnergyTrade)this.trade).canAdd(this.payedEnergy)) {
            SimpleInventory inputCopy = SimpleInventory.createCopy(this.input);
            NonNullList stockResult = NonNullList.m_122779_();
            NonNullList result = NonNullList.m_122779_();
            if (((EnergyTrade)this.trade).moneyNeeded > 0) {
                int moneyFound = ((EnergyTrade)this.trade).getFoundMoney(inputCopy);
                if (((EnergyTrade)this.trade).moneyNeeded > moneyFound) {
                    return false;
                }
                int coinsLeft = ((EnergyTrade)this.trade).consumeCoins(inputCopy, ((EnergyTrade)this.trade).moneyNeeded);
                ItemRegistries.generateCoins(((EnergyTrade)this.trade).moneyNeeded, (NonNullList<ItemStack>)stockResult, false);
                if (coinsLeft < 0) {
                    ItemRegistries.generateCoins(-coinsLeft, (NonNullList<ItemStack>)result, false);
                }
            } else if (!((EnergyTrade)this.trade).hasItems(TransporterManager.getTransporter(inputCopy), this.inputFilter, (NonNullList<ItemStack>)stockResult)) {
                return false;
            }
            SimpleInventory outputCopy = SimpleInventory.createCopy(this.output);
            if (!EnergyOMatTileEntity.insertItems(TransporterManager.getTransporter(outputCopy), (NonNullList<ItemStack>)result, null, false)) {
                return false;
            }
            if (!((EnergyTrade)this.trade).infinite && !this.insertItems((NonNullList<ItemStack>)stockResult)) {
                return false;
            }
            this.payedEnergy += ((EnergyTrade)this.trade).offeredEnergy;
            this.lastPayed += ((EnergyTrade)this.trade).offeredEnergy;
            this.updateGuiFields("payedEnergy", "lastPayed");
            EnergyOMatTileEntity.mergeCoins(outputCopy);
            outputCopy.overrideTo(this.output);
            inputCopy.overrideTo(this.input);
            return true;
        }
        return false;
    }

    @Override
    protected int recalculateStock() {
        return ((EnergyTrade)this.trade).offeredEnergy <= 0 ? 0 : (Integer.MAX_VALUE - this.payedEnergy) / ((EnergyTrade)this.trade).offeredEnergy;
    }

    @Override
    protected IHasInventory getInput() {
        return this.input;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnet) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnet) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return side == this.getFacing();
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return side == this.getFacing().m_122424_();
    }

    @Override
    public int getSinkTier() {
        return 13;
    }

    @Override
    public int getRequestedEnergy() {
        return Math.min(this.payedEnergy, this.maxBuffer - this.storedEU);
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        int toAdd = Math.min(Math.min(this.payedEnergy, this.maxBuffer - this.storedEU), amount);
        this.payedEnergy -= toAdd;
        this.storedEU += toAdd;
        this.updateGuiFields("payedEnergy", "storedEU");
        return amount - toAdd;
    }

    @Override
    public int getSourceTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.maxOutput);
    }

    @Override
    public int getMaxEnergyOutput() {
        return Short.MAX_VALUE;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(this.maxOutput, this.storedEU);
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.storedEU -= consumed;
        if (this.storedEU <= 0 && this.payedEnergy <= 0) {
            this.lastPayed = 0;
            this.updateGuiField("lastPayed");
        }
        this.updateGuiField("storedEU");
    }

    @Override
    public void onUpgradesChanged() {
        int extraEnergy = 0;
        double energyMultiplier = 1.0;
        int extraTier = 0;
        for (int i = 0; i < 2; ++i) {
            ItemStack item = this.upgrades.getStackInSlot(i);
            if (!(item.m_41720_() instanceof IUpgradeItem)) continue;
            IUpgradeItem upgrade = (IUpgradeItem)item.m_41720_();
            upgrade.onInstall(item, this);
            extraEnergy += upgrade.getExtraEnergyStorage(item, this) * item.m_41613_();
            energyMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, this), item.m_41613_());
            extraTier += upgrade.getExtraTier(item, this) * item.m_41613_();
        }
        this.maxBuffer = Math.min(RecipeMods.apply(10000, extraEnergy, energyMultiplier), 500000);
        this.maxOutput = EnergyNet.INSTANCE.getPowerFromTier(Mth.m_14045_((int)(1 + extraTier), (int)1, (int)6));
        if (this.storedEU > this.maxBuffer) {
            this.storedEU = this.maxBuffer;
        }
        this.updateGuiFields("maxBuffer", "maxOutput");
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        return 0;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return TYPES;
    }

    @Override
    public int getAvailableEnergy() {
        return 0;
    }

    @Override
    public boolean useEnergy(int toUse, boolean doUse) {
        return false;
    }

    @Override
    public boolean isMachineWorking() {
        return false;
    }

    @Override
    public void setRedstoneSensitive(boolean flag) {
    }

    @Override
    public boolean isRedstoneSensitive() {
        return false;
    }

    @Override
    public IItemHandler getConnectedInventory(Direction dir) {
        return null;
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        return null;
    }

    protected boolean insertItems(NonNullList<ItemStack> toInsert) {
        for (Direction dir : this.items) {
            SimpleInventory inv;
            IItemHandler handler = this.items.getHandler(dir);
            if (handler == null || !EnergyOMatTileEntity.insertItems(TransporterManager.getTransporter(inv = SimpleInventory.createCopy(handler)), toInsert, null, false)) continue;
            EnergyOMatTileEntity.mergeCoins(inv);
            inv.applyDifferences(handler);
            return true;
        }
        return false;
    }
}

