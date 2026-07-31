/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public class GenerationStep {

    public static final class Carving
    extends Enum<Carving>
    implements StringRepresentable {
        public static final /* enum */ Carving AIR = new Carving("air");
        public static final /* enum */ Carving LIQUID = new Carving("liquid");
        public static final Codec<Carving> f_64194_;
        private final String f_64196_;
        private static final /* synthetic */ Carving[] $VALUES;

        public static Carving[] values() {
            return (Carving[])$VALUES.clone();
        }

        public static Carving valueOf(String p_64210_) {
            return Enum.valueOf(Carving.class, p_64210_);
        }

        private Carving(String p_64202_) {
            this.f_64196_ = p_64202_;
        }

        public String m_64208_() {
            return this.f_64196_;
        }

        @Override
        public String m_7912_() {
            return this.f_64196_;
        }

        private static /* synthetic */ Carving[] m_158285_() {
            return new Carving[]{AIR, LIQUID};
        }

        static {
            $VALUES = Carving.m_158285_();
            f_64194_ = StringRepresentable.m_216439_(Carving::values);
        }
    }

    public static final class Decoration
    extends Enum<Decoration>
    implements StringRepresentable {
        public static final /* enum */ Decoration RAW_GENERATION = new Decoration("raw_generation");
        public static final /* enum */ Decoration LAKES = new Decoration("lakes");
        public static final /* enum */ Decoration LOCAL_MODIFICATIONS = new Decoration("local_modifications");
        public static final /* enum */ Decoration UNDERGROUND_STRUCTURES = new Decoration("underground_structures");
        public static final /* enum */ Decoration SURFACE_STRUCTURES = new Decoration("surface_structures");
        public static final /* enum */ Decoration STRONGHOLDS = new Decoration("strongholds");
        public static final /* enum */ Decoration UNDERGROUND_ORES = new Decoration("underground_ores");
        public static final /* enum */ Decoration UNDERGROUND_DECORATION = new Decoration("underground_decoration");
        public static final /* enum */ Decoration FLUID_SPRINGS = new Decoration("fluid_springs");
        public static final /* enum */ Decoration VEGETAL_DECORATION = new Decoration("vegetal_decoration");
        public static final /* enum */ Decoration TOP_LAYER_MODIFICATION = new Decoration("top_layer_modification");
        public static final Codec<Decoration> f_224188_;
        private final String f_224189_;
        private static final /* synthetic */ Decoration[] $VALUES;

        public static Decoration[] values() {
            return (Decoration[])$VALUES.clone();
        }

        public static Decoration valueOf(String p_64228_) {
            return Enum.valueOf(Decoration.class, p_64228_);
        }

        private Decoration(String p_224193_) {
            this.f_224189_ = p_224193_;
        }

        public String m_224194_() {
            return this.f_224189_;
        }

        @Override
        public String m_7912_() {
            return this.f_224189_;
        }

        private static /* synthetic */ Decoration[] m_158286_() {
            return new Decoration[]{RAW_GENERATION, LAKES, LOCAL_MODIFICATIONS, UNDERGROUND_STRUCTURES, SURFACE_STRUCTURES, STRONGHOLDS, UNDERGROUND_ORES, UNDERGROUND_DECORATION, FLUID_SPRINGS, VEGETAL_DECORATION, TOP_LAYER_MODIFICATION};
        }

        static {
            $VALUES = Decoration.m_158286_();
            f_224188_ = StringRepresentable.m_216439_(Decoration::values);
        }
    }
}

