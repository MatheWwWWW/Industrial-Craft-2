/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.animation;

import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class KeyframeAnimations {
    public static void m_232319_(HierarchicalModel<?> p_232320_, AnimationDefinition p_232321_, long p_232322_, float p_232323_, Vector3f p_232324_) {
        float $$5 = KeyframeAnimations.m_232316_(p_232321_, p_232322_);
        for (Map.Entry<String, List<AnimationChannel>> $$6 : p_232321_.f_232257_().entrySet()) {
            Optional<ModelPart> $$7 = p_232320_.m_233393_($$6.getKey());
            List<AnimationChannel> $$8 = $$6.getValue();
            $$7.ifPresent(p_232330_ -> $$8.forEach(p_232311_ -> {
                Keyframe[] $$5 = p_232311_.f_232212_();
                int $$6 = Math.max(0, Mth.m_14049_(0, $$5.length, p_232315_ -> $$5 <= $$5[p_232315_].f_232283_()) - 1);
                int $$7 = Math.min($$5.length - 1, $$6 + 1);
                Keyframe $$8 = $$5[$$6];
                Keyframe $$9 = $$5[$$7];
                float $$10 = $$5 - $$8.f_232283_();
                float $$11 = Mth.m_14036_($$10 / ($$9.f_232283_() - $$8.f_232283_()), 0.0f, 1.0f);
                $$9.f_232285_().m_232222_(p_232324_, $$11, $$5, $$6, $$7, p_232323_);
                p_232311_.f_232211_().m_232247_((ModelPart)p_232330_, p_232324_);
            }));
        }
    }

    private static float m_232316_(AnimationDefinition p_232317_, long p_232318_) {
        float $$2 = (float)p_232318_ / 1000.0f;
        return p_232317_.f_232256_() ? $$2 % p_232317_.f_232255_() : $$2;
    }

    public static Vector3f m_232302_(float p_232303_, float p_232304_, float p_232305_) {
        return new Vector3f(p_232303_, -p_232304_, p_232305_);
    }

    public static Vector3f m_232331_(float p_232332_, float p_232333_, float p_232334_) {
        return new Vector3f(p_232332_ * ((float)Math.PI / 180), p_232333_ * ((float)Math.PI / 180), p_232334_ * ((float)Math.PI / 180));
    }

    public static Vector3f m_232298_(double p_232299_, double p_232300_, double p_232301_) {
        return new Vector3f((float)(p_232299_ - 1.0), (float)(p_232300_ - 1.0), (float)(p_232301_ - 1.0));
    }
}

