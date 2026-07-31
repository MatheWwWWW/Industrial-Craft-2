/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.ContainerBase;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.gui.dynamic.TextProvider;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;

public class TextLabel
extends GuiElement<TextLabel> {
    private final TextProvider.ITextProvider textProvider;
    private final IntSupplier color;
    private final boolean shadow;
    private final boolean fixedHoverWidth;
    private final boolean fixedHoverHeight;
    private final int baseX;
    private final int baseY;
    private final boolean centerX;
    private final boolean centerY;

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, String string, int n3, boolean bl) {
        return TextLabel.create(ic2Gui, n, n2, TextProvider.of(string), n3, bl);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, TextProvider.ITextProvider iTextProvider, int n3, boolean bl) {
        return TextLabel.create(ic2Gui, n, n2, iTextProvider, n3, bl, false, false);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, String string, int n3, boolean bl, boolean bl2, boolean bl3) {
        return TextLabel.create(ic2Gui, n, n2, TextProvider.of(string), n3, bl, bl2, bl3);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, TextProvider.ITextProvider iTextProvider, int n3, boolean bl, boolean bl2, boolean bl3) {
        return TextLabel.create(ic2Gui, n, n2, -1, -1, iTextProvider, n3, bl, bl2, bl3);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, TextProvider.ITextProvider iTextProvider, int n5, boolean bl, boolean bl2, boolean bl3) {
        return TextLabel.create(ic2Gui, n, n2, n3, n4, iTextProvider, n5, bl, 0, 0, bl2, bl3);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, TextProvider.ITextProvider iTextProvider, int n5, boolean bl, int n6, int n7, boolean bl2, boolean bl3) {
        return TextLabel.create(ic2Gui, n, n2, n3, n4, iTextProvider, () -> n5, bl, n6, n7, bl2, bl3);
    }

    public static TextLabel createRightAligned(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, TextProvider.ITextProvider iTextProvider, int n5, boolean bl, int n6, int n7, boolean bl2, boolean bl3) {
        return TextLabel.create(ic2Gui, n, n2, n3, n4, iTextProvider, () -> n5, bl, n6 - TextLabel.getWidth(ic2Gui, iTextProvider), n7, bl2, bl3);
    }

    public static TextLabel create(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, TextProvider.ITextProvider iTextProvider, IntSupplier intSupplier, boolean bl, int n5, int n6, boolean bl2, boolean bl3) {
        boolean bl4;
        boolean bl5;
        if (n3 < 0) {
            bl5 = false;
            n3 = TextLabel.getWidth(ic2Gui, iTextProvider);
        } else {
            bl5 = true;
        }
        if (n4 < 0) {
            bl4 = false;
            n4 = 8;
        } else {
            bl4 = true;
        }
        int n7 = n + n5;
        int n8 = n2 + n6;
        if (bl2) {
            if (bl5) {
                n7 += n3 / 2;
            } else {
                n -= n3 / 2;
            }
        }
        if (bl3) {
            if (bl4) {
                n8 += (n4 + 1) / 2;
            } else {
                n2 -= n4 / 2;
            }
        }
        return new TextLabel(ic2Gui, n, n2, n3, n4, iTextProvider, intSupplier, bl, bl5, bl4, n7, n8, bl2, bl3);
    }

    private TextLabel(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, TextProvider.ITextProvider iTextProvider, IntSupplier intSupplier, boolean bl, boolean bl2, boolean bl3, int n5, int n6, boolean bl4, boolean bl5) {
        super(ic2Gui, n, n2, n3, n4);
        this.textProvider = iTextProvider;
        this.color = intSupplier;
        this.shadow = bl;
        this.fixedHoverWidth = bl2;
        this.fixedHoverHeight = bl3;
        this.baseX = n5;
        this.baseY = n6;
        this.centerX = bl4;
        this.centerY = bl5;
    }

    private static int getWidth(Ic2Gui<?> ic2Gui, TextProvider.ITextProvider iTextProvider) {
        String string = iTextProvider.get(((ContainerBase)((Object)ic2Gui.getContainer())).base, TextProvider.emptyTokens());
        if (string.isEmpty()) {
            return 0;
        }
        return Minecraft.m_91087_().f_91062_.m_92895_(string);
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        int n3;
        int n4;
        String string = this.textProvider.get(this.getBase(), this.getTokens());
        if (string.isEmpty()) {
            n4 = 0;
            n3 = 0;
        } else {
            n3 = this.gui.getStringWidth(string);
            n4 = 8;
        }
        int n5 = this.baseX;
        if (this.centerX) {
            n5 -= n3 / 2;
        }
        int n6 = this.baseY;
        if (this.centerY) {
            n6 -= n4 / 2;
        }
        if (!this.fixedHoverWidth) {
            this.x = n5;
            this.width = n3;
        }
        if (!this.fixedHoverHeight) {
            this.y = n6;
            this.height = n4;
        }
        super.drawBackground(poseStack, n, n2);
        if (!string.isEmpty()) {
            this.gui.drawString(poseStack, n5, n6, string, this.color.getAsInt(), this.shadow);
        }
    }

    public static enum TextAlignment {
        Start,
        Center,
        End;

        private static final Map<String, TextAlignment> map;
        public final String name = this.name().toLowerCase(Locale.ENGLISH);

        public static TextAlignment get(String string) {
            return map.get(string);
        }

        private static Map<String, TextAlignment> getMap() {
            TextAlignment[] textAlignmentArray = TextAlignment.values();
            HashMap<String, TextAlignment> hashMap = new HashMap<String, TextAlignment>(textAlignmentArray.length);
            for (TextAlignment textAlignment : textAlignmentArray) {
                hashMap.put(textAlignment.name, textAlignment);
            }
            return hashMap;
        }

        static {
            map = TextAlignment.getMap();
        }
    }
}

