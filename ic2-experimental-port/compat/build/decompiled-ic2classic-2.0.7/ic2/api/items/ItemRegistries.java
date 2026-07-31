/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.ListMultimap
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Multimaps
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMaps
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntComparators
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntLists
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.ShieldItem
 *  net.minecraft.world.level.ItemLike
 */
package ic2.api.items;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import ic2.api.items.IBoxableItem;
import ic2.api.items.ICoinItem;
import ic2.api.items.armor.IMetalArmor;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.ItemLike;

public final class ItemRegistries {
    static final Int2ObjectMap<Item> COIN_MAP = Int2ObjectMaps.synchronize((Int2ObjectMap)new Int2ObjectLinkedOpenHashMap());
    static final IntList SORTED_COINS = IntLists.synchronize((IntList)new IntArrayList());
    static final Map<Item, IMetalArmor> METAL_ARMORS = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());
    static final Multimap<Item, IBoxableItem> BOX_ITEMS = Multimaps.synchronizedListMultimap((ListMultimap)ArrayListMultimap.create());
    static final Map<ShieldItem, Set<String>> SHIELD_BLOCKABLE = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectLinkedOpenHashMap());
    static final Set<String> SHIELD_BLOCKABLE_GLOBAL = ObjectSets.synchronize((ObjectSet)new ObjectLinkedOpenHashSet());

    public static void registerMetalArmor(IMetalArmor armor, Item ... items) {
        for (Item item : items) {
            METAL_ARMORS.put(item, armor);
        }
    }

    public static boolean isMetalArmor(Player player, EquipmentSlot slot) {
        ItemStack stack = player.m_6844_(slot);
        if (stack.m_41619_()) {
            return false;
        }
        IMetalArmor armor = METAL_ARMORS.get(stack.m_41720_());
        if (armor == null && stack.m_41720_() instanceof IMetalArmor) {
            armor = (IMetalArmor)stack.m_41720_();
        }
        return armor != null && armor.isMetalArmor(stack, player, slot);
    }

    public static void registerBoxableItems(IBoxableItem box, Item ... items) {
        for (Item item : items) {
            BOX_ITEMS.put((Object)item, (Object)box);
        }
    }

    public static boolean isBoxable(ItemStack stack) {
        Item item = stack.m_41720_();
        for (IBoxableItem box : BOX_ITEMS.get((Object)item)) {
            if (!box.canStoreIntoBox(stack)) continue;
            return true;
        }
        return item instanceof IBoxableItem && ((IBoxableItem)item).canStoreIntoBox(stack);
    }

    public static void registerCoin(Item item) {
        int value;
        if (item instanceof ICoinItem && (value = ((ICoinItem)item).getMoneyValue(new ItemStack((ItemLike)item))) > 0) {
            COIN_MAP.put(value, (Object)item);
            SORTED_COINS.clear();
            SORTED_COINS.addAll((IntCollection)COIN_MAP.keySet());
            SORTED_COINS.sort(IntComparators.OPPOSITE_COMPARATOR);
        }
    }

    public static NonNullList<ItemStack> generateCoins(int coinValue, NonNullList<ItemStack> result, boolean limit) {
        int m = SORTED_COINS.size();
        for (int i = 0; i < m; ++i) {
            Item item;
            int value = SORTED_COINS.getInt(i);
            if (coinValue < value || (item = (Item)COIN_MAP.get(value)) == null) continue;
            int amount = coinValue / value;
            if (limit && amount < 7 && i + 1 < m) continue;
            coinValue -= amount * value;
            while (amount > 0) {
                ItemStack stack = new ItemStack((ItemLike)item);
                stack.m_41764_(Math.min(stack.m_41741_(), amount));
                amount -= stack.m_41613_();
                result.add((Object)stack);
            }
            if (coinValue > 0) continue;
            return result;
        }
        return result;
    }

    public static void registerBlockableDamageSource(ShieldItem shield, DamageSource source) {
        ObjectLinkedOpenHashSet set = SHIELD_BLOCKABLE.get(shield);
        if (set == null) {
            set = new ObjectLinkedOpenHashSet();
            SHIELD_BLOCKABLE.put(shield, (Set<String>)set);
        }
        set.add(source.m_19385_());
    }

    public static void registerBlockableDamageSource(DamageSource source) {
        SHIELD_BLOCKABLE_GLOBAL.add(source.m_19385_());
    }

    public static boolean isDamageSourceBlockable(ShieldItem shield, DamageSource source) {
        return SHIELD_BLOCKABLE_GLOBAL.contains(source.m_19385_()) || SHIELD_BLOCKABLE.getOrDefault(shield, Collections.emptySet()).contains(source.m_19385_());
    }
}

