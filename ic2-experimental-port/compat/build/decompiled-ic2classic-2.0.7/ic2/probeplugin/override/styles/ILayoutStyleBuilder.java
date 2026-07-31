/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.Color
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.ILayoutStyle
 *  mcjty.theoneprobe.apiimpl.styles.LayoutStyle
 */
package ic2.probeplugin.override.styles;

import mcjty.theoneprobe.api.Color;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;

public interface ILayoutStyleBuilder {
    public static ILayoutStyle create() {
        return new LayoutStyle();
    }

    public static ILayoutStyle border(Color color) {
        return new LayoutStyle().borderColor(color);
    }

    public static ILayoutStyle border(Integer color) {
        return new LayoutStyle().borderColor(color);
    }

    public static ILayoutStyle spacing(int spacing) {
        return new LayoutStyle().spacing(spacing);
    }

    public static ILayoutStyle aligned(ElementAlignment align) {
        return new LayoutStyle().alignment(align);
    }

    public static ILayoutStyle padding(int padding) {
        return new LayoutStyle().padding(padding);
    }

    public static ILayoutStyle padding(int xPadding, int yPadding) {
        return new LayoutStyle().hPadding(xPadding).vPadding(yPadding);
    }

    public static ILayoutStyle padding(int top, int bottom, int left, int right) {
        return new LayoutStyle().topPadding(top).bottomPadding(bottom).leftPadding(left).rightPadding(right);
    }
}

