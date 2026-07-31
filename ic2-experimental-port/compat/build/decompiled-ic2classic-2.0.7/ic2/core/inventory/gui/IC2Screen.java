/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.math.Matrix4f
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Widget
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
 *  net.minecraft.client.renderer.texture.SimpleTexture
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.RenderTooltipEvent$Pre
 *  net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.inventory.gui;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.gui.components.base.OpenerButton;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import ic2.core.inventory.slot.LockedSlot;
import ic2.core.platform.corehacks.mixins.client.ScreenMixin;
import ic2.core.platform.rendering.RenderUtils;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Vec2i;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

@OnlyIn(value=Dist.CLIENT)
public class IC2Screen
extends AbstractContainerScreen<IC2Container> {
    public static final int DEFAULT_TEXT_COLOR = 0x404040;
    public static final int SHOW_PLAYER_INVENTORY_NAME = 1;
    public static final int SHOW_CONTAINER_NAME = 2;
    public static final int SHOW_NOT_DISPLAY_TOOLTIPS = 4;
    public static final int RELOAD_GUI = 8;
    public static final int RESIZE_GUI = 16;
    public static final int IGNORE_GUI_BOUNDS = 32;
    public static final int UPDATE_TEXTURE = 64;
    public static final int RENDER_TOOLTIP = 128;
    public static long MAX_TOOLTIP_DELAY = 200L;
    ResourceLocation texture;
    AbstractTexture textureInstance;
    List<ITooltipProvider> tooltips = CollectionUtils.createList();
    List<AbstractWidget> expansionButtons = CollectionUtils.createList();
    Int2ObjectMap<AbstractWidget> widgets = new Int2ObjectOpenHashMap();
    Int2IntMap slotColors = new Int2IntOpenHashMap();
    int flags = 0;
    int clock = 0;
    int lastMouseX = 0;
    int lastMouseY = 0;
    long lastCheck = 0L;
    int lastDrawnToolTipAmount = 0;
    Component guiName;
    Vec2i nameOffset = new Vec2i();
    Vec2i mcNameOffset = new Vec2i();
    Predicate<Slot> toolTipOverride;

    public IC2Screen(IC2Container screenContainer, Inventory inv, Component titleIn) {
        super((AbstractContainerMenu)screenContainer, inv, titleIn);
        this.setFlag(3);
        this.guiName = titleIn;
        this.slotColors.defaultReturnValue(-2130706433);
    }

    public <U extends AbstractWidget> U addRenderableWidget(int index, U widget) {
        this.widgets.put(index, widget);
        return this.m_142416_(widget);
    }

    public <U extends GuiEventListener & Widget> U m_142416_(U widget) {
        if (widget instanceof ITooltipProvider) {
            this.tooltips.add((ITooltipProvider)widget);
        }
        if (widget instanceof OpenerButton) {
            this.expansionButtons.add((AbstractWidget)widget);
        }
        return (U)super.m_142416_(widget);
    }

    public void addOpenerWidget(int index) {
        AbstractWidget widget = (AbstractWidget)this.widgets.get(index);
        if (widget != null) {
            this.expansionButtons.add(widget);
        }
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
        this.textureInstance = null;
    }

    public void setFlag(int flag) {
        this.flags |= flag;
    }

    public void clearFlag(int flag) {
        this.flags &= ~flag;
    }

    public boolean hasFlag(int flag) {
        return (this.flags & flag) == flag;
    }

    public void hideSlot(int ... slotIndexes) {
        for (int slotIndex : slotIndexes) {
            this.slotColors.put(slotIndex, 0);
        }
    }

    public void setCustomHoverColor(int color, int ... slotIndexes) {
        for (int slotIndex : slotIndexes) {
            this.slotColors.put(slotIndex, color);
        }
    }

    public void removeSlotColors(int ... slotIndexes) {
        for (int slotIndex : slotIndexes) {
            this.slotColors.remove(slotIndex);
        }
    }

    public void setToolTipValidator(Predicate<Slot> toolTipOverride) {
        this.toolTipOverride = toolTipOverride;
    }

    protected void m_7856_() {
        super.m_7856_();
        this.m_169413_();
        this.widgets.clear();
        this.tooltips.clear();
        for (Slot slot : ((IC2Container)this.f_97732_).f_38839_) {
            if (!(slot instanceof ITooltipProvider)) continue;
            this.tooltips.add((ITooltipProvider)slot);
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public void m_7861_() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        super.m_7861_();
    }

    @SubscribeEvent
    public void onTooltip(RenderTooltipEvent.Pre event) {
        if (this.lastDrawnToolTipAmount > 0) {
            event.setY(event.getY() + this.lastDrawnToolTipAmount * 10 + 7 + (this.lastDrawnToolTipAmount == 1 ? -1 : 0));
        }
    }

    public void m_6305_(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.hasFlag(16)) {
            this.clearFlag(16);
            this.f_96541_.m_5741_();
        }
        if (this.hasFlag(8)) {
            this.clearFlag(8);
            this.m_7856_();
        }
        super.m_6305_(matrixStack, mouseX, mouseY, partialTicks);
        this.m_7025_(matrixStack, mouseX, mouseY);
        this.onPostDraw(matrixStack, partialTicks, mouseX, mouseY);
        int x = mouseX - this.getGuiLeft();
        int y = mouseX - this.getGuiTop();
        ObjectList results = CollectionUtils.createList();
        if (mouseX != Integer.MAX_VALUE && mouseY != Integer.MAX_VALUE) {
            Consumer<Component> listener = T -> results.addAll(this.f_96547_.m_92923_((FormattedText)T, Math.max(mouseX, this.f_96543_ - mouseX)));
            for (ITooltipProvider provider : this.tooltips) {
                provider.addToolTip(this, x, y, listener);
            }
            this.onTooltipCollection(matrixStack, mouseX, mouseY, listener);
        }
        if (!this.hasFlag(128) && (this.lastMouseX != mouseX || this.lastMouseY != mouseY) || results.isEmpty()) {
            this.lastCheck = System.currentTimeMillis();
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            if (results.isEmpty()) {
                this.clearFlag(128);
            }
            this.lastDrawnToolTipAmount = 0;
            return;
        }
        if (System.currentTimeMillis() - this.lastCheck < MAX_TOOLTIP_DELAY) {
            this.lastDrawnToolTipAmount = 0;
            return;
        }
        this.setFlag(128);
        this.lastDrawnToolTipAmount = 0;
        this.m_96617_(matrixStack, (List)results, mouseX, mouseY);
        this.lastDrawnToolTipAmount = results.size();
    }

    protected void m_7286_(PoseStack matrixStackIn, float partialTicks, int mouseX, int mouseY) {
        this.m_7333_(matrixStackIn);
        this.bindDefaultTexture();
        this.drawTextureRegion(matrixStackIn, this.getGuiLeft(), this.getGuiTop(), 0.0f, 0.0f, this.getXSize(), this.getYSize());
    }

    protected void m_7027_(PoseStack matrixStackIn, int mouseX, int mouseY) {
        if (this.hasFlag(1)) {
            this.f_96547_.m_92889_(matrixStackIn, this.f_169604_, (float)(8 + this.mcNameOffset.getX()), (float)(this.f_97727_ - 92 + this.mcNameOffset.getY()), 0x404040);
        }
        if (this.hasFlag(2)) {
            this.f_96547_.m_92889_(matrixStackIn, this.guiName, (float)(this.f_97726_ / 2 + this.nameOffset.getX() - this.f_96547_.m_92852_((FormattedText)this.guiName) / 2), (float)(6 + this.nameOffset.getY()), 0x404040);
        }
    }

    public void onPostDraw(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
    }

    public void onTooltipCollection(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {
    }

    public void m_181908_() {
        ++this.clock;
        super.m_181908_();
    }

    public void closeAllComponents() {
    }

    public boolean isHoveringOpenerButton(int mouseX, int mouseY) {
        mouseX += this.f_97735_;
        mouseY += this.f_97736_;
        int m = this.expansionButtons.size();
        for (int i = 0; i < m; ++i) {
            if (!this.expansionButtons.get(i).m_5953_((double)mouseX, (double)mouseY)) continue;
            return true;
        }
        return false;
    }

    public boolean isOverArea(int mouseX, int mouseY, int minX, int minY, int width, int height) {
        return mouseX >= minX && mouseY >= minY && mouseX <= minX + width && mouseY <= minY + height;
    }

    protected boolean m_7467_(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        if (this.hasFlag(32)) {
            return false;
        }
        return super.m_7467_(mouseX, mouseY, guiLeftIn, guiTopIn, mouseButton);
    }

    public void drawString(PoseStack matrixStackIn, Component text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text.m_7532_(), (float)x, (float)y, color);
    }

    public void drawCenterString(PoseStack matrixStackIn, Component text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text.m_7532_(), (float)(x - this.f_96547_.m_92724_(text.m_7532_()) / 2), (float)y, color);
    }

    public void drawRightString(PoseStack matrixStackIn, Component text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text.m_7532_(), (float)(x - this.f_96547_.m_92724_(text.m_7532_())), (float)y, color);
    }

    public void drawString(PoseStack matrixStackIn, FormattedCharSequence text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text, (float)x, (float)y, color);
    }

    public void drawCenterString(PoseStack matrixStackIn, FormattedCharSequence text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text, (float)(x - this.f_96547_.m_92724_(text) / 2), (float)y, color);
    }

    public void drawRightString(PoseStack matrixStackIn, FormattedCharSequence text, int x, int y, int color) {
        this.f_96547_.m_92877_(matrixStackIn, text, (float)(x - this.f_96547_.m_92724_(text)), (float)y, color);
    }

    public void drawItemStack(PoseStack matrix, float x, float y, ItemStack stack) {
        this.drawItemStack(matrix, x, y, stack, 20.0f, 20.0f);
    }

    public void drawItemStack(PoseStack matrix, float x, float y, ItemStack stack, float width, float height) {
        this.drawItemStack(matrix, x, y, stack, width, height, false, null);
    }

    public void drawItemStackWithDeco(PoseStack matrix, float x, float y, ItemStack stack) {
        this.drawItemStack(matrix, x, y, stack, 20.0f, 20.0f, true, null);
    }

    public void drawItemStack(PoseStack matrix, float x, float y, ItemStack stack, float width, float height, boolean durability, String text) {
        PoseStack pose = RenderSystem.m_157191_();
        pose.m_85836_();
        pose.m_85837_((double)x, (double)y, 0.0);
        float xScale = 20.0f / width;
        float yScale = 20.0f / height;
        pose.m_85841_(1.0f / xScale, 1.0f / yScale, 1.0f);
        Lighting.m_84931_();
        RenderSystem.m_69482_();
        this.f_96542_.m_115123_(stack, 2, 2);
        if (durability) {
            RenderUtils.renderGuiItemDecorations(pose, this.f_96547_, stack, 2.0f, 2.0f, text);
        }
        RenderSystem.m_69465_();
        pose.m_85849_();
        RenderSystem.m_157182_();
    }

    public void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height) {
        this.drawTextureRegion(stack, x, y, texX, texY, width, height, width, height);
    }

    public void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        float t_minX = texX / 256.0f;
        float t_minY = texY / 256.0f;
        float t_maxX = (texX + width) / 256.0f;
        float t_maxY = (texY + height) / 256.0f;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
        bufferbuilder.m_85982_(matrix, x, maxY, (float)this.m_93252_()).m_193479_(color).m_7421_(t_minX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, (float)this.m_93252_()).m_193479_(color).m_7421_(t_maxX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, (float)this.m_93252_()).m_193479_(color).m_7421_(t_maxX, t_minY).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(color).m_7421_(t_minX, t_minY).m_5752_();
        tessellator.m_85914_();
    }

    public void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height, float texWidth, float texHeight) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        float t_minX = texX / 256.0f;
        float t_minY = texY / 256.0f;
        float t_maxX = (texX + texWidth) / 256.0f;
        float t_maxY = (texY + texHeight) / 256.0f;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        bufferbuilder.m_85982_(matrix, x, maxY, (float)this.m_93252_()).m_7421_(t_minX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, (float)this.m_93252_()).m_7421_(t_maxX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, (float)this.m_93252_()).m_7421_(t_maxX, t_minY).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_7421_(t_minX, t_minY).m_5752_();
        tessellator.m_85914_();
    }

    public void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height, float texWidth, float texHeight, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        float t_minX = texX / 256.0f;
        float t_minY = texY / 256.0f;
        float t_maxX = (texX + texWidth) / 256.0f;
        float t_maxY = (texY + texHeight) / 256.0f;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
        bufferbuilder.m_85982_(matrix, x, maxY, (float)this.m_93252_()).m_193479_(color).m_7421_(t_minX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, (float)this.m_93252_()).m_193479_(color).m_7421_(t_maxX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, (float)this.m_93252_()).m_193479_(color).m_7421_(t_maxX, t_minY).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(color).m_7421_(t_minX, t_minY).m_5752_();
        tessellator.m_85914_();
    }

    public static void drawTexture(PoseStack stack, float x, float y, float z, TextureAtlasSprite sprite, float width, float height) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        bufferbuilder.m_85982_(matrix, x, maxY, z).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, z).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, z).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, z).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_5752_();
        tessellator.m_85914_();
    }

    public static void drawTexture(PoseStack stack, float x, float y, float z, TextureAtlasSprite sprite, int color, float width, float height) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
        bufferbuilder.m_85982_(matrix, x, maxY, z).m_193479_(color).m_7421_(sprite.m_118409_(), sprite.m_118412_()).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, z).m_193479_(color).m_7421_(sprite.m_118410_(), sprite.m_118412_()).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, z).m_193479_(color).m_7421_(sprite.m_118410_(), sprite.m_118411_()).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, z).m_193479_(color).m_7421_(sprite.m_118409_(), sprite.m_118411_()).m_5752_();
        tessellator.m_85914_();
    }

    public void drawColoredRegion(PoseStack stack, float x, float y, float width, float height, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        RenderSystem.m_69472_();
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferbuilder.m_85982_(matrix, x, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        tessellator.m_85914_();
        RenderSystem.m_69493_();
    }

    public void drawColorFrame(PoseStack stack, float x, float y, float width, float height, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        RenderSystem.m_69472_();
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_85815_);
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y + height, (float)this.m_93252_()).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(color).m_5752_();
        tessellator.m_85914_();
        RenderSystem.m_69493_();
    }

    public static void drawFrame(PoseStack stack, float x, float y, float width, float height, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        RenderSystem.m_69472_();
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_85815_);
        bufferbuilder.m_85982_(matrix, x, y, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y + height, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y + height, 0.0f).m_193479_(color).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, 0.0f).m_193479_(color).m_5752_();
        tessellator.m_85914_();
        RenderSystem.m_69493_();
    }

    public void drawGradientRegionHorizontal(PoseStack stack, float x, float y, float width, float height, int leftColor, int rightColor) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        RenderSystem.m_69472_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferbuilder.m_85982_(matrix, x, y + height, (float)this.m_93252_()).m_193479_(leftColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, (float)this.m_93252_()).m_193479_(rightColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, (float)this.m_93252_()).m_193479_(rightColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(leftColor | 0xFF000000).m_5752_();
        tessellator.m_85914_();
        RenderSystem.m_69461_();
        RenderSystem.m_69493_();
    }

    public void drawGradientRegionVertical(PoseStack stack, float x, float y, float width, float height, int topColor, int bottomColor) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        RenderSystem.m_69472_();
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferbuilder.m_85982_(matrix, x, y + height, (float)this.m_93252_()).m_193479_(bottomColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y + height, (float)this.m_93252_()).m_193479_(bottomColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x + width, y, (float)this.m_93252_()).m_193479_(topColor | 0xFF000000).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_193479_(topColor | 0xFF000000).m_5752_();
        tessellator.m_85914_();
        RenderSystem.m_69461_();
        RenderSystem.m_69493_();
    }

    public void makeSlotFunction(PoseStack matrix, Slot slot) {
        int i = slot.f_40220_ + this.f_97735_;
        int j = slot.f_40221_ + this.f_97736_;
        this.m_93250_(100);
        this.f_96542_.f_115093_ = 100.0f;
        ItemStack stack = slot.m_7993_();
        if (stack.m_41619_() && slot.m_6659_()) {
            Pair pair = slot.m_7543_();
            if (pair != null) {
                RenderSystem.m_157456_((int)0, (ResourceLocation)((ResourceLocation)pair.getFirst()));
                IC2Screen.m_93200_((PoseStack)matrix, (int)i, (int)j, (int)this.m_93252_(), (int)16, (int)16, (TextureAtlasSprite)((TextureAtlasSprite)this.f_96541_.m_91258_((ResourceLocation)pair.getFirst()).apply((ResourceLocation)pair.getSecond())));
            }
        } else {
            RenderSystem.m_69482_();
            this.f_96542_.m_174229_((LivingEntity)this.f_96541_.f_91074_, stack, i, j, 0);
            this.f_96542_.m_115174_(this.f_96547_, stack, i, j, null);
        }
        this.f_96542_.f_115093_ = 0.0f;
        this.m_93250_(0);
    }

    protected void m_6057_(PoseStack matrixStack, ItemStack itemStack, int mouseX, int mouseY) {
        if (this.hasFlag(4) && this.getSlotUnderMouse() instanceof LockedSlot) {
            return;
        }
        super.m_6057_(matrixStack, itemStack, mouseX, mouseY);
    }

    public void m_96570_(PoseStack matrixStack, @Nullable Style style, int mouseX, int mouseY) {
        super.m_96570_(matrixStack, style, mouseX, mouseY);
    }

    public boolean m_6375_(double mouseX, double mouseY, int p_231044_5_) {
        this.lastCheck = System.currentTimeMillis();
        return super.m_6375_(mouseX, mouseY, p_231044_5_);
    }

    public boolean m_6348_(double mouseX, double mouseY, int button) {
        this.lastCheck = System.currentTimeMillis();
        return super.m_6348_(mouseX, mouseY, button);
    }

    public void bindDefaultTexture() {
        RenderSystem.m_157453_((int)0, (int)this.getActiveTexture().m_117963_());
    }

    public AbstractTexture getActiveTexture() {
        if (this.textureInstance == null) {
            this.textureInstance = this.getTexture(this.texture);
        }
        return this.textureInstance;
    }

    public AbstractTexture getTexture(ResourceLocation location) {
        AbstractTexture obj = this.f_96541_.m_91097_().m_118506_(location);
        if (obj == null) {
            obj = new SimpleTexture(location);
            this.f_96541_.m_91097_().m_118495_(location, obj);
        }
        return obj;
    }

    public void bindTexture(ResourceLocation location) {
        RenderSystem.m_157453_((int)0, (int)this.getTexture(location).m_117963_());
    }

    public void preventDoubleClick() {
        ((ScreenMixin)((Object)this)).setLastClickButton(0);
    }

    public void setPlayerInventoryOffset(int x, int y) {
        this.mcNameOffset.setX(x);
        this.mcNameOffset.setY(y);
    }

    public void setContainerOffset(int x, int y) {
        this.nameOffset.setX(x);
        this.nameOffset.setY(y);
    }

    public void modifySize(int width, int height) {
        this.f_97726_ += width;
        this.f_97727_ += height;
    }

    public void setMaxSize(int width, int height) {
        this.f_97726_ = width;
        this.f_97727_ = height;
    }

    public void setXSize(int width) {
        this.f_97726_ = width;
    }

    public void setYSize(int height) {
        this.f_97727_ = height;
    }

    public void setGuiName(Component guiName) {
        this.guiName = guiName;
    }

    public Component m_96636_() {
        return this.guiName;
    }

    public void setRepeatingKeys(boolean value) {
        this.getMinecraft().f_91068_.m_90926_(value);
    }

    public int getZLevel() {
        return this.m_93252_();
    }

    public void setZLevel(int z) {
        this.m_93250_(z);
    }

    public LocalPlayer getPlayer() {
        return this.f_96541_.f_91074_;
    }

    public UUID getPlayerID() {
        return this.f_96541_.f_91074_.m_20148_();
    }

    public ResourceLocation getGuiTexture() {
        return this.texture;
    }

    public AbstractTexture getTextureObject() {
        return this.textureInstance;
    }

    public ItemRenderer getRenderItem() {
        return this.f_96542_;
    }

    public Font getFont() {
        return this.f_96547_;
    }

    public int getClock() {
        return this.clock;
    }

    public boolean isSlotDragging() {
        return this.f_97738_;
    }

    public AbstractWidget getButton(int id) {
        return (AbstractWidget)this.widgets.get(id);
    }

    public <K extends AbstractWidget> K getCastedButton(int id, Class<K> clz) {
        return (K)((AbstractWidget)this.widgets.get(id));
    }

    public boolean hasButton(int id) {
        return this.widgets.containsKey(id);
    }

    public Int2ObjectMap<AbstractWidget> getWidgets() {
        return this.widgets;
    }

    public void removeButtons(Predicate<AbstractWidget> widgets) {
        this.m_6702_().removeIf(T -> T instanceof AbstractWidget && widgets.test((AbstractWidget)T));
        this.f_169369_.removeIf(T -> T instanceof AbstractWidget && widgets.test((AbstractWidget)T));
    }

    public int getSlotColor(int index) {
        return this.slotColors.get(index);
    }

    public boolean isSlotHovered(Slot slot, int mouseX, int mouseY) {
        return this.m_6774_(slot.f_40220_, slot.f_40221_, 16, 16, mouseX + this.f_97735_, mouseY + this.f_97736_);
    }

    public <K extends AbstractContainerMenu> K getCastedContainer(Class<K> clz) {
        return (K)(clz.isInstance(this.f_97732_) ? this.f_97732_ : null);
    }

    public static Style getComponentNew(List<FormattedCharSequence> list, int mouseX, int mouseY) {
        int yLevel = 9;
        if (mouseX >= 0 && mouseY >= 0) {
            int index = 0;
            while (yLevel <= mouseY) {
                yLevel += 9;
                ++index;
            }
            if (index >= list.size()) {
                return null;
            }
            return Minecraft.m_91087_().f_91062_.m_92865_().m_92338_(list.get(index), mouseX);
        }
        return null;
    }

    public <T extends IFluidHandler & IFluidTank> void displayMultiTank(PoseStack matrix, int x, int y, int xOver, int yOver, int ySize, T tank) {
        if (((IFluidTank)tank).getFluidAmount() <= 0) {
            return;
        }
        RenderSystem.m_69478_();
        x += this.getGuiLeft();
        y += this.getGuiTop() + ySize;
        Function map = this.f_96541_.m_91258_(InventoryMenu.f_39692_);
        this.bindTexture(InventoryMenu.f_39692_);
        float yTotal = 0.0f;
        float yProgress = 0.0f;
        int max = ((IFluidTank)tank).getCapacity();
        int m = tank.getTanks();
        block0: for (int i = 0; i < m; ++i) {
            float lvl;
            FluidStack stack = tank.getFluidInTank(i);
            float f = lvl = stack.isEmpty() ? 0.0f : (float)stack.getAmount() / (float)max * (float)ySize;
            if (lvl <= 0.0f) continue;
            yTotal += lvl;
            IClientFluidTypeExtensions attr = IClientFluidTypeExtensions.of((Fluid)stack.getFluid());
            TextureAtlasSprite liquidIcon = (TextureAtlasSprite)map.apply(attr.getStillTexture(stack));
            if (liquidIcon == map.apply(MissingTextureAtlasSprite.m_118071_())) continue;
            int color = attr.getTintColor(stack);
            while (yProgress < yTotal) {
                float bounds = Math.min(16.0f, yTotal - yProgress);
                IC2Screen.drawTexture(matrix, x, (float)y - yProgress - bounds, this.m_93252_(), liquidIcon, color, 16.0f, bounds);
                if (bounds <= 0.0f || yProgress + 1.0f >= yTotal) continue block0;
                yProgress += bounds;
            }
        }
        this.bindDefaultTexture();
        this.drawTextureRegion(matrix, x, y - ySize, xOver, yOver, 16.0f, ySize);
    }

    public void displayTank(PoseStack stack, float x, float y, float xOver, float yOver, float ySize, float yMax, FluidStack fluid) {
        this.displayTank(stack, x, y, xOver, yOver, 16.0f, ySize, yMax, fluid);
    }

    public void displayTank(PoseStack stack, float x, float y, float xOver, float yOver, float xSize, float ySize, float yMax, FluidStack fluid) {
        IClientFluidTypeExtensions attr;
        if (fluid.isEmpty()) {
            return;
        }
        x += (float)this.getGuiLeft();
        y += (float)this.getGuiTop();
        Function map = this.f_96541_.m_91258_(InventoryMenu.f_39692_);
        TextureAtlasSprite liquidIcon = (TextureAtlasSprite)map.apply((attr = IClientFluidTypeExtensions.of((Fluid)fluid.getFluid())).getStillTexture(fluid));
        if (liquidIcon == map.apply(MissingTextureAtlasSprite.m_118071_())) {
            return;
        }
        this.bindTexture(InventoryMenu.f_39692_);
        RenderSystem.m_69478_();
        int color = attr.getTintColor(fluid);
        int yTotal = 0;
        y += yMax;
        while ((float)yTotal < ySize) {
            float bounds = Math.min(16.0f, ySize - (float)yTotal);
            IC2Screen.drawTexture(stack, x, y - (float)yTotal - bounds, this.m_93252_(), liquidIcon, color, xSize, bounds);
            yTotal += Mth.m_14167_((float)bounds);
            if (!(bounds <= 0.0f) && !((float)yTotal >= ySize)) continue;
            break;
        }
        this.bindDefaultTexture();
        this.drawTextureRegion(stack, x, y - yMax, xOver, yOver, xSize, yMax);
    }
}

