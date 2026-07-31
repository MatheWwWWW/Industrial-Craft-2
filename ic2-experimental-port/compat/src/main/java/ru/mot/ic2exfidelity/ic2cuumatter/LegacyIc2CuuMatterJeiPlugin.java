package ru.mot.ic2exfidelity.ic2cuumatter;

import ic2.core.ref.Ic2Blocks;
import ic2.core.ref.Ic2Items;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import ru.mot.ic2exfidelity.advancedsolars.LegacyAdvancedSolarsPrerequisites;

@JeiPlugin
public final class LegacyIc2CuuMatterJeiPlugin implements IModPlugin {
    private static final ResourceLocation PLASMAFIER_TEXTURE = new ResourceLocation(
            "ic2", "textures/gui_sprites/blocks/machines/ev/gui_plasmafier.png");

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("ic2cuumatter", "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new IRecipeCategory<?>[] {
                new LegacyMassFabricatorCategory(helper, Ic2Blocks.MASS_FABRICATOR),
                new LegacyPlasmafierCategory(
                        helper, PLASMAFIER_TEXTURE,
                        LegacyAdvancedSolarsPrerequisites.PLASMAFIER.get())
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<LegacyMassFabricatorCategory.Recipe> matterRecipes = new ArrayList<>();
        matterRecipes.add(matter(Items.f_41852_, "0"));
        matterRecipes.add(matter(Ic2Items.SCRAP, "1,000"));
        matterRecipes.add(matter(Ic2Items.SCRAP_BOX, "45,000"));
        Item scrapMetal = ForgeRegistries.ITEMS.getValue(
                new ResourceLocation("ic2", "scrap_metal"));
        if (scrapMetal != null && scrapMetal != Items.f_41852_) {
            matterRecipes.add(matter(scrapMetal, "100,000"));
        }
        registration.addRecipes(LegacyMassFabricatorCategory.TYPE, matterRecipes);
        registration.addRecipes(LegacyPlasmafierCategory.TYPE, List.of(
                new LegacyPlasmafierCategory.Recipe(
                        Ingredient.m_43927_(new ItemStack(Ic2Items.UU_MATTER, 10)),
                        Ingredient.m_43929_(Ic2Items.EMPTY_CELL),
                        new ItemStack(LegacyAdvancedSolarsPrerequisites.PLASMA_CELL.get()))));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(Ic2Blocks.MASS_FABRICATOR),
                LegacyMassFabricatorCategory.TYPE);
        registration.addRecipeCatalyst(
                new ItemStack(LegacyAdvancedSolarsPrerequisites.PLASMAFIER.get()),
                LegacyPlasmafierCategory.TYPE);
    }

    private static LegacyMassFabricatorCategory.Recipe matter(
            Item item, String amplifier) {
        return new LegacyMassFabricatorCategory.Recipe(
                Ingredient.m_43927_(new ItemStack(item)),
                new ItemStack(Ic2Items.UU_MATTER), amplifier);
    }
}
