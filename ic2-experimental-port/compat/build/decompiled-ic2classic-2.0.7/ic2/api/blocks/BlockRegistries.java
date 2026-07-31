/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectCollection
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.api.blocks;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.util.List;
import java.util.Set;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistries {
    static final Object2IntMap<Block> ORE_VALUES = new Object2IntOpenHashMap();
    static final Int2ObjectMap<ObjectSet<Block>> VALUES_TO_ORES = new Int2ObjectOpenHashMap();
    static final List<Runnable> LISTENERS = ObjectLists.synchronize((ObjectList)new ObjectArrayList());

    public static void registerListener(Runnable run) {
        LISTENERS.add(run);
    }

    public static void reload() {
        ORE_VALUES.clear();
        VALUES_TO_ORES.clear();
        LISTENERS.forEach(Runnable::run);
        for (Object2IntMap.Entry entry : ORE_VALUES.object2IntEntrySet()) {
            ObjectSet sets = (ObjectSet)VALUES_TO_ORES.get(entry.getIntValue());
            if (sets == null) {
                sets = new ObjectLinkedOpenHashSet();
                VALUES_TO_ORES.put(entry.getIntValue(), (Object)sets);
            }
            sets.add((Object)((Block)entry.getKey()));
        }
    }

    public static void registerOre(int value, TagKey<Block> tag) {
        for (Block entry : ForgeRegistries.BLOCKS.tags().getTag(tag)) {
            ORE_VALUES.put((Object)entry, value);
        }
    }

    public static void registerOre(int value, Block ... blocks) {
        for (Block block : blocks) {
            ORE_VALUES.put((Object)block, value);
        }
    }

    public static Set<Block> getBlocksForOreValue(int value) {
        return ObjectSets.unmodifiable((ObjectSet)((ObjectSet)VALUES_TO_ORES.getOrDefault(value, (Object)ObjectSets.emptySet())));
    }

    public static int getOreValue(BlockState state) {
        return ORE_VALUES.getInt((Object)state.m_60734_());
    }

    public static int getOreValue(Block block) {
        return ORE_VALUES.getInt((Object)block);
    }

    public static List<Block> getAllValueBlocks() {
        return new ObjectArrayList((ObjectCollection)ORE_VALUES.keySet());
    }
}

