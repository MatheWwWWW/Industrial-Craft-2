/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.Tags$Items
 */
package ic2.core.block.machines.tiles.lv;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.ingridients.inputs.ItemTagInput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.machine.single.BaseMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import ic2.core.block.machines.containers.lv.MixingFurnaceContainer;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class AlloySmelterTileEntity
extends BasicMachineTileEntity {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_alloy_smelter.png");

    public AlloySmelterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4, 16, 250, 32);
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.IMPORT, 1, 3);
        handler.registerSlotAccess(AccessRule.BOTH, 0);
        handler.registerSlotAccess(AccessRule.EXPORT, 2);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 1, 3);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 2);
        handler.registerInputFilter(SpecialFilters.createChargeFilter(), 0);
        handler.registerOutputFilter(ElectricItemFilter.NOT_DISCHARGE_FILTER, 0);
        handler.registerInputFilter(T -> this.matchesInput(T, true, false), 1);
        handler.registerInputFilter(T -> this.matchesInput(T, false, false), 3);
        handler.registerInputFilter(SpecialFilters.ALWAYS_FALSE, 2);
        handler.registerNamedSlot(SlotType.BATTERY, 0);
        handler.registerNamedSlot(SlotType.INPUT, 1);
        handler.registerNamedSlot(SlotType.OUTPUT, 2);
        handler.registerNamedSlot(SlotType.EXTRA_INPUT, 3);
    }

    @Override
    protected void createInvCaches() {
        this.inOut = new IHasInventory[2];
        this.inOut[0] = new RangedInventory(this, 1, 3);
        this.inOut[1] = new RangedInventory(this, 2).setOutputOnly();
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return IC2.RECIPES.get((boolean)this.isSimulating()).mixingFurnace;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ALLOY_SMELTER;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MixingFurnaceContainer(this, player, windowID);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack input) {
        return this.getRecipeList().getRecipe(this::matchesRecipe);
    }

    protected boolean matchesRecipe(IMachineRecipeList.RecipeEntry entry) {
        IInput[] input = entry.getInputs();
        return input.length >= 2 && (this.matches(input[0], 1) && this.matches(input[1], 3) || this.matches(input[0], 3) && this.matches(input[1], 1));
    }

    protected boolean matches(IInput input, int slot) {
        ItemStack stack = (ItemStack)this.inventory.get(slot);
        return input.matches(stack) && stack.m_41613_() >= input.getInputSize();
    }

    @Override
    protected BaseMachineTileEntity.RecipeResult isRecipeStillValid(int slot, IMachineRecipeList.RecipeEntry entry) {
        return this.matchesRecipe(entry) ? BaseMachineTileEntity.RecipeResult.SUCCESS : BaseMachineTileEntity.RecipeResult.PASS;
    }

    @Override
    public void consumeInput(IInput[] inputs, int slot, boolean consumeContainers) {
        if (this.matches(inputs[0], 1)) {
            this.consume(inputs[0], consumeContainers, 1);
            this.consume(inputs[1], consumeContainers, 3);
        } else {
            this.consume(inputs[0], consumeContainers, 3);
            this.consume(inputs[1], consumeContainers, 1);
        }
    }

    protected void consume(IInput input, boolean consume, int slot) {
        if (consume && ((ItemStack)this.inventory.get(slot)).hasCraftingRemainingItem()) {
            this.addOutput(slot, ((ItemStack)this.inventory.get(slot)).getCraftingRemainingItem());
        }
        ((ItemStack)this.inventory.get(slot)).m_41774_(input.getInputSize());
    }

    @Override
    public int getValidRoom(ItemStack stack) {
        int room;
        if (StackUtil.isStackEqual((ItemStack)this.inventory.get(1), stack) && (room = StackUtil.getStackSizeLeft((ItemStack)this.inventory.get(1))) > 0) {
            return room;
        }
        if (StackUtil.isStackEqual((ItemStack)this.inventory.get(3), stack) && (room = StackUtil.getStackSizeLeft((ItemStack)this.inventory.get(3))) > 0) {
            return room;
        }
        if (((ItemStack)this.inventory.get(1)).m_41619_() && ((ItemStack)this.inventory.get(3)).m_41619_()) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, 0)) != null ? stack.m_41613_() : 0;
        }
        if (((ItemStack)this.inventory.get(1)).m_41619_()) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, 1)) != null ? stack.m_41613_() : 0;
        }
        return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, 2)) != null ? stack.m_41613_() : 0;
    }

    public boolean matchesInput(ItemStack stack, boolean first, boolean hand) {
        int mainSlot;
        if (hand) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, 0)) != null;
        }
        int n = mainSlot = first ? 1 : 3;
        if (((ItemStack)this.inventory.get(mainSlot)).m_41619_()) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, ((ItemStack)this.inventory.get(first ? 3 : 1)).m_41619_() ? 0 : (first ? 1 : 2))) != null;
        }
        return StackUtil.isStackEqual((ItemStack)this.inventory.get(mainSlot), stack);
    }

    protected boolean matchesRecipe(IMachineRecipeList.RecipeEntry entry, ItemStack stack, int state) {
        IInput[] array = entry.getInputs();
        if (array.length < 2) {
            return false;
        }
        switch (state) {
            case 0: {
                return array[0].matches(stack) || array[1].matches(stack);
            }
            case 1: {
                return array[0].matches((ItemStack)this.inventory.get(3)) ? array[1].matches(stack) : array[1].matches((ItemStack)this.inventory.get(3)) && array[0].matches(stack);
            }
            case 2: {
                return array[0].matches((ItemStack)this.inventory.get(1)) ? array[1].matches(stack) : array[1].matches((ItemStack)this.inventory.get(1)) && array[0].matches(stack);
            }
        }
        return false;
    }

    protected boolean matchesRecipe(IMachineRecipeList.RecipeEntry entry, int state) {
        IInput[] array = entry.getInputs();
        if (array.length < 2) {
            return false;
        }
        switch (state) {
            case 0: {
                return array[0].matches((ItemStack)this.inventory.get(1)) || array[0].matches((ItemStack)this.inventory.get(3)) || array[1].matches((ItemStack)this.inventory.get(1)) || array[1].matches((ItemStack)this.inventory.get(3));
            }
            case 1: {
                return array[0].matches((ItemStack)this.inventory.get(3)) ? array[1].matches((ItemStack)this.inventory.get(1)) : array[1].matches((ItemStack)this.inventory.get(3)) && array[0].matches((ItemStack)this.inventory.get(1));
            }
            case 2: {
                return array[0].matches((ItemStack)this.inventory.get(1)) ? array[1].matches((ItemStack)this.inventory.get(3)) : array[1].matches((ItemStack)this.inventory.get(1)) && array[0].matches((ItemStack)this.inventory.get(3));
            }
        }
        return false;
    }

    public static void loadRecipes(IMachineRecipeList recipes) {
        recipes.addIC2SimpleRecipe("aluminium", new ItemStack((ItemLike)IC2Items.INGOT_ALUMINIUM), IC2Tags.DUST_ALUMINIUM, ItemTags.f_13137_);
        recipes.addIC2SimpleRecipe("aluminium_block", new ItemStack((ItemLike)IC2Blocks.ALUMINIUM_BLOCK), IC2Blocks.DUST_ALUMINIUM_BLOCK, new ItemTagInput((TagKey<Item>)ItemTags.f_13137_, 9));
        recipes.addIC2SimpleRecipe("bronze_ore", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 8), new ItemTagInput(IC2Tags.ORE_COPPER, 3), IC2Tags.ORE_TIN);
        recipes.addIC2SimpleRecipe("bronze_ore_raw_copper", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 8), new ItemInput(Items.f_151051_, 3), IC2Tags.ORE_TIN);
        recipes.addIC2SimpleRecipe("bronze_ore_raw_tin", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 8), new ItemTagInput(IC2Tags.ORE_COPPER, 3), IC2Items.RAW_TIN);
        recipes.addIC2SimpleRecipe("bronze_raw", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 8), new ItemInput(Items.f_151051_, 3), IC2Items.RAW_TIN);
        recipes.addIC2SimpleRecipe("bronze_dust", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 4), new ItemTagInput(IC2Tags.DUST_COPPER, 3), IC2Tags.DUST_TIN);
        recipes.addIC2SimpleRecipe("bronze_ingot", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 4), new ItemTagInput(IC2Tags.INGOT_COPPER, 3), IC2Tags.INGOT_TIN);
        recipes.addIC2SimpleRecipe("bronze_block", new ItemStack((ItemLike)IC2Blocks.BRONZE_BLOCK, 4), new ItemTagInput(IC2Tags.STORAGE_COPPER, 3), IC2Tags.STORAGE_TIN);
        recipes.addIC2SimpleRecipe("bronze_block_2", new ItemStack((ItemLike)IC2Blocks.BRONZE_BLOCK, 4), new ItemStack((ItemLike)IC2Blocks.DUST_COPPER_BLOCK, 3), new ItemStack((ItemLike)IC2Blocks.DUST_TIN_BLOCK));
        recipes.addIC2SimpleRecipe("bronze_mixed", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 12), IC2Tags.STORAGE_COPPER, new ItemTagInput(IC2Tags.INGOT_TIN, 3));
        recipes.addIC2SimpleRecipe("bronze_mixed_2", new ItemStack((ItemLike)IC2Items.INGOT_BRONZE, 36), new ItemTagInput(IC2Tags.INGOT_COPPER, 27), IC2Tags.STORAGE_TIN);
        recipes.addIC2SimpleRecipe("pulsating_quartz", new ItemStack((ItemLike)IC2Items.PULSATING_QUARTZ), new ItemTagInput((TagKey<Item>)Tags.Items.DUSTS_REDSTONE, 2), Items.f_42692_);
        recipes.addIC2SimpleRecipe("netherite_ingot", new ItemStack((ItemLike)Items.f_42418_), new ItemInput(Items.f_42419_, 2), new ItemInput(Items.f_42417_, 2));
    }
}

