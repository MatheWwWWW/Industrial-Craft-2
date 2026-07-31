/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
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
 *  net.minecraft.core.Registry
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.alchemy.PotionUtils
 *  net.minecraft.world.level.ItemLike
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.jeiplugin.core.recipes.categories.brew;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.api.recipes.registries.IPotionBrewRegistry;
import ic2.core.IC2;
import ic2.core.block.misc.tiles.BarrelTileEntity;
import ic2.core.platform.recipes.misc.GlobalRecipes;
import ic2.core.platform.recipes.misc.PotionBrewRegistry;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class PotionBrewCategory
implements IRecipeCategory<PotionBrew> {
    RecipeType<PotionBrew> id;
    IDrawable background;
    IDrawable icon;

    public PotionBrewCategory(IGuiHelper helper, RecipeType<PotionBrew> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)new ItemStack(provider));
        this.background = helper.createDrawable(texture, 40, 10, 100, 65);
    }

    public RecipeType<PotionBrew> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return Component.m_237113_((String)"Potion Brew");
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void draw(PotionBrew recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        Font font = Minecraft.m_91087_().f_91062_;
        long time = 0L;
        for (int i = 0; i <= recipe.duration; ++i) {
            time = (long)((double)time + 5000.0 * Math.pow(3.0, i) * 2.5);
        }
        String s = DurationFormatUtils.formatDuration((long)time, (String)"HH:mm:ss");
        font.m_92883_(stack, s, (float)(75 - font.m_92895_(s) / 2), 8.0f, 0x404040);
    }

    public void setRecipe(IRecipeLayoutBuilder layout, PotionBrew recipe, IFocusGroup focus) {
        layout.addSlot(RecipeIngredientRole.INPUT, 25, 25).addItemStack(recipe.container);
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 5).addItemStack(recipe.prev);
        layout.addSlot(RecipeIngredientRole.INPUT, 25, 5).addItemStack(recipe.ingredient);
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 45).addItemStack(recipe.getGlowStone());
        layout.addSlot(RecipeIngredientRole.INPUT, 25, 45).addItemStack(recipe.getRedStone());
        layout.addSlot(RecipeIngredientRole.INPUT, 5, 25).addItemStack(recipe.getTime());
        layout.addSlot(RecipeIngredientRole.OUTPUT, 76, 25).addItemStack(recipe.result);
    }

    public static class PotionBrew
    implements Comparable<PotionBrew> {
        MobEffect instance;
        ItemStack ingredient;
        ItemStack container;
        ItemStack prev;
        ItemStack result;
        int redstone;
        int glowStone;
        int duration;

        public PotionBrew(MobEffect instance, ItemStack ingredient, ItemStack container, ItemStack prev, ItemStack result, int redstone, int glowStone, int duration) {
            this.instance = instance;
            this.ingredient = ingredient;
            this.container = container;
            this.prev = prev;
            this.result = result;
            this.redstone = redstone;
            this.glowStone = glowStone;
            this.duration = duration;
        }

        @Override
        public int compareTo(PotionBrew o) {
            int result = Integer.compare(Registry.f_122823_.m_7447_((Object)this.instance), Registry.f_122823_.m_7447_((Object)o.instance));
            if (result != 0) {
                return result;
            }
            result = Integer.compare(this.redstone, o.redstone);
            if (result != 0) {
                return result;
            }
            result = Integer.compare(this.glowStone, o.glowStone);
            if (result != 0) {
                return result;
            }
            return Integer.compare(this.duration, this.duration);
        }

        public boolean hasRedstone() {
            return this.redstone > 0;
        }

        public boolean hasGlowStone() {
            return this.glowStone > 0;
        }

        public ItemStack getRedStone() {
            return new ItemStack((ItemLike)Items.f_42451_, this.redstone);
        }

        public ItemStack getGlowStone() {
            return new ItemStack((ItemLike)Items.f_42525_, this.glowStone);
        }

        public ItemStack getTime() {
            return new ItemStack((ItemLike)Items.f_42524_, this.duration + 1);
        }

        public static List<PotionBrew> createList(GlobalRecipes recipes) {
            ObjectList result = CollectionUtils.createList();
            PotionBrewRegistry registry = recipes.potions;
            Map<Item, IPotionBrewRegistry.PotionContainer> containers = registry.getContainers();
            Map<Item, Map<MobEffect, MobEffect>> effects = registry.getEffects();
            for (int time = 0; time <= 6; time += 6) {
                for (int glow = 0; glow < 3; ++glow) {
                    for (int red = 0; red < 3; ++red) {
                        int redstone;
                        int n = red == 0 ? 0 : (redstone = red == 1 ? 10 : 20);
                        int glowStone = glow == 0 ? 0 : (red == 1 ? 10 : 20);
                        int solid = time > 0 && red > 0 ? (int)Mth.m_14036_((float)((float)time * ((float)redstone / 10.0f)), (float)0.0f, (float)(BarrelTileEntity.POTION_DURATIONS.length - 1)) : 0;
                        int alc = time > 0 && glow > 0 ? (int)Mth.m_14036_((float)((float)time * 0.5f * ((float)glowStone / 10.0f)), (float)0.0f, (float)2.0f) : 0;
                        switch (alc) {
                            case 1: {
                                solid = (int)((float)solid * 0.5f);
                                break;
                            }
                            case 2: {
                                solid = (int)((float)solid * 0.25f);
                            }
                        }
                        for (Map.Entry<Item, Map<MobEffect, MobEffect>> entry : effects.entrySet()) {
                            for (Map.Entry<MobEffect, MobEffect> subEntry : entry.getValue().entrySet()) {
                                ItemStack prev = subEntry.getKey() == null ? new ItemStack((ItemLike)Items.f_42588_) : PotionBrew.createPotions(subEntry.getKey(), 0, 20, new ItemStack((ItemLike)Items.f_42589_));
                                for (Map.Entry<Item, IPotionBrewRegistry.PotionContainer> containerEntry : containers.entrySet()) {
                                    IPotionBrewRegistry.PotionContainer value = containerEntry.getValue();
                                    ItemStack output = PotionBrew.createPotions(subEntry.getValue(), alc, (int)Math.max(20.0f, (float)BarrelTileEntity.POTION_DURATIONS[10 - solid] * 20.0f * value.getDurationEffectiveness()), new ItemStack((ItemLike)value.getOutput(), 1000 / value.getFluidUsage()));
                                    result.add((PotionBrew)new PotionBrew(subEntry.getValue(), new ItemStack((ItemLike)entry.getKey()), new ItemStack((ItemLike)containerEntry.getKey(), 1000 / value.getFluidUsage()), prev, output, redstone, glowStone, time));
                                }
                            }
                        }
                    }
                }
            }
            result.sort(null);
            return result;
        }

        public static ItemStack createPotions(MobEffect effect, int level, int time, ItemStack stack) {
            ObjectList effects = ObjectLists.singleton((Object)new MobEffectInstance(effect, time, level));
            PotionUtils.m_43552_((ItemStack)stack, (Collection)effects);
            stack.m_41784_().m_128405_("CustomPotionColor", PotionUtils.m_43564_((Collection)effects));
            MutableComponent name = Component.m_237115_((String)(stack.m_41720_().m_5524_() + ".effect." + IC2.RECIPES.get((boolean)false).potions.getName(effect)));
            name.m_6270_(name.m_7383_().m_131155_(Boolean.valueOf(false)));
            stack.m_41714_((Component)name);
            return stack;
        }
    }
}

