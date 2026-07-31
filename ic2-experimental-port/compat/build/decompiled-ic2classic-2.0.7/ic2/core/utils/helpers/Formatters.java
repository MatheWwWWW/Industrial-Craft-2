/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.helpers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formatters {
    public static final DecimalFormat F_POINT_2 = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat XP_FORMAT = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat EU_FORMAT = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat BREWING = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat EU_READER_FORMAT = new DecimalFormat("###,###.##", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat CABLE_LOSS_FORMAT = new DecimalFormat("0.####", new DecimalFormatSymbols(Locale.US));
}

