/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntIterator
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machine.container;

import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.slot.SlotHologramSlot;
import ic2.core.slot.SlotInvSlot;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ContainerBatchCrafter
extends ContainerElectricMachine<TileEntityBatchCrafter> {
    protected final Int2IntMap indexToSlot = new Int2IntOpenHashMap();
    public static final int HEIGHT = 206;

    public ContainerBatchCrafter(int n, Inventory inventory, TileEntityBatchCrafter tileEntityBatchCrafter) {
        super(Ic2ScreenHandlers.BATCH_CRAFTER, n, inventory, tileEntityBatchCrafter, 206, 8, 62);
        int n2;
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 3; ++i) {
                this.m_38897_(new SlotHologramSlot(tileEntityBatchCrafter.craftingGrid, i + n2 * 3, 30 + i * 18, 17 + n2 * 18, 1, new SlotHologramSlot.ChangeCallback(){

                    @Override
                    public void onChanged(int n) {
                        if (((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).m_58898_() && !((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).m_58904_().f_46443_) {
                            ((TileEntityBatchCrafter)ContainerBatchCrafter.this.base).matrixChange(n);
                        }
                    }
                }));
            }
        }
        this.m_38897_(new SlotInvSlot(tileEntityBatchCrafter.craftingOutput, 0, 124, 35));
        for (n2 = 0; n2 < 9; ++n2) {
            this.indexToSlot.put(n2, this.m_38897_((Slot)new SlotInvSlot((InvSlot)tileEntityBatchCrafter.ingredientsRow[n2], (int)0, (int)(8 + n2 * 18), (int)84)).f_40219_);
            this.m_38897_(new SlotInvSlot(tileEntityBatchCrafter.containerOutput, n2, 8 + n2 * 18, 102));
        }
        for (n2 = 0; n2 < 4; ++n2) {
            this.m_38897_(new SlotInvSlot(tileEntityBatchCrafter.upgradeSlot, n2, 152, 8 + n2 * 18));
        }
    }

    @Override
    protected ItemStack handlePlayerSlotShiftClick(Player player, ItemStack itemStack) {
        Tuple.T2<List<ItemStack>, ? extends IntCollection> t2 = StackUtil.balanceStacks((Container)((TileEntityBatchCrafter)this.base).ingredients, ((TileEntityBatchCrafter)this.base).acceptPredicate, StackUtil.getSlotsFromInv((Container)((TileEntityBatchCrafter)this.base).ingredients), Collections.singleton(itemStack));
        IntIterator intIterator = ((IntCollection)t2.b).iterator();
        while (intIterator.hasNext()) {
            int n = intIterator.nextInt();
            ((Slot)this.f_38839_.get(this.indexToSlot.get(n))).m_6654_();
        }
        itemStack = ((List)t2.a).isEmpty() ? StackUtil.emptyStack : (ItemStack)((List)t2.a).get(0);
        return itemStack;
    }

    @Override
    public List<String> getNetworkedFields() {
        List<String> list = super.getNetworkedFields();
        list.add("guiProgress");
        list.add("recipeOutput");
        return list;
    }
}

