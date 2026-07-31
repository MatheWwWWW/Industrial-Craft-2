/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Suppliers
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.InventoryMenu
 */
package ic2.core.gui;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.ContainerBase;
import ic2.core.Ic2Gui;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import ic2.core.gui.ScrollDirection;
import ic2.core.gui.dynamic.TextProvider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.InventoryMenu;

public abstract class GuiElement<T extends GuiElement<T>> {
    protected static final int hoverColor = -2130706433;
    public static final ResourceLocation commonTexture = new ResourceLocation("ic2", "textures/gui/common.png");
    private static final Map<Class<?>, Set<ImplementedMethod>> IMPLEMENTED_METHOD_CACHE = new IdentityHashMap();
    protected final Ic2Gui<?> gui;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private IEnableHandler enableHandler;
    private Supplier<String> tooltipProvider;

    protected GuiElement(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4) {
        if (n3 < 0) {
            throw new IllegalArgumentException("negative width");
        }
        if (n4 < 0) {
            throw new IllegalArgumentException("negative height");
        }
        this.gui = ic2Gui;
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
    }

    public final boolean isEnabled() {
        return this.enableHandler == null || this.enableHandler.isEnabled();
    }

    public boolean contains(int n, int n2) {
        return n >= this.x && n <= this.x + this.width && n2 >= this.y && n2 <= this.y + this.height;
    }

    public T withEnableHandler(IEnableHandler iEnableHandler) {
        this.enableHandler = iEnableHandler;
        return (T)this;
    }

    public T withTooltip(String string) {
        return this.withTooltip((Supplier<String>)Suppliers.ofInstance((Object)string));
    }

    public T withTooltip(Supplier<String> supplier) {
        this.tooltipProvider = supplier;
        return (T)this;
    }

    public void tick() {
    }

    public void drawBackground(PoseStack poseStack, int n, int n2) {
    }

    public void drawForeground(PoseStack poseStack, int n, int n2) {
        if (this.contains(n, n2) && !this.suppressTooltip(n, n2)) {
            String string;
            List<Component> list = this.getToolTip();
            if (this.tooltipProvider != null && (string = this.tooltipProvider.get()) != null && !string.isEmpty()) {
                GuiElement.addLines(list, string);
            }
            if (!list.isEmpty()) {
                this.gui.drawTooltip(n, n2, list);
            }
        }
    }

    private static void addLines(List<Component> list, String string) {
        int n;
        int n2 = 0;
        while ((n = string.indexOf(10, n2)) != -1) {
            list.add(GuiElement.processText(string.substring(n2, n)));
            n2 = n + 1;
        }
        if (n2 == 0) {
            list.add(GuiElement.processText(string));
        } else {
            list.add(GuiElement.processText(string.substring(n2)));
        }
    }

    public boolean onMouseClick(int n, int n2, MouseButton mouseButton, boolean bl) {
        return bl && this.onMouseClick(n, n2, mouseButton);
    }

    protected boolean onMouseClick(int n, int n2, MouseButton mouseButton) {
        return false;
    }

    public boolean onMouseDrag(int n, int n2, MouseButton mouseButton, boolean bl) {
        return bl && this.onMouseDrag(n, n2, mouseButton);
    }

    protected boolean onMouseDrag(int n, int n2, MouseButton mouseButton) {
        return false;
    }

    public boolean onMouseRelease(int n, int n2, MouseButton mouseButton, boolean bl) {
        return bl && this.onMouseRelease(n, n2, mouseButton);
    }

    protected boolean onMouseRelease(int n, int n2, MouseButton mouseButton) {
        return false;
    }

    public void onMouseScroll(int n, int n2, ScrollDirection scrollDirection) {
    }

    public boolean onKeyTyped(char c, int n) {
        return false;
    }

    protected boolean suppressTooltip(int n, int n2) {
        return false;
    }

    protected List<Component> getToolTip() {
        return new ArrayList<Component>();
    }

    protected static Component processText(String string) {
        return Component.m_237115_((String)string);
    }

    protected final Container getBase() {
        return ((ContainerBase)((Object)this.gui.getContainer())).base;
    }

    protected final Map<String, TextProvider.ITextProvider> getTokens() {
        HashMap<String, TextProvider.ITextProvider> hashMap = new HashMap<String, TextProvider.ITextProvider>();
        hashMap.put("name", TextProvider.of(this.gui.m_96636_()));
        return hashMap;
    }

    protected static void bindTexture(ResourceLocation resourceLocation) {
        Ic2Gui.bindTexture(resourceLocation);
    }

    public static void bindCommonTexture() {
        Ic2Gui.bindTexture(commonTexture);
    }

    protected static void bindBlockTexture() {
        Ic2Gui.bindTexture(InventoryMenu.f_39692_);
    }

    protected static TextureAtlas getBlockTextureMap() {
        return (TextureAtlas)Minecraft.m_91087_().m_91097_().m_118506_(InventoryMenu.f_39692_);
    }

    public final Set<ImplementedMethod> getImplementedMethods() {
        Class<?> clazz = this.getClass();
        Set<ImplementedMethod> set = IMPLEMENTED_METHOD_CACHE.get(clazz);
        if (set == null) {
            set = EnumSet.noneOf(ImplementedMethod.class);
            for (Class<?> clazz2 = clazz; clazz2 != GuiElement.class; clazz2 = clazz2.getSuperclass()) {
                for (Method method : clazz2.getDeclaredMethods()) {
                    ImplementedMethod implementedMethod;
                    if ((method.getModifiers() & 0xA) != 0 || (implementedMethod = ImplementedMethod.LOOKUP.get(method.getName())) == null || set.contains((Object)implementedMethod) || method.isAnnotationPresent(SkippedMethod.class)) continue;
                    set.add(implementedMethod);
                }
            }
            IMPLEMENTED_METHOD_CACHE.put(clazz, set);
        }
        return set;
    }

    public static enum ImplementedMethod {
        tick,
        drawBackground,
        drawForeground,
        onMouseClick,
        onMouseDrag,
        onMouseRelease,
        onMouseScroll,
        onKeyTyped;

        static final Map<String, ImplementedMethod> LOOKUP;

        private static Map<String, ImplementedMethod> createLookup() {
            ImplementedMethod[] implementedMethodArray = ImplementedMethod.values();
            HashMap<String, ImplementedMethod> hashMap = new HashMap<String, ImplementedMethod>(implementedMethodArray.length);
            for (ImplementedMethod implementedMethod : implementedMethodArray) {
                hashMap.put(implementedMethod.name(), implementedMethod);
            }
            return hashMap;
        }

        static {
            LOOKUP = ImplementedMethod.createLookup();
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD})
    protected static @interface SkippedMethod {
    }
}

