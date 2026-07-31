/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.Color
 *  mcjty.theoneprobe.api.IIconStyle
 *  mcjty.theoneprobe.apiimpl.styles.IconStyle
 */
package ic2.probeplugin.override.styles;

import mcjty.theoneprobe.api.Color;
import mcjty.theoneprobe.api.IIconStyle;
import mcjty.theoneprobe.apiimpl.styles.IconStyle;

public interface IIconStyleBuilder {
    public static IIconStyle create() {
        return new IconStyle();
    }

    public static IIconStyle color(Color color) {
        return new IconStyle().color(color);
    }

    public static IIconStyle color(int color) {
        return new IconStyle().color(color);
    }

    public static IIconStyle bounds(int width, int height) {
        return new IconStyle().width(width).height(height);
    }

    public static IIconStyle texture(int texWidth, int texHeight) {
        return new IconStyle().textureWidth(texWidth).textureHeight(texHeight);
    }
}

