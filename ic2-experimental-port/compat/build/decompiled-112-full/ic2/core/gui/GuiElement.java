/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.google.common.base.Suppliers
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureMap
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.gui;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import ic2.core.ContainerBase;
import ic2.core.GuiIC2;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollDirection;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public abstract class GuiElement<T extends GuiElement<T>> {
    protected static final int hoverColor = -2130706433;
    public static final ResourceLocation commonTexture = new ResourceLocation("ic2", "textures/gui/common.png");
    private static final Map<Class<?>, Subscriptions> SUBSCRIPTIONS = new HashMap();
    protected final GuiIC2<?> gui;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private IEnableHandler enableHandler;
    private Supplier<String> tooltipProvider;

    protected GuiElement(GuiIC2<?> gui, int x, int y, int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("negative width");
        }
        if (height < 0) {
            throw new IllegalArgumentException("negative height");
        }
        this.gui = gui;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public final boolean isEnabled() {
        return this.enableHandler == null || this.enableHandler.isEnabled();
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }

    public T withEnableHandler(IEnableHandler enableHandler) {
        this.enableHandler = enableHandler;
        return (T)this;
    }

    public T withTooltip(String tooltip) {
        return this.withTooltip((Supplier<String>)Suppliers.ofInstance((Object)tooltip));
    }

    public T withTooltip(Supplier<String> tooltipProvider) {
        this.tooltipProvider = tooltipProvider;
        return (T)this;
    }

    public void tick() {
    }

    public void drawBackground(int mouseX, int mouseY) {
    }

    public void drawForeground(int mouseX, int mouseY) {
        if (this.contains(mouseX, mouseY) && !this.suppressTooltip(mouseX, mouseY)) {
            String tooltip;
            List<String> lines = this.getToolTip();
            if (this.tooltipProvider != null && (tooltip = (String)this.tooltipProvider.get()) != null && !tooltip.isEmpty()) {
                GuiElement.addLines(lines, tooltip);
            }
            if (!lines.isEmpty()) {
                this.gui.drawTooltip(mouseX, mouseY, lines);
            }
        }
    }

    private static void addLines(List<String> list, String str) {
        int pos;
        int startPos = 0;
        while ((pos = str.indexOf(10, startPos)) != -1) {
            list.add(GuiElement.processText(str.substring(startPos, pos)));
            startPos = pos + 1;
        }
        if (startPos == 0) {
            list.add(GuiElement.processText(str));
        } else {
            list.add(GuiElement.processText(str.substring(startPos)));
        }
    }

    public boolean onMouseClick(int mouseX, int mouseY, MouseButton button, boolean onThis) {
        return onThis && this.onMouseClick(mouseX, mouseY, button);
    }

    protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
        return false;
    }

    public boolean onMouseDrag(int mouseX, int mouseY, MouseButton button, long timeFromLastClick, boolean onThis) {
        return onThis && this.onMouseDrag(mouseX, mouseY, button, timeFromLastClick);
    }

    protected boolean onMouseDrag(int mouseX, int mouseY, MouseButton button, long timeFromLastClick) {
        return false;
    }

    public boolean onMouseRelease(int mouseX, int mouseY, MouseButton button, boolean onThis) {
        return onThis && this.onMouseRelease(mouseX, mouseY, button);
    }

    protected boolean onMouseRelease(int mouseX, int mouseY, MouseButton button) {
        return false;
    }

    public void onMouseScroll(int mouseX, int mouseY, ScrollDirection direction) {
    }

    public boolean onKeyTyped(char typedChar, int keyCode) {
        return false;
    }

    protected boolean suppressTooltip(int mouseX, int mouseY) {
        return false;
    }

    protected List<String> getToolTip() {
        return new ArrayList<String>();
    }

    protected static String processText(String text) {
        return Localization.translate(text);
    }

    protected final IInventory getBase() {
        return ((ContainerBase)((Object)this.gui.getContainer())).base;
    }

    protected final Map<String, TextProvider.ITextProvider> getTokens() {
        HashMap<String, TextProvider.ITextProvider> ret = new HashMap<String, TextProvider.ITextProvider>();
        ret.put("name", TextProvider.ofTranslated(this.getBase().func_70005_c_()));
        return ret;
    }

    protected static void bindTexture(ResourceLocation texture) {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(texture);
    }

    public static void bindCommonTexture() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(commonTexture);
    }

    protected static void bindBlockTexture() {
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
    }

    protected static TextureMap getBlockTextureMap() {
        return Minecraft.func_71410_x().func_147117_R();
    }

    private static final Method hasMethod(Class<?> cls, String name, Class<?> ... params) {
        try {
            return !cls.getDeclaredMethod(name, params).isAnnotationPresent(SkippedMethod.class) ? Method.PRESENT : Method.SKIPPED;
        }
        catch (NoSuchMethodException e) {
            return Method.MISSING;
        }
    }

    public final Subscriptions getSubscriptions() {
        Class<?> cls = this.getClass();
        Subscriptions subscriptions = SUBSCRIPTIONS.get(cls);
        if (subscriptions == null) {
            Method tick = Method.MISSING;
            Method background = Method.MISSING;
            Method mouseClick = Method.MISSING;
            Method mouseDrag = Method.MISSING;
            Method mouseRelease = Method.MISSING;
            Method mouseScroll = Method.MISSING;
            Method key = Method.MISSING;
            while (!(cls == GuiElement.class || tick.hasSeen() && background.hasSeen() && mouseClick.hasSeen() && mouseDrag.hasSeen() && mouseRelease.hasSeen() && mouseScroll.hasSeen() && key.hasSeen())) {
                if (!tick.hasSeen()) {
                    tick = GuiElement.hasMethod(cls, "tick", new Class[0]);
                }
                if (!background.hasSeen()) {
                    background = GuiElement.hasMethod(cls, "drawBackground", Integer.TYPE, Integer.TYPE);
                }
                if (!mouseClick.hasSeen()) {
                    mouseClick = GuiElement.hasMethod(cls, "onMouseClick", Integer.TYPE, Integer.TYPE, MouseButton.class);
                }
                if (!mouseClick.hasSeen()) {
                    mouseClick = GuiElement.hasMethod(cls, "onMouseClick", Integer.TYPE, Integer.TYPE, MouseButton.class, Boolean.TYPE);
                }
                if (!mouseDrag.hasSeen()) {
                    mouseDrag = GuiElement.hasMethod(cls, "onMouseDrag", Integer.TYPE, Integer.TYPE, MouseButton.class, Long.TYPE);
                }
                if (!mouseDrag.hasSeen()) {
                    mouseDrag = GuiElement.hasMethod(cls, "onMouseDrag", Integer.TYPE, Integer.TYPE, MouseButton.class, Long.TYPE, Boolean.TYPE);
                }
                if (!mouseRelease.hasSeen()) {
                    mouseRelease = GuiElement.hasMethod(cls, "onMouseRelease", Integer.TYPE, Integer.TYPE, MouseButton.class);
                }
                if (!mouseRelease.hasSeen()) {
                    mouseRelease = GuiElement.hasMethod(cls, "onMouseRelease", Integer.TYPE, Integer.TYPE, MouseButton.class, Boolean.TYPE);
                }
                if (!mouseScroll.hasSeen()) {
                    mouseScroll = GuiElement.hasMethod(cls, "onMouseScroll", Integer.TYPE, Integer.TYPE, ScrollDirection.class);
                }
                if (!key.hasSeen()) {
                    key = GuiElement.hasMethod(cls, "onKeyTyped", Character.TYPE, Integer.TYPE);
                }
                cls = cls.getSuperclass();
            }
            subscriptions = new Subscriptions(tick.isPresent(), background.isPresent(), mouseClick.isPresent(), mouseDrag.isPresent(), mouseRelease.isPresent(), mouseScroll.isPresent(), key.isPresent());
            SUBSCRIPTIONS.put(this.getClass(), subscriptions);
        }
        return subscriptions;
    }

    public static final class Subscriptions {
        public final boolean tick;
        public final boolean background;
        public final boolean mouseClick;
        public final boolean mouseDrag;
        public final boolean mouseRelease;
        public final boolean mouseScroll;
        public final boolean key;

        Subscriptions(boolean tick, boolean background, boolean mouseClick, boolean mouseDrag, boolean mouseRelease, boolean mouseScroll, boolean key) {
            this.tick = tick;
            this.background = background;
            this.mouseClick = mouseClick;
            this.mouseDrag = mouseDrag;
            this.mouseRelease = mouseRelease;
            this.mouseScroll = mouseScroll;
            this.key = key;
        }

        public String toString() {
            return String.format("tick: %s, background: %s, mouseClick: %s, mouseDrag: %s, mouseRelease: %s, mouseScroll: %s, key: %s", this.tick, this.background, this.mouseClick, this.mouseDrag, this.mouseRelease, this.mouseScroll, this.key);
        }
    }

    private static enum Method {
        PRESENT,
        SKIPPED,
        MISSING;


        boolean hasSeen() {
            return this != MISSING;
        }

        boolean isPresent() {
            return this == PRESENT;
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD})
    protected static @interface SkippedMethod {
    }
}

