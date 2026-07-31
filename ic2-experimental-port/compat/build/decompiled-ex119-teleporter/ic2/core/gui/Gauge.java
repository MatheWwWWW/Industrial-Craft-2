/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;

public abstract class Gauge<T extends Gauge<T>>
extends GuiElement<T> {
    protected final GaugeProperties properties;

    protected Gauge(Ic2Gui<?> ic2Gui, int n, int n2, GaugeProperties gaugeProperties) {
        super(ic2Gui, n + gaugeProperties.hoverXOffset, n2 + gaugeProperties.hoverYOffset, gaugeProperties.hoverWidth, gaugeProperties.hoverHeight);
        this.properties = gaugeProperties;
    }

    protected abstract double getRatio();

    protected boolean isActive(double d) {
        return d > 0.0;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        double d = this.getRatio();
        if (d <= 0.0 && this.properties.bgWidth <= 0) {
            return;
        }
        Gauge.bindTexture(this.properties.texture);
        double d2 = this.x - this.properties.hoverXOffset;
        double d3 = this.y - this.properties.hoverYOffset;
        if (this.properties.bgWidth >= 0) {
            boolean bl = this.isActive(d);
            this.gui.drawTexturedRect(poseStack, d2 + (double)this.properties.bgXOffset, d3 + (double)this.properties.bgYOffset, this.properties.bgWidth, this.properties.bgHeight, bl ? (double)this.properties.uBgActive : (double)this.properties.uBgInactive, bl ? (double)this.properties.vBgActive : (double)this.properties.vBgInactive);
            if (d <= 0.0) {
                return;
            }
        }
        d = Math.min(d, 1.0);
        double d4 = this.properties.uInner;
        double d5 = this.properties.vInner;
        double d6 = this.properties.innerWidth;
        double d7 = this.properties.innerHeight;
        double d8 = this.properties.vertical ? d7 : d6;
        double d9 = d * d8;
        if (!this.properties.smooth) {
            d9 = Math.round(d9);
        }
        if (d9 <= 0.0) {
            return;
        }
        if (this.properties.vertical) {
            if (this.properties.reverse) {
                d5 += d7 - d9;
                d3 += d7 - d9;
            }
            d7 = d9;
        } else {
            if (this.properties.reverse) {
                d4 += d6 - d9;
                d2 += d6 - d9;
            }
            d6 = d9;
        }
        this.gui.drawTexturedRect(poseStack, d2, d3, d6, d7, d4, d5);
    }

    public static class GaugeProperties {
        public final short uInner;
        public final short vInner;
        public final short innerWidth;
        public final short innerHeight;
        public final short hoverXOffset;
        public final short hoverYOffset;
        public final short hoverWidth;
        public final short hoverHeight;
        public final short bgXOffset;
        public final short bgYOffset;
        public final short bgWidth;
        public final short bgHeight;
        public final short uBgInactive;
        public final short vBgInactive;
        public final short uBgActive;
        public final short vBgActive;
        public final boolean vertical;
        public final boolean reverse;
        public final boolean smooth;
        public final ResourceLocation texture;

        public GaugeProperties(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12, int n13, int n14, int n15, int n16, boolean bl, boolean bl2, boolean bl3, ResourceLocation resourceLocation) {
            this.uInner = (short)n;
            this.vInner = (short)n2;
            this.innerWidth = (short)n3;
            this.innerHeight = (short)n4;
            this.hoverXOffset = (short)n5;
            this.hoverYOffset = (short)n6;
            this.hoverWidth = (short)n7;
            this.hoverHeight = (short)n8;
            this.bgXOffset = (short)n9;
            this.bgYOffset = (short)n10;
            this.bgWidth = (short)n11;
            this.bgHeight = (short)n12;
            this.uBgInactive = (short)n13;
            this.vBgInactive = (short)n14;
            this.uBgActive = (short)n15;
            this.vBgActive = (short)n16;
            this.vertical = bl;
            this.reverse = bl2;
            this.smooth = bl3;
            this.texture = resourceLocation;
        }
    }

    public static final class GaugeStyle
    extends Enum<GaugeStyle>
    implements IGaugeStyle {
        public static final /* enum */ GaugeStyle Fuel = new GaugeStyle(new GaugePropertyBuilder(112, 80, 13, 13, GaugePropertyBuilder.GaugeOrientation.Up).withHover(0, 0, 14, 14).withBackground(0, 0, 16, 16, 96, 80).build());
        public static final /* enum */ GaugeStyle Bucket = new GaugeStyle(new GaugePropertyBuilder(110, 111, 14, 16, GaugePropertyBuilder.GaugeOrientation.Up).withBackground(96, 111).build());
        public static final /* enum */ GaugeStyle ProgressWind = new GaugeStyle(new GaugePropertyBuilder(242, 91, 13, 13, GaugePropertyBuilder.GaugeOrientation.Up).withBackground(242, 63, 242, 77).build());
        public static final /* enum */ GaugeStyle ProgressArrow = new GaugeStyle(new GaugePropertyBuilder(165, 16, 22, 15, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-5, 0, 32, 16, 160, 0).build());
        public static final /* enum */ GaugeStyle ProgressArrowModern = new GaugeStyle(new GaugePropertyBuilder(86, 234, 16, 10, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(0, 0, 16, 10, 70, 234).build());
        public static final /* enum */ GaugeStyle ProgressArrowModernReversed = new GaugeStyle(new GaugePropertyBuilder(70, 244, 16, 10, GaugePropertyBuilder.GaugeOrientation.Left).withBackground(0, 0, 16, 10, 86, 244).build());
        public static final /* enum */ GaugeStyle ProgressCrush = new GaugeStyle(new GaugePropertyBuilder(165, 52, 21, 11, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-5, -3, 32, 16, 160, 32).build());
        public static final /* enum */ GaugeStyle ProgressTriangle = new GaugeStyle(new GaugePropertyBuilder(165, 80, 22, 15, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-5, 0, 32, 16, 160, 64).build());
        public static final /* enum */ GaugeStyle ProgressDrop = new GaugeStyle(new GaugePropertyBuilder(165, 112, 22, 15, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-5, 0, 32, 16, 160, 96).build());
        public static final /* enum */ GaugeStyle ProgressRecycler = new GaugeStyle(new GaugePropertyBuilder(133, 80, 18, 15, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-5, 0, 32, 16, 128, 64).build());
        public static final /* enum */ GaugeStyle ProgressMetalFormer = new GaugeStyle(new GaugePropertyBuilder(200, 19, 46, 9, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-8, -3, 64, 16, 192, 0).build());
        public static final /* enum */ GaugeStyle ProgressCentrifuge = new GaugeStyle(new GaugePropertyBuilder(252, 33, 3, 28, GaugePropertyBuilder.GaugeOrientation.Up).withBackground(-1, -1, 5, 30, 246, 32).build());
        public static final /* enum */ GaugeStyle HeatCentrifuge = new GaugeStyle(new GaugePropertyBuilder(225, 54, 20, 4, GaugePropertyBuilder.GaugeOrientation.Right).withBackground(-1, -1, 22, 6, 224, 47).build());
        public static final /* enum */ GaugeStyle HeatNuclearReactor = new GaugeStyle(new GaugePropertyBuilder(0, 243, 100, 13, GaugePropertyBuilder.GaugeOrientation.Right).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guinuclearreactor.png")).build());
        public static final /* enum */ GaugeStyle HeatSteamGenerator = new GaugeStyle(new GaugePropertyBuilder(177, 1, 7, 76, GaugePropertyBuilder.GaugeOrientation.Up).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guisteamgenerator.png")).build());
        public static final /* enum */ GaugeStyle CalcificationSteamGenerator = new GaugeStyle(new GaugePropertyBuilder(187, 1, 7, 58, GaugePropertyBuilder.GaugeOrientation.Up).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guisteamgenerator.png")).build());
        public static final /* enum */ GaugeStyle ProgressCondenser = new GaugeStyle(new GaugePropertyBuilder(1, 185, 82, 7, GaugePropertyBuilder.GaugeOrientation.Right).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guicondenser.png")).build());
        public static final /* enum */ GaugeStyle HeatFermenter = new GaugeStyle(new GaugePropertyBuilder(177, 10, 40, 3, GaugePropertyBuilder.GaugeOrientation.Right).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guifermenter.png")).build());
        public static final /* enum */ GaugeStyle ProgressFermenter = new GaugeStyle(new GaugePropertyBuilder(177, 1, 40, 7, GaugePropertyBuilder.GaugeOrientation.Right).withHoverBorder(1).withTexture(IC2.getIdentifier("textures/gui/guifermenter.png")).build());
        public static final /* enum */ GaugeStyle ProgressOreWasher = new GaugeStyle(new GaugePropertyBuilder(177, 118, 18, 18, GaugePropertyBuilder.GaugeOrientation.Right).withTexture(IC2.getIdentifier("textures/gui/guiorewashingplant.png")).withBackground(-1, -1, 20, 19, 102, 38).build());
        public static final /* enum */ GaugeStyle ProgressBlockCutter = new GaugeStyle(new GaugePropertyBuilder(176, 15, 46, 17, GaugePropertyBuilder.GaugeOrientation.Right).withTexture(IC2.getIdentifier("textures/gui/guiblockcutter.png")).withBackground(55, 33).build());
        public static final /* enum */ GaugeStyle ProgressLongArrow = new GaugeStyle(new GaugePropertyBuilder(176, 15, 34, 13, GaugePropertyBuilder.GaugeOrientation.Right).withTexture(IC2.getIdentifier("textures/gui/gui_canner_classic.png")).withBackground(74, 36).build());
        private static final Map<String, IGaugeStyle> map;
        private final String name = this.name().toLowerCase(Locale.ENGLISH);
        public final GaugeProperties properties;
        private static final /* synthetic */ GaugeStyle[] $VALUES;

        public static GaugeStyle[] values() {
            return (GaugeStyle[])$VALUES.clone();
        }

        public static GaugeStyle valueOf(String string) {
            return Enum.valueOf(GaugeStyle.class, string);
        }

        private GaugeStyle(GaugeProperties gaugeProperties) {
            this.properties = gaugeProperties;
        }

        @Override
        public GaugeProperties getProperties() {
            return this.properties;
        }

        public static void addStyle(String string, IGaugeStyle iGaugeStyle) {
            assert (string != null) : "Cannot add null name";
            assert (iGaugeStyle != null) : "Cannot add null style";
            if (map.containsKey(string)) {
                throw new RuntimeException("Duplicate style name for " + string + "!");
            }
            map.put(string, iGaugeStyle);
        }

        public static IGaugeStyle get(String string) {
            return map.get(string);
        }

        private static Map<String, IGaugeStyle> getMap() {
            GaugeStyle[] gaugeStyleArray = GaugeStyle.values();
            HashMap<String, IGaugeStyle> hashMap = new HashMap<String, IGaugeStyle>(gaugeStyleArray.length);
            for (GaugeStyle gaugeStyle : gaugeStyleArray) {
                hashMap.put(gaugeStyle.name, gaugeStyle);
            }
            return hashMap;
        }

        private static /* synthetic */ GaugeStyle[] $values() {
            return new GaugeStyle[]{Fuel, Bucket, ProgressWind, ProgressArrow, ProgressArrowModern, ProgressArrowModernReversed, ProgressCrush, ProgressTriangle, ProgressDrop, ProgressRecycler, ProgressMetalFormer, ProgressCentrifuge, HeatCentrifuge, HeatNuclearReactor, HeatSteamGenerator, CalcificationSteamGenerator, ProgressCondenser, HeatFermenter, ProgressFermenter, ProgressOreWasher, ProgressBlockCutter, ProgressLongArrow};
        }

        static {
            $VALUES = GaugeStyle.$values();
            map = GaugeStyle.getMap();
        }
    }

    public static interface IGaugeStyle {
        public GaugeProperties getProperties();
    }

    public static class GaugePropertyBuilder {
        private final short uInner;
        private final short vInner;
        private final short innerWidth;
        private final short innerHeight;
        private short hoverXOffset;
        private short hoverYOffset;
        private short hoverWidth;
        private short hoverHeight;
        private short bgXOffset;
        private short bgYOffset;
        private short bgWidth;
        private short bgHeight;
        private short uBgInactive;
        private short vBgInactive;
        private short uBgActive;
        private short vBgActive;
        private final boolean vertical;
        private final boolean reverse;
        private boolean smooth = true;
        private ResourceLocation texture = GuiElement.commonTexture;

        public GaugePropertyBuilder(int n, int n2, int n3, int n4, GaugeOrientation gaugeOrientation) {
            this.uInner = GaugePropertyBuilder.toShort(n);
            this.vInner = GaugePropertyBuilder.toShort(n2);
            this.innerWidth = this.hoverWidth = GaugePropertyBuilder.toShort(n3);
            this.innerHeight = this.hoverHeight = GaugePropertyBuilder.toShort(n4);
            this.vertical = gaugeOrientation.vertical;
            this.reverse = gaugeOrientation.reverse;
        }

        public GaugePropertyBuilder withHoverBorder(int n) {
            this.hoverXOffset = GaugePropertyBuilder.toShort(-n);
            this.hoverYOffset = GaugePropertyBuilder.toShort(-n);
            this.hoverWidth = GaugePropertyBuilder.toShort(this.innerWidth + 2 * n);
            this.hoverHeight = GaugePropertyBuilder.toShort(this.innerHeight + 2 * n);
            return this;
        }

        public GaugePropertyBuilder withHover(int n, int n2, int n3, int n4) {
            this.hoverXOffset = GaugePropertyBuilder.toShort(n);
            this.hoverYOffset = GaugePropertyBuilder.toShort(n2);
            this.hoverWidth = GaugePropertyBuilder.toShort(n3);
            this.hoverHeight = GaugePropertyBuilder.toShort(n4);
            return this;
        }

        public GaugePropertyBuilder withBackground(int n, int n2) {
            return this.withBackground(0, 0, this.innerWidth, this.innerHeight, n, n2);
        }

        public GaugePropertyBuilder withBackground(int n, int n2, int n3, int n4, int n5, int n6) {
            return this.withBackground(n, n2, n3, n4, n5, n6, n5, n6);
        }

        public GaugePropertyBuilder withBackground(int n, int n2, int n3, int n4) {
            return this.withBackground(0, 0, this.innerWidth, this.innerHeight, n, n2, n3, n4);
        }

        public GaugePropertyBuilder withBackground(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            this.bgXOffset = GaugePropertyBuilder.toShort(n);
            this.bgYOffset = GaugePropertyBuilder.toShort(n2);
            this.bgWidth = GaugePropertyBuilder.toShort(n3);
            this.bgHeight = GaugePropertyBuilder.toShort(n4);
            this.uBgInactive = GaugePropertyBuilder.toShort(n5);
            this.vBgInactive = GaugePropertyBuilder.toShort(n6);
            this.uBgActive = GaugePropertyBuilder.toShort(n7);
            this.vBgActive = GaugePropertyBuilder.toShort(n8);
            return this;
        }

        public GaugePropertyBuilder withSmooth(boolean bl) {
            this.smooth = bl;
            return this;
        }

        public GaugePropertyBuilder withTexture(ResourceLocation resourceLocation) {
            this.texture = resourceLocation;
            return this;
        }

        public GaugeProperties build() {
            return new GaugeProperties(this.uInner, this.vInner, this.innerWidth, this.innerHeight, this.hoverXOffset, this.hoverYOffset, this.hoverWidth, this.hoverHeight, this.bgXOffset, this.bgYOffset, this.bgWidth, this.bgHeight, this.uBgInactive, this.vBgInactive, this.uBgActive, this.vBgActive, this.vertical, this.reverse, this.smooth, this.texture);
        }

        private static short toShort(int n) {
            return (short)n;
        }

        public static enum GaugeOrientation {
            Up(true, true),
            Down(true, false),
            Left(false, true),
            Right(false, false);

            final boolean vertical;
            final boolean reverse;

            private GaugeOrientation(boolean bl, boolean bl2) {
                this.vertical = bl;
                this.reverse = bl2;
            }
        }
    }
}

