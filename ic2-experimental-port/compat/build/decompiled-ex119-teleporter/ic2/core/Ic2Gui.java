/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.MultiLineLabel
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.client.renderer.texture.MissingTextureAtlasSprite
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IKeyboardDependent;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollDirection;
import ic2.core.util.StackUtil;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class Ic2Gui<T extends ContainerBase<? extends Container>>
extends AbstractContainerScreen<T> {
    public static final int textHeight = 8;
    protected static Runnable closeHandler;
    private boolean fixKeyEvents = false;
    private final Set<GuiElement.ImplementedMethod> elementMethods = EnumSet.noneOf(GuiElement.ImplementedMethod.class);
    private final Queue<Tooltip> queuedTooltips = new ArrayDeque<Tooltip>();
    protected final List<GuiElement<?>> elements = new ArrayList();

    public Ic2Gui(T t, Inventory inventory, Component component) {
        this(t, inventory, component, 176, 166);
    }

    public Ic2Gui(T t, Inventory inventory, Component component, int n) {
        this(t, inventory, component, 176, n);
    }

    public Ic2Gui(T t, Inventory inventory, Component component, int n, int n2) {
        super(t, inventory, component);
        this.f_97727_ = n2;
        this.f_97726_ = n;
    }

    public final T getContainer() {
        return (T)((Object)((ContainerBase)this.f_97732_));
    }

    public final int getX() {
        return this.f_97735_;
    }

    public final int getY() {
        return this.f_97736_;
    }

    public final Slot getFocusedSlot() {
        return this.f_97734_;
    }

    public void m_7856_() {
        super.m_7856_();
        for (GuiElement<?> guiElement : this.elements) {
            if (!(guiElement instanceof IKeyboardDependent)) continue;
            this.f_96541_.f_91068_.m_90926_(true);
            this.fixKeyEvents = true;
            break;
        }
    }

    public void m_6305_(PoseStack poseStack, int n, int n2, float f) {
        this.m_7333_(poseStack);
        super.m_6305_(poseStack, n, n2, f);
        this.m_7025_(poseStack, n, n2);
    }

    public void m_181908_() {
        super.m_181908_();
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.tick)) {
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                guiElement.tick();
            }
        }
    }

    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        this.drawBackgroundAndTitle(poseStack, f, n -= this.f_97735_, n2 -= this.f_97736_);
        if (((ContainerBase)this.f_97732_).base instanceof IUpgradableBlock) {
            Ic2Gui.bindTexture(new ResourceLocation("ic2", "textures/gui/infobutton.png"));
            this.drawTexturedRect(poseStack, 3.0, 3.0, 10.0, 10.0, 0.0, 0.0);
        }
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.drawBackground)) {
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                guiElement.drawBackground(poseStack, n, n2);
            }
        }
    }

    protected void drawBackgroundAndTitle(PoseStack poseStack, float f, int n, int n2) {
        this.bindTexture();
        this.m_93228_(poseStack, this.f_97735_, this.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
        this.drawXCenteredString(poseStack, this.f_97726_ / 2, 6, this.f_96539_, 0x404040, false);
    }

    protected final void m_7027_(PoseStack poseStack, int n, int n2) {
        this.drawForegroundLayer(poseStack, n - this.f_97735_, n2 - this.f_97736_);
        this.flushTooltips(poseStack);
    }

    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        if (((ContainerBase)this.f_97732_).base instanceof IUpgradableBlock) {
            this.handleUpgradeTooltip(n, n2);
        }
        for (GuiElement<?> guiElement : this.elements) {
            if (!guiElement.isEnabled()) continue;
            guiElement.drawForeground(poseStack, n, n2);
        }
    }

    private void handleUpgradeTooltip(int n, int n2) {
        if (n < 0 || n > 12 || n2 < 0 || n2 > 12) {
            return;
        }
        ArrayList<Component> arrayList = new ArrayList<Component>();
        arrayList.add((Component)Component.m_237115_((String)"ic2.generic.text.upgrade"));
        for (ItemStack itemStack : Ic2Gui.getCompatibleUpgrades((IUpgradableBlock)((ContainerBase)this.f_97732_).base)) {
            arrayList.add((Component)itemStack.m_41786_().m_6881_().m_130940_(ChatFormatting.GRAY));
        }
        this.drawTooltip(n, n2, arrayList);
    }

    private static List<ItemStack> getCompatibleUpgrades(IUpgradableBlock iUpgradableBlock) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        Set<UpgradableProperty> set = iUpgradableBlock.getUpgradableProperties();
        for (ItemStack itemStack : UpgradeRegistry.getUpgrades()) {
            IUpgradeItem iUpgradeItem = (IUpgradeItem)itemStack.m_41720_();
            if (!iUpgradeItem.isSuitableFor(itemStack, set)) continue;
            arrayList.add(itemStack);
        }
        return arrayList;
    }

    public boolean m_6050_(double d, double d2, double d3) {
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.onMouseScroll)) {
            ScrollDirection scrollDirection = d3 != 0.0 ? (d3 < 0.0 ? ScrollDirection.down : ScrollDirection.up) : ScrollDirection.stopped;
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled() || !guiElement.contains((int)d, (int)d2)) continue;
                guiElement.onMouseScroll((int)d, (int)d2, scrollDirection);
            }
        }
        return super.m_6050_(d, d2, d3);
    }

    public boolean m_6375_(double d, double d2, int n) {
        MouseButton mouseButton;
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.onMouseClick) && (mouseButton = MouseButton.get(n)) != null) {
            boolean bl = false;
            d -= (double)this.f_97735_;
            d2 -= (double)this.f_97736_;
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                bl |= guiElement.onMouseClick((int)d, (int)d2, mouseButton, guiElement.contains((int)d, (int)d2));
            }
            if (!bl) {
                d += (double)this.f_97735_;
                d2 += (double)this.f_97736_;
            } else {
                return true;
            }
        }
        return super.m_6375_(d, d2, n);
    }

    public boolean m_7979_(double d, double d2, int n, double d3, double d4) {
        MouseButton mouseButton;
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.onMouseDrag) && (mouseButton = MouseButton.get(n)) != null) {
            boolean bl = false;
            d -= (double)this.f_97735_;
            d2 -= (double)this.f_97736_;
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                bl |= guiElement.onMouseDrag((int)d, (int)d2, mouseButton, guiElement.contains((int)d, (int)d2));
            }
            if (!bl) {
                d += (double)this.f_97735_;
                d2 += (double)this.f_97736_;
            } else {
                return true;
            }
        }
        return super.m_7979_(d, d2, n, d3, d4);
    }

    public boolean m_6348_(double d, double d2, int n) {
        MouseButton mouseButton;
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.onMouseRelease) && (mouseButton = MouseButton.get(n)) != null) {
            boolean bl = false;
            d -= (double)this.f_97735_;
            d2 -= (double)this.f_97736_;
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                bl |= guiElement.onMouseRelease((int)d, (int)d2, mouseButton, guiElement.contains((int)d, (int)d2));
            }
            if (!bl) {
                d += (double)this.f_97735_;
                d2 += (double)this.f_97736_;
            } else {
                return true;
            }
        }
        return super.m_6348_(d, d2, n);
    }

    public boolean m_5534_(char c, int n) {
        if (this.elementMethods.contains((Object)GuiElement.ImplementedMethod.onKeyTyped)) {
            boolean bl = false;
            for (GuiElement<?> guiElement : this.elements) {
                if (!guiElement.isEnabled()) continue;
                bl |= guiElement.onKeyTyped(c, n);
            }
            if (bl) {
                return true;
            }
        }
        return super.m_5534_(c, n);
    }

    public void m_7861_() {
        super.m_7861_();
        if (this.fixKeyEvents) {
            this.f_96541_.f_91068_.m_90926_(false);
        }
        if (closeHandler != null) {
            closeHandler.run();
        }
    }

    public void drawTexturedRect(PoseStack poseStack, double d, double d2, double d3, double d4, double d5, double d6) {
        this.drawTexturedRect(poseStack, d, d2, d3, d4, d5, d6, false);
    }

    public void drawTexturedRect(PoseStack poseStack, double d, double d2, double d3, double d4, double d5, double d6, boolean bl) {
        this.drawTexturedRect(poseStack, d, d2, d3, d4, d5 / 256.0, d6 / 256.0, (d5 + d3) / 256.0, (d6 + d4) / 256.0, bl);
    }

    public void drawTexturedRect(PoseStack poseStack, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, boolean bl) {
        double d9 = (d += (double)this.f_97735_) + d3;
        double d10 = (d2 += (double)this.f_97736_) + d4;
        if (bl) {
            double d11 = d5;
            d5 = d7;
            d7 = d11;
        }
        Matrix4f matrix4f = poseStack.m_85850_().m_85861_();
        int n = this.m_93252_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        bufferBuilder.m_85982_(matrix4f, (float)d, (float)d2, (float)n).m_7421_((float)d5, (float)d6).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)d, (float)d10, (float)n).m_7421_((float)d5, (float)d8).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)d9, (float)d10, (float)n).m_7421_((float)d7, (float)d8).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)d9, (float)d2, (float)n).m_7421_((float)d7, (float)d6).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
    }

    /*
     * Unable to fully structure code
     */
    public void drawSprite(PoseStack var1_1, double var2_2, double var4_3, double var6_4, double var8_5, TextureAtlasSprite var10_6, int var11_7, double var12_8, boolean var14_9, boolean var15_10) {
        if (var10_6 == null) {
            var10_6 = ((TextureAtlas)this.f_96541_.m_91097_().m_118506_(InventoryMenu.f_39692_)).m_118316_(MissingTextureAtlasSprite.m_118071_());
        }
        var2_2 += (double)this.f_97735_;
        var4_3 += (double)this.f_97736_;
        var12_8 *= 16.0;
        var16_11 = var10_6.m_118409_();
        var18_12 = var10_6.m_118411_();
        var20_13 = (double)var10_6.m_118410_() - var16_11;
        var22_14 = (double)var10_6.m_118412_() - var18_12;
        var24_15 = var11_7 >>> 24 & 255;
        var25_16 = var11_7 >>> 16 & 255;
        var26_17 = var11_7 >>> 8 & 255;
        var27_18 = var11_7 & 255;
        var28_19 = var1_1.m_85850_().m_85861_();
        var29_20 = this.m_93252_();
        RenderSystem.m_157427_((Supplier<ShaderInstance>)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, m_172820_(), ()Lnet/minecraft/client/renderer/ShaderInstance;)());
        var30_21 = Tesselator.m_85913_().m_85915_();
        var30_21.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        for (var39_22 = var2_2; var39_22 < var2_2 + var6_4; var39_22 += var31_23) {
            if (var39_22 != var2_2 || !var14_9) ** GOTO lbl-1000
            var31_23 = var6_4 % var12_8;
            if (v0 > 0.0) {
                var35_25 = var16_11 + var20_13 * (1.0 - var31_23 / var12_8);
            } else lbl-1000:
            // 2 sources

            {
                var31_23 = var12_8;
                var35_25 = var16_11;
            }
            var41_27 = Math.min(var39_22 + var31_23, var2_2 + var6_4);
            var43_28 = var35_25 + (var41_27 - var39_22) / var12_8 * var20_13;
            for (var45_29 = var4_3; var45_29 < var4_3 + var8_5; var45_29 += var33_24) {
                if (var45_29 != var4_3 || !var15_10) ** GOTO lbl-1000
                var33_24 = var8_5 % var12_8;
                if (v1 > 0.0) {
                    var37_26 = var18_12 + var22_14 * (1.0 - var33_24 / var12_8);
                } else lbl-1000:
                // 2 sources

                {
                    var33_24 = var12_8;
                    var37_26 = var18_12;
                }
                var47_30 = Math.min(var45_29 + var33_24, var4_3 + var8_5);
                var49_31 = var37_26 + (var47_30 - var45_29) / var12_8 * var22_14;
                var30_21.m_85982_(var28_19, (float)var39_22, (float)var45_29, (float)var29_20).m_7421_((float)var35_25, (float)var37_26).m_6122_(var25_16, var26_17, var27_18, var24_15).m_5752_();
                var30_21.m_85982_(var28_19, (float)var39_22, (float)var47_30, (float)var29_20).m_7421_((float)var35_25, (float)var49_31).m_6122_(var25_16, var26_17, var27_18, var24_15).m_5752_();
                var30_21.m_85982_(var28_19, (float)var41_27, (float)var47_30, (float)var29_20).m_7421_((float)var43_28, (float)var49_31).m_6122_(var25_16, var26_17, var27_18, var24_15).m_5752_();
                var30_21.m_85982_(var28_19, (float)var41_27, (float)var45_29, (float)var29_20).m_7421_((float)var43_28, (float)var37_26).m_6122_(var25_16, var26_17, var27_18, var24_15).m_5752_();
            }
        }
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)var30_21.m_231175_());
    }

    public void drawItem(int n, int n2, ItemStack itemStack) {
        this.f_96542_.m_115123_(itemStack, this.f_97735_ + n, this.f_97736_ + n2);
    }

    public void drawItemStack(int n, int n2, ItemStack itemStack) {
        this.drawItem(n, n2, itemStack);
        this.f_96542_.m_115174_(this.f_96547_, itemStack, this.f_97735_ + n, this.f_97736_ + n2, null);
    }

    public void drawColoredRect(PoseStack poseStack, int n, int n2, int n3, int n4, int n5) {
        int n6 = n5 >>> 24;
        boolean bl = n6 != 255 && n6 != 0;
        Matrix4f matrix4f = poseStack.m_85850_().m_85861_();
        int n7 = (n += this.f_97735_) + n3;
        int n8 = (n2 += this.f_97736_) + n4;
        int n9 = this.m_93252_();
        if (bl) {
            RenderSystem.m_69478_();
        }
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        BufferBuilder bufferBuilder = Tesselator.m_85913_().m_85915_();
        bufferBuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        bufferBuilder.m_85982_(matrix4f, (float)n, (float)n2, (float)n9).m_193479_(n5).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)n, (float)n8, (float)n9).m_193479_(n5).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)n7, (float)n8, (float)n9).m_193479_(n5).m_5752_();
        bufferBuilder.m_85982_(matrix4f, (float)n7, (float)n2, (float)n9).m_193479_(n5).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferBuilder.m_231175_());
        if (bl) {
            RenderSystem.m_69461_();
        }
    }

    public int drawString(PoseStack poseStack, int n, int n2, String string, int n3) {
        return this.f_96547_.m_92883_(poseStack, string, (float)n, (float)n2, n3);
    }

    public int drawString(PoseStack poseStack, int n, int n2, String string, int n3, boolean bl) {
        if (!bl) {
            return this.f_96547_.m_92883_(poseStack, string, (float)(this.f_97735_ + n), (float)(this.f_97736_ + n2), n3) - this.f_97735_;
        }
        return this.f_96547_.m_92750_(poseStack, string, (float)(this.f_97735_ + n), (float)(this.f_97736_ + n2), n3) - this.f_97735_;
    }

    public void drawTrimmedString(PoseStack poseStack, int n, int n2, String string, int n3, int n4) {
        MultiLineLabel.m_94341_((Font)this.f_96547_, (FormattedText)Component.m_237113_((String)string), (int)n3).m_6508_(poseStack, n, n2, 10, n4);
    }

    public void drawXCenteredString(PoseStack poseStack, int n, int n2, String string, int n3, boolean bl) {
        this.drawCenteredString(poseStack, n, n2, string, n3, bl, true, false);
    }

    public void drawXCenteredString(PoseStack poseStack, int n, int n2, Component component, int n3, boolean bl) {
        this.drawCenteredString(poseStack, n, n2, component, n3, bl, true, false);
    }

    public void drawXYCenteredString(PoseStack poseStack, int n, int n2, String string, int n3, boolean bl) {
        this.drawCenteredString(poseStack, n, n2, string, n3, bl, true, true);
    }

    public void drawXYCenteredString(PoseStack poseStack, int n, int n2, Component component, int n3, boolean bl) {
        this.drawCenteredString(poseStack, n, n2, component, n3, bl, true, true);
    }

    public void drawCenteredString(PoseStack poseStack, int n, int n2, String string, int n3, boolean bl, boolean bl2, boolean bl3) {
        if (bl2) {
            n -= this.getStringWidth(string) / 2;
        }
        if (bl3) {
            n2 -= 4;
        }
        this.f_96547_.m_92883_(poseStack, string, (float)(this.f_97735_ + n), (float)(this.f_97736_ + n2), n3);
    }

    public void drawCenteredString(PoseStack poseStack, int n, int n2, Component component, int n3, boolean bl, boolean bl2, boolean bl3) {
        if (bl2) {
            n -= this.getStringWidth(component) / 2;
        }
        if (bl3) {
            n2 -= 4;
        }
        this.f_96547_.m_92889_(poseStack, component, (float)(this.f_97735_ + n), (float)(this.f_97736_ + n2), n3);
    }

    public int getStringWidth(String string) {
        return this.f_96547_.m_92895_(string);
    }

    public int getStringWidth(Component component) {
        return this.f_96547_.m_92852_((FormattedText)component);
    }

    public String trimStringToWidth(String string, int n) {
        return this.f_96547_.m_92837_(string, n, false);
    }

    public String trimStringToWidthReverse(String string, int n) {
        return this.f_96547_.m_92837_(string, n, true);
    }

    public void drawTooltip(int n, int n2, List<Component> list) {
        this.queuedTooltips.add(new Tooltip(list, n, n2));
    }

    public void drawTooltip(PoseStack poseStack, int n, int n2, ItemStack itemStack) {
        assert (!StackUtil.isEmpty(itemStack));
        this.m_6057_(poseStack, itemStack, n, n2);
    }

    protected void flushTooltips(PoseStack poseStack) {
        for (Tooltip tooltip : this.queuedTooltips) {
            this.m_169388_(poseStack, tooltip.text, Optional.empty(), tooltip.x, tooltip.y);
        }
        this.queuedTooltips.clear();
    }

    protected void addElement(GuiElement<?> guiElement) {
        this.elements.add(guiElement);
        this.elementMethods.addAll(guiElement.getImplementedMethods());
    }

    protected final void bindTexture() {
        Ic2Gui.bindTexture(this.getTexture());
    }

    public static void bindTexture(ResourceLocation resourceLocation) {
        RenderSystem.m_157456_((int)0, (ResourceLocation)resourceLocation);
    }

    protected IClickHandler createEventSender(final int n) {
        if (((ContainerBase)this.f_97732_).base instanceof BlockEntity) {
            return new IClickHandler(){

                @Override
                public void onClick(MouseButton mouseButton) {
                    IC2.network.get(false).initiateClientTileEntityEvent((BlockEntity)((ContainerBase)((Ic2Gui)Ic2Gui.this).f_97732_).base, n);
                }
            };
        }
        throw new IllegalArgumentException("not applicable for " + ((ContainerBase)this.f_97732_).base);
    }

    protected abstract ResourceLocation getTexture();

    private static class Tooltip {
        final int x;
        final int y;
        final List<Component> text;

        Tooltip(List<Component> list, int n, int n2) {
            this.text = list;
            this.x = n;
            this.y = n2;
        }
    }
}

