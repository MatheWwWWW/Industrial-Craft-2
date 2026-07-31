/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machines.tiles.hv;

import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.queue.SimpleStackOutput;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseAdvMultiMachineTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseMultiMachineTileEntity;
import ic2.core.block.machines.containers.hv.PressureFurnaceContainer;
import ic2.core.block.multi.tiles.MachineLinkingTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.inventory.inv.RangedInventory;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.math.StructureBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PressureAlloyFurnaceTileEntity
extends BaseAdvMultiMachineTileEntity
implements IWrenchRemovable {
    public static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui_sprites/blocks/machines/lv/gui_alloy_smelter.png");
    public static final Component HEAT = Component.m_237115_((String)"info.block.ic2.induction_furnace.heat");

    public PressureAlloyFurnaceTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 4, 2, 15, 4000, 10000, 512);
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
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return super.hasGui(player, hand, side) && this.isValid;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new PressureFurnaceContainer(this, player, windowID);
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.85 - 0.05 * (double)this.baseTier;
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{2};
    }

    @Override
    public Slot[] addSlots(Player player) {
        return null;
    }

    @Override
    public Component getSpeedName() {
        return HEAT;
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
    public BlockEntityType<?> createType() {
        return IC2Tiles.PRESSURE_ALLOY_FURNACE;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected void onInitialValidation() {
        List<BaseLinkingTileEntity> tiles = this.children.getTiles();
        int m = tiles.size();
        for (int i = 0; i < m; ++i) {
            tiles.get(i).setState(i + 1).setFacing(this.getFacing());
        }
        this.onStateChanged();
    }

    @Override
    public void onStructureInvalidated(boolean wasValid, boolean removeMaster) {
        super.onStructureInvalidated(wasValid, removeMaster);
        if (wasValid) {
            this.speed = 0;
            this.updateGuiField("speed");
        }
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        return newState.m_60734_() == IC2Blocks.PRESSURE_ALLOY_FURNACE_MULTIBLOCK;
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        int i = 1;
        while (builder.cubicStructure(i, 2, 2, 2)) {
            if (!builder.isBlock(IC2Blocks.PRESSURE_ALLOY_FURNACE_MULTIBLOCK)) {
                return false;
            }
            this.children.add(builder.getTile(MachineLinkingTileEntity.class));
            ++i;
        }
        return true;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(int slot, ItemStack stack) {
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
    protected BaseMultiMachineTileEntity.RecipeResult isRecipeStillValid(int slot, IMachineRecipeList.RecipeEntry entry) {
        return this.matchesRecipe(entry) ? BaseMultiMachineTileEntity.RecipeResult.SUCCESS : BaseMultiMachineTileEntity.RecipeResult.FAIL;
    }

    @Override
    public void consumeInput(IInput[] inputs, int slot, boolean emptyContainer) {
        if (this.matches(inputs[0], 1)) {
            this.consume(inputs[0], emptyContainer, 1);
            this.consume(inputs[1], emptyContainer, 3);
        } else {
            this.consume(inputs[0], emptyContainer, 3);
            this.consume(inputs[1], emptyContainer, 1);
        }
    }

    protected void consume(IInput input, boolean consume, int slot) {
        if (consume && ((ItemStack)this.inventory.get(slot)).hasCraftingRemainingItem()) {
            this.outputs.add(new SimpleStackOutput(((ItemStack)this.inventory.get(slot)).getCraftingRemainingItem(), 2));
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
        int subSlot;
        if (hand) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, 0)) != null;
        }
        int mainSlot = first ? 1 : 3;
        int n = subSlot = first ? 3 : 1;
        if (((ItemStack)this.inventory.get(mainSlot)).m_41619_()) {
            return this.getRecipeList().getRecipe(T -> this.matchesRecipe((IMachineRecipeList.RecipeEntry)T, stack, ((ItemStack)this.inventory.get(subSlot)).m_41619_() ? 0 : (first ? 1 : 2))) != null;
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
}

