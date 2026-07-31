/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  mezz.jei.api.constants.VanillaTypes
 *  mezz.jei.api.gui.builder.IRecipeLayoutBuilder
 *  mezz.jei.api.gui.drawable.IDrawable
 *  mezz.jei.api.gui.drawable.IDrawableAnimated$StartDirection
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
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.food.FoodProperties
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.jeiplugin.core.recipes.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.RecipeRegistry;
import ic2.api.recipes.ingridients.inputs.IInput;
import ic2.api.recipes.ingridients.inputs.ItemInput;
import ic2.api.recipes.registries.ICannerRecipeRegistry;
import ic2.core.platform.recipes.misc.CannerRegistry;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class CannerCategory
implements IRecipeCategory<CannerRecipe> {
    RecipeType<CannerRecipe> id;
    ItemStack stack;
    IDrawable background;
    IDrawable progress;
    IDrawable charge;
    IDrawable icon;

    public CannerCategory(IGuiHelper helper, RecipeType<CannerRecipe> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.background = helper.createDrawable(texture, 50, 15, 90, 63);
        this.progress = helper.drawableBuilder(texture, 176, 14, 23, 16).buildAnimated(150, IDrawableAnimated.StartDirection.LEFT, false);
        this.charge = helper.drawableBuilder(texture, 176, 0, 13, 14).buildAnimated(500, IDrawableAnimated.StartDirection.TOP, true);
    }

    public RecipeType<CannerRecipe> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return this.stack.m_41786_();
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void draw(CannerRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.progress.draw(stack, 24, 20);
        Font font = Minecraft.m_91087_().f_91062_;
        if (recipe.isRepair()) {
            MutableComponent comp = Component.m_237113_((String)("+" + recipe.getRepair().m_14419_()));
            font.m_92877_(stack, comp.m_7532_(), (float)(90 - font.m_92724_(comp.m_7532_())), 2.0f, 0x404040);
        } else if (recipe.isFuel()) {
            ICannerRecipeRegistry.FuelValue value = recipe.getFuels();
            MutableComponent comp = Component.m_237113_((String)("+" + (Serializable)(value.isMultiplier() ? ItemStack.f_41584_.format(value.getFuelMultiplier() * 100.0f) + "%" : Integer.valueOf(value.getFuelValue()))));
            font.m_92877_(stack, comp.m_7532_(), (float)(90 - font.m_92724_(comp.m_7532_())), 2.0f, 0x404040);
        }
    }

    public void setRecipe(IRecipeLayoutBuilder layout, CannerRecipe recipe, IFocusGroup focus) {
        if (recipe.isFilling()) {
            ObjectList extra = CollectionUtils.createList();
            for (ItemStack out : ((IInput)recipe.getFilling().m_14418_()).getComponents()) {
                if (!out.hasCraftingRemainingItem()) continue;
                extra.add((ItemStack)out.getCraftingRemainingItem());
            }
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 38).addItemStack(recipe.getContainer());
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 2).addItemStacks(((IInput)recipe.getFilling().m_14418_()).getComponents());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 20).addItemStack((ItemStack)recipe.getFilling().m_14419_());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 44).addItemStacks(extra);
        } else if (recipe.isRepair()) {
            ObjectList extra = CollectionUtils.createList();
            for (ItemStack out : ((IInput)recipe.getRepair().m_14418_()).getComponents()) {
                if (!out.hasCraftingRemainingItem()) continue;
                extra.add((ItemStack)out.getCraftingRemainingItem());
            }
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 38).addItemStack(recipe.getContainer());
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 2).addItemStacks(((IInput)recipe.getRepair().m_14418_()).getComponents());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 20).addItemStack(recipe.getContainer());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 44).addItemStacks(extra);
        } else if (recipe.isFuel()) {
            ICannerRecipeRegistry.FuelValue result = recipe.getFuels();
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 38).addItemStack(recipe.getContainer());
            layout.addSlot(RecipeIngredientRole.INPUT, 19, 2).addItemStack(result.getStack());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 20).addItemStack(recipe.getContainer());
            layout.addSlot(RecipeIngredientRole.OUTPUT, 69, 44).addItemStack(result.getStack().getCraftingRemainingItem());
        }
    }

    public static class CannerRecipe {
        ItemStack container;
        Object recipe;
        int type;

        public CannerRecipe(ItemStack container, Object recipe, int type) {
            this.container = container;
            this.recipe = recipe;
            this.type = type;
        }

        public boolean isRepair() {
            return this.type == 0;
        }

        public boolean isFuel() {
            return this.type == 1;
        }

        public boolean isFilling() {
            return this.type == 2;
        }

        public ItemStack getContainer() {
            return this.container;
        }

        public ICannerRecipeRegistry.FuelValue getFuels() {
            return (ICannerRecipeRegistry.FuelValue)this.recipe;
        }

        public Tuple<IInput, Integer> getRepair() {
            return (Tuple)this.recipe;
        }

        public Tuple<IInput, ItemStack> getFilling() {
            return (Tuple)this.recipe;
        }

        public static List<CannerRecipe> createRecipes(GlobalRecipes recipe) {
            ObjectArrayList result = new ObjectArrayList();
            CannerRegistry registry = recipe.canner;
            for (Map.Entry<Item, List<Tuple<IInput, Integer>>> entry : registry.getRepairItems().entrySet()) {
                ItemStack stack = new ItemStack((ItemLike)entry.getKey());
                for (Tuple<IInput, Integer> subEntry : entry.getValue()) {
                    result.add(new CannerRecipe(stack, subEntry, 0));
                }
            }
            result.add(new CannerRecipe(new ItemStack((ItemLike)IC2Items.ELECTRIC_SPRAYER), new Tuple((Object)new ItemInput(IC2Items.CF_PELLET), (Object)26), 0));
            result.add(new CannerRecipe(new ItemStack((ItemLike)IC2Items.CF_PACK), new Tuple((Object)new ItemInput(IC2Items.CF_PELLET), (Object)26), 0));
            for (ICannerRecipeRegistry.FuelValue fuelValue : registry.getFuels()) {
                result.add(new CannerRecipe(new ItemStack((ItemLike)IC2Items.FUEL_CAN), fuelValue, 1));
            }
            for (Map.Entry entry : registry.getFillables().entrySet()) {
                for (Tuple subEntry : (List)entry.getValue()) {
                    result.add(new CannerRecipe((ItemStack)entry.getKey(), subEntry, 2));
                }
            }
            for (Item item : ForgeRegistries.ITEMS) {
                int amount;
                FoodProperties prop;
                if (!item.m_41472_() || (prop = item.getFoodProperties(new ItemStack((ItemLike)item), null)) == null || (amount = Math.max(1, Mth.m_14165_((double)((double)prop.m_38744_() / 2.0)))) <= 0) continue;
                ItemStack input = new ItemStack((ItemLike)IC2Items.TIN_CAN, amount);
                Item foodResult = RecipeRegistry.CAN_EFFECTS.getItemForFood(new ItemStack((ItemLike)item));
                ItemStack output = new ItemStack((ItemLike)(foodResult == null ? IC2Items.TIN_CAN_FILLED : foodResult), amount);
                result.add(new CannerRecipe(input, new Tuple((Object)new ItemInput(item), (Object)output), 2));
            }
            return result;
        }
    }
}

