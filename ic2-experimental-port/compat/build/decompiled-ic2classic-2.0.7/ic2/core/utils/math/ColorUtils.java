/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 */
package ic2.core.utils.math;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public class ColorUtils {
    static final float DIVIDER = 0.003921569f;
    public static final int WHITE = -1;
    public static final int LIGHT_GRAY = ColorUtils.rgb(192, 192, 192);
    public static final int GRAY = ColorUtils.rgb(128, 128, 128);
    public static final int DARK_GRAY = ColorUtils.rgb(64, 64, 64);
    public static final int BLACK = -16777216;
    public static final int RED = ColorUtils.rgb(255, 0, 0);
    public static final int PINK = ColorUtils.rgb(255, 175, 175);
    public static final int ORANGE = ColorUtils.rgb(255, 200, 0);
    public static final int YELLOW = ColorUtils.rgb(255, 255, 0);
    public static final int GREEN = ColorUtils.rgb(0, 255, 0);
    public static final int MAGENTA = ColorUtils.rgb(255, 0, 255);
    public static final int CYAN = ColorUtils.rgb(0, 255, 255);
    public static final int BLUE = ColorUtils.rgb(0, 0, 255);

    public static int rgb(int r, int g, int b) {
        return ColorUtils.rgb(r, g, b, 255);
    }

    public static int rgb(int r, int g, int b, int a) {
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int rgb(float r, float g, float b) {
        return ColorUtils.rgb(r, g, b, 1.0f);
    }

    public static int rgb(float r, float g, float b, float a) {
        return ColorUtils.rgb((int)((double)(r * 255.0f) + 0.5), (int)((double)(g * 255.0f) + 0.5), (int)((double)(b * 255.0f) + 0.5), (int)((double)(a * 255.0f) + 0.5));
    }

    public static float[] rgbFloat(int color) {
        return new float[]{ColorUtils.getFloatR(color), ColorUtils.getFloatG(color), ColorUtils.getFloatB(color)};
    }

    public static int getRed(int rgb) {
        return rgb >> 16 & 0xFF;
    }

    public static int getGreen(int rgb) {
        return rgb >> 8 & 0xFF;
    }

    public static int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    public static int getAlpha(int rgb) {
        return rgb >> 24 & 0xFF;
    }

    public static float getFloatR(int rgb) {
        return (float)(rgb >> 16 & 0xFF) * 0.003921569f;
    }

    public static float getFloatG(int rgb) {
        return (float)(rgb >> 8 & 0xFF) * 0.003921569f;
    }

    public static float getFloatB(int rgb) {
        return (float)(rgb & 0xFF) * 0.003921569f;
    }

    public static float getFloatA(int rgb) {
        return (float)(rgb >> 24 & 0xFF) * 0.003921569f;
    }

    public static boolean needsDarkColor(int rgba) {
        return ColorUtils.getBrightness(rgba) >= 130;
    }

    public static int getBrightness(int rgba) {
        return ColorUtils.getBrightness(rgba >> 16 & 0xFF, rgba >> 8 & 0xFF, rgba & 0xFF);
    }

    public static int getBrightness(int r, int g, int b) {
        return (int)Math.sqrt((float)(r * r) * 0.241f + (float)(g * g) * 0.691f + (float)(b * b) * 0.068f);
    }

    public static int mixColors(int from, int to, float progress) {
        float weight0 = 1.0f - progress;
        float weight1 = progress;
        int r = (int)((float)(from >> 16 & 0xFF) * weight0 + (float)(to >> 16 & 0xFF) * weight1);
        int g = (int)((float)(from >> 8 & 0xFF) * weight0 + (float)(to >> 8 & 0xFF) * weight1);
        int b = (int)((float)(from & 0xFF) * weight0 + (float)(to & 0xFF) * weight1);
        int a = (int)((float)(from >> 24 & 0xFF) * weight0 + (float)(to >> 24 & 0xFF) * weight1);
        return (a & 0xFF) << 24 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int doubleDarker(int color) {
        return ColorUtils.darker(ColorUtils.darker(color));
    }

    public static int trippleDarker(int color) {
        return ColorUtils.darker(ColorUtils.darker(ColorUtils.darker(color)));
    }

    public static int darker(int color) {
        return ColorUtils.darker(color, 0.7f);
    }

    public static int darker(int color, float factor) {
        if (Float.compare(factor, 1.0f) == 0) {
            return color;
        }
        int r = Math.max(0, (int)((float)(color >> 16 & 0xFF) * factor));
        int g = Math.max(0, (int)((float)(color >> 8 & 0xFF) * factor));
        int b = Math.max(0, (int)((float)(color & 0xFF) * factor));
        return color & 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }

    public static int doubleBrighter(int color) {
        return ColorUtils.brighter(ColorUtils.brighter(color));
    }

    public static int brighter(int color) {
        return ColorUtils.brighter(color, 0.7f);
    }

    public static int brighter(int color, float factor) {
        if (Float.compare(factor, 1.0f) == 0) {
            return color;
        }
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int i = (int)(1.0 / (1.0 - (double)factor));
        if (r == 0 && g == 0 && b == 0) {
            return color & 0xFF000000 | (i & 0xFF) << 16 | (i & 0xFF) << 8 | i & 0xFF;
        }
        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }
        return color & 0xFF000000 | Math.min(255, (int)((float)r / factor)) << 16 | Math.min(255, (int)((float)g / factor)) << 8 | Math.min(255, (int)((float)b / factor));
    }

    public static Style toColor(int color) {
        return Style.f_131099_.m_131148_(TextColor.m_131266_((int)(color & 0xFFFFFF)));
    }

    public static String toHex(int color) {
        return "#" + Integer.toHexString(0x1000000 | color & 0xFFFFFF).substring(1);
    }
}

