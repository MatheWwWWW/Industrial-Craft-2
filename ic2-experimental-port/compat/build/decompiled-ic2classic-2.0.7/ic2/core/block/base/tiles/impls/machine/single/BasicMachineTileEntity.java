/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls.machine.single;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.INullableInput;
import ic2.api.recipes.ingridients.queue.SimpleStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.misc.RecipeFlags;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.tiles.IRecipeMachine;
import ic2.api.tiles.readers.IProgressMachine;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IXPMachine;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.machines.containers.lv.BasicMachineContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.filter.special.MachineFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BasicMachineTileEntity
extends BaseMachineTileEntity
implements ITickListener,
IRecipeMachine,
IProgressMachine,
ITileGui,
IXPMachine {
    public static final EnumSet<IUpgradeItem.UpgradeType> BASIC_MACHINE_UPGRADES = EnumSet.allOf(IUpgradeItem.UpgradeType.class);
    @NetworkInfo
    public float progress = 0.0f;
    @NetworkInfo
    public int recipeOperation;
    @NetworkInfo
    public int recipeEnergy;
    protected IMachineRecipeList.RecipeEntry usedRecipe = null;
    protected IMachineRecipeList.RecipeEntry lastRecipe = null;
    protected boolean rebuildCache = true;

    public BasicMachineTileEntity(BlockPos pos, BlockState state, int slots, int energyPerTick, int maxProgress, int maxInput) {
        this(pos, state, slots, 4, energyPerTick, maxProgress, energyPerTick * maxProgress, maxInput);
    }

    public BasicMachineTileEntity(BlockPos pos, BlockState state, int slots, int upgradeSlots, int energyPerTick, int maxProgress, int maxInput) {
        this(pos, state, slots, upgradeSlots, energyPerTick, maxProgress, energyPerTick * maxProgress, maxInput);
    }

    public BasicMachineTileEntity(BlockPos pos, BlockState state, int size, int upgradeSlots, int energyPerTick, int maxProgress, int maxEnergy, int maxInput) {
        super(pos, state, size, upgradeSlots, energyPerTick, maxProgress, maxEnergy, maxInput);
        this.sensitive = false;
        this.recipeOperation = maxProgress;
        this.recipeEnergy = energyPerTick;
        this.setFuelSlot(0);
        this.addGuiFields("recipeOperation", "recipeEnergy", "progress");
        this.addNetworkFields("soundLevel");
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    public abstract ResourceLocation getTexture();

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new MachineFilter(this), 1);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 2);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.progress = compound.m_128457_("progress");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putFloat(compound, "progress", this.progress, 0.0f);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new BasicMachineContainer(this, player, windowID);
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return BASIC_MACHINE_UPGRADES;
    }

    @Override
    public float getProgress() {
        return this.progress;
    }

    @Override
    public float getMaxProgress() {
        return this.recipeOperation;
    }

    @Override
    public int getEnergyPerTick() {
        return this.recipeEnergy;
    }

    @Override
    public int getCreatedXP(boolean consume) {
        if (this.processedRecipes.isEmpty()) {
            return 0;
        }
        float totalXP = 0.0f;
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)this.processedRecipes)) {
            IMachineRecipeList.RecipeEntry recipe = this.getRecipeList().getRecipe((ResourceLocation)entry.getKey());
            if (recipe == null) continue;
            totalXP += recipe.getOutput().getExperience() * (float)entry.getIntValue();
        }
        if (consume) {
            this.processedRecipes.clear();
        }
        return (int)totalXP;
    }

    @Override
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1);
        this.inOut[1] = new RangedInventory(this, 2).setOutputOnly();
    }

    protected boolean isSpeedMachine() {
        return false;
    }

    @Override
    public void onTick() {
        boolean canWork;
        this.handleRedstone();
        boolean charging = this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        boolean hasRecipe = this.hasRecipe();
        this.rebuildCache = false;
        boolean bl = canWork = this.canProcess() && this.hasEnergy(1);
        if (this.isSpeedMachine()) {
            canWork |= hasRecipe && this.hasEnergy(1);
        }
        boolean canOperate = this.addItemsToInventory() && canWork && hasRecipe;
        this.onPreTick(hasRecipe, canWork, canOperate);
        if (canOperate && this.hasEnergy(this.recipeEnergy)) {
            this.setOperating(true);
            this.useEnergy(this.recipeEnergy);
            this.progress += this.progressPerTick;
            if (this.progress >= (float)this.recipeOperation) {
                this.operate();
                this.progress = 0.0f;
                this.notifyListeners();
                this.rebuildCache = true;
            }
            this.updateGuiField("progress");
        } else {
            this.setOperating(false);
            if (this.progress > 0.0f) {
                this.progress = Math.max(0.0f, hasRecipe ? this.progress - 1.0f : 0.0f);
                this.updateGuiField("progress");
            }
            if (this.canStopTicking(charging)) {
                this.removeFromTick();
            }
        }
        this.storage.onTick((NonNullList<ItemStack>)this.inventory, this);
        this.handleComparators();
    }

    @Override
    public void onNotify(IHasInventory inventory, int slot) {
        if (this.isSimulating()) {
            this.addToTick();
            this.rebuildCache = true;
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (this.isSimulating()) {
            this.addToTick();
            this.rebuildCache = true;
            this.onSlotChanged(slot, (ItemStack)this.inventory.get(slot), stack);
        }
        super.setStackInSlot(slot, stack);
    }

    protected void onPreTick(boolean hasRecipe, boolean canWork, boolean canOperate) {
    }

    public boolean hasRecipe() {
        this.usedRecipe = this.getRecipe(1);
        return this.usedRecipe != null;
    }

    protected IMachineRecipeList.RecipeEntry getRecipe(int slot) {
        IMachineRecipeList.RecipeEntry usedRecipe;
        if (!(this.rebuildCache || (usedRecipe = this.getCachedRecipe(slot)) != null && usedRecipe.hasNullInput())) {
            return usedRecipe;
        }
        if (((ItemStack)this.inventory.get(slot)).m_41619_() && !this.canWorkWithoutItems()) {
            this.setLastRecipe(slot, null);
            return null;
        }
        IMachineRecipeList.RecipeEntry lastRecipe = this.getLastRecipe(slot);
        if (lastRecipe != null) {
            IInput recipe = lastRecipe.getInputs()[0];
            boolean nullable = recipe instanceof INullableInput;
            if (nullable && !recipe.matches((ItemStack)this.inventory.get(slot))) {
                lastRecipe = null;
            } else if (!nullable) {
                switch (this.isRecipeStillValid(slot, lastRecipe)) {
                    case FAIL: {
                        lastRecipe = null;
                        break;
                    }
                    case PASS: {
                        return null;
                    }
                    case IGNORE: {
                        if (((ItemStack)this.inventory.get(slot)).m_41619_() || !recipe.matches((ItemStack)this.inventory.get(slot))) {
                            lastRecipe = null;
                            break;
                        }
                        if (recipe.getInputSize() <= ((ItemStack)this.inventory.get(slot)).m_41613_()) break;
                        return null;
                    }
                }
            }
        }
        this.setLastRecipe(slot, lastRecipe);
        if (lastRecipe == null) {
            IMachineRecipeList.RecipeEntry out = this.getRecipe(slot, ((ItemStack)this.inventory.get(slot)).m_41777_());
            if (out == null) {
                return null;
            }
            lastRecipe = out;
            this.setLastRecipe(slot, out);
            this.handleMods(slot, out);
        }
        switch (this.canFillRecipeIntoOutputs(slot, lastRecipe.getOutput())) {
            case SUCCESS: {
                return lastRecipe;
            }
            case PASS: {
                return null;
            }
        }
        ItemStack invStack = (ItemStack)this.inventory.get(2);
        if (invStack.m_41619_()) {
            return lastRecipe;
        }
        if (invStack.m_41613_() >= invStack.m_41741_()) {
            return null;
        }
        for (ItemStack output : lastRecipe.getOutput().getAllOutputs()) {
            if (!StackUtil.canFitInto(invStack, output)) continue;
            return lastRecipe;
        }
        return null;
    }

    protected IMachineRecipeList.RecipeEntry getCachedRecipe(int slot) {
        return this.usedRecipe;
    }

    protected IMachineRecipeList.RecipeEntry getLastRecipe(int slot) {
        return this.lastRecipe;
    }

    protected void setLastRecipe(int slot, IMachineRecipeList.RecipeEntry entry) {
        this.lastRecipe = entry;
    }

    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack stack) {
        return this.getRecipeList().getRecipe(stack, true);
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        if (this.getRecipeList().getRecipe(stack, false) == null) {
            return 0;
        }
        ItemStack ref = (ItemStack)this.inventory.get(1);
        if (ref.m_41619_()) {
            return stack.m_41741_();
        }
        return StackUtil.isStackEqual(ref, stack) ? StackUtil.getStackSizeLeft(ref) : 0;
    }

    public boolean canInsertSlot(int slot, ItemStack stack) {
        if (this.getRecipeList().getRecipe(stack, false) == null) {
            return false;
        }
        ItemStack invStack = this.getStackInSlot(slot);
        return invStack.m_41619_() || StackUtil.isStackEqual(invStack, stack);
    }

    public void operate() {
        CompoundTag recipeData = new CompoundTag();
        this.storage.onRecipeFinishedPre((NonNullList<ItemStack>)this.inventory, this, this.usedRecipe.getOutput(), recipeData);
        this.operateOnce(1, this.usedRecipe.getInputs(), this.usedRecipe.getOutput(), recipeData);
        this.onRecipeProcessed(this.usedRecipe.getLocation());
        this.storage.onRecipeFinishedPost((NonNullList<ItemStack>)this.inventory, this, this.usedRecipe, this.outputs);
        if (this.outputs.size() > 0) {
            this.addItemsToInventory();
        }
    }

    public void operateOnce(int slot, IInput[] input, IRecipeOutput output, CompoundTag recipeData) {
        for (ItemStack stack : this instanceof IRecipeOutput.IRecipeOverride ? output.onRecipeProcessed(this.m_58904_().f_46441_, this.getPersistentData(), recipeData, (IRecipeOutput.IRecipeOverride)((Object)this)) : output.onRecipeProcessed(this.m_58904_().f_46441_, this.getPersistentData(), recipeData)) {
            this.addOutput(slot, stack);
        }
        this.consumeInput(input, slot, RecipeFlags.CONSUME_CONTAINERS.getFlag(output.getMetadata(), false) || this.consumeContainers());
    }

    public void consumeInput(IInput[] inputs, int slot, boolean emptyContainer) {
        IInput input = inputs[0];
        if (input instanceof INullableInput && ((ItemStack)this.inventory.get(slot)).m_41619_()) {
            return;
        }
        if (!emptyContainer && ((ItemStack)this.inventory.get(slot)).hasCraftingRemainingItem()) {
            this.addOutput(slot, ((ItemStack)this.inventory.get(slot)).getCraftingRemainingItem());
        }
        ((ItemStack)this.inventory.get(slot)).m_41774_(input.getInputSize());
    }

    protected void addOutput(int inputSlot, ItemStack output) {
        this.outputs.add(new SimpleStackOutput(output, 2));
    }

    protected void onSlotChanged(int slot, ItemStack oldStack, ItemStack newStack) {
        if (slot == 1 && !StackUtil.isStackEqual(oldStack, newStack)) {
            if (this.progress > 0.0f) {
                this.playSound(true);
            }
            this.progress = 0.0f;
            this.updateGuiField("progress");
        }
    }

    protected boolean canWorkWithoutItems() {
        return false;
    }

    protected boolean consumeContainers() {
        return false;
    }

    protected BaseMachineTileEntity.RecipeResult isRecipeStillValid(int slot, IMachineRecipeList.RecipeEntry entry) {
        return BaseMachineTileEntity.RecipeResult.IGNORE;
    }

    protected InteractionResult canFillRecipeIntoOutputs(int slot, IRecipeOutput output) {
        return InteractionResult.FAIL;
    }

    @Override
    protected void handleMods() {
        this.handleMods(1, this.usedRecipe);
    }

    protected void handleMods(int slot, IMachineRecipeList.RecipeEntry entry) {
        this.updateGuiFields("recipeEnergy", "recipeOperation");
        if (entry == null) {
            this.recipeEnergy = Math.max(1, this.energyConsume);
            this.recipeOperation = Math.max(1, this.operationLength);
            return;
        }
        CompoundTag nbt = entry.getOutput().getMetadata();
        this.recipeEnergy = RecipeMods.ENERGY_USAGE.apply(nbt, this.energyConsume);
        this.recipeOperation = RecipeMods.RECIPE_TIME.apply(nbt, this.operationLength);
    }

    protected boolean canStopTicking(boolean charging) {
        return !charging && this.progress <= 0.0f && !this.storage.has(IUpgradeItem.Functions.TICK);
    }

    protected void setOperating(boolean value) {
        if (this.setActive(value) && !value && !((ItemStack)this.inventory.get(1)).m_41619_() && this.progress > 0.0f) {
            this.playSound(true);
        }
    }

    protected boolean isOperating() {
        return this.isActive();
    }
}

