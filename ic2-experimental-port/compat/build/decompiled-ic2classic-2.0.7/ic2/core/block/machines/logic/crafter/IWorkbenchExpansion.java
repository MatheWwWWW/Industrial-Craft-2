/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.machines.logic.crafter;

import ic2.api.util.DirectionList;
import ic2.core.block.machines.logic.crafter.CraftRecipe;
import ic2.core.block.machines.logic.crafter.CraftingList;
import ic2.core.block.machines.logic.crafter.Snapshot;
import ic2.core.block.machines.tiles.nv.IndustrialWorkbenchTileEntity;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.utils.helpers.AABBUtil;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IWorkbenchExpansion {
    public boolean addUnloadListener(IndustrialWorkbenchTileEntity var1);

    default public boolean needsRecipeInfo() {
        return false;
    }

    default public void setCurrentCraftingRecipe(CraftRecipe recipe, int recipeSlot) {
    }

    default public void clearCurrentRecipe() {
    }

    public void createMainTransporter(boolean var1, List<IItemTransporter> var2, List<IItemTransporter> var3);

    public void createTrashTransporter(boolean var1, List<IItemTransporter> var2, List<IItemTransporter> var3);

    public CraftingList getExpansionRecipeList();

    public void createSnapshot();

    public void destroySnapshot();

    public void releaseSnapshot();

    public void triggerRestock(IItemTransporter var1, IFluidHandler var2, Player var3);

    public Snapshot takeSnapshot();

    public void setSnapshot(Snapshot var1);

    public static NonNullList<ItemStack> restockInventory(IItemTransporter transporter, IHasInventory inventory, Int2ObjectMap<ItemStack[]> difference) {
        NonNullList toAdd = NonNullList.m_122779_();
        Int2ObjectLinkedOpenHashMap toRequest = new Int2ObjectLinkedOpenHashMap();
        for (Int2ObjectMap.Entry entry : Int2ObjectMaps.fastIterable(difference)) {
            int slot = entry.getIntKey();
            ItemStack stored = ((ItemStack[])entry.getValue())[0];
            ItemStack expected = ((ItemStack[])entry.getValue())[1];
            if (!expected.m_41619_() && !stored.m_41619_()) {
                if (expected.m_41763_() ? !ItemStack.m_41758_((ItemStack)expected, (ItemStack)stored) : !ItemStack.m_41746_((ItemStack)expected, (ItemStack)stored)) {
                    toAdd.add((Object)stored);
                    inventory.setStackInSlot(slot, ItemStack.f_41583_);
                    toRequest.put(slot, (Object)expected);
                    continue;
                }
                if (expected.m_41741_() <= stored.m_41613_()) continue;
                toRequest.put(slot, (Object)StackUtil.copyWithSize(expected, expected.m_41741_() - stored.m_41613_()));
                continue;
            }
            if (expected.m_41619_() || !stored.m_41619_()) continue;
            toRequest.put(slot, (Object)StackUtil.copyWithSize(expected, expected.m_41741_()));
        }
        for (Int2ObjectMap.Entry entry : Int2ObjectMaps.fastIterable((Int2ObjectMap)toRequest)) {
            ItemStack value = (ItemStack)entry.getValue();
            ItemStack removed = transporter.removeItem(StackFilter.defaultCompare((ItemStack)entry.getValue()), null, value.m_41613_(), false);
            if (removed.m_41619_()) continue;
            value.m_41774_(removed.m_41613_());
            ItemStack stored = inventory.getStackInSlot(entry.getIntKey());
            if (!stored.m_41619_()) {
                stored.m_41769_(removed.m_41613_());
                inventory.setStackInSlot(entry.getIntKey(), stored);
                continue;
            }
            inventory.setStackInSlot(entry.getIntKey(), removed.m_41777_());
        }
        return toAdd;
    }

    public static <T extends BlockEntity> void searchForBenches(T tile) {
        AABBUtil.countBlocks(tile.m_58904_(), tile.m_58899_(), IndustrialWorkbenchTileEntity.getExpansionLimit(), new ExpansionFilter((IWorkbenchExpansion)tile), 0, DirectionList.ALL);
    }

    public static class ExpansionFilter
    implements AABBUtil.IBlockFilter {
        IWorkbenchExpansion expansion;

        public ExpansionFilter(IWorkbenchExpansion expansion) {
            this.expansion = expansion;
        }

        @Override
        public boolean isValid(LevelReader world, BlockPos pos) {
            BlockEntity tile = world.m_7702_(pos);
            if (tile instanceof IndustrialWorkbenchTileEntity) {
                ((IndustrialWorkbenchTileEntity)tile).addExpansion(this.expansion);
                return false;
            }
            return tile instanceof IWorkbenchExpansion;
        }

        @Override
        public boolean isValid(BlockState state) {
            return false;
        }
    }
}

