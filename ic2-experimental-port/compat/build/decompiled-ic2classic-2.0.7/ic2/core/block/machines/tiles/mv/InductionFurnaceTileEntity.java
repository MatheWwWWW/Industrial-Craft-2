/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.ingridients.queue.MultiStackOutput;
import ic2.api.recipes.ingridients.queue.SimpleStackOutput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BaseAdvMachineTileEntity;
import ic2.core.block.machines.components.mv.InductionFurnaceComponent;
import ic2.core.block.machines.containers.mv.AdvMachineContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.inventory.slot.XPSlot;
import ic2.core.platform.registries.IC2Sounds;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class InductionFurnaceTileEntity
extends BaseAdvMachineTileEntity {
    static final CompoundTag EMPTY = new CompoundTag();
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/mv/gui_induction.png");
    public static final Component HEAT = Component.m_237115_((String)"info.block.ic2.induction_furnace.heat");
    @NetworkInfo
    public boolean split = true;
    IMachineRecipeList.RecipeEntry otherLastRecipe = null;
    IMachineRecipeList.RecipeEntry otherUsedRecipe = null;

    public InductionFurnaceTileEntity(BlockPos pos, BlockState state) {
        this(pos, state, 15, 4000);
    }

    public InductionFurnaceTileEntity(BlockPos pos, BlockState state, int cost, int time) {
        super(pos, state, 5, cost, time);
        this.addGuiFields("split");
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.IMPORT, 1, 2);
        handler.registerSlotAccess(AccessRule.EXPORT, this.getOutputSlots());
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1, 2);
        handler.registerSlotsForSide(DirectionList.UP.invert(), this.getOutputSlots());
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(this::canInsertSlot, 1, 2);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, this.getOutputSlots());
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1, 2);
        handler.registerNamedSlot(SlotType.OUTPUT, this.getOutputSlots());
    }

    @Override
    protected ResourceLocation getWorkingSound() {
        return IC2Sounds.ELECTRIC_FURNACE_PROCESSING;
    }

    @Override
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1, 2);
        this.inOut[1] = new RangedInventory(this, this.getOutputSlots()).setOutputOnly();
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
        if (StackUtil.isStackEqual(ref, stack)) {
            return StackUtil.getStackSizeLeft(ref);
        }
        ref = (ItemStack)this.inventory.get(2);
        if (ref.m_41619_()) {
            return stack.m_41741_();
        }
        return StackUtil.isStackEqual(ref, stack) ? StackUtil.getStackSizeLeft(ref) : 0;
    }

    @Override
    protected boolean consumeContainers() {
        return true;
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).furnace;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.INDUCTION_FURNACE;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128379_("split", this.split);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.split = compound.m_128471_("split");
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{3, 4};
    }

    @Override
    protected IMachineRecipeList.RecipeEntry getCachedRecipe(int slot) {
        return slot == 1 ? this.usedRecipe : this.otherUsedRecipe;
    }

    @Override
    protected IMachineRecipeList.RecipeEntry getLastRecipe(int slot) {
        return slot == 1 ? this.lastRecipe : this.otherLastRecipe;
    }

    @Override
    protected void setLastRecipe(int slot, IMachineRecipeList.RecipeEntry entry) {
        if (slot == 1) {
            this.lastRecipe = entry;
        } else {
            this.otherLastRecipe = entry;
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public boolean hasRecipe() {
        this.usedRecipe = this.getRecipe(1);
        this.otherUsedRecipe = this.getRecipe(2);
        return this.usedRecipe != null || this.otherUsedRecipe != null;
    }

    @Override
    public void operate() {
        if (!this.split) {
            this.processSlot(!((ItemStack)this.inventory.get(1)).m_41619_() ? 1 : 2);
            this.rebuildCache = true;
            this.processSlot(!((ItemStack)this.inventory.get(2)).m_41619_() ? 2 : 1);
            return;
        }
        this.processSlot(1);
        this.processSlot(2);
    }

    protected void processSlot(int slot) {
        IMachineRecipeList.RecipeEntry entry = this.getRecipe(slot);
        if (entry == null) {
            return;
        }
        CompoundTag recipeData = new CompoundTag();
        this.storage.onRecipeFinishedPre((NonNullList<ItemStack>)this.inventory, this, entry.getOutput(), recipeData);
        this.operateOnce(slot, entry.getInputs(), entry.getOutput(), recipeData);
        this.onRecipeProcessed(entry.getLocation());
        this.storage.onRecipeFinishedPost((NonNullList<ItemStack>)this.inventory, this, entry, this.outputs);
        if (this.outputs.size() > 0) {
            this.addItemsToInventory();
        }
    }

    @Override
    protected void addOutput(int inputSlot, ItemStack output) {
        this.outputs.add(this.split ? new SimpleStackOutput(output, 2 + inputSlot) : new MultiStackOutput(output, this.getOutputSlots()));
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack stack) {
        return this.getRecipeList().getRecipe(stack, true);
    }

    @Override
    protected InteractionResult canFillRecipeIntoOutputs(int slot, IRecipeOutput output) {
        if (this.split) {
            ItemStack invStack = this.getStackInSlot(2 + slot);
            if (invStack.m_41619_()) {
                return InteractionResult.SUCCESS;
            }
            for (ItemStack stack : output.getAllOutputs()) {
                if (!StackUtil.canFitInto(stack, invStack)) continue;
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }
        return super.canFillRecipeIntoOutputs(slot, output);
    }

    @Override
    public Slot[] addSlots(Player player) {
        Slot[] slots = new Slot[]{FilterSlot.createDischargeSlot(this, this.tier, 0, 56, 53), new FilterSlot(this, 1, 47, 17, T -> this.getRecipeList().getRecipe(T, false) != null), new FilterSlot(this, 2, 63, 17, T -> this.getRecipeList().getRecipe(T, false) != null), new XPSlot(this, 3, 113, 35), new XPSlot(this, 4, 131, 35)};
        return slots;
    }

    @Override
    public void addComponents(AdvMachineContainer container) {
        container.addComponent(new InductionFurnaceComponent(this));
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 1) {
            this.split = value == 1;
            this.updateGuiField("split");
        }
        super.onClientDataReceived(entity, key, value);
    }

    @Override
    public Component getSpeedName() {
        return HEAT;
    }

    @Override
    public void handleMods(int slot, IMachineRecipeList.RecipeEntry entry) {
        this.updateGuiFields("recipeEnergy", "recipeOperation");
        IMachineRecipeList.RecipeEntry main = this.getRecipe(1);
        IMachineRecipeList.RecipeEntry sub = this.getRecipe(2);
        if (main == null && sub == null) {
            this.recipeEnergy = Math.max(1, this.energyConsume);
            this.recipeOperation = Math.max(1, this.operationLength);
            return;
        }
        CompoundTag mainNBT = main == null ? EMPTY : main.getOutput().getMetadata();
        CompoundTag subNBT = sub == null ? EMPTY : sub.getOutput().getMetadata();
        this.recipeEnergy = Math.max(RecipeMods.ENERGY_USAGE.apply(mainNBT, this.energyConsume), RecipeMods.ENERGY_USAGE.apply(subNBT, this.energyConsume));
        this.recipeOperation = Math.max(RecipeMods.RECIPE_TIME.apply(mainNBT, this.operationLength), RecipeMods.RECIPE_TIME.apply(subNBT, this.operationLength));
    }
}

