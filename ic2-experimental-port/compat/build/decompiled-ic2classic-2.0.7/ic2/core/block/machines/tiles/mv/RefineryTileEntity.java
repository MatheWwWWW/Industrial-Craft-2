/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraftforge.common.ForgeMod
 *  net.minecraftforge.common.Tags$Items
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.registries.RegistryObject
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.items.IUpgradeItem;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.INullableInput;
import ic2.api.recipes.ingridients.inputs.SubItemInput;
import ic2.api.recipes.ingridients.queue.MultiStackOutput;
import ic2.api.recipes.ingridients.recipes.IFluidRecipeOutput;
import ic2.api.recipes.misc.RecipeFlags;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IRefiningRecipeList;
import ic2.api.tiles.IFluidMachine;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.cache.CapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.IClickable;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IXPMachine;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.misc.comparator.types.base.ProgressComparator;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.machines.containers.mv.RefineryContainer;
import ic2.core.block.machines.recipes.misc.AlcoholInput;
import ic2.core.block.machines.recipes.misc.AlcoholOutput;
import ic2.core.block.machines.recipes.misc.WhiskyOutput;
import ic2.core.fluid.InsertionTank;
import ic2.core.fluid.LayeredFluidTank;
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
import ic2.core.platform.registries.IC2Fluids;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.FluidHelper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.RegistryObject;

public class RefineryTileEntity
extends BaseMachineTileEntity
implements ITileGui,
ITickListener,
IFluidHandler,
IFluidMachine,
IXPMachine,
IClickable {
    public static final EnumSet<IUpgradeItem.UpgradeType> BASIC_MACHINE_UPGRADES = EnumSet.allOf(IUpgradeItem.UpgradeType.class);
    @NetworkInfo
    public InsertionTank firstTank = new InsertionTank(16000);
    @NetworkInfo
    public InsertionTank secondTank = new InsertionTank(16000);
    @NetworkInfo
    public LayeredFluidTank output = new LayeredFluidTank(64000);
    @NetworkInfo
    public float progress = 0.0f;
    @NetworkInfo
    public int recipeOperation;
    @NetworkInfo
    public int recipeEnergy;
    protected IRefiningRecipeList.FluidRecipe usedRecipe = null;
    protected IRefiningRecipeList.FluidRecipe lastRecipe = null;
    protected boolean rebuildCache = true;
    List<FluidStack> fluidOutputs = CollectionUtils.createList();
    ICache<IFluidHandler> fluidCache = new CapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER);

    public RefineryTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 5, 4, 20, 250, 5624, 128);
        this.addCaches(this.fluidCache);
        this.setFuelSlot(0);
        this.addGuiFields("recipeOperation", "recipeEnergy", "progress", "firstTank", "secondTank", "output");
        this.firstTank.addListener(T -> this.updateGuiField("firstTank")).addListener(T -> this.revalidateRecipe());
        this.secondTank.addListener(T -> this.updateGuiField("secondTank")).addListener(T -> this.revalidateRecipe());
        this.output.addListener(T -> this.updateGuiField("output"));
        this.output.addListener(T -> this.revalidateRecipe());
        this.addCapability(ForgeCapabilities.FLUID_HANDLER, this);
        this.addComparator(new ProgressComparator("progress", ComparatorNames.PROGRESS, this));
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1);
        handler.registerSlotAccess(AccessRule.EXPORT, 2, 3, 4);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2, 3, 4);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(new MachineFilter(this), 1);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 2, 3, 4);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2, 3, 4);
    }

    protected void revalidateRecipe() {
        this.addToTick();
        this.rebuildCache |= !this.isOperating();
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.progress = compound.m_128457_("progress");
        this.firstTank.readFromNBT(compound.m_128469_("firstTank"));
        this.secondTank.readFromNBT(compound.m_128469_("secondTank"));
        this.output.read(compound.m_128469_("outputTank"));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128350_("progress", this.progress);
        compound.m_128365_("firstTank", (Tag)this.firstTank.writeToNBT(new CompoundTag()));
        compound.m_128365_("secondTank", (Tag)this.secondTank.writeToNBT(new CompoundTag()));
        compound.m_128365_("outputTank", (Tag)this.output.write(new CompoundTag()));
    }

    @Override
    public boolean supportsNotify() {
        return false;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.REFINERY;
    }

    @Override
    public boolean onRightClick(Player player, InteractionHand hand, Direction side, BlockHitResult hit) {
        ItemStack stack = player.m_21120_(hand);
        if (stack.m_41619_()) {
            return false;
        }
        return FluidHelper.fillContainers(stack, player, this) || FluidHelper.drainContainers(stack, player, this);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new RefineryContainer(this, player, windowID);
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        super.onClientDataReceived(entity, key, value);
        if (key == 0) {
            this.firstTank.setFluid(FluidStack.EMPTY);
            this.updateGuiField("firstTank");
        } else if (key == 1) {
            this.secondTank.setFluid(FluidStack.EMPTY);
            this.updateGuiField("secondTank");
        } else if (key == 2) {
            FluidStack stack = this.output.getFluidInTank(0);
            if (stack.isEmpty()) {
                return;
            }
            int filled = this.firstTank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
            if (filled > 0) {
                this.output.drain(new FluidStack(stack, filled), IFluidHandler.FluidAction.EXECUTE);
                this.updateGuiFields("output", "firstTank");
            } else {
                filled = this.secondTank.fill(stack, IFluidHandler.FluidAction.EXECUTE);
                if (filled > 0) {
                    this.output.drain(new FluidStack(stack, filled), IFluidHandler.FluidAction.EXECUTE);
                    this.updateGuiFields("output", "secondTank");
                }
            }
        }
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
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1);
        this.inOut[1] = new RangedInventory(this, 2, 3, 4).setOutputOnly();
    }

    @Override
    public int getCreatedXP(boolean consume) {
        if (this.processedRecipes.isEmpty()) {
            return 0;
        }
        float totalXP = 0.0f;
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)this.processedRecipes)) {
            IRefiningRecipeList.FluidRecipe recipe = this.getRecipeList().getRecipe((ResourceLocation)entry.getKey());
            if (recipe == null) continue;
            totalXP += recipe.getOutput().getExperience() * (float)entry.getIntValue();
        }
        if (consume) {
            this.processedRecipes.clear();
        }
        return (int)totalXP;
    }

    @Override
    protected boolean addItemsToInventory() {
        if (!this.fluidOutputs.isEmpty()) {
            Iterator<FluidStack> iter = this.fluidOutputs.iterator();
            while (iter.hasNext()) {
                FluidStack out = iter.next();
                out.shrink(this.output.fill(out, IFluidHandler.FluidAction.EXECUTE));
                if (out.getAmount() > 0) continue;
                iter.remove();
            }
        }
        return this.fluidOutputs.isEmpty() && super.addItemsToInventory();
    }

    public IRefiningRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).refining;
    }

    @Override
    public void onTick() {
        boolean canOperate;
        this.handleRedstone();
        boolean charging = this.handleChargeSlot((int)((double)this.maxEnergy * 0.9));
        boolean hasRecipe = this.hasRecipe();
        this.rebuildCache = false;
        boolean canWork = this.canProcess() || hasRecipe && this.hasEnergy(1);
        boolean bl = canOperate = this.addItemsToInventory() && canWork && hasRecipe;
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

    public void operate() {
        CompoundTag recipeData = new CompoundTag();
        this.storage.onRecipeFinishedPre((NonNullList<ItemStack>)this.inventory, this, this.usedRecipe.getOutput(), recipeData);
        this.operateOnce(1, this.usedRecipe.getItemInput(), this.usedRecipe.getFirstTank(), this.usedRecipe.getSecondTank(), this.usedRecipe.getOutput(), recipeData);
        this.onRecipeProcessed(this.usedRecipe.getLocation());
        this.storage.onRecipeFinishedPost((NonNullList<ItemStack>)this.inventory, this, null, this.outputs);
        if (this.outputs.size() > 0 || this.fluidOutputs.size() > 0) {
            this.addItemsToInventory();
        }
    }

    public void operateOnce(int slot, IInput input, FluidStack first, FluidStack second, IFluidRecipeOutput output, CompoundTag recipeData) {
        for (ItemStack stack : output.onRecipeProcessed(this.m_58904_().f_46441_, this.getPersistentData(), recipeData)) {
            this.addOutput(slot, stack);
        }
        this.fluidOutputs.addAll(output.onFluidRecipeProcessed(this.m_58904_().f_46441_, this.getPersistentData(), recipeData, ((ItemStack)this.inventory.get(slot)).m_41777_()));
        this.consumeInput(input, first, second, slot, RecipeFlags.CONSUME_CONTAINERS.getFlag(output.getMetadata(), false));
    }

    public void consumeInput(IInput input, FluidStack first, FluidStack second, int slot, boolean emptyContainer) {
        this.firstTank.drainInternally(first.getAmount(), IFluidHandler.FluidAction.EXECUTE);
        this.secondTank.drainInternally(second.getAmount(), IFluidHandler.FluidAction.EXECUTE);
        if (input instanceof INullableInput && ((ItemStack)this.inventory.get(slot)).m_41619_()) {
            return;
        }
        if (!emptyContainer && ((ItemStack)this.inventory.get(slot)).hasCraftingRemainingItem()) {
            this.addOutput(slot, ((ItemStack)this.inventory.get(slot)).getCraftingRemainingItem());
        }
        ((ItemStack)this.inventory.get(slot)).m_41774_(input.getInputSize());
    }

    protected void addOutput(int inputSlot, ItemStack output) {
        this.outputs.add(new MultiStackOutput(output, 2, 3, 4));
    }

    public boolean hasRecipe() {
        this.usedRecipe = this.getRecipe();
        return this.usedRecipe != null;
    }

    public IRefiningRecipeList.FluidRecipe getRecipe() {
        List<ItemStack> outputs;
        if (!this.rebuildCache) {
            return this.usedRecipe;
        }
        IRefiningRecipeList.FluidRecipe last = this.lastRecipe;
        if (last != null) {
            IInput input = last.getItemInput();
            if (((ItemStack)this.inventory.get(1)).m_41619_() || !input.matches((ItemStack)this.inventory.get(1)) || !this.firstTank.getFluid().isFluidEqual(last.getFirstTank()) || !this.secondTank.getFluid().isFluidEqual(last.getSecondTank())) {
                last = null;
            } else if (input.getInputSize() > ((ItemStack)this.inventory.get(1)).m_41613_() || !this.firstTank.getFluid().containsFluid(last.getFirstTank()) || !this.secondTank.getFluid().containsFluid(last.getSecondTank())) {
                return null;
            }
        }
        this.lastRecipe = last;
        if (last == null) {
            IRefiningRecipeList.FluidRecipe recipe = this.getRecipeList().getRecipe((ItemStack)this.inventory.get(1), this.firstTank.getFluid(), this.secondTank.getFluid(), true);
            if (recipe == null) {
                return null;
            }
            last = recipe;
            this.lastRecipe = recipe;
            this.handleMods(1, recipe);
        }
        if ((this.fitIntoOutput(2, outputs = last.getOutput().getAllOutputs()) || this.fitIntoOutput(3, outputs) || this.fitIntoOutput(4, outputs)) && this.fitIntoOutputs(last.getOutput().getAllFluidOutputs())) {
            return last;
        }
        return null;
    }

    public boolean fitIntoOutputs(List<FluidStack> fluids) {
        if (fluids.isEmpty()) {
            return true;
        }
        for (FluidStack fluid : fluids) {
            if (this.output.fill(fluid, IFluidHandler.FluidAction.SIMULATE) < fluid.getAmount()) continue;
            return true;
        }
        return false;
    }

    public boolean fitIntoOutput(int slot, List<ItemStack> items) {
        if (items.isEmpty()) {
            return true;
        }
        ItemStack inv = (ItemStack)this.inventory.get(slot);
        if (inv.m_41619_()) {
            return true;
        }
        if (StackUtil.getStackSizeLeft(inv) <= 0) {
            return false;
        }
        for (ItemStack stack : items) {
            if (!StackUtil.canFitInto(inv, stack)) continue;
            return true;
        }
        return false;
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

    @Override
    protected void handleMods() {
        this.handleMods(1, this.usedRecipe);
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        if (this.getRecipeList().getRecipe(stack, this.firstTank.getFluid(), this.secondTank.getFluid(), false) == null) {
            return 0;
        }
        ItemStack ref = (ItemStack)this.inventory.get(1);
        if (ref.m_41619_()) {
            return stack.m_41741_();
        }
        return StackUtil.isStackEqual(ref, stack) ? StackUtil.getStackSizeLeft(ref) : 0;
    }

    @Override
    public EnumSet<IUpgradeItem.UpgradeType> getSupportedUpgradeTypes() {
        return BASIC_MACHINE_UPGRADES;
    }

    @Override
    public IFluidHandler getConnectedTank(Direction dir) {
        return this.fluidCache.getHandler(dir);
    }

    public int getTanks() {
        return 2 + this.output.getTanks();
    }

    public FluidStack getFluidInTank(int tank) {
        switch (tank) {
            case 0: {
                return this.firstTank.getFluid();
            }
            case 1: {
                return this.secondTank.getFluid();
            }
        }
        return this.output.getFluidInTank(tank - 2);
    }

    public int getTankCapacity(int tank) {
        switch (tank) {
            case 0: {
                return this.firstTank.getTankCapacity(0);
            }
            case 1: {
                return this.secondTank.getTankCapacity(0);
            }
        }
        return this.output.getTankCapacity(tank - 2);
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return tank == 0 || tank == 1;
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        boolean first = this.firstTank.getFluid().isFluidEqual(resource);
        boolean second = this.secondTank.getFluid().isFluidEqual(resource);
        if (first && !second) {
            return this.firstTank.fill(resource, action);
        }
        if (!first && second) {
            return this.secondTank.fill(resource, action);
        }
        if (!first) {
            int fill = this.firstTank.fill(resource, action);
            if (fill != 0) {
                return fill;
            }
            return this.secondTank.fill(resource, action);
        }
        return 0;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return this.output.drain(resource, action);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return this.output.drain(maxDrain, action);
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

    protected void handleMods(int slot, IRefiningRecipeList.FluidRecipe entry) {
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

    public static void loadRecipes(IRefiningRecipeList recipes) {
        RefineryTileEntity.createOreBuilder(recipes, "iron_to_raw", (TagKey<Item>)Tags.Items.ORES_IRON, Items.f_151050_, 2);
        RefineryTileEntity.createOreBuilder(recipes, "gold_to_raw", (TagKey<Item>)Tags.Items.ORES_GOLD, Items.f_151053_, 1);
        RefineryTileEntity.createOreBuilder(recipes, "copper_to_raw", IC2Tags.ORE_COPPER, Items.f_151051_, 3);
        RefineryTileEntity.createOreBuilder(recipes, "tin_to_raw", IC2Tags.ORE_TIN, IC2Items.RAW_TIN, 3);
        RefineryTileEntity.createOreBuilder(recipes, "silver_to_raw", IC2Tags.ORE_SILVER, IC2Items.RAW_SILVER, 1);
        RefineryTileEntity.createOreBuilder(recipes, "aluminum_to_raw", IC2Tags.ORE_ALUMINIUM, IC2Items.RAW_ALUMINIUM, 15);
        recipes.addIC2Recipe("polished_uranium").mainInput(IC2Fluids.ALCOHOL, 100).catalyst(IC2Tags.ORE_URANIUM).buildSimple(new ItemStack((ItemLike)IC2Items.ORE_URANIUM_DROP, 5));
        recipes.addIC2Recipe("enriched_lava").mainInput((Fluid)Fluids.f_76195_, 16000).catalyst(new ItemStack((ItemLike)Items.f_42593_, 5)).addMod(RecipeMods.ENERGY_USAGE, 8.0).addMod(RecipeMods.RECIPE_TIME, 2.0).buildSimple(new FluidStack(IC2Fluids.BLAZING_LAVA, 16000));
        recipes.addIC2Recipe("polished_gold").mainInput(IC2Fluids.ALCOHOL, 500).catalyst((ItemLike)Items.f_42417_).buildSimple(new ItemStack((ItemLike)IC2Items.INGOT_POLISHED_GOLD));
        recipes.addIC2Recipe("bio_gas_fuel").mainInput(IC2Fluids.WOOD_GAS, 1000).secondInput((Fluid)Fluids.f_76193_, 1000).addMod(RecipeMods.RECIPE_TIME, 2.0).buildSimple(new FluidStack(IC2Fluids.BIO_FUEL, 100));
        recipes.addIC2Recipe("bio_fuel").catalyst(new ItemStack((ItemLike)IC2Items.PLANT_BALL_COMPRESSED, 16)).mainInput((Fluid)Fluids.f_76193_, 2000).buildSimple(new FluidStack(IC2Fluids.BIO_FUEL, 250));
        recipes.addIC2Recipe("alcohol_mug").mainInput((Fluid)Fluids.f_76193_, 1000).catalyst(new AlcoholInput(IC2Items.MUG_BEER)).buildCustom(T -> new AlcoholOutput(IC2Items.MUG));
        recipes.addIC2Recipe("alcohol_glass").mainInput((Fluid)Fluids.f_76193_, 1000).catalyst(new AlcoholInput(IC2Items.GLASS_BEER)).buildCustom(T -> new AlcoholOutput(IC2Items.GLASS));
        recipes.addIC2Recipe("whisky_mug").mainInput((Fluid)Fluids.f_76193_, 4000).catalyst(new SubItemInput(IC2Items.MUG_WHISKY)).buildCustom(T -> new WhiskyOutput(IC2Items.MUG));
        recipes.addIC2Recipe("whisky_glass").mainInput((Fluid)Fluids.f_76193_, 4000).catalyst(new SubItemInput(IC2Items.GLASS_WHISKY)).buildCustom(T -> new WhiskyOutput(IC2Items.GLASS));
        recipes.addIC2Recipe("unrefined_whisky").mainInput((Fluid)Fluids.f_76193_, 1000).catalyst(new ItemStack((ItemLike)IC2Items.FLOUR, 4)).addMod(RecipeMods.RECIPE_TIME, 0.5).buildSimple(new FluidStack(IC2Fluids.WHISKY, 250));
        recipes.addIC2Recipe("refined_whisky").mainInput(IC2Fluids.WHISKY, 1000).buildSimple(new ItemStack((ItemLike)IC2Items.WHISKY_GRIST));
        recipes.addIC2Recipe("coal_dust_washing").addMod(RecipeMods.RECIPE_TIME, 0.1).mainInput((Fluid)Fluids.f_76193_, 100).catalyst((ItemLike)IC2Items.DUST_COAL).buildSimple(new ItemStack((ItemLike)IC2Items.DUST_HYDRATED_COAL));
        recipes.addIC2Recipe("charcoal_dust_washing").addMod(RecipeMods.RECIPE_TIME, 0.1).mainInput((Fluid)Fluids.f_76193_, 100).catalyst((ItemLike)IC2Items.DUST_CHARCOAL).buildSimple(new ItemStack((ItemLike)IC2Items.DUST_HYDRATED_CHARCOAL));
        recipes.addIC2Recipe("mud_to_clay").catalyst((ItemLike)Items.f_220216_).buildSimple((List<ItemStack>)ObjectArrayList.of((Object[])new ItemStack[]{new ItemStack((ItemLike)Items.f_42329_), new ItemStack((ItemLike)Items.f_42461_)}), (List<FluidStack>)ObjectLists.singleton((Object)new FluidStack((Fluid)Fluids.f_76193_, 500)));
        recipes.addIC2Recipe("chocolate").catalyst(new ItemStack((ItemLike)Items.f_42533_, 8)).mainInput((RegistryObject<Fluid>)ForgeMod.MILK, 250).buildSimple(new ItemStack((ItemLike)IC2Items.CHOCOLATE));
    }

    private static void createOreBuilder(IRefiningRecipeList recipe, String id, TagKey<Item> ore, Item output, int base) {
        recipe.addIC2Recipe(id).addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput((Fluid)Fluids.f_76195_, 25 * base).buildRange(new ItemStack((ItemLike)output), base, base * 3);
        recipe.addIC2Recipe(id + "_advanced").addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput(IC2Fluids.BIO_FUEL, 50 * base).buildRange(new ItemStack((ItemLike)output), base * 3, base * 5);
        recipe.addIC2Recipe(id + "_alk").addMod(RecipeMods.RECIPE_TIME, 0.5).catalyst(ore).mainInput(IC2Fluids.ALCOHOL, 25 * base).buildRange(new ItemStack((ItemLike)output), base * 5, base * 7);
    }
}

