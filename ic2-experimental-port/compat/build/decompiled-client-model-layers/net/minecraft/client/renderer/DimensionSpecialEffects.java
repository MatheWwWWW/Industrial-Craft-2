/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public abstract class DimensionSpecialEffects {
    private static final Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> f_108857_ = (Object2ObjectMap)Util.m_137469_(new Object2ObjectArrayMap(), p_108881_ -> {
        OverworldEffects $$1 = new OverworldEffects();
        p_108881_.defaultReturnValue((Object)$$1);
        p_108881_.put((Object)BuiltinDimensionTypes.f_223542_, (Object)$$1);
        p_108881_.put((Object)BuiltinDimensionTypes.f_223543_, (Object)new NetherEffects());
        p_108881_.put((Object)BuiltinDimensionTypes.f_223544_, (Object)new EndEffects());
    });
    private final float[] f_108858_ = new float[4];
    private final float f_108859_;
    private final boolean f_108860_;
    private final SkyType f_108861_;
    private final boolean f_108862_;
    private final boolean f_108863_;

    public DimensionSpecialEffects(float p_108866_, boolean p_108867_, SkyType p_108868_, boolean p_108869_, boolean p_108870_) {
        this.f_108859_ = p_108866_;
        this.f_108860_ = p_108867_;
        this.f_108861_ = p_108868_;
        this.f_108862_ = p_108869_;
        this.f_108863_ = p_108870_;
    }

    public static DimensionSpecialEffects m_108876_(DimensionType p_108877_) {
        return (DimensionSpecialEffects)f_108857_.get((Object)p_108877_.f_63837_());
    }

    @Nullable
    public float[] m_7518_(float p_108872_, float p_108873_) {
        float $$2 = 0.4f;
        float $$3 = Mth.m_14089_(p_108872_ * ((float)Math.PI * 2)) - 0.0f;
        float $$4 = -0.0f;
        if ($$3 >= -0.4f && $$3 <= 0.4f) {
            float $$5 = ($$3 - -0.0f) / 0.4f * 0.5f + 0.5f;
            float $$6 = 1.0f - (1.0f - Mth.m_14031_($$5 * (float)Math.PI)) * 0.99f;
            $$6 *= $$6;
            this.f_108858_[0] = $$5 * 0.3f + 0.7f;
            this.f_108858_[1] = $$5 * $$5 * 0.7f + 0.2f;
            this.f_108858_[2] = $$5 * $$5 * 0.0f + 0.2f;
            this.f_108858_[3] = $$6;
            return this.f_108858_;
        }
        return null;
    }

    public float m_108871_() {
        return this.f_108859_;
    }

    public boolean m_108882_() {
        return this.f_108860_;
    }

    public abstract Vec3 m_5927_(Vec3 var1, float var2);

    public abstract boolean m_5781_(int var1, int var2);

    public SkyType m_108883_() {
        return this.f_108861_;
    }

    public boolean m_108884_() {
        return this.f_108862_;
    }

    public boolean m_108885_() {
        return this.f_108863_;
    }

    public static final class SkyType
    extends Enum<SkyType> {
        public static final /* enum */ SkyType NONE = new SkyType();
        public static final /* enum */ SkyType NORMAL = new SkyType();
        public static final /* enum */ SkyType END = new SkyType();
        private static final /* synthetic */ SkyType[] $VALUES;

        public static SkyType[] values() {
            return (SkyType[])$VALUES.clone();
        }

        public static SkyType valueOf(String p_108919_) {
            return Enum.valueOf(SkyType.class, p_108919_);
        }

        private static /* synthetic */ SkyType[] m_172563_() {
            return new SkyType[]{NONE, NORMAL, END};
        }

        static {
            $VALUES = SkyType.m_172563_();
        }
    }

    public static class OverworldEffects
    extends DimensionSpecialEffects {
        public static final int f_172562_ = 192;

        public OverworldEffects() {
            super(192.0f, true, SkyType.NORMAL, false, false);
        }

        @Override
        public Vec3 m_5927_(Vec3 p_108908_, float p_108909_) {
            return p_108908_.m_82542_(p_108909_ * 0.94f + 0.06f, p_108909_ * 0.94f + 0.06f, p_108909_ * 0.91f + 0.09f);
        }

        @Override
        public boolean m_5781_(int p_108905_, int p_108906_) {
            return false;
        }
    }

    public static class NetherEffects
    extends DimensionSpecialEffects {
        public NetherEffects() {
            super(Float.NaN, true, SkyType.NONE, false, true);
        }

        @Override
        public Vec3 m_5927_(Vec3 p_108901_, float p_108902_) {
            return p_108901_;
        }

        @Override
        public boolean m_5781_(int p_108898_, int p_108899_) {
            return true;
        }
    }

    public static class EndEffects
    extends DimensionSpecialEffects {
        public EndEffects() {
            super(Float.NaN, false, SkyType.END, true, false);
        }

        @Override
        public Vec3 m_5927_(Vec3 p_108894_, float p_108895_) {
            return p_108894_.m_82490_(0.15f);
        }

        @Override
        public boolean m_5781_(int p_108891_, int p_108892_) {
            return false;
        }

        @Override
        @Nullable
        public float[] m_7518_(float p_108888_, float p_108889_) {
            return null;
        }
    }
}

