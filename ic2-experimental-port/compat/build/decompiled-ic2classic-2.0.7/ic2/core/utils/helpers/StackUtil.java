/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Component$Serializer
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.FluidUtil
 */
package ic2.core.utils.helpers;

import ic2.api.items.ITagItem;
import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class StackUtil {
    public static final int EMPTY_FLAG = 1;
    public static final int EMPTY_KEY = 2;
    public static final int ITEM_FLAG = 4;
    public static final int NBT_FUZZY_FLAG = 8;
    public static final int NBT_EXACT_FLAG = 16;
    public static final int TAG_FLAG = 32;
    public static final int DURABILITY_FLAG = 64;
    public static final int FLUID_FLAG = 128;
    public static final int IGNORE_DURABILITY_NBT = 256;
    public static final int DEFAULT_COMPARE = 20;
    public static final int EMPTY_OR_DEFAULT = 22;
    private static final String DAMAGE_TAG = "Damage";

    public static void addOrPop(Player player, BlockPos pos, ItemStack stack) {
        if (stack.m_41619_()) {
            return;
        }
        if (!player.m_36356_(stack)) {
            Block.m_49840_((Level)player.f_19853_, (BlockPos)pos, (ItemStack)stack);
        } else {
            player.f_36096_.m_38946_();
        }
    }

    public static void pop(Level level, BlockPos pos, ItemStack stack) {
        ObjectArrayList result = new ObjectArrayList();
        StackUtil.expand(stack, stack.m_41613_(), (List<ItemStack>)result);
        for (ItemStack entry : result) {
            Block.m_49840_((Level)level, (BlockPos)pos, (ItemStack)entry);
        }
    }

    public static void addOrDrop(Player player, ItemStack stack) {
        if (stack.m_41619_()) {
            return;
        }
        if (!player.m_36356_(stack)) {
            player.m_36176_(stack, false);
        } else {
            player.f_36096_.m_38946_();
        }
    }

    public static CompoundTag getNbtData(ItemStack stack) {
        return !stack.m_41782_() ? new CompoundTag() : stack.m_41783_();
    }

    public static void addTooltip(ItemStack stack, Component s) {
        CompoundTag nbt = stack.m_41698_("display");
        ListTag list = nbt.m_128437_("Lore", 8);
        list.add((Object)StringTag.m_129297_((String)Component.Serializer.m_130703_((Component)s)));
        nbt.m_128365_("Lore", (Tag)list);
    }

    public static void addTooltip(ItemStack stack, String s) {
        CompoundTag nbt = stack.m_41698_("display");
        ListTag list = nbt.m_128437_("Lore", 8);
        list.add((Object)StringTag.m_129297_((String)Component.Serializer.m_130703_((Component)Component.m_237113_((String)s))));
        nbt.m_128365_("Lore", (Tag)list);
    }

    public static int getStackSizeLeft(ItemStack stack) {
        return stack.m_41741_() - stack.m_41613_();
    }

    public static ItemStack copyWithSize(ItemStack stack, int newSize) {
        ItemStack copy = stack.m_41777_();
        copy.m_41764_(newSize);
        return copy;
    }

    public static List<ItemStack> copyList(List<ItemStack> list) {
        ObjectArrayList result = new ObjectArrayList(list.size());
        for (ItemStack stack : list) {
            result.add(stack.m_41777_());
        }
        return result;
    }

    public static List<ItemStack> copyNonEmpty(List<ItemStack> list) {
        ObjectArrayList result = new ObjectArrayList(list.size());
        for (ItemStack stack : list) {
            if (stack.m_41619_()) continue;
            result.add(stack.m_41777_());
        }
        return result;
    }

    public static boolean isNBTEqual(CompoundTag subject, CompoundTag target, boolean durability) {
        if (subject == null || target == null) {
            return target == null || target.m_128456_();
        }
        for (String key : subject.m_128431_()) {
            if (durability && key.equals(DAMAGE_TAG)) continue;
            Tag subjectNBT = subject.m_128423_(key);
            Tag targetNBT = target.m_128423_(key);
            if (targetNBT != null && subjectNBT.m_7060_() == targetNBT.m_7060_() && subjectNBT.equals(targetNBT)) continue;
            return false;
        }
        return true;
    }

    public static boolean isNBTExact(ItemStack subject, ItemStack target, boolean durability) {
        boolean key = subject.m_41782_();
        boolean value = target.m_41782_();
        if (!key && !value) {
            return true;
        }
        if (!key || !value) {
            return false;
        }
        CompoundTag keyTag = subject.m_41783_();
        CompoundTag valueTag = target.m_41783_();
        if (keyTag.m_128440_() != valueTag.m_128440_()) {
            return false;
        }
        for (String entry : keyTag.m_128431_()) {
            if (durability && entry.equals(DAMAGE_TAG)) continue;
            Tag subjectNBT = keyTag.m_128423_(entry);
            Tag targetNBT = valueTag.m_128423_(entry);
            if (targetNBT != null && subjectNBT.m_7060_() == targetNBT.m_7060_() && subjectNBT.equals(targetNBT)) continue;
            return false;
        }
        return true;
    }

    public static boolean isTagEqual(ItemStack key, ItemStack other) {
        if (key.m_41619_() && other.m_41619_()) {
            return true;
        }
        if (key.m_41720_() instanceof ITagItem) {
            return ((ITagItem)key.m_41720_()).matches(key, other);
        }
        return false;
    }

    public static boolean canFitInto(ItemStack key, ItemStack value) {
        return StackUtil.isStackEqual(key, value) && StackUtil.getStackSizeLeft(key) >= value.m_41613_();
    }

    public static boolean canFitInto(ItemStack key, ItemStack value, int flags) {
        return StackUtil.isStackEqual(key, value, flags) && StackUtil.getStackSizeLeft(key) >= value.m_41613_();
    }

    public static boolean isFluidEqual(ItemStack key, ItemStack other) {
        return FluidUtil.getFluidContained((ItemStack)key).orElse(FluidStack.EMPTY).isFluidEqual(FluidUtil.getFluidContained((ItemStack)other).orElse(FluidStack.EMPTY));
    }

    public static boolean isStackEqual(ItemStack key, ItemStack other) {
        return key.m_41656_(other) && StackUtil.isNBTExact(key, other, false);
    }

    public static boolean isStackEqual(ItemStack key, ItemStack other, int flags) {
        boolean keyEmpty = key.m_41619_();
        boolean otherEmpty = other.m_41619_();
        if (keyEmpty && otherEmpty) {
            return (flags & 1) != 0;
        }
        if (keyEmpty || otherEmpty) {
            return key.m_41619_() && (flags & 2) != 0;
        }
        if ((flags & 4) != 0 && !key.m_41656_(other)) {
            if ((flags & 0x20) != 0 && StackUtil.isTagEqual(key, other)) {
                return true;
            }
            return (flags & 0x80) != 0 && StackUtil.isFluidEqual(key, other);
        }
        if ((flags & 4) == 0) {
            if ((flags & 0x20) != 0 && StackUtil.isTagEqual(key, other)) {
                return true;
            }
            if ((flags & 0x80) != 0 && StackUtil.isFluidEqual(key, other)) {
                return true;
            }
        }
        if ((flags & 0x40) != 0 && key.m_41773_() != other.m_41773_()) {
            return false;
        }
        if ((flags & 8) != 0 && !StackUtil.isNBTEqual(key.m_41783_(), other.m_41783_(), (flags & 0x100) != 0)) {
            return false;
        }
        return (flags & 0x10) == 0 || StackUtil.isNBTExact(key, other, (flags & 0x100) != 0);
    }

    public static void expand(ItemStack stack, int count, List<ItemStack> result) {
        while (count > 0) {
            int max = Math.min(count, stack.m_41741_());
            result.add(StackUtil.copyWithSize(stack, max));
            count -= max;
        }
    }

    public static boolean containsItemStack(List<ItemStack> stacks, ItemStack stack) {
        int m = stacks.size();
        for (int i = 0; i < m; ++i) {
            if (!StackUtil.isStackEqual(stack, stacks.get(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isInventoryEmpty(NonNullList<ItemStack> inventory) {
        boolean empty = true;
        for (int i = 0; i < inventory.size(); ++i) {
            if (((ItemStack)inventory.get(i)).m_41619_()) continue;
            empty = false;
            break;
        }
        return empty;
    }

    public static int countItems(List<ItemStack> items) {
        int count = 0;
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            count += items.get(i).m_41613_();
        }
        return count;
    }

    public static List<ItemStack> nonAir(List<ItemStack> list) {
        ObjectList items = CollectionUtils.createList();
        for (ItemStack stack : list) {
            if (stack.m_41619_()) continue;
            items.add((ItemStack)stack);
        }
        return items;
    }

    public static int hasItems(NonNullList<ItemStack> items, IFilter ... filters) {
        if (filters.length > 30 || items == null || items.size() <= 0) {
            return 0;
        }
        int result = 0;
        int m = filters.length;
        block0: for (int x = 0; x < m; ++x) {
            int n = items.size();
            for (int i = 0; i < n; ++i) {
                if (!filters[x].matches((ItemStack)items.get(i))) continue;
                result |= 1 << x;
                continue block0;
            }
        }
        return result;
    }

    public static boolean hasHotbarItems(Player player, IFilter filter) {
        if (player == null) {
            return false;
        }
        Inventory inv = player.m_150109_();
        for (int i = 0; i < 9; ++i) {
            if (!filter.matches(inv.m_8020_(i))) continue;
            return true;
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!filter.matches(player.m_6844_(slot))) continue;
            return true;
        }
        return false;
    }

    public static int hasHotbarItems(Player player, IFilter ... filters) {
        if (filters.length > 30 || player == null) {
            return 0;
        }
        ObjectList items = CollectionUtils.createList();
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = player.m_150109_().m_8020_(i);
            if (stack.m_41619_()) continue;
            items.add((ItemStack)stack);
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack;
            if (slot == EquipmentSlot.MAINHAND || (stack = player.m_6844_(slot)).m_41619_()) continue;
            items.add((ItemStack)stack);
        }
        if (items.isEmpty()) {
            return 0;
        }
        int result = 0;
        int m = filters.length;
        block2: for (int x = 0; x < m; ++x) {
            int n = items.size();
            for (int i = 0; i < n; ++i) {
                if (!filters[x].matches((ItemStack)items.get(i))) continue;
                result |= 1 << x;
                continue block2;
            }
        }
        return result;
    }
}

