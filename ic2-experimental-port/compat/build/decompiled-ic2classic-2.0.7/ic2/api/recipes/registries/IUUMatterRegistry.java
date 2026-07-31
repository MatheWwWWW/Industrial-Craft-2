/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.chars.Char2IntMap
 *  it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import it.unimi.dsi.fastutil.chars.Char2IntMap;
import it.unimi.dsi.fastutil.chars.Char2IntOpenHashMap;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public interface IUUMatterRegistry
extends IListenableRegistry<IUUMatterRegistry> {
    public void registerUUShape(UUMatterBuilder var1);

    public void registerUUEntry(ItemStack var1, int var2);

    public List<UUMatterEntry> getEntries();

    public void removeUUEntry(UUMatterEntry var1);

    public static class UUMatterBuilder {
        ItemStack output;
        ResourceLocation id;
        String[] shape;
        boolean special = false;
        boolean hidden = true;
        Char2IntMap letters = new Char2IntOpenHashMap();

        public UUMatterBuilder(String name) {
            this("ic2", name);
        }

        public UUMatterBuilder(String mod, String name) {
            this(new ResourceLocation(mod, name));
        }

        public UUMatterBuilder(ResourceLocation id) {
            this.id = id;
            this.letters.put('M', 1);
        }

        public UUMatterBuilder setSpecial() {
            this.special = true;
            return this;
        }

        public UUMatterBuilder setVisible() {
            this.hidden = false;
            return this;
        }

        public UUMatterBuilder addLetters(char letter, int uuMatterItems) {
            this.letters.put(letter, uuMatterItems);
            return this;
        }

        public UUMatterBuilder setOutput(ItemLike provider, int amount) {
            this.output = new ItemStack(provider, amount);
            return this;
        }

        public UUMatterBuilder setOutput(ItemStack output) {
            this.output = output.m_41777_();
            return this;
        }

        public UUMatterBuilder setShape(String ... shapes) {
            int length = shapes[0].length();
            for (int i = 1; i < shapes.length; ++i) {
                if (shapes[i].length() == length) continue;
                throw new IllegalStateException("Each row needs to be the same length");
            }
            this.shape = shapes;
            return this;
        }

        public UUMatterEntry createEntry() {
            int totalUUMatter = 0;
            for (int i = 0; i < this.shape.length; ++i) {
                String s = this.shape[i];
                int m = s.length();
                for (int j = 0; j < m; ++j) {
                    totalUUMatter += this.letters.get(s.charAt(j)) * 1000;
                }
            }
            return new UUMatterEntry(this.output, totalUUMatter /= this.output.m_41613_());
        }

        public ItemStack getOutput() {
            return this.output.m_41777_();
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public String[] getShape() {
            return this.shape;
        }

        public boolean isSpecial() {
            return this.special;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        public Char2IntMap getLetters() {
            return this.letters;
        }
    }

    public static class UUMatterEntry {
        final ItemStack stack;
        final int uuNeeded;

        public UUMatterEntry(ItemStack stack, int uuNeeded) {
            this.stack = stack.m_41777_();
            this.stack.m_41764_(1);
            this.uuNeeded = uuNeeded;
        }

        public ItemStack getStack() {
            return this.stack.m_41777_();
        }

        public int getUUNeeded() {
            return this.uuNeeded;
        }
    }
}

