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

public class SimpleConfigTranslationSupplier
extends ConfigTranslationSupplier {
    private ConfigEntry<? extends Number> value;
    float multiplier = 1.0f;

    public SimpleConfigTranslationSupplier(String key, NumberFormat formatter, ConfigEntry<? extends Number> value) {
        this(key, formatter, value, 1.0f);
    }

    public SimpleConfigTranslationSupplier(String key, NumberFormat formatter, ConfigEntry<? extends Number> value, float multiplier) {
        super(key, formatter);
        this.value = value;
        this.multiplier = multiplier;
    }

    @Override
    protected Component buildTextComponent() {
        return Component.m_237110_((String)this.key, (Object[])new Object[]{this.formatter.format(this.value.getValue().floatValue() * this.multiplier)}).m_130940_(ChatFormatting.GRAY);
    }
}

