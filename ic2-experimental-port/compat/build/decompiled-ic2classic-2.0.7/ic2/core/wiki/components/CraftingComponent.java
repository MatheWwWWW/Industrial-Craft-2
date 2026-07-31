/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.wiki.components;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.audio.AudioManager;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.wiki.base.managers.IWikiProvider;
import ic2.core.wiki.components.BaseWikiComponent;
import ic2.core.wiki.recipes.providers.IRecipeReference;
import ic2.core.wiki.recipes.renderers.IRecipeRenderer;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CraftingComponent
extends BaseWikiComponent {
    public static final int TEXT_COLOR = -12829907;
    public static final int BASE_COLOR = -1;
    public static Box2i LEFT_BOX = new Box2i(2, 64, 10, 8);
    public static Box2i RIGHT_BOX = new Box2i(104, 64, 10, 8);
    public static Box2i CRAFT_BOX = new Box2i(18, 64, 8, 8);
    public static Box2i USE_BOX = new Box2i(92, 64, 8, 8);
    IRecipeReference reference;
    int offset;
    boolean isOutput;

    public CraftingComponent(IRecipeReference reference) {
        super(75);
        this.reference = reference;
        this.reference.setListener(() -> {
            this.isOutput = this.hasOutputs();
        });
    }

    public List<IRecipeRenderer> getRecipes() {
        return this.isOutput ? this.reference.getOutputs() : this.reference.getInputs();
    }

    public boolean hasInputs() {
        return this.reference.getInputs().size() > 0;
    }

    public boolean hasOutputs() {
        return this.reference.getOutputs().size() > 0;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
        this.reference.addSeachInfo(listener);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderBackground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        List<IRecipeRenderer> recipes = this.getRecipes();
        if (recipes.size() <= 0) {
            return;
        }
        this.offset = Math.min(this.offset, recipes.size() - 1);
        IRecipeRenderer render = recipes.get(this.offset);
        if (render == null) {
            return;
        }
        render.renderBackground(screen, stack, x, y);
        screen.bindDefaultTexture();
        float effect = this.offset == 0 ? 0.5f : (LEFT_BOX.isInBox((mouseX += screen.getGuiLeft()) - x, (mouseY += screen.getGuiTop()) - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + 2, y + 66, 10.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = this.offset + 1 >= recipes.size() ? 0.5f : (RIGHT_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + 104, y + 66, 0.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = !this.isOutput || !this.hasInputs() ? 0.5f : (USE_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + 91, y + 66, 117.0f, 235.0f, 8.0f, 8.0f, 16.0f, 16.0f, ColorUtils.darker(-1, effect));
        effect = this.isOutput || !this.hasOutputs() ? 0.5f : (CRAFT_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + 16, y + 66, 133.0f, 235.0f, 8.0f, 8.0f, 16.0f, 16.0f, ColorUtils.darker(-1, effect));
        render.renderItems(screen, stack, x, y);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        List<IRecipeRenderer> recipes = this.getRecipes();
        if (recipes.size() <= 0) {
            return;
        }
        screen.drawCenterString(stack, (Component)this.string(this.offset + 1 + " / " + recipes.size()), x + 58, y + 66, -12829907);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        if (LEFT_BOX.isInBox(mouseX - (x += 9), mouseY - (y += 9))) {
            if (this.offset > 0) {
                --this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        if (RIGHT_BOX.isInBox(mouseX - x, mouseY - y)) {
            if (this.offset + 1 < this.getRecipes().size()) {
                ++this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        if (USE_BOX.isInBox(mouseX - x, mouseY - y)) {
            if (this.hasInputs()) {
                this.isOutput = false;
                this.offset = 0;
            }
            return true;
        }
        if (CRAFT_BOX.isInBox(mouseX - x, mouseY - y)) {
            if (this.hasOutputs()) {
                this.isOutput = true;
                this.offset = 0;
            }
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addToolTips(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, Consumer<Component> tooltips) {
        List<IRecipeRenderer> recipes;
        if (CRAFT_BOX.isInBox(mouseX - x, mouseY - y)) {
            tooltips.accept((Component)this.translate("gui.ic2.wiki.show.craft"));
        }
        if (USE_BOX.isInBox(mouseX - x, mouseY - y)) {
            tooltips.accept((Component)this.translate("gui.ic2.wiki.show.usage"));
        }
        if ((recipes = this.getRecipes()).size() <= 0) {
            return;
        }
        recipes.get(this.offset).getHoverInfo(screen, mouseX, mouseY, x, y).forEach(tooltips);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoveredStack(IC2Screen screen, int x, int y, int mouseX, int mouseY) {
        List<IRecipeRenderer> recipes = this.getRecipes();
        if (recipes.isEmpty()) {
            return ItemStack.f_41583_;
        }
        return recipes.get(this.offset).getHoverStack(screen, mouseX, mouseY, x, y);
    }
}

