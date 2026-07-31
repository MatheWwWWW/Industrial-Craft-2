/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.IProgressStyle
 *  mcjty.theoneprobe.api.NumberFormat
 *  mcjty.theoneprobe.apiimpl.styles.ProgressStyle
 */
package ic2.probeplugin.override.styles;

import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.NumberFormat;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;

public interface IProgressStyleBuilder {
    public static IProgressStyle create() {
        return new ProgressStyle();
    }

    public static IProgressStyle armor() {
        return new ProgressStyle().armorBar(true);
    }

    public static IProgressStyle life() {
        return new ProgressStyle().lifeBar(true);
    }

    public static IProgressStyle aligned(ElementAlignment align) {
        return new ProgressStyle().alignment(align);
    }

    public static IProgressStyle bounds(int width, int height) {
        return new ProgressStyle().bounds(width, height);
    }

    public static IProgressStyle textOnly(String prefix) {
        return new ProgressStyle().prefix(prefix).numberFormat(NumberFormat.NONE);
    }

    public static IProgressStyle text(String prefix, String suffix) {
        return new ProgressStyle().prefix(prefix).suffix(suffix);
    }
}

