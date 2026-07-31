/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.core.IC2Classic;
import ic2.api.energy.EnergyNet;
import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.tiles.INotifiableMachine;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.UpgradeStorage;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.lv.MachineBufferContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.components.simple.BufferBoxComponent;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.math.MathUtils;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

public class MachineBufferTileEntity
extends BaseElectricTileEntity
implements INotifiableMachine,
ITickListener,
IInventoryMachine,
ITileGui,
BufferBoxComponent.IBufferBox,
ITileActivityProvider {
    public static final EnumSet<IUpgradeItem.UpgradeType> UPGRADES = EnumSet.of(IUpgradeItem.UpgradeType.CUSTOM_MOD, IUpgradeItem.UpgradeType.MACHINE_MOD, IUpgradeItem.UpgradeType.REDSTONE_MOD, IUpgradeItem.UpgradeType.TRANSPORT_MOD, IUpgradeItem.UpgradeType.PROCESSING_MOD);
    boolean notified = false;
    @NetworkInfo
    public boolean tick = true;
    @NetworkInfo
    public boolean notifyWhenTick = false;
    ICache<IItemHandler> inventories = new CapabilityCache<IItemHandler>(this, DirectionList.ALL, ForgeCapabilities.ITEM_HANDLER);
    ICache<ITube> tubeCache = new CapabilityCache<ITube>(this, DirectionList.ALL, IC2Classic.TUBE_CAPABILITY);
    protected UpgradeStorage storage = new UpgradeStorage();
    protected IHasInventory inout = new RangedInventory(this, MathUtils.fromTo(0, 9));

    public MachineBufferTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 13, 32, 500);
        this.addCapability(IC2Classic.NOTIFY_CAPABILITY, this);
        this.addCaches(this.inventories, this.tubeCache);
        this.addGuiFields("tick", "notifyWhenTick");
        this.sensitive = false;
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        int[] array = MathUtils.fromTo(0, 9);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, array);
        handler.registerSlotsForSide(DirectionList.ALL, array);
        handler.registerNamedSlot(SlotType.STORAGE, array);
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MACHINE_BUFFER;
    }

    @Override
    public boolean isTicking() {
        return this.tick;
    }

    @Override
    public boolean isTickNotified() {
        return this.notifyWhenTick;
    }

    @Override
    public IHasInventory getInputInventory() {
        return this.inout;
    }

    @Override
    public IHasInventory getOutputInventory() {
        return this.inout;
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 1) {
            this.tick = value == 1;
            this.updateGuiField("tick");
        }
        if (key == 2) {
            this.notifyWhenTick = value == 1;
            this.updateGuiField("notifyWhenTick");
        }
        super.onClientDataReceived(entity, key, value);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MachineBufferContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.setActive(!this.sensitive || this.isRedstonePowered());
        if (this.isActive()) {
            if (this.tick) {
                this.storage.onTick((NonNullList<ItemStack>)this.inventory, this);
            }
            if (this.notified) {
                if (!this.tick || this.notifyWhenTick) {
                    this.storage.onTick((NonNullList<ItemStack>)this.inventory, this);
                }
                this.notified = false;
            }
        }
        this.handleComparators();
    }

    @Override
    public void onNotify() {
        this.notified = true;
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        return stack.m_41741_();
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return UPGRADES;
    }

    @Override
    public int getAvailableEnergy() {
        return this.energy;
    }

    @Override
    public boolean useEnergy(int toUse, boolean doUse) {
        if (this.hasEnergy(toUse)) {
            if (doUse) {
                this.useEnergy(toUse);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMachineWorking() {
        return false;
    }

    @Override
    public boolean isRedstoneSensitive() {
        return this.sensitive;
    }

    @Override
    public void setRedstoneSensitive(boolean flag) {
        this.sensitive = flag;
    }

    @Override
    public void onUpgradesChanged() {
        int extraEnergyStorage = 0;
        double energyStorageMultiplier = 1.0;
        int extraTier = 0;
        boolean redstonePowered = false;
        this.sensitive = false;
        this.storage.clear();
        for (int i = 0; i < 4; ++i) {
            ItemStack item = (ItemStack)this.inventory.get(i + this.inventory.size() - 4);
            if (!(item.m_41720_() instanceof IUpgradeItem)) continue;
            IUpgradeItem upgrade = (IUpgradeItem)item.m_41720_();
            upgrade.onInstall(item, this);
            extraEnergyStorage += upgrade.getExtraEnergyStorage(item, this) * item.m_41613_();
            energyStorageMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, this), item.m_41613_());
            extraTier += upgrade.getExtraTier(item, this) * item.m_41613_();
            this.storage.validate(i + this.inventory.size() - 4, upgrade.getFunctions(item));
            if (!upgrade.useRedstoneInvertion(item, this)) continue;
            redstonePowered = true;
        }
        this.inverted = redstonePowered;
        this.setMaxEnergy(RecipeMods.apply(500, extraEnergyStorage, energyStorageMultiplier));
        this.tier = Mth.m_14045_((int)(this.baseTier + extraTier), (int)0, (int)13);
        this.maxInput = EnergyNet.INSTANCE.getPowerFromTier(this.tier);
        this.energy = Math.min(this.energy, this.maxEnergy);
        this.updateGuiFields("maxInput", "energy");
    }

    @Override
    public IItemHandler getConnectedInventory(Direction dir) {
        InventoryHandler handler = this.getInventoryHandler();
        return handler.isBlockSideUnlocked(dir) ? this.inventories.getHandler(dir) : null;
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        InventoryHandler handler = this.getInventoryHandler();
        return handler.isBlockSideUnlocked(dir) ? this.tubeCache.getHandler(dir) : null;
    }
}

