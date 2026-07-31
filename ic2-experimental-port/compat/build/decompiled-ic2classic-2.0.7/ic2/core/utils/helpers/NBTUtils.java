/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.bytes.ByteArrayList
 *  it.unimi.dsi.fastutil.bytes.ByteCollection
 *  it.unimi.dsi.fastutil.bytes.ByteList
 *  it.unimi.dsi.fastutil.ints.IntArrays
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  net.minecraft.Util
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.ByteArrayTag
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.IntArrayTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.LongArrayTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.nbt.TextComponentTagVisitor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.StringUtil
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.registries.IForgeRegistry
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.core.Logger
 */
package ic2.core.utils.helpers;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.TextHelper;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TextComponentTagVisitor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class NBTUtils {
    public static void putBoolean(CompoundTag data, String id, boolean value, boolean defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128379_(id, value);
        }
    }

    public static void putByte(CompoundTag data, String id, byte value, byte defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128344_(id, value);
        }
    }

    public static void putByte(CompoundTag data, String id, int value, int defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128344_(id, (byte)value);
        }
    }

    public static void putShort(CompoundTag data, String id, short value, short defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128376_(id, value);
        }
    }

    public static void putShort(CompoundTag data, String id, int value, int defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128376_(id, (short)value);
        }
    }

    public static void putInt(CompoundTag data, String id, int value, int defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128405_(id, value);
        }
    }

    public static void putLong(CompoundTag data, String id, long value, long defaultValue) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128356_(id, value);
        }
    }

    public static void putFloat(CompoundTag data, String id, float value, float defaultValue) {
        if (Float.floatToIntBits(value) == Float.floatToIntBits(defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128350_(id, value);
        }
    }

    public static void putDouble(CompoundTag data, String id, double value, double defaultValue) {
        if (Double.doubleToLongBits(value) == Double.doubleToLongBits(defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128347_(id, value);
        }
    }

    public static void putString(CompoundTag data, String id, String value, String defaultValue) {
        if (Objects.equals(value, defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128359_(id, value);
        }
    }

    public static <T extends Enum<T>> void putEnum(CompoundTag data, String id, @Nullable T value) {
        int index;
        int n = index = value == null ? 0 : value.ordinal() + 1;
        if (index == 0) {
            data.m_128473_(id);
            return;
        }
        if (index < 127) {
            data.m_128344_(id, (byte)index);
        } else if (index < Short.MAX_VALUE) {
            data.m_128376_(id, (short)index);
        } else {
            data.m_128405_(id, index);
        }
    }

    public static <T extends Enum<T>> void putEnum(CompoundTag data, String id, @Nonnull T value, @Nonnull T defaultValue) {
        if (value == defaultValue || value == null) {
            data.m_128473_(id);
            return;
        }
        int index = value.ordinal() + 1;
        if (index < 127) {
            data.m_128344_(id, (byte)index);
        } else if (index < Short.MAX_VALUE) {
            data.m_128376_(id, (short)index);
        } else {
            data.m_128405_(id, index);
        }
    }

    public static <T> void putForgeEntry(CompoundTag data, String id, T value, T defaultValue, IForgeRegistry<T> registry) {
        if (value == defaultValue) {
            data.m_128473_(id);
        } else {
            data.m_128359_(id, registry.getKey(value).toString());
        }
    }

    public static void putByteArray(CompoundTag data, String id, byte[] value) {
        if (value.length <= 0) {
            data.m_128473_(id);
        } else {
            data.m_128382_(id, value);
        }
    }

    public static void putByteArray(CompoundTag data, String id, ByteCollection value) {
        if (value.isEmpty()) {
            data.m_128473_(id);
        } else {
            data.m_128382_(id, value.toByteArray());
        }
    }

    public static void putByteArray(CompoundTag data, String id, byte[] value, byte[] defaultValue) {
        if (Arrays.equals(value, defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128382_(id, value);
        }
    }

    public static void putIntArray(CompoundTag data, String id, int[] value) {
        if (value.length <= 0) {
            data.m_128473_(id);
        } else {
            data.m_128385_(id, value);
        }
    }

    public static void putIntArray(CompoundTag data, String id, IntCollection value) {
        if (value.isEmpty()) {
            data.m_128473_(id);
        } else {
            data.m_128385_(id, value.toIntArray());
        }
    }

    public static void putIntArray(CompoundTag data, String id, int[] value, int[] defaultValue) {
        if (Arrays.equals(value, defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128385_(id, value);
        }
    }

    public static void putSortedIntArray(CompoundTag data, String id, int[] value, int[] defaultValue) {
        IntArrays.quickSort((int[])value);
        if (Arrays.equals(value, defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128385_(id, value);
        }
    }

    public static void putLongArray(CompoundTag data, String id, long[] value) {
        if (value.length <= 0) {
            data.m_128473_(id);
        } else {
            data.m_128388_(id, value);
        }
    }

    public static void putLongArray(CompoundTag data, String id, LongCollection value) {
        if (value.isEmpty()) {
            data.m_128473_(id);
        } else {
            data.m_128388_(id, value.toLongArray());
        }
    }

    public static void putLongArray(CompoundTag data, String id, long[] value, long[] defaultValue) {
        if (Arrays.equals(value, defaultValue)) {
            data.m_128473_(id);
        } else {
            data.m_128388_(id, value);
        }
    }

    public static void put(CompoundTag data, String id, CompoundTag value) {
        if (value.m_128456_()) {
            data.m_128473_(id);
        } else {
            data.m_128365_(id, (Tag)value);
        }
    }

    public static void put(CompoundTag data, String id, ListTag value) {
        if (value.isEmpty()) {
            data.m_128473_(id);
        } else {
            data.m_128365_(id, (Tag)value);
        }
    }

    public static void putIf(CompoundTag data, String id, CompoundTag value, boolean add) {
        if (value.m_128456_() || !add) {
            data.m_128473_(id);
        } else {
            data.m_128365_(id, (Tag)value);
        }
    }

    public static void putIf(CompoundTag data, String id, ListTag value, boolean add) {
        if (value.isEmpty() || !add) {
            data.m_128473_(id);
        } else {
            data.m_128365_(id, (Tag)value);
        }
    }

    public static void add(ListTag data, Tag value) {
        if (value instanceof CompoundTag && ((CompoundTag)value).m_128456_()) {
            return;
        }
        if (value instanceof ListTag && ((ListTag)value).isEmpty()) {
            return;
        }
        data.add((Object)value);
    }

    public static boolean getBoolean(CompoundTag data, String id, boolean defaultValue) {
        return NBTUtils.getByte(data, id, (byte)(defaultValue ? 1 : 0)) == 1;
    }

    public static byte getByte(CompoundTag data, String id, byte defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7063_() : defaultValue;
    }

    public static short getShort(CompoundTag data, String id, short defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7053_() : defaultValue;
    }

    public static int getInt(CompoundTag data, String id, int defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7047_() : defaultValue;
    }

    public static long getLong(CompoundTag data, String id, long defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7046_() : defaultValue;
    }

    public static float getFloat(CompoundTag data, String id, float defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7057_() : defaultValue;
    }

    public static double getDouble(CompoundTag data, String id, double defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof NumericTag ? ((NumericTag)value).m_7061_() : defaultValue;
    }

    public static String getString(CompoundTag data, String id, String defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof StringTag ? value.m_7916_() : defaultValue;
    }

    public static <T extends Enum<T>> T getEnum(CompoundTag data, String id, Class<T> clz) {
        int value = data.m_128451_(id);
        return (T)(value == 0 ? null : ((Enum[])clz.getEnumConstants())[value - 1]);
    }

    public static <T extends Enum<T>> T getEnum(CompoundTag data, String id, Class<T> clz, T defaultValue) {
        int value = data.m_128451_(id);
        return (T)(value == 0 ? defaultValue : ((Enum[])clz.getEnumConstants())[value - 1]);
    }

    public static <T> T getForgeEntry(CompoundTag data, String id, IForgeRegistry<T> registry, T defaultValue) {
        Tag value = data.m_128423_(id);
        return (T)(value instanceof StringTag ? registry.getValue(ResourceLocation.m_135820_((String)value.m_7916_())) : defaultValue);
    }

    public static byte[] getByteArray(CompoundTag data, String id, byte[] defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof ByteArrayTag ? ((ByteArrayTag)value).m_128227_() : defaultValue;
    }

    public static int[] getIntArray(CompoundTag data, String id, int[] defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof IntArrayTag ? ((IntArrayTag)value).m_128648_() : defaultValue;
    }

    public static long[] getLongArray(CompoundTag data, String id, long[] defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof LongArrayTag ? ((LongArrayTag)value).m_128851_() : defaultValue;
    }

    public static void removeIfEmpty(CompoundTag data, String subTag) {
        Tag nbt = data.m_128423_(subTag);
        if (nbt == null) {
            return;
        }
        if (nbt instanceof CompoundTag) {
            CompoundTag subNBT = (CompoundTag)nbt;
            if (subNBT.m_128440_() == 1) {
                NBTUtils.removeIfEmpty(subNBT, (String)subNBT.m_128431_().iterator().next());
            }
            if (subNBT.m_128456_()) {
                data.m_128473_(subTag);
            }
        } else if (nbt instanceof ListTag && ((ListTag)nbt).isEmpty()) {
            data.m_128473_(subTag);
        }
    }

    public static void saveItem(CompoundTag data, String id, ItemStack stack) {
        NBTUtils.putIf(data, id, stack.m_41739_(new CompoundTag()), !stack.m_41619_());
    }

    public static void saveItems(CompoundTag data, NonNullList<ItemStack> inv) {
        NBTUtils.saveItems(data, "items", inv);
    }

    public static void saveItems(CompoundTag data, String id, NonNullList<ItemStack> inv) {
        Object2ObjectSortedMap mappedSlots = CollectionUtils.createLinkedMap(ItemStackStrategy.COUNT_INSTANCE);
        int m = inv.size();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = (ItemStack)inv.get(i);
            if (stack.m_41619_()) continue;
            ByteList items = (ByteList)mappedSlots.get((Object)stack);
            if (items == null) {
                items = new ByteArrayList();
                mappedSlots.put((Object)stack, (Object)items);
            }
            items.add((byte)i);
        }
        ListTag list = new ListTag();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(mappedSlots)) {
            CompoundTag nbt = ((ItemStack)entry.getKey()).m_41739_(new CompoundTag());
            ByteList slots = (ByteList)entry.getValue();
            if (slots.size() > 1) {
                nbt.m_128382_("slots", slots.toByteArray());
            } else {
                if (slots.size() != 1) continue;
                nbt.m_128344_("slot", slots.getByte(0));
            }
            list.add((Object)nbt);
        }
        NBTUtils.put(data, id, list);
    }

    public static ItemStack loadItem(CompoundTag data, String id, ItemStack defaultValue) {
        Tag value = data.m_128423_(id);
        return value instanceof CompoundTag ? ItemStack.m_41712_((CompoundTag)((CompoundTag)value)) : defaultValue;
    }

    public static void loadItems(CompoundTag data, NonNullList<ItemStack> inv) {
        NBTUtils.loadItems(data, "items", inv);
    }

    public static void loadItems(CompoundTag data, String id, NonNullList<ItemStack> inv) {
        ListTag items = data.m_128437_(id, 10);
        int max = inv.size();
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            CompoundTag nbt = (CompoundTag)items.get(i);
            if (nbt.m_128441_("slot")) {
                int slot = nbt.m_128451_("slot");
                if (slot < 0 || slot >= max) continue;
                inv.set(slot, (Object)ItemStack.m_41712_((CompoundTag)nbt));
                continue;
            }
            ItemStack stack = ItemStack.m_41712_((CompoundTag)nbt);
            for (byte slot : nbt.m_128463_("slots")) {
                if (slot < 0 || slot >= max) continue;
                inv.set((int)slot, (Object)stack.m_41777_());
            }
        }
    }

    public static NonNullList<ItemStack> getItems(ItemStack stack) {
        return NBTUtils.getItems(stack, "items");
    }

    public static NonNullList<ItemStack> getItems(ItemStack stack, String tag) {
        NonNullList list = NonNullList.m_122779_();
        CompoundTag data = StackUtil.getNbtData(stack);
        ListTag items = data.m_128437_(tag, 10);
        int m = items.size();
        for (int i = 0; i < m; ++i) {
            ItemStack item;
            CompoundTag nbt = (CompoundTag)items.get(i);
            if (nbt.m_128441_("slot")) {
                item = ItemStack.m_41712_((CompoundTag)nbt);
                if (item.m_41619_()) continue;
                list.add((Object)item);
                continue;
            }
            item = ItemStack.m_41712_((CompoundTag)nbt);
            if (item.m_41619_()) continue;
            int n = nbt.m_128463_("slots").length;
            for (int j = 0; j < n; ++j) {
                list.add((Object)item.m_41777_());
            }
        }
        return list;
    }

    public static List<Component> convertState(BlockState state) {
        ObjectArrayList list = new ObjectArrayList();
        for (Map.Entry entry : state.m_61148_().entrySet()) {
            list.add(Component.m_237113_((String)("[" + ((Property)entry.getKey()).m_61708_() + ": " + Util.m_137453_((Property)((Property)entry.getKey()), entry.getValue()) + "]")));
        }
        return list;
    }

    public static List<Component> convertNBT(CompoundTag nbt) {
        ArrayList<Component> list = new ArrayList<Component>();
        Logger logger = (Logger)LogManager.getLogger(CompoundTag.class);
        Level before = logger.getLevel();
        logger.setLevel(Level.DEBUG);
        int layer = 0;
        for (String s : TextHelper.convertText(new TextComponentTagVisitor("\n", 0).m_178281_((Tag)nbt)).split("\n")) {
            if (StringUtil.m_14408_((String)(s = s.trim()))) continue;
            if (NBTUtils.isCloser(s)) {
                --layer;
            }
            list.add((Component)Component.m_237113_((String)NBTUtils.getBuffer(layer, s)));
            if (!NBTUtils.isOpener(s)) continue;
            ++layer;
        }
        logger.setLevel(before);
        return list;
    }

    static void append(List<Component> list, int layer, List<Component> result) {
        for (Component component : list) {
            result.add((Component)Component.m_237113_((String)NBTUtils.getBuffer(layer, "")).m_7220_((Component)component.m_6879_().m_6270_(component.m_7383_())));
            NBTUtils.append(component.m_7360_(), layer + 1, result);
        }
    }

    static boolean isOpener(String s) {
        return s.startsWith("{") || s.startsWith("[") || s.endsWith("[") || s.endsWith("{");
    }

    static boolean isCloser(String s) {
        return s.startsWith("}") || s.startsWith("]");
    }

    static String getBuffer(int layer, String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < layer; ++i) {
            builder.append("    ");
        }
        builder.append(s);
        return builder.toString();
    }
}

