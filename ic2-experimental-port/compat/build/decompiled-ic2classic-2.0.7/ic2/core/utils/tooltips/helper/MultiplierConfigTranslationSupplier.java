/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.IC2;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.tooltips.helper.ConfigTranslationSupplier;
import java.text.NumberFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class MultiplierConfigTranslationSupplier
extends ConfigTranslationSupplier {
    private double value;
    private ConfigEntry<? extends Number> multiplier;
    private boolean multiply;

    public MultiplierConfigTranslationSupplier(String key, NumberFormat formatter, double value, ConfigEntry<? extends Number> multiplier, boolean multiply) {
        super(key, formatter);
        this.value = value;
        this.multiplier = multiplier;
        this.multiply = multiply;
    }

    @Override
    protected Component buildTextComponent() {
        double mul = this.multiplier.getValue().doubleValue();
        IC2.LOGGER.info("Tets: " + mul + ", " + this.value);
        return Component.m_237110_((String)this.key, (Object[])new Object[]{this.formatter.format(this.multiply ? this.value * mul : this.value / mul)}).m_130940_(ChatFormatting.GRAY);
    }
}

