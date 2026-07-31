/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls.machine.multi;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.queue.MultiStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.tiles.readers.ISpeedMachine;
import ic2.api.util.DirectionList;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.SpeedComparator;
import ic2.core.block.base.tiles.impls.machine.multi.BasicMultiMachineTileEntity;
import ic2.core.block.machines.containers.mv.AdvMultiMachineContainer;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.MachineFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.geometry.Box2i;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseAdvMultiMachineTileEntity
extends BasicMultiMachineTileEntity
implements ISpeedMachine {
    public static final EnumSet<IUpgradeItem.UpgradeType> TYPES = EnumSet.complementOf(EnumSet.of(IUpgradeItem.UpgradeType.RECIPE_MOD));
    public static final int MAX_SPEED = 10000;
    public static final Box2i PROGRESS_BOX = new Box2i(79, 34, 24, 16);
    public static final Box2i CHARGE_BOX = new Box2i(56, 36, 14, 14);
    @NetworkInfo
    public boolean isProcessing = false;
    @NetworkInfo
    public int speed;

    public BaseAdvMultiMachineTileEntity(BlockPos pos, BlockState state, int slots, int energyPerTick, int maxProgress) {
        this(pos, state, slots, 2, energyPerTick, maxProgress);
    }

    public BaseAdvMultiMachineTileEntity(BlockPos pos, BlockState state, int slots, int upgradeSlots, int energyPerTick, int maxProgress) {
        this(pos, state, slots, upgradeSlots, energyPerTick, maxProgress, 10000, 128);
    }

    public BaseAdvMultiMachineTileEntity(BlockPos pos, BlockState state, int size, int upgradeSlots, int energyPerTick, int maxProgress, int maxEnergy, int maxInput) {
        super(pos, state, size, upgradeSlots, energyPerTick, maxProgress, maxEnergy, maxInput);
        this.isWorkingString = "isProcessing";
        this.sensitive = true;
        this.addGuiFields("speed");
        this.addNetworkFields("isProcessing");
        this.addComparator(new FlagComparator("working", ComparatorNames.WORKING, () -> this.isProcessing, 0, 15));
        this.addComparator(new SpeedComparator("speed", ComparatorNames.SPEED, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, this.getOutputSlots());
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), this.getOutputSlots());
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new MachineFilter(this), 1);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, this.getOutputSlots());
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, this.getOutputSlots());
    }

    public abstract int[] getOutputSlots();

    public abstract Slot[] addSlots(Player var1);

    public void addComponents(AdvMultiMachineContainer container) {
    }

    public Box2i getProgressBox() {
        return PROGRESS_BOX;
    }

    public Box2i getChargeBox() {
        return CHARGE_BOX;
    }

    public abstract Component getSpeedName();

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new AdvMultiMachineContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "speed", this.speed, this.getMaxSpeed());
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.speed = NBTUtils.getInt(compound, "speed", this.getMaxSpeed());
    }

    @Override
    public boolean shouldBlockUpdateEnableTick() {
        return true;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return TYPES;
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
    public boolean isMachineWorking() {
        return this.isOperating();
    }

    @Override
    protected boolean isOperating() {
        return this.isProcessing;
    }

    @Override
    protected void setOperating(boolean value) {
        if (value != this.isProcessing) {
            this.isProcessing = value;
            this.updateTileField("isProcessing");
            if (!value && !((ItemStack)this.inventory.get(1)).m_41619_() && this.progress > 0.0f) {
                this.playSound(true);
            }
        }
    }

    @Override
    protected boolean canStopTicking(boolean charging) {
        return false;
    }

    @Override
    protected boolean isSpeedMachine() {
        return true;
    }

    @Override
    protected void onPreTick(boolean hasRecipe, boolean canWork, boolean canOperate) {
        if (canWork && this.hasEnergy(1)) {
            if (this.speed < 10000) {
                ++this.speed;
                this.updateGuiField("speed");
            }
            this.useEnergy(1);
            if (this.setActive(true)) {
                this.playSound(false);
            }
        } else {
            if (this.speed > 0) {
                this.speed = Math.max(0, this.speed - 4);
                this.updateGuiField("speed");
            }
            this.setActive(false);
        }
        this.progressPerTick = (float)this.speed / 30.0f;
    }

    @Override
    protected InteractionResult canFillRecipeIntoOutputs(int slot, IRecipeOutput output) {
        List<ItemStack> outputs = output.getAllOutputs();
        for (int outputSlot : this.getOutputSlots()) {
            ItemStack inv = (ItemStack)this.inventory.get(outputSlot);
            if (inv.m_41619_()) {
                return InteractionResult.SUCCESS;
            }
            if (StackUtil.getStackSizeLeft(inv) <= 0) continue;
            for (ItemStack stack : outputs) {
                if (!StackUtil.canFitInto(inv, stack)) continue;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void addOutput(int inputSlot, ItemStack output) {
        this.outputs.add(new MultiStackOutput(output, this.getOutputSlots()));
    }

    @Override
    public void onUpgradesChanged() {
        this.handleUpgrades(true);
    }
}

