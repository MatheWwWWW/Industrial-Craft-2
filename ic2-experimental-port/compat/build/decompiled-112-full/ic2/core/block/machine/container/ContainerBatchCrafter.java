/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntCollection
 *  gnu.trove.map.TIntIntMap
 *  gnu.trove.map.hash.TIntIntHashMap
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.machine.container;

import gnu.trove.TIntCollection;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBatchCrafter
extends ContainerElectricMachine<TileEntityBatchCrafter> {
    protected final TIntIntMap indexToSlot = new TIntIntHashMap();
    public static final short HEIGHT = 206;

    public ContainerBatchCrafter(EntityPlayer player, TileEntityBatchCrafter tileEntity) {
        super(player, tileEntity, 206, 8, 62);
        int slot;
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.func_75146_a(new SlotHologramSlot(tileEntity.craftingGrid, x + y * 3, 30 + x * 18, 17 + y * 18, 1, new SlotHologramSlot.ChangeCallback(){

                    @Override
                    public void onChanged(int index) {
                        if (((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).func_145830_o() && !((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).func_145831_w().field_72995_K) {
                            ((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).matrixChange(index);
                        }
                    }
                }));
            }
        }
        this.func_75146_a(new SlotInvSlot(tileEntity.craftingOutput, 0, 124, 35));
        for (slot = 0; slot < 9; ++slot) {
            this.indexToSlot.put(slot, this.func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.ingredientsRow[slot], (int)0, (int)(8 + slot * 18), (int)84)).field_75222_d);
            this.func_75146_a(new SlotInvSlot(tileEntity.containerOutput, slot, 8 + slot * 18, 102));
        }
        for (slot = 0; slot < 4; ++slot) {
            this.func_75146_a(new SlotInvSlot(tileEntity.upgradeSlot, slot, 152, 8 + slot * 18));
        }
    }

    @Override
    protected ItemStack handlePlayerSlotShiftClick(EntityPlayer player, ItemStack sourceItemStack) {
        Tuple.T2<List<ItemStack>, ? extends TIntCollection> changes = StackUtil.balanceStacks((IInventory)((TileEntityBatchCrafter)this.base).ingredients, ((TileEntityBatchCrafter)this.base).acceptPredicate, StackUtil.getSlotsFromInv((IInventory)((TileEntityBatchCrafter)this.base).ingredients), Collections.singleton(sourceItemStack));
        for (int currentSlot : (TIntCollection)changes.b) {
            ((Slot)this.field_75151_b.get(this.indexToSlot.get(currentSlot))).func_75218_e();
        }
        sourceItemStack = ((List)changes.a).isEmpty() ? StackUtil.emptyStack : (ItemStack)((List)changes.a).get(0);
        return sourceItemStack;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> fields = super.getNetworkedFields();
        fields.add("guiProgress");
        fields.add("recipeOutput");
        return fields;
    }
}

