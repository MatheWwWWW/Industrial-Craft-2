/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.registries.IRareEarthRegistry;
import ic2.api.tiles.readers.ISpeedMachine;
import ic2.api.tiles.readers.ISubProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IInventoryMachine;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.misc.comparator.types.base.SpeedComparator;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.machines.containers.mv.RareEarthCentrifugeContainer;
import ic2.core.block.machines.tiles.lv.RareEarthExtractorTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.MachineFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RareEarthCentrifugeTileEntity
extends BaseMachineTileEntity
implements ITickListener,
IInventoryMachine,
ISubProgressMachine,
ISpeedMachine,
ITileGui {
    public static final Component SPEED = Component.m_237115_((String)"info.block.ic2.rare_earth_centrifuge.speed");
    public static final EnumSet<IUpgradeItem.UpgradeType> UPGRADES = EnumSet.complementOf(EnumSet.of(IUpgradeItem.UpgradeType.RECIPE_MOD));
    public static final int MAX_SPEED = 10000;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_16)
    int speed = 0;
    @NetworkInfo
    ResourceLocation currentID = RareEarthExtractorTileEntity.EMPTY;
    @NetworkInfo
    public float materialProgress = 0.0f;
    @NetworkInfo
    public float progress = 0.0f;
    @NetworkInfo
    public boolean isProcessing;
    @NetworkInfo
    public float soundLevel = 1.0f;
    public IFilter gui_Filter = T -> this.getRegistry().getOutputFor(T) != null;

    public RareEarthCentrifugeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 3, 2, 15, 99, 5120, 128);
        this.setFuelSlot(0);
        this.addGuiFields("speed", "progress");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addComparator(new FlagComparator("working", ComparatorNames.WORKING, () -> this.isProcessing, 0, 15));
        this.addComparator(new SpeedComparator("speed", ComparatorNames.SPEED, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new MachineFilter(this), 1);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    public Component getSpeedName() {
        return SPEED;
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.currentID = new ResourceLocation(compound.m_128461_("recipe_id"));
        this.materialProgress = compound.m_128457_("material");
        this.progress = compound.m_128451_("progress");
        this.speed = compound.m_128451_("speed");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128359_("recipe_id", this.currentID.toString());
        compound.m_128350_("material", this.materialProgress);
        compound.m_128344_("progress", (byte)this.progress);
        compound.m_128376_("speed", (short)this.speed);
    }

    public IRareEarthRegistry getRegistry() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).rare_earth;
    }

    @Override
    public boolean supportsNotify() {
        return true;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.CENTRIFUGAL_RARE_EARTH_EXTRACTOR;
    }

    @Override
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1);
        this.inOut[1] = new RangedInventory(this, 2).setOutputOnly();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new RareEarthCentrifugeContainer(this, player, windowID);
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public int getMaxSpeed() {
        return 10000;
    }

    @Override
    public float getSubProgress() {
        return this.materialProgress;
    }

    @Override
    public float getMaxSubProgress() {
        return 1000.0f;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return this.operationLength;
    }

    public ItemStack getCurrentOutput() {
        return this.getRegistry().getOutputFor(this.currentID);
    }

    @Override
    public void onTick() {
        this.handleChargeSlot(this.maxEnergy);
        this.handleRedstone();
        if (this.hasEnergy(1) && (!((ItemStack)this.inventory.get(1)).m_41619_() || this.canProcess())) {
            this.setActive(true);
            if (this.speed < 10000) {
                ++this.speed;
                this.updateGuiField("speed");
            }
            this.useEnergy(1);
        } else {
            if (this.speed > 0) {
                this.speed = Math.max(0, this.speed - 4);
                this.updateGuiField("speed");
            }
            this.setActive(false);
        }
        this.progressPerTick = (float)this.speed / 300.0f;
        this.handleComparators();
        if (this.hasEnergy(this.energyConsume) && !((ItemStack)this.inventory.get(1)).m_41619_()) {
            IRareEarthRegistry.RareEntry entry = this.getRegistry().getOutputFor((ItemStack)this.inventory.get(1));
            if (entry == null || !this.canAdd(entry)) {
                if (this.progress != 0.0f) {
                    this.progress = 0.0f;
                    this.updateGuiField("progress");
                }
                this.isProcessing = false;
                return;
            }
            if (!entry.matches(this.currentID)) {
                this.currentID = entry.getID();
                this.materialProgress = 0.0f;
                this.updateGuiField("materialProgress");
                this.updateGuiField("currentID");
            }
            if (this.speed <= 0) {
                this.isProcessing = false;
                return;
            }
            this.isProcessing = true;
            this.progress += this.progressPerTick;
            this.useEnergy(this.energyConsume);
            if (this.progress >= (float)this.operationLength) {
                this.progress = 0.0f;
                this.materialProgress += entry.getValue();
                this.updateGuiField("materialProgress");
                ItemStack stack = (ItemStack)this.inventory.get(1);
                if (stack.hasCraftingRemainingItem()) {
                    this.inventory.set(1, (Object)stack.getCraftingRemainingItem());
                } else {
                    stack.m_41774_(1);
                }
                if (this.materialProgress >= 1000.0f) {
                    this.materialProgress -= 1000.0f;
                    this.setOrGrow(2, entry.getOutput().m_41777_(), false);
                }
                this.notifyListeners();
                this.storage.onRecipeFinished((NonNullList<ItemStack>)this.inventory, this);
            }
            this.updateGuiField("progress");
        } else {
            this.isProcessing = false;
            if (this.progress != 0.0f) {
                this.progress = Math.max(0.0f, ((ItemStack)this.inventory.get(1)).m_41619_() ? 0.0f : this.progress - 1.0f);
                this.updateGuiField("progress");
            }
        }
        this.storage.onTick((NonNullList<ItemStack>)this.inventory, this);
    }

    private boolean canAdd(IRareEarthRegistry.RareEntry entry) {
        return this.materialProgress + entry.getValue() < 1000.0f || ((ItemStack)this.inventory.get(2)).m_41619_() || StackUtil.canFitInto((ItemStack)this.inventory.get(2), entry.getOutput());
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        ItemStack inv = (ItemStack)this.inventory.get(1);
        if (inv.m_41619_()) {
            IRareEarthRegistry.RareEntry entry = this.getRegistry().getOutputFor(stack);
            if (entry != null) {
                return stack.m_41741_();
            }
        } else if (StackUtil.isStackEqual(inv, stack)) {
            return inv.m_41741_() - inv.m_41613_();
        }
        return 0;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return UPGRADES;
    }

    @Override
    public boolean isMachineWorking() {
        return this.isProcessing;
    }

    @Override
    public void onUpgradesChanged() {
        this.handleUpgrades(true);
    }

    @Override
    protected void handleMods() {
    }
}

