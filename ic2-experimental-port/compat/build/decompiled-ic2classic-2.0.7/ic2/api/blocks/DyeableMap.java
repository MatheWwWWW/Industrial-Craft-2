/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.block.Block
 */
package ic2.api.blocks;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import java.util.Map;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

public class DyeableMap {
    Map<Block, DyeColor> blockToColor = new Object2ObjectLinkedOpenHashMap();
    Map<DyeColor, Block> colorToBlock = new Object2ObjectLinkedOpenHashMap();
    ObjectSet<Block> blocks = new ObjectLinkedOpenHashSet();

    public void addBlock(Block block, DyeColor color) {
        this.blocks.add((Object)block);
        this.blockToColor.put(block, color);
        this.colorToBlock.put(color, block);
    }

    public void addBlocks(Map<Block, DyeColor> maps) {
        this.blocks.addAll(maps.keySet());
        this.blockToColor.putAll(maps);
        for (Map.Entry<Block, DyeColor> entry : maps.entrySet()) {
            this.colorToBlock.put(entry.getValue(), entry.getKey());
        }
    }

    public void addReverseBlocks(Map<DyeColor, Block> maps) {
        this.blocks.addAll(maps.values());
        this.colorToBlock.putAll(maps);
        for (Map.Entry<DyeColor, Block> entry : maps.entrySet()) {
            this.blockToColor.put(entry.getValue(), entry.getKey());
        }
    }

    public Block getBlock(DyeColor color) {
        return this.colorToBlock.get(color);
    }

    public Block getBlock(DyeColor color, Block defaultValue) {
        return this.colorToBlock.getOrDefault(color, defaultValue);
    }

    public DyeColor getColor(Block block) {
        return this.blockToColor.get(block);
    }

    public ObjectSet<Block> getBlocks() {
        return ObjectSets.unmodifiable(this.blocks);
    }

    public Block[] getBlockArray() {
        return (Block[])this.blocks.toArray(Block[]::new);
    }

    public boolean contains(Block block) {
        return this.blocks.contains((Object)block);
    }
}

