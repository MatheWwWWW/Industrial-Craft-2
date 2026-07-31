/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipes.registries;

import ic2.api.items.IRepairable;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.registries.IListenableRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IAdvancedCraftingManager
extends IListenableRegistry<IAdvancedCraftingManager> {
    default public void addShapedIC2Recipe(String location, ItemStack output, Object ... args) {
        this.addShapedRecipe(new ResourceLocation("ic2", location), output, args);
    }

    default public void addShapelessIC2Recipe(String location, ItemStack output, Object ... args) {
        this.addShapelessRecipe(new ResourceLocation("ic2", location), output, args);
    }

    default public void addRepairIC2Recipe(String location, IRepairable repair, IInput repairMaterial, int effect, Object ... args) {
        this.addRepairRecipe(new ResourceLocation("ic2", location), repair, repairMaterial, effect, args);
    }

    public void addShapedRecipe(ResourceLocation var1, ItemStack var2, Object ... var3);

    public void addShapelessRecipe(ResourceLocation var1, ItemStack var2, Object ... var3);

    public void addRepairRecipe(ResourceLocation var1, IRepairable var2, IInput var3, int var4, Object ... var5);

    default public void addIC2SmeltingRecipe(String location, ItemStack output, Object input, SmeltingType ... types) {
        this.addIC2SmeltingRecipe(location, output, input, 0.0f, types);
    }

    default public void addIC2SmeltingRecipe(String location, ItemStack output, Object input, float xp, SmeltingType ... types) {
        this.addIC2SmeltingRecipe(location, output, input, xp, 1.0f, types);
    }

    default public void addIC2SmeltingRecipe(String location, ItemStack output, Object input, float xp, float extraTime, SmeltingType ... types) {
        this.addSmeltingRecipe(new ResourceLocation("ic2", location), output, input, xp, extraTime, types);
    }

    default public void addSmeltingRecipe(ResourceLocation location, ItemStack output, Object input, SmeltingType ... types) {
        this.addSmeltingRecipe(location, output, input, 0.0f, types);
    }

    default public void addSmeltingRecipe(ResourceLocation location, ItemStack output, Object input, float xp, SmeltingType ... types) {
        this.addSmeltingRecipe(location, output, input, xp, 1.0f, types);
    }

    public void addSmeltingRecipe(ResourceLocation var1, ItemStack var2, Object var3, float var4, float var5, SmeltingType ... var6);

    public void addStoneCutterRecipe(ResourceLocation var1, ItemStack var2, Object var3);

    default public void addStoneCutterIC2Recipe(String location, ItemStack output, Object input) {
        this.addStoneCutterRecipe(new ResourceLocation("ic2", location), output, input);
    }

    public void addSmithingRecipe(ResourceLocation var1, Object var2, Object var3, ItemStack var4);

    default public void addSmithingIC2Recipe(String location, Object main, Object secondary, ItemStack output) {
        this.addSmithingRecipe(new ResourceLocation("ic2", location), main, secondary, output);
    }

    public static enum SmeltingType {
        FURNACE(200, "furnace"),
        SMOKER(100, "smelter"),
        BLASTFURNACE(100, "blast"),
        CAMPFIRE(100, "camp");

        String suffix;
        int baseTime;

        private SmeltingType(int baseTime, String suffix) {
            this.baseTime = baseTime;
            this.suffix = suffix;
        }

        public int getBaseTime() {
            return this.baseTime;
        }

        public ResourceLocation convert(ResourceLocation location) {
            return new ResourceLocation(location.m_135827_(), location.m_135815_() + "_" + this.suffix);
        }
    }
}

