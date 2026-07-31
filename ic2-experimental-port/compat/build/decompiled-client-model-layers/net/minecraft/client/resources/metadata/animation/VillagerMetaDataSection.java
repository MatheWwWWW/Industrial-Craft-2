/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resources.metadata.animation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.resources.metadata.animation.VillagerMetadataSectionSerializer;

public class VillagerMetaDataSection {
    public static final VillagerMetadataSectionSerializer f_119065_ = new VillagerMetadataSectionSerializer();
    public static final String f_174866_ = "villager";
    private final Hat f_119066_;

    public VillagerMetaDataSection(Hat p_119069_) {
        this.f_119066_ = p_119069_;
    }

    public Hat m_119070_() {
        return this.f_119066_;
    }

    public static final class Hat
    extends Enum<Hat> {
        public static final /* enum */ Hat NONE = new Hat("none");
        public static final /* enum */ Hat PARTIAL = new Hat("partial");
        public static final /* enum */ Hat FULL = new Hat("full");
        private static final Map<String, Hat> f_119074_;
        private final String f_119075_;
        private static final /* synthetic */ Hat[] $VALUES;

        public static Hat[] values() {
            return (Hat[])$VALUES.clone();
        }

        public static Hat valueOf(String p_119088_) {
            return Enum.valueOf(Hat.class, p_119088_);
        }

        private Hat(String p_119081_) {
            this.f_119075_ = p_119081_;
        }

        public String m_119082_() {
            return this.f_119075_;
        }

        public static Hat m_119085_(String p_119086_) {
            return f_119074_.getOrDefault(p_119086_, NONE);
        }

        private static /* synthetic */ Hat[] m_174867_() {
            return new Hat[]{NONE, PARTIAL, FULL};
        }

        static {
            $VALUES = Hat.m_174867_();
            f_119074_ = Arrays.stream(Hat.values()).collect(Collectors.toMap(Hat::m_119082_, p_119084_ -> p_119084_));
        }
    }
}

