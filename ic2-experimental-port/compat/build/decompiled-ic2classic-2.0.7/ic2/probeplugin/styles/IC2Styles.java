/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mcjty.theoneprobe.api.ElementAlignment
 *  mcjty.theoneprobe.api.ILayoutStyle
 *  mcjty.theoneprobe.api.IProgressStyle
 *  mcjty.theoneprobe.api.NumberFormat
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package ic2.probeplugin.styles;

import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.helpers.SanityHelper;
import ic2.core.utils.math.ColorUtils;
import ic2.probeplugin.base.ProbePluginHelper;
import ic2.probeplugin.override.styles.ILayoutStyleBuilder;
import ic2.probeplugin.override.styles.IProgressStyleBuilder;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.ILayoutStyle;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.NumberFormat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class IC2Styles {
    public static final int DEFAULT_BAR_WIDTH = 118;
    public static final int DEFAULT_BAR_HEIGHT = 12;
    private static final int EU_BAR_COLOR = -61184;
    private static final int PROGRESS_BAR_COLOR = -16733185;
    private static final int SPEED_BAR_COLOR = -295680;
    private static final int MATERIAL_BAR_COLOR = -5829955;
    private static final int XP_BAR_COLOR = -15949283;
    private static final int UU_BAR_COLOR = -4441721;
    private static final int SCRAP_BAR_COLOR = -10996205;
    private static final int WHEAT_BAR_COLOR = -1189115;
    private static final int HOPS_BAR_COLOR = -16337649;
    private static final int REACTOR_COLD_COLOR = -16337649;
    private static final int REACTOR_WARM_COLOR = -1189115;
    private static final int REACTOR_HOT_COLOR = -1203707;
    private static final int REACTOR_VERY_HOT = -1245179;
    public static final int LAVA_COLOR = -29925;
    public static final IProgressStyle FUEL_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(ColorUtils.DARK_GRAY, ColorUtils.darker(ColorUtils.DARK_GRAY), ColorUtils.brighter(ColorUtils.DARK_GRAY)).numberFormat(NumberFormat.COMMAS).prefix("ic2.probe.fuel.storage.name");
    public static final IProgressStyle EU_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-61184, ColorUtils.darker(-61184), ColorUtils.doubleDarker(-61184)).numberFormat(NumberFormat.COMMAS);
    public static final IProgressStyle PROGRESS_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-16733185, ColorUtils.darker(-16733185), ColorUtils.doubleDarker(-16733185)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle SPEED_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-295680, ColorUtils.darker(-295680), ColorUtils.doubleDarker(-295680)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle MATERIAL_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-5829955, ColorUtils.darker(-5829955), ColorUtils.doubleDarker(-5829955)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle XP_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-15949283, -15949283, ColorUtils.darker(-15949283)).numberFormat(NumberFormat.FULL).prefix("ic2.probe.xp.prefix.name").suffix("ic2.probe.xp.suffix.name");
    public static final IProgressStyle UU_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-4441721, ColorUtils.darker(-4441721), ColorUtils.doubleDarker(-4441721)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle SCRAP_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-10996205, ColorUtils.darker(-10996205), ColorUtils.doubleDarker(-10996205)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle WHEAT_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-1189115, ColorUtils.darker(-1189115), ColorUtils.doubleDarker(-1189115)).numberFormat(NumberFormat.NONE);
    public static final IProgressStyle HOPS_BAR = IProgressStyleBuilder.bounds(118, 12).borderlessColor(-16337649, ColorUtils.darker(-16337649), ColorUtils.doubleDarker(-16337649)).numberFormat(NumberFormat.NONE);
    public static final ILayoutStyle HEADER_STYLE = ILayoutStyleBuilder.spacing(1);
    public static final ILayoutStyle INNER_STYLE = ILayoutStyleBuilder.spacing(1);
    public static final ILayoutStyle OUTER_STYLE = ILayoutStyleBuilder.spacing(5);
    public static final ILayoutStyle BARS_STYLE = ILayoutStyleBuilder.spacing(-1);
    public static final ILayoutStyle CENTER_STYLE = ILayoutStyleBuilder.aligned(ElementAlignment.ALIGN_CENTER);
    public static final ILayoutStyle SLOT_CONTAINER_STYLE = ILayoutStyleBuilder.create().leftPadding(5);
    public static final ILayoutStyle SLOTS_STYLE = ILayoutStyleBuilder.spacing(-3).borderColor(Integer.valueOf(-1)).leftPadding(2);

    public static IProgressStyle crops(String display, int color, Object ... args) {
        return IProgressStyleBuilder.bounds(100, 11).borderlessColor(color, ColorUtils.brighter(color), ColorUtils.trippleDarker(color)).alignment(ElementAlignment.ALIGN_CENTER).prefix(display, args).numberFormat(NumberFormat.NONE);
    }

    public static IProgressStyle enricher(int color, String name, int stored) {
        return IProgressStyleBuilder.bounds(118, 12).prefix("ic2.probe.uranium.type.name", new Object[]{SanityHelper.toPascalCase(name), stored}).numberFormat(NumberFormat.NONE).borderlessColor(color, ColorUtils.darker(color), ColorUtils.doubleDarker(color));
    }

    public static IProgressStyle reactorBar(int currentProgress, int maxProgress) {
        String current;
        float progress = (float)currentProgress / (float)maxProgress;
        Object object = currentProgress > 1500000 ? Formatters.EU_READER_FORMAT.format((double)currentProgress / 1000000.0) + "m" : (current = currentProgress > 5000 ? Formatters.EU_READER_FORMAT.format((double)currentProgress / 1000.0) + "k" : Integer.toString(currentProgress));
        String max = maxProgress > 1500000 ? Formatters.EU_READER_FORMAT.format((double)maxProgress / 1000000.0) + "m" : (maxProgress > 5000 ? Formatters.EU_READER_FORMAT.format((double)maxProgress / 1000.0) + "k" : Integer.toString(maxProgress));
        return IC2Styles.applyReactorColor(PROGRESS_BAR.copy().prefix("ic2.probe.reactor.heat.name", new Object[]{current, max}), progress);
    }

    private static IProgressStyle applyReactorColor(IProgressStyle style, float progress) {
        if ((double)progress < 0.25) {
            return style.borderlessColor(-16337649, ColorUtils.darker(-16337649), ColorUtils.doubleDarker(-16337649));
        }
        if ((double)progress < 0.5) {
            return style.borderlessColor(-1189115, ColorUtils.darker(-1189115), ColorUtils.doubleDarker(-1189115));
        }
        if ((double)progress < 0.75) {
            return style.borderlessColor(-1203707, ColorUtils.darker(-1203707), ColorUtils.doubleDarker(-1203707));
        }
        return style.borderlessColor(-1245179, ColorUtils.darker(-1245179), ColorUtils.doubleDarker(-1245179));
    }

    public static IProgressStyle progressBar(int currentProgress, int maxProgress) {
        if (maxProgress > 360000) {
            return IC2Styles.progressBar(currentProgress / 72000, maxProgress / 72000, "h");
        }
        if (maxProgress > 6000) {
            return IC2Styles.progressBar(currentProgress / 1200, maxProgress / 1200, "m");
        }
        if (maxProgress > 800) {
            return IC2Styles.progressBar(currentProgress / 20, maxProgress / 20, "s");
        }
        return IC2Styles.progressBar(currentProgress, maxProgress, " t");
    }

    public static IProgressStyle progressBar(int currentProgress, int maxProgress, String prefix, String suffix) {
        if (maxProgress > 2500000) {
            return IC2Styles.progressBar(currentProgress / 1000000, maxProgress / 1000000, prefix + "m" + suffix);
        }
        if (maxProgress > 2500) {
            return IC2Styles.progressBar(currentProgress / 1000, maxProgress / 1000, prefix + "k" + suffix);
        }
        return IC2Styles.progressBar(currentProgress, maxProgress, prefix + suffix);
    }

    public static IProgressStyle oceanWater(int waterFound, int maxWater, String unit) {
        return PROGRESS_BAR.copy().prefix("ic2.probe.water.full.name", new Object[]{ProbePluginHelper.formatInt(waterFound, 4), maxWater + unit});
    }

    public static IProgressStyle oceanCorals(int coralsFound, int maxCorals, String unit) {
        return UU_BAR.copy().prefix("ic2.probe.corals.full.name", new Object[]{ProbePluginHelper.formatInt(coralsFound, 2), maxCorals + unit});
    }

    public static IProgressStyle progressBar(int currentProgress, int maxProgress, String unit) {
        return PROGRESS_BAR.copy().prefix("ic2.probe.progress.full.name", new Object[]{ProbePluginHelper.formatInt(currentProgress, String.valueOf(maxProgress).length(), true), maxProgress + unit});
    }

    public static IProgressStyle secondaryProgressBar(int currentProgress, int maxProgress, String unit) {
        return PROGRESS_BAR.copy().prefix("ic2.probe.progress.secondary.full.name", new Object[]{ProbePluginHelper.formatInt(currentProgress, String.valueOf(maxProgress).length(), true), maxProgress + unit});
    }

    public static IProgressStyle tank(int color, MutableComponent fluidName, int currentAmount, int maxAmount) {
        return IProgressStyleBuilder.bounds(118, 12).borderlessColor(color, color, ColorUtils.doubleDarker(color)).prefix((Component)fluidName.m_130946_(": ").m_130946_(ProbePluginHelper.formatInt(currentAmount, 4)).m_130946_("/").m_130946_(ProbePluginHelper.formatInt(maxAmount, 3)).m_130946_(" mB")).bounds(118, 12).numberFormat(NumberFormat.NONE);
    }
}

