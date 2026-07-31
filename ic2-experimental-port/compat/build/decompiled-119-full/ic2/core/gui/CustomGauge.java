/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.Ic2Gui;
import ic2.core.gui.Gauge;

public class CustomGauge
extends Gauge<CustomGauge> {
    private final IGaugeRatioProvider provider;

    public static CustomGauge asFuel(Ic2Gui<?> ic2Gui, int n, int n2, IGaugeRatioProvider iGaugeRatioProvider) {
        return new CustomGauge(ic2Gui, n, n2, iGaugeRatioProvider, Gauge.GaugeStyle.Fuel.properties);
    }

    public static CustomGauge create(Ic2Gui<?> ic2Gui, int n, int n2, IGaugeRatioProvider iGaugeRatioProvider, Gauge.GaugeStyle gaugeStyle) {
        return new CustomGauge(ic2Gui, n, n2, iGaugeRatioProvider, gaugeStyle.properties);
    }

    public CustomGauge(Ic2Gui<?> ic2Gui, int n, int n2, IGaugeRatioProvider iGaugeRatioProvider, Gauge.GaugeProperties gaugeProperties) {
        super(ic2Gui, n, n2, gaugeProperties);
        this.provider = iGaugeRatioProvider;
    }

    @Override
    protected double getRatio() {
        return this.provider.getRatio();
    }

    public static interface IGaugeRatioProvider {
        public double getRatio();
    }
}

