/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.longs.LongLists
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectCollection
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.block.machines.logic.scanner;

import ic2.core.block.machines.tiles.hv.OreScannerTileEntity;
import ic2.core.inventory.inv.SimpleInventory;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongLists;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Comparator;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class ScannerInventory
extends SimpleInventory {
    OreScannerTileEntity tile;
    Object2ObjectMap<Block, LongList> positionMap = CollectionUtils.createLinkedMap();
    Block[] blocks = new Block[0];
    ItemStack[] items = new ItemStack[0];

    public ScannerInventory(int size, OreScannerTileEntity tile) {
        super(size);
        this.tile = tile;
    }

    @Override
    public void load(CompoundTag nbt) {
        for (CompoundTag entry : NBTListWrapper.wrap(nbt.m_128437_("results", 10), CompoundTag.class)) {
            Block block = (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entry.m_128461_("name")));
            if (block == Blocks.f_50016_) continue;
            this.positionMap.put((Object)block, (Object)LongArrayList.wrap((long[])entry.m_128467_("positions")));
        }
        this.populate();
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.positionMap)) {
            CompoundTag data = new CompoundTag();
            data.m_128359_("name", ForgeRegistries.BLOCKS.getKey((Object)((Block)entry.getKey())).toString());
            data.m_128388_("positions", ((LongList)entry.getValue()).toLongArray());
            list.add((Object)data);
        }
        nbt.m_128365_("results", (Tag)list);
        return nbt;
    }

    public void clearData() {
        this.tile.offset = 0;
        this.items = new ItemStack[0];
        this.blocks = new Block[0];
        this.positionMap.clear();
        this.updateSelection();
    }

    public void populate() {
        this.tile.offset = 0;
        ObjectArrayList blockResults = new ObjectArrayList((ObjectCollection)this.positionMap.keySet());
        blockResults.sort(Comparator.comparingInt(T -> ((LongList)this.positionMap.get(T)).size()).reversed());
        ObjectList results = CollectionUtils.createList();
        for (int i = 0; i < blockResults.size(); ++i) {
            Block entry = (Block)blockResults.get(i);
            ItemStack stack = new ItemStack((ItemLike)entry.m_5456_());
            if (stack.m_41619_()) {
                blockResults.remove(i--);
                continue;
            }
            stack.m_41784_().m_128405_("ore_scanner_count", ((LongList)this.positionMap.get((Object)entry)).size());
            StackUtil.addTooltip(stack, ChatFormatting.RESET + ChatFormatting.AQUA + "Click to toggle Highlight");
            results.add((ItemStack)stack);
        }
        this.items = (ItemStack[])results.toArray(ItemStack[]::new);
        this.blocks = (Block[])blockResults.toArray(Block[]::new);
        this.updateSelection();
    }

    public void updateSelection() {
        int m = this.getSlotCount();
        for (int i = 0; i < m; ++i) {
            int index = i + this.tile.offset;
            if (index >= this.items.length) {
                this.setStackInSlot(i, ItemStack.f_41583_);
                continue;
            }
            this.setStackInSlot(i, this.items[index].m_41777_());
        }
    }

    public LongList getBlocks(int index) {
        return (index += this.tile.offset) < 0 || index >= this.blocks.length ? LongLists.EMPTY_LIST : (LongList)this.positionMap.get((Object)this.blocks[index]);
    }

    public Block getBlock(int index) {
        return (index += this.tile.offset) < 0 || index >= this.blocks.length ? Blocks.f_50016_ : this.blocks[index];
    }

    public int getColor(int index) {
        return (index += this.tile.offset) < 0 || index >= this.blocks.length ? 0 : this.blocks[index].m_49966_().m_60767_().m_76339_().f_76396_ | 0x96000000;
    }

    public Object2ObjectMap<Block, LongList> getPositionMap() {
        return this.positionMap;
    }

    public int resultSize() {
        return this.items.length;
    }

    public NonNullList<ItemStack> getFoundItems() {
        NonNullList list = NonNullList.m_122779_();
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.positionMap)) {
            ItemStack stack = new ItemStack((ItemLike)((Block)entry.getKey()).m_5456_());
            if (stack.m_41619_()) continue;
            stack.m_41764_(((LongList)entry.getValue()).size());
            list.add((Object)stack);
        }
        return list;
    }
}

