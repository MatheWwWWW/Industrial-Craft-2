/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.IModPlugin
 *  mezz.jei.api.JeiPlugin
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  mezz.jei.api.registration.IRecipeCatalystRegistration
 *  mezz.jei.api.registration.IRecipeCategoryRegistration
 *  mezz.jei.api.registration.IRecipeRegistration
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 */
package ic2.integration.jei;

import ic2.core.IC2;
import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2RecipeTypes;
import ic2.integration.jei.recipe.machine.DynamicCategory;
import ic2.integration.jei.recipe.machine.IORecipeWrapper;
import ic2.integration.jei.recipe.machine.MetalFormerCategory;
import java.util.List;
import java.util.function.BiConsumer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ItemLike;

@JeiPlugin
public class Ic2JeiPlugin
implements IModPlugin {
    private final RecipeType<IORecipeWrapper> BLAST_FURNACE = RecipeType.create((String)"ic2", (String)"blast_furnace", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> BLOCK_CUTTER = RecipeType.create((String)"ic2", (String)"block_cutter", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> CENTRIFUGE = RecipeType.create((String)"ic2", (String)"centrifuge", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> COMPRESSOR = RecipeType.create((String)"ic2", (String)"compressor", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> EXTRACTOR = RecipeType.create((String)"ic2", (String)"extractor", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> MACERATOR = RecipeType.create((String)"ic2", (String)"macerator", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> METAL_FORMER_EXTRUDING = RecipeType.create((String)"ic2", (String)"metal_former_extruding", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> METAL_FORMER_ROLLING = RecipeType.create((String)"ic2", (String)"metal_former_rolling", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> METAL_FORMER_CUTTING = RecipeType.create((String)"ic2", (String)"metal_former_cutting", IORecipeWrapper.class);
    private final RecipeType<IORecipeWrapper> ORE_WASHER = RecipeType.create((String)"ic2", (String)"ore_washer", IORecipeWrapper.class);

    public ResourceLocation getPluginUid() {
        return IC2.getIdentifier("plugin");
    }

    public void registerCategories(IRecipeCategoryRegistration iRecipeCategoryRegistration) {
        IGuiHelper iGuiHelper = iRecipeCategoryRegistration.getJeiHelpers().getGuiHelper();
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.BLAST_FURNACE, this.BLAST_FURNACE, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.BLOCK_CUTTER, this.BLOCK_CUTTER, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.CENTRIFUGE, this.CENTRIFUGE, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.COMPRESSOR, this.COMPRESSOR, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.EXTRACTOR, this.EXTRACTOR, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.MACERATOR, this.MACERATOR, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new MetalFormerCategory(this.METAL_FORMER_EXTRUDING, 0, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new MetalFormerCategory(this.METAL_FORMER_ROLLING, 1, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new MetalFormerCategory(this.METAL_FORMER_CUTTING, 2, iGuiHelper)});
        iRecipeCategoryRegistration.addRecipeCategories(new IRecipeCategory[]{new DynamicCategory(Ic2Blocks.ORE_WASHING_PLANT, this.ORE_WASHER, iGuiHelper)});
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration iRecipeCatalystRegistration) {
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.BLAST_FURNACE), new RecipeType[]{this.BLAST_FURNACE});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.BLOCK_CUTTER), new RecipeType[]{this.BLOCK_CUTTER});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.CENTRIFUGE), new RecipeType[]{this.CENTRIFUGE});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.COMPRESSOR), new RecipeType[]{this.COMPRESSOR});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.EXTRACTOR), new RecipeType[]{this.EXTRACTOR});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.MACERATOR), new RecipeType[]{this.MACERATOR});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.METAL_FORMER), new RecipeType[]{this.METAL_FORMER_CUTTING, this.METAL_FORMER_EXTRUDING, this.METAL_FORMER_ROLLING});
        iRecipeCatalystRegistration.addRecipeCatalyst(new ItemStack((ItemLike)Ic2Blocks.ORE_WASHING_PLANT), new RecipeType[]{this.ORE_WASHER});
    }

    public void registerRecipes(IRecipeRegistration iRecipeRegistration) {
        RecipeManager recipeManager = Minecraft.m_91087_().f_91073_.m_7465_();
        BiConsumer<RecipeType, net.minecraft.world.item.crafting.RecipeType> biConsumer = (recipeType, recipeType2) -> {
            List<IORecipeWrapper> list = recipeManager.m_44013_(recipeType2).stream().map(recipeHolder -> new IORecipeWrapper(recipeHolder.recipe())).toList();
            iRecipeRegistration.addRecipes(recipeType, list);
        };
        biConsumer.accept(this.BLAST_FURNACE, Ic2RecipeTypes.BLAST_FURNACE);
        biConsumer.accept(this.BLOCK_CUTTER, Ic2RecipeTypes.BLOCK_CUTTER);
        biConsumer.accept(this.CENTRIFUGE, Ic2RecipeTypes.CENTRIFUGE);
        biConsumer.accept(this.COMPRESSOR, Ic2RecipeTypes.COMPRESSOR);
        biConsumer.accept(this.EXTRACTOR, Ic2RecipeTypes.EXTRACTOR);
        biConsumer.accept(this.MACERATOR, Ic2RecipeTypes.MACERATOR);
        biConsumer.accept(this.METAL_FORMER_CUTTING, Ic2RecipeTypes.METAL_FORMER_CUTTING);
        biConsumer.accept(this.METAL_FORMER_EXTRUDING, Ic2RecipeTypes.METAL_FORMER_EXTRUDING);
        biConsumer.accept(this.METAL_FORMER_ROLLING, Ic2RecipeTypes.METAL_FORMER_ROLLING);
        biConsumer.accept(this.ORE_WASHER, Ic2RecipeTypes.ORE_WASHER);
    }
}

