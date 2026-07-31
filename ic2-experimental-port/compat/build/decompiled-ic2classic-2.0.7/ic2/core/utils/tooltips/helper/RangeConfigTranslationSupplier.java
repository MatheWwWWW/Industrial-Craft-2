/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.tooltips.helper.ConfigTranslationSupplier;
import java.text.NumberFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class RangeConfigTranslationSupplier
extends ConfigTranslationSupplier {
    private double minValue;
    private double maxValue;
    private ConfigEntry<? extends Number> multiplier;
    private boolean multiply;

    public RangeConfigTranslationSupplier(String key, NumberFormat formatter, double minValue, double maxValue, ConfigEntry<? extends Number> multiplier, boolean multiply) {
        super(key, formatter);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.multiplier = multiplier;
        this.multiply = multiply;
    }

    @Override
    protected Component buildTextComponent() {
        double mul = this.multiplier.getValue().doubleValue();
        return Component.m_237110_((String)this.key, (Object[])new Object[]{this.formatter.format(this.multiply ? this.minValue * mul : this.minValue / mul), this.formatter.format(this.multiply ? this.maxValue * mul : this.maxValue / mul)}).m_130940_(ChatFormatting.GRAY);
    }
}

