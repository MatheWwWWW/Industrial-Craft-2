/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.utils.tooltips.helper;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.ic2.PassiveGeneratorSetting;
import ic2.core.utils.helpers.Formatters;
import ic2.core.utils.tooltips.ILangHelper;
import ic2.core.utils.tooltips.helper.BasicTextToolTip;
import ic2.core.utils.tooltips.helper.EUReaderTooltip;
import ic2.core.utils.tooltips.helper.MultiplierConfigTranslationSupplier;
import ic2.core.utils.tooltips.helper.PassiveConfigTranslationSupplier;
import ic2.core.utils.tooltips.helper.RangeConfigTranslationSupplier;
import ic2.core.utils.tooltips.helper.SimpleConfigTranslationSupplier;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ITooltipProvider
extends ILangHelper {
    public static final ITooltipProvider LV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(32L));
    public static final ITooltipProvider MV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(128L));
    public static final ITooltipProvider HV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(512L));
    public static final ITooltipProvider EV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(2048L));
    public static final ITooltipProvider IV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(8192L));
    public static final ITooltipProvider LuV_MACHINE = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.sink_input", Formatters.EU_READER_FORMAT.format(32768L));
    public static final ITooltipProvider LV_OUT = ITooltipProvider.output(32);
    public static final ITooltipProvider MV_OUT = ITooltipProvider.output(128);
    public static final ITooltipProvider HV_OUT = ITooltipProvider.output(512);
    public static final ITooltipProvider EV_OUT = ITooltipProvider.output(2048);
    public static final ITooltipProvider IV_OUT = ITooltipProvider.output(8192);
    public static final ITooltipProvider LuV_OUT = ITooltipProvider.output(32768);
    public static final ITooltipProvider TRANSFORMER_PACKETS = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.source_packets", 4);
    public static final ITooltipProvider UNLOADER_PACKETS = ITooltipProvider.euReaderTooltip("tooltip.item.ic2.eu_reader.source_packets", 10);

    @OnlyIn(value=Dist.CLIENT)
    public void addInformation(ItemStack var1, BlockGetter var2, List<Component> var3, TooltipFlag var4);

    public static ITooltipProvider tooltip(String s) {
        return new BasicTextToolTip(s, new Object[0]);
    }

    public static ITooltipProvider tooltip(String s, Object ... args) {
        return new BasicTextToolTip(s, args);
    }

    public static ITooltipProvider euReaderTooltip(String s) {
        return new EUReaderTooltip(s, new Object[0]);
    }

    public static ITooltipProvider euReaderTooltip(String s, Object ... args) {
        return new EUReaderTooltip(s, args);
    }

    public static ITooltipProvider output(int euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.source_output", euATick);
    }

    public static ITooltipProvider productionVariable() {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.variable", new Object[0]);
    }

    public static ITooltipProvider production(int euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider production(int minEU, int maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider productionPassive(int euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.passive", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider productionPassive(int minEU, int maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.passive.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider consumption(int euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.consumption", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider consumptionUse(int euPerUse) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.use", Formatters.EU_READER_FORMAT.format(euPerUse));
    }

    public static ITooltipProvider consumption(int minEU, int maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.consumption.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider storage(int storage) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.storage", Formatters.EU_READER_FORMAT.format(storage));
    }

    public static ITooltipProvider production(float euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider production(float minEU, float maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider productionPassive(float euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.passive", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider productionPassive(float minEU, float maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.production.passive.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider consumption(float euATick) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.consumption", Formatters.EU_READER_FORMAT.format(euATick));
    }

    public static ITooltipProvider consumptionUse(float euPerUse) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.use", Formatters.EU_READER_FORMAT.format(euPerUse));
    }

    public static ITooltipProvider consumption(float minEU, float maxEU) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.consumption.range", Formatters.EU_READER_FORMAT.format(minEU), Formatters.EU_READER_FORMAT.format(maxEU));
    }

    public static ITooltipProvider storage(float storage) {
        return new EUReaderTooltip("tooltip.item.ic2.eu_reader.storage", Formatters.EU_READER_FORMAT.format(storage));
    }

    public static ITooltipProvider production(ConfigEntry<? extends Number> euATick) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production", Formatters.EU_READER_FORMAT, euATick));
    }

    public static ITooltipProvider production(ConfigEntry<? extends Number> euATick, float multiplier) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production", Formatters.EU_READER_FORMAT, euATick, multiplier));
    }

    public static ITooltipProvider productionPassive(ConfigEntry<? extends Number> euATick) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production.passive", Formatters.EU_READER_FORMAT, euATick));
    }

    public static ITooltipProvider consumption(ConfigEntry<? extends Number> euATick) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.consumption", Formatters.EU_READER_FORMAT, euATick));
    }

    public static ITooltipProvider consumptionUse(ConfigEntry<? extends Number> euPerUse) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.use", Formatters.EU_READER_FORMAT, euPerUse));
    }

    public static ITooltipProvider storage(ConfigEntry<? extends Number> storage) {
        return new EUReaderTooltip(new SimpleConfigTranslationSupplier("tooltip.item.ic2.eu_reader.storage", Formatters.EU_READER_FORMAT, storage));
    }

    public static ITooltipProvider production(double value, ConfigEntry<? extends Number> multiplier, boolean multiply) {
        return new EUReaderTooltip(new MultiplierConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production", Formatters.EU_READER_FORMAT, value, multiplier, multiply));
    }

    public static ITooltipProvider production(double minValue, double maxValue, ConfigEntry<? extends Number> multiplier, boolean multiply) {
        return new EUReaderTooltip(new RangeConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production.range", Formatters.EU_READER_FORMAT, minValue, maxValue, multiplier, multiply));
    }

    public static ITooltipProvider productionPassive(double minValue, double maxValue, PassiveGeneratorSetting generatorSettings, boolean isFuelProduction) {
        return new EUReaderTooltip(new PassiveConfigTranslationSupplier("tooltip.item.ic2.eu_reader.production.passive.range", Formatters.EU_READER_FORMAT, minValue, maxValue, generatorSettings, isFuelProduction));
    }
}

