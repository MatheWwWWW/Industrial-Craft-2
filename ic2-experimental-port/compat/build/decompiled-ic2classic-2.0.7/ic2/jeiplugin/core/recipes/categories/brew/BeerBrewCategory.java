/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
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
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.jeiplugin.core.recipes.categories.brew;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.item.food_and_drink.IC2Drink;
import ic2.core.item.food_and_drink.IC2FoodsAndDrinks;
import ic2.core.item.food_and_drink.drinks.Beer;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class BeerBrewCategory
implements IRecipeCategory<Brew> {
    RecipeType<Brew> id;
    IDrawable background;
    IDrawable icon;

    public BeerBrewCategory(IGuiHelper helper, RecipeType<Brew> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack(provider));
        this.background = helper.createDrawable(texture, 40, 10, 100, 65);
    }

    public RecipeType<Brew> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return Component.m_237113_((String)"Beer Brewing");
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void draw(Brew recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.m_91087_().f_91062_;
        long time = 0L;
        for (int i = recipe.getTime(); i >= 1; --i) {
            time = (long)((double)time + 24000.0 * Math.pow(3.0, i - 1));
        }
        String s = DurationFormatUtils.formatDuration((long)Duration.ofSeconds(time / 20L).toMillis(), (String)"HH:mm:ss");
        font.m_92883_(stack, s, (float)(75 - font.m_92895_(s) / 2), 8.0f, 0x404040);
        s = " <= Ratios";
        font.m_92883_(stack, s, 23.0f, 50.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, Brew recipe, IFocusGroup focus) {
        ItemStack stack = new ItemStack((ItemLike)Items.f_42524_, recipe.getTime() + 1);
        StackUtil.addTooltip(stack, "Brew Time Stage (Accumulated Brew time)");
        layout.addSlot(RecipeIngredientRole.INPUT, 25, 25).addItemStacks(recipe.getInput());
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 5).addItemStack(recipe.getWheat());
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 25).addItemStack(recipe.getHops());
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 45).addItemStack(recipe.getWater());
        layout.addSlot(RecipeIngredientRole.INPUT, 25, 5).addItemStack(stack);
        layout.addSlot(RecipeIngredientRole.OUTPUT, 76, 25).addItemStacks(recipe.getOutput());
    }

    public static class Brew
    implements Comparable<Brew> {
        static final long[] DURATIONS = new long[]{24000L, 96000L, 312000L, 960000L, 18456000L, 24288000L};
        int wheat;
        int hops;
        int water;
        int time;
        List<ItemStack> input;
        List<ItemStack> output;

        public Brew(int wheat, int hops, int water, int time, List<ItemStack> input, List<ItemStack> output) {
            this.wheat = wheat;
            this.hops = hops;
            this.water = water;
            this.time = time;
            this.input = input;
            this.output = output;
        }

        public boolean hasHops() {
            return this.hops > 0;
        }

        public boolean hasWheat() {
            return this.wheat > 0;
        }

        public boolean hasWater() {
            return this.water > 0;
        }

        public ItemStack getHops() {
            return new ItemStack((ItemLike)IC2Items.HOPS, this.hops);
        }

        public ItemStack getWheat() {
            return new ItemStack((ItemLike)Items.f_42405_, this.wheat);
        }

        public ItemStack getWater() {
            return new ItemStack((ItemLike)IC2Items.CELL_WATER, this.water);
        }

        public int getTime() {
            return this.time;
        }

        public List<ItemStack> getInput() {
            return this.input;
        }

        public List<ItemStack> getOutput() {
            return this.output;
        }

        @Override
        public int compareTo(Brew o) {
            int result = Integer.compare(this.wheat, o.wheat);
            if (result != 0) {
                return result;
            }
            result = Integer.compare(this.hops, o.hops);
            if (result != 0) {
                return result;
            }
            result = Integer.compare(this.water, o.water);
            if (result != 0) {
                return result;
            }
            return Long.compare(this.time, o.time);
        }

        public static List<Brew> createBrew() {
            IntOpenHashSet duplicationCheck = new IntOpenHashSet();
            ObjectList result = CollectionUtils.createList();
            for (int time = 0; time < 6; ++time) {
                for (int water = 1; water <= 32; ++water) {
                    for (int hops = 0; hops < 64; ++hops) {
                        for (int wheat = 0; wheat < 64; ++wheat) {
                            MutableInt quality = new MutableInt(time);
                            MutableInt alc = new MutableInt();
                            MutableInt solid = new MutableInt();
                            Brew.createAlcoholLevel(hops, wheat, alc, quality);
                            Brew.createSolidLevel(hops, wheat, water * 1000, solid, quality);
                            if (!duplicationCheck.add(Beer.createData(quality.getValue(), solid.getValue(), alc.getValue()))) continue;
                            ObjectList input = CollectionUtils.createList();
                            ObjectList output = CollectionUtils.createList();
                            for (IC2Drink drink : IC2FoodsAndDrinks.CONTAINERS) {
                                ItemStack item = drink.fillWith(IC2FoodsAndDrinks.BEER, 1);
                                if (item.m_41619_()) continue;
                                output.add((ItemStack)Beer.createData(item, quality.getValue(), solid.getValue(), alc.getValue()));
                                input.add((ItemStack)new ItemStack((ItemLike)drink));
                            }
                            result.add((Brew)new Brew(wheat, hops, water, quality.getValue(), (List<ItemStack>)input, (List<ItemStack>)output));
                        }
                    }
                }
            }
            result.sort(null);
            return result;
        }

        private static void createAlcoholLevel(int hopsAmount, int wheatAmount, MutableInt alc, MutableInt quality) {
            float hopsToWheat = wheatAmount > 0 ? (float)hopsAmount / (float)wheatAmount : 10.0f;
            alc.setValue(0);
            if (hopsAmount <= 0 && wheatAmount <= 0) {
                hopsToWheat = 0.0f;
            }
            if (hopsToWheat >= 5.0f) {
                quality.setValue(5);
            } else if (hopsToWheat >= 4.0f) {
                alc.setValue(6);
            } else if (hopsToWheat >= 3.0f) {
                alc.setValue(5);
            } else if (hopsToWheat >= 2.0f) {
                alc.setValue(4);
            } else if (hopsToWheat > 0.5f) {
                alc.setValue(3);
            } else if (hopsToWheat > 0.33333334f) {
                alc.setValue(2);
            } else if (hopsToWheat > 0.25f) {
                alc.setValue(1);
            }
        }

        private static void createSolidLevel(int hopsAmount, int wheatAmount, int fluidAmount, MutableInt solidRatio, MutableInt quality) {
            float solid = fluidAmount > 0 ? (float)(hopsAmount + wheatAmount) / ((float)fluidAmount / 1000.0f) : 10.0f;
            solidRatio.setValue(0);
            if (solid >= 4.0f) {
                quality.setValue(5);
            } else if (solid >= 2.4f) {
                solidRatio.setValue(6);
            } else if (solid >= 2.0f) {
                solidRatio.setValue(5);
            } else if (solid > 1.0f) {
                solidRatio.setValue(4);
            } else if (solid == 1.0f) {
                solidRatio.setValue(3);
            } else if (solid > 0.5f) {
                solidRatio.setValue(2);
            } else if (solid > 0.4166667f) {
                solidRatio.setValue(1);
            }
        }
    }
}

