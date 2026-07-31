/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap$BasicEntry
 *  it.unimi.dsi.fastutil.objects.Object2DoubleMap$Entry
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
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  org.apache.commons.lang3.time.DurationFormatUtils
 */
package ic2.jeiplugin.core.recipes.categories;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.logic.planner.newLogic.SimulationResult;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.tooltips.ILangHelper;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
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
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class ReactorCategory
implements IRecipeCategory<ReactorRecipe> {
    RecipeType<ReactorRecipe> id;
    ItemStack stack;
    IDrawable background;
    IDrawable icon;
    IDrawable slot;

    public ReactorCategory(IGuiHelper helper, RecipeType<ReactorRecipe> location, ResourceLocation texture, ItemLike provider) {
        this.id = location;
        this.stack = new ItemStack(provider);
        this.icon = helper.createDrawableIngredient((IIngredientType)VanillaTypes.ITEM_STACK, (Object)this.stack);
        this.slot = helper.createDrawable(texture, 165, 0, 18, 18);
        this.background = helper.createDrawable(texture, 0, 8, 165, 186);
    }

    public IDrawable getBackground() {
        return this.background;
    }

    public IDrawable getIcon() {
        return this.icon;
    }

    public RecipeType<ReactorRecipe> getRecipeType() {
        return this.id;
    }

    public Component getTitle() {
        return this.stack.m_41611_();
    }

    public void draw(ReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        int size = recipe.setup.chambersRequired + 3;
        int startX = 83 - 9 * size;
        int startY = -2;
        for (int i = 0; i < 54; ++i) {
            int x = i % 9;
            if (x >= size) continue;
            this.slot.draw(matrixStack, startX + 18 * x, startY + 18 * (i / 9));
        }
        Font font = Minecraft.m_91087_().f_91062_;
        int y = 110;
        for (Component process : recipe.text) {
            font.m_92889_(matrixStack, process, 0.0f, (float)y, 0x404040);
            y += 10;
        }
        font.m_92889_(matrixStack, (Component)Component.m_237115_((String)"jei.ic2.reacotr.copy").m_130948_(Style.f_131099_.m_131162_(Boolean.valueOf(mouseX >= 99.0 && mouseX <= 166.0 && mouseY >= 108.0 && mouseY <= 118.0))), 100.0f, 110.0f, 0x404040);
    }

    public List<Component> getTooltipStrings(ReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        Component comp;
        Style style = IC2Screen.getComponentNew(Lists.transform(recipe.text, Component::m_7532_), (int)mouseX, (int)mouseY - 110);
        if (style != null && style.m_131186_() != null && (comp = (Component)style.m_131186_().m_130823_(HoverEvent.Action.f_130831_)) != null) {
            return CollectionUtils.asList(comp);
        }
        return super.getTooltipStrings((Object)recipe, recipeSlotsView, mouseX, mouseY);
    }

    public boolean handleInput(ReactorRecipe recipe, double mouseX, double mouseY, InputConstants.Key mouseButton) {
        if (mouseX >= 99.0 && mouseX <= 166.0 && mouseY >= 108.0 && mouseY <= 118.0) {
            Minecraft.m_91087_().f_91068_.m_90911_(recipe.data);
            Minecraft.m_91087_().f_91074_.m_5661_((Component)Component.m_237115_((String)"jei.ic2.reactor.copied"), false);
            return true;
        }
        return false;
    }

    public void setRecipe(IRecipeLayoutBuilder layout, ReactorRecipe recipe, IFocusGroup focus) {
        int size = recipe.setup.chambersRequired + 3;
        int startX = 84 - 9 * size;
        int startY = -1;
        for (int i = 0; i < 54; ++i) {
            int x = i % 9;
            if (x >= size) continue;
            layout.addSlot(RecipeIngredientRole.INPUT, startX + 18 * x, startY + 18 * (i / 9)).addItemStack(recipe.setup.items.getStackInSlot(i));
        }
    }

    public static class ReactorRecipe
    implements ILangHelper {
        public static final String[] EU_SUFFIX = new String[]{"EU", "k EU", "m EU", "b EU"};
        public static final String[] BUCKET_SUFFIX = new String[]{"mB", "B", "kB", "MB"};
        public final String name;
        public final String data;
        public final ReactorSetup setup;
        public final List<Component> text = CollectionUtils.createList();

        public ReactorRecipe(String name, ReactorSetup setup) {
            this.name = name;
            this.data = setup.setup;
            this.setup = setup;
            this.createText();
        }

        private void createText() {
            SimulationResult result = this.setup.simulation;
            this.text.add((Component)this.translate("jei.ic2.reactor.by", this.name));
            this.text.add((Component)this.translate("jei.ic2.reactor.stats", this.bool(result != null ? result.isStable : this.setup.isStable), this.bool(this.setup.breeder)));
            this.text.add((Component)this.translate("jei.ic2.reactor.efficiency", Formatters.XP_FORMAT.format(this.setup.efficiency), Formatters.XP_FORMAT.format(this.setup.totalEfficiency)));
            this.text.add((Component)this.translate("jei.ic2.reactor.heat", Formatters.EU_FORMAT.format(this.setup.startingHeat)));
            if (this.setup.isSteam) {
                this.text.add((Component)this.translate("jei.ic2.reactor.steam", this.getValue(this.setup.totalOutput, true), Formatters.XP_FORMAT.format(this.setup.output)));
                if (result != null) {
                    MutableComponent effective = this.translate("jei.ic2.reactor.steam.effective", this.getValue((long)((float)this.setup.totalOutput * result.effectiveProduction), true), Formatters.XP_FORMAT.format(this.setup.output * result.effectiveProduction));
                    this.text.add((Component)effective.m_6270_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, (Object)this.translate("jei.ic2.reactor.effective")))));
                }
            } else {
                this.text.add((Component)this.translate("jei.ic2.reactor.output", this.getValue(this.setup.totalOutput, false), Formatters.EU_FORMAT.format(this.setup.output)));
                if (result != null) {
                    MutableComponent effective = this.translate("jei.ic2.reactor.output.effective", this.getValue((long)((float)this.setup.totalOutput * result.effectiveProduction), false), Formatters.EU_FORMAT.format(this.setup.output * result.effectiveProduction));
                    this.text.add((Component)effective.m_6270_(Style.f_131099_.m_131144_(new HoverEvent(HoverEvent.Action.f_130831_, (Object)this.translate("jei.ic2.reactor.effective")))));
                }
            }
            if (result != null) {
                this.text.add((Component)this.translate("Gen/Cool Time: %s/%s", DurationFormatUtils.formatDuration((long)((long)(result.ticksSimulated * result.tickRate) * 50L), (String)"HH:mm:ss"), DurationFormatUtils.formatDuration((long)((long)result.ticksCooled * 50L), (String)"HH:mm:ss")));
            }
        }

        private String getValue(long value, boolean bucket) {
            Object2DoubleMap.Entry<String> entry = this.getSuffics(value, bucket);
            return Formatters.EU_FORMAT.format((double)this.setup.totalOutput / entry.getDoubleValue()) + (String)entry.getKey();
        }

        private Object2DoubleMap.Entry<String> getSuffics(long input, boolean bucket) {
            double value = 1.0;
            int index = 0;
            while (input > 1000L && index + 1 < BUCKET_SUFFIX.length) {
                ++index;
                value *= 1000.0;
                input /= 1000L;
            }
            return new AbstractObject2DoubleMap.BasicEntry((Object)(bucket ? BUCKET_SUFFIX[index] : EU_SUFFIX[index]), value);
        }

        public boolean isValid() {
            return this.setup != null;
        }
    }
}

