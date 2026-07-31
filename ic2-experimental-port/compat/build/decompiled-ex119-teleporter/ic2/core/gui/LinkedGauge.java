/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.Ic2Gui;
import ic2.core.gui.Gauge;
import ic2.core.gui.dynamic.IGuiValueProvider;

public class LinkedGauge
extends Gauge<LinkedGauge> {
    private final IGuiValueProvider provider;
    protected final String name;

    public LinkedGauge(Ic2Gui<?> ic2Gui, int n, int n2, IGuiValueProvider iGuiValueProvider, String string, Gauge.IGaugeStyle iGaugeStyle) {
        super(ic2Gui, n, n2, iGaugeStyle.getProperties());
        this.provider = iGuiValueProvider;
        this.name = string;
    }

    @Override
    protected double getRatio() {
        return this.provider.getGuiValue(this.name);
    }
}

