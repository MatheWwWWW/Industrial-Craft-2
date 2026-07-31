/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.ResultContainer
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.machine.tileentity;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.SimpleCraftingInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableTag;
import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.network.GrowingBuffer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import ic2.core.ref.Ic2ItemTags;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import it.unimi.dsi.fastutil.ints.IntCollection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityIndustrialWorkbench
extends TileEntityInventory
implements IHasGui {
    public final InvSlot craftingGrid = new InvSlot(this, "crafting", InvSlot.Access.NONE, 9);
    public final InvSlot craftingStorage = new InvSlot(this, "craftingStorage", InvSlot.Access.I, 18);
    public final InvSlotCraftingCombo leftCrafting = new InvSlotCraftingCombo(this, "left", Ic2ItemTags.FORGE_HAMMERS);
    public final InvSlotCraftingCombo rightCrafting = new InvSlotCraftingCombo(this, "right", Ic2ItemTags.WIRE_CUTTERS);

    public TileEntityIndustrialWorkbench(BlockPos blockPos, BlockState blockState) {
        super((BlockEntityType<? extends TileEntityInventory>)Ic2BlockEntities.INDUSTRIAL_WORKBENCH, blockPos, blockState);
    }

    @Override
    public void onPlaced(ItemStack itemStack, LivingEntity livingEntity, Direction direction) {
        super.onPlaced(itemStack, livingEntity, direction);
        if (!itemStack.m_41782_() || !itemStack.m_41783_().m_128441_("PLACED")) {
            this.leftCrafting.tool.put(new ItemStack((ItemLike)Ic2Items.FORGE_HAMMER));
            this.rightCrafting.tool.put(new ItemStack((ItemLike)Ic2Items.CUTTER));
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack itemStack, boolean bl) {
        itemStack = super.adjustDrop(itemStack, bl);
        StackUtil.getOrCreateNbtData(itemStack).m_128379_("PLACED", true);
        return itemStack;
    }

    public void rebalance() {
        if (!this.craftingGrid.isEmpty()) {
            boolean bl = false;
            SimpleCraftingInventory.InvSlotCraftingInventory invSlotCraftingInventory = new SimpleCraftingInventory.InvSlotCraftingInventory(this.craftingGrid, 3);
            int n = this.craftingStorage.size();
            for (int i = 0; i < n; ++i) {
                if (this.craftingStorage.isEmpty(i)) continue;
                Tuple.T2<List<ItemStack>, ? extends IntCollection> t2 = StackUtil.balanceStacks((Container)invSlotCraftingInventory, this.craftingStorage.get(i));
                if (((IntCollection)t2.b).isEmpty()) continue;
                bl = true;
                ItemStack itemStack = ((List)t2.a).isEmpty() ? StackUtil.emptyStack : (ItemStack)((List)t2.a).get(0);
                this.craftingStorage.put(i, itemStack);
            }
            if (bl) {
                this.m_6596_();
            }
        }
    }

    private static int getPossible(int n, ItemStack itemStack, ItemStack itemStack2) {
        int n2 = Math.min(n, itemStack2.m_41753_() ? itemStack2.m_41741_() : 1);
        if (!StackUtil.isEmpty(itemStack)) {
            if (!StackUtil.checkItemEqualityStrict(itemStack, itemStack2)) {
                return 0;
            }
            n2 -= StackUtil.getSize(itemStack);
        }
        return Math.min(n2, StackUtil.getSize(itemStack2));
    }

    private static ItemStack transfer(InvSlot invSlot, ItemStack itemStack, boolean bl) {
        for (int i = 0; i < invSlot.size(); ++i) {
            ItemStack itemStack2 = invSlot.get(i);
            int n = TileEntityIndustrialWorkbench.getPossible(invSlot.getStackSizeLimit(), itemStack2, itemStack);
            if (n < 1) continue;
            if (StackUtil.isEmpty(itemStack2)) {
                if (!bl) continue;
                invSlot.put(i, StackUtil.copyWithSize(itemStack, n));
            } else {
                invSlot.put(i, StackUtil.incSize(itemStack2, n));
            }
            itemStack = StackUtil.decSize(itemStack, n);
            if (StackUtil.isEmpty(itemStack)) break;
        }
        return itemStack;
    }

    public void clear(Player player) {
        if (!this.craftingGrid.isEmpty()) {
            block0: for (int i = 0; i < this.craftingGrid.size(); ++i) {
                if (this.craftingGrid.isEmpty(i)) continue;
                ItemStack itemStack = this.craftingGrid.get(i);
                for (int j = 0; j < 2; ++j) {
                    if (!StackUtil.isEmpty(itemStack = TileEntityIndustrialWorkbench.transfer(this.craftingStorage, itemStack, j == 1))) continue;
                    this.craftingGrid.clear(i);
                    continue block0;
                }
                if (StackUtil.storeInventoryItem(itemStack, player, false)) {
                    this.craftingGrid.clear(i);
                    continue;
                }
                this.craftingGrid.put(itemStack);
            }
        }
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int n, Player player) {
        return new ContainerIndustrialWorkbench(n, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int n, Inventory inventory, GrowingBuffer growingBuffer) {
        return new ContainerIndustrialWorkbench(n, inventory, this);
    }

    public static class InvSlotCraftingCombo {
        protected CraftingRecipe recipe;
        public final InvSlotConsumable input;
        public final InvSlotConsumableTag tool;
        public final CraftingContainer crafting = new SimpleCraftingInventory(2, 1){

            private InvSlot getSlot(int n) {
                switch (n) {
                    case 0: {
                        return tool;
                    }
                    case 1: {
                        return input;
                    }
                }
                throw new IllegalArgumentException("Invalid index: " + n);
            }

            @Override
            protected ItemStack get(int n) {
                return this.getSlot(n).get();
            }

            @Override
            protected void set(int n, ItemStack itemStack) {
                this.getSlot(n).put(itemStack);
            }
        };
        public final ResultContainer resultInv = new ResultContainer();

        public InvSlotCraftingCombo(TileEntityInventory tileEntityInventory, String string, TagKey<Item> tagKey) {
            this.input = new InvSlotConsumable(tileEntityInventory, string + "Input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public boolean accepts(ItemStack itemStack) {
                    ItemStack itemStack2 = this.get();
                    try {
                        this.put(itemStack);
                        boolean bl = this.canProcess();
                        return bl;
                    }
                    finally {
                        this.put(itemStack2);
                    }
                }

                @Override
                public void onChanged() {
                    resultInv.m_6836_(0, this.getOutputStack());
                }
            };
            this.tool = new InvSlotConsumableTag(tileEntityInventory, string + "Tool", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, tagKey){

                @Override
                public void onChanged() {
                    resultInv.m_6836_(0, this.getOutputStack());
                }
            };
        }

        protected boolean canProcess() {
            if (!this.crafting.m_7983_()) {
                Level level = this.tool.base.getParent().m_58904_();
                if (level == null || level.m_7654_() == null) {
                    return false;
                }
                if (this.recipe == null || !this.recipe.m_5818_((Container)this.crafting, level)) {
                    this.recipe = level.m_7654_().m_129894_().m_44015_(RecipeType.f_44107_, (Container)this.crafting, level).orElse(null);
                    return this.recipe != null;
                }
                return true;
            }
            return false;
        }

        public ItemStack getOutputStack() {
            return !this.canProcess() ? StackUtil.emptyStack : this.recipe.m_5874_((Container)this.crafting);
        }
    }
}

