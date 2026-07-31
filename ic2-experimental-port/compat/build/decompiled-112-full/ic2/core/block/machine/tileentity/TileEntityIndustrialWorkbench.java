/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntCollection
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCraftResult
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.tileentity;

import gnu.trove.TIntCollection;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableOreDict;
import ic2.core.block.machine.container.ContainerIndustrialWorkbench;
import ic2.core.block.machine.gui.GuiIndustrialWorkbench;
import ic2.core.profile.NotClassic;
import ic2.core.ref.ItemName;
import ic2.core.util.IInventoryInvSlot;
import ic2.core.util.InventorySlotCrafting;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@NotClassic
public class TileEntityIndustrialWorkbench
extends TileEntityInventory
implements IHasGui {
    public final InvSlot craftingGrid = new InvSlot(this, "crafting", InvSlot.Access.NONE, 9);
    public final InvSlot craftingStorage = new InvSlot(this, "craftingStorage", InvSlot.Access.I, 18);
    public final InvSlotCraftingCombo leftCrafting = new InvSlotCraftingCombo(this, "left", "craftingToolForgeHammer");
    public final InvSlotCraftingCombo rightCrafting = new InvSlotCraftingCombo(this, "right", "craftingToolWireCutter");

    @Override
    public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        if (!stack.func_77942_o() || !stack.func_77978_p().func_74764_b("PLACED")) {
            this.leftCrafting.tool.put(ItemName.forge_hammer.getItemStack());
            this.rightCrafting.tool.put(ItemName.cutter.getItemStack());
        }
    }

    @Override
    protected ItemStack adjustDrop(ItemStack drop, boolean wrench) {
        drop = super.adjustDrop(drop, wrench);
        StackUtil.getOrCreateNbtData(drop).func_74757_a("PLACED", true);
        return drop;
    }

    public void rebalance() {
        if (!this.craftingGrid.isEmpty()) {
            boolean changed = false;
            IInventoryInvSlot crafting = new IInventoryInvSlot(this.craftingGrid);
            int size = this.craftingStorage.size();
            for (int index = 0; index < size; ++index) {
                if (this.craftingStorage.isEmpty(index)) continue;
                Tuple.T2<List<ItemStack>, ? extends TIntCollection> changes = StackUtil.balanceStacks((IInventory)crafting, this.craftingStorage.get(index));
                if (((TIntCollection)changes.b).isEmpty()) continue;
                changed = true;
                ItemStack toPut = ((List)changes.a).isEmpty() ? StackUtil.emptyStack : (ItemStack)((List)changes.a).get(0);
                this.craftingStorage.put(index, toPut);
            }
            if (changed) {
                this.func_70296_d();
            }
        }
    }

    private static int getPossible(int max, ItemStack existing, ItemStack in) {
        int amount = Math.min(max, in.func_77985_e() ? in.func_77976_d() : 1);
        if (!StackUtil.isEmpty(existing)) {
            if (!StackUtil.checkItemEqualityStrict(existing, in)) {
                return 0;
            }
            amount -= StackUtil.getSize(existing);
        }
        return Math.min(amount, StackUtil.getSize(in));
    }

    private static ItemStack transfer(InvSlot slot, ItemStack gridItem, boolean allowEmpty) {
        for (int index = 0; index < slot.size(); ++index) {
            ItemStack stack = slot.get(index);
            int amount = TileEntityIndustrialWorkbench.getPossible(slot.getStackSizeLimit(), stack, gridItem);
            if (amount < 1) continue;
            if (StackUtil.isEmpty(stack)) {
                if (!allowEmpty) continue;
                slot.put(index, StackUtil.copyWithSize(gridItem, amount));
            } else {
                slot.put(index, StackUtil.incSize(stack, amount));
            }
            gridItem = StackUtil.decSize(gridItem, amount);
            if (StackUtil.isEmpty(gridItem)) break;
        }
        return gridItem;
    }

    public void clear(EntityPlayer player) {
        if (!this.craftingGrid.isEmpty()) {
            block0: for (int index = 0; index < this.craftingGrid.size(); ++index) {
                if (this.craftingGrid.isEmpty(index)) continue;
                ItemStack stack = this.craftingGrid.get(index);
                for (int pass = 0; pass < 2; ++pass) {
                    if (!StackUtil.isEmpty(stack = TileEntityIndustrialWorkbench.transfer(this.craftingStorage, stack, pass == 1))) continue;
                    this.craftingGrid.clear(index);
                    continue block0;
                }
                if (StackUtil.storeInventoryItem(stack, player, false)) {
                    this.craftingGrid.clear(index);
                    continue;
                }
                this.craftingGrid.put(stack);
            }
        }
    }

    public ContainerBase<TileEntityIndustrialWorkbench> getGuiContainer(EntityPlayer player) {
        return new ContainerIndustrialWorkbench(player, this);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiIndustrialWorkbench(new ContainerIndustrialWorkbench(player, this));
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
    }

    public static class InvSlotCraftingCombo {
        protected IRecipe recipe;
        public final InvSlotConsumable input;
        public final InvSlotConsumableOreDict tool;
        public final InventoryCrafting crafting = new InventorySlotCrafting(2, 1){

            private InvSlot getSlot(int index) {
                switch (index) {
                    case 0: {
                        return tool;
                    }
                    case 1: {
                        return input;
                    }
                }
                throw new IllegalArgumentException("Invalid index: " + index);
            }

            @Override
            protected ItemStack get(int index) {
                return this.getSlot(index).get();
            }

            @Override
            protected void put(int index, ItemStack stack) {
                this.getSlot(index).put(stack);
            }

            @Override
            public boolean func_191420_l() {
                return input.isEmpty() && tool.isEmpty();
            }

            @Override
            public void func_174888_l() {
                input.clear();
                tool.clear();
            }
        };
        public final InventoryCraftResult resultInv = new InventoryCraftResult();

        public InvSlotCraftingCombo(TileEntityInventory base, String name, String tool) {
            this.input = new InvSlotConsumable(base, name + "Input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public boolean accepts(ItemStack stack) {
                    ItemStack prev = this.get();
                    try {
                        this.put(stack);
                        boolean bl = this.canProcess();
                        return bl;
                    }
                    finally {
                        this.put(prev);
                    }
                }

                @Override
                public void onChanged() {
                    resultInv.func_70299_a(0, this.getOutputStack());
                }
            };
            this.tool = new InvSlotConsumableOreDict(base, name + "Tool", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, tool){

                @Override
                public void onChanged() {
                    resultInv.func_70299_a(0, this.getOutputStack());
                }
            };
        }

        protected boolean canProcess() {
            if (!this.crafting.func_191420_l()) {
                if (this.recipe == null || !this.recipe.func_77569_a(this.crafting, this.tool.base.getParent().func_145831_w())) {
                    this.recipe = CraftingManager.func_192413_b((InventoryCrafting)this.crafting, (World)this.tool.base.getParent().func_145831_w());
                    return this.recipe != null;
                }
                return true;
            }
            return false;
        }

        public ItemStack getOutputStack() {
            return !this.canProcess() ? StackUtil.emptyStack : this.recipe.func_77572_b(this.crafting);
        }
    }
}

