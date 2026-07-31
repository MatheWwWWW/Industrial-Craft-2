/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class HorseModel<T extends AbstractHorse>
extends AgeableListModel<T> {
    private static final float f_170648_ = 2.1816616f;
    private static final float f_170649_ = 1.0471976f;
    private static final float f_170650_ = 0.7853982f;
    private static final float f_170651_ = 0.5235988f;
    private static final float f_170652_ = 0.2617994f;
    protected static final String f_170647_ = "head_parts";
    private static final String f_170653_ = "left_hind_baby_leg";
    private static final String f_170654_ = "right_hind_baby_leg";
    private static final String f_170655_ = "left_front_baby_leg";
    private static final String f_170656_ = "right_front_baby_leg";
    private static final String f_170657_ = "saddle";
    private static final String f_170658_ = "left_saddle_mouth";
    private static final String f_170659_ = "left_saddle_line";
    private static final String f_170660_ = "right_saddle_mouth";
    private static final String f_170661_ = "right_saddle_line";
    private static final String f_170662_ = "head_saddle";
    private static final String f_170663_ = "mouth_saddle_wrap";
    protected final ModelPart f_102751_;
    protected final ModelPart f_102752_;
    private final ModelPart f_170664_;
    private final ModelPart f_170665_;
    private final ModelPart f_170666_;
    private final ModelPart f_170642_;
    private final ModelPart f_170643_;
    private final ModelPart f_170644_;
    private final ModelPart f_170645_;
    private final ModelPart f_170646_;
    private final ModelPart f_102761_;
    private final ModelPart[] f_102762_;
    private final ModelPart[] f_102763_;

    public HorseModel(ModelPart p_170668_) {
        super(true, 16.2f, 1.36f, 2.7272f, 2.0f, 20.0f);
        this.f_102751_ = p_170668_.m_171324_("body");
        this.f_102752_ = p_170668_.m_171324_(f_170647_);
        this.f_170664_ = p_170668_.m_171324_("right_hind_leg");
        this.f_170665_ = p_170668_.m_171324_("left_hind_leg");
        this.f_170666_ = p_170668_.m_171324_("right_front_leg");
        this.f_170642_ = p_170668_.m_171324_("left_front_leg");
        this.f_170643_ = p_170668_.m_171324_(f_170654_);
        this.f_170644_ = p_170668_.m_171324_(f_170653_);
        this.f_170645_ = p_170668_.m_171324_(f_170656_);
        this.f_170646_ = p_170668_.m_171324_(f_170655_);
        this.f_102761_ = this.f_102751_.m_171324_("tail");
        ModelPart $$1 = this.f_102751_.m_171324_(f_170657_);
        ModelPart $$2 = this.f_102752_.m_171324_(f_170658_);
        ModelPart $$3 = this.f_102752_.m_171324_(f_170660_);
        ModelPart $$4 = this.f_102752_.m_171324_(f_170659_);
        ModelPart $$5 = this.f_102752_.m_171324_(f_170661_);
        ModelPart $$6 = this.f_102752_.m_171324_(f_170662_);
        ModelPart $$7 = this.f_102752_.m_171324_(f_170663_);
        this.f_102762_ = new ModelPart[]{$$1, $$2, $$3, $$6, $$7};
        this.f_102763_ = new ModelPart[]{$$4, $$5};
    }

    public static MeshDefinition m_170669_(CubeDeformation p_170670_) {
        MeshDefinition $$1 = new MeshDefinition();
        PartDefinition $$2 = $$1.m_171576_();
        PartDefinition $$3 = $$2.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171488_(-5.0f, -8.0f, -17.0f, 10.0f, 10.0f, 22.0f, new CubeDeformation(0.05f)), PartPose.m_171419_(0.0f, 11.0f, 5.0f));
        PartDefinition $$4 = $$2.m_171599_(f_170647_, CubeListBuilder.m_171558_().m_171514_(0, 35).m_171481_(-2.05f, -6.0f, -2.0f, 4.0f, 12.0f, 7.0f), PartPose.m_171423_(0.0f, 4.0f, -12.0f, 0.5235988f, 0.0f, 0.0f));
        PartDefinition $$5 = $$4.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 13).m_171488_(-3.0f, -11.0f, -2.0f, 6.0f, 5.0f, 7.0f, p_170670_), PartPose.f_171404_);
        $$4.m_171599_("mane", CubeListBuilder.m_171558_().m_171514_(56, 36).m_171488_(-1.0f, -11.0f, 5.01f, 2.0f, 16.0f, 2.0f, p_170670_), PartPose.f_171404_);
        $$4.m_171599_("upper_mouth", CubeListBuilder.m_171558_().m_171514_(0, 25).m_171488_(-2.0f, -11.0f, -7.0f, 4.0f, 5.0f, 5.0f, p_170670_), PartPose.f_171404_);
        $$2.m_171599_("left_hind_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, p_170670_), PartPose.m_171419_(4.0f, 14.0f, 7.0f));
        $$2.m_171599_("right_hind_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, p_170670_), PartPose.m_171419_(-4.0f, 14.0f, 7.0f));
        $$2.m_171599_("left_front_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, p_170670_), PartPose.m_171419_(4.0f, 14.0f, -12.0f));
        $$2.m_171599_("right_front_leg", CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, p_170670_), PartPose.m_171419_(-4.0f, 14.0f, -12.0f));
        CubeDeformation $$6 = p_170670_.m_171471_(0.0f, 5.5f, 0.0f);
        $$2.m_171599_(f_170653_, CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, $$6), PartPose.m_171419_(4.0f, 14.0f, 7.0f));
        $$2.m_171599_(f_170654_, CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, $$6), PartPose.m_171419_(-4.0f, 14.0f, 7.0f));
        $$2.m_171599_(f_170655_, CubeListBuilder.m_171558_().m_171514_(48, 21).m_171480_().m_171488_(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, $$6), PartPose.m_171419_(4.0f, 14.0f, -12.0f));
        $$2.m_171599_(f_170656_, CubeListBuilder.m_171558_().m_171514_(48, 21).m_171488_(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, $$6), PartPose.m_171419_(-4.0f, 14.0f, -12.0f));
        $$3.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(42, 36).m_171488_(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 4.0f, p_170670_), PartPose.m_171423_(0.0f, -5.0f, 2.0f, 0.5235988f, 0.0f, 0.0f));
        $$3.m_171599_(f_170657_, CubeListBuilder.m_171558_().m_171514_(26, 0).m_171488_(-5.0f, -8.0f, -9.0f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.5f)), PartPose.f_171404_);
        $$4.m_171599_(f_170658_, CubeListBuilder.m_171558_().m_171514_(29, 5).m_171488_(2.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, p_170670_), PartPose.f_171404_);
        $$4.m_171599_(f_170660_, CubeListBuilder.m_171558_().m_171514_(29, 5).m_171488_(-3.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, p_170670_), PartPose.f_171404_);
        $$4.m_171599_(f_170659_, CubeListBuilder.m_171558_().m_171514_(32, 2).m_171488_(3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, p_170670_), PartPose.m_171430_(-0.5235988f, 0.0f, 0.0f));
        $$4.m_171599_(f_170661_, CubeListBuilder.m_171558_().m_171514_(32, 2).m_171488_(-3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, p_170670_), PartPose.m_171430_(-0.5235988f, 0.0f, 0.0f));
        $$4.m_171599_(f_170662_, CubeListBuilder.m_171558_().m_171514_(1, 1).m_171488_(-3.0f, -11.0f, -1.9f, 6.0f, 5.0f, 6.0f, new CubeDeformation(0.2f)), PartPose.f_171404_);
        $$4.m_171599_(f_170663_, CubeListBuilder.m_171558_().m_171514_(19, 0).m_171488_(-2.0f, -11.0f, -4.0f, 4.0f, 5.0f, 2.0f, new CubeDeformation(0.2f)), PartPose.f_171404_);
        $$5.m_171599_("left_ear", CubeListBuilder.m_171558_().m_171514_(19, 16).m_171488_(0.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new CubeDeformation(-0.001f)), PartPose.f_171404_);
        $$5.m_171599_("right_ear", CubeListBuilder.m_171558_().m_171514_(19, 16).m_171488_(-2.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, new CubeDeformation(-0.001f)), PartPose.f_171404_);
        return $$1;
    }

    @Override
    public void m_6973_(T p_102785_, float p_102786_, float p_102787_, float p_102788_, float p_102789_, float p_102790_) {
        boolean $$6 = ((AbstractHorse)p_102785_).m_6254_();
        boolean $$7 = ((Entity)p_102785_).m_20160_();
        for (ModelPart $$8 : this.f_102762_) {
            $$8.f_104207_ = $$6;
        }
        for (ModelPart $$9 : this.f_102763_) {
            $$9.f_104207_ = $$7 && $$6;
        }
        this.f_102751_.f_104201_ = 11.0f;
    }

    @Override
    public Iterable<ModelPart> m_5607_() {
        return ImmutableList.of((Object)this.f_102752_);
    }

    @Override
    protected Iterable<ModelPart> m_5608_() {
        return ImmutableList.of((Object)this.f_102751_, (Object)this.f_170664_, (Object)this.f_170665_, (Object)this.f_170666_, (Object)this.f_170642_, (Object)this.f_170643_, (Object)this.f_170644_, (Object)this.f_170645_, (Object)this.f_170646_);
    }

    @Override
    public void m_6839_(T p_102780_, float p_102781_, float p_102782_, float p_102783_) {
        super.m_6839_(p_102780_, p_102781_, p_102782_, p_102783_);
        float $$4 = Mth.m_14201_(((AbstractHorse)p_102780_).f_20884_, ((AbstractHorse)p_102780_).f_20883_, p_102783_);
        float $$5 = Mth.m_14201_(((AbstractHorse)p_102780_).f_20886_, ((AbstractHorse)p_102780_).f_20885_, p_102783_);
        float $$6 = Mth.m_14179_(p_102783_, ((AbstractHorse)p_102780_).f_19860_, ((Entity)p_102780_).m_146909_());
        float $$7 = $$5 - $$4;
        float $$8 = $$6 * ((float)Math.PI / 180);
        if ($$7 > 20.0f) {
            $$7 = 20.0f;
        }
        if ($$7 < -20.0f) {
            $$7 = -20.0f;
        }
        if (p_102782_ > 0.2f) {
            $$8 += Mth.m_14089_(p_102781_ * 0.4f) * 0.15f * p_102782_;
        }
        float $$9 = ((AbstractHorse)p_102780_).m_30663_(p_102783_);
        float $$10 = ((AbstractHorse)p_102780_).m_30667_(p_102783_);
        float $$11 = 1.0f - $$10;
        float $$12 = ((AbstractHorse)p_102780_).m_30533_(p_102783_);
        boolean $$13 = ((AbstractHorse)p_102780_).f_30517_ != 0;
        float $$14 = (float)((AbstractHorse)p_102780_).f_19797_ + p_102783_;
        this.f_102752_.f_104201_ = 4.0f;
        this.f_102752_.f_104202_ = -12.0f;
        this.f_102751_.f_104203_ = 0.0f;
        this.f_102752_.f_104203_ = 0.5235988f + $$8;
        this.f_102752_.f_104204_ = $$7 * ((float)Math.PI / 180);
        float $$15 = ((Entity)p_102780_).m_20069_() ? 0.2f : 1.0f;
        float $$16 = Mth.m_14089_($$15 * p_102781_ * 0.6662f + (float)Math.PI);
        float $$17 = $$16 * 0.8f * p_102782_;
        float $$18 = (1.0f - Math.max($$10, $$9)) * (0.5235988f + $$8 + $$12 * Mth.m_14031_($$14) * 0.05f);
        this.f_102752_.f_104203_ = $$10 * (0.2617994f + $$8) + $$9 * (2.1816616f + Mth.m_14031_($$14) * 0.05f) + $$18;
        this.f_102752_.f_104204_ = $$10 * $$7 * ((float)Math.PI / 180) + (1.0f - Math.max($$10, $$9)) * this.f_102752_.f_104204_;
        this.f_102752_.f_104201_ = $$10 * -4.0f + $$9 * 11.0f + (1.0f - Math.max($$10, $$9)) * this.f_102752_.f_104201_;
        this.f_102752_.f_104202_ = $$10 * -4.0f + $$9 * -12.0f + (1.0f - Math.max($$10, $$9)) * this.f_102752_.f_104202_;
        this.f_102751_.f_104203_ = $$10 * -0.7853982f + $$11 * this.f_102751_.f_104203_;
        float $$19 = 0.2617994f * $$10;
        float $$20 = Mth.m_14089_($$14 * 0.6f + (float)Math.PI);
        this.f_170642_.f_104201_ = 2.0f * $$10 + 14.0f * $$11;
        this.f_170642_.f_104202_ = -6.0f * $$10 - 10.0f * $$11;
        this.f_170666_.f_104201_ = this.f_170642_.f_104201_;
        this.f_170666_.f_104202_ = this.f_170642_.f_104202_;
        float $$21 = (-1.0471976f + $$20) * $$10 + $$17 * $$11;
        float $$22 = (-1.0471976f - $$20) * $$10 - $$17 * $$11;
        this.f_170665_.f_104203_ = $$19 - $$16 * 0.5f * p_102782_ * $$11;
        this.f_170664_.f_104203_ = $$19 + $$16 * 0.5f * p_102782_ * $$11;
        this.f_170642_.f_104203_ = $$21;
        this.f_170666_.f_104203_ = $$22;
        this.f_102761_.f_104203_ = 0.5235988f + p_102782_ * 0.75f;
        this.f_102761_.f_104201_ = -5.0f + p_102782_;
        this.f_102761_.f_104202_ = 2.0f + p_102782_ * 2.0f;
        this.f_102761_.f_104204_ = $$13 ? Mth.m_14089_($$14 * 0.7f) : 0.0f;
        this.f_170643_.f_104201_ = this.f_170664_.f_104201_;
        this.f_170643_.f_104202_ = this.f_170664_.f_104202_;
        this.f_170643_.f_104203_ = this.f_170664_.f_104203_;
        this.f_170644_.f_104201_ = this.f_170665_.f_104201_;
        this.f_170644_.f_104202_ = this.f_170665_.f_104202_;
        this.f_170644_.f_104203_ = this.f_170665_.f_104203_;
        this.f_170645_.f_104201_ = this.f_170666_.f_104201_;
        this.f_170645_.f_104202_ = this.f_170666_.f_104202_;
        this.f_170645_.f_104203_ = this.f_170666_.f_104203_;
        this.f_170646_.f_104201_ = this.f_170642_.f_104201_;
        this.f_170646_.f_104202_ = this.f_170642_.f_104202_;
        this.f_170646_.f_104203_ = this.f_170642_.f_104203_;
        boolean $$23 = ((AgeableMob)p_102780_).m_6162_();
        this.f_170664_.f_104207_ = !$$23;
        this.f_170665_.f_104207_ = !$$23;
        this.f_170666_.f_104207_ = !$$23;
        this.f_170642_.f_104207_ = !$$23;
        this.f_170643_.f_104207_ = $$23;
        this.f_170644_.f_104207_ = $$23;
        this.f_170645_.f_104207_ = $$23;
        this.f_170646_.f_104207_ = $$23;
        this.f_102751_.f_104201_ = $$23 ? 10.8f : 0.0f;
    }
}

