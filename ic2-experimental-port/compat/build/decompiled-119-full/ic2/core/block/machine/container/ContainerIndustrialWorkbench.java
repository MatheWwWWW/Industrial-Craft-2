/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.inventory.ResultContainer
 *  net.minecraft.world.inventory.ResultSlot
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.CraftingRecipe
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.machine.container;

import ic2.core.ContainerFullInv;
import ic2.core.IC2;
import ic2.core.block.SimpleCraftingInventory;
import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ContainerIndustrialWorkbench
extends ContainerFullInv<TileEntityIndustrialWorkbench> {
    protected final CraftingContainer craftMatrix;
    protected final Container craftResult;
    protected final Slot[] outputs;
    public final Player player;
    public final int indexOutput;
    public final int indexGridStart;
    public final int indexGridEnd;
    public final int indexBufferStart;
    public final int indexBufferEnd;
    public final int indexOutputHammer;
    public final int indexOutputCutter;
    public static final int WIDTH = 194;
    public static final int HEIGHT = 228;

    public ContainerIndustrialWorkbench(int n, Inventory inventory, TileEntityIndustrialWorkbench tileEntityIndustrialWorkbench) {
        super(Ic2ScreenHandlers.INDUSTRIAL_WORKBENCH, n, inventory, tileEntityIndustrialWorkbench, 228);
        int n2;
        int n3;
        this.craftMatrix = new SimpleCraftingInventory.InvSlotCraftingInventory(((TileEntityIndustrialWorkbench)this.base).craftingGrid, 3){

            @Override
            protected void set(int n, ItemStack itemStack) {
                super.set(n, itemStack);
                ContainerIndustrialWorkbench.this.m_6199_((Container)this);
            }
        };
        this.craftResult = new ResultContainer();
        this.outputs = new Slot[3];
        this.player = inventory.f_35978_;
        this.indexOutput = this.f_38839_.size();
        this.outputs[0] = this.m_38897_((Slot)new ResultSlot(this.player, this.craftMatrix, this.craftResult, 0, 124, 61){

            protected void m_5845_(ItemStack itemStack) {
                if (IC2.sideProxy.isRendering()) {
                    IC2.network.get(false).sendContainerEvent(ContainerIndustrialWorkbench.this, "craft");
                } else {
                    ContainerIndustrialWorkbench.this.onContainerEvent("craft");
                }
                super.m_5845_(itemStack);
            }
        });
        this.indexGridStart = this.f_38839_.size();
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 3; ++n2) {
                this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.craftingGrid, n2 + n3 * 3, 30 + n2 * 18, 43 + n3 * 18){

                    public void m_6654_() {
                        super.m_6654_();
                        ContainerIndustrialWorkbench.this.m_6199_((Container)ContainerIndustrialWorkbench.this.craftMatrix);
                    }
                });
            }
        }
        this.indexGridEnd = this.f_38839_.size();
        this.indexBufferStart = this.f_38839_.size();
        for (n3 = 0; n3 < 2; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.craftingStorage, n2 + n3 * 9, 8 + n2 * 18, 106 + n3 * 18));
            }
        }
        this.indexBufferEnd = this.f_38839_.size();
        this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.leftCrafting.tool, 0, 7, 17));
        this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.leftCrafting.input, 0, 25, 17));
        this.indexOutputHammer = this.f_38839_.size();
        this.outputs[1] = this.m_38897_((Slot)new ResultSlot(this.player, tileEntityIndustrialWorkbench.leftCrafting.crafting, (Container)tileEntityIndustrialWorkbench.leftCrafting.resultInv, 0, 69, 17));
        this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.rightCrafting.tool, 0, 91, 17));
        this.m_38897_(new SlotInvSlot(tileEntityIndustrialWorkbench.rightCrafting.input, 0, 109, 17));
        this.indexOutputCutter = this.f_38839_.size();
        this.outputs[2] = this.m_38897_((Slot)new ResultSlot(this.player, tileEntityIndustrialWorkbench.rightCrafting.crafting, (Container)tileEntityIndustrialWorkbench.rightCrafting.resultInv, 0, 153, 17));
        this.m_6199_((Container)this.craftMatrix);
    }

    @Override
    public void onContainerEvent(String string) {
        if ("craft".equals(string)) {
            this.m_38946_();
            ((TileEntityIndustrialWorkbench)this.base).rebalance();
            this.m_38946_();
        } else if ("clear".equals(string)) {
            this.m_38946_();
            ((TileEntityIndustrialWorkbench)this.base).clear(this.player);
            this.m_38946_();
        }
        super.onContainerEvent(string);
    }

    public void m_6199_(Container container) {
        Level level = ((TileEntityIndustrialWorkbench)this.base).m_58904_();
        CraftingRecipe craftingRecipe = level.m_7654_().m_129894_().m_44015_(RecipeType.f_44107_, (Container)this.craftMatrix, level).orElse(null);
        ItemStack itemStack = craftingRecipe.m_5874_((Container)this.craftMatrix);
        this.craftResult.m_6836_(0, itemStack);
    }

    public boolean m_5882_(ItemStack itemStack, Slot slot) {
        for (Slot slot2 : this.outputs) {
            if (slot.f_40218_ != slot2.f_40218_) continue;
            return false;
        }
        return super.m_5882_(itemStack, slot);
    }

    @Override
    protected ItemStack handlePlayerSlotShiftClick(Player player, ItemStack itemStack) {
        Tuple.T2<List<ItemStack>, ? extends IntCollection> t2 = StackUtil.balanceStacks((Container)this.craftMatrix, itemStack);
        IntIterator intIterator = ((IntCollection)t2.b).iterator();
        while (intIterator.hasNext()) {
            int n = intIterator.nextInt();
            ((Slot)this.f_38839_.get(n + 37)).m_6654_();
        }
        if (!((List)t2.a).isEmpty()) {
            return super.handlePlayerSlotShiftClick(player, (ItemStack)((List)t2.a).get(0));
        }
        return StackUtil.emptyStack;
    }

    @Override
    protected ItemStack handleGUISlotShiftClick(Player player, ItemStack itemStack) {
        int n;
        ItemStack itemStack2 = itemStack.m_41777_();
        Slot slot = null;
        for (Slot object2 : this.outputs) {
            if (object2.m_7993_() != itemStack) continue;
            slot = object2;
            break;
        }
        boolean bl = slot != null;
        int n2 = 0;
        for (n = this.indexBufferStart; n < this.indexBufferEnd; ++n) {
            Slot slot2 = (Slot)this.f_38839_.get(n);
            if (slot2.m_7993_() != itemStack) continue;
            n2 = 1;
            break;
        }
        block2: for (n = 0; n < 2 && !StackUtil.isEmpty(itemStack); ++n) {
            ListIterator listIterator = this.f_38839_.listIterator(this.f_38839_.size());
            while (listIterator.hasPrevious()) {
                Slot slot3 = (Slot)listIterator.previous();
                if (slot3.f_40218_ != player.m_150109_() && (n2 != 0 || slot3.f_40219_ < this.indexBufferStart || slot3.f_40219_ >= this.indexBufferEnd) || !ContainerIndustrialWorkbench.isValidTargetSlot(slot3, itemStack, n == 1, false) || !StackUtil.isEmpty(itemStack = this.transfer(itemStack, slot3))) continue;
                if (!bl) continue block2;
                slot.m_40234_(itemStack, itemStack2);
                slot.m_142406_(player, itemStack2);
                if (!slot.m_6657_() || !StackUtil.checkItemEquality(slot.m_7993_(), itemStack2)) continue block2;
                itemStack = slot.m_7993_();
                itemStack2 = itemStack.m_41777_();
                assert (listIterator.hasNext());
                listIterator.next();
            }
        }
        return itemStack;
    }
}

