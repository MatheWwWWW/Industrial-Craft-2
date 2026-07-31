/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public final class ModelPart {
    public static final float f_233552_ = 1.0f;
    public float f_104200_;
    public float f_104201_;
    public float f_104202_;
    public float f_104203_;
    public float f_104204_;
    public float f_104205_;
    public float f_233553_ = 1.0f;
    public float f_233554_ = 1.0f;
    public float f_233555_ = 1.0f;
    public boolean f_104207_ = true;
    public boolean f_233556_;
    private final List<Cube> f_104212_;
    private final Map<String, ModelPart> f_104213_;
    private PartPose f_233557_ = PartPose.f_171404_;

    public ModelPart(List<Cube> p_171306_, Map<String, ModelPart> p_171307_) {
        this.f_104212_ = p_171306_;
        this.f_104213_ = p_171307_;
    }

    public PartPose m_171308_() {
        return PartPose.m_171423_(this.f_104200_, this.f_104201_, this.f_104202_, this.f_104203_, this.f_104204_, this.f_104205_);
    }

    public PartPose m_233566_() {
        return this.f_233557_;
    }

    public void m_233560_(PartPose p_233561_) {
        this.f_233557_ = p_233561_;
    }

    public void m_233569_() {
        this.m_171322_(this.f_233557_);
    }

    public void m_171322_(PartPose p_171323_) {
        this.f_104200_ = p_171323_.f_171405_;
        this.f_104201_ = p_171323_.f_171406_;
        this.f_104202_ = p_171323_.f_171407_;
        this.f_104203_ = p_171323_.f_171408_;
        this.f_104204_ = p_171323_.f_171409_;
        this.f_104205_ = p_171323_.f_171410_;
        this.f_233553_ = 1.0f;
        this.f_233554_ = 1.0f;
        this.f_233555_ = 1.0f;
    }

    public void m_104315_(ModelPart p_104316_) {
        this.f_233553_ = p_104316_.f_233553_;
        this.f_233554_ = p_104316_.f_233554_;
        this.f_233555_ = p_104316_.f_233555_;
        this.f_104203_ = p_104316_.f_104203_;
        this.f_104204_ = p_104316_.f_104204_;
        this.f_104205_ = p_104316_.f_104205_;
        this.f_104200_ = p_104316_.f_104200_;
        this.f_104201_ = p_104316_.f_104201_;
        this.f_104202_ = p_104316_.f_104202_;
    }

    public boolean m_233562_(String p_233563_) {
        return this.f_104213_.containsKey(p_233563_);
    }

    public ModelPart m_171324_(String p_171325_) {
        ModelPart $$1 = this.f_104213_.get(p_171325_);
        if ($$1 == null) {
            throw new NoSuchElementException("Can't find part " + p_171325_);
        }
        return $$1;
    }

    public void m_104227_(float p_104228_, float p_104229_, float p_104230_) {
        this.f_104200_ = p_104228_;
        this.f_104201_ = p_104229_;
        this.f_104202_ = p_104230_;
    }

    public void m_171327_(float p_171328_, float p_171329_, float p_171330_) {
        this.f_104203_ = p_171328_;
        this.f_104204_ = p_171329_;
        this.f_104205_ = p_171330_;
    }

    public void m_104301_(PoseStack p_104302_, VertexConsumer p_104303_, int p_104304_, int p_104305_) {
        this.m_104306_(p_104302_, p_104303_, p_104304_, p_104305_, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void m_104306_(PoseStack p_104307_, VertexConsumer p_104308_, int p_104309_, int p_104310_, float p_104311_, float p_104312_, float p_104313_, float p_104314_) {
        if (!this.f_104207_) {
            return;
        }
        if (this.f_104212_.isEmpty() && this.f_104213_.isEmpty()) {
            return;
        }
        p_104307_.m_85836_();
        this.m_104299_(p_104307_);
        if (!this.f_233556_) {
            this.m_104290_(p_104307_.m_85850_(), p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);
        }
        for (ModelPart $$8 : this.f_104213_.values()) {
            $$8.m_104306_(p_104307_, p_104308_, p_104309_, p_104310_, p_104311_, p_104312_, p_104313_, p_104314_);
        }
        p_104307_.m_85849_();
    }

    public void m_171309_(PoseStack p_171310_, Visitor p_171311_) {
        this.m_171312_(p_171310_, p_171311_, "");
    }

    private void m_171312_(PoseStack p_171313_, Visitor p_171314_, String p_171315_) {
        if (this.f_104212_.isEmpty() && this.f_104213_.isEmpty()) {
            return;
        }
        p_171313_.m_85836_();
        this.m_104299_(p_171313_);
        PoseStack.Pose $$3 = p_171313_.m_85850_();
        for (int $$4 = 0; $$4 < this.f_104212_.size(); ++$$4) {
            p_171314_.m_171341_($$3, p_171315_, $$4, this.f_104212_.get($$4));
        }
        String $$5 = p_171315_ + "/";
        this.f_104213_.forEach((p_171320_, p_171321_) -> p_171321_.m_171312_(p_171313_, p_171314_, $$5 + p_171320_));
        p_171313_.m_85849_();
    }

    public void m_104299_(PoseStack p_104300_) {
        p_104300_.m_85837_(this.f_104200_ / 16.0f, this.f_104201_ / 16.0f, this.f_104202_ / 16.0f);
        if (this.f_104205_ != 0.0f) {
            p_104300_.m_85845_(Vector3f.f_122227_.m_122270_(this.f_104205_));
        }
        if (this.f_104204_ != 0.0f) {
            p_104300_.m_85845_(Vector3f.f_122225_.m_122270_(this.f_104204_));
        }
        if (this.f_104203_ != 0.0f) {
            p_104300_.m_85845_(Vector3f.f_122223_.m_122270_(this.f_104203_));
        }
        if (this.f_233553_ != 1.0f || this.f_233554_ != 1.0f || this.f_233555_ != 1.0f) {
            p_104300_.m_85841_(this.f_233553_, this.f_233554_, this.f_233555_);
        }
    }

    private void m_104290_(PoseStack.Pose p_104291_, VertexConsumer p_104292_, int p_104293_, int p_104294_, float p_104295_, float p_104296_, float p_104297_, float p_104298_) {
        for (Cube $$8 : this.f_104212_) {
            $$8.m_171332_(p_104291_, p_104292_, p_104293_, p_104294_, p_104295_, p_104296_, p_104297_, p_104298_);
        }
    }

    public Cube m_233558_(RandomSource p_233559_) {
        return this.f_104212_.get(p_233559_.m_188503_(this.f_104212_.size()));
    }

    public boolean m_171326_() {
        return this.f_104212_.isEmpty();
    }

    public void m_233564_(Vector3f p_233565_) {
        this.f_104200_ += p_233565_.m_122239_();
        this.f_104201_ += p_233565_.m_122260_();
        this.f_104202_ += p_233565_.m_122269_();
    }

    public void m_233567_(Vector3f p_233568_) {
        this.f_104203_ += p_233568_.m_122239_();
        this.f_104204_ += p_233568_.m_122260_();
        this.f_104205_ += p_233568_.m_122269_();
    }

    public void m_233570_(Vector3f p_233571_) {
        this.f_233553_ += p_233571_.m_122239_();
        this.f_233554_ += p_233571_.m_122260_();
        this.f_233555_ += p_233571_.m_122269_();
    }

    public Stream<ModelPart> m_171331_() {
        return Stream.concat(Stream.of(this), this.f_104213_.values().stream().flatMap(ModelPart::m_171331_));
    }

    @FunctionalInterface
    public static interface Visitor {
        public void m_171341_(PoseStack.Pose var1, String var2, int var3, Cube var4);
    }

    public static class Cube {
        private final Polygon[] f_104341_;
        public final float f_104335_;
        public final float f_104336_;
        public final float f_104337_;
        public final float f_104338_;
        public final float f_104339_;
        public final float f_104340_;

        public Cube(int p_104343_, int p_104344_, float p_104345_, float p_104346_, float p_104347_, float p_104348_, float p_104349_, float p_104350_, float p_104351_, float p_104352_, float p_104353_, boolean p_104354_, float p_104355_, float p_104356_) {
            this.f_104335_ = p_104345_;
            this.f_104336_ = p_104346_;
            this.f_104337_ = p_104347_;
            this.f_104338_ = p_104345_ + p_104348_;
            this.f_104339_ = p_104346_ + p_104349_;
            this.f_104340_ = p_104347_ + p_104350_;
            this.f_104341_ = new Polygon[6];
            float $$14 = p_104345_ + p_104348_;
            float $$15 = p_104346_ + p_104349_;
            float $$16 = p_104347_ + p_104350_;
            p_104345_ -= p_104351_;
            p_104346_ -= p_104352_;
            p_104347_ -= p_104353_;
            $$14 += p_104351_;
            $$15 += p_104352_;
            $$16 += p_104353_;
            if (p_104354_) {
                float $$17 = $$14;
                $$14 = p_104345_;
                p_104345_ = $$17;
            }
            Vertex $$18 = new Vertex(p_104345_, p_104346_, p_104347_, 0.0f, 0.0f);
            Vertex $$19 = new Vertex($$14, p_104346_, p_104347_, 0.0f, 8.0f);
            Vertex $$20 = new Vertex($$14, $$15, p_104347_, 8.0f, 8.0f);
            Vertex $$21 = new Vertex(p_104345_, $$15, p_104347_, 8.0f, 0.0f);
            Vertex $$22 = new Vertex(p_104345_, p_104346_, $$16, 0.0f, 0.0f);
            Vertex $$23 = new Vertex($$14, p_104346_, $$16, 0.0f, 8.0f);
            Vertex $$24 = new Vertex($$14, $$15, $$16, 8.0f, 8.0f);
            Vertex $$25 = new Vertex(p_104345_, $$15, $$16, 8.0f, 0.0f);
            float $$26 = p_104343_;
            float $$27 = (float)p_104343_ + p_104350_;
            float $$28 = (float)p_104343_ + p_104350_ + p_104348_;
            float $$29 = (float)p_104343_ + p_104350_ + p_104348_ + p_104348_;
            float $$30 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_;
            float $$31 = (float)p_104343_ + p_104350_ + p_104348_ + p_104350_ + p_104348_;
            float $$32 = p_104344_;
            float $$33 = (float)p_104344_ + p_104350_;
            float $$34 = (float)p_104344_ + p_104350_ + p_104349_;
            this.f_104341_[2] = new Polygon(new Vertex[]{$$23, $$22, $$18, $$19}, $$27, $$32, $$28, $$33, p_104355_, p_104356_, p_104354_, Direction.DOWN);
            this.f_104341_[3] = new Polygon(new Vertex[]{$$20, $$21, $$25, $$24}, $$28, $$33, $$29, $$32, p_104355_, p_104356_, p_104354_, Direction.UP);
            this.f_104341_[1] = new Polygon(new Vertex[]{$$18, $$22, $$25, $$21}, $$26, $$33, $$27, $$34, p_104355_, p_104356_, p_104354_, Direction.WEST);
            this.f_104341_[4] = new Polygon(new Vertex[]{$$19, $$18, $$21, $$20}, $$27, $$33, $$28, $$34, p_104355_, p_104356_, p_104354_, Direction.NORTH);
            this.f_104341_[0] = new Polygon(new Vertex[]{$$23, $$19, $$20, $$24}, $$28, $$33, $$30, $$34, p_104355_, p_104356_, p_104354_, Direction.EAST);
            this.f_104341_[5] = new Polygon(new Vertex[]{$$22, $$23, $$24, $$25}, $$30, $$33, $$31, $$34, p_104355_, p_104356_, p_104354_, Direction.SOUTH);
        }

        public void m_171332_(PoseStack.Pose p_171333_, VertexConsumer p_171334_, int p_171335_, int p_171336_, float p_171337_, float p_171338_, float p_171339_, float p_171340_) {
            Matrix4f $$8 = p_171333_.m_85861_();
            Matrix3f $$9 = p_171333_.m_85864_();
            for (Polygon $$10 : this.f_104341_) {
                Vector3f $$11 = $$10.f_104360_.m_122281_();
                $$11.m_122249_($$9);
                float $$12 = $$11.m_122239_();
                float $$13 = $$11.m_122260_();
                float $$14 = $$11.m_122269_();
                for (Vertex $$15 : $$10.f_104359_) {
                    float $$16 = $$15.f_104371_.m_122239_() / 16.0f;
                    float $$17 = $$15.f_104371_.m_122260_() / 16.0f;
                    float $$18 = $$15.f_104371_.m_122269_() / 16.0f;
                    Vector4f $$19 = new Vector4f($$16, $$17, $$18, 1.0f);
                    $$19.m_123607_($$8);
                    p_171334_.m_5954_($$19.m_123601_(), $$19.m_123615_(), $$19.m_123616_(), p_171337_, p_171338_, p_171339_, p_171340_, $$15.f_104372_, $$15.f_104373_, p_171336_, p_171335_, $$12, $$13, $$14);
                }
            }
        }
    }

    static class Vertex {
        public final Vector3f f_104371_;
        public final float f_104372_;
        public final float f_104373_;

        public Vertex(float p_104375_, float p_104376_, float p_104377_, float p_104378_, float p_104379_) {
            this(new Vector3f(p_104375_, p_104376_, p_104377_), p_104378_, p_104379_);
        }

        public Vertex m_104384_(float p_104385_, float p_104386_) {
            return new Vertex(this.f_104371_, p_104385_, p_104386_);
        }

        public Vertex(Vector3f p_104381_, float p_104382_, float p_104383_) {
            this.f_104371_ = p_104381_;
            this.f_104372_ = p_104382_;
            this.f_104373_ = p_104383_;
        }
    }

    static class Polygon {
        public final Vertex[] f_104359_;
        public final Vector3f f_104360_;

        public Polygon(Vertex[] p_104362_, float p_104363_, float p_104364_, float p_104365_, float p_104366_, float p_104367_, float p_104368_, boolean p_104369_, Direction p_104370_) {
            this.f_104359_ = p_104362_;
            float $$9 = 0.0f / p_104367_;
            float $$10 = 0.0f / p_104368_;
            p_104362_[0] = p_104362_[0].m_104384_(p_104365_ / p_104367_ - $$9, p_104364_ / p_104368_ + $$10);
            p_104362_[1] = p_104362_[1].m_104384_(p_104363_ / p_104367_ + $$9, p_104364_ / p_104368_ + $$10);
            p_104362_[2] = p_104362_[2].m_104384_(p_104363_ / p_104367_ + $$9, p_104366_ / p_104368_ - $$10);
            p_104362_[3] = p_104362_[3].m_104384_(p_104365_ / p_104367_ - $$9, p_104366_ / p_104368_ - $$10);
            if (p_104369_) {
                int $$11 = p_104362_.length;
                for (int $$12 = 0; $$12 < $$11 / 2; ++$$12) {
                    Vertex $$13 = p_104362_[$$12];
                    p_104362_[$$12] = p_104362_[$$11 - 1 - $$12];
                    p_104362_[$$11 - 1 - $$12] = $$13;
                }
            }
            this.f_104360_ = p_104370_.m_122432_();
            if (p_104369_) {
                this.f_104360_.m_122263_(-1.0f, 1.0f, 1.0f);
            }
        }
    }
}

