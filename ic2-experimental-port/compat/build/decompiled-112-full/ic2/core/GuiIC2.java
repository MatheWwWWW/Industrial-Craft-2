/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package ic2.core;

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
import ic2.core.init.Localization;
import ic2.core.util.StackUtil;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public abstract class GuiIC2<T extends ContainerBase<? extends IInventory>>
extends GuiContainer {
    private boolean fixKeyEvents = false;
    private boolean tick = false;
    private boolean background = false;
    private boolean mouseClick = false;
    private boolean mouseDrag = false;
    private boolean mouseRelease = false;
    private boolean mouseScroll = false;
    private boolean key = false;
    private final Queue<Tooltip> queuedTooltips = new ArrayDeque<Tooltip>();
    protected final T container;
    protected final List<GuiElement<?>> elements = new ArrayList();
    public static final int textHeight = 8;

    public GuiIC2(T container) {
        this(container, 176, 166);
    }

    public GuiIC2(T container, int ySize) {
        this(container, 176, ySize);
    }

    public GuiIC2(T container, int xSize, int ySize) {
        super(container);
        this.container = container;
        this.field_147000_g = ySize;
        this.field_146999_f = xSize;
    }

    public T getContainer() {
        return this.container;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        for (GuiElement<?> element : this.elements) {
            if (!(element instanceof IKeyboardDependent)) continue;
            Keyboard.enableRepeatEvents((boolean)true);
            this.fixKeyEvents = true;
            break;
        }
    }

    public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
        this.func_146276_q_();
        super.func_73863_a(mouseX, mouseY, partialTicks);
        this.func_191948_b(mouseX, mouseY);
    }

    public void func_73876_c() {
        super.func_73876_c();
        if (this.tick) {
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                element.tick();
            }
        }
    }

    protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawBackgroundAndTitle(partialTicks, mouseX -= this.field_147003_i, mouseY -= this.field_147009_r);
        if (((ContainerBase)((Object)this.container)).base instanceof IUpgradableBlock) {
            this.field_146297_k.func_110434_K().func_110577_a(new ResourceLocation("ic2", "textures/gui/infobutton.png"));
            this.drawTexturedRect(3.0, 3.0, 10.0, 10.0, 0.0, 0.0);
        }
        if (this.background) {
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                element.drawBackground(mouseX, mouseY);
            }
        }
    }

    protected void drawBackgroundAndTitle(float partialTicks, int mouseX, int mouseY) {
        this.bindTexture();
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        String name = Localization.translate(((ContainerBase)((Object)this.container)).base.func_70005_c_());
        this.drawXCenteredString(this.field_146999_f / 2, 6, name, 0x404040, false);
    }

    protected final void func_146979_b(int mouseX, int mouseY) {
        this.drawForegroundLayer(mouseX - this.field_147003_i, mouseY - this.field_147009_r);
        this.flushTooltips();
    }

    protected void drawForegroundLayer(int mouseX, int mouseY) {
        if (((ContainerBase)((Object)this.container)).base instanceof IUpgradableBlock) {
            this.handleUpgradeTooltip(mouseX, mouseY);
        }
        for (GuiElement<?> element : this.elements) {
            if (!element.isEnabled()) continue;
            element.drawForeground(mouseX, mouseY);
        }
    }

    private void handleUpgradeTooltip(int mouseX, int mouseY) {
        int areaSize = 12;
        if (mouseX < 0 || mouseX > 12 || mouseY < 0 || mouseY > 12) {
            return;
        }
        ArrayList<String> text = new ArrayList<String>();
        text.add(Localization.translate("ic2.generic.text.upgrade"));
        for (ItemStack stack : GuiIC2.getCompatibleUpgrades((IUpgradableBlock)((ContainerBase)((Object)this.container)).base)) {
            text.add(stack.func_82833_r());
        }
        this.drawTooltip(mouseX, mouseY, text);
    }

    private static List<ItemStack> getCompatibleUpgrades(IUpgradableBlock block) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        Set<UpgradableProperty> properties = block.getUpgradableProperties();
        for (ItemStack stack : UpgradeRegistry.getUpgrades()) {
            IUpgradeItem item = (IUpgradeItem)stack.func_77973_b();
            if (!item.isSuitableFor(stack, properties)) continue;
            ret.add(stack);
        }
        return ret;
    }

    public void func_146274_d() throws IOException {
        super.func_146274_d();
        if (this.mouseScroll) {
            int mouseX = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c - this.field_147003_i;
            int mouseY = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1 - this.field_147009_r;
            int scrollDelta = Mouse.getEventDWheel();
            ScrollDirection direction = scrollDelta != 0 ? (scrollDelta < 0 ? ScrollDirection.down : ScrollDirection.up) : ScrollDirection.stopped;
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled() || !element.contains(mouseX, mouseY)) continue;
                element.onMouseScroll(mouseX, mouseY, direction);
            }
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        MouseButton button;
        boolean handled = false;
        if (this.mouseClick && (button = MouseButton.get(mouseButton)) != null) {
            mouseX -= this.field_147003_i;
            mouseY -= this.field_147009_r;
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                handled |= element.onMouseClick(mouseX, mouseY, button, element.contains(mouseX, mouseY));
            }
            if (!handled) {
                mouseX += this.field_147003_i;
                mouseY += this.field_147009_r;
            } else {
                this.mouseHandled = true;
            }
        }
        if (!handled) {
            super.func_73864_a(mouseX, mouseY, mouseButton);
        }
    }

    protected void func_146273_a(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        MouseButton button;
        boolean handled = false;
        if (this.mouseDrag && (button = MouseButton.get(clickedMouseButton)) != null) {
            mouseX -= this.field_147003_i;
            mouseY -= this.field_147009_r;
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                handled |= element.onMouseDrag(mouseX, mouseY, button, timeSinceLastClick, element.contains(mouseX, mouseY));
            }
            if (!handled) {
                mouseX += this.field_147003_i;
                mouseY += this.field_147009_r;
            } else {
                this.mouseHandled = true;
            }
        }
        if (!handled) {
            super.func_146273_a(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
    }

    protected void func_146286_b(int mouseX, int mouseY, int state) {
        MouseButton button;
        boolean handled = false;
        if (this.mouseRelease && (button = MouseButton.get(state)) != null) {
            mouseX -= this.field_147003_i;
            mouseY -= this.field_147009_r;
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                handled |= element.onMouseRelease(mouseX, mouseY, button, element.contains(mouseX, mouseY));
            }
            if (!handled) {
                mouseX += this.field_147003_i;
                mouseY += this.field_147009_r;
            } else {
                this.mouseHandled = true;
            }
        }
        if (!handled) {
            super.func_146286_b(mouseX, mouseY, state);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) throws IOException {
        boolean handled = false;
        if (this.key) {
            for (GuiElement<?> element : this.elements) {
                if (!element.isEnabled()) continue;
                handled |= element.onKeyTyped(typedChar, keyCode);
            }
            this.keyHandled = handled;
        }
        if (!handled) {
            super.func_73869_a(typedChar, keyCode);
        }
    }

    public void func_146281_b() {
        super.func_146281_b();
        if (this.fixKeyEvents) {
            Keyboard.enableRepeatEvents((boolean)false);
        }
    }

    public void drawTexturedRect(double x, double y, double width, double height, double texX, double texY) {
        this.drawTexturedRect(x, y, width, height, texX, texY, false);
    }

    public void drawTexturedRect(double x, double y, double width, double height, double texX, double texY, boolean mirrorX) {
        this.drawTexturedRect(x, y, width, height, texX / 256.0, texY / 256.0, (texX + width) / 256.0, (texY + height) / 256.0, mirrorX);
    }

    public void drawTexturedRect(double x, double y, double width, double height, double uS, double vS, double uE, double vE, boolean mirrorX) {
        double xE = (x += (double)this.field_147003_i) + width;
        double yE = (y += (double)this.field_147009_r) + height;
        if (mirrorX) {
            double tmp = uS;
            uS = uE;
            uE = tmp;
        }
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Tessellator tessellator = Tessellator.func_178181_a();
        BufferBuilder worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b(x, y, (double)this.field_73735_i).func_187315_a(uS, vS).func_181675_d();
        worldrenderer.func_181662_b(x, yE, (double)this.field_73735_i).func_187315_a(uS, vE).func_181675_d();
        worldrenderer.func_181662_b(xE, yE, (double)this.field_73735_i).func_187315_a(uE, vE).func_181675_d();
        worldrenderer.func_181662_b(xE, y, (double)this.field_73735_i).func_187315_a(uE, vS).func_181675_d();
        tessellator.func_78381_a();
    }

    /*
     * Unable to fully structure code
     */
    public void drawSprite(double x, double y, double width, double height, TextureAtlasSprite sprite, int color, double scale, boolean fixRight, boolean fixBottom) {
        if (sprite == null) {
            sprite = this.field_146297_k.func_147117_R().func_174944_f();
        }
        x += (double)this.field_147003_i;
        y += (double)this.field_147009_r;
        scale *= 16.0;
        spriteUS = sprite.func_94209_e();
        spriteVS = sprite.func_94206_g();
        spriteWidth = (double)sprite.func_94212_f() - spriteUS;
        spriteHeight = (double)sprite.func_94210_h() - spriteVS;
        a = color >>> 24 & 255;
        r = color >>> 16 & 255;
        g = color >>> 8 & 255;
        b = color & 255;
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        tessellator = Tessellator.func_178181_a();
        buffer = tessellator.func_178180_c();
        buffer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        for (xS = x; xS < x + width; xS += maxWidth) {
            if (xS != x || !fixRight) ** GOTO lbl-1000
            maxWidth = width % scale;
            if (v0 > 0.0) {
                uS = spriteUS + spriteWidth * (1.0 - maxWidth / scale);
            } else lbl-1000:
            // 2 sources

            {
                maxWidth = scale;
                uS = spriteUS;
            }
            xE = Math.min(xS + maxWidth, x + width);
            uE = uS + (xE - xS) / scale * spriteWidth;
            for (yS = y; yS < y + height; yS += maxHeight) {
                if (yS != y || !fixBottom) ** GOTO lbl-1000
                maxHeight = height % scale;
                if (v1 > 0.0) {
                    vS = spriteVS + spriteHeight * (1.0 - maxHeight / scale);
                } else lbl-1000:
                // 2 sources

                {
                    maxHeight = scale;
                    vS = spriteVS;
                }
                yE = Math.min(yS + maxHeight, y + height);
                vE = vS + (yE - yS) / scale * spriteHeight;
                buffer.func_181662_b(xS, yS, (double)this.field_73735_i).func_187315_a(uS, vS).func_181669_b(r, g, b, a).func_181675_d();
                buffer.func_181662_b(xS, yE, (double)this.field_73735_i).func_187315_a(uS, vE).func_181669_b(r, g, b, a).func_181675_d();
                buffer.func_181662_b(xE, yE, (double)this.field_73735_i).func_187315_a(uE, vE).func_181669_b(r, g, b, a).func_181675_d();
                buffer.func_181662_b(xE, yS, (double)this.field_73735_i).func_187315_a(uE, vS).func_181669_b(r, g, b, a).func_181675_d();
            }
        }
        tessellator.func_78381_a();
    }

    public void drawItem(int x, int y, ItemStack stack) {
        this.field_146296_j.func_175042_a(stack, this.field_147003_i + x, this.field_147009_r + y);
    }

    public void drawItemStack(int x, int y, ItemStack stack) {
        this.drawItem(x, y, stack);
        this.field_146296_j.func_180453_a(this.field_146289_q, stack, this.field_147003_i + x, this.field_147009_r + y, null);
    }

    public void drawColoredRect(int x, int y, int width, int height, int color) {
        GuiIC2.func_73734_a((int)(x += this.field_147003_i), (int)(y += this.field_147009_r), (int)(x + width), (int)(y + height), (int)color);
    }

    public int drawString(int x, int y, String text, int color, boolean shadow) {
        return this.field_146289_q.func_175065_a(text, (float)(this.field_147003_i + x), (float)(this.field_147009_r + y), color, shadow) - this.field_147003_i;
    }

    public void drawXCenteredString(int x, int y, String text, int color, boolean shadow) {
        this.drawCenteredString(x, y, text, color, shadow, true, false);
    }

    public void drawXYCenteredString(int x, int y, String text, int color, boolean shadow) {
        this.drawCenteredString(x, y, text, color, shadow, true, true);
    }

    public void drawCenteredString(int x, int y, String text, int color, boolean shadow, boolean centerX, boolean centerY) {
        if (centerX) {
            x -= this.getStringWidth(text) / 2;
        }
        if (centerY) {
            y -= 4;
        }
        this.field_146289_q.func_78276_b(text, this.field_147003_i + x, this.field_147009_r + y, color);
    }

    public int getStringWidth(String text) {
        return this.field_146289_q.func_78256_a(text);
    }

    public String trimStringToWidth(String text, int width) {
        return this.field_146289_q.func_78262_a(text, width, false);
    }

    public String trimStringToWidthReverse(String text, int width) {
        return this.field_146289_q.func_78262_a(text, width, true);
    }

    public void drawTooltip(int x, int y, List<String> text) {
        this.queuedTooltips.add(new Tooltip(text, x, y));
    }

    public void drawTooltip(int x, int y, ItemStack stack) {
        assert (!StackUtil.isEmpty(stack));
        this.func_146285_a(stack, x, y);
    }

    protected void flushTooltips() {
        for (Tooltip tooltip : this.queuedTooltips) {
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_146283_a(tooltip.text, tooltip.x, tooltip.y);
            GlStateManager.func_179140_f();
        }
        this.queuedTooltips.clear();
    }

    protected void addElement(GuiElement<?> element) {
        this.elements.add(element);
        if (!(this.tick && this.background && this.mouseClick && this.mouseDrag && this.mouseRelease && this.mouseScroll && this.key)) {
            GuiElement.Subscriptions subs = element.getSubscriptions();
            if (!this.tick) {
                this.tick = subs.tick;
            }
            if (!this.background) {
                this.background = subs.background;
            }
            if (!this.mouseClick) {
                this.mouseClick = subs.mouseClick;
            }
            if (!this.mouseDrag) {
                this.mouseDrag = subs.mouseDrag;
            }
            if (!this.mouseRelease) {
                this.mouseRelease = subs.mouseRelease;
            }
            if (!this.mouseScroll) {
                this.mouseScroll = subs.mouseScroll;
            }
            if (!this.key) {
                this.key = subs.key;
            }
        }
    }

    protected final void bindTexture() {
        this.field_146297_k.func_110434_K().func_110577_a(this.getTexture());
    }

    protected IClickHandler createEventSender(final int event) {
        if (((ContainerBase)((Object)this.container)).base instanceof TileEntity) {
            return new IClickHandler(){

                @Override
                public void onClick(MouseButton button) {
                    IC2.network.get(false).initiateClientTileEntityEvent((TileEntity)((ContainerBase)((Object)GuiIC2.this.container)).base, event);
                }
            };
        }
        throw new IllegalArgumentException("not applicable for " + ((ContainerBase)((Object)this.container)).base);
    }

    protected abstract ResourceLocation getTexture();

    private static class Tooltip {
        final int x;
        final int y;
        final List<String> text;

        Tooltip(List<String> text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
        }
    }
}

