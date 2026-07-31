/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonPrimitive
 */
package net.minecraft.data.models.blockstates;

import com.google.gson.JsonPrimitive;
import net.minecraft.data.models.blockstates.VariantProperty;
import net.minecraft.resources.ResourceLocation;

public class VariantProperties {
    public static final VariantProperty<Rotation> f_125518_ = new VariantProperty<Rotation>("x", p_125529_ -> new JsonPrimitive((Number)p_125529_.f_125534_));
    public static final VariantProperty<Rotation> f_125519_ = new VariantProperty<Rotation>("y", p_125525_ -> new JsonPrimitive((Number)p_125525_.f_125534_));
    public static final VariantProperty<ResourceLocation> f_125520_ = new VariantProperty<ResourceLocation>("model", p_125527_ -> new JsonPrimitive(p_125527_.toString()));
    public static final VariantProperty<Boolean> f_125521_ = new VariantProperty<Boolean>("uvlock", JsonPrimitive::new);
    public static final VariantProperty<Integer> f_125522_ = new VariantProperty<Integer>("weight", JsonPrimitive::new);

    public static final class Rotation
    extends Enum<Rotation> {
        public static final /* enum */ Rotation R0 = new Rotation(0);
        public static final /* enum */ Rotation R90 = new Rotation(90);
        public static final /* enum */ Rotation R180 = new Rotation(180);
        public static final /* enum */ Rotation R270 = new Rotation(270);
        final int f_125534_;
        private static final /* synthetic */ Rotation[] $VALUES;

        public static Rotation[] values() {
            return (Rotation[])$VALUES.clone();
        }

        public static Rotation valueOf(String p_125544_) {
            return Enum.valueOf(Rotation.class, p_125544_);
        }

        private Rotation(int p_125540_) {
            this.f_125534_ = p_125540_;
        }

        private static /* synthetic */ Rotation[] m_176452_() {
            return new Rotation[]{R0, R90, R180, R270};
        }

        static {
            $VALUES = Rotation.m_176452_();
        }
    }
}

