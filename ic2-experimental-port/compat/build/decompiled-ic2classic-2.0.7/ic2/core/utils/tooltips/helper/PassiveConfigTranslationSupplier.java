/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.utils.config.ic2.PassiveGeneratorSetting;
import ic2.core.utils.tooltips.helper.ConfigTranslationSupplier;
import java.text.NumberFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class PassiveConfigTranslationSupplier
extends ConfigTranslationSupplier {
    double minValue;
    double maxValue;
    PassiveGeneratorSetting generatorSetting;
    boolean isFuelProduction;

    public PassiveConfigTranslationSupplier(String key, NumberFormat formatter, double minValue, double maxValue, PassiveGeneratorSetting generatorSetting, boolean isFuelProduction) {
        super(key, formatter);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.generatorSetting = generatorSetting;
        this.isFuelProduction = isFuelProduction;
    }

    @Override
    protected Component buildTextComponent() {
        double min = this.minValue / (double)this.generatorSetting.getPassiveProduction();
        double max = this.maxValue / (double)this.generatorSetting.getPassiveProduction();
        if (this.isFuelProduction) {
            min *= (double)this.generatorSetting.getProduction();
            max *= (double)this.generatorSetting.getProduction();
        }
        return Component.m_237110_((String)this.key, (Object[])new Object[]{this.formatter.format(min), this.formatter.format(max)}).m_130940_(ChatFormatting.GRAY);
    }
}

