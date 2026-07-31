/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.chars.Char2IntMap
 *  it.unimi.dsi.fastutil.chars.Char2IntMap$Entry
 *  it.unimi.dsi.fastutil.chars.Char2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.platform.recipes.misc;

import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.recipes.registries.IUUMatterRegistry;
import ic2.core.block.machines.recipes.IRecipeList;
import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.platform.recipes.crafting.helpers.RecipeMods;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.recipes.misc.BaseRegistry;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class UUMatterRegistry
extends BaseRegistry<IUUMatterRegistry>
implements IUUMatterRegistry,
IRecipeList,
Consumer<IAdvancedCraftingManager> {
    List<IUUMatterRegistry.UUMatterEntry> entries = CollectionUtils.createList();
    Map<ItemStack, IUUMatterRegistry.UUMatterEntry> mappedEntries = CollectionUtils.createMap(ItemStackStrategy.INSTANCE);

    public UUMatterRegistry(Consumer<IUUMatterRegistry> listener) {
        this.registerListener(listener);
    }

    @Override
    protected void reloadInternals() {
        this.entries.clear();
        this.mappedEntries.clear();
    }

    @Override
    protected void onAfterReload() {
        int m = this.entries.size();
        for (int i = 0; i < m; ++i) {
            IUUMatterRegistry.UUMatterEntry entry = this.entries.get(i);
            this.mappedEntries.putIfAbsent(entry.getStack().m_41777_(), entry);
        }
    }

    @Override
    public void accept(IAdvancedCraftingManager t) {
        this.reload();
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        ObjectList entry = CollectionUtils.createList();
        for (int i = 0; i < size; ++i) {
            ItemStack stack = buffer.m_130267_();
            int uu = buffer.readInt();
            entry.add((IUUMatterRegistry.UUMatterEntry)new IUUMatterRegistry.UUMatterEntry(stack, uu));
        }
        this.entries = entry;
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.entries.size());
        int m = this.entries.size();
        for (int i = 0; i < m; ++i) {
            IUUMatterRegistry.UUMatterEntry entry = this.entries.get(i);
            buffer.m_130055_(entry.getStack());
            buffer.writeInt(entry.getUUNeeded());
        }
    }

    @Override
    public void registerUUEntry(ItemStack output, int milliUU) {
        if (output.m_41619_() || milliUU <= 0) {
            return;
        }
        this.entries.add(new IUUMatterRegistry.UUMatterEntry(output, milliUU));
    }

    @Override
    public void registerUUShape(IUUMatterRegistry.UUMatterBuilder builder) {
        if (!builder.isSpecial()) {
            this.entries.add(builder.createEntry());
        }
        ObjectArrayList obj = new ObjectArrayList();
        if (builder.isHidden()) {
            obj.add(RecipeMods.HIDDEN_RECIPE);
        }
        obj.addAll(ObjectArrayList.wrap((Object[])builder.getShape()));
        for (Char2IntMap.Entry entry : Char2IntMaps.fastIterable((Char2IntMap)builder.getLetters())) {
            obj.add(Character.valueOf(entry.getCharKey()));
            obj.add(new ItemStack((ItemLike)IC2Items.UUMATTER, entry.getIntValue()));
        }
        AdvRecipeRegistry.INSTANCE.addShapedRecipe(builder.getId(), builder.getOutput(), obj.toArray(new Object[obj.size()]));
    }

    @Override
    public List<IUUMatterRegistry.UUMatterEntry> getEntries() {
        return this.entries;
    }

    @Override
    public void removeUUEntry(IUUMatterRegistry.UUMatterEntry entry) {
        this.entries.remove(entry);
    }

    public Map<ItemStack, IUUMatterRegistry.UUMatterEntry> getMappedEntries() {
        return this.mappedEntries;
    }
}

