/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.wiki.recipes.providers;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.item.ItemStack;

public interface IRecipeProvider {
    public void reloadRecipes();

    public List<IRecipeRenderer> getFromInput(ItemStack var1, boolean var2);

    public List<IRecipeRenderer> getFromOutput(ItemStack var1, boolean var2);

    default public <K, V> void addToSet(K key, V value, Map<K, Set<V>> map) {
        Set<V> set = map.get(key);
        if (set == null) {
            set = CollectionUtils.createLinkedSet();
            map.put(key, set);
        }
        set.add(value);
    }

    default public <K, V> void addToList(K key, V value, Map<K, List<V>> map) {
        List<V> list = map.get(key);
        if (list == null) {
            list = CollectionUtils.createList();
            map.put(key, list);
        }
        list.add(value);
    }
}

