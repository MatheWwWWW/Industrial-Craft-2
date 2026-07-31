/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.core.IC2Classic;
import ic2.api.energy.EnergyNet;
import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.tiles.IFluidMachine;
import ic2.api.tiles.IMachine;
import ic2.api.tiles.INotifiableMachine;
import ic2.api.tiles.tubes.ITube;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.misc.BucketFiller;
import ic2.core.block.base.misc.UpgradeStorage;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.TankComparator;
import ic2.core.block.base.tiles.BaseElectricTileEntity;
import ic2.core.block.machines.containers.lv.MachineTankContainer;
import ic2.core.fluid.LayeredFluidTank;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.FluidFilter;
import ic2.core.inventory.gui.components.simple.BufferBoxComponent;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.FluidHelper;
import java.util.EnumSet;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

public class MachineTankTileEntity
extends BaseElectricTileEntity
implements BufferBoxComponent.IBufferBox,
ITickListener,
IFluidMachine,
IMachine,
INotifiableMachine,
ITileGui,
Consumer<LayeredFluidTank>,
ITileActivityProvider,
IClickable {
    public static final EnumSet<IUpgradeItem.UpgradeType> UPGRADES = EnumSet.of(IUpgradeItem.UpgradeType.CUSTOM_MOD, IUpgradeItem.UpgradeType.MACHINE_MOD, IUpgradeItem.UpgradeType.REDSTONE_MOD, IUpgradeItem.UpgradeType.TRANSPORT_MOD, IUpgradeItem.UpgradeType.PROCESSING_MOD);
    boolean notified = false;
    @NetworkInfo
    public boolean tick = true;
    @NetworkInfo
    public boolean notifyWhenTick = false;
    @NetworkInfo
    public LayeredFluidTank tank;
    ICache<IFluidHandler> handler = new CapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER);
    protected UpgradeStorage storage = new UpgradeStorage();
    protected BucketFiller filler = new BucketFiller(this, this.tank, 4, 5);
    protected BucketFiller drainer = new BucketFiller(this, this.tank, 6, 7);

    public MachineTankTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 8, 32, 500);
        this.addCapability(IC2Classic.NOTIFY_CAPABILITY, this);
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this.tank);
        this.addCaches(this.handler);
        this.addGuiFields("tank", "tick", "notifyWhenTick");
        this.tank.addListener(this);
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addComparator(new TankComparator("tank", ComparatorNames.TANK, this.tank));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        this.tank = new LayeredFluidTank(64000);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerInputFilter(new FluidFilter.FluidTankFillFilter(this.tank), 6);
        handler.registerInputFilter(new FluidFilter.FluidTankFilter(this.tank), 4);
        handler.registerSlotAccess(AccessRule.IMPORT, 4, 6);
        handler.registerSlotAccess(AccessRule.EXPORT, 5, 7);
        handler.registerSlotsForSide(DirectionList.ALL, 4, 5, 6, 7);
        handler.registerNamedSlot(SlotType.INPUT, 4, 6);
        handler.registerNamedSlot(SlotType.OUTPUT, 5, 7);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128365_("fluids", (Tag)this.tank.write(new CompoundTag()));
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.tank.read(compound.m_128469_("fluids"));
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        if (FluidHelper.drainContainers(player.m_21120_(hand), player, this.tank)) {
            return true;
        }
        return FluidHelper.fillContainers(player.m_21120_(hand), player, this.tank);
    }

    @Override
    public void accept(LayeredFluidTank t) {
        this.updateGuiField("tank");
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
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MachineTankContainer(this, player, windowID);
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.setActive(!this.isRedstonePowered());
        this.filler.fillTank();
        this.drainer.fillItem();
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
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.MACHINE_TANK;
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
            ItemStack item = (ItemStack)this.inventory.get(i);
            if (!(item.m_41720_() instanceof IUpgradeItem)) continue;
            IUpgradeItem upgrade = (IUpgradeItem)item.m_41720_();
            upgrade.onInstall(item, this);
            extraEnergyStorage += upgrade.getExtraEnergyStorage(item, this) * item.m_41613_();
            energyStorageMultiplier *= Math.pow(upgrade.getEnergyStorageMultiplier(item, this), item.m_41613_());
            extraTier += upgrade.getExtraTier(item, this) * item.m_41613_();
            this.storage.validate(i, upgrade.getFunctions(item));
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
        return null;
    }

    @Override
    public IFluidHandler getConnectedTank(Direction dir) {
        return this.handler.getHandler(dir);
    }

    @Override
    public ITube getConnectedTube(Direction dir) {
        return null;
    }

    @Override
    public void onNotify() {
        this.notified = true;
    }
}

