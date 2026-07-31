/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.ingredient.IRecipeSlotsView
 *  mezz.jei.api.helpers.IGuiHelper
 *  mezz.jei.api.ingredients.IIngredientType
 *  mezz.jei.api.recipe.IFocusGroup
 *  mezz.jei.api.recipe.RecipeIngredientRole
 *  mezz.jei.api.recipe.RecipeType
 *  mezz.jei.api.recipe.category.IRecipeCategory
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.jeiplugin.core.recipes.categories.brew;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.time.Duration;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class RumBrewCategory
implements IRecipeCategory<CustomBrew> {
    RecipeType<CustomBrew> id;
    IDrawable background;
    IDrawable icon;

    public RumBrewCategory(IGuiHelper helper, RecipeType<CustomBrew> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack(provider));
        this.background = helper.createDrawable(texture, 20, 26, 120, 40);
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public RecipeType<CustomBrew> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return Component.m_237113_((String)"Rum Brewing");
    }

    public void draw(CustomBrew recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.m_91087_().f_91062_;
        String s = DurationFormatUtils.formatDuration((long)Duration.ofSeconds(recipe.calculateTimeRequired() / 20).toMillis(), (String)"HH:mm:ss");
        font.m_92883_(stack, "Brew Time: " + s, 3.0f, 32.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder builder, CustomBrew rum, IFocusGroup group) {
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 8).addItemStack(rum.input);
        builder.addSlot(RecipeIngredientRole.CATALYST, 43, 8).addItemStack(rum.brewer);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 8).addItemStack(rum.output);
    }

    public static class CustomBrew {
        ItemStack input;
        ItemStack brewer;
        ItemStack output;
        int amount;

        public CustomBrew(Item input, int amount, Item brewer, ItemStack output) {
            this.amount = amount;
            this.input = new ItemStack((ItemLike)input, amount);
            this.brewer = new ItemStack((ItemLike)brewer);
            this.output = output.m_41777_();
            this.output.m_41764_(amount);
        }

        public int calculateTimeRequired() {
            return (int)(1200.0 * (double)Math.max(1, this.amount) * Math.pow(0.95, Math.max(0, this.amount - 1)));
        }

        public static List<CustomBrew> create() {
            ObjectList list = CollectionUtils.createList();
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 1, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.MUG_RUM)));
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 16, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.MUG_RUM)));
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 32, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.MUG_RUM)));
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 1, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.GLASS_RUM)));
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 16, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.GLASS_RUM)));
            list.add((CustomBrew)new CustomBrew(Items.f_41909_, 32, IC2Blocks.BARREL.m_5456_(), CustomBrew.create(IC2Items.GLASS_RUM)));
            return list;
        }

        private static ItemStack create(Item item) {
            ItemStack stack = new ItemStack((ItemLike)item);
            stack.m_41784_().m_128344_("progress", (byte)100);
            return stack;
        }
    }
}

