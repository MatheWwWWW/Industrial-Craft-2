/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.registries.IPotionBrewRegistry;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionBrewRegistry
implements IPotionBrewRegistry,
IRecipeList {
    List<Consumer<IPotionBrewRegistry>> listeners = ObjectLists.synchronize(CollectionUtils.createList());
    Map<Item, IPotionBrewRegistry.PotionContainer> items = CollectionUtils.createLinkedMap();
    Map<Item, Map<MobEffect, MobEffect>> effects = CollectionUtils.createLinkedMap();
    Map<MobEffect, String> names = CollectionUtils.createLinkedMap();

    public PotionBrewRegistry(Consumer<IPotionBrewRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    public void registerListener(Consumer<IPotionBrewRegistry> listener) {
        if (listener == null) {
            return;
        }
        this.listeners.add(listener);
    }

    public void reload() {
        this.items.clear();
        this.effects.clear();
        int m = this.listeners.size();
        for (int i = 0; i < m; ++i) {
            this.listeners.get(i).accept(this);
        }
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.items.size());
        for (Map.Entry<Item, IPotionBrewRegistry.PotionContainer> entry : this.items.entrySet()) {
            IPotionBrewRegistry.PotionContainer item = entry.getValue();
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)entry.getKey());
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)item.getOutput());
            buffer.m_130130_(item.getFluidUsage());
            buffer.writeFloat(item.getDurationEffectiveness());
        }
        buffer.m_130130_(this.effects.size());
        for (Map.Entry<Item, Object> entry : this.effects.entrySet()) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.ITEMS, (Object)entry.getKey());
            Map map = (Map)entry.getValue();
            buffer.m_130130_(map.size());
            for (Map.Entry entry2 : map.entrySet()) {
                buffer.writeBoolean(entry2.getKey() != null);
                if (entry2.getKey() != null) {
                    buffer.writeRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS, (Object)((MobEffect)entry2.getKey()));
                }
                buffer.writeRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS, (Object)((MobEffect)entry2.getValue()));
            }
        }
        buffer.m_130130_(this.names.size());
        for (Map.Entry<Item, Object> entry : this.names.entrySet()) {
            buffer.writeRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS, (Object)((MobEffect)entry.getKey()));
            buffer.m_130070_((String)entry.getValue());
        }
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        int i;
        Object2ObjectSortedMap<Item, IPotionBrewRegistry.PotionContainer> newItems = CollectionUtils.createLinkedMap();
        Object2ObjectSortedMap newEffects = CollectionUtils.createLinkedMap();
        Object2ObjectSortedMap<MobEffect, String> newNames = CollectionUtils.createLinkedMap();
        int size = buffer.m_130242_();
        for (i = 0; i < size; ++i) {
            Item container = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            Item item = (Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS);
            newItems.put(container, new IPotionBrewRegistry.PotionContainer(item, buffer.m_130242_(), buffer.readFloat()));
        }
        size = buffer.m_130242_();
        for (i = 0; i < size; ++i) {
            Object2ObjectSortedMap<MobEffect, MobEffect> effects = CollectionUtils.createLinkedMap();
            newEffects.put((Item)buffer.readRegistryIdUnsafe(ForgeRegistries.ITEMS), effects);
            int subSize = buffer.m_130242_();
            for (int j = 0; j < subSize; ++j) {
                MobEffect key = buffer.readBoolean() ? (MobEffect)buffer.readRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS) : null;
                effects.put(key, (MobEffect)buffer.readRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS));
            }
        }
        size = buffer.m_130242_();
        for (i = 0; i < size; ++i) {
            newNames.put((MobEffect)buffer.readRegistryIdUnsafe(ForgeRegistries.MOB_EFFECTS), buffer.m_130277_());
        }
        this.items = newItems;
        this.effects = newEffects;
        this.names = newNames;
    }

    @Override
    public void registerBrew(Item item, MobEffect fromEffect, MobEffect toEffect) {
        Map<MobEffect, MobEffect> map = this.effects.get(item);
        if (map == null) {
            map = CollectionUtils.createMap();
            this.effects.put(item, map);
        }
        map.put(fromEffect, toEffect);
    }

    @Override
    public void removeBrew(Item item) {
        this.effects.remove(item);
    }

    @Override
    public MobEffect getEffect(Item item, MobEffect original) {
        Map<MobEffect, MobEffect> map = this.effects.get(item);
        return map == null ? null : map.get(original);
    }

    @Override
    public Map<Item, Map<MobEffect, MobEffect>> getEffects() {
        return new Object2ObjectLinkedOpenHashMap(this.effects);
    }

    @Override
    public List<Item> getRequiredItems(MobEffect desiredEffect) {
        ObjectList list = CollectionUtils.createList();
        while (desiredEffect != null) {
            boolean found = false;
            for (Map.Entry<Item, Map<MobEffect, MobEffect>> entry : this.effects.entrySet()) {
                for (Map.Entry<MobEffect, MobEffect> subEntry : entry.getValue().entrySet()) {
                    if (desiredEffect != subEntry.getValue()) continue;
                    desiredEffect = subEntry.getKey();
                    found = true;
                    break;
                }
                if (!found) continue;
                list.add((Item)entry.getKey());
                break;
            }
            if (found) continue;
            return ObjectLists.emptyList();
        }
        return list;
    }

    @Override
    public void registerName(MobEffect effect, String name) {
        if (effect == null || name == null || name.isEmpty()) {
            return;
        }
        this.names.put(effect, name);
    }

    @Override
    public String getName(MobEffect effect) {
        return this.names.get(effect);
    }

    @Override
    public void registerPotionContainer(Item input, IPotionBrewRegistry.PotionContainer output) {
        if (input == null || output == null || input == Items.f_41852_) {
            return;
        }
        this.items.put(input, output);
    }

    @Override
    public IPotionBrewRegistry.PotionContainer getPotionContainer(Item input) {
        return this.items.get(input);
    }

    @Override
    public Map<Item, IPotionBrewRegistry.PotionContainer> getContainers() {
        return new Object2ObjectLinkedOpenHashMap(this.items);
    }
}

