/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 */
package ic2.core.gui;

import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.comp.Energy;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.Gauge;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.network.chat.Component;

public class EnergyGauge
extends Gauge<EnergyGauge> {
    private static final boolean useCleanEnergyValues = false;
    private final Energy energy;

    public static EnergyGauge asBar(Ic2Gui<?> ic2Gui, int n, int n2, Ic2TileEntity ic2TileEntity) {
        return new EnergyGauge(ic2Gui, n, n2, ic2TileEntity, EnergyGaugeStyle.Bar);
    }

    public static EnergyGauge asBolt(Ic2Gui<?> ic2Gui, int n, int n2, Ic2TileEntity ic2TileEntity) {
        return new EnergyGauge(ic2Gui, n, n2, ic2TileEntity, EnergyGaugeStyle.Bolt);
    }

    public EnergyGauge(Ic2Gui<?> ic2Gui, int n, int n2, Ic2TileEntity ic2TileEntity, EnergyGaugeStyle energyGaugeStyle) {
        super(ic2Gui, n, n2, energyGaugeStyle.properties);
        this.energy = ic2TileEntity.getComponent(Energy.class);
    }

    @Override
    protected List<Component> getToolTip() {
        List<Component> list = super.getToolTip();
        double d = this.energy.getEnergy();
        double d2 = this.energy.getCapacity();
        list.add((Component)Component.m_237113_((String)(Util.toSiString(d, 4) + "/" + Util.toSiString(d2, 4) + " " + Localization.translate("ic2.generic.text.EU"))));
        return list;
    }

    @Override
    protected double getRatio() {
        return this.energy.getFillRatio();
    }

    public static enum EnergyGaugeStyle {
        Bar(new Gauge.GaugePropertyBuilder(132, 43, 24, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-4, -11, 32, 32, 128, 0).build()),
        Bolt(new Gauge.GaugePropertyBuilder(116, 65, 7, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up).withBackground(-4, -1, 16, 16, 96, 64).build()),
        StirlingBar(new Gauge.GaugePropertyBuilder(176, 15, 58, 14, Gauge.GaugePropertyBuilder.GaugeOrientation.Right).withTexture(IC2.getIdentifier("textures/gui/guistirlinggenerator.png")).withBackground(59, 33).build());

        private static final Map<String, EnergyGaugeStyle> map;
        public final String name = this.name().toLowerCase(Locale.ENGLISH);
        public final Gauge.GaugeProperties properties;

        private EnergyGaugeStyle(Gauge.GaugeProperties gaugeProperties) {
            this.properties = gaugeProperties;
        }

        public static EnergyGaugeStyle get(String string) {
            return map.get(string);
        }

        private static Map<String, EnergyGaugeStyle> getMap() {
            EnergyGaugeStyle[] energyGaugeStyleArray = EnergyGaugeStyle.values();
            HashMap<String, EnergyGaugeStyle> hashMap = new HashMap<String, EnergyGaugeStyle>(energyGaugeStyleArray.length);
            for (EnergyGaugeStyle energyGaugeStyle : energyGaugeStyleArray) {
                hashMap.put(energyGaugeStyle.name, energyGaugeStyle);
            }
            return hashMap;
        }

        static {
            map = EnergyGaugeStyle.getMap();
        }
    }
}

