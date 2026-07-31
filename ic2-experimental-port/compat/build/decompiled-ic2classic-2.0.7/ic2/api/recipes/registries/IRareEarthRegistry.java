/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IListenableRegistry;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IRareEarthRegistry
extends IListenableRegistry<IRareEarthRegistry> {
    public void registerOutput(ResourceLocation var1, ItemStack var2);

    public void registerInput(ResourceLocation var1, float var2, ItemStack ... var3);

    public RareEntry getOutputFor(ItemStack var1);

    public ItemStack getOutputFor(ResourceLocation var1);

    public RareEntry removeInput(ItemStack var1);

    public void removeOutput(ItemStack var1);

    public void remove(ResourceLocation var1);

    public List<RareEntry> getAllRecipes();

    public static class RareEntry {
        ResourceLocation id;
        ItemStack input;
        float value;
        ItemStack output;

        public RareEntry(ResourceLocation id, ItemStack input, float value, ItemStack output) {
            this.id = id;
            this.input = input;
            this.value = value;
            this.output = output;
        }

        public ResourceLocation getID() {
            return this.id;
        }

        public ItemStack getInput() {
            return this.input;
        }

        public ItemStack getOutput() {
            return this.output;
        }

        public float getValue() {
            return this.value;
        }

        public boolean matches(ResourceLocation location) {
            return this.id.equals((Object)location);
        }
    }
}

