/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.ITextStyle
 *  mcjty.theoneprobe.apiimpl.styles.TextStyle
 */
package ic2.probeplugin.override.styles;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.ITextStyle;
import mcjty.theoneprobe.apiimpl.styles.TextStyle;

public interface ITextStyleBuilder {
    public static ITextStyle create() {
        return new TextStyle();
    }

    public static ITextStyle aligned(ElementAlignment align) {
        return new TextStyle().alignment(align);
    }

    public static ITextStyle width(Integer width) {
        return new TextStyle().width(width);
    }

    public static ITextStyle height(Integer height) {
        return new TextStyle().height(height);
    }

    public static ITextStyle bounds(Integer width, Integer height) {
        return new TextStyle().width(width).height(height);
    }

    public static ITextStyle padding(int padding) {
        return new TextStyle().padding(padding);
    }

    public static ITextStyle padding(int xPadding, int yPadding) {
        return new TextStyle().hPadding(yPadding).vPadding(xPadding);
    }

    public static ITextStyle padding(int top, int bottom, int left, int right) {
        return new TextStyle().topPadding(top).bottomPadding(bottom).leftPadding(left).rightPadding(right);
    }
}

