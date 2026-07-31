/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.TooltipFlag$Default
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
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MultiCraftingComponent
extends BaseWikiComponent {
    public static final int TEXT_COLOR = -12829907;
    public static final int BASE_COLOR = -1;
    public static final Box2i LEFT_BOX = new Box2i(2, 85, 10, 8);
    public static final Box2i LEFT_SUB_BOX = new Box2i(2, 71, 10, 8);
    public static final Box2i RIGHT_BOX = new Box2i(104, 85, 10, 8);
    public static final Box2i RIGHT_SUB_BOX = new Box2i(104, 71, 10, 8);
    public static final Box2i CRAFT_BOX = new Box2i(16, 85, 8, 8);
    public static final Box2i USE_BOX = new Box2i(91, 85, 8, 8);
    public static final Box2i ITEM_BOX = new Box2i(15, 67, 15, 15);
    Map<ItemStack, IRecipeReference> references;
    List<ItemStack> items;
    int itemIndex;
    int itemOffset;
    int offset;
    boolean isOutput = true;

    public MultiCraftingComponent(Map<ItemStack, IRecipeReference> references) {
        super(94);
        this.references = references;
        this.items = new ObjectArrayList(references.keySet());
        references.get(this.items.get(0)).setListener(() -> {
            this.isOutput = this.hasOutputs();
        });
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void addSearchString(Consumer<Component> listener) {
        for (ItemStack stack : this.items) {
            listener.accept(stack.m_41611_());
        }
    }

    public IRecipeReference getReference() {
        if (this.itemIndex < 0 || this.itemIndex >= this.items.size()) {
            return null;
        }
        return this.references.get(this.items.get(this.itemIndex));
    }

    public List<IRecipeRenderer> getRecipes() {
        IRecipeReference ref = this.getReference();
        if (ref == null) {
            return ObjectLists.emptyList();
        }
        return this.isOutput ? ref.getOutputs() : ref.getInputs();
    }

    public boolean hasInputs() {
        IRecipeReference ref = this.getReference();
        return ref != null && ref.getInputs().size() > 0;
    }

    public boolean hasOutputs() {
        IRecipeReference ref = this.getReference();
        return ref != null && ref.getOutputs().size() > 0;
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
        mouseX += screen.getGuiLeft();
        mouseY += screen.getGuiTop();
        render.renderBackground(screen, stack, x, y);
        int renderIndex = -1;
        for (int i = 0; i < 5; ++i) {
            screen.drawTextureRegion(stack, x + 15 + i * 17, y + 66, 124.0f, 0.0f, 18.0f, 18.0f, 18.0f, 18.0f);
            if (i + this.itemOffset != this.itemIndex) continue;
            renderIndex = i;
        }
        if (renderIndex != -1) {
            screen.drawTextureRegion(stack, x + 15 + renderIndex * 17, y + 66, 141.0f, 0.0f, 18.0f, 18.0f, 18.0f, 18.0f);
        }
        screen.bindDefaultTexture();
        float effect = this.itemOffset == 0 ? 0.5f : (LEFT_SUB_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + LEFT_SUB_BOX.getX(), y + LEFT_SUB_BOX.getY(), 10.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = this.itemOffset + 5 >= this.items.size() ? 0.5f : (RIGHT_SUB_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + RIGHT_SUB_BOX.getX(), y + RIGHT_SUB_BOX.getY(), 0.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = this.offset == 0 ? 0.5f : (LEFT_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + LEFT_BOX.getX(), y + LEFT_BOX.getY(), 10.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = this.offset + 1 >= recipes.size() ? 0.5f : (RIGHT_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + RIGHT_BOX.getX(), y + RIGHT_BOX.getY(), 0.0f, 209.0f, 10.0f, 8.0f, ColorUtils.darker(-1, effect));
        effect = !this.isOutput || !this.hasInputs() ? 0.5f : (USE_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + USE_BOX.getX(), y + USE_BOX.getY(), 117.0f, 235.0f, 8.0f, 8.0f, 16.0f, 16.0f, ColorUtils.darker(-1, effect));
        effect = this.isOutput || !this.hasOutputs() ? 0.5f : (CRAFT_BOX.isInBox(mouseX - x, mouseY - y) ? 0.75f : 1.0f);
        screen.drawTextureRegion(stack, x + CRAFT_BOX.getX(), y + CRAFT_BOX.getY(), 133.0f, 235.0f, 8.0f, 8.0f, 16.0f, 16.0f, ColorUtils.darker(-1, effect));
        render.renderItems(screen, stack, x, y);
        for (int i = 0; i < 5; ++i) {
            if (i + this.itemOffset >= this.items.size()) continue;
            screen.drawItemStack(stack, x + 15 + i * 17, y + 66, this.items.get(i + this.itemOffset), 18.0f, 18.0f);
        }
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
        if ((recipes = this.getRecipes()).size() > 0) {
            recipes.get(this.offset).getHoverInfo(screen, mouseX, mouseY, x, y).forEach(tooltips);
        }
        mouseX -= x;
        mouseY -= y;
        for (int i = 0; i < 5; ++i) {
            if (i + this.itemOffset >= this.items.size() || mouseX < 15 + i * 17 || mouseX >= 33 + i * 17 || mouseY < 66 || mouseY >= 84) continue;
            this.items.get(i + this.itemOffset).m_41651_((Player)screen.getPlayer(), (TooltipFlag)TooltipFlag.Default.NORMAL).forEach(tooltips);
            break;
        }
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public ItemStack getHoveredStack(IC2Screen screen, int x, int y, int mouseX, int mouseY) {
        ItemStack stack;
        List<IRecipeRenderer> recipes = this.getRecipes();
        if (recipes.size() > 0 && !(stack = recipes.get(this.offset).getHoverStack(screen, mouseX, mouseY, x, y)).m_41619_()) {
            return stack;
        }
        mouseX -= x;
        mouseY -= y;
        for (int i = 0; i < 5; ++i) {
            if (i + this.itemOffset >= this.items.size() || mouseX < 15 + i * 17 || mouseX >= 33 + i * 17 || mouseY < 66 || mouseY >= 84) continue;
            return this.items.get(i + this.itemOffset).m_41777_();
        }
        return ItemStack.f_41583_;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void renderForeground(IC2Screen screen, int x, int y, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        List<IRecipeRenderer> recipes = this.getRecipes();
        if (recipes.size() <= 0) {
            return;
        }
        screen.drawCenterString(stack, (Component)this.string(this.offset + 1 + " / " + recipes.size()), x + 58, y + 86, -12829907);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(IC2Screen screen, int x, int y, int mouseX, int mouseY, IWikiProvider provider) {
        if (LEFT_BOX.isInBox(mouseX -= (x += 9), mouseY -= (y += 9))) {
            if (this.offset > 0) {
                --this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        if (LEFT_SUB_BOX.isInBox(mouseX, mouseY)) {
            if (this.itemOffset > 0) {
                --this.itemOffset;
            }
            return true;
        }
        if (RIGHT_BOX.isInBox(mouseX, mouseY)) {
            if (this.offset + 1 < this.getRecipes().size()) {
                ++this.offset;
                IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            }
            return true;
        }
        if (RIGHT_SUB_BOX.isInBox(mouseX, mouseY)) {
            if (this.itemOffset < this.items.size() - 5) {
                ++this.itemOffset;
            }
            return true;
        }
        for (int i = 0; i < 5; ++i) {
            int index = this.itemOffset + i;
            if (index >= this.items.size() || !ITEM_BOX.isInBox(mouseX - i * 17, mouseY) || this.itemIndex == index) continue;
            this.itemIndex = index;
            this.offset = 0;
            this.isOutput = this.hasOutputs();
            IC2.AUDIO.playSound(Minecraft.m_91087_().f_91074_, SoundEvents.f_11713_.m_11660_(), AudioManager.SoundType.STATIC, 1.0f, 2.0f);
            return true;
        }
        if (USE_BOX.isInBox(mouseX, mouseY)) {
            if (this.hasInputs()) {
                this.isOutput = false;
                this.offset = 0;
            }
            return true;
        }
        if (CRAFT_BOX.isInBox(mouseX, mouseY)) {
            if (this.hasOutputs()) {
                this.isOutput = true;
                this.offset = 0;
            }
            return true;
        }
        return false;
    }
}

